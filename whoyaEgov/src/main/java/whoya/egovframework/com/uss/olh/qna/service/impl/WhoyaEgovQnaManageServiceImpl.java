package whoya.egovframework.com.uss.olh.qna.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.uss.olh.qna.service.WhoyaEgovQnaManageService;
import egovframework.com.uss.olh.qna.service.EgovQnaManageService;
import egovframework.com.uss.olh.qna.service.QnaManageDefaultVO;
import egovframework.com.uss.olh.qna.service.QnaManageVO;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;

/**
 * Q&A정보를 처리하는 비즈니스 구현 클래스
 */
@Service("WhoyaQnaManageService")
public class WhoyaEgovQnaManageServiceImpl extends AbstractServiceImpl implements WhoyaEgovQnaManageService {

	@Resource(name = "QnaManageService")
	private EgovQnaManageService qnaManageService;
	
	/**
	 * Q&A 글 목록을 조회한다.
	 * @param searchVO
	 * @return 글 목록
	 * @exception Exception
	 */
	public List selectQnaList(QnaManageDefaultVO searchVO) throws Exception {
		return qnaManageService.selectQnaList(searchVO);
	}
  
	/**
	 * Q&A 글을 등록한다.
	 * @param vo
	 * @exception Exception
	 */
	public void insertQnaCn(QnaManageVO vo) throws Exception {
		qnaManageService.insertQnaCn(vo);
	}
	
	/**
	 * Q&A 글을 수정한다.(조회수를 수정)
	 * @param vo
	 * @exception Exception
	 */
    public void updateQnaInqireCo(QnaManageVO vo) throws Exception {
    	qnaManageService.updateQnaInqireCo(vo);
    }
  
    /**
	 * Q&A 글을 조회한다.
	 * @param vo
	 * @return 조회한 글
	 * @exception Exception
	 */
    public QnaManageVO selectQnaListDetail(QnaManageVO vo) throws Exception {
    	return qnaManageService.selectQnaListDetail(vo);
    }
//    @Resource(name="QnaManageDAO")
//    private QnaManageDAO qnaManageDAO;
//        
//    /** ID Generation */    
//	@Resource(name="egovQnaManageIdGnrService")
//	private EgovIdGnrService idgenService;
//
//
//    /**
//	 * Q&A 글 총 갯수를 조회한다.
//	 * @param searchVO
//	 * @return 글 총 갯수
//	 */
//    public int selectQnaListTotCnt(QnaManageDefaultVO searchVO) {
//		return qnaManageDAO.selectQnaListTotCnt(searchVO);
//	}
//    
//    /**
//	 * 작성비밀번호를 확인한다.
//	 * @param vo
//	 * @return 글 총 갯수
//	 */
//    public int selectQnaPasswordConfirmCnt(QnaManageVO vo) {
//		return qnaManageDAO.selectQnaPasswordConfirmCnt(vo);
//	}
//    
//	/**
//	 * Q&A 글을 수정한다.
//	 * @param vo
//	 * @exception Exception
//	 */
//    public void updateQnaCn(QnaManageVO vo) throws Exception {           	
//    	qnaManageDAO.updateQnaCn(vo);    	
//    }
//
//	/**
//	 * Q&A 글을 삭제한다.
//	 * @param vo
//	 * @exception Exception
//	 */
//    public void deleteQnaCn(QnaManageVO vo) throws Exception {
//    	qnaManageDAO.deleteQnaCn(vo);    	
//    }
//
//    
//    /**
//	 * Q&A 답변 글을 조회한다.
//	 * @param vo
//	 * @return 조회한 글
//	 * @exception Exception
//	 */
//    public QnaManageVO selectQnaAnswerListDetail(QnaManageVO vo) throws Exception {
//        QnaManageVO resultVO = qnaManageDAO.selectQnaAnswerListDetail(vo);
//        if (resultVO == null)
//            throw processException("info.nodata.msg");
//        return resultVO;
//    }
//    
//    /**
//	 * Q&A 답변 글 목록을 조회한다.
//	 * @param searchVO
//	 * @return 글 목록
//	 * @exception Exception
//	 */
//    public List selectQnaAnswerList(QnaManageDefaultVO searchVO) throws Exception {
//        return qnaManageDAO.selectQnaAnswerList(searchVO);
//    }
//
//    /**
//	 * Q&A 답변 글 총 갯수를 조회한다.
//	 * @param searchVO
//	 * @return 글 총 갯수
//	 */
//    public int selectQnaAnswerListTotCnt(QnaManageDefaultVO searchVO) {
//		return qnaManageDAO.selectQnaListTotCnt(searchVO);
//	}
//            
//	/**
//	 * Q&A 답변 글을 수정한다.
//	 * @param vo
//	 * @exception Exception
//	 */
//    public void updateQnaCnAnswer(QnaManageVO vo) throws Exception {
//    	qnaManageDAO.updateQnaCnAnswer(vo);    	
//    }
    
}
