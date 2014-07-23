package kr.co.gitech.storyz.common.push;


/**
 * 안드로이드, 아이폰 메시지 통합 발송 서비스
 */
public interface StoryZMessageService {

	public int send(MessageContext context, PushMessage message);
}
