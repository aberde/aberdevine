<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.inquire.vo.*" %>

<bean:define name="InquireForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
<bean:define name="InquireForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
<bean:define name="InquireForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
<bean:define name="InquireForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="InquireForm" property="searchVO.searchCategory" id="searchCategory" type="java.lang.String"/>
<bean:define name="InquireForm" property="voList2" id="cateL" type="java.util.ArrayList"/>

<bean:define id="path" type="java.lang.String" value="/Inquire.do"/>
<script type="text/javascript">
<!--
activeDebug = true;  
module = '/switch.do?prefix=/inquire&method=getInquireList&page=';


function goInquireMainList(){
	fm.elements["method"].value="getInquireMainList";
	fm.submit();
}

function goCate(arg){
<%
	if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
%>	
		fm.elements["searchVO.start_yy"].value="";
		fm.elements["searchVO.start_mm"].value="";
		fm.elements["searchVO.end_yy"].value="";
		fm.elements["searchVO.end_mm"].value="";
<%
	}
%>
	fm.elements["searchVO.searchTxt"].value="";
	fm.elements["searchVO.whichSearch"].value="";
	fm.elements["searchVO.searchCategory"].value=arg;
	fm.elements["searchVO.type"].value="";
	fm.elements["method"].value="getInquireList";
	fm.submit();
}

function goInquireView(arg1, arg2){
	fm.elements["searchVO.seq"].value=arg2;	
	fm.elements["searchVO.board_type"].value=arg1;	
	fm.elements["method"].value="getInquireView";
	fm.submit();
}

function goStatList(arg1, arg2){
	fm.elements["searchVO.board_type"].value=arg1;
	fm.elements["searchVO.type"].value=arg2;
	fm.elements["searchVO.searchCategory"].value="";
	fm.elements["method"].value="getInquireList";
	fm.elements["searchVO.menu_sn"].value = "14";
	fm.submit();
}

function goStatList1(arg1, arg2){
	var url ="/switch.do?prefix=/offer&page=/Offer.do?method=offerList&searchVO.board_type="+arg1+"&searchVO.type="+arg2+"&searchVO.menu_sn=14";
	document.location.href = url;
}

function goSearch(){
//	fm.elements["searchVO.searchCategory"].value=fm.elements["catr_list"].value;
	fm.elements["method"].value="getInquireList";
	fm.submit();
}


function goExcelDown(){
	fm.elements["searchVO.board_type"].value='QNA';
	fm.elements["method"].value="getInquireExcelDown";
	fm.submit();
}

function goTrans_info(arg){
	var width = '430';
    var height = '350';
    var left = (screen.width - width)/2;
    var top = (screen.height - height)/2;
   	var winNM = 'trans_info';
   	var url = '/switch.do?prefix=/inquire&page=/Inquire.do?method=getTrans_info&searchVO.trans_id='+arg;  
    var windowFeatures = "width=" + width + ",height=" + height +
        ",status,resizable,scrollbars=N,left=" + left + ",top=" + top +
        ",screenX=" + left + ",screenY=" + top;
   	window.open(url, winNM, windowFeatures);
}
//-->
</script>
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
				var ObjEventLeftMenu = new EventLeftMenu(0, 3, 0);
				//-->
				</SCRIPT>
		</div>
		<!-- end # 레프트 메뉴 -->
		<!-- start # LY-ContentTitle -->
		<div class="LY-Content">
			<div class="LY-ContentTitle">
				<h1><img src="/images/content/Content_Title01_3.gif" alt="Q&A - 다른 사용자들이 등록한 질의응답정보를 조회 할 수 있습니다." /></h1>
			</div>
			<!-- srart 검색 테이블 감싸기 -->
			<html:form action="/Inquire" method="post" name="fm" type="kr.go.rndcall.mgnt.inquire.form.InquireForm">
			<html:hidden name="InquireForm" property="method" value="getInquireList"/>
			<html:hidden name="InquireForm" property="vo.cell_number"/>
			<html:hidden name="InquireForm" property="vo.email"/>
			<html:hidden name="InquireForm" property="searchVO.loginId"/>
			<html:hidden name="InquireForm" property="searchVO.name"/>
			<html:hidden name="InquireForm" property="searchVO.board_type"/>
			<html:hidden name="InquireForm" property="searchVO.seq"/>
			<html:hidden name="InquireForm" property="searchVO.type"/>			
			<html:hidden name="InquireForm" property="searchVO.menu_sn"/>
<%
		if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
%>
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
					  				<td><a href="JavaScript:goStatList('QNA','1')"><span class="Orenge"><bean:write name="InquireForm" property="vo.statCnt1"/>건</span></a></td>
									<td><a href="JavaScript:goStatList('QNA','2')"><span class="Orenge"><bean:write name="InquireForm" property="vo.statCnt2"/>건</span></a></td>
									<td><a href="JavaScript:goStatList('QNA','3')"><span class="Orenge"><bean:write name="InquireForm" property="vo.statCnt3"/>건</span></a></td>
									<td><a href="JavaScript:goStatList('QNA','4')"class="btn_TList"><strong>전체보기</strong></a></td>
					  			</tr>
					  			<tr><th>신문고현황</th>
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
			<!-- end # OnlineInfor // 미처리 현황정보-->
<%
		}
		if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
%>				
			<!-- start # Adim-InforSearch -->
			<div class="InforSearch02" title="검색">
				<ul class="Search">
				<li>
				<table border="0" cellspacing="0" cellpadding="0" class="Search-Pre">
			  	<tr><th>기간</th>
			   		<td colspan="3">
			   			<html:select name="InquireForm" property="searchVO.start_yy" title="시작년도" alt="시작년도">
						<html:options name="InquireForm" property="yearListDesc"/>
						</html:select>년
						<html:select name="InquireForm" property="searchVO.start_mm" title="시작월" alt="시작월">
						<html:options name="InquireForm" property="mon_list"/>
						</html:select>월
						~
						<html:select name="InquireForm" property="searchVO.end_yy" title="종료년도" alt="종료년도">
						<html:options name="InquireForm" property="yearListDesc"/>
						</html:select>년
						<html:select name="InquireForm" property="searchVO.end_mm" title="종료월" alt="종료월">
						<html:options name="InquireForm" property="mon_list"/>
						</html:select>월
			   		</td>
			  		<td class="Btn" rowspan="2"><a href="javascript:goSearch()" class="btn_BSearch"><strong>검색</strong></a> </td>
			  	</tr>
			  	<tr>
			  		<th width="80px">등록방법</th>
			  		<td>
			  			<html:select name="InquireForm" property="searchVO.insert_type" title="등록방법" alt="등록방법">
			  				<html:option value="">전체</html:option>
			  				<html:option value="ONLINE">온라인</html:option>
			  				<html:option value="OFFLINE">오프라인</html:option>
			  			</html:select>
			  		</td>
			  		<th width="80px">처리기관</th>
			  		<td>
			  			<html:select name="InquireForm" property="searchVO.public_trans_yn" title="처리기관" alt="처리기관">
			  				<html:option value="">전체</html:option>
			  				<html:option value="N">자체</html:option>
			  				<html:option value="Y">타기관</html:option>
			  			</html:select>
			  		</td>
			  	</tr>
			  	<tr><td colspan="4">	
						<html:select name="InquireForm" property="searchVO.searchCategory">
							<html:option value=""> == 분류선택 == </html:option>
							<html:optionsCollection name="InquireForm" property="voList2" value="code" label="code_nm"/>
						</html:select>&nbsp;&nbsp;
						<html:select name="InquireForm" property="searchVO.whichSearch" title="검색분류">
						  	<html:option value="title">제목</html:option>
						  	<html:option value="contents">내용</html:option>
						  	<html:option value="all">제목+내용</html:option>
						  	<html:option value="name">작성자명</html:option>
						</html:select>
						<html:text name="InquireForm" property="searchVO.searchTxt" size="30" title="검색어"/>
					</td>
			  		<td class="Btn"><a href="javascript:goExcelDown()" class="btn_BExDw"><strong>엑셀다운로드</strong></a>
			  		</td>
			  	</tr>
				</table>
				</li>
				</ul>
			</div>
			<!-- start # Adim-InforSearch -->
			
			<br />
			<br />
			
			<!-- start # Basic-List -->
			<div class="Basic-List-area">
<%
		}else{
%>
			<div class="InforSearch" title="검색">
				<ul class="Search">
					<li>
						<table border="0" cellspacing="0" cellpadding="0" class="Search-Pre">
							<tr><td>
									<html:select name="InquireForm" property="searchVO.searchCategory">
										<html:option value=""> == 분류선택 == </html:option>
										<html:optionsCollection name="InquireForm" property="voList2" value="code" label="code_nm"/>
									</html:select>&nbsp;&nbsp;
									<html:select name="InquireForm" property="searchVO.whichSearch">
									  	<html:option value="title">제목</html:option>
									   	<html:option value="contents">내용</html:option>
										<html:option value="all">제목+내용</html:option>
									</html:select>
								 	<html:text name="InquireForm" property="searchVO.searchTxt" title="검색어" styleClass="lineInputKO" size="40" maxlength="35"onchange="trim(this)"  />
								 </td>
			  				     <td class="Btn"><a href="javascript:goSearch()" class="btn_BSearch"><strong>검색</strong></a> </td>
			  				</tr>
						</table>
					</li>
				</ul>
			</div>
			<br/>
			<div class="Basic-List-area">
				<!-- start # 리스트 검색 -->
<%
				}
%>				

			
				<!-- start # 리스트 목록 -->
				<ul class="List">
				<table border="0" cellspacing="0" cellpadding="0" class="Basic-List">
				<colgroup>					
					<col width="40px" />
					<col width="120px" />
					<col width="*" />
<%
				if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
%>				
					<col width="60px" />
					<col width="60px" />
<%
				}
%>
					<col width="80px" />
<%
					if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
					}else{
%>		   					
	   						
					<col width="50px" />
<%
					}
%>	 									
				</colgroup>
				<thead>
	  				<tr><th>번호</th>
	   					<th>분류</th>
	   					<th>제목</th>
<%
					if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
%>				
						<th>상태</th>
						<th>담당자</th>
	   					<th>등록방법</th>
<%
					}
%>	   						   			
	   					<th>등록일</th>
<%
					if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
					}else{
%>		   					
	   					<th>조회수</th>
<%
					}
%>	   					
	  				</tr>
				</thead>
				<tbody>
	  			<logic:empty name="InquireForm" property="voList">
					<tr><td style="border-right:0px;text-align:center; height=:50px;" colspan="99">등록된 정보가 없습니다.</td></tr>
				</logic:empty>
				<logic:notEmpty name="InquireForm" property="voList">
					<logic:iterate name="InquireForm" property="voList" indexId="rowNum" id="vo">
			  			<tr><td><%= totRowCount.intValue() - rowNum.intValue() -  Util.replaceNull((String)pagerOffset, 0) %></td>
		   					<td><bean:write name="vo" property="category1"/></td>
		   					<td class="Left">
								<bean:define name="vo" property="title" id="title" type="java.lang.String"/>
<%
								String title_n= "";
								int len = 24;
								if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
									len = 24;
								}
								if(title.length() > len){
									title_n = title.substring(0,len)+"...";
								}else{
									title_n = title;
								}
%> 						
 								<a href="JavaScript:goInquireView('QNA',<bean:write name="vo" property="seq"/>)">
 								<%=title_n %></a>		   					
		   					</td>
<%
				if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
%>
							<td>
								<bean:define name="vo" property="stat" id="stat" type="java.lang.String"/>
<%
								if(stat.equals("Y")){
									out.print("<a href='#' class='btn_Orange' style='color:#FFFFFF;'><strong>완료</strong></a>");
								}else{
									out.print("<a href='#' class='btn_Green' style='color:#FFFFFF;'><strong>처리중</strong></a>");
								}
%>
							</td>
							<td>
								<bean:define name="vo" property="trans_id" id="trans_id" type="java.lang.String"/>
<%
								if(!trans_id.equals("-")){
%>								
									<a href="JavaScript:goTrans_info('<bean:write name="vo" property="trans_id"/>')" class="btn_TBasic"><strong>보기</strong></a>
<%	
								}
%>
							</td>
							<td><bean:write name="vo" property="insert_type"/></td>
<%
					}
%>
		   					<td><bean:write name="vo" property="reg_dt"/></td>
<%
					if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
					}else{
%>		   					
	   						<td><bean:write name="vo" property="read_count"/></td>
<%
					}
%>			   					
		   					
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
		<!-- end # Basic-List -->
	</div>
	<!-- end # LY-Container -->
</div>
<!-- end #LY-Wrapper -->
<%@  include file="/include/bottom.jsp"%>