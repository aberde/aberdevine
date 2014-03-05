package whoya.egovframework.com.uss.olp.qrm.web;

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
import whoya.egovframework.com.uss.olp.qrm.service.WhoyaEgovQustnrRespondManageService;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.olp.qrm.service.QustnrRespondManageVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
/**
 * 설문응답자관리 Controller Class 구현 
 */

@Controller
public class WhoyaEgovQustnrRespondManageController {
  
    @Resource(name = "whoyaEgovQustnrRespondManageService")
    private WhoyaEgovQustnrRespondManageService egovQustnrRespondManageService;
    
    @Resource(name="EgovCmmUseService")
    private EgovCmmUseService cmmUseService;
    
    /**
     * 응답자관리 화면
     * @return ModelAndView
     */
    @RequestMapping(value="/whoya/uss/olp/qrm/EgovQustnrRespondManageList.do")
    public ModelAndView egovQustnrRespondManageList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("whoya/egovframework/com/uss/olp/qrm/EgovQustnrRespondManageList");
        return mav;
    }
    
    /**
     * 응답자정보 목록을 조회한다. 
     * @param searchVO
     * @param commandMap
     * @param qustnrRespondManageVO
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qrm/EgovQustnrRespondManageListJSON.do")
    public @ResponseBody JSONObject egovQustnrRespondManageListJSON(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, QustnrRespondManageVO qustnrRespondManageVO, ModelMap model) throws Exception {
        JSONObject resultList = new JSONObject();
        
        try {
            String sSearchMode = commandMap.get("searchMode") == null ? "" : (String)commandMap.get("searchMode");

            //설문지정보에서 넘어오면 자동검색 설정
            if(sSearchMode.equals("Y")){
                searchVO.setSearchCondition("QESTNR_ID");
                searchVO.setSearchKeyword(qustnrRespondManageVO.getQestnrId());
            }
            
            searchVO.setPageIndex(0);
            // EgovMap 형식으로 받음.
            List sampleList = egovQustnrRespondManageService.selectQustnrRespondManageList(searchVO);
            whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(sampleList));
            
            // 순번 컬럼 추가.
            // TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
            for ( int i = 0 ; i < list.size(); i++ ) {
                whoyaMap wmap = list.getMap(i);
                wmap.put("no", i + 1);
            }
                        
            // 순번,설문지정보(제목),응답자명,등록자,등록일자,설문응답자아이디
            resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,qestnrSj,respondNm,frstRegisterNm,frstRegisterPnttm,qestnrRespondId"));
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
     * 성별 목록을 조회한다.
     * @param comDefaultCodeVO ComDefaultCodeVO
     * @return List<CmmnDetailCode>
     * @exception Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qrm/selectSexdstnCodeList.do", headers="Accept=application/json")
    public @ResponseBody List<CmmnDetailCode> selectSexdstnCodeList(ComDefaultCodeVO comDefaultCodeVO) throws Exception {
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
     * 직업 목록을 조회한다.
     * @param comDefaultCodeVO ComDefaultCodeVO
     * @return List<CmmnDetailCode>
     * @exception Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qrm/selectOccpTyCodeList.do", headers="Accept=application/json")
    public @ResponseBody List<CmmnDetailCode> selectOccpTyCodeList(ComDefaultCodeVO comDefaultCodeVO) throws Exception {
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
     * 응답자정보를 등록한다.
     * @param searchVO
     * @param commandMap
     * @param qustnrRespondManageVO
     * @param bindingResult
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qrm/EgovQustnrRespondManageRegist.do")
    public @ResponseBody JSONObject qustnrRespondManageRegist(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, @ModelAttribute("qustnrRespondManageVO") QustnrRespondManageVO qustnrRespondManageVO, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        
        try {
            //로그인 객체 선언
            LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

            //아이디 설정
            qustnrRespondManageVO.setFrstRegisterId((String)loginVO.getUniqId());
            qustnrRespondManageVO.setLastUpdusrId((String)loginVO.getUniqId());
            
            egovQustnrRespondManageService.insertQustnrRespondManage(qustnrRespondManageVO);
            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
        
        return resultJSON;
    }
     
    /**
     * 응답자정보 목록을 상세조회 조회한다. 
     * @param searchVO
     * @param qustnrRespondManageVO
     * @param commandMap
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qrm/EgovQustnrRespondManageDetail.do")
    public @ResponseBody EgovMap egovQustnrRespondManageDetail(@ModelAttribute("searchVO") ComDefaultVO searchVO, QustnrRespondManageVO qustnrRespondManageVO, Map commandMap, ModelMap model) throws Exception {
        try {
            EgovMap map = (EgovMap)egovQustnrRespondManageService.selectQustnrRespondManageDetail(qustnrRespondManageVO).get(0);
            return map;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 응답자정보를 삭제한다.
     * @param searchVO
     * @param commandMap
     * @param qustnrRespondManageVO
     * @param bindingResult
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qrm/EgovQustnrRespondManageDelete.do")
    public @ResponseBody JSONObject qustnrRespondManageDelete(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, @ModelAttribute("qustnrRespondManageVO") QustnrRespondManageVO qustnrRespondManageVO, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        
        try {
            egovQustnrRespondManageService.deleteQustnrRespondManage(qustnrRespondManageVO);
            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
        
        return resultJSON;
    }
  
    /**
     * 응답자정보를 수정한다.
     * @param searchVO
     * @param commandMap
     * @param qustnrRespondManageVO
     * @param bindingResult
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/olp/qrm/EgovQustnrRespondManageModify.do")
    public @ResponseBody JSONObject qustnrRespondManageModify(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, @ModelAttribute("qustnrRespondManageVO") QustnrRespondManageVO qustnrRespondManageVO, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        
        try {
            //로그인 객체 선언
            LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
            
            //아이디 설정
            qustnrRespondManageVO.setFrstRegisterId((String)loginVO.getUniqId());
            qustnrRespondManageVO.setLastUpdusrId((String)loginVO.getUniqId());
            
            egovQustnrRespondManageService.updateQustnrRespondManage(qustnrRespondManageVO);
            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
        return resultJSON;
    }
}


