package com.hanjin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanjin.bean.SsoLoginBean;
import com.hanjin.service.SsoLoginService;

@Controller
@RequestMapping("/ssoLogin")
public class SsoLoginController {

	@Autowired
	private SsoLoginService service;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public SsoLoginBean getSsoLogin(String user, String password) {
		System.out.println("Creating SSO Login");

		SsoLoginBean bean = new SsoLoginBean();
		try {
			bean = service.getSsoLogin(user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bean;
	}
}
