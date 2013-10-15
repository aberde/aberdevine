package com.hanjin.service;

import org.springframework.stereotype.Service;

import com.hanjin.bean.MessageCountBean;
import com.hanjin.dao.MessageCountDAO;

@Service
public class MessageCountServiceImpl implements MessageCountService {

	/**
	 * Message Count
	 * @param luid 사용자아이디
	 * @return MessageCountBean
	 * @throws Exception
	 */
	public MessageCountBean getMessageCount(String luid) throws Exception {
		MessageCountDAO dao = new MessageCountDAO();
		MessageCountBean bean = dao.getMessageCount(luid);
		
		return bean;
	}
}
