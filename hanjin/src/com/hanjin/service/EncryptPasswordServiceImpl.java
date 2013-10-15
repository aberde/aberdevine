package com.hanjin.service;

import org.springframework.stereotype.Service;

import com.hanjin.bean.EncryptPasswordBean;
import com.hanjin.dao.EncryptPasswordDAO;

@Service
public class EncryptPasswordServiceImpl implements EncryptPasswordService {

	/**
	 * 비밀번호 암호화
	 * @param pwd 암호화 시킬 비밀번호
	 * @return 암호화된 비밀번호(XML형식)
	 * @throws Exception
	 */
	public EncryptPasswordBean getEncryptPassword(String pwd) throws Exception {
		
		EncryptPasswordDAO dao = new EncryptPasswordDAO();
		EncryptPasswordBean bean = dao.getEncryptPassword(pwd);
		
		return bean;
	}
}
