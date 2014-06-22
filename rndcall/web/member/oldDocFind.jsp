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

<bean:define id="path" type="java.lang.String" value="/member.do"/>

<html>
<head>
<title>R&amp;D도우미센터</title>

<script language="JavaScript" src="/js/validate.js"></script>
<script>
<!--

function updateDocRegister() {
	var chkBox = document.getElementsByName("chkDoc");
	var seq = "";
	for (i = 0; i < chkBox.length; i++) {
		if (chkBox[i].checked) {
			if (seq == "") {
				seq = chkBox[i].value;
			} else {
				seq += "," + chkBox[i].value;
			}
		}
	}
	fm.elements["vo.seq"].value = seq;
	fm.method.value = "updateDocRegister";
	fm.submit();
}

function goIndex() {
	var chkBox = document.getElementsByName("chkDoc");
	var seq = "";
	for (i = 0; i < chkBox.length; i++) {
		if (chkBox[i].checked) {
			if (seq == "") {
				seq = chkBox[i].value;
			} else {
				seq += "," + chkBox[i].value;
			}
		}
	}
	fm.elements["vo.seq"].value = seq;
	fm.elements["vo.etc"].value = "complete";
	fm.method.value = "updateDocRegister";
	fm.submit();
}

function fn_chkAll() {
	var chkBox = document.getElementsByName("chkDoc");
	for (i = 0; i < chkBox.length; i++) {
	    if (document.getElementById("chkAll").checked) {
			chkBox[i].checked = true;
	    } else {
			chkBox[i].checked = false;
	    }
	}
}

function viewDetail(arg1, arg2){
	window.open("/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireViewPop&searchVO.board_type=" + arg1 + "&searchVO.seq=" + arg2, "techpop","toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=800,height=800");
}

//-->
</script>

<!-- start # LY-Container -->
<div class="LY-Container">


<html:form action="/member" method="post" name="fm" type="kr.go.rndcall.mgnt.member.MemberForm">
	<html:hidden name="memberForm" property="method" value="getInsert"/>
	<html:hidden name="memberForm" property="vo.email"/>
	<html:hidden name="memberForm" property="vo.name"/>
	<html:hidden name="memberForm" property="vo.login_id"/>
	<html:hidden name="memberForm" property="vo.etc"/>
	<html:hidden name="memberForm" property="vo.seq"/>
	<html:hidden name="memberForm" property="searchVO.menu_sn" value="06"/>

	<!-- start # 안내글 -->
	<div class="Gi-Guide">
		<ul class="Guide">
			<li class="Title"><img src="/images/content/Gg_GuideTitle.gif" alt="안내" /></li>
			<li class="Content">가입하신 이름과 이메일 주소를 기준으로 기존 R&amp;D도우미센터에 문의하신 내용을 확인하는  페이지입니다.<br />
								아래 목록에서 회원님의 문의글이 맞다면 체크박스를 선택하신 후 저장 버튼을 클릭하십시오.<br />
								저장이 완료된 문의글은 '마이페이지' 메뉴에서 확인하실 수 있습니다.</li>
		</ul>
		<ul class="GText-end">
			<li>&nbsp;</li>
		</ul>
	</div>
	<br />
	<!-- start # InforSearch // 멀티라인 -->
	<div class="Basic-List-area">
		<!-- start # 리스트 목록 -->
		<ul class="List">
			<table border="0" cellspacing="0" cellpadding="0" class="Basic-List">
				<thead>
					<tr>
						<th width="40px"><input type="checkbox" name="chkAll" id="chkAll" title="전체선택" onclick="fn_chkAll()"/></th>
						<th width="*">제목</th>
						<th width="200px">E-Mail</th>
						<th width="150px">등록자</th>
						<th width="100px">등록일</th>
					</tr>
				</thead>
				<tbody>	
					<logic:empty name="memberForm" property="voList">
						<tr>
							<td colspan="99">검색된 결과가 없습니다.</td>
						</tr>
					</logic:empty>	
					<logic:notEmpty name="memberForm" property="voList">
						<logic:iterate name="memberForm" property="voList" id="vo" indexId="rowNum">
							<tr>
								<td><input type="checkbox" name="chkDoc" value="<bean:write name='vo' property='seq'/>"/></td>
								<td><a href="javascript:viewDetail('QNA', '<bean:write name="vo" property="seq"/>');"><bean:write name="vo" property="title"/></a></td>
								<td><bean:write name="vo" property="email"/></td>
								<td><bean:write name="vo" property="name"/></td>
								<td><bean:write name="vo" property="reg_dt"/></td>
							</tr>
						</logic:iterate>
					</logic:notEmpty>	
				</tbody>
			</table>
		</ul>
	</div>
	<!-- start # 버튼 -->
	<div class="Basic-Button">
		<ul class="Right">
			<li>
				<logic:notEmpty name="memberForm" property="voList">
				<a href="javascript:updateDocRegister();" class="btn_BSave"><strong>저장</strong></a>
				</logic:notEmpty>
				&nbsp;<a href="javascript:goIndex();" class="btn_Basic"><strong>메인화면</strong></a></li>
		</ul>	
	</div>
</html:form>
<!-- end # Basic-List -->

</div>
</div>
<!-- end # LY-Container -->
<%@ include file="/include/bottom.jsp"%>

<script>	
	if (fm.elements["vo.etc"].value == "complete") {
		document.location.href = "/index.jsp";
	}
	if( "<%=errCd%>" == "true" ) {
		alert("정상적으로 저장되었습니다.");
	}
</script>
