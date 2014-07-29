package kr.co.gitech.storyz.dto.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import kr.co.gitech.storyz.dto.common.BasicDTO;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "root")
/**
 * 아이디 중복 체크
 */
public class CheckIdDTO extends BasicDTO {

	/** 아이디 사용가능 여부(Y: 등록가능, N: 중복, R: 예약ID) */
	@XmlElement(name = "result", nillable = true)
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
