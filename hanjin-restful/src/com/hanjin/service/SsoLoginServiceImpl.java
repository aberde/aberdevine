package com.hanjin.service;

import org.springframework.stereotype.Service;

import com.hanjin.bean.SsoLoginBean;
import com.hanjin.dao.SsoLoginDAO;

@Service
public class SsoLoginServiceImpl implements SsoLoginService {

	/**
	 * SSO �α���
	 * @param user ����ھ��̵�
	 * @param password ��й�ȣ
	 * @return xml������ ����� ����
	 * @throws Exception
	 */
	public SsoLoginBean getSsoLogin(String user, String password) throws Exception {
		
		SsoLoginDAO dao = new SsoLoginDAO();
		SsoLoginBean bean = dao.getSsoLogin(user, password);
		
		return bean;
	}
}
