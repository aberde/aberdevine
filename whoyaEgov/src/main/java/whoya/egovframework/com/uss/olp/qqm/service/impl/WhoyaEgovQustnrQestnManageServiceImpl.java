package whoya.egovframework.com.uss.olp.qqm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.uss.olp.qqm.service.WhoyaEgovQustnrQestnManageService;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.uss.olp.qqm.service.EgovQustnrQestnManageService;
import egovframework.com.uss.olp.qqm.service.QustnrQestnManageVO;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
/**
 * 설문문항을 처리하는 ServiceImpl Class 구현
 */
@Service("whoyaEgovQustnrQestnManageService")
public class WhoyaEgovQustnrQestnManageServiceImpl extends AbstractServiceImpl implements WhoyaEgovQustnrQestnManageService{

    @Resource(name = "egovQustnrQestnManageService")
    private EgovQustnrQestnManageService egovQustnrQestnManageService;
    
    /**
     * 설문문항 목록을 조회한다. 
     * @param searchVO - 조회할 정보가 담긴 VO
     * @return List
     * @throws Exception
     */
    public List selectQustnrQestnManageList(ComDefaultVO searchVO) throws Exception{
        return egovQustnrQestnManageService.selectQustnrQestnManageList(searchVO);
    }
    
    /**
     * 설문문항를(을) 등록한다.
     * @param searchVO - 조회할 정보가 담긴 VO
     * @throws Exception
     */
    public void insertQustnrQestnManage(QustnrQestnManageVO qustnrQestnManageVO) throws Exception {
        egovQustnrQestnManageService.insertQustnrQestnManage(qustnrQestnManageVO);
    }
  
    /**
     * 설문문항를(을) 삭제한다.
     * @param searchVO - 조회할 정보가 담긴 VO
     * @throws Exception
     */
    public void deleteQustnrQestnManage(QustnrQestnManageVO qustnrQestnManageVO) throws Exception{
        egovQustnrQestnManageService.deleteQustnrQestnManage(qustnrQestnManageVO);
    }
  
    /**
     * 설문문항를(을) 상세조회 한다.
     * @param QustnrQestnManage - 회정정보가 담김 VO
     * @return List
     * @throws Exception
     */
    public List selectQustnrQestnManageDetail(QustnrQestnManageVO qustnrQestnManageVO) throws Exception{
        return egovQustnrQestnManageService.selectQustnrQestnManageDetail(qustnrQestnManageVO);
    }
  
    /**
     * 설문문항를(을) 수정한다.
     * @param searchVO - 조회할 정보가 담긴 VO
     * @throws Exception
     */
    public void updateQustnrQestnManage(QustnrQestnManageVO qustnrQestnManageVO) throws Exception{
        egovQustnrQestnManageService.updateQustnrQestnManage(qustnrQestnManageVO);
    }
//	//final private Log log = LogFactory.getLog(this.getClass());
//	
//	@Resource(name="qustnrQestnManageDao")
//	private QustnrQestnManageDao dao;
//	
//	@Resource(name="egovQustnrQestnManageIdGnrService")
//	private EgovIdGnrService idgenService;
//	
//	
//    /**
//	 * 설문조사 응답자답변내용결과/기타답변내용결과 통계를 조회한다. 
//	 * @param Map - 설문지 정보가 담김 Parameter
//	 * @return Map
//	 * @throws Exception
//	 */
//	public List selectQustnrManageStatistics2(Map map) throws Exception{
//		return (List)dao.selectQustnrManageStatistics2(map);
//	}
//	
//    /**
//	 * 설문조사 통계를 조회한다. 
//	 * @param Map - 설문지 정보가 담김 Parameter
//	 * @return Map
//	 * @throws Exception
//	 */
//	public List selectQustnrManageStatistics(Map map) throws Exception{
//		return (List)dao.selectQustnrManageStatistics(map);
//	}
//    /**
//	 * 설문지정보 설문제목을 조회한다. 
//	 * @param Map - 설문지 정보가 담김 Parameter
//	 * @return Map
//	 * @throws Exception
//	 */
//	public Map selectQustnrManageQestnrSj(Map map) throws Exception{
//		return (Map)dao.selectQustnrManageQestnrSj(map);
//	}
//	
//    /**
//	 * 설문문항를(을) 목록 전체 건수를(을) 조회한다.
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @return int
//	 * @throws Exception
//	 */
//	public int selectQustnrQestnManageListCnt(ComDefaultVO searchVO) throws Exception{
//		return (Integer)dao.selectQustnrQestnManageListCnt(searchVO);
//	}
}
