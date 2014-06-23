package kr.go.rndcall.mgnt.member;

import kr.go.rndcall.mgnt.member.MemberDAO;
import kr.go.rndcall.mgnt.member.MemberSearchVO;

public class MemberBiz {

	public String getIdCheck(MemberSearchVO searchVO) throws Exception {
		String cnt = "";		
		MemberDAO dao =  new MemberDAO();
		cnt =  dao.getIdCheck(searchVO.getLogin_id());
		
		return cnt;
	}
	
	public String getInsert(MemberVO vo) throws Exception {
		String ins = "";		
		MemberDAO dao =  new MemberDAO();
		ins =  dao.getInsert(vo);
		
		return ins;
	}
	
	public MemberResultVO getUserInfo(MemberVO vo) throws Exception {
		MemberResultVO resultVO = new MemberResultVO();		
		MemberDAO dao =  new MemberDAO();
		resultVO =  dao.getUserInfo(vo.getLogin_id());
		
		return resultVO;
	}
	
	public String getUpdate(MemberVO vo) throws Exception {
		String ins = "";		
		MemberDAO dao =  new MemberDAO();
		ins =  dao.getUpdate(vo);
		
		return ins;
	}
	
	public MemberResultVO idFind(MemberVO vo) throws Exception {
		MemberResultVO reslutVO =new MemberResultVO();
		
		MemberDAO dao =  new MemberDAO();
		reslutVO =  dao.idFind(vo);		
		return reslutVO;
	}

	public MemberResultVO pwFind(MemberVO vo) throws Exception {
		MemberResultVO reslutVO =new MemberResultVO();
		
		MemberDAO dao =  new MemberDAO();
		reslutVO =  dao.pwFind(vo);		
		return reslutVO;
	}

	public int getOldDocCnt(MemberVO vo) throws Exception {
		
		MemberDAO dao =  new MemberDAO();
		return dao.getOldDocCnt(vo);		
	}

	public MemberResultVO getOldDocList(MemberVO vo) throws Exception {
		
		MemberDAO dao =  new MemberDAO();
		return dao.getOldDocList(vo);		
	}

	public MemberResultVO getOldDocSave(MemberVO vo) throws Exception {
		MemberResultVO resultVO = new MemberResultVO();
		MemberDAO dao =  new MemberDAO();
		String errCd = dao.getOldDocSave(vo);
		resultVO = dao.getOldDocList(vo);
		resultVO.setErrCd(errCd);
		return resultVO;		
	}

}
