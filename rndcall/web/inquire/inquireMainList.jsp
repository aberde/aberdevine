<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>

<bean:define name="InquireForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>

<bean:define id="path" type="java.lang.String" value="/Inquire.do"/>

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
	<!-- start # LY-Container -->
	<div class="LY-Container">
	<!-- start # OnLine-MSelect -->
		<div class="Online-MSelect">
			<ul class="MTep">
					<li class="Tep05"><a href="JavaScript:goInquireForm()" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image1','','/images/content/OnLine_Mtap01_Up.png',1)">
											<img src="/images/content/OnLine_Mtap01_Dw.png" name="Image1"  border="0" id="Image1" alt="문의하기" /></a></li>
					<li class="Tep06"><a href="JavaScript:goFaq()" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image2','','/images/content/OnLine_Mtap02_Up.png',2)">
											<img src="/images/content/OnLine_Mtap02_Dw.png" name="Image2"  border="0" id="Image2" alt="자주묻는질문" /></a></li>
					<li class="Tep07"><a href="JavaScript:goInquireList('QNA')" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image3','','/images/content/OnLine_Mtap03_Up.png',3)">
											<img src="/images/content/OnLine_Mtap03_Dw.png" name="Image3"  border="0" id="Image3" alt="Q&A"/></a></li>
					<%--<li class="Tep04"><a href="JavaScript:goOffer()" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image4','','/images/content/OnLine_Mtap04_Up.png',4)">
											<img src="/images/content/OnLine_Mtap04_Dw.png" name="Image4"  border="0" id="Image4" alt="제안하기" /></a></li>
										 --%> 
			</ul>
		</div>
		<!-- srart 검색 테이블 감싸기 -->
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
<%
	if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
%>
		<div class="OnlineInfor">
			<ul class="Infor">
				<li title="미처리 현황정보">
				<table border="0" cellspacing="0" cellpadding="0" class="On-untreat"  >
	  			<thead>
				<tr><th>&nbsp;</th>
	    			<th>미처리 총건수</th>
	    			<th>자체처리</th>
	    			<th>타기관처리</th>
	    			<th class="End">&nbsp;</th>
	  			</tr>
				</thead>
				<tbody>
	  			<tr><th>문의현황</th>
	  				<td><a href="JavaScript:goStatList('QNA','1')"><span class="Orenge"><bean:write name="InquireForm" property="vo.statCnt1"/>건</span></a></td>
					<td><a href="JavaScript:goStatList('QNA','2')"><span class="Orenge"><bean:write name="InquireForm" property="vo.statCnt2"/>건</span></a></td>
					<td><a href="JavaScript:goStatList('QNA','3')"><span class="Orenge"><bean:write name="InquireForm" property="vo.statCnt3"/>건</span></a></td>
					<td><a href="JavaScript:goStatList('QNA','4')"class="btn_TList"><strong>전체보기</strong></a></td>
	  			</tr>
	  			<tr><th>제안현황</th>
		  			<td><a href="JavaScript:goStatList1('OFFER','1')"><span class="Orenge"><bean:write name="InquireForm" property="vo.statCnt4"/>건</span></a></td>
					<td><a href="JavaScript:goStatList1('OFFER','2')"><span class="Orenge"><bean:write name="InquireForm" property="vo.statCnt5"/>건</span></a></td>
					<td><a href="JavaScript:goStatList1('OFFER','3')"><span class="Orenge"><bean:write name="InquireForm" property="vo.statCnt6"/>건</span></a></td>
					<td><a href="JavaScript:goStatList1('OFFER','4')" class="btn_TList"><strong>전체보기</strong></a></td>
	  			</tr>
				</tbody>
				</table>
				</li>
			</ul>
		</div>
		<!-- end # Adim-Infor -->
		<br>
<%
		}
%>
		<!-- end 소제목 구분 -->
		<!--  start 검색내부 박스 -->	
		<!-- start # OnLine-MBest -->
		<div class="OnLine-MBest">
			<!-- start # 많이 조회한 문의 Best 10 -->
			<!-- start # 많이 조회한 문의 Best 10 -->
			<ul class="MBest01">
				<li class="Title"><img src="/images/content/OnLine_MBest_Title02.gif" alt="많이 조회한 문의 Best 10" /> </li>
				<li class="more"><a href="JavaScript:goMore('1')"><img src="/images/icon/icon_More02.gif" alt="더보기" border="0" /></a></li>
				<li>
				<table border="0" cellspacing="0" cellpadding="0" class="MBest10-List">
				<logic:empty name="InquireForm" property="voList">
					<tr><td style="border-right:0px;text-align:center; height=:50px;" colspan="3">검색된 결과가 없습니다.</td></tr>
				</logic:empty>
				<logic:notEmpty name="InquireForm" property="voList">
					<logic:iterate name="InquireForm" property="voList" indexId="rowNum1" id="vo">
 					<tr><th><%= rowNum1.intValue() + Util.replaceNull((String)pagerOffset, 0) + 1 %></th>
 						<td class="com2"><bean:write name="vo" property="category1"/></td>
 						<td>
							<bean:define name="vo" property="title" id="title" type="java.lang.String"/>
<%
							String title_n= "";
							if(title.length() > 22){
								title_n = title.substring(0,22)+"...";
							}else{
								title_n = title;
							}
%> 						
 							<a href="javascript:goInquireView('QNA','<bean:write name="vo" property="seq"/>')">
 							<%=title_n %></a>
 						</td>
 					</tr>
 					</logic:iterate>
 				</logic:notEmpty>
 				</table>
				</li>
			</ul>
			<ul class="MBest02">
				<li class="Title"><img src="/images/content/OnLine_MBest_Title01.gif" alt="자주 묻는 질문 Best 10" /> </li>
				<li class="more"><a href="JavaScript:goMore('2')"><img src="/images/icon/icon_More01.gif" alt="더보기" border="0" /></a></li>
				<li>
				<table border="0" cellspacing="0" cellpadding="0" class="MBest10-List">
				<logic:empty name="InquireForm" property="voList1">
					<tr><td style="border-right:0px;text-align:center; height=:50px;" colspan="3">검색된 결과가 없습니다.</td></tr>
				</logic:empty>
				<logic:notEmpty name="InquireForm" property="voList1">
					<logic:iterate name="InquireForm" property="voList1" indexId="rowNum1" id="vo">
 					<tr><th><%= rowNum1.intValue() + Util.replaceNull((String)pagerOffset, 0) + 1 %></th>
 						<td class="com2"><bean:write name="vo" property="category1"/></td>
 						<td>
							<bean:define name="vo" property="title" id="title" type="java.lang.String"/>
<%
							String title_n= "";
							if(title.length() > 22){
								title_n = title.substring(0,22)+"...";
							}else{
								title_n = title;
							}
%> 						
 							<a href="JavaScript:goFaqView('FAQ','<bean:write name="vo" property="seq"/>')">
 							<%=title_n %></a>
 						</td>
 					</tr>
 					</logic:iterate>
 				</logic:notEmpty>
 				</table>
				</li>
			</ul>
			<div class="OnLine-MBest-DLine"></div>
		</div>
		</html:form>
	</div>
	<!-- end # LY-Container -->
</div>
<%@  include file="/include/bottom.jsp"%>