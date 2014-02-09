package com.aps.rarp.co.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


/**  
 * @Class Name  : DspCoXssFilter.java
 * @Description : XSS 방지를 위한 Filter
 * @Modification Information  
 * @
 * @ 수정일      수정자      수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2012. 9. 4.  김정수     
 * 
 * @author 김정수
 * @since 2012. 9. 4.
 * @version 1.0
 * 
 *  Copyright (C) 2012 by SAMSUNG SDS co.,Ltd. All right reserved.
 */
public class RarpCoXssFilter implements Filter {

    protected static final Logger LOGGER = Logger.getLogger(RarpCoXssFilter.class);
    
    public void destroy() {
        LOGGER.debug("destroy");
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        LOGGER.debug("=======================================================");
        LOGGER.debug( "EfrosXssFilter ");
        LOGGER.debug("=======================================================");
        request.setCharacterEncoding("utf-8");
        chain.doFilter(new RarpCoXssRequest(request), response);
    }
        
    public void init(FilterConfig arg0) throws ServletException {
        LOGGER.debug("init");
    }

}