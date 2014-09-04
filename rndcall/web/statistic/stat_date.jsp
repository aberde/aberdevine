<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>


<bean:define name="StatisticForm" property="searchVO.start_yy" id="start_yy" type="java.lang.String"/>
<bean:define name="StatisticForm" property="searchVO.start_mm" id="start_mm" type="java.lang.String"/>
<bean:define name="StatisticForm" property="searchVO.end_mm" id="end_mm" type="java.lang.String"/>

<bean:define id="path" type="java.lang.String" value="/Statistic .do"/>

<%
	int cnt1 = 0;
	int cnt2 = 0;
	int cnt3 = 0;
	int cnt4 = 0;
	int cnt5 = 0;
	int count=0;
	String mm="";
%>

<%
	if (mainLoginVO == null || !mainIsLogin) {	
%>
	<script type="text/javascript">
		alert('로그인이 필요한 메뉴입니다.');
		document.location.href = "/index.jsp";
	</script>
<%
	} else if (!mainRoleCD.equals("0000C") && !mainRoleCD.equals("0000Z")) {
%>
	<script type="text/javascript">
		alert('권한이 없습니다.');
		document.location.href = "/index.jsp";
	</script>
<%		
	}
%>

<script language="JavaScript" src="/js/FusionCharts.js"></script>
<script type="text/javascript">
<!--
activeDebug = true;  
module = '/switch.do?prefix=/statistic&method=getStatCategory&page=';
		
function goCategoryL(){
	fm.elements["method"].value="getStatCategory";
	fm.submit();
}

function goDateL(){

	fm.elements["searchVO.start_yy"].value="";
	fm.elements["searchVO.start_mm"].value="";
	fm.elements["searchVO.end_mm"].value="";
	fm.elements["method"].value="getStatDate";
	fm.submit();
}

function goSearch(){
	fm.elements["method"].value="getStatDate";
	fm.submit();
}

function goVisitL(){
	fm.elements["searchVO.stat_type"].value="YY";
	fm.elements["method"].value="getStatVisit";
	fm.submit();
}

function goExcelDown(arg){
	fm.elements["searchVO.down_type"].value=arg;	
	fm.elements["method"].value="getStatExcelDownLoad";
	fm.submit();
}
function showChart(){

		var chartObj = new FusionCharts("/charts/MSColumn3D.swf","chart0", "654", "200", "0", "30");
		var chartUrl = "/statistic/charts/chart1.jsp" + escape("?start_yy=<%=start_yy%>&start_mm=<%=start_mm%>&end_mm=<%=end_mm%>");

		chartObj.setDataURL(chartUrl);
		chartObj.render("chart1");
	}
	
function goStatList(arg1, arg2){


	var width = '900';
    var height = '560';
    var left = (screen.width - width)/2;
    var top = (screen.height - height)/2;
   	var winNM = 'getStatList';
   	var url = '/switch.do?prefix=/statistic&method=getStatDate&page=/Statistic.do?method=getStatDataList&searchVO.whichSearch='+arg1+'&searchVO.searchTxt1='+arg2+'&searchVO.searchVO.srart_yy=<%=start_yy%>&searchVO.searchVO.srart_mm=<%=start_mm%>&searchVO.searchVO.end_mm=<%=end_mm%>';  
    var windowFeatures = "width=" + width + ",height=" + height +
        ",status,resizable,scrollbars=yes,left=" + left + ",top=" + top +
        ",screenX=" + left + ",screenY=" + top;
   	var formObjNM = 'fm';
    var win = window.open(url, winNM, windowFeatures);
    
}
//-->
</script>
<div class="LY-Container">
	<html:form action="/Statistic" method="post" name="fm" type="kr.go.rndcall.mgnt.statistic.form.StatisticForm">
	<html:hidden name="StatisticForm" property="method" value="getStatDate"/>
	<html:hidden name="StatisticForm" property="searchVO.stat_type"/>
	<html:hidden name="StatisticForm" property="searchVO.down_type"/>	
	<html:hidden name="StatisticForm" property="searchVO.menu_sn"/>	
		<!-- start # 레프트 메뉴 -->
		<div id="LY-Left">
				<!-- start # left menu  -->
				<ul id="Left-Menu">
						<li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=09"><img src="../images/Menu/left/lm03_01_off.gif" alt="권한관리" /></a></li>
						<li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=09"><img src="../images/Menu/left/lm03_02_off.gif" alt="분야관리" /></a></li>
						<li><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm&searchVO.menu_sn=09"><img src="../images/Menu/left/lm03_03_off.gif" alt="오프라인자료등록" /></a></li>
						<li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatBoardType&searchVO.menu_sn=09"><img src="../images/Menu/left/lm03_04_off.gif" alt="통계관리" /></a></li>
				</ul>
				<SCRIPT type=text/javascript>
				<!--
				var ObjEventLeftMenu = new EventLeftMenu(3, 4, 0);
				//-->
				</SCRIPT>
		</div>
		<!-- end # 레프트 메뉴 -->
	<div class="LY-Content">
	<div class="Reference-STab">
		<ul>
			<li>
				<table border="0" cellspacing="0" cellpadding="0" class="Reference-STab-Menu" >
  					<tr>
   						 <td><a href="JavaScript:goCategoryL()" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image6','','../images/content/Statistic_STap01_Up.gif',6)">
						 <img src="../images/content/Statistic_STap01_Dw.gif" name="Image6" border="0" id="Image6" alt="분야별통계" /></a></td>
   						 <td><a href="JavaScript:goDateL()">
   						 <img src="../images/content/Statistic_STap02_Up.gif" alt="기간별통계" border="0" /></a></td>
   						 <td><a href="JavaScript:goVisitL()" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image7','','../images/content/Statistic_STap03_Up.gif',7)">
						 <img src="../images/content/Statistic_STap03_Dw.gif" name="Image7" border="0" id="Image7" alt="접속자현황" /></a></td>
  					</tr>
				</table>
			</li>
		</ul>				
	</div>
	<br/>
	<!-- end # OnLine-STab -->


	<!-- start # InforSearch // 멀티라인 -->
	<div class="InforSearch" title="검색">
			<ul class="Search">
					<li>
					<table border="0" cellspacing="0" cellpadding="0" class="Search-Pre">
	  					<tr>
	   						 <th>기간</th>
				   		<td>
				   			<html:select name="StatisticForm" property="searchVO.start_yy" title="년도" alt="년도">
							<html:options name="StatisticForm" property="yearListDesc"/>
							</html:select>년
							<html:select name="StatisticForm" property="searchVO.start_mm" title="시작월" alt="시작월">
							<html:options name="StatisticForm" property="mon_list"/>
							</html:select>월
							~
							<html:select name="StatisticForm" property="searchVO.end_mm" title="종료월" alt="종료월">
							<html:options name="StatisticForm" property="mon_list"/>
							</html:select>월
				   		</td>
	  					     <td class="Btn"><a href="javascript:goSearch()" class="btn_BSearch"><strong>검색</strong></a> 
				  		</td>
	  					</tr>
					</table>
					</li>
			</ul>
	</div>
	<br/>

<!-- start # Basic-List -->
<div class="Basic-List-area">
	<!-- start # 리스트 목록 -->
	<ul class="List">
			<table border="0" cellspacing="0" cellpadding="0" class="Basic-List">
			<colgroup>
				<col width="30px" />
				<col width="120px" />
				<col width="110px" />
				<col width="70px" />
				<col width="70px" />
				<col width="70px" />
				<col width="70px" />
			</colgroup>
			<thead>
			<tr>
				<th class="center" rowspan="2">순번</th>
				<th class="center" rowspan="2">월별</th>
				<th class="center" colspan="3">등록건수</th>
				<th class="center" rowspan="2">처리건수</th>
				<th class="center" rowspan="2">미처리건수</th>
			</tr>
			<tr>
				<th class="S-Menu">전체건수</th>
				<th class="S-Menu">온라인</th>
				<th class="S-Menu">오프라인</th>
			</tr>
			</thead>
			<tbody>
			<logic:empty name="StatisticForm" property="voList">
				<tr><td style="border-right:0px;text-align:center; height=:50px;" colspan="7">등록된 정보가 없습니다.</td></tr>
			</logic:empty>
			<logic:notEmpty name="StatisticForm" property="voList">
				<logic:iterate name="StatisticForm" property="voList" indexId="rowNum" id="vo">
					<bean:define name="vo" property="total_cnt" id="total_cnt" type="java.lang.Integer"/>
					<bean:define name="vo" property="online_cnt" id="online_cnt" type="java.lang.Integer"/>
					<bean:define name="vo" property="offline_cnt" id="offline_cnt" type="java.lang.Integer"/>
					<bean:define name="vo" property="disposal_cnt" id="disposal_cnt" type="java.lang.Integer"/>						
					<bean:define name="vo" property="undisposal_cnt" id="undisposal_cnt" type="java.lang.Integer"/>									
					<bean:define name="vo" property="stat_mm" id="stat_mm" type="java.lang.String"/>
<%
					cnt1 += total_cnt.intValue();
					cnt2 += online_cnt.intValue();
					cnt3 += offline_cnt.intValue();
					cnt4 += disposal_cnt.intValue();
					cnt5 += undisposal_cnt.intValue();
%>					
					<tr>
						<td class="center"><%=++count %></td>
<%
						stat_mm	= stat_mm.replace("월","");
						if(stat_mm.length() < 2 ) mm="0"+stat_mm;
%>							
						<td style="text-align:left;"><b><bean:write name="vo" property="stat_mm"/></b></td>
						<td style="text-align:center;"><a href="JavaScript:goStatList('T','<%=mm %>')"><bean:write name="vo" property="total_cnt"/></a></td>
						<td style="text-align:center;"><a href="JavaScript:goStatList('ON','<%=mm %>')"><bean:write name="vo" property="online_cnt"/></a></td>
						<td style="text-align:center;"><a href="JavaScript:goStatList('OFF','<%=mm %>')"><bean:write name="vo" property="offline_cnt"/></a></td>
						<td style="text-align:center;"><a href="JavaScript:goStatList('Y','<%=mm %>')"><bean:write name="vo" property="disposal_cnt"/></a></td>						
						<td style="text-align:center;"><a href="JavaScript:goStatList('N','<%=mm %>')"><bean:write name="vo" property="undisposal_cnt"/></a></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
			<tr><td class="center"></td>
				<td style="text-align:left;"><b>총건수</b></td>
				<td style="text-align:center;"><%=cnt1%></td>
				<td style="text-align:center;"><%=cnt2%></td>
				<td style="text-align:center;"><%=cnt3%></td>
				<td style="text-align:center;"><%=cnt4%></td>						
				<td style="text-align:center;"><%=cnt5%></td>
			</tr>	
		</tbody>		
	</table>
	<br/>
			
			
	<!-- start # Graph-Infor -->
	<div class="Graph-Infor">
		<!-- start # 타이틀 -->
		<ul class="Title">
				<li>월별 통계 현황</li>
		</ul>
		<!-- start # 그래프 영역 // 높이값 프리 가로사이즈만 마춰 주세요 // Width:950px  -->
		<ul class="Infor">
			<li>
				<!-- 그래프 들어가요 -->
				<div id="chart1">로딩중....</div>
				<script>showChart();</script>
				<!-- 그래프 들어가요 -->
			</li>
		</ul>
	</div>
	<!-- end # Graph-Infor -->			
	<div style="margin:10px 0; display:block">
		<ul class="Center">
			<div class="Basic-Button">
				<ul class="Center">
					<li><a href="javascript:goExcelDown('date');"  class="btn_Basic"><strong>엑셀다운로드</strong></a></li>
				</ul>	
			</div>
		</ul>
	</div>		
			
	</html:form>
	<!-- end 검색 테이블 감싸기 -->
	<br />
	<!-- end .ntiscontents -->
	</div>
<!-- end .container -->
</div>
<%@  include file="/include/bottom.jsp"%>	