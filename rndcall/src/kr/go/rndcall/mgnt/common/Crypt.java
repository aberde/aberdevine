package kr.go.rndcall.mgnt.common;
import kr.go.ntis.sso.api.SSOApiCrypt;

public class Crypt {
	
	static final String eamSalt = "egEer0FxhS0nxwjfRfDaxw==";
	
	public static String encrypt(String target)  throws Exception {
		SSOApiCrypt ep = new SSOApiCrypt();
		return ep.encryptData(target, eamSalt);
	}
	
	public static String decrypt(String target)  throws Exception {
		SSOApiCrypt ep = new SSOApiCrypt();
		return ep.decryptData(target, eamSalt);
	}

}
