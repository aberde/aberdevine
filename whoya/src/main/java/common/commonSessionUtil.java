package common;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class commonSessionUtil {

	public static final String SESSION = "SESSION";
	
	/**
	 * MemberSession 객체를 반환한다.
	 * @return
	 */
	public static commonSession getCommonSession() {
		Object obj = RequestContextHolder.getRequestAttributes().getAttribute(SESSION, RequestAttributes.SCOPE_SESSION);
		//return obj != null ? (commonSession)obj : null; 
		return (commonSession)obj; 
	}
	
	public static void setCommonSession(commonSession commonSession) {
		RequestContextHolder.getRequestAttributes().setAttribute(SESSION, commonSession, RequestAttributes.SCOPE_SESSION);
	}
	
	public static void removeCommonSession() {
		RequestContextHolder.getRequestAttributes().removeAttribute(SESSION, RequestAttributes.SCOPE_SESSION);
	}
}
