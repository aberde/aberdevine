package whoya.egovframework.com.uss.olp.qtm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.uss.olp.qtm.service.WhoyaEgovQustnrTmplatManageService;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.uss.olp.qtm.service.EgovQustnrTmplatManageService;
import egovframework.com.uss.olp.qtm.service.QustnrTmplatManageVO;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
/**
 * 설문템플릿 ServiceImpl Class 구현 
 */
@Service("whoyaEgovQustnrTmplatManageService")
public class WhoyaEgovQustnrTmplatManageServiceImpl extends AbstractServiceImpl implements WhoyaEgovQustnrTmplatManageService{
	
	@Resource(name = "egovQustnrTmplatManageService")
	private EgovQustnrTmplatManageService egovQustnrTmplatManageService;
	
	/**
	 * 설문템플릿 목록을 조회한다. 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return List
	 * @throws Exception
	 */
	public List selectQustnrTmplatManageList(ComDefaultVO searchVO) throws Exception{
		return egovQustnrTmplatManageService.selectQustnrTmplatManageList(searchVO);
	}
	
	/**
	 * 설문템플릿를(을) 등록한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @throws Exception
	 */
	public void insertQustnrTmplatManage(QustnrTmplatManageVO qustnrTmplatManageVO) throws Exception {
		egovQustnrTmplatManageService.insertQustnrTmplatManage(qustnrTmplatManageVO);
	}
	
	/**
	 * 설문템플릿를(을) 상세조회 한다.
	 * @param QustnrTmplatManage - 회정정보가 담김 VO
	 * @return List
	 * @throws Exception
	 */
	public List selectQustnrTmplatManageDetail(QustnrTmplatManageVO qustnrTmplatManageVO) throws Exception{
		return egovQustnrTmplatManageService.selectQustnrTmplatManageDetail(qustnrTmplatManageVO);
	}
	
	/**
	 * 설문템플릿를(을) 삭제한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @throws Exception
	 */
	public void deleteQustnrTmplatManage(QustnrTmplatManageVO qustnrTmplatManageVO) throws Exception {
		egovQustnrTmplatManageService.deleteQustnrTmplatManage(qustnrTmplatManageVO);
	}
	
	/**
	 * 설문템플릿를(을) 수정한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @throws Exception
	 */
	public void updateQustnrTmplatManage(QustnrTmplatManageVO qustnrTmplatManageVO) throws Exception {
		egovQustnrTmplatManageService.updateQustnrTmplatManage(qustnrTmplatManageVO);
	}
//	//final private Log log = LogFactory.getLog(this.getClass());
//	
//	@Resource(name="qustnrTmplatManageDao")
//	private QustnrTmplatManageDao dao;
//	
//	@Resource(name="egovQustnrTmplatManageIdGnrService")
//	private EgovIdGnrService idgenService;
//	
//    /**
//	 * 템플릿파일명을 조회한다. 
//	 * @param qustnrTmplatManageVO - 조회할 정보가 담긴 VO
//	 * @return List
//	 * @throws Exception
//	 */
//	public Map selectQustnrTmplatManageTmplatImagepathnm(QustnrTmplatManageVO qustnrTmplatManageVO) throws Exception{
//		return (Map)dao.selectQustnrTmplatManageTmplatImagepathnm(qustnrTmplatManageVO);
//	}
//	
//    /**
//	 * 설문템플릿를(을) 목록 전체 건수를(을) 조회한다.
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @return int
//	 * @throws Exception
//	 */
//	public int selectQustnrTmplatManageListCnt(ComDefaultVO searchVO) throws Exception{
//		return (Integer)dao.selectQustnrTmplatManageListCnt(searchVO);
//	}
}
