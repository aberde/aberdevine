package kr.go.rndcall.mgnt.category;

import kr.go.rndcall.mgnt.common.BaseResultVO;

public class CategoryResultVO extends BaseResultVO {
	
	private CategorySearchVO searchVO = new CategorySearchVO();
	private CategoryVO vo = new CategoryVO();
	
	/**
	 * @return the searchVO
	 */
	public CategorySearchVO getSearchVO() {
		return searchVO;
	}
	/**
	 * @return the vo
	 */
	public CategoryVO getVo() {
		return vo;
	}
	/**
	 * @param searchVO the searchVO to set
	 */
	public void setSearchVO(CategorySearchVO searchVO) {
		this.searchVO = searchVO;
	}
	/**
	 * @param vo the vo to set
	 */
	public void setVo(CategoryVO vo) {
		this.vo = vo;
	}
	
}
