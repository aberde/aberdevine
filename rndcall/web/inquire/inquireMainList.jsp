<%@page contentType="text/html; charset=utf-8" %>

<%@include file="/include/top.jsp"%>

	<bean:define name="InquireForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>

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
		function goInquireMainList(){
			fm.elements["searchVO.type"].value="";
			fm.elements["method"].value="getInquireMainList";
			fm.submit();
		}
		
		function goInquireView(arg1, arg2){
			fm.elements["searchVO.seq"].value=arg2;	
			fm.elements["searchVO.board_type"].value=arg1;
			fm.elements["searchVO.searchCategory"].value="";
			fm.elements["method"].value="getInquireView";
			fm.submit();
		}
		
		function goStatList(arg1, arg2){
			fm.elements["searchVO.board_type"].value=arg1;
			fm.elements["searchVO.type"].value=arg2;
			fm.elements["searchVO.searchCategory"].value="";
			fm.elements["method"].value="getInquireList";
			fm.submit();
		}
		
		function goFaqView(arg1, arg2){
			var url ="/switch.do?prefix=/faq&page=/Faq.do?method=faqDetailView&searchVO.board_type=" + arg1 + "&searchVO.seq=" + arg2 + "&searchVO.menu_sn=01";
			document.location.href = url;
		}
		function goStatList1(arg1, arg2){
			var url ="/switch.do?prefix=/offer&page=/Offer.do?method=offerList&searchVO.board_type="+arg1+"&searchVO.type="+arg2+"&searchVO.menu_sn=01";
			document.location.href = url;
		}
		function goMore(arg){
			if(arg=="1"){
				fm.elements["searchVO.board_type"].value="QNA";
				fm.elements["searchVO.searchCategory"].value="";
				fm.elements["method"].value="getInquireList";
				fm.submit();
			}else{
				var url ="/switch.do?prefix=/faq&page=/Faq.do?method=faqList&searchVO.menu_sn=01";
				document.location.href = url;
			}
		}
	//-->
	</script>
	
	<!-- container -->
	<div id="container" class="advice">
		<!-- lnb -->
		<div class="lnb">
			<div class="tit-area">
				<h2>온라인 상담</h2>
				<span><img src="/img/common/h2_entxt02.gif" alt="Online Consultation" /></span>
			</div>
			<ul class="lnb-lst">
				<li class="on"><a href="JavaScript:goInquireMainList()">온라인 상담</a></li>
				<li><a href="JavaScript:goFaq()">자주 묻는 질문</a></li>
			</ul>
		</div>
		<!-- //lnb -->
		<!-- content -->
		<div class="content clearfix">
			<div class="location txt-r">
				<ul class="fr clearfix">
					<li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
					<li><a href="JavaScript:goInquireMainList()">온라인 상담</a></li>
					<li class="on"><a href="JavaScript:goInquireMainList()">온라인 상담</a></li>
				</ul>
			</div>
			<!-- section -->
			<div class="section">
				<div class="tit-area">
					<h3>온라인 상담</h3>
					<p>R&amp;D 관련 규정 및 제도에 대해 궁금하신 사항에 담당자가 답변해 드립니다.기존 답변을 검색 후 질의해주세요.</p>
				</div>
				
				<html:form action="/Inquire" method="post" name="fm" type="kr.go.rndcall.mgnt.inquire.form.InquireForm">
					<html:hidden name="InquireForm" property="method" value="getInquireInsert"/>
					<html:hidden name="InquireForm" property="vo.cell_number"/>
					<html:hidden name="InquireForm" property="vo.email"/>	
					<html:hidden name="InquireForm" property="searchVO.loginId"/>
					<html:hidden name="InquireForm" property="searchVO.name"/>
					<html:hidden name="InquireForm" property="searchVO.board_type"/>
					<html:hidden name="InquireForm" property="searchVO.searchCategory"/>
					<html:hidden name="InquireForm" property="searchVO.seq"/>
					<html:hidden name="InquireForm" property="searchVO.type"/>
					<html:hidden name="InquireForm" property="searchVO.menu_sn"/>
				
					<!-- inquiry -->
					<div class="inquiry-bx mt30">
						<ul class="clearfix">
							<li><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireList&searchVO.board_type=QNA&searchVO.type=&searchVO.searchCategory=&searchVO.menu_sn=01"><img src="/img/sub/inquiry_img01.gif" alt="국가연구개발사업 공통법령 및 제도 질의 바로가기" /></a></li>
                            <li><a href="/inquire/inquireForm2.jsp"><img src="/img/sub/inquiry_img02.gif" alt="중앙행정기관별 국가연구개발사업 질의 바로가기" /></a></li>
						</ul>
					</div>
					<div class="inquiry-list clearfix">
						<div class="best">
							<h4><img src="/img/sub/inquiry_tit.gif" alt="많이 조회한 문의 BEST5" /></h4>
							<ul class="list">
								<logic:empty name="InquireForm" property="voList">
									<li style="background: url(); text-align: center;">검색된 결과가 없습니다.</li>
								</logic:empty>
								<logic:notEmpty name="InquireForm" property="voList">
									<logic:iterate name="InquireForm" property="voList" indexId="rowNum1" id="vo">
										<li class="num0<%= rowNum1.intValue() + Util.replaceNull((String)pagerOffset, 0) + 1 %>">
											<a href="javascript:goInquireView('QNA','<bean:write name="vo" property="seq"/>')">
												<span class="inquiry-icon"><bean:write name="vo" property="category1"/></span>
												<bean:define name="vo" property="title" id="title" type="java.lang.String"/>
												<%
													String title_n = "";
													if ( title.length() > 20 ) {
														title_n = title.substring(0,20) + "...";
													} else {
														title_n = title;
													}
												%> 						
												<%=title_n %>
											</a>
										</li>
									</logic:iterate>
								</logic:notEmpty>
							</ul>
							<span class="more"><a href="JavaScript:goMore('1')">+ 더보기</a></span>
						</div>
						<div class="best">
							<h4><img src="/img/sub/inquiry_tit02.gif" alt="자주 묻는 질문 BEST5" /></h4>
							<ul class="list">
								<logic:empty name="InquireForm" property="voList1">
									<li style="background: url(); text-align: center;">검색된 결과가 없습니다.</li>
								</logic:empty>
								<logic:notEmpty name="InquireForm" property="voList1">
									<logic:iterate name="InquireForm" property="voList1" indexId="rowNum1" id="vo">
										<li class="num0<%= rowNum1.intValue() + Util.replaceNull((String)pagerOffset, 0) + 1 %>">
											<a href="JavaScript:goFaqView('FAQ','<bean:write name="vo" property="seq"/>')">
												<span class="inquiry-icon"><bean:write name="vo" property="category1"/></span>
												<bean:define name="vo" property="title" id="title" type="java.lang.String"/>
												<%
													String title_n = "";
													if ( title.length() > 20 ) {
														title_n = title.substring(0,20) + "...";
													} else {
														title_n = title;
													}
												%>
												<%=title_n %>
											</a>
										</li>
									</logic:iterate>
								</logic:notEmpty>
							</ul>
							<span class="more"><a href="JavaScript:goMore('2')">+ 더보기</a></span>
						</div>
					</div>
					<!-- // inquiry -->
				</html:form>
			</div>
			<!-- //section -->
		</div>
		<!-- //content -->
	</div>
	<!-- // container -->
	
<%@include file="/include/bottom.jsp"%>