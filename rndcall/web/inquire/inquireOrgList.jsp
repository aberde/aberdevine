<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.inquire.vo.*" %>

<bean:define name="InquireForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
<bean:define name="InquireForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
<bean:define name="InquireForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
<bean:define name="InquireForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="InquireForm" property="voList2" id="cateL" type="java.util.ArrayList"/>

<bean:define id="path" type="java.lang.String" value="/Inquire.do"/>
<%
	if (mainLoginVO == null || !mainIsLogin) {	
%>
	<script type="text/javascript">
		alert('로그인이 필요한 메뉴입니다.');
		document.location.href = "/index.jsp";
	</script>
<%
	} else if (!mainRoleCD.equals("0000B") && !mainRoleCD.equals("0000C") && !mainRoleCD.equals("0000Z")) {
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
module = '/switch.do?prefix=/inquire&method=getInquireOrgList&page=';
		
function goInquireForm(){
	fm.elements["method"].value="getInquireForm";
	fm.submit();
}

function goInquireMainList(){
	fm.elements["method"].value="getInquireMainList";
	fm.submit();
}

function goCate(arg){
	fm.elements["searchVO.searchCategory"].value=arg;
	fm.elements["searchVO.type"].value="";
	fm.elements["method"].value="getInquireOrgList";
	fm.submit();
}

function goInquireOrgView(arg1, arg2){
	fm.elements["searchVO.seq"].value=arg2;	
	fm.elements["searchVO.board_type"].value=arg1;	
	fm.elements["method"].value="getInquireOrgView";
	fm.submit();
}

function goStatList(arg1, arg2){
	fm.elements["searchVO.board_type"].value=arg1;
	fm.elements["searchVO.type"].value=arg2;
	fm.elements["searchVO.searchCategory"].value="";
	fm.elements["method"].value="getInquireList";
	fm.submit();
}

function goSearch(){
//	fm.elements["searchVO.searchCategory"].value=fm.elements["catr_list"].value;
	fm.elements["method"].value="getInquireOrgList";
	fm.submit();
}
//-->
</script>
	<!-- start # LY-Container -->
	<div class="LY-Container">
		<!-- srart 검색 테이블 감싸기 -->
		<html:form action="/Inquire" method="post" name="fm" type="kr.go.rndcall.mgnt.inquire.form.InquireForm">
		<html:hidden name="InquireForm" property="method" value="getInquireOrgView"/>
		<html:hidden name="InquireForm" property="vo.cell_number"/>
		<html:hidden name="InquireForm" property="vo.email"/>
		<html:hidden name="InquireForm" property="searchVO.loginId"/>
		<html:hidden name="InquireForm" property="searchVO.name"/>
		<html:hidden name="InquireForm" property="searchVO.board_type"/>
		<html:hidden name="InquireForm" property="searchVO.seq"/>
		<html:hidden name="InquireForm" property="searchVO.type"/>
		<html:hidden name="InquireForm" property="searchVO.menu_sn"/>
		
		<!-- start # 기관 담당자 텝 -->
		<div class="Gi-Tap">
			<ul>
					<li><img src="../images/content/Gg_Tap01_up.gif" alt="기관담당자" border="0" /></li>
					<li><a href="/switch.do?prefix=&page=/discussion.do?method=retrieveDiscussDetail&searchVO.menu_sn=12"><img src="../images/content/Gg_Tap02_dw.gif" alt="함께 생각하세요" border="0" /></a></li>
			</ul>
		</div>
		<!-- end # 기관 담당자 텝 -->
		
		<br />
	    <div class="InforSearch02" title="검색">
				<ul class="Search">
				<li>
				<table border="0" cellspacing="0" cellpadding="0" class="Adim-Search-Pre">
			  	<tr><th>기간</th>
			   		<td>
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
			  		<td>&nbsp;</td>
			  	</tr>
			  	<tr><th>분류</th>
			   		<td>	
						<html:select name="InquireForm" property="searchVO.searchCategory">
							<html:option value=""> == 분야선택 == </html:option>
							<html:optionsCollection name="InquireForm" property="voList2" value="code" label="code_nm"/>
						</html:select>&nbsp;&nbsp;
						<html:select name="InquireForm" property="searchVO.whichSearch" title="검색분류">
						  	<html:option value="title">제목</html:option>
						  	<html:option value="contents">내용</html:option>
						  	<html:option value="all">제목+내용</html:option>
						</html:select>
						<html:text name="InquireForm" property="searchVO.searchTxt" title="검색어"/>
					</td>
			  		<td class="Btn"><a href="javascript:goSearch()" class="btn_BSearch"><strong>검색</strong></a>
			  		</td>
			  	</tr>
				</table>
				</li>
				</ul>
			</div>
		<!-- end 검색 테이블 감싸기 -->
		<br>
		<!-- end 소제목 구분 -->'
		
		<div class="Basic-List-area">
		
		<!-- start # 리스트 목록 -->
				<ul class="List">
				<table border="0" cellspacing="0" cellpadding="0" class="Basic-List">
				<colgroup>					
					<col width="50px" />
					<col width="100px" />
					<col width="*" />
					<col width="80px" />
					<col width="80px" />
					<col width="50px" />
					<col width="80px" />
				</colgroup>
				<thead>
	  				<tr><th>번호</th>
	   					<th>분류</th>
	   					<th>제목</th>	   					
						<th>등록자</th>
	   					<th>등록일</th>
						<th>상태</th>
	   					<th>조회수</th>
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
							if(title.length() > 24){
								title_n = title.substring(0,24)+"...";
							}else{
								title_n = title;
							}
%> 						
 							<a href="JavaScript:goInquireOrgView('QNA',<bean:write name="vo" property="seq"/>)">
 							<%=title_n %></a>		   					
		   					</td>
		   					<td><bean:write name="vo" property="reg_nm"/></td>
		   					<td><bean:write name="vo" property="reg_dt"/></td>
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
							<td><bean:write name="vo" property="read_count"/></td>
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