<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Content</title>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery.mobile-1.3.2.min.css" />" />
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.mobile-1.3.2.min.js" />"></script>
<%-- 	<script type="text/javascript" src="<c:url value="/resources/js/common.js" />"></script> --%>
</head>
<body>
	<div data-role="page">
		
		<!-- 왼쪽 메뉴 -->
		<div id="nav-panel" data-role="panel" data-position="left" data-position-fixed="true" data-display="reveal">
			<div data-role="collapsible-set" data-inset="false">
				<div data-role="collapsible">
					<h2>MENU LIST</h2>
					<ul data-role="listview">
						<li><a href="./layout" data-transition="slide">Menu1</a></li>
						<li><a href="#" class="ui-btn-active">Menu2</a></li>
						<li><a href="./content" data-transition="slide">Menu3</a></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- /왼쪽 메뉴 -->
		
		<!-- 제목 -->
		<div data-role="header">
			<h1>HEADER</h1>
			<a href="#nav-panel" data-icon="bars" data-iconpost="notext">Menu</a>
		</div>
		<!-- /제목 -->
		
		<!-- 내용 -->
		<div data-role="content">
			<div data-role="collapsible" data-inset="false">
				<h3>CONTENT LIST</h3>
				<ul data-role="listview">
					<li><a href="#">Content1</a></li>
					<li><a href="#">Content2</a></li>
					<li><a href="#">Content3</a></li>
				</ul>
			</div>
			<div data-role="collapsible" data-inset="false">
				<h3>CONTENT LIST2</h3>
				<ul data-role="listview">
					<li><a href="#">Content1</a></li>
					<li><a href="#">Content2</a></li>
					<li><a href="#">Content3</a></li>
				</ul>
			</div>
		</div>
		<!-- /내용 -->
			
	</div>
</body>
</html>