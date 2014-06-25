<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.faq.vo.*" %>

<%@include file="/include/top.jsp"%>

	<bean:define name="FaqForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String"/>
	<bean:define name="FaqForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
	<bean:define name="FaqForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
	<bean:define name="FaqForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
	<bean:define name="FaqForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
	<bean:define name="FaqForm" property="searchVO.loginId" id="loginId" type="java.lang.String"/>
	
	<bean:define id="path" type="java.lang.String" value="/Faq.do"/>
	
	<script type="text/javascript">
	<!--
		var data = {
			num : 1	// 위치순번
		};
		// 현재메뉴 위치.
		menuFocus(data);
	//-->
	</script>
	
	<script type="text/javascript">
	<!--
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
	//-->
	</script>
	
	<!-- container -->
	<div id="container">
		<!-- lnb -->
		<div class="lnb">
			<div class="tit-area">
				<h2>온라인상담</h2>
				<span><img src="/img/common/h2_entxt02.gif" alt="Online Consultation" /></span>
			</div>
			<ul class="lnb-lst">
				<li><a href="JavaScript:goInquireForm()">온라인상담</a></li>
				<li class="on"><a href="JavaScript:goFaq()">자주묻는질문</a></li>
			</ul>				
		</div>
		<!-- //lnb -->
		<!-- content -->
		<div class="content clearfix">
			<div class="location txt-r">		
				<ul class="fr clearfix">
					<li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
					<li><a href="JavaScript:goInquireForm()">온라인상담</a></li>
					<li class="on"><a href="JavaScript:goFaq()">자주묻는 질문</a></li>
				</ul>
			</div>
			<!-- section -->
			<div class="section">		
				<div class="tit-area">
					<h3>자주묻는 질문</h3>
					<p>가장 많이 묻는 질의 응답을 카테고리별로 검색이 가능합니다.</p>
				</div>

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
	
					<!--  tab-typ01 -->
					<div class="tab-type01 mt30">
						<ul class="clearfix">
							<bean:define name="FaqForm" property="searchVO.searchCategory" id="category" type="java.lang.String"/>
							<input name="catr_list" type="hidden" value="<%= category %>" />
<!-- 							// TODO 검색 function 추가. -->
							<li <% if(category.equals("")){ %>class="on"<% } %>><a href="">전체</a></li>
							<logic:notEmpty name="FaqForm" property="voList2">
								<logic:iterate name="FaqForm" property="voList2" indexId="comRowNm" id="mCode">
									<bean:define name="mCode" property="code" id="code" type="java.lang.String"/>
									<li <% if(category.equals(code)){ %>class="on"<% } %>><a href="<bean:write name="mCode" property="code" />"><bean:write name="mCode" property="code_nm" /></a></li>
								</logic:iterate>
							</logic:notEmpty>
						</ul>
					</div>
					<!--  //tab-typ01 -->
					<!-- search-box -->
					<div class="search-box mt20">
						<div class="search-form">
							<html:select name="FaqForm" property="searchVO.whichSearch" style="width:90px;">
							  	<html:option value="title">제목</html:option>
							   	<html:option value="contents">내용</html:option>
								<html:option value="all">제목+내용</html:option>
							</html:select>
							
							<html:text name="FaqForm" property="searchVO.searchTxt" title="검색어를 입력하세요" styleClass="lineInputKO" size="30" maxlength="35"onchange="trim(this)"  />
							<a href="javascript:goSearch()" class="search-btn"><img src="/img/sub/icon_zoom.gif" alt="검색" /></a>
						</div>
					</div>
					<!-- //search-box -->
					<!-- board-type01 -->
					<div class="board-type01 mt20">
						<div class="board-box">
							<table border="0" summary="번호, 제목, 글쓴이, 등록일, 상태, 조회수">
								<caption>자주묻는질문 목록</caption>
								<colgroup>
									<col width="7%" />
									<col width="*" />
									<col width="8%" />
									<col width="12%" />
									<col width="12%" />
									<col width="8%" />
								</colgroup>
								<thead>
									<tr>
										<th scope="col">번호</th>
										<th scope="col">제목</th>
										<th scope="col">글쓴이</th>
										<th scope="col">등록일</th>
										<th scope="col">상태</th>
										<th scope="col">조회수</th>
									</tr>
								</thead>
								<tbody>
									<logic:empty name="FaqForm" property="voList">
										<tr>
											<td colspan="6">검색된 결과가 없습니다.</td>
										</tr>
									</logic:empty>
									<logic:notEmpty name="FaqForm" property="voList">
										<logic:iterate name="FaqForm" property="voList" indexId="rowNum" id="vo">
											<tr <%= rowNum.intValue() % 2 == 0 ? "class=\"on\"" : "" %>>
												<td><%= totRowCount.intValue() - rowNum.intValue() -  Util.replaceNull((String)pagerOffset, 0) %></td>
												<td class="txt-l">
													<bean:define name="vo" property="title" id="title" type="java.lang.String"/>
													<%
														String title_n = "";
														int len = 40;
														if ( title.length() > len ) {
															title_n = title.substring(0,len) + "...";
														} else {
															title_n = title;
														}
													%> 					
													<a href="JavaScript:faqDetailView('FAQ','<bean:write name="vo" property="seq"/>')"><%=title_n %></a>
												</td>
												<td><bean:write name="vo" property="reg_nm"/></td>
												<td><bean:write name="vo" property="reg_dt"/></td>
												<td><span class="btn-set set4 yellow">답변중</span></td>
												<td><bean:write name="vo" property="read_count"/></td>
											</tr>
										</logic:iterate>
									</logic:notEmpty>
								</tbody>
							</table>
						</div>
					</div>
					<!-- // board-type01 -->
					
					<!-- page-area-->
					<%@ include file="/include/page.jsp"%>
					
					<!-- btn-set-->
					<div class="btn-lst txt-r">
						<%
							if ( roleCd.equals("0000A") || roleCd.equals("0000B") || roleCd.equals("0000Z") ) {
						%>
						<span class="btn-set pink"><a href="JavaScript:goInquireForm()">상담하기</a></span>
<!-- 						// TODO 취소 이벤트 추가. -->
						<span class="btn-set"><a href="#">취소</a></span>
						<%
							}
						%>
						<%
							if ( roleCd.equals("0000Z") || roleCd.equals("0000C") ) {
						%>
						<span class="btn-set pink"><a href="javascript:create()">자주묻는질문 등록</a></span>
						<%
							}
						%>
					</div>
					<!-- //btn-set-->
					<!-- //page-area -->
				</html:form>
			</div>
			<!-- //section -->	
		</div>
		<!-- //content -->
	</div>
	<!-- // container -->

<%@include file="/include/bottom.jsp"%>