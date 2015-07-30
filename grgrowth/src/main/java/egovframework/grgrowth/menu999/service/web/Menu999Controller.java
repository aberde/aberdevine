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
package egovframework.grgrowth.menu999.service.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.grgrowth.menu999.service.Menu999Service;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 이용안내
 */
@Controller
public class Menu999Controller {

	@Resource(name = "menu999Service")
	private Menu999Service menu999Service;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** 
	 * 이용안내 > 홈페이지 이용안내
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu999/sub001/GRG_999_101.do")
	public String GRG_999_101(ModelMap model) throws Exception {
		return "menu999/sub001/GRG_999_101";
	}

	/** 
	 * 이용안내 > 저작권정책
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu999/sub002/GRG_999_201.do")
	public String GRG_999_201(ModelMap model) throws Exception {
	    return "menu999/sub002/GRG_999_201";
	}
	
	/** 
	 * 이용안내 > 사이트 맵
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu999/sub003/GRG_999_301.do")
	public String GRG_999_301(ModelMap model) throws Exception {
	    return "menu999/sub003/GRG_999_301";
	}
	
	/** 
	 * 이용안내 > 찾아오시는 길
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu999/sub004/GRG_999_401.do")
	public String GRG_999_401(ModelMap model) throws Exception {
	    return "menu999/sub004/GRG_999_401";
	}
}
