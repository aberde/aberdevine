package com.hanjin.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class EmailLoginBean {

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

		/** Cookie의 JSESSIONID */
		private String JSESSIONID;
		
		private String total;
		
		/** 성공 */
		private String success;
		
		/** 실패 */
		private String fail;
		
		@XmlElement(name = "JSESSIONID")
		public String getJSESSIONID() {
			return JSESSIONID;
		}
		
		public void setJSESSIONID(String JSESSIONID) {
			this.JSESSIONID = JSESSIONID;
		}

		@XmlElement(name = "total")
		public String getTotal() {
			return total;
		}

		public void setTotal(String total) {
			this.total = total;
		}

		@XmlElement(name = "success")
		public String getSuccess() {
			return success;
		}

		public void setSuccess(String success) {
			this.success = success;
		}

		@XmlElement(name = "fail")
		public String getFail() {
			return fail;
		}

		public void setFail(String fail) {
			this.fail = fail;
		}
		
	}
}
