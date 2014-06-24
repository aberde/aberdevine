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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>R&D도우미센터</title>
	<link rel="stylesheet" type="text/css" href="/css/common.css" />
	<link rel="stylesheet" type="text/css" href="/css/layout.css" />
	<link rel="stylesheet" type="text/css" href="/css/sub.css" />
	<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
	<!--[if lt IE 7]>
	<style media="screen" type="text/css">
	#container {
		height:100%;
	}
	</style>
	<![endif]-->
	<script language="JavaScript" src="/js/ajax.js"></script>
	<script language="JavaScript" src="/js/common.js"></script> 
	<script language="JavaScript" src="/js/validate.js"></script> 
	<script language="javascript" type="text/javascript">
	<!--
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
		
		/**
		 * 현재메뉴 위치.
		 * @param data
		 *   num : 위치순번
		 */
		function menuFocus(data) {
			$(".menu > a:eq(" + data.num + ")").css("color", "#d23962");
		}
	//-->
	</script>
</head>
<!--[if lt IE 7]>  <body class="no-js ie ie6 lte9 lte8 lte7"> <![endif]-->
<!--[if IE 7]>     <body class="no-js ie ie7 lte9 lte8 lte7"> <![endif]-->
<!--[if IE 8]>     <body class="no-js ie ie8 lte9 lte8"> <![endif]-->
<!--[if IE 9]>     <body class="no-js ie ie9 lte9"> <![endif]-->
<!--[if gt IE 9]>  <body class="no-js">  <![endif]-->
<!--[if !IE]><!--> <body> <!--<![endif]-->
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
	
<!-- wrap -->
<div id="wrap">
	<!-- header -->
	<div id="header">
		<div class="head clearfix">
			<h1><a href="/index.jsp"><img src="/img/common/h1_logo.gif" alt="미래창조과학부. R&D도우미센터" /></a></h1>
			<div class="nav-bx clearfix">
				<% 
					if ( mainLoginVO == null || mainIsLogin == false ) {
				%>
				<!-- 로그아웃 상태 -->
				<ul class="nav-lst fl">
					<li><a href="/index.jsp">홈</a></li>
					<li><a href="/sitemap.jsp?searchVO.menu_sn=08">사이트맵</a></li>
					<li><a href="/switch.do?prefix=&page=/member.do?method=getUserInsertForm&searchVO.menu_sn=06">회원가입</a></li>
					<li><a href="JavaScript:login();">로그인</a></li>
				</ul>
				<div class="search-bx fl">
					<label for="search"><strong>통합검색</strong></label>
					<input type="text" id="search" name="word" defaltvalue="검색어 입력창" onkeydown="checkKey();" />
					<a href="JavaScript:goUniSearch();" class="search-btn">검색</a>
				</div>
				<%
					} else {
				%>
				<!-- 로그인 상태 -->
				<ul class="nav-lst fl">
					<li><a href="/index.jsp">홈</a></li>
					<li><a href="/sitemap.jsp?searchVO.menu_sn=08">사이트맵</a></li>
					<li><a href="javascript:update();">개인정보변경</a></li>
					<%
						if ( mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C") ) {
					%>
					<li><a href="/switch.do?prefix=&page=/discussion.do?method=retrieveDiscussDetail&searchVO.menu_sn=09">커뮤니티</a></li>
					<%
						}
					%>
					<li><a href="JavaScript:goMypage()">마이페이지</a></li>
					<li><a href="/logout.jsp">로그아웃</a></li>
<!-- 					// TODO 사용자 접속확인 메세지 사용여부 확인. -->
<%-- 					<li class="Join-infor"><strong><%= nameKO %></strong>님 접속하셨습니다.</li> --%>
				</ul>
				<div class="search-bx fl">
					<label for="search"><strong>통합검색</strong></label>
					<input type="text" id="search" name="word" onkeydown="checkKey();" />
					<a href="JavaScript:goUniSearch();" class="search-btn">검색</a>
				</div>
				<%
					}
				%>
			</div>
		</div>
		<!-- gnb -->
		<div class="gnb">
			<div class="gnb-bx clearfix">
				<ul class="menu-lst fl clearfix">
					<li class="menu"><a href="/development/development01.jsp">국가연구개발사업이란?</a>
						<ul class="snb">
							<li><a href="/development/development01.jsp">정의 및 법령 체계</a></li>
							<li><a href="/development/development02.jsp">사업추진체계</a></li>
						</ul>
					</li>
					<li class="menu"><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireMainList&searchVO.menu_sn=01">온라인 상담</a>
						<ul class="snb">
							<li><a href="JavaScript:goInquireForm()">온라인 상담</a></li>
							<li><a href="JavaScript:goFaq()">자주 묻는 질문</a></li>
<!-- 							// TODO Q&A는 사라지는건지? -->
<!-- 							<li><a href="JavaScript:goInquireList()">Q&A</a></li> -->
						</ul>
					</li>
					<li class="menu"><a href="JavaScript:goOffer()">온라인신문고</a></li>
					<li class="menu"><a href="JavaScript:goNotice()">알림</a>
						<ul class="snb">
							<li><a href="JavaScript:goNotice()">공지사항</a></li>
						</ul>
					</li>
					<li class="menu"><a href="JavaScript:goData()">자료실</a>
						<ul class="snb">
							<li><a href="#none;">법령 및 행정 규칙</a></li>
							<li><a href="#none;">제도</a></li>
							<li><a href="#none;">기타</a></li>
						</ul>
					</li>
					<li class="menu"><a href="/center/center.jsp">센터소개</a></li>
					<%
						if ( mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C") ) {
					%>
					<li class="menu"><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=05">관리자</a>
						<ul class="snb">
							<li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=05">권한관리</a></li>
							<li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=05">분야관리</a></li>
							<li><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm&searchVO.menu_sn=05">오프라인자료등록</a></li>
							<li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatCategory&searchVO.menu_sn=05">통계정보</a></li>
						</ul>
					</li>
					<%
						}
					%>
<!-- 					// TODO 기관담당자 -->
					<%
						if ( mainRoleCD.equals("0000B") ) {
					%>
					<li id="Top-Menu-06" class="menu"><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireOrgList&searchVO.board_type=QNA&searchVO.menu_sn=12">기관담당자</a></li>
					<%
						}
					%>
				</ul>
				<p class="all-menu fr"><a href="#none;">전체보기</a></p>
			</div>		
		</div>
		<!-- //gnb -->
	</div>
	<!-- // header -->