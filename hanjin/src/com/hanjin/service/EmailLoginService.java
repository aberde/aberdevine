package com.hanjin.service;

import com.hanjin.bean.EmailLoginBean;

public interface EmailLoginService {

	/**
	 * Email �α���
	 * @param userid ����ھ��̵�(�̸���)
	 * @return JSESSIONID
	 * @throws Exception
	 */
	public EmailLoginBean getEmailLogin(String userid) throws Exception;
}
