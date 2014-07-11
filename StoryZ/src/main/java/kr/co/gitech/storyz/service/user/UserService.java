package kr.co.gitech.storyz.service.user;

import java.util.HashMap;

import org.springframework.ui.ModelMap;

/**
 * 사용자 정보
 */
public interface UserService {

	public HashMap<String, String> findId(ModelMap model) throws Exception;
}
