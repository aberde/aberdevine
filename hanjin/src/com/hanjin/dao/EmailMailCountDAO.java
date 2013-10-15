package com.hanjin.dao;

import java.util.Map;

import com.hanjin.bean.EmailMailCountBean;
import com.hanjin.cmm.HanjinServerURL;
import com.hanjin.cmm.ReadXml;
import com.hanjin.cmm.URLConnection;

public class EmailMailCountDAO {
	
	/**
	 * Email Mail 읽지 않은 메일수
	 * @param dirkey Inbox_사용자아이디
	 * @param JSESSIONID
	 * @return 읽지 않은 메일수
	 * @throws Exception
	 */
	public EmailMailCountBean getEmailMailCount(String dirkey, String JSESSIONID) throws Exception {
		EmailMailCountBean bean = new EmailMailCountBean();

		String url = HanjinServerURL.EMAIL_MAILCOUNT_URL;
		String data = "dirkey=" + dirkey;
		String cookie = "JSESSIONID=" + JSESSIONID;
		Map<String, String> retValue = URLConnection.getURLConnection(url, data, cookie);

		EmailMailCountBean.Result result = new EmailMailCountBean.Result();
		result = (EmailMailCountBean.Result)ReadXml.getStringXMLtoObject(retValue.get("responseText"), result);
		bean.setResult(result);
		
	    return bean;
	}
}
