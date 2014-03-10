package whoya.egovframework.com.uss.olp.qim.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.uss.olp.qim.service.WhoyaEgovQustnrItemManageService;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.uss.olp.qim.service.EgovQustnrItemManageService;
import egovframework.com.uss.olp.qim.service.QustnrItemManageVO;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
/**
 * 설문항목관리를 처리하는 ServiceImpl Class 구현
 */
@Service("whoyaEgovQustnrItemManageService")
public class WhoyaEgovQustnrItemManageServiceImpl extends AbstractServiceImpl implements WhoyaEgovQustnrItemManageService{
    
    @Resource(name = "egovQustnrItemManageService")
    private EgovQustnrItemManageService egovQustnrItemManageService;
    
    /**
     * 설문항목 목록을 조회한다. 
     * @param searchVO - 조회할 정보가 담긴 VO
     * @return List
     * @throws Exception
     */
    public List selectQustnrItemManageList(ComDefaultVO searchVO) throws Exception{
        return egovQustnrItemManageService.selectQustnrItemManageList(searchVO);
    }
    
    /**
     * 설문항목를(을) 등록한다.
     * @param searchVO - 조회할 정보가 담긴 VO
     * @throws Exception
     */
    public void insertQustnrItemManage(QustnrItemManageVO qustnrItemManageVO) throws Exception {
        egovQustnrItemManageService.insertQustnrItemManage(qustnrItemManageVO);
    }
  
    /**
     * 설문항목를(을) 상세조회 한다.
     * @param QustnrItemManage - 회정정보가 담김 VO
     * @return List
     * @throws Exception
     */
    public List selectQustnrItemManageDetail(QustnrItemManageVO qustnrItemManageVO) throws Exception{
        return egovQustnrItemManageService.selectQustnrItemManageDetail(qustnrItemManageVO);
    }
  
    /**
     * 설문항목를(을) 삭제한다.
     * @param searchVO - 조회할 정보가 담긴 VO
     * @throws Exception
     */
    public void deleteQustnrItemManage(QustnrItemManageVO qustnrItemManageVO) throws Exception{
        egovQustnrItemManageService.deleteQustnrItemManage(qustnrItemManageVO);
    }
  
    /**
     * 설문항목를(을) 수정한다.
     * @param searchVO - 조회할 정보가 담긴 VO
     * @throws Exception
     */
    public void updateQustnrItemManage(QustnrItemManageVO qustnrItemManageVO) throws Exception{
        egovQustnrItemManageService.updateQustnrItemManage(qustnrItemManageVO);
    }
//	//final private Log log = LogFactory.getLog(this.getClass());
//	
//	@Resource(name="qustnrItemManageDao")
//	private QustnrItemManageDao dao;
//	
//	@Resource(name="egovQustnrItemManageIdGnrService")
//	private EgovIdGnrService idgenService;
//	
//    /**
//	 * 설문템플릿(을)를  목록을 조회한다. 
//	 * @param qustnrItemManageVO - 설문항목 정보 담김 VO
//	 * @return List
//	 * @throws Exception
//	 */	
//	public List selectQustnrTmplatManageList(QustnrItemManageVO qustnrItemManageVO) throws Exception{
//		return (List)dao.selectQustnrTmplatManageList(qustnrItemManageVO);
//	}
//	
//	
//    /**
//	 * 설문항목를(을) 목록 전체 건수를(을) 조회한다.
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @return int
//	 * @throws Exception
//	 */
//	public int selectQustnrItemManageListCnt(ComDefaultVO searchVO) throws Exception{
//		return (Integer)dao.selectQustnrItemManageListCnt(searchVO);
//	}
}
