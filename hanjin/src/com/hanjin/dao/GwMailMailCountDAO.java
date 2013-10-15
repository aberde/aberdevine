package com.hanjin.dao;

import java.net.URLEncoder;
import java.util.Map;

import com.hanjin.bean.GwMailMailCountBean;
import com.hanjin.cmm.HanjinServerURL;
import com.hanjin.cmm.ReadXml;
import com.hanjin.cmm.URLConnection;

public class GwMailMailCountDAO {
	
	/**
	 * GW Mail ���� ���� ���ϼ�
	 * @param MailId ����ھ��̵�
	 * @param MailPassword ����ں�й�ȣ
	 * @return ���� ���� ���ϼ�
	 * @throws Exception
	 */
	public GwMailMailCountBean getGwMailMailCount(String MailId, String MailPassword) throws Exception {
		GwMailMailCountBean bean = new GwMailMailCountBean();

		String url = HanjinServerURL.GW_MAIL_MAILCOUNT_URL;
		String data = "MailId=" + MailId + "&MailPassword=" + URLEncoder.encode(MailPassword, "UTF-8")
				+ "&ServerVersion=Exchange2010"
				+ "&ServiceUrl=" + URLEncoder.encode("http://exchange.hanjin.com/ews/exchange.asmx", "UTF-8")
				+ "&BoxName=InBox"
				+ "&function=MailCount";
		Map<String, String> retValue = URLConnection.getURLConnection(url, data, "");

		GwMailMailCountBean.Result result = new GwMailMailCountBean.Result();
		result = (GwMailMailCountBean.Result)ReadXml.getStringXMLtoObject(retValue.get("responseText"), result);
		bean.setResult(result);
		
	    return bean;
	}
}
