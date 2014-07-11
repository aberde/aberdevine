package kr.co.gitech.storyz.common.message.pushcode;

import org.springframework.util.Assert;

/**
 * Push Server정보 코드 정의
 */
public enum PushCode {

	// #######################################################
	// ## IOS Push Server 접근
	// #######################################################
	/** IOS 인증서 위치 */
	CERT_FILE_PATH("push.ios.certfilepath")

	/** IOS 인증서 비밀번호 */
	, CERT_PASSWORD("push.ios.certpassword")
	
	/** SANDBOX 사용여부 */
	, USE_SANDBOX("push.ios.usesandbox");
	// #######################################################

	/**
	 * @param message
	 *            메시지 코드
	 */
	private PushCode(String message) {
		Assert.notNull(message, "Message must not be null");
		
		this.message = message;
	}

	/**
	 * 메시지 코드
	 */
	private final String message;

	/**
	 * 메시지 코드
	 */
	public String message() {
		return message;
	}
}
