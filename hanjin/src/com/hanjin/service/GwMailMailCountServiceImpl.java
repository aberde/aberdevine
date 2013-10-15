package com.hanjin.service;

import org.springframework.stereotype.Service;

import com.hanjin.bean.GwMailMailCountBean;
import com.hanjin.dao.GwMailMailCountDAO;

@Service
public class GwMailMailCountServiceImpl implements GwMailMailCountService {

	/**
	 * GW Mail 읽지 않은 메일수
	 * @param MailId 사용자아이디
	 * @param MailPassword 사용자비밀번호
	 * @return 읽지 않은 메일수
	 * @throws Exception
	 */
	public GwMailMailCountBean getGwMailMailCount(String MailId, String MailPassword) throws Exception {
		GwMailMailCountDAO dao = new GwMailMailCountDAO();
		GwMailMailCountBean bean = dao.getGwMailMailCount(MailId, MailPassword);
		
		return bean;
	}
}
