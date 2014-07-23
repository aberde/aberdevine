package kr.co.gitech.storyz.common.push;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * iOS 메시지 발송기 <br/>
 */
public interface IosApnsService {

	public Future<Integer> send(List<MessageEntry> contextList, Map<String, Object> customMessage, PushMessage message) throws Exception;
}
