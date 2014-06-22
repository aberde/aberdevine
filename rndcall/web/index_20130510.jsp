<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@ taglib uri="/tags/taglibs-log" prefix="log"%>
<%@ page import="kr.go.rndcall.mgnt.login.LoginVO" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="kr.go.rndcall.mgnt.common.BaseSqlDAO"%>
<%@ page import="kr.go.rndcall.mgnt.inquire.vo.*" %>

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
  
  	String dsname = "java:/jdbc/callcenter";
	BaseSqlDAO dao = new BaseSqlDAO();
	Connection conn = null;
	PreparedStatement pstmt = null;
	InquireVO vo = null;
	ResultSet rs = null;
	ArrayList voList = new ArrayList();
	ArrayList voList1 = new ArrayList();
	
	try {
		conn = dao.getConnection(dsname);
		
		String query="";
		query = "INSERT INTO RNDCALL_VISIT(SEQ, VISIT_IP, VISIT_DT, VISIT_ID ) VALUES(RNDCALL_VISIT_SEQ.NEXT_VALUE,?,SYSDATE,?)";
		
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, request.getRemoteAddr());
		pstmt.setString(2, login_id);	
		pstmt.execute();
		
		/*
		query = "";
		query  = " SELECT SEQ, BOARD_TYPE, TITLE, REG_DT1 ";
		query += " FROM ( ";
		query += " 		SELECT ROWNUM RNUM, SEQ, BOARD_TYPE, TITLE, REG_DT1, REG_DT  ";
		query += "         FROM ( ";
		query += " 				SELECT SEQ, BOARD_TYPE, TITLE, REG_DT1, REG_DT  ";
		query += "                 FROM ( ";
		query += " 						SELECT Q.SEQ, Q.BOARD_TYPE, Q.TITLE,  ";
		query += "                                TO_CHAR(Q.REG_DT,'yyyy.mm.dd') AS REG_DT1,  ";
		query += "                                Q.REG_DT AS REG_DT  ";
		query += "                         FROM RNDCALL_BOARD_QUESTION Q,  ";
		query += "                              RNDCALL_BOARD_ANSWER A  ";
		query += "                         WHERE Q.DEL_YN='N'  ";
		query += "                               AND Q.SEQ = A.Q_SEQ(+)  ";
		query += "                               AND Q.BOARD_TYPE IN('FAQ','INS','ETC','OFFER')  ";
		query += "                         UNION ALL  ";
		query += " 						SELECT Q.SEQ, Q.BOARD_TYPE, Q.TITLE,  ";
		query += "                                TO_CHAR(Q.REG_DT,'yyyy.mm.dd') AS REG_DT1,  ";
		query += "                                Q.REG_DT AS REG_DT  ";
		query += "                         FROM RNDCALL_BOARD_QUESTION Q,  ";
		query += "                              RNDCALL_BOARD_ANSWER A  ";
		query += "                         WHERE Q.DEL_YN='N' ";
		query += "                               AND Q.SEQ = A.Q_SEQ  ";
		query += "                               AND Q.BOARD_TYPE = 'QNA'  ";
		query += "                 )  ";
		query += " 				ORDER BY reg_dt DESC  ";
		query += "         )  ";
		query += " )  ";
		query += " WHERE RNUM BETWEEN 1 AND 4 ";
		*/
		
		query = "";
		query  = " SELECT SEQ, BOARD_TYPE, TITLE, REG_DT1, REG_DT ";
		query += "  FROM ( ";
		query += "        (SELECT Q.SEQ, Q.BOARD_TYPE, Q.TITLE, ";
		query += "               TO_CHAR(Q.REG_DT,'yyyy.mm.dd') AS REG_DT1, ";
		query += "               Q.REG_DT AS REG_DT ";
		query += "          FROM RNDCALL_BOARD_QUESTION Q LEFT OUTER JOIN RNDCALL_BOARD_ANSWER A ON Q.SEQ = A.Q_SEQ ";
		query += "         WHERE Q.DEL_YN='N' ";
		query += "           AND Q.BOARD_TYPE = 'FAQ' ";
		query += "           AND Q.OPEN_YN = 'Y' ";
		query += "           AND Q.INSERT_TYPE = 'ONLINE' ";
		query += "         ORDER BY reg_dt DESC limit 0, 4) ";
		query += "         UNION ALL ";
		query += "        (SELECT Q.SEQ, Q.BOARD_TYPE, Q.TITLE, ";
		query += "               TO_CHAR(Q.REG_DT,'yyyy.mm.dd') AS REG_DT1, ";
		query += "               Q.REG_DT AS REG_DT ";
		query += "          FROM RNDCALL_BOARD_QUESTION Q LEFT OUTER JOIN RNDCALL_BOARD_ANSWER A ON Q.SEQ = A.Q_SEQ ";
		query += "         WHERE Q.DEL_YN='N' ";
		query += "           AND Q.OPEN_YN = 'Y' ";
		query += "           AND Q.INSERT_TYPE = 'ONLINE' ";
		query += "           AND Q.BOARD_TYPE = 'DATA' ";
		query += "		 ORDER BY reg_dt DESC limit 0, 4) ";
		query += "         UNION ALL ";
		query += "        (SELECT Q.SEQ, Q.BOARD_TYPE, Q.TITLE, ";
		query += "               TO_CHAR(Q.REG_DT,'yyyy.mm.dd') AS REG_DT1, ";
		query += "               Q.REG_DT AS REG_DT ";
		query += "          FROM RNDCALL_BOARD_QUESTION Q INNER JOIN RNDCALL_BOARD_ANSWER A ON Q.SEQ = A.Q_SEQ ";
		query += "         WHERE Q.DEL_YN='N' ";
		query += "           AND Q.BOARD_TYPE = 'QNA' ";
		query += "           AND Q.OPEN_YN = 'Y' ";
		query += "           AND Q.INSERT_TYPE = 'ONLINE' ";
		query += "		 ORDER BY reg_dt DESC limit 0, 4) ";
		query += "       ) AS A";
		query += " ORDER BY reg_dt DESC limit 0, 4 ";
		
		System.out.println("Query :::: " +query);
		pstmt = conn.prepareStatement(query);
	    rs = pstmt.executeQuery();
		
		while (rs.next()) {
			vo = new InquireVO();
			vo.setSeq(rs.getString("SEQ"));
			vo.setTitle(rs.getString("TITLE"));
			vo.setBoard_type(rs.getString("BOARD_TYPE"));
			vo.setReg_dt(rs.getString("REG_DT1"));
			
			voList.add(vo);
		}
		
		System.out.println("VoList Size ::::: " + voList.size());
		pstmt.close();
		rs.close();
		
		/*
		query = "";
		query  = " SELECT SEQ, BOARD_TYPE, TITLE, REG_DT1 ";
		query += " FROM ( ";
		query += " 		SELECT ROWNUM RNUM, SEQ, BOARD_TYPE, TITLE, REG_DT1, REG_DT  ";
		query += "         FROM ( ";
		query += " 					SELECT Q.SEQ, Q.BOARD_TYPE, Q.TITLE,  ";
		query += "                         TO_CHAR(Q.REG_DT,'yyyy.mm.dd') AS REG_DT1,  ";
		query += "                         Q.REG_DT AS REG_DT  ";
		query += "                  FROM RNDCALL_BOARD_QUESTION Q  ";
		query += "                  WHERE Q.DEL_YN='N'  ";
		query += "                        AND Q.BOARD_TYPE ='NOTICE' ";
		query += " 				    ORDER BY reg_dt DESC  ";
		query += "         )  ";
		query += " )  ";
		query += " WHERE RNUM BETWEEN 1 AND 4 ";
		*/
		
		//공지사항 수정쿼리
		query = "";
		query = " SELECT Q.SEQ, Q.BOARD_TYPE, Q.TITLE, ";
		query += "       TO_CHAR(Q.REG_DT,'yyyy.mm.dd') AS REG_DT1,";
		query += "       Q.REG_DT AS REG_DT";
		query += "  FROM RNDCALL_BOARD_QUESTION Q";
		query += " WHERE Q.DEL_YN='N'";
		query += "   AND Q.BOARD_TYPE ='NOTICE'";
		query += "ORDER BY reg_dt DESC limit 0, 4";
		
		pstmt = conn.prepareStatement(query);
	    rs = pstmt.executeQuery();
		
		while (rs.next()) {
			vo = new InquireVO();
			vo.setSeq(rs.getString("SEQ"));
			vo.setTitle(rs.getString("TITLE"));
			vo.setBoard_type(rs.getString("BOARD_TYPE"));
			vo.setReg_dt(rs.getString("REG_DT1"));
			
			voList1.add(vo);
		}
		pstmt.close();
		rs.close();
		
	} catch (Exception ee) {
		ee.printStackTrace(System.out);
		if(rs != null) rs.close();
		if(rs != null) pstmt.close();
		if(conn != null) conn.close();		
	} finally {
		if(rs != null) rs.close();
		if(pstmt != null) pstmt.close();
		if(conn != null) conn.close();
	}
%>

<link rel="stylesheet" type="text/css" href="/css/Base.css" />
<link rel="stylesheet" type="text/css" href="/css/Layout.css" />
<link rel="stylesheet" type="text/css" href="/css/Header.css" />
<link rel="stylesheet" type="text/css" href="/css/main.css" />
<link rel="stylesheet" type="text/css" href="/css/Footer.css" />

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>R&amp;D도우미센터</title>
<script language="JavaScript" src="/js/prototype.js"></script>
<script language="JavaScript" src="/js/Field.js"></script>
<script language="JavaScript" src="/js/EventMenu.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function goData(){
	document.location.href="/switch.do?prefix=/data&page=/Data.do?method=dataList&searchVO.menu_sn=02";
} 

function goNotice(){
	document.location.href="/switch.do?prefix=/notice&page=/Notice.do?method=noticeList&searchVO.menu_sn=04";
}

function goMypage(){
<% 
	if(mainLoginVO != null && mainIsLogin != false){
%>		
			document.location.href="/switch.do?prefix=/mypage&page=/Mypage.do?method=getMypageList&searchVO.menu_sn=03";
<% 
	}else{
%>		
		alert("로그인후 이용하실수 있습니다.");	
<%
	}
%>
}


function login() {	
	
	var winfeatures = "toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=525,height=305";
	var url = "/login.jsp?returnURL=" + escape(document.location.href);

	loginWin = window.open( url, "login", winfeatures);
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
			url ="/switch.do?prefix=/offer&page=/Offer.do?method=offerList&searchVO.menu_sn=01";
		}else{
			url ="/switch.do?prefix=/offer&page=/Offer.do?method=offerInsertForm&searchVO.menu_sn=01";
		}
		document.location.href = url;
	}
}

//자주하느질문 링크
function goFaq(){
	var url ="/switch.do?prefix=/faq&page=/Faq.do?method=faqList&searchVO.menu_sn=01";
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
</head>
<form name="fm2" method="post" action="/login.do" >
  <input type="hidden" name="method" value="login"/>
  <input type="hidden" name="vo.login_id"/>
  <input type="hidden" name="vo.password"/>
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
  <input type="hidden" name="searchVO.menu_sn" value="08"/>
</form>
<body class="LY-Main-BG">
<!-- start #LY-Wrapper -->
<div class="LY-Wrapper">
	<!-- start #LY-Main-Header --> 
	<div class="LY-Main-Header">
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
				<li><a href="/sitemap.jsp?searchVO.menu_sn=08"><img src="/images/header/Util_Menu02.gif" alt="사이트 맵" border="0" /></a> </li>
				<li><a href="/switch.do?prefix=&page=/member.do?method=getUserInsertForm&searchVO.menu_sn=06"><img src="/images/header/Util_Menu06.gif" alt="회원가입" border="0" /></a> </li>
				<li><a href="JavaScript:login();"><img src="/images/header/Util_Menu04.gif" alt="로그인" border="0" /></a></li>				
				<li class="Search"><img src="/images/header/Util_Menu03.gif" alt="Search" border="0" />
				<input name="word" type="text" id="search" class="searchform" onkeydown="checkKey();"/> 
				<a href="JavaScript:goUniSearch();" class="search"><img src="/images/header/Btn_TopSearch.gif" alt="검색" border="0" /></a>
				</li>
			</ul>
<%
		}else{
%>			
			
			<!-- 로그인 상태 -->
			<ul>
				<li><a href="index.jsp"><img src="/images/header/Util_Menu01.gif" alt="홈으로" border="0" /></a> </li>
				<li><a href="/sitemap.jsp?searchVO.menu_sn=08"><img src="/images/header/Util_Menu02.gif" alt="사이트 맵" border="0" /></a> </li>
				<li><a href="javascript:update();"><img src="/images/header/Util_Menu07.gif" alt="개인정보변경" border="0" /></a> </li>
				<%
					if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
				%>
					<li><a href="/switch.do?prefix=&page=/discussion.do?method=retrieveDiscussDetail&searchVO.menu_sn=09"><img src="/images/header/Util_Menu08.gif" alt="커뮤니티" border="0" /></a> </li>
				<%
					}
				%>
				<li><a href="/logout.jsp"><img src="/images/header/Util_Menu05.gif" alt="로그아웃" border="0" /></a> </li>
				<li class="Join-infor"><strong><%= nameKO %></strong>님 접속하셨습니다.</li>
				<li class="Search"><img src="/images/header/Util_Menu03.gif" alt="Search" border="0" />
				<input name="word" type="text" id="search" class="searchform" onkeydown="checkKey();"/> 
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
				<li id="Top-Menu-02"><a href="JavaScript:goData()"><img src="/images/Menu/tm02_off.gif" alt="자료실" border="0" /></a> </li>
<%
			if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
%>
				<li id="Top-Menu-03"><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=05"><img src="/images/Menu/tm05_off.gif" alt="관리자" border="0" /></a>
				<ul class="Top-Menu-Sub">
					<li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=05"><img src="/images/Menu/tm05_01_off.gif" alt="권한관리" /></a></li>
					<li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=05"><img src="/images/Menu/tm05_02_off.gif" alt="분야관리" /></a></li>
					<li><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm&searchVO.menu_sn=05"><img src="/images/Menu/tm05_03_off.gif" alt="오프라인자료등록" /></a></li>
					<li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatCategory&searchVO.menu_sn=05"><img src="/images/Menu/tm05_04_off.gif" alt="통계정보" /></a></li>
				</ul>
			 	</li>
<%
			}
%>
				<li id="Top-Menu-03"><a href="JavaScript:goMypage()"><img src="/images/Menu/tm03_off.gif" alt="마이페이지" border="0" /></a></li>
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
	</div>
	<!-- end #LY-Main-Header -->

	<!-- start # LY-Main-Content -->
	<div class="LY-Main-Img">
		<!-- start # Slogan -->
		<ul class="Slogan"><img src="/images/main/main_Slogan.jpg" alt="R&D도우미 홀씨가 연구자 여러분을 찾아갑니다. 연구자 여러분들의 목소리를 귀담아 듣겠습니다." /></ul>
		<!-- start # Q-Link -->
		<ul class="Q-Link">
			<a href="JavaScript:goInquireForm()"><img src="/images/main/main_QLink.jpg" alt="온라인상담 문의사항에 친절하게 답변해드리겠습니다. 바로가기" border="0" /></a>
		</ul>
		<!--start # Main-Movie-->
		<ul class="Main-Movie"><img src="/images/main/main_Movie.jpg" alt="" /></ul>
	</div>
	<!-- end # LY-Main-Content -->

	<!-- start # LY-Notice-Infor-->
	<div class="LY-Main-Ninfor">
		<!-- start # 최근게시물 -->
		<ul class="Main-Ninfor">
			<ul class="Title">
				<li class="Title"><img src="/images/main/Main_Title01.gif" alt="최근게시물" /></li>
			</ul>
			<ul class="List">
				<li>
				<table border="0" cellspacing="0" cellpadding="0" class="Main-NinforList">
<%
			InquireVO boardVO = new InquireVO();
			InquireVO noticeVO = new InquireVO();
			String title= "";
			String img= "";
			String link= "";
			for(int i=0; i <voList.size(); i++){
				boardVO = (InquireVO)voList.get(i);
				title= "";
				img= "";
				if(boardVO.getTitle() != null && boardVO.getTitle().length() > 12){
					title = boardVO.getTitle().substring(0,12)+"...";
				}else{
					title = boardVO.getTitle();
				}
				
				if(boardVO.getBoard_type().equals("FAQ")){
					img= "icon_Notice01.gif";
					link ="/switch.do?prefix=/faq&page=/Faq.do?method=faqDetailView&searchVO.board_type=FAQ&searchVO.seq="+boardVO.getSeq()+"&searchVO.menu_sn=01";
					//link ="/switch.do?prefix=&page=/Faq.do?method=faqList&searchVO.menu_sn=01";
				}else if(boardVO.getBoard_type().equals("QNA")){
					img= "icon_Notice02.gif";
					link ="switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireView&searchVO.board_type=QNA&searchVO.seq="+boardVO.getSeq()+"&searchVO.menu_sn=01";
				}else {
					img= "icon_Notice03.gif";
					link ="/switch.do?prefix=/data&page=/Data.do?method=dataDetailView&searchVO.board_type=FAQ&searchVO.seq="+boardVO.getSeq()+"&searchVO.menu_sn=01";
				}
%>				
				<tr><td><img src="/images/icon/<%=img%>" /> <a href="<%=link%>"><%=title%></a></td>
					<td class="Day"><%=boardVO.getReg_dt() %></td>
				</tr>
<%
			}
%>				
				</table>
				</li>
			</ul>
		</ul>
		<!-- end # 최근게시물 -->

		<!-- start # 공지사항 -->
		<ul class="Main-Ninfor">
			<ul class="Title">
				<li class="Title"><img src="/images/main/Main_Title02.gif" alt="공지사항" /></li>
				<li class="More"><a href="JavaScript:goNotice()"><img src="/images/main/Btn_more.gif" alt="더보기" border="0" /></a></li>
			</ul>
			<ul class="List">
				<li>
				<table border="0" cellspacing="0" cellpadding="0" class="Main-NinforList">
<%
			for(int i=0; i <voList1.size(); i++){
				noticeVO = (InquireVO)voList1.get(i);
				title= "";
				if(noticeVO.getTitle().length() > 14){
					title = noticeVO.getTitle().substring(0,14)+"...";
				}else{
					title = noticeVO.getTitle();
				}
				
%>
				<tr><td><a href="/switch.do?prefix=/notice&page=/Notice.do?method=noticeDetailView&searchVO.board_type=NOTICE&searchVO.seq=<%=noticeVO.getSeq()%>&searchVO.menu_sn=04"><%=title%> </a></td>
					<td class="Day"><%=noticeVO.getReg_dt() %></td>
				</tr>
<%
			}
%>
				</table>
				</li>
			</ul>
		</ul>
		<!-- end # 공지사항 -->
	
		<!-- start # 전화상담 -->
		<ul class="Main-Nhelpline">
			<li><img src="/images/main/img_helpline.gif" alt="전화상담 :02-724-8700, 평일 | 09:00 ~ 11:30, 13:30 ~ 18:00, 국가R&D사업 관련 규정 및 제도에 대한 궁금증을 문의하세요. " /></li>
		</ul>
		<!-- end # 공지사항 -->
	</div>
	<!-- end # LY-Notice-Infor-->

	<!-- start # LY-Footer -->
	<div class="LY-MainFooter">
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

	</div>
	<!-- end # LY-Footer -->
</div>
<!-- end #LY-Wrapper -->
</body>
</html>