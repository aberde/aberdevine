package kr.go.rndcall.mgnt.member.admin;

import java.util.ArrayList;

import kr.go.rndcall.mgnt.common.BaseForm;

public class MemberAdminForm extends BaseForm {
	
	private MemberAdminSearchVO searchVO = new MemberAdminSearchVO();
	private MemberAdminVO vo  = new MemberAdminVO();
	private ArrayList<MemberAdminVO> orgCdList = new ArrayList<MemberAdminVO>();
	
	/**
	 * @return the searchVO
	 */
	public MemberAdminSearchVO getSearchVO() {
		return searchVO;
	}
	/**
	 * @return the vo
	 */
	public MemberAdminVO getVo() {
		return vo;
	}
	/**
	 * @param searchVO the searchVO to set
	 */
	public void setSearchVO(MemberAdminSearchVO searchVO) {
		this.searchVO = searchVO;
	}
	/**
	 * @param vo the vo to set
	 */
	public void setVo(MemberAdminVO vo) {
		this.vo = vo;
	}
	/**
	 * @return the orgCdList
	 */
	public ArrayList<MemberAdminVO> getOrgCdList() {
		return orgCdList;
	}
	/**
	 * @param orgCdList the orgCdList to set
	 */
	public void setOrgCdList(ArrayList<MemberAdminVO> orgCdList) {
		this.orgCdList = orgCdList;
	}

}
