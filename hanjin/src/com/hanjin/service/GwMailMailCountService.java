package com.hanjin.service;

import com.hanjin.bean.GwMailMailCountBean;

public interface GwMailMailCountService {

	/**
	 * GW Mail ���� ���� ���ϼ�
	 * @param MailId ����ھ��̵�
	 * @param MailPassword ����ں�й�ȣ
	 * @return ���� ���� ���ϼ�
	 * @throws Exception
	 */
	public GwMailMailCountBean getGwMailMailCount(String MailId, String MailPassword) throws Exception;
}
