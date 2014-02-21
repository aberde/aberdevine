package whoya.egovframework.com.uss.olh.qna.web;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import whoya.whoyaLib;
import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.common.Common;
import whoya.common.commonReturn;
import whoya.egovframework.com.uss.olh.qna.service.WhoyaEgovQnaManageService;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.olh.qna.service.QnaManageDefaultVO;
import egovframework.com.uss.olh.qna.service.QnaManageVO;
import egovframework.com.utl.sim.service.EgovFileScrty;

/**
 * Q&A를 처리하는 Controller 클래스
 */

@Controller
public class WhoyaEgovQnaManageController {

	@Resource(name = "WhoyaQnaManageService")
	private WhoyaEgovQnaManageService qnaManageService;
  
	@Resource(name="EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	/**
	 * Q&A관리 화면
	 * @return ModelAndView
	 */
	@RequestMapping(value="/whoya/uss/olh/qna/QnaListInqire.do")
	public ModelAndView selectQnaist() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/egovframework/com/uss/olh/qna/EgovQnaListInqire");
		return mav;
	}
	
	/**
     * Q&A정보 목록을 조회한다.
     * @param searchVO
     * @param model
     * @return	JSONObject
     * @throws Exception
     */
	@RequestMapping(value="/whoya/uss/olh/qna/QnaListInqireJSON.do")
	public @ResponseBody JSONObject selectQnaList(@ModelAttribute("searchVO") QnaManageDefaultVO searchVO, ModelMap model) throws Exception {
		JSONObject resultList = new JSONObject();
		
		try {
			searchVO.setPageIndex(0);
			List QnaList = qnaManageService.selectQnaList(searchVO);
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(QnaList));
			
			// 번호 컬럼 추가.
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine)  
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap wmap = list.getMap(i);
				wmap.put("no", i + 1);
				String writngDe = (String)wmap.get("writngDe");
				wmap.put("writngDe", writngDe.substring(0, 4) + "-" + writngDe.substring(4, 6) + "-" + writngDe.substring(6));
			}

			// 순번,질문제목,작성자,진행상태,조회수,작성일자,qaId
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,qestnSj,wrterNm,qnaProcessSttusCodeNm,inqireCo,writngDe,qaId"));
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
     * Q&A정보를 등록하기 위한 전 처리(인증체크)   
     * @param searchVO
     * @param qnaManageVO
     * @param model
     * @return	JSONObject
     * @throws Exception
     */
	@RequestMapping("/whoya/uss/olh/qna/QnaCnRegistView.do")
	public @ResponseBody JSONObject insertQnaCnView(@ModelAttribute("searchVO") QnaManageDefaultVO searchVO, QnaManageVO qnaManageVO, Model model) throws Exception {
		JSONObject resultJSON = new JSONObject();
		
		try {
		  	// 로그인VO에서  사용자 정보 가져오기
		  	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		  	
		  	String	wrterNm 	= loginVO.getName();					// 사용자명
		  	String	emailAdres 	= loginVO.getEmail();					// email 주소
		  	
		  	qnaManageVO.setWrterNm(wrterNm);							// 작성자명
		  	qnaManageVO.setEmailAdres(emailAdres);    					// email 주소
		  	
		  	resultJSON.put("status", commonReturn.SUCCESS);
		  	resultJSON.put("result", qnaManageVO);
		  	resultJSON.put("qnaManageVO", qnaManageVO);
		} catch ( Exception e ) {
			e.printStackTrace();
			resultJSON.put("status", commonReturn.FAIL);
		}
		
		return resultJSON;
	}
  
	/**
     * Q&A정보를 등록한다.
     * @param searchVO
     * @param qnaManageVO
     * @param bindingResult
     * @return	"forward:/uss/olh/qna/QnaListInqire.do"
     * @throws Exception
     */
	@RequestMapping("/whoya/uss/olh/qna/QnaCnRegist.do")
	public @ResponseBody void insertQnaCn(@ModelAttribute("searchVO") QnaManageDefaultVO searchVO, @ModelAttribute("qnaManageVO") QnaManageVO qnaManageVO, ModelMap model) throws Exception {
		try {
			// 로그인VO에서  사용자 정보 가져오기
		  	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		  	
		  	String	frstRegisterId = loginVO.getUniqId();
				    	    			          			        
		  	qnaManageVO.setFrstRegisterId(frstRegisterId);		// 최초등록자ID
		  	qnaManageVO.setLastUpdusrId(frstRegisterId);    	// 최종수정자ID
		  	
		  	// 작성비밀번호를 암호화 하기 위해서 Get
		  	String	writngPassword = qnaManageVO.getWritngPassword();
		  	
		  	// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 암호화 한다.
		  	qnaManageVO.setWritngPassword(EgovFileScrty.encode(writngPassword));
		  	
	  		qnaManageService.insertQnaCn(qnaManageVO);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	/**
     * Q&A정보 목록에 대한 상세정보를 조회한다.
     * @param passwordConfirmAt
     * @param qnaManageVO
     * @param searchVO
     * @param model
     * @return	"/uss/olh/qna/EgovQnaDetailInqire"
     * @throws Exception
     */
	@RequestMapping("/whoya/uss/olh/qna/QnaDetailInqire.do")
	public @ResponseBody QnaManageVO selectQnaListDetail(@RequestParam("passwordConfirmAt") String passwordConfirmAt , QnaManageVO qnaManageVO, @ModelAttribute("searchVO") QnaManageDefaultVO searchVO, ModelMap model) throws Exception {  
		try {
			qnaManageService.updateQnaInqireCo(qnaManageVO);
			QnaManageVO vo = qnaManageService.selectQnaListDetail(qnaManageVO);
					
			vo.setPasswordConfirmAt(passwordConfirmAt);    		// 작성비밀번호 확인여부
			
			// 작성 비밀번호를 얻는다.
			String	writngPassword = vo.getWritngPassword();		
			// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 복호화한다.
			vo.setWritngPassword(EgovFileScrty.decode(writngPassword));
			// 날짜형식변경(YYYY-MM-DD)
			vo.setWritngDe(vo.getWritngDe().substring(0, 4) + "-" + vo.getWritngDe().substring(4, 6) + "-" + vo.getWritngDe().substring(6));
				
		  	return vo;
		} catch ( Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}
  
	/**
     * 수정,삭제을 위해 작성 비밀번호를 확인한다.
     * @param qnaManageVO
     * @param searchVO
     * @return	JSONObject
     * @throws Exception
     */
	@RequestMapping("/whoya/uss/olh/qna/QnaPasswordConfirm.do")
	public @ResponseBody JSONObject selectPasswordConfirm(QnaManageVO qnaManageVO, @ModelAttribute("searchVO") QnaManageDefaultVO searchVO, Model model) throws Exception {
		JSONObject result = new JSONObject();
		try {
			// 작성비밀번호를 암호화 하기 위해서 Get
			String	writngPassword = qnaManageVO.getWritngPassword();
			// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 암호화 한다.
			qnaManageVO.setWritngPassword(EgovFileScrty.encode(writngPassword));
	  	    	    	
			int searchCnt = qnaManageService.selectQnaPasswordConfirmCnt(qnaManageVO);
	          	    	
			if ( searchCnt > 0) {		// 작성 비밀번호가 일치하는 경우
				result.put("status", commonReturn.SUCCESS);
			} else	{					// 작성비밀번호가 틀린경우
				result.put("status", commonReturn.FAIL);
			}
		} catch ( Exception e ) {
			result.put("status", commonReturn.FAIL);
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
  
	/**
     * Q&A정보를 삭제처리한다.               
     * @param qnaManageVO
     * @param searchVO
     * @throws Exception
     */
	@RequestMapping("/whoya/uss/olh/qna/QnaCnDelete.do")
	public @ResponseBody JSONObject deleteQnaCn(QnaManageVO qnaManageVO, @ModelAttribute("searchVO") QnaManageDefaultVO searchVO) throws Exception {
		JSONObject result = new JSONObject();
		try {
			qnaManageService.deleteQnaCn(qnaManageVO);
			result.put("status", commonReturn.SUCCESS);
		} catch ( Exception e ) {
			result.put("status", commonReturn.FAIL);
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
	
	/**
     * Q&A정보를 수정처리한다.           
     * @param searchVO
     * @param qnaManageVO
     * @return	JSONObject
     * @throws Exception
     */
	@RequestMapping("/whoya/uss/olh/qna/QnaCnUpdt.do")
	public @ResponseBody JSONObject updateQnaCn(@ModelAttribute("searchVO") QnaManageDefaultVO searchVO, @ModelAttribute("qnaManageVO") QnaManageVO qnaManageVO) throws Exception {
	  	JSONObject result = new JSONObject();
		try {
			// 로그인VO에서  사용자 정보 가져오기
			LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			String	lastUpdusrId = loginVO.getUniqId();
			qnaManageVO.setLastUpdusrId(lastUpdusrId);    	// 최종수정자ID
			// 작성비밀번호를 암호화 하기 위해서 Get
			String	writngPassword = qnaManageVO.getWritngPassword();
			// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 암호화 한다.
			qnaManageVO.setWritngPassword(EgovFileScrty.encode(writngPassword));
			
			qnaManageService.updateQnaCn(qnaManageVO);
			result.put("status", commonReturn.SUCCESS);
		} catch ( Exception e ) {
			result.put("status", commonReturn.FAIL);
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
	
	/**
	 * Q&A답변관리 화면
	 * @return ModelAndView
	 */
	@RequestMapping(value="/whoya/uss/olh/qnm/QnaAnswerListInqire.do")
	public ModelAndView selectQnaAnswerList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/egovframework/com/uss/olh/qna/EgovQnaAnswerListInqire");
		return mav;
	}
	
	/**
     * Q&A답변정보 목록을 조회한다.
     * @param searchVO
     * @param model
     * @return	"/uss/olh/qna/EgovQnaAnswerListInqire"
     * @throws Exception
     */
	@RequestMapping(value="/whoya/uss/olh/qnm/QnaAnswerListInqireJSON.do")
	public @ResponseBody JSONObject selectQnaAnswerListJSON(@ModelAttribute("searchVO") QnaManageDefaultVO searchVO, ModelMap model) throws Exception {
		JSONObject resultList = new JSONObject();
		
		try {
			searchVO.setPageIndex(0);
			List QnaAnswerList = qnaManageService.selectQnaAnswerList(searchVO);
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(QnaAnswerList));
			
			// 번호 컬럼 추가.
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine)  
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap wmap = list.getMap(i);
				wmap.put("no", i + 1);
				String writngDe = (String)wmap.get("writngDe");
				wmap.put("writngDe", writngDe.substring(0, 4) + "-" + writngDe.substring(4, 6) + "-" + writngDe.substring(6));
			}

			// 순번,질문제목,작성자,진행상태,조회수,작성일자,qaId
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,qestnSj,wrterNm,qnaProcessSttusCodeNm,inqireCo,writngDe,qaId"));
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
     * Q&A답변정보 목록에 대한 상세정보를 조회한다.
     * @param qnaManageVO
     * @param searchVO
     * @param model
     * @return	QnaManageVO
     * @throws Exception
     */
	@RequestMapping("/whoya/uss/olh/qnm/QnaAnswerDetailInqire.do")
	public @ResponseBody QnaManageVO selectQnaAnswerListDetail(QnaManageVO qnaManageVO, @ModelAttribute("searchVO") QnaManageDefaultVO searchVO, ModelMap model) throws Exception {
		try {
			QnaManageVO vo = qnaManageService.selectQnaListDetail(qnaManageVO);
			return vo;
		} catch ( Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 진행처리상태 목록을 조회한다.
	 * @param boardMasterVO BoardMasterVO
	 * @param model ModelMap
	 * @return JSONObject
	 * @exception Exception
	 */
	@RequestMapping(value="/whoya/uss/olh/qnm/selectQnaProcessSttusCodeList.do", headers="Accept=application/json")
	public @ResponseBody List<CmmnDetailCode> selectQnaProcessSttusCodeList(ComDefaultCodeVO comDefaultCodeVO) throws Exception {
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
     * Q&A답변정보를 수정처리한다.
     * @param qnaManageVO
     * @param searchVO
     * @return	"forward:/uss/olh/qnm/QnaAnswerListInqire.do"
     * @throws Exception
     */
	@RequestMapping("/whoya/uss/olh/qnm/QnaCnAnswerUpdt.do")
	public @ResponseBody JSONObject updateQnaCnAnswer(QnaManageVO qnaManageVO, @ModelAttribute("searchVO") QnaManageDefaultVO searchVO) throws Exception {
		JSONObject result = new JSONObject();
		try {
			// 로그인VO에서  사용자 정보 가져오기
		  	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		  	String	lastUpdusrId = loginVO.getUniqId();
		  	qnaManageVO.setLastUpdusrId(lastUpdusrId);    	// 최종수정자ID
		  	qnaManageService.updateQnaCnAnswer(qnaManageVO);
			result.put("status", commonReturn.SUCCESS);
		} catch ( Exception e ) {
			result.put("status", commonReturn.FAIL);
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
//	 
//
//	protected Log log = LogFactory.getLog(this.getClass());
//	
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
//     * @return	"/uss/olh/qna/"
//     * @throws Exception
//     */
//    @RequestMapping(value="/uss/olh/qna/EgovMain.do")
//    public String egovMain(ModelMap model) throws Exception {
//    	return "egovframework/com/uss/olh/qna/EgovMain";
//    }
//    
//    /**
//     * 메뉴를 조회한다.
//     * @param model
//     * @return	"/uss/olh/qna/EgovLeft"
//     * @throws Exception
//     */
//    @RequestMapping(value="/uss/olh/qna/EgovLeft.do")
//    public String egovLeft(ModelMap model) throws Exception {
//    	return "egovframework/com/uss/olh/qna/EgovLeft";
//    }
//    
//    
//    /**
//     * 로그인/실명확인 처리
//     * @param qnaManageVO
//     * @param searchVO
//     * @param model
//     * @return	/uss/olh/qna/EgovLoginRealnmChoice
//     * @throws Exception
//     */
//    @RequestMapping("/uss/olh/qna/LoginRealnmChoice.do")
//    public String selectLoginRealnmChoice(
//    		QnaManageVO qnaManageVO,
//            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO,
//            Model	model)
//            throws Exception {
//    	    	
//        model.addAttribute("QnaManageVO", new QnaManageVO());    	
//    	        
//        return "egovframework/com/uss/olh/qna/EgovQnaLoginRealnmChoice";        
//    }
//
//
//    /**
//     * 작성 비밀번호를 확인하기 위한 전 처리
//     * @param qnaManageVO
//     * @param searchVO
//     * @param model
//     * @return	"/uss/olh/qna/EgovQnaPasswordConfirm"
//     * @throws Exception
//     */
//    @RequestMapping("/uss/olh/qna/QnaPasswordConfirmView.do")
//    public String selectPasswordConfirmView(
//            QnaManageVO qnaManageVO,
//            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO,
//            Model	model)
//            throws Exception {
//    	    	
//        model.addAttribute("QnaManageVO", new QnaManageVO());    	
//    	        
//        return "egovframework/com/uss/olh/qna/EgovQnaPasswordConfirm";        
//    }
//    
//    /**
//     * Q&A정보를 수정하기 위한 전 처리(비밀번호 암호화)       
//     * @param qnaManageVO
//     * @param searchVO
//     * @param model
//     * @return	"/uss/olh/qna/EgovQnaCnUpdt
//     * @throws Exception
//     */
//    @RequestMapping("/uss/olh/qna/QnaCnUpdtView.do")
//    public String updateQnaCnView(
//    		QnaManageVO qnaManageVO,
//            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO, ModelMap model)
//            throws Exception {    	    		        	
//    	        
//    	QnaManageVO vo = qnaManageService.selectQnaListDetail(qnaManageVO);
//
//		// 작성 비밀번호를 얻는다.
//		String	writngPassword = vo.getWritngPassword();		
//		
//		// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 복호화한다.
//    	vo.setWritngPassword(EgovFileScrty.decode(writngPassword));
//		
//        // 복호화된 패스워드를 넘긴다.. 
//        model.addAttribute("qnaManageVO", vo);
//        
//        // result에도 세팅(jstl 사용을 위해)
//        model.addAttribute(selectQnaListDetail("Y",qnaManageVO, searchVO, model));
//    	        
//        return "egovframework/com/uss/olh/qna/EgovQnaCnUpdt";
//    }
//
//    /**
//     * 삭제을 위해 작성 비밀번호를 확인한다.
//     * @param qnaManageVO
//     * @param searchVO
//     * @return	"forward:/uss/olh/qna/QnaDetailInqire.do"
//     * @throws Exception
//     */
//    @RequestMapping("/uss/olh/qna/QnaPasswordConfirmDel.do")
//    public String selectPasswordConfirmDel(
//            QnaManageVO qnaManageVO,
//            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO,
//            Model	model)            
//            throws Exception {
//
//    	// 작성비밀번호를 암호화 하기 위해서 Get
//    	String	writngPassword = qnaManageVO.getWritngPassword();
//
//    	// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 암호화 한다.
//    	qnaManageVO.setWritngPassword(EgovFileScrty.encode(writngPassword));
//    	    	    	
//        int searchCnt = qnaManageService.selectQnaPasswordConfirmCnt(qnaManageVO);
//            	    	
//        if ( searchCnt > 0) {		// 작성 비밀번호가 일치하는 경우
//        	
//        	// Q&A를 삭제
//        	return	"forward:/uss/olh/qna/QnaCnDelete.do";
//        	
//        } else	{					// 작성비밀번호가 틀린경우
//        
//        	// 작성비밀번호 확인 결과 세팅.
//        	//qnaManageVO.setPasswordConfirmAt("N");
//        	
//        	String	passwordConfirmAt = "N";
//        	
//            //model.addAttribute("QnaManageVO", qnaManageVO);
//                    	
//        	// Q&A 상세조회 화면으로 이동.
//        	return "forward:/uss/olh/qna/QnaDetailInqire.do?passwordConfirmAt=" + passwordConfirmAt;
//        	
//        	
//        }
//                        
//    }    
//
//        
//    /**
//     * Q&A답변정보를 수정하기 위한 전 처리(공통코드 처리)        
//     * @param qnaManageVO
//     * @param searchVO
//     * @param model
//     * @return	"/uss/olh/qna/EgovQnaCnAnswerUpdt"
//     * @throws Exception
//     */
//    @RequestMapping("/uss/olh/qnm/QnaCnAnswerUpdtView.do")
//    public String updateQnaCnAnswerView(
//    		QnaManageVO qnaManageVO,
//            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO, ModelMap model)
//            throws Exception {    	    		        		        
//  
//
//    	// 공통코드를 가져오기 위한 Vo
//    	ComDefaultCodeVO vo = new ComDefaultCodeVO();
//		vo.setCodeId("COM028");
//		
//		List _result = cmmUseService.selectCmmCodeDetail(vo);
//		model.addAttribute("resultList", _result);		
//    	
//        // 변수명은 CoC 에 따라 
//        model.addAttribute(selectQnaAnswerListDetail(qnaManageVO, searchVO, model));
//        
//        return "egovframework/com/uss/olh/qna/EgovQnaCnAnswerUpdt";
//    }
//
	
}
