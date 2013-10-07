package com.hanjin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanjin.bean.GwMailLoginBean;
import com.hanjin.service.GwMailLoginService;

@Controller
@RequestMapping("/gwMailLogin")
public class GwMailLoginController {

	@Autowired
	private GwMailLoginService service;
	
	/**
	 * GW Mail 로그인
	 * @param MailId 사용자아이디
	 * @param MailPassword 사용자비밀번호
	 * @return 로그인 성공여부
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public GwMailLoginBean.Result getGwMailLogin(String MailId, String MailPassword) {
		System.out.println("Creating GW Mail Login");

		GwMailLoginBean bean = new GwMailLoginBean();
		try {
			bean = service.getGwMailLogin(MailId, MailPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean.getResult();
	}
}
