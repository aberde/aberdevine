<%@page import="java.net.URLEncoder"%>
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
<bean:define name="StatisticForm" property="searchVO.excelForm" id="excelForm" type="java.lang.String"/>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <style type="text/css">
        #tbl0 th {border: 1px solid}
        #tbl0 td {border: 1px solid}
    </style>
</head>
<% 
response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("분류별통계2", "utf-8") + ".xls"); 
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
        <tr>
            <td colspan="2">			
                <%= excelForm %>
            </td>
        </tr>
    </table>
</body>
</html>	