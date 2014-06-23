<%@ page contentType="application/vnd.ms-excel;charset=utf-8" %>

<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@ taglib uri="/tags/taglibs-log" prefix="log"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
<% 
response.setHeader("Content-Disposition", "attachment;filename=%EC%98%A8%EB%9D%BC%EC%9D%B8%EC%83%81%EB%8B%B4.XLS"); 
response.setHeader("Content-Description", "JSP Generated Data"); 
%>


<bean:define name="InquireForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
<bean:define name="InquireForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
<bean:define name="InquireForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
<bean:define name="InquireForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="InquireForm" property="searchVO.loginId" id="loginId" type="java.lang.String"/>
<bean:define name="InquireForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String"/>
<bean:define name="InquireForm" property="searchVO.menu_sn" id="menu_sn" type="java.lang.String"/>
<bean:define id="path" type="java.lang.String" value="/Inquire.do"/>
<body>
<!-- srart 검색 테이블 감싸기 -->
<html:form action="/Inquire" method="post" name="fm" type="kr.go.rndcall.mgnt.inquire.form.InquireForm">
<html:hidden name="InquireForm" property="method" value="getInquireList"/>
<html:hidden name="InquireForm" property="vo.cell_number"/>
<html:hidden name="InquireForm" property="vo.email"/>
<html:hidden name="InquireForm" property="searchVO.loginId"/>
<html:hidden name="InquireForm" property="searchVO.name"/>
<html:hidden name="InquireForm" property="searchVO.board_type"/>
<html:hidden name="InquireForm" property="searchVO.searchCategory"/>
<html:hidden name="InquireForm" property="searchVO.seq"/>
<html:hidden name="InquireForm" property="searchVO.type"/>
<table border="1" cellspacing="0" cellpadding="0">
<colgroup>
	<col width="80px" />
	<col width="150px" />
	<col width="150px" />
	<col width="200px" />
	<col width="80px" />
	<col width="80px" />
	<col width="120px" />
	<col width="100px" />
	<col width="100px" />
	<col width="100px" />
	<col width="80px" />
	<col width="300px" />
	<col width="300px" />
	<col width="100px" />
	<col width="150px" />
	<col width="120px" />
</colgroup>
<tr>
	<th class="center">번호</th>
	<th class="center">분류(대)</th>
	<th class="center">분류(중)</th>
	<th class="center">제목</th>
	<th class="center">상태</th>
	<th class="center">자체/타기관여부</th>
	<th class="center">담당자부처</th>
	<th class="center">담당자이름</th>
	<th class="center">등록방법</th>
	<th class="center">등록일</th>
	<th class="center">공개여부</th>
	<th class="center">질의내용</th>
	<th class="center">답변내용</th>
	<th class="center">질문자이름</th>
	<th class="center">질문자이메일</th>
	<th class="center">질문자전화번호</th>
</tr>
<logic:empty name="InquireForm" property="voList">
	<tr><td style="border-right:0px;text-align:center; height=:50px;" colspan="16">등록된 정보가 없습니다.</td></tr>
</logic:empty>
<logic:notEmpty name="InquireForm" property="voList">
	<logic:iterate name="InquireForm" property="voList" indexId="rowNum" id="vo">
		<tr><td style="text-align:center;"><%= rowNum.intValue() + Util.replaceNull((String)pagerOffset, 0) + 1 %></td>
			<td style="text-align:center;"><bean:write name="vo" property="category1"/></td>
			<td style="text-align:center;"><bean:write name="vo" property="category2"/></td>
			<td><bean:write name="vo" property="title"/></td>		
			<td style="text-align:center;"><bean:write name="vo" property="stat"/></td>
			<td style="text-align:center;"><bean:write name="vo" property="public_trans_yn"/></td>
			<td style="text-align:center;"><bean:write name="vo" property="public_trans_org_nm"/></td>
			<td style="text-align:center;"><bean:write name="vo" property="public_trans_id"/></td>
			<td style="text-align:center;"><bean:write name="vo" property="insert_type"/></td>
			<td style="text-align:center;"><bean:write name="vo" property="reg_dt"/></td>
			<td style="text-align:center;"><bean:write name="vo" property="open_yn"/></td>
			<td><bean:write name="vo" property="contents"/></td>		
			<td><bean:write name="vo" property="answerContents"/></td>		
			<td style="text-align:center;"><bean:write name="vo" property="reg_nm"/></td>
			<td style="text-align:center;"><bean:write name="vo" property="email"/></td>
			<td style="text-align:center;"><bean:write name="vo" property="cell_number"/></td>
		</tr>
	</logic:iterate>
</logic:notEmpty>			
</table>
</html:form>
<!-- end 검색 테이블 감싸기 -->
</body>
</html>