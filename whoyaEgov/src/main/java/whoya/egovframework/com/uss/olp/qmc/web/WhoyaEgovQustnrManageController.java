package whoya.egovframework.com.uss.olp.qmc.web;

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
import whoya.egovframework.com.uss.olp.qmc.service.WhoyaEgovQustnrManageService;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.olp.qmc.service.QustnrManageVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
/**
 * 설문관리를 처리하는 Controller Class 구현
 */ 

@Controller
public class WhoyaEgovQustnrManageController {
  
	@Resource(name = "whoyaEgovQustnrManageService")
	private WhoyaEgovQustnrManageService egovQustnrManageService;
  
	@Resource(name="EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	/**
	 * 설문관리 화면
	 * @return ModelAndView
	 */
	@RequestMapping(value="/whoya/uss/olp/qmc/EgovQustnrManageList.do")
	public ModelAndView egovQustnrManageList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/egovframework/com/uss/olp/qmc/EgovQustnrManageList");
		return mav;
	}
	
	/**
	 * 설문관리 목록을 조회한다. 
	 * @param searchVO
	 * @param commandMap
	 * @param qustnrManageVO
	 * @param model
	 * @return  JSONObject
	 * @throws Exception
	 */
	@RequestMapping(value="/whoya/uss/olp/qmc/EgovQustnrManageJSONList.do")
	public @ResponseBody JSONObject egovQustnrManageJSONList(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, QustnrManageVO qustnrManageVO, ModelMap model) throws Exception {
		JSONObject resultList = new JSONObject();
		
		try {
			searchVO.setPageIndex(0);
			// EgovMap 형식으로 받음.
			List sampleList = egovQustnrManageService.selectQustnrManageList(searchVO);
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(sampleList));
			
			// 롤 정보 컬럼 추가.(이미지 링크걸기.)
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap wmap = list.getMap(i);
				wmap.put("no", i + 1);
				wmap.put("qestnrDe", (String)wmap.get("qestnrBeginDe") + " ~ " + (String)wmap.get("qestnrEndDe"));
				wmap.put("qustnrQRM", "../../../images/egovframework/com/cmm/icon/search.gif^선택^javascript:whoya.common.qustnrManageSelect(\"" + wmap.get("qestnrId") + "\", \"" + wmap.get("qestnrTmplatId") + "\", \"QRM\");^_self");
				wmap.put("qustnrQQM", "../../../images/egovframework/com/cmm/icon/search.gif^선택^javascript:whoya.common.qustnrManageSelect(\"" + wmap.get("qestnrId") + "\", \"" + wmap.get("qestnrTmplatId") + "\", \"QQM\");^_self");
				wmap.put("qustnrQRI", "../../../images/egovframework/com/cmm/icon/search.gif^선택^javascript:whoya.common.qustnrManageSelect(\"" + wmap.get("qestnrId") + "\", \"" + wmap.get("qestnrTmplatId") + "\", \"QRI\");^_self");
				wmap.put("qustnrQST", "../../../images/egovframework/com/cmm/icon/search.gif^선택^javascript:whoya.common.qustnrManageStatisticsSelect(\"" + wmap.get("qestnrId") + "\", \"" + wmap.get("qestnrTmplatId") + "\");^_self");
			}
						
			// 순번,설문제목,설문기간,설문응답자정보,설문문항,설문조사,통계,등록자,등록일자,설문지ID
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,qestnrSj,qestnrDe,qustnrQRM,qustnrQQM,qustnrQRI,qustnrQST,frstRegisterNm,frstRegisterPnttm,qestnrId"));
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
	 * 설문대상 목록을 조회한다.
	 * @param boardMasterVO BoardMasterVO
	 * @param model ModelMap
	 * @return List<CmmnDetailCode>
	 * @exception Exception
	 */
	@RequestMapping(value="/whoya/uss/olp/qmc/selectQestnrTrgetList.do", headers="Accept=application/json")
	public @ResponseBody List<CmmnDetailCode> selectQestnrTrgetList(ComDefaultCodeVO comDefaultCodeVO) throws Exception {
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
	 * 템플릿 유형 목록을 조회한다.
	 * @param boardMasterVO BoardMasterVO
	 * @param model ModelMap
	 * @return List<EgovMap>
	 * @exception Exception
	 */
	@RequestMapping(value="/whoya/uss/olp/qmc/selectQustnrTmplatList.do", headers="Accept=application/json")
	public @ResponseBody List selectQustnrTmplatList(@ModelAttribute("qustnrManageVO") QustnrManageVO qustnrManageVO) throws Exception {
		List listQustnrTmplat = null;
		try {
			listQustnrTmplat = egovQustnrManageService.selectQustnrTmplatManageList(qustnrManageVO);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return listQustnrTmplat;
	}
	
	/**
	 * 설문관리를 등록한다. 
	 * @param searchVO
	 * @param commandMap
	 * @param qustnrManageVO
	 * @param bindingResult
	 * @param model
	 * @return JSONObject
	 * @throws Exception
	 */
	@RequestMapping(value="/whoya/uss/olp/qmc/EgovQustnrManageRegist.do")
	public @ResponseBody JSONObject qustnrManageRegist(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, @ModelAttribute("qustnrManageVO") QustnrManageVO qustnrManageVO, ModelMap model) throws Exception {
		JSONObject result = new JSONObject();
		try {
			//로그인 객체 선언
			LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			//아이디 설정
			qustnrManageVO.setFrstRegisterId((String)loginVO.getUniqId());
			qustnrManageVO.setLastUpdusrId((String)loginVO.getUniqId());
			
			egovQustnrManageService.insertQustnrManage(qustnrManageVO);
			
			result.put("status", commonReturn.SUCCESS);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.put("status", commonReturn.FAIL);
		}
		
		return result;
	}
	
	/**
	 * 설문관리 목록을 상세조회 조회한다. 
	 * @param searchVO
	 * @param qustnrManageVO
	 * @param commandMap
	 * @param model
	 * @return "egovframework/com/uss/olp/qmc/EgovQustnrManageDetail"; 
	 * @throws Exception
	 */
	@RequestMapping(value="/whoya/uss/olp/qmc/EgovQustnrManageDetail.do")
	public @ResponseBody EgovMap egovQustnrManageDetail(@ModelAttribute("searchVO") ComDefaultVO searchVO, QustnrManageVO qustnrManageVO, Map commandMap, ModelMap model) throws Exception {
		try {
			EgovMap map = (EgovMap)egovQustnrManageService.selectQustnrManageDetail(qustnrManageVO).get(0);
			return map;
		} catch ( Exception e ) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	/**
	 * 설문관리를 수정한다. 
	 * @param searchVO
	 * @param commandMap
	 * @param qustnrManageVO
	 * @param bindingResult
	 * @param model
	 * @return JSONObject
	 * @throws Exception
	 */
	@RequestMapping(value="/whoya/uss/olp/qmc/EgovQustnrManageModify.do")
	public @ResponseBody JSONObject qustnrManageModify(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, QustnrManageVO qustnrManageVO, ModelMap model) throws Exception {
    	JSONObject result = new JSONObject();
		try {
			//로그인 객체 선언
			LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			
			//아이디 설정
			qustnrManageVO.setFrstRegisterId((String)loginVO.getUniqId());
			qustnrManageVO.setLastUpdusrId((String)loginVO.getUniqId());
			
	    	egovQustnrManageService.updateQustnrManage(qustnrManageVO);
			
			result.put("status", commonReturn.SUCCESS);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.put("status", commonReturn.FAIL);
		}
		
		return result;
	}
	
	/**
	 * 설문관리를 삭제한다. 
	 * @param searchVO
	 * @param commandMap
	 * @param qustnrManageVO
	 * @param bindingResult
	 * @param model
	 * @return JSONObject
	 * @throws Exception
	 */
	@RequestMapping(value="/whoya/uss/olp/qmc/EgovQustnrManageDelete.do")
	public @ResponseBody JSONObject qustnrManageDelete(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, QustnrManageVO qustnrManageVO, ModelMap model) throws Exception {
		JSONObject result = new JSONObject();
		try {
			egovQustnrManageService.deleteQustnrManage(qustnrManageVO);
			
			result.put("status", commonReturn.SUCCESS);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.put("status", commonReturn.FAIL);
		}
		
		return result;
	}
  
	/**
	 * 설문관리 팝업 목록을 조회한다. 
	 * @param searchVO
	 * @param commandMap
	 * @param qustnrManageVO
	 * @param model
	 * @return JSONObject
	 * @throws Exception
	 */
	@RequestMapping(value="/whoya/uss/olp/qmc/EgovQustnrManageListPopup.do")
	public @ResponseBody JSONObject egovQustnrManageListPopup(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, QustnrManageVO qustnrManageVO, ModelMap model) throws Exception {
		JSONObject resultList = new JSONObject();
		
		try {
			searchVO.setPageIndex(0);
			// EgovMap 형식으로 받음.
			List sampleList = egovQustnrManageService.selectQustnrManageList(searchVO);
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(sampleList));
			
			// 롤 정보 컬럼 추가.(이미지 링크걸기.)
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap wmap = list.getMap(i);
				wmap.put("no", i + 1);
				wmap.put("qestnrDe", (String)wmap.get("qestnrBeginDe") + " ~ " + (String)wmap.get("qestnrEndDe"));
				wmap.put("selectLink", "../../../images/egovframework/com/cmm/icon/search.gif^선택^javascript:whoya.common.qestnrSelect(\"" + wmap.get("qestnrId") + "\", \"" + wmap.get("qestnrTmplatId") + "\", \"" + wmap.get("qestnrSj") + "\");^_self");
			}
						
			// 번호,설문제목,설문기간,등록자,등록일자,선택
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,qestnrSj,qestnrDe,frstRegisterNm,frstRegisterPnttm,selectLink"));
		 	resultList.put("status", commonReturn.SUCCESS);
		 	resultList.put("message", "조회되었습니다");
		} catch(Exception e) {
			e.printStackTrace();
			resultList.put("status", commonReturn.FAIL);
			resultList.put("message", e.getMessage());
		}
		
		return resultList;
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
}


