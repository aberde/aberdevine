package whoya.egovframework.com.cop.cmy.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.cop.cmy.service.WhoyaEgovCommunityManageService;
import egovframework.com.cop.cmy.service.Community;
import egovframework.com.cop.cmy.service.CommunityVO;
import egovframework.com.cop.cmy.service.EgovCommunityManageService;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;

/**
 * 커뮤니티 정보를 관리하기 위한 서비스 구현  클래스
 */
@Service("WhoyaEgovCommunityManageService")
public class WhoyaEgovCommunityManageServiceImpl extends AbstractServiceImpl implements WhoyaEgovCommunityManageService {
	
	@Resource(name = "EgovCommunityManageService")
	private EgovCommunityManageService cmmntyService;
	
	/**
	 * 커뮤니티 정보 목록을 조회한다.
	 * 
	 * @see egovframework.com.cop.cmy.service.EgovCommunityManageService#selectCommunityInfs(egovframework.com.cop.cmy.service.CommunityVO)
	 */
	public Map<String, Object> selectCommunityInfs(CommunityVO cmmntyVO) throws Exception {
		return cmmntyService.selectCommunityInfs(cmmntyVO);
	}
	
	/**
	 * 커뮤니티에 대한 정보를 등록한다.
	 * 
	 * @see egovframework.com.cop.cmy.service.EgovCommunityManageService#insertCommunityInf(egovframework.com.cop.cmy.service.Community)
	 */
	public void insertCommunityInf(Community cmmnty) throws Exception {
		cmmntyService.insertCommunityInf(cmmnty);
	}
	
	/**
     * 커뮤니티에 대한 특정 사용자 정보를 조회한다.
     * 
     * @see egovframework.com.cop.cmy.service.EgovCommunityManageService#selectCommunityInf(egovframework.com.cop.cmy.service.CommunityVO)
     */
    public Map<String, Object> selectCommunityInf(CommunityVO cmmntyVO) throws Exception {
    	return cmmntyService.selectCommunityInf(cmmntyVO);
    }
    
    /**
     * 커뮤니티 정보를 수정한다.
     * 
     * @see egovframework.com.cop.cmy.service.EgovCommunityManageService#updateCommunityInf(egovframework.com.cop.cmy.service.Community)
     */
    public void updateCommunityInf(Community cmmnty) throws Exception {
    	cmmntyService.updateCommunityInf(cmmnty);
    }
	
    /**
     * 커뮤니티에 대한 정보를 삭제한다.
     * 
     * @see egovframework.com.cop.cmy.service.EgovCommunityManageService#deleteCommunityInf(egovframework.com.cop.cmy.service.Community)
     */
    public void deleteCommunityInf(Community cmmnty) throws Exception {
    	cmmntyService.deleteCommunityInf(cmmnty);
    }
//    @Resource(name = "EgovBBSAttributeManageService")
//    private EgovBBSAttributeManageService bbsAttrbService;
//
//    @Resource(name = "EgovCmyBBSUseInfoManageService")
//    private EgovCmyBBSUseInfoManageService bbsUseService;
//
//    @Resource(name = "CommunityManageDAO")
//    private CommunityManageDAO cmmntyDAO;
//
//    @Resource(name = "egovCmmntyIdGnrService")
//    private EgovIdGnrService idgenService;
//
//    Logger log = Logger.getLogger(this.getClass());
//
//    /**
//     * 커뮤니티 사용정보를 삭제한다.
//     * 
//     * @see egovframework.com.cop.cmy.service.EgovCommunityManageService#deleteCommunityUserInf(egovframework.com.cop.cmy.service.CommunityUser)
//     */
//    public void deleteCommunityUserInf(CommunityUser cmmntyUser) throws Exception {
//	cmmntyDAO.deleteCommunityUserInf(cmmntyUser);
//
//	BoardUseInfVO bdUseVO = new BoardUseInfVO();
//	
//	bdUseVO.setLastUpdusrId(cmmntyUser.getLastUpdusrId());
//	bdUseVO.setCmmntyId(cmmntyUser.getCmmntyId());
//	bdUseVO.setTrgetId(cmmntyUser.getEmplyrId());
//	
//	bbsUseService.deleteBBSUseInfByCmmnty(bdUseVO);
//    }
//
//    /**
//     * 커뮤니티에 대한 게시판 사용정보를 등록한다.
//     * 
//     * @see egovframework.com.cop.cmy.service.EgovCommunityManageService#insertCommunityBBSUseInf(egovframework.com.cop.bbs.service.com.service.BoardUseInf)
//     */
//    public void insertCommunityBBSUseInf(BoardUseInf bdUseInf) throws Exception {
//	//cmmntyDAO.insertCommunityBBSUseInf(bdUseInf);
//	//커뮤니티에 게시판을 하나 추가하게 되면 - _- 해당 게시판이 등록된 커뮤니티의
//	//모든 소속사용자에게 사용 권한을 줘야하나 - _-? 일단 그렇게 진행
//    }
//
//    /**
//     * 커뮤니티용 게사판 정보를 생성한다.
//     * 
//     * @param cmmnty
//     * @return
//     */
//    private List<BoardMaster> makeBdMstrListforCmmnty(Community cmmnty) {
//	BoardMaster bdMstr;
//	ArrayList<BoardMaster> result = new ArrayList<BoardMaster>();
//
//	for (int i = 0; i < 5; i++) {
//	    bdMstr = new BoardMaster();
//	    
//	    bdMstr.setFrstRegisterId(cmmnty.getFrstRegisterId());
//	    bdMstr.setUseAt("Y");
//	    bdMstr.setBbsUseFlag("Y");
//	    bdMstr.setTrgetId(cmmnty.getCmmntyId());
//	    bdMstr.setRegistSeCode("REGC06");
//	    
//	    if (i == 0) {
//		bdMstr.setBbsTyCode("BBST04");
//		bdMstr.setReplyPosblAt("N");
//		bdMstr.setFileAtchPosblAt("N");
//		bdMstr.setBbsAttrbCode("BBSA03");
//		bdMstr.setBbsNm("방명록");
//	    } else if (i == 1) {
//		bdMstr.setBbsTyCode("BBST03");
//		bdMstr.setReplyPosblAt("N");
//		bdMstr.setFileAtchPosblAt("Y");
//		bdMstr.setPosblAtchFileNumber(3);
//		bdMstr.setBbsAttrbCode("BBSA01");
//		bdMstr.setBbsNm("공지게시판");
//	    } else if (i == 3) {
//		bdMstr.setBbsTyCode("BBST01");
//		bdMstr.setReplyPosblAt("Y");
//		bdMstr.setFileAtchPosblAt("N");
//		bdMstr.setBbsAttrbCode("BBSA03");
//		bdMstr.setBbsNm("자유게시판");
//	    } else if (i == 4) {
//		bdMstr.setBbsTyCode("BBST01");
//		bdMstr.setReplyPosblAt("N");
//		bdMstr.setFileAtchPosblAt("Y");
//		bdMstr.setPosblAtchFileNumber(5);
//		bdMstr.setBbsAttrbCode("BBSA03");
//		bdMstr.setBbsNm("자료실");
//	    } else {
//		bdMstr.setBbsTyCode("BBST01");
//		bdMstr.setReplyPosblAt("N");
//		bdMstr.setFileAtchPosblAt("Y");
//		bdMstr.setPosblAtchFileNumber(5);
//		bdMstr.setBbsAttrbCode("BBSA02");
//		bdMstr.setBbsNm("갤러리");
//	    }
//	    
//	    bdMstr.setBbsIntrcn(cmmnty.getCmmntyNm() + " - " + bdMstr.getBbsNm());
//	    
//	    result.add(bdMstr);
//	}
//	
//	return result;
//    }
//
//    /**
//     * 커뮤니티 사용자 정보를 등록한다.
//     * 
//     * @see egovframework.com.cop.cmy.service.EgovCommunityManageService#insertCommunityUserInf(egovframework.com.cop.cmy.service.CommunityUser)
//     */
//    public String insertCommunityUserInf(CommunityUser cmmntyUser) throws Exception {
//	//cmmntyId
//	CommunityVO vo = new CommunityVO();
//	
//	vo.setCmmntyId(cmmntyUser.getCmmntyId());
//
//	String retVal = "";
//	int cnt = cmmntyDAO.checkExistUser(cmmntyUser);
//	if (cnt == 0) {
//	    cmmntyDAO.insertCommunityUserInf(cmmntyUser);
//
//	    List<CommunityVO> tmpList = cmmntyDAO.selectCommunityBBSUseInf(vo);
//	    
//	    BoardUseInf bdUseInf;
//	    //신규로 추가된 사용자에 대해서 현재 커뮤니티의 모든 게시판에 대한 사용정보를 입력한다.
//
//	    Iterator<CommunityVO> iter = tmpList.iterator();
//	    while (iter.hasNext()) {
//		bdUseInf = new BoardUseInf();
//		
//		bdUseInf.setFrstRegisterId(cmmntyUser.getFrstRegisterId());
//		bdUseInf.setBbsId(((CommunityVO)iter.next()).getBbsId());
//		bdUseInf.setTrgetId(cmmntyUser.getEmplyrId());
//		bdUseInf.setRegistSeCode("REGC07");
//		bdUseInf.setUseAt("Y");
//		
//		bbsUseService.insertBBSUseInf(bdUseInf);
//	    }
//	} else {
//	    retVal = "EXIST";
//	}
//	
//	return retVal;
//    }
//	
//	/**
//     * 커뮤니티 사용자 정보를 확인한다.
//     * 
//     * @see egovframework.com.cop.cmy.service.EgovCommunityManageService#checkCommunityUserInf(egovframework.com.cop.cmy.service.CommunityUser)
//     */
//    public String checkCommunityUserInf(CommunityUser cmmntyUser) throws Exception {
//	// 회원가입 승인처리 적용시 기존 insertCommunityUserInf 대신 사용자 확인만 확인
//
//	//cmmntyId
//	CommunityVO vo = new CommunityVO();
//	vo.setCmmntyId(cmmntyUser.getCmmntyId());
//
//	if (cmmntyDAO.checkExistUser(cmmntyUser) == 0) {
//	    return "";
//	} else {
//	    return "EXIST";
//	}
//    }
//
//    /**
//     * 커뮤니티 게사판 사용정보 목록을 조회한다.
//     * 
//     * @see egovframework.com.cop.cmy.service.EgovCommunityManageService#selectCommunityBBSUseInf(egovframework.com.cop.cmy.service.CommunityVO)
//     */
//    public List<CommunityVO> selectCommunityBBSUseInf(CommunityVO cmmntyVO) throws Exception {
//	return cmmntyDAO.selectCommunityBBSUseInf(cmmntyVO);
//    }
//
//    /**
//     * 커뮤니티 관리자 정보를 조회한다.
//     * 
//     * @param cmmntyVO
//     * @return
//     * @throws Exception
//     */
//    public CommunityUser selectManager(CommunityVO cmmntyVO) throws Exception {
//	CommunityUser cmmntyUser = new CommunityUser();
//
//	List<CommunityUser> managers = cmmntyDAO.selectCommunityManagerInfs(cmmntyVO);
//
//	if (managers.size() == 1) {
//	    cmmntyUser.setEmplyrId(managers.get(0).getEmplyrId());
//	    cmmntyUser.setEmplyrNm(managers.get(0).getEmplyrNm());
//	} else if (managers.size() > 1) {
//	    cmmntyUser.setEmplyrId(managers.get(0).getEmplyrId());
//	    cmmntyUser.setEmplyrNm(managers.get(0).getEmplyrNm() + "외 " + (managers.size() - 1) + "명");
//	} else {
//	    // no-op
//	    log.debug("No managers...");
//	}
//
//	return cmmntyUser;
//    }
//
//    /**
//     * 커뮤니티 사용자 정보에 대한 목록을 조회한다.
//     * 
//     * @see egovframework.com.cop.cmy.service.EgovCommunityManageService#selectCommunityUserInfs(egovframework.com.cop.cmy.service.CommunityUserVO)
//     */
//    public Map<String, Object> selectCommunityUserInfs(CommunityUserVO cmmntyUserVO) throws Exception {
//	return null;
//    }
//
//    /**
//     * 커뮤니티 게시판 사용정보를 수정한다.
//     * 
//     * @see egovframework.com.cop.cmy.service.EgovCommunityManageService#updateCommunityBBSUseInf(egovframework.com.cop.bbs.service.com.service.BoardUseInf)
//     */
//    public void updateCommunityBBSUseInf(BoardUseInf bdUseInf) throws Exception {
//	cmmntyDAO.updateCommunityBBSUseInf(bdUseInf);
//    }
//
//    /**
//     * 커뮤니티 사용자 정보를 수정한다.
//     * 
//     * @see egovframework.com.cop.cmy.service.EgovCommunityManageService#updateCommunityUserInf(egovframework.com.cop.cmy.service.CommunityUser)
//     */
//    public void updateCommunityUserInf(CommunityUser cmmntyUser) throws Exception {
//	cmmntyDAO.updateCommunityUserInf(cmmntyUser);
//    }
//
//    /**
//     * 포트릿을 위한 커뮤니티 정보 목록 정보를 조회한다.
//     * 
//     * @see egovframework.com.cop.cmy.service.EgovCommunityManageService#selectCmmntyListPortlet(egovframework.com.cop.cmy.service.CommunityVO)
//     */
//    public List<CommunityVO> selectCmmntyListPortlet(CommunityVO cmmntyVO) throws Exception {
//	return cmmntyDAO.selectCmmntyListPortlet(cmmntyVO);
//    }
//
//    /**
//     * 커뮤니티용 템플릿 경로명을 조회한다.
//     * 
//     * @see egovframework.com.cop.cmy.service.EgovCommunityManageService#selectCmmntyTemplat(egovframework.com.cop.cmy.service.CommunityVO)
//     */
//    public String selectCmmntyTemplat(CommunityVO cmmntyVO) throws Exception {
//	return cmmntyDAO.selectCmmntyTemplat(cmmntyVO);
//    }
//
//    /**
//     * 모든 커뮤니티 목록을 조회한다.
//     * 
//     * @see egovframework.com.cop.cmy.service.EgovCommunityManageService#selectAllCmmnty(egovframework.com.cop.cmy.service.CommunityVO)
//     */
//    public List<CommunityVO> selectAllCmmnty(CommunityVO cmmntyVO) throws Exception {
//	return cmmntyDAO.selectAllCmmnty(cmmntyVO);
//    }
//    
//    /**
//     * 관리자 여부를 확인한다.
//     */
//    public boolean isManager(CommunityUser cmmntyUser) throws Exception {
//	CommunityUser result = cmmntyDAO.selectSingleCommunityUserInf(cmmntyUser);
//	
//	if (result.getMngrAt().equals("Y") && result.getUseAt().equals("Y")) {
//	    return true;
//	}
//	
//	return false;
//    }
}
