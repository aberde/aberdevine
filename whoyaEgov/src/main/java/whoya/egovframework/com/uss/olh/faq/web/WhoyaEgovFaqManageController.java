package whoya.egovframework.com.uss.olh.faq.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import whoya.whoyaLib;
import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.common.Common;
import whoya.common.commonReturn;
import whoya.egovframework.com.uss.olh.faq.service.WhoyaEgovFaqManageService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.olh.faq.service.FaqManageDefaultVO;
import egovframework.com.uss.olh.faq.service.FaqManageVO;
 
/**
 * FAQ내용을 처리하는 비즈니스 구현 클래스
 */

@Controller
public class WhoyaEgovFaqManageController {
	
	@Resource(name = "WhoyaFaqManageService")
	private WhoyaEgovFaqManageService faqManageService;
	 
	@Resource(name="EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;
	
	// 첨부파일 관련 
	@Resource(name="EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	/**
	 * FAQ관리 화면
	 * @return ModelAndView
	 */
	@RequestMapping(value="/whoya/uss/olh/faq/FaqListInqire.do")
	public ModelAndView selectFaqList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/egovframework/com/uss/olh/faq/EgovFaqListInqire");
		return mav;
	}
    
	/**
	 * FAQ 목록을 조회한다.
	 * @param searchVO
	 * @param model
	 * @return	JSONObject
	 * @throws Exception
	 */
	@RequestMapping(value="/whoya/uss/olh/faq/FaqListInqireJSON.do")
	public @ResponseBody JSONObject selectFaqListJSON(@ModelAttribute("searchVO") FaqManageDefaultVO searchVO, ModelMap model) throws Exception {
		JSONObject resultList = new JSONObject();
		
		try {
			searchVO.setPageIndex(0);
			// EgovMap 형식으로 받음.
			List FaqList = faqManageService.selectFaqList(searchVO);
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(FaqList));
			
			// 번호 컬럼 추가.
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine)  
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap wmap = list.getMap(i);
				wmap.put("no", i + 1);
			}
						
			// 순번,질문제목,조회수,등록일자,FAQ ID
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,qestnSj,inqireCo,frstRegisterPnttm,faqId"));
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
     * FAQ를 등록한다.
     * @param multiRequest
     * @param searchVO
     * @param faqManageVO
     * @param bindingResult
     * @return	JSONObject
     * @throws Exception
     */
	@RequestMapping("/whoya/uss/olh/faq/FaqCnRegist.do")
	public @ResponseBody JSONObject insertFaqCn(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") FaqManageDefaultVO searchVO, @ModelAttribute("faqManageVO") FaqManageVO faqManageVO, BindingResult bindingResult) throws Exception {
		JSONObject resultJSON = new JSONObject();
		
		try {
		  	List<FileVO> _result = new ArrayList<FileVO>();	// 2012.11 KISA 보안조치
		  	String _atchFileId = "";
		  	final Map<String, MultipartFile> files = multiRequest.getFileMap();
		  	if(!files.isEmpty()){
	  			_result = fileUtil.parseFileInf(files, "FAQ_", 0, "", ""); 
	  			_atchFileId = fileMngService.insertFileInfs(_result);  //파일이 생성되고나면 생성된 첨부파일 ID를 리턴한다.
			}
		  	
		  	// 리턴받은 첨부파일ID를 셋팅한다..
		  	faqManageVO.setAtchFileId(_atchFileId);	
		  	
		  	// 로그인VO에서  사용자 정보 가져오기
		  	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		  	
		  	String	frstRegisterId = loginVO.getUniqId();
		  			        
		  	faqManageVO.setFrstRegisterId(frstRegisterId);		// 최초등록자ID
		  	faqManageVO.setLastUpdusrId(frstRegisterId);    	// 최종수정자ID
		  	            	    
		  	faqManageService.insertFaqCn(faqManageVO);
		  	resultJSON.put("status", commonReturn.SUCCESS);
		} catch ( Exception e ) {
			e.printStackTrace();
			resultJSON.put("status", commonReturn.FAIL);
		}
		
		return resultJSON;
	}
	
	/**
	 * FAQ 목록에 대한 상세정보를 조회한다.
	 * @param faqManageVO
	 * @param searchVO
	 * @param model
	 * @return FaqManageVO
	 * @throws Exception
	 */
	@RequestMapping("/whoya/uss/olh/faq/FaqListDetailInqire.do")
	public @ResponseBody FaqManageVO selectFaqListDetail(FaqManageVO faqManageVO, @ModelAttribute("searchVO") FaqManageDefaultVO searchVO, ModelMap model) throws Exception {
		// ##############################################
		// ## FAQ 조회수 증가
		// ##############################################
		// 로그인VO에서  사용자 정보 가져오기
    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	String	lastUpdusrId = loginVO.getUniqId();
    	faqManageVO.setLastUpdusrId(lastUpdusrId);    	// 최종수정자ID
    	faqManageService.updateFaqInqireCo(faqManageVO);
    	// ##############################################
		
		FaqManageVO vo = faqManageService.selectFaqListDetail(faqManageVO);
		return	vo;
	}
	
	/**
     * FAQ를 수정처리한다.           
     * @param atchFileAt
     * @param multiRequest
     * @param searchVO
     * @param faqManageVO
     * @param bindingResult
     * @param model
     * @return	JSONObject
     * @throws Exception
     */
	@RequestMapping("/whoya/uss/olh/faq/FaqCnUpdt.do")
	public @ResponseBody JSONObject updateFaqCn(@RequestParam("atchFileAt") String atchFileAt , final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") FaqManageDefaultVO searchVO, 
			@ModelAttribute("faqManageVO") FaqManageVO faqManageVO, BindingResult bindingResult, ModelMap model) throws Exception {
		JSONObject resultJSON = new JSONObject();
		
		try {
			// 첨부파일 관련 ID 생성 start....
			String _atchFileId = faqManageVO.getAtchFileId();	
			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			if(!files.isEmpty()){
				if("N".equals(atchFileAt)){
					List<FileVO> _result = fileUtil.parseFileInf(files, "FAQ_", 0, _atchFileId, "");	
					_atchFileId = fileMngService.insertFileInfs(_result);
								
					// 첨부파일 ID 셋팅
					faqManageVO.setAtchFileId(_atchFileId);    	// 첨부파일 ID
				}else{				
					FileVO fvo = new FileVO();
					fvo.setAtchFileId(_atchFileId);
					int _cnt = fileMngService.getMaxFileSN(fvo);
					List<FileVO> _result = fileUtil.parseFileInf(files, "FAQ_", _cnt, _atchFileId, "");	
					fileMngService.updateFileInfs(_result);
				}
			}	
			// 첨부파일 관련 ID 생성 end...
			
		  	// 로그인VO에서  사용자 정보 가져오기
		  	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		  	String	lastUpdusrId = loginVO.getUniqId();
		  	faqManageVO.setLastUpdusrId(lastUpdusrId);    	// 최종수정자ID
		  	faqManageService.updateFaqCn(faqManageVO);
		  	resultJSON.put("status", commonReturn.SUCCESS);
		} catch ( Exception e ) {
			e.printStackTrace();
			resultJSON.put("status", commonReturn.FAIL);
		}
		
		return resultJSON;
	}

	/**
     * FAQ를 삭제처리한다.               
     * @param faqManageVO
     * @param searchVO
     * @throws Exception
     */
	@RequestMapping("/whoya/uss/olh/faq/FaqCnDelete.do")
	public @ResponseBody void deleteFaqCn(FaqManageVO faqManageVO, @ModelAttribute("searchVO") FaqManageDefaultVO searchVO) throws Exception {
		try {
			// 첨부파일 삭제를 위한 ID 생성 start....
			String _atchFileId = faqManageVO.getAtchFileId();	
	  	
			faqManageService.deleteFaqCn(faqManageVO);
	  	    	
		  	// 첨부파일을 삭제하기 위한  Vo
		  	FileVO fvo = new FileVO();
		  	fvo.setAtchFileId(_atchFileId);
		  			
		  	fileMngService.deleteAllFileInf(fvo);
		  	// 첨부파일 삭제 End.............
		} catch ( Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}
//	 
//	 
//
//	protected Log log = LogFactory.getLog(this.getClass());
//    
//    /** EgovPropertyService */
//    @Resource(name = "propertiesService")
//    protected EgovPropertyService propertiesService;
//
//	/** EgovMessageSource */
//    @Resource(name="egovMessageSource")
//    EgovMessageSource egovMessageSource;
//
//    // Validation 관련
//	@Autowired
//	private DefaultBeanValidator beanValidator;
//	
//    /**
//     * 개별 배포시 메인메뉴를 조회한다.
//     * @param model
//     * @return	"/uss/olh/faq/"
//     * @throws Exception
//     */
//    @RequestMapping(value="/uss/olh/faq/EgovMain.do")
//    public String egovMain(ModelMap model) throws Exception {
//    	return "egovframework/com/uss/olh/faq/EgovMain";
//    }
//    
//    /**
//     * 메뉴를 조회한다.
//     * @param model
//     * @return	"/uss/olh/faq/EgovLeft"
//     * @throws Exception
//     */
//    @RequestMapping(value="/uss/olh/faq/EgovLeft.do")
//    public String egovLeft(ModelMap model) throws Exception {
//    	return "egovframework/com/uss/olh/faq/EgovLeft";
//    }
//
//    /**
//     * FAQ 조회수를  수정처리           
//     * @param faqManageVO
//     * @param searchVO
//     * @return	"forward:/uss/olh/faq/FaqListDetailInqire.do"
//     * @throws Exception
//     */
//    @RequestMapping("/uss/olh/faq/FaqInqireCoUpdt.do")
//    public String updateFaqInqireCo(
//            FaqManageVO faqManageVO,
//            @ModelAttribute("searchVO") FaqManageDefaultVO searchVO) 
//            throws Exception {
//
//    	// 로그인VO에서  사용자 정보 가져오기
//    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//    	
//    	String	lastUpdusrId = loginVO.getUniqId();
//    	    			        
//    	faqManageVO.setLastUpdusrId(lastUpdusrId);    	// 최종수정자ID
//    	
//    	faqManageService.updateFaqInqireCo(faqManageVO);
//            	        
//        return "forward:/uss/olh/faq/FaqListDetailInqire.do";
//        
//    }
//    
//    /**
//     * FAQ를 등록하기 위한 전 처리    
//     * @param searchVO
//     * @param model
//     * @return	"/uss/olh/faq/EgovFaqCnRegist"
//     * @throws Exception
//     */
//    @RequestMapping("/uss/olh/faq/FaqCnRegistView.do")
//    public String insertFaqCnView(
//            @ModelAttribute("searchVO") FaqManageDefaultVO searchVO, Model model)
//            throws Exception {
//    		  
//        model.addAttribute("faqManageVO", new FaqManageVO());              
//        
//        return "egovframework/com/uss/olh/faq/EgovFaqCnRegist";
//                
//    }
//    
//    /**
//     * FAQ를 수정하기 위한 전 처리        
//     * @param faqId
//     * @param searchVO
//     * @param model
//     * @return	"/uss/olh/faq/EgovFaqCnUpdt"
//     * @throws Exception
//     */
//    @RequestMapping("/uss/olh/faq/FaqCnUpdtView.do")
//    public String updateFaqCnView(@RequestParam("faqId") String faqId ,
//            @ModelAttribute("searchVO") FaqManageDefaultVO searchVO, ModelMap model)
//            throws Exception {
//    	
//    	
//        FaqManageVO faqManageVO = new FaqManageVO();
//        
//        // Primary Key 값 세팅
//        faqManageVO.setFaqId(faqId);
//		
//        // 변수명은 CoC 에 따라 
//        model.addAttribute(selectFaqListDetail(faqManageVO, searchVO, model));
//        
//        // 변수명은 CoC 에 따라 JSTL사용을 위해
//        model.addAttribute("faqManageVO", faqManageService.selectFaqListDetail(faqManageVO));        
//        
//        return "egovframework/com/uss/olh/faq/EgovFaqCnUpdt";
//    }
}
