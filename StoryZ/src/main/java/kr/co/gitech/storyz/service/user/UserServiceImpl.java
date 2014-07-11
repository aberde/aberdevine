package kr.co.gitech.storyz.service.user;

import java.util.HashMap;

import kr.co.gitech.storyz.dao.user.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
 * 사용자 정보
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	 
	public HashMap<String, String> findId(ModelMap model) throws Exception {
		return userDAO.findId(model);
	}
}
