package whoya.egovframework.com.uat.uia.service;

import egovframework.com.cmm.LoginVO;


/**
 * 일반 로그인, 인증서 로그인을 처리하는 비즈니스 인터페이스 클래스
 */
public interface WhoyaEgovLoginService {
	
	/**
	 * 일반 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    LoginVO actionLogin(LoginVO vo) throws Exception;
	
//	/**
//     * 2011.08.26
//	 * EsntlId를 이용한 로그인을 처리한다
//	 * @param vo LoginVO
//	 * @return LoginVO
//	 * @exception Exception
//	 */
//    public LoginVO actionLoginByEsntlId(LoginVO vo) throws Exception;
//    
//    /**
//	 * 인증서 로그인을 처리한다
//	 * @param vo LoginVO
//	 * @return LoginVO
//	 * @exception Exception
//	 */
//    LoginVO actionCrtfctLogin(LoginVO vo) throws Exception;
//    
//    /**
//	 * 아이디를 찾는다.
//	 * @param vo LoginVO
//	 * @return LoginVO
//	 * @exception Exception
//	 */
//    LoginVO searchId(LoginVO vo) throws Exception;
//    
//    /**
//	 * 비밀번호를 찾는다.
//	 * @param vo LoginVO
//	 * @return boolean
//	 * @exception Exception
//	 */
//    boolean searchPassword(LoginVO vo) throws Exception;
}
