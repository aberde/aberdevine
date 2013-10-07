package com.hanjin.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class EncryptPasswordBean {
	
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
		/** 암호화 된 비밀번호 */
		private String Pwd;
		
		@XmlElement(name = "Pwd")
		public String getPwd() {
			return Pwd;
		}
		
		public void setPwd(String Pwd) {
			this.Pwd = Pwd;
		}
	}

}
