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
<%
	if (mainLoginVO == null || !mainIsLogin) {	
%>
	<script type="text/javascript">
		alert('로그인이 필요한 메뉴입니다.');
		document.location.href = "index.jsp";
	</script>
<%		
	}
%>

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


function goMemberUpdate(){
	if(fnCompare()){
		if(confirm("회원정보를 수정하시겠습니까?")){
			fm.elements["vo.email"].value = fm.email_id.value+"@"+fm.email_domain.value;
			fm.elements["method"].value = "getUpdate";
			fm.submit();
		}
	}
}

function validate(){

	if (isRequired(fm.elements["vo.login_id"])){
		return false;
	} else if (!fnCompare()) {
		return false;
	} else if (isRequired(fm.elements["vo.mobile"])){
		return false;
	} else if (isRequired(fm.elements["email_id"])||isRequired(fm.elements["email_domain"])){
		return false;
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
//-->
</script>

<!-- start # LY-Container -->
<div class="LY-Container">


<html:form action="/member" method="post" name="fm" type="kr.go.rndcall.mgnt.member.MemberForm">
<html:hidden name="memberForm" property="method" value="getUpdate"/>
<html:hidden name="memberForm" property="vo.name"/>
<html:hidden name="memberForm" property="vo.email"/>
<html:hidden name="memberForm" property="vo.login_id"/>
<html:hidden name="memberForm" property="searchVO.menu_sn"/>
<bean:define name="memberForm" property="vo.email" id="email" type="java.lang.String"/>

<!-- start # 회원가입 -->
<div class="Join-InforTip">

	<!-- start #  가입양식 -->
	<div class="Join-Check">
		<img src="/images/content/Join_Text05.gif" alt="개인정보 수정" style=" float:left;" />
		<img src="/images/content/Join_Text01.gif" alt="[ 체크 ] 는 필수입력 사항입니다." />
	</div>
	<!-- start # 상세보기 -->
	<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
		<colgroup>
			<col width="130px" />
			<col width="*" />
		</colgroup>
		<tr>
			<th class="Btmline Left">&nbsp;&nbsp; 사용자 아이디 </th>
			<td><strong><bean:write name="memberForm" property="vo.login_id" /></strong></td>
		</tr>
		
		<tr>
			<th class="Btmline Right"><img src="/images/icon/icon_check.gif" alt="체크" /> 비밀번호 </th>
             <td>
               <html:password name="memberForm" property="vo.password" size="20" title="비밀번호" tabindex="3"/>
               * 영문자와 숫자 및 특수문자를 사용하여 9자리 이상으로 조합 <span class="Orenge">(예시:abc123!@#)</span>
             </td>
		</tr>
		<tr>
			<th class="Btmline Right"><img src="/images/icon/icon_check.gif" alt="체크" /> 비밀번호 확인 </th>
             <td><html:password name="memberForm" property="vo.re_password" size="20" title="비밀번호확인" tabindex="4"/>&nbsp;<span id="repw">* 비밀번호를 한 번 더 입력하십시오.</span></td>
		</tr>
		<tr>
			<th class="Btmline Right">&nbsp;&nbsp; 이름 </th>
			<td><bean:write name="memberForm" property="vo.name"/></td>
		</tr>
		<tr>
			<th class="Btmline Right"> 휴대폰 </th>
			<td><html:text name="memberForm" property="vo.mobile" size="20" title="휴대폰" tabindex="6" style="ime-mode:disabled;" onkeypress="return numeralsOnly(event)" onkeyup="this.value = this.value.maskPhoneNo()" maxlength="13"/> <span class="Orenge">(예시:01012345678)</span>
		</tr>
		<tr>
			<th class="Btmline Right"> 이메일 </th>
			<td>
				<%
					String[] arr = null;
					if(!email.equals("")&& !email.equals("@")){
						arr = email.split("@");
					}else{
						arr = new String[2];
						arr[0]="";
						arr[1]="";
					}
				%>
				<input type="text" id="email_id" name="email_id" size="20" title="이메일주소" tabindex="7" value="<%= arr[0] %>"/> @ 
				<input type="text" id="email_domain" name="email_domain" size="20" title="이메일주소도메인" tabindex="8" value="<%= arr[1] %>"/>
				<select name="email_domain_sel" onchange="selectEmailDomain(document.fm)" title="이메일주소도메인선택" tabindex="9">
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
	<!-- start # 버튼 -->
	<div class="Basic-Button">
		<ul class="Right">
			<li><a href="javascript:goMemberUpdate();" class="btn_BSave"><strong>저장</strong></a> <a href="javascript:goIndex();" class="btn_Basic"><strong>취소</strong></a></li>
		</ul>	
	</div>
</div>
</html:form>
<!-- end # LY-Container -->
</div>
</div>
<%@ include file="/include/bottom.jsp"%>


<script>	
	if( "<%=errCd%>" == "true" ) {
		alert("회원정보 변경이 정상적으로 이루어졌습니다.");
		if ("<%= errMsg %>" == "0") {
			document.location.href = "/index.jsp";
		} else {
			fmDoc.method.value = "getOldDocList";
			fmDoc.submit();
		}
	}
</script>
