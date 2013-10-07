package com.hanjin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanjin.bean.EncryptPasswordBean;
import com.hanjin.service.EncryptPasswordService;

@Controller
@RequestMapping("/encryptPassword")
public class EncryptPasswordController {

	@Autowired
	private EncryptPasswordService service;
	
	/**
	 * ��й�ȣ ��ȣȭ
	 * @param pwd ��ȣȭ ��ų ��й�ȣ
	 * @return ��ȣȭ�� ��й�ȣ(XML����)
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public EncryptPasswordBean.Result getEncryptPassword(String pwd) {
		System.out.println("Creating encryptPassword");

		EncryptPasswordBean bean = new EncryptPasswordBean();
		try {
			bean = service.getEncryptPassword(pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean.getResult();
	}
}
