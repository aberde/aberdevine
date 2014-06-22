package kr.go.rndcall.mgnt.mypage.biz;

import java.sql.SQLException;

import javax.naming.NamingException;

import kr.go.rndcall.mgnt.mypage.vo.SatiVO;
import kr.go.rndcall.mgnt.mypage.dao.MypageDAO;
import kr.go.rndcall.mgnt.mypage.vo.MypageAttachVO;
import kr.go.rndcall.mgnt.mypage.vo.MypageResultVO;
import kr.go.rndcall.mgnt.mypage.vo.MypageSearchVO;
import kr.go.rndcall.mgnt.mypage.vo.MypageVO;
import kr.go.rndcall.mgnt.common.AttachVO;
import kr.go.rndcall.mgnt.common.DAOBaseException;

public class MypageBiz {
	public MypageResultVO getMypageList(MypageSearchVO searchVO) throws Exception {
		  
		MypageDAO MypageDAO = new MypageDAO();
		
		return MypageDAO.getMypageList(searchVO);
	}
	
	public MypageResultVO getMypageView(MypageSearchVO searchVO) throws Exception {
		  
		MypageDAO MypageDAO = new MypageDAO();
		
		return MypageDAO.getMypageView(searchVO);
	}
	
	public MypageResultVO getFileInfo(String file_id) throws Exception {
		  
		MypageDAO MypageDAO = new MypageDAO();
		
		return MypageDAO.getFileInfo(file_id);
	}
	
	public boolean getMypageUpdate(MypageVO vo, MypageSearchVO searchVO) throws Exception {
		  
		MypageDAO MypageDAO = new MypageDAO();
		
		return MypageDAO.getMypageUpdate(vo, searchVO);
	}
	
	public boolean getMypageDelete(MypageSearchVO searchVO) throws Exception {
		  
		MypageDAO MypageDAO = new MypageDAO();
		
		return	MypageDAO.getMypageDelete(searchVO);		
	}
	public MypageResultVO getSatiInfo(String seq, String login_id) throws Exception {
		  
		MypageDAO MypageDAO = new MypageDAO();
		
		return MypageDAO.getSatiInfo(seq, login_id);
	}
	/**
	 * Q&A 만족도평가 등록하는 메소드
	 */
	public boolean getSatiInsert(MypageVO vo, MypageSearchVO searchVO, SatiVO satiVO) throws Exception {
		  
		MypageDAO MypageDAO = new MypageDAO();
		
		return MypageDAO.getSatiInsert(vo, searchVO, satiVO);
	}
	
	public void putAttach(MypageVO vo, AttachVO[] attachVO) throws Exception {
		MypageDAO MypageDAO = new MypageDAO(); 
		
		MypageDAO.putAttach(vo, attachVO);
	}

	public void getQuestionFileDelete(MypageVO vo, MypageSearchVO searchVO) throws Exception {
		MypageDAO MypageDAO = new MypageDAO(); 
		
		MypageDAO.getFileDelete(vo, searchVO);
	}
	
	//스크랩 삭제
	public boolean getMypageScrapDelete(MypageSearchVO searchVO) throws Exception {
		  
		MypageDAO MypageDAO = new MypageDAO();
		
		return MypageDAO.getMypageScrapDelete(searchVO);
	}
}
