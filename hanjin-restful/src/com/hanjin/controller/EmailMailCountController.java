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

import com.hanjin.bean.EmailMailCountBean;
import com.hanjin.service.EmailMailCountService;

@Controller
@RequestMapping("/emailMailCount")
public class EmailMailCountController {

	@Autowired
	private EmailMailCountService service;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public EmailMailCountBean.Result getEmailMailCount(String dirkey, String JSESSIONID, HttpServletResponse response) {
		System.out.println("Creating Email MailCount");

		EmailMailCountBean bean = new EmailMailCountBean();
		try {
			bean = service.getEmailMailCount(dirkey, JSESSIONID);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(EmailMailCountBean.class);
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
