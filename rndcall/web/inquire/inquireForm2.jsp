<%@page contentType="text/html; charset=utf-8" %>

<%@include file="/include/top.jsp"%>

    <!-- container -->
    <div id="container">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>온라인상담</h2>
                <span><img src="/img/common/h2_entxt02.gif" alt="Online Consultation" /></span>
            </div>
            <ul class="lnb-lst">
                <li class="on"><a href="JavaScript:goInquireForm()">온라인상담</a></li>
                <li><a href="JavaScript:goFaq()">자주묻는질문</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href="JavaScript:goInquireList()">온라인상담</a></li>
                    <li class="on"><a href="JavaScript:goInquireForm()">온라인상담</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>온라인상담</h3>
                    <p>가각 중앙행정기관에서 개별적으로 관리하는 국가연구개발사업 관련 질의 처리를 위해 각 부처 전문기관 홈페이지로의 링크 서비스를 제공합니다.</p>
                </div>
                
                <!-- tbl-type01 -->
                <div class="tbl-type01 mt30">
                    <table border="0" summary="중앙행정기관, 전문기관, 전문기관 링크주소">
                        <caption>중앙행정기관 링크</caption>
                        <colgroup>
                            <col width="14%" />
                            <col width="*" />
                            <col width="20%" />
                        </colgroup>
                        <thead>
                            <tr>
                                <th scope="col">중앙행정기관</th>
                                <th scope="col">전문기관</th>
                                <th scope="col">전문기관 링크주소</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td rowspan="2" class="txt-c">미래창조과학부</td>
                                <td>한국연구재단</td>
                                <td><a href="http://www.nrf.re.kr" target="_blank" title="새창열림">www.nrf.re.kr</a></td>
                            </tr>
                            <tr class="on">
                                <td class="line-l">정보통신산업진흥원</td>
                                <td><a href="http://www.nipa.kr" target="_blank" title="새창열림">www.nipa.kr</a></td>
                            </tr>
                            <tr>
                                <td rowspan="3" class="txt-c">산업통상자원부</td>
                                <td >한국산업기술평가관리원</td>
                                <td><a href="http://www.keit.re.kr" target="_blank" title="새창열림">www.keit.re.kr</a></td>
                            </tr>
                            <tr class="on">
                                <td class="line-l">한국산업기술진흥원</td>
                                <td><a href="http://www.kiat.or.kr" target="_blank" title="새창열림">www.kiat.or.kr</a></td>
                            </tr>
                            <tr>
                                <td class="line-l">한국에너지기술평가원</td>
                                <td><a href="http://www.ketep.re.kr" target="_blank" title="새창열림">www.ketep.re.kr</a></td>
                            </tr>
                            <tr>
                                <td class="txt-c">국토교통부</td>
                                <td class="on">국토교통과학기술진흥원</td>
                                <td class="on"><a href="http://www.kaia.re.kr" target="_blank" title="새창열림">www.kaia.re.kr</a></td>
                            </tr>
                            <tr >
                                <td class="txt-c">해양수산부</td>
                                <td>한국해양과학기술진흥원</td>
                                <td><a href="http://www.kimst.re.kr" target="_blank" title="새창열림">www.kimst.re.kr</a></td>
                            </tr>
                            <tr>
                                <td class="txt-c">농림축산식품부</td>
                                <td class="on">농림수산식품기술기획평가원</td>
                                <td class="on"><a href="http://www.ipet.re.kr" target="_blank" title="새창열림">www.ipet.re.kr</a></td>
                            </tr>
                            <tr>
                                <td class="txt-c">보건복지부</td>
                                <td>한국보건산업진흥원</td>
                                <td><a href="http://www.khidi.or.kr" target="_blank" title="새창열림">www.khidi.or.kr</a></td>
                            </tr>
                            <tr>
                                <td class="txt-c">환경부</td>
                                <td class="on">한국환경산업기술원</td>
                                <td class="on"><a href="http://www.keiti.re.kr" target="_blank" title="새창열림">www.keiti.re.kr</a></td>
                            </tr>
                            <tr>
                                <td class="txt-c">문화체육관광부</td>
                                <td>한국콘텐츠진흥원</td>
                                <td><a href="http://www.kocca.kr" target="_blank" title="새창열림">www.kocca.kr</a></td>
                            </tr>
                            <tr>
                                <td class="txt-c">중소기업청</td>
                                <td class="on">중소기업기술정보진흥원</td>
                                <td class="on"><a href="http://www.tipa.or.kr" target="_blank" title="새창열림">www.tipa.or.kr</a></td>
                            </tr>
                            <tr>
                                <td class="txt-c">방위사업청</td>
                                <td>국방기술품질원</td>
                                <td><a href="http://www.dtaq.re.kr" target="_blank" title="새창열림">www.dtaq.re.kr</a></td>
                            </tr>
                            <tr>
                                <td rowspan="2" class="txt-c">기상청</td>
                                <td class="on">한국기상산업진흥원</td>
                                <td class="on"><a href="http://www.kmipa.or.kr" target="_blank" title="새창열림">www.kmipa.or.kr</a></td>
                            </tr>
                            <tr>
                                <td class="line-l">기상기술개발원</td>
                                <td><a href="http://www.cater.re.kr" target="_blank" title="새창열림">www.cater.re.kr</a></td>
                            </tr>
                            <tr>
                                <td class="txt-c">소방방재청</td>
                                <td class="on">국립방재연구원</td>
                                <td class="on"><a href="http://www.ndmi.go.kr" target="_blank" title="새창열림">www.ndmi.go.kr</a></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <!-- //tbl-type01 -->
            </div>
            <!-- //section -->  
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->
    
<%@include file="/include/bottom.jsp"%>