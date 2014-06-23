<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<bean:define name="MypageForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="MypageForm" property="vo.public_trans_yn" id="public_trans_yn" type="java.lang.String"/>
<bean:define name="MypageForm" property="vo.stat" id="stat" type="java.lang.String"/>
<bean:define id="path" type="java.lang.String" value="/Mypage.do"/>

<script type="text/javascript">
<!--		

function goList(){
	fm.elements["method"].value="getMypageList";
	fm.submit();
}

function downLoad(fileNM, saveFileNM, filePath, yn){
    fileDownLoad.elements["fileNM"].value = fileNM;
    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
    fileDownLoad.elements["filePath"].value = filePath;
    fileDownLoad.elements["desCipher"].value = yn;
    fileDownLoad.submit();
}

function goUpdate(){
	fm.elements["method"].value="getMypageUpdateForm";
	fm.submit();
}

function goDelete(){
	fm.elements["method"].value="getMypageDelete";
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
		<!-- start # LY-ContentTitle -->
		
		<!-- end # LY-ContentTitle -->
		<br />
		<!-- srart 검색 테이블 감싸기 -->
		<html:form action="/Mypage" method="post" name="fm" type="kr.go.rndcall.mgnt.mypage.form.MypageForm">
		<html:hidden name="MypageForm" property="method" value="getMypageView"/>
		<html:hidden name="MypageForm" property="searchVO.loginId"/>
		<html:hidden name="MypageForm" property="searchVO.name"/>
		<html:hidden name="MypageForm" property="searchVO.seq"/>
		<html:hidden name="MypageForm" property="searchVO.board_type"/>
		<html:hidden name="MypageForm" property="searchVO.type"/>
		<html:hidden name="MypageForm" property="searchVO.menu_sn"/>
		<div class="Basic-List-area">
			<ul class="List">
			<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
			<colgroup>
				<col width="130px" />
				<col width="*" />
			</colgroup>
			<tr>
				<th class="Btmline">제목</th>
				<td>
		   			<bean:define name="MypageForm" property="vo.board_type" id="board_type" type="java.lang.String"/>
<%
					if(board_type.equals("QNA")){
%>							
						<img src="/images/icon/icon_myinquiry.gif">
<%
					}else if(board_type.equals("OFFER")){
%>
						<img src="/images/icon/icon_myoffer.gif">
<%
					}
%>
					<bean:write name="MypageForm" property="vo.title" filter="false"/>
				</td>
			</tr>
			<tr>
				<th class="Btmline">등록일</th>
				<td><bean:write name="MypageForm" property="vo.reg_dt"/></td>
			</tr>
			<tr><th class="Btmline">질문내용</th>
				<td><bean:write name="MypageForm" property="vo.contents" filter="false"/>
					<br/><br/>
					<logic:notEmpty name="MypageForm" property="voList">
					첨부파일 : 
						<logic:iterate name="MypageForm" property="voList" indexId="rowNum" id="attachVO">
							<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
						</logic:iterate>
					</logic:notEmpty>
				</td>
			</tr>
<%
		if(stat.equals("Y")){
%>						
			<tr><th class="Btmline1">답변내용</th>
				<td><bean:write name="MypageForm" property="vo.answerContents" filter="false"/>
    			<br/><br/>
				<logic:notEmpty name="MypageForm" property="voList1">
				첨부파일 : 
					<logic:iterate name="MypageForm" property="voList1" indexId="rowNum1" id="attachVO1">
						<a href="javascript:downLoad('<bean:write name="attachVO1" property="file_nm"/>', '<bean:write name="attachVO1" property="file_path"/>', '', 'Y');"><bean:write name="attachVO1" property="file_nm" /></a><br/>
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
			<bean:define name="MypageForm" property="satiVO.sati_reg_yn" id="sati_reg_yn" type="java.lang.String"/>
			<bean:define name="MypageForm" property="searchVO.board_type" id="board_type" type="java.lang.String"/>
<%
	if(board_type.equals("QNA")){
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
					<li class="Mem">만족도 평가 참여인원 : <strong><bean:write name="MypageForm" property="satiVO.sati_reg_cnt"/>명</strong></li>
					<li class="Grade">평점 : <strong><bean:write name="MypageForm" property="satiVO.avg_app_point"/>점</strong></li>
				</ul>
				<!-- start # 체크 -->
				<ul class="Check">
					<li>
					<table border="0" cellspacing="0" cellpadding="0">		
					<tr>
						<td><html:radio name="MypageForm" property="satiVO.app_point" value="5" />매우만족 <img src="/images/icon/SApp_Star5.gif" alt="매우만족" /></td>
						<td><html:radio name="MypageForm" property="satiVO.app_point" value="4" />만족<img src="/images/icon/SApp_Star4.gif" alt="만족" /></td>
						<td><html:radio name="MypageForm" property="satiVO.app_point" value="3" />보통 <img src="/images/icon/SApp_Star3.gif" alt="보통" /></td>
						<td><html:radio name="MypageForm" property="satiVO.app_point" value="2" />불만족 <img src="/images/icon/SApp_Star2.gif" alt="불만족" /></td>
						<td> <html:radio name="MypageForm" property="satiVO.app_point" value="1" />매우불만족  <img src="/images/icon/SApp_Star1.gif" alt="매우불만족" /></td>
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
					<li class="Mem">만족도 평가 참여인원 : <strong><bean:write name="MypageForm" property="satiVO.sati_reg_cnt"/>명</strong></li>
					<li class="Grade">평점 : <strong><bean:write name="MypageForm" property="satiVO.avg_app_point"/>점</strong></li>
				</ul>
			</div>
<%
			}
		}
	}
%>
			<div class="Basic-Button">
				<ul class="Center">
					<li><a href="JavaScript:goList()" class="btn_Basic"><strong>목록</strong></a>


					<bean:define name="MypageForm" property="vo.up_del_stat" id="up_del_stat" type="java.lang.String"/>
					<bean:define name="MypageForm" property="vo.stat" id="stat" type="java.lang.String"/>
<%
					if(up_del_stat.equals("Y") && stat.equals("N")){
%>					
						<a href="JavaScript:goUpdate()" class="btn_Basic"><strong>수정</strong></a>
						<a href="JavaScript:goDelete()" class="btn_Basic"><strong>삭제</strong></a></li>
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