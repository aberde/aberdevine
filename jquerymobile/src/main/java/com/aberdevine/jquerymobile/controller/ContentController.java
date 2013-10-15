package com.aberdevine.jquerymobile.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ContentController {
	
	private static final Logger logger = LoggerFactory.getLogger(ContentController.class);
	
	@RequestMapping(value = "/content", method = RequestMethod.GET)
	public String layout(Model model) {
		logger.debug("content");
		
		return "content/content";
	}
	
}
