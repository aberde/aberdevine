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
<%@ page import="kr.go.rndcall.mgnt.login.LoginVO" %>

<%
  boolean mainIsLogin =false;
  LoginVO mainLoginVO = (LoginVO) session.getAttribute("loginUserVO");
  if(mainLoginVO != null && mainLoginVO.getLogin_id()!=null && !mainLoginVO.getLogin_id().equals("guest")) {
	  mainIsLogin = true;
	  System.out.println("Login_id : " + mainLoginVO.getLogin_id());
	  System.out.println("RoleCD : " + mainLoginVO.getRoleCD());
  }

  String mainRoleCD = "guest";
  String nameKO = "";
  String login_id = "";

  if (mainLoginVO != null && mainIsLogin) {	
	  mainRoleCD = mainLoginVO.getRoleCD();
	  nameKO = mainLoginVO.getName();
	  login_id = mainLoginVO.getLogin_id();
  }
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>R&amp;D도우미센터</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="http://www.rndcall.go.kr/common/css/main.css" type="text/css" rel="stylesheet"  />
<script src="http://www.rndcall.go.kr/common/js/kr_code.js" language="javascript" type="text/javascript" charset="utf-8"></script>
<script src="http://www.rndcall.go.kr/common/js/main.js" language="javascript" type="text/javascript" charset="utf-8"></script>
<script src="http://www.rndcall.go.kr/common/js/DD_belatedPNG_0.0.8a.js" language="javascript" type="text/javascript" charset="utf-8"></script>
<script src="http://www.rndcall.go.kr/js/common.js" type="text/javascript" ></script>
<link href="/css/basic.css" rel="stylesheet" type="text/css" />
<link href="/css/Content.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="/js/validate.js"></script>
<script language="JavaScript" src="/js/common.js"></script> 
<script type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<script type="text/JavaScript">
<!--
function goData(){
	document.location.href="/switch.do?prefix=/data&page=/Data.do?method=dataList";
} 

function goNotice(){
	document.location.href="/switch.do?prefix=/notice&page=/Notice.do?method=noticeList";
}

function goMypage(){
<% 
	if(mainLoginVO != null && mainIsLogin != false){
		if(!mainRoleCD.equals("0000C") && !mainRoleCD.equals("0000Z")){
%>
			
			document.location.href="/switch.do?prefix=/mypage&page=/Mypage.do?method=getMypageList";
<% 
		}else{
%>		
			alert("로그인후 이용하실수 있습니다.");	
<%
		}
	}else{
%>		
		alert("로그인후 이용하실수 있습니다.");	
<%
	}
%>
}
/**
* 입력여부 체크
*/
function chkNull( str ) {
	if( str.length < 1 || str == "" || str == null ) {
		return true;
	} else {
		return false;
	}
}

function onEnter(){
	if(event.keyCode==13){
		login();
		event.returnValue=false;
	}
}

function login() {	

	//1.아이디
	if ( chkNull( document.all.id.value ) ) {
		alert( reg_idIsNull );
		document.all.id.focus();
		return;
	}
	//2.아이디 형식 체크
	if ( chkNull( document.all.password.value ) ) {
		alert( reg_passIsNull );
		document.all.password.focus();
		return;
	} 
	
	document.fm2.elements["vo.login_id"].value = document.all.id.value;
	document.fm2.elements["vo.password"].value = document.all.password.value;
	document.fm2.submit();
	
}


function update() {	
	document.fm3.elements["vo.login_id"].value = '<%= login_id %>';
	document.fm3.submit();
}
//-->
</script>
<script language="javascript" type="text/javascript">
//<![CDATA[
var subNum  = "0";
var pageNum = "0";
var pageNum2= "0";
DD_belatedPNG.fix('.gnbSub');
DD_belatedPNG.fix('.gnbSub ul');
//]]>
</script>
</head>
<body>
<form name="fm2" method="post" action="/login.do" >
  <input type="hidden" name="method" value="login"/>
  <input type="hidden" name="vo.login_id"/>
  <input type="hidden" name="vo.password"/>
</form>
<div id="mainWrap">
	<div id="wrap">
		<div id="mainheader">
			<div class="headerTop">
				<p class="headertt"><img src="http://www.rndcall.go.kr/common/images/layout/headertt.gif" alt="국가R&amp;D사업 관련 규정 및 제도에 대한 궁금증을 해소하여 드립니다."/></p>
				<div>
					<p>
<% 
					if(mainLoginVO == null || mainIsLogin == false){
%>
						<a href="/switch.do?prefix=&page=/member.do?method=getUserInsertForm">회원가입</a>
						아이디 : <input type="text" name="id" title="아이디" size="10" onkeypress="onEnter()"/>&nbsp;&nbsp;
						패스워드 : <input type="password" name="password" title="패스워드" size="10" onkeypress="onEnter()"/>&nbsp;&nbsp;
						<input type="button" value="로그인" onclick="login();"/>
<%
					}else{
%>
					<b><%= nameKO %></b>님이 로그인하셨습니다. &nbsp; <a href="/logout.jsp">로그아웃</a>&nbsp;&nbsp;<a href="javascript:update();">회원정보변경</a>
					
<%
						if(mainRoleCD.equals("0000B")){
%>
							<a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireOrgList&searchVO.board_type=QNA">기관담당자</a>
<%
						}
					}
%>
					</p>
				</div>
			</div>
			<div class="headerCon">
				<h1><a href="/index.jsp"><img src="http://www.rndcall.go.kr/common/images/layout/logo.gif" alt="국가과학기술위원회 R&amp;D도우미센터"/></a></h1>
				<div id="gnbMenu">
					<ul id="gnb">
						<li class="gnbMenu2 first ">
							<a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireMainList"><img src="http://www.rndcall.go.kr/common/images/layout/gnb1_off.gif" alt="온라인상담"/></a>
							<p class="gnbSub"></p>
						</li>
						<li class="gnbMenu2">
							<a href="JavaScript:goData()"><img src="http://www.rndcall.go.kr/common/images/layout/gnb4_off.gif" alt="자료실"/></a><p class="gnbSub"></p>
						</li>
						<li class="gnbMenu2">
							<a href="JavaScript:goNotice()"><img src="/images/btn/gnb6_off.gif" alt="공지사항"/></a><p class="gnbSub"></p>
						</li>
<%
					if(!mainRoleCD.equals("0000C") && !mainRoleCD.equals("0000Z")){
%>						
						<li class="gnbMenu2 last">
							<a href="JavaScript:goMypage()"><img src="http://www.rndcall.go.kr/common/images/layout/gnb5_off.gif" alt="마이페이지"/></a>
							<p class="gnbSub"></p>
						</li>
<%
					}else{
%>						
						<li class="gnbMenu2 last"><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList">관리자</a>
							<div class="gnbSub gnbSub3">
								<ul>
									<li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList">권한관리</a></li>
									<li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList">분야관리</a></li>
									<li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatCategory">통계관리</a></li>
									<li><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm">오프라인자료등록</a></li>
								</ul>
							</div>
						</li>
<%
					}
%>						
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>