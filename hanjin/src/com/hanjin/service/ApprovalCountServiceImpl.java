package com.hanjin.service;

import org.springframework.stereotype.Service;

import com.hanjin.bean.ApprovalCountBean;
import com.hanjin.dao.ApprovalCountDAO;

@Service
public class ApprovalCountServiceImpl implements ApprovalCountService {

	/**
	 * Approval Count
	 * @param pUserID 사용자아이디(luid)
	 * @return ApprovalCountBean
	 * @throws Exception
	 */
	public ApprovalCountBean getApprovalCount(String pUserID) throws Exception {
		ApprovalCountDAO dao = new ApprovalCountDAO();
		ApprovalCountBean bean = dao.getApprovalCount(pUserID);
		
		return bean;
	}
}
