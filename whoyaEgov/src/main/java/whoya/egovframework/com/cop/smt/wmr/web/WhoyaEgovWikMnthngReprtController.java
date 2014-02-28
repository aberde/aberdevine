package whoya.egovframework.com.cop.smt.wmr.web;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
import whoya.egovframework.com.cop.smt.wmr.service.WhoyaEgovWikMnthngReprtService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.smt.wmr.service.ReportrVO;
import egovframework.com.cop.smt.wmr.service.WikMnthngReprt;
import egovframework.com.cop.smt.wmr.service.WikMnthngReprtVO;

/**
 * 개요
 * - 주간월간보고에 대한 controller 클래스를 정의한다.
 * 
 * 상세내용
 * - 주간월간보고에 대한 등록, 수정, 삭제, 조회, 승인기능을 제공한다.
 * - 주간월간보고의 조회기능은 목록조회, 상세조회로 구분된다.
 */

@Controller
public class WhoyaEgovWikMnthngReprtController {

	@Resource(name="WhoyaEgovWikMnthngReprtService")
	protected WhoyaEgovWikMnthngReprtService wikMnthngReprtService;

	@Resource(name="EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;

	// 첨부파일 관련 
	@Resource(name="EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	/**
	 * 주간/월간보고관리 화면
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/whoya/cop/smt/wmr/selectWikMnthngReprtList.do")
	public ModelAndView selectWikMnthngReprtList() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/egovframework/com/cop/smt/wmr/EgovWikMnthngReprtList");
		return mav;
	}
	
	/**
	 * 주간월간보고 정보에 대한 목록을 조회한다.
	 * @param WikMnthngReprtVO - 주간월간보고 VO
	 * @return  String - 리턴 URL
	 * 
	 * @param wikMnthngReprtVO
	 */
    @RequestMapping("/whoya/cop/smt/wmr/selectWikMnthngReprtListJSON.do")
	public @ResponseBody JSONObject selectWikMnthngReprtListJSON(@ModelAttribute("searchVO") WikMnthngReprtVO wikMnthngReprtVO, ModelMap model) throws Exception{
    	JSONObject resultList = new JSONObject();
		
		try {
			//로그인 객체 선언
			LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			wikMnthngReprtVO.setSearchId(loginVO.getUniqId());
			wikMnthngReprtVO.setPageIndex(0);
			Map<String, Object> map = wikMnthngReprtService.selectWikMnthngReprtList(wikMnthngReprtVO);
			List<WikMnthngReprtVO> voList = (List<WikMnthngReprtVO>)map.get("resultList");
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(voList));
			
			// 번호 컬럼 추가.
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap wmap = list.getMap(i);
				wmap.put("no", i + 1);
				wmap.put("reprtBgnEnd", wmap.get("reprtBgnDe") + " ~ " + wmap.get("reprtEndDe"));
			}

			// 번호,보고유형,보고일자,보고서제목,해당일자,작성자,승인,보고서ID
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,reprtSe,reprtDe,reprtSj,reprtBgnEnd,wrterNm,confmDt,reprtId"));
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
	 * 보고자 정보에 대한 목록을 조회한다.
	 * @param ReportrVO
	 * @return  String
	 * 
	 * @param reportrVO
	 */
	@RequestMapping("/whoya/cop/smt/wmr/selectReportrList.do")
	public @ResponseBody JSONObject selectReportrList(@ModelAttribute("searchVO") ReportrVO reportrVO, ModelMap model) throws Exception{
		JSONObject resultList = new JSONObject();
		
		try {
			reportrVO.setPageIndex(0);
			Map<String, Object> map = wikMnthngReprtService.selectReportrList(reportrVO);
			List<ReportrVO> voList = (List<ReportrVO>)map.get("resultList");
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(voList));
			
			// 번호 컬럼 추가.
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap wmap = list.getMap(i);
				wmap.put("no", i + 1);
				wmap.put("selectLink", "../../../images/egovframework/com/cmm/icon/search.gif^선택^javascript:whoya.common.reportrSelect(\"" + wmap.get("uniqId") + "\", \"" + wmap.get("emplNo") + "\", \"" + wmap.get("emplyrNm") + "\", \"" + wmap.get("orgnztNm") + "\");^_self");
			}

			// 번호,부서,직위,사번,사원명,선택
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,orgnztNm,ofcpsNm,emplNo,emplyrNm,selectLink"));
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
	 * 주간월간보고 정보의 등록페이지로 이동한다.
	 * @param WikMnthngReprt - 주간월간보고 model
	 * @return  String - 리턴 URL
	 * 
	 * @param wikMnthngReprt
	 */
    @RequestMapping("/whoya/cop/smt/wmr/addWikMnthngReprt.do")
	public @ResponseBody WikMnthngReprtVO addWikMnthngReprt(@ModelAttribute("wikMnthngReprtVO") WikMnthngReprtVO wikMnthngReprtVO, ModelMap model) throws Exception{
    	try {
	    	// 1. 로그인 객체 선언
			LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			
	    	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.KOREA);
			wikMnthngReprtVO.setReprtDe(formatter.format(new java.util.Date()));
	    	wikMnthngReprtVO.setWrterId(loginVO.getUniqId());
	    	wikMnthngReprtVO.setWrterNm(loginVO.getName());
	    	wikMnthngReprtVO.setWrterClsfNm(wikMnthngReprtService.selectWrterClsfNm(loginVO.getUniqId()));
    	} catch ( Exception e ) {
    		e.printStackTrace();
    		throw e;
    	}
    	
    	return wikMnthngReprtVO; 	
	}
    
	/**
	 * 주간월간보고 정보를 등록한다.
	 * @param WikMnthngReprt - 주간월간보고 model
	 * @return  JSONObject
	 * 
	 * @param wikMnthngReprt
	 */
    @RequestMapping("/whoya/cop/smt/wmr/insertWikMnthngReprt.do")
	public @ResponseBody JSONObject insertWikMnthngReprt(final MultipartHttpServletRequest multiRequest, @ModelAttribute("wikMnthngReprtVO") WikMnthngReprtVO wikMnthngReprtVO, BindingResult bindingResult, ModelMap model) throws Exception{
    	JSONObject resultJSON = new JSONObject();
		
		try {
			// 첨부파일 관련 첨부파일ID 생성
			List<FileVO> _result = null;
			String _atchFileId = "";
			
			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			
			if(!files.isEmpty()){
			 _result = fileUtil.parseFileInf(files, "DSCH_", 0, "", ""); 
			 _atchFileId = fileMngService.insertFileInfs(_result);  //파일이 생성되고나면 생성된 첨부파일 ID를 리턴한다.
			}
			
	    	// 리턴받은 첨부파일ID를 셋팅한다..
			wikMnthngReprtVO.setAtchFileId(_atchFileId);			// 첨부파일 ID
			
			// 1. 로그인 객체 선언
			LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			//아이디 설정
			wikMnthngReprtVO.setFrstRegisterId((String)loginVO.getUniqId());
			wikMnthngReprtVO.setLastUpdusrId((String)loginVO.getUniqId());
			
			wikMnthngReprtService.insertWikMnthngReprt(wikMnthngReprtVO);
		  	resultJSON.put("status", commonReturn.SUCCESS);
		} catch ( Exception e ) {
			e.printStackTrace();
			resultJSON.put("status", commonReturn.FAIL);
		}
		
		return resultJSON;
	}
  
    /**
	 * 주간월간보고 정보를 조회한다.
	 * @param WikMnthngReprtVO - 주간월간보고 VO
	 * @return  String - 리턴 URL
	 * 
	 * @param wikMnthngReprtVO
	 */
    @RequestMapping("/whoya/cop/smt/wmr/selectWikMnthngReprt.do")
	public @ResponseBody WikMnthngReprt selectWikMnthngReprt(@ModelAttribute("wikMnthngReprtVO") WikMnthngReprtVO wikMnthngReprtVO, ModelMap model) throws Exception{
    	try {
    		WikMnthngReprt wikMnthngReprt = wikMnthngReprtService.selectWikMnthngReprt(wikMnthngReprtVO);
    		return wikMnthngReprt;
    	} catch ( Exception e ) {
    		e.printStackTrace();
    		throw e;
    	}
    }
    
	/**
	 * 주간월간보고 정보를 삭제한다.
	 * @param WikMnthngReprt - 주간월간보고 model
	 * @return  JSONObject
	 * 
	 * @param wikMnthngReprt
	 */
    @RequestMapping("/whoya/cop/smt/wmr/deleteWikMnthngReprt.do")
	public @ResponseBody JSONObject deleteWikMnthngReprt(@ModelAttribute("wikMnthngReprtVO") WikMnthngReprtVO wikMnthngReprtVO, ModelMap model) throws Exception{
    	JSONObject resultJSON = new JSONObject();
		
		try {
			// 첨부파일 삭제를 위한 ID 생성 start....
			String _atchFileId = wikMnthngReprtVO.getAtchFileId();	
	    	
			// 첨부파일을 삭제하기 위한  Vo
	    	FileVO fvo = new FileVO();
	    	fvo.setAtchFileId(_atchFileId);
	    			
	    	fileMngService.deleteAllFileInf(fvo);
	    	// 첨부파일 삭제 End.............
	    	
	    	wikMnthngReprtService.deleteWikMnthngReprt(wikMnthngReprtVO);
		  	resultJSON.put("status", commonReturn.SUCCESS);
		} catch ( Exception e ) {
			e.printStackTrace();
			resultJSON.put("status", commonReturn.FAIL);
		}
		
		return resultJSON;
	}
  
	/**
	 * 주간월간보고 정보를 수정한다.
	 * @param WikMnthngReprt - 주간월간보고 model
	 * @return  JSONObject
	 * 
	 * @param wikMnthngReprt
	 */
    @RequestMapping("/whoya/cop/smt/wmr/updateWikMnthngReprt.do")
	public @ResponseBody JSONObject updateWikMnthngReprt(final MultipartHttpServletRequest multiRequest, Map commandMap, @ModelAttribute("wikMnthngReprtVO") WikMnthngReprtVO wikMnthngReprtVO, BindingResult bindingResult, ModelMap model) throws Exception{
    	JSONObject resultJSON = new JSONObject();
		
		try {
			/* *****************************************************************
	      	// 첨부파일 관련 ID 생성 start....
			****************************************************************** */
	  		String _atchFileId = wikMnthngReprtVO.getAtchFileId();	
	  		
	  		
	  		final Map<String, MultipartFile> files = multiRequest.getFileMap();
	  		
	  		if(!files.isEmpty()){
	  			String atchFileAt = commandMap.get("atchFileAt") == null ? "" : (String)commandMap.get("atchFileAt");
	  			if("N".equals(atchFileAt)){
	  				List<FileVO> _result = fileUtil.parseFileInf(files, "DSCH_", 0, _atchFileId, "");	
	  				_atchFileId = fileMngService.insertFileInfs(_result);
	  							
	  				// 첨부파일 ID 셋팅
	  				wikMnthngReprtVO.setAtchFileId(_atchFileId);    	// 첨부파일 ID
	  				
	  			}else{
	  				FileVO fvo = new FileVO();
	  				fvo.setAtchFileId(_atchFileId);
	  				int _cnt = fileMngService.getMaxFileSN(fvo);
	  				List<FileVO> _result = fileUtil.parseFileInf(files, "DSCH_", _cnt, _atchFileId, "");	
	  				fileMngService.updateFileInfs(_result);
	  			}
	  		}
	  		
	  		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	  		wikMnthngReprtVO.setLastUpdusrId(user.getUniqId());		    
	  		wikMnthngReprtService.updateWikMnthngReprt(wikMnthngReprtVO);
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
//	@Resource(name="EgovCmmUseService")
//	private EgovCmmUseService cmmUseService;
//	
//	@Resource(name="propertiesService")
//    protected EgovPropertyService propertyService;
//    
//    @Resource(name="egovMessageSource")
//    EgovMessageSource egovMessageSource;
//    
//    @Autowired
//    private DefaultBeanValidator beanValidator;
//    
//	 
//    
//    //Logger log = Logger.getLogger(this.getClass());
//    
//    /**
//	 * 보고자 정보에 대한 팝업 목록을 조회한다.
//	 * @param ReportrVO
//	 * @return  String
//	 * 
//	 * @param reportrVO
//	 */
//	@RequestMapping("/cop/smt/wmr/selectReportrListPopup.do")
//	public String selectReportrListPopup(@ModelAttribute("searchVO") ReportrVO reportrVO, ModelMap model) throws Exception{
//		return "egovframework/com/cop/smt/wmr/EgovReportrListPopup";
//	}
//
//	/**
//	 * 주간월간보고 정보의 수정페이지로 이동한다.
//	 * @param WikMnthngReprt - 주간월간보고 model
//	 * @return  String - 리턴 URL
//	 * 
//	 * @param wikMnthngReprt
//	 */
//    @RequestMapping("/cop/smt/wmr/modifyWikMnthngReprt.do")
//	public String modifyWikMnthngReprt(@ModelAttribute("wikMnthngReprtVO") WikMnthngReprtVO wikMnthngReprtVO, BindingResult bindingResult, ModelMap model) throws Exception{
//    	// 0. Spring Security 사용자권한 처리
//    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
//        	return "egovframework/com/uat/uia/EgovLoginUsr";
//    	}
//		
//    	WikMnthngReprtVO resultVO = wikMnthngReprtService.selectWikMnthngReprt(wikMnthngReprtVO);
//		resultVO.setSearchCnd(wikMnthngReprtVO.getSearchCnd());
//		resultVO.setSearchWrd(wikMnthngReprtVO.getSearchWrd());
//		resultVO.setSearchDe(wikMnthngReprtVO.getSearchDe());
//		resultVO.setSearchBgnDe(wikMnthngReprtVO.getSearchBgnDe());
//		resultVO.setSearchEndDe(wikMnthngReprtVO.getSearchEndDe());
//		resultVO.setSearchSttus(wikMnthngReprtVO.getSearchSttus());
//		resultVO.setPageIndex(wikMnthngReprtVO.getPageIndex());
//        model.addAttribute("wikMnthngReprtVO", resultVO);
//        
//		return "egovframework/com/cop/smt/wmr/EgovWikMnthngReprtUpdt";
//	}
//    
//
//	/**
//	 * 주간월간보고 정보를 승인한다.
//	 * @param WikMnthngReprt - 주간월간보고 model
//	 * @return  String - 리턴 URL
//	 * 
//	 * @param wikMnthngReprt
//	 */
//    @RequestMapping("/cop/smt/wmr/confirmWikMnthngReprt.do")
//	public String confirmWikMnthngReprt(@ModelAttribute("wikMnthngReprtVO") WikMnthngReprtVO wikMnthngReprtVO, ModelMap model) throws Exception{
//    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//		if (isAuthenticated) {	    
//    		wikMnthngReprtService.confirmWikMnthngReprt(wikMnthngReprtVO);
//		}
//
//		return "forward:/cop/smt/wmr/selectWikMnthngReprtList.do";
//	}

}