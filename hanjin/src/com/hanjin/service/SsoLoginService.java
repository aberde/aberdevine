package com.hanjin.service;

import com.hanjin.bean.SsoLoginBean;

public interface SsoLoginService {

	/**
	 * SSO �α���
	 * @param user ����ھ��̵�
	 * @param password ��й�ȣ
	 * @return xml������ ����� ����
	 * @throws Exception
	 */
	public SsoLoginBean getSsoLogin(String user, String password) throws Exception;
}
