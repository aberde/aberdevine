/** 
 *  1. 일반적인 DAO의 Connection 기능을 구현하며 해당클래스를 상속받아 구현하도록 한다.  
 *  2. 사용된 DataSource 자원은 항상 반환이 되도록 보장해야 한다.
 *             (close 구분은 언제나 finally절에서 처리)  
 *  
 * @author 김종송
 * @since 2007-08-20
 * @version 1.0
 *  1.1 : 2007-08-24 홍길동 aaa함수의 로직에 기존 데이터 존재 여부 확인 추가 (예)
 *  1.2 : 2007-08-24 이철수 bbb 함수 추가 (예)
 * Copyright (C) 2007 by SAMSUNG SDS co.,Ltd. All right reserved. {<-- 이것은 각 컨소시엄별로 작성하세요.}
 */

package kr.go.rndcall.mgnt.common;

import java.util.Locale;
import java.util.Properties;
import java.sql.*;
import cubrid.jdbc.driver.*;

public class BaseSqlDAO 
{
	
	protected Connection conn = null;
	protected PreparedStatement pstmt = null;
	protected CallableStatement cstmt = null;
	protected Statement s = null;
	protected ResultSet rs = null;
	protected Properties props = null;
	protected String orgQuery = "";
	protected boolean bPaging = false;
 
    /** SearchServer JDBC Connection URL */
	protected static final String JDBCon = "jdbc:searchserver";
    /** SearchServer JDBC Driver Classname */
    protected static final String JDBCDriver = "jdbc.searchserver.SSDriver";

	/* DB에 데이터의 구성을 언어별 조회가 가능하도록 되어 있을 때 사용한다.
	 * 일반적인 수량의 조회에서는 languageStatus는 사용할 필요가 없다.
	 * (Default설정은 한국어로 한다.)
	 */
	private int languageStatus = Constants.iKOREAN; //iENGLISH
	private Locale locale = null;
	
	public BaseSqlDAO()
	{
	}
	
	// Query String 로드
	public String loadQueryString(String queryId) {
		QueryLoader ql = QueryLoader.getInstance();
		//System.out.println(queryId);
		return ql.getQueryString(queryId);
	}
	
	public String appendOrderBy(String queryString, String sTarget, String sMethod) {
		StringBuffer sb = new StringBuffer(queryString);
		sb.append(" order by ").append(sTarget).append(" ").append(sMethod);
		
		return sb.toString();
	}
//	public BaseSqlDAO(QueryLoaderVO cvo)
//	{//언어설정을 해준다.
//		setLanguageStatus(cvo);
//	}
	
	
    public Connection getConnectionSG() throws SQLException, DAOBaseException {

        ///////////////////////////////////////////////////////////////////////////
        // Load the SearchServer JDBC driver to allow connection to the database
        ///////////////////////////////////////////////////////////////////////////
        try {
            Class.forName(JDBCDriver);
        } catch (java.lang.ClassNotFoundException e) {
            e.printStackTrace();
        }
        ///////////////////////////////////////////////////////////////////////////
        // Connect to the SearchServer database
        ///////////////////////////////////////////////////////////////////////////
        try {
            conn = DriverManager.getConnection(JDBCon, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fulcrum 환경 setting
        condition(conn);

        return conn;
    }

	public Connection getConnection(String jndiName)
	{
		String sJndiName = jndiName;
		try {
			sJndiName = Configuration.getInstance().get("conf.datasource.name");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		conn = ConnectionManager.getConnection(sJndiName);
		return conn;
	}
	

	public Connection getConnectionMATRIX(String jndiName)
	{
		conn = ConnectionManager.getConnection(jndiName);
		return conn;
	}
	

	public Connection getConnectionForTransaction(String jndiName)
	{
		String sJndiName = jndiName;
		try {
			sJndiName = Configuration.getInstance().get("conf.datasource2.name");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		conn = ConnectionManager.getConnection(sJndiName);
		try { conn.setAutoCommit(false); } catch(SQLException e) { e.printStackTrace(); }
		return conn;
	}
	
	public Connection getConnectionNTIS(String jndiName)
	{
		String sJndiName = jndiName;
		try {
			sJndiName = Configuration.getInstance().get("conf.datasource2.name");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		conn = ConnectionManager.getConnection(sJndiName);
		return conn;
	}
	
	public Connection getConnectionReal(String jndiName)
	{
		String sJndiName = jndiName;
		try {
			sJndiName = Configuration.getInstance().get("conf.datasourceReal.name");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		conn = ConnectionManager.getConnection(sJndiName);
		return conn;
	}
	
	public Connection getConnectionOctagon(String jndiName)
	{
		String sJndiName = jndiName;
		try {
			sJndiName = Configuration.getInstance().get("conf.datasource3.name");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		conn = ConnectionManager.getConnection(sJndiName);
		return conn;
	}
	
 
	/* FULCRUM 전용  Connection
	 * 2009.09.04 Hwang
	 * SearchSearver 컨넥션 연결. 
	 */
	public Connection getConnection_ful(){
		String url = "";
		String user = "";
		String password = "";
		
		try {
			url = Configuration.getInstance().getString("conf.fulcrum.url");
//			user = Configuration.getInstance().getString("conf.clob.user");
//			password = Configuration.getInstance().getString("conf.clob.password");
			
			//DriverManager.registerDriver(new jdbc.searchserver.SSDriver());
			conn = DriverManager.getConnection( url, user, password );
			conn.setAutoCommit(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}

	/* CLOB 전용  Connection
	 * 2008.01.07 Hoon
	 * CLOB 님께서 풀드라이버 써주시면 오락가락 하니 싱글 드라이버로 써준다. 
	 */
	public Connection getConnection_clob(){
		String url = "";
		String user = "";
		String password = "";
		
		try {
			url = Configuration.getInstance().getString("conf.clob.url");
			user = Configuration.getInstance().getString("conf.clob.user");
			password = Configuration.getInstance().getString("conf.clob.password");
			
//			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection( url, user, password );
			conn.setAutoCommit(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}

	/* 연계서버 전용  Connection
	 * 2008.11.04 bkhwang
	 */
	public Connection getConnection_kisti(){
		String url = "";
		String user = "";
		String password = "";
		
		try {
			url = Configuration.getInstance().getString("conf.kisti.url");
			user = Configuration.getInstance().getString("conf.kisti.user");
			password = Configuration.getInstance().getString("conf.kisti.password");
			
//			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection( url, user, password );
			conn.setAutoCommit(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}

	
	/* 연계서버 전용  Connection
	 * 2008.11.04 bkhwang
	 */
	public Connection getConnectionYanLink(){
		String url = "";
		String user = "";
		String password = "";
		
		try {
			url = Configuration.getInstance().getString("conf.kisti.url");
			user = Configuration.getInstance().getString("conf.kisti.user");
			password = Configuration.getInstance().getString("conf.kisti.password");
			
//			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection( url, user, password );
			conn.setAutoCommit(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public void close() {
		close(this.conn, this.pstmt, this.cstmt, this.rs, this.s);
	}
	
	public void close(Connection con, PreparedStatement pstmt, CallableStatement cstmt, ResultSet rs, Statement s)
	{
		try
		{
			if(!con.getAutoCommit()) con.setAutoCommit(true);
			if (rs != null) rs.close();
			if (s != null) s.close();
			if (pstmt != null) pstmt.close();
			if (cstmt != null) cstmt.close();
			if (con != null) con.close();			
		}
		catch(SQLException e)
		{
			System.err.println("Class Name:" + this.getClass().getName());
			System.err.println("Error in close" + e.getMessage());
		}
	}
	
	public void close(Connection con, PreparedStatement pstmt, ResultSet rs)
	{
		try
		{
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		}
		catch(SQLException e)
		{
			System.err.println("Class Name:" + this.getClass().getName());
			System.err.println("Error in close" + e.getMessage());
		}
	}
	
	
	public void close(Connection con, PreparedStatement pstmt)
	{
		try
		{
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();			
		}
		catch(SQLException e)
		{
			System.err.println("Class Name:" + this.getClass().getName());			
			System.err.println("Error in close" + e.getMessage());
		}
	}
	

	public void throwDAOBaseException(Exception e, String methodName) throws DAOBaseException
	{
		// BaseException 설정절차
		// 1. 처음 발생된 Exception을 BaseException으로 감싸서 처리한다.
		// 2. Exception 의 발생원인과 내용을 String[]으로 설정한다. 
		//    [0] Exception Type(Constants class참조) 
		//    [1] 발생위치(현재 Class.method() 명) 
		//    [2] Exception 내용
		// 3. 해당Exception thow
		DAOBaseException be = new DAOBaseException(e);
		String[] args = new String[3];
		args[0] = Constants.DAO_EXCEPTION;
		args[1] = this.getClass().getName() + "." + methodName;
		args[2] = e.toString();
		be.setMessageArgs(args);
		throw be;
	}
	
	public int getLanguageStatus() 
	{
		return languageStatus;	//iKOREAN, iENGLISH등 각 언어별 상수값을 사용하도록 한다.(Constants에 정의)
	}
	

	public void setLanguageStatus(int i) 
	{
		languageStatus = i;	//iKOREAN, iENGLISH등 각 언어별 상수값을 사용하도록 한다.(Constants에 정의)
	}
	
//	public void setLanguageStatus(QueryLoaderVO cvo)
//	{
//		//CommonModuleAction 에서 Constants.LOCALE_KEY 키값으로 locale정보가 저장시킨다.
//		locale = (Locale)cvo.getValue(Constants.LOCALE_KEY);
//		if ( locale.getLanguage().equals(Constants.KOREAN))
//			setLanguageStatus(Constants.iKOREAN);
//		else
//			setLanguageStatus(Constants.iENGLISH);		
//		
//	}

    /**
     * 조회를 위한 PreparedStatement Open
     * 
     * @param String   query
     * @param boolean  paging query 인 경우 true
     * @return void
     * @exception DAOBaseException
     */
    public void openPreparedStatementForR(String query, boolean pagingFlag) throws DAOBaseException {
        try {
        	this.orgQuery = query;
        		
        	bPaging = pagingFlag;
        	
            if (bPaging) {
                query = getPageQueryForPS(query);
                System.out.println("-- openPreparedStatementForR(String query, boolean pagingFlag) query = \n" + query);	                
            }else{
            System.out.println("-- openPreparedStatementForR(String query, boolean pagingFlag) query = \n" + query);
            }
            
			if (this.rs != null) this.rs.close();
			if (this.pstmt != null) this.pstmt.close();

            //this.prepStmt = conn.prepareStatement(query);
            this.pstmt = conn.prepareStatement(query);
        } catch (Exception e) {
            throw new DAOBaseException(e);
        }
    }
    
    /**
     * 조회를 위한 PreparedStatement Open
     * 
     * @param String   query
     * @return void
     * @exception DAOBaseException
     */
    public void openPreparedStatementForCUD(String query) throws DAOBaseException {
        try {
            this.pstmt = conn.prepareStatement(query);
            System.out.println("-- openPreparedStatementForR(String query, boolean pagingFlag) query = \n" + query);

        } catch (Exception e) {
            throw new DAOBaseException(e);
        }
    }
    
    /**
     * CallableStatement Open
     * 
     * @param query
     * @throws DAOBaseException
     */
    public void openPreparedCall(String query) throws DAOBaseException {
        try {
            this.cstmt = conn.prepareCall(query);
            System.out.println("-- openPreparedCall(String query) query = \n" + query);
        } catch (Exception e) {
            throw new DAOBaseException(e);
        }
    }
    
    /**
     * Get Page Query for Prepared Statement
     * 
     * @param subQuery
     * @return String
     */
    public String getPageQueryForPS(String subQuery) {
        StringBuffer query = new StringBuffer();
//         query.append(" SELECT * FROM(")
//              .append("  SELECT /*+ FIRST_ROWS */ PAGE_QUERY.*, ROWNUM RNUM FROM ( ")
//              .append(subQuery + " ) PAGE_QUERY ")
//              .append("  WHERE ROWNUM <= ? ")
//              .append(") ")
//              .append(" WHERE RNUM >= ? ");
        
        query = new StringBuffer(subQuery + "\n limit ?, ?");

        return query.toString();
    }
    
    /**
     * Excecute Query Method
     * 
     * @return void
     * @exception LException
     */
    public void executeQueryForR() throws DAOBaseException {
        try {
        	if (this.rs != null) this.rs.close();
            this.rs = this.pstmt.executeQuery();
        } catch (SQLException e) {
            throw new DAOBaseException(e);
        }
    }
    
    /**
     * Excecute Query Method
     * 
     * @return Result
     * @exception LException
     */
    public int executeQueryForCUD() throws DAOBaseException {
        try {
            return this.pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOBaseException(e);
        }
    }
    
    
    /**
     * Excecute Query Method
     * 
     * @param vo
     * @return void
     * @exception LException
     */
    public void executeQueryForR(BaseSearchVO vo) throws DAOBaseException {
    	// vo.getPagerOffset() + 1 부터  vo.getPagerOffset() + vo.getMaxPageItems() 까지 조회
    	executeQueryForR(vo, 1);
    }
    
//    public void executeQueryForR(SearchVO vo) throws DAOBaseException {
	// vo.getPagerOffset() + 1 부터  vo.getPagerOffset() + vo.getMaxPageItems() 까지 조회
//    	executeQueryForR(vo, 1);
//    }   


    /**
     * Excecute Query Method
     * 
     * @param v0
     * @param idx
     * @return void
     * @exception LException
     */
    public void executeQueryForR(BaseSearchVO vo, int idx) throws DAOBaseException {
    	// vo.getPagerOffset() + 1 ����  vo.getPagerOffset() + vo.getMaxPageItems() 까지 조회
    	try {
	    	if (vo != null && bPaging) {
		        vo.setTotRowCount(getTotalCount(orgQuery));

	        	if (this.rs != null) this.rs.close();
	        	
	        	int iStart = Integer.parseInt(vo.getPagerOffset());
	        	int iEnd = Integer.parseInt(vo.getMaxPageItems());
	        	this.pstmt.setInt(idx++, iStart);
	        	this.pstmt.setInt(idx++, iEnd); 
	    	}
	    	
            this.rs = this.pstmt.executeQuery(); //여기
        } catch (SQLException e) {
            throw new DAOBaseException(e);
        }
    }    
    
    public void executeQueryForRC(BaseSearchVO vo, int idx) throws DAOBaseException {
    	// vo.getPagerOffset() + 1 ����  vo.getPagerOffset() + vo.getMaxPageItems() 까지 조회
    	try {
	    	if (vo != null && bPaging) {
//		        vo.setTotRowCount(getTotalCount(orgQuery));

	        	if (this.rs != null) this.rs.close();
	        	
	        	int iStart = Integer.parseInt(vo.getPagerOffset());
	        	int iEnd = 10; // Integer.parseInt(vo.getMaxPageItems());
	        	this.pstmt.setInt(idx++, iStart);
	        	this.pstmt.setInt(idx++, iEnd); 
	    	}
	    	
            this.rs = this.pstmt.executeQuery(); //여기
        } catch (SQLException e) {
            throw new DAOBaseException(e);
        }
    }    
    
    /**
     * Excecute Query Method
     * 
     * @param v0
     * @param idx
     * @return void
     * @exception LException
     */
    public void executeQueryForR2(BaseSearchVO vo) throws DAOBaseException {
    	// vo.getPagerOffset() + 1 ����  vo.getPagerOffset() + vo.getMaxPageItems() 까지 조회
    	try {
    		vo.setTotRowCount(getTotalCount(orgQuery));
        	if (this.rs != null) this.rs.close();
	    	
            this.rs = this.pstmt.executeQuery(); //여기
        } catch (SQLException e) {
            throw new DAOBaseException(e);
        }
    }    
    
    public void executeQueryForR3(BaseSearchVO vo, String query) throws DAOBaseException {
    	// vo.getPagerOffset() + 1 ����  vo.getPagerOffset() + vo.getMaxPageItems() 까지 조회
    	try {
    		if (orgQuery.equals("")) orgQuery = query;
    		this.pstmt = conn.prepareStatement(query);
    		pstmt.setString(1, "");
    		pstmt.setString(1, "");
    		vo.setTotRowCount(getTotalCount(orgQuery));
        	if (this.rs != null) this.rs.close();
	    	
            this.rs = this.pstmt.executeQuery(); //여기
        } catch (SQLException e) {
            throw new DAOBaseException(e);
        }
    }    
    
    /**
     * Excecute Query Method
     * 
     * @param v0
     * @param idx
     * @return void
     * @exception LException
     */
    public void executeQueryForRForGroup(BaseSearchVO vo, String[] orderByCol) throws DAOBaseException {
    	// vo.getPagerOffset() + 1 부터  vo.getPagerOffset() + vo.getMaxPageItems() 까지 조회
    	try {
	    	if (vo != null && bPaging) {
		        vo.setTotRowCount(getTotalCountForGroup(orgQuery, orderByCol));

	        	if (this.rs != null) this.rs.close();
	        	
	        	int iStart = Integer.parseInt(vo.getPagerOffset());
	        	int iEnd = Integer.parseInt(vo.getPagerOffset()) + Integer.parseInt(vo.getMaxPageItems());
	        	this.pstmt.setInt(1, iStart);
	        	this.pstmt.setInt(2, iEnd);
	    	}
	    	
            this.rs = this.pstmt.executeQuery(); //여기
        } catch (SQLException e) {
            throw new DAOBaseException(e);
        }
    }

    public boolean executeCallableQuery() throws DAOBaseException {
        try {
            return this.cstmt.execute();
        } catch (SQLException e) {
            throw new DAOBaseException(e);
        }
    }
    
        
    /**
     * Total Count
     * 
     * @param query
     * @return int
     * @exception LException
     */
    protected Integer getTotalCount(String query) throws DAOBaseException {
        Integer rsltCnt = new Integer(0);
 
        PreparedStatement in_pstmt = null;
        ResultSet in_rset = null;

        StringBuffer countQuery = new StringBuffer();
        countQuery.append(" SELECT COUNT(*) AS NUM_OF_ROWS FROM (   \n");
        countQuery.append(query + ") A");
        //countQuery.append(" \n )");
        try {
            in_pstmt = conn.prepareStatement(countQuery.toString());

            in_rset = in_pstmt.executeQuery();

            if (in_rset.next()) {
                rsltCnt = new Integer(in_rset.getInt("NUM_OF_ROWS"));
            }
        } catch (SQLException e) {
            throw new DAOBaseException(e);

        } finally {
            try {
                if (in_rset != null)
                    in_rset.close();
            } catch (Exception e) {
                System.out.println("exception occurred during closing result set. " + e);
            }

            try {
                if (in_pstmt != null)
                    in_pstmt.close();
            } catch (Exception e) {
            	System.out.println("exception occurred during closing preparedStatement. " + e);
            }
        }

        return rsltCnt;
    }
    
    /** 이동주추가 (boardlist 의 검색시에 search_text를 받아서 pstmt.setString()을 수행한다)
     * Total Count
     * 
     * @param query
     * @return int
     * @exception LException
     */
    private int getTotalCount(String query, String search_text) throws DAOBaseException {
        int rsltCnt = 0;

        PreparedStatement in_pstmt = null;
        ResultSet in_rset = null;

        StringBuffer countQuery = new StringBuffer();
        countQuery.append(" SELECT COUNT(*) NUM_OF_ROWS FROM (   \n");
        countQuery.append(query + ") A");
        //countQuery.append(" \n )");
        try {
            in_pstmt = conn.prepareStatement(countQuery.toString());
            in_pstmt.setString(1, search_text) ; 
            //System.out.println("-- ## getTotalCount(String query) > countQuery.toString() = " + countQuery.toString() );            
            
            in_rset = in_pstmt.executeQuery();

            if (in_rset.next()) {
                rsltCnt = in_rset.getInt("NUM_OF_ROWS");
            }
        } catch (SQLException e) {
            throw new DAOBaseException(e);

        } finally {
            try {
                if (in_rset != null)
                    in_rset.close();
            } catch (Exception e) {
                System.out.println("exception occurred during closing result set. " + e);
            }

            try {
                if (in_pstmt != null)
                    in_pstmt.close();
            } catch (Exception e) {
            	System.out.println("exception occurred during closing preparedStatement. " + e);
            }
        }

        return rsltCnt;
    }

    public static void condition(Connection con) throws SQLException {
											
		Statement s = con.createStatement();

//        s.executeUpdate("SET CHARACTER_SET 'UTF8C'");
//    	s.executeUpdate("SET CHARACTER_VARIANT 'fultext'");
        s.executeUpdate("SET CHECK_TEXT_STATUS 'FALSE'");

        //etc
        s.executeUpdate("SET COLLATION_SEQUENCE 'KOREA_87'");
        s.executeUpdate("SET FETCH_BUFFER_SIZE 10000");
        s.executeUpdate("SET SEARCH_MEMORY_SIZE 20000");
        s.executeUpdate("SET MAX_EXEC_TIME      600000");
        s.executeUpdate("SET MAX_SEARCH_ROWS 1000");

        // Set Max_Search_rows setting
//        if (!maxPage.equals("999")) {
//            s.executeUpdate("SET MAX_SEARCH_ROWS " + maxPage);
//        }

//        showResults(con,"select * from server_info");

        // Close the Statement, but leave the connection
        s.close();
    }
    
    /**
     * Excecute Query Method
     * 
     * @return Result
     * @exception LException
     */
    public int executeQueryForCUD(String sql, String[] values) throws DAOBaseException {
        try {
        	openPreparedStatementForCUD(sql);
			int param = 0;
			for(int i=0; i<values.length; ++i) {
				pstmt.setString(++param, values[i]);
			}
            return this.pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOBaseException(e);
        }
    }
    
    /**
     * 조회를 위한 PreparedStatement Open 를 개선한 것으로
     * JSP에서 같은 내용의 컬럼을 병합(rowSpan) 하기위해 rowspan 에 들어갈 카운트 숫자가 필요한데,
     * 페이징을 할 경우 페이징된 결과로 count 를 해야한다. 그래서 추가로 만들었음.
     * 
     * @param String   query
     * @param String[] countCol count 할 컬럼들을 넣는다. 
     * @param 
     * @return void
     * @exception DAOBaseException
     */
    public void openPreparedStatementForPSAndCount(String query, String[] countCol) throws DAOBaseException {
        try {
        	this.orgQuery = query;
        	bPaging = true;
            query = getPageQueryForPSAndCount(query, countCol);
            System.out.println("-- openPreparedStatementForPSAndCount(String query, String[] countCol) query = \n" + query);	                
                       
			if (this.rs != null) this.rs.close();
			if (this.pstmt != null) this.pstmt.close();

            this.pstmt = conn.prepareStatement(query);
        } catch (Exception e) {
            throw new DAOBaseException(e);
        }
    }
    
    /**
     * 조회를 위한 PreparedStatement Open 를 개선한 것으로
     * JSP에서 같은 내용의 컬럼을 병합(rowSpan) 하기위해 rowspan 에 들어갈 카운트 숫자 및
     * 그룹 페이징을 위한 것. 제목1 에 파일이 여러개 있는경우 제목을 기준으로 
     * 
     * @param String   query
     * @param String[] countCol count 할 컬럼들을 넣는다. 
     * @param 
     * @return void
     * @exception DAOBaseException
     */
    public void openPreparedStatementGroupCount(String query, String[] countCol, String[] orderByCol) throws DAOBaseException {
        try {
        	this.orgQuery = query;
        	bPaging = true;
            query = getPageQueryGroupCount(query, countCol, orderByCol);                        
            System.out.println("-- openPreparedStatementGroupCount(String query, String[] countCol, String[] orderByCol) query = \n" + query);	                
                       
			if (this.rs != null) this.rs.close();
			if (this.pstmt != null) this.pstmt.close();

            this.pstmt = conn.prepareStatement(query);
        } catch (Exception e) {
            throw new DAOBaseException(e);
        }
    }
    
    /**
     * Get Page Query for Prepared Statement
     * 
     * @param subQuery
     * @return String
     */
    public String getPageQueryForPSAndCount(String subQuery, String[] countCol) {
    	String count_column = "";
    	for(int i=0; i<countCol.length; ++i) {
    		if(i>0) count_column += ",";
    		count_column += " paging_query." + countCol[i];    		 
    	}
        StringBuffer query = new StringBuffer();        
         query.append(" SELECT paging_query.* ")
              .append(" ,count(*) over(partition by "+count_column)
              .append(") equal_count from ( ")         	  
              .append("  SELECT PAGE_QUERY.*, ROWNUM RNUM ")
              .append(" FROM(")
              .append(subQuery + " ) PAGE_QUERY ")
              .append(") paging_query")
              .append(" LIMIT ?, ? order by rnum");         

        return query.toString();
    }
    
    /**
     * Get Page Query for Prepared Statement
     * 
     * @param subQuery
     * @return String
     */
    public String getPageQueryGroupCount(String subQuery, String[] countCol, String[] rankOrderBy) {
    	String count_column = "";
    	String orderBy_column = "";
    	for(int i=0; i<countCol.length; ++i) {
    		if(i>0) count_column += ",";
    		count_column += " page." + countCol[i];    		 
    	}
    	for(int i=0; i<rankOrderBy.length; ++i) {
    		if(i>0) orderBy_column += ",";
    		orderBy_column += " page." + rankOrderBy[i];    		 
    	}
        StringBuffer query = new StringBuffer();        
        query.append("select * \n")
             .append(" from (  \n")
             .append("    select page.* \n")
		     .append("          ,count(*) over(partition by  "+count_column+") equal_count \n")
			 .append("          ,dense_rank() over(order by "+orderBy_column+" desc ) rank_num  \n")
			 .append("       from ( \n")
			 .append(subQuery)
			 .append("    ) page \n")
			 .append("    order by page.reg_dt desc \n")
			 .append(") \n")
			 .append("LIMIT ?, ? \n");
        return query.toString();
    }
    
    /**
     * Total Count
     * 
     * @param query
     * @return int
     * @exception LException
     */
    protected Integer getTotalCountForGroup(String query, String[] rankOrderBy) throws DAOBaseException {
        Integer rsltCnt = new Integer(0);

        PreparedStatement in_pstmt = null;
        ResultSet in_rset = null;
        
        String orderBy_column = "";
        String orderBy_column2 = "";
        for(int i=0; i<rankOrderBy.length; ++i) {
    		if(i>0) { orderBy_column += ","; orderBy_column2 += ","; }
    		orderBy_column += " page." + rankOrderBy[i];
    		orderBy_column2 += rankOrderBy[i];
    	}
        StringBuffer countQuery = new StringBuffer();        
        countQuery.append("select max(rank_num) as NUM_OF_ROWS \n")
             .append(" from (  \n")
             .append("    select page.* \n")
			 .append("          ,dense_rank() over(order by "+orderBy_column+" desc ) rank_num  \n")
			 .append("       from ( \n")
			 .append(query)
			 .append("    ) page \n")
			 .append("    order by page."+orderBy_column2+" desc \n")
			 .append(") \n");
        try {
        	System.out.println("*************** : " + countQuery.toString());
            in_pstmt = conn.prepareStatement(countQuery.toString());

            in_rset = in_pstmt.executeQuery();

            if (in_rset.next()) {
                rsltCnt = new Integer(in_rset.getInt("NUM_OF_ROWS"));
            }
        } catch (SQLException e) {
            throw new DAOBaseException(e);

        } finally {
            try {
                if (in_rset != null)
                    in_rset.close();
            } catch (Exception e) {
                System.out.println("exception occurred during closing result set. " + e);
            }

            try {
                if (in_pstmt != null)
                    in_pstmt.close();
            } catch (Exception e) {
            	System.out.println("exception occurred during closing preparedStatement. " + e);
            }
        }

        return rsltCnt;
    }
    
    // 주민등록번호로 과학기술인등록번호 조회
    public String getJuminToResNum(String number, String name) throws DAOBaseException {
    	String result = "";
    	PreparedStatement in_pstmt = null;
        ResultSet in_rset = null;
        Connection con1 = null;
        String jndiName = "jdbc/oracleNTIS";
        try {
        	String query= "SELECT hurims_man.fun_get_rrno_kistep@ntis_dblink(?, ?) as ssn FROM DUAL ";
			
        	con1 = this.getConnectionNTIS(jndiName);
			in_pstmt = con1.prepareStatement(query);
			in_pstmt.setString(1, number);
			in_pstmt.setString(2, name);
			in_rset = in_pstmt.executeQuery();

			if (in_rset.next()) {
				result = in_rset.getString("ssn");
			}
        } catch (SQLException e) {
            throw new DAOBaseException(e);

        } finally {
            try {
                if (in_rset != null)
                    in_rset.close();
            } catch (Exception e) {
                System.out.println("exception occurred during closing result set. " + e);
            }

            try {
                if (in_pstmt != null)
                    in_pstmt.close();
            } catch (Exception e) {
            	System.out.println("exception occurred during closing preparedStatement. " + e);
            }
            
            try {
                if (con1 != null)
                	con1.close();
            } catch (Exception e) {
            	System.out.println("exception occurred during closing preparedStatement. " + e);
            }
        }
        

		return result;
    }
    
    // 과학기술인등록번호로 주민등록번호 조회
    public String getResNumToJumin(String number, String name) throws DAOBaseException {
    	String result = "";
    	PreparedStatement in_pstmt = null;
        ResultSet in_rset = null;
        Connection con1 = null;
        String jndiName = "jdbc/oracleRND";
        try {
        	
        	String query= "SELECT hurims_man.fun_get_rno_kistep@ntis_dblink(?, ?) as ssn FROM DUAL ";
        	con1 = this.getConnectionNTIS(jndiName);
        	
			in_pstmt = con1.prepareStatement(query);
			in_pstmt.setString(1, number);
			in_pstmt.setString(2, name);
			in_rset = in_pstmt.executeQuery();

			if (in_rset.next()) {
				result = in_rset.getString("ssn");
			}
			
        } catch (SQLException e) {
        	e.printStackTrace();
            throw new DAOBaseException(e);           
        } finally {
            try {
                if (in_rset != null)
                    in_rset.close();
            } catch (Exception e) {
                System.out.println("exception occurred during closing result set. " + e);
            }

            try {
                if (in_pstmt != null)
                    in_pstmt.close();
            } catch (Exception e) {
            	System.out.println("exception occurred during closing preparedStatement. " + e);
            }
            
            try {
                if (con1 != null)
                	con1.close();
            } catch (Exception e) {
            	System.out.println("exception occurred during closing preparedStatement. " + e);
            }
        }

		return result;
    }
}
