<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.inquire.vo.*" %>
<bean:define name="InquireForm" property="searchVO" id="searchVO" type="kr.go.rndcall.mgnt.inquire.vo.InquireSearchVO"/>
<bean:define name="InquireForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="InquireForm" property="voList" id="file_list" type="java.util.ArrayList"/>
<bean:define name="InquireForm" property="voList2" id="cateL" type="java.util.ArrayList"/>
<bean:define id="path" type="java.lang.String" value="/Inquire.do"/>
<%
	String uni = searchVO.getUni();
%>
<script type="text/javascript">
<!--		
//function goInquireForm(){
//	fm.elements["method"].value="getInquireForm";
//	fm.submit();
//}


function goInquireView(arg1, arg2){
	fm.elements["seq"].value=arg2;	
	fm.elements["board_type"].value=arg1;	
	fm.elements["method"].value="getInquireView";
	fm.submit();
}


function goSatiInsert(){
	if("<%=login_id%>" ==""){
		alert("로그인후 이용하실수 있습니다.");
		return;
	}else{
		fm.elements["method"].value="getSatiInsert";
		fm.submit();
	}
}

function downLoad(fileNM, saveFileNM, filePath, yn){
    fileDownLoad.elements["fileNM"].value = fileNM;
    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
    fileDownLoad.elements["filePath"].value = filePath;
    fileDownLoad.elements["desCipher"].value = yn;
    fileDownLoad.submit();
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
	fm.elements["searchVO.searchCategory"].value=arg;
	fm.elements["method"].value="getInquireList";
	fm.submit();
}

function goUpdate(){
	fm.elements["searchVO.searchCategory"].value="";
	fm.elements["method"].value="getInquireUpdateForm";
	fm.submit();
}

function goDelete(){
	if(confirm("정말로 삭제 하시겠습니까?")){
		fm.elements["searchVO.searchCategory"].value="";
		fm.elements["method"].value="getInquireDelete";
		fm.submit();
	}
}

function goAnswerInsert(){
<%
	if(mainRoleCD.equals("0000Z")){
%>		
		fm.elements["searchVO.searchCategory"].value="";
		fm.elements["method"].value="getAnswerInsertForm";
		fm.submit();
<%		
	}else{
%>
		if('<bean:write name="InquireForm" property="vo.stat"/>' == "N" && '<bean:write name="InquireForm" property="vo.public_trans_yn"/>' == "Y"){
			alert("타기관 담당자에게 위임된 문의사항입니다.");
		}else{
			fm.elements["searchVO.searchCategory"].value="";
			fm.elements["method"].value="getAnswerInsertForm";
			fm.submit();
		}
<%
	}
%>		
}

function goOrgTransForm(arg){
	var width = '430';
    var height = '250';
    var left = (screen.width - width)/2;
    var top = (screen.height - height)/2;
   	var winNM = 'orgTransForm';
   	var url = '/switch.do?prefix=/inquire&page=/Inquire.do?method=getOrgTransForm&searchVO.seq='+arg;  
    var windowFeatures = "width=" + width + ",height=" + height +
        ",status,resizable,scrollbars=N,left=" + left + ",top=" + top +
        ",screenX=" + left + ",screenY=" + top;
   	window.open(url, winNM, windowFeatures);
}
function goPrint(arg1, arg2){
	window.open("/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireViewPop&searchVO.board_type=" + arg1 + "&searchVO.seq=" + arg2, "techpop","toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=800,height=800");
}
function goScrap(arg1, arg2){
	if("<%=login_id%>" ==""){
		alert("로그인후 이용하실수 있습니다.");
		return;
	}else{
		fm.elements["searchVO.board_type"].value=arg1;	
		fm.elements["searchVO.seq"].value=arg2;	
		fm.elements["method"].value="getInquireScrap";
		fm.submit();
	}
}
//-->
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
				var ObjEventLeftMenu = new EventLeftMenu(0, 3, 0);
				//-->
				</SCRIPT>
		</div>
		<!-- end # 레프트 메뉴 -->
	<!-- start # LY-Container -->
	<div class="LY-Content">
		<!-- start # LY-ContentTitle -->
			<div class="LY-ContentTitle">
				<h1><img src="/images/content/Content_Title01_3.gif" alt="Q&A - 다른 사용자들이 등록한 질의응답정보를 조회 할 수 있습니다." /></h1>
			</div>
			<!-- end # LY-ContentTitle -->			
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
			<html:hidden name="InquireForm" property="searchVO.menu_sn"/>
			<html:hidden name="InquireForm" property="searchVO.uni"/>
<%
		if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
%>	
			<html:hidden name="InquireForm" property="searchVO.start_yy"/>
		    <html:hidden name="InquireForm" property="searchVO.start_mm"/>
		    <html:hidden name="InquireForm" property="searchVO.end_yy"/>
		    <html:hidden name="InquireForm" property="searchVO.end_mm"/>
<%
		}
%>
		<!-- start # 상세보기 -->
		<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
			<colgroup>
				<col width="130px" />
				<col width="*" />
			</colgroup>
			<tr><th class="Btmline">제목</th>
				<td colspan="3"><strong>[<bean:write name="InquireForm" property="vo.category1_nm"/>] <bean:write name="InquireForm" property="vo.title" filter="false"/></strong></td>
			</tr>
<%
		if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
%>	
			<tr><th class="Btmline">등록자</th>
				<td colspan="3"><bean:write name="InquireForm" property="vo.reg_nm"/></td>
			</tr>
			<tr><th class="Btmline">이메일</th>
				<td colspan="3"><bean:write name="InquireForm" property="vo.email"/></td>
			</tr>
			<tr><th class="Btmline">연락처</th>
				<td colspan="3"><bean:write name="InquireForm" property="vo.cell_number"/></td>
			</tr>
			<tr><th class="Btmline">공개여부</th>
				<td colspan="3"><bean:write name="InquireForm" property="vo.open_yn"/></td>
			</tr>
<%
		}
%>
		<tr><th class="Btmline">등록일</th>
				<td colspan="3"><bean:write name="InquireForm" property="vo.reg_dt"/></td>
			</tr>
			<tr><th class="Btmline">질의내용</th>
				<td colspan="3">
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
			<logic:equal name="InquireForm" property="vo.stat" value="Y">
			<tr><th class="Btmline1">답변내용</th>
				<td colspan="3"><bean:write name="InquireForm" property="vo.answerContents" filter="false"/>
					<br/><br/>
					<logic:notEmpty name="InquireForm" property="voList1">
					첨부파일 : 
						<logic:iterate name="InquireForm" property="voList1" indexId="rowNum1" id="attachVO">
							<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
						</logic:iterate>
					</logic:notEmpty>
    			</td>
			</tr>
			</logic:equal>
			<logic:equal name="InquireForm" property="vo.gov_answer" value="CHECK">
			<tr><th class="Btmline1">부처명</th>
				<td><bean:write name="InquireForm" property="vo.trans_nm"/>
				<th class="Btmline1">연락처</th>
				<td><bean:write name="InquireForm" property="vo.tel"/></td>
			</tr>
			</logic:equal>
		</table>
		<br/>
		<!-- start # 체크 -->	

	<logic:equal name="InquireForm" property="vo.stat" value="Y">
		<logic:equal name="InquireForm" property="satiVO.sati_reg_yn" value="N">
			<div class="App-Check">
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
						<td><html:radio name="InquireForm" property="satiVO.app_point" value="5" />매우만족 		<img src="/images/icon/App_Star5.gif" alt="매우만족" /></td>
						<td><html:radio name="InquireForm" property="satiVO.app_point" value="4" />만족 			<img src="/images/icon/App_Star4.gif" alt="만족" /></td>
						<td><html:radio name="InquireForm" property="satiVO.app_point" value="3" />보통 			<img src="/images/icon/App_Star3.gif" alt="보통" /></td>
						<td><html:radio name="InquireForm" property="satiVO.app_point" value="2" />불만족 			<img src="/images/icon/App_Star2.gif" alt="불만족" /></td>
						<td> <html:radio name="InquireForm" property="satiVO.app_point" value="1" />매우불만족 	<img src="/images/icon/App_Star1.gif" alt="매우불만족" /></td>
						<td><a href="JavaScript:goSatiInsert()" class="btn_IAppEx"><strong>확인</strong></a> </td>
					</tr>
					</table>
					</li>
				</ul>
			</div>
		</logic:equal>
		<logic:equal name="InquireForm" property="satiVO.sati_reg_yn" value="Y">
			<div class="App-Check-result">
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
		</logic:equal>
	</logic:equal>
			<div class="Basic-Button">
				<ul class="Center">				
<%
					if(uni.equals("uni")){
%>	
					<li><a href="JavaScript:goPrint('QNA','<bean:write name="InquireForm" property="vo.seq"/>');" class="btn_Basic"><strong>인쇄</strong></a>
					<a href="JavaScript:goScrap('QNA','<bean:write name="InquireForm" property="vo.seq"/>');" class="btn_Basic"><strong>스크랩하기</strong></a>
					<a href="JavaScript:history.back()" class="btn_Basic"><strong>목록</strong></a>
<%
					}else{
%>
					<li><a href="JavaScript:goPrint('QNA','<bean:write name="InquireForm" property="vo.seq"/>');" class="btn_Basic"><strong>인쇄</strong></a>
					<a href="JavaScript:goScrap('QNA','<bean:write name="InquireForm" property="vo.seq"/>');" class="btn_Basic"><strong>스크랩하기</strong></a>
					<a href="JavaScript:goInquireList()" class="btn_Basic"><strong>목록</strong></a>
<%
					}
%>


<%
				if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
%>						
					<a href="JavaScript:goUpdate()" class="btn_Basic"><strong>수정</strong></a>
					<a href="JavaScript:goDelete()" class="btn_Basic"><strong>삭제</strong></a>
					<logic:equal name="InquireForm" property="vo.stat" value="N">
						<a href="JavaScript:goAnswerInsert()" class="btn_Basic"><strong>답변달기</strong></a>					
						<logic:notEqual name="InquireForm" property="vo.public_trans_yn" value="Y">	
							<a href="JavaScript:goOrgTransForm('<bean:write name="InquireForm" property="searchVO.seq"/>')" class="btn_Basic"><strong>타기관지정</strong></a>
						</logic:notEqual>
					</logic:equal>
					<logic:notEqual name="InquireForm" property="vo.stat" value="N">
						<a href="JavaScript:goAnswerInsert()" class="btn_Basic"><strong>답변수정</strong></a>
					</logic:notEqual>
<%
				}else{
%>
					<logic:equal name="InquireForm" property="vo.up_del_stat" value="Y">
						<a href="JavaScript:goUpdate()" class="btn_Basic"><strong>수정</strong></a>
						<a href="JavaScript:goDelete()" class="btn_Basic"><strong>삭제</strong></a>
					</logic:equal>
<%
				}
%>
				</li>
				</ul>	
			</div>
			</html:form>
			
	</div>
</div>
<%@  include file="/include/bottom.jsp"%>