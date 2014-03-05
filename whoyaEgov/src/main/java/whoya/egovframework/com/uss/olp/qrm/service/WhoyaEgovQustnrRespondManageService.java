package whoya.egovframework.com.uss.olp.qrm.service;

import java.util.List;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.uss.olp.qrm.service.QustnrRespondManageVO;
/**
 * 설문응답자관리 Service Class 구현 
 */
public interface WhoyaEgovQustnrRespondManageService {
	
    /**
	 * 응답자정보 목록을 조회한다. 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return List
	 * @throws Exception
	 */
	public List selectQustnrRespondManageList(ComDefaultVO searchVO) throws Exception;
	
	/**
	 * 응답자정보를(을) 등록한다.
	 * @param qustnrRespondManageVO - 응답자정보 정보 담김 VO
	 * @throws Exception
	 */
	void  insertQustnrRespondManage(QustnrRespondManageVO qustnrRespondManageVO) throws Exception;
	
    /**
	 * 응답자정보를(을) 상세조회 한다.
	 * @param qustnrRespondManageVO - 응답자정보 정보 담김 VO
	 * @return List
	 * @throws Exception
	 */
	public List selectQustnrRespondManageDetail(QustnrRespondManageVO qustnrRespondManageVO) throws Exception;
	 
	/**
     * 응답자정보를(을) 삭제한다.
     * @param qustnrRespondManageVO - 응답자정보 정보 담김 VO
     * @throws Exception
     */
	void  deleteQustnrRespondManage(QustnrRespondManageVO qustnrRespondManageVO) throws Exception;
	 
	/**
     * 응답자정보를(을) 수정한다.
     * @param qustnrRespondManageVO - 응답자정보 정보 담김 VO
     * @throws Exception
     */
    void  updateQustnrRespondManage(QustnrRespondManageVO qustnrRespondManageVO) throws Exception;
}
