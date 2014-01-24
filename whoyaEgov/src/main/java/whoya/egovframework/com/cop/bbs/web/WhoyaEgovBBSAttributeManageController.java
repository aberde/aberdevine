package whoya.egovframework.com.cop.bbs.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import whoya.whoyaLib;
import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.common.Common;
import whoya.common.commonReturn;
import whoya.egovframework.com.cop.bbs.service.WhoyaEgovBBSAttributeManageService;
import egovframework.com.cop.bbs.service.BoardMasterVO;


/**
 * 게시판 속성관리를 위한 컨트롤러  클래스
 */

@Controller
public class WhoyaEgovBBSAttributeManageController {
	
	@Resource(name="whoyaEgovBBSAttributeManageService")
	WhoyaEgovBBSAttributeManageService whoyaEgovBBSAttributeManageService;
	
	/**
	 * 게시판 속성관리 화면
	 * @return ModelAndView
	 */
	@RequestMapping(value="whoya/cop/bbs/SelectBBSMasterInfs.do")
	public ModelAndView selectBBSMasterInfs() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/egovframework/com/cop/bbs/EgovBoardMstrList");
		return mav;
	}
	
	/**
	 * 게시판 속성관리 목록을 조회한다.
	 * @param boardMasterVO BoardMasterVO
	 * @return JSONObject
	 * @exception Exception
	 */
	@RequestMapping(value="whoya/cop/bbs/SelectBBSMasterJSONInfs.do", headers="Accept=application/json")
	public @ResponseBody JSONObject selectLoginPolicyJSONList(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, ModelMap model) throws Exception {
		
		JSONObject resultList = new JSONObject();
		
		try {
			boardMasterVO.setPageIndex(0);
			Map<String, Object> map = whoyaEgovBBSAttributeManageService.selectBBSMasterInfs(boardMasterVO);
			List<BoardMasterVO> voList = (List<BoardMasterVO>)map.get("resultList");
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(voList));
			
			// 번호 컬럼 추가.
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap wmap = list.getMap(i);
				wmap.put("no", i + 1);
			}
						
			// 번호,게시판명,게시판유형,게시판속성,생성일,사용여부
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,bbsNm,bbsTyCodeNm,bbsAttrbCodeNm,frstRegisterPnttm,useAt"));
		 	resultList.put("status", commonReturn.SUCCESS);
		 	resultList.put("message", "조회되었습니다");
		} catch(Exception e) {
			resultList.put("status", commonReturn.FAIL);
			resultList.put("message", e.getMessage());
			throw e;
		}
		
		return resultList;
	} 
	 

//    @Resource(name = "EgovBBSAttributeManageService")
//    private EgovBBSAttributeManageService bbsAttrbService;
//
//    @Resource(name = "EgovCmmUseService")
//    private EgovCmmUseService cmmUseService;
//
//    @Resource(name = "propertiesService")
//    protected EgovPropertyService propertyService;
//    
//
//
//    @Autowired
//    private DefaultBeanValidator beanValidator;
//
//    //Logger log = Logger.getLogger(this.getClass());
//    
//    /**
//     * 신규 게시판 마스터 등록을 위한 등록페이지로 이동한다.
//     * 
//     * @param boardMasterVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @SuppressWarnings("unchecked")
//    @RequestMapping("/cop/bbs/addBBSMaster.do")
//    public String addBBSMaster(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, ModelMap model) throws Exception {
//	BoardMaster boardMaster = new BoardMaster();
//
//	ComDefaultCodeVO vo = new ComDefaultCodeVO();
//	
//	vo.setCodeId("COM004");
//	
//	List codeResult = cmmUseService.selectCmmCodeDetail(vo);
//	
//	model.addAttribute("typeList", codeResult);
//
//	vo.setCodeId("COM009");
//	
//	codeResult = cmmUseService.selectCmmCodeDetail(vo);
//	
//	model.addAttribute("attrbList", codeResult);
//	model.addAttribute("boardMaster", boardMaster);
//
//	//---------------------------------
//	// 2009.06.26 : 2단계 기능 추가
//	//---------------------------------
//	//String flag = EgovProperties.getProperty("Globals.addedOptions");
//	//if (flag != null && flag.trim().equalsIgnoreCase("true")) {
//	//    model.addAttribute("addedOptions", "true");
//	//}
//	////-------------------------------
//	
//	
//	//---------------------------------
//	// 2011.09.15 : 2단계 기능 추가 반영 방법 변경
//	//---------------------------------
//
//	
//	if(EgovComponentChecker.hasComponent("EgovBBSCommentService")){
//		model.addAttribute("useComment", "true");
//	}
//	if(EgovComponentChecker.hasComponent("EgovBBSSatisfactionService")){
//		model.addAttribute("useSatisfaction", "true");
//	}
//	
//	////-------------------------------
//
//	return "egovframework/com/cop/bbs/EgovBoardMstrRegist";
//    }
//
//    /**
//     * 신규 게시판 마스터 정보를 등록한다.
//     * 
//     * @param boardMasterVO
//     * @param boardMaster
//     * @param status
//     * @return
//     * @throws Exception
//     */
//    @SuppressWarnings("unchecked")
//    @RequestMapping("/cop/bbs/insertBBSMasterInf.do")
//    public String insertBBSMasterInf(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, @ModelAttribute("boardMaster") BoardMaster boardMaster,
//	    BindingResult bindingResult, SessionStatus status, ModelMap model) throws Exception {
//	
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	beanValidator.validate(boardMaster, bindingResult);
//	if (bindingResult.hasErrors()) {
//
//	    ComDefaultCodeVO vo = new ComDefaultCodeVO();
//	    
//	    vo.setCodeId("COM004");
//	    
//	    List codeResult = cmmUseService.selectCmmCodeDetail(vo);
//	    
//	    model.addAttribute("typeList", codeResult);
//
//	    vo.setCodeId("COM009");
//	   
//	    codeResult = cmmUseService.selectCmmCodeDetail(vo);
//	    
//	    model.addAttribute("attrbList", codeResult);
//
//	    return "egovframework/com/cop/bbs/EgovBoardMstrRegist";
//	}
//	
//	if (isAuthenticated) {
//	    boardMaster.setFrstRegisterId(user.getUniqId());
//	    boardMaster.setUseAt("Y");
//	    boardMaster.setTrgetId("SYSTEMDEFAULT_REGIST");
//
//	    bbsAttrbService.insertBBSMastetInf(boardMaster);
//	}
//
//	return "forward:/cop/bbs/SelectBBSMasterInfs.do";
//    }
//
//    /**
//     * 게시판 마스터 목록을 조회한다.
//     * 
//     * @param boardMasterVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @IncludedInfo(name="게시판속성관리", whoyaListUrl="/whoya/cop/bbs/SelectBBSMasterInfs.do", order = 180, gid = 40)
//    @RequestMapping("/cop/bbs/SelectBBSMasterInfs.do")
//    public String selectBBSMasterInfs(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, ModelMap model) throws Exception {
//	boardMasterVO.setPageUnit(propertyService.getInt("pageUnit"));
//	boardMasterVO.setPageSize(propertyService.getInt("pageSize"));
//
//	PaginationInfo paginationInfo = new PaginationInfo();
//	
//	paginationInfo.setCurrentPageNo(boardMasterVO.getPageIndex());
//	paginationInfo.setRecordCountPerPage(boardMasterVO.getPageUnit());
//	paginationInfo.setPageSize(boardMasterVO.getPageSize());
//
//	boardMasterVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
//	boardMasterVO.setLastIndex(paginationInfo.getLastRecordIndex());
//	boardMasterVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
//
//	Map<String, Object> map = bbsAttrbService.selectBBSMasterInfs(boardMasterVO);
//	int totCnt = Integer.parseInt((String)map.get("resultCnt"));
//	
//	paginationInfo.setTotalRecordCount(totCnt);
//
//	model.addAttribute("resultList", map.get("resultList"));
//	model.addAttribute("resultCnt", map.get("resultCnt"));	
//	model.addAttribute("paginationInfo", paginationInfo);
//
//	return "egovframework/com/cop/bbs/EgovBoardMstrList";
//    }
//
//    /**
//     * 게시판 마스터 상세내용을 조회한다.
//     * 
//     * @param boardMasterVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/bbs/SelectBBSMasterInf.do")
//    public String selectBBSMasterInf(@ModelAttribute("searchVO") BoardMasterVO searchVO, ModelMap model) throws Exception {
//	BoardMasterVO vo = bbsAttrbService.selectBBSMasterInf(searchVO);
//
//	model.addAttribute("result", vo);
//	
//	//---------------------------------
//	// 2009.06.26 : 2단계 기능 추가
//	//---------------------------------
//	//String flag = EgovProperties.getProperty("Globals.addedOptions");
//	//if (flag != null && flag.trim().equalsIgnoreCase("true")) {
//	//    model.addAttribute("addedOptions", "true");
//	//}
//	////-------------------------------
//	
//	//---------------------------------
//	// 2011.09.15 : 2단계 기능 추가 반영 방법 변경
//	//---------------------------------
//	
//	if(EgovComponentChecker.hasComponent("EgovBBSCommentService")){
//		model.addAttribute("useComment", "true");
//	}
//	if(EgovComponentChecker.hasComponent("EgovBBSSatisfactionService")){
//		model.addAttribute("useSatisfaction", "true");
//	}
//	
//	////-------------------------------
//	
//	
//	return "egovframework/com/cop/bbs/EgovBoardMstrUpdt";
//    }
//
//    /**
//     * 게시판 마스터 정보를 수정한다.
//     * 
//     * @param boardMasterVO
//     * @param boardMaster
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/bbs/UpdateBBSMasterInf.do")
//    public String updateBBSMasterInf(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, @ModelAttribute("boardMaster") BoardMaster boardMaster,
//	    BindingResult bindingResult, ModelMap model) throws Exception {
//
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	beanValidator.validate(boardMaster, bindingResult);
//	if (bindingResult.hasErrors()) {
//	    BoardMasterVO vo = bbsAttrbService.selectBBSMasterInf(boardMasterVO);
//
//	    model.addAttribute("result", vo);
//	    
//	    return "egovframework/com/cop/bbs/EgovBoardMstrUpdt";
//	}
//
//	if (isAuthenticated) {
//	    boardMaster.setLastUpdusrId(user.getUniqId());
//	    bbsAttrbService.updateBBSMasterInf(boardMaster);
//	}
//
//	return "forward:/cop/bbs/SelectBBSMasterInfs.do";
//    }
//
//    /**
//     * 게시판 마스터 정보를 삭제한다.
//     * 
//     * @param boardMasterVO
//     * @param boardMaster
//     * @param status
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/bbs/DeleteBBSMasterInf.do")
//    public String deleteBBSMasterInf(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, @ModelAttribute("boardMaster") BoardMaster boardMaster,
//	    SessionStatus status) throws Exception {
//
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	if (isAuthenticated) {
//	    boardMaster.setLastUpdusrId(user.getUniqId());
//	    bbsAttrbService.deleteBBSMasterInf(boardMaster);
//	}
//	// status.setComplete();
//	return "forward:/cop/bbs/SelectBBSMasterInfs.do";
//    }
//
//    /**
//     * 게시판 마스터 선택 팝업을 위한 목록을 조회한다.
//     * 
//     * @param boardMasterVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/bbs/SelectBBSMasterInfsPop.do")
//    public String selectBBSMasterInfsPop(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, ModelMap model) throws Exception {
//	boardMasterVO.setPageUnit(propertyService.getInt("pageUnit"));
//	boardMasterVO.setPageSize(propertyService.getInt("pageSize"));
//
//	PaginationInfo paginationInfo = new PaginationInfo();
//	
//	paginationInfo.setCurrentPageNo(boardMasterVO.getPageIndex());
//	paginationInfo.setRecordCountPerPage(boardMasterVO.getPageUnit());
//	paginationInfo.setPageSize(boardMasterVO.getPageSize());
//
//	boardMasterVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
//	boardMasterVO.setLastIndex(paginationInfo.getLastRecordIndex());
//	boardMasterVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
//
//	boardMasterVO.setUseAt("Y");
//	
//	Map<String, Object> map = bbsAttrbService.selectNotUsedBdMstrList(boardMasterVO);
//	int totCnt = Integer.parseInt((String)map.get("resultCnt"));
//	
//	paginationInfo.setTotalRecordCount(totCnt);
//
//	model.addAttribute("resultList", map.get("resultList"));
//	model.addAttribute("resultCnt", map.get("resultCnt"));	
//	model.addAttribute("paginationInfo", paginationInfo);
//
//	return "egovframework/com/cop/bbs/EgovBoardMstrListPop";
//    }
}