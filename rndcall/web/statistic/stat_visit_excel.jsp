<%@ page language="java" contentType="application/vnd.ms-excel; name='excel', text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@ taglib uri="/tags/taglibs-log" prefix="log"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<bean:define name="StatisticForm" property="searchVO.stat_type" id="stat_type" type="java.lang.String"/>
<bean:define name="StatisticForm" property="searchVO.start_yy" id="start_yy" type="java.lang.String"/>
<bean:define name="StatisticForm" property="searchVO.start_mm" id="start_mm" type="java.lang.String"/>

<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
<% 
response.setHeader("Content-Disposition", "attachment;filename=%EC%A0%91%EC%86%8D%EC%9E%90%ED%86%B5%EA%B3%84.XLS"); 
response.setHeader("Content-Description", "JSP Generated Data"); 
%>

<%
	int count=0;
%>
	<body>
			<table border="0" cellspacing="0" cellpadding="0">			
			<tr>
				<th class="center">통계구분</th>
				<td>
<%
					if(stat_type.equals("YY")){
						out.println("년도별검색통계현황");
					}else if(stat_type.equals("MM")){
						out.println(start_yy+"년");
						out.println("월별검색통계현황");
					}else if(stat_type.equals("DD")){
						out.println(start_yy+"년");
						out.println(start_mm+"월");
						out.println("일별검색통계현황");
					}
%>
				</td>
			</tr>
			<tr>
				<th class="center" colspan="2" height="25"></th>
			</tr>			
			</table>
			<table border="1" cellspacing="0" cellpadding="0">
			<colgroup>
				<col width="70px" />
				<col width="100px" />
				<col width="*" />
			</colgroup>
			<tr>
				<th class="center">순번</th>
				<th class="center">기준</th>
				<th class="center">방문자수</th>
			</tr>
			<logic:empty name="StatisticForm" property="voList">
				<tr><td style="border-right:0px;text-align:center; height=:50px;" colspan="7">등록된 정보가 없습니다.</td></tr>
			</logic:empty>
			<logic:notEmpty name="StatisticForm" property="voList">
				<logic:iterate name="StatisticForm" property="voList" indexId="rowNum" id="vo">			
					<tr>
						<td class="center"><%=++count %></td>
						<td style="text-align:left;"><b><bean:write name="vo" property="code"/></b></td>
						<td style="text-align:center;"><bean:write name="vo" property="cnt"/></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>		
			</table>
</body>
</html>