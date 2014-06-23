<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>

<bean:define name="OfferForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="OfferForm" property="searchVO.loginId" id="loginId" type="java.lang.String"/>
<bean:define name="OfferForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String"/>
<bean:define name="OfferForm" property="vo.stat" id="stat" type="java.lang.String"/>
<bean:define id="path" type="java.lang.String" value="/Offer.do"/>
<script type="text/javascript">
<!--		

function goOfferList(){
	fm.action = "/switch.do?prefix=/offer&method=offerList&page=/Offer.do?pager.offset=" + fm.elements["searchVO.pagerOffset"].value;
	fm.submit();
}

function goSatiInsert(){
	if("<%=loginId%>" ==""){
		alert("로그인후 이용하실수 있습니다.");
		return;
	}else{
	fm.elements["method"].value="offerSatiInsert";
	fm.submit();
	}
}

function downLoad(fileNM, saveFileNM, filePath, yn){
    fileDownLoad.elements["fileNM"].value = fileNM;
    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
    fileDownLoad.elements["filePath"].value = filePath;
    fileDownLoad.elements["desCipher"].value = yn;
    fileDownLoad.submit();
}

function goCate(arg){
	fm.elements["searchVO.searchCategory"].value=arg;
	fm.elements["method"].value="getInquireList";
	fm.submit();
}

function goOfferUpdate(){
	fm.elements["searchVO.searchCategory"].value="";
	fm.elements["method"].value="offerUpdateForm";
	fm.submit();
}

function goDelete(){
	fm.elements["searchVO.searchCategory"].value="";
	fm.elements["method"].value="getInquireDelete";
	fm.submit();
}

function goOfferAnswerInsert(){
		fm.elements["searchVO.searchCategory"].value="";
		fm.elements["method"].value="offerAnswerInsertForm";
		fm.submit();
}
function goOfferDelete(){
	fm.elements["searchVO.searchCategory"].value="";
	fm.elements["method"].value="offerDelete";
	fm.submit();
}
//-->
</script>
<form name="fileDownLoad" method="post" action="/downLoad.do" >
    <input type="hidden" name="fileNM"/>
    <input type="hidden" name="saveFileNM"/>
    <input type="hidden" name="filePath"/>
	<input type="hidden" name="desCipher" value="N"/>
</form>	
<div class="LY-Container">
	<html:form action="/Offer" method="post" name="fm" type="kr.go.rndcall.mgnt.offer.form.OfferForm">
	<html:hidden name="OfferForm" property="method" value="getInquireInsert"/>
	<html:hidden name="OfferForm" property="vo.cell_number"/>
	<html:hidden name="OfferForm" property="vo.email"/>
	<html:hidden name="OfferForm" property="searchVO.loginId"/>
	<html:hidden name="OfferForm" property="searchVO.name"/>
	<html:hidden name="OfferForm" property="searchVO.seq"/>
	<html:hidden name="OfferForm" property="searchVO.board_type"/>
	<html:hidden name="OfferForm" property="searchVO.searchCategory"/>
	<html:hidden name="OfferForm" property="searchVO.menu_sn"/>
	<html:hidden name="OfferForm" property="searchVO.pagerOffset"/>
	
		<div class="LY-ContentTitle">
			<h1><img src="/images/content/Content_Title06_1.gif" alt="제안하기 - 가장 많이 묻는 질의응답을 카테고리별로 보실 수 있습니다." /></h1>
		</div>&nbsp;&nbsp;&nbsp;
	<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
		<colgroup>
			<col width="130px" />
			<col width="*" />
		</colgroup>
		<tr>
			<th class="Btmline">제목</th>
			<td><strong><bean:write name="OfferForm" property="vo.title" filter="false"/></strong></td>
		</tr>
		<tr>
			<th class="Btmline">등록일</th>
			<td><bean:write name="OfferForm" property="vo.reg_dt"/></td>
		</tr>
		<tr><th class="Btmline">질의내용</th>
			<td>
			<bean:write name="OfferForm" property="vo.contents" filter="false"/>
			<br/><br/>
			<logic:notEmpty name="OfferForm" property="voList">
			첨부파일 : 
			<logic:iterate name="OfferForm" property="voList" indexId="rowNum" id="attachVO">
				<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
			</logic:iterate>
			</logic:notEmpty>
			</td>
		</tr>
<%
		if(stat.equals("Y")){
%>		
		<tr><th class="Btmline1">답변내용</th>
			<td><bean:write name="OfferForm" property="vo.answerContents" filter="false"/>
			<br/><br/>
			<logic:notEmpty name="OfferForm" property="voList1">
			첨부파일 : 
				<logic:iterate name="OfferForm" property="voList1" indexId="rowNum" id="attachVO">
					<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
				</logic:iterate>
			</logic:notEmpty>
		</tr>
<%
		}
%>			
		</table>

		<div class="Basic-Button">
				<ul class="Center">	
				<bean:define name="OfferForm" property="vo.up_del_stat" id="up_del_stat" type="java.lang.String"/>
				<bean:define name="OfferForm" property="vo.stat" id="stat" type="java.lang.String"/>				
				<li><a href="JavaScript:goOfferList()" class="btn_Basic"><strong>목록</strong></a>
<%
				if(roleCd.equals("0000Z") || roleCd.equals("0000C")){
%>
				<a href="JavaScript:goOfferUpdate()" class="btn_Basic"><strong>질의수정</strong></a>
<%
				}
				if(stat.equals("N")){
%>
				<a href="JavaScript:goOfferAnswerInsert()" class="btn_Basic"><strong>답변달기</strong></a>
<%
				}else{
%>
				<a href="JavaScript:goOfferAnswerInsert()" class="btn_Basic"><strong>답변수정</strong></a>
				<a href="JavaScript:goOfferDelete()" class="btn_Basic"><strong>삭제</strong></a>
<% 
				}
%>
				
			</div>
		</div>
	</html:form>
	<!-- end 검색 테이블 감싸기 -->
	<br />
	<br />
	<!-- end .ntiscontents -->
<!-- end .container -->
</div>
<%@  include file="/include/bottom.jsp"%>