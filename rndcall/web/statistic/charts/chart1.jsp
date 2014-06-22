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
	
	String start_yy = request.getParameter("start_yy");
	String start_mm = request.getParameter("start_mm");
	String end_mm = request.getParameter("end_mm");
	
	String start_yymm =start_yy+""+start_mm;
	String end_yymm =start_yy+""+end_mm;
	
	
	try {
		conn = dao.getConnection_clob();

		String  query  = " SELECT tot.mm,tot.CD_DTL_NM, total_cnt, online_cnt, offline_cnt ,answer_y_cnt,answer_n_cnt ";
				query += " FROM ( ";
				query += "       SELECT TO_NUMBER(SUBSTR(B.CD_DTL_ID,3,5)) AS mm, B.CD_DTL_NM, NVL(total_cnt,'0') AS total_cnt ";
				query += "       FROM ( ";
				query += "             SELECT TO_CHAR(Q.reg_dt,'mm') AS date_mm, COUNT(*) AS total_cnt FROM RNDCALL_BOARD_QUESTION Q ";
				query += "	            WHERE Q.board_type='QNA' ";
				query += "                   AND Q.DEL_YN='N' ";
				query += "                   AND TO_CHAR(Q.reg_dt,'yyyymm') BETWEEN "+start_yymm+" AND  "+end_yymm;
				query += "             GROUP BY TO_CHAR(Q.reg_dt,'mm') ";
				query += "            ) A, RNDCALL_CODE B ";
				query += "       WHERE a.date_mm(+) = TO_NUMBER(SUBSTR(B.CD_DTL_ID,3,5)) ";
				query += "             AND B.cd_id='100' ";
				query += "       ) tot, ";
				query += "       ( ";
				query += "       SELECT TO_NUMBER(SUBSTR(B.CD_DTL_ID,3,5)) AS mm, B.CD_DTL_NM, NVL(online_cnt,'0') AS online_cnt ";
				query += "       FROM ( ";
				query += "             SELECT TO_CHAR(Q.reg_dt,'mm') AS date_mm, COUNT(*) AS online_cnt FROM RNDCALL_BOARD_QUESTION Q ";
				query += "             WHERE Q.board_type='QNA' ";
				query += "                  AND Q.DEL_YN='N' ";
				query += "                   AND Q.INSERT_TYPE='ONLINE' ";
				query += "                   AND TO_CHAR(Q.reg_dt,'yyyymm') BETWEEN "+start_yymm+" AND  "+end_yymm;
				query += "             GROUP BY TO_CHAR(Q.reg_dt,'mm') ";
				query += "             ) A, RNDCALL_CODE B ";
				query += "       WHERE a.date_mm(+) = TO_NUMBER(SUBSTR(B.CD_DTL_ID,3,5)) ";
				query += "             AND B.cd_id='100'  ";
				query += "       ) onL, ";
				query += "       ( ";
				query += "      SELECT TO_NUMBER(SUBSTR(B.CD_DTL_ID,3,5)) AS mm, B.CD_DTL_NM, NVL(offline_cnt,'0') AS offline_cnt ";
				query += "       FROM ( ";
				query += "             SELECT TO_CHAR(Q.reg_dt,'mm') AS date_mm, COUNT(*) AS offline_cnt FROM RNDCALL_BOARD_QUESTION Q ";
				query += "             WHERE Q.board_type='QNA' ";
				query += "                   AND Q.DEL_YN='N' ";
				query += "                   AND Q.INSERT_TYPE='OFFLINE' ";
				query += "                   AND TO_CHAR(Q.reg_dt,'yyyymm') BETWEEN "+start_yymm+" AND  "+end_yymm;
				query += "             GROUP BY TO_CHAR(Q.reg_dt,'mm') ";
				query += "             ) A, RNDCALL_CODE B ";
				query += "      WHERE a.date_mm(+) = TO_NUMBER(SUBSTR(B.CD_DTL_ID,3,5)) ";
				query += "             AND B.cd_id='100'  ";
				query += "       ) offL, ";
				query += "       ( ";
				query += "       SELECT TO_NUMBER(SUBSTR(B.CD_DTL_ID,3,5)) AS mm, B.CD_DTL_NM, NVL(answer_y_cnt,'0') AS answer_y_cnt ";
				query += "       FROM ( ";
				query += "             SELECT TO_CHAR(Q.reg_dt,'mm') AS date_mm, COUNT(*) AS answer_y_cnt FROM RNDCALL_BOARD_QUESTION Q ";
				query += "             WHERE Q.board_type='QNA' ";
				query += "                   AND Q.DEL_YN='N' ";
				query += "                  AND EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER S WHERE Q.SEQ = S.Q_SEQ) ";
				query += "                  AND TO_CHAR(Q.reg_dt,'yyyymm') BETWEEN "+start_yymm+" AND  "+end_yymm;
				query += "	            GROUP BY TO_CHAR(Q.reg_dt,'mm') ";
				query += "             ) A, RNDCALL_CODE B ";
				query += "       WHERE a.date_mm(+) = TO_NUMBER(SUBSTR(B.CD_DTL_ID,3,5)) ";
				query += "             AND B.cd_id='100'  ";
				query += "       ) answer_y, ";
				query += "       ( ";
				query += "       SELECT TO_NUMBER(SUBSTR(B.CD_DTL_ID,3,5)) AS mm, B.CD_DTL_NM, NVL(answer_n_cnt,'0') AS answer_n_cnt ";
				query += "       FROM ( ";
				query += "             SELECT TO_CHAR(Q.reg_dt,'mm') AS date_mm, COUNT(*) AS answer_n_cnt FROM RNDCALL_BOARD_QUESTION Q ";
				query += "            WHERE Q.board_type='QNA' ";
				query += "                   AND Q.DEL_YN='N' ";
				query += "                   AND NOT EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER S WHERE Q.SEQ = S.Q_SEQ) ";
				query += "                   AND TO_CHAR(Q.reg_dt,'yyyymm') BETWEEN "+start_yymm+" AND  "+end_yymm;
				query += "             GROUP BY TO_CHAR(Q.reg_dt,'mm') ";
				query += "             ) A, RNDCALL_CODE B ";
				query += "       WHERE a.date_mm(+) = TO_NUMBER(SUBSTR(B.CD_DTL_ID,3,5)) ";
				query += "             AND B.cd_id='100'  ";
				query += "       ) answer_n ";
				query += " WHERE tot.mm = onL.mm(+) ";
				query += "       AND tot.mm = offL.mm(+) ";
				query += "       AND tot.mm = answer_y.mm(+) ";
				query += "       AND tot.mm = answer_n.mm(+) ";
				query += "       AND tot.mm BETWEEN " + start_mm + " AND "+ end_mm  ;
				query += " ORDER BY TO_NUMBER(tot.mm) asc ";
		
		pstmt = conn.prepareStatement(query);
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			vo =  new StatisticVO();
			vo.setStat_mm(rs.getString("CD_DTL_NM"));
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
<chart caption="" XAxisName="월별" palette="2" animation="1" formatNumberScale="0" numberPrefix="" showValues="0" numDivLines="4" legendPosition="BOTTOM">
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
%>
<dataset seriesName="<%=info.getStat_mm() %>">
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