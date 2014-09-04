package kr.go.rndcall.mgnt.statistic.dao;

import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import kr.go.rndcall.mgnt.common.BaseSqlDAO;
import kr.go.rndcall.mgnt.common.DAOBaseException;
import kr.go.rndcall.mgnt.common.DesCipher;
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.inquire.vo.InquireVO;
import kr.go.rndcall.mgnt.statistic.vo.StatisticAttachVO;
import kr.go.rndcall.mgnt.statistic.vo.StatisticResultVO;
import kr.go.rndcall.mgnt.statistic.vo.StatisticSearchVO;
import kr.go.rndcall.mgnt.statistic.vo.StatisticVO;

import org.apache.log4j.Logger;

import com.initech.util.URLEncoder;

public class StatisticDAO extends BaseSqlDAO{

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private String dsname = "jdbc/rndcall";

	/**
	 * �������� ����Ʈ�� ��ȸ�ϴ� �޼ҵ�
	 */
	public StatisticResultVO getStatCategory(StatisticSearchVO searchVO) throws SQLException, DAOBaseException {


		StatisticVO vo = null;
		ArrayList voList = new ArrayList();
		StatisticResultVO resultVO = new StatisticResultVO();
		
		String start_yymm="";
		String end_yymm="";
		try {
			System.out.println("통계정보리스트 >>>> " + URLEncoder.encode("통계정보리스트", "UTF-8"));
			System.out.println("기간별통계 >>>> " + URLEncoder.encode("기간별통계", "UTF-8"));
			System.out.println("분야별통계 >>>> " + URLEncoder.encode("분야별통계", "UTF-8"));
			System.out.println("접속자통계 >>>> " + URLEncoder.encode("접속자통계", "UTF-8"));
			System.out.println("온라인상담 >>>> " + URLEncoder.encode("온라인상담", "UTF-8"));
			getConnection(dsname);
			
			String query = loadQueryString("sql.statistic.getStatCategory");
			
			Calendar cal = Calendar.getInstance();
			if(searchVO.getStart_yy().equals("")) searchVO.setStart_yy("2007");
			if(searchVO.getStart_mm().equals("")) searchVO.setStart_mm("01");
			if(searchVO.getEnd_yy().equals("")) searchVO.setEnd_yy(Integer.toString(cal.get(Calendar.YEAR)));
			String mm =Integer.toString(cal.get(Calendar.MONTH)+1);
			if(mm.length() < 2 ) mm = "0"+mm;
	
			if(searchVO.getEnd_mm().equals("")) {
				searchVO.setEnd_mm(mm);
			}else{
				if(searchVO.getEnd_mm().length() < 2 ) searchVO.setEnd_mm("0"+searchVO.getEnd_mm());
			}
			
			start_yymm =searchVO.getStart_yy()+""+searchVO.getStart_mm();
			end_yymm =searchVO.getEnd_yy()+""+searchVO.getEnd_mm();
			
			System.out.println("start_yymm::"+start_yymm+"&end_yymm::"+end_yymm+"&searchVO.getStart_mm()::"+searchVO.getEnd_mm()+"&searchVO.getEnd_mm()::"+searchVO.getEnd_mm());
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, start_yymm);
			pstmt.setString(2, end_yymm);
			
			executeQueryForR();
			
			while (rs.next()) {
				vo = new StatisticVO();
				vo.setBoard_type(rs.getString("BOARD_TYPE"));
				vo.setCategory1_nm(rs.getString("CATEGORY_NM"));
				vo.setCategory2_nm(rs.getString("CATEGORY_NM2"));
				vo.setCategory1(rs.getString("CATEGORY_ID"));
				vo.setCategory2(rs.getString("CATEGORY_ID2"));
				vo.setTotal_cnt(rs.getInt("TOT_CNT"));				
				vo.setOnline_cnt(rs.getInt("ONLINE_CNT"));
				vo.setOffline_cnt(rs.getInt("OFFLINE_CNT"));
				vo.setDisposal_cnt(rs.getInt("ANSWER_Y_CNT"));
				vo.setUndisposal_cnt(rs.getInt("ANSWER_N_CNT"));
				
				voList.add(vo);
			}
			
			resultVO.setVoList(voList);
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return resultVO;
	}
	
	
	/**
	 * �������� �������� ��ȸ�ϴ� �޼ҵ�
	 */
	public StatisticResultVO getStatDate(StatisticSearchVO searchVO) throws SQLException, DAOBaseException {


		StatisticVO vo = null;
		ArrayList voList = new ArrayList();
		StatisticResultVO resultVO = new StatisticResultVO();
		
		String start_yymm="";
		String end_yymm="";
		try {
			
			getConnection(dsname);
			
			String query = loadQueryString("sql.statistic.getStatDate");
			
			
			Calendar cal = Calendar.getInstance();
			if(searchVO.getStart_yy().equals("")) searchVO.setStart_yy(Integer.toString(cal.get(Calendar.YEAR)));
			if(searchVO.getStart_mm().equals("")) searchVO.setStart_mm("01");
			String mm =Integer.toString(cal.get(Calendar.MONTH)+1);
			if(mm.length() < 2 ) mm = "0"+mm;
	
			if(searchVO.getEnd_mm().equals("")) {
				searchVO.setEnd_mm(mm);
			}else{
				if(searchVO.getEnd_mm().length() < 2 ) searchVO.setEnd_mm("0"+searchVO.getEnd_mm());
			}
			
			start_yymm =searchVO.getStart_yy()+""+searchVO.getStart_mm();
			end_yymm =searchVO.getStart_yy()+""+searchVO.getEnd_mm();
			
			System.out.println("start_yymm::"+start_yymm+"&end_yymm::"+end_yymm+"&searchVO.getStart_mm()::"+searchVO.getStart_mm()+"&searchVO.getEnd_mm()::"+searchVO.getEnd_mm());
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, start_yymm) ;
			pstmt.setString(2, end_yymm) ;
			pstmt.setString(3, start_yymm) ;
			pstmt.setString(4, end_yymm) ;
			pstmt.setString(5, start_yymm) ;
			pstmt.setString(6, end_yymm) ;
			pstmt.setString(7, start_yymm) ;
			pstmt.setString(8, end_yymm) ;
			pstmt.setString(9, start_yymm) ;
			pstmt.setString(10, end_yymm) ;
			pstmt.setString(11, searchVO.getStart_mm()) ;
			pstmt.setString(12, searchVO.getEnd_mm()) ;
			
			executeQueryForR();
			
			while (rs.next()) {
				vo = new StatisticVO();
				vo.setStat_mm(rs.getString("CD_DTL_NM"));
				vo.setTotal_cnt(rs.getInt("TOTAL_CNT"));				
				vo.setOnline_cnt(rs.getInt("ONLINE_CNT"));		//�¶��ΰǼ�
				vo.setOffline_cnt(rs.getInt("OFFLINE_CNT"));		//�������ΰǼ�
				vo.setDisposal_cnt(rs.getInt("ANSWER_Y_CNT"));		//ó���Ǽ�
				vo.setUndisposal_cnt(rs.getInt("ANSWER_N_CNT"));		//��ó���Ǽ�
				
				voList.add(vo);
			}
			
			resultVO.setVoList(voList);
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return resultVO;
	}

	/**
	 * �оߺ� ��� ����Ʈ ��ȸ�ϴ� �޼ҵ�
	 */
	public StatisticResultVO getStatDataList(StatisticSearchVO searchVO) throws SQLException, DAOBaseException {

		StatisticVO vo = null;
		InquireVO vo1 = null;
		ArrayList voList = new ArrayList();
		StatisticResultVO resultVO = new StatisticResultVO();
		String query = "";
		Reader content_clob = null;
		
		String start_yymm="";
		String end_yymm="";
		
		try {
			
			getConnection(dsname);
			
			Calendar cal = Calendar.getInstance();
			if(searchVO.getStart_yy().equals("")) searchVO.setStart_yy(Integer.toString(cal.get(Calendar.YEAR)));
			if(searchVO.getStart_mm().equals("")) searchVO.setStart_mm("01");
			String mm =Integer.toString(cal.get(Calendar.MONTH)+1);
			if(mm.length() < 2 ) mm = "0"+mm;
	
			if(searchVO.getEnd_mm().equals("")) {
				searchVO.setEnd_mm(mm);
			}else{
				if(searchVO.getEnd_mm().length() < 2 ) searchVO.setEnd_mm("0"+searchVO.getEnd_mm());
			}
			
			start_yymm =searchVO.getStart_yy()+""+searchVO.getStart_mm();
			end_yymm =searchVO.getStart_yy()+""+searchVO.getEnd_mm();
			
			if(searchVO.getWhichSearch().equals("T")){
				query = loadQueryString("sql.statistic.getStatList2");
				query += "  AND TO_CHAR(Q.reg_dt,'yyyymm') >= '"+start_yymm+"' AND TO_CHAR(Q.reg_dt,'yyyymm') <= '"+end_yymm+"'";
				query += "  AND TO_CHAR(Q.reg_dt,'mm') = '"+searchVO.getSearchTxt1()+"'";
			}else if(searchVO.getWhichSearch().equals("ON")){
				query = loadQueryString("sql.statistic.getStatList2");
				query += "  AND TO_CHAR(Q.reg_dt,'yyyymm') >= '"+start_yymm+"' AND AND TO_CHAR(Q.reg_dt,'yyyymm') <='"+end_yymm+"'";
				query += "  AND Q.INSERT_TYPE = 'ONLINE' ";
				query += "  AND TO_CHAR(Q.reg_dt,'mm') = '"+searchVO.getSearchTxt1()+"'";
			}else if(searchVO.getWhichSearch().equals("OFF")){
				query = loadQueryString("sql.statistic.getStatList2");
				query += "  AND TO_CHAR(Q.reg_dt,'yyyymm') >= '"+start_yymm+"' AND AND TO_CHAR(Q.reg_dt,'yyyymm') <='"+end_yymm+"'";
				query += " AND Q.INSERT_TYPE = 'OFFLINE' ";
				query += "  AND TO_CHAR(Q.reg_dt,'mm') = '"+searchVO.getSearchTxt1()+"'";
			}else if(searchVO.getWhichSearch().equals("Y")){
				query = loadQueryString("sql.statistic.getStatList3");
				query += "  AND TO_CHAR(Q.reg_dt,'yyyymm') >= '"+start_yymm+"' AND AND TO_CHAR(Q.reg_dt,'yyyymm') <='"+end_yymm+"'";
				query += " AND EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER A WHERE Q.SEQ = A.Q_SEQ)";
				query += "  AND TO_CHAR(Q.reg_dt,'mm') = '"+searchVO.getSearchTxt1()+"'";
			}else if(searchVO.getWhichSearch().equals("N")){
				query = loadQueryString("sql.statistic.getStatList3");
				query += "  AND TO_CHAR(Q.reg_dt,'yyyymm') >= '"+start_yymm+"' AND AND TO_CHAR(Q.reg_dt,'yyyymm') <='"+end_yymm+"'";
				query += " AND NOT EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER A WHERE Q.SEQ = A.Q_SEQ)";
				query += "  AND TO_CHAR(Q.reg_dt,'mm') = '"+searchVO.getSearchTxt1()+"'";
			}
			
			if(!searchVO.getWhichSearch1().equals("") && !searchVO.getSearchTxt().equals("")){
				if(searchVO.getWhichSearch1().equals("all")){
					query += " AND ( CLOB_TO_CHAR(Q.CONTENTS) like '%"+searchVO.getSearchTxt()+"%' ";
					query += "       OR CLOB_TO_CHAR(A.ANSWER_CONT) like '%"+searchVO.getSearchTxt()+"%' ";
					query += "       OR Q.TITLE like '%"+searchVO.getSearchTxt()+"%' ";
					query += "       OR Q.REG_NM like '%"+searchVO.getSearchTxt()+"%' )";
				}else if(searchVO.getWhichSearch1().equals("title")){
					query += " AND Q.TITLE like '%"+searchVO.getSearchTxt()+"%' ";
				}else if(searchVO.getWhichSearch1().equals("contents")){
					query += " AND CLOB_TO_CHAR(Q.CONTENTS) like '%"+searchVO.getSearchTxt()+"%' ";
					
				}
			}
			
			openPreparedStatementForR(query, true);
			executeQueryForR(searchVO);
			
			while (rs.next()) {
				vo1 = new InquireVO();
				
				vo1.setSeq(rs.getString("SEQ"));
				vo1.setTitle(rs.getString("TITLE"));
				vo1.setBoard_type(rs.getString("BOARD_TYPE"));
				vo1.setCategory1(rs.getString("CATEGORY1"));
				vo1.setCategory2(rs.getString("CATEGORY2"));
				vo1.setReg_dt(rs.getString("REG_DT1"));
				vo1.setReg_nm(rs.getString("REG_NM"));
				vo1.setRead_count(rs.getInt("READ_COUNT"));
				if(searchVO.getWhichSearch().equals("Y") || searchVO.getWhichSearch().equals("SUB_Y")){
					vo1.setStat("처리완료");
				}else if(searchVO.getWhichSearch().equals("N") || searchVO.getWhichSearch().equals("SUB_N")){
					vo1.setStat("미처리");
				}else{
					vo1.setStat(rs.getString("STAT"));
				}
				if(rs.getString("INSERT_TYPE").equals("ONLINE")){
					vo1.setInsert_type("온라인");
				}else if(rs.getString("INSERT_TYPE").equals("OFFLINE")){
					vo1.setInsert_type("오프라인");
				}
				vo1.setCategory1_nm(rs.getString("CATEGORY1_NM"));
				vo1.setCategory2_nm(rs.getString("CATEGORY2_NM"));
				content_clob = rs.getCharacterStream("CONTENTS");
				vo1.setContents(Util.streamToBuf(content_clob).toString().replaceAll("\n", "<br>"));
				
				voList.add(vo1);
			}
			
			resultVO.setTotRowCount(searchVO.getTotRowCount());
			resultVO.setVoList(voList);
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return resultVO;
	}
	
	/**
	 * ���������� ����ϴ� �޼ҵ�
	 */
	public StatisticResultVO getStatVisit(StatisticSearchVO searchVO) throws SQLException, DAOBaseException {
		
		StatisticVO vo = null;
		ArrayList voList = new ArrayList();
		StatisticResultVO resultVO = new StatisticResultVO();
		String query = "";
		String type= "";
		try {
			getConnection(dsname);
			
			if(searchVO.getStat_type().equals("YY")){
				type= "년";
				query = loadQueryString("sql.statistic.getVisitYY");
				query +="ORDER BY CODE ASC";
			}else if(searchVO.getStat_type().equals("MM")){
				type= "월";
				query = "SELECT MM AS CODE, NVL(CNT,0) AS CNT "; 
				query +=" FROM  ";
				query +="        (SELECT TO_NUMBER(TO_CHAR(A.visit_dt,'mm')) AS CODE,  ";
				query +="               COUNT(*) AS CNT  ";
				query +="         FROM RNDCALL_VISIT A  ";
				query +="         WHERE 1=1  ";
				if(!searchVO.getStart_yy().equals("")){
					query +=" AND TO_CHAR(A.visit_dt,'yyyy')='" + searchVO.getStart_yy()+ "' ";
				}
				query +="         GROUP BY TO_NUMBER(TO_CHAR(A.visit_dt,'mm'))  ";
				query +="         ORDER BY CODE ASC  ";
				query +="        ) V RIGHT OUTER JOIN  ";
				query +="        (SELECT TO_NUMBER(SUBSTR(B.CD_DTL_ID,3,5)) AS MM  ";
				query +="          FROM RNDCALL_CODE B  ";
				query +="         WHERE B.CD_ID='100'  ";
				query +="               AND B.USE_YN='Y'  ";
				query +="        ) C  ON V.CODE = C.MM";
//				query +="  WHERE C.MM =V.CODE(+)  ";
				query +=" ORDER BY MM ASC";
			}else if(searchVO.getStat_type().equals("DD")){
				type= "일";
				query = "SELECT MM AS CODE, NVL(CNT,0) AS CNT "; 
				query +=" FROM  ";
				query +="        (SELECT TO_NUMBER(TO_CHAR(A.visit_dt,'dd')) AS CODE,  ";
				query +="               COUNT(*) AS CNT  ";
				query +="         FROM RNDCALL_VISIT A  ";
				query +="         WHERE 1=1  ";
				if(!searchVO.getStart_yy().equals("")){
					query +=" AND TO_CHAR(A.visit_dt,'yyyy')='" + searchVO.getStart_yy()+ "' ";
				}
				if(!searchVO.getStart_mm().equals("")){
					query +=" AND TO_CHAR(A.visit_dt,'mm')='" + searchVO.getStart_mm()+ "' ";
				}
				query +="         GROUP BY TO_NUMBER(TO_CHAR(A.visit_dt,'dd'))  ";
				query +="         ORDER BY CODE ASC  ";
				query +="        ) V RIGHT OUTER JOIN  ";
				query +="        (SELECT TO_NUMBER(B.CD_DTL_ID) AS MM  ";
				query +="          FROM RNDCALL_CODE B  ";
				query +="         WHERE B.CD_ID='101'  ";
				query +="               AND B.USE_YN='Y'  ";
				query +="        ) C  ON V.CODE = C.MM";
//				query +="  WHERE C.MM =V.CODE(+)  ";
				query +=" ORDER BY MM ASC";
				
			}
			
			
			openPreparedStatementForR(query, false);
			executeQueryForR();
			
			while (rs.next()) {
				vo = new StatisticVO();
				vo.setCode(rs.getString("CODE")+type);
				vo.setCnt(rs.getInt("CNT"));
				
				voList.add(vo);
			}
			
			resultVO.setVoList(voList);
			
			
			query = loadQueryString("sql.statistic.getVisitTotal");		
		
			openPreparedStatementForR(query, false);
			executeQueryForR();
			
			if (rs.next()) {
				vo.setTotal_cnt(rs.getInt("CNT"));
			}
			
			resultVO.setVo(vo);
					
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			close();
		}
		return resultVO;
	}
	
	/**
	 * �оߺ� ��� ����Ʈ ��ȸ�ϴ� �޼ҵ�
	 */
	public StatisticResultVO getStatList(StatisticSearchVO searchVO) throws SQLException, DAOBaseException {

		StatisticVO vo = null;
		InquireVO vo1 = null;
		ArrayList voList = new ArrayList();
		StatisticResultVO resultVO = new StatisticResultVO();
		String query = "";
		Reader content_clob = null;
		try {
			
			getConnection(dsname);
			
			if(searchVO.getWhichSearch().equals("T")){
				query = loadQueryString("sql.statistic.getStatList");
				query += " AND DECODE(Q.CATEGORY1,NULL,99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				if(!searchVO.getCategory2().equals("")){
					query += " AND DECODE(Q.CATEGORY2,NULL,99999999,Q.CATEGORY2) = " + searchVO.getCategory2()+ " ";
				}
				
			}else if(searchVO.getWhichSearch().equals("ON")){
				query = loadQueryString("sql.statistic.getStatList");
				query += " AND DECODE(Q.CATEGORY1,NULL,99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				if(!searchVO.getCategory2().equals("")){
					query += " AND DECODE(Q.CATEGORY2,NULL,99999999,Q.CATEGORY2) = " + searchVO.getCategory2()+ " ";
				}
				query += " AND Q.INSERT_TYPE = 'ONLINE' ";
			}else if(searchVO.getWhichSearch().equals("OFF")){
				query = loadQueryString("sql.statistic.getStatList");
				query += " AND DECODE(Q.CATEGORY1,NULL,99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				if(!searchVO.getCategory2().equals("")){
					query += " AND DECODE(Q.CATEGORY2,NULL,99999999,Q.CATEGORY2) = " + searchVO.getCategory2()+ " ";
				}
				query += " AND Q.INSERT_TYPE = 'OFFLINE' ";
			}else if(searchVO.getWhichSearch().equals("Y")){
				query = loadQueryString("sql.statistic.getStatList1");
				query += " AND DECODE(Q.CATEGORY1,NULL,99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				if(!searchVO.getCategory2().equals("")){
					query += " AND DECODE(Q.CATEGORY2,NULL,99999999,Q.CATEGORY2) = " + searchVO.getCategory2()+ " ";
				}
				query += " AND EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER A WHERE Q.SEQ = A.Q_SEQ)";
			}else if(searchVO.getWhichSearch().equals("N")){
				query = loadQueryString("sql.statistic.getStatList1");
				query += " AND DECODE(Q.CATEGORY1,NULL,99999999,Q.CATEGORY1) = '" + searchVO.getCategory1()+ "' ";
				if(!searchVO.getCategory2().equals("")){
					query += " AND DECODE(Q.CATEGORY2,NULL,99999999,Q.CATEGORY2) = '" + searchVO.getCategory2()+ "' ";
				}
				query += " AND NOT EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER A WHERE Q.SEQ = A.Q_SEQ)";
			//�Ұ�����
			}else if(searchVO.getWhichSearch().equals("SUB_T")){
				query = loadQueryString("sql.statistic.getStatList");
				query += " AND DECODE(Q.CATEGORY1,NULL,99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
			}else if(searchVO.getWhichSearch().equals("SUB_ON")){
				query = loadQueryString("sql.statistic.getStatList");
				query += " AND DECODE(Q.CATEGORY1,NULL,99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				query += " AND Q.INSERT_TYPE = 'ONLINE' ";
			}else if(searchVO.getWhichSearch().equals("SUB_OFF")){
				query = loadQueryString("sql.statistic.getStatList");
				query += " AND DECODE(Q.CATEGORY1,NULL,99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				query += " AND Q.INSERT_TYPE = 'OFFLINE' ";
			}else if(searchVO.getWhichSearch().equals("SUB_Y")){
				query = loadQueryString("sql.statistic.getStatList1");
				query += " AND DECODE(Q.CATEGORY1,NULL,99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				query += " AND EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER A WHERE Q.SEQ = A.Q_SEQ)";
			}else if(searchVO.getWhichSearch().equals("SUB_N")){
				query = loadQueryString("sql.statistic.getStatList1");
				query += " AND DECODE(Q.CATEGORY1,NULL,99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				query += " AND NOT EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER A WHERE Q.SEQ = A.Q_SEQ)";
			}
			
			if(!searchVO.getWhichSearch1().equals("") && !searchVO.getSearchTxt().equals("")){
				if(searchVO.getWhichSearch1().equals("all")){
					query += " AND ( CLOB_TO_CHAR(Q.CONTENTS) like '%"+searchVO.getSearchTxt()+"%' ";
					query += "       OR CLOB_TO_CHAR(A.ANSWER_CONT) like '%"+searchVO.getSearchTxt()+"%' ";
					query += "       OR Q.TITLE like '%"+searchVO.getSearchTxt()+"%' ";
					query += "       OR Q.REG_NM like '%"+searchVO.getSearchTxt()+"%' )";
				}else if(searchVO.getWhichSearch1().equals("title")){
					query += " AND Q.TITLE like '%"+searchVO.getSearchTxt()+"%' ";
				}else if(searchVO.getWhichSearch1().equals("contents")){
					query += " AND CLOB_TO_CHAR(Q.CONTENTS) like '%"+searchVO.getSearchTxt()+"%' ";
					
				}
			}
			
			openPreparedStatementForR(query, true);
			executeQueryForR(searchVO);
			
			while (rs.next()) {
				vo1 = new InquireVO();
				
				vo1.setSeq(rs.getString("SEQ"));
				vo1.setTitle(rs.getString("TITLE"));
				vo1.setBoard_type(rs.getString("BOARD_TYPE"));
				vo1.setCategory1(rs.getString("CATEGORY1"));
				vo1.setCategory2(rs.getString("CATEGORY2"));
				vo1.setReg_dt(rs.getString("REG_DT1"));
				vo1.setReg_nm(rs.getString("REG_NM"));
				vo1.setRead_count(rs.getInt("READ_COUNT"));
				if(searchVO.getWhichSearch().equals("Y") || searchVO.getWhichSearch().equals("SUB_Y")){
					vo1.setStat("처리완료");
				}else if(searchVO.getWhichSearch().equals("N") || searchVO.getWhichSearch().equals("SUB_N")){
					vo1.setStat("미처리");
				}else{
					vo1.setStat(rs.getString("STAT"));
				}
				if(rs.getString("INSERT_TYPE").equals("ONLINE")){
					vo1.setInsert_type("온라인");
				}else if(rs.getString("INSERT_TYPE").equals("OFFLINE")){
					vo1.setInsert_type("오프라인");
				}
				vo1.setCategory1_nm(rs.getString("CATEGORY1_NM"));
				vo1.setCategory2_nm(rs.getString("CATEGORY2_NM"));
				content_clob = rs.getCharacterStream("CONTENTS");
				vo1.setContents(Util.streamToBuf(content_clob).toString().replaceAll("\n", "<br>"));
				
				voList.add(vo1);
			}
			
			resultVO.setTotRowCount(searchVO.getTotRowCount());
			resultVO.setVoList(voList);
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return resultVO;
	}
	
	/**
	 * �оߺ� ��� ����Ʈ ��ȸ�ϴ� �޼ҵ�
	 */
	public StatisticResultVO getStatView(StatisticSearchVO searchVO) throws SQLException, DAOBaseException {

		StatisticVO vo = null;
		InquireVO vo1 = null;
		ArrayList voList = new ArrayList();
		StatisticResultVO resultVO = new StatisticResultVO();
		String query = "";
		Reader content_clob = null;
		Reader answerContent_clob = null;
		
		try {
			
			getConnection(dsname);
			
			query = loadQueryString("sql.statistic.getStatView");
			//Q&A����Ʈ ��ȸ
			query += " AND Q.BOARD_TYPE= 'QNA'";
//			query += " AND Q.SEQ = A.Q_SEQ(+) ";
			query += " AND Q.SEQ = '" + searchVO.getSeq() +"'";
						
			pstmt = conn.prepareStatement(query);
		    rs = pstmt.executeQuery();
			
			if (rs.next()) {
				vo1 = new InquireVO();
				vo1.setSeq(rs.getString("SEQ"));				
				vo1.setTitle(rs.getString("TITLE"));
				vo1.setCall_receive_yn(rs.getString("CELL_RECEIVE_YN"));
				vo1.setCell_number(rs.getString("CELL_NUMBER"));
				vo1.setEmail_receive_yn(rs.getString("EMAIL_RECEIVE_YN"));
				vo1.setEmail(rs.getString("EMAIL"));
				vo1.setOpen_yn(rs.getString("OPEN_YN"));
				vo1.setBoard_type(rs.getString("BOARD_TYPE"));
				vo1.setCategory1(rs.getString("CATEGORY1"));
				vo1.setCategory2(rs.getString("CATEGORY2"));
				vo1.setCategory1_nm(rs.getString("CATEGORY1_NM"));
				vo1.setCategory2_nm(rs.getString("CATEGORY2_NM"));
				vo1.setReg_dt(rs.getString("REG_DT1"));
				vo1.setReg_nm(rs.getString("REG_NM"));
				vo1.setRead_count(rs.getInt("READ_COUNT"));
//				content_clob = rs.getCharacterStream("CONTENTS");
//				vo1.setContents(Util.streamToBuf(content_clob).toString());
				vo1.setContents(rs.getString("CONTENTS").replaceAll("\n", "<br>"));
				
//				answerContent_clob = rs.getCharacterStream("ANSWER_CONT");
//				vo1.setAnswerContents(Util.streamToBuf(answerContent_clob).toString());				
				vo1.setAnswerContents(rs.getString("ANSWER_CONT").replaceAll("\n", "<br>"));
				
				vo1.setQuestion_file_id(rs.getString("QUESTION_FILE_ID"));
				vo1.setAnswer_file_id(rs.getString("ANSWER_FILE_ID"));
				vo1.setUp_del_stat(rs.getString("UP_DEL_STAT"));
				vo1.setStat(rs.getString("STAT"));
				vo1.setAnswer_seq(rs.getString("ANSWER_SEQ"));
				vo1.setPublic_trans_yn(rs.getString("PUBLIC_TRANS_YN"));
			}
			
			resultVO.setVo1(vo1);	
			pstmt.close();
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return resultVO;
	}
	
	/**
	 * Q&A ÷�������� ��ȸ�ϴ� �޼ҵ�
	 */
	public StatisticResultVO getFileInfo(String file_id) throws SQLException, DAOBaseException {


		ArrayList voList = new ArrayList();

		StatisticResultVO resultVO = new StatisticResultVO();
		StatisticAttachVO fileVo = null;
		DesCipher dc = new DesCipher();
		
		try {
			getConnection(dsname);
			
			String query = loadQueryString("sql.inquire.question.fileinfo");
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, file_id) ;
			
			executeQueryForR();				
			
			while (this.rs.next()) {
				fileVo = new StatisticAttachVO();
				fileVo.setFile_id(rs.getString("FILE_ID"));
				fileVo.setSeq(rs.getString("SEQ"));
				fileVo.setFile_nm(rs.getString("FILE_NM"));
				fileVo.setFile_type(rs.getString("FILE_TYPE"));
				fileVo.setFile_up_path(rs.getString("FILE_UP_PATH"));
				fileVo.setFile_size(rs.getString("FILE_SIZE"));
				fileVo.setFile_desc(rs.getString("FILE_DESC"));
				fileVo.setFile_del_yn(rs.getString("FILE_DEL_YN"));
				
				
				String fileId = rs.getString("file_id");
				String file_path = rs.getString("file_up_path");
				String[] fPath = file_path.split("FILE/");
					System.out.println("fPath.length ::"+fPath.length );
					logger.debug("fPath.length ::"+fPath.length);
				if(fPath.length >1){
					fileVo.setFile_path(dc.Encode(fPath[1]));
				}else{
					fileVo.setFile_path(dc.Encode(fPath[0]));
				}
				
				
				voList.add(fileVo);
			}
			
			System.out.println("voList.size()::"+voList.size());
			resultVO.setVoList(voList);
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return resultVO;
	}
	
	/**
	 * �������� �������� ��ȸ�ϴ� �޼ҵ�
	 */
	public StatisticResultVO getStatExcelList(StatisticSearchVO searchVO) throws SQLException, DAOBaseException {

		StatisticVO vo = null;
		InquireVO vo1 = null;
		ArrayList voList = new ArrayList();
		StatisticResultVO resultVO = new StatisticResultVO();
		String query = "";
		Reader content_clob = null;
		try {
			
			getConnection(dsname);
			
			if(searchVO.getWhichSearch().equals("T")){
				query = loadQueryString("sql.statistic.getStatList");
				query += " AND DECODE(Q.CATEGORY1,'',99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				if(!searchVO.getCategory2().equals("")){
					query += " AND DECODE(Q.CATEGORY2,'',99999999,Q.CATEGORY2) = " + searchVO.getCategory2()+ " ";
				}
				
			}else if(searchVO.getWhichSearch().equals("ON")){
				query = loadQueryString("sql.statistic.getStatList");
				query += " AND DECODE(Q.CATEGORY1,'',99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				if(!searchVO.getCategory2().equals("")){
					query += " AND DECODE(Q.CATEGORY2,'',99999999,Q.CATEGORY2) = " + searchVO.getCategory2()+ " ";
				}
				query += " AND Q.INSERT_TYPE = 'ONLINE' ";
			}else if(searchVO.getWhichSearch().equals("OFF")){
				query = loadQueryString("sql.statistic.getStatList");
				query += " AND DECODE(Q.CATEGORY1,'',99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				if(!searchVO.getCategory2().equals("")){
					query += " AND DECODE(Q.CATEGORY2,'',99999999,Q.CATEGORY2) = " + searchVO.getCategory2()+ " ";
				}
				query += " AND Q.INSERT_TYPE = 'OFFLINE' ";
			}else if(searchVO.getWhichSearch().equals("Y")){
				query = loadQueryString("sql.statistic.getStatList1");
				query += " AND DECODE(Q.CATEGORY1,'',99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				if(!searchVO.getCategory2().equals("")){
					query += " AND DECODE(Q.CATEGORY2,'',99999999,Q.CATEGORY2) = " + searchVO.getCategory2()+ " ";
				}
				query += " AND EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER A WHERE Q.SEQ = A.Q_SEQ)";
			}else if(searchVO.getWhichSearch().equals("N")){
				query = loadQueryString("sql.statistic.getStatList1");
				query += " AND DECODE(Q.CATEGORY1,'',99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				if(!searchVO.getCategory2().equals("")){
					query += " AND DECODE(Q.CATEGORY2,'','99999999',Q.CATEGORY2) = " + searchVO.getCategory2()+ " ";
				}
				query += " AND NOT EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER A WHERE Q.SEQ = A.Q_SEQ)";
			// �Ұ�����
			}else if(searchVO.getWhichSearch().equals("SUB_T")){
				query = loadQueryString("sql.statistic.getStatList");
				query += " AND DECODE(Q.CATEGORY1,'',99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
			}else if(searchVO.getWhichSearch().equals("SUB_ON")){
				query = loadQueryString("sql.statistic.getStatList");
				query += " AND DECODE(Q.CATEGORY1,'',99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				query += " AND Q.INSERT_TYPE = 'ONLINE' ";
			}else if(searchVO.getWhichSearch().equals("SUB_OFF")){
				query = loadQueryString("sql.statistic.getStatList");
				query += " AND DECODE(Q.CATEGORY1,'',99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				query += " AND Q.INSERT_TYPE = 'OFFLINE' ";
			}else if(searchVO.getWhichSearch().equals("SUB_Y")){
				query = loadQueryString("sql.statistic.getStatList1");
				query += " AND DECODE(Q.CATEGORY1,'',99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				query += " AND EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER A WHERE Q.SEQ = A.Q_SEQ)";
			}else if(searchVO.getWhichSearch().equals("SUB_N")){
				query = loadQueryString("sql.statistic.getStatList1");
				query += " AND DECODE(Q.CATEGORY1,'',99999999,Q.CATEGORY1) = " + searchVO.getCategory1()+ " ";
				query += " AND NOT EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER A WHERE Q.SEQ = A.Q_SEQ)";
			}
			
			if(!searchVO.getWhichSearch1().equals("") && !searchVO.getSearchTxt().equals("")){
				if(searchVO.getWhichSearch1().equals("all")){
					query += " AND ( CLOB_TO_CHAR(Q.CONTENTS) like '%"+searchVO.getSearchTxt()+"%' ";
					query += "       OR CLOB_TO_CHAR(A.ANSWER_CONT) like '%"+searchVO.getSearchTxt()+"%' ";
					query += "       OR Q.TITLE like '%"+searchVO.getSearchTxt()+"%' ";
					query += "       OR Q.REG_NM like '%"+searchVO.getSearchTxt()+"%' )";
				}else if(searchVO.getWhichSearch1().equals("title")){
					query += " AND Q.TITLE like '%"+searchVO.getSearchTxt()+"%' ";
				}else if(searchVO.getWhichSearch1().equals("contents")){
					query += " AND CLOB_TO_CHAR(Q.CONTENTS) like '%"+searchVO.getSearchTxt()+"%' ";
					
				}
			}
			
			openPreparedStatementForR(query, false);
			executeQueryForR(searchVO);
			
			while (rs.next()) {
				vo1 = new InquireVO();
				vo1.setSeq(rs.getString("SEQ"));
				vo1.setTitle(rs.getString("TITLE"));
				vo1.setBoard_type(rs.getString("BOARD_TYPE"));
				vo1.setCategory1(rs.getString("CATEGORY1"));
				vo1.setCategory2(rs.getString("CATEGORY2"));
				vo1.setReg_dt(rs.getString("REG_DT1"));
				vo1.setReg_nm(rs.getString("REG_NM"));
				vo1.setRead_count(rs.getInt("READ_COUNT"));
				if(searchVO.getWhichSearch().equals("Y")){
					vo1.setStat("처리완료");
				}else if(searchVO.getWhichSearch().equals("N")){
					vo1.setStat("미처리");
				}else{
					vo1.setStat(rs.getString("STAT"));
				}
				
				if(rs.getString("INSERT_TYPE").equals("ONLINE")){
					vo1.setInsert_type("온라인");
				}else if(rs.getString("INSERT_TYPE").equals("OFFLINE")){
					vo1.setInsert_type("오프라인");
				}
				vo1.setCategory1_nm(rs.getString("CATEGORY1_NM"));
				vo1.setCategory2_nm(rs.getString("CATEGORY2_NM"));
				content_clob = rs.getCharacterStream("CONTENTS");
				vo1.setContents(Util.streamToBuf(content_clob).toString().replaceAll("\n", ""));
				
				voList.add(vo1);
			}
			
			resultVO.setTotRowCount(searchVO.getTotRowCount());
			resultVO.setVoList(voList);
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return resultVO;
	}
	
	/**
	 * �оߺ� ��� ����Ʈ ��ȸ�ϴ� �޼ҵ�
	 */
	public StatisticResultVO getStatDataExcel(StatisticSearchVO searchVO) throws SQLException, DAOBaseException {

		StatisticVO vo = null;
		InquireVO vo1 = null;
		ArrayList voList = new ArrayList();
		StatisticResultVO resultVO = new StatisticResultVO();
		String query = "";
		Reader content_clob = null;
		
		String start_yymm="";
		String end_yymm="";
		
		try {
			
			getConnection(dsname);
			
			Calendar cal = Calendar.getInstance();
//			if(searchVO.getStart_yy().equals("")) searchVO.setStart_yy(Integer.toString(cal.get(Calendar.YEAR)));
//			if(searchVO.getStart_mm().equals("")) searchVO.setStart_mm("01");
			if(searchVO.getStart_yy().equals("")) searchVO.setEnd_yy(Integer.toString(cal.get(Calendar.YEAR)));
			if(searchVO.getStart_mm().equals("")) searchVO.setStart_mm("01");
			if(searchVO.getEnd_yy().equals("")) searchVO.setEnd_yy(Integer.toString(cal.get(Calendar.YEAR)));
			String mm =Integer.toString(cal.get(Calendar.MONTH)+1);
			if(mm.length() < 2 ) mm = "0"+mm;
	
			if(searchVO.getEnd_mm().equals("")) {
				searchVO.setEnd_mm(mm);
			}else{
				if(searchVO.getEnd_mm().length() < 2 ) searchVO.setEnd_mm("0"+searchVO.getEnd_mm());
			}
			
			start_yymm =searchVO.getStart_yy()+""+searchVO.getStart_mm();
			end_yymm =searchVO.getEnd_yy()+""+searchVO.getEnd_mm();
			
			if(searchVO.getWhichSearch().equals("T")){
				query = loadQueryString("sql.statistic.getStatList2");
				query += "  AND TO_CHAR(Q.reg_dt,'yyyymm') >= '"+start_yymm+"' AND TO_CHAR(Q.reg_dt,'yyyymm') <= '"+end_yymm+"'";
				query += "  AND TO_CHAR(Q.reg_dt,'mm') = '"+searchVO.getSearchTxt1()+"'";
			}else if(searchVO.getWhichSearch().equals("ON")){
				query = loadQueryString("sql.statistic.getStatList2");
				query += "  AND TO_CHAR(Q.reg_dt,'yyyymm') >= '"+start_yymm+"' AND TO_CHAR(Q.reg_dt,'yyyymm') <= '"+end_yymm+"'";
				query += "  AND Q.INSERT_TYPE = 'ONLINE' ";
				query += "  AND TO_CHAR(Q.reg_dt,'mm') = '"+searchVO.getSearchTxt1()+"'";
			}else if(searchVO.getWhichSearch().equals("OFF")){
				query = loadQueryString("sql.statistic.getStatList2");
				query += "  AND TO_CHAR(Q.reg_dt,'yyyymm') >= '"+start_yymm+"' AND TO_CHAR(Q.reg_dt,'yyyymm') <= '"+end_yymm+"'";
				query += " AND Q.INSERT_TYPE = 'OFFLINE' ";
				query += "  AND TO_CHAR(Q.reg_dt,'mm') = '"+searchVO.getSearchTxt1()+"'";
			}else if(searchVO.getWhichSearch().equals("Y")){
				query = loadQueryString("sql.statistic.getStatList3");
				query += "  AND TO_CHAR(Q.reg_dt,'yyyymm') >= '"+start_yymm+"' AND TO_CHAR(Q.reg_dt,'yyyymm') <= '"+end_yymm+"'";
				query += " AND EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER A WHERE Q.SEQ = A.Q_SEQ)";
				query += "  AND TO_CHAR(Q.reg_dt,'mm') = '"+searchVO.getSearchTxt1()+"'";
			}else if(searchVO.getWhichSearch().equals("N")){
				query = loadQueryString("sql.statistic.getStatList3");
				query += "  AND TO_CHAR(Q.reg_dt,'yyyymm') >= '"+start_yymm+"' AND TO_CHAR(Q.reg_dt,'yyyymm') <= '"+end_yymm+"'";
				query += " AND NOT EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER A WHERE Q.SEQ = A.Q_SEQ)";
				query += "  AND TO_CHAR(Q.reg_dt,'mm') = '"+searchVO.getSearchTxt1()+"'";
			}
			
			if(!searchVO.getWhichSearch1().equals("") && !searchVO.getSearchTxt().equals("")){
				if(searchVO.getWhichSearch1().equals("all")){
					query += " AND ( CLOB_TO_CHAR(Q.CONTENTS) like '%"+searchVO.getSearchTxt()+"%' ";
					query += "       OR CLOB_TO_CHAR(A.ANSWER_CONT) like '%"+searchVO.getSearchTxt()+"%' ";
					query += "       OR Q.TITLE like '%"+searchVO.getSearchTxt()+"%' ";
					query += "       OR Q.REG_NM like '%"+searchVO.getSearchTxt()+"%' )";
				}else if(searchVO.getWhichSearch1().equals("title")){
					query += " AND Q.TITLE like '%"+searchVO.getSearchTxt()+"%' ";
				}else if(searchVO.getWhichSearch1().equals("contents")){
					query += " AND CLOB_TO_CHAR(Q.CONTENTS) like '%"+searchVO.getSearchTxt()+"%' ";
					
				}
			}
			
			openPreparedStatementForR(query, false);
			executeQueryForR(searchVO);
			
			while (rs.next()) {
				vo1 = new InquireVO();
				
				vo1.setSeq(rs.getString("SEQ"));
				vo1.setTitle(rs.getString("TITLE"));
				vo1.setBoard_type(rs.getString("BOARD_TYPE"));
				vo1.setCategory1(rs.getString("CATEGORY1"));
				vo1.setCategory2(rs.getString("CATEGORY2"));
				vo1.setReg_dt(rs.getString("REG_DT1"));
				vo1.setReg_nm(rs.getString("REG_NM"));
				vo1.setRead_count(rs.getInt("READ_COUNT"));
				if(searchVO.getWhichSearch().equals("Y") || searchVO.getWhichSearch().equals("SUB_Y")){
					vo1.setStat("처리완료");
				}else if(searchVO.getWhichSearch().equals("N") || searchVO.getWhichSearch().equals("SUB_N")){
					vo1.setStat("미처리");
				}else{
					vo1.setStat(rs.getString("STAT"));
				}
				if(rs.getString("INSERT_TYPE").equals("ONLINE")){
					vo1.setInsert_type("온라인");
				}else if(rs.getString("INSERT_TYPE").equals("OFFLINE")){
					vo1.setInsert_type("오프라인");
				}
				vo1.setCategory1_nm(rs.getString("CATEGORY1_NM"));
				vo1.setCategory2_nm(rs.getString("CATEGORY2_NM"));
				content_clob = rs.getCharacterStream("CONTENTS");
				vo1.setContents(Util.streamToBuf(content_clob).toString().replaceAll("\n", ""));
				
				voList.add(vo1);
			}
			
			resultVO.setTotRowCount(searchVO.getTotRowCount());
			resultVO.setVoList(voList);
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return resultVO;
	}
	
    public StatisticResultVO getStatBoardType(StatisticSearchVO searchVO) throws SQLException, DAOBaseException {


        StatisticVO vo = null;
        ArrayList voList = new ArrayList();
        StatisticResultVO resultVO = new StatisticResultVO();
        
        String start_yymm="";
        String end_yymm="";
        try {
            getConnection(dsname);
            
            String query = loadQueryString("sql.statistic.getStatBoardType");
            
            Calendar cal = Calendar.getInstance();
            if(searchVO.getStart_yy().equals("")) searchVO.setStart_yy("2007");
            if(searchVO.getStart_mm().equals("")) searchVO.setStart_mm("01");
            if(searchVO.getEnd_yy().equals("")) searchVO.setEnd_yy(Integer.toString(cal.get(Calendar.YEAR)));
            String mm =Integer.toString(cal.get(Calendar.MONTH)+1);
            if(mm.length() < 2 ) mm = "0"+mm;
    
            if(searchVO.getEnd_mm().equals("")) {
                searchVO.setEnd_mm(mm);
            }else{
                if(searchVO.getEnd_mm().length() < 2 ) searchVO.setEnd_mm("0"+searchVO.getEnd_mm());
            }
            
            start_yymm =searchVO.getStart_yy()+""+searchVO.getStart_mm();
            end_yymm =searchVO.getEnd_yy()+""+searchVO.getEnd_mm();
            
            System.out.println("start_yymm::"+start_yymm+"&end_yymm::"+end_yymm+"&searchVO.getStart_mm()::"+searchVO.getEnd_mm()+"&searchVO.getEnd_mm()::"+searchVO.getEnd_mm());
            openPreparedStatementForR(query, false);
            
            pstmt.setString(1, start_yymm);
            pstmt.setString(2, end_yymm);
            
            executeQueryForR();
            
            while (rs.next()) {
                vo = new StatisticVO();
                vo.setBoard_type(rs.getString("BOARD_TYPE"));
                vo.setTotal_cnt(rs.getInt("TOTAL_CNT"));                
                vo.setOnline_cnt(rs.getInt("ONLINE_CNT"));
                vo.setOffline_cnt(rs.getInt("OFFLINE_CNT"));
                vo.setDisposal_cnt(rs.getInt("ANSWER_Y_CNT"));
                vo.setUndisposal_cnt(rs.getInt("ANSWER_N_CNT"));
                
                voList.add(vo);
            }
            
            resultVO.setVoList(voList);
        } catch (Exception e) {
            throwDAOBaseException(e, "not");
            System.out.println(e.getStackTrace());
            return null;
        } finally {
            close();
        }

        return resultVO;
    }
    
    public StatisticResultVO getStatQueryUserInfo(StatisticSearchVO searchVO) throws SQLException, DAOBaseException {
        
        
        StatisticVO vo = null;
        ArrayList voList = new ArrayList();
        StatisticResultVO resultVO = new StatisticResultVO();
        
        String start_yymm="";
        String end_yymm="";
        try {
            getConnection(dsname);
            
            String query = loadQueryString("sql.statistic.getStatQueryUserInfo");
            
            Calendar cal = Calendar.getInstance();
            if(searchVO.getStart_yy().equals("")) searchVO.setStart_yy("2007");
            if(searchVO.getStart_mm().equals("")) searchVO.setStart_mm("01");
            if(searchVO.getEnd_yy().equals("")) searchVO.setEnd_yy(Integer.toString(cal.get(Calendar.YEAR)));
            String mm =Integer.toString(cal.get(Calendar.MONTH)+1);
            if(mm.length() < 2 ) mm = "0"+mm;
            
            if(searchVO.getEnd_mm().equals("")) {
                searchVO.setEnd_mm(mm);
            }else{
                if(searchVO.getEnd_mm().length() < 2 ) searchVO.setEnd_mm("0"+searchVO.getEnd_mm());
            }
            
            start_yymm =searchVO.getStart_yy()+""+searchVO.getStart_mm();
            end_yymm =searchVO.getEnd_yy()+""+searchVO.getEnd_mm();
            
            System.out.println("start_yymm::"+start_yymm+"&end_yymm::"+end_yymm+"&searchVO.getStart_mm()::"+searchVO.getEnd_mm()+"&searchVO.getEnd_mm()::"+searchVO.getEnd_mm());
            openPreparedStatementForR(query, false);
            
            pstmt.setString(1, start_yymm);
            pstmt.setString(2, end_yymm);
            
            executeQueryForR();
            
            while (rs.next()) {
                vo = new StatisticVO();
                vo.setBoard_type(rs.getString("BOARD_TYPE"));
                vo.setBoard_typeNm(rs.getString("BOARD_TYPE_NM"));
                vo.setUserType(rs.getString("USER_TYPE"));
                vo.setUserTypeNm(rs.getString("USER_TYPE_NM"));
                vo.setQueryUserInfo(rs.getString("QUERY_USER_INFO"));
                vo.setQueryUserInfoNm(rs.getString("QUERY_USER_INFO_NM"));
                vo.setTotal_cnt(rs.getInt("TOTAL_CNT"));                
                vo.setOnline_cnt(rs.getInt("ONLINE_CNT"));
                vo.setOffline_cnt(rs.getInt("OFFLINE_CNT"));
                vo.setDisposal_cnt(rs.getInt("ANSWER_Y_CNT"));
                vo.setUndisposal_cnt(rs.getInt("ANSWER_N_CNT"));
                
                voList.add(vo);
            }
            
            resultVO.setVoList(voList);
        } catch (Exception e) {
            throwDAOBaseException(e, "not");
            System.out.println(e.getStackTrace());
            return null;
        } finally {
            close();
        }
        
        return resultVO;
    }
    
    public StatisticResultVO getStatUserSector(StatisticSearchVO searchVO) throws SQLException, DAOBaseException {
        StatisticVO vo = null;
        ArrayList voList = new ArrayList();
        StatisticResultVO resultVO = new StatisticResultVO();
        
        try {
            getConnection(dsname);
            
            String query = loadQueryString("sql.statistic.getStatUserSector");
            openPreparedStatementForR(query, false);
            executeQueryForR();
            
            while (rs.next()) {
                vo = new StatisticVO();
                vo.setSector1(rs.getInt("SECTOR1"));
                vo.setSector2(rs.getInt("SECTOR2"));
                vo.setSector3(rs.getInt("SECTOR3"));
                vo.setSector4(rs.getInt("SECTOR4"));
                vo.setSector5(rs.getInt("SECTOR5"));
                vo.setSector6(rs.getInt("SECTOR6"));
                vo.setSector7(rs.getInt("SECTOR7"));
                
                voList.add(vo);
            }
            
            resultVO.setVoList(voList);
        } catch (Exception e) {
            throwDAOBaseException(e, "not");
            System.out.println(e.getStackTrace());
            return null;
        } finally {
            close();
        }
        
        return resultVO;
    }
}
