package com.aps.rarp.co.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aps.rarp.co.RarpCoMessageSource;

/**  
 * @Class Name  : DspCoCommonController.java
 * @Description : 공통 처리를 위한 Controller
 * @Modification Information  
 * @
 * @  수정일      수정자       수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2012.07.09   김정수       최초생성
 * @ 2013.04.24	  IITECH 김명진   공통메세지 처리 페이지 추가
 * 
 * @author 김정수
 * @since 2012. 07.09
 * @version 1.0
 * @see
 * 
 *  Copyright (C) 2012 by SAMSUNG SDS co.,Ltd. All right reserved.
 */
@Controller
public class RarpCoWebCommonController extends RarpCoWebAbstarctController {
	
    /* cmsMessageSource */
    @Resource(name="rarpMessageSource")
    RarpCoMessageSource rarpMessageSource;
    
	protected static final Logger LOGGER = Logger.getLogger(RarpCoWebCommonController.class);

    
    
	/**
	 * 공통 에러 페이지
	 * @param model
	 * @return "selectLoginMain.tiles"
	 * @throws Exception
	 */
    @RequestMapping("/co/selectCmsCoErrorPage.do")
    public String selectCmsCoErrorPage(ModelMap model,Map<String,String> commandMap,HttpServletRequest request) throws Exception {
    	return "co/CmsCoDefaultErrorView";
    }
    
	/**
	 * SSO 에러 페이지
	 * @param model
	 * @return "selectLoginMain.tiles"
	 * @throws Exception
	 */
    @RequestMapping("/co/selectCmsCoSSoErrorPage.do")
    public String selectCmsCoSSoErrorPage(ModelMap model,Map<String,String> commandMap,HttpServletRequest request) throws Exception {
    	
    	String outMsgCode    = request.getParameter("SessionOutMsgCode");
    	String failMessage 	 = "";
    	
    	//로그인 실패 처리 코드 정리
    	if(outMsgCode != null && (
    				   outMsgCode.equals("1")
    				|| outMsgCode.equals("2")
    				|| outMsgCode.equals("3")
    				)
    	){
    		failMessage = rarpMessageSource.getMessage("cms.ma.lo.session.out1");
    	}else{
    		failMessage = rarpMessageSource.getMessage("cms.ma.lo.fail");
    	}
    	
    	//String errorMsg  = new String(request.getParameter("errorMsg").getBytes("8859_1"), "utf-8");
    	//String errorMsg  = request.getParameter("errorMsg");
    	//String errorCode = request.getParameter("errorCode");
    	//model.addAttribute("error_message" , "Error Code : " + errorCode +" , Error Message : " + errorMsg);
    	model.addAttribute("error_message" , failMessage);
        return "co/CmsCoSsoLoginErrorView";
    }
    
}
