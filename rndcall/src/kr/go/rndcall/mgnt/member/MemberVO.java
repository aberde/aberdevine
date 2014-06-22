package kr.go.rndcall.mgnt.member;

import kr.go.rndcall.mgnt.common.BaseVO;

public class MemberVO extends BaseVO{
	
	private String login_id = "";
	private String password = "";
	private String auth_id = "";
	private String mobile = "";
	private String email = "";
	private String name = "";
	private String org_cd = "";
	private String org_nm = "";
	private String attached_nm = "";
	private String roleCD = "";
	private String checkIdVal = "";
	private String re_password = "";
	
	private String seq = "";
	private String title = "";
	private String contents = "";
	
	public String getContents() {
		return contents;
	}
	public String getTitle() {
		return title;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the re_password
	 */
	public String getRe_password() {
		return re_password;
	}
	/**
	 * @param re_password the re_password to set
	 */
	public void setRe_password(String re_password) {
		this.re_password = re_password;
	}
	/**
	 * @return the checkIdVal
	 */
	public String getCheckIdVal() {
		return checkIdVal;
	}
	/**
	 * @param checkIdVal the checkIdVal to set
	 */
	public void setCheckIdVal(String checkIdVal) {
		this.checkIdVal = checkIdVal;
	}
	/**
	 * @return the auth_id
	 */
	public String getAuth_id() {
		return auth_id;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @return the login_id
	 */
	public String getLogin_id() {
		return login_id;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param auth_id the auth_id to set
	 */
	public void setAuth_id(String auth_id) {
		this.auth_id = auth_id;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @param login_id the login_id to set
	 */
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the attached_nm
	 */
	public String getAttached_nm() {
		return attached_nm;
	}
	/**
	 * @return the org_cd
	 */
	public String getOrg_cd() {
		return org_cd;
	}
	/**
	 * @return the org_nm
	 */
	public String getOrg_nm() {
		return org_nm;
	}
	/**
	 * @return the roleCD
	 */
	public String getRoleCD() {
		return roleCD;
	}
	/**
	 * @param attached_nm the attached_nm to set
	 */
	public void setAttached_nm(String attached_nm) {
		this.attached_nm = attached_nm;
	}
	/**
	 * @param org_cd the org_cd to set
	 */
	public void setOrg_cd(String org_cd) {
		this.org_cd = org_cd;
	}
	/**
	 * @param org_nm the org_nm to set
	 */
	public void setOrg_nm(String org_nm) {
		this.org_nm = org_nm;
	}
	/**
	 * @param roleCD the roleCD to set
	 */
	public void setRoleCD(String roleCD) {
		this.roleCD = roleCD;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}

}
