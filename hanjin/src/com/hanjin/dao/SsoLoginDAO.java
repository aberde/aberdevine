package com.hanjin.dao;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.hanjin.bean.SsoLoginBean;
import com.hanjin.cmm.DBConnection;
import com.hanjin.cmm.HanjinServerURL;
import com.hanjin.cmm.URLConnection;

public class SsoLoginDAO {
	
	/**
	 * SSO �α���
	 * @param user ����ھ��̵�
	 * @param password ��й�ȣ
	 * @return xml������ ����� ����
	 * @throws Exception
	 */
	public SsoLoginBean getSsoLogin(String user, String password) throws Exception {
		SsoLoginBean bean = new SsoLoginBean();

		String url = HanjinServerURL.SSO_LOGIN_URL;
		String data = "USER=" + URLEncoder.encode(user, "UTF-8") + "&PASSWORD=" + URLEncoder.encode(password, "UTF-8")
				+ "&TARGET=" + URLEncoder.encode("http://nmo.hanjin.com:8000/sm_sso/auth.html", "UTF-8")
				+ "&smagentname=" + URLEncoder.encode("hj_nmo_agent01.hanjin.com", "UTF-8");
		Map<String, String> retValue = URLConnection.getURLConnection(url, data, "");

		bean.setSMSESSION(retValue.get("SMSESSION"));
		bean.setLuid(retValue.get("luid"));
		bean.setGuid(retValue.get("guid"));
		bean.setEmail(retValue.get("email"));
		
		// email ������� DB�˻��Ͽ� ������.
		if ( bean.getEmail() == null || bean.getEmail().isEmpty() ) {
			DBConnection dbcon = new DBConnection(2);
			
			StringBuffer sql = new StringBuffer();
			sql.append("select MAILID FROM top_comm.VWTBLUSERMASTER WHERE USERID = '" + user + "'");
			HashMap<String, String> map = dbcon.getData(sql.toString());
			if ( map != null ) {
				bean.setEmail(map.get("MAILID"));
			}
		}
	    
	    return bean;
	}
}
