package kr.go.rndcall.mgnt.discussion;

import java.sql.SQLException;

import kr.go.rndcall.mgnt.common.DAOBaseException;
import kr.go.rndcall.mgnt.common.FileVO;
import kr.go.rndcall.mgnt.discussion.DiscussionDAO;
import kr.go.rndcall.mgnt.discussion.DiscussionResultVO;
import kr.go.rndcall.mgnt.discussion.DiscussionSearchVO;
import kr.go.rndcall.mgnt.discussion.DiscussionVO;

public class DiscussionBiz {

	/*
	 * 토론 상세보기
	 */
	public DiscussionResultVO retrieveDiscussDetail(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		DiscussionResultVO resultVOInfo = null;
		DiscussionResultVO resultVOContents = null;
		DiscussionResultVO resultVOReply = null;
		DiscussionResultVO reusltVOBest = null;
		
		DiscussionResultVO resultVO = new DiscussionResultVO();
		// 조회수를 올린다.
		dao.updateDiscussCount(searchVO);
		
		// 토론 상세내역을 가져온다. null 일수 있음.
		resultVOInfo = dao.retrieveDiscussDetail(searchVO);
		// 토론상세 본문을 가져온다.
		resultVOContents = dao.retrieveDiscussContents(searchVO);
		
		// 토론 댓글 및 첨부파일을 가져온다.
		resultVOReply = dao.retrieveDiscussListMain(searchVO);
		
		// 하나로 묶는다.
		resultVO.setVo(resultVOInfo.getVo());
		if(resultVOContents != null && resultVOContents.getVoList() != null) {
			resultVO.setVoList(resultVOContents.getVoList());
		} else {
			resultVO.setVoList(null);
		}
		
		if(resultVOReply != null) {
			resultVO.setVoList1(resultVOReply.getVoList());
			resultVO.setTotRowCount(resultVOReply.getTotRowCount());
		} else {
			resultVO.setVoList1(null);
		}
		return resultVO;
	}
	
	/*
	 * 토론 본문을 입력한다.
	 */
	public void createDiscussContents(DiscussionVO vo, DiscussionSearchVO searchVO, FileVO[] fileInfo) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		dao.createDiscussContents(vo, searchVO, fileInfo);
	}
	
	/*
	 * 토론본문을 삭제한다.
	 */
	public void deleteDiscussContents(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		dao.deleteDiscussContents(searchVO);
	}

	public DiscussionResultVO updateDiscussContentsForm(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		return dao.updateDiscussContentsForm(searchVO);
	}

	public void updateDiscussContents(DiscussionSearchVO searchVO, DiscussionVO vo, FileVO[] fileInfo) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		dao.updateDiscussContents(searchVO, vo, fileInfo);
	}

	public DiscussionResultVO updateIssueDetailsForm(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		
		return dao.updateIssueDetailsForm(searchVO);
	}

	public void updateIssueDetailsContents(DiscussionSearchVO searchVO, DiscussionVO vo, FileVO[] fileInfo) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		dao.updateIssueDetailsContents(searchVO, vo, fileInfo);
		
	}

	public DiscussionResultVO updateCommentContentsForm(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		return dao.updateCommentContentsForm(searchVO);
	}

	public void updateCommentContents(DiscussionSearchVO searchVO, DiscussionVO vo, FileVO[] fileInfo) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		dao.updateCommentContents(searchVO, vo, fileInfo);
	}

	/*
	 * 토론 댓글을 저장한다.
	 */
	public String createDiscussOpin(DiscussionVO vo, DiscussionSearchVO searchVO, FileVO[] fileInfo) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		return dao.createDiscussOpin(vo, searchVO, fileInfo);
	}
	
	/*
	 * 토론 댓글의 댓글을 저장한다.
	 */
	public void createDiscussOpinOpin(DiscussionVO vo, DiscussionSearchVO searchVO, FileVO[] fileInfo) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		dao.createDiscussOpinOpin(vo, searchVO, fileInfo);
	}
	
	/*
	 * 토론에 대해 찬성반대를 입력한다.
	 */
	public void createDisApop(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		dao.createDisApop(searchVO);
	}	
	
	/*
	 * id에 해당하는 찬성반대 내역이 있는지 조회한다.
	 */
	public String isApOp(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
	    return dao.isApOp(searchVO);
	}
	
	/*
	 * 본인글인지 확인한다.
	 */
	public String isSelfDiscussOpin(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
	    return dao.isSelfDiscussOpin(searchVO);
	}
	
	/*
	 * 토론 댓글을 삭제한다.
	 */
	public void deleteDiscussOpin(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		dao.deleteDiscussOpin(searchVO);
	}
	
	/*
	 * 토론을 삭제한다.
	 */
	public void deleteDiscuss(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {				
		DiscussionDAO dao = new DiscussionDAO();
		dao.deleteDiscuss(searchVO);
	}
	
	/*
	 * 토론을 수정한다.
	 */
	public void updateDiscuss(DiscussionSearchVO searchVO, DiscussionVO vo, FileVO[] fileInfo) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		dao.updateDiscuss(searchVO, vo, fileInfo);
	}
	
	/*
	 * 토론 수정폼 데이터 가져오기
	 */
	public DiscussionResultVO retrieveDiscussUpdateForm(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		DiscussionResultVO resultVOInfo = null;
		DiscussionResultVO resultVOFile = null;		
		DiscussionResultVO resultVO = new DiscussionResultVO();
				
		// 토론 상세내역을 가져온다. null 일수 있음.
		resultVOInfo = dao.retrieveDiscussDetail(searchVO);
		// 토론 첨부파일을 가져온다.
		resultVOFile = dao.retrieveDiscussDetailFile(searchVO);

		// 하나로 묶는다.
		DiscussionVO vo = (DiscussionVO)resultVOInfo.getVo();
		String str = vo.getDis_contents().replaceAll("\\r", "").replaceAll("\\n", "");
		vo.setDis_contents(str);
		resultVOInfo.setVo(vo);
		resultVO.setVo(resultVOInfo.getVo());
		resultVO.setVoList(resultVOFile.getVoList());
		return resultVO;
	}
	
	/*
	 * 토론을 첨부파일을 삭제한다.
	 */
	public void deleteDiscussAttach(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		dao.deleteDiscussAttach(searchVO);
	}
	
	/*
	 * 분야별 토론 목록을 가져온다.
	 */
	public DiscussionResultVO retrieveDiscussList(DiscussionVO vo, DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionDAO dao = new DiscussionDAO();
		return dao.retrieveDiscussList(vo, searchVO);
	}

}
