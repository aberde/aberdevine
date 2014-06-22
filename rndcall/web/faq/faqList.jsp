<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
### 경로   : <%= request.getRequestURI() %>
### 사업명 : 11년도 개방형 연구성과분석지원서비스
### 사업일 : 2011-07-10 ~ 2011-08-31
### 개발일 : 2011-07-14
### 개발자 : 박인선
-->
<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.faq.vo.*" %>

<bean:define name="FaqForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String"/>
<bean:define name="FaqForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
<bean:define name="FaqForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
<bean:define name="FaqForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
<bean:define name="FaqForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="FaqForm" property="searchVO.loginId" id="loginId" type="java.lang.String"/>
<bean:define name="FaqForm" property="voList2" id="cateL" type="java.util.ArrayList"/>
<bean:define name="FaqForm" property="searchVO.searchCategory" id="searchCategory" type="java.lang.String"/>

<bean:define id="path" type="java.lang.String" value="/Faq.do"/>
<script language="JavaScript">
activeDebug = true;  
module = '/switch.do?prefix=/faq&method=faqList&page=';
function Save(){
	if(validate()){
		fm.elements["method"].value="faqInsert";
		fm.submit();
	}
}
	
function goCancel(){
	document.location.href="/index.jsp";
}

function putEmailHost(){
	if(fm.elements["putEmail"].value != ""){		
		fm.elements["email1"].focus();
		fm.elements["email2"].readonly=true;
		fm.elements["email2"].value = fm.elements["putEmail"].value;
	}else{
		fm.elements["email1"].focus();
		fm.elements["email2"].value = "";
		fm.elements["email2"].readonly=false;
	}
}
	

function create(){
	fm.elements["method"].value="faqInsertForm";
	fm.submit();
}
function detail(elem, seq) {
	document.fm.elements["method"].value = "faqDetailView";
	document.fm.elements["searchVO.seq"].value = seq;
	alert(seq);
	document.fm.submit();
}
//상세보기
function faqDetailView(arg1, arg2){
	fm.elements["searchVO.seq"].value=arg2;	
	fm.elements["searchVO.board_type"].value=arg1;	
	fm.elements["method"].value="faqDetailView";
	fm.submit();
}
	
function goCate(arg){
	fm.elements["searchVO.whichSearch"].value="";
	fm.elements["searchVO.searchTxt"].value="";
	fm.elements["searchVO.searchCategory"].value=arg;
	fm.elements["searchVO.type"].value="";
	fm.elements["method"].value="faqList";
	fm.submit();
}
function goSearch(){
	fm.elements["searchVO.searchCategory"].value=fm.elements["catr_list"].value;
	fm.elements["method"].value="faqList";
	fm.submit();
}
function showContents(showId) {
    document.getElementById("content"+showId).style.display = "";
    document.getElementById("answer"+showId).style.display = "";
    //document.getElementById("sati"+showId).style.display = "";
    document.getElementById("shortComment"+showId).style.display = "none";
}

function hiddenComment(hiddenId) {
    document.getElementById("content"+hiddenId).style.display = "none";
    document.getElementById("answer"+hiddenId).style.display = "none";
    //document.getElementById("sati"+hiddenId).style.display = "none";
    document.getElementById("shortComment"+hiddenId).style.display = "";
}
function faqContentConfirm(FAQ, seq){
	fm.elements["searchVO.searchCategory"].value="";
	fm.elements["searchVO.board_type"].value =FAQ;
	fm.elements["searchVO.seq"].value = seq;
	fm.elements["vo.title"].value;
	fm.elements["vo.answer_cont"].value;
	fm.elements["vo.contents"].value;
	fm.elements["method"].value="faqContentConfirm";
	fm.submit();
}
function faqContentDelete(seq){
	if (confirm("정말로 삭제 하시겠습니까?")) {
		fm.elements["searchVO.searchCategory"].value="";
		fm.elements["searchVO.seq"].value = seq;
		fm.elements["method"].value="faqDelete";
		fm.submit();
	}
}
function faqCreateForm(){
	if("<%=loginId%>" == ""){
		alert("로그인 후 이용하실 수 있습니다.");
		return;
	}else{
		var url = "/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireForm&searchVO.menu_sn=01";
		document.location.href = url;
	}
}

</script>
</head>
	<!-- start # LY-Container -->
	<div class="LY-Container">
		<!-- start # 레프트 메뉴 -->
		<div id="LY-Left">
				<!-- start # left menu  -->
				<ul id="Left-Menu">
						<li><a href="JavaScript:goInquireForm()"><img src="/images/Menu/left/lm01_01_off.gif" alt="온라인상담" /></a></li>
						<li><a href="JavaScript:goFaq()"><img src="/images/Menu/left/lm01_02_off.gif" alt="자주 묻는 질문" /></a></li>
						<li><a href="JavaScript:goInquireList('QNA')"><img src="/images/Menu/left/lm01_03_off.gif" alt="Q&amp;A" /></a></li>
				</ul>
				<SCRIPT type=text/javascript>
				<!--
				var ObjEventLeftMenu = new EventLeftMenu(0, 2, 0);
				//-->
				</SCRIPT>
		</div>
		<!-- end # 레프트 메뉴 -->
		<!-- start # LY-ContentTitle -->
		<div class="LY-Content">
			<div class="LY-ContentTitle">
				<h1><img src="../images/content/Content_Title01_2.gif" alt="자주 묻는 질문 - 가장 많이 묻는 질의응답을 카테고리별로 보실 수 있습니다." /></h1>
			</div>
			<!-- end # LY-ContentTitle -->
	<!-- end # OnLine-STab -->

	<html:form action="/Faq" method="post" name="fm" type="kr.go.rndcall.mgnt.faq.form.FaqForm" scope="request">
    <html:hidden name="FaqForm" property="method" value="faqList"/>
	<html:hidden name="FaqForm" property="vo.cell_number"/>
	<html:hidden name="FaqForm" property="vo.email"/>
	<html:hidden name="FaqForm" property="searchVO.loginId"/>
	<html:hidden name="FaqForm" property="searchVO.name"/>
	<html:hidden name="FaqForm" property="searchVO.seq"/>
	<html:hidden name="FaqForm" property="vo.title"/>
	<html:hidden name="FaqForm" property="vo.answer_cont"/>
	<html:hidden name="FaqForm" property="vo.contents"/>
	<html:hidden name="FaqForm" property="searchVO.board_type"/>
	<html:hidden name="FaqForm" property="searchVO.searchCategory"/>
	<html:hidden name="FaqForm" property="searchVO.type"/>
	<html:hidden name="FaqForm" property="searchVO.menu_sn"/>
	<html:hidden name="FaqForm" property="searchVO.pagerOffset"/>
	
	<div class="InforSearch" title="검색">
		<ul class="Search">
			<li>
						<table border="0" cellspacing="0" cellpadding="0" class="Search-Pre">
							<tr>
			   					 <td>
			   					 	<bean:define name="FaqForm" property="searchVO.searchCategory" id="category" type="java.lang.String"/>
			   					 	<select name="catr_list" id="catr_list">
									<option value="">====분야선택====</option>
										<logic:notEmpty name="FaqForm" property="voList2">
											<logic:iterate name="FaqForm" property="voList2" indexId="comRowNm" id="mCode">
												<bean:define name="mCode" property="code" id="code" type="java.lang.String"/>
												<option value="<bean:write name="mCode" property="code" />" <% if(category.equals(code)){ %> selected<% } %>><bean:write name="mCode" property="code_nm" /></option>
											</logic:iterate>
										</logic:notEmpty>
									</select>&nbsp;&nbsp;	
									<html:select name="FaqForm" property="searchVO.whichSearch">
									  	<html:option value="title">제목</html:option>
									   	<html:option value="contents">내용</html:option>
										<html:option value="all">제목+내용</html:option>
									</html:select>
								 	<html:text name="FaqForm" property="searchVO.searchTxt" title="제목" styleClass="lineInputKO" size="30" maxlength="35"onchange="trim(this)"  />
								 </td>
			  				     <td class="Btn"><a href="javascript:goSearch()" class="btn_BSearch"><strong>검색</strong></a> </td>
			  				</tr>
						</table>
					</li>
				</ul>
			</div>
			<br/>
			
	<!-- srart 검색 테이블 감싸기 -->
	<div class="Basic-List-area">
	<ul class="List">
	<table border="0" cellspacing="0" cellpadding="0" class="Basic-List">
			<colgroup>
				<col width="50px" />
				<col width="140px" />
				<col width="*" />
				<!--
				<col width="100px" />
				<col width="50px" />
				 -->
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>분류</th>
					<th>제목</th>
					<!-- 
					<th>등록일</th>
					<th>조회수</th>
					 -->
				</tr>
		</thead>
			<logic:empty name="FaqForm" property="voList">
			<tr>
			    <td class="center" colspan="6" width="100%">검색된 결과가 없습니다.</td>
			</tr>
			</logic:empty>
			
		<logic:notEmpty name="FaqForm" property="voList">
			<logic:iterate name="FaqForm" property="voList" indexId="rowNum" id="vo">
			<tr>
				<td><%= totRowCount.intValue() - rowNum.intValue() -  Util.replaceNull((String)pagerOffset, 0) %></td>
				<td class="center"><b>[<bean:write name="vo" property="category1"/>]</b></td>
				<td class="Left">
					<bean:define name="vo" property="title" id="title" type="java.lang.String"/>
<%
					String title_n= "";
					int len = 40;
					if(title.length() > len){
						title_n = title.substring(0,len)+"...";
					}else{
						title_n = title;
					}
%> 					
					<a href="JavaScript:faqDetailView('FAQ','<bean:write name="vo" property="seq"/>')"><%=title_n %></a>
				</td>
				<!--
				<td><b><bean:write name="vo" property="reg_dt"/></b></td>
				<td><bean:write name="vo" property="read_count"/></td>
				 -->
			</tr>
			</logic:iterate>
		</logic:notEmpty>
	</table>
			<div style="margin:10px 0; display:block">
				<ul class="Page-Num">
					<%@ include file="/include/page.jsp"%>
				</ul>
			</div>
	<%
	if (roleCd.equals("0000A") || roleCd.equals("0000B") || roleCd.equals("0000Z")) {
	%>
	<div class="LY-ContentTitle">
		<h1><img src="../images/content/Content_Title01_3_1.gif" width="600px" height="55px" alt="찾으시는 질문이 없다면 문의하기를 통해 작성해주시면 친절하게 답변드리겠습니다.." /><a href="JavaScript:goInquireForm()" class="btn_Basic"><strong>온라인상담</strong></a></h1>
		
	</div>
	<%
	}
	%>
	<%
	if (roleCd.equals("0000Z") || roleCd.equals("0000C")) {
	%>
	<div style="margin:10px 0; display:block">
		<ul class="Center">
			<div class="Basic-Button">
			<ul class="Right">
				<li><a href="javascript:create()"  class="btn_Basic"><strong>자주묻는질문 등록</strong></a></li>
			</ul>
		</ul>
	</div>
	<%
	}
	%>

	</table>
	</ul>
	</div>

	</html:form>
	
	<br />
	<br />
	<!-- end .ntiscontents -->
	</div>
<!-- end .container -->
</div>
<%@  include file="/include/bottom.jsp"%>