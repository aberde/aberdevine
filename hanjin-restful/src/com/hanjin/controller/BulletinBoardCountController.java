package com.hanjin.controller;

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
	public BulletinBoardCountBean.ROOT getBulletinBoardCount(String pUserID) {
		System.out.println("Creating BulletinBoard Count");

		BulletinBoardCountBean bean = new BulletinBoardCountBean();
		try {
			bean = service.getBulletinBoardCount(pUserID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean.getRoot();
	}
}
