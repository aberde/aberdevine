package whoya.ac;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

@Repository("ACI0010_DAO")
public class ACI0010_DAOImpl implements ACI0010_DAO {

	@Resource(name="smcTemplate")
	private SqlMapClientTemplate smcTemplate;

	public Map<String, String> select(String id, Map<String, String> params) throws DataAccessException {
		return (Map<String, String>)smcTemplate.queryForObject(id, params);
	}

	public List<Map<String, String>> list(String id, Map<String, String> params) throws DataAccessException {
		return smcTemplate.queryForList(id, params);
	}

	public Object insertWithPK(String id, Map<String, String> params) throws DataAccessException {
		Object result = smcTemplate.insert(id, params);
		return result;
	}
	
	public int insert(String id, Map<String, String> params) throws DataAccessException {
		return smcTemplate.update(id, params);
	}

	public int update(String id, Map<String, String> params) throws DataAccessException {
		return smcTemplate.update(id, params);
	}

	public int delete(String id, Map<String, String> params) throws DataAccessException {
		return smcTemplate.delete(id, params);
	}

	public long getNumber(String id, Map<String, String> params) throws DataAccessException {
		return (Long)smcTemplate.queryForObject(id, params);
	}
}
