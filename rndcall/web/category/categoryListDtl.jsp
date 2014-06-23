<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/include/top.jsp"%>

<bean:define name="categoryForm" property="errCd" id="errCd" type="java.lang.String"/>
<bean:define name="categoryForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>

<bean:define id="path" type="java.lang.String" value="/category.do"/>

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

<title>R&amp;D도우미센터</title>
<script language="JavaScript" src="/js/validate.js"></script>
<script>
<!--

function fn_delete(id) {
	if (confirm('카테고리 삭제 시 해당 분야의 게시물은 분야값이 삭제됩니다.\n선택한 카테고리를 삭제하시겠습니까?')) {
		fm.elements["vo.category_id"].value = id;
		fm.elements["searchVO.crud"].value = "DELETE";
		fm.method.value = "getCategoryListDtl";
		fm.submit();
	}
}

function cate_update(id) {
	var url = "/switch.do?prefix=&page=/category.do?method=getCategoryInfo&searchVO.category_id=" + id; 
	popWinCenter(url,'Info',435,220,0,0,0,1);
}

function getList() {
	fm.elements["searchVO.parent_id"].value = '';
	fm.elements["searchVO.crud"].value = "";
	fm.method.value = "getCategoryList";
	fm.submit();
}

function insert() {
	if (fm.elements["vo.order_no"].value == "") {
		alert('정렬순번을 입력하십시오.');
	} else if (fm.elements["vo.category_nm"].value == "") {
		alert('카테고리명을 입력하십시오.');
	} else {
		if(confirm("질문분야를 등록하시겠습니까?")){
			fm.elements["searchVO.crud"].value = "INSERT";
			fm.elements["vo.tree_level"].value = "2";
			fm.elements["vo.parent_id"].value = fm.elements["searchVO.parent_id"].value;
			fm.method.value = "getCategoryListDtl";
			fm.submit();
		}
	}
}

function popWinCenter(wname,fname,width,height,resizable,toolbar,scrollbar, type) {
  var str = "height=" + height + ",innerHeight=" + height;
  str += ",width=" + width + ",innerWidth=" + width;
      var xc = 0;
      var yc = 0;
  if (window.screen) {
    var aw = screen.availWidth - 10;
    var ah = screen.availHeight - 30;

    if( type == 1 ) {
    	var xc = 100;
	  	var yc = (ah / 2) - 350;
    } else if ( type == 2 ) {
    	var xc = 550;
	  	var yc = (ah / 2) - 350;
    }

    str += ",left=" + xc + ",screenX=" + xc;
    str += ",top=" + yc + ",screenY=" + yc;
  }

  var opn=window.open(wname,fname,str+", resizable="+resizable+", toolbar="+toolbar+", scrollbars="+scrollbar);
  opn.focus();
}

function search() {
		fm.method.value = "getCategoryListDtl";
		fm.submit();
}

function getList() {
	fm.elements["searchVO.search_word"].value = "";
	fm.method.value = "getCategoryList";
	fm.submit();
}

function checkNum(objNumBox){
	var numBoxValue = objNumBox.value;
	
	for(var i=0;i<numBoxValue.length;i++){
		if(isNaN(numBoxValue.charAt(i))){
			alert("숫자만 입력해주세요.");
			objNumBox.value = '';
			for(var j=0;j<i;j++){
				objNumBox.value += numBoxValue.charAt(j);
			}
			return;
		} 
	}
}

//-->
</script>

<!-- start # LY-Container -->
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
			var ObjEventLeftMenu = new EventLeftMenu(3, 2, 0);
			//-->
		</SCRIPT>
	</div>
	<!-- end # 레프트 메뉴 -->
	<div class="LY-Content">
	<html:form action="/category" method="post" name="fm" type="kr.go.rndcall.mgnt.category.CategoryForm">
	<html:hidden name="categoryForm" property="method" value="getCategoryListDtl"/>
	<html:hidden name="categoryForm" property="searchVO.parent_id"/>
	<html:hidden name="categoryForm" property="searchVO.category_id"/>
	<html:hidden name="categoryForm" property="searchVO.crud"/>
	<html:hidden name="categoryForm" property="vo.category_id"/>
	<html:hidden name="categoryForm" property="vo.parent_id"/>
	<html:hidden name="categoryForm" property="vo.tree_level"/>
	<html:hidden name="categoryForm" property="searchVO.menu_sn" value="<%= menu_sn %>"/>
	<div class="InforSearch02" title="검색">
		<ul class="Search">
			<li>
				<table border="0" cellspacing="0" cellpadding="0" class="Search-Pre">
					<tr>
						 <th>분류</th>
	   					 <td>	
							<html:select name="categoryForm" property="searchVO.search_sel">
								<html:option value="nm">분야명</html:option>
							</html:select>
						 	<html:text name="categoryForm" property="searchVO.search_word" title="제목" styleClass="lineInputKO" size="30" maxlength="35"onchange="trim(this)"  />
						 </td>
	  				     <td class="Btn"><a href="javascript:search()" class="btn_BSearch"><strong>검색</strong></a> </td>
	  				</tr>
				</table>
			</li>
		</ul>
	</div>
	<br/>
	<strong><bean:write name="categoryForm" property="vo.parent_nm" /></strong>
	<!-- start # Basic-List -->
	<div class="Basic-List-area">
		<!-- start # 리스트 목록 -->
		<ul class="List">
			<table border="0" cellspacing="0" cellpadding="0" class="Basic-List">
				<thead>
					<tr>
						<th width="80px">번호</th>
						<th width="100px">분야코드</th>
						<th width="220px">분야명</th>
						<th width="150px">수정</th>
						<th width="150px">삭제</th>
					</tr>
				</thead>
				<tbody>	
					<logic:empty name="categoryForm" property="voList">
						<tr>
							<td colspan="99">등록된 카테고리가 없습니다.</td>
						</tr>
					</logic:empty>	
					<logic:notEmpty name="categoryForm" property="voList">
						<logic:iterate name="categoryForm" property="voList" id="vo" indexId="rowNum">
							<tr>
								<td><bean:write name="vo" property="order_no"/></td>
								<td><bean:write name="vo" property="category_id"/></td>
								<td><bean:write name="vo" property="category_nm"/></td>
								<td><a href="javascript:cate_update('<bean:write name="vo" property="category_id"/>');" class="btn_TWrite"><strong>수정</strong></a></td>
								<td><a href="javascript:fn_delete('<bean:write name="vo" property="category_id"/>');" class="btn_TDel"><strong>삭제</strong></a></td>
							</tr>
						</logic:iterate>
					</logic:notEmpty>	
					<tr>
						<td><html:text name="categoryForm" property="vo.order_no" size="4" maxlength="4" onkeyup="checkNum(this)"/></td>
						<td colspan="2"><html:text name="categoryForm" property="vo.category_nm" size="30" maxlength="50"/></td>
						<td><a href="javascript:insert();" class="btn_TSave"><strong>저장</strong></a></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</ul>
		<br />
		<ul class="List-Search">
			<li>
				<a href="javascript:getList();" class="btn_Basic"><strong>이전화면</strong></a>
			</li>
		</ul>
	</div>
</html:form>
</div>
</div>
<!-- end # LY-Container -->
<%@ include file="/include/bottom.jsp"%>
