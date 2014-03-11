package whoya.egovframework.com.uss.olp.qri.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.uss.olp.qri.service.WhoyaEgovQustnrRespondInfoService;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.uss.olp.qri.service.EgovQustnrRespondInfoService;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
/**
 * 설문조사 ServiceImpl Class 구현 
 */
@Service("whoyaEgovQustnrRespondInfoService")
public class WhoyaEgovQustnrRespondInfoServiceImpl extends AbstractServiceImpl implements WhoyaEgovQustnrRespondInfoService{
  
    @Resource(name = "egovQustnrRespondInfoService")
    private EgovQustnrRespondInfoService egovQustnrRespondInfoService;
    
    /**
     *  설문조사(설문등록)를(을) 목록을 조회한다. 
     * @param searchVO - 조회할 정보가 담긴 VO
     * @return List
     * @throws Exception
     */
    public List selectQustnrRespondInfoManageList(ComDefaultVO searchVO) throws Exception{
        return egovQustnrRespondInfoService.selectQustnrRespondInfoManageList(searchVO);
    }
  
    /**
     * 설문정보를 조회한다. 
     * @param map - 조회할 정보가 담긴 map
     * @return List
     * @throws Exception
     */
    public List selectQustnrRespondInfoManageComtnqestnrinfo(Map map) throws Exception{
        return egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqestnrinfo(map);
    }
    /**
     * 문항정보를 조회한다. 
     * @param map - 조회할 정보가 담긴 map
     * @return List
     * @throws Exception
     */
    public List selectQustnrRespondInfoManageComtnqustnrqesitm(Map map) throws Exception{
        return egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqustnrqesitm(map);
    }
    /**
     * 항목정보를 조회한다. 
     * @param map - 조회할 정보가 담긴 map
     * @return List
     * @throws Exception
     */
    public List selectQustnrRespondInfoManageComtnqustnriem(Map map) throws Exception{
        return egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqustnriem(map);
    }
    
    /**
     * 객관식 통계를 조회 조회한다. 
     * @param map - 조회할 정보가 담긴 map
     * @return List
     * @throws Exception
     */
    public List selectQustnrRespondInfoManageStatistics1(Map map) throws Exception{
        return egovQustnrRespondInfoService.selectQustnrRespondInfoManageStatistics1(map);
    }
    /**
     * 주관식 통계를 조회 조회한다. 
     * @param map - 조회할 정보가 담긴 map
     * @return List
     * @throws Exception
     */
    public List selectQustnrRespondInfoManageStatistics2(Map map) throws Exception{
        return egovQustnrRespondInfoService.selectQustnrRespondInfoManageStatistics2(map);
    }
//	//final private Log log = LogFactory.getLog(this.getClass());
//	
//	@Resource(name="qustnrRespondInfoDao")
//	private QustnrRespondInfoDao dao;
//
//	@Resource(name="qustnrRespondInfoIdGnrService")
//	private EgovIdGnrService idgenService;
//	
//	
//    /**
//	 * 설문템플릿을 조회한다.
//	 * @param map - 조회할 정보가 담긴 map
//	 * @return List
//	 * @throws Exception
//	 */
//	public List selectQustnrTmplatManage(Map map) throws Exception{
//		return (List)dao.selectQustnrTmplatManage(map);
//	}
//	
//    /**
//	 * 회원정보를 조회한다. 
//	 * @param map - 조회할 정보가 담긴 map
//	 * @return List
//	 * @throws Exception
//	 */
//	public Map selectQustnrRespondInfoManageEmplyrinfo(Map map) throws Exception{
//		return (Map)dao.selectQustnrRespondInfoManageEmplyrinfo(map);
//	}
//
//    /**
//	 * 설문조사(설문등록)를(을) 목록 전체 건수를(을) 조회한다.
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @return 
//	 * @throws Exception
//	 */
//	public int selectQustnrRespondInfoManageListCnt(ComDefaultVO searchVO) throws Exception{
//		return (Integer)dao.selectQustnrRespondInfoManageListCnt(searchVO);
//	}
//	
//    /**
//	 * 응답자결과(설문조사) 목록을 조회한다. 
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @return List
//	 * @throws Exception
//	 */
//	public List selectQustnrRespondInfoList(ComDefaultVO searchVO) throws Exception{
//		return (List)dao.selectQustnrRespondInfoList(searchVO);
//	}
//	
//    /**
//	 * 응답자결과(설문조사)를(을) 상세조회 한다.
//	 * @param QustnrRespondInfo - 회정정보가 담김 VO
//	 * @return List
//	 * @throws Exception
//	 */
//	public List selectQustnrRespondInfoDetail(QustnrRespondInfoVO qustnrRespondInfoVO) throws Exception{
//		return (List)dao.selectQustnrRespondInfoDetail(qustnrRespondInfoVO);
//	}
//	
//    /**
//	 * 응답자결과(설문조사)를(을) 목록 전체 건수를(을) 조회한다.
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @return int
//	 * @throws Exception
//	 */
//	public int selectQustnrRespondInfoListCnt(ComDefaultVO searchVO) throws Exception{
//		return (Integer)dao.selectQustnrRespondInfoListCnt(searchVO);
//	}
//
//    /**
//	 * 응답자결과(설문조사)를(을) 등록한다.
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @throws Exception
//	 */
//	public void insertQustnrRespondInfo(QustnrRespondInfoVO qustnrRespondInfoVO) throws Exception {
//		String sMakeId = idgenService.getNextStringId();
//		
//		qustnrRespondInfoVO.setQestnrQesrspnsId(sMakeId);
//
//		dao.insertQustnrRespondInfo(qustnrRespondInfoVO);
//	}
//	
//    /**
//	 * 응답자결과(설문조사)를(을) 수정한다.
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @throws Exception
//	 */
//	public void updateQustnrRespondInfo(QustnrRespondInfoVO qustnrRespondInfoVO) throws Exception{
//		dao.updateQustnrRespondInfo(qustnrRespondInfoVO);
//	}
//	
//    /**
//	 * 응답자결과(설문조사)를(을) 삭제한다.
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @throws Exception
//	 */
//	public void deleteQustnrRespondInfo(QustnrRespondInfoVO qustnrRespondInfoVO) throws Exception{
//		dao.deleteQustnrRespondInfo(qustnrRespondInfoVO);
//	}
}
