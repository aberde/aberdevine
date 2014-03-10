package whoya.egovframework.com.uss.olp.qim.web;

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
import whoya.egovframework.com.uss.olp.qim.service.WhoyaEgovQustnrItemManageService;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.olp.qim.service.QustnrItemManageVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
/**
 * 설문항목관리를 처리하는 Controller Class 구현
 */

@Controller
public class WhoyaEgovQustnrItemManageController {
    
    @Resource(name = "whoyaEgovQustnrItemManageService")
    private WhoyaEgovQustnrItemManageService egovQustnrItemManageService;
    
    /**
     * 항목관리 화면
     * @return ModelAndView
     */
    @RequestMapping(value="/whoya/uss/olp/qim/EgovQustnrItemManageList.do")
    public ModelAndView egovQustnrQestnManageList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("whoya/egovframework/com/uss/olp/qim/EgovQustnrItemManageList");
        return mav;
    }
    
    /**
     * 설문항목 목록을 조회한다.
     * @param searchVO
     * @param commandMap
     * @param qustnrItemManageVO
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qim/EgovQustnrItemManageListJSON.do")
    public @ResponseBody JSONObject egovQustnrItemManageListJSON(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, QustnrItemManageVO qustnrItemManageVO, ModelMap model) throws Exception {
        JSONObject resultList = new JSONObject();
        
        try {
            String sSearchMode = commandMap.get("searchMode") == null ? "" : (String)commandMap.get("searchMode");
            
            //설문문항에 넘어온 건에 대해 조회
            if(sSearchMode.equals("Y")){
                searchVO.setSearchCondition("QUSTNR_QESITM_ID");//qestnrQesitmId
                searchVO.setSearchKeyword(qustnrItemManageVO.getQestnrQesitmId());
            }
            
            searchVO.setPageIndex(0);
            // EgovMap 형식으로 받음.
            List sampleList = egovQustnrItemManageService.selectQustnrItemManageList(searchVO);
            whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(sampleList));
            
            // 순번 컬럼 추가.
            // TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
            for ( int i = 0 ; i < list.size(); i++ ) {
                whoyaMap wmap = list.getMap(i);
                wmap.put("no", i + 1);
            }
                        
            // 순번,항목내용,기타답변여부,등록자,등록일자,항목아이디
            resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,iemCn,etcAnswerAt,frstRegisterNm,frstRegisterPnttm,qustnrIemId"));
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
     * 설문항목를 등록한다.
     * @param searchVO
     * @param commandMap
     * @param qustnrItemManageVO
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qim/EgovQustnrItemManageRegist.do")
    public @ResponseBody JSONObject qustnrItemManageRegist(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, @ModelAttribute("qustnrItemManageVO") QustnrItemManageVO qustnrItemManageVO, ModelMap model) throws Exception {
        JSONObject result = new JSONObject();
        try {
            //로그인 객체 선언
            LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
            //아이디 설정
            qustnrItemManageVO.setFrstRegisterId((String)loginVO.getUniqId());
            qustnrItemManageVO.setLastUpdusrId((String)loginVO.getUniqId());
  
            egovQustnrItemManageService.insertQustnrItemManage(qustnrItemManageVO);
            
            result.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.put("status", commonReturn.FAIL);
        }
        
        return result;
    }
    
    /**
     * 설문항목 목록을 상세조회 조회한다.
     * @param searchVO
     * @param qustnrItemManageVO
     * @param commandMap
     * @param model
     * @return  JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qim/EgovQustnrItemManageDetail.do")
    public @ResponseBody EgovMap egovQustnrItemManageDetail(@ModelAttribute("searchVO") ComDefaultVO searchVO, QustnrItemManageVO qustnrItemManageVO, Map commandMap, ModelMap model) throws Exception {
        try {
            EgovMap map = (EgovMap)egovQustnrItemManageService.selectQustnrItemManageDetail(qustnrItemManageVO).get(0);
            return map;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 설문항목을 삭제한다.
     * @param searchVO
     * @param commandMap
     * @param qustnrQestnManageVO
     * @param bindingResult
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qim/EgovQustnrItemManageDelete.do")
    public @ResponseBody JSONObject egovQustnrItemManageDelete(@ModelAttribute("searchVO") ComDefaultVO searchVO, QustnrItemManageVO qustnrItemManageVO, Map commandMap, ModelMap model) throws Exception {
        JSONObject result = new JSONObject();
        try {
            egovQustnrItemManageService.deleteQustnrItemManage(qustnrItemManageVO);
            result.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.put("status", commonReturn.FAIL);
        }
        
        return result;
    }
    
    /**
     * 설문항목를 수정한다.
     * @param searchVO
     * @param commandMap
     * @param qustnrItemManageVO
     * @param bindingResult
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qim/EgovQustnrItemManageModify.do")
    public @ResponseBody JSONObject qustnrItemManageModify(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, @ModelAttribute("qustnrItemManageVO") QustnrItemManageVO qustnrItemManageVO, ModelMap model) throws Exception {
        JSONObject result = new JSONObject();
        try {
            //로그인 객체 선언
            LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
            //아이디 설정
            qustnrItemManageVO.setFrstRegisterId((String)loginVO.getUniqId());
            qustnrItemManageVO.setLastUpdusrId((String)loginVO.getUniqId());
            
            egovQustnrItemManageService.updateQustnrItemManage(qustnrItemManageVO);
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
//	/**
//	 * 설문항목 팝업 목록을 조회한다.
//	 * @param searchVO
//	 * @param commandMap
//	 * @param qustnrItemManageVO
//	 * @param model
//	 * @return "egovframework/com/uss/olp/qim/EgovQustnrItemManageListPopup"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/uss/olp/qim/EgovQustnrItemManageListPopup.do")
//	public String egovQustnrItemManageListPopup(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO,
//			Map commandMap,
//			QustnrItemManageVO qustnrItemManageVO,
//    		ModelMap model)
//    throws Exception {
//
//		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
//		if(sCmd.equals("del")){
//			egovQustnrItemManageService.deleteQustnrItemManage(qustnrItemManageVO);
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
//        List sampleList = egovQustnrItemManageService.selectQustnrItemManageList(searchVO);
//        model.addAttribute("resultList", sampleList);
//
//        model.addAttribute("searchKeyword", commandMap.get("searchKeyword") == null ? "" : (String)commandMap.get("searchKeyword"));
//        model.addAttribute("searchCondition", commandMap.get("searchCondition") == null ? "" : (String)commandMap.get("searchCondition"));
//
//        int totCnt = (Integer)egovQustnrItemManageService.selectQustnrItemManageListCnt(searchVO);
//		paginationInfo.setTotalRecordCount(totCnt);
//        model.addAttribute("paginationInfo", paginationInfo);
//
//		return "egovframework/com/uss/olp/qim/EgovQustnrItemManageListPopup";
//	}
}


