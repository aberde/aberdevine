<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
### 경로   : <%= request.getRequestURI() %>
### 사업명 : 11년도 개방형 연구성과분석지원서비스
### 사업일 : 2011-07-10 ~ 2011-08-31
### 개발일 : 2011-07-14
### 개발자 : 박인선
-->
<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>

<bean:define name="DataForm" property="totRowCount" id="totRowCount" type="java.lang.Integer" />
<bean:define name="DataForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String" />
<bean:define name="DataForm" property="maxPageItems" id="maxPageItems" type="java.lang.String" />
<bean:define name="DataForm" property="pagerOffset" id="pagerOffset" type="java.lang.String" />
<bean:define name="DataForm" property="searchVO.loginId" id="loginId" type="java.lang.String" />
<bean:define name="DataForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String" />
<bean:define name="DataForm" property="searchVO.board_type" id="board_type" type="java.lang.String" />

<bean:define id="path" type="java.lang.String" value="/Data.do" />
<script type="text/javascript">
activeDebug = true;  
module = '/switch.do?prefix=/data&method=dataList&page=';

function goSearch(){
	fm.elements["method"].value="dataList";
	fm.submit();
}
function goIns(arg){
	fm.elements["searchVO.board_type"].value=arg;	
	fm.elements["method"].value="dataList";
	fm.submit();
}
function dataDetailView(arg){
	fm.elements["searchVO.seq"].value=arg;	
	fm.elements["method"].value="dataDetailView";
	fm.submit();
}
function goLawInfo(){
	var url = "http://rndgate.ntis.go.kr/switch.do?prefix=/un/rndLaw&page=/unRndLaw.do?method=retrieveLawSearchByKey";
	window.open(url);
	document.location.href = "/switch.do?prefix=/data&page=/Data.do?method=dataListLaw&searchVO.menu_sn=02";
}


</script>
</head>
<div class="LY-Container">
	<html:form action="/Data" method="post" name="fm" type="kr.go.rndcall.mgnt.data.form.DataForm">
	<html:hidden name="DataForm" property="method" value="noticeList" />
	<html:hidden name="DataForm" property="searchVO.loginId" />
	<html:hidden name="DataForm" property="searchVO.name" />
	<html:hidden name="DataForm" property="searchVO.board_type" />
	<html:hidden name="DataForm" property="searchVO.seq" />
	<html:hidden name="DataForm" property="searchVO.type" />
	<html:hidden name="DataForm" property="searchVO.menu_sn"/>
	<html:hidden name="DataForm" property="searchVO.pagerOffset"/>
	<!-- end # LY-ContentTitle -->
	<!-- start # 레프트 메뉴 -->
	<div id="LY-Left">
			<!-- start # left menu  -->
			<ul id="Left-Menu">
				<li><a href="#" onclick="goLawInfo()"; onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image8','','/images/Menu/left/lm02_01_on.gif',8)"><img src="/images/Menu/left/lm02_01_off.gif" name="Image8" border="0" id="Image8" alt="기타자료실" /></a></li>
				<li><a href="JavaScript:goIns('DATA')"><img src="../images/Menu/left/lm02_03_off.gif" alt="기타자료" /></a></li>
			</ul>
			<SCRIPT type=text/javascript>
			<!--
			var ObjEventLeftMenu = new EventLeftMenu(2, 1, 0);
			//-->
			</SCRIPT>
	</div>
	<!-- end # 레프트 메뉴 -->
	<!-- start # 컨텐츠 영역 -->
	<div class="LY-Content">
		<!-- start # LY-ContentTitle -->
		<div class="LY-ContentTitle">
			<h1><img src="/images/content/Content_Title02_1.gif" alt="법령자료 - R&amp;D법령 관련 기타 규정 및 안내자료를 조회할 수 있습니다." /></h1>
		</div>
		<!-- end # LY-ContentTitle -->
		<!-- start # InforSearch // 멀티라인 -->
	<br/>
	<!-- start # InforSearch // 멀티라인 -->
	<div class="Basic-List-area">
		<ul class="List">
		<img src="/images/content/law_PageImg.gif" border="0" usemap="#Map" />
		<map name="Map" id="Map"><area shape="rect" coords="261,257,463,290" href="http://rndgate.ntis.go.kr/switch.do?prefix=/un/rndLaw&page=/unRndLaw.do?method=retrieveLawSearchByKey" target="_blank"/></map> 
		
	</ul>
	</div>
	</div>
</html:form> <!-- end 검색 테이블 감싸기 --> <br />
</div>
<%@  include file="/include/bottom.jsp"%>
