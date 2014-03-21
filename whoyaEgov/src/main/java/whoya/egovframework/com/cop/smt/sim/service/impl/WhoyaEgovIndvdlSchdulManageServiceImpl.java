package whoya.egovframework.com.cop.smt.sim.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.cop.smt.sim.service.WhoyaEgovIndvdlSchdulManageService;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cop.smt.sim.service.EgovIndvdlSchdulManageService;
import egovframework.com.cop.smt.sim.service.IndvdlSchdulManageVO;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
/**
 * 일정관리를 처리하는 ServiceImpl Class 구현
 */
@Service("whoyaEgovIndvdlSchdulManageService")
public class WhoyaEgovIndvdlSchdulManageServiceImpl extends AbstractServiceImpl implements WhoyaEgovIndvdlSchdulManageService{

    @Resource(name = "egovIndvdlSchdulManageService")
    private EgovIndvdlSchdulManageService egovIndvdlSchdulManageService;
    
    /**
     * 일정 목록을 조회한다. 
     * @param searchVO - 조회할 정보가 담긴 VO
     * @return List
     * @throws Exception
     */
    public List selectIndvdlSchdulManageList(ComDefaultVO searchVO) throws Exception{
        return egovIndvdlSchdulManageService.selectIndvdlSchdulManageList(searchVO);
    }
    
    /**
     * 일정를(을) 등록한다.
     * @param indvdlSchdulManageVO - 조회할 정보가 담긴 VO
     * @throws Exception
     */
    public void insertIndvdlSchdulManage(IndvdlSchdulManageVO indvdlSchdulManageVO) throws Exception {
        egovIndvdlSchdulManageService.insertIndvdlSchdulManage(indvdlSchdulManageVO);
    }
  
    /**
     * 일정 목록을 Map(map)형식으로 조회한다. 
     * @param Map(map) - 조회할 정보가 담긴 VO
     * @return List
     * @throws Exception
     */
    public List selectIndvdlSchdulManageRetrieve(Map map) throws Exception{
        return egovIndvdlSchdulManageService.selectIndvdlSchdulManageRetrieve(map);
    }
  
    /**
     * 일정를(을) 상세조회 한다.
     * @param IndvdlSchdulManage - 회정정보가 담김 VO
     * @return List
     * @throws Exception
     */
    public List selectIndvdlSchdulManageDetail(IndvdlSchdulManageVO indvdlSchdulManageVO) throws Exception{
        return egovIndvdlSchdulManageService.selectIndvdlSchdulManageDetail(indvdlSchdulManageVO);
    }
  
    /**
     * 일정를(을) 수정한다.
     * @param indvdlSchdulManageVO - 조회할 정보가 담긴 VO
     * @throws Exception
     */
    public void updateIndvdlSchdulManage(IndvdlSchdulManageVO indvdlSchdulManageVO) throws Exception{
        egovIndvdlSchdulManageService.updateIndvdlSchdulManage(indvdlSchdulManageVO);
    }
  
    /**
     * 일정를(을) 삭제한다.
     * @param indvdlSchdulManageVO - 조회할 정보가 담긴 VO
     * @throws Exception
     */
    public void deleteIndvdlSchdulManage(IndvdlSchdulManageVO indvdlSchdulManageVO) throws Exception{
        egovIndvdlSchdulManageService.deleteIndvdlSchdulManage(indvdlSchdulManageVO);
    }
//	//final private Log log = LogFactory.getLog(this.getClass());
//	
//	@Resource(name="indvdlSchdulManageDao")
//	private IndvdlSchdulManageDao dao;
//
//	
//	@Resource(name="deptSchdulManageIdGnrService") 
//	private EgovIdGnrService idgenService;
//
//	
//    /**
//	 * 메인페이지/일정관리조회
//	 * @param map - 조회할 정보가 담긴 map
//	 * @return List
//	 * @throws Exception
//	 */
//	public List selectIndvdlSchdulManageMainList(Map map) throws Exception{
//		return (List)dao.selectIndvdlSchdulManageMainList(map);
//	}
//	
//    /**
//	 * 일정 목록을 VO(model)형식으로 조회한다. 
//	 * @param indvdlSchdulManageVO - 조회할 정보가 담긴 VO
//	 * @return List
//	 * @throws Exception
//	 */
//	public IndvdlSchdulManageVO selectIndvdlSchdulManageDetailVO(IndvdlSchdulManageVO indvdlSchdulManageVO) throws Exception{
//		return (IndvdlSchdulManageVO)dao.selectIndvdlSchdulManageDetailVO(indvdlSchdulManageVO);
//	}
//	
//    /**
//	 * 일정를(을) 목록 전체 건수를(을) 조회한다.
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @return int
//	 * @throws Exception
//	 */
//	public int selectIndvdlSchdulManageListCnt(ComDefaultVO searchVO) throws Exception{
//		return (Integer)dao.selectIndvdlSchdulManageListCnt(searchVO);
//	}
}
