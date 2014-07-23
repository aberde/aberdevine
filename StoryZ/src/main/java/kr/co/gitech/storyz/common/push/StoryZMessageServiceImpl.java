package kr.co.gitech.storyz.common.push;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import kr.co.gitech.storyz.common.StoryZConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 안드로이드, 아이폰 메시지 통합 발송 서비스
 */
@Service("storyZMessageService")
public class StoryZMessageServiceImpl implements StoryZMessageService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IosApnsService iosApnsService;
	
	public int send(MessageContext context, PushMessage message) {
		if (context == null || context.getMessageEntries() == null || context.getMessageEntries().size() == 0) {
			return 0;
		}

		List<MessageEntry> androids = new ArrayList<MessageEntry>();
		List<MessageEntry> ios = new ArrayList<MessageEntry>();

		for (MessageEntry entry : context.getMessageEntries()) {
			String platformKind = entry.getPlatform_kind();
			if (null != platformKind && !"".equals(platformKind)) {
				if (StoryZConstants.PLATFORM_KIND_ANDROID.equals(platformKind)) {
					androids.add(entry);
				} else if (StoryZConstants.PLATFORM_KIND_TAB.equals(platformKind)) {
					androids.add(entry);
				} else if (StoryZConstants.PLATFORM_KIND_IPHONE.equals(platformKind)) {
					ios.add(entry);
				} else if (StoryZConstants.PLATFORM_KIND_IPAD.equals(platformKind)) {
					ios.add(entry);
				}
			}
		}
		logger.info("sum <{}>, androids <{}>, ios <{}>", new Integer[] { context.getMessageEntries().size(), androids.size(), ios.size() });

		int sendToAndroid = sendToAndroid(androids, context.getCustomProperty(), message);
		int sendToIos = sendToIos(ios, context.getCustomProperty(), message);
		return sendToAndroid + sendToIos;
	}

	private final int sendToIos(List<MessageEntry> messages, Map<String, Object> customMessage, PushMessage message) {
		int success = 0;
		if (messages.size() > 0) {
			try {
				Future<Integer> future = iosApnsService.send(messages, customMessage, message);
				success = future.get();
				logger.warn("PNS : sending success result of iOS <{}>, sum <{}>", success, messages.size());
			} catch (Exception e) {
				logger.error("Message <{}>", e.getMessage(), e);
				e.printStackTrace();
			}
		} else {
			logger.warn("ios list is empty");
		}
		return success;
	}

	private final int sendToAndroid(List<MessageEntry> messages, Map<String, Object> customMessage, PushMessage message) {
		int success = 0;
		return success;
	}
}
