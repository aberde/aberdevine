package com.hanjin.service;

import com.hanjin.bean.MessageCountBean;

public interface MessageCountService {

	/**
	 * Message Count
	 * @param luid 사용자아이디
	 * @return MessageCountBean
	 * @throws Exception
	 */
	public MessageCountBean getMessageCount(String luid) throws Exception;
}
