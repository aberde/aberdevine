<%@ page language="java" contentType="application/vnd.ms-excel; name='excel', text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@ taglib uri="/tags/taglibs-log" prefix="log"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>

<bean:define name="StatisticForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
<bean:define name="StatisticForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
<bean:define name="StatisticForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
<bean:define name="StatisticForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="StatisticForm" property="searchVO.loginId" id="loginId" type="java.lang.String"/>
<bean:define name="StatisticForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String"/>
<bean:define id="path" type="java.lang.String" value="/Statistic.do"/>
<% 
response.setHeader("Content-Disposition", "attachment;filename=%ED%86%B5%EA%B3%84%EC%A0%95%EB%B3%B4%EB%A6%AC%EC%8A%A4%ED%8A%B8.xls");
response.setHeader("Content-Description", "JSP Generated Data"); 

int count =0;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>R&D도우미센터</title>
</head>
<body>
<table border="1" cellspacing="0" cellpadding="0" class="Basic-List">
<colgroup>					
	<col width="50px" />
	<col width="90px" />
	<col width="350px" />
	<col width="60px" />
	<col width="50px" />
	<col width="40px" />
	<col width="50px" />
</colgroup>
<thead>
<tr><th>번호</th>
	<th>분류</th>
	<th>제목</th>
	<th>내용</th>
	<th>등록자</th>
	<th>등록일</th>
	<th>상태</th>
	<th>조회수</th>
</tr>
</thead>
<tbody>
<logic:empty name="StatisticForm" property="voList">
	<tr><td style="border-right:0px;text-align:center; height=:50px;" colspan="99">등록된 정보가 없습니다.</td></tr>
</logic:empty>
<logic:notEmpty name="StatisticForm" property="voList">
	<logic:iterate name="StatisticForm" property="voList" indexId="rowNum" id="vo">
		<tr><td><%= ++count   %></td>
			<td><bean:write name="vo" property="category1_nm"/></td>
			<td class="Left">
				<bean:define name="vo" property="title" id="title" type="java.lang.String"/>
<%
				String title_n= "";
				if(title.length() > 16){
					title_n = title.substring(0,16)+"...";
				}else{
					title_n = title;
				}
%> 						
				<%=title_n %>
			</td>
			<td><bean:write name="vo" property="contents"/></td>
			<td><bean:write name="vo" property="reg_nm"/></td>
			<td><bean:write name="vo" property="reg_dt"/></td>
			<td>
				<bean:define name="vo" property="stat" id="stat" type="java.lang.String"/>
<%
				if(stat.equals("Y")){
					out.print("<strong>완료</strong>");
				}else{
					out.print("<strong>처리중</strong>");
				}
%>
			</td>
			<td><bean:write name="vo" property="read_count"/></td>
		</tr>
	</logic:iterate>
</logic:notEmpty>
</tbody>
</table>
</body>	
</html>