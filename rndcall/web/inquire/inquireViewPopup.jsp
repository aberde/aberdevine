<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="kr.go.rndcall.mgnt.inquire.vo.*" %>
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
<bean:define name="InquireForm" property="searchVO" id="searchVO" type="kr.go.rndcall.mgnt.inquire.vo.InquireSearchVO"/>
<bean:define name="InquireForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="InquireForm" property="vo.public_trans_yn" id="public_trans_yn" type="java.lang.String"/>
<bean:define name="InquireForm" property="vo.stat" id="stat" type="java.lang.String"/>
<bean:define name="InquireForm" property="voList" id="file_list" type="java.util.ArrayList"/>
<bean:define name="InquireForm" property="voList2" id="cateL" type="java.util.ArrayList"/>
<bean:define id="path" type="java.lang.String" value="/Inquire.do"/>
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
function goPrint(){
	window.print();
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
<form name="fileDownLoad" method="post" action="/downLoad.do" >
    <input type="hidden" name="fileNM"/>
    <input type="hidden" name="saveFileNM"/>
    <input type="hidden" name="filePath"/>
	<input type="hidden" name="desCipher" value="N"/>
</form>	
	<!-- start # LY-Container -->
	<div class="LY-Container">
		<!-- start # LY-ContentTitle -->
			<div class="LY-ContentTitle">
				<h1><img src="/images/content/Content_Title01_3.gif" alt="Q&A - 다른 사용자들이 등록한 질의응답정보를 조회 할 수 있습니다." /></h1>
			</div>
			<!-- end # LY-ContentTitle -->			
			<html:form action="/Inquire" method="post" name="fm" type="kr.go.rndcall.mgnt.inquire.form.InquireForm">
			<html:hidden name="InquireForm" property="method" value="getInquireInsert"/>
			<html:hidden name="InquireForm" property="vo.cell_number"/>
			<html:hidden name="InquireForm" property="vo.email"/>
			<html:hidden name="InquireForm" property="searchVO.loginId"/>
			<html:hidden name="InquireForm" property="searchVO.name"/>
			<html:hidden name="InquireForm" property="searchVO.seq"/>
			<html:hidden name="InquireForm" property="searchVO.board_type"/>
			<html:hidden name="InquireForm" property="searchVO.searchCategory"/>
			<html:hidden name="InquireForm" property="searchVO.whichSearch"/>
			<html:hidden name="InquireForm" property="searchVO.searchTxt"/>				
			<html:hidden name="InquireForm" property="searchVO.menu_sn"/>
			<html:hidden name="InquireForm" property="searchVO.uni"/>
<%
		if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
%>	
			<html:hidden name="InquireForm" property="searchVO.start_yy"/>
		    <html:hidden name="InquireForm" property="searchVO.start_mm"/>
		    <html:hidden name="InquireForm" property="searchVO.end_yy"/>
		    <html:hidden name="InquireForm" property="searchVO.end_mm"/>
<%
		}
%>
			<!-- start # 상세보기 -->
			<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
			<colgroup>
				<col width="130px" />
				<col width="*" />
			</colgroup>
			<tr><th class="Btmline">제목</th>
				<td><strong>[<bean:write name="InquireForm" property="vo.category1_nm"/>] <bean:write name="InquireForm" property="vo.title" filter="false"/></strong></td>
			</tr>
			<tr><th class="Btmline">등록일</th>
				<td><bean:write name="InquireForm" property="vo.reg_dt"/></td>
			</tr>
			<tr><th class="Btmline">질의내용</th>
				<td>
					<bean:write name="InquireForm" property="vo.contents" filter="false"/>
					<br/><br/>
					<logic:notEmpty name="InquireForm" property="voList">
					첨부파일 : 
						<logic:iterate name="InquireForm" property="voList" indexId="rowNum" id="attachVO">
							<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
						</logic:iterate>
					</logic:notEmpty>
				</td>
			</tr>
<%
		if(stat.equals("Y")){
%>				
			<tr><th class="Btmline1">답변내용</th>
				<td><bean:write name="InquireForm" property="vo.answerContents" filter="false"/>
					<br/><br/>
					<logic:notEmpty name="InquireForm" property="voList1">
					첨부파일 : 
						<logic:iterate name="InquireForm" property="voList1" indexId="rowNum1" id="attachVO">
							<a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
						</logic:iterate>
					</logic:notEmpty>
    			</td>
			</tr>
<%
		}
%>			
			</table>
			<br/>
			<!-- start # 체크 -->	
			<div class="Basic-Button">
				<ul class="Center">				
					<bean:define name="InquireForm" property="vo.up_del_stat" id="up_del_stat" type="java.lang.String"/>
					<bean:define name="InquireForm" property="vo.stat" id="stat" type="java.lang.String"/>	
					<li><a href="JavaScript:self.close()" class="btn_Basic"><strong>창닫기</strong></a></li>
				</ul>	
			</div>
			</html:form>
	</div>
</div>
