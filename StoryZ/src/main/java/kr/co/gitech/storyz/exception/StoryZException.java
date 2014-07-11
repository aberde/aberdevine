package kr.co.gitech.storyz.exception;

import java.util.Map;

import kr.co.gitech.storyz.common.message.MessageResolver;
import kr.co.gitech.storyz.common.message.errorcode.ErrorCode;

/**
 * 예외 처리를 위한 클래스
 */
@SuppressWarnings("serial")
public class StoryZException extends RuntimeException {

	private ErrorCode result = ErrorCode.INTERNAL_ERROR;

	private Map<String, Object> model = null;

	/**
	 * 메시지 코드 리스트
	 */
	private String[] codes;

	/**
	 * 메시지 인수 리스트
	 */
	private Object[] arguments;

	/**
	 * 기본 메시지
	 */
	private String defaultMessage;

	public StoryZException(ErrorCode result) {
		this(result, MessageResolver.getMessage(result.message()));
	}

	public StoryZException(ErrorCode result, String message) {
		super(message);
		this.result = result;
	}

	public StoryZException(ErrorCode result, String message, Throwable cause) {
		super(message, cause);
		this.result = result;
	}

	/**
	 * 에러 응답시에 추가 파라미터를 전송할 경우에 사용.
	 * 
	 * @param result
	 * @param message
	 * @param model
	 */
	public StoryZException(ErrorCode result, String message, Map<String, Object> model) {
		this(result, null, message, model);
	}

	/**
	 * 필수! 메시지에 파라미터가 필요할 때 사용.
	 * 
	 * @param result
	 * @param arguments
	 * @param defaultMessage
	 */
	public StoryZException(ErrorCode result, Object[] arguments, String defaultMessage) {
		this(result, arguments, defaultMessage, null);
	}

	public StoryZException(ErrorCode result, Object[] arguments, String defaultMessage, Map<String, Object> model) {
		this(result, null);
		this.arguments = arguments;
		if (defaultMessage == null)
			defaultMessage = result.message();
		this.defaultMessage = defaultMessage;
		setModel(model);
	}

	/**
	 * 에러 응답시에 메시지를 변환해야 할 경우에 사용.
	 * 
	 * @param result
	 * @param message
	 * @param arguments
	 */
	public StoryZException(ErrorCode result, String message, Object[] arguments) {
		this(result, null);
		this.arguments = arguments;
		this.defaultMessage = message;
	}

	public ErrorCode getResult() {
		return result;
	}

	/**
	 * @return the codes
	 */
	public String[] getCodes() {
		return codes;
	}

	/**
	 * @param codes
	 *            the codes to set
	 */
	public void setCodes(String[] codes) {
		this.codes = codes;
	}

	/**
	 * @return the arguments
	 */
	public Object[] getArguments() {
		return arguments;
	}

	/**
	 * @param arguments
	 *            the arguments to set
	 */
	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	/**
	 * @return the defaultMessage
	 */
	public String getDefaultMessage() {
		return defaultMessage;
	}

	/**
	 * @param defaultMessage
	 *            the defaultMessage to set
	 */
	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	@Override
	public String getMessage() {
		String message = super.getMessage();
		if (message == null) {
			message = result != null ? "code: " + result.message() + ", type: " + getClass().getName() : getLocalizedMessage();
		}
		return message;
	}

	@Override
	public String getLocalizedMessage() {
		return super.getMessage();
	}

	/**
	 * @return the model
	 */
	public Map<String, Object> getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

}
