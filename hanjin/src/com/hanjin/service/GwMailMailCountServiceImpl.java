package com.hanjin.service;

import org.springframework.stereotype.Service;

import com.hanjin.bean.GwMailMailCountBean;
import com.hanjin.dao.GwMailMailCountDAO;

@Service
public class GwMailMailCountServiceImpl implements GwMailMailCountService {

	/**
	 * GW Mail ���� ���� ���ϼ�
	 * @param MailId ����ھ��̵�
	 * @param MailPassword ����ں�й�ȣ
	 * @return ���� ���� ���ϼ�
	 * @throws Exception
	 */
	public GwMailMailCountBean getGwMailMailCount(String MailId, String MailPassword) throws Exception {
		GwMailMailCountDAO dao = new GwMailMailCountDAO();
		GwMailMailCountBean bean = dao.getGwMailMailCount(MailId, MailPassword);
		
		return bean;
	}
}
