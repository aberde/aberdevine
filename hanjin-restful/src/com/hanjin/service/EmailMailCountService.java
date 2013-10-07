package com.hanjin.service;

import com.hanjin.bean.EmailMailCountBean;

public interface EmailMailCountService {

	/**
	 * Email Mail 읽지 않은 메일수
	 * @param dirkey Inbox_사용자아이디
	 * @param JSESSIONID
	 * @return 읽지 않은 메일수
	 * @throws Exception
	 */
	public EmailMailCountBean getEmailMailCount(String dirkey, String JSESSIONID) throws Exception;
}
