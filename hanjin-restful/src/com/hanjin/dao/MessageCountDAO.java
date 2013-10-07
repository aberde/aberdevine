package com.hanjin.dao;

import java.util.HashMap;

import com.hanjin.bean.MessageCountBean;
import com.hanjin.cmm.DBConnection;

public class MessageCountDAO {
	
	/**
	 * Message Count
	 * @param luid 사용자아이디
	 * @return MessageCountBean
	 * @throws Exception
	 */
	public MessageCountBean getMessageCount(String user) throws Exception {
		MessageCountBean bean = new MessageCountBean();
				
		DBConnection dbcon = new DBConnection(9);
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(RECEIVER) MESSAGECOUNT FROM UCADM.MEMO_INFO WHERE READYN = 'N' AND SENDERVIEWYN = 'Y' AND RECEIVER = '" + user + "'");
		HashMap<String, String> map = dbcon.getData(sql.toString());
		if ( map != null ) {
			MessageCountBean.Result result = new MessageCountBean.Result();
			result.setMessageCount(map.get("MESSAGECOUNT"));
			bean.setResult(result);
		}
	    
	    return bean;
	}
}
