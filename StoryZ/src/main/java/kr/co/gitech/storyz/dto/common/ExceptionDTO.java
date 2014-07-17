package kr.co.gitech.storyz.dto.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import kr.co.gitech.storyz.common.StoryZConstants;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "root")
public class ExceptionDTO {
	
	/** 처리결과 코드 (성공:0 / 실패:기타) */
	@XmlElement(name = "status")
	private int status = StoryZConstants.SUCCESS;
	
	/** 처리결과 메시지 */
	@XmlElement(name = "message")
	private String message = StoryZConstants.SUCCESS_MESSAGE;

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
