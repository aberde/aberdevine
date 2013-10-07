package com.hanjin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanjin.bean.EmailMailCountBean;
import com.hanjin.service.EmailMailCountService;

@Controller
@RequestMapping("/emailMailCount")
public class EmailMailCountController {

	@Autowired
	private EmailMailCountService service;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public EmailMailCountBean.Result getEmailMailCount(String dirkey, String JSESSIONID) {
		System.out.println("Creating Email MailCount");

		EmailMailCountBean bean = new EmailMailCountBean();
		try {
			bean = service.getEmailMailCount(dirkey, JSESSIONID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean.getResult();
	}
}
