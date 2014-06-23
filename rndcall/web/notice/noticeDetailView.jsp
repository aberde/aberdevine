<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<bean:define name="NoticeForm" property="searchVO" id="searchVO" type="kr.go.rndcall.mgnt.notice.vo.NoticeSearchVO"/>
<bean:define name="NoticeForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="NoticeForm" property="searchVO.loginId" id="loginId" type="java.lang.String"/>
<bean:define name="NoticeForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String"/>
<bean:define id="path" type="java.lang.String" value="/Notice.do"/>
<%
	String uni = searchVO.getUni();
%>
<script type="text/javascript">
<!--		

function goNoticeList(){
	fm.action = "/switch.do?prefix=/notice&method=noticeList&page=/Notice.do?pager.offset=" + fm.elements["searchVO.pagerOffset"].value;
	fm.submit();
}

function downLoad(fileNM, saveFileNM, filePath, yn){
    fileDownLoad.elements["fileNM"].value = fileNM;
    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
    fileDownLoad.elements["filePath"].value = filePath;
    fileDownLoad.elements["desCipher"].value = yn;
    fileDownLoad.submit();
}


function goNoticeUpdateForm(){
	fm.elements["method"].value="noticeUpdateForm";
	fm.submit();
}
function goNoticeDelete(){
	if (confirm("정말로 삭제 하시겠습니까?")) {
		fm.elements["method"].value="noticeDelete";
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
	<html:form action="/Notice" method="post" name="fm" type="kr.go.rndcall.mgnt.notice.form.NoticeForm">
	<html:hidden name="NoticeForm" property="method" value="getInquireInsert"/>
	<html:hidden name="NoticeForm" property="searchVO.loginId"/>
	<html:hidden name="NoticeForm" property="searchVO.name"/>
	<html:hidden name="NoticeForm" property="searchVO.seq"/>
	<html:hidden name="NoticeForm" property="searchVO.board_type"/>
	<html:hidden name="NoticeForm" property="searchVO.menu_sn"/>
	<html:hidden name="NoticeForm" property="searchVO.uni"/>
	<html:hidden name="NoticeForm" property="searchVO.pagerOffset"/>
	<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
			<colgroup>
				<col width="100px" />
				<col width="*" />
			</colgroup>
			<tr>
				<th class="Btmline">제목</th>
				<td><strong><bean:write name="NoticeForm" property="vo.title" filter="false"/></strong></td>
			</tr>
			<tr>
				<th class="Btmline">작성자</th>
				<td><bean:write name="NoticeForm" property="vo.reg_nm"/></td>
			</tr>
			<tr>
				<th class="Btmline">내용</th>
				<td><bean:write name="NoticeForm" property="vo.contents" filter="false"/>
				<br/><br/>
				<logic:notEmpty name="NoticeForm" property="voList">
				첨부파일 : 
					<logic:iterate name="NoticeForm" property="voList" indexId="rowNum" id="attachVO">
						<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');" ><bean:write name="attachVO" property="file_nm" /></a></a><br/>
					</logic:iterate>
				</logic:notEmpty>
			</tr>
		</table>
		<!-- end .table-view -->
		
		<div style="margin:10px 0; display:block">
		<ul class="Center">
			<div class="Basic-Button">
			<ul class="Center">
				<%
				if(uni.equals("uni")){
				%>
				<li><a href="JavaScript:history.back()" class="btn_Basic"><strong>목록</strong></a></li>
			   	<%
				}else{
			   	%>
				<li><a href="JavaScript:goNoticeList()"  class="btn_Basic"><strong>목록</strong></a>
				<%
				}
				%>
				<%
				if(roleCd.equals("0000Z") || roleCd.equals("0000C")){
				%>
				<a href="JavaScript:goNoticeUpdateForm()" class="btn_Basic"><strong>수정</strong></a>
				<a href="JavaScript:goNoticeDelete()" class="btn_Basic"><strong>삭제</strong></a></li>
				<% 
				}
				%>
			</ul>	
			</div>
		</div>
		
	</html:form>
	<!-- end 검색 테이블 감싸기 -->
	<br />
	<br />
	<!-- end .ntiscontents -->
	</div>
<!-- end .container -->
</div>
<%@  include file="/include/bottom.jsp"%>