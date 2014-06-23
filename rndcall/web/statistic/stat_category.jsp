<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>

<bean:define id="path" type="java.lang.String" value="/Statistic .do"/>
<%
	int cnt1 = 0;
	int cnt2 = 0;
	int cnt3 = 0;
	int cnt4 = 0;
	int cnt5 = 0;
	int cnt1_1 = 0;
	int cnt2_1 = 0;
	int cnt3_1 = 0;
	int cnt4_1 = 0;
	int cnt5_1 = 0;
	
	int count=0;
	String old_cate="";
	String old_cate_nm="";
	String new_category2= "";
%>
<script language="JavaScript" src="/js/FusionCharts.js"></script>
<script language="JavaScript" src="/js/rowspan-bacchusl.js"></script>
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
<script type="text/javascript">
<!--
activeDebug = true;  
module = '/switch.do?prefix=/statistic&method=getStatCategory&page=';
		
window.onload=function(){
	cellMergeChk(iaAdminSubjSub, 2, 0);		//첫번째 td 처리
}
	
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

function goExcelDown(arg){
	fm.elements["searchVO.down_type"].value=arg;	
	fm.elements["method"].value="getStatExcelDownLoad";
	fm.submit();
}

function goStatList(arg1, arg2, arg3){

	var width = '900';
    var height = '560';
    var left = (screen.width - width)/2;
    var top = (screen.height - height)/2;
   	var winNM = 'getStatList';
   	var url = '/switch.do?prefix=/statistic&method=getStatCategory&page=/Statistic.do?method=getStatList&searchVO.whichSearch='+arg1+'&searchVO.category1='+arg2+'&searchVO.category2='+arg3;  
    var windowFeatures = "width=" + width + ",height=" + height +
        ",status,resizable,scrollbars=yes,left=" + left + ",top=" + top +
        ",screenX=" + left + ",screenY=" + top;
   	var formObjNM = 'fm';
    var win = window.open(url, winNM, windowFeatures);
}

function showChart(){

		var chartObj = new FusionCharts("/charts/MSColumn3D.swf","chart0", "654", "200", "0", "30");
		var chartUrl = "/statistic/charts/chart0.jsp";

		chartObj.setDataURL(chartUrl);
		chartObj.render("chart0");
	}
	
function goSearch(){
	fm.elements["method"].value="getStatCategory";
	fm.submit();
}
//-->
</script>
<div class="LY-Container">
	<html:form action="/Statistic" method="post" name="fm" type="kr.go.rndcall.mgnt.statistic.form.StatisticForm">
	<html:hidden name="StatisticForm" property="method" value="getStatCategory"/>
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
   						 <td><a href="JavaScript:goCategoryL()">
   						 <img src="../images/content/Statistic_STap01_Up.gif" alt="분야별통계" border="0" /></a></td>
   						 <td><a href="JavaScript:goVisitL()" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image7','','../images/content/Statistic_STap03_Up.gif',7)">
						 <img src="../images/content/Statistic_STap03_Dw.gif" name="Image7" border="0" id="Image7" alt="접속자현황" /></a></td>
  					</tr>
				</table>
			</li>
		</ul>				
	</div>
	<!-- end # OnLine-STab --> 

	<br />
<!-- start # InforSearch // 멀티라인 -->
	<div class="InforSearch" title="검색">
			<ul class="Search">
					<li>
					<table border="0" cellspacing="0" cellpadding="0" class="Search-Pre">
	  					<tr>
					   		<td>
					   			<html:select name="StatisticForm" property="searchVO.start_yy" title="시작년도" alt="시작년도">
								<html:options name="StatisticForm" property="yearListDesc"/>
								</html:select>년
								<html:select name="StatisticForm" property="searchVO.start_mm" title="시작월" alt="시작월">
								<html:options name="StatisticForm" property="mon_list"/>
								</html:select>월
								~
					   			<html:select name="StatisticForm" property="searchVO.end_yy" title="종료년도" alt="종료년도">
								<html:options name="StatisticForm" property="yearListDesc"/>
								</html:select>년
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
			<table border="0" cellspacing="0" cellpadding="0" class="Basic-List" id="iaAdminSubjSub">
			<colgroup>
				<col width="140px" />
				<col width="130px" />
				<col width="70px" />
				<col width="70px" />
				<col width="70px" />
				<col width="70px" />
				<col width="70px" />
			</colgroup>
			<thead>
			<tr>
				<th class="center" rowspan="2">대분류명</th>
				<th class="center" rowspan="2">소분류명</th>
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
					<bean:define name="vo" property="category1" id="category1" type="java.lang.String"/>
					<bean:define name="vo" property="category2" id="category2" type="java.lang.String"/>					
					<bean:define name="vo" property="category1_nm" id="category1_nm" type="java.lang.String"/>
					<bean:define name="vo" property="total_cnt" id="total_cnt" type="java.lang.Integer"/>
					<bean:define name="vo" property="online_cnt" id="online_cnt" type="java.lang.Integer"/>
					<bean:define name="vo" property="offline_cnt" id="offline_cnt" type="java.lang.Integer"/>
					<bean:define name="vo" property="disposal_cnt" id="disposal_cnt" type="java.lang.Integer"/>						
					<bean:define name="vo" property="undisposal_cnt" id="undisposal_cnt" type="java.lang.Integer"/>
<%
					cnt1 += total_cnt.intValue();
					cnt2 += online_cnt.intValue();
					cnt3 += offline_cnt.intValue();
					cnt4 += disposal_cnt.intValue();
					cnt5 += undisposal_cnt.intValue();
					if(category1.equals(category2)){
						new_category2 ="";
					}else{
						new_category2 = category2;
					}
					if(category1.equals(old_cate)){
						cnt1_1 += total_cnt.intValue();
						cnt2_1 += online_cnt.intValue();
						cnt3_1 += offline_cnt.intValue();
						cnt4_1 += disposal_cnt.intValue();
						cnt5_1 += undisposal_cnt.intValue();
					}else{
						if(!old_cate.equals("")&& !old_cate.equals("1")&&!old_cate.equals("2")&&!old_cate.equals("6")){
%>
						<tr>
							<td style="text-align:left;"><%=old_cate_nm %></td>
							<td style="text-align:left;"><b>소계</b></td>
							<td style="text-align:center;"><a href="JavaScript:goStatList('SUB_T','<%=old_cate%>','')"><b><%=cnt1_1%></b></a></td>
							<td style="text-align:center;"><a href="JavaScript:goStatList('SUB_ON','<%=old_cate%>','')"><b><%=cnt2_1%></b></a></td>
							<td style="text-align:center;"><a href="JavaScript:goStatList('SUB_OFF','<%=old_cate%>','')"><b><%=cnt3_1%></b></a></td>
							<td style="text-align:center;"><a href="JavaScript:goStatList('SUB_Y','<%=old_cate%>','')"><b><%=cnt4_1%></b></a></td>						
							<td style="text-align:center;"><a href="JavaScript:goStatList('SUB_N','<%=old_cate%>','')"><b><%=cnt5_1%></b></a></td>	
						</tr>
<%
						}
						cnt1_1 = 0;
						cnt2_1 = 0;
						cnt3_1 = 0;
						cnt4_1 = 0;
						cnt5_1 = 0;
						cnt1_1 += total_cnt.intValue();
						cnt2_1 += online_cnt.intValue();
						cnt3_1 += offline_cnt.intValue();
						cnt4_1 += disposal_cnt.intValue();
						cnt5_1 += undisposal_cnt.intValue();
						old_cate =category1;
						old_cate_nm=category1_nm;
					}
%>

					<tr>
						<td style="text-align:left;"><bean:write name="vo" property="category1_nm"/></td>
						<td style="text-align:left;"><bean:write name="vo" property="category2_nm"/></td>
						<td style="text-align:center;"><a href="JavaScript:goStatList('T','<bean:write name="vo" property="category1"/>','<%=new_category2%>')"><bean:write name="vo" property="total_cnt"/></a></td>
						<td style="text-align:center;"><a href="JavaScript:goStatList('ON','<bean:write name="vo" property="category1"/>','<%=new_category2%>')"><bean:write name="vo" property="online_cnt"/></a></td>
						<td style="text-align:center;"><a href="JavaScript:goStatList('OFF','<bean:write name="vo" property="category1"/>','<%=new_category2%>')"><bean:write name="vo" property="offline_cnt"/></a></td>
						<td style="text-align:center;"><a href="JavaScript:goStatList('Y','<bean:write name="vo" property="category1"/>','<%=new_category2%>')"><bean:write name="vo" property="disposal_cnt"/></a></td>						
						<td style="text-align:center;"><a href="JavaScript:goStatList('N','<bean:write name="vo" property="category1"/>','<%=new_category2%>')"><bean:write name="vo" property="undisposal_cnt"/></a></td>
					</tr>			
				</logic:iterate>
			</logic:notEmpty>
			<tr bgcolor="#e0ffff"><td class="center" style="display:none"></td>
				<td style="text-align:left;" colspan="2"><b>총건수</b></td>
				<td style="text-align:center;"><b><%=cnt1%></b></td>
				<td style="text-align:center;"><b><%=cnt2%></b></td>
				<td style="text-align:center;"><b><%=cnt3%></b></td>
				<td style="text-align:center;"><b><%=cnt4%></b></td>						
				<td style="text-align:center;"><b><%=cnt5%></b></td>
			</tr>
			</tbody>	
		</table>
		</ul>
	</div>
	<br/>
	<div style="margin:10px 0; display:block">
		<ul class="Center">
			<div class="Basic-Button">
				<ul class="Center">
					<li><a href="javascript:goExcelDown('category');"  class="btn_Basic"><strong>엑셀다운로드</strong></a></li>
				</ul>	
			</div>
		</ul>
	</div>
	
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