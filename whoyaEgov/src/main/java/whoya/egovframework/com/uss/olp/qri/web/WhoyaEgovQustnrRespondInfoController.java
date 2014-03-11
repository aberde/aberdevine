package whoya.egovframework.com.uss.olp.qri.web;

import java.util.HashMap;
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

import whoya.whoyaLib;
import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.common.Common;
import whoya.common.commonReturn;
import whoya.egovframework.com.uss.olp.qri.service.WhoyaEgovQustnrRespondInfoService;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 설문조사 Controller Class 구현
 */
@Controller
public class WhoyaEgovQustnrRespondInfoController {
    
    @Resource(name = "whoyaEgovQustnrRespondInfoService")
    private WhoyaEgovQustnrRespondInfoService egovQustnrRespondInfoService;
    
    /**
     * 설문조사 화면
     * @return ModelAndView
     */
    @RequestMapping(value="/whoya/uss/olp/qnn/EgovQustnrRespondInfoManageList.do")
    public ModelAndView egovQustnrRespondInfoManageList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("whoya/egovframework/com/uss/olp/qnn/EgovQustnrRespondInfoManageList");
        return mav;
    }
    
    /**
     * 설문조사(설문등록) 목록을 조회한다.
     * @param searchVO
     * @param request
     * @param response
     * @param commandMap
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qnn/EgovQustnrRespondInfoManageListJSON.do")
    public @ResponseBody JSONObject egovQustnrRespondInfoManageListJSON(@ModelAttribute("searchVO") ComDefaultVO searchVO, HttpServletRequest request, HttpServletResponse response, Map commandMap, ModelMap model) throws Exception {
        JSONObject resultList = new JSONObject();
        
        try {
            searchVO.setPageIndex(0);
            // EgovMap 형식으로 받음.
            List sampleList = egovQustnrRespondInfoService.selectQustnrRespondInfoManageList(searchVO);
            whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(sampleList));
            
            // 순번 컬럼 추가.
            // TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
            for ( int i = 0 ; i < list.size(); i++ ) {
                whoyaMap wmap = list.getMap(i);
                wmap.put("no", i + 1);
                wmap.put("qestnrDe", (String)wmap.get("qestnrBeginDe") + " ~ " + (String)wmap.get("qestnrEndDe"));
                wmap.put("qustnrRespondStatistics", "../../../images/egovframework/com/cmm/icon/search.gif^선택^javascript:whoya.common.qustnrRespondStatisticsSelect(\"" + wmap.get("qestnrId") + "\", \"" + wmap.get("qestnrTmplatId") + "\");^_self");
            }
                        
            // 순번,설문제목,설문기간,통계,등록자,등록일자,설문ID,설문템플릿ID
            resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,qestnrSj,qestnrDe,qustnrRespondStatistics,frstRegisterNm,frstRegisterPnttm,qestnrId,qestnrTmplatId"));
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
     * 설문조사 전체 통계를 조회한다.
     * @param searchVO
     * @param request
     * @param commandMap
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qnn/EgovQustnrRespondInfoManageStatistics.do")
    public @ResponseBody Map egovQustnrRespondInfoManageStatistics(@ModelAttribute("searchVO") ComDefaultVO searchVO, HttpServletRequest request, Map commandMap, ModelMap model) throws Exception {
        HashMap map = new HashMap();
        
        try {
            // EgovMap 형식으로 받음.
            List sampleList = egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqestnrinfo(commandMap);
            
            // 순번 컬럼 추가.
            // TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
            for ( int i = 0 ; i < sampleList.size(); i++ ) {
                EgovMap wmap = (EgovMap)sampleList.get(i);
                wmap.put("qestnrDe", (String)wmap.get("qestnrBeginDe") + " ~ " + (String)wmap.get("qestnrEndDe"));
            }
            
            //설문정보
            map.put("Comtnqestnrinfo",  sampleList);
            //문항정보
            map.put("Comtnqustnrqesitm",  egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqustnrqesitm(commandMap));
            //항목정보
            map.put("Comtnqustnriem",  egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqustnriem(commandMap));
            //설문템플릿ID 설정
            map.put("qestnrTmplatId",  commandMap.get("qestnrTmplatId") == null ? "" : (String)commandMap.get("qestnrTmplatId") );
            //설문지정보ID 설정
            map.put("qestnrId",  commandMap.get("qestnrId") == null ? "" : (String)commandMap.get("qestnrId"));
            //객관식통계 답안
            map.put("qestnrStatistic1", egovQustnrRespondInfoService.selectQustnrRespondInfoManageStatistics1(commandMap));
            //주관식통계 답안
            map.put("qestnrStatistic2", egovQustnrRespondInfoService.selectQustnrRespondInfoManageStatistics2(commandMap));
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        return map;
    }
//
//
//
//	protected Log log = LogFactory.getLog(this.getClass());
//
//	@Autowired
//	private DefaultBeanValidator beanValidator;
//
//	/** EgovMessageSource */
//    @Resource(name="egovMessageSource")
//    EgovMessageSource egovMessageSource;
//
//	@Resource(name = "egovQustnrRespondManageService")
//	private EgovQustnrRespondManageService egovQustnrRespondManageService;
//
//    /** EgovPropertyService */
//    @Resource(name = "propertiesService")
//    protected EgovPropertyService propertiesService;
//
//	@Resource(name="EgovCmmUseService")
//	private EgovCmmUseService cmmUseService;
//
//
//	/**
//	 * 설문템플릿을 적용한다.
//	 * @param searchVO
//	 * @param request
//	 * @param commandMap
//	 * @param model
//	 * @return "egovframework/com/uss/olp/template/template"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/uss/olp/qri/template/template")
//	public String egovQustnrRespondInfoManageTemplate(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO,
//			HttpServletRequest request,
//			Map commandMap,
//    		ModelMap model)
//    throws Exception {
//
//
//		 String sTemplateUrl = (String)commandMap.get("templateUrl");
//
//		 log.debug("qestnrId=>" + commandMap.get("qestnrId"));
//		 log.debug("qestnrTmplatId=>" + commandMap.get("qestnrTmplatId"));
//		 log.debug("templateUrl=>" + commandMap.get("templateUrl"));
//
// 		 //설문템플릿정보
//		 model.addAttribute("QustnrTmplatManage",  (List)egovQustnrRespondInfoService.selectQustnrTmplatManage(commandMap));
//
//    	 //설문정보
//    	 model.addAttribute("Comtnqestnrinfo",  (List)egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqestnrinfo(commandMap));
//    	 //문항정보
//    	 model.addAttribute("Comtnqustnrqesitm",  (List)egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqustnrqesitm(commandMap));
//    	 //항목정보
//    	 model.addAttribute("Comtnqustnriem",  (List)egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqustnriem(commandMap));
//    	 //설문템플릿ID 설정
//    	 model.addAttribute("qestnrTmplatId",  commandMap.get("qestnrTmplatId") == null ? "" : (String)commandMap.get("qestnrTmplatId") );
//       	 //설문지정보ID 설정
//    	 model.addAttribute("qestnrId",  commandMap.get("qestnrId") == null ? "" : (String)commandMap.get("qestnrId"));
//
//         //객관식통계 답안
//    	 model.addAttribute("qestnrStatistic1", (List)egovQustnrRespondInfoService.selectQustnrRespondInfoManageStatistics1(commandMap));
//
//         //주관식통계 답안
//    	 model.addAttribute("qestnrStatistic2", (List)egovQustnrRespondInfoService.selectQustnrRespondInfoManageStatistics2(commandMap));
//
//    	 //이전 주소
//    	 model.addAttribute("returnUrl", request.getHeader("REFERER"));
//
//
//
//		return sTemplateUrl;
//	}
//
//	/**
//	 * 설문조사(설문등록)를 등록한다.
//	 * @param searchVO
//	 * @param commandMap
//	 * @param model
//	 * @return "egovframework/com/uss/olp/qnn/EgovQustnrRespondInfoManageRegist"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/uss/olp/qnn/EgovQustnrRespondInfoManageRegist.do")
//	public String egovQustnrRespondInfoManageRegist(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO,
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
//		if(loginVO == null){ loginVO = new LoginVO();}
//
//		String sLocationUrl = "egovframework/com/uss/olp/qnn/EgovQustnrRespondInfoManageRegist";
//
//		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
//		log.info("cmd =>" + sCmd);
//
//
//     	//성별코드조회
//    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
//    	voComCode.setCodeId("COM014");
//    	List listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
//    	model.addAttribute("comCode014", listComCode);
//
//    	//직업코드조회
//    	voComCode.setCodeId("COM034");
//    	listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
//    	model.addAttribute("comCode034", listComCode);
//
//        if(sCmd.equals("save")){
//
//    		//설문조사 처리 START
//    		String sKey ="";
//    		String sVal ="";
//           	for(Object key:commandMap.keySet()){
//
//           		sKey = key.toString();
//
//           		//설문문항정보 추출
//           		if(sKey.length() > 6 && sKey.substring(0, 6).equals("QQESTN")){
//
//
//           			//설문조사 등록
//	           		//객관식 답안 처리
//	           		if( ((String)commandMap.get("TY_"+key)).equals("1") ){
//
//           				if( commandMap.get(key) instanceof String){
//	           				sVal = (String)commandMap.get(key);
//
//		           			QustnrRespondInfoVO qustnrRespondInfoVO = new QustnrRespondInfoVO();
//
//		           			qustnrRespondInfoVO.setQestnrTmplatId((String)commandMap.get("qestnrTmplatId"));
//		           			qustnrRespondInfoVO.setQestnrId((String)commandMap.get("qestnrId"));
//		           			qustnrRespondInfoVO.setQestnrQesitmId(sKey);
//		           			qustnrRespondInfoVO.setQustnrIemId(sVal);
//
//		           			qustnrRespondInfoVO.setRespondAnswerCn("");
//
//		           			qustnrRespondInfoVO.setRespondNm((String)commandMap.get("respondNm"));
//		           			qustnrRespondInfoVO.setEtcAnswerCn((String)commandMap.get("ETC_" + sVal));
//
//
//		            		qustnrRespondInfoVO.setFrstRegisterId((String)loginVO.getUniqId());
//		            		qustnrRespondInfoVO.setLastUpdusrId((String)loginVO.getUniqId());
//
//		           			egovQustnrRespondInfoService.insertQustnrRespondInfo(qustnrRespondInfoVO);
//           				}else{
//	        				String[] arrVal = (String[]) commandMap.get(key);
//	        				for(int g=0; g < arrVal.length; g++ ){
//	        					//("QQESTN arr :" + arrVal[g]);
//			           			QustnrRespondInfoVO qustnrRespondInfoVO = new QustnrRespondInfoVO();
//
//			           			qustnrRespondInfoVO.setQestnrTmplatId((String)commandMap.get("qestnrTmplatId"));
//			           			qustnrRespondInfoVO.setQestnrId((String)commandMap.get("qestnrId"));
//			           			qustnrRespondInfoVO.setQestnrQesitmId(sKey);
//			           			qustnrRespondInfoVO.setQustnrIemId(arrVal[g]);
//
//			           			qustnrRespondInfoVO.setRespondAnswerCn("");
//
//			           			qustnrRespondInfoVO.setRespondNm((String)commandMap.get("respondNm"));
//			           			qustnrRespondInfoVO.setEtcAnswerCn((String)commandMap.get("ETC_" + arrVal[g]));
//
//
//			            		qustnrRespondInfoVO.setFrstRegisterId((String)loginVO.getUniqId());
//			            		qustnrRespondInfoVO.setLastUpdusrId((String)loginVO.getUniqId());
//
//			           			egovQustnrRespondInfoService.insertQustnrRespondInfo(qustnrRespondInfoVO);
//	        				}
//           				}
//
//
//	           		//주관식 답안 처리
//	           		}else if( ((String)commandMap.get("TY_"+key)).equals("2") ){
//	           			QustnrRespondInfoVO qustnrRespondInfoVO = new QustnrRespondInfoVO();
//
//	           			qustnrRespondInfoVO.setQestnrTmplatId((String)commandMap.get("qestnrTmplatId"));
//	           			qustnrRespondInfoVO.setQestnrId((String)commandMap.get("qestnrId"));
//	           			qustnrRespondInfoVO.setQestnrQesitmId(sKey);
//	           			qustnrRespondInfoVO.setQustnrIemId(null);
//
//	           			qustnrRespondInfoVO.setRespondAnswerCn((String)commandMap.get(sKey));
//
//	           			qustnrRespondInfoVO.setRespondNm((String)commandMap.get("respondNm"));
//	           			qustnrRespondInfoVO.setEtcAnswerCn(null);
//
//	            		qustnrRespondInfoVO.setFrstRegisterId((String)loginVO.getUniqId());
//	            		qustnrRespondInfoVO.setLastUpdusrId((String)loginVO.getUniqId());
//
//
//	           			egovQustnrRespondInfoService.insertQustnrRespondInfo(qustnrRespondInfoVO);
//	           		}
//
//
//           		}
//        	}
//
//       		//설문응답자 처리
//       		QustnrRespondManageVO qustnrRespondManageVO = new QustnrRespondManageVO();
//
//       		qustnrRespondManageVO.setQestnrId((String)commandMap.get("qestnrId"));
//       		qustnrRespondManageVO.setQestnrTmplatId((String)commandMap.get("qestnrTmplatId"));
//
//       		qustnrRespondManageVO.setSexdstnCode((String)commandMap.get("sexdstnCode"));
//       		qustnrRespondManageVO.setOccpTyCode((String)commandMap.get("occpTyCode"));
//       		qustnrRespondManageVO.setBrth((String)commandMap.get("brth"));
//       		qustnrRespondManageVO.setRespondNm((String)commandMap.get("respondNm"));
//
//       		qustnrRespondManageVO.setFrstRegisterId((String)loginVO.getUniqId());
//       		qustnrRespondManageVO.setLastUpdusrId((String)loginVO.getUniqId());
//       		egovQustnrRespondManageService.insertQustnrRespondManage(qustnrRespondManageVO);
//
//           	String ReusltScript = "";
//
//           	ReusltScript += "<script type='text/javaScript' language='javascript'>";
//        	ReusltScript += "alert(' 설문참여에 응해주셔서 감사합니다!  ');";
//           	ReusltScript += "</script>";
//
//           	model.addAttribute("reusltScript", ReusltScript);
//        	sLocationUrl = "forward:/uss/olp/qnn/EgovQustnrRespondInfoManageList.do";
//        }else{
//
//        	 if(loginVO.getUniqId() != null){
//	        	 commandMap.put("uniqId", (String)loginVO.getUniqId());
//	        	 //사용자정보
//	        	 model.addAttribute("Emplyrinfo",  (Map)egovQustnrRespondInfoService.selectQustnrRespondInfoManageEmplyrinfo(commandMap));
//        	 }
//
//     		 //설문템플릿정보
//    		 model.addAttribute("QustnrTmplatManage",  (List)egovQustnrRespondInfoService.selectQustnrTmplatManage(commandMap));
//
//        	 //설문정보
//        	 model.addAttribute("Comtnqestnrinfo",  (List)egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqestnrinfo(commandMap));
//        	 //문항정보
//        	 model.addAttribute("Comtnqustnrqesitm",  (List)egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqustnrqesitm(commandMap));
//        	 //항목정보
//        	 model.addAttribute("Comtnqustnriem",  (List)egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqustnriem(commandMap));
//        	 //설문템플릿ID 설정
//        	 model.addAttribute("qestnrTmplatId",  commandMap.get("qestnrTmplatId") == null ? "" : (String)commandMap.get("qestnrTmplatId") );
//           	 //설문지정보ID 설정
//        	 model.addAttribute("qestnrId",  commandMap.get("qestnrId") == null ? "" : (String)commandMap.get("qestnrId"));
//
//        }
//
//		return sLocationUrl;
//	}
//
//	/**
//	 * 응답자결과(설문조사) 목록을 조회한다.
//	 * @param searchVO
//	 * @param request
//	 * @param commandMap
//	 * @param qustnrRespondInfoVO
//	 * @param model
//	 * @return "egovframework/com/uss/olp/qri/EgovQustnrRespondInfoList"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/uss/olp/qri/EgovQustnrRespondInfoList.do")
//	public String egovQustnrRespondInfoList(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO,
//			HttpServletRequest request,
//			Map commandMap,
//			QustnrRespondInfoVO qustnrRespondInfoVO,
//    		ModelMap model)
//    throws Exception {
//
//    	// 0. Spring Security 사용자권한 처리
//    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
//        	return "egovframework/com/uat/uia/EgovLoginUsr";
//    	}
//
//		//로그인 객체 선언
//		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//		if(loginVO == null){ loginVO = new LoginVO();}
//
//		String sSearchMode = commandMap.get("searchMode") == null ? "" : (String)commandMap.get("searchMode");
//
//		//설문지정보에서 넘어오면 자동검색 설정
//		if(sSearchMode.equals("Y")){
//			searchVO.setSearchCondition("QESTNR_ID");
//			searchVO.setSearchKeyword(qustnrRespondInfoVO.getQestnrId());
//		}
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
//        List sampleList = egovQustnrRespondInfoService.selectQustnrRespondInfoList(searchVO);
//        model.addAttribute("resultList", sampleList);
//
//        model.addAttribute("searchKeyword", commandMap.get("searchKeyword") == null ? "" : (String)commandMap.get("searchKeyword"));
//        model.addAttribute("searchCondition", commandMap.get("searchCondition") == null ? "" : (String)commandMap.get("searchCondition"));
//
//        int totCnt = (Integer)egovQustnrRespondInfoService.selectQustnrRespondInfoListCnt(searchVO);
//		paginationInfo.setTotalRecordCount(totCnt);
//        model.addAttribute("paginationInfo", paginationInfo);
//
//		return "egovframework/com/uss/olp/qri/EgovQustnrRespondInfoList";
//	}
//
//	/**
//	 * 응답자결과(설문조사) 목록을 상세조회 조회한다.
//	 * @param searchVO
//	 * @param qustnrRespondInfoVO
//	 * @param commandMap
//	 * @param model
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/uss/olp/qri/EgovQustnrRespondInfoDetail.do")
//	public String egovQustnrRespondInfoDetail(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO,
//			QustnrRespondInfoVO qustnrRespondInfoVO,
//			Map commandMap,
//    		ModelMap model)
//    throws Exception {
//
//		String sLocationUrl = "egovframework/com/uss/olp/qri/EgovQustnrRespondInfoDetail";
//
//		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
//
//		if(sCmd.equals("del")){
//			egovQustnrRespondInfoService.deleteQustnrRespondInfo(qustnrRespondInfoVO);
//			sLocationUrl = "redirect:/uss/olp/qri/EgovQustnrRespondInfoList.do";
//		}else{
//	        List sampleList = egovQustnrRespondInfoService.selectQustnrRespondInfoDetail(qustnrRespondInfoVO);
//	        model.addAttribute("resultList", sampleList);
//		}
//
//		return sLocationUrl;
//	}
//
//	/**
//	 * 응답자결과(설문조사)를 수정한다.
//	 * @param searchVO
//	 * @param commandMap
//	 * @param request
//	 * @param qustnrRespondInfoVO
//	 * @param bindingResult
//	 * @param model
//	 * @return "egovframework/com/uss/olp/qri/EgovQustnrRespondInfoModify"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/uss/olp/qri/EgovQustnrRespondInfoModify.do")
//	public String qustnrRespondInfoModify(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO,
//			Map commandMap,
//			HttpServletRequest request,
//			@ModelAttribute("qustnrRespondInfoVO") QustnrRespondInfoVO qustnrRespondInfoVO,
//			BindingResult bindingResult,
//    		ModelMap model)
//    throws Exception {
//
//    	// 0. Spring Security 사용자권한 처리
//    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
//        	return "egovframework/com/uat/uia/EgovLoginUsr";
//    	}
//
//		//로그인 객체 선언
//		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//		if(loginVO == null){ loginVO = new LoginVO();}
//
//		String sLocationUrl = "egovframework/com/uss/olp/qri/EgovQustnrRespondInfoModify";
//
//		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
//
//        if(sCmd.equals("save")){
//    		//서버  validate 체크
//            beanValidator.validate(qustnrRespondInfoVO, bindingResult);
//    		if(bindingResult.hasErrors()){
//    	        return sLocationUrl;
//    		}
//
//    		//아이디 설정
//    		qustnrRespondInfoVO.setFrstRegisterId((String)loginVO.getUniqId());
//    		qustnrRespondInfoVO.setLastUpdusrId((String)loginVO.getUniqId());
//
//        	egovQustnrRespondInfoService.updateQustnrRespondInfo(qustnrRespondInfoVO);
//        	sLocationUrl = "redirect:/uss/olp/qri/EgovQustnrRespondInfoList.do";
//		}else{
//	        List sampleList = egovQustnrRespondInfoService.selectQustnrRespondInfoDetail(qustnrRespondInfoVO);
//	        model.addAttribute("resultList", sampleList);
//		}
//
//		return sLocationUrl;
//	}
//
//	/**
//	 * 응답자결과(설문조사)를 등록한다.
//	 * @param searchVO
//	 * @param commandMap
//	 * @param request
//	 * @param qustnrRespondInfoVO
//	 * @param bindingResult
//	 * @param model
//	 * @return "egovframework/com/uss/olp/qri/EgovQustnrRespondInfoRegist"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/uss/olp/qri/EgovQustnrRespondInfoRegist.do")
//	public String qustnrRespondInfoRegist(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO,
//			Map commandMap,
//			HttpServletRequest request,
//			@ModelAttribute("qustnrRespondInfoVO") QustnrRespondInfoVO qustnrRespondInfoVO,
//			BindingResult bindingResult,
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
//		if(loginVO == null){ loginVO = new LoginVO();}
//
//		String sLocationUrl = "egovframework/com/uss/olp/qri/EgovQustnrRespondInfoRegist";
//
//		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
//		log.info("cmd =>" + sCmd);
//
//        if(sCmd.equals("save")){
//    		//서버  validate 체크
//            beanValidator.validate(qustnrRespondInfoVO, bindingResult);
//    		if(bindingResult.hasErrors()){
//    	        return sLocationUrl;
//    		}
//
//    		//아이디 설정
//    		qustnrRespondInfoVO.setFrstRegisterId((String)loginVO.getUniqId());
//    		qustnrRespondInfoVO.setLastUpdusrId((String)loginVO.getUniqId());
//
//        	egovQustnrRespondInfoService.insertQustnrRespondInfo(qustnrRespondInfoVO);
//        	sLocationUrl = "redirect:/uss/olp/qri/EgovQustnrRespondInfoList.do";
//        }
//
//		return sLocationUrl;
//	}
}


