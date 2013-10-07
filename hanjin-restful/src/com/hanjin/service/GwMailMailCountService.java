package com.hanjin.service;

import com.hanjin.bean.GwMailMailCountBean;

public interface GwMailMailCountService {

	/**
	 * GW Mail 읽지 않은 메일수
	 * @param MailId 사용자아이디
	 * @param MailPassword 사용자비밀번호
	 * @return 읽지 않은 메일수
	 * @throws Exception
	 */
	public GwMailMailCountBean getGwMailMailCount(String MailId, String MailPassword) throws Exception;
}
