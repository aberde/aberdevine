package kr.go.rndcall.mgnt.offer.biz;

import java.sql.SQLException;

import javax.naming.NamingException;

import kr.go.rndcall.mgnt.inquire.dao.InquireDAO;
import kr.go.rndcall.mgnt.inquire.vo.InquireSearchVO;
import kr.go.rndcall.mgnt.inquire.vo.InquireVO;
import kr.go.rndcall.mgnt.offer.dao.OfferDAO;
import kr.go.rndcall.mgnt.offer.vo.OfferAttachVO;
import kr.go.rndcall.mgnt.offer.vo.OfferResultVO;
import kr.go.rndcall.mgnt.offer.vo.OfferSearchVO;
import kr.go.rndcall.mgnt.offer.vo.OfferVO;
import kr.go.rndcall.mgnt.offer.vo.SatiVO;
import kr.go.rndcall.mgnt.common.AttachVO;
import kr.go.rndcall.mgnt.common.DAOBaseException;

public class OfferBiz {
	
	//제안하기를 등록한다.
	public OfferResultVO offerInsert(OfferVO vo, OfferSearchVO searchVO) throws Exception {
    	OfferResultVO reslutVO = new OfferResultVO();
    	OfferDAO dao =  new OfferDAO();
        reslutVO =  dao.offerInsert(vo, searchVO);
        return reslutVO;
    }
	//제안하기 리스트를 조회한다.
	public OfferResultVO offerList(OfferSearchVO searchVO) throws DAOBaseException, SQLException, NamingException {
		OfferDAO OfferDAO = new OfferDAO();
		return OfferDAO.offerList(searchVO);
	}
	//제안하기 상세보기를 한다.
	public OfferResultVO offerDetailView(OfferSearchVO searchVO) throws Exception {
		OfferDAO OfferDAO = new OfferDAO();
		return OfferDAO.offerDetailView(searchVO);
	}
	//첨부파일정보
	public OfferResultVO getFileInfo(String file_id) throws Exception {
		OfferDAO OfferDAO = new OfferDAO();
		return OfferDAO.getFileInfo(file_id);
	}
    //대분류 코드를 가져온다.
    public OfferResultVO retrieveCategory1Code(OfferSearchVO searchVO) throws SQLException, DAOBaseException {
    	OfferDAO dao = new OfferDAO();
        return dao.retrieveCategory1Code(searchVO);
    }
    //소분류 코드를 가져온다.
    public OfferResultVO retrieveCategory2Code(OfferSearchVO searchVO) throws SQLException, DAOBaseException {
    	OfferDAO dao = new OfferDAO();
        return dao.retrieveCategory2Code(searchVO);
    }
	//제안하기 만족도 체크
	public boolean offerSatiInsert(OfferVO vo, OfferSearchVO searchVO, SatiVO satiVO) throws Exception {
		OfferDAO OfferDAO = new OfferDAO();
		OfferResultVO resultVo =  new OfferResultVO();
		return OfferDAO.offerSatiInsert(vo, searchVO, satiVO);
	}
	public OfferResultVO getSatiInfo(String seq, String login_id) throws Exception {
		OfferDAO OfferDAO = new OfferDAO();
		return OfferDAO.getSatiInfo(seq, login_id);
	}
	//첨부파일 등록
    public void putAttach(OfferVO vo, AttachVO[] attachVO) throws Exception {
    	OfferDAO dao = new OfferDAO();        
        dao.putAttach(vo, attachVO);
    }
/*    //제안하기 등록
    public OfferResultVO offerInsert(OfferVO vo) throws Exception {
    	OfferResultVO reslutVO = new OfferResultVO();
    	OfferDAO dao =  new OfferDAO();
        reslutVO =  dao.offerInsert(vo);
        return reslutVO;
    }*/
	//제안하기 답변등록
	public boolean offerAnswerInsert(OfferVO vo, OfferSearchVO searchVO) throws Exception {
		OfferDAO OfferDAO = new OfferDAO();
		return OfferDAO.offerAnswerInsert(vo, searchVO);
	}
	//첨부파일 삭제
	public void getAnswerFileDelete(OfferVO vo, OfferSearchVO searchVO) throws Exception {
		OfferDAO OfferDAO = new OfferDAO();
		OfferDAO.getAnswerFileDelete(vo, searchVO);
	}
	public void getQuestionFileDelete(OfferVO vo, OfferSearchVO searchVO) throws Exception {
		OfferDAO OfferDAO = new OfferDAO(); 
		
		OfferDAO.getAnswerFileDelete(vo, searchVO);
	}
	//제안하기 답변 등록하는 메소드
	public boolean offerAnswerUpdate(OfferVO vo, OfferSearchVO searchVO) throws Exception {
		OfferDAO OfferDAO = new OfferDAO();
		return OfferDAO.offerAnswerUpdate(vo, searchVO);
	}
	//제안하기 수정
	public boolean offerUpdate(OfferVO vo, OfferSearchVO searchVO) throws Exception {
		OfferDAO OfferDAO = new OfferDAO();
		return OfferDAO.offerUpdate(vo, searchVO);
	}
	//제안하기 삭제
	public boolean offerDelete(OfferSearchVO searchVO) throws Exception {
		OfferDAO OfferDAO = new OfferDAO();
		return	OfferDAO.offerDelete(searchVO);		
	}
	//SMS 발송
	public boolean sendSms(String seq, String type, String login_id) throws Exception {
		OfferDAO OfferDAO = new OfferDAO();
		return OfferDAO.sendSms(seq, type, login_id);
	}
	
	//이메일 발송
	public boolean sendEmail(String seq, String type, String login_id) throws Exception {
		OfferDAO OfferDAO = new OfferDAO();
		return OfferDAO.sendEmail(seq, type, login_id);
	}
}
