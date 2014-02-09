package com.aps.rarp.ma.web;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.aps.rarp.co.RarpCoLoginSessionManager;
import com.aps.rarp.co.web.RarpCoWebAbstarctController;
import com.aps.rarp.ma.model.RarpMaMainDAO;


/**
* 메인페이지 처리를 위한  Controller 구현 클래스
* <p><b>NOTE:</b>
* @author 
* @since 2012.02.26
* @see
*
* <pre>
* == 개정이력(Modification Information) ==
*
* 수정일       수정자    수정내용
* ---------- ------- ---------------------------
* 2012.02.26     
*
* Copyright (C) 2013 by APS. All right reserved.
* </pre>
*/
@Controller
public class RarpMaMainController extends RarpCoWebAbstarctController{

	protected static final Logger logger = Logger.getLogger(RarpMaMainController.class);
	
	@Resource(name="rarpMaMainDAO")
	private RarpMaMainDAO rarpMaMainDAO;
	
	@Resource(name="loginSessionManager")
	private RarpCoLoginSessionManager loginSessionManager;
	
	/**
	 * 로그인 페이지
	 * @param model
	 * @param session
	 * @return "selectMain.tiles"
	 * @throws Exception
	 */
    @RequestMapping(value = "/ma/login.do")
    public String initLogin(ModelMap model,@RequestParam HashMap<String, String> paramMap) throws Exception {
        return "ma/login";
    }
    
	/**
	 * 로그인 수행
	 * @param model
	 * @param session
	 * @return "selectMain.tiles"
	 * @throws Exception
	 */
    @RequestMapping(value = "/ma/loginAction.do")
    public String actionLogin(HttpServletRequest request,ModelMap model,@RequestParam HashMap<String, String> paramMap ) throws Exception {
		
    	try
    	{
	    	HashMap<String, String> userMap = rarpMaMainDAO.selectUserInfo(paramMap);
	    	
	    	if(paramMap != null)
	    	{
	        	// 세션 생성해 주기
	    		WebUtils.setSessionAttribute(request, "USER_INFO", userMap);
	        	
	    		// 현재 로그인 정보를 세션에 저장
	    		loginSessionManager.setSession(userMap); 
	    		model.put("ResultCode", "200");
	    	}
	    	else
	    	{
	    		model.put("ResultCode", "400");
	    	}
    	}
    	catch(Exception ex)
    	{
    		model.put("ResultCode", "400");
    	}
		
        return "jsonView";
    }
	/**
	 * 메인 페이지
	 * @param model
	 * @param session
	 * @return "selectMain.tiles"
	 * @throws Exception
	 */
    @RequestMapping(value = "/ma/main.do")
    public String initMain(ModelMap model,@RequestParam HashMap<String, String> paramMap) throws Exception {
    	
    	if(loginSessionManager.getLoginInfo("USER_TYPE").equals("A"))
    	{
    		model.put("MENU", "MAIN");
    		return "ma/main";
    	}
    	else
    		return "forward:/hs/ispt.do";
    }
	/**
	 * 로그아웃 수행
	 * @param session
	 * @return "selectMain.tiles"
	 * 
	 * @param request
	 * @param model
	 * @param paramMap
	 * @exception Exception Exception
	 */
	@RequestMapping(value = "/ma/logoutAction.do")
	public String actionLogout(HttpServletRequest request, ModelMap model, @RequestParam HashMap<String, String> paramMap)
	  throws Exception{
		loginSessionManager.removeSession(request);
		return "ma/login";
	}
	
	
}//end RarpMaMainController