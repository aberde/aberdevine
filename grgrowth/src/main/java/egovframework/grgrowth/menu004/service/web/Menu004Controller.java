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
package egovframework.grgrowth.menu004.service.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.grgrowth.menu004.service.Menu004Service;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 위원회
 */
@Controller
public class Menu004Controller {

	@Resource(name = "menu004Service")
	private Menu004Service menu004Service;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** 
	 * 위원회 > 인사말
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu004/sub001/GRG_004_101.do")
	public String GRG_004_101(ModelMap model) throws Exception {
		return "menu004/sub001/GRG_004_101";
	}

	/** 
	 * 위원회 > 위원소개 > 당연직위원
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu004/sub002/GRG_004_201.do")
	public String GRG_004_201(ModelMap model) throws Exception {
	    return "menu004/sub002/GRG_004_201";
	}
	
	/** 
	 * 위원회 > 위원소개 > 민간위원
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu004/sub002/GRG_004_202.do")
	public String GRG_004_202(ModelMap model) throws Exception {
	    return "menu004/sub002/GRG_004_202";
	}
	
	/** 
	 * 위원회 > 위원회연혁
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu004/sub003/GRG_004_301.do")
	public String GRG_004_301(ModelMap model) throws Exception {
	    return "menu004/sub003/GRG_004_301";
	}
	
	/** 
	 * 위원회 > 위원회기능 및 구성
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu004/sub004/GRG_004_401.do")
	public String GRG_004_401(ModelMap model) throws Exception {
	    return "menu004/sub004/GRG_004_401";
	}
	
	/** 
	 * 위원회 > 위원회로고
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu004/sub005/GRG_004_501.do")
	public String GRG_004_501(ModelMap model) throws Exception {
	    return "menu004/sub005/GRG_004_501";
	}
}
