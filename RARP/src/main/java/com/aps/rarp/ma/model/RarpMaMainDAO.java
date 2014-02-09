package com.aps.rarp.ma.model;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

/**
 * @author Administrator
 * @version 1.0
 * @created 21-11-2013 3:42:21
 */
@Repository("rarpMaMainDAO")
public class RarpMaMainDAO extends EgovAbstractDAO{

	/**
	 * 사용자 정보
	 * 
	 * @param paramMap
	 * @exception Exception
	 */
	public HashMap<String, String> selectUserInfo(HashMap<String, String> paramMap) throws Exception{
		return (HashMap<String, String>)selectByPk("RarpMaMainDAO.selectUserInfo", paramMap);		
	}

	
}//end RarpMaMainDAO