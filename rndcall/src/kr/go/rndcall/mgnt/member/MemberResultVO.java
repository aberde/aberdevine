package kr.go.rndcall.mgnt.member;

import kr.go.rndcall.mgnt.common.BaseResultVO;

public class MemberResultVO extends BaseResultVO {

	private MemberVO vo = new MemberVO();

	/**
	 * @return the vo
	 */
	public MemberVO getVo() {
		return vo;
	}

	/**
	 * @param vo the vo to set
	 */
	public void setVo(MemberVO vo) {
		this.vo = vo;
	}
}
