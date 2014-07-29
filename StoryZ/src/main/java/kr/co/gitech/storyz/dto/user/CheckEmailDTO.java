package kr.co.gitech.storyz.dto.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import kr.co.gitech.storyz.dto.common.BasicDTO;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "root")
/**
 * 이메일 중복 체크
 */
public class CheckEmailDTO extends BasicDTO {

	/** 이메일 사용가능 여부(Y: 등록가능, N: 중복) */
	@XmlElement(name = "register_yn", nillable = true)
	private String register_yn;

	public String getRegister_yn() {
		return register_yn;
	}

	public void setRegister_yn(String register_yn) {
		this.register_yn = register_yn;
	}
}
