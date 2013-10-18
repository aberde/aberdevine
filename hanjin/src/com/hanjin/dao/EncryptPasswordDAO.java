package com.hanjin.dao;

import java.net.URLEncoder;
import java.util.Map;

import com.hanjin.bean.EncryptPasswordBean;
import com.hanjin.cmm.HanjinServerURL;
import com.hanjin.cmm.ReadXml;
import com.hanjin.cmm.URLConnection;

public class EncryptPasswordDAO {

	/**
	 * ��й�ȣ ��ȣȭ
	 * @param pwd ��ȣȭ ��ų ��й�ȣ
	 * @return ��ȣȭ�� ��й�ȣ(XML����)
	 * @throws Exception
	 */
	public EncryptPasswordBean getEncryptPassword(String pwd) throws Exception {
		EncryptPasswordBean bean = new EncryptPasswordBean();
		
		String url = HanjinServerURL.ENCRYPT_PASSWORD_URL;
		String data = "pwd=" + URLEncoder.encode(pwd, "UTF-8");
		Map<String, String> retValue = URLConnection.getURLConnection(url, data, "");

		EncryptPasswordBean.Result result = new EncryptPasswordBean.Result();
		result = (EncryptPasswordBean.Result)ReadXml.getStringXMLtoObject(retValue.get("responseText"), result);
		bean.setResult(result);
		
		return bean;
	}
}
