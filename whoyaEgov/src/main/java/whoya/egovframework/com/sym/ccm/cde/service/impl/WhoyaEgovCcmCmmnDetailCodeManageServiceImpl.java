package whoya.egovframework.com.sym.ccm.cde.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.sym.ccm.cde.service.WhoyaEgovCcmCmmnDetailCodeManageService;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.sym.ccm.cde.service.CmmnDetailCodeVO;
import egovframework.com.sym.ccm.cde.service.EgovCcmCmmnDetailCodeManageService;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;



/**
 * 
 * 공통상세코드에 대한 서비스 구현클래스를 정의한다
 */
@Service("WhoyaCmmnDetailCodeManageService")
public class WhoyaEgovCcmCmmnDetailCodeManageServiceImpl extends AbstractServiceImpl implements WhoyaEgovCcmCmmnDetailCodeManageService {
    
    @Resource(name = "CmmnDetailCodeManageService")
    private EgovCcmCmmnDetailCodeManageService cmmnDetailCodeManageService;
    
    /**
     * 공통상세코드 목록을 조회한다.
     */
    public List selectCmmnDetailCodeList(CmmnDetailCodeVO searchVO) throws Exception {
          return cmmnDetailCodeManageService.selectCmmnDetailCodeList(searchVO);
    }
    
    /**
     * 공통상세코드를 등록한다.
     */
    public void insertCmmnDetailCode(CmmnDetailCode cmmnDetailCode) throws Exception {
        cmmnDetailCodeManageService.insertCmmnDetailCode(cmmnDetailCode);       
    }
    
    /**
     * 공통상세코드 상세항목을 조회한다.
     */
    public CmmnDetailCode selectCmmnDetailCodeDetail(CmmnDetailCode cmmnDetailCode) throws Exception {
        return cmmnDetailCodeManageService.selectCmmnDetailCodeDetail(cmmnDetailCode);
    }
    
    /**
     * 공통상세코드를 수정한다.
     */
    public void updateCmmnDetailCode(CmmnDetailCode cmmnDetailCode) throws Exception {
        cmmnDetailCodeManageService.updateCmmnDetailCode(cmmnDetailCode);
    }
    
	/**
	 * 공통상세코드를 삭제한다.
	 */
	public void deleteCmmnDetailCode(CmmnDetailCode cmmnDetailCode) throws Exception {
	    cmmnDetailCodeManageService.deleteCmmnDetailCode(cmmnDetailCode);
	}
//
//    @Resource(name="CmmnDetailCodeManageDAO")
//    private CmmnDetailCodeManageDAO cmmnDetailCodeManageDAO;
//
//	/**
//	 * 공통상세코드 총 갯수를 조회한다.
//	 */
//	public int selectCmmnDetailCodeListTotCnt(CmmnDetailCodeVO searchVO) throws Exception {
//        return cmmnDetailCodeManageDAO.selectCmmnDetailCodeListTotCnt(searchVO);
//	}

}
