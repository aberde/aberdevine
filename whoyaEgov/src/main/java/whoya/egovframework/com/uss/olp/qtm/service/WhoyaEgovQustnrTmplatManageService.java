package whoya.egovframework.com.uss.olp.qtm.service;

import java.util.List;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.uss.olp.qtm.service.QustnrTmplatManageVO;

/**
 * 설문템플릿 Service Class 구현 
 */
public interface WhoyaEgovQustnrTmplatManageService {
	
	/**
	 * 설문템플릿 목록을 조회한다. 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return List
	 * @throws Exception
	 */
	public List selectQustnrTmplatManageList(ComDefaultVO searchVO) throws Exception;
	
	/**
	 * 설문템플릿를(을) 등록한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @throws Exception
	 */
	void  insertQustnrTmplatManage(QustnrTmplatManageVO qustnrTmplatManageVO) throws Exception;
	
	/**
	 * 설문템플릿를(을) 상세조회 한다.
	 * @param QustnrTmplatManage - 회정정보가 담김 VO
	 * @return List
	 * @throws Exception
	 */
	public List selectQustnrTmplatManageDetail(QustnrTmplatManageVO qustnrTmplatManageVO) throws Exception;
	
	/**
	 * 설문템플릿를(을) 삭제한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @throws Exception
	 */
	void  deleteQustnrTmplatManage(QustnrTmplatManageVO qustnrTmplatManageVO) throws Exception;
	
	/**
	 * 설문템플릿를(을) 수정한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @throws Exception
	 */
	void  updateQustnrTmplatManage(QustnrTmplatManageVO qustnrTmplatManageVO) throws Exception;
//    /**
//	 * 템플릿파일명을 조회한다. 
//	 * @param qustnrTmplatManageVO - 조회할 정보가 담긴 VO
//	 * @return List
//	 * @throws Exception
//	 */
//	public Map selectQustnrTmplatManageTmplatImagepathnm(QustnrTmplatManageVO qustnrTmplatManageVO) throws Exception;
//	
//    /**
//	 * 설문템플릿를(을) 목록 전체 건수를(을) 조회한다.
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @return int 
//	 * @throws Exception
//	 */
//	public int selectQustnrTmplatManageListCnt(ComDefaultVO searchVO) throws Exception;
//	
	
}
