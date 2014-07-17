package kr.co.gitech.storyz.dao.user;

import kr.co.gitech.storyz.dto.user.UserDTO;

import org.springframework.ui.ModelMap;

/**
 * 사용자 정보
 */
public interface UserDAO {

	public UserDTO findId(ModelMap model) throws Exception;
}
