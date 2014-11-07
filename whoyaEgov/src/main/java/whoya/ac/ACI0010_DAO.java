package whoya.ac;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface ACI0010_DAO {

	public Map<String, String> select(String id, Map<String, String> params) throws DataAccessException;

	public List<Map<String, String>> list(String id, Map<String, String> params) throws DataAccessException;

	public Object insertWithPK(String id, Map<String, String> params) throws DataAccessException;
	
	public int insert(String id, Map<String, String> params) throws DataAccessException;

	public int update(String id, Map<String, String> params) throws DataAccessException;

	public int delete(String id, Map<String, String> params) throws DataAccessException;

	public long getNumber(String id, Map<String, String> params) throws DataAccessException;
}
