package kr.go.rndcall.mgnt.faq.biz;

import java.sql.SQLException;

import javax.naming.NamingException;

import kr.go.rndcall.mgnt.faq.dao.FaqDAO;
import kr.go.rndcall.mgnt.faq.vo.FaqAttachVO;
import kr.go.rndcall.mgnt.faq.vo.FaqResultVO;
import kr.go.rndcall.mgnt.faq.vo.FaqSearchVO;
import kr.go.rndcall.mgnt.faq.vo.FaqVO;
import kr.go.rndcall.mgnt.faq.vo.SatiVO;
import kr.go.rndcall.mgnt.common.AttachVO;
import kr.go.rndcall.mgnt.common.DAOBaseException;

public class FaqBiz {
	//첨부파일 등록
    public void putAttach(FaqVO vo, AttachVO[] attachVO) throws Exception {
    	FaqDAO dao = new FaqDAO();        
        dao.putAttach(vo, attachVO);
    }
	//자료실 첨부파일정보
	public FaqResultVO getFileInfo(String file_id) throws Exception {
		FaqDAO FaqDAO = new FaqDAO();
		return FaqDAO.getFileInfo(file_id);
	}
	//첨부파일 삭제유무에따른 메소드
	public void getFileDelete(FaqVO vo, FaqSearchVO searchVO) throws Exception {
		FaqDAO FaqDAO = new FaqDAO();
		FaqDAO.getFileDelete(vo, searchVO);
	}
	//FAQ를 등록한다.
	public boolean faqInsert(FaqVO vo, FaqSearchVO searchVO) throws Exception {
		FaqDAO FaqDAO = new FaqDAO();		
		return FaqDAO.faqInsert(vo, searchVO);
	}
	//FAQ답변을 등록한다.
/*	public boolean faqAnswerInsert(FaqVO vo, FaqSearchVO searchVO) throws Exception {
		FaqDAO FaqDAO = new FaqDAO();		
		return FaqDAO.faqAnswerInsert(vo, searchVO);
	}*/
	//sms 발송
	public boolean smsCommit(FaqVO vo, FaqSearchVO searchVO) throws DAOBaseException, SQLException, NamingException {
		FaqDAO FaqDAO = new FaqDAO();
		return FaqDAO.smsCommit(vo, searchVO);
	}
	//email 발송
	public boolean emailCommit(FaqVO vo, FaqSearchVO searchVO, String server_ip) throws Exception {
		FaqDAO FaqDAO = new FaqDAO();
		return FaqDAO.emailCommit(vo, searchVO, server_ip);
	}
	public FaqResultVO faqList(FaqVO vo, FaqSearchVO searchVO) throws DAOBaseException, SQLException, NamingException {
		FaqDAO FaqDAO = new FaqDAO();
		
		return FaqDAO.faqList(vo, searchVO);
	}
	//FAQ상세보기를 한다.
	public FaqResultVO faqDetailView(FaqVO vo, FaqSearchVO searchVO) throws DAOBaseException, SQLException, NamingException {
		FaqDAO FaqDAO = new FaqDAO();
		
		return FaqDAO.faqDetailView(vo, searchVO);
	}
	//대분류 코드를 가져온다.
    public FaqResultVO retrieveCategory1Code(FaqSearchVO searchVO) throws SQLException, DAOBaseException {
    	FaqDAO dao = new FaqDAO();
        return dao.retrieveCategory1Code(searchVO);
    }
    //소분류 코드를 가져온다.
    public FaqResultVO retrieveCategory2Code(FaqSearchVO searchVO) throws SQLException, DAOBaseException {
    	FaqDAO dao = new FaqDAO();
        return dao.retrieveCategory2Code(searchVO);
    }
    //만족도평가를 등록한다.
	public boolean faqSatiInsert(FaqVO vo, FaqSearchVO searchVO, SatiVO satiVO) throws Exception {
		FaqDAO FaqDAO = new FaqDAO();
		FaqResultVO resultVo =  new FaqResultVO();
		return FaqDAO.faqSatiInsert(vo, searchVO, satiVO);
	}
	//만족도 정보를 가져온다.
	public FaqResultVO getSatiInfo(String seq, String login_id) throws Exception {
		FaqDAO FaqDAO = new FaqDAO();
		return FaqDAO.getSatiInfo(seq, login_id);
	}
	//업데이트를 위해 정보를 불러온다.
	public FaqResultVO faqContentConfirm(FaqVO vo, FaqSearchVO searchVO) throws Exception {
		FaqDAO FaqDAO = new FaqDAO();
		return FaqDAO.faqContentConfirm(vo, searchVO);
	}	
	//만족도를 평가한다.
	public void rcSatiInsert(FaqSearchVO searchVO) throws Exception {
		FaqDAO FaqDAO = new FaqDAO();
		FaqResultVO resultVo =  new FaqResultVO();
		boolean success = false;
		success = FaqDAO.getSatiInsert(searchVO);
	}
	//FAQ를 업데이트한다.
	public boolean faqUpdate(FaqVO vo, FaqSearchVO searchVO) throws Exception {
		FaqDAO FaqDAO = new FaqDAO();
		return FaqDAO.faqUpdate(vo, searchVO);
	}
	//FAQ를 삭제한다.
	public boolean faqDelete(FaqSearchVO searchVO) throws Exception {
		FaqDAO FaqDAO = new FaqDAO();
		return	FaqDAO.faqDelete(searchVO);		
	}
	//SMS 발송
	public boolean sendSms(String seq, String type, String login_id) throws Exception {
		FaqDAO FaqDAO = new FaqDAO();
		return FaqDAO.sendSms(seq, type, login_id);
	}

	//부처담당자 리스트를 가져온다.
	public FaqResultVO orgTelNum(FaqVO vo, FaqSearchVO searchVO) throws DAOBaseException, SQLException, NamingException {
		FaqDAO FaqDAO = new FaqDAO();
		
		return FaqDAO.orgTelNum(vo, searchVO);
	}
}
