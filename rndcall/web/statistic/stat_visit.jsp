<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<bean:define name="StatisticForm" property="searchVO.stat_type" id="stat_type" type="java.lang.String"/>
<bean:define id="path" type="java.lang.String" value="/Statistic .do"/>
<%
	int total_cnt=0;
	int count=0;
%>

<%
	if (mainLoginVO == null || !mainIsLogin) {	
%>
	<script type="text/javascript">
		alert('로그인이 필요한 메뉴입니다.');
		document.location.href = "index.jsp";
	</script>
<%
	} else if (!mainRoleCD.equals("0000C") && !mainRoleCD.equals("0000Z")) {
%>
	<script type="text/javascript">
		alert('권한이 없습니다.');
		document.location.href = "index.jsp";
	</script>
<%		
	}
%>
<script type="text/javascript">
<!--
activeDebug = true;  
module = '/switch.do?prefix=/statistic&method=getStatCategory&page=';
		
function goCategoryL(){
	fm.elements["method"].value="getStatCategory";
	fm.submit();
}

function goDateL(){
	fm.elements["method"].value="getStatDate";
	fm.submit();
}

function goVisitL(){
	fm.elements["searchVO.stat_type"].value="YY";
	fm.elements["method"].value="getStatVisit";
	fm.submit();
}

function goVisitForm(arg){
<%
	if(stat_type.equals("MM")){
%>
		fm.elements["searchVO.start_yy"].value="";
<%
	}
%>	
<%
	if(stat_type.equals("DD")){
%>
		fm.elements["searchVO.start_yy"].value="";
		fm.elements["searchVO.start_mm"].value="";
<%
	}
%>		
	
	fm.elements["searchVO.stat_type"].value=arg;
	fm.elements["method"].value="getStatVisit";
	fm.submit();
}

function goSearch(){
	var stat = fm.elements["stat_type"].value;

	if(stat == "YY"){
		fm.elements["searchVO.start_yy"].value="";
		fm.elements["searchVO.start_mm"].value="";
	}
	if(stat == "MM"){
		fm.elements["searchVO.start_mm"].value="";
	}
	
	fm.elements["searchVO.stat_type"].value = stat;
	fm.elements["method"].value="getStatVisit";
	fm.submit();
}
function goDateL(){
	fm.elements["method"].value="getStatDate";
	fm.submit();
}
function goExcelDown(arg){
	fm.elements["searchVO.down_type"].value=arg;	
	fm.elements["method"].value="getStatExcelDownLoad";
	fm.submit();
}
//-->
</script>
<div class="LY-Container">
	<html:form action="/Statistic" method="post" name="fm" type="kr.go.rndcall.mgnt.statistic.form.StatisticForm">
	<html:hidden name="StatisticForm" property="method" value="getStatVisit"/>
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
						<li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatCategory&searchVO.menu_sn=09"><img src="../images/Menu/left/lm03_04_off.gif" alt="통계관리" /></a></li>
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
   						 <td><a href="JavaScript:goVisitL()">
   						 <img src="../images/content/Statistic_STap03_Up.gif" alt="접속자현황" border="0" /></a></td>
  					</tr>
				</table>
			</li>
		</ul>				
	</div>
	<br/>
	<!-- end # OnLine-STab -->

	<div class="InforSearch" title="검색">
		<ul class="Search">
			<li>
			<table border="0" cellspacing="0" cellpadding="0" class="Search-Pre">
	  		<tr>
				<td>
					<select name="stat_type"  title="검색분류">
					 	<option value="YY" <% if(stat_type.equals("YY")){ %> selected<% } %>>년도별</option>
					 	<option value="MM" <% if(stat_type.equals("MM")){ %> selected<% } %>>월별</option>
					 	<option value="DD" <% if(stat_type.equals("DD")){ %> selected<% } %>>일별</option>
					</select>
					<html:select name="StatisticForm" property="searchVO.start_yy" title="시작년도" alt="시작년도" styleClass="OutLine_Y">
						<option value="">전체</option>
						<html:options name="StatisticForm" property="yearListDesc"/>
					</html:select>년
					<html:select name="StatisticForm" property="searchVO.start_mm" title="시작월" alt="시작월" styleClass="OutLine_Y">
						<option value="">전체</option>
						<html:options name="StatisticForm" property="mon_list"/>
					</html:select>월
				</td>
	  		    <td class="Btn"><a href="javascript:goSearch()" class="btn_BSearch"><strong>검색</strong></a></td>
	  		</tr>
			</table>
			</li>
		</ul>
	</div>
		
	<br/>
<div class="Basic-List-area">
	<!-- start # 리스트 목록 -->
	<ul class="Right">
		<table border="0" cellspacing="0" cellpadding="0" class="Basic-List">
			<colgroup>
				<col width="70px" />
				<col width="100px" />
				<col width="*" />
			</colgroup>
			<thead>
			<tr>
				<th class="Center">순번</th>
				<th class="Center">기준</th>
				<th class="Center">방문자수</th>
			</tr>
			</thead>
			<tbody>
			<logic:empty name="StatisticForm" property="voList">
				<tr><td style="border-right:0px;text-align:center; height=:50px;" colspan="7">등록된 정보가 없습니다.</td></tr>
			</logic:empty>
			<logic:notEmpty name="StatisticForm" property="voList">
				<logic:iterate name="StatisticForm" property="voList" indexId="rowNum" id="vo">					
					<bean:define name="vo" property="cnt" id="cnt" type="java.lang.Integer"/>
<%
						total_cnt +=  cnt.intValue();
%>					
					<tr>
						<td class="Center"><%=++count %></td>
						<td style="text-align:center;"><b><bean:write name="vo" property="code"/></b></td>
						<td style="text-align:center;"><bean:write name="vo" property="cnt"/></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
			<tr>
				<td class="Center"></td>
				<td style="text-align:center;"><b>총계</b></td>
				<td style="text-align:center;"><b><%=total_cnt%></b></td>
			</tr>	
			</tbody>	
			</table>
				<div style="margin:10px 0; display:block">
		<ul class="Center">
			<div class="Basic-Button">
				<ul class="Center">
					<li><a href="javascript:goExcelDown('visit');"  class="btn_Basic"><strong>엑셀다운로드</strong></a></li>
				</ul>	
			</div>
		</ul>
	</div>	
	</html:form>
	<!-- end 검색 테이블 감싸기 -->
	<br />
	<br />
	<!-- end .ntiscontents -->
	</div>
<!-- end .container -->
</div>
<%@  include file="/include/bottom.jsp"%>	