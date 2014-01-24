package whoya.egovframework.com.sec.ram.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.com.sec.ram.service.AuthorManageVO;

/**
 * 권한관리에 관한 서비스 인터페이스 클래스를 정의한다.
 */

public interface WhoyaEgovAuthorManageService {
	
	/**
	 * 개별사용자에게 할당된 권한리스트 조회
	 * @param authorManageVO AuthorManageVO
	 * @return List<AuthorManageVO>
	 * @exception Exception
	 */
	public List<AuthorManageVO> selectAuthorList(AuthorManageVO authorManageVO) throws Exception;

	/**
	 * 권한관리 등록/수정/삭제한다.
	 * @param request
	 * @param response
	 */
	public void saveAuthor(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
