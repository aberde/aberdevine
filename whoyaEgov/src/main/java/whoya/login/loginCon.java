package whoya.login;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.common.commonReturn;
import whoya.common.commonSession;
import whoya.common.commonSessionUtil;


@Controller
public class loginCon extends MultiActionController {

	@Resource(name="loginSvc")
	private loginSvc loginService;

	@RequestMapping(value="whoya/content/login.do")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/content/login");
		return mav;
	}
	
	@RequestMapping(value="whoya/content/loginChk.do", headers="Accept=application/json")
	public @ResponseBody JSONObject loginChk( HttpServletRequest request
			                                , HttpServletResponse response 
										    , @RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		logger.debug(paramMap.toString());
		JSONObject result = new JSONObject();
		
		//로그인 처리 시작
		String usrId = ServletRequestUtils.getStringParameter(request, "usrId");
		String pwd = ServletRequestUtils.getStringParameter(request, "pwd");

		if (usrId != null && pwd != null) {
			
			whoyaMap params = new whoyaMap();
			params.put("usrId", usrId);
			params.put("pwd", pwd); //암호화 
			//params.put("pwd", commonEncrypt.encryptMD5(pwd)); //암호화 
			//System.out.println(params.put("pwd", commonEncrypt.encryptMD5(pwd).toString())); //암호화 

			whoyaMap user = loginService.login(params);
			System.out.println(user);

			if (user != null && user.size() != 0) {
				
				//params.clear();
				//System.out.println(params.put("usrId", user.getString("pwd")));
				
				commonSession commonSession = new commonSession(user);
				commonSessionUtil.setCommonSession(commonSession);
				
				result.put("status", commonReturn.SUCCESS);
				//String redirect = paramMap.get("redirect");
				
				//로그인 후 페이지 리다이렉트 처리
				//if (redirect != null) {
				//	logger.info("Redirect to [" + redirect + "] after login");
				//	result.put("redirect", redirect);
				//}
				//result.put("message", "로그인 OK!\n성공.");
				return result;
			}
		}
		
		result.put("status", commonReturn.FAIL);
		result.put("message", "로그인 실패!\n아이디 와  비밀번호를 확인하세요.");
		return result;
	}
	
	@RequestMapping(value="whoya/content/main.do")
	public ModelAndView main() {
		ModelAndView mav = new ModelAndView();

		commonSession session = commonSessionUtil.getCommonSession();
		System.out.println(session.getUsrId());
		
		whoyaMap params = new whoyaMap();
		params.put("coId", session.getCoId());
		params.put("usrId", session.getUsrId());

		//commonList list = new commonList();
		try{
				whoyaList list = loginService.menu(params);
				mav.addObject("menuList", list);
		
				mav.setViewName("whoya/content/main");
				return mav;
		} 
		catch(Exception e){
			
	  			logger.fatal("[Exceptoin error] content/main.do : " +e);
	  			mav.addObject("msgCode" , "error");		
	  			mav.addObject("msg" , e.getMessage());
	  			return new ModelAndView("/error");
	  	}		
	}	
}
