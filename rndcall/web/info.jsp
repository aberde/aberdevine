<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@ taglib uri="/tags/taglibs-log" prefix="log"%>
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

  if (mainLoginVO != null && mainIsLogin) {	
	  mainRoleCD = mainLoginVO.getRoleCD();
	  nameKO = mainLoginVO.getName();
	  login_id = mainLoginVO.getLogin_id();
  }  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<script LANGUAGE="JavaScript" src="/js/prototype.js"></script>
<script LANGUAGE="JavaScript" src="/js/Field.js"></script>
<script LANGUAGE="JavaScript" src="/js/EventMenu.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function goData(){
	document.location.href="/switch.do?prefix=/data&page=/Data.do?method=dataListLaw&searchVO.menu_sn=02";
} 

function goNotice(){
	document.location.href="/switch.do?prefix=/notice&page=/Notice.do?method=noticeList&searchVO.menu_sn=04";
}

function goMypage(){
<% 
	if(mainLoginVO != null && mainIsLogin != false){
		if(!mainRoleCD.equals("0000C") && !mainRoleCD.equals("0000Z")){
%>		
			document.location.href="/switch.do?prefix=/mypage&page=/Mypage.do?method=getMypageList&searchVO.menu_sn=03";
<% 
		}else{
%>		
			alert("로그인후 이용하실수 있습니다.");	
<%
		}
	}else{
%>		
		alert("로그인후 이용하실수 있습니다.");	
<%
	}
%>
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

function onEnter(){
	if(event.keyCode==13){
		login();
		event.returnValue=false;
	}
}

function login() {	

	//1.아이디
	if ( chkNull( document.all.id.value ) ) {
		alert( reg_idIsNull );
		document.all.id.focus();
		return;
	}
	//2.아이디 형식 체크
	if ( chkNull( document.all.password.value ) ) {
		alert( reg_passIsNull );
		document.all.password.focus();
		return;
	} 
	
	document.fm2.elements["vo.login_id"].value = document.all.id.value;
	document.fm2.elements["vo.password"].value = document.all.password.value;
	document.fm2.submit();
	
}


function update() {	
	document.fm3.elements["vo.login_id"].value = '<%= login_id %>';
	document.fm3.submit();
}

//문의하기 링크
function goInquireForm(){
	if("<%=login_id%>" == ""){
		alert("로그인 후 이용하실 수 있습니다.");
		return;
	}else{		
		document.location.href = "/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireForm&searchVO.menu_sn=01";
	}
}

//Q&A링크
function goInquireList(){
	document.location.href = "/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireList&searchVO.board_type=QNA&searchVO.type=&searchVO.searchCategory=&searchVO.menu_sn=01";
}

//제안하기 질문 링크
function goOffer(){
	var url;
	if("<%=login_id%>" == ""){
		alert("로그인 후 이용하실 수 있습니다.");
		return;		
	}else{
		if("<%=mainRoleCD%>" == "0000Z" || "<%=mainRoleCD%>" == "0000C"){
			url ="/switch.do?prefix=&page=/Offer.do?method=offerList&searchVO.menu_sn=01";
		}else{
			url ="/switch.do?prefix=&page=/Offer.do?method=offerInsertForm&searchVO.menu_sn=01";
		}
		document.location.href = url;
	}
}

//자주하느질문 링크
function goFaq(){
	var url ="/switch.do?prefix=&page=/Faq.do?method=faqList&searchVO.menu_sn=01";
	document.location.href = url;
}

function goSiteUrl(num){
	var url = eval("document.siteUrlForm.siteurl"+num).value;
	if(url != '' && url != '#'){
		window.open(url, '_blank');
	}
}
//-->
</script>

<script type="text/JavaScript">
<!--
function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
function goUniSearch() {
//	if("<%=login_id%>" == ""){
//		alert("로그인 후 이용하실 수 있습니다.");
//		return;		
//	}else{
		//var fm = document.fm4;
		var word = document.all.word.value;
		//alert(word);
		if(word == ""){
		alert("검색어를 입력하십시요");
		}else {
		fm4.elements["searchVO.word"].value = word;
		fm4.submit();
		}
//	}
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
<form name="fm3" method="post" action="/member.do" >
  <input type="hidden" name="method" value="getUserUpdateForm"/>
  <input type="hidden" name="vo.login_id"/>
  <input type="hidden" name="searchVO.menu_sn" value="06"/>
</form>
<form name="fm4" method="post" action="/UniSearch.do">
  <input type="hidden" name="method" value="uniSearch"/>
  <input type="hidden" name="vo.login_id"/>
  <input type="hidden" name="searchVO.word"/>
  <input type="hidden" name="searchVO.menu_sn" value="13"/>
</form>
<body class="LY-WrapperBG08">


<!-- start #LY-Wrapper01 // 공지사항 BG 이미지 -->
<div class="LY-Wrapper08">

<!-- start #LY-Main-Header01 // 공지사항 BG 이미지 --> 
<div class="LY-Header01">

<!-- start # Top-Logo -->
<div class="Top-Logo"><a href="/index.jsp"><img src="/images/header/Logo.gif"  alt="국가과학기술위원회 R&D도우미센터" border="0"/></a></div>
<!-- end # Top-Logo -->

<!-- start # Top-Search -->
<div class="Top-UtileMenu">
<% 
		if(mainLoginVO == null || mainIsLogin == false){
%>
			<!-- 로그아웃 상태 -->
			<ul>
				<li><a href="index.jsp"><img src="/images/header/Util_Menu01.gif" alt="홈으로" border="0" /></a> </li>
				<li><a href="/sitemap.jsp"><img src="/images/header/Util_Menu02.gif" alt="사이트 맵" border="0" /></a> </li>
				<li><a href="/switch.do?prefix=&page=/member.do?method=getUserInsertForm&searchVO.menu_sn=06"><img src="/images/header/Util_Menu06.gif" alt="회원가입" border="0" /></a> </li>
				<li>
					아이디 : <input type="text" name="id" title="아이디" size="10" onkeypress="onEnter()"/>&nbsp;&nbsp;
					패스워드 : <input type="password" name="password" title="패스워드" size="10" onkeypress="onEnter()"/>
					<a href="JavaScript:login();"><img src="/images/header/Util_Menu04.gif" alt="로그인" border="0" /></a> 
				</li>				
				<li class="Search"><img src="/images/header/Util_Menu03.gif" alt="Search" border="0" />
				<input name="word" type="text" id="search" class="searchform"> 
				<a href="JavaScript:goUniSearch();" class="search"><img src="/images/header/Btn_TopSearch.gif" alt="검색" border="0" /></a>
				</li>
			</ul>
<%
		}else{
%>			
			
			<!-- 로그인 상태 -->
			<ul>
				<li><a href="index.jsp"><img src="/images/header/Util_Menu01.gif" alt="홈으로" border="0" /></a> </li>
				<li><a href="/sitemap.jsp"><img src="/images/header/Util_Menu02.gif" alt="사이트 맵" border="0" /></a> </li>
				<li><a href="javascript:update();"><img src="/images/header/Util_Menu07.gif" alt="개인정보변경" border="0" /></a> </li>
				<li><a href="/logout.jsp"><img src="/images/header/Util_Menu05.gif" alt="로그아웃" border="0" /></a> </li>
				<li class="Join-infor"><strong><%= nameKO %></strong>님 접속하셨습니다.</li>
				<li class="Search"><img src="/images/header/Util_Menu03.gif" alt="Search" border="0" />
				<input name="word" type="text" id="search" class="searchform"> 
				<a href="JavaScript:goUniSearch();" class="search"><img src="/images/header/Btn_TopSearch.gif" alt="검색" border="0" /></a>
				</li>
			</ul>
<%
		}
%>	
</div>
<!-- end # Top-Search -->

<!-- start # Top-Menu -->
		<div id="LY-Top">
			<ul id="Top-Menu">
				<li id="Top-Menu-01"><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireMainList&searchVO.menu_sn=01"><img src="/images/Menu/tm01_off.gif" alt="온라인상담" border="0" /></a>
				<ul class="Top-Menu-Sub">
					<li><a href="JavaScript:goInquireForm()"><img src="/images/Menu/tm01_01_off.gif" alt="문의하기" /></a></li>
					<li><a href="JavaScript:goFaq()"><img src="/images/Menu/tm01_02_off.gif" alt="자주묻는질문" /></a></li>
					<li><a href="JavaScript:goInquireList()"><img src="/images/Menu/tm01_03_off.gif" alt="Q&A" /></a></li>
					<li><a href="JavaScript:goOffer()"><img src="/images/Menu/tm01_04_off.gif" alt="제안하기" /></a></li>
				</ul>
				</li>
				<li id="Top-Menu-02"><a href="JavaScript:goData()"><img src="/images/Menu/tm02_off.gif" alt="자료실" border="0" /></a>
				<ul class="Top-Menu-Sub">
						<li><a href="http://rndgate.ntis.go.kr/switch.do?prefix=/un/rndLaw&page=/unRndLaw.do?method=retrieveLawSearchByKey" target="_blank"><img src="../images/Menu/tm02_01_off.gif" alt="법령자료" /></a></li>
						<li><a href="JavaScript:goData()"><img src="../images/Menu/tm02_03_off.gif" alt="기타자료" /></a></li>
		  			</ul>
				</li>
<%
			if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
%>
				<li id="Top-Menu-03"><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=05"><img src="/images/Menu/tm05_off.gif" alt="관리자" border="0" /></a>
				<ul class="Top-Menu-Sub">
					<li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=05"><img src="/images/Menu/tm03_01_off.gif" alt="권한관리" /></a></li>
					<li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=05"><img src="/images/Menu/tm03_02_off.gif" alt="분야관리" /></a></li>
					<li><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm&searchVO.menu_sn=05"><img src="/images/Menu/tm05_03_off.gif" alt="오프라인자료등록" /></a></li>
					<li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatCategory&searchVO.menu_sn=05"><img src="/images/Menu/tm05_04_off.gif" alt="통계정보" /></a></li>
				</ul>
			 	</li>
<%
			} else {
%>
				<li id="Top-Menu-03"><a href="JavaScript:goMypage()"><img src="/images/Menu/tm03_off.gif" alt="마이페이지" border="0" /></a></li>
<%
			}
%>
				<li id="Top-Menu-04"><a href="JavaScript:goNotice()"><img src="/images/Menu/tm04_off.gif" alt="공지사항" border="0" /></a></li>
<%
			if(mainRoleCD.equals("0000B")){
%>
				<li id="Top-Menu-06"><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireOrgList&searchVO.board_type=QNA&searchVO.menu_sn=12"><img src="/images/Menu/tm06_off.gif" alt="기관담당자" border="0" /></a></li>
<%
			}
%>				
			</ul>
			<script type=text/javascript>
			<!--
				var ObjEventTopMenu = new EventTopMenu(0, 0, 0);
			//-->
			</script>
		</div>
		<!--  end #  Top-Menu -->

<!-- start # PageTitle -->
<div class="PageTitle"><img src="../images/header/PageText07.gif" alt="R&amp;D도우미센터 개인정보처리방침 입니다." /></div>
<!-- end # PageTitle -->
</div>
<!-- end #LY-Main-Header -->

<!-- start # LY-ContentTitle -->
<div class="LY-Container">
<!-- end # LY-ContentTitle -->


<!-- start # 개인정보처리방침 -->
<div class="Mem-InforTip">

						<p class="info">
							국가과학기술위원회가 취급하는 모든 개인정보는 「개인정보 보호법」 등 관련 법령상의 개인정보보호 규정을 준수하여 수집·보유·처리되고 있습니다.
						</p>
						</br>
						<table cellpadding="0" cellspacing="0" class="MemTitle" summary="" >
								<tr>									
									<td>국가과학기술위원회는 「개인정보 보호법」에 따라 이용자의 개인정보 보호 및 권익을 보호하고 개인정보화 관련한 이용자의 고충을 원활하게 처리할 수 있도록 다음과 같은 처리 방침을 두고 있습니다.
									</td>
								</tr>
								<tr>
									<td>
									특히, 개인정보를 처리하는 우리 위원회 소관 홈페이지의 경우 「개인정보 보호법」 제30조 제1항 및 동법 시행령 제31조 제1항의 규정에 의하여 해당 홈페이지에 별도의 「개인정보 처리방침」을 정하여 운영하고 있습니다.
									</td>
								</tr>
						</table>
						</br></br>
						<div class="Ctip">
							<h2 class="tipTit">1조. 홈페이지 이용자의 개인정보 보호</h2>
							<p class="Ttxt">
								국가과학기술위원회에서 운영하고 있는 웹사이트는 다음과 같으며, 이 방침은 별도의 설명이 없는 한 국가과학기술위원회에서 운용하는 웹사이트에 적용됨을 알려드립니다.</br>
							    - 영문 웹 사이트 <a href="http://www.nstc.go.kr/eng" target="_blank"  title="새창으로 열기">http://www.nstc.go.kr/eng</a></br>
							    - 모바일 사이트  <a href="http://www.nstc.go.kr/eng" target="_blank"  title="새창으로 열기">http://m.nstc.go.kr</a></br>
								</br>
								<strong>▣ 웹사이트에서 운영하는 보안조치</strong></br>
								  홈페이지의 보안 또는 지속적인 서비스를 위해, 국가과학기술위원회는 네트워크 트래픽의 통제(Monitor)는 물론 불법적으로 정보를 변경하는 등의 시도를 탐지하기 위해 여러 가지 프로그램을 운영하고 있습니다. 여러분이 홈페이지에 기재한 사항은 다른 사람들이 조회 또는 열람할 수도 있으니 게시 시 주의하시기 바랍니다. 이용자 여러분이 홈페이지게 기재한 내용 중 개인정보가 포함되어 있는 경우 개인정보를 삭제 조치 후 게시하여야 합니다. 특정 항목에 해당 사실이 없는 내용에 대하여 삭제합니다.</br>
								</br>
								 <strong>▣ 이메일 및 웹서식 등을 통한 수집정보</strong></br>
								  이용자 여러분은 우편, 전화 또는 온라인 전자서식 등을 통한 전자적 방법을 통해 의사를 표시할 수 있습니다. 이러한 방법의 선택에 있어 몇 가지 유의사항을 알려드립니다.</br>
								  - 홈페이지에 기재한 사항은 다른 사람들이 조회 또는 열람할 수도 있습니다.</br>
								  - 홈페이지에 기재한 사항은 관련법규에 근거하여 필요한 다른 사람과 공유될 수 있으며, 관련 법령의 시행과 정책개발의 자료로도 사용될 수 있습니다. 또한, 이러한 정보는 타 부처와 공유되거나, 필요에 의하여 제공될 수도 있습니다. 홈페이지 보안을 위해 관리적·기술적 노력을 하고 있으나, 만약의 침해사고 시 문제가 될 수 있는 민감한 정보의 기재는 피하여 주시기 바랍니다.</br>
								</br>
								 <strong>▣ 부가서비스 이용 시 수집되는 개인정보</strong></br>
								  국가과학기술위원회가 운영하는 홈페이지에 포함된 링크 또는 배너를 클릭하여 다른 사이트 또는 웹페이지로 옮겨갈 경우, 개인정보보호방침은 그 사이트 운영기관이 게시한 방침이 적용됨으로 새로 방문한 사이트의 방침을 확인하시기 바랍니다.
							</p>
							<br />
							<br />

							<h2 class="tipTit">2조. 개인정보의 처리목적, 개인정보의 처리 및 보유기간, 처리하는 개인정보의 항목</h2>
							<p class="Ttxt">
								☞ 개인정보보호 종합지원포털 (<a href="http://www.privacy.go.kr" target="_blank"  title="새창으로 열기">www.privacy.go.kr</a>) ▶ 개인정보민원 ▶ 개인정보의 열람 등 요구 ▶ 개인정보파일 목록 검색 ▶ 기관명에 “국가과학기술위원회” 입력 후 조회
							</p>
							<br />
							<br />
							<h2 class="tipTit">3조. 개인정보의 제3자 제공</h2>
							<p class="Ttxt">
								국가과학기술위원회는 원칙적으로 정보주체의 개인정보를 수집·이용 목적으로 명시한 범위 내에서 처리하며, 다음의 경우를 제외하고는 정보주체의 사전 동의 없이는 본래의 목적 범위를 초과하여 처리하거나 제3자에게 제공하지 않습니다.<br/>
								<br/>
								1. 정보주체로부터 별도의 동의를 받는 경우<br/>		
								</br>
								2. 법률에 특별한 규정이 있는 경우<br/>
								</br>
								3. 정보주체 또는 법정대리인이 의사표시를 할 수 없는 상태에 있거나 주소불명 등으로 사전 동의를 받을 수 없는 경우로서 명백히 정보주체 또는 제3자의 급박한 생명, 신체, 재산의 이익을 위하여 필요하다고 인정되는 경우<br/>
								</br>
								4. 통계작성 및 학술연구 등의 목적을 위하여 필요한 경우로서 특정 개인을 알아 볼 수 없는 형태로 개인정보를 제공하는 경우<br/>
								</br>
								5. 개인정보를 목적 외의 용도로 이용하거나 이를 제3자에게 제공하지 아니하면 다른 법률에서 정하는 소관 업무를 수행할 수 없는 경우로서 보호위원회의 심의·의결을 거친 경우<br/>
								</br>
								6. 조약, 그 밖의 국제협정의 이행을 위하여 외국정보 또는 국제기구에 제공하기 위하여 필요한 경우<br/>
								</br>
								7. 범죄의 수사와 공소의 제기 및 유지를 위하여 필요한 경우<br/>
								</br>
								8. 법원의 재판업무 수행을 위하여 필요한 경우<br/>
								</br>
								9. 형 및 감호, 보호처분의 집행을 위하여 필요한 경우<br/>
								</br>
							</p>
							<br />
							<br />
							<h2 class="tipTit">4조. 개인정보 처리 위탁</h2>
							<p class="Ttxt">
    							국가과학기술위원회는 개인정보의 처리업무를 위탁하는 경우 다음의 내용이 포함된 문서에 의하여 처리하고 있습니다.<br/>
								<br/>
								1. 위탁업무 수행 목적 외 개인정보의 처리 금지에 관한 사항<br/>
								</br>
								2. 개인정보의 관리적·기술적 보호조치에 관한 사항<br/>
								</br>
								3. 개인정보의 안전관리에 관한 사항<br/>
								<br/>
								- 위탁업무의 목적 및 범위, 재위탁 제한에 관한 사항, 개인정보 안전성 확보 조치에 관한 사항, 위탁업무와 관련하여 보유하고 있는 개인정보의 관리현황점검 등 감독에 관한 사항, 수탁자가 준수하여야할 의무를 위반한 경우의 손해배상책임에 관한 사항<br/>
								<br/>
								또한, 위탁하는 업무의 내용과 개인정보 처리업무를 위탁받아 처리하는 자(“수탁자”)에 대하여 해당 홈페이지에 공개하고 있습니다.
							</p>
							<br />
							<br />
							<h2 class="tipTit">5조. 정보주체의 권리·의무 및 그 행사 방법</h2>
							<p class="Ttxt">
								정보주체는 다음과 같은 권리를 행사 할 수 있으며, 만14세 미만 아동의 법정대리인은 그 아동의 개인정보에 대한 열람, 정정·삭제, 처리정지를 요구할 수 있습니다.<br/>
								<br/>
								<strong>▣ 개인정보 열람 요구</strong><br/>
								  국가과학기술위원회에서 보유하고 있는 개인정보파일은 「개인정보 보호법」 제35조(개인정보의 열람)에 따라 자신의 개인정보에 대한 열람을 요구할 수 있습니다. 다만, 개인정보 열람 요구는 제35조제5항에 의하여 다음과 같이 제한될 수 있습니다.<br/>
								<br/>
								1. 법률에 따라 열람이 금지되거나 제한되는 경우<br/>
								<br/>
								2. 다른 사람의 생명·신체를 해할 우려가 있거나 다른 사람의 재산과 그 밖의 이익을 부당하게 침해할 우려가 있는 경우<br/>
								<br/>
								3. 공공기관이 다음 각 목의 어느 하나에 해당하는 업무를 수행할 때 중대한 지장을 초래하는 경우</br>
								<br/>
								가. 조세의 부과·징수 또는 환급에 관한 업무</br>
								<br/>
								나. 「초·중등교육법」 및 「고등교육법」에 따른 각급 학교, 「평생교육법」에 따른 평생교육시설, 그 밖의 다른 법률에 따라 설치된 고등교육기관에서의 성적 평가 또는 입학자 선발에 관한 업무</br>
								<br/>
								다. 학력·기능 및 채용에 관한 시험, 자격 심사에 관한 업무</br>
								<br/>
								라. 보상금·급부금 산정 등에 대하여 진행 중인 평가 또는 판단에 관한 업무</br>
								<br/>
								마. 다른 법률에 따라 진행 중인 감사 및 조사에 관한 업무</br>
								</br>
								<strong>▣ 개인정보 정정·삭제 요구</strong></br>
									국가과학기술위원회에서 보유하고 있는 개인정보파일은 「개인정보 보호법」 제36조(개인정보의 정정·삭제)에 따라 정정·삭제를 요구할 수 있습니다. 다만, 다른 법령에서 그 개인정보가 수집 대상으로 명시되어 있는 경우에는 그 삭제를 요구할 수 없습니다.
									</br>
								</br>
								<strong>▣ 개인정보 처리정지 요구</strong></br>
									국가과학기술위원회에서 보유하고 있는 개인정보파일은 「개인정보 보호법」 제37조(개인정보의 처리정지 등)에 따라 처리정지를 요구할 수 있습니다. 다만, 개인정보 처리정지 요구 시 법 제37조 제2항에 의하여 처리정지 요구가 거절될 수 있습니다.</br>
								1. 법률에 특별한 규정이 있거나 법령상 의무를 준수하기 위하여 불가피한 경우</br>
								</br>
								2. 다른 사람의 생명·신체를 해할 우려가 있거나 다른 사람의 재산과 그 밖의 이익을 부당하게 침해할 우려가 있는 경우</br>
								</br>
								3. 공공기관이 개인정보를 처리하지 아니하면 다른 법률에서 정하는 소관 업무를 수행할 수 없는 경우</br>
								</br>
								4. 개인정보를 처리하지 아니하면 정보주체와 약정한 서비스를 제공하지 못하는 등 계약의 이행이 곤란한 경우로서 정보주체가 그 계약의 해지 의사를 명확하게 밝히지 아니한 경우</br>
							</p>
							<br />
							<br />
							<h2 class="tipTit">6조. 개인정보의 파기</h2>
							<p class="Ttxt">
    							국가과학기술위원회는 원칙적으로 개인정보 처리목적이 달성된 개인정보는 지체없이 파기합니다. 파기의 절차, 기한 및 방법은 다음과 같습니다.<br/>
								1. 파기 절차<br/>								
								<br/>
								개인정보는 목적 달성 후 즉시 또는 별도의 공간에 옮겨져 내부 방침 및 기타 관련법령에 따라 일정기간 저장된 후 파기됩니다. 별도의 공간으로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 다른 목적으로 이용되지 않습니다.<br/>
								<br/>
								2. 파기 기한 및 파기 방법<br/>								
								<br/>
								보유기간이 만료되었거나 개인정보의 처리목적달성, 해당 업무의 폐지 등 그 개인정보가 불필요하게 되었을 때에는 지체없이 파기합니다. 전자적 파일형태의 정보는 기록을 재생할 수 없는 기술적 방법을 사용합니다. 종이에 출력된 대인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다.
								<br/>
							</p>
							<br />
							<br />
							<h2 class="tipTit">7조. 개인정보의 안전성 확보 조치</h2>
							<p class="Ttxt">
    							1. 개인정보 취급직원의 최소화 및 교육<br/>
								<br/>
								개인정보를 취급하는 직원은 반드시 필요한 인원에 한하여 지정 · 관리하고 있으며, 취급직원을 대상으로 안전한 관리를 위한 교육을 실시하고 있습니다.<br/>
								<br/>
								2. 개인정보에 대한 접근 제한<br/>
								<br/>
								개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여·변경·말소를 통하여 개인정보에 대한 접근통제를 위한 필요한 조치를 하고 있으며, 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.<br/>
								<br/>
								3. 접속기록의 보관<br/>
								<br/>
								개인정보처리시스템에 접속한 기록(웹 로그, 요약정보 등)을 최소 6개월 이상 보관·관리하고 있습니다.<br/>
								<br/>
								4. 개인정보의 암호화<br/>
								<br/>
								개인정보는 암호화 등을 통해 안전하게 저장 및 관리되고 있습니다. 또한 중요한 데이터는 저장 및 전송 시 암호화하여 사용하는 등의 별도 보안기능을 사용하고 있습니다.<br/>
								<br/>
								5. 보안프로그램 설치 및 주기적 점검·갱신<br/>
								<br/>
								해킹이나 컴퓨터 바이러스 등에 의한 개인정보 유출 및 훼손을 막기 위하여 보안프로그램을 설치하고, 주기적으로 갱신·점검하고 있습니다.<br/>
								<br/>
								6. 비인가자에 대한 출입 통제<br/>
								<br/>
								개인정보를 보관하고 있는 개인정보시스템의 물리적 보관 장소를 별도로 두고, 이에 대해 출입통제 절차를 수립, 운영하고 있습니다.<br/>
							</p>
							<br />
							<br />
							<h2 class="tipTit">8조. 권익침해 구제 방법</h2>
							<p class="Ttxt">
    							개인정보에 관한 권리 또는 이익을 침해받은 사람은 개인정보침해 신고센터 등으로 침해사실을 신고 할 수 있습니다.<br/>
								<br/>
								☞ 개인정보침해신고센터 : (국번없이) 118 (내선2번)<br/>
								<br/>
								☞ 정보보호마크인증위원회 : 02-580-0533~4<br/>
								<br/>
								☞ 대검찰청 사이버범죄수사단 : 02-3480-3573<br/>
								<br/>
								☞ 경찰청 사이버테러대응센터 : 02-1566-0112<br/>
								<br/>
								또한, 개인정보의 열람, 정정·삭제, 처리정지 등에 대한 정보주체자의 요구에 대하여 공공기관의 장이 행한 처분 또는 부작위로 인하여 권리 또는 이익을 침해 받은 자는 행정심판법이 정하는 바에 따라 행정심판을 청구할 수 있습니다.<br/>
								<br/>
								☞ 중앙행정심판위원회(<a href="http://www.simpan.go.kr" target="_blank"  title="새창으로 열기">www.simpan.go.kr</a>)의 전화번호 안내 참조<br/>
							</p>
							<br />
							<br />
							<h2 class="tipTit">9조. 개인정보 보호책임자 및 담당자 연락처</h2>
							<p class="Ttxt">
								가. 국가과학기술위원회 개인정보 보호책임자 : 기획관리관 최호권<br/>
								<br/>
								나. 개인정보보호 담당부서 : 행정관리담당관실<br/>
								<br/>
								- 연락처 : 02-724-8593, FAX : 02-724-8599<br/>
								- 이메일 : mhkim@nstc.go.kr<br/>
								<br/>
    							다. 전화상담 가능시간<br/>
								<br/>
								- 월~금 : 09:00~18:00 (12:00~13:00, 점심시간 제외)<br/>
								- 휴무일 : 토ㆍ일요일, 법정 공휴일<br/>
								- 인터넷(웹사이트<a href="http://www.nstc.go.kr" target="_blank"  title="새창으로 열기"> www.nstc.go.kr</a>)을 통한 상담ㆍ신고는 연중 1일 24시간 가능<br/>
							</p>
							<br />
							<br />
							<h2 class="tipTit">10조. 개인정보처리방침 변경</h2>
							<p class="Ttxt">
								이 개인정보처리방침은 2012. 5. 부터 적용됩니다.<br/>
								- 「개인정보 보호법」 제30조(개인정보 처리방침의 수립 및 공개) 및 제32조(개인정보파일의 등록 및 공개) 등과 관련 사항을 추가 및 변경<br/>
								<br/>
								이전의 개인정보처리방침은 아래에서 확인할 수 있습니다.<br/>
    							- 2011. 12. ~ 2012. 5. 적용 지침 <a href="/sitetip/info_old.jsp">(클릭)</a><br/>
							</p>							
						</div>
</div>
<!-- end # 개인정보처리방침 -->













</div>
<!-- end # LY-Container -->



</div>
<!-- end #LY-Wrapper -->


<!-- start # LY-Footer -->
<div class="LY-Footer">

<div class="Footer-Wrap">
		<!-- Logo -->
		<ul class="Logo">
			<li><a href="#"><img src="/images/footer/Footer_Logo.gif" border="0" alt="국가과학기술위원회" /></a></li>
		</ul>
		<!-- Add-Infor -->
		<ul class="Add-Infor">
			<li><a href="/info.jsp"><img src="/images/footer/Footer_JoinInfor.gif" alt="개인정보처리방침" border="0" /></a> <a href="/email.jsp"><img src="/images/footer/Footer_MailNo.gif" alt="무단이메일수집거부" border="0" /></a></li>
			<li><img src="/images/footer/Footer_ADD.gif" alt="110ㆍ700 서울시 종로구 새문안로 82 (신문로1가 116) S타워 18층  /  Tel. 02.724.8700  |  Fax. 02.724.8759" /></li>
			<li><img src="/images/footer/Footer_Copyright.gif" alt="COPYRIGHT(C)2012 NATIONAL SCIENCE & TECHNOLOGY COMMISSION ALL RIGHTS RESERVED,." /></li>
		</ul>
		<!-- Link -->
		<form name="siteUrlForm" method="post">
		<ul class="Footer-Link">
			<li><select name="siteurl1" id="siteurl1" title="R&D관련부처 바로가기">
			  	<option value="#" selected="selected">R&D관련부처 바로가기</option>
				<option value="http://www.mest.go.kr">교육과학기술부 </option>
				<option value="http://www.mcst.go.kr">문화체육관광부</option>
				<option value="http://www.maf.go.kr">농림수산식품부</option>
				<option value="http://www.mke.go.kr">지식경제부</option>
				<option value="http://www.mohw.go.kr">보건복지가족부</option>
				<option value="http://www.me.go.kr">환경부</option>
				<option value="http://www.moct.go.kr">국토해양부</option>
				<option value="http://www.dapa.go.kr">방위사업청</option>
				<option value="http://www.nema.go.kr">소방방재청</option>
				<option value="http://www.cha.go.kr">문화재청</option>
				<option value="http://www.rda.go.kr">농촌진흥청</option>
				<option value="http://www.forest.go.kr">산림청</option>
				<option value="http://www.smba.go.kr">중소기업청</option>
				<option value="http://www.kfda.go.kr">식품의약품안전청</option>
				<option value="http://www.kma.go.kr">기상청</option>
			</select> 
			<a href="JavaScript:goSiteUrl(1)"><img src="/images/btn/btn_LinkMove.gif" alt="이동" border="0" /></a></li>
			<li><select name="siteurl2" id="siteurl2" title="전문기관 바로가기">
			  	<option value="#" selected="selected">전문기관 바로가기</option>
				<option value="http://www.nrf.re.kr">한국연구재단</option>
				<option value="http://www.kocca.kr">한국문화콘텐츠진흥원</option>
				<option value="http://www.ipet.re.kr">농림수산식품기획평가원</option>
				<option value="http://www.rda.go.kr">농촌진흥청</option>
				<option value="http://www.keit.re.kr">한국산업기술평가관리원</option>
				<option value="http://www.nipa.re.kr">정보통신산업진흥원</option>
				<option value="http://www.khidi.or.kr">한국보건산업진흥원</option>
				<option value="http://www.keiti.re.kr">한국환경산업기술원</option>
				<option value="http://www.kicttep.re.kr">한국건설교통기술평가원</option>
				<option value="http://www.kimst.re.kr">한국해양과학기술진흥원</option>
				<option value="http://www.dtaq.re.kr">국방기술품질원</option>
				<option value="http://www.kma.go.kr">기상청</option>
				<option value="http://www.kfda.go.kr">식품의약품품안전청</option>
				<option value="http://www.forest.go.kr">산림청</option>
				<option value="http://www.nema.go.kr">소방방재청</option>
			</select> 
			<a href="JavaScript:goSiteUrl(2)"><img src="/images/btn/btn_LinkMove.gif" alt="이동" border="0" /></a></li>
			<li><select name="siteurl3" id="siteurl3" title="관련기관 바로가기">
			  	<option value="#" selected="selected">관련기관 바로가기</option>
				<option value="#">----성과물전담기관-------</option>
				<option value="http://www.socop.or.kr/">컴퓨터프로그램보호위원회(소프트웨어) </option>
				<option value="http://kctc.kribb.re.kr/">한국생명공학연구원 생물자원센터(생물소재) </option>
				<option value="http://scholar.ndsl.kr/">한국과학기술정보연구원(논문) </option>
				<option value="http://www.kipi.or.kr/">특허청 한국특허정보원(특허) </option>
				<option value="http://report.ndsl.kr/">한국과학기술정보연구원(보고서) </option>
				<option value="http://www.kobic.re.kr/">한국생명공학연구원 국가생물자원정보관리센터(생물정보) </option>
				<option value="http://www.chembank.org/">한국화학연구원 한국화합물은행(화합물) </option>
				<option value="http://nfec.ntis.go.kr/">한국기초과학지원연구원(장비 및 기자재) </option>
				<option value="#">---------기타-----------</option>
				<option value="http://www.nstc.go.kr/">국가과학기술위원회 </option>
				<option value="http://sntnet.mest.go.kr/">교육과학기술부(SnT-Net) </option>
				<option value="http://www.ndsl.kr/">과학기술정보통합서시스(NDSL) </option>
				<option value="http://www.kosen21.org/">한국과학기술정보연구원(한민족과학기술자네트워크) </option>
				<option value="http://society.kisti.re.kr/">한국과학기술정보연구원(과학기술학회마을) </option>
				<option value="http://fact.kisti.re.kr/">한국과학기술정보연구원(과학기술 사실정보 데이터베이스) </option>
				<option value="http://www.ntb.kr/">한국기술거래소(국가기술은행) </option>
				<option value="http://www.kird.re.kr/">연구개발인력교육원 </option>
				</select> 
				<a href="JavaScript:goSiteUrl(3)"><img src="/images/btn/btn_LinkMove.gif" alt="이동" border="0" /></a>
			</li>
		</ul>
		</form>
		<form name="fm4" method="post" action="/UniSearch.do">
		  <input type="hidden" name="method" value="uniSearch"/>
		  <input type="hidden" name="vo.login_id"/>
		  <input type="hidden" name="searchVO.word"/>
		  <input type="hidden" name="searchVO.menu_sn" value="13"/>
		</form>
	</div>
	<!-- end # LY-Footer -->
</div>
<!-- end #LY-Wrapper -->
</body>
</html>