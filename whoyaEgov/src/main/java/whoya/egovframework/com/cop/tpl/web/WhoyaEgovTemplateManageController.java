package whoya.egovframework.com.cop.tpl.web;

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
import whoya.egovframework.com.cop.tpl.service.WhoyaEgovTemplateManageService;
import egovframework.com.cop.tpl.service.TemplateInfVO;

/**
 * 템플릿 관리를 위한 컨트롤러 클래스
 */

@Controller
public class WhoyaEgovTemplateManageController {
	
	@Resource(name = "WhoyaEgovTemplateManageService")
	private WhoyaEgovTemplateManageService tmplatService;	
	/**
	 * 팝업을 위한 템플릿 목록을 조회한다.
	 * 
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/whoya/cop/tpl/selectTemplateInfsPop.do")
	public @ResponseBody JSONObject selectTemplateInfsPop(@ModelAttribute("searchVO") TemplateInfVO tmplatInfVO, Map<String, Object> commandMap, ModelMap model) throws Exception {
		JSONObject resultList = new JSONObject();
		
		try {
			String typeFlag = (String)commandMap.get("typeFlag");
			
			if ("CLB".equals(typeFlag)) {
				tmplatInfVO.setTypeFlag(typeFlag);
				tmplatInfVO.setTmplatSeCode("TMPT03");
			} else if ("CMY".equals(typeFlag)) {
				tmplatInfVO.setTypeFlag(typeFlag);
				tmplatInfVO.setTmplatSeCode("TMPT02");
			} else {
				tmplatInfVO.setTypeFlag(typeFlag);
				tmplatInfVO.setTmplatSeCode("TMPT01");
			}
			
			tmplatInfVO.setPageIndex(0);
			Map<String, Object> map = tmplatService.selectTemplateInfs(tmplatInfVO);
			List<TemplateInfVO> voList = (List<TemplateInfVO>)map.get("resultList");
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(voList));
			
			// 번호 컬럼 추가.
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap wmap = list.getMap(i);
				wmap.put("no", i + 1);
				wmap.put("selectLink", "../../../images/egovframework/com/cmm/icon/search.gif^선택^javascript:tmplatSelect(\"" + wmap.get("tmplatId") + "\", \"" + wmap.get("tmplatNm") + "\");^_self");
			}

			// 번호,템플릿명,템플릿구분,템플릿경로,사용여부,등록일자,선택
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,tmplatNm,tmplatSeCodeNm,tmplatCours,useAt,frstRegisterPnttm,selectLink"));
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
//    @Resource(name = "EgovCmmUseService")
//    private EgovCmmUseService cmmUseService;
//
//    @Resource(name = "propertiesService")
//    protected EgovPropertyService propertyService;
//
//    @Autowired
//    private DefaultBeanValidator beanValidator;
//
//    //Logger log = Logger.getLogger(this.getClass());
//
//    /**
//     * 템플릿 목록을 조회한다.
//     * 
//     * @param searchVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @IncludedInfo(name="템플릿관리", order = 200 ,gid = 40)
//    @RequestMapping("/cop/tpl/selectTemplateInfs.do")
//    public String selectTemplateInfs(@ModelAttribute("searchVO") TemplateInfVO tmplatInfVO, ModelMap model) throws Exception {
//	tmplatInfVO.setPageUnit(propertyService.getInt("pageUnit"));
//	tmplatInfVO.setPageSize(propertyService.getInt("pageSize"));
//
//	PaginationInfo paginationInfo = new PaginationInfo();
//	
//	paginationInfo.setCurrentPageNo(tmplatInfVO.getPageIndex());
//	paginationInfo.setRecordCountPerPage(tmplatInfVO.getPageUnit());
//	paginationInfo.setPageSize(tmplatInfVO.getPageSize());
//
//	tmplatInfVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
//	tmplatInfVO.setLastIndex(paginationInfo.getLastRecordIndex());
//	tmplatInfVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
//
//	Map<String, Object> map = tmplatService.selectTemplateInfs(tmplatInfVO);
//	int totCnt = Integer.parseInt((String)map.get("resultCnt"));
//	
//	paginationInfo.setTotalRecordCount(totCnt);
//
//	model.addAttribute("resultList", map.get("resultList"));
//	model.addAttribute("resultCnt", map.get("resultCnt"));
//	model.addAttribute("paginationInfo", paginationInfo);
//
//	return "egovframework/com/cop/tpl/EgovTemplateList";
//    }
//
//    /**
//     * 템플릿에 대한 상세정보를 조회한다.
//     * 
//     * @param searchVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @SuppressWarnings("unchecked")
//    @RequestMapping("/cop/tpl/selectTemplateInf.do")
//    public String selectTemplateInf(@ModelAttribute("searchVO") TemplateInfVO tmplatInfVO, ModelMap model) throws Exception {
//
//	ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
//	
//	codeVO.setCodeId("COM005");
//	List result = cmmUseService.selectCmmCodeDetail(codeVO);
//
//	TemplateInfVO vo = tmplatService.selectTemplateInf(tmplatInfVO);
//
//	model.addAttribute("TemplateInfVO", vo);
//	model.addAttribute("resultList", result);
//
//	return "egovframework/com/cop/tpl/EgovTemplateUpdt";
//    }
//
//    /**
//     * 템플릿 정보를 등록한다.
//     * 
//     * @param searchVO
//     * @param tmplatInfo
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @SuppressWarnings("unchecked")
//    @RequestMapping("/cop/tpl/insertTemplateInf.do")
//    public String insertTemplateInf(@ModelAttribute("searchVO") TemplateInfVO searchVO, @ModelAttribute("templateInf") TemplateInf templateInf,
//	    BindingResult bindingResult, SessionStatus status, ModelMap model) throws Exception {
//
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	beanValidator.validate(templateInf, bindingResult);
//
//	if (bindingResult.hasErrors()) {
//	    ComDefaultCodeVO vo = new ComDefaultCodeVO();
//	    
//	    vo.setCodeId("COM005");
//	    
//	    List result = cmmUseService.selectCmmCodeDetail(vo);
//	    
//	    model.addAttribute("resultList", result);
//
//	    return "egovframework/com/cop/tpl/EgovTemplateRegist";
//	}
//
//	templateInf.setFrstRegisterId(user.getUniqId());
//	
//	if (isAuthenticated) {
//	    tmplatService.insertTemplateInf(templateInf);
//	}
//
//	return "forward:/cop/tpl/selectTemplateInfs.do";
//    }
//
//    /**
//     * 템플릿 등록을 위한 등록페이지로 이동한다.
//     * 
//     * @param searchVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @SuppressWarnings("unchecked")
//    @RequestMapping("/cop/tpl/addTemplateInf.do")
//    public String addTemplateInf(@ModelAttribute("searchVO") TemplateInfVO searchVO, ModelMap model) throws Exception {
//	ComDefaultCodeVO vo = new ComDefaultCodeVO();
//	
//	vo.setCodeId("COM005");
//	
//	List result = cmmUseService.selectCmmCodeDetail(vo);
//	
//	model.addAttribute("resultList", result);
//
//	return "egovframework/com/cop/tpl/EgovTemplateRegist";
//    }
//
//    /**
//     * 템플릿 정보를 수정한다.
//     * 
//     * @param searchVO
//     * @param tmplatInfo
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @SuppressWarnings("unchecked")
//    @RequestMapping("/cop/tpl/updateTemplateInf.do")
//    public String updateTemplateInf(@ModelAttribute("searchVO") TemplateInfVO tmplatInfVO, @ModelAttribute("templateInf") TemplateInf templateInf,
//	    BindingResult bindingResult, SessionStatus status, ModelMap model) throws Exception {
//
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	beanValidator.validate(templateInf, bindingResult);
//
//	if (bindingResult.hasErrors()) {
//	    ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
//	    
//	    codeVO.setCodeId("COM005");
//	    
//	    List result = cmmUseService.selectCmmCodeDetail(codeVO);
//
//	    TemplateInfVO vo = tmplatService.selectTemplateInf(tmplatInfVO);
//
//	    model.addAttribute("TemplateInfVO", vo);
//	    model.addAttribute("resultList", result);
//
//	    return "egovframework/com/cop/tpl/EgovTemplateUpdt";
//	}
//
//	templateInf.setLastUpdusrId(user.getUniqId());
//	
//	if (isAuthenticated) {
//	    tmplatService.updateTemplateInf(templateInf);
//	}
//
//	return "forward:/cop/tpl/selectTemplateInfs.do";
//    }
//
//    /**
//     * 템플릿 정보를 삭제한다.
//     * 
//     * @param searchVO
//     * @param tmplatInfo
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cop/bbs/deleteTemplateInf.do")
//    public String deleteTemplateInf(@ModelAttribute("searchVO") TemplateInfVO searchVO, @ModelAttribute("tmplatInf") TemplateInf tmplatInf,
//	    SessionStatus status, ModelMap model) throws Exception {
//
//	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	tmplatInf.setLastUpdusrId(user.getUniqId());
//	
//	if (isAuthenticated) {
//	    tmplatService.deleteTemplateInf(tmplatInf);
//	}
//
//	return "forward:/cop/tpl/selectTemplateInfs.do";
//    }
}
