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
	 * 비밀번호 암호화
	 * @param pwd 암호화 시킬 비밀번호
	 * @return 암호화된 비밀번호(XML형식)
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
