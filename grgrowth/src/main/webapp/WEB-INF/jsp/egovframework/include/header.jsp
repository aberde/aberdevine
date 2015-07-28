<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/customTag.tld" %>

<!DOCTYPE html>
<!--[if IE 7]>
<html id="ie7" dir="ltr" lang="ko-KR">
<![endif]-->
<!--[if !(IE 7) ]><!-->
<html dir="ltr" lang="ko-KR">
<!--<![endif]-->
<head>
	<meta charset="UTF-8" />
	<title>녹색성장위원회</title>
	
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css" />">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/layout.css" />">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/green.css" />">
	<style type="text/css">
		#navcats .nav_cats .sub-menu {
			display: block;
			/* The way to change overlay opacity is the follow properties. Opacity is a tricky issue due to
					longtime IE abuse of it, so opacity is not offically supported - use at your own risk. 
					To play it safe, disable overlay opacity in IE. */
			/* For Firefox/Opera/Safari/Chrome */
			opacity: 10;
			/* For IE 5-7 */
			filter: progid:DXImageTransform.Microsoft.Alpha(Opacity=100);
			/* For IE 8 */
			-MS-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=100)";
		}
		
		/*#navcats .nav_cats .sf-sub-1 .sub-width1-1 a{color:red;}*/
	</style>
	
	<script type="text/javascript">
		//<![CDATA[
		// Google Analytics for WordPress by Yoast v4.2.2 | http://yoast.com/wordpress/google-analytics/
		var _gaq = _gaq || [];
		_gaq.push(['_setAccount','UA-24093225-1']);
		_gaq.push(['_trackPageview'],['_trackPageLoadTime']);
		(function() {
			var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
			ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
			var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
		})();
		//]]>
	</script>
	<script type="text/javascript" src="<c:url value="/js/jquery-1.11.3.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/jQuery.print.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/validator.js" />"></script>
	<script type="text/javascript">
		/**
		 * 네비게이션 버튼 선택
		 * @param selNo 버튼위치
		 */
		function fnChangeBtnNavi(selNo) {
			var totalNo = 5;
			var naviBtn; // 버튼
			for ( var i = 1; i <= totalNo; i++ ) {
				naviBtn = $("#mNavi" + i);
				if ( selNo == i ) {
					naviBtn.attr("src", "<c:url value="/images/main/mNavi" />" + i + "w.gif");
				} else {
					naviBtn.attr("src", "<c:url value="/images/main/mNavi" />" + i + ".gif");
				}
			}
		}
		
		/**
		 * 전체메뉴 펼치기/닫기
		 */
		function fnNaviAllopenclose() {
			var objDiv = $("#divNaviAll");

			if ( objDiv.is(":visible") ) {
				fnNaviAllclose();
			} else {
				fnNaviAllopen();
			}
		}
		
		/**
		 * 전체메뉴 펼치기
		 */
		function fnNaviAllopen() {
			var objBtn = $("#mNavi6");
			var objDiv = $("#divNaviAll");

			objBtn.attr("src", "<c:url value="/images/main/mNavi_close.gif" />");
			objDiv.show();
		}
		
		/**
		 * 전체메뉴 닫기
		 */
		function fnNaviAllclose() {
			var objBtn = $("#mNavi6");
			var objDiv = $("#divNaviAll");

			objBtn.attr("src", "<c:url value="/images/main/mNavi_open.gif" />");
			objDiv.hide();
		}
		
		/**
		 * 네비게이션 버튼 mouseover
		 */
		function fnShow(obj) {
			var btn = $("#mNavi" + obj);
			btn.attr("src", "<c:url value="/images/main/mNavi" />" + obj + "o.gif");
		}
		
		/**
		 * 네비게이션 버튼 mouseout
		 */
		function fnHide(obj) {
			var btn = $("#mNavi" + obj);
			btn.attr("src", "<c:url value="/images/main/mNavi" />" + obj + ".gif");
		}
		
		$(document).ready(function() {
			// 화면 접근시 네비게이션 선택
			fnChangeBtnNavi();
		});
	</script>
</head>

<body>
	<!-- wrapper -->
	<div id="wrapper">

		<div class="clear"></div>
		<div class="clear"></div>

		<div id="navcats">

			<!-- logo -->
			<div id="logo" style="padding:0px 0 10px 0px;">
				<a href="<c:url value="/" />" title="녹색성장위원회"><img 
					src="<c:url value="/images/main/logo2.jpg" />" alt="녹색성장위원회" title="녹색성장위원회" /></a>
			</div>
			<!-- /logo -->

			<!-- navi -->
			<ul id="menu-main-menu" class="nav_cats dropdown">
				<li>
					<a href="<c:url value="/menu001/sub001/GRG_001_101.do" />"><img id="mNavi1" src="<c:url value="/images/main/mNavi1.gif" />"
						border="0" alt="녹색성장" onmouseover="fnShow(1)" onmouseout="fnHide(1);" /></a>
					<ul class="sub-menu" onmouseover="fnShow(1);" onmouseout="fnHide(1);">
						<li class="sub-width1-1 sub-depth">
							<a href="<c:url value="/menu001/sub001/GRG_001_101.do" />">개념</a>
							<ul class="sub-menu">
								<li><a href="<c:url value="/menu001/sub001/GRG_001_101.do" />">개념</a></li>
								<li><a href="<c:url value="/menu001/sub001/GRG_001_102.do" />">추진배경</a></li>
							</ul>
						</li>
						<li class="sub-width1-2 sub-depth">
							<a href="<c:url value="/menu001/sub002/GRG_001_201.do" />">국가전략</a>
							<ul class="sub-menu">
								<li><a href="<c:url value="/menu001/sub002/GRG_001_201.do" />">국가전략</a></li>
								<li><a href="<c:url value="/menu001/sub002/GRG_001_202.do" />">5개년계획</a></li>
							</ul>
						</li>
					</ul>
				</li>
				<li>
					<a href="<c:url value="/cmmn/commonBoardList.do?category_seq=1" />"><img id="mNavi2" src="<c:url value="/images/main/mNavi2.gif" />"
						border="0" alt="소식" onmouseover="fnShow(2);" onmouseout="fnHide(2);" /></a>
					<ul class="sub-menu" onmouseover="fnShow(2);" onmouseout="fnHide(2);">
						<li class="sub-width2-1"><a href="<c:url value="/cmmn/commonBoardList.do?category_seq=1" />">위원회활동</a></li>
						<li class="sub-width2-1"><a href="<c:url value="/cmmn/commonBoardList.do?category_seq=2" />">주요소식</a></li>
					</ul>
				</li>
				<li>
					<a href="<c:url value="/cmmn/commonBoardList.do?category_seq=3" />"><img id="mNavi3" src="<c:url value="/images/main/mNavi3.gif" />"
						border="0" alt="자료실" onmouseover="fnShow(3);" onmouseout="fnHide(3);" /></a>
					<ul class="sub-menu" onmouseover="fnShow(3);" onmouseout="fnHide(3);">
						<li class="sub-width3-1 sub-depth">
							<a href="<c:url value="/cmmn/commonBoardList.do?category_seq=3" />">회의자료</a>
							<ul class="sub-menu">
								<li><a href="<c:url value="/cmmn/commonBoardList.do?category_seq=3" />">회의자료(2013.03~)</a></li>
								<li><a href="<c:url value="/cmmn/commonBoardList.do?category_seq=4" />">회의자료(~2013.02)</a></li>
							</ul>
						</li>
						<li class="sub-width3-4"><a href="<c:url value="/menu003/sub002/GRG_003_201.do" />">녹색법령</a></li>
						<li class="sub-width3-5"><a href="<c:url value="/menu003/sub003/GRG_003_301.do?category_seq=5" />">용어사전</a></li>
						<li class="sub-width3-7"><a href="<c:url value="/menu003/sub004/GRG_003_401.do" />">관련 사이트</a></li>
					</ul>
				</li>
				<li>
					<a href="<c:url value="/menu004/sub001/GRG_004_101.do" />"><img id="mNavi5" src="<c:url value="/images/main/mNavi5.gif" />"
						border="0" alt="녹색성장위원회 소개" onmouseover="fnShow(5);" onmouseout="fnHide(5);" /></a>
					<ul class="sub-menu" onmouseover="fnShow(5);" onmouseout="fnHide(5);">
						<li><a href="<c:url value="/menu004/sub001/GRG_004_101.do" />">인사말</a></li>
						<li class="sub-width5-2 sub-depth"><a href="<c:url value="/menu004/sub002/GRG_004_201.do" />">위원소개</a>
							<ul>
								<li><a href="<c:url value="/menu004/sub002/GRG_004_201.do" />">당연직위원</a></li>
								<li><a href="<c:url value="/menu004/sub002/GRG_004_202.do" />">민간위원</a></li>
							</ul>
						</li>
						<li><a href="<c:url value="/menu004/sub003/GRG_004_301.do" />">위원회연혁</a></li>
						<li><a href="<c:url value="/menu004/sub004/GRG_004_401.do" />">위원회기능 및 구성</a>
						<li class="sub-width5-7"><a href="<c:url value="/menu004/sub005/GRG_004_501.do" />">위원회로고</a></li>
					</ul>
				</li>
			</ul>
			<!-- /navi -->
			
			<div class="clear"></div>

			<!-- 전체 메뉴 -->
			<div class="all">
				<p class="naviOpenAll">
					<img id="mNavi6" src="<c:url value="/images/main/mNavi_open.gif" />" border="0" alt="전체펼침" onclick="fnNaviAllopenclose()" style="cursor: pointer;" />
				</p>
				<div class="divNaviAll" id="divNaviAll">
					<ul class="ulAllNavi-1 ulAllN">
						<li>
							<a href="<c:url value="/menu001/sub001/GRG_001_101.do" />" class="me">녹색성장</a>
							<ul>
								<li><a href="<c:url value="/menu001/sub001/GRG_001_101.do" />">개념</a></li>
								<li><a href="<c:url value="/menu001/sub002/GRG_001_201.do" />">국가전략</a></li>
							</ul>
						</li>
					</ul>
					<ul class="ulAllNavi-2 ulAllN">
						<li>
							<a href="<c:url value="/cmmn/commonBoardList.do?category_seq=1" />" class="me">소식</a>
							<ul>
								<li><a href="<c:url value="/cmmn/commonBoardList.do?category_seq=1" />">위원회 활동</a></li>
								<li><a href="<c:url value="/cmmn/commonBoardList.do?category_seq=2" />">주요소식</a></li>
							</ul>
						</li>
					</ul>
					<ul class="ulAllNavi-3 ulAllN">
						<li>
							<a href="<c:url value="/cmmn/commonBoardList.do?category_seq=3" />" class="me">정보</a>
							<ul>
								<li><a href="<c:url value="/cmmn/commonBoardList.do?category_seq=3" />">회의자료</a></li>
								<li><a href="<c:url value="/menu003/sub002/GRG_003_201.do" />">녹색법령</a></li>
								<li><a href="<c:url value="/menu003/sub003/GRG_003_301.do?category_seq=5" />">용어사전</a></li>
								<li><a href="<c:url value="/menu003/sub004/GRG_003_401.do" />">관련 사이트</a></li>
							</ul>
						</li>
					</ul>
					<ul class="ulAllNavi-4 ulAllN">
						<li>
							<a href="<c:url value="/menu004/sub001/GRG_004_101.do" />" class="me">위원회</a>
							<ul>
								<li><a href="<c:url value="/menu004/sub001/GRG_004_101.do" />">인사말</a></li>
								<li><a href="<c:url value="/menu004/sub002/GRG_004_201.do" />">위원회소개</a></li>
								<li><a href="<c:url value="/menu004/sub003/GRG_004_301.do" />">위원회연혁</a></li>
								<li><a href="<c:url value="/menu004/sub004/GRG_004_401.do" />">위원회기능 및 구성</a></li>
								<li><a href="<c:url value="/menu004/sub005/GRG_004_501.do" />">위원회로고</a></li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
			<!-- /전체 메뉴 -->

		</div>

		<div class="clear"></div>
