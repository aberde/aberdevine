package whoya.egovframework.com.uat.uap.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import whoya.whoyaLib;
import whoya.whoyaList;
import whoya.common.Common;
import whoya.common.commonReturn;
import whoya.egovframework.com.uat.uap.service.WhoyaEgovLoginPolicyService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uat.uap.service.LoginPolicy;
import egovframework.com.uat.uap.service.LoginPolicyVO;

/**
 * 로그인정책관리에 관한 controller 클래스를 정의한다.
 */

@Controller
public class WhoyaEgovLoginPolicyController extends MultiActionController {

	@Resource(name="whoyaEgovLoginPolicyService")
	WhoyaEgovLoginPolicyService egovLoginPolicyService;
	
	/**
	 * 로그인정책관리 화면
	 * @return ModelAndView
	 */
	@RequestMapping(value="/whoya/uat/uap/selectLoginPolicyList.do")
	public ModelAndView selectLoginPolicyList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/egovframework/com/uat/uap/EgovLoginPolicyList");
		return mav;
	}
	
	/**
	 * 로그인정책관리 목록을 조회한다.
	 * @param loginPolicyVO LoginPolicyVO
	 * @return JSONObject
	 * @exception Exception
	 */
	@RequestMapping(value="/whoya/uat/uap/selectLoginPolicyJSONList.do", headers="Accept=application/json")
	public @ResponseBody JSONObject selectLoginPolicyJSONList(@ModelAttribute("loginPolicyVO") LoginPolicyVO loginPolicyVO, ModelMap model) throws Exception {
		
		JSONObject resultList = new JSONObject();
		
		try {
			loginPolicyVO.setPageIndex(0);
			List<LoginPolicyVO> voList = egovLoginPolicyService.selectLoginPolicyList(loginPolicyVO);
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(voList));
			
			// 사용자 ID,사용자 명,IP 정보,제한여부
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "emplyrId,emplyrNm,ipInfo,lmttAt"));
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
	 * 로그인정책 목록의 상세정보를 조회한다.
	 * @param loginPolicyVO - 로그인정책 VO
	 * @return String - 리턴 Url
	 */
	@RequestMapping("/whoya/uat/uap/getLoginPolicy.do")
	public @ResponseBody LoginPolicyVO selectLoginPolicy(@ModelAttribute("loginPolicyVO") LoginPolicyVO loginPolicyVO, ModelMap model) throws Exception {
		try {
			LoginPolicyVO vo = egovLoginPolicyService.selectLoginPolicy(loginPolicyVO);
			return vo;
		} catch ( Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 로그인정책 정보를 신규로 등록한다.
	 * @param loginPolicy - 로그인정책 model
	 * @return JSONObject
	 */
	@RequestMapping("/whoya/uat/uap/addLoginPolicy.do")
	public @ResponseBody JSONObject insertLoginPolicy(@ModelAttribute("loginPolicy") LoginPolicy loginPolicy, BindingResult bindingResult, ModelMap model) throws Exception {
		JSONObject result = new JSONObject();
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			loginPolicy.setUserId(user.getId());
			
			egovLoginPolicyService.insertLoginPolicy(loginPolicy);
			result.put("status", commonReturn.SUCCESS);
		} catch ( Exception e ) {
			result.put("status", commonReturn.FAIL);
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	/**
	 * 기 등록된 로그인정책 정보를 수정한다.
	 * @param loginPolicy - 로그인정책 model
	 * @return JSONObject
	 */
	@RequestMapping("/whoya/uat/uap/updtLoginPolicy.do")
	public @ResponseBody JSONObject updateLoginPolicy(@ModelAttribute("loginPolicy") LoginPolicy loginPolicy, BindingResult bindingResult, ModelMap model) throws Exception {
		JSONObject result = new JSONObject();
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			loginPolicy.setUserId(user.getId());
			
			egovLoginPolicyService.updateLoginPolicy(loginPolicy);
			result.put("status", commonReturn.SUCCESS);
		} catch ( Exception e ) {
			result.put("status", commonReturn.FAIL);
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	/**
	 * 기 등록된 로그인정책 정보를 삭제한다.
	 * @param loginPolicy - 로그인정책 model
	 * @return JSONObject
	 */
	@RequestMapping("/whoya/uat/uap/removeLoginPolicy.do")
	public @ResponseBody JSONObject deleteLoginPolicy(@ModelAttribute("loginPolicy") LoginPolicy loginPolicy, ModelMap model) throws Exception {
		JSONObject result = new JSONObject();
		try {
			egovLoginPolicyService.deleteLoginPolicy(loginPolicy);
			result.put("status", commonReturn.SUCCESS);
		} catch ( Exception e ) {
			result.put("status", commonReturn.FAIL);
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
//	/**
//	 * 로그인정책관리 등록/수정/삭제한다.
//	 * @param request
//	 * @param response
//	 * @param model
//	 * @exception Exception
//	 */
//	@RequestMapping(value="whoya/uat/uap/saveLoginPolicy.do")
//	public @ResponseBody void saveLoginPolicy(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
//		try {
//		    whoyaEgovLoginPolicyService.saveLoginPolicy(request, response);
//		} catch ( Exception e ) {
//			e.printStackTrace();
//			throw e;
//		}
//	}
}
