package egovframework.com.ext.jfile.sample.service.impl;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository
public class SampleDAO extends EgovComAbstractDAO {

	private Log log = LogFactory.getLog(getClass());
	public void testConnection() {
		try {
			if(log.isDebugEnabled()) {
				log.debug(getSqlMapClientTemplate().getDataSource().getConnection());
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
}