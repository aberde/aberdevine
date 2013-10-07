package com.hanjin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanjin.bean.MessageCountBean;
import com.hanjin.service.MessageCountService;

@Controller
@RequestMapping("/messageCount")
public class MessageCountController {

	@Autowired
	private MessageCountService service;
	
	/**
	 * Message Count
	 * @param luid 사용자아이디
	 * @return MessageCountBean
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public MessageCountBean.Result getMessageCount(String luid) {
		System.out.println("Creating Message Count");

		MessageCountBean bean = new MessageCountBean();
		try {
			bean = service.getMessageCount(luid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bean.getResult();
	}
}
