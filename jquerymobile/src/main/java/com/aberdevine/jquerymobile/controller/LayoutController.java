package com.aberdevine.jquerymobile.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LayoutController {
	
	private static final Logger logger = LoggerFactory.getLogger(LayoutController.class);
	
	@RequestMapping(value = "/layout", method = RequestMethod.GET)
	public String layout(Model model) {
		logger.debug("layout");
		
		return "layout/layout";
	}
	
}
