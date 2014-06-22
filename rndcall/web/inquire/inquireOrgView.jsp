<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.inquire.vo.*" %>

<bean:define name="InquireForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="InquireForm" property="vo.public_trans_yn" id="public_trans_yn" type="java.lang.String"/>
<bean:define name="InquireForm" property="vo.stat" id="stat" type="java.lang.String"/>
<bean:define name="InquireForm" property="voList2" id="cateL" type="java.util.ArrayList"/>

<bean:define id="path" type="java.lang.String" value="/Inquire.do"/>
<%
	if (mainLoginVO == null || !mainIsLogin) {	
%>
	<script type="text/javascript">
		alert('로그인이 필요한 메뉴입니다.');
		document.location.href = "index.jsp";
	</script>
<%
	} else if (!mainRoleCD.equals("0000B") && !mainRoleCD.equals("0000C") && !mainRoleCD.equals("0000Z")) {
%>
	<script type="text/javascript">
		alert('권한이 없습니다.');
		document.location.href = "index.jsp";
	</script>
<%		
	}
%>


<script type="text/javascript">
<!--
function goInquireOrgList(){
	fm.elements["method"].value="getInquireOrgList";
	fm.submit();
}

function goSatiInsert(){
<%
	if(login_id.equals("")){
%>
		alert("만족도 평가는 로그인후 이용하실 수 있습니다.");
		return;
<%
	}else{
%>
		fm.elements["method"].value="getSatiOrgInsert";
		fm.submit();
<%
	}
%>	
}

function downLoad(fileNM, saveFileNM, filePath, yn){
    fileDownLoad.elements["fileNM"].value = fileNM;
    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
    fileDownLoad.elements["filePath"].value = filePath;
    fileDownLoad.elements["desCipher"].value = yn;
    fileDownLoad.submit();
}

function goInquireOrgForm(){
	fm.elements["searchVO.searchCategory"].value="";
	fm.elements["method"].value="getInquireOrgForm";
	fm.submit();
}

function goCate(arg){
	fm.elements["searchVO.searchCategory"].value=arg;
	fm.elements["method"].value="getInquireOrgList";
	fm.submit();
}
//-->
</script>
<form name="fileDownLoad" method="post" action="/downLoad.do" >
    <input type="hidden" name="fileNM"/>
    <input type="hidden" name="saveFileNM"/>
    <input type="hidden" name="filePath"/>
	<input type="hidden" name="desCipher" value="N"/>
</form>	
<!-- start # LY-Container -->
	<div class="LY-Container">
		<html:form action="/Inquire" method="post" name="fm" type="kr.go.rndcall.mgnt.inquire.form.InquireForm">
		<html:hidden name="InquireForm" property="method" value="getInquireInsert"/>
		<html:hidden name="InquireForm" property="vo.cell_number"/>
		<html:hidden name="InquireForm" property="vo.email"/>
		<html:hidden name="InquireForm" property="searchVO.loginId"/>
		<html:hidden name="InquireForm" property="searchVO.name"/>
		<html:hidden name="InquireForm" property="searchVO.seq"/>
		<html:hidden name="InquireForm" property="searchVO.board_type"/>
		<html:hidden name="InquireForm" property="searchVO.searchCategory"/>	
	    <html:hidden name="InquireForm" property="searchVO.whichSearch"/>
	    <html:hidden name="InquireForm" property="searchVO.searchTxt"/>
	    <html:hidden name="InquireForm" property="searchVO.start_yy"/>
	    <html:hidden name="InquireForm" property="searchVO.start_mm"/>
	    <html:hidden name="InquireForm" property="searchVO.end_yy"/>
	    <html:hidden name="InquireForm" property="searchVO.end_mm"/>				
		<html:hidden name="InquireForm" property="searchVO.menu_sn"/>
		<!-- start # 상세보기 -->
		<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
		<colgroup>
			<col width="130px" />
			<col width="*" />
		</colgroup>
		<tr><th class="Btmline">제목</th>
			<td><strong>[<bean:write name="InquireForm" property="vo.category1_nm"/>] <bean:write name="InquireForm" property="vo.title" filter="false"/></strong></td>
		</tr>
		<tr><th class="Btmline">등록일</th>
			<td><bean:write name="InquireForm" property="vo.reg_dt"/></td>
		</tr>
		<tr><th class="Btmline">질의내용</th>
			<td>
				<bean:write name="InquireForm" property="vo.contents" filter="false"/>
				<br/><br/>
				<logic:notEmpty name="InquireForm" property="voList">
				첨부파일 : 
					<logic:iterate name="InquireForm" property="voList" indexId="rowNum" id="attachVO">
						<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
					</logic:iterate>
				</logic:notEmpty>
			</td>
		</tr>
<%
	if(stat.equals("Y")){
%>				
		<tr><th class="Btmline1">답변내용</th>
			<td><bean:write name="InquireForm" property="vo.answerContents" filter="false"/>
				<br/><br/>
				<logic:notEmpty name="InquireForm" property="voList1">
				첨부파일 : 
					<logic:iterate name="InquireForm" property="voList1" indexId="rowNum1" id="attachVO">
						<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
					</logic:iterate>
				</logic:notEmpty>
    		</td>
		</tr>
<%
	}
%>			
		</table>
		<br/>
		<!-- start # 체크 -->	
		<bean:define name="InquireForm" property="satiVO.sati_reg_yn" id="sati_reg_yn" type="java.lang.String"/>
<%
	if(stat.equals("Y")){
		if(sati_reg_yn.equals("N")){
%>
			<div class="App-MYCheck">
				<!-- start # 타이틀 -->
					<ul class="Left">
						<li class="Title">답변에 만족하시나요?</li>
					</ul>
					<!-- start #  참여인원 및 평점 -->
					<ul class="Member">
						<li class="Mem">만족도 평가 참여인원 : <strong><bean:write name="InquireForm" property="satiVO.sati_reg_cnt"/>명</strong></li>
						<li class="Grade">평점 : <strong><bean:write name="InquireForm" property="satiVO.avg_app_point"/>점</strong></li>
					</ul>
					<!-- start # 체크 -->
					<ul class="Check">
						<li>
						<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><html:radio name="InquireForm" property="satiVO.app_point" value="5" /> 매우만족<img src="/images/icon/SApp_Star5.gif" alt="매우만족" /></td>
							<td><html:radio name="InquireForm" property="satiVO.app_point" value="4" /> 만족<img src="/images/icon/SApp_Star4.gif" alt="만족" /></td>
							<td><html:radio name="InquireForm" property="satiVO.app_point" value="3" />보통<img src="/images/icon/SApp_Star3.gif" alt="보통" /></td>
							<td><html:radio name="InquireForm" property="satiVO.app_point" value="2" /> 불만족<img src="/images/icon/SApp_Star2.gif" alt="불만족" /></td>
							<td> <html:radio name="InquireForm" property="satiVO.app_point" value="1" /> 매우불만족<img src="/images/icon/SApp_Star1.gif" alt="매우불만족" /></td>
							<td><a href="JavaScript:goSatiInsert()" class="btn_IAppEx"><strong>확인</strong></a> </td>
						</tr>
						</table>
						</li>
					</ul>
				</div>
<%
			}else{	
%>				
				<div class="App-MYCheck-result">
					<!-- start # 타이틀 -->
					<ul class="Left">
						<li class="Title">만족도 평가를 완료하였습니다.</li>
					</ul>
					<!-- start #  참여인원 및 평점 -->
					<ul class="Member">
						<li class="Mem">만족도 평가 참여인원 : <strong><bean:write name="InquireForm" property="satiVO.sati_reg_cnt"/>명</strong></li>
						<li class="Grade">평점 : <strong><bean:write name="InquireForm" property="satiVO.avg_app_point"/>점</strong></li>
					</ul>
				</div>
<%
			}
		}
%>
			<div class="Basic-Button">
				<ul class="Right">				
					<bean:define name="InquireForm" property="vo.up_del_stat" id="up_del_stat" type="java.lang.String"/>
					<bean:define name="InquireForm" property="vo.stat" id="stat" type="java.lang.String"/>
					<li><a href="JavaScript:goInquireOrgList()" class="btn_Basic"><strong>목록</strong></a></li>
<%
				if(stat.equals("N")){
%>
					<li><a href="JavaScript:goInquireOrgForm()" class="btn_Basic"><strong>답변달기</strong></a></li>					
<%
				}else{
%>
					<li><a href="JavaScript:goInquireOrgForm()" class="btn_Basic"><strong>답변수정</strong></a></li>
<%
				}
%>
				</ul>	
			</div>
			</html:form>
		</div>
	</div>
</div>
<%@  include file="/include/bottom.jsp"%>