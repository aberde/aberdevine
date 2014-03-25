package whoya.egovframework.com.uss.ion.ulm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.uss.ion.ulm.service.WhoyaEgovUnityLinkService;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.uss.ion.ulm.service.EgovUnityLinkService;
import egovframework.com.uss.ion.ulm.service.UnityLink;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;

/**
 * 통합링크관리를 처리하는 ServiceImpl Class 구현
 */
@Service("whoyaEgovUnityLinkService")
public class WhoyaEgovUnityLinkServiceImpl extends AbstractServiceImpl implements WhoyaEgovUnityLinkService {
    
    /** egovOnlinePollService */
    @Resource(name = "egovUnityLinkService")
    private EgovUnityLinkService egovUnityLinkService;
    
    /**
     * 통합링크관리를(을) 목록을 조회 한다.
     * @param searchVO 조회할 정보가 담김 VO
     * @return List
     * @throws Exception
     */
    public List selectUnityLinkList(ComDefaultVO searchVO) throws Exception {
        return egovUnityLinkService.selectUnityLinkList(searchVO);
    }
    
    /**
     * 통합링크관리를(을) 등록한다.
     * @param unityLink 조회할 정보가 담긴 VO
     * @throws Exception
     */
    public void insertUnityLink(UnityLink unityLink)throws Exception {
        egovUnityLinkService.insertUnityLink(unityLink);
    }
  
    /**
     * 통합링크관리를(을) 상세조회 한다.
     * @param unityLink 조회할 정보가 담김 VO
     * @return List
     * @throws Exception
     */
    public UnityLink selectUnityLinkDetail(UnityLink unityLink) throws Exception {
        return egovUnityLinkService.selectUnityLinkDetail(unityLink);
    }
    
    /**
     * 통합링크관리를(을) 수정한다.
     * @param searchVO 조회할 정보가 담긴 VO
     * @throws Exception
     */
    public void updateUnityLink(UnityLink unityLink) throws Exception {
        egovUnityLinkService.updateUnityLink(unityLink);
    }

    /**
     * 통합링크관리를(을) 삭제한다.
     * @param searchVO 조회할 정보가 담긴 VO
     * @throws Exception
     */
    public void deleteUnityLink(UnityLink unityLink) throws Exception {
        egovUnityLinkService.deleteUnityLink(unityLink);
    }
//    @Resource(name = "onlineUnityLinkDao")
//    private UnityLinkDao dao;
//
//    @Resource(name = "egovUnityLinkIdGnrService")
//    private EgovIdGnrService idgenService;
//
//    /**
//     *통합링크관리 메인 셈플 목록을 조회한다.
//     * @param unityLink  통합링크관리 정보 담김 VO
//     * @return List
//     * @throws Exception
//     */
//    public List selectUnityLinkSample(UnityLink unityLink) throws Exception {
//        return (List)dao.selectUnityLinkSample(unityLink);
//    }
//
//    /**
//     * 통합링크관리를(을) 목록 전체 건수를(을) 조회한다.
//     * @param searchVO  조회할 정보가 담긴 VO
//     * @return int
//     * @throws Exception
//     */
//    public int selectUnityLinkListCnt(ComDefaultVO searchVO) throws Exception {
//        return (Integer)dao.selectUnityLinkListCnt(searchVO);
//    }
//    
}
