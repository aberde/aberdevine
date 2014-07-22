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
                    <!-- <p>각 중앙행정기관에서 개별적으로 관리하는 국가연구개발사업 관련 질의 처리를 위해 각 부처 전문기관 홈페이지로의 링크 서비스를 제공합니다.</p> -->
                </div>
                <!--  explain-bx -->
                <div class="explain-bx mt10">
                    <strong>각 중앙행정기관에서 개별적으로 운영하는 국가연구개발사업 관련 질의에 대한 정확한 답변제공을 위해, 각 부처에서 운영하는 전문기관 홈페이지로의 연결 서비스를 제공합니다.</strong>
                </div>
                <!--  //explain-bx -->
                <!-- tbl-type01 -->
                <div class="tbl-type01 mt10">
                    <table border="0" summary="중앙행정기관, 전문기관, 전문기관 링크주소">
                        <caption>중앙행정기관 링크</caption>
                        <colgroup>
                            <col width="16%" />
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
                                <td><a href="http://www.nrf.re.kr/nrf_tot_cms/board/qna_tot/list.jsp?show_no=174&check_no=169&c_relation=biz&c_relation2=0" target="_blank" title="새창열림">www.nrf.re.kr</a></td>
                            </tr>
                            <tr class="on">
                                <td class="line-l">정보통신산업진흥원</td>
                                <td><a href="http://www.nipa.kr/biz/tree.it?menuNo=17" target="_blank" title="새창열림">www.nipa.kr</a></td>
                            </tr>
                            <tr>
                                <td rowspan="3" class="txt-c">산업통상자원부</td>
                                <td >한국산업기술평가관리원</td>
                                <td><a href="http://www.keit.re.kr/voc/list.do?gbn=02_21&voc_code=0027" target="_blank" title="새창열림">www.keit.re.kr</a></td>
                            </tr>
                            <tr class="on">
                                <td class="line-l">한국산업기술진흥원</td>
                                <td><a href="http://www.kiat.or.kr/site/program/board/list.jsp?menuID=001003001&boardTypeID=4" target="_blank" title="새창열림">www.kiat.or.kr</a></td>
                            </tr>
                            <tr>
                                <td class="line-l">한국에너지기술평가원</td>
                                <td><a href="http://voc.ketep.re.kr/ketep/contents/main/NR_index.do?_m=main" target="_blank" title="새창열림">www.ketep.re.kr</a></td>
                            </tr>
                            <tr>
                                <td class="txt-c">국토교통부</td>
                                <td class="on">국토교통과학기술진흥원</td>
                                <td class="on"><a href="https://www.kaia.re.kr/app/faq/faq_02.jsp" target="_blank" title="새창열림">www.kaia.re.kr</a></td>
                            </tr>
                            <tr >
                                <td class="txt-c">해양수산부</td>
                                <td>한국해양과학기술진흥원</td>
                                <td><a href="http://www.kimst.re.kr/board-list.do?boardId=qna" target="_blank" title="새창열림">www.kimst.re.kr</a></td>
                            </tr>
                            <tr>
                                <td class="txt-c">농림축산식품부</td>
                                <td class="on">기술기획평가원</td>
                                <td class="on"><a href="http://www.ipet.re.kr/Rnd/QnaLV.asp?cate=MB" target="_blank" title="새창열림">www.ipet.re.kr</a></td>
                            </tr>
                            <tr>
                                <td class="txt-c">보건복지부</td>
                                <td>한국보건산업진흥원</td>
                                <td><a href="http://www.khidi.or.kr/www/run.do?menu=02020000" target="_blank" title="새창열림">www.khidi.or.kr</a></td>
                            </tr>
                            <tr>
                                <td class="txt-c">환경부</td>
                                <td class="on">한국환경산업기술원</td>
                                <td class="on"><a href="https://ecoservice.keiti.re.kr:8446/main.do" target="_blank" title="새창열림">www.ecoservice.keiti.re.kr</a></td>
                            </tr>
                            <tr>
                                <td class="txt-c">문화체육관광부</td>
                                <td>한국콘텐츠진흥원</td>
                                <td><a href="http://www.kocca.kr/cop/contents.do?menuNo=200955" target="_blank" title="새창열림">www.kocca.kr</a></td>
                            </tr>
                            <tr>
                                <td class="txt-c">중소기업청</td>
                                <td class="on">중소기업기술정보진흥원</td>
                                <td class="on"><a href="http://www.tipa.or.kr/qna/qna_list.jsp" target="_blank" title="새창열림">www.tipa.or.kr</a></td>
                            </tr>
                            <tr>
                                <td class="txt-c">방위사업청</td>
                                <td>국방기술품질원</td>
                                <td><a href="http://www.dtaq.re.kr/board-list.do?boardId=notice" target="_blank" title="새창열림">www.dtaq.re.kr</a></td>
                            </tr>

                            <!-- <tr>
                                <td rowspan="2" class="txt-c">기상청</td>
                                <td class="on">한국기상산업진흥원</td>
                                <td class="on"><a href="www.kmipa.or.kr" target="_blank" title="새창열림">www.kmipa.or.kr</a></td>
                            </tr>
                            <tr>
                                <td class="line-l">기상기술개발원</td>
                                <td><a href="www.cater.re.kr" target="_blank" title="새창열림">www.cater.re.kr</a></td>
                            </tr>
                             -->
                            <tr>
                                <td class="txt-c">기상청</td>
                                <td class="on">연구관리시스템</td>
                                <td class="on"><a href="https://rnd.kma.go.kr/kma/main/main.vw" target="_blank" title="새창열림">www.rnd.kma.go.kr</a></td>
                            </tr>

                            <tr>
                                <td class="txt-c">소방방재청</td>
                                <td>국립재난안전 연구원</td>
                                <td><a href="http://www.ndmi.go.kr/depart/researchplan/notice/list.jsp" target="_blank" title="새창열림">www.ndmi.go.kr</a></td>
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