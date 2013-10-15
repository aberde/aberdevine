package com.hanjin.service;

import com.hanjin.bean.EncryptPasswordBean;

public interface EncryptPasswordService {

	/**
	 * 비밀번호 암호화
	 * @param pwd 암호화 시킬 비밀번호
	 * @return 암호화된 비밀번호(XML형식)
	 * @throws Exception
	 */	
	public EncryptPasswordBean getEncryptPassword(String pwd) throws Exception;
}
