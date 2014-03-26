package whoya.egovframework.com.sym.ccm.cca.web;

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
import whoya.egovframework.com.sym.ccm.cca.service.WhoyaEgovCcmCmmnCodeManageService;
import whoya.egovframework.com.sym.ccm.ccc.service.WhoyaEgovCcmCmmnClCodeManageService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sym.ccm.cca.service.CmmnCode;
import egovframework.com.sym.ccm.cca.service.CmmnCodeVO;
import egovframework.com.sym.ccm.ccc.service.CmmnClCodeVO;

/**
 * 
 * 공통코드에 관한 요청을 받아 서비스 클래스로 요청을 전달하고 서비스클래스에서 처리한 결과를 웹 화면으로 전달을 위한 Controller를 정의한다
 */
	
@Controller
public class WhoyaEgovCcmCmmnCodeManageController {
    
    @Resource(name = "WhoyaCmmnCodeManageService")
    private WhoyaEgovCcmCmmnCodeManageService cmmnCodeManageService;
    
    @Resource(name = "WhoyaCmmnClCodeManageService")
    private WhoyaEgovCcmCmmnClCodeManageService cmmnClCodeManageService;
    
    /**
     * 공통분류코드 화면
     * @return ModelAndView
     */
    @RequestMapping(value = "/whoya/sym/ccm/cca/EgovCcmCmmnCodeList.do")
    public ModelAndView selectCmmnCodeList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("whoya/egovframework/com/sym/ccm/cca/EgovCcmCmmnCodeList");
        return mav;
    }
    
    /**
     * 공통코드 목록을 조회한다.
     * @param loginVO
     * @param searchVO
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/sym/ccm/cca/EgovCcmCmmnCodeListJSON.do")
    public @ResponseBody JSONObject selectCmmnCodeListJSON(@ModelAttribute("searchVO") CmmnCodeVO searchVO, ModelMap model) throws Exception {
        JSONObject resultList = new JSONObject();
        
        try {
            searchVO.setPageIndex(0);
            // EgovMap 형식으로 받음.
            List entrprsList = cmmnCodeManageService.selectCmmnCodeList(searchVO);
            whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(entrprsList));
            
            // 번호 컬럼 추가.
            // TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
            for ( int i = 0 ; i < list.size(); i++ ) {
                whoyaMap wmap = list.getMap(i);
                wmap.put("no", i + 1);
            }
            
            // 순번,분류명,코드ID,코드ID명,사용여부
            resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,clCodeNm,codeId,codeIdNm,useAt"));
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
     * 공통코드를 등록한다.
     * @param loginVO
     * @param cmmnCode
     * @param bindingResult
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/sym/ccm/cca/EgovCcmCmmnCodeRegist.do")
    public @ResponseBody JSONObject insertCmmnCode (@ModelAttribute("cmmnCode") CmmnCode cmmnCode, ModelMap model) throws Exception {    
        JSONObject resultJSON = new JSONObject();
        try {
            // 로그인 객체 선언
            LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            
            cmmnCode.setFrstRegisterId(loginVO.getUniqId());
            cmmnCodeManageService.insertCmmnCode(cmmnCode);
            
            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
        
        return resultJSON;
    }
    
    /**
     * 공통코드 상세항목을 조회한다.
     * @param loginVO
     * @param cmmnCode
     * @param model
     * @return CmmnCode
     * @throws Exception
     */
    @RequestMapping(value="/whoya/sym/ccm/cca/EgovCcmCmmnCodeDetail.do")
    public @ResponseBody CmmnCode selectCmmnCodeDetail(CmmnCode cmmnCode, ModelMap model) throws Exception {
        try {
            CmmnCode vo =cmmnCodeManageService.selectCmmnCodeDetail(cmmnCode);
            return vo;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 공통코드를 수정한다.
     * @param loginVO
     * @param cmmnCode
     * @param bindingResult
     * @param commandMap
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/sym/ccm/cca/EgovCcmCmmnCodeModify.do")
    public @ResponseBody JSONObject updateCmmnCode (@ModelAttribute("cmmnCode") CmmnCode cmmnCode, Map commandMap, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        try {
            // 로그인 객체 선언
            LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            
            cmmnCode.setLastUpdusrId(loginVO.getUniqId());
            cmmnCodeManageService.updateCmmnCode(cmmnCode);

            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
        
        return resultJSON;
    }
  
    /**
     * 공통코드를 삭제한다.
     * @param loginVO
     * @param cmmnCode
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/sym/ccm/cca/EgovCcmCmmnCodeRemove.do")
    public @ResponseBody JSONObject deleteCmmnCode (CmmnCode cmmnCode, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        try {
            cmmnCodeManageService.deleteCmmnCode(cmmnCode);

            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
          
        return resultJSON;
    }
    
    /**
     * 공통분류코드 목록을 조회한다.
     * @param loginVO
     * @param cmmnCode
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/sym/ccm/cca/selectCmmnClCodeList.do")
    public @ResponseBody JSONObject selectCmmnClCodeList(CmmnCode cmmnCode, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        try {
            CmmnClCodeVO searchVO;
            searchVO = new CmmnClCodeVO();
            searchVO.setRecordCountPerPage(999999);
            searchVO.setFirstIndex(0);
            searchVO.setSearchCondition("CodeList");
            List CmmnCodeList = cmmnClCodeManageService.selectCmmnClCodeList(searchVO);
            
            resultJSON.put("list", CmmnCodeList);
            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
        
        return resultJSON;
    }
}