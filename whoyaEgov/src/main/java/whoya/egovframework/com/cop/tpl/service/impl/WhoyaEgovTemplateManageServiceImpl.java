package whoya.egovframework.com.cop.tpl.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.cop.tpl.service.WhoyaEgovTemplateManageService;
import egovframework.com.cop.tpl.service.EgovTemplateManageService;
import egovframework.com.cop.tpl.service.TemplateInfVO;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;

/**
 * 템플릿 정보관리를 위한 서비스 구현 클래스
 */
@Service("WhoyaEgovTemplateManageService")
public class WhoyaEgovTemplateManageServiceImpl extends AbstractServiceImpl implements WhoyaEgovTemplateManageService {

	@Resource(name = "EgovTemplateManageService")
	private EgovTemplateManageService tmplatService;	
	
    /**
     * 템플릿에 대한 목록를 조회한다.
     * 
     * @see egovframework.com.cop.bbs.com.service.EgovTemplateManageService#selectTemplateInfs(egovframework.com.cop.bbs.com.service.TemplateInfVO)
     */
    public Map<String, Object> selectTemplateInfs(TemplateInfVO tmplatInfVO) throws Exception {
		return tmplatService.selectTemplateInfs(tmplatInfVO);
    }

//    @Resource(name = "TemplateManageDAO")
//    private TemplateManageDAO tmplatDAO;
//
//    @Resource(name = "egovTmplatIdGnrService")
//    private EgovIdGnrService idgenService;
//
//    /**
//     * 템플릿 정보를 삭제한다.
//     * 
//     * @see egovframework.com.cop.bbs.com.service.EgovTemplateManageService#deleteTemplateInf(egovframework.com.cop.bbs.com.service.TemplateInf)
//     */
//    public void deleteTemplateInf(TemplateInf tmplatInf) throws Exception {
//	tmplatDAO.deleteTemplateInf(tmplatInf);
//    }
//
//    /**
//     * 템플릿 정보를 등록한다.
//     * 
//     * @see egovframework.com.cop.bbs.com.service.EgovTemplateManageService#insertTemplateInf(egovframework.com.cop.bbs.com.service.TemplateInf)
//     */
//    public void insertTemplateInf(TemplateInf tmplatInf) throws Exception {
//
//	tmplatInf.setTmplatId(idgenService.getNextStringId());
//
//	tmplatDAO.insertTemplateInf(tmplatInf);
//    }
//
//    /**
//     * 템플릿에 대한 상세정보를 조회한다.
//     * 
//     * @see egovframework.com.cop.bbs.com.service.EgovTemplateManageService#selectTemplateInf(egovframework.com.cop.bbs.com.service.TemplateInfVO)
//     */
//    public TemplateInfVO selectTemplateInf(TemplateInfVO tmplatInfVO) throws Exception {
//	TemplateInfVO vo = new TemplateInfVO();
//	vo = tmplatDAO.selectTemplateInf(tmplatInfVO);
//	return vo;
//    }
//
//    /**
//     * 템플릿에 대한 미리보기 정보를 조회한다.
//     * 
//     * @see egovframework.com.cop.bbs.com.service.EgovTemplateManageService#selectTemplatePreview(egovframework.com.cop.bbs.com.service.TemplateInfVO)
//     */
//    public TemplateInfVO selectTemplatePreview(TemplateInfVO tmplatInfVO) throws Exception {
//	TemplateInfVO vo = new TemplateInfVO();
//	
//	vo = tmplatDAO.selectTemplatePreview(tmplatInfVO);
//	
//	return vo;
//    }
//
//    /**
//     * 템플릿 정보를 수정한다.
//     * 
//     * @see egovframework.com.cop.bbs.com.service.EgovTemplateManageService#updateTemplateInf(egovframework.com.cop.bbs.com.service.TemplateInf)
//     */
//    public void updateTemplateInf(TemplateInf tmplatInf) throws Exception {
//	tmplatDAO.updateTemplateInf(tmplatInf);
//    }
//
//    /**
//     * 템플릿 구분에 따른 목록을 조회한다.
//     * 
//     * @see egovframework.com.cop.bbs.com.service.EgovTemplateManageService#selectAllTemplateInfs(egovframework.com.cop.bbs.com.service.TemplateInfVO)
//     */
//    public List<TemplateInfVO> selectTemplateInfsByCode(TemplateInfVO tmplatInfVO) throws Exception {
//	return tmplatDAO.selectTemplateInfsByCode(tmplatInfVO);
//    }
}
