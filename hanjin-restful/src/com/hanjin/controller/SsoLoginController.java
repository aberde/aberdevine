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

import com.hanjin.bean.SsoLoginBean;
import com.hanjin.service.SsoLoginService;

@Controller
@RequestMapping("/ssoLogin")
public class SsoLoginController {

	@Autowired
	private SsoLoginService service;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public SsoLoginBean getSsoLogin(String user, String password, HttpServletResponse response) {
		System.out.println("Creating SSO Login");

		SsoLoginBean bean = new SsoLoginBean();
		try {
			bean = service.getSsoLogin(user, password);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(com.hanjin.bean.SsoLoginBean.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			Writer writer = new StringWriter();
			marshaller.marshal(bean, writer);
		
			response.setContentLength(writer.toString().replaceAll("\\n\\s*", "").getBytes().length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bean;
	}
}
