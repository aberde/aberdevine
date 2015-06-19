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
package egovframework.grgrowth.admin.service.web;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.grgrowth.common.service.CommonBoardVO;
import egovframework.grgrowth.common.service.CommonCategoryVO;
import egovframework.grgrowth.common.service.CommonService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 관리자
 */
@Controller
public class AdminController {
    
    @Resource(name = "fileUploadProperties")
    Properties fileUploadProperties;

	@Resource(name = "commonService")
	private CommonService commonService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** 
	 * 게시판 > 목록
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/boardList.do")
	public String boardList(@ModelAttribute("vo") CommonBoardVO vo, ModelMap model) throws Exception {
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

	    // 모든 카테고리에서 검색
	    vo.setCategory_seq(0);
	    
	    // ####################################################################
	    // ## 게시판 페이징
	    // ####################################################################
	    int totCnt = commonService.boardListTotCnt(vo);
	    paginationInfo.setTotalRecordCount(totCnt);

	    model.addAttribute("paginationInfo", paginationInfo);
	    // ####################################################################

	    // ####################################################################
	    // ## 게시판 목록
	    // ####################################################################
	    List<CommonBoardVO> boardList = commonService.boardList(vo);
	    model.addAttribute("boardList", boardList);
	    // ####################################################################

	    return "/admin/boardList";
	}

	/** 
	 * 게시판 > 상세
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/boardView.do")
	public String boardView(@ModelAttribute("vo") CommonBoardVO vo, ModelMap model) throws Exception {
	    // ####################################################################
	    // ## 게시판 목록
	    // ####################################################################
	    CommonBoardVO commonBoardVO = commonService.boardView(vo);
	    model.addAttribute("commonBoardVO", commonBoardVO);
	    // ####################################################################
	    
	    return "/admin/boardView";
	}
	
	/** 
	 * 게시판 > 등록/수정 화면
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/boardForm.do")
	public String boardForm(@ModelAttribute("vo") CommonBoardVO vo, ModelMap model) throws Exception {
	    // ####################################################################
        // ## 카테고리 목록
        // ####################################################################
        List<CommonCategoryVO> categoryList = commonService.categoryList();
        
        model.addAttribute("categoryList", categoryList);
        // ####################################################################
	    
	    if ( vo.getBoard_seq() > 0 ) {
	        // ####################################################################
	        // ## 게시판 목록
	        // ####################################################################
	        CommonBoardVO commonBoardVO = commonService.boardView(vo);
	        model.addAttribute("vo", commonBoardVO);
	        // ####################################################################
	    }
	    
	    return "/admin/boardForm";
	}

	/** 
	 * 게시판 > 등록/수정 처리
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/boardSaveProc.do")
	public String boardSaveProc(@ModelAttribute("vo") CommonBoardVO vo, ModelMap model) throws Exception {
	    if ( vo.getBoard_seq() > 0 ) {
	        // ####################################################################
	        // ## 게시판 수정
	        // ####################################################################
	        commonService.boardUpdate(vo);
	        // ####################################################################
	    } else {
	        // ####################################################################
	        // ## 게시판 등록
	        // ####################################################################
	        commonService.boardInsert(vo);
	        // ####################################################################
	    }
	    
	    return "forward:/admin/boardList.do";
	}
	
	/** 
     * 게시판 > 삭제 처리
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/admin/boardDeleteProc.do")
    public String boardDeleteProc(@ModelAttribute("vo") CommonBoardVO vo, ModelMap model) throws Exception {
        commonService.boardDelete(vo);
        
        return "forward:/admin/boardList.do";
    }
}
