package com.hanjin.service;

import com.hanjin.bean.EncryptPasswordBean;

public interface EncryptPasswordService {

	/**
	 * ��й�ȣ ��ȣȭ
	 * @param pwd ��ȣȭ ��ų ��й�ȣ
	 * @return ��ȣȭ�� ��й�ȣ(XML����)
	 * @throws Exception
	 */	
	public EncryptPasswordBean getEncryptPassword(String pwd) throws Exception;
}
