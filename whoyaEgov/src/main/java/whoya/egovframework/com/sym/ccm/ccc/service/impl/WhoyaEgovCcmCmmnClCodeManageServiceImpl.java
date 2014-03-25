package whoya.egovframework.com.sym.ccm.ccc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.sym.ccm.ccc.service.WhoyaEgovCcmCmmnClCodeManageService;
import egovframework.com.sym.ccm.ccc.service.CmmnClCode;
import egovframework.com.sym.ccm.ccc.service.CmmnClCodeVO;
import egovframework.com.sym.ccm.ccc.service.EgovCcmCmmnClCodeManageService;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;



/**
 * 
 * 공통분류코드에 대한 서비스 구현클래스를 정의한다
 */
@Service("WhoyaCmmnClCodeManageService")
public class WhoyaEgovCcmCmmnClCodeManageServiceImpl extends AbstractServiceImpl implements WhoyaEgovCcmCmmnClCodeManageService {
    
    @Resource(name = "CmmnClCodeManageService")
    private EgovCcmCmmnClCodeManageService cmmnClCodeManageService;
    
    /**
     * 공통분류코드 목록을 조회한다.
     */
    public List selectCmmnClCodeList(CmmnClCodeVO searchVO) throws Exception {
          return cmmnClCodeManageService.selectCmmnClCodeList(searchVO);
    }
    
    /**
     * 공통분류코드 상세항목을 조회한다.
     */
    public CmmnClCode selectCmmnClCodeDetail(CmmnClCode cmmnClCode) throws Exception {
        return cmmnClCodeManageService.selectCmmnClCodeDetail(cmmnClCode);
    }
    
    /**
     * 공통분류코드를 등록한다.
     */
    public void insertCmmnClCode(CmmnClCode cmmnClCode) throws Exception {
        cmmnClCodeManageService.insertCmmnClCode(cmmnClCode);       
    }
    
    /**
     * 공통분류코드를 수정한다.
     */
    public void updateCmmnClCode(CmmnClCode cmmnClCode) throws Exception {
        cmmnClCodeManageService.updateCmmnClCode(cmmnClCode);
    }
  
    /**
     * 공통분류코드를 삭제한다.
     */
    public void deleteCmmnClCode(CmmnClCode cmmnClCode) throws Exception {
        cmmnClCodeManageService.deleteCmmnClCode(cmmnClCode);
    }
//    @Resource(name="CmmnClCodeManageDAO")
//    private CmmnClCodeManageDAO cmmnClCodeManageDAO;
//
//	/**
//	 * 공통분류코드 총 갯수를 조회한다.
//	 */
//	public int selectCmmnClCodeListTotCnt(CmmnClCodeVO searchVO) throws Exception {
//        return cmmnClCodeManageDAO.selectCmmnClCodeListTotCnt(searchVO);
//	}

}
