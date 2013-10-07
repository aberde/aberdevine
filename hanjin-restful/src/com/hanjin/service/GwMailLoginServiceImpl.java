package com.hanjin.service;

import org.springframework.stereotype.Service;

import com.hanjin.bean.GwMailLoginBean;
import com.hanjin.dao.GwMailLoginDAO;

@Service
public class GwMailLoginServiceImpl implements GwMailLoginService {

	/**
	 * GW Mail �α���
	 * @param MailId ����ھ��̵�
	 * @param MailPassword ����ں�й�ȣ
	 * @return �α��� ��������
	 * @throws Exception
	 */
	public GwMailLoginBean getGwMailLogin(String MailId, String MailPassword) throws Exception {
		GwMailLoginDAO dao = new GwMailLoginDAO();
		GwMailLoginBean bean = dao.getGwMailLogin(MailId, MailPassword);
		
		return bean;
	}
}
