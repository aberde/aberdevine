<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>

<bean:define name="MypageForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
<bean:define name="MypageForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
<bean:define name="MypageForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
<bean:define name="MypageForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define id="path" type="java.lang.String" value="/Mypage.do"/>
<%
	String board_nm = "";
%>
<script type="text/javascript">
<!--
activeDebug = true;  
module = '/switch.do?prefix=/mypage&method=getMypageList&page=';

function goMypageView(arg1, arg2){
	fm.elements["searchVO.seq"].value=arg2;	
	fm.elements["searchVO.board_type"].value=arg1;
	fm.elements["searchVO.menu_sn"].value = "03";	
	fm.elements["method"].value="getMypageView";
	fm.submit();
}

function goStatList(arg1, arg2){
	fm.elements["searchVO.type"].value=arg2;	
	fm.elements["searchVO.board_type"].value=arg1;	
	fm.elements["searchVO.menu_sn"].value = "03";
	fm.elements["method"].value="getMypageList";
	fm.submit();
}

//-->
</script>
	<!-- start # LY-Container -->
	<div class="LY-Container">
		<!-- start # 마이페이지 상단 기본정보 영역  -->
		<table border="0" cellspacing="0" cellpadding="0" class="MyPage-Header">
		<tr><td>
			<!-- start # 마이페이기 기본정보 -->
			<div class="MyPage-Infor">
				<ul class="Title">
					<li><img src="/images/content/MyPage_Title01.gif" alt="문의하기" /></li>
				</ul>
				<ul class="Infor">	
					<li class="start"><img src="../images/icon/icon_MyPage01.gif" alt="" /> 	<p>전체 : <strong><a href="JavaScript:goStatList('QNA','1')"><bean:write name="MypageForm" property="vo.statCnt1"/>건</a></strong></p></li>
					<li><img src="../images/icon/icon_MyPage02.gif" />			<p>처리중 : <strong><a href="JavaScript:goStatList('QNA','2')"><bean:write name="MypageForm" property="vo.statCnt2"/>건</a></strong></p></li>
					<li class="end"><img src="../images/icon/icon_MyPage03.gif" alt="" />	<p>완료 : <strong><a href="JavaScript:goStatList('QNA','3')"><bean:write name="MypageForm" property="vo.statCnt3"/>건</a></strong></p></li>
				</ul>
			</div>
			<!-- end # 마이페이기 기본정보 -->
			</td>
			<td>
			<!-- start # 마이페이기 기본정보 -->
			<div class="MyPage-Infor">
				<ul class="Title">
					<li><img src="../images/content/MyPage_Title03.gif" alt="신문고" /></li>
				</ul>
				<ul class="Infor">	
					<li class="start"><img src="/images/icon/icon_MyPage01.gif" alt="" /> <p>전체 :<strong> <a href="JavaScript:goStatList('OFFER','1')"><bean:write name="MypageForm" property="vo.statCnt4"/>건</a></strong></li>
					<li> <img src="/images/icon/icon_MyPage04.gif" /><p>처리중 : <strong><a href="JavaScript:goStatList('OFFER','2')"><bean:write name="MypageForm" property="vo.statCnt5"/>건</a></strong></li>
					<li class="end"> <img src="/images/icon/icon_MyPage03.gif" alt="" /><p>완료 : <strong><a href="JavaScript:goStatList('OFFER','3')"><bean:write name="MypageForm" property="vo.statCnt6"/>건</a></strong></li>
				</ul>
			</div>
			<!-- end # 마이페이기 기본정보 -->
			</td>
			<td class="scrap">
				<div class="MyScrap-List ">
						<ul class="Title">
								<li><img src="../images/content/MyPage_scrap_Title.gif" alt="스크랩현황" /></li>
						</ul>
						<ul class="Infor">	
								<li class="end"> <img src="../images/icon/icon_MyPage05.gif" alt="" /><p>건수 : <strong><a href="JavaScript:goStatList('QNA','4')"><bean:write name="MypageForm" property="vo.statCnt7"/>건</a></strong></li>
						</ul>
				</div>
			</td>
		</tr>
		</table>
		<!-- end # 마이페이지 상단 기본정보 영역  -->
		<br />
		<!-- srart 검색 테이블 감싸기 -->
		<html:form action="/Mypage" method="post" name="fm" type="kr.go.rndcall.mgnt.mypage.form.MypageForm">
		<html:hidden name="MypageForm" property="method" value="getMypageList"/>
		<html:hidden name="MypageForm" property="vo.cell_number"/>
		<html:hidden name="MypageForm" property="vo.email"/>
		<html:hidden name="MypageForm" property="searchVO.loginId"/>
		<html:hidden name="MypageForm" property="searchVO.name"/>
		<html:hidden name="MypageForm" property="searchVO.board_type"/>
		<html:hidden name="MypageForm" property="searchVO.searchCategory"/>
		<html:hidden name="MypageForm" property="searchVO.seq"/>
		<html:hidden name="MypageForm" property="searchVO.type"/>	
		<html:hidden name="MypageForm" property="searchVO.menu_sn"/>
		<!-- start # Basic-List -->
		<div class="Basic-List-area">
			<ul class="List">
			<table border="0" cellspacing="0" cellpadding="0" class="Basic-List">
			<thead>
	  		<tr><th>번호</th>
		  		<th>구분</th>
	   			<th>분류</th>
	   			<th>제목</th>
	   			<th>등록일</th>
	   			<th>처리일</th>
				<th>진행상태</th>
			</tr>
			</thead>
			<tbody>
			<logic:empty name="MypageForm" property="voList">
				<tr><td colspan="99">등록된 정보가 없습니다.</td></tr>
			</logic:empty>
			<logic:notEmpty name="MypageForm" property="voList">
				<logic:iterate name="MypageForm" property="voList" indexId="rowNum" id="vo">
			  		<tr><td><%= rowNum.intValue() + Util.replaceNull((String)pagerOffset, 0) + 1 %></td>
			   			<bean:define name="vo" property="board_type" id="board_type" type="java.lang.String"/>
<%
						if(board_type.equals("QNA")){
%>							
							<td><img src="/images/icon/icon_myinquiry.gif"></td>
<%
						}else if(board_type.equals("OFFER")){
%>
							<td><img src="/images/icon/icon_myoffer.gif"></td>
<%
						}
%>
						<bean:define name="vo" property="category1" id="category1" type="java.lang.String"/>
<%
						if(!category1.equals("-")){
%>							
							<td class='com1'><bean:write name='vo' property='category1'/></td>
<%
						}else{
%>							
							<td></td>
<%
						}
%>						
						
						
			   			<td class="Left">
							<bean:define name="vo" property="title" id="title" type="java.lang.String"/>
<%
							String title_n= "";
							if(title.length() > 26){
								title_n = title.substring(0,26)+"...";
							}else{
								title_n = title;
							}
%>			   			
			   				<a href="JavaScript:goMypageView('<bean:write name="vo" property="board_type"/>',<bean:write name="vo" property="seq"/>)"><%=title_n %></a>
			   			</td>
			   			<td><bean:write name="vo" property="reg_dt"/></td>
			   			<td><bean:write name="vo" property="answer_reg_dt"/></td>
			  			<td>
							<bean:define name="vo" property="stat" id="stat" type="java.lang.String"/>
<%
							if(stat.equals("Y")){
%>
								<a href="#" class="btn_Orange"><font color="#ffffff"><strong>&nbsp;완&nbsp;료&nbsp;</strong></font></a>
<%
							}else{
%>
								<a href="#" class="btn_Black"><font color="#ffffff"><strong>처리중</strong></font></a>
<%
							}
%>			  			
			  			</td>
			  		</tr>
		  		</logic:iterate>
	  		</logic:notEmpty>
	  		</tbody>
			</table>
		</ul>
		<ul class="Page-Num">
				<%@ include file="/include/page.jsp"%>
			</ul>
			</html:form>
		</div>	
		<!-- end # Basic-List -->
	</div>
	<!-- end # LY-Container -->
</div>
<!-- end #LY-Wrapper -->
<%@  include file="/include/bottom.jsp"%>