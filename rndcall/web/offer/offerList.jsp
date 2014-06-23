<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>

<bean:define name="OfferForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
<bean:define name="OfferForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
<bean:define name="OfferForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
<bean:define name="OfferForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="OfferForm" property="searchVO.loginId" id="loginId" type="java.lang.String"/>
<bean:define name="OfferForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String"/>
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
<script type="text/javascript">
<!--
activeDebug = true;  
module = '/switch.do?prefix=/offer&method=offerList&page=';

function offerDetailView(arg1, arg2){
	fm.elements["searchVO.seq"].value=arg2;	
	fm.elements["searchVO.board_type"].value=arg1;	
	fm.elements["method"].value="offerDetailView";
	fm.submit();
}
function goSearch(){
	fm.elements["method"].value="offerList";
	fm.submit();
}
function goStatList(arg1, arg2){
	fm.elements["searchVO.whichSearch"].value="";
	fm.elements["searchVO.searchTxt"].value="";
	fm.elements["searchVO.board_type"].value=arg1;
	fm.elements["searchVO.type"].value=arg2;
	fm.elements["searchVO.searchCategory"].value="";
	fm.elements["method"].value="offerList";
	fm.submit();
}
function goStatList1(arg1, arg2){
	var url ="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireList&searchVO.board_type="+arg1+"&searchVO.type="+arg2+"&searchVO.menu_sn=01";
	document.location.href = url;
}

//function goInquireForm(){
//	if("<%=loginId%>" == ""){
//		alert("로그인 후 이용하실 수 있습니다.");
//		return;
//	}else{
//		var url ="/switch.do?prefix=&page=/Inquire.do?method=getInquireForm";
//		document.location.href = url;
//	}
//}

//function goInquireList(arg){
//	var url ="/switch.do?prefix=&page=/Inquire.do?method=getInquireList&searchVO.board_type="+arg;
//	document.location.href = url;
//}

//function goFaq(){
//		var url ="/switch.do?prefix=&page=/Faq.do?method=faqList";
//		document.location.href = url;
//}
//-->
</script>
</head>
<div class="LY-Container">
	<html:form action="/Offer" method="post" name="fm" type="kr.go.rndcall.mgnt.offer.form.OfferForm">
	<html:hidden name="OfferForm" property="method" value="offerList"/>
	<html:hidden name="OfferForm" property="vo.cell_number"/>
	<html:hidden name="OfferForm" property="vo.email"/>
	<html:hidden name="OfferForm" property="searchVO.loginId"/>
	<html:hidden name="OfferForm" property="searchVO.name"/>
	<html:hidden name="OfferForm" property="searchVO.board_type"/>
	<html:hidden name="OfferForm" property="searchVO.searchCategory"/>
	<html:hidden name="OfferForm" property="searchVO.seq"/>
	<html:hidden name="OfferForm" property="searchVO.type"/>
	<html:hidden name="OfferForm" property="searchVO.menu_sn"/>
	<html:hidden name="OfferForm" property="searchVO.pagerOffset"/>
	
			<div class="LY-ContentTitle">
				<h1><img src="/images/content/Content_Title06_1.gif" alt="제안하기 - 가장 많이 묻는 질의응답을 카테고리별로 보실 수 있습니다." /></h1>
			</div>&nbsp;&nbsp;&nbsp;
		
			<!-- start # OnlineInfor // 미처리 현황정보-->
			<div class="OnlineInfor">
				<ul class="Infor">
					<li title="미처리 현황정보">
							<table border="0" cellspacing="0" cellpadding="0" class="On-untreat">
		  						<thead>
								<tr>
		    						<th>&nbsp;</th>
		    						<th>미처리 총건수</th>
		    						<th>자체처리</th>
		    						<th>타기관처리</th>
		    						<th class="End">&nbsp;</th>
		  						</tr>
								</thead>
								<tbody>
					  			<tr><th>문의현황</th>
					  				<td><a href="JavaScript:goStatList1('QNA','1')"><span class="Orenge"><bean:write name="OfferForm" property="vo.statCnt1"/>건</span></a></td>
									<td><a href="JavaScript:goStatList1('QNA','2')"><span class="Orenge"><bean:write name="OfferForm" property="vo.statCnt2"/>건</span></a></td>
									<td><a href="JavaScript:goStatList1('QNA','3')"><span class="Orenge"><bean:write name="OfferForm" property="vo.statCnt3"/>건</span></a></td>
									<td><a href="JavaScript:goStatList1('QNA','4')"class="btn_TList" style="color:#444;"><strong>전체보기</strong></a></td>
					  			</tr>
					  			<tr><th>신문고현황</th>
						  			<td><a href="JavaScript:goStatList('OFFER','1')"><span class="Orenge"><bean:write name="OfferForm" property="vo.statCnt4"/>건</span></a></td>
									<td><a href="JavaScript:goStatList('OFFER','2')"><span class="Orenge"><bean:write name="OfferForm" property="vo.statCnt5"/>건</span></a></td>
									<td><a href="JavaScript:goStatList('OFFER','3')"><span class="Orenge"><bean:write name="OfferForm" property="vo.statCnt6"/>건</span></a></td>
									<td><a href="JavaScript:goStatList('OFFER','4')" class="btn_TList" style="color:#444;"><strong>전체보기</strong></a></td>
					  			</tr>
							</tbody>
							</table>
					</li>
				</ul>
			</div>
			<!-- end # OnlineInfor // 미처리 현황정보-->
			<!-- start # InforSearch // 멀티라인 -->
			<div class="InforSearch02" title="검색">
				<ul class="Search">
					<li>
						<table border="0" cellspacing="0" cellpadding="0" class="Search-Pre">
							<tr>
			   					 <td>	
									<html:select name="OfferForm" property="searchVO.whichSearch">
									  	<html:option value="title">제목</html:option>
									   	<html:option value="contents">내용</html:option>
										<html:option value="all">제목+내용</html:option>
									</html:select>
								 	<html:text name="OfferForm" property="searchVO.searchTxt" title="제목" styleClass="lineInputKO" size="50" maxlength="35"onchange="trim(this)"  />
								 </td>
			  				     <td class="Btn"><a href="javascript:goSearch()" class="btn_BSearch"><strong>검색</strong></a> </td>
			  				</tr>
						</table>
					</li>
				</ul>
			</div>
			<br/>
			<!-- start # InforSearch // 멀티라인 -->
		
	
			<div class="Basic-List-area">
				<!-- start # 리스트 검색 -->
				<ul class="List">
				<table border="0" cellspacing="0" cellpadding="0" class="Basic-List">
				<colgroup>					
					<col width="40px" />
					<col width="*" />
					<col width="100px" />
					<col width="100px" />
					<col width="50px" />
					<col width="50px" />
				</colgroup>
				<thead>
					<tr>
						<th class="center">번호</th>
						<th class="center">제목</th>
						<th class="center">등록자</th>
						<th class="center">등록일</th>
						<th class="center">상태</th>
						<th class="center">조회수</th>
					</tr>
				</thead>
				<tbody>
				<logic:empty name="OfferForm" property="voList">
					<tr><td style="border-right:0px;text-align:center; height=:50px;" colspan="7">등록된 정보가 없습니다.</td></tr>
				</logic:empty>
				<logic:notEmpty name="OfferForm" property="voList">
					<logic:iterate name="OfferForm" property="voList" indexId="rowNum" id="vo">
						<tr><td class="center"><%= totRowCount.intValue() - rowNum.intValue() -  Util.replaceNull((String)pagerOffset, 0) %></td>
							<td class="Left">
							<bean:define name="vo" property="title" id="title" type="java.lang.String"/>
							<%
								String title_n= "";
								int len = 14;
								if(title.length() > len){
									title_n = title.substring(0,len)+"...";
								}else{
									title_n = title;
								}
%> 
								<a href="JavaScript:offerDetailView('OFFER',<bean:write name="vo" property="seq"/>)"><bean:write name="vo" property="title"/></a>
							</td>		
							<td class="center">
								<bean:write name="vo" property="reg_nm"/>
							</td>
							<td class="center">
								<bean:write name="vo" property="reg_dt"/>
							</td>
<%
						if(roleCd.equals("0000Z") || roleCd.equals("0000C")){
%>							
							<td class="center">
								<bean:define name="vo" property="stat" id="stat" type="java.lang.String"/>
<%
							if(stat.equals("Y")){
								out.print("<a href='#' class='btn_Orange' style='color:#FFFFFF;'><strong>접수완료</strong></a>");
							}else{
								out.print("<a href='#' class='btn_Green' style='color:#FFFFFF;'><strong>접수중</strong></a>");
							}
%>								
							</td>
<%
						}
%>							
							<td class="center">
								<bean:write name="vo" property="read_count"/>
							</td>					
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
		</div>
	</html:form>
	
	<!-- end 검색 테이블 감싸기 -->
	<br />
	<br />
	<!-- end .ntiscontents -->
	</div>
	
<!-- end .container -->

<%@  include file="/include/bottom.jsp"%>