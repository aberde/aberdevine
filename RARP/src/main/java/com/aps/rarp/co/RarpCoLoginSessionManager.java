package com.aps.rarp.co;

import java.io.Serializable;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

/**
 * @author  	
 * @since 2012. 05.29
 * <pre>
 * == 개정이력(Modification Information) ==
 *
 * 수정일           	      수정자 	 	수정내용
 * ------------		---------------		----------------
 *</pre>
 */
@Service(value = "loginSessionManager")
public class RarpCoLoginSessionManager implements Serializable {

	private static final long serialVersionUID = 1L;
	private HashMap<String, String> userMap;
	
	/* 세션 생성해 주기 */
	public void setSession(HttpServletRequest request , HashMap<String, String> userMap) throws Exception {
		WebUtils.setSessionAttribute(request,RarpCoConstants.userSession, userMap);
	}
	
	/* 세션 받아 오기 */
	public HashMap<String, String> getSession(HttpServletRequest request) throws Exception {
		return (HashMap<String, String>)WebUtils.getSessionAttribute(request, RarpCoConstants.userSession);
	}
	
	/* 세션 VO 받아 오기 */
	public HashMap<String, String> getLoginMap(HttpServletRequest request) throws Exception{
		
		userMap = getSession(request);
		
		if(userMap == null){
			userMap = new HashMap<String, String>();
		}
		return userMap;
	}
	
	/* 세션 VO 받아 오기 */
	public String getLoginUserID(HttpServletRequest request) throws Exception{
		
		userMap = getSession(request);
		String UserId = "";
		if(userMap != null){
			UserId = userMap.get("USER_ID");
		}
		return UserId;
	}
	
	/* 세션 ID 받아 오기 */
	public String getLoginUserID() throws Exception{
		
		String UserId = "";
		if(userMap != null){
			UserId = userMap.get("USER_ID");
		}
		return UserId;
	}

	/* 세션 데이터 가져오기  */
	public String getLoginInfo(String key) throws Exception{
		
		String value = "";
		if(userMap != null){
			value = userMap.get(key);
		}
		return value;
	}
	/**
	 * 로그인VO 얻음
	 * 
	 * @param
	 * @return LoginVO
	 * @throws Exception
	 */
	public HashMap<String, String> getLoginMap() {
		if (userMap == null) {
			this.userMap = new HashMap<String, String>();
		}
		return userMap;
	}

	/**
	 * 세션을 생성해준다.
	 * 
	 * @param DspCoLoginVO
	 */
	public void setSession(HashMap<String, String> loginMap) {
		this.userMap = (HashMap<String, String>) loginMap.clone();
	}

	/**
	 * 세션을 삭제한다.
	 * 
	 * @param request
	 */
	public void removeSession(HttpServletRequest req) {
		HttpSession session = req.getSession(false);

		session.invalidate();
	}
	
	public void putLoginInfo(String key, String value)
	{
		this.userMap.put(key, value);
	}
}

