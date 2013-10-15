package com.hanjin.service;

import org.springframework.stereotype.Service;

import com.hanjin.bean.EmailMailCountBean;
import com.hanjin.dao.EmailMailCountDAO;

@Service
public class EmailMailCountServiceImpl implements EmailMailCountService {

	/**
	 * Email Mail 읽지 않은 메일수
	 * @param dirkey Inbox_사용자아이디
	 * @param JSESSIONID
	 * @return 읽지 않은 메일수
	 * @throws Exception
	 */
	public EmailMailCountBean getEmailMailCount(String dirkey, String JSESSIONID) throws Exception {
		EmailMailCountDAO dao = new EmailMailCountDAO();
		EmailMailCountBean bean = dao.getEmailMailCount(dirkey, JSESSIONID);
		
		return bean;
	}
}
