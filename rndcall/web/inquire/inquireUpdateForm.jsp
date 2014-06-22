<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%
String size = "70";
%>
<bean:define id="path" type="java.lang.String" value="/Inquire.do"/>
<script language="JavaScript" src="/js/file.js"></script>
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
	function goUpdate(){
		if(validate()){
			if(confirm("수정하시겠습니까?")){
				fm.elements["method"].value="getInquireUpdate";
				fm.elements["vo.file_id"].value = fm.elements["vo.question_file_id"].value;
				fm.submit();
			}
		}
	}
	
	function goCancel(){
		fm.elements["method"].value="getInquireView";
		fm.elements["searchVO.menu_sn"].value = "01";
		fm.submit();
	}
	
	function putEmailHost(){
		if(fm.elements["putEmail"].value != ""){		
			fm.elements["email1"].focus();
			fm.elements["email2"].readonly=true;
			fm.elements["email2"].value = fm.elements["putEmail"].value;
		}else{
			fm.elements["email1"].focus();
			fm.elements["email2"].value = "";
			fm.elements["email2"].readonly=false;
		}
	}
	
	function validate() {
		//SMS수신 체크시 핸드폰번호 체크
		var sms="";		
		for(i=0; i < fm.elements["vo.call_receive_yn"].length; i++){
     		if(fm.elements["vo.call_receive_yn"][i].checked){
     			sms = fm.elements["vo.call_receive_yn"][i].value;
    		}
   		}
		
		if(sms == "Y"){
			fm.elements["vo.cell_number"].value = fm.elements["cell_no1"].value+ "-" +fm.elements["cell_no2"].value+ "-" +fm.elements["cell_no3"].value;
			if (!isNotValidTel(fm.elements["vo.cell_number"])) return false;
		}		
		
		//EMAIL수신 체크시 EMAIL 체크
		var email="";		
		for(i=0; i < fm.elements["vo.email_receive_yn"].length; i++){
     		if(fm.elements["vo.email_receive_yn"][i].checked){
     			email = fm.elements["vo.email_receive_yn"][i].value;
    		}
   		}
   		
   		if(email == "Y"){
			fm.elements["vo.email"].value = fm.elements["email1"].value + "@" + fm.elements["email2"].value;
			if(!isEMailAddr(fm.elements["vo.email"])){
				return false;
			}
		}
		
		
		//제목 필수 입력 체크
		if (isRequired(fm.elements["vo.title"])){
			return false;
		}
		//질의내용 필수 입력 체크
		if (isRequired(fm.elements["vo.contents"])){
			return false;
		}
	
	 	return true;
	}
	
	function goInquireMainList(){
		fm.elements["method"].value="getInquireMainList";
		fm.submit();
	}
	
	function fncFileAddLenChk(fileObjName, size){
	   
	    var fileArea = document.getElementById(fileObjName);
	    var childNds = fileArea.childNodes;
	    
	    //alert(childNds.length);
	    //최대 3개까지 첨부 가능
	    if(childNds.length < 3){
	        fncFileAdd(fileObjName, size);
	    }else{
	    	alert("첨부파일은 3개까지 가능합니다.");
	    	return;
	    }
	}
	
	function downLoad(fileNM, saveFileNM, filePath, yn){
	    fileDownLoad.elements["fileNM"].value = fileNM;
	    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
	    fileDownLoad.elements["filePath"].value = filePath;
	    fileDownLoad.elements["desCipher"].value = yn;
	    fileDownLoad.submit();
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
		<html:form action="/Inquire" method="post" name="fm" enctype="multipart/form-data" type="kr.go.rndcall.mgnt.inquire.form.InquireForm">
		<html:hidden name="InquireForm" property="method" value="getInquireUpdate"/>
		<html:hidden name="InquireForm" property="vo.cell_number"/>
		<html:hidden name="InquireForm" property="vo.email"/>
		<html:hidden name="InquireForm" property="vo.question_file_id"/>
		<html:hidden name="InquireForm" property="vo.file_id"/>
		<html:hidden name="InquireForm" property="searchVO.loginId"/>
		<html:hidden name="InquireForm" property="searchVO.name"/>
		<html:hidden name="InquireForm" property="searchVO.seq"/>
		<html:hidden name="InquireForm" property="searchVO.board_type"/>
		<html:hidden name="InquireForm" property="searchVO.menu_sn"/>
		<!-- start # 문의하기 -->
		<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
		<colgroup>
			<col width="130px" />
			<col width="*" />
		</colgroup>
		<tr><th class="Btmline">SMS</th>
		    <td><html:radio name="InquireForm" property="vo.call_receive_yn" value="Y" > 수신 &nbsp;&nbsp;&nbsp;</html:radio>
				<html:radio name="InquireForm" property="vo.call_receive_yn" value="N" > 미수신 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html:radio>
				<bean:define name="InquireForm" property="vo.call_receive_yn" id="call_receive_yn" type="java.lang.String"/>
				<bean:define name="InquireForm" property="vo.cell_number" id="cell_number" type="java.lang.String"/>
<%
				String[] cell = null;
				if(call_receive_yn.equals("Y")){
					cell = cell_number.split("-");
				}else{
					cell = new String[3];
					cell[0]="";
					cell[1]="";
					cell[2]="";
				}
%>					
				<select name="cell_no1" id="cell_no1" title="핸드폰 앞자리 선택">
				   	<option value="">::: 선택 :::</option>
				  	<option value="010" <% if(cell[0].equals("010")){%> selected <% } %>>010</option>
					<option value="011" <% if(cell[0].equals("011")){%> selected <% } %>>011</option>
					<option value="016" <% if(cell[0].equals("016")){%> selected <% } %>>016</option>
					<option value="017" <% if(cell[0].equals("017")){%> selected <% } %>>017</option>
					<option value="018" <% if(cell[0].equals("018")){%> selected <% } %>>018</option>
					<option value="019" <% if(cell[0].equals("019")){%> selected <% } %>>019</option>
				<select>
				<input type="text" class="Num" title="핸드폰 중간자리 입력" name="cell_no2" value="<%=cell[1]%>" id="cell_no2" maxlength="4" onkeypress="return numeralsOnly(event)" /> -
				<input type="text" class="Num" title="핸드폰 끝자리 입력" name="cell_no3"  value="<%=cell[2]%>" id="cell_no3" maxlength="4" onkeypress="return numeralsOnly(event)" />
			</td>
		</tr>
		
		<tr><th class="Btmline">E-MAIL</th>
			<td><html:radio name="InquireForm" property="vo.email_receive_yn" value="Y"> 수신 &nbsp;&nbsp;&nbsp;</html:radio>
				<html:radio name="InquireForm" property="vo.email_receive_yn" value="N"> 미수신 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html:radio>
				<bean:define name="InquireForm" property="vo.email_receive_yn" id="email_receive_yn" type="java.lang.String"/>
				<bean:define name="InquireForm" property="vo.email" id="email" type="java.lang.String"/>
<%
				String[] email_addr = null;
				if(email_receive_yn.equals("Y")){
					email_addr = email.split("@");
				}else{
					email_addr = new String[2];
					email_addr[0]="";
					email_addr[1]="";
				}
%>	
				<input type="text" name="email1" class="Email" value="<%=email_addr[0]%>" />@<input type="text" name="email2" class="Email"  value="<%=email_addr[1]%>"/>
				<select name="putEmail" id="drEmailHost" onchange="putEmailHost();" class="sel1">
					<option value="">직접입력</option>
					<option value="naver.com" <% if(email_addr[1].equals("naver.com")){%> selected <% } %>>naver.com</option>
					<option value="hanmail.net"<% if(email_addr[1].equals("hanmail.net")){%> selected <% } %>>hanmail.net</option>
					<option value="nate.com"<% if(email_addr[1].equals("nate.com")){%> selected <% } %>>nate.com</option>
					<option value="gmail.com"<% if(email_addr[1].equals("gmail.com")){%> selected <% } %>>gmail.com</option>
					<option value="chol.com"<% if(email_addr[1].equals("chol.com")){%> selected <% } %>>chol.com</option>
					<option value="dreamwiz.com"<% if(email_addr[1].equals("dreamwiz.com")){%> selected <% } %>>dreamwiz.com</option>
					<option value="empal.com"<% if(email_addr[1].equals("empal.com")){%> selected <% } %>>empal.com</option>
					<option value="freechal.com"<% if(email_addr[1].equals("freechal.com")){%> selected <% } %>>freechal.com</option>
					<option value="hanmir.com"<% if(email_addr[1].equals("hanmir.com")){%> selected <% } %>>hanmir.com</option>
					<option value="netian.com"<% if(email_addr[1].equals("netian.com")){%> selected <% } %>>netian.com</option>
					<option value="hitel.net"<% if(email_addr[1].equals("hitel.net")){%> selected <% } %>>hitel.net</option>
					<option value="hotmail.com"<% if(email_addr[1].equals("hotmail.com")){%> selected <% } %>>hotmail.com</option>
					<option value="korea.com"<% if(email_addr[1].equals("korea.com")){%> selected <% } %>>korea.com</option>
					<option value="lycos.co.kr"<% if(email_addr[1].equals("lycos.co.kr")){%> selected <% } %>>lycos.co.kr</option>
					<option value="paran.com"<% if(email_addr[1].equals("paran.com")){%> selected <% } %>>paran.com</option>
					<option value="yahoo.com"<% if(email_addr[1].equals("yahoo.com")){%> selected <% } %>>yahoo.com</option>
					<option value="yahoo.co.kr"<% if(email_addr[1].equals("yahoo.co.kr")){%> selected <% } %>>yahoo.co.kr</option>
			</td>
		</tr>
		<tr><th class="Btmline">공개여부</th>
			<td><html:radio name="InquireForm" property="vo.open_yn" value="Y"> 공개 &nbsp;&nbsp;&nbsp;</html:radio>
				<html:radio name="InquireForm" property="vo.open_yn" value="N"> 비공개 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html:radio>
			</td>
		</tr>
		<tr><th class="Btmline">제목</th>
    		<td><html:text name="InquireForm" property="vo.title" size="90" alt="제목" title="제목"/></td>
    	</tr>
  		<tr><th class="Btmline">질의내용</th>
    		<td>
      			<html:textarea name="InquireForm" property="vo.contents" rows="20" cols="90"  alt="질의 내용" title="질의 내용"/>
    		</td>
    	</tr>
    	<logic:notEmpty name="InquireForm" property="voList">
			<tr>
				<th class="Btmline">기존파일 삭제여부</th>
				<td>
					<logic:iterate name="InquireForm" property="voList" indexId="rowNum" id="attachVO">						
						<bean:define name="attachVO" property="file_id" id="file_id" type="java.lang.String"/>
						<bean:define name="attachVO" property="seq" id="seq" type="java.lang.String"/>
<%
						String f_id = file_id+"-"+seq;
%>
						<input type="checkbox" name="file_del" value="<%=f_id%>"/>
						<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');" ><bean:write name="attachVO" property="file_nm" /></a><br/>
					</logic:iterate>
				</td>
			</tr>
		</logic:notEmpty>
		<tr><th class="Btmline">첨부파일</th>
			<td colspan="3">
				<a href="#" onclick="fncFileAddLenChk('fileArea', '<%=size%>')" class="btn_TLadd"><strong>파일추가</strong></a>
				<a href="#" onclick="fncFileDel('fileArea')" class="btn_TLdel"><strong>파일제거</strong></a>
				<div id="fileArea"></div>
			</td>
		</tr>
		</table>
		<div class="Basic-Button">
			<ul class="Right">
				<li><a href="JavaScript:goUpdate()" class="btn_Basic"><strong>수정하기</strong></a></li>
				<li><a href="JavaScript:goCancel()" class="btn_Basic"><strong>취소하기</strong></a></li>
			</ul>	
		</div>
		</html:form>
	</div>
<%@  include file="/include/bottom.jsp"%>