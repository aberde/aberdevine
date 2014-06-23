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
	fm.elements["searchVO.whichSearch"].value="";
	fm.elements["searchVO.searchTxt"].value="";
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
	<html:hidden name="DataForm" property="method" value="dataList" />
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
	</div>
	<script type=text/javascript>
	<!--
	var ObjEventLeftMenu = new EventLeftMenu(2, 2, 0);
	//-->
	</script>
	<!-- end # 레프트 메뉴 -->
	<!-- start # 컨텐츠 영역 -->
	<div class="LY-Content">
		<!-- start # LY-ContentTitle -->
		<div class="LY-ContentTitle">
			<h1><img src="/images/content/Content_Title02_3.gif" alt="기타자료 - R&amp;D법령 관련 기타 규정 및 안내자료를 조회할 수 있습니다." /></h1>
		</div>
		<!-- end # LY-ContentTitle -->
		<!-- start # InforSearch // 멀티라인 -->
	<div class="InforSearch" title="검색">
		<ul class="Search">
			<li>
				<table border="0" cellspacing="0" cellpadding="0" class="Search-Pre">
					<tr>
	   					 <td>	
							<html:select name="DataForm" property="searchVO.whichSearch">
								<html:option value="title">제목</html:option>
								<html:option value="contents">내용</html:option>
								<html:option value="all">제목+내용</html:option>
							</html:select>
						 	<html:text name="DataForm" property="searchVO.searchTxt" title="제목" styleClass="lineInputKO" size="30" maxlength="35"onchange="trim(this)"  />
						 </td>
	  				     <td class="Btn"><a href="javascript:goSearch()" class="btn_BSearch"><strong>검색</strong></a> </td>
	  				</tr>
				</table>
			</li>
		</ul>
	</div>
	<br/>
	<!-- start # InforSearch // 멀티라인 -->
	<div class="Basic-List-area">
		<ul class="List">
		<table border="0" cellspacing="0" cellpadding="0" class="Basic-List">
			<colgroup>
				<col width="60px" />
				<col width="*" />
				<col width="60px" />
				<col width="120px" />
			</colgroup>
			<thead>
				<tr>
		   			 <th>번호</th>
		   			 <th>제목</th>
		   			 <th>첨부</th>
		   			 <th>등록일</th>
		  		</tr>
	  		</thead>
	  		<tbody>
			<logic:empty name="DataForm" property="voList">
				<tr>
					<td style="border-right:0px;text-align:center; height=:50px;"
						colspan="7">등록된 정보가 없습니다.</td>
				</tr>
			</logic:empty>
			<logic:notEmpty name="DataForm" property="voList">
				<logic:iterate name="DataForm" property="voList" indexId="rowNum" id="vo">
					<tr>
						<td class="center"><%=totRowCount.intValue() - rowNum.intValue() - Util.replaceNull((String) pagerOffset, 0)%></td>
						<td class="Left">
							<bean:define name="vo" property="title" id="title" type="java.lang.String"/>
<%
								String title_n= "";
								int len = 36;
								if(title.length() > len){
									title_n = title.substring(0,len)+"...";
								}else{
									title_n = title;
								}
%> 						
						<a href="JavaScript:dataDetailView('<bean:write name="vo" property="seq"/>')"><%=title_n %></a></td>
						<td class="center">
						<logic:equal name="vo" property="file_id" value="N">
							<html:img src="/images/icon/disk01.gif"/>
						</logic:equal>
						<logic:notEqual name="vo" property="file_id" value="N">
								
						</logic:notEqual>
						</td>
						<td class="center"><bean:write name="vo" property="reg_dt" />
						</td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
			</tbody>
		</table>
		<div style="margin:10px 0; display:block">
			<ul class="Page-Num">
				<%@ include file="/include/page.jsp"%>
			</ul>
		</div>
	</table>
	</ul>
	<%
	if (roleCd.equals("0000Z") || roleCd.equals("0000C")) {
	%>
	<div style="margin:10px 0; display:block">
		<ul class="Center">
			<div class="Basic-Button">
			<ul class="Right">
				<li><a href="/switch.do?prefix=/data&page=/Data.do?method=dataInsertForm&searchVO.menu_sn=02"  class="btn_Basic"><strong>자료실등록</strong></a></li>
			</ul>
		</ul>
	</div>
	<%
	}
	%>
	</div>
	</div>
</html:form> <!-- end 검색 테이블 감싸기 --> <br />
</div>
<%@  include file="/include/bottom.jsp"%>
