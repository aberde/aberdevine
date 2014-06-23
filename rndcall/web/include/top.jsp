﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
### 경로   : <%= request.getRequestURI() %>
### 사업명 : 2012년 사업고나리 서비스 - R&D도우미센터
### 사업일 : 2012.04 ~2012.10
### 개발일 : 2012.09.17
### 개발자 : 박인선
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

<%
  boolean mainIsLogin =false;
  LoginVO mainLoginVO = (LoginVO) session.getAttribute("loginUserVO");
  if(mainLoginVO != null && mainLoginVO.getLogin_id()!=null && !mainLoginVO.getLogin_id().equals("guest")) {
	  mainIsLogin = true;
  }

  String mainRoleCD = "guest";
  String nameKO = "";
  String login_id = "";
  String menu_sn = "";
  
  menu_sn = (String) request.getParameter("searchVO.menu_sn");
  System.out.println("111111111111111111111111"+ (String)request.getAttribute("searchVO.menu_sn"));
  if(menu_sn =="" || menu_sn==null || menu_sn == "null") menu_sn = (String)request.getAttribute("searchVO.menu_sn");
  
  System.out.println(" top 페이지 menu_sn::"+menu_sn);

  if (mainLoginVO != null && mainIsLogin) {	
	  mainRoleCD = mainLoginVO.getRoleCD();
	  nameKO = mainLoginVO.getName();
	  login_id = mainLoginVO.getLogin_id();
  }
%>
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
@import url("/css/Admin.css");
-->
</style>
<script language="JavaScript" src="/js/ajax.js"></script>
<script language="JavaScript" src="/js/prototype.js"></script>
<script language="JavaScript" src="/js/Field.js"></script>
<script language="JavaScript" src="/js/EventMenu.js"></script>
<script language="JavaScript" src="/js/common.js"></script> 
<script language="JavaScript" src="/js/validate.js"></script> 
<script language="javascript" type="text/javascript">

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

function update() {	
	document.fm3.elements["vo.login_id"].value = '<%= login_id %>';
	document.fm3.submit();
}

//문의하기 링크
function goInquireForm(){
	if("<%=login_id%>" == ""){
		alert("로그인 후 이용하실 수 있습니다.");
		return;
	}else{		
		document.location.href = "/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireForm&searchVO.menu_sn=01";
	}
}

//Q&A링크
function goInquireList(){
	document.location.href = "/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireList&searchVO.board_type=QNA&searchVO.type=&searchVO.searchCategory=&searchVO.menu_sn=01";
}

//제안하기 질문 링크
function goOffer(){
	var url;
	if("<%=login_id%>" == ""){
		alert("로그인 후 이용하실 수 있습니다.");
		return;		
	}else{
		if("<%=mainRoleCD%>" == "0000Z" || "<%=mainRoleCD%>" == "0000C"){
			url ="/switch.do?prefix=/offer&page=/Offer.do?method=offerList&searchVO.menu_sn=14";
		}else{
			url ="/switch.do?prefix=/offer&page=/Offer.do?method=offerInsertForm&searchVO.menu_sn=14";
		}
		document.location.href = url;
	}
}

//자주하느질문 링크
function goFaq(){
	var url ="/switch.do?prefix=/faq&page=/Faq.do?method=faqList&searchVO.menu_sn=01";
	document.location.href = url;
}

//자료실
function goData(){
	var url ="/switch.do?prefix=/data&page=/Data.do?method=dataList&searchVO.menu_sn=02";
	document.location.href = url;
}
//공지사항
function goNotice(){
	var url ="/switch.do?prefix=/notice&page=/Notice.do?method=noticeList&searchVO.menu_sn=04";
	document.location.href = url;
}

//마이페이지
function goMypage(){
<% 
	if(mainLoginVO != null && mainIsLogin != false){
%>		
			document.location.href="/switch.do?prefix=/mypage&page=/Mypage.do?method=getMypageList&searchVO.menu_sn=03";
<% 
	}else{
%>		
		alert("로그인후 이용하실수 있습니다.");	
<%
	}
%>
}
function pageSubmit(fmObj, module, pageUrl) {
    fmObj.action = module + pageUrl;
    fmObj.submit();
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

function login() {	
	
	var winfeatures = "toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=525,height=305";
	var url = "/login.jsp?returnURL=" + escape(document.location.href);

	loginWin = window.open( url, "login", winfeatures);
}

function goUniSearch() {
//	if("<%=login_id%>" == ""){
//		alert("로그인 후 이용하실 수 있습니다.");
//		return;		
//	}else{
		//var fm = document.fm4;
		var word = document.all.word.value;
		//alert(word);
		if(word == ""){
		alert("검색어를 입력하십시요");
		}else {
		fm4.elements["searchVO.word"].value = word;
		fm4.submit();
		}
//	}
}

//-->
</script>
</head>
<form name="fm2" method="post" action="/login.do" >
  <input type="hidden" name="method" value="login"/>
  <input type="hidden" name="vo.login_id"/>
  <input type="hidden" name="vo.password"/>
  <input type="hidden" name="returnURL"/>
</form>
<form name="fm3" method="post" action="/member.do" >
  <input type="hidden" name="method" value="getUserUpdateForm"/>
  <input type="hidden" name="vo.login_id"/>
  <input type="hidden" name="searchVO.menu_sn" value="06"/>
</form>
<form name="fm4" method="post" action="/UniSearch.do">
  <input type="hidden" name="method" value="uniSearch"/>
  <input type="hidden" name="vo.login_id"/>
  <input type="hidden" name="searchVO.word"/>
  <input type="hidden" name="searchVO.menu_sn" value="13"/>
</form>
<body class="LY-WrapperBG<%=menu_sn%>">
<!-- start #LY-Wrapper01 // 온라인상담 BG 이미지 -->
<div class="LY-Wrapper<%=menu_sn%>">
	<!-- start #LY-Main-Header01 // 온라인상담 BG 이미지 --> 
	<div class="LY-Header01">
		<!-- start # Top-Logo -->
		
		<div class="Top-Logo"><a href="/index.jsp"><img src="/images/header/Logo.gif"  alt="국가과학기술위원회 R&D도우미센터" border="0"/></a></div>
		<!-- end # Top-Logo -->
		<!-- start # Top-Search -->
		<div class="Top-UtileMenu">
<% 
		if(mainLoginVO == null || mainIsLogin == false){
%>		
			<!-- 로그아웃 상태 -->
			<ul>
					<li><a href="/index.jsp"><img src="/images/header/Util_Menu01.gif" alt="홈으로" border="0" /></a> </li>
					<li><a href="/sitemap.jsp?searchVO.menu_sn=08"><img src="/images/header/Util_Menu02.gif" alt="사이트 맵" border="0" /></a> </li>
					<li><a href="/switch.do?prefix=&page=/member.do?method=getUserInsertForm&searchVO.menu_sn=06"><img src="/images/header/Util_Menu06.gif" alt="회원가입" border="0" /></a> </li>
					<li><a href="JavaScript:login();"><img src="/images/header/Util_Menu04.gif" alt="로그인" border="0" /></a></li>
					<li class="Search"><img src="/images/header/Util_Menu03.gif" alt="Search" border="0" />
					<input name="word" type="text" id="search" class="searchform"> 
					<a href="JavaScript:goUniSearch();" class="search"><img src="/images/header/Btn_TopSearch.gif" alt="검색" border="0" /></a>
					</li>
			</ul>
<%
		}else{
%>			
			<!-- 로그인 상태  -->
			<ul>
					<li><a href="/index.jsp"><img src="/images/header/Util_Menu01.gif" alt="홈으로" border="0" /></a> </li>
					<li><a href="/sitemap.jsp?searchVO.menu_sn=08"><img src="/images/header/Util_Menu02.gif" alt="사이트 맵" border="0" /></a> </li>
					<li><a href="javascript:update();"><img src="/images/header/Util_Menu07.gif" alt="개인정보변경" border="0" /></a> </li>
					<%
						if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
					%>
					<li><a href="/switch.do?prefix=&page=/discussion.do?method=retrieveDiscussDetail&searchVO.menu_sn=09"><img src="/images/header/Util_Menu08.gif" alt="커뮤니티" border="0" /></a> </li>
					<%
						}
					%>
					<li><a href="/logout.jsp"><img src="/images/header/Util_Menu05.gif" alt="로그아웃" border="0" /></a> </li>
					<li class="Join-infor"><strong><%= nameKO %></strong>님 접속하셨습니다.</li>
					<li class="Search"><img src="/images/header/Util_Menu03.gif" alt="Search" border="0" />
					<input name="word" type="text" id="search" class="searchform"> 
					<a href="JavaScript:goUniSearch();" class="search"><img src="/images/header/Btn_TopSearch.gif" alt="검색" border="0" /></a>
					</li>
			</ul>
<%
		}
%>			
		</div>
		<!-- end # Top-Search -->

		<!-- start # Top-Menu -->
		<div id="LY-Top">
			<ul id="Top-Menu">
				<li id="Top-Menu-01"><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireMainList&searchVO.menu_sn=01"><img src="/images/Menu/tm01_off.gif" alt="온라인상담" border="0" /></a>
				<ul class="Top-Menu-Sub">
					<li><a href="JavaScript:goInquireForm()"><img src="/images/Menu/tm01_01_off.gif" alt="문의하기" /></a></li>
					<li><a href="JavaScript:goFaq()"><img src="/images/Menu/tm01_02_off.gif" alt="자주묻는질문" /></a></li>
					<li><a href="JavaScript:goInquireList()"><img src="/images/Menu/tm01_03_off.gif" alt="Q&A" /></a></li>
				</ul>
				</li>
				<li id="Top-Menu-06"><a href="JavaScript:goOffer()"><img src="/images/Menu/tm07_off.gif" alt="신문고" border="0" /></a> </li>
				<li id="Top-Menu-02"><a href="JavaScript:goData()"><img src="/images/Menu/tm02_off.gif" alt="자료실" border="0" /></a> </li>
<%
			if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
%>
				<li id="Top-Menu-03"><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=05"><img src="/images/Menu/tm05_off.gif" alt="관리자" border="0" /></a>
				<ul class="Top-Menu-Sub">
					<li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=05"><img src="/images/Menu/tm05_01_off.gif" alt="권한관리" /></a></li>
					<li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=05"><img src="/images/Menu/tm05_02_off.gif" alt="분야관리" /></a></li>
					<li><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm&searchVO.menu_sn=05"><img src="/images/Menu/tm05_03_off.gif" alt="오프라인자료등록" /></a></li>
					<li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatCategory&searchVO.menu_sn=05"><img src="/images/Menu/tm05_04_off.gif" alt="통계정보" /></a></li>
				</ul>
			 	</li>
<%
			}
%>
				<li id="Top-Menu-03"><a href="JavaScript:goMypage()"><img src="/images/Menu/tm03_off.gif" alt="마이페이지" border="0" /></a></li>
				<li id="Top-Menu-04"><a href="JavaScript:goNotice()"><img src="/images/Menu/tm04_off.gif" alt="공지사항" border="0" /></a></li>
<%
			if(mainRoleCD.equals("0000B")){
%>
				<li id="Top-Menu-06"><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireOrgList&searchVO.board_type=QNA&searchVO.menu_sn=12"><img src="/images/Menu/tm06_off.gif" alt="기관담당자" border="0" /></a></li>
<%
			}
%>				
			</ul>
			<script type=text/javascript>
			<!--
				var ObjEventTopMenu = new EventTopMenu(0, 0, 0);
			//-->
			</script>
		</div>
		<!--  end #  Top-Menu -->
		<!-- start # PageTitle -->
		<div class="PageTitle"><img src="/images/header/PageText<%=menu_sn%>.gif" alt="메뉴타이틀" /></div>
		<!-- end # PageTitle -->
	</div>
	<!-- end #LY-Main-Header -->