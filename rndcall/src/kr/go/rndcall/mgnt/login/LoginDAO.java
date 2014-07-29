package kr.go.rndcall.mgnt.login;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

import kr.go.rndcall.mgnt.common.SSOUtil;
import kr.go.rndcall.mgnt.common.BaseSqlDAO;
import kr.go.rndcall.mgnt.common.DAOBaseException;
import kr.go.rndcall.mgnt.login.LoginVO;
import kr.go.rndcall.mgnt.login.LoginResultVO;

public class LoginDAO extends BaseSqlDAO{

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private String dsname = "jdbc/rndcall";

	/**
	 * 로그인하는 메소드
	 * 회원권한 테이블에서 아이디와 비밀번호를 조회하여 로그인 한다.
	 */
	public LoginResultVO login(LoginVO vo) throws SQLException, DAOBaseException {
				
    	LoginResultVO resultVO = new LoginResultVO();
    	ArrayList voList = new ArrayList();
    	SSOUtil ssoUtil = new SSOUtil();
		String pw = "";

		try {
			
    		getConnection(dsname);
    		
    		try{
    			pw = ssoUtil.encrypt(vo.getPassword());
    		} catch (Exception e){}
    		
    		String query = loadQueryString("sql.login.member_select"); 
			query = new StringBuffer(query).append("\n AND A.USER_ID = '" + vo.getLogin_id() + "' AND A.PASSWORD = '"+pw+"'").toString();
    		
    		pstmt = conn.prepareStatement(query);
	        this.rs = pstmt.executeQuery();
    		
	        logger.debug("### query :: "+query);
	        resultVO.setErrCd("N");
	        
	        while(rs.next()){
	        	vo = new LoginVO();
	        	
	        	vo.setAuth_id(rs.getString("AUTH_ID"));
	        	vo.setLogin_id(rs.getString("USER_ID"));
	        	vo.setName(rs.getString("NAME"));
	        	vo.setRoleCD(rs.getString("AUTH"));
	        	vo.setEmail(rs.getString("EMAIL"));
	        	vo.setMobile(rs.getString("MOBILE"));
	        	vo.setSector(rs.getString("SECTOR"));

		        resultVO.setErrCd("Y");
	        }
	        
	        voList.add(vo);
	        resultVO.setVoList(voList);
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
	        resultVO.setErrCd("N");
			return null;
		} finally {
			close();
		}

		return resultVO;
	}	
	
	/**
	 * 로그아웃
	 * 
	 */
	public LoginVO logout(LoginVO vo) throws SQLException, DAOBaseException {
				
		try {
			
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return vo;
	}	
	
}