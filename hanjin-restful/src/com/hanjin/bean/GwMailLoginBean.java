package com.hanjin.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class GwMailLoginBean {

	private Result result;
	
	public Result getResult() {
		return result;
	}
	
	public void setResult(Result result) {
		this.result = result;
	}
	
	@XmlRootElement(name = "Result")
	@XmlAccessorType(XmlAccessType.NONE)
	public static class Result {
		/** GW Mail 로그인 성공여부 */
		private String Status;
	
		@XmlElement(name = "Status")
		public String getStatus() {
			return Status;
		}
	
		public void setStatus(String status) {
			Status = status;
		}
	}
}
