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
<bean:define name="DataForm" property="voList" id="file_list" type="java.util.ArrayList"/>
<bean:define name="DataForm" property="searchVO.board_type" id="board_type" type="java.lang.String" />
<bean:define id="path" type="java.lang.String" value="/Data.do"/>
<script language="JavaScript" src="/js/file.js"></script>
<%  String size = "70"; // default size
    if(!Util.isNull(request.getParameter("size"))) {
    	size = request.getParameter("size");
    }		
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
	function dataUpdate(){
		var del_file_id = "";
		if(validate()){
<%
				if(file_list.size() > 0){
%>  		
					if(document.fm.file_del.value == undefined){
						for(i=0; i < fm.file_del.length; i++){
				     		if(fm.file_del[i].checked){
				     			del_file_id += fm.file_del[i].value+",";
				    		}
				   		}
				   		del_file_id = del_file_id.substr(0,(del_file_id.length-1));
					}else{
						if(fm.file_del.checked){
				     		del_file_id = fm.file_del.value;
				    	}
					}
<%
				}
%>			
   			fm.elements["vo.del_file_id"].value=del_file_id;
			fm.elements["method"].value="dataUpdate";
			fm.submit();
		}
	}
	function fncFileAddLenChk(fileObjName, size){
	   
	    var fileArea = document.getElementById(fileObjName);
	    var childNds = fileArea.childNodes;
	    
	    //alert(childNds.length);
	    if(childNds.length < 10) //최대 3개까지 첨부 가능
	        fncFileAdd(fileObjName, size);
	}
	
	
	function validate() {
		
		//제목 필수 입력 체크
		if (isRequired(fm.elements["vo.title"])){
			return false;
		}
		//질의내용 필수 입력 체크
		if (isRequired(fm.elements["vo.contents"])){
			return false;
		}
	
	 	return true;
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
window.onload= function() { // onload 시 호출. 데이터 초기화.
}
//-->
</script>
</head>
<div class="LY-Container">
	<html:form action="/Data" method="post" name="fm" enctype="multipart/form-data" type="kr.go.rndcall.mgnt.data.form.DataForm" onsubmit="return checkOnSubmit(this)">
	<html:hidden name="DataForm" property="method" value="dataUpdate"/>
	<html:hidden name="DataForm" property="searchVO.loginId"/>
	<html:hidden name="DataForm" property="searchVO.name"/>
	<html:hidden name="DataForm" property="searchVO.seq"/>
	<html:hidden name="DataForm" property="searchVO.board_type"/>
	<html:hidden name="DataForm" property="searchVO.menu_sn"/>
	<html:hidden name="DataForm" property="vo.del_file_id"/>
	<html:hidden name="DataForm" property="vo.file_id"/>
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
	<div class="LY-Content">
	<div class="LY-ContentTitle">
		<h1><img src="/images/content/Content_Title02_3.gif" alt="기타자료 - R&amp;D법령 관련 기타 규정 및 안내자료를 조회할 수 있습니다." /></h1>
	</div>
	<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
		<colgroup>
			<col width="100px" />
			<col width="*" />
		</colgroup>
		<tr>
			<th class="Btmline">제목</th>
			<td>
				<html:text name="DataForm" property="vo.title" size="90" alt="제목" title="제목"/>
			</td>
		</tr>
		<tr>
			<th class="Btmline">내용</th>
			<td><html:textarea name="DataForm" property="vo.contents" rows="20" cols="90"  alt="질의 내용" title="질의 내용"/></td>
		</tr>
		<tr>
			<th class="Btmline">파일 삭제여부</th>
			<td>
			<logic:iterate name="DataForm" property="voList" indexId="rowNum" id="attachVO">						
				<bean:define name="attachVO" property="file_id" id="file_id" type="java.lang.String"/>
					<bean:define name="attachVO" property="seq" id="seq" type="java.lang.String"/>
<%
						String f_id = file_id+"-"+seq;
%>
					<input type="checkbox" name="file_del" value="<%=f_id%>"/><a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><img src="/images/icon/disk01.gif" alt="첨부파일"/><bean:write name="attachVO" property="file_nm" /></a><br/>
			</logic:iterate>
			</td>
		</tr>
		<tr>
			<th class="Btmline">첨부파일</th>
			<td colspan="3">
				<a href="#" onclick="fncFileAddLenChk('fileArea', '<%=size%>')" class="btn_TLadd"><strong>파일추가</strong></a>
				<a href="#" onclick="fncFileDel('fileArea')" class="btn_TLdel"><strong>파일제거</strong></a>
				<div id="fileArea"></div>
			</td>
		</tr>
	</table>
	<div style="margin:10px 0; display:block">
		<ul class="Center">
			<div class="Basic-Button">
				<ul class="Center">
					<li><a href="JavaScript:dataUpdate()"  class="btn_Basic"><strong>저장</strong></a>
					<a href="JavaScript:history.back()" class="btn_Basic"><strong>취소</strong></a></li>
				</ul>	
			</div>
		</ul>
	</div>	
	
	</html:form>
	
	</div>
	
<!-- end .container -->
<%@  include file="/include/bottom.jsp"%>