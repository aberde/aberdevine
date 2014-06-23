<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
### 경로   : <%= request.getRequestURI() %>
### 사업명 : 11년도 개방형 연구성과분석지원서비스
### 사업일 : 2011-07-10 ~ 2011-08-31
### 개발일 : 2011-07-14
### 개발자 : 박인선
-->
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/include/top_pop.jsp"%>

<title>R&amp;D도우미센터 - 아이디찾기</title>
<script>
<!--

function onLoad() {
	document.all.name.focus();
}

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

function idFind() {	

	//1.이름
	if ( chkNull( document.all.name.value ) ) {
		alert("이름을 입력하세요.");
		document.all.name.focus();
		return;
	}
	//2.이메일
	if ( chkNull( document.all.email_id.value) || chkNull( document.all.email_domain.value) ) {
		alert( "이메일 주소를 입력하세요.");
		document.all.email_id.focus();
		return;
	}
	
	document.fm.elements["vo.name"].value = document.all.name.value;
	document.fm.elements["vo.email"].value = document.all.email_id.value+"@"+document.all.email_domain.value;


	var winfeatures = "toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=325,height=155";
	var url = "/switch.do?prefix=&page=/member.do?method=idFind&vo.name=" + document.all.name.value + "&vo.email=" + escape(document.all.email_id.value+"@"+document.all.email_domain.value);

	idFindWin = window.open( url, "idFind", winfeatures);
	
	self.close();
}

/**
* 이메일 도메인 선택
*/
function selectEmailDomain( form ) {
	var f = form;
	f.email_domain.value = "";
	if( f.email_domain_sel.value != "none" ) {
		f.email_domain.value = f.email_domain_sel.value;
		f.email_domain.readOnly = true;
	}else{
		f.email_domain.readOnly = false;
		f.email_domain.focus();
	}
}

//-->
</script>
<body onload="onLoad()">

<form name="fm" method="post" action="/member.do" >
  <input type="hidden" name="method" value="idFind"/>
  <input type="hidden" name="vo.name"/>
  <input type="hidden" name="vo.email"/>
</form>

<!-- start # LY-PopLayout  -->
<div class="LY-PopLayout" style="width:500px;">
	<!-- start # 타이틀 -->
	<ul class="Header">
			<li class="Title">아이디 찾기</li>
			<li class="Img"></li>
	</ul>
	<!-- start # 컨텐츠 -->
	<ul class="Content">
		<li>
			<h1><img src="/images/content/Pop_Title01.gif" alt="아이디 찾기" /></h1>
			<!-- start # Login-Input -->
			<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
				<colgroup>
					<col width="120px" />
					<col width="*" />
				</colgroup>
				<tr>
					<th class="Btmline">이름</th>
					<td><input type="text" name="name" onkeypress="onEnter()" tabindex="1" style="ime-mode:active;" /></td>
					</tr>
				<tr>
					<th class="Btmline">이메일 주소</th>
					<td>
						<input type="text" id="email_id" name="email_id" size="15" title="이메일주소" tabindex="2" onkeypress="onEnter()" style="ime-mode:inactive;"/> @ 
						<input type="text" id="email_domain" name="email_domain" size="15" title="이메일주소도메인" tabindex="3" onkeypress="onEnter()" style="ime-mode:inactive;"/>
						<select name="email_domain_sel" onchange="selectEmailDomain(document.fm)" title="이메일주소도메인선택" tabindex="8">
							<option value="none" selected="selected">직접입력</option>
							<option value="empal.com">엠파스</option>
							<option value="naver.com">네이버</option>
							<option value="hanmail.net">다음</option>
							<option value="hotmail.com">핫메일</option>
							<option value="yahoo.co.kr">야후</option>
							<option value="nate.com">네이트</option>
							<option value="paran.com">파란</option>
						</select>
					</td>
				</tr>
			</table>
			<br />
			<div class="Basic-Button">
				<ul class="Center">
					<li><a href="javascript:idFind();" class="btn_IBasic"><strong>확인</strong></a> <a href="javascript:fm.reset();" class="btn_IBasic"><strong>취소</strong></a></li>
				</ul>	
			</div>
		</li>
	</ul>
	<br />
	<ul class="Button">
		<li><a href="javascript:window.close();" class="btn_BClose"><strong>창닫기</strong></a></li>
	</ul>
</div>

<!-- end # LY-PopLayout  팝업 레이아웃 -->

</body>
</html>
