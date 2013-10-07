package com.hanjin.service;

import org.springframework.stereotype.Service;

import com.hanjin.bean.EmailMailCountBean;
import com.hanjin.dao.EmailMailCountDAO;

@Service
public class EmailMailCountServiceImpl implements EmailMailCountService {

	/**
	 * Email Mail ���� ���� ���ϼ�
	 * @param dirkey Inbox_����ھ��̵�
	 * @param JSESSIONID
	 * @return ���� ���� ���ϼ�
	 * @throws Exception
	 */
	public EmailMailCountBean getEmailMailCount(String dirkey, String JSESSIONID) throws Exception {
		EmailMailCountDAO dao = new EmailMailCountDAO();
		EmailMailCountBean bean = dao.getEmailMailCount(dirkey, JSESSIONID);
		
		return bean;
	}
}
