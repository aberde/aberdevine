package kr.co.gitech.storyz.dao.user;

import kr.co.gitech.storyz.dto.user.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

/**
 * 사용자 정보
 */
@SuppressWarnings("unchecked")
@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	public UserDTO findId(ModelMap model) throws Exception {
		UserDTO userDTO = (UserDTO)sqlMapClientTemplate.queryForObject("user.findId", model);
		return userDTO;
	}
}
