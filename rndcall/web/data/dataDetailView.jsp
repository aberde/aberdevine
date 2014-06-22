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
<bean:define name="DataForm" property="searchVO" id="searchVO" type="kr.go.rndcall.mgnt.data.vo.DataSearchVO"/>
<bean:define name="DataForm" property="totRowCount" id="totRowCount" type="java.lang.Integer" />
<bean:define name="DataForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String" />
<bean:define name="DataForm" property="maxPageItems" id="maxPageItems" type="java.lang.String" />
<bean:define name="DataForm" property="pagerOffset" id="pagerOffset" type="java.lang.String" />
<bean:define name="DataForm" property="searchVO.loginId" id="loginId" type="java.lang.String" />
<bean:define name="DataForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String" />
<bean:define name="DataForm" property="searchVO.board_type" id="board_type" type="java.lang.String" />
<bean:define id="path" type="java.lang.String" value="/Data.do"/>
<%
	String uni = searchVO.getUni();
%>
<script type="text/javascript">
<!--		

function goDataList(){
	//fm.elements["method"].value="faqList";
	fm.action = "/switch.do?prefix=/data&method=dataList&page=/Data.do?pager.offset=" + fm.elements["searchVO.pagerOffset"].value;
	fm.submit();
}
function downLoad(fileNM, saveFileNM, filePath, yn){
    fileDownLoad.elements["fileNM"].value = fileNM;
    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
    fileDownLoad.elements["filePath"].value = filePath;
    fileDownLoad.elements["desCipher"].value = yn;
    fileDownLoad.submit();
}


function goDataUpdateForm(){
	fm.elements["method"].value="dataUpdateForm";
	fm.submit();
}
function goDataDelete(){
	if (confirm("정말로 삭제 하시겠습니까?")) {
		fm.elements["method"].value="dataDelete";
		fm.submit();
	}
}
function goIns(arg){
	fm.elements["searchVO.whichSearch"].value="";
	fm.elements["searchVO.searchTxt"].value="";
	fm.elements["searchVO.board_type"].value=arg;	
	fm.elements["method"].value="dataList";
	fm.submit();
}
function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
function goIns(arg){
	fm.elements["searchVO.board_type"].value=arg;	
	fm.elements["method"].value="dataList";
	fm.submit();
}
function goLawInfo(){
	var url = "http://rndgate.ntis.go.kr/switch.do?prefix=/un/rndLaw&page=/unRndLaw.do?method=retrieveLawSearchByKey";
	window.open(url);
	document.location.href = "/switch.do?prefix=/data&page=/Data.do?method=dataListLaw&searchVO.menu_sn=02";
}
//-->
</script>
</head>
<form name="fileDownLoad" method="post" action="/downLoad.do" >
    <input type="hidden" name="fileNM"/>
    <input type="hidden" name="saveFileNM"/>
    <input type="hidden" name="filePath"/>
	<input type="hidden" name="desCipher" value="N"/>
</form>	
<div class="LY-Container">
	<!--  start 도움말 외각 라인 -->

	<!-- srart 검색 테이블 감싸기 -->
	<html:form action="Data" method="post" name="fm" type="kr.go.rndcall.mgnt.data.form.DataForm">
	<html:hidden name="DataForm" property="method" value="dataDetailView"/>
	<html:hidden name="DataForm" property="searchVO.loginId"/>
	<html:hidden name="DataForm" property="searchVO.name"/>
	<html:hidden name="DataForm" property="searchVO.seq"/>
	<html:hidden name="DataForm" property="searchVO.board_type"/>
	<html:hidden name="DataForm" property="searchVO.whichSearch"/>
	<html:hidden name="DataForm" property="searchVO.searchTxt"/>
	<html:hidden name="DataForm" property="searchVO.menu_sn"/>
	<html:hidden name="DataForm" property="searchVO.uni"/>
	<html:hidden name="DataForm" property="searchVO.pagerOffset"/>
	<!-- start # 레프트 메뉴 -->
	<div id="LY-Left">
		<!-- start # left menu  -->
		<ul id="Left-Menu">
			<li><a href="#" onclick="goLawInfo()"; onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image8','','/images/Menu/left/lm02_01_on.gif',8)"><img src="/images/Menu/left/lm02_01_off.gif" name="Image8" border="0" id="Image8" alt="기타자료실" /></a></li>
			<li><a href="JavaScript:goIns('DATA')"><img src="../images/Menu/left/lm02_03_off.gif" alt="기타자료" /></a></li>
			</ul>
			<SCRIPT type=text/javascript>
			<!--
			var ObjEventLeftMenu = new EventLeftMenu(2, 2, 0);
			//-->
			</SCRIPT>
	</div>
	<!-- end # 레프트 메뉴 -->
	<!-- end # LY-ContentTitle -->
		<div class="LY-Content">
		<!-- start # LY-ContentTitle -->
		<div class="LY-ContentTitle">
			<h1><img src="/images/content/Content_Title02_3.gif" alt="기타자료 - R&amp;D법령 관련 기타 규정 및 안내자료를 조회할 수 있습니다." /></h1>
		</div>
		<!-- end # LY-ContentTitle -->
		<!-- start # InforSearch // 멀티라인 -->
		<!-- start # OnLine-STab -->
	<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
			<colgroup>
				<col width="100px" />
				<col width="*" />
			</colgroup>
			<tr>
				<th class="Btmline">
					<b>[기타자료실]</b>
				</th>
				<td><b><bean:write name="DataForm" property="vo.title" filter="false"/></b></td>
			</tr>
			<tr>
				<th class="Btmline">내용</th>
				<td><bean:write name="DataForm" property="vo.contents" filter="false"/>
				<br/><br/>
				<logic:notEmpty name="DataForm" property="voList">
				첨부파일 : 
					<logic:iterate name="DataForm" property="voList" indexId="rowNum" id="attachVO">
						<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');" ><bean:write name="attachVO" property="file_nm" /></a></a><br/>
					</logic:iterate>
				</logic:notEmpty>
				</td>
			</tr>
			</table>

		<div style="margin:10px 0; display:block">
		<ul class="Center">
			<div class="Basic-Button">
			<ul class="Center">
				<%
				if(uni.equals("uni")){
				%>
				<li><a href="JavaScript:history.back()" class="btn_Basic"><strong>목록</strong></a>
			   	<%
				}else{
			   	%>
				<li><a href="JavaScript:goDataList()"  class="btn_Basic"><strong>목록</strong></a>
				<%
				}
				%>
				<%
				if(roleCd.equals("0000Z") || roleCd.equals("0000C")){
				%>
				<a href="JavaScript:goDataUpdateForm()" class="btn_Basic"><strong>수정</strong></a>
				<a href="JavaScript:goDataDelete()" class="btn_Basic"><strong>삭제</strong></a></li>
				<% 
				}
				%>
			</ul>	
			</div>
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