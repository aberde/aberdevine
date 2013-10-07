package common;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * 에러처리를 담당하는 공통 컨트롤러
 */
@Controller
@RequestMapping(value="/common")
public class commonException extends MultiActionController {
	
	@RequestMapping(value="/ajaxAccessDenined.do")
	public @ResponseBody JSONObject ajaxAccessDenined(HttpServletRequest request, HttpServletResponse response) {
		
		response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		JSONObject result = new JSONObject();
		result.put("status", "NOT_AUTHORIZATION");
		result.put("massage", "사용권한이 없습니다.");
		
		return result;
	}
}
