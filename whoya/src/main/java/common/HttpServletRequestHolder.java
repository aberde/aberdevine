package common;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestHolder {
	
	private final static ThreadLocal<HttpServletRequest> holder = new ThreadLocal<HttpServletRequest>();
	
	public static void set(HttpServletRequest request) {
		holder.set(request);
	}
	
	public static HttpServletRequest get() {
		return holder.get();
	}
	
	public static void remove() {
		holder.remove();
	}
	
	public static String getRealPath(String uri) {
		return holder.get().getSession().getServletContext().getRealPath(uri);
	}
	
	public static String getRequestURI() {
		return holder.get().getRequestURI();
	}
}
