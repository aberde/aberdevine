<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>

<bean:define name="NoticeForm" property="totRowCount" id="totRowCount" type="java.lang.Integer" />
<bean:define name="NoticeForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String" />
<bean:define name="NoticeForm" property="maxPageItems" id="maxPageItems" type="java.lang.String" />
<bean:define name="NoticeForm" property="pagerOffset" id="pagerOffset" type="java.lang.String" />
<bean:define name="NoticeForm" property="searchVO.loginId" id="loginId" type="java.lang.String" />
<bean:define name="NoticeForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String" />
<bean:define id="path" type="java.lang.String" value="/Notice.do" />

<script type="text/javascript">
<!--
activeDebug = true;  
module = '/switch.do?prefix=/notice&method=noticeList&page=';

function noticeDetailView(arg1, arg2){
	fm.elements["searchVO.seq"].value=arg2;	
	fm.elements["searchVO.board_type"].value=arg1;	
	fm.elements["method"].value="noticeDetailView";
	fm.submit();
}
function goSearch(){
	fm.elements["method"].value="noticeList";
	fm.submit();
}
//-->
</script>
</head>
<div class="LY-Container">
	<html:form action="/Notice" method="post" name="fm" type="kr.go.rndcall.mgnt.notice.form.NoticeForm">
	<html:hidden name="NoticeForm" property="method" value="noticeList" />
	<html:hidden name="NoticeForm" property="searchVO.loginId" />
	<html:hidden name="NoticeForm" property="searchVO.name" />
	<html:hidden name="NoticeForm" property="searchVO.board_type" />
	<html:hidden name="NoticeForm" property="searchVO.seq" />
	<html:hidden name="NoticeForm" property="searchVO.type" />
	<html:hidden name="NoticeForm" property="searchVO.menu_sn" />
	<html:hidden name="NoticeForm" property="searchVO.pagerOffset"/>
	<!-- start # InforSearch // 멀티라인 -->
	<div class="InforSearch02" title="검색">
		<ul class="Search">
			<li>
				<table border="0" cellspacing="0" cellpadding="0" class="Search-Pre">
					<tr>
	   					 <td>	
							<html:select name="NoticeForm" property="searchVO.whichSearch" >
								<html:option value="title">제목</html:option>
								<html:option value="contents">내용</html:option>
								<html:option value="all">제목+내용</html:option>
							</html:select>
						 	<html:text name="NoticeForm" property="searchVO.searchTxt" title="제목" styleClass="lineInputKO" size="30" maxlength="35"onchange="trim(this)"  />
						 </td>
	  				     <td class="Btn"><a href="javascript:goSearch()" class="btn_BSearch"><strong>검색</strong></a> </td>
	  				</tr>
				</table>
			</li>
		</ul>
	</div>
	<br />
	<!-- start # InforSearch // 멀티라인 -->
	<div class="Basic-List-area">
		<ul class="List">
		<table border="0" cellspacing="0" cellpadding="0" class="Basic-List">
			<colgroup>
				<col width="60px" />
				<col width="*" />
				<col width="60px" />
				<col width="120px" />
				<col width="60px" />
			</colgroup>
			<thead>
				<tr>
		   			<th>번호</th>
		   			<th>제목</th>
		   			<th>첨부파일</th>
		   			<th>등록일</th>
		   			<th>조회수</th>
		  		</tr>
	  		</thead>
	  		<tbody>
				<logic:empty name="NoticeForm" property="voList">
					<tr>
						<td style="border-right:0px;text-align:center; height=:50px;" colspan="7">등록된 정보가 없습니다.</td>
					</tr>
				</logic:empty>
				<logic:notEmpty name="NoticeForm" property="voList">
					<logic:iterate name="NoticeForm" property="voList" indexId="rowNum" id="vo">
						<tr>
							<td class="center"><%=totRowCount.intValue() - rowNum.intValue() - Util.replaceNull((String) pagerOffset, 0)%></td>
							<td class="Left"><a href="JavaScript:noticeDetailView('NOTICE',<bean:write name="vo" property="seq"/>)"><bean:write name="vo" property="title" /></a></td>
							<td class="center">
							<logic:equal name="vo" property="file_id" value="N">
								<html:img src="/images/icon/disk01.gif"/>
							</logic:equal>
							<logic:notEqual name="vo" property="file_id" value="N">
								
							</logic:notEqual>
							</td>
							<td class="center"><bean:write name="vo" property="reg_dt" />
							</td>
							<td class="center"><bean:write name="vo" property="read_count" /></td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
			</tbody>
		</table>
		<div style="margin:10px 0; display:block">
			<ul class="Page-Num">
				<%@ include file="/include/page.jsp"%>
			</ul>
		</div>
	</ul>
	<%
	if (roleCd.equals("0000Z") || roleCd.equals("0000C")) {
	%>
	<div style="margin:10px 0; display:block">
		<ul class="Center">
			<div class="Basic-Button">
			<ul class="Right">
				<li><a href="/switch.do?prefix=/notice&page=/Notice.do?method=noticeInsertForm&searchVO.menu_sn=04"  class="btn_Basic"><strong>공지사항등록</strong></a></li>
			</ul>
		</ul>
	</div>
	<%
	}
	%>
</div>
</html:form>
</div>
<%@  include file="/include/bottom.jsp"%>
