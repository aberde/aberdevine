package com.hanjin.controller;

import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanjin.bean.EmailLoginBean;
import com.hanjin.service.EmailLoginService;

@Controller
@RequestMapping("/emailLogin")
public class EmailLoginController {

	@Autowired
	private EmailLoginService service;
	
	/**
	 * Email 로그인
	 * @param userid 사용자아이디(이메일)
	 * @return EmailLoginBean
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public EmailLoginBean.Result getEmailLogin(String userid, HttpServletResponse response) {
		System.out.println("Creating Email Login");

		EmailLoginBean bean = new EmailLoginBean();
		try {
			bean = service.getEmailLogin(userid);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(EmailLoginBean.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			Writer writer = new StringWriter();
			marshaller.marshal(bean.getResult(), writer);
		
			response.setContentLength(writer.toString().replaceAll("\\n\\s*", "").getBytes().length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bean.getResult();
	}
}
