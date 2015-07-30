<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" session="false"%>
<div id="sidebar">
	<c:choose>
		<c:when test="${ fn:indexOf(pageContext.request.requestURI, 'menu001') >= 0 }">
			<!--//녹색성장-->
			<div id="side_box">
				<p>
					<img src="<c:url value="/images/menu001/menu_tt01.gif" />">
				</p>
				<ul class="side_1dh">
					<li>
						<a href="<c:url value="/menu001/sub001/GRG_001_101.do" />" class="<c:out value="${ fn:indexOf(pageContext.request.requestURI, 'sub001') >= 0 ? 'ontitle' : 'outtitle' }" />"><b>개념</b></a>
						<ul>
							<li>
								<a href="<c:url value="/menu001/sub001/GRG_001_101.do" />" class="onsubtit">개념
									<c:if test="${ fn:indexOf(pageContext.request.requestURI, 'GRG_001_101') >= 0 }">
										<span>
											<img src="<c:url value="/images/menu001/menu_ic.gif" />">
										</span>
									</c:if>
								</a>
							</li>
							<li class="last">
								<a href="<c:url value="/menu001/sub001/GRG_001_102.do" />" class="onsubtit">추진배경
									<c:if test="${ fn:indexOf(pageContext.request.requestURI, 'GRG_001_102') >= 0 }">
										<span>
											<img src="<c:url value="/images/menu001/menu_ic.gif" />">
										</span>
									</c:if>
								</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="<c:url value="/menu001/sub002/GRG_001_201.do" />" class="<c:out value="${ fn:indexOf(pageContext.request.requestURI, 'sub002') >= 0 ? 'ontitle' : 'outtitle' }" />"><b>국가전략</b></a>
						<ul>
							<li>
								<a href="<c:url value="/menu001/sub002/GRG_001_201.do" />" class="onsubtit">국가전략
									<c:if test="${ fn:indexOf(pageContext.request.requestURI, 'GRG_001_201') >= 0 }">
										<span>
											<img src="<c:url value="/images/menu001/menu_ic.gif" />">
										</span>
									</c:if>
								</a>
							</li>
							<li class="last">
								<a href="<c:url value="/menu001/sub002/GRG_001_202.do" />" class="onsubtit">5개년 계획
									<c:if test="${ fn:indexOf(pageContext.request.requestURI, 'GRG_001_202') >= 0 }">
										<span>
											<img src="<c:url value="/images/menu001/menu_ic.gif" />">
										</span>
									</c:if>
								</a>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</c:when>
		<c:when test="${ param.category_seq eq '1' || param.category_seq eq '2' }">
			<!--//소식-->
			<div id="side_box">
				<p>
					<img src="<c:url value="/images/menu002/menu_tt02.gif" />">
				</p>
				<ul class="side_1dh">
					<li class="last"><a href="<c:url value="/cmmn/commonBoardList.do?category_seq=1" />" class="<c:out value="${ param.category_seq eq '1' ? 'ontitle' : 'outtitle' }" />"><b>위원회 활동</b></a></li>
					<li class="last"><a href="<c:url value="/cmmn/commonBoardList.do?category_seq=2" />" class="<c:out value="${ param.category_seq eq '2' ? 'ontitle' : 'outtitle' }" />"><b>주요소식</b></a></li>
				</ul>
			</div>
		</c:when>
		<c:when test="${ (param.category_seq eq '3' || param.category_seq eq '4' || param.category_seq eq '5')
			|| fn:indexOf(pageContext.request.requestURI, 'menu003') >= 0 }">
			<!--//정보-->
			<div id="side_box">
				<p>
					<img src="<c:url value="/images/menu003/menu_tt03.gif" />">
				</p>
				<ul class="side_1dh">
					<li>
						<a href="<c:url value="/cmmn/commonBoardList.do?category_seq=3" />" class="<c:out value="${ param.category_seq eq '3' ? 'ontitle' : 'outtitle' }" />"><b>회의자료</b></a>
						<ul>
							<li>
								<a href="<c:url value="/cmmn/commonBoardList.do?category_seq=3" />">회의자료(2013.03~)
									<c:if test="${ param.category_seq eq '3' }">
										<span>
											<img src="<c:url value="/images/menu001/menu_ic.gif" />">
										</span>
									</c:if>
								</a>
							</li>
							<li class="last">
								<a href="<c:url value="/cmmn/commonBoardList.do?category_seq=4" />">회의자료(~2013.02)
									<c:if test="${ param.category_seq eq '4' }">
										<span>
											<img src="<c:url value="/images/menu001/menu_ic.gif" />">
										</span>
									</c:if>
								</a>
							</li>
						</ul>
					</li>
					<li class="last"><a href="<c:url value="/menu003/sub002/GRG_003_201.do" />" class="<c:out value="${ fn:indexOf(pageContext.request.requestURI, 'sub002') >= 0 ? 'ontitle' : 'outtitle' }" />"><b>녹색법령</b></a></li>
					<li class="last"><a href="<c:url value="/menu003/sub003/GRG_003_301.do?category_seq=5" />" class="<c:out value="${ param.category_seq eq '5' ? 'ontitle' : 'outtitle' }" />"><b>용어사전</b></a></li>
					<li class="last"><a href="<c:url value="/menu003/sub004/GRG_003_401.do" />" class="<c:out value="${ fn:indexOf(pageContext.request.requestURI, 'sub004') >= 0 ? 'ontitle' : 'outtitle' }" />"><b>관련 사이트</b></a></li>
				</ul>
			</div>
		</c:when>
		<c:when test="${ fn:indexOf(pageContext.request.requestURI, 'menu004') >= 0 }">
			<!--//위원회-->
			<div id="side_box">
				<p>
					<img src="<c:url value="/images/menu004/menu_tt04.gif" />">
				</p>
				<ul class="side_1dh">
					<li class="last"><a href="<c:url value="/menu004/sub001/GRG_004_101.do" />" class="<c:out value="${ fn:indexOf(pageContext.request.requestURI, 'GRG_004_101') >= 0 ? 'ontitle' : 'outtitle' }" />"><b>인사말</b></a></li>
					<li class="last"><a href="<c:url value="/menu004/sub002/GRG_004_201.do" />" class="<c:out value="${ fn:indexOf(pageContext.request.requestURI, 'GRG_004_201') >= 0 ? 'ontitle' : 'outtitle' }" />"><b>위원소개</b></a>
						<ul>
							<li>
								<a href="<c:url value="/menu004/sub002/GRG_004_201.do" />" class="onsubtit">당연직위원
									<c:if test="${ fn:indexOf(pageContext.request.requestURI, 'GRG_004_201') >= 0 }">
										<span>
											<img src="<c:url value="/images/menu001/menu_ic.gif" />">
										</span>
									</c:if>
								</a>
							</li>
						</ul>
						<ul>
							<li>
								<a href="<c:url value="/menu004/sub002/GRG_004_202.do" />" class="onsubtit">민간위원
									<c:if test="${ fn:indexOf(pageContext.request.requestURI, 'GRG_004_202') >= 0 }">
										<span>
											<img src="<c:url value="/images/menu001/menu_ic.gif" />">
										</span>
									</c:if>
								</a>
							</li>
						</ul>
					</li>
					<li class="last"><a href="<c:url value="/menu004/sub003/GRG_004_301.do" />" class="<c:out value="${ fn:indexOf(pageContext.request.requestURI, 'GRG_004_301') >= 0 ? 'ontitle' : 'outtitle' }" />"><b>위원회연혁</b></a></li>
					<li class="last"><a href="<c:url value="/menu004/sub004/GRG_004_401.do" />" class="<c:out value="${ fn:indexOf(pageContext.request.requestURI, 'GRG_004_401') >= 0 ? 'ontitle' : 'outtitle' }" />"><b>위원회기능 및 구성 </b></a></li>
					<li class="last"><a href="<c:url value="/menu004/sub005/GRG_004_501.do" />" class="<c:out value="${ fn:indexOf(pageContext.request.requestURI, 'GRG_004_501') >= 0 ? 'ontitle' : 'outtitle' }" />"><b>위원회로고</b></a></li>
				</ul>
			</div>
		</c:when>
		<c:when test="${ fn:indexOf(pageContext.request.requestURI, 'menu999') >= 0 && fn:indexOf(pageContext.request.requestURI, 'GRG_999_301') == -1 }">
			<!--//위원회-->
			<div id="side_box">
				<p>
					<img src="<c:url value="/images/menu999/menu_tt05.gif" />">
				</p>
				<ul class="side_1dh">
					<li class="last"><a href="<c:url value="/menu999/sub001/GRG_999_101.do" />" class="<c:out value="${ fn:indexOf(pageContext.request.requestURI, 'GRG_999_101') >= 0 ? 'ontitle' : 'outtitle' }" />"><b>홈페이지이용안내</b></a></li>
					<li class="last"><a href="<c:url value="/menu999/sub002/GRG_999_201.do" />" class="<c:out value="${ fn:indexOf(pageContext.request.requestURI, 'GRG_999_201') >= 0 ? 'ontitle' : 'outtitle' }" />"><b>저작권정책</b></a></li>
					<li class="last"><a href="<c:url value="/menu999/sub003/GRG_999_301.do" />" class="<c:out value="${ fn:indexOf(pageContext.request.requestURI, 'GRG_999_301') >= 0 ? 'ontitle' : 'outtitle' }" />"><b>사이트맵</b></a></li>
					<li class="last"><a href="<c:url value="/menu999/sub004/GRG_999_401.do" />" class="<c:out value="${ fn:indexOf(pageContext.request.requestURI, 'GRG_999_401') >= 0 ? 'ontitle' : 'outtitle' }" />"><b>찾아오시는길</b></a></li>
				</ul>
			</div>
		</c:when>
	</c:choose>
	
</div>