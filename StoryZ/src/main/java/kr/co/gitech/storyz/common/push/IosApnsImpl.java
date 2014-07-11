package kr.co.gitech.storyz.common.push;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import kr.co.gitech.storyz.common.StoryZConstants;
import kr.co.gitech.storyz.common.message.MessageResolver;
import kr.co.gitech.storyz.common.message.pushcode.PushCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;
import com.notnoop.apns.PayloadBuilder;

/**
 * iOS 메시지 발송기 <br/>
 */
public class IosApnsImpl {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Async
	public Future<Integer> send(List<MessageEntry> contextList, Map<String, Object> customMessage, PushMessage message) throws Exception {
		int result = 0;

		ApnsService service = getApnsService();
		if (service == null)
			return new AsyncResult<Integer>(result);

		for (MessageEntry entry : contextList) {
			try {
				if (!(StoryZConstants.PLATFORM_KIND_IPHONE.equals(entry.getPlatform_kind()) || StoryZConstants.PLATFORM_KIND_IPAD.equals(entry.getPlatform_kind())))
					continue;
				String deviceToken = entry.getPns_token();
				PayloadBuilder builder = APNS.newPayload().alertBody(entry.getAlert()).sound(entry.getSound());
				if (customMessage != null)
					builder.customFields(customMessage);
				builder.customField(MessageEntry.MESSAGE_TYPE, message.getType().kind());
				int badge = entry.getBadge();
				if (message.isUseBadge() && badge > 0) {
					builder.badge(badge);
				}

				if (builder.isTooLong()) {
					builder = builder.shrinkBody();
				}
				service.push(deviceToken, builder.build());
				logger.debug("PNS : sending pns_token <{}>, message <{}> to Apple PNS", deviceToken, builder.build());
				result++;
			} catch (Exception e) {
				logger.error("APNS send fail. target <" + entry.getUser_key() + "|" + entry.getAlert() + "> message <{}>", e.getMessage(), e);
			} finally {
			}
		}
		logger.warn("completed to send to APNS");
		return new AsyncResult<Integer>(result);
	}

	private ApnsService getApnsService() {
		ApnsService service = null;
		try {
			InputStream certStream = this.getClass().getClassLoader().getResourceAsStream(MessageResolver.getMessage(PushCode.CERT_FILE_PATH.message()));
			ApnsServiceBuilder serviceBuilder = APNS.newService().withCert(certStream, MessageResolver.getMessage(PushCode.CERT_PASSWORD.message()));
			String sandbox = MessageResolver.getMessage(PushCode.USE_SANDBOX.message());
			boolean b = Boolean.valueOf(sandbox);
			if (b) {
				service = serviceBuilder.withSandboxDestination().build();
			} else {
				service = serviceBuilder.withProductionDestination().build();
			}
		} catch (Exception e) {
			logger.error("APNS connection fail. Message <{}>", e.getMessage(), e);
		}

		return service;
	}
}
