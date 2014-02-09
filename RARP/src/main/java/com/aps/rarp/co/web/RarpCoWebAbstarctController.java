package com.aps.rarp.co.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.util.WebUtils;

import com.aps.rarp.co.RarpCoLoginSessionManager;
import com.aps.rarp.co.RarpCoConstants;
import com.aps.rarp.co.util.StringUtil;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


/**
 *  Abstract Controller
 * @author 
 * @since 2012. 05.29
 * <pre>
 * == 개정이력(Modification Information) ==
 *
 * 수정일                		수정자 	 			수정내용
 * ------------		---------------		---------------------------

 *</pre>
 */
public class RarpCoWebAbstarctController {

    /** EgovPropertyService */
    @Resource(name = "egovPropertiesService")
    private EgovPropertyService propertiesService;
    
	@Resource(name = "loginSessionManager")
	protected RarpCoLoginSessionManager loginSessionManager;
	
	
	protected static final Logger LOGGER = Logger.getLogger(RarpCoWebAbstarctController.class);
	
	/**
	 * 로그인 vo getter 메서드
	 * null일 수도 있다 .
	 * @return 로그인 vo
	 */	
	protected HashMap<String, String> getLoginVO(HttpServletRequest request) throws Exception {
		HashMap<String, String> getLoginVO = null;
	
		if(loginSessionManager.getLoginMap(request) != null){
			getLoginVO = loginSessionManager.getLoginMap(request);
		}else{ 
			getLoginVO = new HashMap<String, String>();
		}
		return getLoginVO;
	}
	
	/**
	 * 로그인 vo getter 메서드
	 * null일 수도 있다 .
	 * @return 로그인 vo
	 */
	protected HashMap<String, String> getLoginVO() {
		HashMap<String, String> getLoginVO = null;
		if(loginSessionManager.getLoginMap() != null){
			getLoginVO = loginSessionManager.getLoginMap();
		}else{
			getLoginVO = new HashMap<String, String>();
		}
		return getLoginVO;
	}
	
	/**
	 * 세션을 생성해준다.
	 * 
	 * @param DspCoLoginVO
	 */
	protected void setSession(HashMap<String, String> loginVO) {
		loginSessionManager.setSession(loginVO);
	}
	
	/**
	 * 세션을 삭제한다.
	 * 
	 * @param request
	 */
	protected void removeSession(HttpServletRequest req) {
		loginSessionManager.removeSession(req);
	}
	
	/**
	 * 프로퍼티 값을 가져온다.
	 * 
	 * @param request
	 */
	protected RarpCoConstants getConstants() {
		return new RarpCoConstants();
	}
	
	/**
	 * 사용자id getter 메서드
	 * 
	 * @return 사용자id
	 * @throws Exception 
	 */
	protected String getLoginUserID() throws Exception {
	    String rtrStr;
		if(loginSessionManager == null || 
		        loginSessionManager.getLoginMap() == null ||  
		        loginSessionManager.getLoginUserID() == null ||  
		        loginSessionManager.getLoginUserID().equals("")){
		    rtrStr = "";		//개발팀 요청으로 임시로 하드코딩
		}else{ 
		    rtrStr = loginSessionManager.getLoginUserID();
		}
		return rtrStr;
	}


	
}
