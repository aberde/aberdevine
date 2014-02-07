package whoya.egovframework.com.cop.bbs.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.cop.bbs.service.WhoyaEgovBBSAttributeManageService;
import egovframework.com.cop.bbs.service.BoardMaster;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.EgovBBSAttributeManageService;

/**
 * 게시판 속성관리를 위한 서비스 구현 클래스
 */
@Service("whoyaEgovBBSAttributeManageService")
public class WhoyaEgovBBSAttributeManageServiceImpl implements WhoyaEgovBBSAttributeManageService {

	@Resource(name="EgovBBSAttributeManageService")
	EgovBBSAttributeManageService egovBBSAttributeManageService;
	
    /**
     * 게시판 속성 정보의 목록을 조회 한다.
     * 
     * @param BoardMasterVO
     */
    public Map<String, Object> selectBBSMasterInfs(BoardMasterVO searchVO) throws Exception {
    	return egovBBSAttributeManageService.selectBBSMasterInfs(searchVO);
    }

    /**
     * 사용중이지 않은 게시판 속성 정보의 목록을 조회 한다.
     */
    public Map<String, Object> selectNotUsedBdMstrList(BoardMasterVO searchVO) throws Exception {
		return egovBBSAttributeManageService.selectNotUsedBdMstrList(searchVO);
    }

    /**
     * 신규 게시판 속성정보를 생성한다.
     * 
     * @see egovframework.com.cop.bbs.brd.service.EgovBBSAttributeManageService#insertBBSMastetInf(egovframework.com.cop.bbs.brd.service.BoardMaster)
     */
    public String insertBBSMastetInf(BoardMaster boardMaster) throws Exception {
		return egovBBSAttributeManageService.insertBBSMastetInf(boardMaster);
    }

    /**
     * 게시판 속성정보를 수정한다.
     * 
     * @see egovframework.com.cop.bbs.brd.service.EgovBBSAttributeManageService#updateBBSMasterInf(egovframework.com.cop.bbs.brd.service.BoardMaster)
     */
    public void updateBBSMasterInf(BoardMaster boardMaster) throws Exception {
		egovBBSAttributeManageService.updateBBSMasterInf(boardMaster);
    }

    /**
     * 게시판 속성정보 한 건을 상세조회한다.
     * 
     * @see egovframework.com.cop.bbs.brd.service.EgovBBSAttributeManageService#selectBBSMasterInf(egovframework.com.cop.bbs.brd.service.BoardMasterVO)
     */
    public BoardMasterVO selectBBSMasterInf(BoardMaster searchVO) throws Exception {
    	return egovBBSAttributeManageService.selectBBSMasterInf(searchVO);
    }
		
    /**
     * 등록된 게시판 속성정보를 삭제한다.
     * 
     * @see egovframework.com.cop.bbs.brd.service.EgovBBSAttributeManageService#deleteBBSMasterInf(egovframework.com.cop.bbs.brd.service.BoardMaster)
     */
    public void deleteBBSMasterInf(BoardMaster boardMaster) throws Exception {
    	egovBBSAttributeManageService.deleteBBSMasterInf(boardMaster);
    }
    
//    @Resource(name = "BBSAttributeManageDAO")
//    private BBSAttributeManageDAO attrbMngDAO;
//
//    @Resource(name = "BBSUseInfoManageDAO")
//    private BBSUseInfoManageDAO bbsUseDAO;
//
//    @Resource(name = "EgovUserInfManageService")
//    private EgovUserInfManageService userService;
//
//    @Resource(name = "egovBBSMstrIdGnrService")
//    private EgovIdGnrService idgenService;
//    
//    //---------------------------------
//    // 2009.06.26 : 2단계 기능 추가
//    //---------------------------------
//    @Resource(name = "BBSAddedOptionsDAO")
//    private BBSAddedOptionsDAO addedOptionsDAO;
//    ////-------------------------------
//
//    /**
//     * 게시판 속성 정보의 목록을 조회 한다.
//     * 
//     * @see egovframework.com.cop.bbs.brd.service.EgovBBSAttributeManageService#selectAllBBSMasteInf(egovframework.com.cop.bbs.brd.service.BoardMasterVO)
//     */
//    public List<BoardMasterVO> selectAllBBSMasteInf(BoardMasterVO vo) throws Exception {
//	return attrbMngDAO.selectAllBBSMasteInf(vo);
//    }
//
//    /**
//     * 템플릿의 유효여부를 점검한다.
//     * 
//     * @see egovframework.com.cop.bbs.brd.service.EgovBBSAttributeManageService#validateTemplate(egovframework.com.cop.bbs.brd.service.BoardMasterVO)
//     */
//    public void validateTemplate(BoardMasterVO searchVO) throws Exception {
//	log.debug("validateTemplate method ignored...");
//    }
//
//    /**
//     * 사용중인 게시판 속성 정보의 목록을 조회 한다.
//     */
//    public Map<String, Object> selectBdMstrListByTrget(BoardMasterVO vo) throws Exception {
//	List<BoardMasterVO> result = attrbMngDAO.selectBdMstrListByTrget(vo);
//	int cnt = attrbMngDAO.selectBdMstrListCntByTrget(vo);
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
//     * 커뮤니티, 동호회에서 사용중인 게시판 속성 정보의 목록을 전체조회 한다.
//     */
//    public List<BoardMasterVO> selectAllBdMstrByTrget(BoardMasterVO vo) throws Exception {
//	return attrbMngDAO.selectAllBdMstrByTrget(vo);
//    }
//
}
