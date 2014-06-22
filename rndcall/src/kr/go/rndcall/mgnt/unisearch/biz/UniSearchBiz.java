/** 
 * {파일, 클래스에 대한 설명을 기술한다.}
 * @author bkhwang
 * @version {Revision}
 * @since 2009. 10. 20
 * Copyright (C) 2007 by SAMSUNG SDS co.,Ltd. All right reserved.
 */
package kr.go.rndcall.mgnt.unisearch.biz;

import java.sql.SQLException;

import javax.naming.NamingException;

import kr.go.rndcall.mgnt.unisearch.dao.UniSearchDAO;
import kr.go.rndcall.mgnt.unisearch.vo.UniSearchResultVO;
import kr.go.rndcall.mgnt.unisearch.vo.UniSearchSearchVO;
import kr.go.rndcall.mgnt.unisearch.vo.UniSearchVO;
import kr.go.rndcall.mgnt.common.DAOBaseException;

public class UniSearchBiz {
	
	public UniSearchResultVO uniSearch(UniSearchSearchVO searchVO) throws Exception {
		UniSearchDAO dao = new UniSearchDAO();
		
		return dao.uniSearch(searchVO);
	}
	
}
