package kr.go.rndcall.mgnt.member;

import kr.go.rndcall.mgnt.common.BaseForm;

public class MemberForm extends BaseForm {
	
	private MemberSearchVO searchVO = new MemberSearchVO();
	private MemberVO vo = new MemberVO();
	
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

}
