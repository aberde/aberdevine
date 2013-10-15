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
	public MessageCountBean.Result getMessageCount(String luid, HttpServletResponse response) {
		System.out.println("Creating Message Count");

		MessageCountBean bean = new MessageCountBean();
		try {
			bean = service.getMessageCount(luid);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(MessageCountBean.class);
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
