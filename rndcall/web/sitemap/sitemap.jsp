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
            <div class="section ">
                <!-- center_bx -->
                <div class="sitemap-area mt60 ">
                    <div class="sitebox rnd">
                        <h3><a href="/development/development01.jsp">국가연구개발 사업이란?</a></h3>
                        <ul>
                            <li><a href="/development/development01.jsp">정의 및 법령체계</a></li>
                            <li><a href="/development/development02.jsp">사업추진체계</a></li>
                        </ul>
                    </div>
                    <div class="sitebox online">
                        <h3><a href="JavaScript:goInquireMainList()">온라인상담</a></h3>
                        <ul>
                            <li><a href="JavaScript:goInquireMainList()">온라인 상담</a></li>
                            <li><a href="JavaScript:goFaq()">자주찾는 질문</a></li>
                        </ul>
                    </div>
                    <div class="sitebox sinmungo">
                        <h3><a href="JavaScript:goOffer()">R&amp;D신문고</a></h3>
                    </div>
                    <div class="sitebox notic">
                        <h3><a href="JavaScript:goNotice()">새소식</a></h3>
                        <ul>
                            <li><a href="JavaScript:goNotice()">새소식</a></li>
                        </ul>
                    </div>
                    <div class="sitebox data">
                        <h3><a href="JavaScript:goData()">자료실</a></h3>
                        <ul>
                            <li><a href="Javascript:goLawInfo()">법령 및 행정규칙</a></li>
                            <li><a href="JavaScript:goIns('SYSTEM')">연구관리제도</a></li>
                            <li><a href="JavaScript:goData()">기타</a></li>
                        </ul>
                    </div>
                    <div class="sitebox center">
                        <h3><a href="/center/center.jsp">센터소개</a></h3>
                    </div>
                </div>
                <!-- //center_bx -->
            </div>
            <!-- //section -->
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->

<%@include file="/include/bottom.jsp"%>