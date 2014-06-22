package kr.go.rndcall.mgnt.common;


/**
 * <PRE>
 * Class  : DAOBaseException
 * Comment: 클래스의 기능/특이사항 기록
 *          1.
 *          2.
 * History:버전변경기록
 * 
 * </PRE>
 * Created on Aug 30, 2005
 * @author: Sungyun.kang
 *          Copyright (C) 2004 by SAMSUNG SDS co.,Ltd. All right reserved.
 */

public class DAOBaseException extends BaseException 
{
	public DAOBaseException() 
	{
		super();
	}

	public DAOBaseException(String s) 
	{
		super(s);
	}

	public DAOBaseException(Exception e)
	{
		super(e);
	}
}
