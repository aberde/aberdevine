package com.hanjin.service;

import org.springframework.stereotype.Service;

import com.hanjin.bean.EncryptPasswordBean;
import com.hanjin.dao.EncryptPasswordDAO;

@Service
public class EncryptPasswordServiceImpl implements EncryptPasswordService {

	/**
	 * ��й�ȣ ��ȣȭ
	 * @param pwd ��ȣȭ ��ų ��й�ȣ
	 * @return ��ȣȭ�� ��й�ȣ(XML����)
	 * @throws Exception
	 */
	public EncryptPasswordBean getEncryptPassword(String pwd) throws Exception {
		
		EncryptPasswordDAO dao = new EncryptPasswordDAO();
		EncryptPasswordBean bean = dao.getEncryptPassword(pwd);
		
		return bean;
	}
}
