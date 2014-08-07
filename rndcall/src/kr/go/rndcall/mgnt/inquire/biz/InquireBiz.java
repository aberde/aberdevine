package kr.go.rndcall.mgnt.inquire.biz;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import kr.go.rndcall.mgnt.common.DAOBaseException;
import kr.go.rndcall.mgnt.inquire.dao.InquireDAO;
import kr.go.rndcall.mgnt.inquire.vo.InquireSearchVO;
import kr.go.rndcall.mgnt.inquire.vo.InquireResultVO;
import kr.go.rndcall.mgnt.inquire.vo.InquireVO;
import kr.go.rndcall.mgnt.inquire.vo.SatiVO;
import kr.go.rndcall.mgnt.common.AttachVO;

public class InquireBiz {
	
	//온라인 상담 메인 리스트 조회
	public InquireResultVO getInquireMainList(InquireSearchVO searchVO) throws Exception {
		  
		InquireDAO InquireDAO = new InquireDAO();
		
		return InquireDAO.getInquireMainList(searchVO);
	}
	
	//온라인상담 Q&A 리스트 조회
	public InquireResultVO getInquireList(InquireSearchVO searchVO) throws Exception {
		  
		InquireDAO InquireDAO = new InquireDAO();
		
		return InquireDAO.getInquireList(searchVO);
	}
	
	
	public InquireResultVO getInquireView(InquireSearchVO searchVO) throws Exception {
		  
		InquireDAO InquireDAO = new InquireDAO();
		System.out.println("==============상세정보 조회==============");
		return InquireDAO.getInquireView(searchVO);
	}
	
	public InquireResultVO getFileInfo(String file_id) throws Exception {
		  
		InquireDAO InquireDAO = new InquireDAO();
		
		return InquireDAO.getFileInfo(file_id);
	}
	
	public boolean getInquireInsert(InquireVO vo, InquireSearchVO searchVO) throws Exception {
		  
		InquireDAO InquireDAO = new InquireDAO();
		
		return InquireDAO.getInquireInsert(vo, searchVO);
	}
	
	public InquireResultVO getSatiInfo(String seq, String login_id) throws Exception {
		  
		InquireDAO InquireDAO = new InquireDAO();
		
		return InquireDAO.getSatiInfo(seq, login_id);
	}
	
	
	public boolean getInquireUpdate(InquireVO vo, InquireSearchVO searchVO) throws Exception {
		  
		InquireDAO InquireDAO = new InquireDAO();
		
		return InquireDAO.getInquireUpdate(vo, searchVO);
	}
	
	public void getQuestionFileDelete(InquireVO vo, InquireSearchVO searchVO) throws Exception {
		InquireDAO InquireDAO = new InquireDAO(); 
		
		InquireDAO.getFileDelete(vo, searchVO);
	}
	
	public boolean getInquireDelete(InquireSearchVO searchVO) throws Exception {
		  
		InquireDAO IquireDAO = new InquireDAO();
		
		return	IquireDAO.getInquireDelete(searchVO);		
	}
	
	/**
	 * Q&A 만족도평가 등록하는 메소드
	 */
	public boolean getSatiInsert(InquireVO vo, InquireSearchVO searchVO, SatiVO satiVO) throws Exception {
		  
		InquireDAO IquireDAO = new InquireDAO();
		InquireResultVO resultVo =  new InquireResultVO();
		
		return IquireDAO.getSatiInsert(vo, searchVO, satiVO);
	}
	
	/**
	 * 대분야정보 조회 메소드
	 */
	public InquireResultVO getCodeInfo() throws Exception {
		  
		InquireDAO InquireDAO = new InquireDAO();
		
		return InquireDAO.getCodeInfo();
	}
	
	/**
	 * 소분야정보 조회 메소드
	 */
	public InquireResultVO getCodeInfo1() throws Exception {
		  
		InquireDAO InquireDAO = new InquireDAO();
		
		return InquireDAO.getCodeInfo1();
	}
	
    
	/**
	 * Q&A 답변 등록하는 메소드
	 */	
	public boolean getAnswerInsert(InquireVO vo, InquireSearchVO searchVO) throws Exception {
		
		InquireDAO InquireDAO = new InquireDAO();
		
		return InquireDAO.getAnswerInsert(vo, searchVO);
	}
	
	/**
	 * Q&A 답변 등록하는 메소드
	 */	
	public boolean getAnswerUpdate(InquireVO vo, InquireSearchVO searchVO) throws Exception {
		
		InquireDAO InquireDAO = new InquireDAO();
		
		return InquireDAO.getAnswerUpdate(vo, searchVO);
	}
	
	public void putAttach(InquireVO vo, AttachVO[] attachVO) throws Exception {
		InquireDAO InquireDAO = new InquireDAO(); 
		
		InquireDAO.putAttach(vo, attachVO);
	}
		
	public void getAnswerFileDelete(InquireVO vo, InquireSearchVO searchVO) throws Exception {
		InquireDAO InquireDAO = new InquireDAO(); 
		
		InquireDAO.getFileDelete(vo, searchVO);
	}
	/**
	 * Q&A 타기관 담당자지정 등록하는 메소드
	 */	
	public InquireResultVO getOrgTransList() throws Exception {
		
		InquireDAO InquireDAO = new InquireDAO();
		
		return InquireDAO.getOrgTransList();
	}
	
	/**
	 * Q&A 타기관 담당자지정 등록하는 메소드
	 */	
	public boolean getOrgTransInsert(InquireVO vo, InquireSearchVO searchVO, String server_ip) throws Exception {
		
		InquireDAO IquireDAO = new InquireDAO();
		
		return IquireDAO.getOrgTransInsert(vo, searchVO, server_ip);
	}   
	
	
	// 온라인상담 Q&A 리스트 조회
	public InquireResultVO getInquireOrgList(InquireSearchVO searchVO) throws Exception {
		  
		InquireDAO InquireDAO = new InquireDAO();
		
		return InquireDAO.getInquireOrgList(searchVO);
	}
	
	/**
	 * Q&A SMS 발송하는 메소드
	 */
	public boolean sendSms(String seq, String type, String login_id) throws Exception {
			
		InquireDAO InquireDAO = new InquireDAO();
		
		return InquireDAO.sendSms(seq, type, login_id);
	}
	
	/**
	 * Q&A 이메일 발송하는 메소드
	 */
	public boolean sendEmail(String seq, String type, String login_id) throws Exception {
		
		InquireDAO IquireDAO = new InquireDAO();
		
		return IquireDAO.sendEmail(seq, type, login_id);
	}
	
	/**
	 * Q&A 엑셀다운로드 파일 생성후 파일명과 파일경로 조회하는 메소드
	 */
	public InquireResultVO getInquireExcelDown(InquireSearchVO searchVO) throws Exception {
		
		InquireDAO InquireDAO = new InquireDAO();
		
		return InquireDAO.getInquireExcelDown(searchVO);
	}
	
	/**
	 * USER정보 조회
	 */
	public InquireResultVO getUserInfo(String id) throws Exception {
		  
		InquireDAO InquireDAO = new InquireDAO();
		
		return InquireDAO.getUserInfo(id);
	}

	/**
	 * 게시물 스크랩 등록
	 */
	public InquireResultVO getInquireScrap(InquireSearchVO searchVO) throws Exception {
		  
		InquireDAO InquireDAO = new InquireDAO();
		
		return InquireDAO.getInquireScrap(searchVO);
	}
	
    /**
     * 비회원 비밀번호 확인
     */
    public boolean getPasswordCheck(InquireVO vo) throws Exception {
            
        InquireDAO InquireDAO = new InquireDAO();
        
        return InquireDAO.getPasswordCheck(vo);
    }
}