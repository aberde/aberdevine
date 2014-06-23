package kr.go.rndcall.mgnt.notice.biz;

import java.sql.SQLException;

import javax.naming.NamingException;

import kr.go.rndcall.mgnt.notice.dao.NoticeDAO;
import kr.go.rndcall.mgnt.notice.vo.NoticeAttachVO;
import kr.go.rndcall.mgnt.notice.vo.NoticeResultVO;
import kr.go.rndcall.mgnt.notice.vo.NoticeSearchVO;
import kr.go.rndcall.mgnt.notice.vo.NoticeVO;
import kr.go.rndcall.mgnt.common.AttachVO;
import kr.go.rndcall.mgnt.common.DAOBaseException;

public class NoticeBiz {

	//첨부파일 등록
    public void putAttach(NoticeVO vo, AttachVO[] attachVO) throws Exception {
    	NoticeDAO dao = new NoticeDAO();        
        dao.putAttach(vo, attachVO);
    }
	//공지사항을 등록한다.
	public NoticeResultVO noticeInsert(NoticeVO vo, NoticeSearchVO searchVO) throws Exception {
		NoticeResultVO reslutVO = new NoticeResultVO();
		NoticeDAO dao =  new NoticeDAO();
        reslutVO =  dao.noticeInsert(vo, searchVO);
        return reslutVO;
    }
	//공지사항의 리스트를 가져온다.
	public NoticeResultVO noticeList(NoticeSearchVO searchVO) throws DAOBaseException, SQLException, NamingException {
		NoticeDAO NoticeDAO = new NoticeDAO();
		return NoticeDAO.noticeList(searchVO);
	}
	//공지사항 상세보기를 한다.
	public NoticeResultVO noticeDetailView(NoticeSearchVO searchVO) throws Exception {
		NoticeDAO NoticeDAO = new NoticeDAO();
		return NoticeDAO.noticeDetailView(searchVO);
	}
	//공지사항 첨부파일정보
	public NoticeResultVO getFileInfo(String file_id) throws Exception {
		NoticeDAO NoticeDAO = new NoticeDAO();
		return NoticeDAO.getFileInfo(file_id);
	}
	//공지사항을 수정한다.
	public boolean noticeUpdate(NoticeVO vo, NoticeSearchVO searchVO) throws Exception {
		NoticeDAO NoticeDAO = new NoticeDAO();
		return NoticeDAO.noticeUpdate(vo, searchVO);
	}
	//공지사항을 삭제한다.
	public boolean noticeDelete(NoticeSearchVO searchVO) throws Exception {
		NoticeDAO NoticeDAO = new NoticeDAO();
		return	NoticeDAO.noticeDelete(searchVO);		
	}
	//첨부파일 삭제유무에따른 메소드
	public void getFileDelete(NoticeVO vo, NoticeSearchVO searchVO) throws Exception {
		NoticeDAO NoticeDAO = new NoticeDAO();
		NoticeDAO.getFileDelete(vo, searchVO);
	}
	
}
