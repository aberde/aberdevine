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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import egovframework.grgrowth.admin.service.AdminLoginVO;
import egovframework.grgrowth.common.GrgrowthConstants;
import egovframework.grgrowth.common.Util;
import egovframework.grgrowth.common.service.CommonBoardVO;
import egovframework.grgrowth.common.service.CommonService;
import egovframework.grgrowth.common.service.FileInfoVO;
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
	 * 로그인
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/adminLogin.do")
	public String adminLogin(@ModelAttribute("vo") AdminLoginVO vo, ModelMap model) throws Exception {
	    return "/admin/adminLogin";
	}
	
	/** 
	 * 로그인 처리
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/adminLoginProc.do")
	public String adminLoginProc(@ModelAttribute("vo") AdminLoginVO vo, ModelMap model, HttpServletRequest request) throws Exception {
	    if ( GrgrowthConstants.ADMIN_USER_ID.equals(vo.getUser_id())
	            && GrgrowthConstants.ADMIN_USER_PW.equals(vo.getUser_pw()) ) {
	        request.getSession().setAttribute(GrgrowthConstants.SESSION_USER_ID, vo.getUser_id());
	        
	        return "redirect:/admin/boardList.do";
	    } else {
	        vo.setErr_msg(GrgrowthConstants.ERR_LOGIN_FALSE);
	        return "/admin/adminLogin";
	    }
	}
	
	/** 
	 * 로그아웃 처리
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/adminLogoutProc.do")
	public String adminLogoutProc(@ModelAttribute("vo") AdminLoginVO vo, ModelMap model, HttpServletRequest request) throws Exception {
	    request.getSession().invalidate();
	    
	    return "redirect:/admin/adminLogin.do";
	}
	
	/** 
	 * 게시판 > 목록
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/boardList.do")
	public String boardList(@ModelAttribute("vo") CommonBoardVO vo, ModelMap model, HttpServletRequest request) throws Exception {
	    if ( Util.getSessionUser_id(request) == null ) {
	        return "redirect:/admin/adminLogin.do";
	    }
	    
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

	    if ( vo.getCategory_seq() == 0 ) {  // 카테고리 미존재시 위원회활동으로 설정
	        vo.setCategory_seq(1);
	    }
	    
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
	    
	    // 검색구분
        model.addAttribute("search_section", GrgrowthConstants.SEARCH_SECTION);

	    return "/admin/boardList";
	}

	/** 
	 * 게시판 > 상세
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/boardView.do")
	public String boardView(@ModelAttribute("vo") CommonBoardVO vo, ModelMap model, HttpServletRequest request) throws Exception {
	    if ( Util.getSessionUser_id(request) == null ) {
	        return "redirect:/admin/adminLogin.do";
        }
	    
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
	public String boardForm(@ModelAttribute("vo") CommonBoardVO vo, ModelMap model, HttpServletRequest request) throws Exception {
	    if ( Util.getSessionUser_id(request) == null ) {
	        return "redirect:/admin/adminLogin.do";
        }
	    
	    // 카테고리
        model.addAttribute("search_section", GrgrowthConstants.SEARCH_SECTION);
	    
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
	@RequestMapping(value = "/admin/boardSaveProc.do", method = RequestMethod.POST)
	public String boardSaveProc(@ModelAttribute("vo") CommonBoardVO vo, ModelMap model, HttpServletRequest request) throws Exception {
	    if ( Util.getSessionUser_id(request) == null ) {
	        return "redirect:/admin/adminLogin.do";
        }
	    
	    int file_seq = 0;
	    if ( vo.getFile_seq() > 0 ) {  // 수정시
	        file_seq = vo.getFile_seq();
	    } else {  // 등록시
	        Map<String, Object> dataMap = new HashMap<String, Object>();
	        commonService.fileInfoInsert(dataMap);
	        file_seq = Integer.parseInt((String)dataMap.get("file_seq"));
	        vo.setFile_seq(file_seq);
	    }
	    
	    // ####################################################################
        // ## 파일 저장
        // ####################################################################
	    String uploadPath = fileUploadProperties.getProperty("file.upload.path");
	    List<MultipartFile> uploadFiles = vo.getUploadFileInfo();
        List<FileInfoVO> fileInfoList = new ArrayList<FileInfoVO>();
 
        // 저장 경로
        String savePath = uploadPath + Util.getDatePattern("yyyy") + File.separator + Util.getDatePattern("MM") + File.separator;
        
        File savePathFile = new File(savePath);
        if ( !savePathFile.exists() ) {
            savePathFile.mkdirs();
        }
        
        if ( uploadFiles != null && uploadFiles.size() > 0 ) {
            for ( int i = 0; i < uploadFiles.size(); i++ ) {
                MultipartFile multipartFile = uploadFiles.get(i);
                
                String orignl_file_nm = multipartFile.getOriginalFilename();  // 원파일명
                String stre_file_nm = Util.getDatePattern("yyyyMMddHHmmssSSS") + i;  // 실제저장된 파일명
                String file_extsn = orignl_file_nm.substring(orignl_file_nm.lastIndexOf(".") + 1);

                if ( !"".equalsIgnoreCase(orignl_file_nm) ) {
                    multipartFile.transferTo(new File(savePath + stre_file_nm + "." + file_extsn));

                    FileInfoVO fileInfoVO = new FileInfoVO();
                    fileInfoVO.setFile_seq(file_seq);
                    fileInfoVO.setFile_extsn(file_extsn);
                    fileInfoVO.setFile_size(multipartFile.getSize());
                    fileInfoVO.setFile_stre_cours(savePath);
                    fileInfoVO.setOrignl_file_nm(orignl_file_nm);
                    fileInfoVO.setStre_file_nm(stre_file_nm);
                    
                    fileInfoList.add(fileInfoVO);
                }
            }
        }
        
        if ( fileInfoList.size() > 0 ) {
            commonService.fileInsert(fileInfoList);
        }
        // ####################################################################
	    
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
	    
	    return "redirect:/admin/boardList.do?category_seq=" + vo.getCategory_seq();
	}
	
	/** 
     * 게시판 > 삭제 처리
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/admin/boardDeleteProc.do")
    public String boardDeleteProc(@ModelAttribute("vo") CommonBoardVO vo, ModelMap model, HttpServletRequest request) throws Exception {
        if ( Util.getSessionUser_id(request) == null ) {
            return "redirect:/admin/adminLogin.do";
        }
        
        commonService.boardDelete(vo);
        return "redirect:/admin/boardList.do";
    }
}
