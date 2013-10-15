package com.hanjin.service;

import org.springframework.stereotype.Service;

import com.hanjin.bean.SsoLoginBean;
import com.hanjin.dao.SsoLoginDAO;

@Service
public class SsoLoginServiceImpl implements SsoLoginService {

	/**
	 * SSO 로그인
	 * @param user 사용자아이디
	 * @param password 비밀번호
	 * @return xml형식의 사용자 정보
	 * @throws Exception
	 */
	public SsoLoginBean getSsoLogin(String user, String password) throws Exception {
		
		SsoLoginDAO dao = new SsoLoginDAO();
		SsoLoginBean bean = dao.getSsoLogin(user, password);
		
		return bean;
	}
}
