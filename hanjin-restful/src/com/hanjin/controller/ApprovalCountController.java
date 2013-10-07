package com.hanjin.controller;

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
	public ApprovalCountBean.ROOT getApprovalCount(String pUserID) {
		System.out.println("Creating Approval Count");

		ApprovalCountBean bean = new ApprovalCountBean();
		try {
			bean = service.getApprovalCount(pUserID);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bean.getRoot();
	}
}
