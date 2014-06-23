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
<bean:define name="StatisticForm" property="searchVO.crud" id="crud" type="java.lang.String"/>
<bean:define name="StatisticForm" property="vo1.stat" id="stat" type="java.lang.String"/>
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
		


function goStatList(){
<%
	if(crud.equals("date")){
%>
		fm.elements["method"].value="getStatDataList";
<%
	}else{
%>
		fm.elements["method"].value="getStatList";
<%
	}
%>
	fm.submit();
}

function downLoad(fileNM, saveFileNM, filePath, yn){
    fileDownLoad.elements["fileNM"].value = fileNM;
    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
    fileDownLoad.elements["filePath"].value = filePath;
    fileDownLoad.elements["desCipher"].value = yn;
    fileDownLoad.submit();
}
//-->
</script>
</head>
<body>
<form name="fileDownLoad" method="post" action="/downLoad.do" >
    <input type="hidden" name="fileNM"/>
    <input type="hidden" name="saveFileNM"/>
    <input type="hidden" name="filePath"/>
	<input type="hidden" name="desCipher" value="N"/>
</form>	
<div class="LY-PopLayout">
	<!-- start # 타이틀 -->
	<ul class="Header">
		<li class="Title">통계정보 상세화면</li>
		<li class="Img"></li>
	</ul>
	<!-- start # 컨텐츠 -->
	<ul class="Content">
		<li>
		<html:form action="/Statistic" method="post" name="fm" type="kr.go.rndcall.mgnt.statistic.form.StatisticForm">
		<html:hidden name="StatisticForm" property="method" value="getStatList"/>
		<html:hidden name="StatisticForm" property="searchVO.category1"/>
		<html:hidden name="StatisticForm" property="searchVO.category2"/>
		<html:hidden name="StatisticForm" property="searchVO.whichSearch"/>		
		<html:hidden name="StatisticForm" property="searchVO.whichSearch1"/>	
		<html:hidden name="StatisticForm" property="searchVO.searchTxt"/>	
		<html:hidden name="StatisticForm" property="searchVO.searchTxt1"/>
		<html:hidden name="StatisticForm" property="searchVO.start_yy"/>		
		<html:hidden name="StatisticForm" property="searchVO.start_mm"/>	
		<html:hidden name="StatisticForm" property="searchVO.end_mm"/>
		<html:hidden name="StatisticForm" property="searchVO.crud"/>
		<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
			<colgroup>
				<col width="130px" />
				<col width="*" />
			</colgroup>
			<tr><th class="Btmline">제목</th>
				<td><strong>[<bean:write name="StatisticForm" property="vo1.category1_nm"/>] <bean:write name="StatisticForm" property="vo1.title"/></strong></td>
			</tr>
			<tr><th class="Btmline">등록일</th>
				<td><bean:write name="StatisticForm" property="vo1.reg_dt"/></td>
			</tr>
			<tr><td colspan="2"><bean:write name="StatisticForm" property="vo1.contents" filter="false"/></td></tr>
			<tr><th class="Btmline">첨부파일</th>
				<td>
					<logic:notEmpty name="StatisticForm" property="voList">
						<logic:iterate name="StatisticForm" property="voList" indexId="rowNum" id="attachVO">
							<bean:write name="attachVO" property="file_nm" /><a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');" class="btn_TFileDw"><strong>파일다운로드</strong></a><br/>
						</logic:iterate>
					</logic:notEmpty>
				</td>
			</tr>
<%
		if(stat.equals("Y")){
%>				
			<tr><td class="Right"><img src="/images/icon/icon_reply.gif" alt="답변"/></td>
    			<td><bean:write name="StatisticForm" property="vo1.answerContents" filter="false"/></td>
			</tr>
			<tr><th class="Btmline">첨부파일</th>
				<td>
					<logic:notEmpty name="StatisticForm" property="voList1">
						<logic:iterate name="StatisticForm" property="voList1" indexId="rowNum1" id="attachVO">
							<bean:write name="attachVO" property="file_nm" /><a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');" class="btn_TFileDw"><strong>파일다운로드</strong></a><br/>
						</logic:iterate>
					</logic:notEmpty>
				</td>
			</tr>
<%
		}
%>			
			</table>
			<div class="Basic-Button">
				<ul class="Right">
					<li><a href="JavaScript:goStatList()" class="btn_Basic"><strong>목록</strong></a></li>
				</ul>	
			</div>
			</html:form>
		</li>
	</ul>
</div>
</body>	
</html>