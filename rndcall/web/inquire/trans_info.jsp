<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!--
### 경로   : <%= request.getRequestURI() %>
### 사업명 : 
### 사업일 : 
### 개발일 : 
### 개발자 : 
### Copyright (C) 2007 by SYSTEM GATE INC. All right reserved. 
-->
<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@ taglib uri="/tags/taglibs-log" prefix="log"%>
<bean:define name="InquireForm" property="errCd" id="errCd" type="java.lang.String"/>
<bean:define id="path" type="java.lang.String" value="/Inquire.do"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-Type" content="text/html; charset=utf-8" />
<title>타기관담당자정보</title>
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
<!-- start # LY-PopLayout  팝업 레이아웃 -->
<div class="LY-PopLayout" style="width:410px;">
	<!-- start # 타이틀 -->
	<ul class="Header">
		<li class="Title">타기관담당자정보</li>
		<li class="Img"></li>
	</ul>
	<!-- start # 컨텐츠 -->
	<ul class="Content">
		<li>
		<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
		<colgroup>
			<col width="120px" />
			<col width="*" />
		</colgroup>
		<tr>
			<th class="Btmline">ID</th>
			<td><bean:write name="InquireForm" property="loginVO.username"/></td>
		</tr>
		<tr>
			<th class="Btmline">이름</th>
			<td><bean:write name="InquireForm" property="loginVO.name"/></td>
		</tr>
		<tr>
			<th class="Btmline">부처명</th>
			<td><bean:write name="InquireForm" property="loginVO.org_nm"/></td>
		</tr>
		<tr>
			<th class="Btmline">소속부서명</th>
			<td><bean:write name="InquireForm" property="loginVO.attached_nm"/></td>
		</tr>
		<tr>
			<th class="Btmline">핸드폰번호</th>
			<td><bean:write name="InquireForm" property="loginVO.mobile"/></td>
		</tr>
		<tr>
			<th class="Btmline">이메일</th>
			<td><bean:write name="InquireForm" property="loginVO.email"/></td>
		</tr>
		</table>
		</li>
	</ul>
	<!-- start # 버튼 -->
	<ul class="Button">
		<li><a href="JavaScript:self.close()" class="btn_BClose"><strong>창닫기</strong></a></li>
	</ul>
</div>
<!-- end # LY-PopLayout  팝업 레이아웃 -->
</head>
</html>
				
					