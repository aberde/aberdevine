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
package egovframework.grgrowth.common.service.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.grgrowth.common.service.CommonBoardVO;
import egovframework.grgrowth.common.service.CommonCategoryVO;
import egovframework.grgrowth.common.service.CommonService;
import egovframework.grgrowth.common.service.FileInfoVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 공통
 */
@Controller
public class CommonController {
    
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
	@RequestMapping(value = "/cmmn/commonBoardList.do")
	public String commonBoardList(@ModelAttribute("vo") CommonBoardVO vo, ModelMap model) throws Exception {
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
	    // ## 카테고리 상세정보
	    // ####################################################################
	    CommonCategoryVO categoryVO = new CommonCategoryVO();
	    categoryVO.setCategory_seq(vo.getCategory_seq());
	    CommonCategoryVO categoryView = commonService.categoryView(categoryVO);
	    
	    model.addAttribute("categoryView", categoryView);
	    // ####################################################################
	    
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

	    return "/cmmn/commonBoardList";
	}

	/** 
	 * 게시판 > 상세
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cmmn/commonBoardView.do")
	public String commonBoardView(@ModelAttribute("vo") CommonBoardVO vo, ModelMap model) throws Exception {
	    // ####################################################################
        // ## 게시판 목록
        // ####################################################################
        CommonBoardVO commonBoardVO = commonService.boardView(vo);
        model.addAttribute("commonBoardVO", commonBoardVO);
        // ####################################################################
	    
	    return "/cmmn/commonBoardView";
	}
	
	/**
	 * 파일 다운로드
	 * @param requestedFile
	 * @param response
	 * @throws Exception
	 */
    @RequestMapping(value = "/cmmn/downloadFile.do")
    public void downloadFile(@ModelAttribute("vo") FileInfoVO vo, HttpServletResponse response) throws Exception {
        // ####################################################################
        // ## 첨부파일 상세정보
        // ####################################################################
        FileInfoVO fileInfoVO = commonService.fileInfoView(vo);
        // ####################################################################
        
        
        File uFile = new File(fileInfoVO.getFile_stre_cours() + fileInfoVO.getStre_file_nm() + "." + fileInfoVO.getFile_extsn());
        int fSize = (int) uFile.length();
 
        if ( fSize > 0 ) {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(uFile));
            String mimetype = "text/html";
 
            response.setBufferSize(fSize);
            response.setContentType(mimetype);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileInfoVO.getOrignl_file_nm(), "utf-8") + "\"");
            response.setContentLength(fSize);
 
            FileCopyUtils.copy(in, response.getOutputStream());
            in.close();
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } else {
            //setContentType을 프로젝트 환경에 맞추어 변경
            response.setContentType("application/x-msdownload");
            PrintWriter printwriter = response.getWriter();
            printwriter.println("<html>");
            printwriter.println("<br><br><br><h2>Could not get file name:<br>" + URLEncoder.encode(fileInfoVO.getOrignl_file_nm(), "utf-8") + "</h2>");
            printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
            printwriter.println("<br><br><br>&copy; webAccess");
            printwriter.println("</html>");
            printwriter.flush();
            printwriter.close();
        }
    }
}
