package kr.go.rndcall.mgnt.common;
/**
 * <PRE>
 * Class  : ConnectionManager
 * Comment: 클래스의 기능/특이사항 기록
 *          1. JNDI에서 DataSource를 얻어와서 Connection을 넘겨준다.
 *          2. Connection 해제를 해준다.
 * History:버전변경기록
 * 
 * </PRE>
 * Created on Aug 30, 2005
 * @author: Sungyun.kang
 *          Copyright (C) 2004 by SAMSUNG SDS co.,Ltd. All right reserved.
 */

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import cubrid.jdbc.driver.CUBRIDConnection;

public class ConnectionManager  
{
	private static InitialContext ctx;

	private static DataSource getDataSource(String jndiName) 
	{
		DataSource ds = null;
		try 
		{
			if (ctx == null)
				ctx = new InitialContext();
			ds = (DataSource)ctx.lookup(jndiName);	//DataSource 찾기 최적화-->hashMap에 저장시켜놓고 재사용(속도저하시 적용)
		}
		catch (NamingException e) 
		{
			System.err.println("ConnectionManager.getDataSource():JNDI NamingException.....");
			e.printStackTrace();
		}
				
		return ds;			
	}

	public static Connection getConnection(String jndiName) 
	{
		Connection con = null;
	
		try 
		{
			con = getDataSource("java:/jdbc/callcenter").getConnection();
		}
		catch(SQLException e) 
		{
			System.err.println("ConnectionManager.getConnection():SQLException .....");			
			e.printStackTrace();
		}

		return con;
	}

	public static void freeConnection(Connection con) 
	{
      if (con != null) 
      {
		try 
		{
			 con.close();
		}
		catch(SQLException e) 
		{
			System.err.println("ConnectionManager.freeConnection():SQLException .....");			
			e.printStackTrace();
		}
	  }// end if
	}
	
}

