package kr.go.rndcall.mgnt.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Reader;
import java.net.URLEncoder;
import java.net.URLDecoder;

import org.apache.log4j.Logger;

import kr.go.rndcall.mgnt.common.DAOBaseException;
import kr.go.rndcall.mgnt.common.SSOUtil;
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.login.LoginResultVO;
import kr.go.rndcall.mgnt.login.LoginVO;
import kr.go.rndcall.mgnt.common.BaseSqlDAO;

public class MemberDAO extends BaseSqlDAO {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private String dsname = "jdbc/rndcall";

	public String getIdCheck(String login_id) throws SQLException, DAOBaseException {
		
		String cnt ="";
		try {
			getConnection(dsname);
			String query = loadQueryString("sql.member.getIdCheck"); 
			
			query = new StringBuffer(query).append(" WHERE USER_ID = '").append(login_id).append("' ").toString();			
			openPreparedStatementForR(query,  false);

			logger.debug("pstmt.execute()...");

			rs = pstmt.executeQuery(); // 
			
			while(rs.next()) {
				cnt = rs.getString("CNT");
			}
			

		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			return null;
		} finally {
			close();
		}
		return cnt;
	}
	
	public String getInsert(MemberVO vo) throws SQLException, DAOBaseException {
		
		SSOUtil ssoUtil = new SSOUtil();
		String query =  "";
		int param =1;
		String ssoPw = "";
		String ins = "false";
		
		try {
			ssoPw = ssoUtil.encrypt(vo.getPassword());			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			if (getIdCheck(vo.getLogin_id()).equals("0")) {
				getConnection(dsname);		
			
				query = loadQueryString("sql.member.getInsert"); 
				param =1;
										
				pstmt = conn.prepareStatement(query);
				pstmt.setString(param++, vo.getLogin_id());
				pstmt.setString(param++, vo.getName());
				pstmt.setString(param++, vo.getOrg_cd());
				pstmt.setString(param++, vo.getOrg_cd());
				pstmt.setString(param++, vo.getEmail());
				pstmt.setString(param++, vo.getMobile());
				pstmt.setString(param++, vo.getLogin_id());
				pstmt.setString(param++, ssoPw);
				pstmt.setString(param++, "0000A");
				pstmt.setString(param++, vo.getIdFindQuestion());
				pstmt.setString(param++, vo.getIdFindAnswer());
				pstmt.setString(param++, vo.getPwFindQuestion());
				pstmt.setString(param++, vo.getPwFindAnswer());
				pstmt.setString(param++, vo.getSector());
							
				pstmt.execute();
				pstmt.close();
				ins="true";
			}
				
		}catch(SQLException e){
			ins = "false";
			e.printStackTrace();
		} finally {
			close();
		}
		
		return ins;
	}
	
	public LoginResultVO getMemberInfo(String id) throws SQLException {
     	
    	//LoginVO vo = null;
		LoginVO vo = new LoginVO();
        LoginResultVO resultVO = new LoginResultVO();
        ArrayList <LoginVO> voList = new ArrayList <LoginVO> ();
        
        String query = "";

        try {
        	getConnection(dsname);
        	
			query = loadQueryString("sql.member.getUserView"); 

			query += " AND A.USER_ID = '" + id + "' \n";
			pstmt = conn.prepareStatement(query);

			logger.debug("pstmt.execute()...");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new LoginVO();
				vo.setAuth_id(rs.getString("AUTH_ID"));
				vo.setLogin_id(rs.getString("USER_ID"));
				vo.setName(rs.getString("NAME"));
				vo.setOrg_cd(rs.getString("ORG_CD"));
				vo.setOrg_nm(rs.getString("ORG_NM"));
				vo.setAttached_nm(rs.getString("ATTACHED_NM"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setMobile(rs.getString("MOBILE"));
				vo.setReg_id(rs.getString("REG_ID"));
				vo.setReg_dt(rs.getString("REG_DT"));
				vo.setRoleCD(rs.getString("AUTH"));

	            voList.add(vo);
			}
	        resultVO.setVoList(voList);
        	resultVO.setVo(vo);
		} catch (Exception e) {
			return null;
        } finally {
        	close();
        }
        return resultVO;
    }
	
	public MemberResultVO getUserInfo(String id) throws SQLException {
     	
    	//LoginVO vo = null;
		MemberVO vo = new MemberVO();
		MemberResultVO resultVO = new MemberResultVO();
        ArrayList <MemberVO> voList = new ArrayList <MemberVO> ();
        SSOUtil ssoUtil = new SSOUtil();
		String ssoPw = "";
        String query = "";

        try {
        	getConnection(dsname);
        	
			query = loadQueryString("sql.member.getUserView"); 

			query += " AND A.USER_ID = '" + id + "' \n";
			pstmt = conn.prepareStatement(query);

			logger.debug("pstmt.execute()...");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new MemberVO();
				vo.setAuth_id(rs.getString("AUTH_ID"));
				vo.setLogin_id(rs.getString("USER_ID"));
				vo.setName(rs.getString("NAME"));
				vo.setOrg_cd(rs.getString("ORG_CD"));
				vo.setOrg_nm(rs.getString("ORG_NM"));
				vo.setAttached_nm(rs.getString("ATTACHED_NM"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setMobile(rs.getString("MOBILE"));
				vo.setReg_id(rs.getString("REG_ID"));
				vo.setReg_dt(rs.getString("REG_DT"));
				vo.setRoleCD(rs.getString("AUTH"));
				vo.setPassword(rs.getString("PASSWORD"));
				try {
					ssoPw = ssoUtil.decrypt(vo.getPassword());			
				}catch(Exception e){
					e.printStackTrace();
				}
				vo.setPassword(ssoPw);
				vo.setRe_password(ssoPw);
				vo.setIdFindQuestion(rs.getString("IDFINDQUESTION"));
			    vo.setIdFindAnswer(rs.getString("IDFINDANSWER"));
			    vo.setPwFindQuestion(rs.getString("PWFINDQUESTION"));
			    vo.setPwFindAnswer(rs.getString("PWFINDANSWER"));
			    vo.setSector(rs.getString("SECTOR"));
				
	            voList.add(vo);
			}
	        resultVO.setVoList(voList);
        	resultVO.setVo(vo);
		} catch (Exception e) {
			return null;
        } finally {
        	close();
        }
        return resultVO;
    }
	
	public String getUpdate(MemberVO vo) throws SQLException, DAOBaseException {
		
		SSOUtil ssoUtil = new SSOUtil();
		String query =  "";
		int param =1;
		String ssoPw = "";
		String ins = "false";
		
		try {
			ssoPw = ssoUtil.encrypt(vo.getPassword());			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			getConnection(dsname);		
			
			//query = loadQueryString("sql.member.getUpdate"); 
			query  = " UPDATE RNDCALL_AUTH SET ";
			if (!vo.getLogin_id().equals("")) {
				query += " 	  USER_ID = ?, ";
			}
			if(!vo.getPassword().equals("")) {
				query += " 	  PASSWORD = ?, ";
			}
			query += " 	  EMAIL = ?, ";
			query += " 	  MOBILE = ?, ";
			query += " 	  EDIT_ID = ?, ";
			query += " 	  EDIT_DT = SYSDATE, ";
			query += " 	  SECTOR = ?, ";
			query += " 	  IDFINDQUESTION = ?, ";
			query += " 	  IDFINDANSWER = ?, ";
			query += " 	  PWFINDQUESTION = ?, ";
			query += " 	  PWFINDANSWER = ? ";
			query += " WHERE USER_ID = ? ";
				
			param =1;
		
			System.out.println("update query="+query);
			
			pstmt = conn.prepareStatement(query);
			if (!vo.getLogin_id().equals("")) {
				pstmt.setString(param++, vo.getLogin_id());
			}
			
			if(!vo.getPassword().equals("")) {
				pstmt.setString(param++, ssoPw);
			}
			pstmt.setString(param++, vo.getEmail());
			pstmt.setString(param++, vo.getMobile());
			pstmt.setString(param++, vo.getLogin_id());
			pstmt.setString(param++, vo.getSector());
			pstmt.setString(param++, vo.getIdFindQuestion());
			pstmt.setString(param++, vo.getIdFindAnswer());
			pstmt.setString(param++, vo.getPwFindQuestion());
			pstmt.setString(param++, vo.getPwFindAnswer());
			pstmt.setString(param++, vo.getLogin_id());
			
			pstmt.executeUpdate();
			pstmt.close();				
				
		}catch(SQLException e){
			ins = "false";
			e.printStackTrace();
		} finally {
			ins="true";
//			conn.commit();
			close();
		}
		
		return ins;
	}
	
	public MemberResultVO idFind(MemberVO vo) throws SQLException, DAOBaseException {
		
		MemberResultVO resultVO = new MemberResultVO();
		ArrayList voList = new ArrayList();
		try {
			getConnection(dsname);
			String query = loadQueryString("sql.member.idFind"); 
			
			pstmt = conn.prepareStatement(query);
			System.out.println("query:::"+query);
			System.out.println("vo.getIdFindQuestion():::"+vo.getIdFindQuestion());
			System.out.println("vo.getIdFindAnswer():::"+vo.getIdFindAnswer());
			
			pstmt.setString(1, vo.getIdFindQuestion());
			pstmt.setString(2, vo.getIdFindAnswer());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				vo = new MemberVO();
				vo.setLogin_id(rs.getString("USER_ID"));
				voList.add(vo);
			}
			resultVO.setVoList(voList);

		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			return null;
		} finally {
			close();
		}
		return resultVO;
	}
	
	public MemberResultVO pwFind(MemberVO vo) throws SQLException, DAOBaseException {
		
		MemberResultVO resultVO = new MemberResultVO();
		SSOUtil ssoUtil = new SSOUtil();

		try {
			getConnection(dsname);
			String query = loadQueryString("sql.member.pwFind"); 
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getLogin_id());
			pstmt.setString(2, vo.getPwFindQuestion());
			pstmt.setString(3, vo.getPwFindAnswer());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				vo.setPassword(ssoUtil.decrypt(rs.getString("PASSWORD")));
			}
			resultVO.setVo(vo);

		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			return null;
		} finally {
			close();
		}
		return resultVO;
	}

	public int getOldDocCnt(MemberVO vo) throws SQLException, DAOBaseException {
		
		int cnt = 0;
		
		try {
			getConnection(dsname);
			String query = loadQueryString("sql.member.getOldDocCnt"); 
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				cnt = rs.getInt("CNT");
			}

		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			return 0;
		} finally {
			close();
		}
		return cnt;
	}

	public MemberResultVO getOldDocList(MemberVO vo) throws SQLException, DAOBaseException {
		
		MemberResultVO resultVO = new MemberResultVO();
		Reader content_clob = null;
		ArrayList voList = new ArrayList();
		MemberVO dvo = new MemberVO();

		try {
			getConnection(dsname);
			String query = loadQueryString("sql.member.getOldDocList"); 
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				dvo = new MemberVO();
				
				dvo.setSeq(rs.getString("SEQ"));
				dvo.setTitle(rs.getString("TITLE"));
//				content_clob = rs.getCharacterStream("CONTENTS");
//				dvo.setContents(Util.streamToBuf(content_clob).toString());
				dvo.setContents(rs.getString("CONTENTS"));
				dvo.setMobile(rs.getString("CELL_NUMBER"));
				dvo.setEmail(rs.getString("EMAIL"));
				dvo.setName(rs.getString("REG_NM"));
				dvo.setReg_dt(rs.getString("REG_DT"));
				
				voList.add(dvo);
			}
			resultVO.setVoList(voList);

		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			return null;
		} finally {
			close();
		}
		return resultVO;
	}

	public String getOldDocSave(MemberVO vo) throws SQLException, DAOBaseException {
		String result = "";
		
		try {
			getConnection(dsname);
			
			if (!vo.getSeq().equals("")) {
				String query = "UPDATE RNDCALL_BOARD_QUESTION \n" +
							   "   SET REG_ID = '" + vo.getLogin_id() + "' \n" +
							   " WHERE SEQ IN (" + vo.getSeq() + ") \n";
				
				pstmt = conn.prepareStatement(query);
				pstmt.execute();
				
				result = "true";
			}
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			return result;
		} finally {
			close();
		}
		return result;
	}
}
