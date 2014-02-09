package com.aps.rarp.co.web;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.aps.rarp.co.RarpCoLoginSessionManager;
import com.aps.rarp.co.RarpCoMessageSource;

public class RarpCoWebAuthenticInterceptor extends HandlerInterceptorAdapter {
	
    protected static final Logger logger = Logger.getLogger(RarpCoWebAuthenticInterceptor.class);
	
    @Resource(name = "loginSessionManager")
    private RarpCoLoginSessionManager loginSessionManager;
    
    /* cmsMessageSource */
    @Resource(name="rarpMessageSource")
    private RarpCoMessageSource rarpMessageSource;
    
    
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		    logger.info(" ##### RarpCoWebAuthenticInterceptor PreHandle ##### ");
	        String requestURI  = request.getRequestURI();
	        // 로그인 하지 않아도 사용할 수 있는 URL을 로그인 체크 예외
	        if(	  requestURI.contains("/ma/login.do") 
	        	|| requestURI.contains("/ma/logout.do")
	            || requestURI.contains("/ma/loginAction.do") 
	        ){
	        	logger.info(" ##### RarpCoWebAuthenticInterceptor 인증 우회 ##### ");
	        	return true;
	        }else{
	        	 logger.info(" #### RarpCoWebAuthenticInterceptor 인증 ##### ");
	        	 HttpSession session = request.getSession(false);
	        	 HashMap<String, String> userMap = null;
	     	   	if(session == null){
	    	   		 
	    	   		 WebUtils.setSessionAttribute(request ,"ReturnURI" , requestURI);
	    	   		 ModelAndView modelAndView = new ModelAndView("redirect:/ma/login.do");
				     			  modelAndView.addObject("SessionOutMsgCode","1");
				     throw new ModelAndViewDefiningException(modelAndView);
				}
	     	   	
	     	 	loginSessionManager = (RarpCoLoginSessionManager)session.getAttribute("scopedTarget.loginSessionManager");
	    	   	
				if(loginSessionManager == null){
					 
					 ModelAndView modelAndView = new ModelAndView("redirect:/ma/login.do");
				     			  modelAndView.addObject("SessionOutMsgCode","2");
				     throw new ModelAndViewDefiningException(modelAndView);
				}
				
				userMap = loginSessionManager.getLoginMap(request);

				
				if(userMap == null || "".equals(loginSessionManager.getLoginUserID())){

					ModelAndView modelAndView = new ModelAndView("redirect:/ma/login.do");
							     modelAndView.addObject("SessionOutMsgCode","3");
					throw new ModelAndViewDefiningException(modelAndView);
				}
				
				String sRsmo = request.getParameter("RSMO");
		    	if(sRsmo != null)
		    		loginSessionManager.putLoginInfo("RSMO_CD", sRsmo);
		    	
		    	request.setAttribute("origRequestURL", request.getRequestURL());
	    		return true;
	        }
	}
	
	  /**
	   * postHandle
	   * 
	   * @param HttpServletRequest request, HttpServletResponse response, Object handler 
	   * @return 
	   * @throws Exception 
	   */
	  @Override
	  public void postHandle(HttpServletRequest request,
	      HttpServletResponse response, Object handler, ModelAndView modeAndView) throws Exception {
		  logger.info("postHandle");
		 
	  }

}
