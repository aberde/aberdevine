/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.grgrowth.main.service.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.grgrowth.main.service.MainService;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 메인
 */
@Controller
public class MainController {

	@Resource(name = "mainService")
	private MainService mainService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** 
	 * 메인화면 호출.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/")
	public String home(ModelMap model) throws Exception {
		return "main/home";
	}
}
