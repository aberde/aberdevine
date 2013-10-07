package com.hanjin.service;

import org.springframework.stereotype.Service;

import com.hanjin.bean.EmailLoginBean;
import com.hanjin.dao.EmailLoginDAO;

@Service
public class EmailLoginServiceImpl implements EmailLoginService {

	/**
	 * Email �α���
	 * @param userid ����ھ��̵�(�̸���)
	 * @return JSESSIONID
	 * @throws Exception
	 */
	public EmailLoginBean getEmailLogin(String userid) throws Exception {
		EmailLoginDAO dao = new EmailLoginDAO();
		EmailLoginBean bean = dao.getEmailLogin(userid);
		
		return bean;
	}
}
