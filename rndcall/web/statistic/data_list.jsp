<%@ page contentType="text/html; charset=utf-8" %>

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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>R&D도우미센터</title>
<style type="text/css">
<!--
@import url("/css/Base.css");
@import url("/css/Layout.css");
@import url("/css/Header.css");
@import url("/css/Content.css");
@import url("/css/Footer.css");
@import url("/css/Button.css");
-->
</style>
<script language="JavaScript" src="/js/validate.js"></script>
<script language="JavaScript" src="/js/common.js"></script>

<script type="text/javascript">
<!--
activeDebug = true;  
module = '/switch.do?prefix=/statistic&method=getStatList&page=';

function goSearch(){
	fm.elements["method"].value="getStatDataList";
	fm.submit();
}


function goExcelDown(){
	fm.elements["method"].value="getStatDataExcel";
	fm.submit();
}

function goStatView(arg){
	fm.elements["searchVO.seq"].value=arg;
	fm.elements["method"].value="getStatView";
	fm.submit();
}
//-->
</script>
</head>
<body>
<div class="LY-PopLayout">
	<!-- start # 타이틀 -->
	<ul class="Header">
		<li class="Title">통계정보 상세리스트</li>
		<li class="Img"></li>
	</ul>
	<!-- start # 컨텐츠 -->
	<ul class="Content">
		<li>
		<html:form action="/Statistic" method="post" name="fm" type="kr.go.rndcall.mgnt.statistic.form.StatisticForm">
		<html:hidden name="StatisticForm" property="method" value="getStatDataList"/>
		<html:hidden name="StatisticForm" property="searchVO.start_yy"/>
		<html:hidden name="StatisticForm" property="searchVO.start_mm"/>
		<html:hidden name="StatisticForm" property="searchVO.end_mm"/>
		<html:hidden name="StatisticForm" property="searchVO.whichSearch"/>		
		<html:hidden name="StatisticForm" property="searchVO.searchTxt1"/>
		<html:hidden name="StatisticForm" property="searchVO.seq"/>
		<html:hidden name="StatisticForm" property="searchVO.crud"/>
		<div class="Basic-List-area">
			<!-- start # 리스트 검색 -->
			<ul class="List-Search">		
				<li>
				  	<html:select name="StatisticForm" property="searchVO.whichSearch1" title="검색분류">
			     	<html:option value="all">전체</html:option>
				  	<html:option value="title">제목</html:option>
				   	<html:option value="contents">내용</html:option>
					</html:select>
				</li>
				<li><html:text name="StatisticForm" property="searchVO.searchTxt" title="검색어" size="40"/> </li>
				<li><a href="javascript:goSearch();"><img src="/images/btn/btn_search.gif" alt="검색" border="0" /></a>
					<a href="javascript:goExcelDown();" class="btn_Basic"><strong>엑셀다운로드</strong></a></li>
			</ul>
			
			<ul class="List">
				<table border="0" cellspacing="0" cellpadding="0" class="Basic-List">
				<colgroup>					
					<col width="40px" />
					<col width="100px" />
					<col width="*" />
					<col width="60px" />
					<col width="50px" />
					<col width="40px" />
					<col width="50px" />
				</colgroup>
				<thead>
	  				<tr><th>번호</th>
	   					<th>분류</th>
	   					<th>제목</th>	   					
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
			  			<tr><td><%= totRowCount.intValue() - rowNum.intValue() -  Util.replaceNull((String)pagerOffset, 0) %></td>
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
 							<a href="JavaScript:goStatView('<bean:write name="vo" property="seq"/>');"><%=title_n %></a>
		   					</td>
		   					<td><bean:write name="vo" property="reg_nm"/></td>
		   					<td><bean:write name="vo" property="reg_dt"/></td>
							<td>
								<bean:define name="vo" property="stat" id="stat" type="java.lang.String"/>
<%
								if(stat.equals("Y")){
									out.print("<a href='#' class='btn_Orange' style='color:#FFFFFF;'><strong>완료</strong></a>");
								}else{
									out.print("<a href='#' class='btn_Green' style='color:#FFFFFF;'><strong>처리중</strong></a>");
								}
%>
							</td>
							<td><bean:write name="vo" property="read_count"/></td>
  						</tr>
	  				</logic:iterate>
  				</logic:notEmpty>
				</tbody>
			</table>
			</ul>
			<ul class="Page-Num">
				<%@ include file="/include/page.jsp"%>
			</ul>
			</html:form>
		</li>
	</ul>
</div>
</body>	
</html>