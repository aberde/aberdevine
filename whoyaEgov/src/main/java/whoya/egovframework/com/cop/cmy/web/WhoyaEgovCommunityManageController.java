package whoya.egovframework.com.cop.cmy.web;

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
import whoya.egovframework.com.cop.cmy.service.WhoyaEgovCommunityManageService;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.cmy.service.CommunityVO;

/**
 * 커뮤니티 정보를 관리하기 위한 컨트롤러 클래스
 */

@Controller
public class WhoyaEgovCommunityManageController {
	
	@Resource(name = "WhoyaEgovCommunityManageService")
	private WhoyaEgovCommunityManageService cmmntyService;
	 
	/**
	 * 팝업을 위한 커뮤니티 목록 정보를 조회한다.
	 * 
	 * @param cmmntyVO
	 * @param model
	 * @return JSONObject
	 * @throws Exception
	 */
	@RequestMapping(value="/whoya/cop/cmy/selectCmmntyInfsByPop.do", headers="Accept=application/json")
	public @ResponseBody JSONObject selectCmmntyInfsByPop(@ModelAttribute("searchVO") CommunityVO cmmntyVO, ModelMap model) throws Exception {
		JSONObject resultList = new JSONObject();
		
		try {
			cmmntyVO.setPageIndex(0);
			Map<String, Object> map = cmmntyService.selectCommunityInfs(cmmntyVO);
			List<CommunityVO> voList = (List<CommunityVO>)map.get("resultList");
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(voList));
			
			// 번호 컬럼 추가.
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap wmap = list.getMap(i);
				wmap.put("no", i + 1);
				wmap.put("selectLink", "../../../images/egovframework/com/cmm/icon/search.gif^선택^javascript:communitySelect(\"" + wmap.get("cmmntyId") + "\", \"" + wmap.get("cmmntyNm") + "\");^_self");
			}
			
			// 번호,커뮤니티명,생성자,생성일,사용여부,선택
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,cmmntyNm,frstRegisterNm,frstRegisterPnttm,useAt,selectLink"));
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
//    @Resource(name = "EgovBBSAttributeManageService")
//    private EgovBBSAttributeManageService bbsAttrbService;
//
//    @Resource(name = "EgovClubManageService")
//    private EgovClubManageService clubService;
//
//    @Resource(name = "EgovConfirmManageService")
//    private EgovConfirmManageService confmService;
//
//    @Resource(name = "propertiesService")
//    protected EgovPropertyService propertyService;
//
//    @Resource(name = "EgovBBSManageService")
//    private EgovBBSManageService bbsMngService;
//
//    @Autowired
//    private DefaultBeanValidator beanValidator;
//
//    //Logger log = Logger.getLogger(this.getClass());
//	
//	/**
//     * 커뮤니티에 대한 목록을 조회한다.
//     * 
//     * @param cmmntyVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @IncludedInfo(name="커뮤니티관리", order = 270 ,gid = 40)
//    @RequestMapping("/cop/cmy/selectCmmntyInfs.do")
//    public String selectCmmntyInfs(@ModelAttribute("searchVO") CommunityVO cmmntyVO, ModelMap model) throws Exception {
//	cmmntyVO.setPageUnit(propertyService.getInt("pageUnit"));
//	cmmntyVO.setPageSize(propertyService.getInt("pageSize"));
//
//	PaginationInfo paginationInfo = new PaginationInfo();
//	
//	paginationInfo.setCurrentPageNo(cmmntyVO.getPageIndex());
//	paginationInfo.setRecordCountPerPage(cmmntyVO.getPageUnit());
//	paginationInfo.setPageSize(cmmntyVO.getPageSize());
//
//	cmmntyVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
//	cmmntyVO.setLastIndex(paginationInfo.getLastRecordIndex());
//	cmmntyVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
//
//	Map<String, Object> map = cmmntyService.selectCommunityInfs(cmmntyVO);
//	int totCnt = Integer.parseInt((String)map.get("resultCnt"));
//	
//	paginationInfo.setTotalRecordCount(totCnt);
//
//	model.addAttribute("resultList", map.get("resultList"));
//	model.addAttribute("resultCnt", map.get("resultCnt"));
//	model.addAttribute("paginationInfo", paginationInfo);
//
//	return "egovframework/com/cop/cmy/EgovCmmntyList";
//    }
//
//    /**
//     * 커뮤니티 등록을 위한 등록페이지로 이동한다.
//     * 
//     * @param cmmntyVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cmy/addCmmntyInf.do")
//    public String addCmmntyInf(@ModelAttribute("searchVO") CommunityVO cmmntyVO, ModelMap model) throws Exception {
//	return "egovframework/com/cop/cmy/EgovCmmntyRegist";
//    }
//
//    /**
//     * 커뮤니티 정보를 등록한다.
//     * 
//     * @param cmmntyVO
//     * @param cmmnty
//     * @param sessionVO
//     * @param status
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cmy/insertCmmntyInf.do")
//    public String insertCmmntyInf(@ModelAttribute("searchVO") CommunityVO cmmntyVO, @ModelAttribute("community") Community community,
//	    BindingResult bindingResult, SessionStatus status, ModelMap model) throws Exception {
//
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	beanValidator.validate(community, bindingResult);
//
//	if (bindingResult.hasErrors()) {
//	    return "egovframework/com/cop/cmy/EgovCmmntyRegist";
//	}
//
//	community.setUseAt("Y");
//	community.setRegistSeCode("REGC02");
//	community.setFrstRegisterId(user.getUniqId());
//
//	if (isAuthenticated) {
//	    cmmntyService.insertCommunityInf(community);
//	}
//
//	return "forward:/cop/cmy/selectCmmntyInfs.do";
//    }
//
//    /**
//     * 커뮤니티에 대한 상세정보를 조회한다.
//     * 
//     * @param cmmntyVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cmy/selectCmmntyInf.do")
//    public String selectCmmntyInf(@ModelAttribute("searchVO") CommunityVO cmmntyVO, ModelMap model, HttpServletRequest request) throws Exception {
//	Map<String, Object> map = cmmntyService.selectCommunityInf(cmmntyVO);
//	CommunityVO vo = (CommunityVO)map.get("cmmntyVO");
//	
//	//-----------------------
//	// 제공 URL 
//	//-----------------------
//	vo.setProvdUrl(request.getContextPath()+ "/cop/cmy/CmmntyMainPage.do?cmmntyId=" + vo.getCmmntyId());
//	////---------------------
//
//	model.addAttribute("cmmntyVO", vo);
//	model.addAttribute("result", map.get("resultList"));
//
//	return "egovframework/com/cop/cmy/EgovCmmntyInqire";
//    }
//
//    /**
//     * 커뮤니티 정보 수정을 위한 수정페이지로 이동한다.
//     * 
//     * @param cmmntyVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cmy/forUpdateCmmntyInf.do")
//    public String forUpdateCmmntyInf(@ModelAttribute("searchVO") CommunityVO cmmntyVO, Map<String, Object> commandMap, ModelMap model)
//	    throws Exception {
//
//	String cmmntyId = (String)commandMap.get("param_cmmntyId");
//	cmmntyVO.setCmmntyId(cmmntyId);
//	
//	Map<String, Object> map = cmmntyService.selectCommunityInf(cmmntyVO);
//	
//	model.addAttribute("cmmntyVO", (CommunityVO)map.get("cmmntyVO"));
//	model.addAttribute("cmmntyUser", (CommunityUser)map.get("cmmntyUser"));
//	model.addAttribute("result", map.get("resultList"));
//
//	return "egovframework/com/cop/cmy/EgovCmmntyUpdt";
//    }
//
//    /**
//     * 커뮤니티 정보를 수정한다.
//     * 
//     * @param cmmntyVO
//     * @param sessionVO
//     * @param status
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cmy/updtCmmntyInf.do")
//    public String updtCmmntyInf(@ModelAttribute("searchVO") CommunityVO cmmntyVO, @ModelAttribute("community") Community community,
//	    BindingResult bindingResult, SessionStatus status, ModelMap model) throws Exception {
//
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	beanValidator.validate(community, bindingResult);
//	if (bindingResult.hasErrors()) {
//
//	    Map<String, Object> map = cmmntyService.selectCommunityInf(cmmntyVO);
//	    CommunityVO vo = (CommunityVO)map.get("cmmntyVO");
//
//	    model.addAttribute("cmmntyVO", vo);
//	    model.addAttribute("result", map.get("resultList"));
//
//	    return "egovframework/com/cop/cmy/EgovCmmntyUpdt";
//	}
//
//	community.setLastUpdusrId(user.getUniqId());
//	
//	if (isAuthenticated) {
//	    if ("Y".equals(community.getUseAt())) {
//		cmmntyService.updateCommunityInf(community);
//	    } else {
//		cmmntyService.deleteCommunityInf(community);
//	    }
//
//	}
//
//	return "forward:/cop/cmy/selectCmmntyInfs.do";
//    }
//
//    /**
//     * 포트릿을 위한 커뮤니티 정보 목록 정보를 조회한다.
//     * 
//     * @param cmmntyVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cus/CmmntyListPortlet.do")
//    public String selectCmmntyListPortlet(@ModelAttribute("searchVO") CommunityVO cmmntyVO, ModelMap model) throws Exception {
//	List<CommunityVO> result = cmmntyService.selectCmmntyListPortlet(cmmntyVO);
//	
//	model.addAttribute("resultList", result);
//
//	return "egovframework/com/cop/cmy/EgovCmmntyListPortlet";
//    }
//
//    /**
//     * 커뮤니티 메인페이지를 조회한다.
//     * 
//     * @param cmmntyVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cmy/CmmntyMainPage.do")
//    public String selectCmmntyMainPage(@ModelAttribute("searchVO") CommunityVO cmmntyVO, ModelMap model) throws Exception {
//
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	cmmntyVO.setEmplyrId(user.getUniqId());
//
//	String tmplatCours = cmmntyService.selectCmmntyTemplat(cmmntyVO);
//	if ("".equals(tmplatCours) || tmplatCours == null) {
//	    tmplatCours = "egovframework/com/cop/tpl/EgovCmmntyBaseTmpl";
//	}
//	Map<String, Object> map = cmmntyService.selectCommunityInf(cmmntyVO);
//
//	//model.addAttribute("cmmntyVO", cmmntyVO);
//	model.addAttribute("cmmntyVO", (CommunityVO)map.get("cmmntyVO"));
//	model.addAttribute("cmmntyUser", (CommunityUser)map.get("cmmntyUser"));
//		
//	//--------------------------------
//	// 게시판 목록 정보 처리
//	//--------------------------------
//	BoardMasterVO bbsVo = new BoardMasterVO();
//	
//	bbsVo.setTrgetId(cmmntyVO.getCmmntyId());
//	
//	List<BoardMasterVO> bbsResult = bbsAttrbService.selectAllBdMstrByTrget(bbsVo);
//
//	model.addAttribute("bbsList", bbsResult);
//	////------------------------------
//
//	//--------------------------------
//	// 동호회 목록 정보
//	//--------------------------------
//	Club clubVo = new Club();
//	
//	clubVo.setCmmntyId(cmmntyVO.getCmmntyId());
//	
//	List<ClubVO> clubResult = clubService.selectClubListPortletByTrget(clubVo);
//
//	model.addAttribute("clubList", clubResult);
//	////------------------------------
//
//	if (isAuthenticated) {
//	    model.addAttribute("isAuthenticated", "Y");
//	} else {
//	    model.addAttribute("isAuthenticated", "N");
//	}
//	
//	return tmplatCours;
//    }
//	
//    /**
//     * 커뮤니티 메인페이지의 기본 내용(게시판 4개 표시) 조회한다.
//     * 
//     * @param cmmntyVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cmy/CmmntyMainContents.do")
//    public String selectCmmntyMainContents(@ModelAttribute("searchVO") CommunityVO cmmntyVO, ModelMap model) throws Exception {
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	@SuppressWarnings("unused")
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	cmmntyVO.setEmplyrId(user.getUniqId());
//
//	//--------------------------------
//	// 게시판 목록 정보 처리
//	//--------------------------------
//	BoardMasterVO bbsVo = new BoardMasterVO();
//	
//	bbsVo.setTrgetId(cmmntyVO.getCmmntyId());
//	
//	List<BoardMasterVO> bbsResult = bbsAttrbService.selectAllBdMstrByTrget(bbsVo);
//
//	// 방명록 제외 처리
//	for (int i = 0; i < bbsResult.size(); i++) {
//	    if ("BBST04".equals(bbsResult.get(i).getBbsTyCode())) {
//		bbsResult.remove(i);
//	    }
//	}
//
//	model.addAttribute("bbsList", bbsResult);
//
//	//--------------------------------
//	// 게시물 목록 정보 처리
//	//--------------------------------
//	BoardVO boardVo = null;
//	BoardMasterVO masterVo = null;
//	
//	ArrayList<Object> target = new ArrayList<Object>();	// Object => List<BoardVO>
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
//	return "egovframework/com/cop/tpl/EgovCmmntyBaseTmplContents";
//    }
//
//    /**
//     * 커뮤니티 사용신청을 등록한다.
//     * 
//     * @param cmmntyUser
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cmy/insertCmmntyUserBySelf.do")
//    public String insertCmmntyUserBySelf(@ModelAttribute("cmmntyUser") CommunityUser cmmntyUser, ModelMap model) throws Exception {
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	String retVal = "";
//
//	if ("".equals(cmmntyUser.getMngrAt())) {
//	    cmmntyUser.setMngrAt("N");
//	}
//	cmmntyUser.setUseAt("Y");
//	cmmntyUser.setFrstRegisterId(user.getUniqId());
//	cmmntyUser.setEmplyrId(user.getUniqId());
//
//	if (isAuthenticated) {
//
//	    //---------------------------------------------
//	    // 승인요청 처리
//	    //---------------------------------------------
//	    //retVal = cmmntyService.insertCommunityUserInf(cmmntyUser);
//	    retVal = cmmntyService.checkCommunityUserInf(cmmntyUser);
//
//	    if (!retVal.equals("EXIST")) {
//		
//		CommunityVO cmmntyVO = new CommunityVO();
//		
//		cmmntyVO.setCmmntyId(cmmntyUser.getCmmntyId());
//		
//		CommunityUser manager = cmmntyService.selectManager(cmmntyVO);
//
//		ConfirmHistory history = new ConfirmHistory();
//
//		history.setConfmRqesterId(user.getUniqId()); 		// 요청자 ID
//		history.setConfmerId(manager.getEmplyrId()); 		// 관리자
//		history.setConfmTyCode("CF11"); 			// 커뮤니티사용자등록
//		history.setConfmSttusCode("AP01"); 			// 승인요청
//		history.setOpertTyCode("WC01"); 			// 회원가입
//		history.setOpertId(""); 				// 작업자 ID
//		history.setTrgetJobTyCode("CMY"); 			// 대상작업구분
//		history.setTrgetJobId(cmmntyUser.getCmmntyId());	// 대상작업 ID
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
//	return "egovframework/com/cop/cmy/EgovCmmntyMsg";
//    }

//
//    /**
//     * 커뮤니티 탈퇴신청을 처리한다.
//     * 
//     * @param cmmntyUser
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cmy/deleteCmmntyUserBySelf.do")
//    public String deleteCmmntyUserBySelf(@ModelAttribute("cmmntyUser") CommunityUser cmmntyUser, ModelMap model) throws Exception {
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	String retVal = "DEL_REQ_SUCCESS";
//
//	cmmntyUser.setUseAt("N");
//	cmmntyUser.setLastUpdusrId(user.getUniqId());
//	//cmmntyUser.setEmplyrId(user.getUniqId());		//커뮤니티 탈퇴 요청시 승인자를 선택하므로 탈퇴 승인자가 자신이 될 수 없음(2011.9.7 수정분)
//	cmmntyUser.setSecsnDe(EgovDateUtil.getToday());
//
//	if (isAuthenticated) {
//	    ConfirmHistory history = new ConfirmHistory();
//	    
//	    history.setConfmRqesterId(user.getUniqId());
//	    history.setConfmerId(cmmntyUser.getEmplyrId());
//	    history.setConfmTyCode("CF12"); //006
//	    history.setConfmSttusCode("AP01"); //007
//	    history.setOpertTyCode("WC03");
//	    history.setOpertId("");
//	    history.setTrgetJobTyCode("CMY");
//	    history.setTrgetJobId(cmmntyUser.getCmmntyId());
//
//	    confmService.insertConfirmRequest(history);
//
//	    //cmmntyService.deleteCommunityUserInf(cmmntyUser);
//	}
//
//	model.addAttribute("returnMsg", retVal);
//	
//	return "egovframework/com/cop/cmy/EgovCmmntyMsg";
//    }
//    
//    /**
//     * 미리보기 커뮤니티 메인페이지를 조회한다.
//     * 
//     * @param cmmntyVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cmy/previewCmmntyMainPage.do")
//    public String previewCmmntyMainPage(@ModelAttribute("searchVO") CommunityVO cmmntyVO, ModelMap model) throws Exception {
//
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	cmmntyVO.setEmplyrId(user.getUniqId());
//
//	String tmplatCours = cmmntyVO.getSearchWrd();
//	
//	CommunityVO vo = new CommunityVO();
//	
//	vo.setCmmntyNm("미리보기 커뮤니티");
//	vo.setCmmntyIntrcn("미리보기를 위한 커뮤니티입니다.");
//	vo.setUseAt("Y");
//	vo.setFrstRegisterId(user.getUniqId());	// 본인
//	
//	CommunityUser cmmntyUser = new CommunityUser();
//	
//	cmmntyUser.setEmplyrId(user.getUniqId());
//	cmmntyUser.setEmplyrNm("관리자");
//
//	model.addAttribute("cmmntyVO", vo);
//	model.addAttribute("cmmntyUser", cmmntyUser);
//		
//	//--------------------------------
//	// 게시판 목록 정보 처리
//	//--------------------------------
//	List<BoardMasterVO> bbsResult = new ArrayList<BoardMasterVO>();
//	
//	BoardMasterVO target = null;
//	
//	target = new BoardMasterVO();
//	target.setBbsNm("방명록");
//	bbsResult.add(target);
//	
//	target = new BoardMasterVO();
//	target.setBbsNm("공지게시판");
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
//	target = new BoardMasterVO();
//	target.setBbsNm("자료실");
//	bbsResult.add(target);
//	
//	model.addAttribute("bbsList", bbsResult);
//	////------------------------------
//
//	//--------------------------------
//	// 동호회 목록 정보
//	//--------------------------------
//	List<ClubVO> clubResult = new ArrayList<ClubVO>();
//	
//	ClubVO club = new ClubVO();
//	club.setClbNm("미리보기 동호회");
//	clubResult.add(club);
//
//	model.addAttribute("clubList", clubResult);
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
//     * 커뮤니티 메인페이지의 기본 내용(게시판 4개 표시) 조회한다.
//     * 
//     * @param cmmntyVO
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/cmy/previewCmmntyMainContents.do")
//    public String previewCmmntyMainContents(@ModelAttribute("searchVO") CommunityVO cmmntyVO, ModelMap model) throws Exception {
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	@SuppressWarnings("unused")
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	cmmntyVO.setEmplyrId(user.getUniqId());
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
//	ArrayList<Object> target = new ArrayList<Object>();	// Object => List<BoardVO>
//	for (int i = 0; i < bbsResult.size() && i < 4; i++) {
//
//	    target.add(null);
//	}
//
//	model.addAttribute("articleList", target);
//	
//	model.addAttribute("preview", "true");
//
//	return "egovframework/com/cop/tpl/EgovCmmntyBaseTmplContents";
//    }
}
