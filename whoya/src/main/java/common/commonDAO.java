package common;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.whoya;

@Repository("whoya")
public class commonDAO implements whoya {

	@Resource(name="smcTemplate")
	private SqlMapClientTemplate smcTemplate;

	public whoyaMap select(String id, whoyaMap params) throws DataAccessException {
		whoyaMap result;
		Object obj = smcTemplate.queryForObject(id, params);
		
		if (obj != null) 
			result = (whoyaMap)obj;
		else
			result = new whoyaMap();
		
		return result;
	}

	public whoyaList list(String id, whoyaMap params) throws DataAccessException {
		whoyaList result;
		
		@SuppressWarnings("rawtypes")
		List list = smcTemplate.queryForList(id, params);
		
		if (list != null) 
			result = new whoyaList(list);
		else
			result = new whoyaList();
		
		return result;
	}

	/**
	 * ibatis에서 selectKey를 사용해서 키 값을 가져올 경우 사용한다.
	 */
	public Object insertWithPK(String id, whoyaMap params) throws DataAccessException {
		Object result = smcTemplate.insert(id, params);
		return result;
	}
	
	public int insert(String id, whoyaMap params) throws DataAccessException {
		return smcTemplate.update(id, params);
	}

	public int update(String id, whoyaMap params) throws DataAccessException {
		return smcTemplate.update(id, params);
	}

	public int delete(String id, whoyaMap params) throws DataAccessException {
		return smcTemplate.delete(id, params);
	}

	/**
	 * count 같이 단일 숫자를 가져올때 사용.
	 */
	public long getNumber(String id, whoyaMap params) throws DataAccessException {
		return (Long)smcTemplate.queryForObject(id, params);
	}
}
