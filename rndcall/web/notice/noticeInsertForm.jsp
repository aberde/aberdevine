<%@ page contentType="text/html; charset=utf-8" %>
<%@  include file="/include/top.jsp"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<bean:define name="NoticeForm" property="errCd" id="errCd" type="java.lang.String"/>
<bean:define id="path" type="java.lang.String" value="/Notice.do"/>

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
	window.onload= function() { // onload 시 호출. 데이터 초기화.
    	if("<%=errCd%>" == "true"){
    		alert("정상적으로 등록되었습니다.");
    		document.location.href="/switch.do?prefix=/notice&page=/Notice.do?method=noticeList&searchVO.menu_sn=04";
    	}else if("<%=errCd%>" == "false"){    	
    		alert("등록이 실패하였습니다.");
    		return;
    	}
	}
	function fncFileAddLenChk(fileObjName, size){
	   
	    var fileArea = document.getElementById(fileObjName);
	    var childNds = fileArea.childNodes;
	    
	    //alert(childNds.length);
	    if(childNds.length < 20) //최대 3개까지 첨부 가능
	        fncFileAdd(fileObjName, size);
	}
	function Save(){
		if(validate()){
			fm.elements["method"].value="noticeInsert";
			fm.submit();
		}
	}
	
	function goCancel(){
		document.location.href="/index.jsp";
	}
	
	
	function validate() {
		//SMS수신 체크시 핸드폰번호 체크
		
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
	<html:form action="/Notice" method="post" name="fm" type="kr.go.rndcall.mgnt.notice.form.NoticeForm" enctype="multipart/form-data" onsubmit="return checkOnSubmit(this)">
    <html:hidden name="NoticeForm" property="method" value="noticeInsert"/>
	<html:hidden name="NoticeForm" property="searchVO.loginId"/>
	<html:hidden name="NoticeForm" property="searchVO.name"/>
	<html:hidden name="NoticeForm" property="searchVO.menu_sn"/>
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
			<th class="Btmline">내용</th>
			<td><textarea name="vo.contents" rows="18" class="Out_line W90" title="자주하는질문 내용" ></textarea></td>
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