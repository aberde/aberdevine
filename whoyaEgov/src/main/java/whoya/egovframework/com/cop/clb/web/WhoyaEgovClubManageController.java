package whoya.egovframework.com.cop.clb.web;

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
import whoya.egovframework.com.cop.clb.service.WhoyaEgovClubManageService;
import egovframework.com.cop.clb.service.ClubVO;

/**
 *  동호회 정보를 관리하기 위한 컨트롤러 클래스
 */
@Controller
public class WhoyaEgovClubManageController {

	@Resource(name = "WhoyaEgovClubManageService")
	private WhoyaEgovClubManageService clubService;
	
	/**
	 * 팝업을 위하여 동호회에 대한 목록을 조회한다.
	 * 
	 * @param clubVO
	 * @param model
	 * @return JSONObject
	 * @throws Exception
	 */
	@RequestMapping("/whoya/cop/clb/selectClubInfsByPop.do")
	public @ResponseBody JSONObject selectClubtyInfsByPop(@ModelAttribute("searchVO") ClubVO clubVO, ModelMap model) throws Exception {
		JSONObject resultList = new JSONObject();
		
		try {
			clubVO.setPageIndex(0);
			clubVO.setUseAt("Y");
			Map<String, Object> map = clubService.selectClubInfs(clubVO);
			List<ClubVO> voList = (List<ClubVO>)map.get("resultList");
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(voList));
			
			// 번호 컬럼 추가.
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap wmap = list.getMap(i);
				wmap.put("no", i + 1);
				wmap.put("selectLink", "../../../images/egovframework/com/cmm/icon/search.gif^선택^javascript:whoya.common.clubSelect(\"" + wmap.get("clbId") + "\", \"" + wmap.get("clbNm") + "\");^_self");
			}
			
			// 번호,동호회명,커뮤니티명,등록일,선택
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,clbNm,cmmntyNm,bbsAttrbCodeNm,frstRegisterPnttm,selectLink"));
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
	 * 특정 커뮤니티에 사용되는 동호회 목록을 조회한다.
	 * 
	 * @param clubVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/whoya/cop/clb/selectClubInfByCmmntyId.do")
	public @ResponseBody JSONObject selectClubInfByCmmntyId(@ModelAttribute("searchVO") ClubVO clubVO, Map<String, Object> commandMap, ModelMap model) throws Exception {
		JSONObject resultList = new JSONObject();
		
		try {
			clubVO.setPageIndex(0);
			Map<String, Object> map = clubService.selectClubInfsByCmmntyId(clubVO);
			List<ClubVO> voList = (List<ClubVO>)map.get("resultList");
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(voList));
			
			// 번호 컬럼 추가.
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap wmap = list.getMap(i);
				wmap.put("no", i + 1);
			}
			
			// 번호,동호회명,등록일,사용여부
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,clbNm,frstRegisterPnttm,useAt"));
		 	resultList.put("status", commonReturn.SUCCESS);
		 	resultList.put("message", "조회되었습니다");
		} catch(Exception e) {
			resultList.put("status", commonReturn.FAIL);
			resultList.put("message", e.getMessage());
			throw e;
		}
		
		return resultList;
	}
	 

//
//    @Resource(name = "EgovConfirmManageService")
//    private EgovConfirmManageService confmService;
//
//    @Resource(name = "propertiesService")
//    protected EgovPropertyService propertyService;
//
//    @Resource(name = "EgovBBSAttributeManageService")
//    private EgovBBSAttributeManageService bbsAttrbService;
//
//    @Resource(name = "EgovBBSManageService")
//    private EgovBBSManageService bbsMngService;
//    
//
//
//    @Autowired
//    private DefaultBeanValidator beanValidator;
//
//    private static final Logger LOG = Logger.getLogger(EgovClubManageController.class.getClass());
//    
//    /**
//     * 동호회에 대한 목록을 조회한다.
//     * 
//     * @param clubVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @SuppressWarnings("unchecked")
//    @RequestMapping("/cop/clb/selectClubInfs.do")
//    public String selectClubInfs(@ModelAttribute("searchVO") ClubVO clubVO, ModelMap model) throws Exception {
//	clubVO.setPageUnit(propertyService.getInt("pageUnit"));
//	clubVO.setPageSize(propertyService.getInt("pageSize"));
//
//	PaginationInfo paginationInfo = new PaginationInfo();
//	
//	paginationInfo.setCurrentPageNo(clubVO.getPageIndex());
//	paginationInfo.setRecordCountPerPage(clubVO.getPageUnit());
//	paginationInfo.setPageSize(clubVO.getPageSize());
//
//	clubVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
//	clubVO.setLastIndex(paginationInfo.getLastRecordIndex());
//	clubVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
//
//	HashMap _map = (HashMap)clubService.selectClubInfs(clubVO);
//	int totCnt = Integer.parseInt((String)_map.get("resultCnt"));
//	
//	paginationInfo.setTotalRecordCount(totCnt);
//
//	model.addAttribute("resultList", _map.get("resultList"));
//	model.addAttribute("resultCnt", _map.get("resultCnt"));
//	model.addAttribute("paginationInfo", paginationInfo);
//
//	return "egovframework/com/cop/clb/EgovClubList";
//    }
//
//    /**
//     * 동호회 등록을 위한 등록페이지로 이동한다.
//     * 
//     * @param clubVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/clb/addClubInf.do")
//    public String addClubInf(@ModelAttribute("searchVO") ClubVO clubVO, ModelMap model) throws Exception {
//	return "egovframework/com/cop/clb/EgovClubRegist";
//    }
//	
//	/**
//     * 동호회 정보를 등록한다.
//     * 
//     * @param clubVO
//     * @param club
//     * @param sessionVO
//     * @param status
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/clb/insertClubInf.do")
//    public String insertClubInf(@ModelAttribute("searchVO") ClubVO clubVO, @ModelAttribute("club") Club club, BindingResult bindingResult,
//	    SessionStatus status, ModelMap model) throws Exception {
//
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	beanValidator.validate(club, bindingResult);
//
//	if (bindingResult.hasErrors()) {
//	    return "egovframework/com/cop/clb/EgovClubRegist";
//	}
//
//	club.setUseAt("Y");
//	club.setRegistSeCode("REGC03");
//	club.setFrstRegisterId(user.getUniqId());
//
//	if (isAuthenticated) {
//	    clubService.insertClubInf(club);
//	}
//
//	return "forward:/cop/clb/selectClubInfs.do";
//    }
//
//    /**
//     * 동호회에 대한 게시판 정보 및 사용자 정보를 조회한다.
//     * 
//     * @param clubVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/clb/selectClubInf.do")
//    public String selectClubInf(@ModelAttribute("searchVO") ClubVO clubVO, ModelMap model, HttpServletRequest request) throws Exception {
//	Map<String, Object> map = clubService.selectClubInf(clubVO);
//	ClubVO vo = (ClubVO)map.get("clubVO");
//	
//	//-----------------------
//	// 제공 URL 
//	//-----------------------
//	vo.setProvdUrl(request.getContextPath()+ "/cop/cus/ClubMainPage.do?param_clbId=" + vo.getClbId());
//	////---------------------
//
//	model.addAttribute("clubVO", vo);
//	model.addAttribute("result", map.get("resultList"));
//
//	return "egovframework/com/cop/clb/EgovClubInqire";
//    }
//
//    /**
//     * 동호회 수정을 위한 수정페이지로 이동한다.
//     * 
//     * @param clubVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/clb/forUpdateClbInf.do")
//    public String forUpdateClbInf(@ModelAttribute("searchVO") ClubVO clubVO, ModelMap model) throws Exception {
//	Map<String, Object> map = clubService.selectClubInf(clubVO);
//
//	model.addAttribute("clubVO", (ClubVO)map.get("clubVO"));
//	model.addAttribute("clubUser", (ClubUser)map.get("clubUser"));
//	model.addAttribute("result", map.get("resultList"));
//
//	return "egovframework/com/cop/clb/EgovClubUpdt";
//    }
//
//    /**
//     * 동호회에 대한 상세내용을 수정한다.
//     * 
//     * @param clubVO
//     * @param club
//     * @param sessionVO
//     * @param status
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/clb/updateClbInf.do")
//    public String updateClbInf(@ModelAttribute("searchVO") ClubVO clubVO, @ModelAttribute("club") Club club, BindingResult bindingResult,
//	    SessionStatus status, ModelMap model) throws Exception {
//
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	beanValidator.validate(club, bindingResult);
//
//	if (bindingResult.hasErrors()) {
//
//	    Map<String, Object> map = clubService.selectClubInf(clubVO);
//	    ClubVO vo = (ClubVO)map.get("clubVO");
//	    
//	    model.addAttribute("clubVO", vo);
//	    model.addAttribute("result", map.get("resultList"));
//
//	    return "egovframework/com/cop/clb/EgovClubUpdt";
//	}
//
//	club.setLastUpdusrId(user.getUniqId());
//	
//	if (isAuthenticated) {
//	    if ("Y".equals(club.getUseAt())) {
//		clubService.updateClubInf(club);
//	    } else {
//		clubService.deleteClubInf(club);
//	    }
//	}
//
//	return "forward:/cop/clb/selectClubInfs.do";
//    }
//
//    /**
//     * 동호회 가입 신청을 처리한다.
//     * 
//     * @param clubUser
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/clb/insertClubUserBySelf.do")
//    public String insertClubUserBySelf(@ModelAttribute("clubUser") ClubUser clubUser, ModelMap model) throws Exception {
//
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	String retVal = "";
//
//	if ("".equals(clubUser.getOprtrAt())) {
//	    clubUser.setOprtrAt("N");
//	}
//	
//	clubUser.setUseAt("Y");
//	clubUser.setFrstRegisterId(user.getUniqId());
//	clubUser.setEmplyrId(user.getUniqId());
//	
//	if (isAuthenticated) {
//
//	    //---------------------------------------------
//	    // 승인요청 처리
//	    //---------------------------------------------
//	    //retVal = clubService.insertClubUserInf(clubUser);
//	    retVal = clubService.checkClubUserInf(clubUser);
//
//	    if (!retVal.equals("EXIST")) {
//		ClubVO clubVO = new ClubVO();
//		clubVO.setClbId(clubUser.getClbId());
//
//		ClubUser operator = clubService.selectOperator(clubVO);
//
//		ConfirmHistory history = new ConfirmHistory();
//
//		history.setConfmRqesterId(user.getUniqId()); 	// 요청자 ID
//		history.setConfmerId(operator.getEmplyrId()); 	// 관리자
//		history.setConfmTyCode("CF13"); 		// 동호회사용자등록
//		history.setConfmSttusCode("AP01"); 		// 승인요청
//		history.setOpertTyCode("WC01"); 		// 회원가입
//		history.setOpertId(clubUser.getCmmntyId()); 	// 작업자 ID (동호회의 경우 커뮤니티ID 지정)
//		history.setTrgetJobTyCode("CLB"); 		// 대상작업구분
//		history.setTrgetJobId(clubUser.getClbId()); 	// 대상작업 ID
//
//		int cnt = confmService.countConfirmRequest(history);
//
//		if (cnt == 0) {
//		    confmService.insertConfirmRequest(history);
//		} else {
//		    retVal = "ING";
//		}
//	    }
//	    ////-------------------------------------------
//	}
//
//	model.addAttribute("returnMsg", retVal);
//	
//	return "egovframework/com/cop/clb/EgovClubMsg";
//    }
//
//    /**
//     * 동호회 목록 조회 포틀릿화면에서 특정 동호회를 선택한다.
//     * 
//     * @param clubVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cus/ClubMainPage.do")
//    public String selectClubMainPage(@ModelAttribute("searchVO") ClubVO clubVO, Map<String, Object> commandMap, ModelMap model) throws Exception {
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	String clbId = (String)commandMap.get("param_clbId");
//
//	clubVO.setClbId(clbId);
//	clubVO.setEmplyrId(user.getUniqId());
//	
//	String tmplatCours = clubService.selectClubTemplat(clubVO);
//
//	if ("".equals(tmplatCours) || tmplatCours == null) {
//	    tmplatCours = "egovframework/com/cop/tpl/EgovClbBaseTmpl";
//	}
//	
//	Map<String, Object> map = clubService.selectClubInf(clubVO);
//	ClubVO vo = (ClubVO)map.get("clubVO");
//
//	model.addAttribute("clubVO", vo);
//	model.addAttribute("clubUser", (ClubUser)map.get("clubUser"));
//
//	//--------------------------------
//	// 게시판 목록 정보 처리
//	//--------------------------------
//	BoardMasterVO bbsVo = new BoardMasterVO();
//	bbsVo.setTrgetId(clubVO.getClbId());
//	List<BoardMasterVO> bbsResult = bbsAttrbService.selectAllBdMstrByTrget(bbsVo);
//
//	model.addAttribute("bbsList", bbsResult);
//	////------------------------------
//
//	if (isAuthenticated) {
//	    model.addAttribute("isAuthenticated", "Y");
//	} else {
//	    model.addAttribute("isAuthenticated", "N");
//	}
//	return tmplatCours;
//    }
//
//    /**
//     * 동호회 목록 조회 포틀릿화면에서 특정 동호회를 선택한다.
//     * 
//     * @param clubVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cus/ClubMainContents.do")
//    public String selectClubMainContents(@ModelAttribute("searchVO") ClubVO clubVO, Map<String, Object> commandMap, ModelMap model) throws Exception {
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	@SuppressWarnings("unused")
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	clubVO.setEmplyrId(user.getUniqId());
//
//	//--------------------------------
//	// 게시판 목록 정보 처리
//	//--------------------------------
//	BoardMasterVO bbsVo = new BoardMasterVO();
//	bbsVo.setTrgetId(clubVO.getClbId());
//	List<BoardMasterVO> bbsResult = bbsAttrbService.selectAllBdMstrByTrget(bbsVo);
//
//	// 방명록 제외 처리
//	for (int i = 0; i < bbsResult.size(); i++) {
//	    if ("BBST04".equals(bbsResult.get(i).getBbsTyCode())) {
//		bbsResult.remove(i);
//	    }
//	}
//	model.addAttribute("bbsList", bbsResult);
//
//	//--------------------------------
//	// 게시물 목록 정보 처리
//	//--------------------------------
//	BoardVO boardVo = null;
//	BoardMasterVO masterVo = null;
//	List<Object> target = new ArrayList<Object>();	// Object => List<BoardVO>
//	for (int i = 0; i < bbsResult.size() && i < 4; i++) {
//	    masterVo = bbsResult.get(i);
//	    boardVo = new BoardVO();
//
//	    boardVo.setBbsId(masterVo.getBbsId());
//	    boardVo.setBbsNm(masterVo.getBbsNm());
//
//	    boardVo.setPageUnit(4);
//	    boardVo.setPageSize(4);
//
//	    boardVo.setFirstIndex(0);
//	    boardVo.setRecordCountPerPage(4);
//
//	    Map<String, Object> map = bbsMngService.selectBoardArticles(boardVo, masterVo.getBbsAttrbCode());
//
//	    target.add(map.get("resultList"));
//	}
//
//	model.addAttribute("articleList", target);
//
//	return "egovframework/com/cop/tpl/EgovClbBaseTmplContents";
//    }
//
//    /**
//     * 커뮤니티를 위한 동호회 등록화면으로 이동한다.
//     * 
//     * @param clubVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/clb/addCmmntyClubInf.do")
//    public String addCmmntyClubInf(@ModelAttribute("searchVO") ClubVO clubVO, ModelMap model) throws Exception {
//	/*
//	ClubVO vo = new ClubVO();
//	vo = (ClubVO)clubService.selectCmmntyInfByClbId(clubVO);
//	 */
//	
//	model.addAttribute("clubVO", clubVO);
//
//	return "egovframework/com/cop/clb/EgovClubRegistByTrget";
//    }
//
//    /**
//     * 커뮤니티에 사용되는 동호회 상세정보를 조회한다.
//     * 
//     * @param clubVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/clb/selectCmmntyClubInf.do")
//    public String selectCmmntyClubInf(@ModelAttribute("searchVO") ClubVO clubVO, ModelMap model) throws Exception {
//	Map<String, Object> map = clubService.selectClubInf(clubVO);
//	ClubVO vo = (ClubVO)map.get("clubVO");
//	
//	model.addAttribute("clubVO", vo);
//	model.addAttribute("result", map.get("resultList"));
//
//	return "egovframework/com/cop/clb/EgovClubInqireByTrget";
//    }
//
//    /**
//     * 커뮤니티에 사용되는 동호회 정보 수정을 위한 수정페이지로 이동한다.
//     * 
//     * @param clubVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/clb/forUpdateCmmntyClbInf.do")
//    public String forUpdateCmmntyClbInf(@ModelAttribute("searchVO") ClubVO clubVO, ModelMap model) throws Exception {
//	Map<String, Object> map = clubService.selectClubInf(clubVO);
//	ClubVO vo = (ClubVO)map.get("clubVO");
//
//	model.addAttribute("clubVO", vo);
//	model.addAttribute("clubUser", (ClubUser)map.get("clubUser"));
//	model.addAttribute("result", map.get("resultList"));
//
//	return "egovframework/com/cop/clb/EgovClubUpdtByTrget";
//    }
//
//    /**
//     * 포틀릿를 위하여 커뮤니티에 사용되는 동호회 목록을 조회 한다.
//     * 
//     * @param club
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cus/ClubListPortlet.do")
//    public String selectClubListPortlet(@ModelAttribute("searchVO") Club club, ModelMap model) throws Exception {
//	List<ClubVO> result = clubService.selectClubListPortlet(club);
//	
//	model.addAttribute("resultList", result);
//	
//	return "egovframework/com/cop/clb/EgovClubListPortlet";
//    }
//
//    /**
//     * 포틀릿를 위하여 특정 커뮤니티에 사용되는 동호회 목록을 조회 한다.
//     * 
//     * @param clubVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/clb/selectAllClubByTrget.do")
//    public String selectAllClubByTrget(@ModelAttribute("club") Club club, ModelMap model) throws Exception {
//
//	List<ClubVO> result = clubService.selectClubListPortletByTrget(club);
//	
//	model.addAttribute("resultList", result);
//	
//	return "egovframework/com/cop/clb/EgovClubListPortletByTrget";
//    }
//
//    /**
//     * 동호회 탈퇴신청을 처리한다.
//     * 
//     * @param clubUser
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/com/deleteClubUserBySelf.do")
//    public String deleteClubUserBySelf(@ModelAttribute("clubUser") ClubUser clubUser, ModelMap model) throws Exception {
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	String retVal = "DEL_REQ_SUCCESS";
//
//	//clubUser.setLastUpdusrId(user.getUniqId());
//	//clubUser.setEmplyrId(user.getUniqId());		
//	//clubUser.setSecsnDe(EgovDateUtil.getToday());	
//	if (isAuthenticated) {
//	    //( #confmNumber#, #confmRqesterId#, #confmerId#, #confmTyCode#, 
//	    // #confmSttusCode#, #opertTyCode#, #opertId#, #trgetJobTyCode#, #trgetJobId# )
//
//	    ConfirmHistory history = new ConfirmHistory();
//	    
//	    history.setConfmRqesterId(user.getUniqId());
//	    history.setConfmerId(clubUser.getEmplyrId());
//	    history.setConfmTyCode("CF14"); //006
//	    history.setConfmSttusCode("AP01"); //007
//	    history.setOpertTyCode("WC03");
//	    history.setOpertId("");
//	    history.setTrgetJobTyCode("CLB");
//	    history.setTrgetJobId(clubUser.getClbId());
//
//	    confmService.insertConfirmRequest(history);
//
//	    //clubService.deleteClubUserInf(clubUser);
//	}
//
//	model.addAttribute("returnMsg", retVal);
//	
//	return "egovframework/com/cop/clb/EgovClubMsg";
//    }
//    
//    /**
//     * 미리보기 처리.
//     * 
//     * @param clubVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cus/previewClubMainPage.do")
//    public String preivewClubMainPage(@ModelAttribute("searchVO") ClubVO clubVO, Map<String, Object> commandMap, ModelMap model) throws Exception {
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	String tmplatCours = clubVO.getSearchWrd();
//	
//	ClubVO vo = new ClubVO();
//	
//	vo.setClbNm("미리보기 동호회");
//	vo.setClbIntrcn("미리보기를 위한 동호회입니다.");
//	vo.setUseAt("Y");
//	vo.setFrstRegisterId(user.getUniqId());	// 본인
//	
//	ClubUser clubUser = new ClubUser();
//	
//	clubUser.setEmplyrId(user.getUniqId());
//	clubUser.setEmplyrNm("관리자");
//
//	model.addAttribute("clubVO", vo);
//	model.addAttribute("clubUser", clubUser);
//
//	//--------------------------------
//	// 게시판 목록 정보 처리
//	//--------------------------------
//	List<BoardMasterVO> bbsResult = new ArrayList<BoardMasterVO>();
//	
//	BoardMasterVO target = null;
//	
//	target = new BoardMasterVO();
//	target.setBbsNm("공지게시판");
//	bbsResult.add(target);
//	
//	target = new BoardMasterVO();
//	target.setBbsNm("자료실");
//	bbsResult.add(target);
//	
//	target = new BoardMasterVO();
//	target.setBbsNm("방명록");
//	bbsResult.add(target);
//	
//	target = new BoardMasterVO();
//	target.setBbsNm("갤러리");
//	bbsResult.add(target);
//	
//	target = new BoardMasterVO();
//	target.setBbsNm("자유게시판");
//	bbsResult.add(target);
//
//	model.addAttribute("bbsList", bbsResult);
//	////------------------------------
//
//	if (isAuthenticated) {
//	    model.addAttribute("isAuthenticated", "Y");
//	} else {
//	    model.addAttribute("isAuthenticated", "N");
//	}
//	
//	model.addAttribute("preview", "true");
//	
//	return tmplatCours;
//    }
//    
//    /**
//     * 동호회 목록 조회 포틀릿화면에서 특정 동호회를 선택한다.
//     * 
//     * @param clubVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cus/previewClubMainContents.do")
//    public String previewClubMainContents(@ModelAttribute("searchVO") ClubVO clubVO, Map<String, Object> commandMap, ModelMap model) throws Exception {
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	@SuppressWarnings("unused")
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	clubVO.setEmplyrId(user.getUniqId());
//
//	//--------------------------------
//	// 게시판 목록 정보 처리
//	//--------------------------------
//	List<BoardMasterVO> bbsResult = new ArrayList<BoardMasterVO>();
//
//	BoardMasterVO master = null;
//	
//	master = new BoardMasterVO();
//	master.setBbsNm("공지게시판");
//	bbsResult.add(master);
//	
//	master = new BoardMasterVO();
//	master.setBbsNm("갤러리");
//	bbsResult.add(master);
//	
//	master = new BoardMasterVO();
//	master.setBbsNm("자유게시판");
//	bbsResult.add(master);
//	
//	master = new BoardMasterVO();
//	master.setBbsNm("자료실");
//	bbsResult.add(master);
//	
//	model.addAttribute("bbsList", bbsResult);
//
//	//--------------------------------
//	// 게시물 목록 정보 처리
//	//--------------------------------
//	List<Object> target = new ArrayList<Object>();	// Object => List<BoardVO>
//	for (int i = 0; i < bbsResult.size() && i < 4; i++) {
//	    
//	    target.add(null);
//	}
//
//	model.addAttribute("articleList", target);
//	
//	model.addAttribute("preview", "true");
//
//	return "egovframework/com/cop/tpl/EgovClbBaseTmplContents";
//    }

}
