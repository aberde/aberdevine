<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
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
<script type="text/JavaScript">
	function fncFileAddLenChk(fileObjName, size){
	   
	    var fileArea = document.getElementById(fileObjName);
	    var childNds = fileArea.childNodes;
	    
	    //alert(childNds.length);
	    if(childNds.length < 3) //최대 3개까지 첨부 가능
	        fncFileAdd(fileObjName, size);
	}
	function Save(){ 
		if(validate()){
			fm.elements["method"].value="offerInsert";
			fm.elements["searchVO.type"].value="03";
			fm.submit();
			alert("마이페이지에서 등록한 글에대한 정보를 확인할수 있습니다.");
		}
	}
	
	function goCancel(){
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
	

</script>
</head>
	<div class="LY-Container">
	
	<html:form action="/Offer" method="post" name="fm" type="kr.go.rndcall.mgnt.offer.form.OfferForm" enctype="multipart/form-data" onsubmit="return checkOnSubmit(this)">
    <html:hidden name="OfferForm" property="method" value="offerInsert"/>
	<html:hidden name="OfferForm" property="vo.cell_number"/>
	<html:hidden name="OfferForm" property="vo.email"/>
	<html:hidden name="OfferForm" property="searchVO.loginId"/>
	<html:hidden name="OfferForm" property="searchVO.name"/>
	<html:hidden name="OfferForm" property="searchVO.menu_sn"/>
	<html:hidden name="OfferForm" property="searchVO.type"/>
<!-- 	<div class="LY-Content"> -->
		<div class="LY-ContentTitle">
			<h1><img src="/images/content/Content_Title06_1.gif" alt="제안하기 - 가장 많이 묻는 질의응답을 카테고리별로 보실 수 있습니다." /></h1>
		</div>&nbsp;&nbsp;&nbsp;
	<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
	<colgroup>
		<col width="130px" />
		<col width="*" />
	</colgroup>
		<tr>
			<th class="Btmline">제목</th>
			<td>
				<input type="text" name="vo.title" class="Out_lineY W90" size="50" title="제목"/>
			</td>
		</tr>
		<tr>
			<th rowspan="2" class="Btmline">수신방법</th>
			<td><html:radio name="OfferForm" property="vo.call_receive_yn" value="Y" disabled="true">수신</html:radio>
				<html:radio name="OfferForm" property="vo.call_receive_yn" value="N" disabled="true">미수신</html:radio>
				<select name="cell_no1" id="cell_no1" title="핸드폰 앞자리 선택">
				   	<option value="">::: 선택 :::</option>
				  	<option value="010">010</option>
					<option value="011">011</option>
					<option value="016">016</option>
					<option value="017">017</option>
					<option value="018">018</option>
					<option value="019">019</option>
				</select>
				<input type="text" class="Out_lineY W10" title="핸드폰 중간자리 입력" name="cell_no2" id="cell_no2" maxlength="4" onkeypress="return numeralsOnly(event)" /> -
				<input type="text" class="Out_lineY W10" title="핸드폰 끝자리 입력" name="cell_no3" id="cell_no3" maxlength="4" onkeypress="return numeralsOnly(event)" />
			</td>
		</tr>
		<tr>
			<td><html:radio name="OfferForm" property="vo.email_receive_yn" value="Y" disabled="true">수신</html:radio>
				<html:radio name="OfferForm" property="vo.email_receive_yn" value="N"disabled="true">미수신</html:radio>
				<input type="text" name="email1" class="Out_lineY W20" />@<input type="text" name="email2" class="Out_lineY W20" />
				<select name="putEmail" id="drEmailHost" onchange="putEmailHost();" class="sel1">
					<option value="" selected>직접입력</option>
					<option value="naver.com">naver.com</option>
					<option value="hanmail.net">hanmail.net</option>
					<option value="nate.com">nate.com</option>
					<option value="gmail.com">gmail.com</option>
					<option value="chol.com">chol.com</option>
					<option value="dreamwiz.com">dreamwiz.com</option>
					<option value="empal.com">empal.com</option>
					<option value="freechal.com">freechal.com</option>
					<option value="hanmir.com">hanmir.com</option>
					<option value="netian.com">netian.com</option>
					<option value="hitel.net">hitel.net</option>
					<option value="hotmail.com">hotmail.com</option>
					<option value="korea.com">korea.com</option>
					<option value="lycos.co.kr">lycos.co.kr</option>
					<option value="paran.com">paran.com</option>
					<option value="yahoo.com">yahoo.com</option>
					<option value="yahoo.co.kr">yahoo.co.kr</option>
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
			<th class="Btmline">내용</th>
			<td><textarea name="vo.contents" rows="9" cols="40" title="자주하는질문 내용" ></textarea></td>
		</tr>
		<tr>
			<th class="Btmline">첨부<br>파일</th>
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
					<li><a href="JavaScript:Save()"  class="btn_Basic"><strong>등록</strong></a>
					<a href="JavaScript:history.back()" class="btn_Basic"><strong>취소</strong></a></li>
				</ul>	
			</div>
		</ul>
	</div>	
	<!-- </div>-->
	</html:form>
	<!-- end 검색 테이블 감싸기 -->
	<br />
	<br />
	<!-- end .ntiscontents -->
	</div>
<!-- end .container -->
</div>
<%@  include file="/include/bottom.jsp"%>