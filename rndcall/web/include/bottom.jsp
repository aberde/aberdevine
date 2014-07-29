<%@ page contentType="text/html; charset=utf-8" %>	
<!-- start .footer -->

<script language="JavaScript" type="text/JavaScript">
<!--
function goSiteUrl(num){
	var url = eval("document.siteUrlForm.siteurl"+num).value;
	if(url != '' && url != '#'){
		window.open(url, '_blank');
	}
}
//-->
</script>
	<!-- footer -->
    <div id="footer">
        <div class="footer clearfix">
            <p class="foot-logo"><img src="/img/common/foot_logo.gif" alt="미래창조과학부" /></p>
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