<%@page contentType="text/html; charset=utf-8" %>

<%@include file="/include/top.jsp"%>
	
	<script type="text/javascript">
	<!--
		var data = {
			num : 5	// 위치순번
		};
		// 현재메뉴 위치.
		menuFocus(data);
	//-->
	</script>
	
	<!-- container -->
	<div id="container">
		<!-- lnb -->
		<div class="lnb">
			<div class="tit-area">
				<h2>센터소개</h2>
				<span><img src="/img/common/h2_entxt06.gif" alt="Introduce" /></span>
			</div>
			<ul class="lnb-lst">
				<li class="on"><a href="/center/center.jsp">센터소개</a></li>
			</ul>				
		</div>
		<!-- //lnb -->
		<!-- content -->
		<div class="content clearfix">
			<div class="location txt-r">		
				<ul class="fr clearfix">
					<li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
					<li class="on"><a href="/center/center.jsp">센터소개</a></li>
				</ul>
			</div>
			<!-- section -->
            <div class="section">       
                <!-- center_bx -->
                <div class="center-bx mt60">
                    <h4>R&amp;D도우미센터는</h4>
                    <p class="txt">국가연구개발사업 수행 중 연구현장에서 발생하는 애로사항을 <br />해결해드리기 위하여 미래창조과학부에서 운영하고 있습니다.</p>
                    <ul>
                        <li>
                            <p class="tit"><span>온라인상담</span> <strong>바로가기</strong><a href="JavaScript:goInquireForm()"><img src="/img/sub/go_btn01.gif" alt="go" /></a></p>
                            <p>「국가연구개발사업의 관리 등에 관한 규정」 및 관련 주요제도에 대해 궁금할 때 전문상담원이 연구 자가 질의한 사항에 대한 명쾌한 답변을 해드립니다.</p>
                        </li>
                        <li class="bg01">
                            <p class="tit color-p"><span>R&amp;D 신문고</span> <strong>바로가기</strong><a href="JavaScript:goOffer()"><img src="/img/sub/go_btn02.gif" alt="go" /></a></p>
                            <p>연구현장의 불합리한 제도나 관행 등에 대한 개선사항이나 좋은 아이디어가 있을 때 제안해 주신 의견을 수렴하여 제도개선을 추진합니다.<br /><br />
                                최근 신설되거나 개선된 연구관리 제도를 현장에 적용하는 과정에서 생기는 애로사항이 있을 때 온라인 신문고를 통해 제안해 주시면 법적·제도적으로 직접 해결하여 드립니다.</p>
                        </li>
                        <li class="bg02">
                            <p class="tit color-y"><span>자료실</span> <strong>바로가기</strong><a href="JavaScript:goData()"><img src="/img/sub/go_btn03.gif" alt="go" /></a></p>
                            <p>국가연구개발사업 관련 규정, 지침 등을 한 곳에서 확인할 수 있도록 자료를 제공합니다.</p>
                        </li>
                    </ul>               
                </div>
                <!-- //center_bx -->
                <div>
                    <p class="bullet">접수 및 처리절차</p>
                    <ul class="center-lst clearfix">
                        <li class="info">
                            <div>
                                <strong>질의사항 접수</strong>
                                <ul class="lst-dot">
                                    <li>(대상)국가 R&amp;D 사업 제도 관련 애로사항</li>
                                    <li>(방법)전화(1800-7109) 홈페이지<br /><a href="www.mdcall.go.kr" target="_blank">(www.mdcall.go.kr)</a></li>
                                </ul>
                            </div>
                            <span class="arrow"></span>
                        </li>
                        <li class="info bg01">
                            <div>
                                <strong>답변제공</strong>
                                <ul class="lst-dot">
                                    <li>접수 후 신속하게 답변 </li>
                                    <li>필요할 경우 관계부처 협의, 전문가 자문 진행</li>
                                </ul>
                            </div>
                            <span class="arrow"></span>
                        </li>
                        <li class="info bg02">
                            <div>
                                <strong>후속조치</strong>
                                <ul class="lst-dot">
                                    <li>유사질의 검색서비스 제공</li>
                                    <li>R&amp;D 관련 제도개선 시 검토 및 반영</li>
                                </ul>
                            </div>
                        </li>
                    </ul>                   
                </div>
                <div>
                    <p class="bullet">이용안내</p>
                    <ul class="center-info">
                        <li><strong>상담전화 :</strong> 1800-7109(친한연구) (상담 가능시간 : 평일 오전 9:00 ~ 11:30, 오후 1:30 ~ 6:00)</li>
                        <li class="txt"><strong>온라인상담 :</strong> 「온라인상담」 메뉴 이용</li>
                        <li class="txt01"><strong>각종 건의 :</strong> 「온라인신문고」 메뉴 이용</li>
                    </ul>
                </div>
            </div>  
            <!-- //section -->
		</div>
		<!-- //content -->
	</div>
	<!-- // container -->
		
<%@include file="/include/bottom.jsp"%>