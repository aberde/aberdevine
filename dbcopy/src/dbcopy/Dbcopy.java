package dbcopy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

public class Dbcopy {

    // MS-SQL DB 정보
    static String MSSQL_DB_CLASSFORNAME;
	static String MSSQL_DB_URL;
	static String MSSQL_DB_ID;
	static String MSSQL_DB_PW;
	
	// ORACLE DB 정보
	static String ORACLE_DB_CLASSFORNAME;
	static String ORACLE_DB_URL;
	static String ORACLE_DB_ID;
	static String ORACLE_DB_PW;
	
	// 백업여부
	static boolean BACKUP_ADDJOB;
	static boolean BACKUP_DEPTMASTER;
	static boolean BACKUP_USERMASTER;

	// 프로퍼티 셋팅.
	static Properties prop = null;
	static String today = "";

	public static void main(String[] args) {
	    getinstanceProp();
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        today = sdf.format(new Date());
	    
        // 임시 테스트용.
//        test();
        // 겸직부서정보 동기화
//		setAddJob();
		// 부서정보 동기화
//		setDeptMaster();
		// 직원정보 동기화
//		setUserMaster();
        
        // ORACLE 접속테스트
        oracleConnectionTest();
        // MSSQL 접속테스트
//        mssqlConnectionTest();
	}

    /**
     * 프로퍼티 파일 셋팅.
    */
    private static void getinstanceProp() {
        try {
            Properties prop = (new Property()).getInstance();
	        
	        MSSQL_DB_CLASSFORNAME = prop.getProperty("MSSQL_DB_CLASSFORNAME");
	        MSSQL_DB_URL = prop.getProperty("MSSQL_DB_URL");
	        MSSQL_DB_ID = prop.getProperty("MSSQL_DB_ID");
	        MSSQL_DB_PW = prop.getProperty("MSSQL_DB_PW");

	        ORACLE_DB_CLASSFORNAME = prop.getProperty("ORACLE_DB_CLASSFORNAME");
	        ORACLE_DB_URL = prop.getProperty("ORACLE_DB_URL");
	        ORACLE_DB_ID = prop.getProperty("ORACLE_DB_ID");
	        ORACLE_DB_PW = prop.getProperty("ORACLE_DB_PW");

	        BACKUP_ADDJOB = "true".equals(prop.getProperty("BACKUP_ADDJOB"));
	        BACKUP_DEPTMASTER = "true".equals(prop.getProperty("BACKUP_DEPTMASTER"));
	        BACKUP_USERMASTER = "true".equals(prop.getProperty("BACKUP_USERMASTER"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 직원정보 동기화
    */
    private static void setUserMaster() {
        // ##############################################################################
		// ## 직원정보
		// ###############
		System.out.println("[" + today + "] TOP_COMM.VWTBLUSERMASTER start");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                  \n");
		sql.append("     USERID              \n");
		sql.append("     , USERNM            \n");
		sql.append("     , MAILID            \n");
		sql.append("     , DEPTID            \n");
		sql.append("     , DEPTNM            \n");
		sql.append("     , OFFICEPHONE       \n");
		sql.append("     , HOMEPHONE         \n");
		sql.append("     , FAX               \n");
		sql.append("     , COMPID            \n");
		sql.append("     , COMPNM            \n");
		sql.append("     , MOBILE            \n");
		sql.append("     , POSTALCODE        \n");
		sql.append("     , ADDRESS           \n");
		sql.append("     , PHOTOFILE         \n");
		sql.append("     , SIGNFILE          \n");
		sql.append("     , SECULEVEL         \n");
		sql.append("     , TITLECD           \n");
		sql.append("     , TITLENM           \n");
		sql.append("     , TITLESORT         \n");
		sql.append("     , DEGREECD          \n");
		sql.append("     , DEGREENM          \n");
		sql.append("     , RESPONSIBILITYCD  \n");
		sql.append("     , RESPONSIBILITYNM  \n");
		sql.append("     , ROLESTRING        \n");
		sql.append("     , ADSPATH           \n");
		sql.append("     , UPDATEDT          \n");
		sql.append("     , LASTNM            \n");
		sql.append("     , FIRSTNM           \n");
		sql.append("     , MIDDLENM          \n");
		sql.append("     , INITIALS          \n");
		sql.append("     , JOBDESC           \n");
		sql.append("     , LOCATION          \n");
		sql.append("     , COUNTRY           \n");
		sql.append("     , SEX               \n");
		sql.append("     , BIRTHDT           \n");
		sql.append("     , LOCATIONNM        \n");
		sql.append("     , MARRIED           \n");
		sql.append("     , DEPTADMIN         \n");
		sql.append("     , DIFCOMPANY        \n");
		sql.append("     , ROOTDEPT          \n");
		sql.append("     , DEPTTEAMNM        \n");
		sql.append("     , DEPTTEAM          \n");
		sql.append("     , ADDJOB            \n");
		sql.append("     , BUJAEINFO         \n");
		sql.append("     , BUJAEINFOENDDATE  \n");
		sql.append("     , SIRNM             \n");
		sql.append("     , PUBLICPHOTO       \n");
		sql.append("     , UPDATEEXPIRES     \n");
		sql.append("     , JOBCODE           \n");
		sql.append("     , SIP               \n");
		sql.append("     , MDB               \n");
		sql.append("     , OWAFLAG           \n");
		sql.append("     , EMP_NO            \n");
		sql.append("     , USERTYPE          \n");
		sql.append("     , USERGB            \n");
		sql.append("     , ALERTYN           \n");
		sql.append(" FROM EZHRMASTER.DBO.VWTBLUSERMASTER ");
		
		setData("TOP_COMM.VWTBLUSERMASTER", getData(sql.toString()), BACKUP_USERMASTER);
		
		System.out.println("[" + today + "] TOP_COMM.VWTBLUSERMASTER end");
		// ##############################################################################
    }

    /**
     * 부서정보 동기화
    */
    private static void setDeptMaster() {
        // ##############################################################################
		// ## 부서정보
		// ##############################################################################
		System.out.println("[" + today + "] TOP_COMM.VWTBLDEPTMASTER start");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                 \n");
		sql.append("     DEPTID             \n");
		sql.append("     , DEPTNM           \n");
		sql.append("     , DEPTFULLNM       \n");
		sql.append("     , SIMPLENM         \n");
		sql.append("     , DEPTGB           \n");
		sql.append("     , USEFLAG          \n");
		sql.append("     , COMPID           \n");
		sql.append("     , COMPNM           \n");
		sql.append("     , PARENTDEPTID     \n");
		sql.append("     , DEPTLEVEL        \n");
		sql.append("     , DEPTSORT         \n");
		sql.append("     , DOCDEPTID        \n");
		sql.append("     , DEPTMASTERID     \n");
		sql.append("     , UPN              \n");
		sql.append("     , ADFLAG           \n");
		sql.append("     , ADSPATH          \n");
		sql.append("     , UPDATEDT         \n");
		sql.append("     , DETAILADDRESS    \n");
		sql.append("     , LOCATION         \n");
		sql.append("     , COUNTRY          \n");
		sql.append("     , POSTALCODE       \n");
		sql.append("     , DESCRIPTION      \n");
		sql.append("     , DEPTPHONE        \n");
		sql.append("     , DEPTRECEIPT      \n");
		sql.append("     , DEPTFAX          \n");
		sql.append("     , SENDNM           \n");
		sql.append("     , ROOTDEPT         \n");
		sql.append("     , DEPTTEAM         \n");
		sql.append("     , SIGNFILE         \n");
		sql.append("     , MAILID           \n");
		sql.append("     , SIMSADEPT        \n");
		sql.append("     , STARTSUMMERTIME  \n");
		sql.append("     , ENDSUMMERTIME    \n");
		sql.append("     , CITY             \n");
		sql.append(" FROM EZHRMASTER.DBO.VWTBLDEPTMASTER ");
		
		setData("TOP_COMM.VWTBLDEPTMASTER", getData(sql.toString()), BACKUP_DEPTMASTER);
		
		System.out.println("[" + today + "] TOP_COMM.VWTBLDEPTMASTER end");
		// ##############################################################################
    }

    /**
     * 겸직부서정보 동기화
    */
    private static void setAddJob() {
        // ##############################################################################
		// ## 겸직부서정보
		// ##############################################################################
		System.out.println("[" + today + "] TOP_COMM.VWTBLADDJOB start");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT            \n");
		sql.append("     USERID        \n");
        sql.append("     , DEPTID      \n");
        sql.append("     , DEPTNM      \n");
        sql.append("     , JOBCD       \n");
        sql.append("     , USEFLAG     \n");
        sql.append("     , EXPR4       \n");
        sql.append("     , JIKCHEKCD   \n");
        sql.append("     , JIKCHEKNM   \n");
        sql.append("     , JOBDESC     \n");
        sql.append("     , ADFLAG      \n");
        sql.append("     , EDMFLAG     \n");
        sql.append("     , INTERFLAG   \n");
        sql.append("     , UPDATEDT    \n");
        sql.append("     , TITLECD     \n");
        sql.append("     , TITLENM     \n");
        sql.append("     , TITLESORT   \n");
        sql.append("     , ROOTDEPT    \n");
        sql.append("     , TEAMDEPT    \n");
		sql.append(" FROM EZHRMASTER.DBO.VWTBLADDJOB ");
		
		setData("TOP_COMM.VWTBLADDJOB", getData(sql.toString()), BACKUP_ADDJOB);
		
		System.out.println("[" + today + "] TOP_COMM.VWTBLADDJOB end");
		// ##############################################################################
    }

    /**
     * 임시 테스트용
    */
    private static void test() {
        // ##############################################################################
        // ## 임시 테스트용
        // ##############################################################################
	    System.out.println("[" + today + "] TEMP_20130903 start");
	    
	    StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append("     A ");
		sql.append("     , B ");
		sql.append("     , C ");
		sql.append(" FROM TEMP_20130903 ");
		
		setData("TEMP_20130903", getData(sql.toString()), BACKUP_USERMASTER);

		System.out.println("[" + today + "] TEMP_20130903 end");
		// ##############################################################################
    }

	/**
	 * MS-SQL 서버에서 데이터 가져오기.
	 * @param sql select Query
	 * @return 목록
	 */
	private static ArrayList<HashMap<String, String>> getData(String sql) {
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(MSSQL_DB_CLASSFORNAME);
			conn = DriverManager.getConnection(MSSQL_DB_URL, MSSQL_DB_ID, MSSQL_DB_PW);
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); 
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			while (rs.next()) {
				HashMap<String, String> map = new HashMap<String, String>();
				for ( int i = 0; i < rsmd.getColumnCount(); i++ ) {
					map.put(rsmd.getColumnName(i + 1), rs.getString(i + 1));
				}
				
				list.add(map);
			}
			
            System.out.println("[" + today + "] SELECT_TABLE : " + list.size());
		} catch (Exception e) {
			e.printStackTrace();
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
			}
		}
		
		return list;
	}
	
	/**
	 * ORACLE 서버에 데이터 등록.
	 * @param tableName 테이블명
	 * @param list 데이터
	 * @param backup 백업여부(테이블명_오늘일자[yyyyMMdd])
	 */
	private static void setData(String tableName, ArrayList<HashMap<String, String>> list, boolean backup) {
	    if ( list.size() > 0 ) {  // 데이터 존재시에만 실행.
    	    Connection conn = null;
    		PreparedStatement pstmt1 = null;
    		PreparedStatement pstmt2 = null;
    		PreparedStatement pstmt3 = null;
    		
    		try {
    			Class.forName(ORACLE_DB_CLASSFORNAME);
    			conn = DriverManager.getConnection(ORACLE_DB_URL, ORACLE_DB_ID, ORACLE_DB_PW);
    			conn.setAutoCommit(false);
    			
    			String[] columnNames = getColumnNames(list);
    			
    			// ###########################################################################
    			// ## 백업 테이블 생성
    			// ###########################################################################
    			if ( backup ) {  // 테이블 백업시.
        			// 임시 테이블 생성하여 백업
        			StringBuffer sql1 = new StringBuffer();
        			sql1.append(" CREATE TABLE " + tableName + "_" + today + " AS ");
        			sql1.append(" SELECT ");
        			sql1.append(arrayToString(columnNames, ","));
        			sql1.append(" FROM " + tableName);
        			
        			pstmt1 = conn.prepareStatement(sql1.toString());
        	        int tempTableCnt = pstmt1.executeUpdate();
        	        
        	        System.out.println("[" + today + "] TEMP_TABLE " + tableName + "_" + today + " : " + tempTableCnt);
    			}
    			// ###########################################################################
    			
    			// ###########################################################################
    			// ## 테이블 데이터 삭제
    			// ###########################################################################
    	        StringBuffer sql2 = new StringBuffer();
    	        sql2.append(" DELETE FROM " + tableName);
    	        
    	        pstmt2 = conn.prepareStatement(sql2.toString());
    	        int deleteCnt = pstmt2.executeUpdate();
    	        
    	        System.out.println("[" + today + "] DELETE_TABLE " + tableName + " : " + deleteCnt);
    	        // ###########################################################################
    	        
    	        // ###########################################################################
    	        // ## 테이블 데이터 등록
    	        // ###########################################################################
    	        StringBuffer sql3 = new StringBuffer();
    	        sql3.append(" INSERT INTO " + tableName + " ( ");
    	        sql3.append(arrayToString(columnNames, ","));
    	        sql3.append(" ) VALUES ( ");
    	        sql3.append(getColumnValues(columnNames, ","));
    	        sql3.append(" ) ");
    	        
    	        pstmt3 = conn.prepareStatement(sql3.toString());
    	        
    	        for ( int i = 0; i < list.size(); i++ ) {
    	        	HashMap<String, String> map = list.get(i);
    				
    				for ( int j = 0; j < columnNames.length; j++ ) {
    				    pstmt3.setString(j + 1, map.get(columnNames[j]));
    				}
    				pstmt3.addBatch();
    	        }
    	        int[] insertCnt = pstmt3.executeBatch();
    	        
    	        System.out.println("[" + today + "] INSERT_TABLE " + tableName + " : " + insertCnt.length);
    	        // ###########################################################################
    		} catch (Exception e) {
    		    try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
    			e.printStackTrace();
    		} finally {
    			try {
    			    if ( conn != null ) {
    			        conn.commit();
    			    }
    			    if ( pstmt1 != null ) {
    			        pstmt1.close();
                    }
    			    if ( pstmt2 != null ) {
    			        pstmt2.close();
    			    }
    			    if ( pstmt3 != null ) {
    			        pstmt3.close();
    			    }
                    if ( conn != null ) {
                        conn.close();
                    }
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
		}
	}

	/**
	 * HashMap에서 컬럼명 추출
	 * @param list
	 * @return 
	 */
	private static String[] getColumnNames(ArrayList<HashMap<String, String>> list) {
		String[] columnNames = null;
		
		if ( list.size() > 0 ) {
			HashMap<String, String> map = list.get(0);
			
			Set<String> keySet = map.keySet();
			columnNames = keySet.toArray(new String[0]);
		}
		
		return columnNames;
	}
	
	/**
	 * 배열된 문자를 구분자 구분으로 문자열로 반환
	 * @param str 배열 문자
	 * @param sep 구분자
	 * @return
	 */
	private static String arrayToString(String[] str, String sep) {
		String ret = "";
		
		if ( str != null ) {
			for ( int i = 0; i < str.length; i++ ) {
				ret += sep + str[i];
			}
			ret = ret.substring(sep.length());
		}
		
		return ret;
	}
	
	/**
	 * 컬럼수에 해당하는 입력 값 필드 생성.
	 * @param str 컬럼명배열
	 * @param sep 구분자
	 * @return
	 */
	private static String getColumnValues(String[] str, String sep) {
		String ret = "";
		
		if ( str != null ) {
			for ( int i = 0; i < str.length; i++ ) {
				ret += sep + "?";
			}
			ret = ret.substring(sep.length());
		}
		
		return ret;
	}

	/**
	 * 임시데이터 등록
	 * @param tableName
	 * @param list
	 * @param backup
	 */
	private static void setData2(String tableName, ArrayList<HashMap<String, String>> list, boolean backup) {
        Connection conn = null;
        PreparedStatement pstmt3 = null;
        
        try {
            Class.forName(ORACLE_DB_CLASSFORNAME);
            conn = DriverManager.getConnection(ORACLE_DB_URL, ORACLE_DB_ID, ORACLE_DB_PW);
            conn.setAutoCommit(false);
            
            // ###########################################################################
            // ## 테이블 데이터 등록
            // ###########################################################################
            StringBuffer sql3 = new StringBuffer();
            sql3.append(" INSERT INTO " + tableName + " ( ");
            sql3.append(" A, B, C ");
            sql3.append(" ) VALUES ( ");
            sql3.append(" ?, ?, ?");
            sql3.append(" ) ");
            
            pstmt3 = conn.prepareStatement(sql3.toString());
            
            for ( int i = 0; i < 60000; i++ ) {
                for ( int j = 0; j < 3; j++ ) {
                    pstmt3.setString(j + 1, i + "");
                }
                pstmt3.addBatch();
            }
            int[] insertCnt = pstmt3.executeBatch();
            
            System.out.println("[" + today + "] INSERT_TABLE " + tableName + " : " + insertCnt.length);
            // ###########################################################################
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if ( conn != null ) {
                    conn.commit();
                }
                if ( pstmt3 != null ) {
                    pstmt3.close();
                }
                if ( conn != null ) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	/** 
	 * ORACLE DB 접속상태 확인.
	 */
	private static void oracleConnectionTest() {
	    Connection conn = null;
        
        try {
        	Class.forName(ORACLE_DB_CLASSFORNAME);
            conn = DriverManager.getConnection(ORACLE_DB_URL, ORACLE_DB_ID, ORACLE_DB_PW);
            System.out.println("[" + today + "] ORACLE Connection Test");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if ( conn != null ) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	/** 
	 * MSSQL DB 접속상태 확인.
	 */
	private static void mssqlConnectionTest() {
        Connection conn = null;
        
        try {
        	Class.forName(MSSQL_DB_CLASSFORNAME);
            conn = DriverManager.getConnection(MSSQL_DB_URL, MSSQL_DB_ID, MSSQL_DB_PW);
            
            System.out.println("[" + today + "] MSSQL Connection Test");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if ( conn != null ) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
}

