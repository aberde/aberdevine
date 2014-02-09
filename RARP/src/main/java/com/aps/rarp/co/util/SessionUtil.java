package com.aps.rarp.co.util;

import java.util.Locale;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @Class Name : CmsCoSessionUtil.java
 * @Description : Service Layer 단에서 Session 정보에 접근하고 Spring에서 제공하는
 *              RequestContextHolder 를 이용하여 request 객체를 service까지 전달하지 않고 사용할 수
 *              있게 해줌
 * @
 * @  수정일      수정자       수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2012.05.31   
 * 
 * @author 
 * @since 2012. 05.31
 * @version 1.0
 * 
 *          Copyright (C) 2013 by APS All right reserved.
 */
public class SessionUtil {
	/**
	 * attribute 값을 가져 오기 위한 method
	 * @param String attribute key name
	 * @return Object attribute obj
	 */
	public static Object getAttribute(String name) throws Exception {
		return (Object) RequestContextHolder.getRequestAttributes()
				.getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
	
	/**
	 * session 에 들어있는 로케일 정보 가져오기
	 * @param String attribute key name
	 * @return Object attribute obj
	 */
	public static Locale getLocaleAttribute() throws Exception {
		return (Locale) RequestContextHolder.getRequestAttributes()
				.getAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE", RequestAttributes.SCOPE_SESSION);
	}

	/**
	 * attribute 설정 method
	 * @param String attribute key name
	 * @param Object attribute obj
	 * @return void
	 */
	public static void setAttribute(String name, Object object)
			throws Exception {
		RequestContextHolder.getRequestAttributes().setAttribute(name, object,
				RequestAttributes.SCOPE_SESSION);
	}

	/**
	 * 설정한 attribute 삭제
	 * @param String attribute key name
	 * @return void
	 */
	public static void removeAttribute(String name) throws Exception {
		RequestContextHolder.getRequestAttributes().removeAttribute(name,
				RequestAttributes.SCOPE_SESSION);
	}

	/**
	 * session id
	 * @param void
	 * @return String SessionId 값
	 */
	public static String getSessionId() throws Exception {
		return RequestContextHolder.getRequestAttributes().getSessionId();
	}

}
