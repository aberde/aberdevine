package whoya.egovframework.com.sec.ram.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import whoya.whoyaDataProcess;
import whoya.whoyaMap;
import whoya.common.Common;
import whoya.egovframework.com.sec.ram.service.WhoyaEgovAuthorManageService;
import egovframework.com.sec.ram.service.AuthorManage;
import egovframework.com.sec.ram.service.AuthorManageVO;
import egovframework.com.sec.ram.service.EgovAuthorManageService;

/**
 * 권한에 관한 ServiceImpl 클래스를 정의한다.
 */

@Service("whoyaEgovAuthorManageService")
public class WhoyaEgovAuthorManageServiceImpl implements WhoyaEgovAuthorManageService {
    
	@Resource(name="egovAuthorManageService")
	EgovAuthorManageService egovAuthorManageService;

    /**
	 * 권한 목록을 조회한다.
	 * @param authorManageVO AuthorManageVO
	 * @return List<AuthorManageVO>
	 * @exception Exception
	 */
    public List<AuthorManageVO> selectAuthorList(AuthorManageVO authorManageVO) throws Exception {
        return egovAuthorManageService.selectAuthorList(authorManageVO);
    }
    
    /**
	 * 권한을 조회한다.
	 * @param authorManageVO AuthorManageVO
	 * @return AuthorManageVO
	 * @exception Exception
	 */
    public AuthorManageVO selectAuthor(AuthorManageVO authorManageVO) throws Exception {
    	return egovAuthorManageService.selectAuthor(authorManageVO);
    }
    
    /**
	 * 권한을 등록한다.
	 * @param authorManage AuthorManage
	 * @exception Exception
	 */
    public void insertAuthor(AuthorManage authorManage) throws Exception {
    	egovAuthorManageService.insertAuthor(authorManage);
    }

    /**
	 * 권한을 수정한다.
	 * @param authorManage AuthorManage
	 * @exception Exception
	 */
    public void updateAuthor(AuthorManage authorManage) throws Exception {
    	egovAuthorManageService.updateAuthor(authorManage);
    }

    /**
	 * 권한을 삭제한다.
	 * @param authorManage AuthorManage
	 * @exception Exception
	 */
    public void deleteAuthor(AuthorManage authorManage) throws Exception {
    	egovAuthorManageService.deleteAuthor(authorManage);
    }
    
    /**
	 * 권한관리 다중 삭제한다.
	 * @param ids 삭제 할 데이터 키.
	 * @param rows 데이터.
	 */
	public void saveAuthor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] ids = request.getParameter("ids").split(",");
		
	    whoyaDataProcess  data = new whoyaDataProcess();
	    whoyaMap rows = new whoyaMap();
	    rows = data.dataProcess(request);
	    
		for (int i = 0; i < ids.length; i++) {
			whoyaMap cols = (whoyaMap) rows.get(ids[i]);
			AuthorManage authorManage = new AuthorManage();
			authorManage = (AuthorManage)Common.convertWhoyaMapToObject(cols, authorManage);
		    
		    if (cols.get("!nativeeditor_status").equals("deleted" ) ) {
		    	egovAuthorManageService.deleteAuthor(authorManage);
		    }
		}
		data.dataRefresh(request, response);
	}
}
