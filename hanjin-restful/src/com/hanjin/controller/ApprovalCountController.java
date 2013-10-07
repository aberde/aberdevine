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

import com.hanjin.bean.ApprovalCountBean;
import com.hanjin.service.ApprovalCountService;

@Controller
@RequestMapping("/approvalCount")
public class ApprovalCountController {

	@Autowired
	private ApprovalCountService service;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public ApprovalCountBean.ROOT getApprovalCount(String pUserID, HttpServletResponse response) {
		System.out.println("Creating Approval Count");

		ApprovalCountBean bean = new ApprovalCountBean();
		try {
			bean = service.getApprovalCount(pUserID);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(ApprovalCountBean.class);
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
