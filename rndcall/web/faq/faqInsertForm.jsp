<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>

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
<script type="text/JavaScript">
	function fncFileAddLenChk(fileObjName, size){
	   
	    var fileArea = document.getElementById(fileObjName);
	    var childNds = fileArea.childNodes;
	    
	    //alert(childNds.length);
	    if(childNds.length < 20) //최대 3개까지 첨부 가능
	        fncFileAdd(fileObjName, size);
	}
	function Save(){
		var cate = fm.elements["vo.analysis_yn"];
		//alert(cate);
		if(cate.checked){
			if(validate()) {
				fm.elements["method"].value="faqInsert";
				fm.submit();
				var width = '480';
			    var height = '630';
			    var left = (screen.width - width)/2;
			    var top = (screen.height - height)/2;
			   	var winNM = 'FaqForm';
			   	var url = '/switch.do?prefix=&page=/Faq.do?method=smsPopForm';  
			    var windowFeatures = "width=" + width + ",height=" + height + ",status,resizable,scrollbars=N,left=" + left + ",top=" + top + ",screenX=" + left + ",screenY=" + top;
			   	window.open("", winNM, windowFeatures);
				fm.action= url;
				fm.target= winNM;
				fm.submit();
				
			}
		}else{
			if(validate()){
				fm.elements["method"].value="faqInsert";
				fm.elements["searchVO.menu_sn"].value="01";
				fm.submit();
			}
		}
	}
	
	
	function goCancel(){
		document.location.href="/index.jsp";
	}
	
	
	function validate() {
		if (isRequired(fm.elements["vo.category1"])){
			return false;
		}
		//제목 필수 입력 체크
		if (isRequired(fm.elements["vo.title"])){
			return false;
		}
		//질의내용 필수 입력 체크
		if (isRequired(fm.elements["vo.contents"])){
			return false;
		}
		//답변내용
		if (isRequired(fm.elements["vo.answer_cont"])){
			return false;
		}
	
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

function init_data() { // onload 시 호출. 데이터 초기화.
    <logic:iterate name="FaqForm" property="voList2" id="mCode" indexId="comRowNm">
    categoryL[<%=comRowNm.intValue()%>] = new category_obejct("<bean:write name="mCode" property="code_nm" />", "<bean:write name="mCode" property="code" />", "<bean:write name="mCode" property="subjectNo" />");
    </logic:iterate>
    <logic:iterate name="FaqForm" property="voList3" id="mCode" indexId="comRowNm">
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
    /*
    if(categoryL[i].parentCode == value) { // 중분류객체의 parentCode 와 대분류값 비교.. 같다면..
      // option 생성하여 현재 객체의 codeName, codeValue 추가.
      cateL[idx++] = new Option(categoryL[i].codeName, categoryL[i].codeValue); 
    } // if끝
    */
    //if(categoryL[i].parentCode == value) { // 중분류객체의 parentCode 와 대분류값 비교.. 같다면..
      // option 생성하여 현재 객체의 codeName, codeValue 추가.
      cateL[idx++] = new Option(categoryL[i].codeName, categoryL[i].codeValue); 
    //} // if끝
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
</script>
<body onload="init_data()">
	<div class="LY-Container">
		<!-- start # 레프트 메뉴 -->
		<div id="LY-Left">
				<!-- start # left menu  -->
				<ul id="Left-Menu">
						<li><a href="JavaScript:goInquireForm()"><img src="/images/Menu/left/lm01_01_off.gif" alt="온라인상담" /></a></li>
						<li><a href="JavaScript:goFaq()"><img src="/images/Menu/left/lm01_02_off.gif" alt="자주 묻는 질문" /></a></li>
						<li><a href="JavaScript:goInquireList('QNA')"><img src="/images/Menu/left/lm01_03_off.gif" alt="Q&amp;A" /></a></li>
						<li><a href="JavaScript:goOffer()"><img src="/images/Menu/left/lm01_04_off.gif" alt="제안하기" /></a></li>
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
		<html:form action="/Faq" method="post" name="fm" type="kr.go.rndcall.mgnt.faq.form.FaqForm" enctype="multipart/form-data" onsubmit="return checkOnSubmit(this)">
	    <html:hidden name="FaqForm" property="method" value="faqInsert"/>
		<html:hidden name="FaqForm" property="vo.cell_number"/>
		<html:hidden name="FaqForm" property="vo.email"/>
		<html:hidden name="FaqForm" property="searchVO.loginId"/>
		<html:hidden name="FaqForm" property="searchVO.name"/>
		<html:hidden name="FaqForm" property="searchVO.menu_sn"/>
		<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
		<colgroup>
			<col width="100px" />
			<col width="*" />
		</colgroup>
		<tr>
			<th class="Btmline">분류선택</th>
			<td>
				<html:select name="FaqForm" property="vo.category1" styleId="category1" title="대분류" onchange="f_cate_change(this.value)">
			    <html:option value="">::: 선택 :::</html:option>
				<html:optionsCollection name="FaqForm" property="voList" label="cd_nm" value="cd_nm"/>
				</html:select>
				<html:select name="FaqForm" property="vo.category2" styleId="category2" title="소분류">
				<html:option value="">::: 선택 :::</html:option>
				<html:optionsCollection name="FaqForm" property="voList" label="cd_nm" value="cd_nm"/>
			  </html:select>
			</td>
		</tr>
		<tr>
			<th class="Btmline">유권해석</th>
				<td>
					<input type="checkbox" name="vo.analysis_yn" title="유권해석" value="Y"/> (유권해석인 경우 체크)
				</td>
		</tr>
		<tr>
			<th class="Btmline">제목</th>
				<td>
					<input type="text" name="vo.title" class="Out_lineY W90" size="50" title="제목"/>
				</td>
		</tr>
		<tr>
			<th class="Btmline">질의내용</th>
			<td><textarea name="vo.contents" rows="10" class="Out_line W90" title="자주하는질문 내용" ></textarea></td>
		</tr>
		<tr>
			<th class="Btmline">답변내용</th>
			<td><textarea name="vo.answer_cont" rows="10" class="Out_line W90" title="자주하는질문 내용답변" ></textarea></td>
		</tr>
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
					<li><a href="JavaScript:Save()"  class="btn_Basic"><strong>등록</strong></a>
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