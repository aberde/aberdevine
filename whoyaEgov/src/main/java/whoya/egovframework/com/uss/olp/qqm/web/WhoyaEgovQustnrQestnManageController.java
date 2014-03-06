package whoya.egovframework.com.uss.olp.qqm.web;

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
import whoya.egovframework.com.uss.olp.qqm.service.WhoyaEgovQustnrQestnManageService;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.olp.qqm.service.QustnrQestnManageVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
/**
 * 설문문항을 처리하는 Controller Class 구현
 */ 

@Controller
public class WhoyaEgovQustnrQestnManageController {
  
    @Resource(name = "whoyaEgovQustnrQestnManageService")
    private WhoyaEgovQustnrQestnManageService egovQustnrQestnManageService;
  
    @Resource(name="EgovCmmUseService")
    private EgovCmmUseService cmmUseService;
    
    /**
     * 항목관리 화면
     * @return ModelAndView
     */
    @RequestMapping(value="/whoya/uss/olp/qqm/EgovQustnrQestnManageList.do")
    public ModelAndView egovQustnrQestnManageList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("whoya/egovframework/com/uss/olp/qqm/EgovQustnrQestnManageList");
        return mav;
    }
    
    /**
     * 설문문항 목록을 조회한다. 
     * @param searchVO
     * @param qustnrQestnManageVO
     * @param commandMap
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qqm/EgovQustnrQestnManageListJSON.do")
    public @ResponseBody JSONObject egovQustnrQestnManageListJSON(@ModelAttribute("searchVO") ComDefaultVO searchVO, @ModelAttribute("qustnrQestnManageVO") QustnrQestnManageVO qustnrQestnManageVO, Map commandMap, ModelMap model) throws Exception {
        JSONObject resultList = new JSONObject();
        
        try {
            String sSearchMode = commandMap.get("searchMode") == null ? "" : (String)commandMap.get("searchMode");

            //설문지정보에서 넘어오면 자동검색 설정
            if(sSearchMode.equals("Y")){
                searchVO.setSearchCondition("QESTNR_ID");
                searchVO.setSearchKeyword(qustnrQestnManageVO.getQestnrId());
            }
            
            searchVO.setPageIndex(0);
            // EgovMap 형식으로 받음.
            List sampleList = egovQustnrQestnManageService.selectQustnrQestnManageList(searchVO);
            whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(sampleList));
            
            // 순번 컬럼 추가.
            // TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
            for ( int i = 0 ; i < list.size(); i++ ) {
                whoyaMap wmap = list.getMap(i);
                wmap.put("no", i + 1);
                wmap.put("qustnrItem", "../../../images/egovframework/com/cmm/icon/search.gif^선택^javascript:whoya.common.qustnrItemSelect();^_self");
                wmap.put("qustnrStatistics", "../../../images/egovframework/com/cmm/icon/search.gif^선택^javascript:whoya.common.qustnrStatisticsSelect(\"" + wmap.get("qestnrQesitmId") + "\");^_self");
            }
                        
            // 순번,질문내용,질문유형,질문항목,통계,등록자,등록일자,설문문항 ID
            resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,qestnCn,qestnTyCode,qustnrItem,qustnrStatistics,frstRegisterNm,frstRegisterPnttm,qestnrQesitmId"));
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
     * 질문유형 목록을 조회한다.
     * @param comDefaultCodeVO ComDefaultCodeVO
     * @return List<CmmnDetailCode>
     * @exception Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qqm/selectQestnTyCodeList.do", headers="Accept=application/json")
    public @ResponseBody List<CmmnDetailCode> selectQestnTyCodeList(ComDefaultCodeVO comDefaultCodeVO) throws Exception {
        List<CmmnDetailCode> codeResult = null;
        try {
            codeResult = cmmUseService.selectCmmCodeDetail(comDefaultCodeVO);
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        return codeResult;
    }

    /**
     * 설문문항를 등록한다.
     * @param searchVO
     * @param commandMap
     * @param qustnrQestnManageVO
     * @param bindingResult
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qqm/EgovQustnrQestnManageRegist.do")
    public @ResponseBody JSONObject qustnrQestnManageRegist(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, @ModelAttribute("qustnrQestnManageVO") QustnrQestnManageVO qustnrQestnManageVO, ModelMap model) throws Exception {
        JSONObject result = new JSONObject();
        try {
            //로그인 객체 선언
            LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
            //아이디 설정
            qustnrQestnManageVO.setFrstRegisterId((String)loginVO.getUniqId());
            qustnrQestnManageVO.setLastUpdusrId((String)loginVO.getUniqId());

            egovQustnrQestnManageService.insertQustnrQestnManage(qustnrQestnManageVO);
            
            result.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.put("status", commonReturn.FAIL);
        }
        
        return result;
    }
  
    /**
     * 설문문항 목록을 상세조회 조회한다. 
     * @param searchVO
     * @param qustnrQestnManageVO
     * @param commandMap
     * @param model
     * @return "egovframework/com/uss/olp/qqm/EgovQustnrQestnManageDetail"
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qqm/EgovQustnrQestnManageDetail.do")
    public @ResponseBody EgovMap egovQustnrQestnManageDetail(@ModelAttribute("searchVO") ComDefaultVO searchVO, @ModelAttribute("qustnrQestnManageVO") QustnrQestnManageVO qustnrQestnManageVO, Map commandMap, ModelMap model) throws Exception {
        try {
            EgovMap map = (EgovMap)egovQustnrQestnManageService.selectQustnrQestnManageDetail(qustnrQestnManageVO).get(0);
            return map;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 설문문항를 삭제한다.
     * @param searchVO
     * @param commandMap
     * @param qustnrQestnManageVO
     * @param bindingResult
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qqm/EgovQustnrQestnManageDelete.do")
    public @ResponseBody JSONObject qustnrQestnManageDelete(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, @ModelAttribute("qustnrQestnManageVO") QustnrQestnManageVO qustnrQestnManageVO, ModelMap model) throws Exception {
        JSONObject result = new JSONObject();
        try {
            egovQustnrQestnManageService.deleteQustnrQestnManage(qustnrQestnManageVO);
            result.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.put("status", commonReturn.FAIL);
        }
        
        return result;
    }
  
    /**
     * 설문문항를 수정한다. 
     * @param searchVO
     * @param commandMap
     * @param qustnrQestnManageVO
     * @param bindingResult
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qqm/EgovQustnrQestnManageModify.do")
    public @ResponseBody JSONObject qustnrQestnManageModify(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, @ModelAttribute("qustnrQestnManageVO") QustnrQestnManageVO qustnrQestnManageVO, ModelMap model) throws Exception {
        JSONObject result = new JSONObject();
        try {
            //로그인 객체 선언
            LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
            //아이디 설정
            qustnrQestnManageVO.setFrstRegisterId((String)loginVO.getUniqId());
            qustnrQestnManageVO.setLastUpdusrId((String)loginVO.getUniqId());
            
            egovQustnrQestnManageService.updateQustnrQestnManage(qustnrQestnManageVO);
            result.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.put("status", commonReturn.FAIL);
        }
        
        return result;
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
//    /** EgovPropertyService */
//    @Resource(name = "propertiesService")
//    protected EgovPropertyService propertiesService;
//    
//    /**
//     * 설문항목 통계를 조회한다. 
//     * @param searchVO
//     * @param qustnrQestnManageVO
//     * @param commandMap
//     * @param model
//     * @return "egovframework/com/uss/olp/qqm/EgovQustnrQestnManageStatistics"
//     * @throws Exception
//     */
//    @RequestMapping(value="/uss/olp/qqm/EgovQustnrQestnManageStatistics.do")
//	public String egovQustnrQestnManageStatistics(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO, 
//			QustnrQestnManageVO qustnrQestnManageVO,
//			Map commandMap,
//    		ModelMap model)
//    throws Exception {
//
//		String sLocationUrl = "egovframework/com/uss/olp/qqm/EgovQustnrQestnManageStatistics";
//		
//        List sampleList = egovQustnrQestnManageService.selectQustnrQestnManageDetail(qustnrQestnManageVO);
//        model.addAttribute("resultList", sampleList);
//        // 객관식설문통계
//        HashMap mapParam = new HashMap();
//        mapParam.put("qestnrQesitmId", (String)qustnrQestnManageVO.getQestnrQesitmId());
//        List statisticsList = egovQustnrQestnManageService.selectQustnrManageStatistics(mapParam);
//        model.addAttribute("statisticsList", statisticsList);
//        // 주관식설문통계
//        List statisticsList2 = egovQustnrQestnManageService.selectQustnrManageStatistics2(mapParam);
//        model.addAttribute("statisticsList2", statisticsList2);
//		return sLocationUrl; 	
//	}
//	
//	/**
//	 * 설문문항 팝업 록을 조회한다. 
//	 * @param searchVO
//	 * @param qustnrQestnManageVO
//	 * @param commandMap
//	 * @param model
//	 * @return "egovframework/com/uss/olp/qqm/EgovQustnrQestnManageListPopup"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/uss/olp/qqm/EgovQustnrQestnManageListPopup.do")
//	public String egovQustnrQestnManageListPopup(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO, 
//			@ModelAttribute("qustnrQestnManageVO") QustnrQestnManageVO qustnrQestnManageVO,
//			Map commandMap, 
//    		ModelMap model)
//    throws Exception {
//		
//		String sSearchMode = commandMap.get("searchMode") == null ? "" : (String)commandMap.get("searchMode");
//
//		//설문지정보에서 넘어오면 자동검색 설정
//		if(sSearchMode.equals("Y")){
//			searchVO.setSearchCondition("QESTNR_ID");
//			searchVO.setSearchKeyword(qustnrQestnManageVO.getQestnrId());
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
//        List resultList = egovQustnrQestnManageService.selectQustnrQestnManageList(searchVO);
//        model.addAttribute("resultList", resultList);
//                
//        int totCnt = (Integer)egovQustnrQestnManageService.selectQustnrQestnManageListCnt(searchVO);
//		paginationInfo.setTotalRecordCount(totCnt);
//        model.addAttribute("paginationInfo", paginationInfo);
//		
//		return "egovframework/com/uss/olp/qqm/EgovQustnrQestnManageListPopup"; 
//	}
}


