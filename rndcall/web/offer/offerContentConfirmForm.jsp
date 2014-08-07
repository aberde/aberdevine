<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<bean:define id="path" type="java.lang.String" value="/Offer.do"/>
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
<script language="JavaScript">
	function Save(){
		fm.elements["method"].value="offerUpdate";
		fm.submit();
	}
	
	function goCancel(){
		document.location.href="/index.jsp";
	}
	
</script>
<body onload="init_data()">
<div class="location">
	<a href="#" target="_self" >NTIS 홈</a> &gt; <a href="http://rndgate.ntis.go.kr:9998/indexfaq.jsp" target="_self">R&amp;D 도우미센터</a> &gt; <a href="#" target="_self">온라인 상담</a> &gt;제안하기
</div>
<div class="ntiscontent1000">
	<h1>제안하기</h1>
	<br />
	<!--  start 도움말 외각 라인 -->
	<table border="0" cellspacing="0" cellpadding="0" id="HelpTable">
	<tr><td class="TL"></td>
		<td class="TM"><img src="/images/box/HelpTitle.gif" alt="도움말"  /></td>
		<td class="TR"></td>
	</tr>
	<tr><td class="ML"></td>
		<td class="MM">
			<!-- start 도움말 -->
			<div class="HelpText">
				<ul>
					<li>이용시 불편한 점이나 개선사항이 있거나, R&amp;D도우미센터에 제안할 내용이 있으실 경우</li>
					<li>제안하기에 등록하여 담당자에게 알려줄수 있다.</li>
				</ul>
			</div>
			<!-- end 도움말 -->
		</td>
		<td class="MR"></td>
	</tr>
	<tr><td class="DL"></td>
		<td class="DM"></td>
		<td class="DR"></td>
	</tr>
	</table>
	<!-- end 도움말 외각 라인 -->
	<!-- srart 검색 테이블 감싸기 -->
	<html:form action="/Offer" method="post" name="fm" type="kr.go.rndcall.mgnt.offer.form.OfferForm" scope="request">
    <html:hidden name="OfferForm" property="method" value="offerInsert"/>
	<html:hidden name="OfferForm" property="vo.cell_number"/>
	<html:hidden name="OfferForm" property="vo.email"/>
	<html:hidden name="OfferForm" property="searchVO.loginId"/>
	<html:hidden name="OfferForm" property="searchVO.name"/>
	<html:hidden name="OfferForm" property="searchVO.seq"/>
	<html:hidden name="OfferForm" property="searchVO.board_type"/>
	<html:hidden name="OfferForm" property="searchVO.searchCategory"/>
	<table border="0" cellspacing="0" cellpadding="0" class="SearchTable">
	<tr><td class="TL"></td>
		<td class="TM"></td>
		<td class="TR"></td>
	</tr>
	<tr><td class="ML"></td>
		<td class="MM">
		<!-- start 소제목 구분 -->
		<!-- end 소제목 구분 -->
		<!--  start 검색내부 박스 -->		
		<table border="0" cellspacing="0" cellpadding="0" id="SearchInnerBox">
		<tr><td class="TL"></td>
			<td class="TM"></td>
			<td class="TR"></td>
		</tr>
		<tr><td class="ML"></td>
			<td class="MM">
			<!-- start .table-view -->
			<table border="0" cellspacing="0" cellpadding="0" class="SearchTable-view">
			<colgroup>
				<col width="100px" />
				<col width="*" />
			</colgroup>

			<tr>
				<th class="right">제목</th>
				<td>
					<html:text name="OfferForm" property="vo.title" size="90" alt="제목" title="제목"/>
				</td>
			</tr>
			<tr>
				<th class="right">질문내용</th>
				<td><html:textarea name="OfferForm" property="vo.contents" rows="20" cols="90"  alt="질의 내용" title="질의 내용"/></td>
			</tr>
			<tr>
				<th class="right">답변내용</th>
				<td><html:textarea name="OfferForm" property="vo.answer_cont" rows="20" cols="90"  alt="질의 내용" title="질의 내용"/></td>
			</tr>
			</table>
			<!-- end .table-view -->
			</td>
			<td class="MR"></td>
		</tr>
		<tr><td class="DL"></td>
			<td class="DM"></td>
			<td class="DR"></td>
		</tr>
		</table>
		<!-- end 검색내부 박스 -->
		<!-- start 하단버튼 -->
		<p class="btnboxright" style="margin-bottom:0px; text-align:center; border-top:0px solid #cecece; padding-top:0px;">
			<a href="javascript:Save();" class="btn2">저장</a>
			<a href="" class="btn2">취소</a>
		</p>
		<!-- end 하단버튼 -->
		<br />
		</td>
		<td class="MR"></td>
	</tr>
	<tr><td class="DL"></td>
		<td class="DM"></td>
		<td class="DR"></td>
	</tr>
	</table>
	</html:form>
	<!-- end 검색 테이블 감싸기 -->
	<br />
	<br />
	<!-- end .ntiscontents -->
	</div>
<!-- end .container -->
</div>
<%@  include file="/include/bottom.jsp"%>