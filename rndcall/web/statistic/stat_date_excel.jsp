<%@ page language="java" contentType="application/vnd.ms-excel; name='excel', text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@ taglib uri="/tags/taglibs-log" prefix="log"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
<% 
response.setHeader("Content-Disposition", "attachment;filename=%EA%B8%B0%EA%B0%84%EB%B3%84%ED%86%B5%EA%B3%84.xls"); 
response.setHeader("Content-Description", "JSP Generated Data"); 
%>

<%
	int cnt1 = 0;
	int cnt2 = 0;
	int cnt3 = 0;
	int cnt4 = 0;
	int cnt5 = 0;
	int count=0;
%>
			<table border="1" cellspacing="0" cellpadding="0" class="SearchTable-view">
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
				<th class="center" rowspan="2">순번</th>
				<th class="center" rowspan="2">월별</th>
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
%>					
					<tr>
						<td class="center"><%=++count %></td>
						<td style="text-align:left;"><b><bean:write name="vo" property="stat_mm"/></b></td>
						<td style="text-align:left;"><bean:write name="vo" property="total_cnt"/></td>
						<td style="text-align:left;"><bean:write name="vo" property="online_cnt"/></td>
						<td style="text-align:center;"><bean:write name="vo" property="offline_cnt"/></td>
						<td style="text-align:center;"><bean:write name="vo" property="disposal_cnt"/></td>						
						<td style="text-align:center;"><bean:write name="vo" property="undisposal_cnt"/></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
			<tr><td class="center"></td>
				<td style="text-align:left;"><b>총건수</b></td>
				<td style="text-align:center;"><%=cnt1%></td>
				<td style="text-align:center;"><%=cnt2%></td>
				<td style="text-align:center;"><%=cnt3%></td>
				<td style="text-align:center;"><%=cnt4%></td>						
				<td style="text-align:center;"><%=cnt5%></td>
			</tr>			
			</table>
			</html>