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
	boolean mainIsLogin = false;
	LoginVO mainLoginVO = (LoginVO)session.getAttribute("loginUserVO");
	if ( mainLoginVO != null && mainLoginVO.getLogin_id() != null && !mainLoginVO.getLogin_id().equals("guest") ) {
		mainIsLogin = true;
	}

	String mainRoleCD = "guest";
	String nameKO = "";
	String login_id = "";

	if ( mainLoginVO != null && mainIsLogin ) {	
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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>R&D도우미센터</title>
	<link rel="stylesheet" type="text/css" href="/css/common.css" />
	<link rel="stylesheet" type="text/css" href="/css/main.css" />
	<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="/js/main.js"></script>
	<script type="text/javascript">
		/** 메인슬라이드 **/
		$(document).ready(function(){
			$("div.bn-slide").ImgRolling();  
		});
	</script>
	<!--[if lt IE 7]>
	<style media="screen" type="text/css">
	#container {
		height:100%;
	}
	</style>
	<![endif]-->
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
		
		//신문고 질문 링크
		function goOffer(){
			var url;
			if("<%=login_id%>" == ""){
				alert("로그인 후 이용하실 수 있습니다.");
				return;		
			}else{
				if("<%=mainRoleCD%>" == "0000Z" || "<%=mainRoleCD%>" == "0000C"){
					url ="/switch.do?prefix=/offer&page=/Offer.do?method=offerList&searchVO.menu_sn=14";
				}else{
					url ="/switch.do?prefix=/offer&page=/Offer.do?method=offerInsertForm&searchVO.menu_sn=14";
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
<!--[if lt IE 7]>  <body class="no-js ie ie6 lte9 lte8 lte7"> <![endif]-->
<!--[if IE 7]>     <body class="no-js ie ie7 lte9 lte8 lte7"> <![endif]-->
<!--[if IE 8]>     <body class="no-js ie ie8 lte9 lte8"> <![endif]-->
<!--[if IE 9]>     <body class="no-js ie ie9 lte9"> <![endif]-->
<!--[if gt IE 9]>  <body class="no-js">  <![endif]-->
<!--[if !IE]><!--> <body> <!--<![endif]-->
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
	
	
<!-- wrap -->
<div id="wrap">
	<!-- header -->
	<div id="header">
		<div class="head clearfix">
			<h1><a href="/index.jsp"><img src="img/common/h1_logo.gif" alt="미래창조과학부. R&D도우미센터" /></a></h1>
			<div class="nav-bx clearfix">
				<% 
					if ( mainLoginVO == null || mainIsLogin == false ) {
				%>
				<!-- 로그아웃 상태 -->
				<ul class="nav-lst fl">
					<li><a href="/index.jsp">홈</a></li>
					<li><a href="/sitemap.jsp?searchVO.menu_sn=08">사이트맵</a></li>
					<li><a href="/switch.do?prefix=&page=/member.do?method=getUserInsertForm&searchVO.menu_sn=06">회원가입</a></li>
					<li><a href="JavaScript:login();">로그인</a></li>
				</ul>
				<div class="search-bx fl">
					<label for="search"><strong>통합검색</strong></label>
					<input type="text" id="search" name="word" defaltvalue="검색어 입력창" onkeydown="checkKey();" />
					<a href="JavaScript:goUniSearch();" class="search-btn">검색</a>
				</div>
				<%
					} else {
				%>
				<!-- 로그인 상태 -->
				<ul class="nav-lst fl">
					<li><a href="/index.jsp">홈</a></li>
					<li><a href="/sitemap.jsp?searchVO.menu_sn=08">사이트맵</a></li>
					<li><a href="javascript:update();">개인정보변경</a></li>
					<%
						if ( mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C") ) {
					%>
					<li><a href="/switch.do?prefix=&page=/discussion.do?method=retrieveDiscussDetail&searchVO.menu_sn=09">커뮤니티</a></li>
					<%
						}
					%>
					<li><a href="JavaScript:goMypage()">마이페이지</a></li>
					<li><a href="/logout.jsp">로그아웃</a></li>
<!-- 					// TODO 사용자 접속확인 메세지 사용여부 확인. -->
<%-- 					<li class="Join-infor"><strong><%= nameKO %></strong>님 접속하셨습니다.</li> --%>
				</ul>
				<div class="search-bx fl">
					<label for="search"><strong>통합검색</strong></label>
					<input type="text" id="search" name="word" onkeydown="checkKey();" />
					<a href="JavaScript:goUniSearch();" class="search-btn">검색</a>
				</div>
				<%
					}
				%>
			</div>
		</div>
		<!-- gnb -->
		<div class="gnb">
			<div class="gnb-bx clearfix">
				<ul class="menu-lst fl clearfix">
					<li class="menu"><a href="/development/development01.jsp">국가연구개발사업이란?</a>
						<ul class="snb">
							<li><a href="/development/development01.jsp">정의 및 법령 체계</a></li>
							<li><a href="/development/development02.jsp">사업추진체계</a></li>
						</ul>
					</li>
					<li class="menu"><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireMainList&searchVO.menu_sn=01">온라인 상담</a>
						<ul class="snb">
							<li><a href="JavaScript:goInquireForm()">온라인 상담</a></li>
							<li><a href="JavaScript:goFaq()">자주 묻는 질문</a></li>
<!-- 							// TODO Q&A는 사라지는건지? -->
<!-- 							<li><a href="JavaScript:goInquireList()">Q&A</a></li> -->
						</ul>
					</li>
					<li class="menu"><a href="JavaScript:goOffer()">온라인신문고</a></li>
					<li class="menu"><a href="JavaScript:goNotice()">알림</a>
						<ul class="snb">
							<li><a href="JavaScript:goNotice()">공지사항</a></li>
						</ul>
					</li>
					<li class="menu"><a href="JavaScript:goData()">자료실</a>
						<ul class="snb">
							<li><a href="#none;">법령 및 행정 규칙</a></li>
							<li><a href="#none;">제도</a></li>
							<li><a href="#none;">기타</a></li>
						</ul>
					</li>
					<li class="menu"><a href="/center/center.jsp">센터소개</a></li>
					<%
						if ( mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C") ) {
					%>
					<li class="menu"><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=05">관리자</a>
						<ul class="snb">
							<li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=05">권한관리</a></li>
							<li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=05">분야관리</a></li>
							<li><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm&searchVO.menu_sn=05">오프라인자료등록</a></li>
							<li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatCategory&searchVO.menu_sn=05">통계정보</a></li>
						</ul>
					</li>
					<%
						}
					%>
<!-- 					// TODO 기관담당자 -->
					<%
						if ( mainRoleCD.equals("0000B") ) {
					%>
					<li id="Top-Menu-06" class="menu"><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireOrgList&searchVO.board_type=QNA&searchVO.menu_sn=12">기관담당자</a></li>
					<%
						}
					%>
				</ul>
				<p class="all-menu fr"><a href="#none;">전체보기</a></p>
			</div>		
		</div>
		<!-- //gnb -->
	</div>
	<!-- // header -->	
	<!-- container -->
	<div id="container">
		<!-- content -->
		<div class="content">
			<!-- main-slide -->
			<div class="main-visual">
				<ul class="main-slide clearfix">
					<li>
						<a href="#none;">
							<img src="img/main/main_visual.jpg" alt="" />
							<span class="txt"><img src="img/main/visual_txt.png" alt="R&D 도우미 홀씨가 연구자 여러분을 찾아갑니다.연구자 여러분들의 목소리를 귀담아 듣겠습니다." /></span>
						</a>
					</li>
					<li>
						<a href="#none;">
							<img src="img/main/main_visual.jpg" alt="" />
							<span class="txt"><img src="img/main/visual_txt.png" alt="R&D 도우미 홀씨가 연구자 여러분을 찾아갑니다.연구자 여러분들의 목소리를 귀담아 듣겠습니다." /></span>
						</a>
					</li>
				</ul>
		        <div class="page-set">
		        	<a href="#none;" class="on"></a>
		        	<a href="#none;"></a>
		        </div>	        
			</div>
			<!-- main-slide -->	
			<!-- banner -->
			<div class="banner">
				<!-- board-lst -->
				<ul class="board-lst">
					<li class="bg01">
						<strong>온라인상담</strong>
						<p>규정 및 제도에 대한 <br />궁금증을 문의 하세요</p>
						<a href="JavaScript:goInquireForm();" class="btn-go">GO</a>
					</li>
					<li class="bg02">
						<strong>온라인신문고</strong>
						<p>규정 및 제도에 대한 <br />궁금증을 문의 하세요</p>
						<a href="JavaScript:goOffer();" class="btn-go">GO</a>
					</li>
					<li class="bg03">
						<strong class="mb20">자료실</strong>
						<a href="JavaScript:goData();" class="btn-go">GO</a>
					</li>
					<li class="bg04">
						<strong class="mb20">알림</strong>
						<a href="JavaScript:goNotice();" class="btn-go">GO</a>
					</li>
				</ul>
				<!-- //board-lst -->
				<!-- bn-slide -->
				<div class="bn-slide">
					<ul class="slide">
						<li id="bnr1"><img src="img/main/bn_slide01.jpg" alt="국가연구개발사업 학생인건비. 통합관리제 운영 매뉴얼. 일시 : 2013년 4월 03일 미래창조과학부 장관 " /></li>
						<li id="bnr2"><img src="img/main/bn_slide01.jpg" alt="국가연구개발사업 학생인건비. 통합관리제 운영 매뉴얼. 일시 : 2013년 4월 03일 미래창조과학부 장관 " /></li>
						<li id="bnr3"><img src="img/main/bn_slide01.jpg" alt="국가연구개발사업 학생인건비. 통합관리제 운영 매뉴얼. 일시 : 2013년 4월 03일 미래창조과학부 장관 " /></li>
						<li id="bnr4"><img src="img/main/bn_slide01.jpg" alt="국가연구개발사업 학생인건비. 통합관리제 운영 매뉴얼. 일시 : 2013년 4월 03일 미래창조과학부 장관 " /></li>
					</ul>
					<div class="btn-bx">
						<span class="btn-con">
							<button class="stop"><strong>정지</strong></button>
							<button class="play"><strong>재생</strong></button>
						</span>
						<span class="page-set">
							<a class="on" href="#bnr1"><strong class="hidden">1번 슬라이드</strong></a>
							<a class="" href="#bnr2"><strong class="hidden">2번 슬라이드</strong></a>
							<a class="" href="#bnr3"><strong class="hidden">3번 슬라이드</strong></a>
							<a class="" href="#bnr4"><strong class="hidden">4번 슬라이드</strong></a>
						</span>						
					</div>					
				</div>
				<!-- //bn-slide -->
				<!-- bn-tel -->
				<div class="bn-tel">
					<p><img src="img/main/bn_tel.png" alt="친한연구 1800 - 7109" /></p>
					<ul class="mt15">
						<li>평일 : 09:00 ~ 11:30, 13:30 ~18:00</li>
						<li>국가R&D사업 관련 규정 및 제도에 대한 <br />궁금증을 문의하세요.</li>
					</ul>				
				</div>
			</div>
			<!-- //banner -->
		</div>
		<!-- //content -->
		<div class="cont-foot">
			<ul class="cont-lst clearfix">
				<li>
					<strong>공지사항</strong>
					
					<%
						InquireVO noticeVO = new InquireVO();
						String title= "";
					
						for(int i=0; i <voList1.size(); i++){
							noticeVO = (InquireVO)voList1.get(i);
							title= "";
							if(noticeVO.getTitle().length() > 14){
								title = noticeVO.getTitle().substring(0,14)+"...";
							}else{
								title = noticeVO.getTitle();
							}
				
					%>
					<span><%= title %>'이 게시되었습니다. <a href="JavaScript:goNotice()">더보기</a></span>
					<%
						}
					%>


<!-- 					// TODO  -->
<!-- 					최근게시판 -->
<%-- 					<% --%>
<!-- 						InquireVO boardVO = new InquireVO(); -->
<!-- 						String img= ""; -->
<!-- 						String link= ""; -->
<!-- 						for(int i=0; i <voList.size(); i++){ -->
<!-- 							boardVO = (InquireVO)voList.get(i); -->
<!-- 							title= ""; -->
<!-- 							img= ""; -->
<!-- 							if(boardVO.getTitle() != null && boardVO.getTitle().length() > 12){ -->
<!-- 								title = boardVO.getTitle().substring(0,12)+"..."; -->
<!-- 							}else{ -->
<!-- 								title = boardVO.getTitle(); -->
<!-- 							} -->
							
<!-- 							if(boardVO.getBoard_type().equals("FAQ")){ -->
<!-- 								img= "icon_Notice01.gif"; -->
<!-- 								link ="/switch.do?prefix=/faq&page=/Faq.do?method=faqDetailView&searchVO.board_type=FAQ&searchVO.seq="+boardVO.getSeq()+"&searchVO.menu_sn=01"; -->
<!-- 								//link ="/switch.do?prefix=&page=/Faq.do?method=faqList&searchVO.menu_sn=01"; -->
<!-- 							}else if(boardVO.getBoard_type().equals("QNA")){ -->
<!-- 								img= "icon_Notice02.gif"; -->
<!-- 								link ="switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireView&searchVO.board_type=QNA&searchVO.seq="+boardVO.getSeq()+"&searchVO.menu_sn=01"; -->
<!-- 							}else { -->
<!-- 								img= "icon_Notice03.gif"; -->
<!-- 								link ="/switch.do?prefix=/data&page=/Data.do?method=dataDetailView&searchVO.board_type=FAQ&searchVO.seq="+boardVO.getSeq()+"&searchVO.menu_sn=01"; -->
<!-- 							} -->
<!-- 					%> -->
<%-- 				<tr><td><img src="/images/icon/<%=img%>" /> <a href="<%=link%>"><%=title%></a></td> --%>
<%-- 					<td class="Day"><%=boardVO.getReg_dt() %></td> --%>
<!-- 				</tr> -->
<%-- 					<% --%>
<!-- 						} -->
<!-- 					%> -->
					
					
					
				</li>
				<li class="txt">
					<strong>공지사항</strong>
					<span><%= title %>'이 게시되었습니다. <a href="JavaScript:goNotice()">더보기</a></span>
				</li>
			</ul>			
		</div>
	</div>
	<!-- // container -->
	<!-- footer -->
	<div id="footer">
		<div class="footer clearfix">
			<p class="foot-logo"><img src="/img/common/foot_logo.gif" alt="미래창조과학부" /></p>
			<div class="add">
				<ul class="add-lst clearfix">
					<li><a href="/info.jsp">개인정보처리방침 </a></li>
					<li><a href="/email.jsp">이메일무단수집거부</a></li>
				</ul>
				<address>427-700 경기도 과천시 관문로 47 정부과천청사 4동(중앙동)<br /> 대표번호 02-2110-2737   팩스 02-2110-0256</address>
				<p class="copyright">COPYRIGHT ⓒ 2014 MINISTRY OF SCIENCE,ICT &amp; PLANNING, ALL RIGHTS RESERVED. </p>	
			</div>
			<div class="family-site clearfix">
				<p class="txt fl">바로가기</p>
				<div class="bn-service fl">					
					<form name="siteUrlForm" method="post">
						<div>
							<select name="siteurl1" id="siteurl1" title="R&D관련부처 바로가기">
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
							<span class="btn-go"><a href="JavaScript:goSiteUrl(1)">go</a></span>
						</div>
						<div>
							<select name="siteurl2" id="siteurl2" title="전문기관 바로가기">
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
							<span class="btn-go"><a href="JavaScript:goSiteUrl(2)">go</a></span>
						</div>
						<div>
							<select name="siteurl3" id="siteurl3" title="관련기관 바로가기">
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
							<span class="btn-go"><a href="JavaScript:goSiteUrl(3)">go</a></span>
						</div>
					</form>
				</div>
			</div>					
		</div>
	</div>
	<!-- footer -->
</div>
<!-- // wrap -->

</body>
</html>