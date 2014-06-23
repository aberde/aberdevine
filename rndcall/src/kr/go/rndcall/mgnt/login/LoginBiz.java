package kr.go.rndcall.mgnt.login;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.initech.eam.nls.CookieManager;
import com.initech.eam.smartenforcer.SECode;

import org.apache.log4j.Logger;

import kr.go.rndcall.mgnt.login.LoginResultVO;
import kr.go.rndcall.mgnt.login.LoginDAO;
import kr.go.rndcall.mgnt.login.LoginVO;
import kr.go.rndcall.mgnt.common.DAOBaseException;

public class LoginBiz {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	private String NLS_DOMAIN = ".ntis.go.kr";

	public LoginResultVO login(HttpServletRequest request, LoginVO vo) throws Exception {
		  
		LoginResultVO resultVO = new LoginResultVO();
		LoginDAO dao = new LoginDAO();
		
		resultVO = dao.login(vo);
		
		if(resultVO.getErrCd().equals("Y") ){
			LoginVO loginUserVO = null;
			
			//System.out.println("### 세션 생성 시작 :"+loginUserVO );			
			for (int i = 0; i < resultVO.getVoList().size(); i++) {
				loginUserVO = (LoginVO) resultVO.getVoList().get(i);
			}
			
			logger.debug("### loginUserVO :"+loginUserVO );
			request.getSession().setAttribute("loginUserVO", loginUserVO);
		}	


		return resultVO;
	}
	
	public void logout(HttpServletResponse response) {

		CookieManager.removeNexessCookie(NLS_DOMAIN, response);
		CookieManager.removeCookie(SECode.USER_URL, NLS_DOMAIN, response);
		CookieManager.removeCookie(SECode.R_TOA, NLS_DOMAIN, response);

	}
	
}