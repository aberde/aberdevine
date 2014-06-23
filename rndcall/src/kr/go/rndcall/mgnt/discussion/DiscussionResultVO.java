package kr.go.rndcall.mgnt.discussion;

import kr.go.rndcall.mgnt.common.BaseResultVO;

public class DiscussionResultVO extends BaseResultVO {

	protected DiscussionVO vo = new DiscussionVO();
	protected DiscussionSearchVO searchVO = new DiscussionSearchVO();
	
	public DiscussionSearchVO getSearchVO() {
		return searchVO;
	}
	public DiscussionVO getVo() {
		return vo;
	}
	public void setSearchVO(DiscussionSearchVO searchVO) {
		this.searchVO = searchVO;
	}
	public void setVo(DiscussionVO vo) {
		this.vo = vo;
	}

}
