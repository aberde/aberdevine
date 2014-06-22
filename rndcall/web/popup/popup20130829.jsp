<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

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
  if(menu_sn =="" || menu_sn==null || menu_sn == "null") menu_sn = (String)request.getAttribute("searchVO.menu_sn");
  
  System.out.println(" top 페이지 menu_sn::"+menu_sn);

  if (mainLoginVO != null && mainIsLogin) {	
	  mainRoleCD = mainLoginVO.getRoleCD();
	  nameKO = mainLoginVO.getName();
	  login_id = mainLoginVO.getLogin_id();
  }
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>R&D도우미센터</title>
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
<script language="JavaScript" src="/js/ajax.js"></script>
<script language="JavaScript" src="/js/prototype.js"></script>
<script language="JavaScript" src="/js/Field.js"></script>
<script language="JavaScript" src="/js/EventMenu.js"></script>
<script language="JavaScript" src="/js/common.js"></script> 
<script language="JavaScript" src="/js/validate.js"></script> 
<script language="javascript" type="text/javascript">

<!--

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function update() {	
	document.fm3.elements["vo.login_id"].value = '<%= login_id %>';
	document.fm3.submit();
}

//제안하기 질문 링크
function goOffer(){
	if("<%=login_id%>" == ""){
		//로그인화면 팝업하기
		opener.login();
	}else{
		//제안하기 이동
		opener.goOffer();
	}
	self.close();
	return;		
}

function pageSubmit(fmObj, module, pageUrl) {
    fmObj.action = module + pageUrl;
    fmObj.submit();
}

/**
* 입력여부 체크
*/
function chkNull( str ) {
	if( str.length < 1 || str == "" || str == null ) {
		return true;
	} else {
		return false;
	}
}

//-->
</script>
</head>
<form name="fm2" method="post" action="/login.do" >
  <input type="hidden" name="method" value="login"/>
  <input type="hidden" name="vo.login_id"/>
  <input type="hidden" name="vo.password"/>
  <input type="hidden" name="returnURL"/>
</form>
<body>
	<img src="/images/popup/popup20130829_340x300.png" alt="여러분의 의견을 들려주세요" border="0" style="outline: none;" usemap="#Offer" />
	<map name="Offer" id="Offer">
		<area shape="rect" coords="7,255,170,293" href="JavaScript:goOffer()" />
		<area shape="rect" coords="171,255,333,293" href="mailto:jy1480@msip.go.kr" />
	</map>

</body>
</html>