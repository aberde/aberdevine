package com.hanjin.service;

import com.hanjin.bean.MessageCountBean;

public interface MessageCountService {

	/**
	 * Message Count
	 * @param luid ����ھ��̵�
	 * @return MessageCountBean
	 * @throws Exception
	 */
	public MessageCountBean getMessageCount(String luid) throws Exception;
}
