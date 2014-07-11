package kr.co.gitech.storyz.dao.user;

import java.util.HashMap;

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
	
	public HashMap<String, String> findId(ModelMap model) throws Exception {
		HashMap<String, String> map = (HashMap<String, String>)sqlMapClientTemplate.queryForObject("user.findId", model);
		return map;
	}
}
