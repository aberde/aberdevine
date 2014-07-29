<%@page contentType="text/html; charset=utf-8"%>

<%@include file="/include/top.jsp"%>

    <!-- container -->
    <div id="container">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>회원가입</h2>
                <span><img src="/img/common/h2_membership.gif" alt="Online Consultation" /></span>
            </div>
            <ul class="lnb-lst">
                <li class="on"><a href="/switch.do?prefix=&page=/member.do?method=getUserInsertForm&searchVO.menu_sn=06">회원가입</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li class="on"><a href="/switch.do?prefix=&page=/member.do?method=getUserInsertForm&searchVO.menu_sn=06">회원가입</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="welcome mt60">
                    <div class="welcome-bx">
                        <strong>회원가입 완료</strong>
                        <p>R&amp;D 도우미센터 회원가입을 환영합니다.</p>
                    </div>
                </div>
                
            </div>  
            <!-- //section -->  
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->

<%@include file="/include/bottom.jsp"%>
