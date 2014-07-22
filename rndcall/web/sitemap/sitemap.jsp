<%@page contentType="text/html; charset=utf-8" %>

<%@include file="/include/top.jsp"%>
	
	<!-- container -->
    <div id="container">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>사이트 맵</h2>
                <span><img src="/img/common/h2_entxt06.gif" alt="Online Consultation" /></span>
            </div>
            <ul class="lnb-lst">
                <li class="on"><a href="/sitemap/sitemap.jsp">사이트 맵</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li class="on"><a href="/sitemap/sitemap.jsp">사이트 맵</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">
                <!-- center_bx -->
                <div class="sitemap-area mt60">
                    <ul class="sitemap clearfix">
                        <li class=""><strong><a href="/development/development01.jsp">국가연구개발사업이란?</a></strong>
                            <ul>
                                <li><a href="/development/development01.jsp">- 정의 및 법령체계</a></li>
                                <li><a href="/development/development02.jsp">- 사업추진체계</a></li>
                            </ul>
                        </li>
                        <li><strong><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireList&searchVO.menu_sn=01">온라인 상담</a></strong>
                            <ul>
                                <li><a href="JavaScript:goInquireForm()">- 온라인 상담</a></li>
                                <li><a href="JavaScript:goFaq()">- 자주 찾는 질문</a></li>
                            </ul>
                        </li>
                        <li><strong><a href="JavaScript:goOffer()">R&D 신문고</a></strong></li>
                        <li><strong><a href="JavaScript:goNotice()">알림</a></strong>
                            <ul>
                                <li><a href="JavaScript:goNotice()">- 공지사항</a></li>
                            </ul>
                        </li>
                        <li><strong><a href="JavaScript:goData()">자료실</a></strong>
                            <ul>
                                <li><a href="Javascript:goLawInfo()">- 법령 및 행정규칙</a></li>
                                <li><a href="JavaScript:goIns('SYSTEM')">- 제도</a></li>
                                <li><a href="JavaScript:goIns('DATA')">- 기타 </a></li>
                            </ul>
                        </li>
                        <li class="end"><strong><a href="/center/center.jsp">센터 소개</a></strong></li>
                    </ul>
                </div>
            </div>  
            <!-- //section -->  
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->
		
<%@include file="/include/bottom.jsp"%>