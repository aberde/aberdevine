package kr.go.rndcall.mgnt.member.admin;

public class MemberAdminBiz {

	public MemberAdminResultVO getUserList(MemberAdminSearchVO searchVO) throws Exception {
		MemberAdminResultVO resultVO = new MemberAdminResultVO();
		MemberAdminDAO dao = new MemberAdminDAO();
		
		resultVO = dao.getUserList(searchVO);
		resultVO.setOrgCdList(dao.getOrgCD(searchVO));
		
		return resultVO;
	}

	public MemberAdminResultVO getUserInfo(MemberAdminSearchVO searchVO, MemberAdminVO vo) throws Exception {
		MemberAdminResultVO resultVO = new MemberAdminResultVO();
		MemberAdminDAO dao = new MemberAdminDAO();
		String updt = "";
		
		if (searchVO.getCrud().equals("UPDT")) {
			updt = dao.updateUserInfo(vo);
			searchVO.setCrud("UDC");
		}
		
		resultVO = dao.getUserInfo(searchVO);
		resultVO.setOrgCdList(dao.getOrgCD(searchVO));
		resultVO.setErrCd(updt);
		
		return resultVO;
	}
}
