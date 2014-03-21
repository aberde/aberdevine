package whoya.egovframework.com.uss.olp.mgt.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import whoya.whoyaLib;
import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.common.Common;
import whoya.common.commonReturn;
import whoya.egovframework.com.uss.olp.mgt.service.WhoyaEgovMeetingManageService;
import egovframework.com.cmm.ComDefaultVO;
/**
 * 회의관리를 처리하기 위한 Controller 구현 Class
 */

@Controller
public class WhoyaEgovMeetingManageController {
  
    /** egovMeetingManageService Member Variable */
    @Resource(name = "whoyaEgovMeetingManageService")
    private WhoyaEgovMeetingManageService egovMeetingManageService;
    
    /**
     * 부서목록을 조회한다.
     * @param searchVO
     * @param commandMap
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/mgt/EgovMeetingManageLisAuthorGroupPopup.do")
    public @ResponseBody JSONObject egovMeetingManageLisAuthorGroupPopupPost (@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, ModelMap model) throws Exception {
        JSONObject resultList = new JSONObject();
        
        try {
            // EgovMap 형식으로 받음.
            List sampleList = egovMeetingManageService.egovMeetingManageLisAuthorGroupPopup(searchVO);
            whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(sampleList));
            
            // 순번 컬럼 추가.
            // TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
            for ( int i = 0 ; i < list.size(); i++ ) {
                whoyaMap wmap = list.getMap(i);
                wmap.put("no", i + 1);
                wmap.put("selectLink", "../../../images/egovframework/com/cmm/icon/search.gif^선택^javascript:whoya.common.groupSelect(\"" + wmap.get("orgnztId") + "\", \"" + wmap.get("orgnztNm") + "\");^_self");
            }
                        
            // 순번,부서명,부서설명,선택
            resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,orgnztNm,orgnztDc,selectLink"));
            resultList.put("status", commonReturn.SUCCESS);
            resultList.put("message", "조회되었습니다");
        } catch(Exception e) {
            e.printStackTrace();
            resultList.put("status", commonReturn.FAIL);
            resultList.put("message", e.getMessage());
        }
        
        return resultList;
    }
  
    /**
     * 회원목록을 조회한다.
     * @param searchVO
     * @param commandMap
     * @param model
     * @return  JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/mgt/EgovMeetingManageLisEmpLyrPopup.do")
    public @ResponseBody JSONObject egovMeetingManageLisEmpLyrPopupPost (@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, ModelMap model) throws Exception {
        JSONObject resultList = new JSONObject();
        
        try {
            // EgovMap 형식으로 받음.
            List sampleList = egovMeetingManageService.egovMeetingManageLisEmpLyrPopup(searchVO);
            whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(sampleList));
            
            // 순번 컬럼 추가.
            // TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
            for ( int i = 0 ; i < list.size(); i++ ) {
                whoyaMap wmap = list.getMap(i);
                wmap.put("no", i + 1);
                String homeadres = (String)wmap.get("homeadres") == null ? "" : (String)wmap.get("homeadres");
                String detailAdres = (String)wmap.get("detailAdres") == null ? "" : (String)wmap.get("detailAdres");
                wmap.put("adres", homeadres + " " + detailAdres);
                wmap.put("selectLink", "../../../images/egovframework/com/cmm/icon/search.gif^선택^javascript:whoya.common.schdulChargerSelect(\"" + wmap.get("esntlId") + "\", \"" + wmap.get("emplyrId") + "\");^_self");
            }
                        
            // 순번,아이디,이름,전화번호,주소,선택
            resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,emplyrId,userNm,offmTelno,adres,selectLink"));
            resultList.put("status", commonReturn.SUCCESS);
            resultList.put("message", "조회되었습니다");
        } catch(Exception e) {
            e.printStackTrace();
            resultList.put("status", commonReturn.FAIL);
            resultList.put("message", e.getMessage());
        }
        
        return resultList;
    }
//		 
//	 
//	
//	/** Log Member Variable */
//	protected Log log = LogFactory.getLog(this.getClass());
//	
//	/** beanValidator Member Variable */
//	@Autowired
//	private DefaultBeanValidator beanValidator;
//	 
//    /** propertiesService Member Variable */
//    @Resource(name = "propertiesService")
//    protected EgovPropertyService propertiesService;
//    
//    @RequestMapping(value="/uss/olp/mgt/EgovMeetingManageMain.do")
//    public String egovMeetingManageMain(ModelMap model) throws Exception {
//    	return "egovframework/com/uss/olp/mgt/EgovMeetingManageMain";
//    }
//    
//    @RequestMapping(value="/uss/olp/mgt/EgovMeetingManageLeft.do")
//    public String egovMeetingManageLeft(ModelMap model) throws Exception {
//    	return "egovframework/com/uss/olp/mgt/EgovMeetingManageLeft";
//    }
//    
//	/** EgovMessageSource Member Variable */
//    @Resource(name="egovMessageSource")
//    EgovMessageSource egovMessageSource;
//    
//    
//    /**
//     * 개별 배포시 메인메뉴를 조회한다.
//     * @param model
//     * @return	"/uss/sam/cpy/"
//     * @throws Exception
//     */
//    @RequestMapping(value="/uss/olp/mgt/EgovMain.do")
//    public String egovMain(ModelMap model) throws Exception {
//    	return "egovframework/com/uss/olp/mgt/EgovMain";
//    }
//    
//    /**
//     * 메뉴를 조회한다.
//     * @param model
//     * @return	"/uss/sam/cpy/EgovLeft"
//     * @throws Exception
//     */
//    @RequestMapping(value="/uss/olp/mgt/EgovLeft.do")
//    public String egovLeft(ModelMap model) throws Exception {
//    	return "egovframework/com/uss/olp/mgt/EgovLeft";
//    }
//    
//	/**
//	 * 회의정보 목록을 조회한다.  
//	 * @param searchVO
//	 * @param commandMap
//	 * @param meetingManageVO
//	 * @param model
//	 * @return "egovframework/com/uss/olp/mgt/EgovMeetingManageList"
//	 * @throws Exception
//	 */
//    @IncludedInfo(name="회의관리", order = 650 ,gid = 50)
//	@RequestMapping(value="/uss/olp/mgt/EgovMeetingManageList.do")
//	public String egovMeetingManageList(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO, 
//			Map commandMap, 
//			MeetingManageVO meetingManageVO,
//    		ModelMap model)
//    throws Exception {
//
//    	/** EgovPropertyService.sample */
//    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
//    	searchVO.setPageSize(propertiesService.getInt("pageSize"));
//    	
//    	/** pageing */
//    	PaginationInfo paginationInfo = new PaginationInfo();
//		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
//		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
//		paginationInfo.setPageSize(searchVO.getPageSize());
//		
//		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
//		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
//		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
//		
//        List sampleList = egovMeetingManageService.selectMeetingManageList(searchVO);
//        model.addAttribute("resultList", sampleList);
//        
//        model.addAttribute("searchKeyword", commandMap.get("searchKeyword") == null ? "" : (String)commandMap.get("searchKeyword"));
//        model.addAttribute("searchCondition", commandMap.get("searchCondition") == null ? "" : (String)commandMap.get("searchCondition"));
//        
//        int totCnt = (Integer)egovMeetingManageService.selectMeetingManageListCnt(searchVO);
//		paginationInfo.setTotalRecordCount(totCnt);
//        model.addAttribute("paginationInfo", paginationInfo);
//		
//		return "egovframework/com/uss/olp/mgt/EgovMeetingManageList"; 
//		
//	}
//	
//	/**
//	 * 회의정보 목록을 상세조회 조회한다. 
//	 * @param searchVO
//	 * @param meetingManageVO
//	 * @param commandMap
//	 * @param model
//	 * @return "egovframework/com/uss/olp/mgt/EgovMeetingManageDetail"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/uss/olp/mgt/EgovMeetingManageDetail.do")
//	public String egovMeetingManageDetail(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO, 
//			MeetingManageVO meetingManageVO,
//			Map commandMap,
//    		ModelMap model)
//    throws Exception {
//		
//		String sLocationUrl = "egovframework/com/uss/olp/mgt/EgovMeetingManageDetail";
//		
//		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");		
//		if(sCmd.equals("del")){
//			egovMeetingManageService.deleteMeetingManage(meetingManageVO);
//			sLocationUrl = "redirect:/uss/olp/mgt/EgovMeetingManageList.do"; 
//		}else{
//			List sampleList = egovMeetingManageService.selectMeetingManageDetail(meetingManageVO);
//        	model.addAttribute("resultList", sampleList);
//		}
//        
//		return sLocationUrl; 	
//	}
//		
//	/**
//	  * 회의정보를 수정한다. 
//	 * @param searchVO
//	 * @param meetingManageVO
//	 * @param bindingResult
//	 * @param commandMap
//	 * @param model
//	 * @return "egovframework/com/uss/olp/mgt/EgovMeetingManageModify"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/uss/olp/mgt/EgovMeetingManageModify.do")
//	public String meetingManageModify(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO, 
//			@ModelAttribute("meetingManageVO") MeetingManageVO meetingManageVO,
//			BindingResult bindingResult,
//			Map commandMap,
//    		ModelMap model)
//    throws Exception {
//
//    	// 0. Spring Security 사용자권한 처리
//    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
//        	return "egovframework/com/uat/uia/EgovLoginUsr";
//    	}
//		//로그인 객체 선언
//		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//		
//		String sLocationUrl = "egovframework/com/uss/olp/mgt/EgovMeetingManageModify"; 
//
//		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
//		
//        if(sCmd.equals("save")){
//    		//서버  validate 체크
//            beanValidator.validate(meetingManageVO, bindingResult);
//    		if(bindingResult.hasErrors()){
//                List resultList = egovMeetingManageService.selectMeetingManageDetail(meetingManageVO);
//                model.addAttribute("resultList", resultList);
//    			return sLocationUrl;
//    		}
//    		//아이디 설정
//        	meetingManageVO.setFrstRegisterId((String)loginVO.getUniqId());
//        	meetingManageVO.setLastUpdusrId((String)loginVO.getUniqId());
//        	
//        	egovMeetingManageService.updateMeetingManage(meetingManageVO);
//        	sLocationUrl = "redirect:/uss/olp/mgt/EgovMeetingManageList.do"; 
//        }else{
//            List resultList = egovMeetingManageService.selectMeetingManageDetail(meetingManageVO);
//            model.addAttribute("resultList", resultList);
//        }
//
//		return sLocationUrl; 	
//	}
//	
//	/**
//	 * 회의정보를 등록한다. 
//	 * @param searchVO
//	 * @param meetingManageVO
//	 * @param bindingResult
//	 * @param commandMap
//	 * @param model
//	 * @return "egovframework/com/uss/olp/mgt/EgovMeetingManageRegist"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/uss/olp/mgt/EgovMeetingManageRegist.do")
//	public String meetingManageRegist(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO, 
//			@ModelAttribute("meetingManageVO") MeetingManageVO meetingManageVO, 
//			BindingResult bindingResult,
//			Map commandMap, 
//    		ModelMap model)
//    throws Exception {
//    	// 0. Spring Security 사용자권한 처리
//    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
//        	return "egovframework/com/uat/uia/EgovLoginUsr";
//    	}
//    	
//		//로그인 객체 선언
//		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//		
//		String sLocationUrl = "egovframework/com/uss/olp/mgt/EgovMeetingManageRegist"; 
//		
//		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
//		log.info("cmd =>" + sCmd);
//		
//        if(sCmd.equals("save")){
//    		//서버  validate 체크
//            beanValidator.validate(meetingManageVO, bindingResult);
//    		if(bindingResult.hasErrors()){
//    			return sLocationUrl;
//    		}
//    		//아이디 설정
//        	meetingManageVO.setFrstRegisterId((String)loginVO.getUniqId());
//        	meetingManageVO.setLastUpdusrId((String)loginVO.getUniqId());
//    		
//        	egovMeetingManageService.insertMeetingManage(meetingManageVO);
//        	sLocationUrl = "redirect:/uss/olp/mgt/EgovMeetingManageList.do"; 
//        }
//		
//		return sLocationUrl; 
//	}
//	
}


