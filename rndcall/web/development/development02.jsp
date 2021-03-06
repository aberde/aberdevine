<%@page contentType="text/html; charset=utf-8" %>

<%@include file="/include/top.jsp"%>
	
	<script type="text/javascript">
	<!--
		var data = {
			num : 0	// 위치순번
		};
		// 현재메뉴 위치.
		menuFocus(data);
	//-->
	</script>
	
	<!-- container -->
	<div id="container" class="development">
	    <!-- lnb -->
	    <div class="lnb">
	        <div class="tit-area">
	            <h2 class="develop-lnb">국가연구개발 <br />사업이란?</h2>
	        </div>
	        <ul class="lnb-lst">
	            <li><a href="/development/development01.jsp">정의 및 법령체계</a></li>
	            <li class="on"><a href="#none;">사업추진체계</a></li>
	        </ul>
	    </div>
	    <!-- //lnb -->
	    <!-- content -->
	    <div class="content clearfix">
	        <div class="location txt-r">
	            <ul class="fr clearfix">
	                <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
	                <li><a href="/development/development01.jsp">국가연구개발사업이란?</a></li>
	                <li class="on"><a href="/development/development02.jsp">사업추진체계</a></li>
	            </ul>
	        </div>
	        <!-- section -->
	        <div class="section">
	            <div class="tit-area">
	                <h3>사업추진체계</h3>
	            </div>
	            <!-- develop-tbl -->
	            <ul class="develop-tbl clearfix">
	                <li>구분</li>
	                <li>중앙행정기관</li>
	                <li class="last">전문기관</li>
	                <li>주관연구기관</li>
	            </ul>
	            <!-- //develop-tbl -->
	            <div class="grap-bg"><img src="/img/sub/develop_bg.gif" alt="" /></div>
	            <div class="btn-area txt-c">
	                <span class="btn-set red">
	                    <a href="http://rndgate.ntis.go.kr/switch.do?prefix=/un&page=/unRndList.do?method=retrieveList" target="_blank">국가연구개발사업 공고문 보러가기</a>
	                </span>
	            </div>
	        </div>
	        <!-- //section -->
	    </div>
	    <!-- //content -->
	</div>
	<!-- // container -->
		
<%@include file="/include/bottom.jsp"%>