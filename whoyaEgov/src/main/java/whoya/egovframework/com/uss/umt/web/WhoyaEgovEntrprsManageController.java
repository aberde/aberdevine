package whoya.egovframework.com.uss.umt.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import whoya.egovframework.com.uss.umt.service.WhoyaEgovEntrprsManageService;
import egovframework.com.uss.umt.service.UserDefaultVO;


/**
 * 기업회원관련 요청을  비지니스 클래스로 전달하고 처리된결과를  해당   웹 화면으로 전달하는  Controller를 정의한다
 */

@Controller
public class WhoyaEgovEntrprsManageController {
    
    /** whoyaEntrprsManageService */
    @Resource(name = "whoyaEntrprsManageService")
    private WhoyaEgovEntrprsManageService whoyaEntrprsManageService;
    
    
    /**
	 * 기업회원관리 화면
	 * @return ModelAndView
	 */
	@RequestMapping(value="/whoya/uss/umt/EgovEntrprsMberManage.do")
	public ModelAndView selectEntrprsMberList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/egovframework/com/uss/umt/EgovEntrprsMberManage");
		return mav;
	}
    
    
	/**
	 * 기업회원목록을 조회한다.
	 * @param userSearchVO 검색조건정보
	 * @return JSONObject
	 * @throws Exception
	 */
	@RequestMapping(value="/whoya/uss/umt/EgovEntrprsMberManageJSON.do", headers="Accept=application/json")
	public @ResponseBody JSONObject selectEntrprsMberJSONList(@ModelAttribute("userSearchVO") UserDefaultVO userSearchVO, ModelMap model)throws Exception {
		
		JSONObject resultList = new JSONObject();
		
		try {
			userSearchVO.setPageIndex(0);
			// EgovMap 형식으로 받음.
			List entrprsList = whoyaEntrprsManageService.selectEntrprsMberList(userSearchVO);
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(entrprsList));
			
			// 롤 정보 컬럼 추가.(이미지 링크걸기.)
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap map = list.getMap(i);
				String areaNo = (String)map.get("areaNo");
				String middleTelno = (String)map.get("middleTelno");
				String endTelno = (String)map.get("endTelno");
				
				map.put("tel", areaNo + ") " + middleTelno + "-" + endTelno);
			}
						
			// 아이디,회사명,신청자이름,신청자이메일,전화번호,등록일,가입상태,사용자고유아이디,사용자 유형,그룹 ID
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "userId,cmpnyNm,userNm,emailAdres,tel,sbscrbDe,sttus,uniqId,userTy,groupId"));
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
	 * 기업회원 등록/수정/삭제한다.
	 * @param request
	 * @param response
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping(value="whoya/uss/umt/saveEntrprsMber.do")
	public @ResponseBody void saveEntrprsMber(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		try {
			whoyaEntrprsManageService.saveEntrprsMber(request, response);
		} catch ( Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}
    
//    
//    @Resource(name="EgovCmmUseService")
//    private EgovCmmUseService cmmUseService;
//    
//    /** EgovPropertyService */
//    @Resource(name = "propertiesService")
//    protected EgovPropertyService propertiesService;
//    
//    /** Log Info */
//    protected Log log = LogFactory.getLog(this.getClass());
//    
//    /** DefaultBeanValidator beanValidator */
//    @Autowired
//	private DefaultBeanValidator beanValidator;
//    
//    /**
//     * 기업회원 등록화면으로 이동한다.
//     * @param userSearchVO 검색조건정보
//     * @param entrprsManageVO 기업회원 초기화정보
//     * @param model 화면모델
//     * @return uss/umt/EgovEntrprsMberInsert
//     * @throws Exception
//     */
//    @RequestMapping("/uss/umt/EgovEntrprsMberInsertView.do")
//    public String insertEntrprsMberView(
//            @ModelAttribute("userSearchVO") UserDefaultVO userSearchVO,
//            @ModelAttribute("entrprsManageVO") EntrprsManageVO entrprsManageVO,
//            Model model
//            )throws Exception {
//        ComDefaultCodeVO vo = new ComDefaultCodeVO();
//        
//        //패스워드힌트목록을 코드정보로부터 조회
//        vo.setCodeId("COM022");
//        List passwordHint_result = cmmUseService.selectCmmCodeDetail(vo);
//        //성별구분코드를 코드정보로부터 조회
//        vo.setCodeId("COM014");
//        List sexdstnCode_result = cmmUseService.selectCmmCodeDetail(vo);
//        //사용자상태코드를 코드정보로부터 조회
//        vo.setCodeId("COM013");
//        List entrprsMberSttus_result = cmmUseService.selectCmmCodeDetail(vo);        
//        //그룹정보를 조회 - GROUP_ID정보
//        vo.setTableNm("COMTNORGNZTINFO");
//        List groupId_result = cmmUseService.selectGroupIdDetail(vo);
//        //기업구분코드를 코드정보로부터 조회 - COM026
//        vo.setCodeId("COM026");
//        List entrprsSeCode_result = cmmUseService.selectCmmCodeDetail(vo);
//        //업종코드를 코드정보로부터 조회 - COM027
//        vo.setCodeId("COM027");
//        List indutyCode_result = cmmUseService.selectCmmCodeDetail(vo);
//        
//        model.addAttribute("passwordHint_result",      passwordHint_result);     //패스워트힌트목록
//        model.addAttribute("sexdstnCode_result",       sexdstnCode_result);      //성별구분코드목록
//        model.addAttribute("entrprsMberSttus_result",entrprsMberSttus_result);//사용자상태코드목록
//        model.addAttribute("groupId_result",         groupId_result);        //그룹정보 목록
//        model.addAttribute("entrprsSeCode_result",  entrprsSeCode_result);  //기업구분코드 목록
//        model.addAttribute("indutyCode_result",     indutyCode_result);        //업종코드목록
//        
//        return "egovframework/com/uss/umt/EgovEntrprsMberInsert";
//    }
//    
//    /**
//     * 기업회원등록처리후 목록화면으로 이동한다.
//     * @param entrprsManageVO 신규기업회원정보
//     * @param bindingResult   입력값검증용  bindingResult
//     * @param model           화면모델
//     * @return forward:/uss/umt/EgovEntrprsMberManage.do
//     * @throws Exception
//     */
//    @RequestMapping("/uss/umt/EgovEntrprsMberInsert.do")
//    public String insertEntrprsMber(
//            @ModelAttribute("entrprsManageVO") EntrprsManageVO entrprsManageVO,
//            BindingResult bindingResult,
//            Model model
//            )throws Exception {
//    	
//    	beanValidator.validate(entrprsManageVO, bindingResult);
//    	if (bindingResult.hasErrors()){
//    		return "egovframework/com/uss/umt/EgovEntrprsMberInsert";
//		}else{
//			if(entrprsManageVO.getGroupId().equals("")){
//				entrprsManageVO.setGroupId(null);
//			}
//			entrprsManageService.insertEntrprsmber(entrprsManageVO);
//	        //Exception 없이 진행시 등록성공메시지
//	        model.addAttribute("resultMsg", "success.common.insert");
//		}
//    	return "forward:/uss/umt/EgovEntrprsMberManage.do";
//    	
//    }
//
//    /**
//     * 기업회원정보 수정을 위해기업회원정보를 상세조회한다.
//     * @param entrprsmberId 상세조회 대상 기업회원아이디
//     * @param userSearchVO 조회조건정보 
//     * @param model 화면모델
//     * @return uss/umt/EgovEntrprsMberSelectUpdt
//     * @throws Exception
//     */
//    @RequestMapping("/uss/umt/EgovEntrprsMberSelectUpdtView.do")
//    public String updateEntrprsMberView(
//            @RequestParam("selectedId") String entrprsmberId ,
//            @ModelAttribute("searchVO") UserDefaultVO userSearchVO,
//            Model model
//            )throws Exception {
//        EntrprsManageVO entrprsManageVO = new EntrprsManageVO();
//        entrprsManageVO = entrprsManageService.selectEntrprsmber(entrprsmberId);
//        model.addAttribute("entrprsManageVO", entrprsManageVO);
//        model.addAttribute("userSearchVO", userSearchVO);
//        
//        ComDefaultCodeVO vo = new ComDefaultCodeVO();
//        //패스워드힌트목록을 코드정보로부터 조회
//        vo.setCodeId("COM022");
//        List passwordHint_result = cmmUseService.selectCmmCodeDetail(vo);
//        //성별구분코드를 코드정보로부터 조회
//        vo.setCodeId("COM014");
//        List sexdstnCode_result = cmmUseService.selectCmmCodeDetail(vo);
//        //사용자상태코드를 코드정보로부터 조회
//        vo.setCodeId("COM013");
//        List entrprsMberSttus_result = cmmUseService.selectCmmCodeDetail(vo);        
//        //그룹정보를 조회 - GROUP_ID정보
//        vo.setTableNm("COMTNORGNZTINFO");
//        List groupId_result = cmmUseService.selectGroupIdDetail(vo);
//        //기업구분코드를 코드정보로부터 조회 - COM026
//        vo.setCodeId("COM026");
//        List entrprsSeCode_result = cmmUseService.selectCmmCodeDetail(vo);
//        //업종코드를 코드정보로부터 조회 - COM027
//        vo.setCodeId("COM027");
//        List indutyCode_result = cmmUseService.selectCmmCodeDetail(vo);
//        
//        model.addAttribute("passwordHint_result",      passwordHint_result);     //패스워트힌트목록
//        model.addAttribute("sexdstnCode_result",       sexdstnCode_result);      //성별구분코드목록
//        model.addAttribute("entrprsMberSttus_result",entrprsMberSttus_result);//사용자상태코드목록
//        model.addAttribute("groupId_result",         groupId_result);        //그룹정보 목록
//        model.addAttribute("entrprsSeCode_result",  entrprsSeCode_result);  //기업구분코드 목록
//        model.addAttribute("indutyCode_result",     indutyCode_result);        //업종코드목록
//        
//        return "egovframework/com/uss/umt/EgovEntrprsMberSelectUpdt";
//    }
//    
//    /**
//     * 기업회원정보 수정후 목록조회 화면으로 이동한다.
//     * @param entrprsManageVO 수정할 기업회원정보 
//     * @param bindingResult 입력값 검증용 bindingResult 
//     * @param model 화면모델
//     * @return forward:/uss/umt/EgovEntrprsMberManage.do
//     * @throws Exception
//     */
//    @RequestMapping("/uss/umt/EgovEntrprsMberSelectUpdt.do")
//    public String updateEntrprsMber(
//            @ModelAttribute("entrprsManageVO") EntrprsManageVO entrprsManageVO, 
//            BindingResult bindingResult,
//            Model model
//            )throws Exception {
//    	beanValidator.validate(entrprsManageVO, bindingResult);
//    	if (bindingResult.hasErrors()){
//    		return "egovframework/com/uss/umt/EgovEntrprsMberSelectUpdt";
//		}else{
//			if(entrprsManageVO.getGroupId().equals("")){
//				entrprsManageVO.setGroupId(null);
//			}
//			entrprsManageService.updateEntrprsmber(entrprsManageVO);
//			//Exception 없이 진행시 수정성공메시지
//	        model.addAttribute("resultMsg", "success.common.update");
//		    return "forward:/uss/umt/EgovEntrprsMberManage.do";
//		}
//    }
//    
//    /**
//     * 기업회원정보삭제후 목록조회 화면으로 이동한다.
//     * @param checkedIdForDel 삭제대상아이디 정보
//     * @param userSearchVO 조회조건정보
//     * @param model 화면모델
//     * @return "forward:/uss/umt/EgovUserManage.do"
//     * @throws Exception
//     */
//    @RequestMapping("/uss/umt/EgovEntrprsMberDelete.do")
//    public String deleteEntrprsMber(
//            @RequestParam("checkedIdForDel") String checkedIdForDel ,
//            @ModelAttribute("searchVO") UserDefaultVO userSearchVO,
//            Model model
//            )throws Exception {
//    	//log.debug("jjycon_delete-->"+checkedIdForDel);
//        entrprsManageService.deleteEntrprsmber(checkedIdForDel);
//        //Exception 없이 진행시 삭제성공메시지
//        model.addAttribute("resultMsg", "success.common.delete");
//        return "forward:/uss/umt/EgovEntrprsMberManage.do";
//    }
//    

//    
//    /**
//     * 기업회원가입신청 등록화면으로 이동한다.
//     * @param userSearchVO 검색조건정보
//     * @param entrprsManageVO 기업회원초기화정보
//     * @param commandMap 파라메터전송 commandMap
//     * @param model 화면모델
//     * @return uss/umt/EgovEntrprsMberSbscrb
//     * @throws Exception
//     */
//    @RequestMapping("/uss/umt/EgovEntrprsMberSbscrbView.do")
//    public String sbscrbEntrprsMberView(
//    		@ModelAttribute("userSearchVO") UserDefaultVO userSearchVO, 
//    		@ModelAttribute("entrprsManageVO") EntrprsManageVO entrprsManageVO,
//    		Map<String, Object> commandMap, 
//    		Model model
//    		)throws Exception {
//        ComDefaultCodeVO vo = new ComDefaultCodeVO();
//        
//        //패스워드힌트목록을 코드정보로부터 조회
//        vo.setCodeId("COM022");
//        List passwordHint_result = cmmUseService.selectCmmCodeDetail(vo);
//        //성별구분코드를 코드정보로부터 조회
//        vo.setCodeId("COM014");
//        List sexdstnCode_result = cmmUseService.selectCmmCodeDetail(vo);
//        //기업구분코드를 코드정보로부터 조회 - COM026
//        vo.setCodeId("COM026");
//        List entrprsSeCode_result = cmmUseService.selectCmmCodeDetail(vo);
//        //업종코드를 코드정보로부터 조회 - COM027
//        vo.setCodeId("COM027");
//        List indutyCode_result = cmmUseService.selectCmmCodeDetail(vo);
//        
//        model.addAttribute("passwordHint_result",      passwordHint_result);     //패스워트힌트목록
//        model.addAttribute("sexdstnCode_result",       sexdstnCode_result);      //성별구분코드목록
//        model.addAttribute("entrprsSeCode_result",  entrprsSeCode_result);        //기업구분코드 목록
//        model.addAttribute("indutyCode_result",     indutyCode_result);           //업종코드목록
//        if(!"".equals((String)commandMap.get("realname"))){
//        	model.addAttribute("applcntNm",   (String)commandMap.get("realname"));      //실명인증된 이름 - 주민번호인증
//        	model.addAttribute("applcntIhidnum",   (String)commandMap.get("ihidnum"));  //실명인증된 주민등록번호 - 주민번호 인증
//        }
//        if(!"".equals((String)commandMap.get("realName"))){
//        	model.addAttribute("applcntNm",   (String)commandMap.get("realName"));      //실명인증된 이름 - ipin인증
//        }
//        //entrprsManageVO.setGroupId("DEFAULT");
//        entrprsManageVO.setEntrprsMberSttus("DEFAULT");
//        
//        return "egovframework/com/uss/umt/EgovEntrprsMberSbscrb";
//    }
//    
//    /**
//     * 기업회원가입신청 등록처리후 로그인화면으로 이동한다.
//     * @param entrprsManageVO 기업회원가입신청정보
//     * @return forward:/uat/uia/egovLoginUsr.do
//     * @throws Exception
//     */
//    @RequestMapping("/uss/umt/EgovEntrprsMberSbscrb.do")
//    public String sbscrbEntrprsMber(
//            @ModelAttribute("entrprsManageVO") EntrprsManageVO entrprsManageVO
//            )throws Exception {
//        
//        //가입상태 초기화
//        entrprsManageVO.setEntrprsMberSttus("A");
//        //그룹정보 초기화
//        //entrprsManageVO.setGroupId("1");
//        //기업회원가입신청 등록시 기업회원등록기능을 사용하여 등록한다. 
//        entrprsManageService.insertEntrprsmber(entrprsManageVO);
//        return "forward:/uat/uia/egovLoginUsr.do";
//    }
//    
//    /**
//     * 기업회원 약관확인 화면을 조회한다.
//     * @param model 화면모델
//     * @return uss/umt/EgovStplatCnfirm
//     * @throws Exception
//     */
//    @RequestMapping("/uss/umt/EgovStplatCnfirmEntrprs.do")
//    public String sbscrbEntrprsMber(Model model)
//            throws Exception {
//        //기업회원용 약관 아이디 설정
//        String stplatId = "STPLAT_0000000000002";
//        //회원가입유형 설정-기업회원
//        String sbscrbTy = "USR02";
//        //약관정보 조회 
//        List stplatList = entrprsManageService.selectStplat(stplatId);
//        model.addAttribute("stplatList",      stplatList);     //약관정보포함
//        model.addAttribute("sbscrbTy",        sbscrbTy);     //회원가입유형포함
//        
//        return "egovframework/com/uss/umt/EgovStplatCnfirm";
//    }
//    
//    /**
//     * 기업회원 암호 수정처리 후 화면 이동한다.
//     * @param model 화면모델
//     * @param commandMap 파라메터전달용 commandMap
//     * @param userSearchVO 검색조건정보
//     * @param entrprsManageVO 기업회원수정정보
//     * @return uss/umt/EgovEntrprsPasswordUpdt
//     * @throws Exception
//     */
//    @RequestMapping(value="/uss/umt/EgovEntrprsPasswordUpdt.do")
//    public String updatePassword(
//    		ModelMap model, 
//    		Map<String, Object> commandMap,
//    		@ModelAttribute("searchVO") UserDefaultVO userSearchVO,
//    		@ModelAttribute("entrprsManageVO") EntrprsManageVO entrprsManageVO
//    		)throws Exception {
//    	String oldPassword = (String)commandMap.get("oldPassword");
//        String newPassword = (String)commandMap.get("newPassword");
//        String newPassword2 = (String)commandMap.get("newPassword2");
//        String uniqId = (String)commandMap.get("uniqId");
//        
//        boolean isCorrectPassword=false;
//        EntrprsManageVO resultVO = new EntrprsManageVO();
//        entrprsManageVO.setEntrprsMberPassword(newPassword);
//        entrprsManageVO.setOldPassword(oldPassword);
//        entrprsManageVO.setUniqId(uniqId);
//        
//    	String resultMsg = "";
//    	resultVO = entrprsManageService.selectPassword(entrprsManageVO);
//    	//패스워드 암호화
//		String encryptPass = EgovFileScrty.encryptPassword(oldPassword);
//    	if (encryptPass.equals(resultVO.getEntrprsMberPassword())){
//    		if (newPassword.equals(newPassword2)){
//        		isCorrectPassword = true;
//        	}else{
//        		isCorrectPassword = false;
//        		resultMsg="fail.user.passwordUpdate2";
//        	}
//    	}else{
//    		isCorrectPassword = false;
//    		resultMsg="fail.user.passwordUpdate1";
//    	}
//    	
//    	if (isCorrectPassword){
//    		entrprsManageVO.setEntrprsMberPassword(EgovFileScrty.encryptPassword(newPassword));
//    		entrprsManageService.updatePassword(entrprsManageVO);
//            model.addAttribute("entrprsManageVO", entrprsManageVO);      
//            resultMsg = "success.common.update";
//        }else{
//        	model.addAttribute("entrprsManageVO", entrprsManageVO);      
//        }
//    	model.addAttribute("userSearchVO", userSearchVO);
//    	model.addAttribute("resultMsg", resultMsg);
//        
//        return "egovframework/com/uss/umt/EgovEntrprsPasswordUpdt";
//    }
//    
//    /**
//     * 기업회원암호 수정 화면 이동
//     * @param model 화면모델
//     * @param commandMap 파라메터전송용 commandMap
//     * @param userSearchVO 검색조건정보
//     * @param entrprsManageVO 기업회원수정정보
//     * @return uss/umt/EgovEntrprsPasswordUpdt
//     * @throws Exception
//     */
//    @RequestMapping(value="/uss/umt/EgovEntrprsPasswordUpdtView.do")
//    public String updatePasswordView(
//    		ModelMap model, 
//    		Map<String, Object> commandMap,
//    		@ModelAttribute("searchVO") UserDefaultVO userSearchVO,
//    		@ModelAttribute("entrprsManageVO") EntrprsManageVO entrprsManageVO
//    		) throws Exception {
//    	String userTyForPassword = (String)commandMap.get("userTyForPassword");
//    	entrprsManageVO.setUserTy(userTyForPassword);
//    	
//    	model.addAttribute("userSearchVO", userSearchVO);
//    	model.addAttribute("entrprsManageVO", entrprsManageVO);
//    	return "egovframework/com/uss/umt/EgovEntrprsPasswordUpdt";
//    }

}