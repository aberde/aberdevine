package whoya.egovframework.com.sec.ram.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.com.sec.ram.service.AuthorRoleManageVO;

/**
 * 권한별 롤 관리에 관한 서비스 인터페이스 클래스를 정의한다.
 */

public interface WhoyaEgovAuthorRoleManageService {
	
	/**
	 * 권한 롤 관계정보 목록 조회
	 * @param authorRoleManageVO AuthorRoleManageVO
	 * @return List<AuthorRoleManageVO>
	 * @exception Exception
	 */
	public List<AuthorRoleManageVO> selectAuthorRoleList(AuthorRoleManageVO authorRoleManageVO) throws Exception;
	
	/**
	 * 권한 롤 관계정보를 화면에서 입력하여 입력항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param request
	 * @param response
	 * @exception Exception
	 */
	public void saveAuthorRole(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
