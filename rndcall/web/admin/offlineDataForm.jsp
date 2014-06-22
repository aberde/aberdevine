<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
### 경로   : <%= request.getRequestURI() %>
### 사업명 : 11년도 개방형 연구성과분석지원서비스
### 사업일 : 2011-07-10 ~ 2011-08-31
### 개발일 : 2011-07-14
### 개발자 : 박인선
-->
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<%@ page import="kr.go.rndcall.mgnt.common.DesCipher" %>
<%  String size = "70"; // default size
    if(!Util.isNull(request.getParameter("size"))) {
    	size = request.getParameter("size");
    }		
	if (mainLoginVO == null || !mainIsLogin) {	
%>
	<script type="text/javascript">
		alert('로그인이 필요한 메뉴입니다.');
		document.location.href = "index.jsp";
	</script>
<%
	} else if (!mainRoleCD.equals("0000C") && !mainRoleCD.equals("0000Z")) {
%>
	<script type="text/javascript">
		alert('권한이 없습니다.');
		document.location.href = "index.jsp";
	</script>
<%		
	}
%>

<bean:define name="AdminForm" property="errCd" id="errCd" type="java.lang.String"/>

<script language="JavaScript" src="/js/file.js"></script>
<script type="text/JavaScript">

	window.onload=function(){
		if("<%=errCd%>" == "1"){
			alert("오프라인자료등록이 성공하였습니다. /n Q&A에서 확인하실수 있습니다.");
			return;
		}else if("<%=errCd%>" == "-1"){
			alert("오프라인자료등록이 등록이 실패하였습니다.");
			return;
		}
	}

function fncFileAddLenChk(fileObjName, size){
   
    var fileArea = document.getElementById(fileObjName);
    var childNds = fileArea.childNodes;
    
    //alert(childNds.length);
    if(childNds.length < 3) //최대 3개까지 첨부 가능
        fncFileAdd(fileObjName, size);
}

function goInsert(){
	if(validate()) {
		if(confirm("등록하시겠습니까?")){
			fm.elements["method"].value = "getOfflineDataInsert";
			fm.submit();
		}
	}
}

function validate() {
/*
 	if (isRequired(fm.elements["vo.bd_title"])){
		return false;
	}
	
	if (isRequired2(fm.elements["vo.step_nm"])){
		return false;
	}
	
	if (isRequired2(fm.elements["vo.bd_contents"])){
		return false;
	}
*/	
    return true;
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
    <logic:iterate name="AdminForm" property="voList2" id="mCode" indexId="comRowNm">
    categoryL[<%=comRowNm.intValue()%>] = new category_obejct("<bean:write name="mCode" property="code_nm" />", "<bean:write name="mCode" property="code" />", "<bean:write name="mCode" property="subjectNo" />");
    </logic:iterate>
    <logic:iterate name="AdminForm" property="voList3" id="mCode" indexId="comRowNm">
    categoryM[<%=comRowNm.intValue()%>] = new category_obejct("<bean:write name="mCode" property="code_nm" />", "<bean:write name="mCode" property="code" />", "<bean:write name="mCode" property="p_code" />");
    </logic:iterate>
    
    //f_cate_change2(fmDetail.elements["searchVO.bz_id"].value);
    f_cate_change2();
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
    cateL[idx++] = new Option(categoryL[i].codeName, categoryL[i].codeValue);
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
      cateM[idx++] = new Option(categoryM[i].codeName, categoryM[i].codeValue); 
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
						<li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=09"><img src="../images/Menu/left/lm03_01_off.gif" alt="권한관리" /></a></li>
						<li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=09"><img src="../images/Menu/left/lm03_02_off.gif" alt="분야관리" /></a></li>
						<li><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm&searchVO.menu_sn=09"><img src="../images/Menu/left/lm03_03_off.gif" alt="오프라인자료등록" /></a></li>
						<li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatCategory&searchVO.menu_sn=09"><img src="../images/Menu/left/lm03_04_off.gif" alt="통계관리" /></a></li>
				</ul>
				<SCRIPT type=text/javascript>
				<!--
				var ObjEventLeftMenu = new EventLeftMenu(3, 3, 0);
				//-->
				</SCRIPT>
		</div>
		<!-- end # 레프트 메뉴 -->
	<div class="LY-Content">
	<html:form action="/Admin" method="post" name="fm" type="kr.go.rndcall.mgnt.admin.form.AdminForm" enctype="multipart/form-data" onsubmit="return checkOnSubmit(this)">
	<html:hidden name="AdminForm" property="method" value="getOfflineDataInsert"/>
	<html:hidden name="AdminForm" property="searchVO.menu_sn"/>
	<div class="Basic-List-area">
	<img src="../images/Menu/OnLine_Text01.gif" alt="단건등록" />
	<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
			<colgroup>
				      <col width="110px" />
      <col width="*" />
      <col width="110px" />
      <col width="*" />
			</colgroup>
			<tr>
				<th class="Btmline">제목</th>
				<td colspan="3"><html:text name="AdminForm" property="vo.title" styleClass="Out_lineY W50" size="80" title="제목"/>
				</td>
			</tr>
			<tr>
				<th class="Btmline">이름</th>
				<td><html:text name="AdminForm" property="vo.name" styleClass="Out_lineY W50"/></td>
				<th class="Btmline">분류선택(대)</th>
				<td>
				  <html:select name="AdminForm" property="vo.category1" styleId="category1" title="분류선택(대)" onchange="f_cate_change(this.value)">
				    <html:option value="">::: 선택 :::</html:option>
					<!-- <html:optionsCollection name="AdminForm" property="voList" label="cd_nm" value="cd_nm"/>-->
				  </html:select>
				</td>
			</tr>
			<tr>
				<th class="Btmline">연락처</th>
				<td>
				  <html:select name="AdminForm" property="vo.cell_number1" title="">
					<html:option value="010">010</html:option>
				  </html:select> - <html:text name="AdminForm" property="vo.cell_number2" styleClass="Out_lineY W50" size="4" maxlength="4"/> - <html:text name="AdminForm" property="vo.cell_number3" styleClass="Out_lineY W50" size="4" maxlength="4" /></td>
				<th class="Btmline">분류선택(중)</th>
				<td>
				  <html:select name="AdminForm" property="vo.category2" styleId="category2" title="분류선택(중)">
				    <html:option value="">::: 선택 :::</html:option>
					<!-- <html:optionsCollection name="AdminForm" property="voList" label="cd_nm" value="cd_nm"/>-->
				  </html:select>
				</td>
			</tr>
			<tr>
				<th class="Btmline">질문일자</th>
				<td><html:text name="AdminForm" property="vo.reg_dt" styleClass="Out_lineY W50" size="10" maxlength="10"/> 예) 2012-01-01</td>
				<th class="Btmline">질문시간</th>
				<td><html:text name="AdminForm" property="vo.reg_time" styleClass="Out_lineY W50" size="5" maxlength="5"/> 예) 15:23</td>
			</tr>
			<tr>
				<th class="Btmline">질문내용</th>
				<td colspan="3"><textarea name="vo.question_contents" id="question_contents" rows="3" cols="10" title="질문내용"/></textarea></td>
			</tr>
			<tr>
				<th class="Btmline">답변내용</th>
				<td colspan="3"><textarea name="vo.answer_contents" id="contents" rows="3" cols="10" title="답변내용"/></textarea></td>
			</tr>
			<tr>
				<th class="Btmline">첨부</th>
				<td colspan="3">
				<a href="#" onclick="fncFileAddLenChk('fileArea', '<%=size%>')" class="btn_TLadd"><strong>파일추가</strong></a>
				<a href="#" onclick="fncFileDel('fileArea')" class="btn_TLdel"><strong>파일제거</strong></a>
				<div id="fileArea"></div>
				</td>
			</tr>
			</table>
			<br/>
		<!-- 일괄등록(엑셀파일로 등록) -->
		
		<img src="../images/Menu/OnLine_Text02.gif" alt="단건등록" />
<%
//엑셀양식 다운로드파일명
DesCipher dc = new DesCipher();
String saveFileNM = "e_relation01.xls"; 
saveFileNM = dc.Encode(saveFileNM);
%>		
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td height="20px"></td>
		</tr>
		<tr>
			<td align="center"><a href="javascript:downLoad('오프라인자료등록(양식).xls', '<%=saveFileNM%>', '/FILE/rndcall/', 'Y');" class="btn_IEx" ><strong>양식 다운로드</strong></a></td>
		</tr>
		<tr>
			<td height="20px"></td>
		</tr>
		</table>
		<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
			<colgroup>
				<col width="120px" />
				<col width="*" />
			</colgroup>
			<tr>
				<th class="right">일괄등록첨부</th>
				<td><input type="file" name="vo.putFile" id="putFile" title="파일선택" class="LineInput" style="width:97%" maxlength="250"/></td>
			</tr>
			
		</table>

		<div style="margin:10px 0; display:block">
		<ul class="Center">
			<div class="Basic-Button">
			<ul class="Center">
				<li><a href="JavaScript:goInsert()"  class="btn_Basic"><strong>등록</strong></a>
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