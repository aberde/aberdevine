package whoya.egovframework.com.uat.uap.service;

import java.util.List;

import egovframework.com.uat.uap.service.LoginPolicy;
import egovframework.com.uat.uap.service.LoginPolicyVO;

public interface WhoyaEgovLoginPolicyService {

	/**
	 * 로그인정책관리 목록을 조회한다.
	 * @param loginPolicyVO - 로그인정책관리 VO
	 * @return List - 로그인정책관리 목록
	 */
	public List<LoginPolicyVO> selectLoginPolicyList(LoginPolicyVO loginPolicyVO) throws Exception;
	
	/**
	 * 로그인정책 목록의 상세정보를 조회한다.
	 * @param loginPolicyVO - 로그인정책 VO
	 * @return LoginPolicyVO - 로그인정책 VO
	 */
	public LoginPolicyVO selectLoginPolicy(LoginPolicyVO loginPolicyVO) throws Exception;
	
	/**
	 * 로그인정책 정보를 신규로 등록한다.
	 * @param loginPolicy - 로그인정책 model
	 */
	public void insertLoginPolicy(LoginPolicy loginPolicy) throws Exception;
	
	/**
	 * 기 등록된 로그인정책 정보를 수정한다.
	 * @param loginPolicy - 로그인정책 model
	 */
	public void updateLoginPolicy(LoginPolicy loginPolicy) throws Exception;

	/**
	 * 기 등록된 로그인정책 정보를 삭제한다.
	 * @param loginPolicy - 로그인정책 model
	 */
	public void deleteLoginPolicy(LoginPolicy loginPolicy) throws Exception;
	
//	/**
//	 * 로그인정책관리 등록/수정/삭제한다.
//	 * @param request
//	 * @param response
//	 */
//	public void saveLoginPolicy(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	
}
