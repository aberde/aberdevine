package kr.go.rndcall.mgnt.category;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import kr.go.rndcall.mgnt.common.BaseSqlDAO;
import kr.go.rndcall.mgnt.common.DAOBaseException;
import kr.go.rndcall.mgnt.category.CategorySearchVO;
import kr.go.rndcall.mgnt.category.CategoryVO;

public class CategoryDAO extends BaseSqlDAO {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private String dsname = "jdbc/oracleRND";

	public CategoryResultVO getCategoryList(CategorySearchVO searchVO) throws SQLException, DAOBaseException {
		
		CategoryResultVO resultVO = new CategoryResultVO();
		CategoryVO vo = null;
		ArrayList<CategoryVO> voList = new ArrayList<CategoryVO>();
		
		try {
			getConnection(dsname);
			String query = loadQueryString("sql.category.getCategoryList"); 
			
			if (!searchVO.getSearch_word().equals("")) {
				query += "   AND CATEGORY_NM LIKE '%" + searchVO.getSearch_word().replaceAll("'", "") + "%'\n";
			}
			query += " ORDER BY ORDER_NO";
			
			openPreparedStatementForR(query, false);
			executeQueryForR(searchVO);
			
			while(rs.next()) {
				vo = new CategoryVO();
				
				vo.setCategory_id(rs.getString("CATEGORY_ID"));
				vo.setCategory_nm(rs.getString("CATEGORY_NM"));
				vo.setTree_level(rs.getString("TREE_LEVEL"));
				vo.setOrder_no(rs.getString("ORDER_NO"));
				vo.setIs_use(rs.getString("ISUSE"));
				vo.setParent_id(rs.getString("PARENT_ID"));
				vo.setReg_dt(rs.getString("REG_DT"));
				vo.setDtl_cnt(rs.getString("DTL_CNT"));

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

	public CategoryResultVO getCategoryListDtl(CategorySearchVO searchVO) throws SQLException, DAOBaseException {
		
		CategoryResultVO resultVO = new CategoryResultVO();
		CategoryVO vo = null;
		ArrayList<CategoryVO> voList = new ArrayList<CategoryVO>();
		
		try {
			getConnection(dsname);
			String query = loadQueryString("sql.category.getCategoryListDtl"); 
			if (!searchVO.getSearch_word().equals("")) {
				query += "   AND CATEGORY_NM LIKE '%" + searchVO.getSearch_word().replaceAll("'", "") + "%'\n";
			}
			query += "   AND PARENT_ID = " + searchVO.getParent_id() + "\n";
			query += " ORDER BY ORDER_NO";
			
			openPreparedStatementForR(query, false);
			executeQueryForR(searchVO);
			
			while(rs.next()) {
				vo = new CategoryVO();
				
				vo.setCategory_id(rs.getString("CATEGORY_ID"));
				vo.setCategory_nm(rs.getString("CATEGORY_NM"));
				vo.setTree_level(rs.getString("TREE_LEVEL"));
				vo.setOrder_no(rs.getString("ORDER_NO"));
				vo.setIs_use(rs.getString("ISUSE"));
				vo.setParent_id(rs.getString("PARENT_ID"));
				vo.setReg_dt(rs.getString("REG_DT"));
				vo.setParent_nm(rs.getString("PARENT_NM"));

				voList.add(vo);
			}
			
			
			if(voList.size() ==0){
				String query1  = " SELECT CATEGORY_NM FROM RNDCALL_CATEGORY WHERE ISUSE = 'Y' AND TREE_LEVEL = 1";
				       query1 += "   AND CATEGORY_ID = " + searchVO.getParent_id() + "\n";
				
				pstmt = conn.prepareStatement(query1);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					vo = new CategoryVO();
							
					vo.setParent_nm(rs.getString("CATEGORY_NM"));

				}
			}
			resultVO.setVo(vo);
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

	public CategoryResultVO getCategoryInfo(CategorySearchVO searchVO) throws SQLException, DAOBaseException {
		
		CategoryResultVO resultVO = new CategoryResultVO();
		CategoryVO vo = new CategoryVO();
		
		try {
			getConnection(dsname);
			String query = loadQueryString("sql.category.getCategoryInfo"); 
			query += "   AND CATEGORY_ID = " + searchVO.getCategory_id() + "\n";
			query += " ORDER BY ORDER_NO";
			
			openPreparedStatementForR(query, false);
			executeQueryForR(searchVO);
			
			if(rs.next()) {
				
				vo.setCategory_id(rs.getString("CATEGORY_ID"));
				vo.setCategory_nm(rs.getString("CATEGORY_NM"));
				vo.setTree_level(rs.getString("TREE_LEVEL"));
				vo.setOrder_no(rs.getString("ORDER_NO"));
				vo.setIs_use(rs.getString("ISUSE"));
				vo.setParent_id(rs.getString("PARENT_ID"));
				vo.setReg_dt(rs.getString("REG_DT"));

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

	public void insert(CategoryVO vo) throws SQLException, DAOBaseException {
		
		try {
			getConnection(dsname);
			if (!vo.getCategory_nm().equals("") && !vo.getOrder_no().equals("")) {
				String query = loadQueryString("sql.category.insert"); 
				
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, vo.getCategory_nm());
				pstmt.setString(2, vo.getTree_level());
				pstmt.setString(3, vo.getOrder_no());
				pstmt.setString(4, vo.getParent_id());
				pstmt.setString(5, vo.getLogin_id());
				
				pstmt.execute();
			}
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			close();
		}
	}

	public String update(CategoryVO vo) throws SQLException, DAOBaseException {
		
		String result = "";
		
		try {
			getConnection(dsname);
			String query = loadQueryString("sql.category.update"); 
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getCategory_nm());
			pstmt.setString(2, vo.getOrder_no());
			pstmt.setString(3, vo.getLogin_id());
			pstmt.setString(4, vo.getCategory_id());
			
			pstmt.execute();
			result = "UPDATE";
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			close();
		}
		return result;
	}

	public void delete(CategoryVO vo) throws SQLException, DAOBaseException {
		
		try {
			getConnection(dsname);
			String query = loadQueryString("sql.category.delete"); 
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getLogin_id()); 
			pstmt.setString(2, vo.getCategory_id());
			pstmt.setString(3, vo.getCategory_id());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			close();
		}
	}

}
