package whoya.egovframework.com.uss.olh.faq.service;

import java.util.List;

import egovframework.com.uss.olh.faq.service.FaqManageDefaultVO;
import egovframework.com.uss.olh.faq.service.FaqManageVO;


/**
 * FAQ를 처리하는 서비스 클래스
 */
public interface WhoyaEgovFaqManageService {
  
	/**
	 * FAQ 글 목록을 조회한다.
	 * @param searchVO
	 * @return 글 목록
	 * @exception Exception
	 */
	List selectFaqList(FaqManageDefaultVO searchVO) throws Exception;	    
  
	/**
	 * FAQ글을 등록한다.
	 * @param vo
	 * @exception Exception
	 */
	void insertFaqCn(FaqManageVO vo) throws Exception;
    
	/**
	 * FAQ 글을 조회한다.
	 * @param vo
	 * @return 조회한 글
	 * @exception Exception
	 */
	FaqManageVO selectFaqListDetail(FaqManageVO vo) throws Exception;
    
	/**
	 * 조회수를 수정한다.
	 * @param vo
	 * @exception Exception
	 */
    void updateFaqInqireCo(FaqManageVO vo) throws Exception;

	/**
	 * FAQ 글을 수정한다.
	 * @param vo
	 * @exception Exception
	 */
    void updateFaqCn(FaqManageVO vo) throws Exception;
    
	/**
	 * FAQ 글을 삭제한다.
	 * @param vo
	 * @exception Exception
	 */
    void deleteFaqCn(FaqManageVO vo) throws Exception;
//    
//    
//    /**
//	 * FAQ 글 총 갯수를 조회한다.
//	 * @param searchVO
//	 * @return 글 총 갯수
//	 */
//    int selectFaqListTotCnt(FaqManageDefaultVO searchVO);
//    
//    
    
}
