package com.hanjin.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Result")
@XmlAccessorType(XmlAccessType.NONE)
public class SsoLoginBean {

	/** Cookies의 세션값 */
	private String SMSESSION;
	
	/** 사용자아이디 */
	private String luid;
	
	/** 사용자아이디 */
	private String guid;

	/** 사용자이메일 */
	private String email;

	@XmlElement(name = "SMSESSION")
	public String getSMSESSION() {
		return SMSESSION;
	}

	public void setSMSESSION(String SMSESSION) {
		this.SMSESSION = SMSESSION;
	}

	@XmlElement(name = "luid")
	public String getLuid() {
		return luid;
	}

	public void setLuid(String luid) {
		this.luid = luid;
	}

	@XmlElement(name = "guid")
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@XmlElement(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
