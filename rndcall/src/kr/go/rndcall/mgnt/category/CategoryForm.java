package kr.go.rndcall.mgnt.category;

import kr.go.rndcall.mgnt.common.BaseForm;

public class CategoryForm extends BaseForm {

	private CategorySearchVO searchVO = new CategorySearchVO();
	private CategoryVO vo = new CategoryVO();
	private String parent_nm = "";
	
	/**
	 * @return the parent_nm
	 */
	public String getParent_nm() {
		return parent_nm;
	}
	/**
	 * @param parent_nm the parent_nm to set
	 */
	public void setParent_nm(String parent_nm) {
		this.parent_nm = parent_nm;
	}
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
