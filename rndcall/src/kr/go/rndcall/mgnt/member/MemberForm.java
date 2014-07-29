package kr.go.rndcall.mgnt.member;

import java.util.ArrayList;

import kr.go.rndcall.mgnt.common.BaseForm;
import kr.go.rndcall.mgnt.member.admin.MemberAdminVO;

public class MemberForm extends BaseForm {
	
	private MemberSearchVO searchVO = new MemberSearchVO();
	private MemberVO vo = new MemberVO();
	private ArrayList<MemberAdminVO> orgCdList = new ArrayList<MemberAdminVO>();
	
	/**
	 * @return the searchVO
	 */
	public MemberSearchVO getSearchVO() {
		return searchVO;
	}
	/**
	 * @return the vo
	 */
	public MemberVO getVo() {
		return vo;
	}
	/**
	 * @param searchVO the searchVO to set
	 */
	public void setSearchVO(MemberSearchVO searchVO) {
		this.searchVO = searchVO;
	}
	/**
	 * @param vo the vo to set
	 */
	public void setVo(MemberVO vo) {
		this.vo = vo;
	}
	
    public ArrayList<MemberAdminVO> getOrgCdList() {
        return orgCdList;
    }
    public void setOrgCdList(ArrayList<MemberAdminVO> orgCdList) {
        this.orgCdList = orgCdList;
    }
}
