<%@page contentType="text/html; charset=utf-8" %>

<%@include file="/include/top.jsp"%>

    <!-- container -->
    <div id="container" class="advice">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>온라인 상담</h2>
                <span><img src="/img/common/h2_entxt02.gif" alt="Online Consultation" /></span>
            </div>
            <ul class="lnb-lst">
                <li class="on"><a href="JavaScript:goInquireMainList()">온라인 상담</a></li>
                <li><a href="JavaScript:goFaq()">자주 묻는 질문</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href="JavaScript:goInquireMainList()">온라인 상담</a></li>
                    <li class="on"><a href="JavaScript:goInquireMainList()">온라인 상담</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>온라인상담</h3>
                     <p>각 중앙행정기관에서 개별적으로 관리하는 국가연구개발사업 관련 질의 처리를 위해 각 부처 전문기관 홈페이지로의 링크 서비스를 제공합니다.</p>
                </div>
                <!--  explain-bx 
                <div class="explain-bx mt10">
                    <strong>각 중앙행정기관에서 개별적으로 운영하는 국가연구개발사업 관련 질의에 대한 정확한 답변제공을 위해, 각 부처에서 운영하는 전문기관 홈페이지로의 연결 서비스를 제공합니다.</strong>
                </div>
                 //explain-bx -->                
                <h5 class="mt20 line-bottom"><strong class="bullet">중앙행정기관별</strong></h5>
                <div class="mt10 center-officearea">
                    <ul id="onlineAdvice_btn">
                        <li><a href="#"><span>미래창조과학부</span></a></li>
                        <li><a href="#"><span>산업통상자원부</span></a></li>
                        <li><a href="#"><span>국토교통부</span></a></li>
                        <li><a href="#"><span>해양수산부</span></a></li>
                        <li><a href="#"><span>농림축산식품부</span></a></li>
                        <li><a href="#"><span>보건복지부</span></a></li>
                        <li><a href="#"><span>환경부</span></a></li>
                        <li><a href="#"><span>문화체육관광부</span></a></li>
                        <li><a href="#"><span>중소기업청</span></a></li>
                        <li><a href="#"><span>방위사업청</span></a></li>
                        <li><a href="#"><span>기상청</span></a></li>
                        <li><a href="#"><span>소방방재청</span></a></li>
                    </ul>
                </div>
                <div class="center-officeBanner">
                    <p class="title"><span class="ch_title">미래창조과학부</span> R&amp;D사업 관리 전문기관&nbsp;<small class="info">(배너를 클릭하시면 해당 홈페이지로 연결됩니다.)</small></p>
                    <div class="center-officeBanner-box">
                    <ul>
                        <li>
                            <a href="http://www.nrf.re.kr/nrf_tot_cms/board/qna_tot/list.jsp?show_no=174&check_no=169&c_relation=biz&c_relation2=0" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner01.gif" alt="한국연구재단"/>
                            <p>한국연구재단</p>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.nipa.kr/biz/tree.it?menuNo=17" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner02.gif" alt="정보통신산업진흥원"/>
                            <p>정보통신산업진흥원</p>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.nia.or.kr/bbs/freeboard_list.asp?BoardID=201112081218206347&boardtype=qna&order=040100" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner03.gif" alt="한국정보화진흥원"/>
                            <p>한국정보화진흥원</p>
                            </a>
                        </li>
                        <li class="fnone">
                            <a href="http://www.kisa.or.kr/main.jsp" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner04.gif" alt="한국인터넷진흥원"/>
                            <p>한국인터넷진흥원</p>
                            </a>
                        </li>
                    </ul>
                    <ul>
                        <li>
                            <a href="http://www.keit.re.kr/voc/list.do?gbn=02_21&voc_code=0027" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner05.gif" alt="한국산업기술평가관리원"/>
                            <p>한국산업기술평가관리원</p>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.kiat.or.kr/site/program/board/list.jsp?menuID=001003001&boardTypeID=4" target="_blank" title="새창열림"><a href="http://www.kiat.or.kr/site/program/board/list.jsp?menuID=001003001&boardTypeID=4" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner06.gif" alt="한국산업기술진흥원"/>
                            <p>한국산업기술진흥원</p>
                            </a>
                        </li>
                        <li>
                            <a href="http://voc.ketep.re.kr/ketep/contents/main/NR_index.do?_m=main" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner07.gif" alt="한국에너지기술평가원"/>
                            <p>한국에너지기술평가원</p>
                            </a>
                        </li>
                    </ul>
                    <ul>
                        <li>
                            <a href="https://www.kaia.re.kr/app/faq/faq_02.jsp" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner08.gif" alt="국토교통과학기술진흥원"/>
                            <p>국토교통과학기술진흥원</p>
                            </a>
                        </li>
                    </ul>
                    <ul>
                        <li>
                            <a href="http://www.kimst.re.kr/board-list.do?boardId=qna" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner09.gif" alt="한국해양과학기술진흥원"/>
                            <p>한국해양과학기술진흥원</p>
                            </a>
                        </li>
                    </ul>
                    <ul>
                        <li>
                            <a href="http://www.ipet.re.kr/Rnd/QnaLV.asp?cate=MB" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner10.gif" alt="농림수산식품기술기획평가원"/>
                            <p>농림수산식품기술기획평가원</p>
                            </a>
                        </li>
                    </ul>
                    <ul>
                        <li>
                            <a href="http://www.khidi.or.kr/www/run.do?menu=02020000" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner11.gif" alt="한국보건산업진흥원"/>
                            <p>한국보건산업진흥원</p>
                            </a>
                        </li>
                    </ul>
                    <ul>
                        <li>
                            <a href="https://ecoservice.keiti.re.kr:8446/main.do" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner12.gif" alt="한국환경산업기술원"/>
                            <p>한국환경산업기술원</p>
                            </a>
                        </li>
                    </ul>
                    <ul>
                        <li>
                            <a href="http://www.kocca.kr/cop/contents.do?menuNo=200955" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner13.gif" alt="한국콘텐츠진흥원"/>
                            <p>한국콘텐츠진흥원</p>
                            </a>
                        </li>
                    </ul>
                    <ul>
                        <li>
                            <a href="http://www.tipa.or.kr/qna/qna_list.jsp" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner14.gif" alt="중소기업기술정보진흥원"/>
                            <p>중소기업기술정보진흥원</p>
                            </a>
                        </li>
                    </ul>
                    <ul>
                        <li>
                            <a href="http://www.dtaq.re.kr/board-list.do?boardId=notice" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner15.gif" alt="국방기술품질원"/>
                            <p>국방기술품질원</p>
                            </a>
                        </li>
                    </ul>
                    <ul>
                        <li>
                            <a href="https://rnd.kma.go.kr/kma/main/main.vw" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner16.gif" alt="연구관리시스템"/>
                            <p>연구관리시스템</p>
                            </a>
                        </li>
                    </ul>
                    <ul>
                        <li>
                            <a href="http://www.ndmi.go.kr/depart/researchplan/notice/list.jsp" target="_blank" title="새창열림">
                            <img src="../../img/sub/centerOfficeBanner17.gif" alt="국립재난안전 연구원"/>
                            <p>국립재난안전 연구원</p>
                            </a>
                        </li>
                    </ul>
                    </div>
                </div>
                <h5 class="mt30 line-bottom"><strong class="bullet">전문기관별</strong><!--a href="#none" title="전문기관별 hide" class="ml10 active" id="close_id"><img src="../../img/btn/btn_open.gif" alt=""/></a--></h5>
                <div class="agency-listArea">
                    <div class="mt10 center-officearea2">
                        <ul>
                            <li><a href="http://www.nrf.re.kr/nrf_tot_cms/board/qna_tot/list.jsp?show_no=174&check_no=169&c_relation=biz&c_relation2=0" target="_blank" title="새창열림"><span>한국연구재단</span></a></li>
                            <li><a href="http://www.nipa.kr/biz/tree.it?menuNo=17" target="_blank" title="새창열림"><span>정보통신산업진흥원</span></a></li>
                            <li><a href="http://www.nia.or.kr/bbs/freeboard_list.asp?BoardID=201112081218206347&boardtype=qna&order=040100" target="_blank" title="새창열림"><span>한국정보화진흥원</span></a></li>
                            <li><a href="http://www.kisa.or.kr/main.jsp" target="_blank" title="새창열림"><span>한국인터넷진흥원</span></a></li>
                            <li><a href="http://www.keit.re.kr/voc/list.do?gbn=02_21&voc_code=0027" target="_blank" title="새창열림"><span>한국산업기술평가관리원</span></a></li>
                            <li><a href="http://www.kiat.or.kr/site/program/board/list.jsp?menuID=001003001&boardTypeID=4" target="_blank" title="새창열림"><span>한국산업기술진흥원</span></a></li>
                            <li><a href="http://voc.ketep.re.kr/ketep/contents/main/NR_index.do?_m=main" target="_blank" title="새창열림"><span>한국에너지기술평가원</span></a></li>
                            <li><a href="https://www.kaia.re.kr/app/faq/faq_02.jsp" target="_blank" title="새창열림"><span>국토교통과학기술진흥원</span></a></li>       
                            <li><a href="http://www.kimst.re.kr/board-list.do?boardId=qna" target="_blank" title="새창열림"><span>한국해양과학기술진흥원</span></a></li>
                            <li><a href="http://www.ipet.re.kr/Rnd/QnaLV.asp?cate=MB" target="_blank" title="새창열림"><span>농림수산식품기술기획평가원</span></a></li>
                            <li><a href="http://www.khidi.or.kr/www/run.do?menu=02020000" target="_blank" title="새창열림"><span>한국보건산업진흥원</span></a></li>
                            <li><a href="https://ecoservice.keiti.re.kr:8446/main.do" target="_blank" title="새창열림"><span>한국환경산업기술원</span></a></li>   
                            <li><a href="http://www.kocca.kr/cop/contents.do?menuNo=200955" target="_blank" title="새창열림"><span>한국콘텐츠진흥원</span></a></li>
                            <li><a href="http://www.tipa.or.kr/qna/qna_list.jsp" target="_blank" title="새창열림"><span>중소기업기술정보진흥원</span></a></li>
                            <li><a href="http://www.dtaq.re.kr/board-list.do?boardId=notice" target="_blank" title="새창열림"><span>국방기술품질원</span></a></li>
                            <li><a href="https://rnd.kma.go.kr/kma/main/main.vw" target="_blank" title="새창열림"><span>기상청 연구관리시스템</span></a></li>     
                            <li><a href="http://www.ndmi.go.kr" target="_blank" title="새창열림"><span>국립재난안전 연구원</span></a></li>        
                        </ul>
                    </div>
                    <!-- tbl-type01 
                    <div class="tbl-type02  mt10 agency-list">
                        <table border="0" summary="중앙행정기관, 전문기관, 전문기관 링크주소">
                            <caption>중앙행정기관 링크</caption>
                            <colgroup>
                                <col width="25%" />
                                <col width="25%" />
                                <col width="25%" />
                                <col width="*" />
                            </colgroup>
                            <tbody>
                                <tr>
                                    <td><a href="http://www.nrf.re.kr/nrf_tot_cms/board/qna_tot/list.jsp?show_no=174&check_no=169&c_relation=biz&c_relation2=0" target="_blank" title="새창열림">한국연구재단</a></td>
                                    <td><a href="http://www.nipa.kr/biz/tree.it?menuNo=17" target="_blank" title="새창열림">정보통신산업진흥원</a></td>
                                    <td><a href="http://www.nia.or.kr/bbs/freeboard_list.asp?BoardID=201112081218206347&boardtype=qna&order=040100" target="_blank" title="새창열림">한국정보화진흥원</a></td>
                                    <td class="line-l"><a href="http://www.kisa.or.kr/main.jsp" target="_blank" title="새창열림">한국인터넷진흥원</a></td>
                                </tr>
                                <tr >
                                    <td ><a href="http://www.keit.re.kr/voc/list.do?gbn=02_21&voc_code=0027" target="_blank" title="새창열림">한국산업기술평가관리원</a></td>
                                    <td><a href="http://www.kiat.or.kr/site/program/board/list.jsp?menuID=001003001&boardTypeID=4" target="_blank" title="새창열림">한국산업기술진흥원</a></td>
                                    <td><a href="http://voc.ketep.re.kr/ketep/contents/main/NR_index.do?_m=main" target="_blank" title="새창열림">한국에너지기술평가원</a></td>
                                    <td><a href="https://www.kaia.re.kr/app/faq/faq_02.jsp" target="_blank" title="새창열림">국토교통과학기술진흥원</a></td>
                                </tr>
                                <tr >
                                    <td><a href="http://www.kimst.re.kr/board-list.do?boardId=qna" target="_blank" title="새창열림">한국해양과학기술진흥원</a></td>
                                    <td><a href="http://www.ipet.re.kr/Rnd/QnaLV.asp?cate=MB" target="_blank" title="새창열림">농림수산식품기술기획평가원</a></td>
                                    <td><a href="http://www.khidi.or.kr/www/run.do?menu=02020000" target="_blank" title="새창열림">한국보건산업진흥원</a></td>
                                    <td><a href="https://ecoservice.keiti.re.kr:8446/main.do" target="_blank" title="새창열림">한국환경산업기술원</a></td>
                                </tr>
                                <tr >
                                    <td><a href="http://www.kocca.kr/cop/contents.do?menuNo=200955" target="_blank" title="새창열림">한국콘텐츠진흥원</a></td>
                                    <td><a href="http://www.tipa.or.kr/qna/qna_list.jsp" target="_blank" title="새창열림">중소기업기술정보진흥원</a></td>
                                    <td><a href="http://www.dtaq.re.kr/board-list.do?boardId=notice" target="_blank" title="새창열림">국방기술품질원</a></td>
                                    <td><a href="https://rnd.kma.go.kr/kma/main/main.vw" target="_blank" title="새창열림">연구관리시스템</a></td>
                                </tr>
                                <tr>
                                    <td><a href="www.kmipa.or.kr" target="_blank" title="새창열림">국립재난안전 연구원</a></td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>                 
                                    <td>&nbsp;</td>                             
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- //tbl-type01 -->
                </div>
                <!-- //agency-listArea -->
            </div>
            <!-- //section -->
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->
    
<%@include file="/include/bottom.jsp"%>