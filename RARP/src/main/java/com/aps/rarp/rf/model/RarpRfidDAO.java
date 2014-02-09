package com.aps.rarp.rf.model;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

/**
 * @author Administrator
 * @version 1.0
 * @created 21-11-2013 오후 3:42:53
 */
@Repository("rarpRfidDAO")
public class RarpRfidDAO extends EgovAbstractDAO{
	
	/**
	 * 인프라 장애 건수 조회
	 * 
	 * @param paramMap
	 * @exception Exception
	 */
	public int selectRcvrCount(HashMap<String, String> paramMap) throws Exception{
		return (Integer)selectByPk("RarpRfidDAO.selectRcvrCount", paramMap);		
	}
	
	/**
	 * 인프라 장애 정보 조회
	 * 
	 * @param paramMap
	 * @exception Exception
	 */
	public List<HashMap<String, Object>> selectRcvrList(HashMap<String, String> paramMap) throws Exception{
		return list("RarpRfidDAO.selectRcvrList", paramMap);		
	}

	/**
	 * 센싱 건수 조회
	 * 
	 * @param paramMap
	 * @exception Exception
	 */
	public int selectTagSensingCount(HashMap<String, String> paramMap)
	  throws Exception{
		return (Integer)selectByPk("RarpRfidDAO.selectTagSensingCount", paramMap);
	}

	/**
	 * 태그 센싱 정보 조회
	 * 
	 * @param paramMap
	 * @exception Exception
	 */
	public List<HashMap<String, String>> selectTagSensingList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpRfidDAO.selectTagSensingList", paramMap);
	}

	/**
	 * 인프라 구조 정보 조회
	 * 
	 * @param paramMap
	 * @exception Exception
	 */
	public List<HashMap<String, String>> selectInfraList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpRfidDAO.selectInfraList", paramMap);
	}

	/**
	 * 인프라  정보 조회
	 * 
	 * @param paramMap
	 * @exception Exception
	 */
	public List<HashMap<String, String>> selectResourceList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpRfidDAO.selectResourceList", paramMap);
	}

	/**
	 * 복구정보
	 * 
	 * @param paramMap
	 * @return 
	 * @exception Exception
	 */
	public List<HashMap<String, String>> selectResRcvrList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpRfidDAO.selectResRcvrList", paramMap);	
	}
	/**
	 * 복구정보
	 * 
	 * @param paramMap
	 * @return 
	 * @exception Exception
	 */
	public List<HashMap<String, String>> selectReaderAntennaList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpRfidDAO.selectReaderAntennaList", paramMap);	
	}
	/**
	 * 모니터링 정보
	 * 
	 * @param paramMap
	 * @return 
	 * @exception Exception
	 */
	public HashMap<String,String> selectResMonInfo(HashMap<String, String> paramMap)
	  throws Exception{
		return (HashMap<String, String>)selectByPk("RarpRfidDAO.selectResMonInfo", paramMap);	
	}
	/**
	 * 리소스 정보
	 * @return
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public HashMap<String,String> selectResInfo(HashMap<String, String> paramMap)
	  throws Exception{
		return (HashMap<String, String>)selectByPk("RarpRfidDAO.selectResourceList", paramMap);	
	}

	/**
	 * 전체 부품 정보
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectTagPartList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpRfidDAO.selectTagPartList", paramMap);
	}

	/**
	 * 센싱된 부품 정보
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectTagSensingPartList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpRfidDAO.selectTagSensingPartList", paramMap);
	}

	/**
	 * 상위 부품 정보
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectTagParentPartList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpRfidDAO.selectTagParentPartList", paramMap);
	}

	
}//end RarpRfidDAO