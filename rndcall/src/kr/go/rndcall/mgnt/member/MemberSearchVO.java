package kr.go.rndcall.mgnt.member;

import kr.go.rndcall.mgnt.common.BaseSearchVO;

public class MemberSearchVO extends BaseSearchVO {
	
	private String checkIdVal = "";
	private String login_id = "";


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

}
