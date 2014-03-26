package whoya.egovframework.com.sym.ccm.cde.web;

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
import whoya.egovframework.com.sym.ccm.cde.service.WhoyaEgovCcmCmmnDetailCodeManageService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sym.ccm.cca.service.CmmnCode;
import egovframework.com.sym.ccm.cca.service.CmmnCodeVO;
import egovframework.com.sym.ccm.cde.service.CmmnDetailCodeVO;

/**
 * 
 * 공통상세코드에 관한 요청을 받아 서비스 클래스로 요청을 전달하고 서비스클래스에서 처리한 결과를 웹 화면으로 전달을 위한 Controller를 정의한다
 */

@Controller
public class WhoyaEgovCcmCmmnDetailCodeManageController {

    @Resource(name = "WhoyaCmmnDetailCodeManageService")
    private WhoyaEgovCcmCmmnDetailCodeManageService cmmnDetailCodeManageService;
    
    @Resource(name = "WhoyaCmmnCodeManageService")
    private WhoyaEgovCcmCmmnCodeManageService cmmnCodeManageService;
    
    /**
     * 공통상세코드 화면
     * @return ModelAndView
     */
    @RequestMapping(value = "/whoya/sym/ccm/cde/EgovCcmCmmnDetailCodeList.do")
    public ModelAndView selectCmmnDetailCodeList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("whoya/egovframework/com/sym/ccm/cde/EgovCcmCmmnDetailCodeList");
        return mav;
    }
    
    /**
     * 공통상세코드 목록을 조회한다.
     * @param loginVO
     * @param searchVO
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/sym/ccm/cde/EgovCcmCmmnDetailCodeListJSON.do")
    public @ResponseBody JSONObject selectCmmnDetailCodeListJSON(@ModelAttribute("searchVO") CmmnDetailCodeVO searchVO, ModelMap model) throws Exception {
        JSONObject resultList = new JSONObject();
        
        try {
            searchVO.setPageIndex(0);
            // EgovMap 형식으로 받음.
            List CmmnCodeList = cmmnDetailCodeManageService.selectCmmnDetailCodeList(searchVO);
            whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(CmmnCodeList));
            
            // 번호 컬럼 추가.
            // TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
            for ( int i = 0 ; i < list.size(); i++ ) {
                whoyaMap wmap = list.getMap(i);
                wmap.put("no", i + 1);
            }
            
            // 순번,코드ID,코드,코드명,사용여부
            resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,codeId,code,codeNm,useAt"));
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
     * 공통상세코드를 등록한다.
     * @param loginVO
     * @param cmmnDetailCode
     * @param cmmnCode
     * @param bindingResult
     * @param model
     * @return JSONObject
     * @throws Exception
     */
      @RequestMapping(value="/whoya/sym/ccm/cde/EgovCcmCmmnDetailCodeRegist.do")
    public @ResponseBody JSONObject insertCmmnDetailCode(@ModelAttribute("cmmnDetailCode") CmmnDetailCode cmmnDetailCode, @ModelAttribute("cmmnCode") CmmnCode cmmnCode, Map commandMap, ModelMap model) throws Exception {
          JSONObject resultJSON = new JSONObject();
          try {
              // 로그인 객체 선언
              LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
              
              cmmnDetailCode.setFrstRegisterId(loginVO.getUniqId());
              cmmnDetailCodeManageService.insertCmmnDetailCode(cmmnDetailCode);
              
              resultJSON.put("status", commonReturn.SUCCESS);
          } catch ( Exception e ) {
              e.printStackTrace();
              resultJSON.put("status", commonReturn.FAIL);
          }
          
          return resultJSON;
    }
      
    /**
     * 공통상세코드 상세항목을 조회한다.
     * @param loginVO
     * @param cmmnDetailCode
     * @param model
     * @return CmmnDetailCode
     * @throws Exception
     */
    @RequestMapping(value="/whoya/sym/ccm/cde/EgovCcmCmmnDetailCodeDetail.do")
    public @ResponseBody CmmnDetailCode selectCmmnDetailCodeDetail (CmmnDetailCode cmmnDetailCode, ModelMap model) throws Exception {
        try {
            CmmnDetailCode vo = cmmnDetailCodeManageService.selectCmmnDetailCodeDetail(cmmnDetailCode);
            return vo;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 공통상세코드를 수정한다.
     * @param loginVO
     * @param cmmnDetailCode
     * @param bindingResult
     * @param commandMap
     * @param model
     * @return JSONObject
     * @throws Exception
     */
      @RequestMapping(value="/whoya/sym/ccm/cde/EgovCcmCmmnDetailCodeModify.do")
    public @ResponseBody JSONObject updateCmmnDetailCode (@ModelAttribute("cmmnDetailCode") CmmnDetailCode cmmnDetailCode, Map commandMap, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        try {
            // 로그인 객체 선언
            LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
              
            cmmnDetailCode.setLastUpdusrId(loginVO.getUniqId());
            cmmnDetailCodeManageService.updateCmmnDetailCode(cmmnDetailCode);

            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
          
        return resultJSON;
    }
    
    /**
     * 공통상세코드를 삭제한다.
     * @param loginVO
     * @param cmmnDetailCode
     * @param model
     * @return JSONObject
     * @throws Exception
     */
      @RequestMapping(value="/whoya/sym/ccm/cde/EgovCcmCmmnDetailCodeRemove.do")
    public @ResponseBody JSONObject deleteCmmnDetailCode (CmmnDetailCode cmmnDetailCode, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        try {
            cmmnDetailCodeManageService.deleteCmmnDetailCode(cmmnDetailCode);

            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
    
        return resultJSON;
    }
      
    /**
     * 공통코드 목록을 조회한다.
     * @param loginVO
     * @param cmmnCode
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/sym/ccm/cca/selectCmmnCodeList.do")
    public @ResponseBody JSONObject selectCmmnCodeList(CmmnCode cmmnCode, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        try {
            CmmnCodeVO searchVO;
            searchVO = new CmmnCodeVO();
            searchVO.setRecordCountPerPage(999999);
            searchVO.setFirstIndex(0);
            searchVO.setSearchCondition("clCode");
            searchVO.setSearchKeyword(cmmnCode.getClCode());
            List CmmnCodeList = cmmnCodeManageService.selectCmmnCodeList(searchVO);
            
            resultJSON.put("list", CmmnCodeList);
            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
        
        return resultJSON;
    }
}