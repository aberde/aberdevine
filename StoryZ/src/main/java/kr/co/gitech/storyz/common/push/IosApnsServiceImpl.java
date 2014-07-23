package kr.co.gitech.storyz.common.push;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Future;

import javapns.Push;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.PushedNotifications;
import kr.co.gitech.storyz.common.StoryZConstants;
import kr.co.gitech.storyz.common.message.MessageResolver;
import kr.co.gitech.storyz.common.message.pushcode.PushCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

/**
 * iOS 메시지 발송기 <br/>
 */
@Service("iosApnsService")
public class IosApnsServiceImpl implements IosApnsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

//	@Async
//	public Future<Integer> send(List<MessageEntry> contextList, Map<String, Object> customMessage, PushMessage message) throws Exception {
//		int result = 0;
//
//		ApnsService service = getApnsService();
//		if (service == null)
//			return new AsyncResult<Integer>(result);
//
//		for (MessageEntry entry : contextList) {
//			try {
//				if (!(StoryZConstants.PLATFORM_KIND_IPHONE.equals(entry.getPlatform_kind()) || StoryZConstants.PLATFORM_KIND_IPAD.equals(entry.getPlatform_kind())))
//					continue;
//				String deviceToken = entry.getPns_token();
//				PayloadBuilder builder = APNS.newPayload().alertBody(entry.getAlert()).sound(entry.getSound());
//				if (customMessage != null)
//					builder.customFields(customMessage);
//				builder.customField(MessageEntry.MESSAGE_TYPE, message.getType().kind());
//				int badge = entry.getBadge();
//				if (message.isUseBadge() && badge > 0) {
//					builder.badge(badge);
//				}
//
//				if (builder.isTooLong()) {
//					builder = builder.shrinkBody();
//				}
//				service.push(deviceToken, builder.build());
//				logger.debug("PNS : sending pns_token <{}>, message <{}> to Apple PNS", deviceToken, builder.build());
//				result++;
//			} catch (Exception e) {
//				logger.error("APNS send fail. target <" + entry.getUser_key() + "|" + entry.getAlert() + "> message <{}>", e.getMessage(), e);
//			} finally {
//			}
//		}
//		logger.warn("completed to send to APNS");
//		return new AsyncResult<Integer>(result);
//	}
//
//	private ApnsService getApnsService() {
//		ApnsService service = null;
//		try {
//			InputStream certStream = this.getClass().getClassLoader().getResourceAsStream(MessageResolver.getMessage(PushCode.CERT_FILE_PATH.message()));
//			ApnsServiceBuilder serviceBuilder = APNS.newService().withCert(certStream, MessageResolver.getMessage(PushCode.CERT_PASSWORD.message()));
//			String sandbox = MessageResolver.getMessage(PushCode.USE_SANDBOX.message());
//			boolean b = Boolean.valueOf(sandbox);
//			if (b) {
//				service = serviceBuilder.withSandboxDestination().build();
//			} else {
//				service = serviceBuilder.withProductionDestination().build();
//			}
//		} catch (Exception e) {
//			logger.error("APNS connection fail. Message <{}>", e.getMessage(), e);
//		}
//
//		return service;
//	}
	
	@Async
	public Future<Integer> send(List<MessageEntry> contextList, Map<String, Object> customMessage, PushMessage message) throws Exception {
		int result = 0;
		
		try {
			List<PayloadPerDevice> payloadDevicePairs = new Vector<PayloadPerDevice>();
			for (MessageEntry entry : contextList) {
				if (!(StoryZConstants.PLATFORM_KIND_IPHONE.equals(entry.getPlatform_kind()) || StoryZConstants.PLATFORM_KIND_IPAD.equals(entry.getPlatform_kind()))) {
					continue;
				}
				
				PushNotificationPayload payload = PushNotificationPayload.complex();
				payload.addAlert(entry.getAlert());
				if ( entry.getBadge() > 0 ) {
					payload.addBadge(entry.getBadge());
				}
				payload.addSound(entry.getSound());
				if( customMessage != null ) {
					Iterator<String> keys = customMessage.keySet().iterator();
					while( keys.hasNext() ) {
						String key = keys.next();
						String value = (String)customMessage.get(key);
						
						payload.addCustomDictionary(key, value);
					}
				}
				payload.addCustomDictionary(MessageEntry.MESSAGE_TYPE, message.getType().kind());
				
				PayloadPerDevice payloadPerDevice = new PayloadPerDevice(payload, entry.getPns_token());
				payloadDevicePairs.add(payloadPerDevice);
			}

			// Apple Push Server 전송.
			PushedNotifications notifications = Push.payloads(this.getClass().getClassLoader().getResourceAsStream(MessageResolver.getMessage(PushCode.CERT_FILE_PATH.message())), MessageResolver.getMessage(PushCode.CERT_PASSWORD.message()), false, payloadDevicePairs);

			if ( notifications != null && notifications.size() > 0 ) {
				for ( PushedNotification pushedNotification : notifications ) {
					// 전송성공여부 확인 
					if ( pushedNotification.isSuccessful() ) {
						result++;
					} else {
						logger.error("APNS send fail. target <" + pushedNotification.getDevice().getToken() + "> message <{}>", pushedNotification.getException().getMessage(), pushedNotification.getException());
					}
				}
			}
		} catch (Exception e) {
			logger.error("APNS send fail.", e.getMessage(), e);
		} 
			
		return new AsyncResult<Integer>(result);
	}

}
