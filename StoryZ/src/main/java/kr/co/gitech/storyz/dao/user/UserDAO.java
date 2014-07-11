package kr.co.gitech.storyz.dao.user;

import java.util.HashMap;

import org.springframework.ui.ModelMap;

/**
 * 사용자 정보
 */
public interface UserDAO {

	public HashMap<String, String> findId(ModelMap model) throws Exception;
}
