<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
### 경로   : <%= request.getRequestURI() %>
### 사업명 : 11년도 개방형 연구성과분석지원서비스
### 사업일 : 2011-07-10 ~ 2011-08-31
### 개발일 : 2011-07-14
### 개발자 : 박인선
-->
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/include/top.jsp"%>

<bean:define name="memberForm" property="errCd" id="errCd" type="java.lang.String"/>
<bean:define name="memberForm" property="errMsg" id="errMsg" type="java.lang.String"/>

<bean:define id="path" type="java.lang.String" value="/member.do"/>

<html>
<head>
<title>R&amp;D도우미센터</title>
<html:form action="/member" method="post" name="fmDoc" type="kr.go.rndcall.mgnt.member.MemberForm">
	<html:hidden name="memberForm" property="method" value="getOldDocList"/>
	<html:hidden name="memberForm" property="vo.email"/>
	<html:hidden name="memberForm" property="vo.name"/>
	<html:hidden name="memberForm" property="vo.login_id"/>
	<html:hidden name="memberForm" property="searchVO.menu_sn" value="06"/>
</html:form>
<%
  if(mainLoginVO != null && mainLoginVO.getLogin_id()!=null && !mainLoginVO.getLogin_id().equals("guest")) {
%>
<script language="JavaScript">
if ("<%= errMsg %>" == "0") {
	document.location.href = "/index.jsp";
} else {
	fmDoc.method.value = "getOldDocList";
	fmDoc.submit();
}
</script>
<%
  }
%>



<script language="JavaScript" src="/js/validate.js"></script>
<script>
<!--

var mod_newPwIsNotExp = "변경 비밀번호는 9~20자 이내 영문 ,숫자 및 특수문자로만 입력하셔야 합니다";
var mod_newPw2IsNull = "변경비밀번호를 한번 더 입력하세요.";
var mod_newPwNotEquals = "변경비밀번호 확인이 일치하지 않습니다. 다시 입력하여 주십시오";
var reg_idIsNull = "아이디를 입력하세요.";
var reg_idIsNotExp = "아이디는 6~20자 이내 영문 또는 영문+숫자로만 입력하셔야 합니다.";
var reg_notCheckId = "아이디 중복을 체크하세요.";
var reg_pwIsNotExp = "비밀번호는 영문자, 숫자 또는 특수문자를 조합하여 9자리 이상 입력하셔야 합니다.";
var reg_pwIsNotExp1 = "비밀번호는 9~20자 이내로 입력하셔야 합니다.";
var reg_pwIsNotExp2 = "비밀번호는 사용자ID와 중복 될 수 없습니다. 다시 입력하여 주십시오.";
var reg_pwIsNull = "비밀번호를 입력하세요.";
var reg_pw2IsNull = "비밀번호를 한번 더 입력하세요.";
var reg_pwNotEquals = "비밀번호 불일치. 다시 입력하세요.";
var reg_pwEquals = "비밀번호 확인.";
var idcheckWin;
var chk_yakgwan = "약관에 동의하셔야 합니다.";
var chk_gaeinjeongbo = "개인정보 활용에 동의하셔야 합니다.";

/**
* 아이디 중복확인 팝업 오픈
*/
function openCheckUserIdPop() {
	var idcheckUrl = "/switch.do?prefix=&page=/member.do?method=getIdCheckForm";
	
	//1.아이디
	if ( chkNull( fm.elements["vo.login_id"].value ) ) {
		alert( reg_idIsNull );
		fm.elements["vo.login_id"].focus();
		return;
	}
	//2.아이디 형식 체크
	if( !chkIdExp( fm.elements["vo.login_id"].value ) ) {
		alert( reg_idIsNotExp );
		fm.elements["vo.login_id"].value = "";
		fm.elements["vo.login_id"].focus();
		return;
	}
	var winfeatures = "toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=435,height=245";
	var url = idcheckUrl + "&searchVO.login_id=" + fm.elements["vo.login_id"].value;
	//var url = idcheckUrl;
	if( idcheckWin != null ) {
		idcheckWin.close();
		idcheckWin = null;
	}
	idcheckWin = window.open( url, "idcheck", winfeatures);
}

function fnCheck(){
	var str = fm.elements["vo.password"].value;	
	
	if( !chkPWExp(str)) {
		alert(reg_pwIsNotExp);
		fm.elements["vo.password"].value = "";
		fm.elements["vo.password"].focus();
		return false;
	}

}

// 입력한두개의  비밀번호가 같은지 비교
function fnCompare(){
	if(fm.elements["vo.password"].value != fm.elements["vo.re_password"].value){
//		alert(mod_newPwNotEquals);
		document.getElementById("repw").style.color = '#FF0000';
		document.getElementById("repw").innerHTML = '<font="red">* ' + reg_pwNotEquals + '</font>';
//		fm.elements["vo.password"].value ="";
		fm.elements["vo.re_password"].value = "";
		fm.elements["vo.re_password"].focus();
		return false;
	} else {
		document.getElementById("repw").innerHTML = '<font="blue">* ' + reg_pwEquals + '</font>';
		return true;
	}
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
    chk2 = /[a-z]/i;  //적어도 한개의 a-z 확인
    return chk1.test( str ) && chk2.test( str ); //&& chk3.test( str );
}

// 비밀번호 정규표현식 체크
function chkPWExp( str ) {
    
	chk1 = /[a-zA-Z]/i;  //적어도 한개의 영문
	chk2 = /[0-9]/i;     //적어도 한개의 숫자
    chk3 = /[~!@\#$%^&*\()\-=+_]/i;  //적어도 한개의 툭수문자 확인
    chk4 = /[a-zA-Z0-9~!@\#$%^&*\()\-=+_]{9,30}/i

    return chk1.test( str ) && chk2.test( str ) && chk3.test( str ) && chk4.test( str );
}

function checkRegisterForm(){
	fm.elements["vo.email"].value = fm.email_id.value+"@"+fm.email_domain.value;
	
	if(validate()) {
		if(fm.elements["vo.checkIdVal"].value !="Y"){
			alert(reg_notCheckId);
			return;
		}else{
			fm.elements["method"].value = "getInsert";
			fm.submit();
		}
	}
}

function validate(){

	if (isRequired(fm.elements["vo.login_id"])){
		return false;
	} else  if (isRequired(fm.elements["vo.password"])){
		return false;
	} else if (fnCheck()) {
		return false;
	} else if (isRequired(fm.elements["vo.re_password"])){
		return false;
	} else if (!fnCompare()) {
		return false;
	} else if (isRequired(fm.elements["vo.name"])){
		return false;
//	} else if (isRequired(fm.elements["email_id"])||isRequired(fm.elements["email_domain"])){
//		return false;
	} else {
		return true;
	}
}

function ready() {
	alert("준비중 입니다.");
}

function goIndex() {
	document.location.href = "/index.jsp";
}

SEPARATOR_DEFAULT = '-';
String.prototype.maskPhoneNo = function() {
 var phoneNo = this.removeAll(SEPARATOR_DEFAULT);

 var idx = 0;
 var result = "";

 if (phoneNo.substr(0, 2) == "02") {
  result += (phoneNo.substr(0, 2) + SEPARATOR_DEFAULT);
  idx = 2;
 } else if (phoneNo.charAt(0) == "0") {
  result += (phoneNo.substr(0, 3) + SEPARATOR_DEFAULT);
  idx = 3;
 }

 if (phoneNo.substr(idx).length < 4) {
  result += phoneNo.substr(idx);
 } else if (phoneNo.substr(idx).length < 8) {
  result += (phoneNo.substr(idx, 3)
          + SEPARATOR_DEFAULT
             + phoneNo.substr(idx + 3));
 } else {
  result += (phoneNo.substr(idx, 4) 
          + SEPARATOR_DEFAULT
             + phoneNo.substr(idx + 4));
 }

 return result;
}

function numeralsOnly(evt) {
    evt = (evt) ? evt : event;
    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :
        ((evt.which) ? evt.which : 0));
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        alert("숫자만 입력하십시오.");
		event.returnValue = false;
		return event.returnValue;
    }
    return true;
}


//-->
</script>

<!-- start # LY-Container -->
<div class="LY-Container">


<html:form action="/member" method="post" name="fm" type="kr.go.rndcall.mgnt.member.MemberForm">
	<html:hidden name="memberForm" property="method" value="getInsert"/>
	<html:hidden name="memberForm" property="vo.email"/>
	<html:hidden name="memberForm" property="vo.checkIdVal"/>
	<html:hidden name="memberForm" property="searchVO.menu_sn" value="06"/>

<!-- start # 안내글 -->
<div class="Gi-Guide">
	<ul class="Guide">
		<li class="Title"><img src="/images/content/Gg_GuideTitle.gif" alt="안내" /></li>
		<li class="Content" style="text-align:center;">Email 및 휴대폰번호는 필수입력 항목이 아니며 일부 서비스에 제한적으로 활용됩니다.<br />
							(ID/PW찾기, 답변등록 알림서비스, 기존 마이페이지 데이터 이관 등)</li>
	</ul>
	<ul class="GText-end">
		<li>&nbsp;</li>
	</ul>
</div>
<br />

<!-- start # 회원가입 -->
<div class="Join-InforTip">
	<div class="Join-Check">
	<!-- start #  가입양식 -->
	<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
		<colgroup>
			<col width="130px" />
			<col width="*" />
		</colgroup>
		<tr>
			<th class="Btmline Left"><img src="/images/icon/icon_check.gif" alt="체크" /> 사용자 아이디  </th>
			<td><html:text name="memberForm" property="vo.login_id" size="20" title="사용자 ID" tabindex="2"/>
             	<a href="javaScript:openCheckUserIdPop();" class="btn_TBasic"><strong>아이디 중복확인</strong></a>
            </td>
		</tr>
		<tr>
			<th class="Btmline Left"><img src="/images/icon/icon_check.gif" alt="체크" /> 비밀번호 </th>
             <td>
               <html:password name="memberForm" property="vo.password" size="20" title="비밀번호" tabindex="3"/>
               * 영문자와 숫자 및 특수문자를 사용하여 9자리 이상으로 조합 <span class="Orenge">(예시:abc123!@#)</span>
             </td>
		</tr>
		<tr>
			<th class="Btmline Left"><img src="/images/icon/icon_check.gif" alt="체크" /> 비밀번호 확인 </th>
             <td><html:password name="memberForm" property="vo.re_password" size="20" title="비밀번호확인" tabindex="4"/>&nbsp;<span id="repw">* 비밀번호를 한 번 더 입력하십시오.</span></td>
		</tr>
		<tr>
			<th class="Btmline Left"><img src="/images/icon/icon_check.gif" alt="체크" /> 이름 </th>
             <td><html:text name="memberForm" property="vo.name" size="20" title="이름" tabindex="5"/></td>
		</tr>
		<tr>
			<th class="Btmline Left"> 휴대폰 </th>
             <td><html:text name="memberForm" property="vo.mobile" size="20" title="휴대폰" tabindex="6" style="ime-mode:disabled;" onkeypress="return numeralsOnly(event)" onkeyup="this.value = this.value.maskPhoneNo()" maxlength="13"/> <span class="Orenge">(예시:01012345678)</span></td>
		</tr>
		<tr>
			<th class="Btmline Left"> 이메일 </th>
			<td>
				<input type="text" id="email_id" name="email_id" size="20" title="이메일주소" tabindex="7"/> @ 
				<input type="text" id="email_domain" name="email_domain" size="20" title="이메일주소도메인" tabindex="7"/>
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
	<!-- start # 버튼 -->
	<div class="Basic-Button">
		<ul class="Right">
			<li><a href="javascript:checkRegisterForm();" class="btn_BSave"><strong>저장</strong></a> <a href="javascript:goIndex();" class="btn_Basic"><strong>취소</strong></a></li>
		</ul>	
	</div>
</div>
</html:form>
<!-- end # Basic-List -->

</div>
</div>
<!-- end # LY-Container -->
<%@ include file="/include/bottom.jsp"%>
