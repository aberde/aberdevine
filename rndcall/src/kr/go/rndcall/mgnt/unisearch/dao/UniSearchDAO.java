/** 
 * {파일, 클래스에 대한 설명을 기술한다.}
 * @author bkhwang
 * @version {Revision}
 * @since 2009. 10. 20
 * Copyright (C) 2007 by SAMSUNG SDS co.,Ltd. All right reserved.
 */
package kr.go.rndcall.mgnt.unisearch.dao;

import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.naming.NamingException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.CHAR;
import org.apache.log4j.Logger;
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.common.BaseSqlDAO;
import kr.go.rndcall.mgnt.common.DAOBaseException;
import kr.go.rndcall.mgnt.unisearch.vo.UniSearchResultVO;
import kr.go.rndcall.mgnt.unisearch.vo.UniSearchSearchVO;
import kr.go.rndcall.mgnt.unisearch.vo.UniSearchVO;

/**
 * @author bkhwang
 *
 */
public class UniSearchDAO extends BaseSqlDAO {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private String dsname = "jdbc/rndcall";

    /**
	 * iaRelationExcelI 검색대상과제 등록자 정보 Select 
	 * 
	 * @param searchVO
	 */
	public UniSearchResultVO uniSearch(UniSearchSearchVO searchVO)throws SQLException, DAOBaseException {
		UniSearchVO vo = new UniSearchVO ();
		UniSearchResultVO resultVO = new UniSearchResultVO();
		ArrayList voList = new ArrayList();
		ArrayList voList1 = new ArrayList();
		ArrayList voList2 = new ArrayList();
		ArrayList voList3 = new ArrayList();
		Reader content_clob = null;
		Reader anscontent_clob = null;
		try {
			//getConnectionNTIS(dsname);
			getConnection(dsname);
			String query = "";
				//QNA
				query = loadQueryString("sql.unisearch.unisearchQNA");
				if (!searchVO.getWord().equals("")) query += " AND A.TITLE like '%" + searchVO.getWord() + "%' \n";
				query += " order by reg_dt desc, title desc ";
				
				openPreparedStatementForR(query, false);
		        executeQueryForR(searchVO);
	
		        while (rs.next()) {
					vo = new UniSearchVO ();
	
					vo.setSeq(rs.getString("seq"));
					vo.setSeq_ans(rs.getString("seq_ans"));
					vo.setTitle(rs.getString("title"));
					vo.setBoard_type(rs.getString("board_type"));
//					content_clob = rs.getCharacterStream("CONTENTS");
//					vo.setContents(Util.streamToBuf(content_clob).toString());
					vo.setContents(rs.getString("CONTENTS"));
					if (vo.getContents() != null && !vo.getContents().equals("")) vo.setContents(vo.getContents().replaceAll("\n", "<br />"));
//					anscontent_clob = rs.getCharacterStream("answercont");
//					vo.setAnswercont(Util.streamToBuf(anscontent_clob).toString());
					vo.setAnswercont(rs.getString("ANSWERCONT"));
					if (vo.getAnswercont() != null && !vo.getAnswercont().equals("")) vo.setAnswercont(vo.getAnswercont().replaceAll("\n", "<br />"));
					vo.setRead_count(rs.getString("read_count"));
					vo.setReg_dt(rs.getString("reg_dt"));
					vo.setReg_nm(rs.getString("reg_nm"));
					voList.add(vo);	
				}
				resultVO.setTotRowCount(searchVO.getTotRowCount());
				resultVO.setVoList(voList);
				//FAQ
				query = loadQueryString("sql.unisearch.unisearchFAQ");
				if (!searchVO.getWord().equals("")) query += " AND A.TITLE like '%" + searchVO.getWord() + "%' \n";
				query += " order by reg_dt desc, title desc ";
				
				openPreparedStatementForR(query, false);
				executeQueryForR(searchVO);
				
				while (rs.next()) {
					vo = new UniSearchVO ();
					
					vo.setSeq(rs.getString("seq"));
					vo.setSeq_ans(rs.getString("seq_ans"));
					vo.setTitle(rs.getString("title"));
					vo.setBoard_type(rs.getString("board_type"));
//					content_clob = rs.getCharacterStream("CONTENTS");
//					vo.setContents(Util.streamToBuf(content_clob).toString());
//					anscontent_clob = rs.getCharacterStream("answercont");
//					vo.setAnswercont(Util.streamToBuf(anscontent_clob).toString());
					vo.setContents(rs.getString("CONTENTS"));
					if (vo.getContents() != null && !vo.getContents().equals("")) vo.setContents(vo.getContents().replaceAll("\n", "<br />"));
					vo.setAnswercont(rs.getString("ANSWERCONT"));
					if (vo.getAnswercont() != null && !vo.getAnswercont().equals("")) vo.setAnswercont(vo.getAnswercont().replaceAll("\n", "<br />"));
					vo.setRead_count(rs.getString("read_count"));
					vo.setReg_dt(rs.getString("reg_dt"));
					vo.setReg_nm(rs.getString("reg_nm"));
					voList1.add(vo);	
				}
				resultVO.setTotRowCount1(searchVO.getTotRowCount1());
				resultVO.setVoList1(voList1);
				//NOTICE
				query = loadQueryString("sql.unisearch.unisearchNOTICE");
				if (!searchVO.getWord().equals("")) query += " AND A.TITLE like '%" + searchVO.getWord() + "%' \n";
				query += " order by reg_dt desc, title desc ";
				
				openPreparedStatementForR(query, false);
				executeQueryForR(searchVO);
				
				while (rs.next()) {
					vo = new UniSearchVO ();
					
					vo.setSeq(rs.getString("seq"));
					vo.setSeq_ans(rs.getString("seq_ans"));
					vo.setTitle(rs.getString("title"));
					vo.setBoard_type(rs.getString("board_type"));
//					content_clob = rs.getCharacterStream("CONTENTS");
//					vo.setContents(Util.streamToBuf(content_clob).toString());
//					anscontent_clob = rs.getCharacterStream("answercont");
//					vo.setAnswercont(Util.streamToBuf(anscontent_clob).toString());
					vo.setContents(rs.getString("CONTENTS"));
					if (vo.getContents() != null && !vo.getContents().equals("")) vo.setContents(vo.getContents().replaceAll("\n", "<br />"));
					vo.setAnswercont(rs.getString("ANSWERCONT"));
					if (vo.getAnswercont() != null && !vo.getAnswercont().equals("")) vo.setAnswercont(vo.getAnswercont().replaceAll("\n", "<br />"));
					vo.setRead_count(rs.getString("read_count"));
					vo.setReg_dt(rs.getString("reg_dt"));
					vo.setReg_nm(rs.getString("reg_nm"));
					voList2.add(vo);	
				}
				resultVO.setTotRowCount2(searchVO.getTotRowCount2());
				resultVO.setVoList2(voList2);
				//DATA
				query = loadQueryString("sql.unisearch.unisearchDATA");
				if (!searchVO.getWord().equals("")) query += " AND A.TITLE like '%" + searchVO.getWord() + "%' \n";
				query += " order by reg_dt desc, title desc ";
				
				openPreparedStatementForR(query, false);
				executeQueryForR(searchVO);
				
				while (rs.next()) {
					vo = new UniSearchVO ();
					
					vo.setSeq(rs.getString("seq"));
					vo.setSeq_ans(rs.getString("seq_ans"));
					vo.setTitle(rs.getString("title"));
					vo.setBoard_type(rs.getString("board_type"));
//					content_clob = rs.getCharacterStream("CONTENTS");
//					vo.setContents(Util.streamToBuf(content_clob).toString());
//					anscontent_clob = rs.getCharacterStream("answercont");
//					vo.setAnswercont(Util.streamToBuf(anscontent_clob).toString());
					vo.setContents(rs.getString("CONTENTS"));
					if (vo.getContents() != null && !vo.getContents().equals("")) vo.setContents(vo.getContents().replaceAll("\n", "<br />"));
					vo.setAnswercont(rs.getString("ANSWERCONT"));
					if (vo.getAnswercont() != null && !vo.getAnswercont().equals("")) vo.setAnswercont(vo.getAnswercont().replaceAll("\n", "<br />"));
					vo.setRead_count(rs.getString("read_count"));
					vo.setReg_dt(rs.getString("reg_dt"));
					vo.setReg_nm(rs.getString("reg_nm"));
					voList3.add(vo);	
				}
				resultVO.setTotRowCount3(searchVO.getTotRowCount3());
				resultVO.setVoList3(voList3);
			
		} catch (Exception e) { 
			throwDAOBaseException(e, "not");
			return null;
		} finally {
			close();
		}
		return resultVO;
	}

}
