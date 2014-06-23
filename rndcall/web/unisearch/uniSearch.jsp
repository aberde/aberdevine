<%@ page contentType="text/html; charset=utf-8"%>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util"%>

<bean:define name="UniSearchForm" property="totRowCount" id="totRowCount" type="java.lang.Integer" />
<bean:define name="UniSearchForm" property="totRowCount1" id="totRowCount1" type="java.lang.Integer" />
<bean:define name="UniSearchForm" property="totRowCount2" id="totRowCount2" type="java.lang.Integer" />
<bean:define name="UniSearchForm" property="totRowCount3" id="totRowCount3" type="java.lang.Integer" />
<bean:define name="UniSearchForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String" />
<bean:define name="UniSearchForm" property="maxPageItems" id="maxPageItems" type="java.lang.String" />
<bean:define name="UniSearchForm" property="pagerOffset" id="pagerOffset" type="java.lang.String" />
<bean:define name="UniSearchForm" property="searchVO.loginId" id="loginId" type="java.lang.String" />
<bean:define name="UniSearchForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String" />
<bean:define name="UniSearchForm" property="searchVO.board_type" id="board_type" type="java.lang.String" />

<bean:define id="path" type="java.lang.String" value="/UniSearch.do" />
<script type="text/javascript">
activeDebug = true;  
module = '/switch.do?prefix=/unisearch&method=uniSearch&page=';

function detailUniSearch(uni, board_type, seq){
	if(board_type== "QNA"){
		document.location.href = "/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireView&searchVO.board_type=" + board_type + "&searchVO.seq=" + seq + "&searchVO.uni=" + uni + "&searchCategory=&searchVO.menu_sn=01";
	}else if(board_type == "FAQ"){
		document.location.href = "/switch.do?prefix=/faq&page=/Faq.do?method=faqDetailView&searchVO.board_type=" + board_type + "&searchVO.seq=" + seq + "&searchVO.uni=" + uni + "&searchCategory=&searchVO.menu_sn=01";
	}else if(board_type == "NOTICE"){
		document.location.href = "/switch.do?prefix=/notice&page=/Notice.do?method=noticeDetailView&searchVO.board_type=" + board_type + "&searchVO.seq=" + seq + "&searchVO.uni=" + uni + "&searchCategory=&searchVO.menu_sn=04";
	}else if(board_type == "DATA"){
		document.location.href = "/switch.do?prefix=/data&page=/Data.do?method=dataDetailView&searchVO.board_type=" + board_type + "&searchVO.seq=" + seq + "&searchVO.uni=" + uni + "&searchCategory=&searchVO.menu_sn=02";
	}else if(board_type == "INS"){
		document.location.href = "/switch.do?prefix=/data&page=/Data.do?method=dataDetailView&searchVO.board_type=" + board_type + "&searchVO.seq=" + seq + "&searchVO.uni=" + uni + "&searchCategory=&searchVO.menu_sn=02";
	}
}
</script>
</head>
<div class="LY-Container"><html:form action="/UniSearch" method="post" name="fm" type="kr.go.rndcall.mgnt.unisearch.form.UniSearchForm">
	<html:hidden name="UniSearchForm" property="method" value="uniSearch" />
	<html:hidden name="UniSearchForm" property="searchVO.loginId" />
	<html:hidden name="UniSearchForm" property="searchVO.name" />
	<html:hidden name="UniSearchForm" property="searchVO.board_type" />
	<html:hidden name="UniSearchForm" property="searchVO.seq" />
	<html:hidden name="UniSearchForm" property="searchVO.type" />
	<html:hidden name="UniSearchForm" property="searchVO.word" />
	<html:hidden name="UniSearchForm" property="searchVO.menu_sn" value="08" />
	<!-- start # All Search -->
	<div class="AllSearch-List"><!-- start # All Search -->
	<div class="All-Search"><!-- start # 타이틀 --> 
	<logic:empty name="UniSearchForm" property="voList">
		<div class="All-Search">
		<ul class="Title">
			<li>
			<tr>
				<td style="border-right:0px;text-align:center; height=:50px;">[Q&A]에
				검색된 정보가 없습니다.</td>
			</tr>
			</li>
		</ul>
		<ul class="STitle">
			<li class="Title01">
			<td style="border-right:0px;text-align:center; height=:50px;"></td>
			</li>
			<li class="Day">
			<td style="border-right:0px;text-align:center; height=:50px;">[Q&A]에
			검색된 정보가 없습니다.</td>
			</li>
		</ul>
		<ul class="Search-Infor">
			<li></li>
		</ul>
		<ul class="Search-Infor">
			<li></li>
		</ul>
		<ul class="Search-Infor">
			<li></li>
		</ul>
		</div>
	</logic:empty>
	<logic:notEmpty name="UniSearchForm" property="voList">
	<ul class="Title">
		<li>
		<tr>
			<td class="center">[Q&A]</td>
		</tr>
		</li>
	</ul>
		<logic:iterate name="UniSearchForm" property="voList" indexId="rowNum"
			id="vo">
			<!-- start # 서브 타이틀  -->
			<ul class="STitle">
				<li class="Title01">
				<tr>
					<td class="center"><%=totRowCount.intValue() + rowNum.intValue() + Util.replaceNull((String) pagerOffset, 0)%>.&nbsp;
				</tr>
				<b>[Q&A]</b> <a
					href="javascript:detailUniSearch('uni','<bean:write name="vo" property="board_type"/>','<bean:write name="vo" property="seq"/>')"><bean:write name="vo" property="title" filter="false" /></a></li>
				<li class="Day"><bean:write name="vo" property="reg_dt" /></li>
			</ul>
			<!-- start # 상세정보 -->
			<ul class="Search-Infor">
				<li><a
					href="javascript:detailUniSearch('uni','<bean:write name="vo" property="board_type"/>','<bean:write name="vo" property="seq"/>')"><bean:write name="vo" property="contents" filter="false" /></a></li>
			</ul>
		</logic:iterate>
	</logic:notEmpty></div>
	</div>
	<!-- end # 검색결과 -->
	<div class="AllSearch-List1"><!-- start # All Search -->
	<div class="All-Search"><!-- start # 타이틀 --> 
	<logic:empty name="UniSearchForm" property="voList1">
		<div class="All-Search">
		<ul class="Title">
			<li>
			<tr>
				<td style="border-right:0px;text-align:center; height=:50px;">[자주하는질문]에	검색된 정보가 없습니다.</td>
			</tr>
			</li>
		</ul>
		<ul class="STitle">
			<li class="Title01">
			<td style="border-right:0px;text-align:center; height=:50px;"></td>
			</li>
			<li class="Day">
			<td style="border-right:0px;text-align:center; height=:50px;">[자주하는질문]에	검색된 정보가 없습니다.</td>
			</li>
		</ul>
		<ul class="Search-Infor">
			<li></li>
		</ul>
		<ul class="Search-Infor">
			<li></li>
		</ul>
		<ul class="Search-Infor">
			<li></li>
		</ul>
		</div>
	</logic:empty>
	<logic:notEmpty name="UniSearchForm" property="voList1">
	<ul class="Title">
		<li>
		<tr>
			<td class="center">[자주하는질문]</td>
		</tr>
		</li>
	</ul>
		<logic:iterate name="UniSearchForm" property="voList1" indexId="rowNum"
			id="vo">
			<!-- start # 서브 타이틀  -->
			<ul class="STitle">
				<li class="Title01">
				<tr>
					<td class="center"><%=totRowCount.intValue() + rowNum.intValue() + Util.replaceNull((String) pagerOffset, 0)%>.&nbsp;
					
				</tr>
				<b>[자주하는질문]</b> <a href="javascript:detailUniSearch('uni','<bean:write name="vo" property="board_type"/>','<bean:write name="vo" property="seq"/>')"><bean:write name="vo" property="title" filter="false" /></a></li>
				<li class="Day"><bean:write name="vo" property="reg_dt" /></li>
			</ul>
			<!-- start # 상세정보 -->
			<ul class="Search-Infor">
				<li><a href="javascript:detailUniSearch('uni','<bean:write name="vo" property="board_type"/>','<bean:write name="vo" property="seq"/>')"><bean:write name="vo" property="contents" filter="false" /></a></li>
			</ul>
		</logic:iterate>
	</logic:notEmpty></div>
	</div>
	<!-- end # 검색결과 -->
	<div class="AllSearch-List2"><!-- start # All Search -->
	<div class="All-Search"><!-- start # 타이틀 --> 
	<logic:empty name="UniSearchForm" property="voList2">
		<div class="All-Search">
		<ul class="Title">
			<li>
			<tr>
				<td style="border-right:0px;text-align:center; height=:50px;">[공지사항]에 검색된 정보가 없습니다.</td>
			</tr>
			</li>
		</ul>
		<ul class="STitle">
			<li class="Title01">
			<td style="border-right:0px;text-align:center; height=:50px;"></td>
			</li>
			<li class="Day">
			<td style="border-right:0px;text-align:center; height=:50px;">[공지사항]에 검색된 정보가 없습니다.</td>
			</li>
		</ul>
		<ul class="Search-Infor">
			<li></li>
		</ul>
		<ul class="Search-Infor">
			<li></li>
		</ul>
		<ul class="Search-Infor">
			<li></li>
		</ul>
		</div>
	</logic:empty>
	<logic:notEmpty name="UniSearchForm" property="voList2">
	<ul class="Title">
		<li>
		<tr>
			<td class="center">[공지사항]</td>
		</tr>
		</li>
	</ul>
		<logic:iterate name="UniSearchForm" property="voList2" indexId="rowNum"	id="vo">
			<!-- start # 서브 타이틀  -->
			<ul class="STitle">
				<li class="Title01">
				<tr>
					<td class="center"><%=totRowCount.intValue() + rowNum.intValue() + Util.replaceNull((String) pagerOffset, 0)%>.&nbsp;
				</tr>
				<b>[공지사항]</b> <a href="javascript:detailUniSearch('uni','<bean:write name="vo" property="board_type"/>','<bean:write name="vo" property="seq"/>')"><bean:write name="vo" property="title" filter="false" /></a></li>
				<li class="Day"><bean:write name="vo" property="reg_dt" /></li>
			</ul>
			<!-- start # 상세정보 -->
			<ul class="Search-Infor">
				<li><a href="javascript:detailUniSearch('uni','<bean:write name="vo" property="board_type"/>','<bean:write name="vo" property="seq"/>')"><bean:write name="vo" property="contents" filter="false" /></a></li>
			</ul>
		</logic:iterate>
	</logic:notEmpty></div>
	</div>
	<!-- end # 검색결과 -->
	<div class="AllSearch-List3"><!-- start # All Search -->
	<div class="All-Search"><!-- start # 타이틀 --> 
	<logic:empty name="UniSearchForm" property="voList3">
		<div class="All-Search">
		<ul class="Title">
			<li>
			<tr>
				<td style="border-right:0px;text-align:center; height=:50px;">[자료실]에 검색된 정보가 없습니다.</td>
			</tr>
			</li>
		</ul>
		<ul class="STitle">
			<li class="Title01">
			<td style="border-right:0px;text-align:center; height=:50px;"></td>
			</li>
			<li class="Day">
			<td style="border-right:0px;text-align:center; height=:50px;">[자료실]에 검색된 정보가 없습니다.</td>
			</li>
		</ul>
		<ul class="Search-Infor">
			<li></li>
		</ul>
		<ul class="Search-Infor">
			<li></li>
		</ul>
		<ul class="Search-Infor">
			<li></li>
		</ul>
		</div>
	</logic:empty>
	<logic:notEmpty name="UniSearchForm" property="voList3">
	<ul class="Title">
		<li>
		<tr>
			<td class="center">[자료실]</td>
		</tr>
		</li>
	</ul>
		<logic:iterate name="UniSearchForm" property="voList3" indexId="rowNum" id="vo">
			<!-- start # 서브 타이틀  -->
			<ul class="STitle">
				<li class="Title01">
				<tr>
					<td class="center"><%=totRowCount.intValue() + rowNum.intValue() + Util.replaceNull((String) pagerOffset, 0)%>.&nbsp;
				</tr>
				<b>[자료실]</b> <a href="javascript:detailUniSearch('uni','<bean:write name="vo" property="board_type"/>','<bean:write name="vo" property="seq"/>')"><bean:write name="vo" property="title" filter="false" /></a></li>
				<li class="Day"><bean:write name="vo" property="reg_dt" /></li>
			</ul>
			<!-- start # 상세정보 -->
			<ul class="Search-Infor">
				<li><a href="javascript:detailUniSearch('uni','<bean:write name="vo" property="board_type"/>','<bean:write name="vo" property="seq"/>')"><bean:write name="vo" property="contents" filter="false" /></a></li>
			</ul>
		</logic:iterate>
	</logic:notEmpty></div>
	</div>
	<!-- end # 검색결과 -->	
</html:form>
<!-- end 검색 테이블 감싸기 -->
<br />
</div>
<%@  include file="/include/bottom.jsp"%>
