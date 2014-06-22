<%@ page contentType="text/html; charset=utf-8" %>

<%@ page import="java.sql.*" %>
<%@ page import="kr.go.rndcall.mgnt.common.BaseSqlDAO"%>
<%@ page import="kr.go.rndcall.mgnt.statistic.vo.StatisticVO"%>

<%
	String dsname = "jdbc/rndcall";
	BaseSqlDAO dao = new BaseSqlDAO();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	java.util.ArrayList list = new java.util.ArrayList();
	StatisticVO vo = null;
	StatisticVO info = null;
	String cate_nm = "";
	try {
		conn = dao.getConnection_clob();
		
		String  query  = " SELECT C.CATEGORY1_ID, c.CATEGORY2_ID, C.CATEGORY1_NM,C.CATEGORY2_NM, ";
        query += " Q1.total_cnt, Q2.online_cnt, Q3.offline_cnt, Q4.answer_y_cnt, Q5.answer_n_cnt ";
        query += " FROM ( ";
        query += "     SELECT decode(category1,NULL,'9999999999',category1) AS category1,  ";
		query += "	      COUNT(*) AS total_cnt FROM RNDCALL_BOARD_QUESTION ";
        query += "     WHERE board_type='QNA' ";
        query += "   AND DEL_YN='N' ";
        query += " GROUP BY decode(category1,NULL,'9999999999',category1) ";
        query += ") Q1, ";
		query += " ( ";
		query += "    SELECT decode(category1,NULL,'9999999999',category1) AS category1, "; 
		query += "           COUNT(*) AS online_cnt FROM RNDCALL_BOARD_QUESTION ";
		query += "    WHERE board_type='QNA' ";
		query += "          AND DEL_YN='N' ";
		query += "          AND INSERT_TYPE='ONLINE'    ";
		query += "    GROUP BY decode(category1,NULL,'9999999999',category1) ";
		query += ") Q2, ";
		query += " ( ";
		query += "    SELECT decode(category1,NULL,'9999999999',category1) AS category1,  ";
		query += "           COUNT(*) AS offline_cnt FROM RNDCALL_BOARD_QUESTION ";
		query += "    WHERE board_type='QNA' ";
		query += "          AND DEL_YN='N' ";
		query += "          AND INSERT_TYPE='OFFLINE' ";
		query += "    GROUP BY decode(category1,NULL,'9999999999',category1) ";
		query += ") Q3, ";
		query += " ( ";
		query += "    SELECT decode(category1,NULL,'9999999999',category1) AS category1,  ";
		query += "           COUNT(*) AS answer_y_cnt FROM RNDCALL_BOARD_QUESTION Q ";
		query += "    WHERE board_type='QNA' ";
		query += "          AND DEL_YN='N' ";
		query += "          AND EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER S WHERE Q.SEQ = S.Q_SEQ) ";
		query += "    GROUP BY decode(category1,NULL,'9999999999',category1) ";
		query += ") Q4, ";
		query += " ( ";
		query += "    SELECT decode(category1,NULL,'9999999999',category1) AS category1,  ";
		query += "           COUNT(*) AS answer_n_cnt FROM RNDCALL_BOARD_QUESTION Q ";
		query += "    WHERE board_type='QNA' ";
		query += "          AND DEL_YN='N' ";
		query += "          AND NOT EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER S WHERE Q.SEQ = S.Q_SEQ) ";
		query += "    GROUP BY decode(category1,NULL,'9999999999',category1) ";
		query += ") Q5, ";
		query += "( ";
		query += "	SELECT A.CATEGORY_ID AS CATEGORY1_ID, ";
		query += "         DECODE(b.CATEGORY_ID,NULL, A.CATEGORY_ID,b.CATEGORY_ID) AS CATEGORY2_ID, ";
		query += "         a.CATEGORY_NM AS CATEGORY1_NM,  ";
		query += "         b.CATEGORY_NM AS CATEGORY2_NM  ";
		query += "  FROM (SELECT CATEGORY_ID ,CATEGORY_NM, parent_id FROM RNDCALL_CATEGORY WHERE ISUSE='Y') a,  ";
		query += "       (SELECT CATEGORY_ID ,CATEGORY_NM, parent_id, order_no FROM RNDCALL_CATEGORY WHERE ISUSE='Y') b   ";
		query += "  WHERE a.CATEGORY_ID = b.PARENT_ID(+)  ";
		query += "        AND a.parent_id = '0' AND a.CATEGORY_ID <> 7 ";
		query += "  ORDER BY a.CATEGORY_ID, b.ORDER_NO ASC ";
		query += ") C ";
		query += " WHERE C.CATEGORY1_ID =Q1.category1(+)  ";
		query += "      AND C.CATEGORY1_ID =Q2.category1(+)  ";  
		query += "      AND C.CATEGORY1_ID =Q3.category1(+)  ";  
		query += "      AND C.CATEGORY1_ID =Q4.category1(+)  ";  
		query += "      AND C.CATEGORY1_ID =Q5.category1(+)  ";
		query += " ORDER BY CATEGORY1_ID, CATEGORY2_ID ASC ";
		
		pstmt = conn.prepareStatement(query);
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			vo =  new StatisticVO();
			vo.setCategory1_nm(rs.getString("CATEGORY1_NM"));
			vo.setCategory2_nm(rs.getString("CATEGORY2_NM"));
			vo.setCategory1(rs.getString("CATEGORY1_ID"));
			vo.setCategory2(rs.getString("CATEGORY2_ID"));
			vo.setTotal_cnt(rs.getInt("TOTAL_CNT"));				
			vo.setOnline_cnt(rs.getInt("ONLINE_CNT"));		//온라인건수
			vo.setOffline_cnt(rs.getInt("OFFLINE_CNT"));		//오프라인건수
			vo.setDisposal_cnt(rs.getInt("ANSWER_Y_CNT"));		//처리건수
			vo.setUndisposal_cnt(rs.getInt("ANSWER_N_CNT"));		//미처리건수
			
			list.add(vo);			
		}
		
		rs.close();
		pstmt.close();
		conn.close();
	} catch (Exception ee) {
		ee.printStackTrace(System.out);		
		if(rs != null) rs.close();
		if(pstmt != null) pstmt.close();
		if(conn != null) conn.close();		
	} finally {		
		if(rs != null) rs.close();
		if(pstmt != null) pstmt.close();
		if(conn != null) conn.close();
	}
	
%>
<chart caption="" XAxisName="분야" palette="2" animation="1" formatNumberScale="0" numberPrefix="" showValues="0" numDivLines="4" legendPosition="BOTTOM">
	<categories>
		<category label="전체" />
		<category label="온라인" />
		<category label="오프라인" />
		<category label="처리" />
		<category label="미처리" />
	</categories>
<%
	for(int i=0; i<list.size(); i++){
		info = (StatisticVO)list.get(i);
		cate_nm = info.getCategory1_nm();
%>
	<dataset seriesName="<%=cate_nm %>">
		<set value="<%=info.getTotal_cnt() %>" />
		<set value="<%=info.getOnline_cnt() %>" />
		<set value="<%=info.getOffline_cnt() %>" />
		<set value="<%=info.getDisposal_cnt() %>" />
		<set value="<%=info.getUndisposal_cnt() %>" />
	</dataset>		
<%
	}
%>
	<styles>
		<definition>
			<style type="font" name="CaptionFont" color="666666" size="15" />
			<style type="font" name="SubCaptionFont" bold="0" />
		</definition>
		<application>
			<apply toObject="caption" styles="CaptionFont" />
			<apply toObject="SubCaption" styles="SubCaptionFont" />
		</application>
	</styles>
</chart>