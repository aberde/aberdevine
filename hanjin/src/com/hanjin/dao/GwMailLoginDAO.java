package com.hanjin.dao;

import java.net.URLEncoder;
import java.util.Map;

import com.hanjin.bean.GwMailLoginBean;
import com.hanjin.cmm.HanjinServerURL;
import com.hanjin.cmm.ReadXml;
import com.hanjin.cmm.URLConnection;

public class GwMailLoginDAO {
	
	/**
	 * GW Mail �α���
	 * @param MailId ����ھ��̵�
	 * @param MailPassword ����ں�й�ȣ
	 * @return �α��� ��������
	 * @throws Exception
	 */
	public GwMailLoginBean getGwMailLogin(String MailId, String MailPassword) throws Exception {
		GwMailLoginBean bean = new GwMailLoginBean();

		String url = HanjinServerURL.GW_MAIL_LOGIN_URL;
		String data = "MailId=" + URLEncoder.encode(MailId, "UTF-8") + "&MailPassword=" + URLEncoder.encode(MailPassword, "UTF-8")
				+ "&ServerVersion=Exchange2010"
				+ "&ServiceUrl=" + URLEncoder.encode("http://exchange.hanjin.com/ews/exchange.asmx", "UTF-8")
				+ "&function=Authentication";
		Map<String, String> retValue = URLConnection.getURLConnection(url, data, "");

		GwMailLoginBean.Result result = new GwMailLoginBean.Result();
		result = (GwMailLoginBean.Result)ReadXml.getStringXMLtoObject(retValue.get("responseText"), result);
		bean.setResult(result);
		
	    return bean;
	}
}
