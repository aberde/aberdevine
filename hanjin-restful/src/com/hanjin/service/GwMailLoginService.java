package com.hanjin.service;

import com.hanjin.bean.GwMailLoginBean;

public interface GwMailLoginService {

	/**
	 * GW Mail 로그인
	 * @param MailId 사용자아이디
	 * @param MailPassword 사용자비밀번호
	 * @return 로그인 성공여부
	 * @throws Exception
	 */
	public GwMailLoginBean getGwMailLogin(String MailId, String MailPassword) throws Exception;
}
