package kr.go.rndcall.mgnt.common;

/**
 * <PRE>
 * Class  : BaseException
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

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import kr.go.rndcall.mgnt.common.Constants;

public class BaseException extends Exception 
{
	protected Throwable rootCause = null;		//최초 발생한 Exception
	private List exceptions = new ArrayList();	//다중Exception을 저장
	private String messageKey = null;			//Exception message 를 나타내기 위한 Key값(*.property에서 찾는 Key값) 
	private Object[] messageArgs = null;		//message 안의 argument를 저장(*.property 내에서 변화되는 값을 argument로 가질수 있다)

	public BaseException() 
	{
		super();
		init();
	}

	public BaseException(String s) 
	{
		super(s);
		init();
	}
	
	public BaseException(Exception e)
	{
		super(e.toString());
		this.setRootCause(e);
		init();
	}

	public void init()
	{
		this.setMessageKey(Constants.EXCEPTION_KEY);
	}

	public List getExceptions() 
	{
		return exceptions;
	}

	public Object[] getMessageArgs() 
	{
		return messageArgs;
	}

	public String getMessageKey() 
	{
		return messageKey;
	}

	public Throwable getRootCause() 
	{
		return rootCause;
	}

	public void setExceptions(List list) 
	{
		exceptions = list;
	}

	public void setMessageArgs(Object[] objects) 
	{
		messageArgs = objects;
	}

	public void setMessageKey(String string) 
	{
		messageKey = string;
	}

	public void setRootCause(Throwable throwable) 
	{
		rootCause = throwable;
	}

	
	public void printStackTrace()
	{
		printStackTrace(System.err);
	}

	public void printStackTrace(PrintStream outStream)
	{
		printStackTrace(new PrintWriter(outStream));
	}
	
	public void printStackTrace(PrintWriter writer)
	{
		super.printStackTrace(writer);
		if(getRootCause() != null)
		{
			getRootCause().printStackTrace(writer);
		}
		writer.flush();
	}
}
