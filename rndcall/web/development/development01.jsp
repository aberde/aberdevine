<%@page contentType="text/html; charset=utf-8" %>

<%@include file="/include/top.jsp"%>

	<script type="text/javascript">
	<!--
		var data = {
			num : 0	// 위치순번
		};
		// 현재메뉴 위치.
		menuFocus(data);
	//-->
	</script>
	
	<!-- container -->
	<div id="container" class="development">
		<!-- lnb -->
		<div class="lnb">
			<div class="tit-area">
				<h2 class="develop-lnb">국가연구개발 <br />사업이란?</h2>
			</div>
			<ul class="lnb-lst">
				<li class="on"><a href="#none;">정의 및 법령체계</a></li>
				<li><a href="/development/development02.jsp">사업추진체계</a></li>
			</ul>				
		</div>
		<!-- //lnb -->
		<!-- content -->
		<div class="content clearfix">
			<div class="location txt-r">		
				<ul class="fr clearfix">
					<li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
					<li><a href="/development/development01.jsp">국가연구개발사업이란?</a></li>
					<li class="on"><a href="/development/development01.jsp">정의 및 법령체계</a></li>
				</ul>
			</div>
			<!-- section -->
            <div class="section develop02-bx">
                <!-- develop02 -->
                <div class="develop02 mt30">
                    <!-- dif-bx -->
                    <div class="dif-bx">
                        <h4>정의 및 법령체계</h4>

                        <h5 class="mt40 line-bottom"><strong class="bullet">정의</strong></h5>
                        <p class="dif-txt mt10">각 중앙행정기관에서는 정부가 마련한 「과학기술기본계획」에 따라<br />
                        맡은 분야에 대한 국가연구개발사업을 추진하여야 합니다.<br /><br />
                        이에 일반적으로 각 중앙행정기관에서 법령에 근거하여 연구개발과제를 특정,<br />
                        그 연구개발비의 전부 또는 일부를 출연(국가 등이 반대급부 없이 예산이나<br />
                        기금 등에서 지급하는 금액)하거나 공공기금 등으로 지원 기획 관리하는<br />
                        사업을 “국가연구개발사업”이라 합니다.
                        </p>
                        <h5 class="mt40"><strong class="bullet">각 중앙행정기관 개별 근거법(예시)</strong></h5>
                        <!-- tbl-type01 -->
                        <p class="ex-txt mt10">- 범 부처 공통법령 : 「과학기술기본법」 「국가연구개발사업의 관리 등에 관한 규정」</p>
                        <div class="tbl-type01 mt10">
                            <table border="0" summary="중앙행정기관, 법률명 ">
                                <caption>각 중앙행정기관 개별 근거법(예시)</caption>
                                <colgroup>
                                    <col width="19%" />
                                    <col width="*" />
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th scope="col">중앙행정기관</th>
                                        <th scope="col">법률명</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td class="txt-c">미래창조과학부</td>
                                        <td>「기초연구진흥 및 기술개발지원에 관한 법률」 「우주개발진흥법」 「핵융합에너지개발진흥법」 「정보통신 진흥 및 융합 활성화 등에 관한 특별법」 「방송통신발전기본법」등</td>
                                    </tr>
                                    <tr class="on">
                                        <td class="txt-c">산업통상자원부</td>
                                        <td>「산업기술혁신촉진법」 「민,군겸용기술사업촉진법」 「산업발전법」 「산업집적활성화 및 공장설립에 관한 법률」 등</td>
                                    </tr>
                                    <tr>
                                        <td class="txt-c">국토교통부</td>
                                        <td>「건설기술관리법」 「국가통합교통체계효율화법」 「철도산업발전기본법」 「도시철도법」 「철도안전법」 </td>
                                    </tr>
                                    <tr class="on">
                                        <td class="txt-c">농림축산식품부</td>
                                        <td>「농림수산식품과학기술 육성법」 「농어업,농어촌 및 식품산업 기본법」 「생명공학육성법」 「식품산업진흥법」 등</td>
                                    </tr>
                                    <tr>
                                        <td class="txt-c">중소기업청</td>
                                        <td>「중소기업기술혁신 촉진법」 등</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <!-- //tbl-type01 -->
                        
                        <!-- <h5 class="mt40"><strong class="bullet">관련법령</strong></h5> -->
                        <!-- dif2-bx -->
                        <!-- <div class="dif2-bx mt10">
                            <dl>
                                <dt class="mt20">「과학기술기본법」 </dt>
                                <dd class="b-line mt10">
                                    제7조(과학기술기본계획) ① 정부는 이 법의 목적을 효율적으로 달성하기 위하여 과학기술발전에 관한 중·장기 정책목표와 방향을 설정하고, 이에 따른 과학기술기본계획(이하 ‘기본계획’이라 한다)을 세우고 추진하여야 한다.<br />
                                    제11조(국가연구개발사업의 추진) ① 관계 중앙행정기관의 장은 기본계획에 따라 맡은 분야의 국가연구개발사업과 그 지원시책을 세워 추진하여야 한다.<br />
                                    ③ 정부는 국가연구개발사업을 투명하고 공정하게 추진하고 효율적으로 관리하며 각 부처가 추진하는 국가연구개발사업을 긴밀히 연계하기 위하여 다음 각 호에 관한 사항을 정하여야 한다.
                                    <ul>
                                        <li>1. 국가연구개발사업의 기획, 공고 등에 관한 사항</li>
                                        <li>2. 국가연구개발사업의 과제의 선정, 협약 등에 관한 사항</li>
                                        <li>3. 연구개발 결과의 평가 및 활용 등에 관한 사항</li>
                                        <li>4. 국가연구개발사업의 보안, 정보관리, 성과관리, 연구윤리의 확보 등 연구수행의 기반에 관한 사항</li>
                                    </ul>
                                </dd>
                            
                                <dt class="mt20">「국가연구개발사업의 관리 등에 관한 규정」</dt>
                                <dd class="mt10">
                                    제1조(목적) 이 영은 「과학기술기본법」 제11조 및 제11조의2부터 제11조의5까지의 규정에 따른 국가연구개발사업의 기획·관리·평가 및 활용 등에 필요한 사항을 규정함을 목적으로 한다.<br />
                                    제2조(정의) 이 영에서 사용하는 용어의 뜻은 아래와 같다.<br />
                                    1. “국가연구개발사업”이란 중앙행정기관이 법령에 근거하여 연구개발과제를 특정하여 그 연구개발비의 전부 또는 일부를 출연하거나 공공기금 등으로 지원하는 과학기술 분야의 연구개발사업을 말한다.<br />
                                    제34조(세부 규정의 제정·운영) 중앙행정기관의 장은 이 영에 저촉되지 아니하는 범위에서 국가연구개발사업의 관리 등에 관한 세부 규정을 제정·시행할 수 있다.
                                </dd>
                            </dl>
                        </div> -->
                        <h5 class="mt40"><strong class="bullet">법령체계</strong></h5>
                        <div class="tbl-type01 mt10">
                            <table border="0" summary="중앙행정기관, 법률명">
                                <caption>각 중앙행정기관 개별 근거법(예시)</caption>
                                <colgroup>
                                    <col width="25%" />
                                    <col width="*" />
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="row" class="txt-c">법률</th>
                                        <td>「과학기술기본법」 </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">대통령령</th>
                                        <td>「국가연구개발사업의 관리 등에 관한 규정」 (공동관리규정)</td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">미래창조과학부령</th>
                                        <td>「국가연구개발사업의 관리 등에 관한 규칙」 <br />
                                        <span class="small">* 「연구윤리 확보 및 부정행위 방지에 관한 규칙」 및 「연구개발계획서 등의 서식에 관한 규칙」 통합</span></td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c" rowspan="6">행정규칙</th>
                                        <td>국가연구개발사업 기관별 간접비 계상기준</td>
                                    </tr>
                                    <tr>
                                        <td class="row-line">국가연구개발사업 동시수행 연구개발과제 수 제한 기준(3책5공)</td>
                                        </tr>
                                    <tr>
                                        <td class="row-line">연구노트 지침</td></tr>
                                    <tr>
                                        <td class="row-line">연구성과 분야별 관리. 유통 전담기관 지정</td>
                                    </tr>
                                    <tr>
                                        <td class="row-line">학생인건비 계상기준</td>
                                    </tr>
                                    <tr>
                                        <td class="row-line">학생인건비 통합관리 지침</td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">미래창조과학부 훈령</th>
                                        <td>「미래창조과학부 소관 과학기술분야 연구개발사업 처리규정」 <br />
                                        「정보통신.방송 기술개발사업 수행관리지침」 </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">교육부 훈령</th>
                                        <td>「교육부 소관 이공분야 연구개발사업 처리규정」 </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">국토교통부 훈령</th>
                                        <td>「국토교통부 소관 연구개발사업 운영규정」 </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">농림축산식품부 훈령</th>
                                        <td>「농림축산식품 연구개발사업 운영규정」 </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">문화체육관광부 훈령</th>
                                        <td>「문화체육관광 연구개발사업 관리규정」 </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">보건복지부 훈령</th>
                                        <td>「보건의료기술연구개발사업 관리규정」 </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">산업통상자원부 훈령</th>
                                        <td>「산업기술혁신사업 공통운영요령」 </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">안전행정부 훈령</th>
                                        <td>「안전행정부 소관 재난안전관련 연구개발사업 처리규정」 </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">해양수산부 훈령</th>
                                        <td>「해양수산 연구개발사업 운영규정」 </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">환경부 훈령</th>
                                        <td>「환경기술개발사업운영규정」 </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">식품의약품안전처 훈령</th>
                                        <td>「식품의약품안전처 연구개발사업관리규정」 </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">기상청 훈령</th>
                                        <td>「기상업무 연구개발사업 처리규정」 </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">농촌진흥청 훈령</th>
                                        <td>「농촌진흥청 농업과학기술 연구개발사업 운영규정」 </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">문화재청 훈령</th>
                                        <td>「국립문화재연구소 용역연구개발사업 운영규정」 </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">산림청 훈령</th>
                                        <td>「산림분야 연구개발사업의 관리 등에 관한 규정」 </td>
                                    </tr>
                                    <!-- <tr>
                                        <th scope="row" class="txt-c">산업통상자원부 훈령</th>
                                        <td>「산업기술혁신사업 공통운영요령」 </td>
                                    </tr> -->
                                    <tr>
                                        <th scope="row" class="txt-c">소방방재청 훈령</th>
                                        <td>「소방방재청 연구개발사업 처리규정」 </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="txt-c">중소기업청 고시</th>
                                        <td>「중소기업기술개발 지원사업 운영요령」 </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <!-- //tbl-type01 -->
                    </div>
                    <!-- //dif-bx -->
                </div>
                <!-- //develop02 -->
            </div>  
            <!-- //section -->
		</div>
		<!-- //content -->
	</div>
	<!-- // container -->
		
<%@include file="/include/bottom.jsp"%>