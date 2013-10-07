package com.hanjin.service;

import org.springframework.stereotype.Service;

import com.hanjin.bean.GwMailLoginBean;
import com.hanjin.dao.GwMailLoginDAO;

@Service
public class GwMailLoginServiceImpl implements GwMailLoginService {

	/**
	 * GW Mail 로그인
	 * @param MailId 사용자아이디
	 * @param MailPassword 사용자비밀번호
	 * @return 로그인 성공여부
	 * @throws Exception
	 */
	public GwMailLoginBean getGwMailLogin(String MailId, String MailPassword) throws Exception {
		GwMailLoginDAO dao = new GwMailLoginDAO();
		GwMailLoginBean bean = dao.getGwMailLogin(MailId, MailPassword);
		
		return bean;
	}
}
