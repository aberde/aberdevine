<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
### 경로   : <%= request.getRequestURI() %>
### 사업명 : 11년도 개방형 연구성과분석지원서비스
### 사업일 : 2011-07-10 ~ 2011-08-31
### 개발일 : 2011-07-14
### 개발자 : 박인선
-->
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/include/top_pop.jsp"%>

<bean:define name="memberForm" property="searchVO.login_id" id="id" type="java.lang.String"/>
<bean:define name="memberForm" property="searchVO.checkIdVal" id="checkIdVal" type="java.lang.String"/>
<bean:define id="path" type="java.lang.String" value="/member.do"/>

<title>아이디 중복 확인</title>

<script>


var reg_idIsNull = "아이디를 입력하세요.";
var reg_idIsNotExp = "아이디는 6~20자 이내 영문 또는 영문+숫자로만 입력하셔야 합니다.";

/**
* 아이디 사용
*/
function useCheckId( user_id ) {
	opener.document.fm.elements["vo.login_id"].value = "";
	opener.document.fm.elements["vo.login_id"].value = user_id;
	opener.document.fm.elements["vo.checkIdVal"].value = "<%=checkIdVal%>";
	opener.document.fm.elements["vo.password"].focus();
	opener.document.fm.elements["vo.login_id"].readOnly = true;
	opener.document.fm.elements["vo.login_id"].style.background = "#eeeeee";
	self.close();
}

/**
* 아이디 중복 검색
*/
function reCheckUserId() {
	//var idcheckUrl = "/switch.do?prefix=/member&page=/member.do?method=getIdCheckForm";
	
	//1.아이디
	if ( chkNull( fm.elements["searchVO.login_id"].value ) ) {
		alert( reg_idIsNull );
		fm.elements["searchVO.login_id"].focus();
		return false;
	}
	//2.아이디 형식 체크
	if( !chkIdExp( fm.elements["searchVO.login_id"].value ) ) {
		alert( reg_idIsNotExp );
		fm.elements["searchVO.login_id"].value = "";
		fm.elements["searchVO.login_id"].focus();
		return false;
	}
	fm.elements["method"].value="getIdCheckForm";
	fm.submit();	
}
/**
* 입력여부 체크
*/
function chkNull( str ) {
	if( str.length < 1 || str == "" || str == null ) {
		return true;
	} else {
		return false;
	}
}

/**
* 아이디 정규표현식 체크
*/
function chkIdExp( str ) {
	chk1 = /^[a-zA-Z\d]{6,20}$/i;  //a-z와 0-9이외의 문자가 있는지 확인
//    chk2 = /[a-z]/i;  //적어도 한개의 a-z 확인
    return chk1.test( str ); //&& chk3.test( str );
}
</script>
<body onload="onLoad()">

<html:form action="/member" method="post" name="fm" type="kr.go.rndcall.mgnt.member.MemberForm" onsubmit="return checkOnSubmit(this)">
<html:hidden name="memberForm" property="method" value="getIdCheckForm"/>

<%
			if(!checkIdVal.equals("Y")){
%> 
<!-- start # LY-PopLayout  팝업 레이아웃 -->
<div class="LY-PopLayout" style="width:410px;">
	<!-- start # 타이틀 -->
	<ul class="Header">
		<li class="Title">아이디 중복확인</li>
		<li class="Img"></li>
	</ul>
	<!-- start # 컨텐츠 -->
	<ul class="Content">
		<li>
			<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
				<colgroup>
					<col width="120px" />
					<col width="*" />
				</colgroup>
				<tr>
					<th class="Btmline">아이디</th>
					<td><html:text name="memberForm" property="searchVO.login_id" size="20" title="사용자ID" />
					<a href="javascript:reCheckUserId();" class="btn_TSearch"><strong>중복확인</strong></a>
				</tr>
				<tr>
					<td colspan="2" class="ID-Infor"><strong>중복된 아이디입니다.</strong><br />   다른 아이디를 검색하세요.</td>
				</tr>
			</table>
		</li>
	</ul>
	<!-- start # 버튼 -->
	<ul class="Button">
		<li><a href="javascript:window.close();" class="btn_BClose"><strong>창닫기</strong></a></li>
	</ul>
</div>
<!-- end # LY-PopLayout  팝업 레이아웃 -->
<%
			}else{
%>	
<!-- start # LY-PopLayout  팝업 레이아웃 -->
<div class="LY-PopLayout" style="width:410px;">
	<!-- start # 타이틀 -->
	<ul class="Header">
		<li class="Title">아이디 중복확인</li>
		<li class="Img"></li>
	</ul>
	<!-- start # 컨텐츠 -->
	<ul class="Content">
		<li>
			<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
				<colgroup>
					<col width="120px" />
					<col width="*" />
				</colgroup>
				<tr>
					<th class="Btmline">아이디</th>
					<td><html:text name="memberForm" property="searchVO.login_id" size="20" title="사용자ID" />
					<a href="javascript:reCheckUserId();" class="btn_TSearch"><strong>중복확인</strong></a>
				</tr>
				<tr>
					<td colspan="2" class="ID-Infor"><strong>아이디</strong>는 사용 가능한 아이디 입니다.<br />  아이디를 사용하시겠습니까?</td>
				</tr>
			</table>
		</li>
	</ul>
	<!-- start # 버튼 -->
	<ul class="Button">
		<li><a href="javascript:useCheckId('<%=id%>');" class="btn_BAmemEx"><strong>아이디 사용</strong></a> <a href="javascript:window.close();" class="btn_BClose"><strong>창닫기</strong></a></li>
	</ul>
</div>
<%
			}
%>
</html:form>
</body>
</html>