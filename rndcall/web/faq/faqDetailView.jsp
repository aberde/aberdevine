<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<bean:define name="FaqForm" property="searchVO" id="searchVO" type="kr.go.rndcall.mgnt.faq.vo.FaqSearchVO"/>
<bean:define name="FaqForm" property="searchVO.loginId" id="loginId" type="java.lang.String" />
<bean:define name="FaqForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String" />
<bean:define id="path" type="java.lang.String" value="/Faq.do"/>
<%
	String uni = searchVO.getUni();
%>
<script language="JavaScript">
	
function goCancel(){
	document.location.href="/index.jsp";
}
	
function create(){
	fm.elements["method"].value="faqInsertForm";
	fm.submit();
}

function detail(elem, seq) {
	document.fm.elements["method"].value = "faqDetailView";
	document.fm.elements["searchVO.seq"].value = seq;
	document.fm.submit();
}

function faqAnswerInsert(){
	var aa = fm.elements["vo.answer_cont"].value
	var aa2 = fm.elements["vo.title"].value
	fm.elements["method"].value="faqAnswerInsert";
	fm.submit();
}

function goSatiInsert(){
	if("<%=login_id%>" ==""){
		alert("로그인후 이용하실수 있습니다.");
		return;
	}else{
		fm.elements["method"].value="faqSatiInsert";
		fm.submit();
	}
}

function goFaq(){
	//fm.elements["method"].value="faqList";
	fmgotoList.action = "/switch.do?prefix=/faq&method=faqList&page=/Faq.do?pager.offset=" + fm.elements["searchVO.pagerOffset"].value;
	fmgotoList.submit();
}
function faqContentConfirm(){
	fm.elements["searchVO.searchCategory"].value="";
	fm.elements["method"].value="faqContentConfirm";
	fm.submit();
}
function faqContentDelete(){
	fm.elements["searchVO.searchCategory"].value="";
	fm.elements["method"].value="faqDelete";
	fm.submit();
}
function downLoad(fileNM, saveFileNM, filePath, yn){
    fileDownLoad.elements["fileNM"].value = fileNM;
    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
    fileDownLoad.elements["filePath"].value = filePath;
    fileDownLoad.elements["desCipher"].value = yn;
    fileDownLoad.submit();
}
</script>
<form name="fileDownLoad" method="post" action="/downLoad.do" >
    <input type="hidden" name="fileNM"/>
    <input type="hidden" name="saveFileNM"/>
    <input type="hidden" name="filePath"/>
	<input type="hidden" name="desCipher" value="N"/>
</form>	
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
			<html:form action="/Faq" method="post" name="fm" type="kr.go.rndcall.mgnt.faq.form.FaqForm" scope="request">
		    <html:hidden name="FaqForm" property="method" value="faqInsert"/>
			<html:hidden name="FaqForm" property="vo.cell_number"/>
			<html:hidden name="FaqForm" property="vo.email"/>
			<html:hidden name="FaqForm" property="vo.title"/>
			<html:hidden name="FaqForm" property="searchVO.loginId"/>
			<html:hidden name="FaqForm" property="searchVO.name"/>
			<html:hidden name="FaqForm" property="searchVO.seq"/>
			<html:hidden name="FaqForm" property="searchVO.board_type"/>
			<html:hidden name="FaqForm" property="searchVO.searchCategory"/>
			<html:hidden name="FaqForm" property="searchVO.menu_sn"/>
			<html:hidden name="FaqForm" property="searchVO.uni"/>
			<html:hidden name="FaqForm" property="searchVO.pagerOffset"/>
			<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
			<colgroup>
				<col width="100px" />
				<col width="*" />
			</colgroup>
			<tr>
				<th class="Btmline">제목</th>
				<td><strong><bean:write name="FaqForm" property="vo.title" filter="false"/></strong></td>
			</tr>
			<tr>
				<th class="Btmline">분류</th>
				<td>
					<bean:write name="FaqForm" property="vo.category1"/>
					<logic:notEqual name="FaqForm" property="vo.category2" value="">
						-<bean:write name="FaqForm" property="vo.category2"/>
					</logic:notEqual>
					<logic:equal name="FaqForm" property="vo.analysis_yn" value="Y">
						&nbsp;(유권해석)
					</logic:equal>
				</td>
			</tr>
			<!--tr>
				<th class="Btmline">등록일</th>
				<td><bean:write name="FaqForm" property="vo.reg_dt"/></td>
			</tr-->
			<tr><th class="Btmline">질의내용</th>
				<td>
				<bean:write name="FaqForm" property="vo.contents" filter="false"/>
    			<br/><br/>
				<logic:notEmpty name="FaqForm" property="voList1">
				첨부파일 :
					<logic:iterate name="FaqForm" property="voList1" indexId="rowNum" id="attachVO">
						<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');" ><bean:write name="attachVO" property="file_nm" /></a></a><br/>
					</logic:iterate>
				</logic:notEmpty>
				</td>
			</tr>
			<tr><th class="Btmline1">답변내용</th>
    			<td><bean:write name="FaqForm" property="vo.answer_cont" filter="false"/>
				</td>
			</tr>	
			</table>
			<br/>

		<bean:define name="FaqForm" property="satiVO.sati_reg_yn" id="sati_reg_yn" type="java.lang.String"/>
<%
		if(sati_reg_yn.equals("N")){
%>
			<div class="App-Check">
			<!-- start # 타이틀 -->
				<ul class="Left">
					<li class="Title">답변에 만족하시나요?</li>
				</ul>
				<!-- start #  참여인원 및 평점 -->
				<ul class="Member">
					<li class="Mem">만족도 평가 참여인원 : <strong><bean:write name="FaqForm" property="satiVO.sati_reg_cnt"/>명</strong></li>
					<li class="Grade">평점 : <strong><bean:write name="FaqForm" property="satiVO.avg_app_point"/>점</strong></li>
				</ul>
				<!-- start # 체크 -->
				<ul class="Check">
					<li>
					<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><html:radio name="FaqForm" property="satiVO.app_point" value="5" />매우만족 		<img src="/images/icon/App_Star5.gif" alt="매우만족" /></td>
						<td><html:radio name="FaqForm" property="satiVO.app_point" value="4" />만족 			<img src="/images/icon/App_Star4.gif" alt="만족" /></td>
						<td><html:radio name="FaqForm" property="satiVO.app_point" value="3" />보통 			<img src="/images/icon/App_Star3.gif" alt="보통" /></td>
						<td><html:radio name="FaqForm" property="satiVO.app_point" value="2" />불만족 			<img src="/images/icon/App_Star2.gif" alt="불만족" /></td>
						<td> <html:radio name="FaqForm" property="satiVO.app_point" value="1" />매우불만족 	<img src="/images/icon/App_Star1.gif" alt="매우불만족" /></td>
						<td><a href="JavaScript:goSatiInsert()" class="btn_IAppEx"><strong>확인</strong></a> </td>
					</tr>
					</table>
					</li>
				</ul>
			</div>			
<%
		}else{	
%>				
				
				
				
				
			<div class="App-Check-result">
				<!-- start # 타이틀 -->
				<ul class="Left">
					<li class="Title">만족도 평가를 완료하였습니다.</li>
				</ul>
				<!-- start #  참여인원 및 평점 -->
				<ul class="Member">
					<li class="Mem">만족도 평가 참여인원 : <strong><bean:write name="FaqForm" property="satiVO.sati_reg_cnt"/>명</strong></li>
					<li class="Grade">평점 : <strong><bean:write name="FaqForm" property="satiVO.avg_app_point"/>점</strong></li>
				</ul>
			</div>
<%
			}
%>
		<div style="margin:10px 0; display:block">
		<ul class="Right">
			<div class="Basic-Button">
			<ul class="Center">
				<%
				if(uni.equals("uni")){
				%>
				<li><a href="JavaScript:history.back()" class="btn_Basic"><strong>목록</strong></a>
			   	<%
				}else{
			   	%>
				<li><a href="JavaScript:goFaq()"  class="btn_Basic"><strong>목록</strong></a>
				<%
				}
			   	%>
				<%
				if(roleCd.equals("0000Z") || roleCd.equals("0000C")){
				%>
				<a href="javascript:faqContentConfirm();" class="btn_Basic"><strong>수정</strong></a>
				<a href="JavaScript:faqContentDelete()" class="btn_Basic"><strong>삭제</strong></a></li>
				<% 
				}
				%>
			</ul>	
			</div>
		</div>
	</html:form>
	<html:form action="/Faq" method="post" name="fmgotoList" type="kr.go.rndcall.mgnt.faq.form.FaqForm" scope="request">
    <html:hidden name="FaqForm" property="method" value="faqInsert"/>
	<html:hidden name="FaqForm" property="vo.cell_number"/>
	<html:hidden name="FaqForm" property="vo.email"/>
	<html:hidden name="FaqForm" property="vo.title"/>
	<html:hidden name="FaqForm" property="searchVO.loginId"/>
	<html:hidden name="FaqForm" property="searchVO.name"/>
	<html:hidden name="FaqForm" property="searchVO.seq"/>
	<html:hidden name="FaqForm" property="searchVO.board_type"/>
	<html:hidden name="FaqForm" property="searchVO.searchCategory"/>
	<html:hidden name="FaqForm" property="searchVO.menu_sn"/>
	<html:hidden name="FaqForm" property="searchVO.uni"/>
	<html:hidden name="FaqForm" property="pagerOffset"/>
	<html:hidden name="FaqForm" property="searchVO.pagerOffset"/>
	<html:hidden name="FaqForm" property="searchVO.searchTxt"/>
	</html:form>
	<!-- end 검색 테이블 감싸기 -->
	<br />
	<br />
	<!-- end .ntiscontents -->
	</div>
<!-- end .container -->
<%@  include file="/include/bottom.jsp"%>