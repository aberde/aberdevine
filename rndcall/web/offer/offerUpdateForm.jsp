<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<%@ include file="/include/top.jsp"%>
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
	}
%>
<script type="text/javascript">
<!--		
	function offerUpdate(){
		if(validate()){
			fm.elements["method"].value="offerUpdate";
			fm.elements["vo.file_id"].value = fm.elements["vo.question_file_id"].value;
			fm.submit();
		}
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
	
	function offerUpdateCancle(){
		document.location.href="/index.jsp";
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
//-->
</script>
<div class="LY-Container">
	
	<!-- end # 레프트 메뉴 -->
	<html:form action="/Offer" method="post" name="fm" enctype="multipart/form-data" type="kr.go.rndcall.mgnt.offer.form.OfferForm">
	<html:hidden name="OfferForm" property="method" value="offerUpdate"/>
	<html:hidden name="OfferForm" property="vo.cell_number"/>
	<html:hidden name="OfferForm" property="vo.email"/>
	<html:hidden name="OfferForm" property="vo.file_id"/>
	<html:hidden name="OfferForm" property="vo.question_file_id"/>
	<html:hidden name="OfferForm" property="searchVO.loginId"/>
	<html:hidden name="OfferForm" property="searchVO.name"/>
	<html:hidden name="OfferForm" property="searchVO.seq"/>
	<html:hidden name="OfferForm" property="searchVO.board_type"/>
	<html:hidden name="OfferForm" property="searchVO.menu_sn"/>
		<div class="LY-ContentTitle">
			<h1><img src="/images/content/Content_Title06_1.gif" alt="제안하기 - 가장 많이 묻는 질의응답을 카테고리별로 보실 수 있습니다." /></h1>
		</div>&nbsp;&nbsp;&nbsp;
	<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
		<colgroup>
			<col width="130px" />
			<col width="*" />
		</colgroup>
		<tr>
			<th class="Btmline">SMS</th>
			<td><html:radio name="OfferForm" property="vo.call_receive_yn" value="Y" >수신</html:radio>
				<html:radio name="OfferForm" property="vo.call_receive_yn" value="N" >미수신</html:radio>
				<bean:define name="OfferForm" property="vo.call_receive_yn" id="call_receive_yn" type="java.lang.String"/>
				<bean:define name="OfferForm" property="vo.cell_number" id="cell_number" type="java.lang.String"/>
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
		<tr>
			<th class="Btmline">EMAIL</th>
			<td><html:radio name="OfferForm" property="vo.email_receive_yn" value="Y">수신</html:radio>
				<html:radio name="OfferForm" property="vo.email_receive_yn" value="N">미수신</html:radio>
				<bean:define name="OfferForm" property="vo.email_receive_yn" id="email_receive_yn" type="java.lang.String"/>
				<bean:define name="OfferForm" property="vo.email" id="email" type="java.lang.String"/>
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
				<input type="text" name="email1" class="Out_lineY W20" value="<%=email_addr[0]%>" />@<input type="text" name="email2" class="Out_lineY W20"  value="<%=email_addr[1]%>"/>
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
				</select>
			</td>
		</tr>
		<tr>
			<th class="Btmline">공개여부</th>
			<td><html:radio name="OfferForm" property="vo.open_yn" value="Y">공개</html:radio>
				<html:radio name="OfferForm" property="vo.open_yn" value="N">비공개</html:radio>
			</td>
		</tr>
		<tr>
			<th class="Btmline">제목</th>
			<td>
				<html:text name="OfferForm" property="vo.title" size="90" alt="제목" title="제목"/>
			</td>
		</tr>
		<tr>
			<th class="Btmline">질의 내용</th>
			<td><html:textarea name="OfferForm" property="vo.contents" rows="20" cols="90"  alt="질의 내용" title="질의 내용"/></td>
		</tr>
		  	<logic:notEmpty name="OfferForm" property="voList">
			<tr>
				<th class="Btmline">기존파일 삭제여부</th>
				<td>
					<logic:iterate name="OfferForm" property="voList" indexId="rowNum" id="attachVO">						
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
	<div style="margin:10px 0; display:block">
	<ul class="Center">
		<div class="Basic-Button">
		<ul class="Center">
			<li><a href="JavaScript:offerUpdate()"  class="btn_Basic"><strong>수정</strong></a>
			<a href="JavaScript:history.back()" class="btn_Basic"><strong>취소</strong></a></li>
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