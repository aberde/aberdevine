package com.aps.rarp.hs.model;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

/**
 * @author Administrator
 * @version 1.0
 * @created 21-11-2013 ���� 3:42:33
 */
@Repository("rarpHsHistoryDAO")
public class RarpHsHistoryDAO extends EgovAbstractDAO{

	

	/**
	 * 변경 이력 갯수 조회
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public int selectChagCount(HashMap<String, String> paramMap)
	  throws Exception{
		return (Integer)selectByPk("RarpHsHistoryDAO.selectChagCount", paramMap);
	}

	/**
	 * 장애 이력 갯수 조회
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public int selectTrblCount(HashMap<String, String> paramMap)
	  throws Exception{
		return (Integer)selectByPk("RarpHsHistoryDAO.selectTrblCount", paramMap);
	}

	/**
	 * 검사 이력 갯수 조회
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public int selectIsptCount(HashMap<String, String> paramMap)
	  throws Exception{
		return (Integer)selectByPk("RarpHsHistoryDAO.selectIsptCount", paramMap);
	}

	/**
	 * 변경 이력 정보 조회
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectChagList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpHsHistoryDAO.selectChagList", paramMap);	
	}

	/**
	 * 장애 이력 정보 조회
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectTrblList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpHsHistoryDAO.selectTrblList", paramMap);	
	}

	/**
	 * 검사 이력 조회
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectIsptList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpHsHistoryDAO.selectIsptList", paramMap);	
	}

	/**
	 * 변경 이력 부품 정보 조회
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectChagPartList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpHsHistoryDAO.selectChagPartList", paramMap);	
	}

	/**
	 * 장애 이력 부품 정보 조회
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectTrblPartList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpHsHistoryDAO.selectTrblPartList", paramMap);	
	}

	/**
	 * 엑셀 출력
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectChagExportList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpHsHistoryDAO.selectChagExportList", paramMap);	
	}
	/**
	 * 엑셀 출력
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectTrbExportlList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpHsHistoryDAO.selectTrbExportlList", paramMap);	
	}
	/**
	 * 엑셀 출력
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectIsptExportList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpHsHistoryDAO.selectIsptExportList", paramMap);	
	}
}//end RarpHsHistoryDAO