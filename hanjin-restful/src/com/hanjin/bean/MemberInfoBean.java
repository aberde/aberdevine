package com.hanjin.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Result")
@XmlAccessorType(XmlAccessType.NONE)
public class MemberInfoBean {

	/** ���� ���� */
	private String result;

	/** �������дܰ� */
	private String failDepth;
	
	/** ����ھ��̵� */
	private String luid;
	
	/** ����ھ��̵� */
	private String guid;
	
	/** email */
	private String email;
	
	/** JSESSIONID */
	private String JSESSIONID;
	
	/** GWmail �������� ���ϼ� */
	private String gwMailCount;
	
	/** e-Mail �������� ���ϼ� */
	private String emailCount;
	
	/** Approval ����Ǽ� */
	private String approvalCount;
	
	/** Bulletin Borad  �ű԰Խñۼ� */
	private String bulletinBoradCount;
	
	/** Messages �����޽����� */
	private String messageCount;

	@XmlElement(name = "result")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@XmlElement(name = "failDepth")
	public String getFailDepth() {
		return failDepth;
	}

	public void setFailDepth(String failDepth) {
		this.failDepth = failDepth;
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

	@XmlElement(name = "JSESSIONID")
	public String getJSESSIONID() {
		return JSESSIONID;
	}

	public void setJSESSIONID(String jSESSIONID) {
		JSESSIONID = jSESSIONID;
	}

	@XmlElement(name = "gwmailcount")
	public String getGwMailCount() {
		return gwMailCount;
	}

	public void setGwMailCount(String gwMailCount) {
		this.gwMailCount = gwMailCount;
	}

	@XmlElement(name = "emailcount")
	public String getEmailCount() {
		return emailCount;
	}

	public void setEmailCount(String emailCount) {
		this.emailCount = emailCount;
	}

	@XmlElement(name = "approvalcount")
	public String getApprovalCount() {
		return approvalCount;
	}

	public void setApprovalCount(String approvalCount) {
		this.approvalCount = approvalCount;
	}

	@XmlElement(name = "bulletinboradcount")
	public String getBulletinBoradCount() {
		return bulletinBoradCount;
	}

	public void setBulletinBoradCount(String bulletinBoradCount) {
		this.bulletinBoradCount = bulletinBoradCount;
	}

	@XmlElement(name = "messagecount")
	public String getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(String messageCount) {
		this.messageCount = messageCount;
	}

}
