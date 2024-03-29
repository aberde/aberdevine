package kr.co.gitech.storyz.dto.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import kr.co.gitech.storyz.dto.common.BasicDTO;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "root")
/**
 * 아이디 찾기
 */
public class FindIdDTO extends BasicDTO {

	/** 사용자 객체 */
	@XmlElement(name = "user", nillable = true)
	private UserDTO user;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
}
