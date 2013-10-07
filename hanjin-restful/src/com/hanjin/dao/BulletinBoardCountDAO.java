package com.hanjin.dao;

import com.Kaoni.ezBoardSTD.ezGateWay.EzBoardSTDLocator;
import com.Kaoni.ezBoardSTD.ezGateWay.EzBoardSTDSoapStub;
import com.hanjin.bean.BulletinBoardCountBean;
import com.hanjin.cmm.ReadXml;

public class BulletinBoardCountDAO {
	
	/**
	 * BulletinBoard Count
	 * @param pUserID 사용자아이디(luid)
	 * @return BulletinBoardCountBean
	 * @throws Exception
	 */
	public BulletinBoardCountBean getBulletinBoardCount(String pUserID) throws Exception {
		BulletinBoardCountBean bean = new BulletinBoardCountBean();

		String retValue = "";
		try {
			String pReadFlag = "1";
			
			EzBoardSTDLocator locator = new EzBoardSTDLocator();
			EzBoardSTDSoapStub stub = (EzBoardSTDSoapStub)locator.getezBoardSTDSoap();
			retValue = stub.getNewItemListCount(pUserID, pReadFlag);
			System.out.println(retValue);
			retValue = "<ROOT>" + retValue + "</ROOT>";
			BulletinBoardCountBean.ROOT root = new BulletinBoardCountBean.ROOT();
			root = (BulletinBoardCountBean.ROOT)ReadXml.getStringXMLtoObject(retValue, root);
			bean.setRoot(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}
}
