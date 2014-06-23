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
<%@ page import="kr.go.rndcall.mgnt.login.LoginVO" %>

<title>R&amp;D도우미센터 - 로그인</title>
<%
  LoginVO mainLoginVO = (LoginVO) session.getAttribute("loginUserVO");
  if(mainLoginVO != null && mainLoginVO.getLogin_id()!=null && !mainLoginVO.getLogin_id().equals("guest")) {
	System.out.println("======>>>" + mainLoginVO.getLogin_id());
%>
<script language="JavaScript">
opener.document.location.reload();
window.close();
</script>
<%
  } else {
	  System.out.println("Not Login!!!!");
	  if (request.getAttribute("errCd") != null && request.getAttribute("errCd").equals("N")) {
%>
<script language="JavaScript">
	alert('아이디 또는 비밀번호가 다릅니다.');
</script>
<% 	  
	  }
  }
%>
<script>
<!--
var reg_idIsNull = "아이디를 입력하세요.";
var reg_pwIsNull = "비밀번호를 입력하세요.";

function onLoad() {
	document.all.id.focus();
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

function onEnter(){
	if(event.keyCode==13){
		login();
		event.returnValue=false;
	}
}

function login() {	

	//1.아이디
	if ( chkNull( document.all.id.value ) ) {
		alert( reg_idIsNull );
		document.all.id.focus();
		return;
	}
	//2.아이디 형식 체크
	if ( chkNull( document.all.password.value ) ) {
		alert( reg_pwIsNull );
		document.all.password.focus();
		return;
	} 
	
	document.fm2.elements["vo.login_id"].value = document.all.id.value;
	document.fm2.elements["vo.password"].value = document.all.password.value;
	document.fm2.submit();
	
}

function goInsert() {
	opener.document.location.href = "/switch.do?prefix=&page=/member.do?method=getUserInsertForm&searchVO.menu_sn=06";
	window.close();
}

//-->
</script>
<body onload="onLoad()">

<form name="fm2" method="post" action="/login.do" >
  <input type="hidden" name="method" value="login"/>
  <input type="hidden" name="vo.login_id"/>
  <input type="hidden" name="vo.password"/>
</form>

<!-- start # LY-PopLayout  -->
<div class="LY-PopLayout" style="width:500px;">
		<!-- start # 타이틀 -->
		<ul class="Header">
				<li class="Title">로그인</li>
				<li class="Img"></li>
		</ul>
		<!-- start # 컨텐츠 -->
		<ul class="Content">
			<li>
			
				<!-- start # Login-Input -->
					<div class="Pop-Login-Input">
							<ul>
									<li class="LoginTitle"><img src="/images/content/Pop_LoginTitle.gif" alt="로그인" /></li>
									<li class="ID"><img src="/images/content/Pop_LoginTitle_01.gif" alt="아이디" /> <input name="id" type="text" onkeypress="onEnter()"/> </li>
									<li class="PW"><img src="/images/content/Pop_LoginTitle_02.gif" alt="비밀번호" /> <input name="password" type="password" onkeypress="onEnter()"/> </li>
									<li class="Btn-Login"><a href="javascript:login();"><img src="/images/btn/btn_Login.gif" alt="로그인" border="0" /></a></li>
									<li class="Mem-Infor01">
										<img src="/images/content/Pop_LoginText01.gif" class="아이디/비밀번호를 잊으셨나요?" />
										<a href="/member/idFind.jsp"><img src="/images/btn/btn_IdSearch.gif" alt="아이디 찾기" border="0" /></a> 
										<a href="/member/pwFind.jsp"><img src="/images/btn/btn_PwSearch.gif" alt="비밀번호 찾기" border="0" /></a> </li>
									<li class="Mem-Infor02">
										<img src="/images/content/Pop_LoginText02.gif" alt="아이디가 없으신 분은 회원가입을 해주세요." />
										<a href="javascript:goInsert();"><img src="/images/btn/btn_Join.gif" alt="회원가입" border="0" /></a> </li>
							</ul>
					</div>
					<!-- end # Login-Input -->


			</li>
		</ul>
</div>
<!-- end # LY-PopLayout  팝업 레이아웃 -->

</body>
</html>
