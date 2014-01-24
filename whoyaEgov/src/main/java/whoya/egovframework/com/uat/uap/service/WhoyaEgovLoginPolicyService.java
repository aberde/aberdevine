package whoya.egovframework.com.uat.uap.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.com.uat.uap.service.LoginPolicyVO;

public interface WhoyaEgovLoginPolicyService {

	/**
	 * 로그인정책관리 목록을 조회한다.
	 * @param loginPolicyVO - 로그인정책관리 VO
	 * @return List - 로그인정책관리 목록
	 */
	public List<LoginPolicyVO> selectLoginPolicyList(LoginPolicyVO loginPolicyVO) throws Exception;
	
	/**
	 * 로그인정책관리 등록/수정/삭제한다.
	 * @param request
	 * @param response
	 */
	public void saveLoginPolicy(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
