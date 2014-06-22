<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<bean:define name="FaqForm" property="voList" id="file_list" type="java.util.ArrayList"/>
<bean:define name="FaqForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String"/>
<bean:define name="FaqForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
<bean:define name="FaqForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
<bean:define name="FaqForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
<bean:define name="FaqForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="FaqForm" property="searchVO.loginId" id="loginId" type="java.lang.String"/>
<bean:define name="FaqForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define id="path" type="java.lang.String" value="/Faq.do"/>
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
<script language="JavaScript">
	function Save(){
	var del_file_id = "";
		if(validate()){
<%
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
%>			
	   		fm.elements["vo.del_file_id"].value=del_file_id;
			fm.elements["method"].value="faqUpdate";
			fm.submit();
		}
	}
	function fncFileAddLenChk(fileObjName, size){
	   
	    var fileArea = document.getElementById(fileObjName);
	    var childNds = fileArea.childNodes;
	    
	    //alert(childNds.length);
	    if(childNds.length < 10) //최대 3개까지 첨부 가능
	        fncFileAdd(fileObjName, size);
	}
	
	function goCancel(){
		document.location.href="/index.jsp";
	}
		function validate() {
		
		//제목 필수 입력 체크
		if (isRequired(fm.elements["vo.title"])){
			return false;
		}
		//질의내용 필수 입력 체크
		if (isRequired(fm.elements["vo.contents"])){
			return false;
		}
		//답변 필수 입력 체크
		if (isRequired(fm.elements["vo.answerContents"])){
			return false;
		}
	
	 	return true;
	}
</script>
</head>
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
		<html:form action="/Faq" method="post" name="fm" enctype="multipart/form-data" type="kr.go.rndcall.mgnt.faq.form.FaqForm" onsubmit="return checkOnSubmit(this)">
	    <html:hidden name="FaqForm" property="method" value="faqInsert"/>
		<html:hidden name="FaqForm" property="vo.cell_number"/>
		<html:hidden name="FaqForm" property="vo.email"/>
		<html:hidden name="FaqForm" property="searchVO.loginId"/>
		<html:hidden name="FaqForm" property="searchVO.name"/>
		<html:hidden name="FaqForm" property="searchVO.seq"/>
		<html:hidden name="FaqForm" property="searchVO.board_type"/>
		<html:hidden name="FaqForm" property="searchVO.searchCategory"/>
		<html:hidden name="FaqForm" property="searchVO.menu_sn"/>
		<html:hidden name="FaqForm" property="vo.del_file_id"/>
		<html:hidden name="FaqForm" property="vo.file_id"/>
		
		<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
		<colgroup>
			<col width="100px" />
			<col width="*" />
		</colgroup>

		<tr>
			<th class="Btmline">제목</th>
			<td>
				<html:text name="FaqForm" property="vo.title" size="85" alt="제목" title="제목"/>
			</td>
		</tr>
		<tr>
			<th class="Btmline">유권해석</th>
				<td>
					<html:checkbox name="FaqForm" property="vo.analysis_yn" title="유권해석" value="Y"/> (유권해석인 경우 체크)
				</td>
		</tr>
		<tr>
			<th class="Btmline">질의내용</th>
			<td><html:textarea name="FaqForm" property="vo.contents" rows="10" cols="50"  alt="질의 내용" title="질의 내용"/></td>
		</tr>
		<tr>
			<th class="Btmline">답변내용</th>
			<td><html:textarea name="FaqForm" property="vo.answerContents" rows="10" cols="50"  alt="답변내용" title="답변내용"/></td>
		</tr>
		<logic:notEmpty name="FaqForm" property="voList">
		<tr>
			<th class="Btmline">파일 삭제여부</th>
			<td>
			<logic:iterate name="FaqForm" property="voList" indexId="rowNum" id="attachVO">						
				<bean:define name="attachVO" property="file_id" id="file_id" type="java.lang.String"/>
					<bean:define name="attachVO" property="seq" id="seq" type="java.lang.String"/>
<%
						String f_id = file_id+"-"+seq;
%>
					<input type="checkbox" name="file_del" value="<%=f_id%>"/><a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><img src="/images/icon/disk01.gif" alt="첨부파일"/><bean:write name="attachVO" property="file_nm" /></a><br/>
			</logic:iterate>
			</td>
		</tr>
		</logic:notEmpty>
		<tr>
			<th class="Btmline">첨부파일</th>
			<td colspan="3">
				<a href="#" onclick="fncFileAddLenChk('fileArea', '<%=size%>')" class="btn_TLadd"><strong>파일추가</strong></a>
				<a href="#" onclick="fncFileDel('fileArea')" class="btn_TLdel"><strong>파일제거</strong></a>
				<div id="fileArea"></div>
			</td>
		</tr>
	</table>
	<div style="margin:10px 0; display:block">
		<ul class="Center">
			<div class="Basic-Button">
				<ul class="Center">
					<li><a href="JavaScript:Save()"  class="btn_Basic"><strong>저장</strong></a>
					<a href="JavaScript:history.back()" class="btn_Basic"><strong>취소</strong></a></li>
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
</div>
<%@  include file="/include/bottom.jsp"%>