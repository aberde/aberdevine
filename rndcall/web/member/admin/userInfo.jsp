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

<bean:define name="memberAdminForm" property="errCd" id="errCd" type="java.lang.String"/>
<bean:define name="memberAdminForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
<bean:define name="memberAdminForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
<bean:define name="memberAdminForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>

<bean:define id="path" type="java.lang.String" value="/memberAdmin.do"/>

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

<title>R&amp;D도우미센터</title>
<script language="JavaScript" src="/js/validate.js"></script>
<script>
<!--

window.onload = function() {
	if ('<bean:write name="memberAdminForm" property="searchVO.crud"/>' == 'UDC' && 'Y' == '<%= errCd %>' ) {
		alert('권한이 정상적으로 수정되었습니다.');
	}
}


function save(){
	fm.method.value = "getUserInfo";
	fm.elements["searchVO.crud"].value = "UPDT";
	fm.submit();
}

function gotoList() {
	fm.method.value = "getUserList";
	fm.submit();
}
//-->
</script>
<!-- start # LY-Container -->
<div class="LY-Container">
<!-- start # 레프트 메뉴 -->
		<div id="LY-Left">
				<!-- start # left menu  -->
				<ul id="Left-Menu">
						<li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=05"><img src="../images/Menu/left/lm03_01_off.gif" alt="권한관리" /></a></li>
						<li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=05"><img src="../images/Menu/left/lm03_02_off.gif" alt="분야관리" /></a></li>
						<li><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm&searchVO.menu_sn=05"><img src="../images/Menu/left/lm03_03_off.gif" alt="오프라인자료등록" /></a></li>
						<li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatCategory&searchVO.menu_sn=05"><img src="../images/Menu/left/lm03_04_off.gif" alt="통계관리" /></a></li>
				</ul>
				<SCRIPT type=text/javascript>
				<!--
				var ObjEventLeftMenu = new EventLeftMenu(3, 1, 0);
				//-->
				</SCRIPT>
		</div>
		<!-- end # 레프트 메뉴 -->
	<div class="LY-Content">

<html:form action="/memberAdmin" method="post" name="fm" type="kr.go.rndcall.mgnt.member.admin.MemberAdminForm">
	<html:hidden name="memberAdminForm" property="method" value="getUserInfo"/>
	<html:hidden name="memberAdminForm" property="orderByTarget"/>
	<html:hidden name="memberAdminForm" property="orderByMethod"/>
	<html:hidden name="memberAdminForm" property="maxIndexPages"/>
	<html:hidden name="memberAdminForm" property="maxPageItems"/>
	<html:hidden name="memberAdminForm" property="pagerOffset"/>
	<html:hidden name="memberAdminForm" property="vo.auth_id"/>
	<html:hidden name="memberAdminForm" property="searchVO.name"/>
	<html:hidden name="memberAdminForm" property="searchVO.login_id"/>
	<html:hidden name="memberAdminForm" property="searchVO.org_cd"/>
	<html:hidden name="memberAdminForm" property="searchVO.roleCD"/>
	<html:hidden name="memberAdminForm" property="searchVO.crud"/>
	<html:hidden name="memberAdminForm" property="searchVO.menu_sn" value="<%= menu_sn %>"/>
<!-- start # 회원가입 -->

	<!-- start # 상세보기 -->
	<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
		<colgroup>
			<col width="130px" />
			<col width="*" />
		</colgroup>
		<tr>
			<th class="Btmline Left">&nbsp; 사용자 아이디</th>
			<td><strong><bean:write name="memberAdminForm" property="vo.login_id" /></strong></td>
		</tr>
		<tr>
			<th class="Btmline Left">&nbsp; 이름</th>
			<td><strong><bean:write name="memberAdminForm" property="vo.name" /></strong></td>
		</tr>
		<tr>
			<th class="Btmline Left">&nbsp; 휴대폰</th>
			<td><strong><bean:write name="memberAdminForm" property="vo.mobile" /></strong></td>
		</tr>
		<tr>
			<th class="Btmline Left">&nbsp; 이메일</th>
			<td><strong><bean:write name="memberAdminForm" property="vo.email" /></strong></td>
		</tr>
		<tr>
			<th class="Btmline Left">&nbsp; 권한</th>
			<td>
				<html:select name="memberAdminForm" property="vo.roleCD">
					<html:option value="0000A">일반사용자</html:option>
					<html:option value="0000B">부처담당자</html:option>
					<html:option value="0000C">운영자</html:option>
					<html:option value="0000Z">시스템관리자</html:option>
				</html:select>
			</td>
		</tr>
		<tr>
			<th class="Btmline Left">&nbsp; 소속기관</th>
			<td>
				<html:select name="memberAdminForm" property="vo.org_cd" styleClass="Out_lineY" >
					<html:option value="">없음</html:option>
					<html:optionsCollection name="memberAdminForm" property="orgCdList" label="org_nm" value="org_cd"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<th class="Btmline Left">&nbsp; 부서</th>
			<td><html:text name="memberAdminForm" property="vo.attached_nm" size="30" title="소속부서명"/></td>
		</tr>
		<tr>
			<th class="Btmline Left">&nbsp; 사무실연락처</th>
			<td><html:text name="memberAdminForm" property="vo.tel" size="30" title="사무실연락처"/></td>
		</tr>
	</table>
	<br />
	<!-- start # 버튼 -->
	<div class="Basic-Button">
		<ul class="Right">
			<li><a href="javascript:save();" class="btn_BSave"><strong>저장</strong></a> <a href="javascript:fm.reset();" class="btn_Basic"><strong>취소</strong></a> <a href="javascript:gotoList();" class="btn_Basic"><strong>목록</strong></a></li>
		</ul>	
	</div>
</div>
</html:form>
<!-- end # Basic-List -->
</div>
</div>
<!-- end # LY-Container -->
<%@ include file="/include/bottom.jsp"%>
