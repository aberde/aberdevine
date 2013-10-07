package com.hanjin.service;

import com.hanjin.bean.SsoLoginBean;

public interface SsoLoginService {

	/**
	 * SSO 로그인
	 * @param user 사용자아이디
	 * @param password 비밀번호
	 * @return xml형식의 사용자 정보
	 * @throws Exception
	 */
	public SsoLoginBean getSsoLogin(String user, String password) throws Exception;
}
