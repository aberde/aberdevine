package whoya.egovframework.com.cop.bbs.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.cop.bbs.service.WhoyaEgovBBSUseInfoManageService;
import egovframework.com.cop.bbs.service.BoardUseInf;
import egovframework.com.cop.bbs.service.BoardUseInfVO;
import egovframework.com.cop.bbs.service.EgovBBSUseInfoManageService;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;

/**
 * 게시판 이용정보를 관리하기 위한 서비스 구현 클래스
 */
@Service("whoyaEgovBBSUseInfoManageService")
public class WhoyaEgovBBSUseInfoManageServiceImpl extends AbstractServiceImpl implements WhoyaEgovBBSUseInfoManageService {
	
	@Resource(name = "EgovBBSUseInfoManageService")
	private EgovBBSUseInfoManageService bbsUseService;
	
	/**
	 * 게시판 사용정보 목록을 조회한다.
	 * 
	 * @see egovframework.com.cop.bbs.service.com.service.EgovBBSUseInfoManageService#selectBBSUseInfs(egovframework.com.cop.bbs.service.com.service.BoardUseInfVO)
	 */
	public Map<String, Object> selectBBSUseInfs(BoardUseInfVO bdUseVO) throws Exception {
		return bbsUseService.selectBBSUseInfs(bdUseVO);
	}

    /**
     * 게시판 사용정보를 등록한다.
     * 
     * @see egovframework.com.cop.bbs.service.com.service.EgovBBSUseInfoManageService#insertBBSUseInf(egovframework.com.cop.bbs.service.com.service.BoardUseInf)
     */
    public void insertBBSUseInf(BoardUseInf bdUseInf) throws Exception {
    	bbsUseService.insertBBSUseInf(bdUseInf);
    }

    /**
     * 게시판 사용정보에 대한 상세정보를 조회한다.
     * 
     * @see egovframework.com.cop.bbs.service.com.service.EgovBBSUseInfoManageService#selectBBSUseInf(egovframework.com.cop.bbs.service.com.service.BoardUseInfVO)
     */
    public BoardUseInfVO selectBBSUseInf(BoardUseInfVO bdUseVO) throws Exception {
    	return bbsUseService.selectBBSUseInf(bdUseVO);
    }

    /**
     * 게시판 사용정보를 수정한다.
     * 
     * @see egovframework.com.cop.bbs.service.com.service.EgovBBSUseInfoManageService#updateBBSUseInf(egovframework.com.cop.bbs.service.com.service.BoardUseInf)
     */
    public void updateBBSUseInf(BoardUseInf bdUseInf) throws Exception {
    	bbsUseService.updateBBSUseInf(bdUseInf);
    }
	
//    @Resource(name = "BBSUseInfoManageDAO")
//    private BBSUseInfoManageDAO bbsUseDAO;
//
//    /**
//     * 게시판 사용 정보를 삭제한다.
//     * 
//     * @see egovframework.com.cop.bbs.service.com.service.EgovBBSUseInfoManageService#deleteBBSUseInf(egovframework.com.cop.bbs.service.com.service.BoardUseInf)
//     */
//    public void deleteBBSUseInf(BoardUseInf bdUseInf) throws Exception {
//	bbsUseDAO.deleteBBSUseInf(bdUseInf);
//    }
//
//    /**
//     * 게시판에 대한 사용정보를 삭제한다.
//     * 
//     * @see egovframework.com.cop.bbs.service.EgovBBSUseInfoManageService#deleteBBSUseInfByBoardId(egovframework.com.cop.bbs.service.BoardUseInf)
//     */
//    public void deleteBBSUseInfByBoardId(BoardUseInf bdUseInf) throws Exception {
//	bbsUseDAO.deleteBBSUseInfByBoardId(bdUseInf);
//    }
//
//    /**
//     * 커뮤니티, 동호회에 사용되는 게시판 사용정보에 대한 목록을 조회한다.
//     * 
//     * @see egovframework.com.cop.bbs.service.EgovBBSUseInfoManageService#selectBBSUseInfsByTrget(egovframework.com.cop.bbs.service.BoardUseInfVO)
//     */
//    public Map<String, Object> selectBBSUseInfsByTrget(BoardUseInfVO bdUseVO) throws Exception {
//	List<BoardUseInfVO> result = bbsUseDAO.selectBBSUseInfsByTrget(bdUseVO);
//	int cnt = bbsUseDAO.selectBBSUseInfsCntByTrget(bdUseVO);
//	
//	Map<String, Object> map = new HashMap<String, Object>();
//	
//	map.put("resultList", result);
//	map.put("resultCnt", Integer.toString(cnt));
//
//	return map;
//    }
//
//    /**
//     * 커뮤니티, 동호회에 사용되는 게시판 사용정보를 수정한다.
//     */
//    public void updateBBSUseInfByTrget(BoardUseInf bdUseInf) throws Exception {
//	bbsUseDAO.updateBBSUseInfByTrget(bdUseInf);
//    }
}
