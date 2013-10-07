package com.hanjin.cmm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Properties;

public class DBConnection {

	// ORACLE DB ����
	public String ORACLE_DB_CLASSFORNAME;
	public String ORACLE_DB_URL;
	public String ORACLE_DB_ID;
	public String ORACLE_DB_PW;
	
	/**
     * ������Ƽ ���� ����.
     * @param dep �ܰ�(2: 2�ܰ�, 9: 9�ܰ�)
     */
    public DBConnection(int dep) throws Exception {
        Properties prop = (new Property()).getInstance();
        
        if ( dep == 2 ) {
        	ORACLE_DB_CLASSFORNAME = prop.getProperty("ORACLE_DB_CLASSFORNAME_2");
        	ORACLE_DB_URL = prop.getProperty("ORACLE_DB_URL_2");
        	ORACLE_DB_ID = prop.getProperty("ORACLE_DB_ID_2");
        	ORACLE_DB_PW = prop.getProperty("ORACLE_DB_PW_2");
        } else if ( dep == 9 ) {
	        ORACLE_DB_CLASSFORNAME = prop.getProperty("ORACLE_DB_CLASSFORNAME_9");
	        ORACLE_DB_URL = prop.getProperty("ORACLE_DB_URL_9");
	        ORACLE_DB_ID = prop.getProperty("ORACLE_DB_ID_9");
	        ORACLE_DB_PW = prop.getProperty("ORACLE_DB_PW_9");
        }
    }
	
    /**
	 * ORACLE �������� ������ ��������.
	 * @param sql select Query
	 * @return ���
	 */
	public HashMap<String, String> getData(String sql) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(ORACLE_DB_CLASSFORNAME);
			conn = DriverManager.getConnection(ORACLE_DB_URL, ORACLE_DB_ID, ORACLE_DB_PW);
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			if (rs.next()) {
				for ( int i = 0; i < rsmd.getColumnCount(); i++ ) {
					map.put(rsmd.getColumnName(i + 1), rs.getString(i + 1));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
		    try {
		        if ( rs != null ) {
		            rs.close();
    			}
		        if ( pstmt != null ) {
	                pstmt.close();
		        }
		        if ( conn != null ) {
		            conn.close();
		        }
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return map;
	}
}
