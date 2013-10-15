package com.hanjin.dao;

import java.net.URLEncoder;
import java.util.Map;

import com.hanjin.bean.EmailLoginBean;
import com.hanjin.cmm.HanjinServerURL;
import com.hanjin.cmm.ReadXml;
import com.hanjin.cmm.URLConnection;

public class EmailLoginDAO {
	
	/**
	 * Email 로그인
	 * @param userid 사용자아이디(이메일)
	 * @return EmailLoginBean
	 * @throws Exception
	 */
	public EmailLoginBean getEmailLogin(String userid) throws Exception {
		EmailLoginBean bean = new EmailLoginBean();

		String url = HanjinServerURL.EMAIL_LOGIN_URL;
		String data = "userid=" + URLEncoder.encode(userid, "UTF-8");
		Map<String, String> retValue = URLConnection.getURLConnection(url, data, "");

		EmailLoginBean.Result result = new EmailLoginBean.Result();
		result = (EmailLoginBean.Result)ReadXml.getStringXMLtoObject(retValue.get("responseText"), result);
		result.setJSESSIONID(retValue.get("JSESSIONID"));
		bean.setResult(result);
		return bean;
	}
}
