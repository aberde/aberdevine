<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.inquire.vo.*" %>

<bean:define name="InquireForm" property="vo.category1" id="category1" type="java.lang.String"/>
<bean:define name="InquireForm" property="vo.category2" id="category2" type="java.lang.String"/>
<bean:define name="InquireForm" property="vo.stat" id="stat" type="java.lang.String"/>
<bean:define name="InquireForm" property="voList" id="q_file_list" type="java.util.ArrayList"/>
<bean:define name="InquireForm" property="voList1" id="a_file_list" type="java.util.ArrayList"/>

<bean:define name="InquireForm" property="voList2" id="cateL" type="java.util.ArrayList"/>

<bean:define id="path" type="java.lang.String" value="/Inquire.do"/>
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
	function goAnswerCreate(){
		var del_file_id = "";
		if(validate()){
<%
			if(!stat.equals("N")){
				if(a_file_list.size() > 0){
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
   			if(confirm("답변을 등록하시겠습니까?")){
<%
				if(stat.equals("N")){
%>   			
					fm.elements["method"].value="getAnswerInsert";
<%
				}else{
%>
					fm.elements["method"].value="getAnswerUpdate";
					fm.elements["vo.file_id"].value = fm.elements["vo.answer_file_id"].value;
<%
				}
%>			
				fm.submit();
			}
		}
	}
	
	function goCancel(){
//		document.location.href="/index.jsp";
		fm.elements["method"].value="getInquireView";
		fm.elements["searchVO.menu_sn"].value = "01";
		fm.submit();
	}
	
	function validate() {
				
		if(isRequired(fm.elements["vo.category1"])){
			return false;
		}
		if(fm.elements["vo.category2"].options.length > 1){
			if(isRequired(fm.elements["vo.category2"])){
				return false;
			}
		}
		
		//질의내용 필수 입력 체크
		if (isRequired(fm.elements["vo.answerContents"])){
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
	    if(childNds.length < 3) //최대 3개까지 첨부 가능
	        fncFileAdd(fileObjName, size);
	}
	
	// 중분류 자바스크립트 객체를 저장할 배열변수
var categoryL = new Array();
var categoryM = new Array();
// 자바스크립트 사용자정의 객체
function category_obejct(codeName, codeValue, parentCode) {
    this.codeName = codeName;
    this.codeValue = codeValue;
    this.parentCode = parentCode;
}


window.onload= function() { // onload 시 호출. 데이터 초기화.
    <logic:iterate name="InquireForm" property="voList2" id="mCode" indexId="comRowNm">
    categoryL[<%=comRowNm.intValue()%>] = new category_obejct("<bean:write name="mCode" property="code_nm" />", "<bean:write name="mCode" property="code" />", "<bean:write name="mCode" property="subjectNo" />");
    </logic:iterate>
    <logic:iterate name="InquireForm" property="voList3" id="mCode" indexId="comRowNm">
    categoryM[<%=comRowNm.intValue()%>] = new category_obejct("<bean:write name="mCode" property="code_nm" />", "<bean:write name="mCode" property="code" />", "<bean:write name="mCode" property="p_code" />");
    </logic:iterate>
    
    //f_cate_change2(fmDetail.elements["searchVO.bz_id"].value);
    f_cate_change2();
    f_cate_change(<%=category1%>);
}

// 대분류 셀렉트박스의 onChange 이벤트에 설정.

//function f_cate_change2(value) { // 대분류 값 가져옴. 중분류 객체의 parentCode 에 해당하는 값.
function f_cate_change2() { // 대분류 값 가져옴. 중분류 객체의 parentCode 에 해당하는 값.
  	// 모든 option 제거.
   	var cateL = document.getElementById("category1"); //중분류 select 박스 객체
   	var opts = cateL.options; // select 박스의 모든 option 을 가져옴.
   	while(opts.length>1) { // 최초 == 선택 == 이라고 된 부분 제외하고 모두 삭제
   		opts[1]=null;
   	}
   	
  	var idx = opts.length; // 남은 option 갯수. 여기선 당연히 1 이겠지만 다른곳에서 응용을 위해..
  	for(var i=0; i<categoryL.length; i++) { // 중분류객체들 모두 조사      	
		if(categoryL[i].codeValue =="<%=category1%>"){
			cateL[idx++] = new Option(categoryL[i].codeName, categoryL[i].codeValue,true,true);
		}else{
			cateL[idx++] = new Option(categoryL[i].codeName, categoryL[i].codeValue);
		}
  	} // for끝
} // function f_cate_change 끝

function f_cate_change(value) { // 대분류 값 가져옴. 중분류 객체의 parentCode 에 해당하는 값.
  	// 모든 option 제거.
   	var cateM = document.getElementById("category2"); //중분류 select 박스 객체
   	var opts = cateM.options; // select 박스의 모든 option 을 가져옴.
   	while(opts.length>1) { // 최초 == 선택 == 이라고 된 부분 제외하고 모두 삭제
   		opts[1]=null;
   	}

  	var idx = opts.length; // 남은 option 갯수. 여기선 당연히 1 이겠지만 다른곳에서 응용을 위해..
  	for(var i=0; i<categoryM.length; i++) { // 중분류객체들 모두 조사
    	if(categoryM[i].parentCode == value) { // 중분류객체의 parentCode 와 대분류값 비교.. 같다면..
      	// option 생성하여 현재 객체의 codeName, codeValue 추가.
	      	if(categoryM[i].codeValue =="<%=category2%>"){
				cateM[idx++] = new Option(categoryM[i].codeName, categoryM[i].codeValue,true,true);
			}else{
				cateM[idx++] = new Option(categoryM[i].codeName, categoryM[i].codeValue); 
			}
    	} // if끝
  	} // for끝
} // function f_cate_change 끝

	function downLoad(fileNM, saveFileNM, filePath, yn){
	    fileDownLoad.elements["fileNM"].value = fileNM;
	    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
	    fileDownLoad.elements["filePath"].value = filePath;
	    fileDownLoad.elements["desCipher"].value = yn;
	    fileDownLoad.submit();
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
<%
		if(!mainRoleCD.equals("0000Z") && !mainRoleCD.equals("0000C")){
%>		
			<div class="basic_Teb_btn">
				<ul class="basic_PageTeb_Dw">
					<li class="Teb_Dw_L"></li>
					<li class="Teb_Dw_M"><a href="JavaScript:goCate('')" class="All">전체</a></li>
					<li class="Teb_Dw_R"></li>
				</ul>
				<bean:define name="InquireForm" property="searchVO.searchCategory" id="choice_ct" type="java.lang.String"/>				
<%
					InquireVO cate = null;
					String choice = "Dw";
					for(int i=0; i< cateL.size(); i++){
						cate = (InquireVO)cateL.get(i);
						System.out.println("cate.getCode()::"+cate.getCode());
						if(cate.getCode().equals(choice_ct)){
							choice = "Up";
						}else{
							choice = "Dw";
						}
						
						if(!cate.getCode_nm().equals("유권해석")){
%>
						<ul class="basic_PageTeb_<%=choice%>">
							
<%
							if(choice.equals("Up")){
%>							
							<li class="Teb_Up_L"></li>
							<li class="Teb_Up_M"><%=cate.getCode_nm()%></li>
							<li class="Teb_Up_R"></li>
<%
							}else{
%>
							<li class="Teb_Dw_L"></li>
							<li class="Teb_Dw_M"><a href="JavaScript:goCate('<%=cate.getCode()%>')"><%=cate.getCode_nm()%></a></li>
							<li class="Teb_Dw_R"></li>
<%
							}
%>
						</ul>
<%
						}					
						choice = "Dw";
					}
%>			
			</div>
<%
		}
%>
			<!-- end 도움말 외각 라인 -->
			<!-- srart 검색 테이블 감싸기 -->
			<html:form action="/Inquire" method="post" name="fm" enctype="multipart/form-data" type="kr.go.rndcall.mgnt.inquire.form.InquireForm" onsubmit="return checkOnSubmit(this)">
			<html:hidden name="InquireForm" property="method" value="getAnswerInsert"/>
			<html:hidden name="InquireForm" property="vo.cell_number"/>
			<html:hidden name="InquireForm" property="vo.email"/>
			<html:hidden name="InquireForm" property="searchVO.loginId"/>
			<html:hidden name="InquireForm" property="searchVO.name"/>
			<html:hidden name="InquireForm" property="searchVO.seq"/>
			<html:hidden name="InquireForm" property="searchVO.board_type"/>
			<html:hidden name="InquireForm" property="vo.del_file_id"/>
			<html:hidden name="InquireForm" property="vo.answer_seq"/>
			<html:hidden name="InquireForm" property="vo.title"/>
			<html:hidden name="InquireForm" property="vo.file_id"/>				
			<html:hidden name="InquireForm" property="vo.answer_file_id"/>				
			<html:hidden name="InquireForm" property="searchVO.menu_sn"/>			
			<!-- start # 상세보기 -->
			<img src="../images/Menu/OnLine_Text03.gif" alt="질의 및 답변내용" />
			<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
			<colgroup>
				<col width="120px" />
				<col width="350px" />
				<col width="120px" />
				<col width="*" />
			</colgroup>
			<tr><th class="Btmline">제목</th>
				<td colspan="3"><strong><bean:write name="InquireForm" property="vo.title" filter="false"/></strong></td>
			</tr>
			<tr>
				<th class="Btmline">질의 내용</th>
				<td colspan="3"><bean:write name="InquireForm" property="vo.contents" filter="false"/>
					<br/><br/>
					<logic:iterate name="InquireForm" property="voList" indexId="rowNum" id="attachVO">
					첨부파일:						
						<bean:define name="attachVO" property="file_id" id="file_id" type="java.lang.String"/>
						<bean:define name="attachVO" property="seq" id="seq" type="java.lang.String"/>
						<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
					</logic:iterate>
				</td>
			</tr>						
			<tr>
				<th class="Btmline1">분야 선택</th>
				<td colspan="3">
					<html:select name="InquireForm" property="vo.category1" styleId="category1" title="대분류" onchange="f_cate_change(this.value)">
				    <html:option value="">::: 선택 :::</html:option>
				  	</html:select>
				  	<html:select name="InquireForm" property="vo.category2" styleId="category2" title="소분류">
				    <html:option value="">::: 선택 :::</html:option>
				    </html:select>
				</td>
			</tr>
			<tr>
				<th class="Btmline1">답변 내용</th>
				<td colspan="3"><html:textarea name="InquireForm" property="vo.answerContents" rows="15" cols="90"  alt="답변 내용" title="답변 내용"/></td>
			</tr>
			<logic:notEmpty name="InquireForm" property="voList1">
			<tr>
				<th class="Btmline1">기존파일 삭제여부</th>
				<td>
					<logic:iterate name="InquireForm" property="voList1" indexId="rowNum1" id="attachVO">						
						<bean:define name="attachVO" property="file_id" id="file_id" type="java.lang.String"/>
						<bean:define name="attachVO" property="seq" id="seq" type="java.lang.String"/>
<%
						String f_id = file_id+"-"+seq;
%>
						<input type="checkbox" name="file_del" value="<%=f_id%>"/>
						<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
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
			<br/>
			<img src="../images/Menu/OnLine_Text04.gif" alt="등록자정보" />
			<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
			<colgroup>
				<col width="15%" />
				<col width="35%" />
				<col width="15%" />
				<col width="35%" />
			</colgroup>
			<tr><th class="Btmline">이름</th>
				<td><bean:write name="InquireForm" property="vo.reg_nm"/></td>
				<th class="Btmline">공개여부</th>
				<td>
					<bean:define name="InquireForm" property="vo.open_yn" id="open_yn" type="java.lang.String"/>
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
				<th class="Btmline">휴대폰번호</th>
				<td>
					<bean:define name="InquireForm" property="vo.call_receive_yn" id="call_receive_yn" type="java.lang.String"/>
					<bean:define name="InquireForm" property="vo.cell_number" id="cell_number" type="java.lang.String"/>
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
					<bean:define name="InquireForm" property="vo.email_receive_yn" id="email_receive_yn" type="java.lang.String"/>
					<bean:define name="InquireForm" property="vo.email" id="email" type="java.lang.String"/>
<%
					if(email_receive_yn.equals("Y")){
						out.println("[수신] " +email);
					}else{
						out.println("[미수신]");
					}
%>
				</td>
			</tr>
			</table>
			<div class="Basic-Button">
				<ul class="Right">
					<li><a href="JavaScript:goAnswerCreate()" class="btn_Basic"><strong>답변등록</strong></a></li>
					<li><a href="JavaScript:goCancel()" class="btn_Basic"><strong>취소</strong></a></li>
				</ul>	
			</div>			
			</html:form>		
		</div>
		
</div>
<%@  include file="/include/bottom.jsp"%>