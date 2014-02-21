package whoya.egovframework.com.cop.bbs.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import whoya.whoyaDataProcess;
import whoya.whoyaLib;
import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.common.Common;
import whoya.common.commonReturn;
import whoya.egovframework.com.cop.bbs.service.WhoyaEgovBBSUseInfoManageService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.bbs.service.BoardUseInf;
import egovframework.com.cop.bbs.service.BoardUseInfVO;

/**
 * 게시판의 이용정보를 관리하기 위한 컨트롤러 클래스
 */

@Controller
public class WhoyaEgovBBSUseInfoManageController {
	
	@Resource(name = "whoyaEgovBBSUseInfoManageService")
    private WhoyaEgovBBSUseInfoManageService bbsUseService; 

	/**
	 * 게시판 사용정보관리 화면
	 * @return ModelAndView
	 */
	@RequestMapping(value="whoya/cop/com/selectBBSUseInfs.do")
	public ModelAndView selectBBSUseInfs() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/egovframework/com/cop/com/EgovBoardUseInfList");
		return mav;
	}
	
	/**
	 * 게시판 사용정보 목록을 조회한다.
	 * @param bdUseVO BoardUseInfVO
	 * @return JSONObject
	 * @exception Exception
	 */
	@RequestMapping(value="whoya/cop/com/selectBBSUseJSONInfs.do", headers="Accept=application/json")
	public @ResponseBody JSONObject selectBBSUseJSONInfs(@ModelAttribute("searchVO") BoardUseInfVO bdUseVO, ModelMap model) throws Exception {
		JSONObject resultList = new JSONObject();
		
		try {
			bdUseVO.setPageIndex(0);
			Map<String, Object> map = bbsUseService.selectBBSUseInfs(bdUseVO);
			List<BoardUseInfVO> voList = (List<BoardUseInfVO>)map.get("resultList");
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(voList));
			
			// 게시판명,사용 커뮤니티 명,사용 동호회 명,등록일시,사용여부,게시판 아이디,대상시스템 아이디
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "bbsNm,cmmntyNm,clbNm,frstRegisterPnttm,useAt,bbsId,trgetId"));
		 	resultList.put("status", commonReturn.SUCCESS);
		 	resultList.put("message", "조회되었습니다");
		} catch(Exception e) {
			resultList.put("status", commonReturn.FAIL);
			resultList.put("message", e.getMessage());
			throw e;
		}
		
		return resultList;
	}

    /**
     * 게시판 사용정보를 등록한다.
     * 
     * @param bdUseVO
     * @param boardUseInf
     * @param bindingResult
     * @param commandMap
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="whoya/cop/com/insertBBSUseInf.do")
    public @ResponseBody void insertBBSUseInf(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	try {
    		String[] ids = request.getParameter("ids").split(",");
    		
    		whoyaDataProcess  data = new whoyaDataProcess();
    	    whoyaMap rows = new whoyaMap();
    	    rows = data.dataProcess(request);
    	    
    		for (int i = 0; i < ids.length; i++) {
    			whoyaMap cols = (whoyaMap) rows.get(ids[i]);
    			BoardUseInf boardUseInf = new BoardUseInf();
    			boardUseInf = (BoardUseInf)Common.convertWhoyaMapToObject(cols, boardUseInf);

    			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    			
    			String trgetType = (String)cols.get("trgetType");
    			String registSeCode = "";
    			
    			// CMMNTY 06/CLUB 05/SYSTEM(REGC01)
    			if ("CMMNTY".equals(trgetType)) {
    				registSeCode = "REGC06";
    			} else if ("CLUB".equals(trgetType)) {
    				registSeCode = "REGC05";
    			} else {
    				registSeCode = "REGC01";
    			}
    			
    			boardUseInf.setUseAt("Y");
    			boardUseInf.setFrstRegisterId(user.getUniqId());
    			boardUseInf.setRegistSeCode(registSeCode);
    			
    			if (isAuthenticated) {
    				bbsUseService.insertBBSUseInf(boardUseInf);
    			}
    		}
    		data.dataRefresh(request, response);
    	} catch ( Exception e ) {
    		e.printStackTrace();
    		throw e;
    	}
    }
    
    /**
     * 게시판 사용정보에 대한 상세정보를 조회한다.
     * 
     * @param bdUseVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/whoya/cop/com/selectBBSUseInf.do")
    public @ResponseBody BoardUseInfVO selectBBSUseInf(@ModelAttribute("searchVO") BoardUseInfVO bdUseVO, ModelMap model, HttpServletRequest request) throws Exception {
		BoardUseInfVO vo = null;
    	try {
    		vo = bbsUseService.selectBBSUseInf(bdUseVO);
	
	    	// 시스템 사용 게시판의 경우 URL 표시
	    	if ("SYSTEM_DEFAULT_BOARD".equals(vo.getTrgetId())) {
	    		if (vo.getBbsTyCode().equals("BBST02")) {	// 익명게시판
	    			vo.setProvdUrl(request.getContextPath()+ "/whoya/cop/bbs/anonymous/selectBoardList.do?bbsId=" + vo.getBbsId());
	    		} else {
	    			vo.setProvdUrl(request.getContextPath()+ "/whoya/cop/bbs/selectBoardList.do?bbsId=" + vo.getBbsId());
	    		}
	    	}
    	} catch ( Exception e ) {
    		e.printStackTrace();
    		throw e;
    	}
    	
    	return vo;
    }
    
    /**
     * 게시판 사용정보를 수정한다.
     * 
     * @param bdUseVO
     * @param bdUseInf
     * @param sessionVO
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/whoya/cop/com/updateBBSUseInf.do")
    public @ResponseBody void updateBBSUseInf(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	try {
    		String[] ids = request.getParameter("ids").split(",");
    		
    		whoyaDataProcess  data = new whoyaDataProcess();
    	    whoyaMap rows = new whoyaMap();
    	    rows = data.dataProcess(request);
    	    
    		for (int i = 0; i < ids.length; i++) {
    			whoyaMap cols = (whoyaMap) rows.get(ids[i]);
    			BoardUseInf boardUseInf = new BoardUseInf();
    			boardUseInf = (BoardUseInf)Common.convertWhoyaMapToObject(cols, boardUseInf);

    			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    			
    			if (isAuthenticated) {
    	    		bbsUseService.updateBBSUseInf(boardUseInf);
    	    	}
    		}
    		data.dataRefresh(request, response);
    	} catch ( Exception e ) {
    		e.printStackTrace();
    		throw e;
    	}
    }
  
//    @Resource(name = "EgovBBSUseInfoManageService")
//    private EgovBBSUseInfoManageService bbsUseService;
//
//    @Resource(name = "propertiesService")
//    protected EgovPropertyService propertyService;
//    
//
//    @Autowired
//    private DefaultBeanValidator beanValidator;
//
//    //protected Logger log = Logger.getLogger(this.getClass());
//    
//    /**
//     * 게시판 사용 정보를 삭제한다.
//     * 
//     * @param bdUseVO
//     * @param bdUseInf
//     * @param sessionVO
//     * @param status
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/com/deleteBBSUseInf.do")
//    public String deleteBBSUseInf(@ModelAttribute("searchVO") BoardUseInfVO bdUseVO, @ModelAttribute("bdUseInf") BoardUseInf bdUseInf,
//	    SessionStatus status, ModelMap model) throws Exception {
//
//	//LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	if (isAuthenticated) {
//	    bbsUseService.deleteBBSUseInf(bdUseInf);
//	}
//
//	return "forward:/cop/com/selectBBSUseInfs.do";
//    }
//
//    /**
//     * 게사판 사용정보 등록을 위한 등록페이지로 이동한다.
//     * 
//     * @param bdUseVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/com/addBBSUseInf.do")
//    public String addBBSUseInf(@ModelAttribute("searchVO") BoardUseInfVO bdUseVO, ModelMap model) throws Exception {
//    	
//    	if(EgovComponentChecker.hasComponent("EgovCommunityManageService")){//2011.09.15
//    		model.addAttribute("useCommunity", "true");
//    	}
//    	if(EgovComponentChecker.hasComponent("EgovClubManageService")){//2011.09.15
//    		model.addAttribute("useClub", "true");
//    	}
//    	
//    	return "egovframework/com/cop/com/EgovBoardUseInfRegist";
//    }
}
