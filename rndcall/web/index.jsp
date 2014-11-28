<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="/tags/struts-html" prefix="html"%>
<%@taglib uri="/tags/struts-logic" prefix="logic"%>
<%@taglib uri="/tags/struts-bean" prefix="bean"%>
<%@taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@taglib uri="/tags/taglibs-log" prefix="log"%>
<%@page import="kr.go.rndcall.mgnt.login.LoginVO" %>
<%@page import="java.sql.*" %>
<%@page import="java.util.*" %>
<%@page import="kr.go.rndcall.mgnt.common.BaseSqlDAO"%>
<%@page import="kr.go.rndcall.mgnt.inquire.vo.*" %>
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
// 		query += "        (SELECT Q.SEQ, Q.BOARD_TYPE, Q.TITLE, ";
// 		query += "               TO_CHAR(Q.REG_DT,'yyyy.mm.dd') AS REG_DT1, ";
// 		query += "               Q.REG_DT AS REG_DT ";
// 		query += "          FROM RNDCALL_BOARD_QUESTION Q LEFT OUTER JOIN RNDCALL_BOARD_ANSWER A ON Q.SEQ = A.Q_SEQ ";
// 		query += "         WHERE Q.DEL_YN='N' ";
// 		query += "           AND Q.BOARD_TYPE = 'FAQ' ";
// 		query += "           AND Q.OPEN_YN = 'Y' ";
// 		query += "           AND Q.INSERT_TYPE = 'ONLINE' ";
// 		query += "         ORDER BY reg_dt DESC limit 0, 4) ";
// 		query += "         UNION ALL ";
		query += "        SELECT Q.SEQ, Q.BOARD_TYPE, Q.TITLE, ";
		query += "               TO_CHAR(Q.REG_DT,'yyyy.mm.dd') AS REG_DT1, ";
		query += "               Q.REG_DT AS REG_DT ";
		query += "          FROM RNDCALL_BOARD_QUESTION Q LEFT OUTER JOIN RNDCALL_BOARD_ANSWER A ON Q.SEQ = A.Q_SEQ ";
		query += "         WHERE Q.DEL_YN='N' ";
// 		query += "           AND Q.OPEN_YN = 'Y' ";
// 		query += "           AND Q.INSERT_TYPE = 'ONLINE' ";
		query += "           AND Q.BOARD_TYPE in ('DATA', 'SYSTEM') ";
		query += "		 ORDER BY reg_dt DESC limit 0, 4 ";
// 		query += "         UNION ALL ";
// 		query += "        (SELECT Q.SEQ, Q.BOARD_TYPE, Q.TITLE, ";
// 		query += "               TO_CHAR(Q.REG_DT,'yyyy.mm.dd') AS REG_DT1, ";
// 		query += "               Q.REG_DT AS REG_DT ";
// 		query += "          FROM RNDCALL_BOARD_QUESTION Q INNER JOIN RNDCALL_BOARD_ANSWER A ON Q.SEQ = A.Q_SEQ ";
// 		query += "         WHERE Q.DEL_YN='N' ";
// 		query += "           AND Q.BOARD_TYPE = 'QNA' ";
// 		query += "           AND Q.OPEN_YN = 'Y' ";
// 		query += "           AND Q.INSERT_TYPE = 'ONLINE' ";
// 		query += "		 ORDER BY reg_dt DESC limit 0, 4) ";
		query += "       ) AS A";
		query += " ORDER BY reg_dt DESC limit 0, 3 ";
		
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
		query += "ORDER BY reg_dt DESC limit 0, 3";
		
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
	    ee.printStackTrace();
	    
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
    <link rel="stylesheet" type="text/css" href="/css/owl.carousel.css" />
    <link rel="stylesheet" type="text/css" href="/css/owl.theme.css" />
	<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="/js/jquery.popup.js"></script>
    <script type="text/javascript" src="/js/owl.carousel.js"></script>
    <script type="text/javascript" src="/js/topNav.js"></script>
    <script type="text/javascript" src="/js/main.js"></script>
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
		
		
		function login(id) {	
			
			var winfeatures = "toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=580,height=305";
			var url = "/login.jsp?returnURL=" + escape(document.location.href);
		
			if ( id ) {
				url +=  "&id=" + id;
			}
			loginWin = window.open( url, "login", winfeatures);
		}
		
		
		function update() {	
			document.fm3.elements["vo.login_id"].value = '<%= login_id %>';
			document.fm3.submit();
		}
		
		//문의하기 링크
		function goInquireForm(){
<%-- 			if("<%=login_id%>" == ""){ --%>
// 				alert("로그인 후 이용하실 수 있습니다.");
// 				return;
// 			}else{		
				document.location.href = "/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireForm&searchVO.menu_sn=01";
// 			}
		}
		
		function goInquireMainList(){
			document.location.href = "/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireMainList&searchVO.menu_sn=01";
		}
		
		//Q&A링크
		function goInquireList(){
			document.location.href = "/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireList&searchVO.board_type=QNA&searchVO.type=&searchVO.searchCategory=&searchVO.menu_sn=01";
		}
		
		//신문고 질문 링크
		function goOffer(){
			var url;
<%-- 			if("<%=mainRoleCD%>" == "0000Z" || "<%=mainRoleCD%>" == "0000C"){ --%>
				url ="/switch.do?prefix=/offer&page=/Offer.do?method=offerList&searchVO.menu_sn=14";
// 			}else{
// 				url ="/switch.do?prefix=/offer&page=/Offer.do?method=offerInsertForm&searchVO.menu_sn=14";
// 			}
			document.location.href = url;
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
		
		function goLawInfo(){
//             var url = "http://rndgate.ntis.go.kr/switch.do?prefix=/un/rndLaw&page=/unRndLaw.do?method=retrieveLawSearchByKey";
//             window.open(url);
            document.location.href = "/switch.do?prefix=/data&page=/Data.do?method=dataListLaw&searchVO.menu_sn=02";
//             document.location.href = "http://rndgate.ntis.go.kr/switch.do?prefix=/un/rndLaw&page=/unRndLaw.do?method=retrieveLawSearchByKey";
        }
		
		function banner() {
			window.open("/pop/banner.html", "banner", "width=400, height=557");
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
<!-- 					<li><a href="/sitemap.jsp?searchVO.menu_sn=08">사이트맵</a></li> -->
					<li><a href="/switch.do?prefix=&page=/member.do?method=getUserInsertForm&searchVO.menu_sn=06">회원가입</a></li>
					<li><a href="JavaScript:login();">로그인</a></li>
				</ul>
				<div class="search-bx fl">
					<label for="search"><strong>통합검색</strong></label>
					<input type="text" id="search" name="word" onkeydown="checkKey();" />
					<a href="JavaScript:goUniSearch();" class="search-btn">검색</a>
				</div>
				<%
					} else {
				%>
				<!-- 로그인 상태 -->
				<ul class="nav-lst fl">
					<li><a href="/index.jsp">홈</a></li>
<!-- 					<li><a href="/sitemap.jsp?searchVO.menu_sn=08">사이트맵</a></li> -->
					<%
						if ( mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C") ) {
					%>
<!-- 					<li><a href="/switch.do?prefix=&page=/discussion.do?method=retrieveDiscussDetail&searchVO.menu_sn=09">커뮤니티</a></li> -->
					<%
						}
					%>
					<li><a href="JavaScript:goMypage()">마이페이지</a></li>
					<li><a href="/logout.jsp">로그아웃</a></li>
                    <li class="user"><strong><%= login_id %></strong>님 접속하셨습니다.</li>
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
        <div class="gnb" id="gnb">
            <div class="gnbContainer">
                <ul>
                    <li>
                        <a href="/development/development01.jsp">국가연구개발사업이란?</a>
                        <div class="snb"> <!-- gnbSub -->
                            <div class="snb-bx"> <!-- gnbSubFix -->
                                <ul>
                                    <li><a href="/development/development01.jsp">정의 및 법령 체계</a></li>
                                    <li><a href="/development/development02.jsp">사업추진체계</a></li>
                                </ul>
                            </div>
                        </div>  
                    </li>
                    <li>
                        <a href="javascript:goInquireMainList()">온라인 상담</a>
                        <div class="snb">
                            <div class="snb-bx">
                                <ul>
                                    <li><a href="JavaScript:goInquireMainList()">온라인 상담</a></li>
                                    <li><a href="JavaScript:goFaq()">자주 묻는 질문</a></li>
                                </ul>
                            </div>
                        </div>  
                    </li>
                    <li>
                        <a href="JavaScript:goOffer()">R&amp;D 신문고</a>
                    </li>
                    <li>
                        <a href="JavaScript:goNotice()">새소식</a>
                    </li>
                    <li>
                        <a href="JavaScript:goLawInfo()">자료실</a>
                        <div class="snb">
                            <div class="snb-bx">
                                <ul>
                                    <li><a href="Javascript:goLawInfo()">법령 및 행정 규칙</a></li>
                                    <li><a href="/switch.do?prefix=/data&page=/Data.do?method=dataSystemList&searchVO.menu_sn=02">연구관리제도</a></li>
                                    <li><a href="JavaScript:goData()">기타</a></li>
                                </ul>
                            </div>
                        </div>  
                    </li>
                    <li>
                        <a href="/center/center.jsp">센터소개</a>
                    </li>
					<%
				        if ( mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C") ) {
 					%>
					<li>
                        <a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getAdminInquireList&searchVO.board_type=QNA&searchVO.type=&searchVO.searchCategory=&searchVO.menu_sn=01">관리자</a>
                        <div class="snb">
                            <div class="snb-bx">
                                <ul>
                                    <li><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getAdminInquireList&searchVO.board_type=QNA&searchVO.type=&searchVO.searchCategory=&searchVO.menu_sn=01">온라인 상담</a></li>
                                    <li><a href="/switch.do?prefix=/offer&page=/Offer.do?method=adminOfferList&searchVO.menu_sn=14">R&D신문고</a></li>
                                    <li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=05">회원관리</a></li>
                                    <li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=05">질문분야관리</a></li>
                                    <li><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm&searchVO.menu_sn=05">오프라인자료등록</a></li>
                                    <li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatBoardType&searchVO.menu_sn=09">통계정보</a></li>
                                </ul>
                            </div>
                        </div>
                    </li>
					<%
 						}
					%>
                    <p class="all-menu fr"><a href="/sitemap/sitemap.jsp" class="btn-menu">전체보기</a></p>
                </ul>
                <!-- //gnb-menu -->
            </div>
        </div>
        <!-- // gnb -->
		
		
<!-- 					// TODO 기관담당자 -->
<%-- 					<% --%>
<!-- // 						if ( mainRoleCD.equals("0000B") ) { -->
<%-- 					%> --%>
<!-- 					<li id="Top-Menu-06" class="menu"><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireOrgList&searchVO.board_type=QNA&searchVO.menu_sn=12">기관담당자</a></li> -->
<%-- 					<% --%>
<!-- // 						} -->
<%-- 					%> --%>
				
				
	</div>
	<!-- // header -->
	<!-- slide -->
    <div id="main-visual" class="main-visual">
        <div class="main-slide clearfix">
          <div id="visual" class="visual">
            <div class="item"></div>
            <div class="item"></div>
            <div class="item"></div>
          </div>
        </div>
    </div>
    <!-- //slide -->
	<!-- container -->
    <div id="container">
        <!-- content -->
        <div class="content">
            <!-- banner -->
            <div class="banner">
                <!-- board-lst -->
                <ul class="board-lst">
                    <li class="bg01">
                        <strong>온라인 상담</strong>
                        <p>규정 및 제도에 대한 <br />궁금증을 문의 하세요</p>
                        <a href="JavaScript:goInquireMainList();" class="btn-go">이동</a>
                    </li>
                    <li class="bg02">
                        <strong>R&amp;D 신문고</strong>
                        <p>규정 및 제도에 대한 <br />개선사항을 건의해주세요</p>
                        <a href="JavaScript:goOffer();" class="btn-go">이동</a>
                    </li>
                    <li class="bg03">
                        <strong class="mb20">자료실</strong>
                        <a href="JavaScript:goLawInfo();" class="btn-go">이동</a>
                    </li>
                    <li class="bg04">
                        <strong class="mb20">새소식</strong>
                        <a href="JavaScript:goNotice();" class="btn-go">이동</a>
                    </li>
                </ul>
                <!-- //board-lst -->
                <!-- bn-slide -->
                <div class="bn-slide">
                    <div class="slide " id="owl-banner">
<!--                         <div><a href="#none;"><img src="img/main/bn_slide01.jpg" alt="국가연구개발사업 학생인건비. 통합관리제 운영 매뉴얼. 일시 : 2013년 4월 03일 미래창조과학부 장관 " /></a></div> -->
<!--                         <div><a href="javascript:banner();"><img src="img/main/bn_slide03.jpg" alt="R&amp;D도우미센터 이렇게 바뀌었습니다. " /></a></div> -->
<!--                         <div><a href="/notice/Notice.do?method=noticeDetailView&searchVO.board_type=NOTICE&searchVO.seq=4832"><img src="img/main/bn_slide04.gif" alt="국가연구개발사업의 관리 등에 관한 규칙 일부개정령안 입법예고('14.10.6~11.17)" /></a></div> -->
                        <div><a href="http://on.frc.co.kr/nation_rnd/default.asp" target="_blank"><img src="img/main/bn_slide04.jpg" alt="국가연구개발사업 제도개선 만족도 조사(2014.11.24~2014.12.05)" /></a></div>
                    </div>
                    <div class="btn-bx">
                        <span class="btn-con">
                            <button class="stop"><strong>정지</strong></button>
                            <button class="play"><strong>재생</strong></button>
                        </span>
                        <span class="page-set">
                            <a class="" href="#bnr1"><strong class="hidden">1번 슬라이드</strong></a>
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
                        <li>평일 : 9:00 ~ 11:30, 13:30 ~18:00</li>
                        <li>국가R&amp;D사업 관련 규정 및 제도에 대한 <br />궁금증을 문의하세요.</li>
                    </ul>               
                </div>
                <!-- //bn-tel -->
                <!--div class="bn-service fl">
                    <strong>바로가기 서비스</strong>
                    <p>R&D 도우미센터 관련 부처 <br />웹사이트로  바로 이동하실 수 있습니다.</p> 
                    <div>
                        <select>
                            <option>R&D관련부처 바로가기</option>
                        </select>
                        <span class="btn-go"><a href="#none;">go</a></span>
                    </div>
                    <div>
                        <select>
                            <option>전문기관 바로가기</option>
                        </select>
                        <span class="btn-go"><a href="#none;">go</a></span>
                    </div>
                    <div>
                        <select>
                            <option>관련기관 바로가기</option>
                        </select>
                        <span class="btn-go"><a href="#none;">go</a></span>
                    </div>
                </div-->
            </div>
            <!-- //banner -->
        </div>
        <!-- //content -->
        <div class="cont-foot">
            <ul class="cont-lst clearfix">
                <li class="clearfix">
                    <strong>새소식</strong>
                    <div class="notice-bx">
                    <%
                        InquireVO noticeVO = new InquireVO();
                        String title= "";
                    
                        for(int i=0; i <voList1.size(); i++){
                            noticeVO = (InquireVO)voList1.get(i);
                            title= "";
                            if(noticeVO.getTitle().length() > 20){
                                title = noticeVO.getTitle().substring(0,20)+"...";
                            }else{
                                title = noticeVO.getTitle();
                            }
                
                    %>
                    <p><%= title %>' <a href="JavaScript:goNotice()">더보기</a></p>
                    <%
                        }
                    %>
                    </div>
                </li>
                <li class="txt clearfix">
                    <strong>자료실</strong>
<!-- 					// TODO  -->
<!-- 					최근게시판 -->
                    <div class="data-bx">
					<%
						InquireVO boardVO = new InquireVO();
						String img= "";
						String link= "";
						for(int i=0; i <voList.size(); i++){
							boardVO = (InquireVO)voList.get(i);
							title= "";
							img= "";
							if(boardVO.getTitle() != null && boardVO.getTitle().length() > 20){
								title = boardVO.getTitle().substring(0,20)+"...";
							}else{
								title = boardVO.getTitle();
							}
							
//  							if(boardVO.getBoard_type().equals("FAQ")){
//  								img= "icon_Notice01.gif";
//  								link ="/switch.do?prefix=/faq&page=/Faq.do?method=faqDetailView&searchVO.board_type=FAQ&searchVO.seq="+boardVO.getSeq()+"&searchVO.menu_sn=01";
//  								//link ="/switch.do?prefix=&page=/Faq.do?method=faqList&searchVO.menu_sn=01";
//  							}else if(boardVO.getBoard_type().equals("QNA")){
//  								img= "icon_Notice02.gif";
//  								link ="switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireView&searchVO.board_type=QNA&searchVO.seq="+boardVO.getSeq()+"&searchVO.menu_sn=01";
//  							}else {
//  								img= "icon_Notice03.gif";
//  								link ="/switch.do?prefix=/data&page=/Data.do?method=dataDetailView&searchVO.board_type=FAQ&searchVO.seq="+boardVO.getSeq()+"&searchVO.menu_sn=01";
//  							}
  					%>
<%-- 				<tr><td><img src="/images/icon/<%=img%>" /> <a href="<%=link%>"><%=title%></a></td> --%>
<%-- 					<td class="Day"><%=boardVO.getReg_dt() %></td> --%>
<!-- 				</tr> -->
                    <p><%= title %>' <a href="JavaScript:goData()">더보기</a></p>
					<%
 						}
 					%>
 					</div>
                </li>
            </ul>           
        </div>          
    </div>
    <!-- // container -->
    <!-- footer -->
    <div id="footer">
        <div class="footer clearfix">
            <p class="foot-logo"><img src="img/common/foot_logo.gif" alt="미래창조과학부" /></p>
            <div class="add">
                <ul class="add-lst clearfix">
                    <li><a href="/guidecenter/private.jsp">개인정보처리방침 </a></li>
                    <li><a href="/guidecenter/email.jsp">이메일무단수집거부</a></li>
                </ul>
                <!-- 02-2110-2737 -->
                <address>427-700 경기도 과천시 관문로 47 정부과천청사 4동(중앙동)<br /> 대표번호 1800-7109   팩스 02-2110-0256</address>
                <p class="copyright">COPYRIGHT ⓒ 2014 MINISTRY OF SCIENCE,ICT &amp; PLANNING, ALL RIGHTS RESERVED. </p> 
            </div>
            
            <form name="siteUrlForm" method="post">
	            <div class="family-site clearfix">
	                <p class="txt fl">바로가기</p>
	                <div class="bn-service fl">                 
	                    <div>
	                        <select name="siteurl1" id="siteurl1" title="R&D관련부처 바로가기">
	                            <option value="#" selected="selected">R&D관련부처 바로가기</option>
	                            <option value="http://www.moe.go.kr">교육부</option>
	                            <option value="http://www.mcst.go.kr">문화체육관광부</option>
	                            <option value="http://www.mafra.go.kr">농림축산식품부</option>
	                            <option value="http://www.motie.go.kr">산업통상자원부</option>
	                            <option value="http://www.mw.go.kr">보건복지부</option>
	                            <option value="http://www.me.go.kr">환경부</option>
	                            <option value="http://www.molit.go.kr">국토교통부</option>
	                            <option value="http://www.dapa.go.kr">방위사업청</option>
	                            <option value="http://www.nema.go.kr">소방방재청</option>
	                            <option value="http://www.cha.go.kr">문화재청</option>
	                            <option value="http://www.rda.go.kr">농촌진흥청</option>
	                            <option value="http://www.forest.go.kr">산림청</option>
	                            <option value="http://www.smba.go.kr">중소기업청</option>
	                            <option value="http://www.mfds.go.kr">식품의약품안전처</option>
	                            <option value="http://www.kms.go.kr">기상청</option>
	                            <option value="http://www.msip.go.kr">미래창조과학부</option>
	                            <option value="http://www.mof.go.kr">해양수산부</option>
	                        </select>
	                        <span class="btn-go"><a href="JavaScript:goSiteUrl(1)">이동</a></span>
	                    </div>
	                    <div>
	                        <select name="siteurl2" id="siteurl2" title="전문기관 바로가기">
	                            <option value="#" selected="selected">전문기관 바로가기</option>
	                            <option value="http://www.nrf.re.kr">한국연구재단</option>
	                            <option value="http://www.kocca.kr">한국콘텐츠진흥원</option>
	                            <option value="http://www.ipet.re.kr">농림수산식품기술기획평가원</option>
	                            <option value="http://www.keit.re.kr">한국산업기술평가관리원</option>
	                            <option value="http://www.nipa.kr">정보통신산업진흥원</option>
	                            <option value="http://www.khidi.or.kr">한국보건산업진흥원</option>
	                            <option value="http://www.keiti.re.kr">한국환경산업기술원</option>
	                            <option value="http://www.kaia.re.kr">국토교통과학기술진흥원</option>
	                            <option value="http://www.kimst.re.kr">한국해양과학기술진흥원</option>
	                            <option value="http://www.dtaq.re.kr">국방기술품질원</option>
	                        </select> 
	                        <span class="btn-go"><a href="JavaScript:goSiteUrl(2)">이동</a></span>
	                    </div>
	                    <div>
	                        <select name="siteurl3" id="siteurl3" title="관련기관 바로가기">
	                            <option value="#" selected="selected">관련기관 바로가기</option>
	                            <option value="#">----성과물전담기관-------</option>
	                            <option value="http://paper.kisti.re.kr">한국과학기술정보연구원(논문)</option>
	                            <option value="http://www.rndip.or.kr">한국지식재산전략원(특허)</option>
	                            <option value="http://report.kisti.re.kr">한국과학기술정보연구원(보고서원문)</option>
	                            <option value="http://nfec.ntis.go.kr">한국기초과학지원연구원(연구시설·장비)</option>
	                            <option value="http://www.ntb.or.kr">한국산업기술진흥원(기술요약정보)</option>
	                            <option value="http://www.chembank.org">한국화학연구원(화합물)</option>
	                            <option value="http://www.biodata.kr">한국생명공학연구원(생명자원: 정보)</option>
	                            <option value="http://kctc.kribb.re.kr">한국생명공학연구원(생명자원: 실물)</option>
	                            <option value="http://www.copyright.or.kr">한국저작권위원회(소프트웨어)</option>
	                            <option value="#">---------기타-----------</option>
	                            <option value="http://www.nstc.go.kr">국가과학기술심의회</option>
	                            <option value="http://www.ndsl.kr">국가과학기술정보센터(NDSL)</option>
	                            <option value="http://www.kosen21.org">한국과학기술정보연구원(한민족과학기술자 네트워크)</option>
	                            <option value="http://society.kisti.re.kr">한국과학기술정보연구원(과학기술 학회마을)</option>
	                            <option value="http://fact.kisti.re.kr">한국과학기술정보연구원(과학기술 사실정보)</option>
	                            <option value="http://www.ntb.kr">국가기술사업화종합정보망</option>
	                            <option value="http://www.kird.re.kr">국가과학기술인력개발원</option>
	                        </select> 
	                        <span class="btn-go"><a href="JavaScript:goSiteUrl(3)">이동</a></span>
	                    </div>
	                </div>          
	            </div>
	        </form>
	        
        </div>
    </div>
    <!-- footer -->
</div>
<!-- // wrap -->

</body>
</html>