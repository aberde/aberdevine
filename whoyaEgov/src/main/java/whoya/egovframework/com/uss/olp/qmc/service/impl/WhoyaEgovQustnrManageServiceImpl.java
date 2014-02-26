package whoya.egovframework.com.uss.olp.qmc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.uss.olp.qmc.service.WhoyaEgovQustnrManageService;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.uss.olp.qmc.service.EgovQustnrManageService;
import egovframework.com.uss.olp.qmc.service.QustnrManageVO;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
/**
 * 설문관리를 처리하는 ServiceImpl Class 구현
 */ 
@Service("whoyaEgovQustnrManageService")
public class WhoyaEgovQustnrManageServiceImpl extends AbstractServiceImpl implements WhoyaEgovQustnrManageService{
	
	@Resource(name = "egovQustnrManageService")
	private EgovQustnrManageService egovQustnrManageService;
	
	/**
	 * 설문관리 목록을 조회한다. 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return List
	 * @throws Exception
	 */
	public List selectQustnrManageList(ComDefaultVO searchVO) throws Exception{
		return egovQustnrManageService.selectQustnrManageList(searchVO);
	}
  
	/**
	 * 설문템플릿 목록을 조회한다. 
	 * @param qustnrManageVO - 설문관리 정보 담김 VO
	 * @return List
	 * @throws Exception
	 */	
	public List selectQustnrTmplatManageList(QustnrManageVO qustnrManageVO) throws Exception{
		return egovQustnrManageService.selectQustnrTmplatManageList(qustnrManageVO);
	}
	
	/**
	 * 설문관리를(을) 등록한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @throws Exception
	 */
	public void insertQustnrManage(QustnrManageVO qustnrManageVO) throws Exception {
		egovQustnrManageService.insertQustnrManage(qustnrManageVO);
	}
  
	/**
	 * 설문관리를(을) 상세조회 한다.
	 * @param QustnrManage - 회정정보가 담김 VO
	 * @return List
	 * @throws Exception
	 */
	public List selectQustnrManageDetail(QustnrManageVO qustnrManageVO) throws Exception{
		return egovQustnrManageService.selectQustnrManageDetail(qustnrManageVO);
	}
	
	/**
	 * 설문관리를(을) 수정한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @throws Exception
	 */
	public void updateQustnrManage(QustnrManageVO qustnrManageVO) throws Exception{
		egovQustnrManageService.updateQustnrManage(qustnrManageVO);
	}
	
	/**
	 * 설문관리를(을) 삭제한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @throws Exception
	 */
	public void deleteQustnrManage(QustnrManageVO qustnrManageVO) throws Exception{
		egovQustnrManageService.deleteQustnrManage(qustnrManageVO);
	}
//	//final private Log log = LogFactory.getLog(this.getClass());
//	
//	@Resource(name="qustnrManageDao")
//	private QustnrManageDao dao;
//	
//	@Resource(name="egovQustnrManageIdGnrService")
//	private EgovIdGnrService idgenService;
//	
//	
//	
//    /**
//	 * 설문관리를 상세조회(Model) 한다. 
//	 * @param qustnrManageVO - 설문관리 정보 담김 VO
//	 * @return List
//	 * @throws Exception
//	 */
//    public QustnrManageVO selectQustnrManageDetailModel(QustnrManageVO qustnrManageVO) throws Exception {    	
//        return (QustnrManageVO) dao.selectQustnrManageDetailModel(qustnrManageVO);
//    }
//	
//    /**
//	 * 설문관리를(을) 목록 전체 건수를(을) 조회한다.
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @return int
//	 * @throws Exception
//	 */
//	public int selectQustnrManageListCnt(ComDefaultVO searchVO) throws Exception{
//		return (Integer)dao.selectQustnrManageListCnt(searchVO);
//	}
}
