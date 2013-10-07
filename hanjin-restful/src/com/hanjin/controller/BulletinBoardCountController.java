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

import com.hanjin.bean.BulletinBoardCountBean;
import com.hanjin.service.BulletinBoardCountService;

@Controller
@RequestMapping("/bulletinBoardCount")
public class BulletinBoardCountController {

	@Autowired
	private BulletinBoardCountService service;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public BulletinBoardCountBean.ROOT getBulletinBoardCount(String pUserID, HttpServletResponse response) {
		System.out.println("Creating BulletinBoard Count");

		BulletinBoardCountBean bean = new BulletinBoardCountBean();
		try {
			bean = service.getBulletinBoardCount(pUserID);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(BulletinBoardCountBean.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			Writer writer = new StringWriter();
			marshaller.marshal(bean.getRoot(), writer);
		
			response.setContentLength(writer.toString().replaceAll("\\n\\s*", "").getBytes().length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean.getRoot();
	}
}
