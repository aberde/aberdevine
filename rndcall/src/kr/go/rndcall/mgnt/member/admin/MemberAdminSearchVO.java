package kr.go.rndcall.mgnt.member.admin;

import kr.go.rndcall.mgnt.common.BaseSearchVO;

public class MemberAdminSearchVO extends BaseSearchVO {

	private String login_id = "";
	private String name = "";
	private String auth_id = "";
	private String roleCD = "";
	private String orgCD = "";
	private String search_sel = "";
	private String search_word = "";
	
	/**
	 * @return the auth_id
	 */
	public String getAuth_id() {
		return auth_id;
	}
	/**
	 * @return the login_id
	 */
	public String getLogin_id() {
		return login_id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the orgCD
	 */
	public String getOrgCD() {
		return orgCD;
	}
	/**
	 * @return the roleCD
	 */
	public String getRoleCD() {
		return roleCD;
	}
	/**
	 * @param auth_id the auth_id to set
	 */
	public void setAuth_id(String auth_id) {
		this.auth_id = auth_id;
	}
	/**
	 * @param login_id the login_id to set
	 */
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param orgCD the orgCD to set
	 */
	public void setOrgCD(String orgCD) {
		this.orgCD = orgCD;
	}
	/**
	 * @param roleCD the roleCD to set
	 */
	public void setRoleCD(String roleCD) {
		this.roleCD = roleCD;
	}
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
	 * @param search_sel the search_sel to set
	 */
	public void setSearch_sel(String search_sel) {
		this.search_sel = search_sel;
	}
	/**
	 * @param search_word the search_word to set
	 */
	public void setSearch_word(String search_word) {
		this.search_word = search_word;
	}
	
	
}

