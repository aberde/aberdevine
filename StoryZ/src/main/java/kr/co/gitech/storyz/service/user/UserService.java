package kr.co.gitech.storyz.service.user;

import java.util.Map;

import kr.co.gitech.storyz.dto.user.UserDTO;

/**
 * 사용자 정보
 */
public interface UserService {

	/**
	 * 아이디 중복 체크
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String checkId(Map<String, Object> model) throws Exception;
	
	/**
	 * 아이디 찾기
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public UserDTO findId(Map<String, Object> map) throws Exception;
}
