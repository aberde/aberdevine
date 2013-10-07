package com.hanjin.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Result")
@XmlAccessorType(XmlAccessType.NONE)
public class SsoLoginBean {

	/** Cookies�� ���ǰ� */
	private String SMSESSION;
	
	/** ����ھ��̵� */
	private String luid;
	
	/** ����ھ��̵� */
	private String guid;

	/** ������̸��� */
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
