package kr.go.rndcall.mgnt.category;

import kr.go.rndcall.mgnt.common.BaseVO;

public class CategoryVO extends BaseVO {
	
	private String category_id = "";
	private String category_nm = "";
	private String tree_level = "";
	private String order_no = "";
	private String parent_id = "";
	private String is_use = "";
	private String login_id = "";
	private String dtl_cnt = "";
	private String parent_nm = "";
	private String qna_yn = "";
	
	
	public String getQna_yn() {
        return qna_yn;
    }
    public void setQna_yn(String qna_yn) {
        this.qna_yn = qna_yn;
    }
    /**
	 * @return the dtl_cnt
	 */
	public String getDtl_cnt() {
		return dtl_cnt;
	}
	/**
	 * @param dtl_cnt the dtl_cnt to set
	 */
	public void setDtl_cnt(String dtl_cnt) {
		this.dtl_cnt = dtl_cnt;
	}
	/**
	 * @return the login_id
	 */
	public String getLogin_id() {
		return login_id;
	}
	/**
	 * @param login_id the login_id to set
	 */
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
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
	 * @return the is_use
	 */
	public String getIs_use() {
		return is_use;
	}
	/**
	 * @return the order_no
	 */
	public String getOrder_no() {
		return order_no;
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
	 * @param is_use the is_use to set
	 */
	public void setIs_use(String is_use) {
		this.is_use = is_use;
	}
	/**
	 * @param order_no the order_no to set
	 */
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
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

}
