package com.hanjin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanjin.bean.GwMailMailCountBean;
import com.hanjin.service.GwMailMailCountService;

@Controller
@RequestMapping("/gwMailMailCount")
public class GwMailMailCountController {

	@Autowired
	private GwMailMailCountService service;
	
	/**
	 * GW Mail 읽지 않은 메일수
	 * @param MailId 사용자아이디
	 * @param MailPassword 사용자비밀번호
	 * @return 읽지 않은 메일수
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public GwMailMailCountBean.Result getGwMailMailCount(String MailId, String MailPassword) {
		System.out.println("Creating GW Mail MailCount");

		GwMailMailCountBean bean = new GwMailMailCountBean();
		try {
			bean = service.getGwMailMailCount(MailId, MailPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean.getResult();
	}
}
