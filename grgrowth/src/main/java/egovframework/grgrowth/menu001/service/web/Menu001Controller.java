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
package egovframework.grgrowth.menu001.service.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.grgrowth.menu001.service.Menu001Service;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 녹색성장
 */
@Controller
public class Menu001Controller {

	@Resource(name = "menu001Service")
	private Menu001Service menu001Service;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** 
	 * 녹색성장 > 개념 > 개념
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu001/sub001/GRG_001_101.do")
	public String GRG_001_101(ModelMap model) throws Exception {
		return "menu001/sub001/GRG_001_101";
	}

	/** 
	 * 녹색성장 > 개념 > 추진배경
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu001/sub001/GRG_001_102.do")
	public String GRG_001_102(ModelMap model) throws Exception {
	    return "menu001/sub001/GRG_001_102";
	}
	
	/** 
	 * 녹색성장 > 국가전략 > 국가전략
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu001/sub002/GRG_001_201.do")
	public String GRG_001_201(ModelMap model) throws Exception {
	    return "menu001/sub002/GRG_001_201";
	}
	
	/** 
	 * 녹색성장 > 국가전략 > 5개년 계획
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu001/sub002/GRG_001_202.do")
	public String GRG_001_202(ModelMap model) throws Exception {
	    return "menu001/sub002/GRG_001_202";
	}
}
