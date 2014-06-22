<%@ page language="java" contentType="application/vnd.ms-excel; name='excel', text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@ taglib uri="/tags/taglibs-log" prefix="log"%>
			
<bean:define name="StatisticForm" property="searchVO.start_yy" id="start_yy" type="java.lang.String"/>
<bean:define name="StatisticForm" property="searchVO.start_mm" id="start_mm" type="java.lang.String"/>
<bean:define name="StatisticForm" property="searchVO.start_yy" id="start_yy" type="java.lang.String"/>
<bean:define name="StatisticForm" property="searchVO.end_mm" id="end_mm" type="java.lang.String"/>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<%
	int cnt1 = 0;
	int cnt2 = 0;
	int cnt3 = 0;
	int cnt4 = 0;
	int cnt5 = 0;
	int cnt1_1 = 0;
	int cnt2_1 = 0;
	int cnt3_1 = 0;
	int cnt4_1 = 0;
	int cnt5_1 = 0;
	
	int count=0;
	String old_cate="";
	String old_cate_nm="";
	String new_category2= "";
%>
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
<% 
response.setHeader("Content-Disposition", "attachment;filename=%EB%B6%84%EC%95%BC%EB%B3%84%ED%86%B5%EA%B3%84.xls"); 
response.setHeader("Content-Description", "JSP Generated Data"); 
%>
			<body>
			<table border="0" cellspacing="0" cellpadding="0">			
			<tr>
				<th class="center">통계년도</th>
				<td><%=start_yy%>년<%=start_mm%>월 ~ <%=start_yy%>년<%=end_mm%>월</td>
			</tr>
			<tr>
				<th class="center" colspan="2" height="25"></th>
			</tr>			
			</table>
			<table border="1" cellspacing="0" cellpadding="0">
			<colgroup>
				<col width="100px" />
				<col width="*" />
				<col width="80px" />
				<col width="80px" />
				<col width="80px" />
				<col width="100px" />
				<col width="100px" />
			</colgroup>
			<tr>
				<th class="center" rowspan="2">대분류명</th>
				<th class="center" rowspan="2">소분류명</th>
				<th class="center" colspan="3">등록건수</th>
				<th class="center" rowspan="2">처리건수</th>
				<th class="center" rowspan="2">미처리건수</th>
			</tr>
			<tr>
				<th class="center">전체건수</th>
				<th class="center">온라인</th>
				<th class="center">오프라인</th>
			</tr>
			<logic:empty name="StatisticForm" property="voList">
				<tr><td style="border-right:0px;text-align:center; height=:50px;" colspan="7">등록된 정보가 없습니다.</td></tr>
			</logic:empty>
			<logic:notEmpty name="StatisticForm" property="voList">
				<logic:iterate name="StatisticForm" property="voList" indexId="rowNum" id="vo">
					<bean:define name="vo" property="category1" id="category1" type="java.lang.String"/>
					<bean:define name="vo" property="category2" id="category2" type="java.lang.String"/>					
					<bean:define name="vo" property="category1_nm" id="category1_nm" type="java.lang.String"/>
					<bean:define name="vo" property="total_cnt" id="total_cnt" type="java.lang.Integer"/>
					<bean:define name="vo" property="online_cnt" id="online_cnt" type="java.lang.Integer"/>
					<bean:define name="vo" property="offline_cnt" id="offline_cnt" type="java.lang.Integer"/>
					<bean:define name="vo" property="disposal_cnt" id="disposal_cnt" type="java.lang.Integer"/>						
					<bean:define name="vo" property="undisposal_cnt" id="undisposal_cnt" type="java.lang.Integer"/>
<%
					cnt1 += total_cnt.intValue();
					cnt2 += online_cnt.intValue();
					cnt3 += offline_cnt.intValue();
					cnt4 += disposal_cnt.intValue();
					cnt5 += undisposal_cnt.intValue();
					if(category1.equals(category2)){
						new_category2 ="";
					}else{
						new_category2 = category2;
					}
					if(category1.equals(old_cate)){
						cnt1_1 += total_cnt.intValue();
						cnt2_1 += online_cnt.intValue();
						cnt3_1 += offline_cnt.intValue();
						cnt4_1 += disposal_cnt.intValue();
						cnt5_1 += undisposal_cnt.intValue();
					}else{
						if(!old_cate.equals("")&& !old_cate.equals("1")&&!old_cate.equals("2")&&!old_cate.equals("6")){
%>
						<tr>
							<td style="text-align:left;"><%=old_cate_nm %></td>
							<td style="text-align:left;"><b>소계</b></td>
							<td style="text-align:center;"><b><%=cnt1_1%></b></td>
							<td style="text-align:center;"><b><%=cnt2_1%></b></td>
							<td style="text-align:center;"><b><%=cnt3_1%></b></td>
							<td style="text-align:center;"><b><%=cnt4_1%></b></td>						
							<td style="text-align:center;"><b><%=cnt5_1%></b></td>	
						</tr>
<%
						}
						cnt1_1 = 0;
						cnt2_1 = 0;
						cnt3_1 = 0;
						cnt4_1 = 0;
						cnt5_1 = 0;
						cnt1_1 += total_cnt.intValue();
						cnt2_1 += online_cnt.intValue();
						cnt3_1 += offline_cnt.intValue();
						cnt4_1 += disposal_cnt.intValue();
						cnt5_1 += undisposal_cnt.intValue();
						old_cate =category1;
						old_cate_nm=category1_nm;
					}
%>

					<tr>
						<td style="text-align:left;"><bean:write name="vo" property="category1_nm"/></td>
						<td style="text-align:left;"><bean:write name="vo" property="category2_nm"/></td>
						<td style="text-align:center;"><bean:write name="vo" property="total_cnt"/></td>
						<td style="text-align:center;"><bean:write name="vo" property="online_cnt"/></td>
						<td style="text-align:center;"><bean:write name="vo" property="offline_cnt"/></td>
						<td style="text-align:center;"><bean:write name="vo" property="disposal_cnt"/></td>						
						<td style="text-align:center;"><bean:write name="vo" property="undisposal_cnt"/></td>
					</tr>			
				</logic:iterate>
			</logic:notEmpty>
			<tr bgcolor="#e0ffff">
				<td style="text-align:left;" colspan="2"><b>총건수</b></td>
				<td style="text-align:center;"><b><%=cnt1%></b></td>
				<td style="text-align:center;"><b><%=cnt2%></b></td>
				<td style="text-align:center;"><b><%=cnt3%></b></td>
				<td style="text-align:center;"><b><%=cnt4%></b></td>						
				<td style="text-align:center;"><b><%=cnt5%></b></td>
			</tr>
			</table>
			</body>
</html>	