package com.hanjin.service;

import com.hanjin.bean.EmailLoginBean;

public interface EmailLoginService {

	/**
	 * Email 로그인
	 * @param userid 사용자아이디(이메일)
	 * @return JSESSIONID
	 * @throws Exception
	 */
	public EmailLoginBean getEmailLogin(String userid) throws Exception;
}
