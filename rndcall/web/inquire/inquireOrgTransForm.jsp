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
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<%@ page import="kr.go.rndcall.mgnt.login.LoginVO" %>
<bean:define name="InquireForm" property="errCd" id="errCd" type="java.lang.String"/>
<bean:define id="path" type="java.lang.String" value="/Inquire.do"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-Type" content="text/html; charset=utf-8" />
<title>타기관담당자지정</title>
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
<%
  boolean mainIsLogin =false;
  LoginVO mainLoginVO = (LoginVO) session.getAttribute("loginUserVO");
  if(mainLoginVO != null && mainLoginVO.getLogin_id()!=null && !mainLoginVO.getLogin_id().equals("guest")) {
	  mainIsLogin = true;
  }

  String mainRoleCD = "guest";
  String nameKO = "";
//  String login_id = "";
  String menu_sn = "";
  
  menu_sn = (String) request.getParameter("searchVO.menu_sn");
  System.out.println("111111111111111111111111"+ (String)request.getAttribute("searchVO.menu_sn"));
  if(menu_sn =="" || menu_sn==null || menu_sn == "null") menu_sn = (String)request.getAttribute("searchVO.menu_sn");
  
  System.out.println(" top 페이지 menu_sn::"+menu_sn);

  if (mainLoginVO != null && mainIsLogin) {	
	  mainRoleCD = mainLoginVO.getRoleCD();
	  nameKO = mainLoginVO.getName();
//	  login_id = mainLoginVO.getLogin_id();
  }
%>

<%
	if (mainLoginVO == null || !mainIsLogin) {	
%>
	<script type="text/javascript">
		alert('로그인이 필요한 메뉴입니다.');
		self.close();
	</script>
<%
	} else if (!mainRoleCD.equals("0000B") && !mainRoleCD.equals("0000C") && !mainRoleCD.equals("0000Z")) {
%>
	<script type="text/javascript">
		alert('권한이 없습니다.');
		self.close();
	</script>
<%		
	}
%>
<script type="text/javascript">
<!--
	window.onload=function(){
		if("<%=errCd%>" == "true"){
			alert("타기관 담당자가 지정되었습니다.");			
			//refreshOpener();
			self.close();
		}else if("<%=errCd%>" == "false"){
			alert("타기관 담당자 지정을 실패했습니다.");			
			//refreshOpener();
			self.close();
			
		}
	}
	
	function refreshOpener() {
		//parent.document.fm.target = "_self";
		opener.document.fm.elements["method"].value = "getInquireView";
		opener.document.fm.submit();
	}
	
	function goOrgTransInsert(){
		alert('준비중입니다.');
//		if(validate()){
//			if(confirm("타기관 담당자를 지정하시겠습니까?")){
//				frm.elements["vo.public_trans_id"].value=frm.elements["org_trans_list"].value;
//				frm.elements["method"].value="getOrgTransInsert";
//				frm.target="orgTransForm";
//				frm.submit();
//			}
//		}
	}
	
	function validate() {
		//부처명 선택
		if (isRequired(frm.elements["org_trans_list"])){
			return false;
		}	
	 	return true;
	}
	
	function goTransChange(){
		frm.elements["searchVO.trans_id"].value = frm.elements["org_trans_list"].value;
		frm.elements["method"].value="getTransChange";
		frm.submit();
	}
//-->
</script>
<!-- start # LY-PopLayout  팝업 레이아웃 -->
<div class="LY-PopLayout" style="width:410px;">
	<!-- start # 타이틀 -->
	<ul class="Header">
		<li class="Title">타기관지정</li>
		<li class="Img"></li>
	</ul>
	<!-- start # 컨텐츠 -->
	<ul class="Content">
		<li>
		<html:form action="/Inquire" method="post" name="frm" type="kr.go.rndcall.mgnt.inquire.form.InquireForm" onsubmit="return checkOnSubmit(this)">
		<html:hidden name="InquireForm" property="method" value="getOrgTransInsert"/>
		<html:hidden name="InquireForm" property="searchVO.seq"/>
		<html:hidden name="InquireForm" property="vo.public_trans_id"/>		
		<html:hidden name="InquireForm" property="searchVO.trans_id"/>
		
		<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
		<colgroup>
			<col width="120px" />
			<col width="*" />
		</colgroup>
		<tr>
			<th class="Btmline">부처명</th>
			<td><bean:define name="InquireForm" property="searchVO.trans_id" id="trans_id" type="java.lang.String"/>
				<select name="org_trans_list" onchange="JavaScript:goTransChange()">  
				<logic:empty name="InquireForm" property="voList">
					<option value="00">등록된 타기관담당자가 없습니다.
				</logic:empty>
				<logic:notEmpty name="InquireForm" property="voList">
					<option value="">==부처선택==</option>
					<logic:iterate name="InquireForm" property="voList" indexId="rowNum" id="loginVO">
						<bean:define name="loginVO" property="login_id" id="login_id" type="java.lang.String"/>				
						<option value="<bean:write name="loginVO" property="login_id"/>" <% if(login_id.equals(trans_id)){ %> selected<% } %>><bean:write name="loginVO" property="org_nm"/>:<bean:write name="loginVO" property="attached_nm"/>
					</logic:iterate>
				</logic:notEmpty>
				</select>				
			</td>
		</tr>
		<tt>
			<th class="Btmline">담당자이메일</th>
			<td><html:text name="InquireForm" property="searchVO.trans_email" title="담당자이메일" styleClass="lineInputKO" size="40" maxlength="35"onchange="trim(this)"  /></td>
		</tr>
		<tt>
			<th class="Btmline">담당자연락처</th>
			<td><html:text name="InquireForm" property="searchVO.trans_phone" title="담당자연락처" styleClass="lineInputKO" size="40" maxlength="35"onchange="trim(this)"  /></td>
		</tr>
		</table>
		</li>
	</ul>
	<!-- start # 버튼 -->
	<ul class="Button">
		<li><a href="JavaScript:goOrgTransInsert()" class="btn_BSMS"><strong>발송</strong></a> <a href="JavaScript:self.close()" class="btn_BClose"><strong>창닫기</strong></a></li>
	</ul>
</div>
<!-- end # LY-PopLayout  팝업 레이아웃 -->
</html:form>
</head>
</html>
				
					