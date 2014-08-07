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
				<li><a href="/index.jsp"><img src="/images/header/Util_Menu01.gif" alt="홈으로" border="0" /></a> </li>
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
				<li><a href="/index.jsp"><img src="/images/header/Util_Menu01.gif" alt="홈으로" border="0" /></a> </li>
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
				<li id="Top-Menu-01"><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireMainList&searchVO.menu_sn=01"><img src="/images/Menu/tm01_off.gif" alt="온라인 상담" border="0" /></a>
				<ul class="Top-Menu-Sub">
					<li><a href="JavaScript:goInquireForm()"><img src="/images/Menu/tm01_01_off.gif" alt="문의하기" /></a></li>
					<li><a href="JavaScript:goFaq()"><img src="/images/Menu/tm01_02_off.gif" alt="자주 묻는 질문" /></a></li>
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
<div class="PageTitle"><img src="../images/header/PageText_09.gif" alt="R&amp;D도우미센터 무단이메일수집거부 입니다." /></div>
<!-- end # PageTitle -->
</div>
</br>
</br>
<!-- start # 개인정보처리방침 -->
<div class="Mail-InforTip">

						<table cellpadding="0" cellspacing="0" class="MemTitle " summary="" >
								<tr>									
									<td class="info">본 웹사이트에 게시된 이메일 주소가 전자우편 수집 프로그램이나 그 밖의 기술적 장치를 이용하여 무단으로 수집되는 것을 거부하며, 이를 위반시 정보통신망법에 의해 형사처벌됨을 유념하시기 바랍니다.
									</td>
								</tr>
								<tr>
									<td class="Infor01">
									<일부개정 2008.2.29 법률 8867호>
									</td>
								</tr>
						</table>
						</br></br>
                          <h2 class="tipTit">정보통신망 이용촉진 및 정보보호 등에 관한 법률</h2>
						  <ul class="tipList">
                            <li class="tipList2">제50조의2 (전자우편주소의 무단 수집행위 등 금지)
                              <ol class="tipListo">
                                  <li> ① 누구든지 인터넷 홈페이지 운영자 또는 관리자의 사전동의 없이 인터넷 홈페이지에서 자동으로 전자우편주소를 <br/>
                                      <span class="tipList4">수집하는 프로그램 그 밖의 기술적 장치를 이용하여 전자우편주소를 수집하여서는 아니된다.</span><br/>
                                      <span class="tipList4">&lt;개정 2004.12.30&gt;</span> </li>
                                <li>② 누구든지 제1항의 규정을 위반하여 수집된 전자우편주소를 판매·유통하여서는 아니된다. </li>
                                <li>③ 누구든지 제1항 및 제2항의 규정에 의하여 수집·판매 및 유통이 금지된 전자우편주소임을 알고 이를 정보전송에 <br/>
                                    <span class="tipList4"> 이용하여서는 아니된다.</span></li>
                              </ol>
                            </li>
						    <li class="tipList2">제50조의2 (전자우편주소의 무단 수집행위 등 금지)
						      <ol class="tipListo">
                                  <li>① 제8조제4항의 규정을 위반하여 표시·판매 또는 판매할 목적으로 진열한 자</li>
						        <li>② 제44조의7제1항제1호의 규정을 위반하여 음란한 부호·문언·음향·화상 또는 영상을 배포·판매·임대하거나 공연히 전시한 자</li>
						        <li>③ 제44조의7제1항제3호의 규정을 위반하여 공포심이나 불안감을 유발하는 부호·문언·음향·화상 또는 영상을<br/>
						            <span class="tipList4"> 반복적으로 상대방에게 도달하게 한 자 </span></li>
						        <li>④ 제50조제6항의 규정을 위반하여 기술적 조치를 한 자 </li>
						        <li>⑤ 제50조의2의 규정을 위반하여 전자우편 주소를 수집·판매·유통 또는 정보전송에 이용한 자 </li>
						        <li>⑥ 제50조의8의 규정을 위반하여 광고성 정보를 전송한 자 </li>
						        <li>⑦ 제53조제4항을 위반하여 등록사항의 변경등록 또는 사업의 양도·양수 또는 합병·상속의 신고를 하지 아니한 자 </li>
					          </ol>
					        </li>
					      </ul>
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