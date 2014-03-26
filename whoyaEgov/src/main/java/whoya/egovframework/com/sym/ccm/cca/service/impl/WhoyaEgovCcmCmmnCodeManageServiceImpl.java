package whoya.egovframework.com.sym.ccm.cca.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.sym.ccm.cca.service.WhoyaEgovCcmCmmnCodeManageService;
import egovframework.com.sym.ccm.cca.service.CmmnCode;
import egovframework.com.sym.ccm.cca.service.CmmnCodeVO;
import egovframework.com.sym.ccm.cca.service.EgovCcmCmmnCodeManageService;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;



/**
 * 
 * 공통코드에 대한 서비스 구현클래스를 정의한다
 */
@Service("WhoyaCmmnCodeManageService")
public class WhoyaEgovCcmCmmnCodeManageServiceImpl extends AbstractServiceImpl implements WhoyaEgovCcmCmmnCodeManageService {
    
    @Resource(name = "CmmnCodeManageService")
    private EgovCcmCmmnCodeManageService cmmnCodeManageService;
    
    /**
     * 공통코드 목록을 조회한다.
     */
    public List selectCmmnCodeList(CmmnCodeVO searchVO) throws Exception {
          return cmmnCodeManageService.selectCmmnCodeList(searchVO);
    }
    
    /**
     * 공통코드를 등록한다.
     */
    public void insertCmmnCode(CmmnCode cmmnCode) throws Exception {
        cmmnCodeManageService.insertCmmnCode(cmmnCode);     
    }
    
    /**
     * 공통코드 상세항목을 조회한다.
     */
    public CmmnCode selectCmmnCodeDetail(CmmnCode cmmnCode) throws Exception {
        return cmmnCodeManageService.selectCmmnCodeDetail(cmmnCode);
    }
    
    /**
     * 공통코드를 수정한다.
     */
    public void updateCmmnCode(CmmnCode cmmnCode) throws Exception {
        cmmnCodeManageService.updateCmmnCode(cmmnCode);
    }
  
    /**
     * 공통코드를 삭제한다.
     */
    public void deleteCmmnCode(CmmnCode cmmnCode) throws Exception {
        cmmnCodeManageService.deleteCmmnCode(cmmnCode);
    }
//    @Resource(name="CmmnCodeManageDAO")
//    private CmmnCodeManageDAO cmmnCodeManageDAO;
//
//	/**
//	 * 공통코드 총 갯수를 조회한다.
//	 */
//	public int selectCmmnCodeListTotCnt(CmmnCodeVO searchVO) throws Exception {
//        return cmmnCodeManageDAO.selectCmmnCodeListTotCnt(searchVO);
//	}

}
