package whoya.egovframework.com.sec.ram.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.com.sec.ram.service.AuthorManage;
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
	 * 개별사용자에게 할당된 권한 조회
	 * @param authorManageVO AuthorManageVO
	 * @exception Exception
	 */
	public AuthorManageVO selectAuthor(AuthorManageVO authorManageVO) throws Exception;
	
	/**
	 * 사용자의 시스테접근권한를 화면에서 입력하여 입력항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param authorManage AuthorManage
	 * @exception Exception
	 */
	public void insertAuthor(AuthorManage authorManage) throws Exception;
	
	/**
	 * 화면에 조회된 사용자권한정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
 	 * @param authorManage AuthorManage
	 * @exception Exception
	 */
	public void updateAuthor(AuthorManage authorManage) throws Exception;
	
	/**
	 * 시스템 사용자중 불필요한 시스템권한정보를 화면에 조회하여 데이터베이스에서 삭제
	 * @param authorManage AuthorManage
	 * @exception Exception
	 */
	public void deleteAuthor(AuthorManage authorManage) throws Exception;

	/**
	 * 권한관리 다중 삭제한다.
	 * @param request
	 * @param response
	 */
	public void saveAuthor(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
