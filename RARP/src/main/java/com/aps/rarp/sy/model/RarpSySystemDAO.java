package com.aps.rarp.sy.model;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

/**
 * @author Administrator
 * @version 1.0
 * @created 21-11-2013 3:42:21
 */
@Repository("rarpSySystemDAO")
public class RarpSySystemDAO extends EgovAbstractDAO{

	/**
	 * 사업소 정보
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectRsmoInfoList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpSySystemDAO.selectRsmoInfoList", paramMap);	
	}

	/**
	 * 공통 코드 상세
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectCommDtlList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpSySystemDAO.selectCommDtlList", paramMap);	
	}
	/**
	 * 편성 정보
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectPrgInfoList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpSySystemDAO.selectPrgInfoList", paramMap);	
	}

	/**
	 * 차호 정보
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectCrgInfoList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpSySystemDAO.selectCrgInfoList", paramMap);	
	}
	
	/**
	 * 차호 정보
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public HashMap<String, String> selectCrgInfo(HashMap<String, String> paramMap)
	  throws Exception{
		return (HashMap<String, String>)selectByPk("RarpSySystemDAO.selectCrgInfo", paramMap);	
	}
	
	/**
	 * 공통코드상세
	 * 
	 * @param paramMap
	 * @exception Exception
	 */
	public HashMap<String, String> selectCommDtlInfo(HashMap<String, String> paramMap) throws Exception{
		return (HashMap<String, String>)selectByPk("RarpSySystemDAO.selectCommDtlList", paramMap);		
	}

	/**
	 * BOM 건수 조회
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public int selectBomCount(HashMap<String, String> paramMap)
	  throws Exception{
		return (Integer)selectByPk("RarpSySystemDAO.selectBomCount", paramMap);		
	}

	/**
	 * BOM 정보 조회
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, Object>> selectBomList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpSySystemDAO.selectBomList", paramMap);		
	}

	/**
	 * BOM 정보 조회
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectBomPathList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpSySystemDAO.selectBomPathList", paramMap);
	}

	/**
	 * bom 개별 정보
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public HashMap<String, String> selectBomInfo(HashMap<String, String> paramMap)
	  throws Exception{
		return (HashMap<String, String>)selectByPk("RarpSySystemDAO.selectBomInfo", paramMap);	
	}

	/**
	 * 부품 정보 상세
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public  HashMap<String, String> selectPartDtlInfo(HashMap<String, String> paramMap)
	  throws Exception{
		return (HashMap<String, String>)selectByPk("RarpSySystemDAO.selectPartDtlList", paramMap);
	}
	
	/**
	 * 부품 정보
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectPartDtlList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpSySystemDAO.selectPartDtlList", paramMap);
	}
	/**
	 * 부품 정보 상세
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectPartDetailList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpSySystemDAO.selectPartDetailList", paramMap);
	}

	/**
	 * 전체 BOM 정보
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public List<HashMap<String, String>> selectBomAllList(HashMap<String, String> paramMap)
	  throws Exception{
		return list("RarpSySystemDAO.selectBomAllList", paramMap);
	}
	
	/**
	 * BOM PATH 정보 
	 * 
	 * @param paramMap
	 * @exception Exception Exception
	 */
	public HashMap<String, String> selectBomPathInfo(HashMap<String, String> paramMap)
	  throws Exception{
		return (HashMap<String, String>)selectByPk("RarpSySystemDAO.selectBomPathInfo", paramMap);	
	}
	
}//end RarpMaMainDAO