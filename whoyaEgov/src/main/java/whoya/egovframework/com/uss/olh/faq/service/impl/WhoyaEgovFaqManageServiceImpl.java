package whoya.egovframework.com.uss.olh.faq.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.uss.olh.faq.service.WhoyaEgovFaqManageService;
import egovframework.com.uss.olh.faq.service.EgovFaqManageService;
import egovframework.com.uss.olh.faq.service.FaqManageDefaultVO;
import egovframework.com.uss.olh.faq.service.FaqManageVO;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;

/**
 * FAQ를 처리하는 비즈니스 구현 클래스
 */
@Service("WhoyaFaqManageService")
public class WhoyaEgovFaqManageServiceImpl extends AbstractServiceImpl implements WhoyaEgovFaqManageService {
  
	@Resource(name = "FaqManageService")
	private EgovFaqManageService faqManageService;
	
	/**
	 * FAQ 글 목록을 조회한다.
	 * @param searchVO
	 * @return 글 목록
	 * @exception Exception
	 */
	public List selectFaqList(FaqManageDefaultVO searchVO) throws Exception {
		return faqManageService.selectFaqList(searchVO);
	}
  
	/**
	 * FAQ 글을 등록한다.
	 * @param vo
	 * @exception Exception
	 */
	public void insertFaqCn(FaqManageVO vo) throws Exception {
		faqManageService.insertFaqCn(vo);
	}
	
	/**
	 * FAQ 글을 조회한다.
	 * @param vo - 조회할 정보가 담긴 FaqManageVO
	 * @return 조회한 글
	 * @exception Exception
	 */
	public FaqManageVO selectFaqListDetail(FaqManageVO vo) throws Exception {
		return faqManageService.selectFaqListDetail(vo);
	}

	/**
	 * FAQ 조회수를 수정한다.
	 * @param vo
	 * @exception Exception
	 */
    public void updateFaqInqireCo(FaqManageVO vo) throws Exception {
    	faqManageService.updateFaqInqireCo(vo);    	
    }
    
	/**
	 * FAQ 글을 수정한다.
	 * @param vo
	 * @exception Exception
	 */
    public void updateFaqCn(FaqManageVO vo) throws Exception {
    	faqManageService.updateFaqCn(vo);    	
    }

	/**
	 * FAQ 글을 삭제한다.
	 * @param vo
	 * @exception Exception
	 */
    public void deleteFaqCn(FaqManageVO vo) throws Exception {
    	faqManageService.deleteFaqCn(vo);    	
    }
//    @Resource(name="FaqManageDAO")
//    private FaqManageDAO faqManageDAO;
//        
//    /** ID Generation */    
//	@Resource(name="egovFaqManageIdGnrService")
//	private EgovIdGnrService idgenService;
//
//
//    /**
//	 * FAQ 글 총 갯수를 조회한다.
//	 * @param searchVO
//	 * @return 글 총 갯수
//	 * @exception
//	 */
//    public int selectFaqListTotCnt(FaqManageDefaultVO searchVO) {
//		return faqManageDAO.selectFaqListTotCnt(searchVO);
//	}
    
}
