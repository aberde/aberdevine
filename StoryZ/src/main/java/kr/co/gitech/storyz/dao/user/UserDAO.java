package kr.co.gitech.storyz.dao.user;

import java.util.Map;

import kr.co.gitech.storyz.dto.user.UserDTO;

/**
 * 사용자 정보
 */
public interface UserDAO {

	/**
	 * 아이디 중복 체크
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Object checkId(Map<String, Object> map) throws Exception;
	
	/**
	 * 이메일 중복 체크
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Object checkEmail(Map<String, Object> map) throws Exception;
	
	/**
	 * 예약아이디에 포함이 되어 있는지 체크
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Object checkIdWithReserv(Map<String, Object> map) throws Exception;
	
	/**
	 * 아이디 찾기
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public UserDTO findId(Map<String, Object> map) throws Exception;
}
