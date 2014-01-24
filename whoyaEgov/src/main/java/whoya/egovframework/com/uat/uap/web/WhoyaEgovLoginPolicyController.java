package whoya.egovframework.com.uat.uap.web;

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
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import whoya.whoyaLib;
import whoya.whoyaList;
import whoya.common.Common;
import whoya.common.commonReturn;
import whoya.egovframework.com.uat.uap.service.WhoyaEgovLoginPolicyService;
import egovframework.com.uat.uap.service.LoginPolicyVO;

/**
 * 로그인정책관리에 관한 controller 클래스를 정의한다.
 */

@Controller
public class WhoyaEgovLoginPolicyController extends MultiActionController {

	@Resource(name="whoyaEgovLoginPolicyService")
	WhoyaEgovLoginPolicyService whoyaEgovLoginPolicyService;
	
	/**
	 * 로그인정책관리 화면
	 * @return ModelAndView
	 */
	@RequestMapping(value="whoya/uat/uap/selectLoginPolicyList.do")
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
	@RequestMapping(value="whoya/uat/uap/selectLoginPolicyJSONList.do", headers="Accept=application/json")
	public @ResponseBody JSONObject selectLoginPolicyJSONList(@ModelAttribute("loginPolicyVO") LoginPolicyVO loginPolicyVO, ModelMap model) throws Exception {
		
		JSONObject resultList = new JSONObject();
		
		try {
			loginPolicyVO.setPageIndex(0);
			List<LoginPolicyVO> voList = whoyaEgovLoginPolicyService.selectLoginPolicyList(loginPolicyVO);
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
	 * 로그인정책관리 등록/수정/삭제한다.
	 * @param request
	 * @param response
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping(value="whoya/uat/uap/saveLoginPolicy.do")
	public @ResponseBody void saveLoginPolicy(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		try {
		    whoyaEgovLoginPolicyService.saveLoginPolicy(request, response);
		} catch ( Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}
}
