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
	public EncryptPasswordBean.Result getEncryptPassword(String pwd, HttpServletResponse response) {
		System.out.println("Creating encryptPassword");

		EncryptPasswordBean bean = new EncryptPasswordBean();
		try {
			bean = service.getEncryptPassword(pwd);
		
			JAXBContext jaxbContext = JAXBContext.newInstance(EncryptPasswordBean.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			Writer writer = new StringWriter();
			marshaller.marshal(bean.getResult(), writer);
		
			response.setContentLength(writer.toString().replaceAll("\\n\\s*", "").getBytes("UTF-8").length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean.getResult();
	}
}
