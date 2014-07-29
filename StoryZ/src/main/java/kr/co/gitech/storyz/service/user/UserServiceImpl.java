package kr.co.gitech.storyz.service.user;

import java.util.Map;

import kr.co.gitech.storyz.dao.user.UserDAO;
import kr.co.gitech.storyz.dto.user.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 사용자 정보
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	/**
	 * 아이디 중복 체크(Y: 등록가능, N: 중복, R: 예약ID)
	 */
	public String checkId(Map<String, Object> map) throws Exception {
		String result = "";
		// 아이디 존재 확인
		String user_id = (String)userDAO.checkId(map);
		if ( user_id == null ) {
			// 예약아이디 존재 확인
			String reserv_id = (String)userDAO.checkIdWithReserv(map);
			if ( reserv_id == null ) {
				result = "Y";
			} else {
				result = "R";
			}
		} else {
			result = "N";
		}

		return result;
	}
	
	/**
	 * 이메일 중복 체크(Y: 등록가능, N: 중복)
	 */
	public String checkEmail(Map<String, Object> map) throws Exception {
		String result = "";
		// 이메일 존재 확인
		UserDTO dto = (UserDTO)userDAO.checkEmail(map);
		if ( dto == null ) {
			result = "Y";
		} else {
			result = "N";
		}

		return result;
	}
	
	/**
	 * 아이디 찾기
	 */
	public UserDTO findId(Map<String, Object> map) throws Exception {
		return userDAO.findId(map);
	}
}
