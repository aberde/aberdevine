package com.hanjin.service;

import com.hanjin.bean.GwMailLoginBean;

public interface GwMailLoginService {

	/**
	 * GW Mail �α���
	 * @param MailId ����ھ��̵�
	 * @param MailPassword ����ں�й�ȣ
	 * @return �α��� ��������
	 * @throws Exception
	 */
	public GwMailLoginBean getGwMailLogin(String MailId, String MailPassword) throws Exception;
}
