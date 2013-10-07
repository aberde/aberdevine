package com.hanjin.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class MessageCountBean {

	private Result result;
	
	public Result getResult() {
		return result;
	}
	
	public void setResult(Result result) {
		this.result = result;
	}
	
	
	@XmlRootElement(name = "result")
	@XmlAccessorType(XmlAccessType.NONE)
	public static class Result {

		/** message Count */
		private String messageCount;
		
		@XmlElement(name = "messageCount")
		public String getMessageCount() {
			return messageCount;
		}
		
		public void setMessageCount(String messageCount) {
			this.messageCount = messageCount;
		}

	}
}
