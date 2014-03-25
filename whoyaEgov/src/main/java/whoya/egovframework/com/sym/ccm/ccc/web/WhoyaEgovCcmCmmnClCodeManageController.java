package whoya.egovframework.com.sym.ccm.ccc.web;

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
import whoya.egovframework.com.sym.ccm.ccc.service.WhoyaEgovCcmCmmnClCodeManageService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sym.ccm.ccc.service.CmmnClCode;
import egovframework.com.sym.ccm.ccc.service.CmmnClCodeVO;
/**
 * 
 * 공통분류코드에 관한 요청을 받아 서비스 클래스로 요청을 전달하고 서비스클래스에서 처리한 결과를 웹 화면으로 전달을 위한 Controller를 정의한다
 */

@Controller
public class WhoyaEgovCcmCmmnClCodeManageController {
    
    @Resource(name = "WhoyaCmmnClCodeManageService")
    private WhoyaEgovCcmCmmnClCodeManageService cmmnClCodeManageService;
    
    /**
     * 공통분류코드 화면
     * @return ModelAndView
     */
    @RequestMapping(value = "/whoya/sym/ccm/ccc/EgovCcmCmmnClCodeList.do")
    public ModelAndView selectCmmnClCodeList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("whoya/egovframework/com/sym/ccm/ccc/EgovCcmCmmnClCodeList");
        return mav;
    }
    
    /**
     * 공통분류코드 목록을 조회한다.
     * @param loginVO
     * @param searchVO
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/sym/ccm/ccc/EgovCcmCmmnClCodeListJSON.do")
    public @ResponseBody JSONObject selectCmmnClCodeListJSON(@ModelAttribute("loginVO") LoginVO loginVO, @ModelAttribute("searchVO") CmmnClCodeVO searchVO, ModelMap model) throws Exception {
        JSONObject resultList = new JSONObject();
        
        try {
            searchVO.setPageIndex(0);
            // EgovMap 형식으로 받음.
            List entrprsList = cmmnClCodeManageService.selectCmmnClCodeList(searchVO);
            whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(entrprsList));
            
            // 번호 컬럼 추가.
            // TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
            for ( int i = 0 ; i < list.size(); i++ ) {
                whoyaMap wmap = list.getMap(i);
                wmap.put("no", i + 1);
            }
            
            // 순번,분류코드,분류코드명,사용여부
            resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,clCode,clCodeNm,useAt"));
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
     * 공통분류코드를 등록한다.
     * @param loginVO
     * @param cmmnClCode
     * @param bindingResult
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/sym/ccm/ccc/EgovCcmCmmnClCodeRegist.do")
    public @ResponseBody JSONObject insertCmmnClCode (@ModelAttribute("cmmnClCode") CmmnClCode cmmnClCode, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        try {
            // 로그인 객체 선언
            LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            
            CmmnClCode vo = cmmnClCodeManageService.selectCmmnClCodeDetail(cmmnClCode);
            cmmnClCode.setFrstRegisterId(loginVO.getUniqId());
            cmmnClCodeManageService.insertCmmnClCode(cmmnClCode);
            
            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
        
        return resultJSON;
    }
    
    /**
     * 공통분류코드 상세항목을 조회한다.
     * @param loginVO
     * @param cmmnClCode
     * @param model
     * @return CmmnClCode
     * @throws Exception
     */
    @RequestMapping(value="/whoya/sym/ccm/ccc/EgovCcmCmmnClCodeDetail.do")
    public @ResponseBody CmmnClCode selectCmmnClCodeDetail (CmmnClCode cmmnClCode, ModelMap model) throws Exception {
        try {
            CmmnClCode vo = cmmnClCodeManageService.selectCmmnClCodeDetail(cmmnClCode);
            return vo;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 공통분류코드를 수정한다.
     * @param loginVO
     * @param cmmnClCode
     * @param bindingResult
     * @param commandMap
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/sym/ccm/ccc/EgovCcmCmmnClCodeModify.do")
    public @ResponseBody JSONObject updateCmmnClCode (@ModelAttribute("administCode") CmmnClCode cmmnClCode, Map commandMap, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        try {
            // 로그인 객체 선언
            LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            
            cmmnClCode.setLastUpdusrId(loginVO.getUniqId());
            cmmnClCodeManageService.updateCmmnClCode(cmmnClCode);

            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
        
        return resultJSON;
    }
  
    /**
     * 공통분류코드를 삭제한다.
     * @param loginVO
     * @param cmmnClCode
     * @param model
     * @return JSONObject
     * @throws Exception
     */
      @RequestMapping(value="/whoya/sym/ccm/ccc/EgovCcmCmmnClCodeRemove.do")
    public @ResponseBody JSONObject deleteCmmnClCode (CmmnClCode cmmnClCode, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        try {
            cmmnClCodeManageService.deleteCmmnClCode(cmmnClCode);

            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
          
        return resultJSON;
    }	
}