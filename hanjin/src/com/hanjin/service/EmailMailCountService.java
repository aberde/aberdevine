package com.hanjin.service;

import com.hanjin.bean.EmailMailCountBean;

public interface EmailMailCountService {

	/**
	 * Email Mail ���� ���� ���ϼ�
	 * @param dirkey Inbox_����ھ��̵�
	 * @param JSESSIONID
	 * @return ���� ���� ���ϼ�
	 * @throws Exception
	 */
	public EmailMailCountBean getEmailMailCount(String dirkey, String JSESSIONID) throws Exception;
}
