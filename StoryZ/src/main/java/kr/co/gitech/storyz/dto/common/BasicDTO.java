package kr.co.gitech.storyz.dto.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class BasicDTO {
	
	/** 처리결과 코드 (성공:0 / 실패:기타) */
	@XmlElement(name = "status")
	private int status;
	
	/** 처리결과 메시지 */
	@XmlElement(name = "message")
	private String message;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
