package kr.go.rndcall.mgnt.category;

import kr.go.rndcall.mgnt.common.BaseSearchVO;

public class CategorySearchVO extends BaseSearchVO {
	
	private String category_id = "";
	private String parent_id = "";
	private String tree_level = "";
	private String category_nm = "";
	private String isUse = "";
	private String search_sel = "";
	private String search_word = "";
	
	/**
	 * @return the search_sel
	 */
	public String getSearch_sel() {
		return search_sel;
	}
	/**
	 * @return the search_word
	 */
	public String getSearch_word() {
		return search_word;
	}
	/**
	 * @param search_word the search_word to set
	 */
	public void setSearch_word(String search_word) {
		this.search_word = search_word;
	}
	/**
	 * @param search_sel the search_sel to set
	 */
	public void setSearch_sel(String search_sel) {
		this.search_sel = search_sel;
	}
	/**
	 * @return the category_id
	 */
	public String getCategory_id() {
		return category_id;
	}
	/**
	 * @return the category_nm
	 */
	public String getCategory_nm() {
		return category_nm;
	}
	/**
	 * @return the isUse
	 */
	public String getIsUse() {
		return isUse;
	}
	/**
	 * @return the parent_id
	 */
	public String getParent_id() {
		return parent_id;
	}
	/**
	 * @return the tree_level
	 */
	public String getTree_level() {
		return tree_level;
	}
	/**
	 * @param category_id the category_id to set
	 */
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	/**
	 * @param category_nm the category_nm to set
	 */
	public void setCategory_nm(String category_nm) {
		this.category_nm = category_nm;
	}
	/**
	 * @param isUse the isUse to set
	 */
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	/**
	 * @param parent_id the parent_id to set
	 */
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	/**
	 * @param tree_level the tree_level to set
	 */
	public void setTree_level(String tree_level) {
		this.tree_level = tree_level;
	}
	
}
