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
package egovframework.grgrowth.menu003.service.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.grgrowth.common.service.CommonBoardVO;
import egovframework.grgrowth.menu003.service.Menu003Service;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 정보
 */
@Controller
public class Menu003Controller {

	@Resource(name = "menu003Service")
	private Menu003Service menu003Service;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** 
	 * 정보 > 녹색법령
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu003/sub002/GRG_003_201.do")
	public String GRG_003_201(ModelMap model) throws Exception {
	    return "menu003/sub002/GRG_003_201";
	}
	
	/** 
	 * 정보 > 용어사전
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu003/sub003/GRG_003_301.do")
	public String GRG_003_301(@ModelAttribute("vo") CommonBoardVO vo, ModelMap model) throws Exception {
	    // ####################################################################
        // ## 페이징 설정
        // ####################################################################
        // currentPageNo : 현재 페이지 번호
        // recordCountPerPage : 한 페이지당 게시되는 게시물 건 수 (=pageUnit)
        // pageSize : 페이지 리스트에 게시되는 페이지 건수
        // totalRecordCount : 전체 게시물 건 수
        // pageUnit과 pageSize는 context-properties.xml에서 설정

        vo.setPageUnit(propertiesService.getInt("pageUnit"));
        vo.setPageSize(propertiesService.getInt("pageSize"));

        PaginationInfo paginationInfo = new PaginationInfo();

        paginationInfo.setCurrentPageNo(vo.getPageIndex());
        paginationInfo.setRecordCountPerPage(vo.getPageUnit());
        paginationInfo.setPageSize(vo.getPageSize());

        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
        vo.setLastIndex(paginationInfo.getLastRecordIndex());
        vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
        // ####################################################################

        // ####################################################################
        // ## 용어사전 페이징
        // ####################################################################
        int totCnt = menu003Service.dicListTotCnt(vo);
        paginationInfo.setTotalRecordCount(totCnt);

        model.addAttribute("paginationInfo", paginationInfo);
        // ####################################################################

        // ####################################################################
        // ## 용어사전 목록
        // ####################################################################
        List<CommonBoardVO> dicList = menu003Service.dicList(vo);
        model.addAttribute("dicList", dicList);
        // ####################################################################
	    
	    return "menu003/sub003/GRG_003_301";
	}
	
	/** 
	 * 정보 > 관련 사이트
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu003/sub004/GRG_003_401.do")
	public String GRG_003_401(ModelMap model) throws Exception {
	    return "menu003/sub004/GRG_003_401";
	}
}
