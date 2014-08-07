<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

	<bean:define name="DataForm" property="totRowCount" id="totRowCount" type="java.lang.Integer" />
	<bean:define name="DataForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String" />
	<bean:define name="DataForm" property="maxPageItems" id="maxPageItems" type="java.lang.String" />
	<bean:define name="DataForm" property="pagerOffset" id="pagerOffset" type="java.lang.String" />
	<bean:define name="DataForm" property="searchVO.loginId" id="loginId" type="java.lang.String" />
	<bean:define name="DataForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String" />
	<bean:define name="DataForm" property="searchVO.board_type" id="board_type" type="java.lang.String" />

    <bean:define id="path" type="java.lang.String" value="/Data.do" />
    
    <script type="text/javascript">
    <!--
        var data = {
            num : 4 // 위치순번
        };
        // 현재메뉴 위치.
        menuFocus(data);
    //-->
    </script>
    
    <script type="text/javascript">
        activeDebug = true;  
        module = '/switch.do?prefix=/data&method=dataList&page=';

		function goSearch(){
			fm.elements["method"].value="dataList";
			fm.submit();
		}
		
		function goIns(arg){
			fm.elements["searchVO.board_type"].value=arg;	
			if ( arg == "SYSTEM" ) {
                fm.elements["method"].value="dataSystemList";
            } else {
                fm.elements["method"].value="dataList";
            }
			fm.submit();
		}
		
		function dataDetailView(arg){
			fm.elements["searchVO.seq"].value=arg;	
			fm.elements["method"].value="dataDetailView";
			fm.submit();
		}
	</script>

    <!-- container -->
    <div id="container" class="dataroom">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>자료실</h2>
                <span><img src="/img/common/h2_data_entxt01.gif" alt="Library" /></span>
            </div>
            <ul class="lnb-lst">
                <li class="on"><a href="Javascript:goLawInfo()">법령 및 행정규칙</a></li>
<!--                 // TODO 제도 링크 확인. -->
                <li><a href="JavaScript:goIns('SYSTEM')">연구관리제도</a></li>
                <li><a href="JavaScript:goIns('DATA')">기타</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href="JavaScript:goIns('DATA')">자료실</a></li>
                    <li class="on"><a href="Javascript:goLawInfo()">법령 및 행정규칙</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>법령 및 행정규칙</h3>
                    <p>국가연구개발사업 관련 법령 및 행정규칙 검색이 가능합니다.  NTIS의 법령정보와 연계하여 제공해드리는 서비스입니다.</p>
                </div>
                <!-- data-con -->
                <div class="data-con">
                    <p><img src="/img/sub/data_txt.png" alt="법령자료는 NTIS 종합법령 서비스로 " /></p>
                    <p class="txt">연계되어 제공합니다.</p>
                    <span class="btn-set sky"><a href="http://rndgate.ntis.go.kr/switch.do?prefix=/un/rndLaw&page=/unRndLaw.do?method=retrieveLawSearchByKey" target="_blank">법령서비스 바로가기</a><span class="bullet">아이콘 실제 사용시 텍스트 삭제</span></span>            
                </div>
                <!-- //data-con -->
            </div>
            <!-- //section -->  
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->
    
    <html:form action="/Data" method="post" name="fm" type="kr.go.rndcall.mgnt.data.form.DataForm">
		<html:hidden name="DataForm" property="method" value="noticeList" />
		<html:hidden name="DataForm" property="searchVO.loginId" />
		<html:hidden name="DataForm" property="searchVO.name" />
		<html:hidden name="DataForm" property="searchVO.board_type" />
		<html:hidden name="DataForm" property="searchVO.seq" />
		<html:hidden name="DataForm" property="searchVO.type" />
		<html:hidden name="DataForm" property="searchVO.menu_sn"/>
		<html:hidden name="DataForm" property="searchVO.pagerOffset"/>
    </html:form>
<%@include file="/include/bottom.jsp"%>
