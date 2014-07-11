package kr.co.gitech.storyz.common.push;

import java.util.List;
import java.util.Map;

/**
 * Push Message Context
 */
public class MessageContext {

	/**
	 * Push 메시지 전송 수신자
	 */
	private List<MessageEntry> messageEntries = null;

	/**
	 * Push Custom 메시지
	 */
	private Map<String, Object> customProperty = null;

	public MessageContext() {
	}

	public MessageContext(List<MessageEntry> entries, Map<String, Object> customProperty) {
		this();
		setMessageEntries(entries);
		setCustomProperty(customProperty);
	}

	/**
	 * @return the messageEntries
	 */
	public List<MessageEntry> getMessageEntries() {
		return messageEntries;
	}

	/**
	 * @param messageEntries
	 *            the messageEntries to set
	 */
	public void setMessageEntries(List<MessageEntry> messageEntries) {
		this.messageEntries = messageEntries;
	}

	/**
	 * @return the customProperty
	 */
	public Map<String, Object> getCustomProperty() {
		return customProperty;
	}

	/**
	 * @param customProperty
	 *            the customProperty to set
	 */
	public void setCustomProperty(Map<String, Object> customProperty) {
		this.customProperty = customProperty;
	}

}
