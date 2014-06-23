<%@ page contentType="text/html; charset=utf-8" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@ taglib uri="/tags/taglibs-log" prefix="log"%>

<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<%@ page import="kr.go.rndcall.mgnt.login.LoginVO" %>
<%
  boolean mainIsLogin =false;
  LoginVO mainLoginVO = (LoginVO) session.getAttribute("loginUserVO");
  if(mainLoginVO != null && mainLoginVO.getLogin_id()!=null && !mainLoginVO.getLogin_id().equals("guest")) {
	  mainIsLogin = true;
  }

  String mainRoleCD = "guest";
  String nameKO = "";
  String login_id = "";
  String menu_sn = "";
  
  menu_sn = (String) request.getParameter("searchVO.menu_sn");
  System.out.println("111111111111111111111111"+ (String)request.getAttribute("searchVO.menu_sn"));
  if(menu_sn =="" || menu_sn==null || menu_sn == "null") menu_sn = (String)request.getAttribute("searchVO.menu_sn");
  
  System.out.println(" top 페이지 menu_sn::"+menu_sn);

  if (mainLoginVO != null && mainIsLogin) {	
	  mainRoleCD = mainLoginVO.getRoleCD();
	  nameKO = mainLoginVO.getName();
	  login_id = mainLoginVO.getLogin_id();
  }
%>
<bean:define name="OfferForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="OfferForm" property="searchVO.loginId" id="loginId" type="java.lang.String"/>
<bean:define name="OfferForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String"/>
<bean:define name="OfferForm" property="vo.stat" id="stat" type="java.lang.String"/>
<bean:define id="path" type="java.lang.String" value="/Offer.do"/>
<style type="text/css">
<!--
@import url("/css/Base.css");
@import url("/css/Layout.css");
@import url("/css/Header.css");
@import url("/css/Content.css");
@import url("/css/Footer.css");
@import url("/css/Button.css");
@import url("/css/Admin.css");
-->
</style>
<script LANGUAGE="JavaScript" src="/js/prototype.js"></script>
<script LANGUAGE="JavaScript" src="/js/Field.js"></script>
<script LANGUAGE="JavaScript" src="/js/EventMenu.js"></script>
<script language="JavaScript" src="/js/validate.js"></script>
<script language="JavaScript" src="/js/common.js"></script> 
<script type="text/javascript">
<!--		


function downLoad(fileNM, saveFileNM, filePath, yn){
    fileDownLoad.elements["fileNM"].value = fileNM;
    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
    fileDownLoad.elements["filePath"].value = filePath;
    fileDownLoad.elements["desCipher"].value = yn;
    fileDownLoad.submit();
}

function goPrint(){
	window.print();
}
//-->
</script>
<form name="fileDownLoad" method="post" action="/downLoad.do" >
    <input type="hidden" name="fileNM"/>
    <input type="hidden" name="saveFileNM"/>
    <input type="hidden" name="filePath"/>
	<input type="hidden" name="desCipher" value="N"/>
</form>	
<div class="LY-Container">
	<html:form action="/Offer" method="post" name="fm" type="kr.go.rndcall.mgnt.offer.form.OfferForm">
	<html:hidden name="OfferForm" property="method" value="offerDetailPop"/>
	<html:hidden name="OfferForm" property="vo.cell_number"/>
	<html:hidden name="OfferForm" property="vo.email"/>
	<html:hidden name="OfferForm" property="searchVO.loginId"/>
	<html:hidden name="OfferForm" property="searchVO.name"/>
	<html:hidden name="OfferForm" property="searchVO.seq"/>
	<html:hidden name="OfferForm" property="searchVO.board_type"/>
	<html:hidden name="OfferForm" property="searchVO.searchCategory"/>
	<html:hidden name="OfferForm" property="searchVO.menu_sn"/>
	<html:hidden name="OfferForm" property="searchVO.pagerOffset"/>
		<div class="LY-ContentTitle">
			<h1><img src="/images/content/Content_Title01_04.gif" alt="제안하기 - 가장 많이 묻는 질의응답을 카테고리별로 보실 수 있습니다." /></h1>
		</div>
	<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
		<colgroup>
			<col width="130px" />
			<col width="*" />
		</colgroup>
		<tr>
			<th class="Btmline">제목</th>
			<td><strong><bean:write name="OfferForm" property="vo.title" filter="false"/></strong></td>
		</tr>
		<tr>
			<th class="Btmline">등록일</th>
			<td><bean:write name="OfferForm" property="vo.reg_dt"/></td>
		</tr>
		<tr><th class="Btmline">질의내용</th>
			<td>
			<bean:write name="OfferForm" property="vo.contents" filter="false"/>
			<br/><br/>
			<logic:notEmpty name="OfferForm" property="voList">
			첨부파일 : 
			<logic:iterate name="OfferForm" property="voList" indexId="rowNum" id="attachVO">
				<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
			</logic:iterate>
			</logic:notEmpty>
			</td>
		</tr>
		<tr><th class="Btmline1">답변내용</th>
			<td><bean:write name="OfferForm" property="vo.answerContents" filter="false"/>
			<br/><br/>
			<logic:notEmpty name="OfferForm" property="voList1">
			첨부파일 : 
				<logic:iterate name="OfferForm" property="voList1" indexId="rowNum" id="attachVO">
					<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
				</logic:iterate>
			</logic:notEmpty>
		</tr>
		</table>

		<div class="Basic-Button">
				<ul class="Center">	
				<bean:define name="OfferForm" property="vo.up_del_stat" id="up_del_stat" type="java.lang.String"/>
				<bean:define name="OfferForm" property="vo.stat" id="stat" type="java.lang.String"/>				
				<li><a href="JavaScript:goPrint();" class="btn_Basic"><strong>인쇄</strong></a>
				<a href="JavaScript:self.close()" class="btn_Basic"><strong>창닫기</strong></a></li>
				
			</div>
	</html:form>
	<!-- end 검색 테이블 감싸기 -->
	<br />
	<br />
	<!-- end .ntiscontents -->
	</div>
<!-- end .container -->
</div>
