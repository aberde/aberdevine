package whoya.egovframework.com.cop.scp.web;

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
import whoya.egovframework.com.cop.scp.service.WhoyaEgovBBSScrapService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.bbs.service.ScrapVO;

/**
 * 스크랩관리 서비스 컨트롤러 클래스
 */
@Controller
public class WhoyaEgovBBSScrapController {
	
	@Resource(name="WhoyaEgovBBSScrapService")
	protected WhoyaEgovBBSScrapService bbsScrapService;
	
	/**
	 * 스크랩 목록 화면
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/whoya/cop/scp/selectScrapList.do")
	public ModelAndView selectScrapList() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/egovframework/com/cop/scp/EgovScrapList");
		return mav;
	}
	
	/**
	 * 스크랩관리 목록 조회를 제공한다.
	 *
	 * @param scrapVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/whoya/cop/scp/selectScrapJSONList.do")
	public @ResponseBody JSONObject selectScrapJSONList(@ModelAttribute("searchVO") ScrapVO scrapVO, ModelMap model) throws Exception {
		JSONObject resultList = new JSONObject();
		
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			
			scrapVO.setUniqId(user.getUniqId());
			scrapVO.setPageIndex(0);
			Map<String, Object> map = bbsScrapService.selectScrapList(scrapVO);
			List<ScrapVO> voList = (List<ScrapVO>)map.get("resultList");
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(voList));
			
			// 번호 컬럼 추가.
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap wmap = list.getMap(i);
				wmap.put("no", i + 1);
			}

			// 번호,스크랩명,등록자,등록일
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,scrapNm,frstRegisterNm,frstRegisterPnttm"));
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
//
//
//
//    @Resource(name = "EgovBBSManageService")
//    private EgovBBSManageService bbsMngService;
//
//    @Resource(name="propertiesService")
//    protected EgovPropertyService propertyService;
//
//    @Resource(name="egovMessageSource")
//    EgovMessageSource egovMessageSource;
//
//    @Autowired
//    private DefaultBeanValidator beanValidator;
//
//    //Logger log = Logger.getLogger(this.getClass());
//
//    /**
//     * 스크랩에 대한 상세정보를 조회한다.
//     *
//     * @param scrapVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/scp/selectScrap.do")
//    public String selectScrap(@ModelAttribute("searchVO") ScrapVO scrapVO, ModelMap model) throws Exception {
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//
//	Scrap scrap = bbsScrapService.selectScrap(scrapVO);
//
//	model.addAttribute("sessionUniqId", user.getUniqId());
//	model.addAttribute("scrap", scrap);
//
//	//-------------------------------------
//	//게시판 내용 취득
//	//-------------------------------------
//	scrapVO.setNttId(scrap.getNttId());
//	scrapVO.setBbsId(scrap.getBbsId());
//
//	BoardVO vo = getBoardInfo(scrapVO);
//
//	model.addAttribute("board", vo);
//	////-----------------------------------
//
//	return "egovframework/com/cop/scp/EgovScrapDetail";
//    }
//
//    /**
//     * 스크랩 등록을 위한 등록 페이지로 이동한다.
//     *
//     * @param scrapVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/scp/addScrap.do")
//    public String addScrap(@ModelAttribute("searchVO") ScrapVO scrapVO, ModelMap model) throws Exception {
//
//	Scrap scrap = new Scrap();
//
//	model.addAttribute("scrap", scrap);
//
//	//-------------------------------------
//	//게시판 내용 취득
//	//-------------------------------------
//	BoardVO vo = getBoardInfo(scrapVO);
//
//	model.addAttribute("board", vo);
//	////-----------------------------------
//
//	return "egovframework/com/cop/scp/EgovScrapRegist";
//    }
//
//    /**
//     * 스크랩된 원 게시판 내용을 조회한다.
//     *
//     * @param scrapVO
//     * @return
//     * @throws Exception
//     */
//    private BoardVO getBoardInfo(ScrapVO scrapVO) throws Exception {
//	BoardVO boardVO = new BoardVO();
//
//	boardVO.setBbsId(scrapVO.getBbsId());
//	boardVO.setNttId(scrapVO.getNttId());
//
//	// 조회수 증가 여부 지정
//	boardVO.setPlusCount(false);
//
//	BoardVO vo = bbsMngService.selectBoardArticle(boardVO);
//	return vo;
//    }
//
//    /**
//     * 스크랩을 등록한다.
//     *
//     * @param scrapVO
//     * @param scrap
//     * @param bindingResult
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/scp/insertScrap.do")
//    public String insertScrap(@ModelAttribute("searchVO") ScrapVO scrapVO, @ModelAttribute("scrap") Scrap scrap,
//	    BindingResult bindingResult, ModelMap model) throws Exception {
//
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	beanValidator.validate(scrap, bindingResult);
//	if (bindingResult.hasErrors()) {
//	    //-------------------------------------
//	    //게시판 내용 취득
//	    //-------------------------------------
//	    BoardVO vo = getBoardInfo(scrapVO);
//
//	    model.addAttribute("board", vo);
//	    ////-----------------------------------
//
//	    return "egovframework/com/cop/scp/EgovScrapRegist";
//	}
//
//	if (isAuthenticated) {
//	    scrap.setFrstRegisterId(user.getUniqId());
//
//	    bbsScrapService.insertScrap(scrap);
//	}
//
//	return "forward:/cop/scp/selectScrapList.do";
//    }
//
//    /**
//     * 스크랩을 삭제한다.
//     *
//     * @param ScrapVO
//     * @param Scrap
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/scp/deleteScrap.do")
//    public String deleteScrap(@ModelAttribute("searchVO") ScrapVO scrapVO, @ModelAttribute("Scrap") Scrap scrap, ModelMap model) throws Exception {
//	@SuppressWarnings("unused")
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	if (isAuthenticated) {
//	    bbsScrapService.deleteScrap(scrapVO);
//	}
//
//	return "forward:/cop/scp/selectScrapList.do";
//    }
//
//    /**
//     * 스크랩 수정 페이지로 이동한다.
//     *
//     * @param scrapVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/scp/forUpdateScrap.do")
//    public String forUpdateScrap(@ModelAttribute("searchVO") ScrapVO scrapVO, @ModelAttribute("scrap") Scrap scrap, ModelMap model) throws Exception {
//	Scrap vo = bbsScrapService.selectScrap(scrapVO);
//
//	model.addAttribute("scrap", vo);
//
//	//-------------------------------------
//	//게시판 내용 취득
//	//-------------------------------------
//	BoardVO board = getBoardInfo(scrapVO);
//
//	model.addAttribute("board", board);
//	////-----------------------------------
//
//	return "egovframework/com/cop/scp/EgovScrapUpdt";
//    }
//
//    /**
//     * 스크랩을 수정한다.
//     *
//     * @param ScrapVO
//     * @param Scrap
//     * @param bindingResult
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/scp/updateScrap.do")
//    public String updateScrap(@ModelAttribute("searchVO") ScrapVO scrapVO, @ModelAttribute("Scrap") Scrap scrap,
//	    BindingResult bindingResult, ModelMap model) throws Exception {
//
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	beanValidator.validate(scrap, bindingResult);
//	if (bindingResult.hasErrors()) {
//
//	    Scrap vo = bbsScrapService.selectScrap(scrapVO);
//
//	    model.addAttribute("result", vo);
//
//	    return "egovframework/com/cop/scp/EgovScrapUpdt";
//	}
//
//	if (isAuthenticated) {
//	    scrap.setLastUpdusrId(user.getUniqId());
//
//	    bbsScrapService.updateScrap(scrap);
//	}
//
//	return "forward:/cop/scp/selectScrapList.do";
//    }
//
//    /**
//     * 마이페이지용 스크랩관리 목록 조회를 제공한다.
//     *
//     * @param scrapVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/scp/selectScrapMainList.do")
//    public String selectScrapMainList(@ModelAttribute("searchVO") ScrapVO scrapVO, ModelMap model) throws Exception {
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//
//	scrapVO.setUniqId(user.getUniqId());
//
//	scrapVO.setPageUnit(propertyService.getInt("pageUnit"));
//	scrapVO.setPageSize(propertyService.getInt("pageSize"));
//
//	scrapVO.setFirstIndex(0);
//	scrapVO.setRecordCountPerPage(5);
//
//	Map<String, Object> map = bbsScrapService.selectScrapList(scrapVO);
//
//	model.addAttribute("resultList", map.get("resultList"));
//	model.addAttribute("resultCnt", map.get("resultCnt"));
//
//	return "egovframework/com/cop/scp/EgovScrapMainList";
//    }
}
