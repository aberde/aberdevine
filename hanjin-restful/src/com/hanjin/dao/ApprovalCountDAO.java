package com.hanjin.dao;

import com.Kaoni.ezApproval.ezGateWay.EzApprovalLocator;
import com.Kaoni.ezApproval.ezGateWay.EzApprovalSoapStub;
import com.hanjin.bean.ApprovalCountBean;
import com.hanjin.cmm.ReadXml;

public class ApprovalCountDAO {
	
	/**
	 * Approval Count
	 * @param pUserID ����ھ��̵�(luid)
	 * @return ApprovalCountBean
	 * @throws Exception
	 */
	public ApprovalCountBean getApprovalCount(String pUserID) throws Exception {
		ApprovalCountBean bean = new ApprovalCountBean();

		String retValue = "";
		try {
			// ������ Ÿ��(Approval Inbox:1, Approval Processing:3, Submitted:2) : ������ 1
			String pListType = "1";
			// �ٱ���(�⺻��:2)
			String strLang = "2";
			String pSubQuery = "";
			String pContID = "";
			
			EzApprovalLocator locator = new EzApprovalLocator();
			EzApprovalSoapStub stub = (EzApprovalSoapStub)locator.getezApprovalSoap();
			retValue = stub.getListCntTotal(pListType, pUserID, strLang, pSubQuery, pContID);
			System.out.println(retValue);
			ApprovalCountBean.ROOT root = new ApprovalCountBean.ROOT();
			root = (ApprovalCountBean.ROOT)ReadXml.getStringXMLtoObject(retValue, root);
			bean.setRoot(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}
}
