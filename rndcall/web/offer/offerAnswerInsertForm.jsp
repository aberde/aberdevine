<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<%@ include file="/include/top.jsp"%>

<bean:define name="OfferForm" property="vo.category1" id="category1" type="java.lang.String"/>
<bean:define name="OfferForm" property="vo.category2" id="category2" type="java.lang.String"/>
<bean:define name="OfferForm" property="vo.stat" id="stat" type="java.lang.String"/>
<bean:define name="OfferForm" property="voList1" id="file_list" type="java.util.ArrayList"/>
<bean:define id="path" type="java.lang.String" value="/Offer.do"/>
<script language="JavaScript" src="/js/file.js"></script>
<%  String size = "70"; // default size
    if(!Util.isNull(request.getParameter("size"))) {
    	size = request.getParameter("size");
    }		
%>
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
	function goAnswerCreate(){
		var del_file_id = "";
		if(validate()){
<%
			if(!stat.equals("N")){
				if(file_list.size() > 0){
%>  		
					if(document.fm.file_del.value == undefined){
						for(i=0; i < fm.file_del.length; i++){
				     		if(fm.file_del[i].checked){
				     			del_file_id += fm.file_del[i].value+",";
				    		}
				   		}
				   		del_file_id = del_file_id.substr(0,(del_file_id.length-1));
					}else{
						if(fm.file_del.checked){
				     		del_file_id = fm.file_del.value;
				    	}
					}
<%
				}
			}
%>			
   			fm.elements["vo.del_file_id"].value=del_file_id;
<%
			if(stat.equals("N")){
%>   			
				fm.elements["method"].value="offerAnswerInsert";
<%
			}else{
%>
				fm.elements["method"].value="offerAnswerUpdate";
				fm.elements["vo.file_id"].value = fm.elements["vo.answer_file_id"].value;
<%
			}
%>			
			fm.submit();
		}
	}
	
	function goCancel(){
		document.location.href="/index.jsp";
	}
	
	function validate() {
		//질의내용 필수 입력 체크
		if (isRequired(fm.elements["vo.answerContents"])){
			return false;
		}
	 	return true;
	}
	
	
	function fncFileAddLenChk(fileObjName, size){
	   
	    var fileArea = document.getElementById(fileObjName);
	    var childNds = fileArea.childNodes;
	    
	    //alert(childNds.length);
	    if(childNds.length < 3) //최대 3개까지 첨부 가능
	        fncFileAdd(fileObjName, size);
	}
	


window.onload= function() { // onload 시 호출. 데이터 초기화.
}

//-->
</script>
<div class="LY-Container">
	
	<!-- end # 레프트 메뉴 -->
	<html:form action="/Offer" method="post" name="fm" enctype="multipart/form-data" type="kr.go.rndcall.mgnt.offer.form.OfferForm" onsubmit="return checkOnSubmit(this)">
	<html:hidden name="OfferForm" property="method" value="getAnswerInsert"/>
	<html:hidden name="OfferForm" property="vo.cell_number"/>
	<html:hidden name="OfferForm" property="vo.email"/>
	<html:hidden name="OfferForm" property="searchVO.loginId"/>
	<html:hidden name="OfferForm" property="searchVO.name"/>
	<html:hidden name="OfferForm" property="searchVO.seq"/>
	<html:hidden name="OfferForm" property="searchVO.board_type"/>
	<html:hidden name="OfferForm" property="vo.del_file_id"/>
	<html:hidden name="OfferForm" property="vo.answer_seq"/>
	<html:hidden name="OfferForm" property="vo.title"/>
	<html:hidden name="OfferForm" property="vo.file_id"/>
	<html:hidden name="OfferForm" property="vo.answer_file_id"/>
	<html:hidden name="OfferForm" property="searchVO.menu_sn"/>
		<div class="LY-ContentTitle">
			<h1><img src="/images/content/Content_Title06_1.gif" alt="제안하기 - 가장 많이 묻는 질의응답을 카테고리별로 보실 수 있습니다." /></h1>
		</div>&nbsp;&nbsp;&nbsp;
	<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
			<tr>
				<th class="Btmline">제목</th>
				<td colspan="3"><bean:write name="OfferForm" property="vo.title" filter="false"/></td>
			</tr>
			<tr>
				<th class="Btmline">이름</th>
				<td><bean:write name="OfferForm" property="vo.reg_nm"/></td>
				<th class="Btmline">상태</th>
				<td>
					<bean:define name="OfferForm" property="vo.stat" id="stat" type="java.lang.String"/>
<%
					if(stat.equals("Y")){
						out.print("완료");
					}else{
						out.print("처리중");
					}
%>
				</td>
			</tr>
			<tr>
				<th class="Btmline">휴대폰번호</th>
				<td>
					<bean:define name="OfferForm" property="vo.call_receive_yn" id="call_receive_yn" type="java.lang.String"/>
					<bean:define name="OfferForm" property="vo.cell_number" id="cell_number" type="java.lang.String"/>
<%
					if(call_receive_yn.equals("Y")){
						out.println("[수신] " +cell_number);
					}else{
						out.println("[미수신]");
					}
%>
				</td>
				<th class="Btmline">EMAIL</th>
				<td>
					<bean:define name="OfferForm" property="vo.email_receive_yn" id="email_receive_yn" type="java.lang.String"/>
					<bean:define name="OfferForm" property="vo.email" id="email" type="java.lang.String"/>
<%
					if(email_receive_yn.equals("Y")){
						out.println("[수신] " +email);
					}else{
						out.println("[미수신]");
					}
%>
				</td>
			</tr>
			<tr>
				<th class="Btmline">조회수</th>
				<td><bean:write name="OfferForm" property="vo.read_count"/></td>
				<th class="Btmline">공개여부</th>
				<td>
					<bean:define name="OfferForm" property="vo.open_yn" id="open_yn" type="java.lang.String"/>
<%
					if(open_yn.equals("Y")){
						out.println("공개");
					}else{
						out.println("비공개");
					}
%>					
				</td>
			</tr>
			<tr>
				<th class="Btmline">등록일</th>
				<td colspan="3"><bean:write name="OfferForm" property="vo.reg_dt"/></td>

			</tr>			
			<tr>
				<th class="Btmline">질의 내용</th>
				<td colspan="3"><bean:write name="OfferForm" property="vo.contents" filter="false"/></td>
			</tr>
			<tr>
				<th class="Btmline1">답변 내용</th>
				<td colspan="3"><html:textarea name="OfferForm" property="vo.answerContents" rows="15" cols="90"  alt="답변 내용" title="답변 내용"/></td>
			</tr>
			<logic:notEmpty name="OfferForm" property="voList1">
			<tr>
				<th class="Btmline1">기존파일 삭제여부</th>
				<td>
					<logic:iterate name="OfferForm" property="voList1" indexId="rowNum" id="attachVO">						
						<bean:define name="attachVO" property="file_id" id="file_id" type="java.lang.String"/>
						<bean:define name="attachVO" property="seq" id="seq" type="java.lang.String"/>
<%
						String f_id = file_id+"-"+seq;
%>
						<input type="checkbox" name="file_del" value="<%=f_id%>"/>
						<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><img src="/images/icon/disk01.gif" alt="첨부파일"/><bean:write name="attachVO" property="file_nm" /></a><br/>
					</logic:iterate>
				</td>
			</tr>
			</logic:notEmpty>
			<tr>
				<th class="Btmline1">첨부파일</th>
				<td colspan="3">
					<a href="#" onclick="fncFileAddLenChk('fileArea', '<%=size%>')" class="btn_TLadd"><strong>파일추가</strong></a>
					<a href="#" onclick="fncFileDel('fileArea')" class="btn_TLdel"><strong>파일제거</strong></a>
					<div id="fileArea"></div>
				</td>
			</tr>
			</table>
	<div style="margin:10px 0; display:block">
		<div class="Basic-Button">
		<ul class="Center">
			<li><a href="JavaScript:goAnswerCreate()"  class="btn_Basic"><strong>답변등록</strong></a><a href="JavaScript:history.back()" class="btn_Basic"><strong>취소</strong></a></li>
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
<%@  include file="/include/bottom.jsp"%>