package com.hanjin.service;

import org.springframework.stereotype.Service;

import com.hanjin.bean.BulletinBoardCountBean;
import com.hanjin.dao.BulletinBoardCountDAO;

@Service
public class BulletinBoardCountServiceImpl implements BulletinBoardCountService {

	/**
	 * BulletinBoard Count
	 * @param pUserID 사용자아이디(luid)
	 * @return BulletinBoardCountBean
	 * @throws Exception
	 */
	public BulletinBoardCountBean getBulletinBoardCount(String pUserID) throws Exception {
		BulletinBoardCountDAO dao = new BulletinBoardCountDAO();
		BulletinBoardCountBean bean = dao.getBulletinBoardCount(pUserID);
		
		return bean;
	}
}
