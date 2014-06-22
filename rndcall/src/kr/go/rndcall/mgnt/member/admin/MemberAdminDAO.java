package kr.go.rndcall.mgnt.member.admin;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import kr.go.rndcall.mgnt.common.DAOBaseException;
import kr.go.rndcall.mgnt.common.BaseSqlDAO;

public class MemberAdminDAO extends BaseSqlDAO {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private String dsname = "jdbc/rndcall";

	public ArrayList<MemberAdminVO> getOrgCD(MemberAdminSearchVO searchVO) throws SQLException, DAOBaseException {
		
		MemberAdminVO vo = null;
		ArrayList<MemberAdminVO> voList = new ArrayList<MemberAdminVO>();
		
		try {
			getConnection(dsname);
			String query = loadQueryString("sql.member.getOrgCD"); 
			
			openPreparedStatementForR(query, false);

			executeQueryForR(searchVO); // 검색조건을 던진다.
			
			while(rs.next()) {
				vo = new MemberAdminVO();
				
				vo.setOrg_cd(rs.getString("CD_DTL_ID"));
				vo.setOrg_nm(rs.getString("CD_DTL_NM"));

				voList.add(vo);
			}
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			return null;
		} finally {
			close();
		}
		return voList;
	}

	public MemberAdminResultVO getUserList(MemberAdminSearchVO searchVO) throws SQLException, DAOBaseException {
		
		MemberAdminResultVO resultVO = new MemberAdminResultVO();
		MemberAdminVO vo = null;
		ArrayList<MemberAdminVO> voList = new ArrayList<MemberAdminVO>();
		
		try {
			getConnection(dsname);
			String query = loadQueryString("sql.member.getUserList"); 
			
			if (!searchVO.getSearch_word().equals("")) {
				
				if (searchVO.getSearch_sel().equals("nm")) {
					query += " AND NAME = '" + searchVO.getSearch_word().replaceAll("'", "") + "' \n";
				} else if (searchVO.getSearch_sel().equals("id")) {
					query += " AND USER_ID = '" + searchVO.getSearch_word().replaceAll("'", "") + "' \n";
				} else if (searchVO.getSearch_sel().equals("")) {
					query += " AND (NAME = '" + searchVO.getSearch_word().replaceAll("'", "") + "' \n";
					query += " OR USER_ID = '" + searchVO.getSearch_word().replaceAll("'", "") + "') \n";
				}
			}
			if (!searchVO.getOrg_cd().equals("")) {
				query += " AND ORG_CD = '" + searchVO.getOrg_cd() + "' \n";
			}
			if (!searchVO.getRoleCD().equals("")) {
				query += " AND AUTH = '" + searchVO.getRoleCD() + "' \n";
			}
			
			openPreparedStatementForR(query, true);

			executeQueryForR(searchVO); // 검색조건을 던진다.
			
			while(rs.next()) {
				vo = new MemberAdminVO();
				
				vo.setAuth_id(rs.getString("AUTH_ID"));
				vo.setLogin_id(rs.getString("USER_ID"));
				vo.setName(rs.getString("NAME"));
				vo.setOrg_cd(rs.getString("ORG_CD"));
				vo.setOrg_nm(rs.getString("ORG_NM"));
				vo.setAttached_nm(rs.getString("ATTACHED_NM"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setMobile(rs.getString("MOBILE"));
				vo.setRoleCD(rs.getString("AUTH"));

				voList.add(vo);
			}
			
			resultVO.setVoList(voList);
			resultVO.setTotRowCount(getTotalCount(query));

		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			return null;
		} finally {
			close();
		}
		return resultVO;
	}

	public MemberAdminResultVO getUserInfo(MemberAdminSearchVO searchVO) throws SQLException, DAOBaseException {
		
		MemberAdminResultVO resultVO = new MemberAdminResultVO();
		MemberAdminVO vo = new MemberAdminVO();
		
		try {
			getConnection(dsname);
			String query = loadQueryString("sql.member.getUserView"); 
			
			query += " AND AUTH_ID = '" + searchVO.getAuth_id() + "' \n";
			
			openPreparedStatementForR(query, true);

			executeQueryForR(searchVO); // 검색조건을 던진다.
			
			if (rs.next()) {
				vo.setAuth_id(rs.getString("AUTH_ID"));
				vo.setLogin_id(rs.getString("USER_ID"));
				vo.setName(rs.getString("NAME"));
				vo.setOrg_cd(rs.getString("ORG_CD"));
				vo.setOrg_nm(rs.getString("ORG_NM"));
				vo.setAttached_nm(rs.getString("ATTACHED_NM"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setMobile(rs.getString("MOBILE"));
				vo.setRoleCD(rs.getString("AUTH"));
				vo.setTel(rs.getString("TEL"));
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

	public String updateUserInfo(MemberAdminVO vo) throws SQLException, DAOBaseException {
		
		String updt = "N";
		
		try {
			getConnection(dsname);
			String query = "UPDATE RNDCALL_AUTH \n" +
						   "   SET AUTH = ? \n" +
						   "     , ORG_CD = ? \n" +
						   "     , ORG_NM = (SELECT CD_DTL_NM FROM RNDCALL_CODE WHERE CD_DTL_ID = ?) \n" +
						   "     , ATTACHED_NM = ? \n" +
						   "     , TEL = ? \n" +
						   "     , EDIT_ID = ? \n" +
						   "     , EDIT_DT = SYSDATE \n" +
						   " WHERE AUTH_ID = ? \n"; 
			
			int param = 1;
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(param++, vo.getRoleCD());
			pstmt.setString(param++, vo.getOrg_cd());
			pstmt.setString(param++, vo.getOrg_cd());
			pstmt.setString(param++, vo.getAttached_nm());
			pstmt.setString(param++, vo.getTel());
			pstmt.setString(param++, vo.getLogin_id());
			pstmt.setString(param++, vo.getAuth_id());
			
			pstmt.execute();
			
			updt = "Y";
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			return null;
		} finally {
			close();
		}
		return updt;
	}

}
