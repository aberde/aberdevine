package kr.co.gitech.storyz.dao.user;

import java.util.Map;

import kr.co.gitech.storyz.dto.user.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

/**
 * 사용자 정보
 */
@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	/**
	 * 아이디 중복 체크
	 */
	public Object checkId(Map<String, Object> map) throws Exception {
		return sqlMapClientTemplate.queryForObject("user.checkId", map);
	}
	
	/**
	 * 이메일 중복 체크
	 */
	public UserDTO checkEmail(Map<String, Object> map) throws Exception {
		UserDTO userDTO = (UserDTO)sqlMapClientTemplate.queryForObject("user.checkEmail", map);
		return userDTO;
	}
	
	/**
	 * 예약아이디에 포함이 되어 있는지 체크
	 */
	public Object checkIdWithReserv(Map<String, Object> map) throws Exception {
		return sqlMapClientTemplate.queryForObject("user._checkIdWithReserv", map);
	}
	
	/**
	 * 아이디 찾기
	 */
	public UserDTO findId(Map<String, Object> map) throws Exception {
		UserDTO userDTO = (UserDTO)sqlMapClientTemplate.queryForObject("user.findId", map);
		return userDTO;
	}
}
