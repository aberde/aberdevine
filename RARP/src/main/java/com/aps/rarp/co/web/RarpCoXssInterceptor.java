package com.aps.rarp.co.web;

//import java.util.Enumeration;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**  
 * @Class Name  : DspCoXssInterceptor.java
 * @Description : XSS 방지를 위해 <> & 값등이 들어왔을때 error 페이지로 리턴처리를 해주는 인터셉터
 * @Modification Information  
 * @
 * @ 수정일      수정자      수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2012. 8. 17.  김정수     
 * 
 * @author 김정수
 * @since 2012. 8. 17.
 * @version 1.0
 * 
 *  Copyright (C) 2012 by SAMSUNG SDS co.,Ltd. All right reserved.
 */

public class RarpCoXssInterceptor extends HandlerInterceptorAdapter{
    protected static final Logger LOGGER = Logger.getLogger(RarpCoXssInterceptor.class);
    
	/**
	 * request 후 request에 해당하는 액션 전에 할 행동
	 * @param HttpServletResponse response
	 * @param HttpServletRequest request
	 * @param Object handler
	 * @return boolean
	 * @throws Exception 
	 */
    
    /*
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	    boolean rtrVal = true;
	    LOGGER.debug(request.getRequestURI());
	    LOGGER.debug("[test]=======================================================");

		String[] filterStr = null;
        
        //filterStr = new String[] {"&", "--", "'", "\"", "/", "@", "<", ">", "\\", "$", "%", ";", "|", "+", "..", "SCRIPT", "<IFRAME", "<OBJECT", "<EMBEDED", "<FORM", "JAVASCRIPT"};
		//20121109 개발팀 요청으로 변경  ";", "\"", "\\"  
		filterStr = new String[] {"--", "'",  "$", "%", "|", "+", "..",  "SCRIPT", "<IFRAME", "<OBJECT", "<EMBEDED", "<FORM", "JAVASCRIPT"};

        Enumeration keys =  request.getParameterNames();
        while(keys.hasMoreElements())
        {
            String key = (String)keys.nextElement();
            String chkPw = ""; 
                    
            if(key.length()>8){
                chkPw = key.substring(0, 8); 
            }
            
            String[] value = request.getParameterValues(key);
            if(!key.equals("INIpluginData")&&
               !key.equals("_ETEExt_SEED_")&&
               !key.equals("ozrPath")&&
               !chkPw.equals("_E2E123_")&&
               !key.equals("SAMLResponse")&&
               !key.equals("duplJoinChkInfo")&&
               //!key.equals("forward")&&
                value!=null &&
               !value.equals("")
               )
            {
                
                for(int i=0; i<filterStr.length; i++)
                {
                    if(value[0].toUpperCase().indexOf(filterStr[i])!=-1)
                    {
                        LOGGER.debug(" key = [" + key + "]  value = ["+ value[0] +"]");
                        response.sendRedirect("/common/returnXssAlert.do");
                        rtrVal = false;
                    }
                }
            }
	    }
		return rtrVal;
	}
	*/
}
