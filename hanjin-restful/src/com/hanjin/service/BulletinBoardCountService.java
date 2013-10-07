package com.hanjin.service;

import com.hanjin.bean.BulletinBoardCountBean;

public interface BulletinBoardCountService {

	/**
	 * BulletinBoard Count
	 * @param pUserID 사용자아이디(luid)
	 * @return BulletinBoardCountBean
	 * @throws Exception
	 */
	public BulletinBoardCountBean getBulletinBoardCount(String pUserID) throws Exception;
}
