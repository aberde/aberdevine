package whoya.egovframework.com.uss.olp.qtm.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import whoya.whoyaLib;
import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.common.Common;
import whoya.common.commonReturn;
import whoya.egovframework.com.uss.olp.qtm.service.WhoyaEgovQustnrTmplatManageService;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.olp.qtm.service.QustnrTmplatManageVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
/**
 * 설문템플릿 Controller Class 구현
 */

@Controller
public class WhoyaEgovQustnrTmplatManageController {
	
	@Resource(name = "whoyaEgovQustnrTmplatManageService")
	private WhoyaEgovQustnrTmplatManageService egovQustnrTmplatManageService;
	
	/**
	 * 설문템플릿관리 화면
	 * @return ModelAndView
	 */
	@RequestMapping(value="/whoya/uss/olp/qtm/EgovQustnrTmplatManageList.do")
	public ModelAndView egovQustnrTmplatManageList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/egovframework/com/uss/olp/qtm/EgovQustnrTmplatManageList");
		return mav;
	}
	
	/**
	 * 설문템플릿 목록을 조회한다.
	 * @param searchVO
	 * @param commandMap
	 * @param qustnrTmplatManageVO
	 * @param model
	 * @return JSONObject
	 * @throws Exception
	 */
	@RequestMapping(value="/whoya/uss/olp/qtm/EgovQustnrTmplatManageListJSON.do")
	public @ResponseBody JSONObject egovQustnrTmplatManageListJSON(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, QustnrTmplatManageVO qustnrTmplatManageVO, ModelMap model, HttpServletRequest request) throws Exception {
		JSONObject resultList = new JSONObject();
		
		try {
			searchVO.setPageIndex(0);
			// EgovMap 형식으로 받음.
			List sampleList = egovQustnrTmplatManageService.selectQustnrTmplatManageList(searchVO);
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(sampleList));
			
			// 롤 정보 컬럼 추가.(이미지 링크걸기.)
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap wmap = list.getMap(i);
				wmap.put("no", i + 1);
				wmap.put("tmplatImg", "../../../uss/olp/qtm/EgovQustnrTmplatManageImg.do?qestnrTmplatId=" + wmap.get("qestnrTmplatId") + "^" + wmap.get("qestnrTmplatTy") + "템플릿이미지");
			}
						
			// 순번,템플릿유형,템플릿유형이미지정보,템플릿설명,작성자명,등록일자,설문템플릿 아이디
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,qestnrTmplatTy,tmplatImg,qestnrTmplatCn,frstRegisterNm,frstRegisterPnttm,qestnrTmplatId"));
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
	 * 설문템플릿를 등록 처리 한다.  / 등록처리
	 * @param multiRequest
	 * @param searchVO
	 * @param qustnrTmplatManageVO
	 * @param model
	 * @return JSONObject
	 * @throws Exception
	 */
	@RequestMapping(value="/whoya/uss/olp/qtm/EgovQustnrTmplatManageRegistActor.do")
	public @ResponseBody JSONObject qustnrTmplatManageRegistActor(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") ComDefaultVO searchVO, QustnrTmplatManageVO qustnrTmplatManageVO, ModelMap model) throws Exception {
		JSONObject resultJSON = new JSONObject();
		
		try {
			//로그인 객체 선언
			LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

			//아이디 설정
			qustnrTmplatManageVO.setFrstRegisterId((String)loginVO.getUniqId());
			qustnrTmplatManageVO.setLastUpdusrId((String)loginVO.getUniqId());

			final Map<String, MultipartFile> files = multiRequest.getFileMap();

			if (!files.isEmpty()) {
				for(MultipartFile file : files.values()){
					if(file.getName().equals("qestnrTmplatImage")){
						qustnrTmplatManageVO.setQestnrTmplatImagepathnm( file.getBytes() );
					}
				}
			}
			
			egovQustnrTmplatManageService.insertQustnrTmplatManage(qustnrTmplatManageVO);
		  	resultJSON.put("status", commonReturn.SUCCESS);
		} catch ( Exception e ) {
			e.printStackTrace();
			resultJSON.put("status", commonReturn.FAIL);
		}
		
		return resultJSON;
	}
	
	/**
	 * 설문템플릿 목록을 상세조회 조회한다.
	 * @param searchVO
	 * @param qustnrTmplatManageVO
	 * @param commandMap
	 * @param model
	 * @return EgovMap
	 * @throws Exception
	 */
	@RequestMapping(value="/whoya/uss/olp/qtm/EgovQustnrTmplatManageDetail.do")
	public @ResponseBody EgovMap egovQustnrTmplatManageDetail(@ModelAttribute("searchVO") ComDefaultVO searchVO, QustnrTmplatManageVO qustnrTmplatManageVO, Map commandMap, ModelMap model) throws Exception {
		try {
			EgovMap map = (EgovMap)egovQustnrTmplatManageService.selectQustnrTmplatManageDetail(qustnrTmplatManageVO).get(0);
			map.put("qestnrTmplatImagepathnm", "");
			return map;
		} catch ( Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 설문템플릿를 삭제 처리 한다.
	 * @param multiRequest
	 * @param searchVO
	 * @param qustnrTmplatManageVO
	 * @param model
	 * @return JSONObject
	 * @throws Exception
	 */
	@RequestMapping(value="/whoya/uss/olp/qtm/EgovQustnrTmplatManageDeleteActor.do")
	public @ResponseBody JSONObject qustnrTmplatManageDeleteActor(QustnrTmplatManageVO qustnrTmplatManageVO, ModelMap model) throws Exception {
		JSONObject resultJSON = new JSONObject();
		
		try {
			egovQustnrTmplatManageService.deleteQustnrTmplatManage(qustnrTmplatManageVO);
		  	resultJSON.put("status", commonReturn.SUCCESS);
		} catch ( Exception e ) {
			e.printStackTrace();
			resultJSON.put("status", commonReturn.FAIL);
		}
		
		return resultJSON;
	}
	
	/**
	 * 설문템플릿를 수정처리 한다.
	 * @param multiRequest
	 * @param searchVO
	 * @param commandMap
	 * @param qustnrTmplatManageVO
	 * @param bindingResult
	 * @param model
	 * @return JSONObject
	 * @throws Exception
	 */
	@RequestMapping(value="/whoya/uss/olp/qtm/EgovQustnrTmplatManageModifyActor.do")
	public @ResponseBody JSONObject qustnrTmplatManageModifyActor(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, @ModelAttribute("qustnrTmplatManageVO") QustnrTmplatManageVO qustnrTmplatManageVO, ModelMap model) throws Exception {
		JSONObject resultJSON = new JSONObject();
		
		try {
			//로그인 객체 선언
			LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

			//아이디 설정
			qustnrTmplatManageVO.setFrstRegisterId((String)loginVO.getUniqId());
			qustnrTmplatManageVO.setLastUpdusrId((String)loginVO.getUniqId());

			final Map<String, MultipartFile> files = multiRequest.getFileMap();

			if (!files.isEmpty()) {
				for(MultipartFile file : files.values()){
					// 파일 수정여부 확인
					if(file.getOriginalFilename() != "") {
						if(file.getName().equals("qestnrTmplatImage")){
							qustnrTmplatManageVO.setQestnrTmplatImagepathnm( file.getBytes() );
						}
					}
				}
			}
	    	egovQustnrTmplatManageService.updateQustnrTmplatManage(qustnrTmplatManageVO);
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
//    @RequestMapping(value="/uss/olp/qtm/EgovQustnrTmplatManageMain.do")
//    public String egovQustnrTmplatManageMain(ModelMap model) throws Exception {
//    	return "egovframework/com/uss/olp/qtm/EgovQustnrTmplatManageMain";
//    }
//
//    @RequestMapping(value="/uss/olp/qtm/EgovQustnrTmplatManageLeft.do")
//    public String egovQustnrTmplatManageLeft(ModelMap model) throws Exception {
//    	return "egovframework/com/uss/olp/qtm/EgovQustnrTmplatManageLeft";
//    }
//
//    /**
//     * 개별 배포시 메인메뉴를 조회한다.
//     * @param model
//     * @return	"/uss/sam/cpy/"
//     * @throws Exception
//     */
//    @RequestMapping(value="/uss/olp/EgovMain.do")
//    public String egovMain(ModelMap model) throws Exception {
//    	return "egovframework/com/uss/olp/qtm/EgovMain";
//    }
//
//    /**
//     * 메뉴를 조회한다.
//     * @param model
//     * @return	"/uss/sam/cpy/EgovLeft"
//     * @throws Exception
//     */
//    @RequestMapping(value="/uss/olp/EgovLeft.do")
//    public String egovLeft(ModelMap model) throws Exception {
//    	return "egovframework/com/uss/olp/qtm/EgovLeft";
//    }
//
//	/**
//	 * 설문템플릿 이미지 목록을 상세조회 조회한다.
//	 * @param request
//	 * @param response
//	 * @param qustnrTmplatManageVO
//	 * @param commandMap
//	 * @return "egovframework/com/uss/olp/qtm/EgovQustnrTmplatManageImg"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/uss/olp/qtm/EgovQustnrTmplatManageImg.do")
//	public void egovQustnrTmplatManageImg(
//			 HttpServletRequest request,
//			 HttpServletResponse response,
//			 QustnrTmplatManageVO qustnrTmplatManageVO,
//			 Map commandMap
//			 )throws Exception {
//
//		Map mapResult = egovQustnrTmplatManageService.selectQustnrTmplatManageTmplatImagepathnm(qustnrTmplatManageVO);
//
//		byte[] img = (byte[])mapResult.get("QUSTNR_TMPLAT_IMAGE_INFOPATHNM");
//
//		String imgtype = "jpeg";
//		String type = "";
//
//		if(imgtype != null && !"".equals(imgtype)){
//		      type="image/"+imgtype;
//		}
//
//		response.setHeader("Content-Type", imgtype);
//		response.setHeader ("Content-Length", "" + img.length);
//		response.getOutputStream().write(img);
//		response.getOutputStream().flush();
//		response.getOutputStream().close();
//	}
//
//	/**
//	 * 설문템플릿를 수정한다.
//	 * @param searchVO
//	 * @param commandMap
//	 * @param qustnrTmplatManageVO
//	 * @param model
//	 * @return "egovframework/com/uss/olp/qtm/EgovQustnrTmplatManageModify"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/uss/olp/qtm/EgovQustnrTmplatManageModify.do")
//	public String qustnrTmplatManageModify(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO,
//			Map commandMap,
//			QustnrTmplatManageVO qustnrTmplatManageVO,
//    		ModelMap model)
//    throws Exception {
//		String sLocationUrl = "egovframework/com/uss/olp/qtm/EgovQustnrTmplatManageModify";
//
//		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
//
//        List sampleList = egovQustnrTmplatManageService.selectQustnrTmplatManageDetail(qustnrTmplatManageVO);
//        model.addAttribute("resultList", sampleList);
//
//
//		return sLocationUrl;
//	}
//
//	/**
//	 * 설문템플릿를 등록한다. / 초기등록페이지
//	 * @param searchVO
//	 * @param commandMap
//	 * @param qustnrTmplatManageVO
//	 * @param model
//	 * @return "egovframework/com/uss/olp/qtm/EgovQustnrTmplatManageRegist"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/uss/olp/qtm/EgovQustnrTmplatManageRegist.do")
//	public String qustnrTmplatManageRegist(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO,
//			Map commandMap,
//			@ModelAttribute("qustnrTmplatManageVO") QustnrTmplatManageVO qustnrTmplatManageVO,
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
//
//		String sLocationUrl = "egovframework/com/uss/olp/qtm/EgovQustnrTmplatManageRegist";
//
//		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
//		log.info("cmd =>" + sCmd);
//
//		//아이디 설정
//		qustnrTmplatManageVO.setFrstRegisterId((String)loginVO.getUniqId());
//		qustnrTmplatManageVO.setLastUpdusrId((String)loginVO.getUniqId());
//
//		return sLocationUrl;
//	}
//

}


