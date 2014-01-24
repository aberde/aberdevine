package whoya.common;

import whoya.whoyaMap;

public class commonSession { 
	
	private final String coId;
	private final String usrId;
	private final String usrNm;
	private final String pwd;
	private final String rolCd;
	private final String rolNm;
	private final String dptCd;
	private final String dptNm;
	
	public commonSession(whoyaMap user) {
		
		this.coId  = user.getString("COID" , null);
		this.usrId = user.getString("USRID", null);
		this.usrNm = user.getString("USRNM", null);
		this.pwd   = user.getString("PWD"  , null);
		this.rolCd = user.getString("ROLCD", null);
		this.rolNm = user.getString("ROLNM", null);
		this.dptCd = user.getString("DPTCD", null);
		this.dptNm = user.getString("DPTNM", null);
	}
	
	public String getCoId() {
		return coId;
	}
	
	public String getUsrId() {
		return usrId;
	}

	public String getUsrNm() {
		return usrNm;
	}
	
	public String getPwd() {
		return pwd;
	}
	
	public String getRolCd() {
		return rolCd;
	}
	
	public String getRolNm() {
		return rolNm;
	}
	
	public String getDptCd() {
		return dptCd;
	}
	
	public String getDptNm() {
		return dptNm;
	}
}
