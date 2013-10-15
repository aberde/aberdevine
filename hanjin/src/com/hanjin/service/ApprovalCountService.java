package com.hanjin.service;

import com.hanjin.bean.ApprovalCountBean;

public interface ApprovalCountService {

	/**
	 * Approval Count
	 * @param pUserID ����ھ��̵�(luid)
	 * @return ApprovalCountBean
	 * @throws Exception
	 */
	public ApprovalCountBean getApprovalCount(String pUserID) throws Exception;
}
