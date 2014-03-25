package whoya.egovframework.com.uss.ion.ulm.web;

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
import whoya.egovframework.com.uss.ion.ulm.service.WhoyaEgovUnityLinkService;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.bnr.service.BannerVO;
import egovframework.com.uss.ion.ulm.service.UnityLink;

/**
 * 통합링크관리를 처리하는 Controller Class 구현
 */

@Controller
public class WhoyaEgovUnityLinkController {
    
    /** egovOnlinePollService */
    @Resource(name = "whoyaEgovUnityLinkService")
    private WhoyaEgovUnityLinkService egovUnityLinkService;
  
    /**
     * 통합링크관리 화면
     * @return ModelAndView
     */
    @RequestMapping(value = "/whoya/uss/ion/ulm/listUnityLink.do")
    public ModelAndView selectEntrprsMberList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("whoya/egovframework/com/uss/ion/ulm/EgovUnityLinkList");
        return mav;
    }
    
    /**
     * 통합링크관리 목록을 조회한다.
     * @param searchVO
     * @param commandMap
     * @param unityLinkVO
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value = "/whoya/uss/ion/ulm/listUnityLinkJSON.do")
    public @ResponseBody JSONObject egovUnityLinkListJSON(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, UnityLink unityLinkVO, ModelMap model) throws Exception {
        JSONObject resultList = new JSONObject();
        
        try {
            searchVO.setPageIndex(0);
            // EgovMap 형식으로 받음.
            List<BannerVO> entrprsList = egovUnityLinkService.selectUnityLinkList(searchVO);
            whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(entrprsList));
            
            // 번호 컬럼 추가.
            // TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
            for ( int i = 0 ; i < list.size(); i++ ) {
                whoyaMap wmap = list.getMap(i);
                wmap.put("no", i + 1);
            }
            
            // 순번,그룹,통합링크명,통합링크URL,등록자,등록일자,통합링크 아이디
            resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,unityLinkSeCode,unityLinkNm,unityLinkUrl,frstRegisterNm,frstRegisterPnttm,unityLinkId"));
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
     * 통합링크관리를 등록한다.
     * @param searchVO
     * @param commandMap
     * @param unityLinkVO
     * @param bindingResult
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value = "/whoya/uss/ion/ulm/registUnityLink.do")
    public @ResponseBody JSONObject egovUnityLinkRegist(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, @ModelAttribute("unityLink") UnityLink unityLink, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        try {
            // 로그인 객체 선언
            LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            //아이디 설정
            unityLink.setFrstRegisterId((String)loginVO.getUniqId());
            unityLink.setLastUpdusrId((String)loginVO.getUniqId());
            //저장
            egovUnityLinkService.insertUnityLink(unityLink);
            
            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
        
        return resultJSON;
    }
    
    /**
     * 통합링크관리 목록을 상세조회 조회한다.
     * @param searchVO
     * @param unityLinkVO
     * @param commandMap
     * @param model
     * @return 
     *         "/uss/ion/ulm/EgovOnlinePollDetail"
     * @throws Exception
     */
    @RequestMapping(value = "/whoya/uss/ion/ulm/detailUnityLink.do")
    public @ResponseBody UnityLink egovUnityLinkDetail(@ModelAttribute("searchVO") ComDefaultVO searchVO, UnityLink unityLink, Map commandMap, ModelMap model) throws Exception {
        try {
            //상세정보 불러오기
            UnityLink unityLinkVO = egovUnityLinkService.selectUnityLinkDetail(unityLink);
            return unityLinkVO;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 통합링크관리를 삭제한다.
     * @param searchVO
     * @param commandMap
     * @param unityLinkVO
     * @param bindingResult
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value = "/whoya/uss/ion/ulm/deleteUnityLink.do")
    public @ResponseBody JSONObject egovUnityLinkDelete(@ModelAttribute("searchVO") ComDefaultVO searchVO, UnityLink unityLink, Map commandMap, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        try {
            egovUnityLinkService.deleteUnityLink(unityLink);
            
            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
        
        return resultJSON;
    }
    
    /**
     * 통합링크관리를 수정한다.
     * @param searchVO
     * @param commandMap
     * @param unityLinkVO
     * @param bindingResult
     * @param model
     * @return 
     *         "/uss/ion/ulm/EgovOnlinePollUpdt"
     * @throws Exception
     */
    @RequestMapping(value = "/whoya/uss/ion/ulm/updtUnityLink.do")
    public @ResponseBody JSONObject egovUnityLinkModify(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, UnityLink unityLink, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        try {
            // 로그인 객체 선언
            LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
            
            //아이디 설정
            unityLink.setFrstRegisterId((String)loginVO.getUniqId());
            unityLink.setLastUpdusrId((String)loginVO.getUniqId());
            //저장
            egovUnityLinkService.updateUnityLink(unityLink);
            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
        
        return resultJSON;
    }
//	
//	 
//	 
//
//    protected Log log = LogFactory.getLog(this.getClass());
//
//    @Autowired
//    private DefaultBeanValidator beanValidator;
//
//    /** EgovMessageSource */
//    @Resource(name = "egovMessageSource")
//    EgovMessageSource egovMessageSource;
//
//    /** EgovPropertyService */
//    @Resource(name = "propertiesService")
//    protected EgovPropertyService propertiesService;
//    
//    /** Egov Common Code Service */
//    @Resource(name="EgovCmmUseService")
//    private EgovCmmUseService cmmUseService;
//
//    /**
//     * 통합링크관리 메인 셈플 목록을 조회한다.
//     * @param searchVO
//     * @param commandMap
//     * @param unityLinkVO
//     * @param model
//     * @return "egovframework/com/uss/ion/ulm/UnityLinkSample"
//     * @throws Exception
//     */
//    @RequestMapping(value = "/uss/ion/ulm/listUnityLinkSample.do")
//    public String egovUnityLinkSample1List(
//            UnityLink unityLinkVO, 
//            ModelMap model)
//            throws Exception {
//
//        List reusltList = egovUnityLinkService.selectUnityLinkSample(unityLinkVO);
//        model.addAttribute("resultList", reusltList);
//        
//        return "egovframework/com/uss/ion/ulm/UnityLinkSample";
//    }
//

}
