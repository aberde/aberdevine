<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/include/top.jsp"%>

<bean:define name="memberAdminForm" property="errCd" id="errCd" type="java.lang.String"/>
<bean:define name="memberAdminForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
<bean:define name="memberAdminForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
<bean:define name="memberAdminForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
<bean:define name="memberAdminForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>

<bean:define id="path" type="java.lang.String" value="/memberAdmin.do"/>

<%
	if (mainLoginVO == null || !mainIsLogin) {	
%>
	<script type="text/javascript">
		alert('로그인이 필요한 메뉴입니다.');
		document.location.href = "/index.jsp";
	</script>
<%
	} else if (!mainRoleCD.equals("0000C") && !mainRoleCD.equals("0000Z")) {
%>
	<script type="text/javascript">
		alert('권한이 없습니다.');
		document.location.href = "/index.jsp";
	</script>
<%		
	}
%>

<title>R&amp;D도우미센터</title>
<script language="JavaScript" src="/js/validate.js"></script>
<script>
<!--

module = '/switch.do?prefix=&page=';

function search() {
	fm.method.value = "getUserList";
	fm.submit();
}

function getUserInfo(id) {
	fm.elements["searchVO.auth_id"].value = id;
	fm.method.value = "getUserInfo";
	fm.submit();
}

function pageSubmit(fmObj, module, pageUrl) {
    fmObj.action = module + pageUrl;
    fmObj.method.value = "getUserList";
    fmObj.submit();
}


//-->
</script>

<!-- start # LY-Container -->
<div class="LY-Container">
		<!-- start # 레프트 메뉴 -->
		<div id="LY-Left">
				<!-- start # left menu  -->
				<ul id="Left-Menu">
						<li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.roleCD=&searchVO.search_sel=&searchVO.search_word=&searchVO.menu_sn=09"><img src="../images/Menu/left/lm03_01_off.gif" alt="권한관리" /></a></li>
						<li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=09"><img src="../images/Menu/left/lm03_02_off.gif" alt="분야관리" /></a></li>
						<li><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm&searchVO.menu_sn=09"><img src="../images/Menu/left/lm03_03_off.gif" alt="오프라인자료등록" /></a></li>
						<li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatCategory&searchVO.menu_sn=09"><img src="../images/Menu/left/lm03_04_off.gif" alt="통계관리" /></a></li>
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
	<html:hidden name="memberAdminForm" property="method" value="getUserList"/>
	<html:hidden name="memberAdminForm" property="orderByTarget"/>
	<html:hidden name="memberAdminForm" property="orderByMethod"/>
	<html:hidden name="memberAdminForm" property="maxIndexPages"/>
	<html:hidden name="memberAdminForm" property="maxPageItems"/>
	<html:hidden name="memberAdminForm" property="pagerOffset"/>
	<html:hidden name="memberAdminForm" property="searchVO.auth_id"/>
	<html:hidden name="memberAdminForm" property="searchVO.menu_sn" value="<%= menu_sn %>"/>
	<!-- start # InforSearch // 멀티라인 -->
	<div class="InforSearch" title="검색">
			<ul class="Search">
					<li>
					<table border="0" cellspacing="0" cellpadding="0" class="Search-Pre">
	  					<tr>
	   						 <td>
				<html:select name="memberAdminForm" property="searchVO.roleCD" onchange="search();">
					<html:option value="">권한전체</html:option>
					<html:option value="0000A">일반사용자</html:option>
					<html:option value="0000B">부처담당자</html:option>
					<html:option value="0000C">운영자</html:option>
					<html:option value="0000Z">시스템관리자</html:option>
				</html:select>
							 &nbsp;
				<html:select name="memberAdminForm" property="searchVO.search_sel">
					<html:option value="">전체</html:option>
					<html:option value="nm">이름</html:option>
					<html:option value="id">아이디</html:option>
				</html:select>
						  <html:text name="memberAdminForm" property="searchVO.search_word" maxlength="30" title="검색어"/> </td>
	  					     <td class="Btn"><a href="javascript:search();" class="btn_BSearch"><strong>검색</strong></a> </td>
	  					</tr>
					</table>
					</li>
			</ul>
	</div>
	<br/>
	<!-- start # InforSearch // 멀티라인 -->
	<div class="Basic-List-area">
		<!-- start # 리스트 목록 -->
		<ul class="List">
			<table border="0" cellspacing="0" cellpadding="0" class="Basic-List">
				<thead>
					<tr>
						<th width="30px">번호</th>
						<th width="80px">ID</th>
						<th width="*">이름</th>
						<th width="150px">소속기관</th>
						<th width="100px">부서</th>
						<th width="100px">권한</th>
						<th width="70px">수정</th>
					</tr>
				</thead>
				<tbody>	
					<logic:empty name="memberAdminForm" property="voList">
						<tr>
							<td colspan="99">조건에 해당하는 회원이 없습니다.</td>
						</tr>
					</logic:empty>	
					<logic:notEmpty name="memberAdminForm" property="voList">
						<logic:iterate name="memberAdminForm" property="voList" id="vo" indexId="rowNum">
							<tr>
								<td><%= Integer.valueOf(pagerOffset).intValue() + rowNum.intValue() + 1 %></td>
								<td><bean:write name="vo" property="login_id"/></td>
								<td><bean:write name="vo" property="name"/></td>
								<td><bean:write name="vo" property="org_nm"/></td>
								<td><bean:write name="vo" property="attached_nm"/></td>
								<td><bean:write name="vo" property="roleCD"/></td>
								<td><a href="javascript:getUserInfo('<bean:write name="vo" property="auth_id"/>');" class="btn_TWrite"><strong>수정</strong></a></td>
							</tr>
						</logic:iterate>
					</logic:notEmpty>	
				</tbody>
			</table>
		</ul>
		<ul class="Page-Num">
			<%@ include file="/include/page.jsp" %>
		</ul>
	</div>
</html:form>

<!-- end # Basic-List -->
</div>
</div>
<!-- end # LY-Container -->
<%@ include file="/include/bottom.jsp"%>
