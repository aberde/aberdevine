package whoya.egovframework.com.uss.olp.qrm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.uss.olp.qrm.service.WhoyaEgovQustnrRespondManageService;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.uss.olp.qrm.service.EgovQustnrRespondManageService;
import egovframework.com.uss.olp.qrm.service.QustnrRespondManageVO;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
/**
 * 설문응답자관리 ServiceImpl Class 구현 
 */
@Service("whoyaEgovQustnrRespondManageService")
public class WhoyaEgovQustnrRespondManageServiceImpl extends AbstractServiceImpl implements WhoyaEgovQustnrRespondManageService{
	
	@Resource(name = "egovQustnrRespondManageService")
	private EgovQustnrRespondManageService egovQustnrRespondManageService;
	
	/**
	 * 응답자정보 목록을 조회한다. 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return List
	 * @throws Exception
	 */
	public List selectQustnrRespondManageList(ComDefaultVO searchVO) throws Exception{
		return egovQustnrRespondManageService.selectQustnrRespondManageList(searchVO);
	}
	
	/**
	 * 응답자정보를(을) 등록한다.
	 * @param qustnrRespondManageVO -  응답자정보 정보가 담긴 VO 
	 * @throws Exception
	 */
	public void insertQustnrRespondManage(QustnrRespondManageVO qustnrRespondManageVO) throws Exception {
		egovQustnrRespondManageService.insertQustnrRespondManage(qustnrRespondManageVO);
	}
	 
	/**
     * 응답자정보를(을) 상세조회 한다.
     * @param QustnrRespondManage - 회정정보가 담김 VO
     * @return List
     * @throws Exception
     */
	public List selectQustnrRespondManageDetail(QustnrRespondManageVO qustnrRespondManageVO) throws Exception{
	    return egovQustnrRespondManageService.selectQustnrRespondManageDetail(qustnrRespondManageVO);
	}
	 
	/**
     * 응답자정보를(을) 삭제한다.
     * @param qustnrRespondManageVO - 응답자정보 정보가 담긴 VO
     * @return 
     * @throws Exception
     */
	public void deleteQustnrRespondManage(QustnrRespondManageVO qustnrRespondManageVO) throws Exception{
	    egovQustnrRespondManageService.deleteQustnrRespondManage(qustnrRespondManageVO);
	}
	 
	/**
     * 응답자정보를(을) 수정한다.
     * @param qustnrRespondManageVO - 응답자정보 조회할 정보가 담긴 VO
     * @throws Exception
     */
    public void updateQustnrRespondManage(QustnrRespondManageVO qustnrRespondManageVO) throws Exception{
        egovQustnrRespondManageService.updateQustnrRespondManage(qustnrRespondManageVO);
    }
}
