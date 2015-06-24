<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" session="false"%>

<%@ include file="/WEB-INF/jsp/egovframework/include/header.jsp" %>

<div id="home_container">

	<div class="home_left">
		<img src="<c:url value="/images/main/home_l_img01.gif" />" alt="녹색성장 더 큰 대한민국" />
		<div id="search" style="margin-top: 15px;">
			<form name="frm" method="post" action="<c:url value="/main/search.do" />" onsubmit="">
				<fieldset>
					<input type="text" id="s" name="searchKeyword" style="width:165px;" value="검색어를 입력하세요" onfocus="if(this.value==this.defaultValue)this.value='';" onblur="if(this.value=='')this.value=this.defaultValue;"/>
				</fieldset>
				<input type="image" id="searchsubmit" src="<c:url value="/images/main/home_l_btn.gif" />" style="margin-top:-4px" alt="Search in site..." />
			</form>
		</div>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<a href="<c:url value="/cmmn/commonBoardList.do?category_seq=3" />"><img style="margin-left: 5px" src="<c:url value="/images/main/home_l_img02.gif" />" alt="자료실" /></a>
	</div>
	<div class="home_center">
		<p class="home_c_main">
			<img src="<c:url value="/images/main/home_c_main.gif" />" alt="Geen KOREA 2013" />
		</p>
		<div class="news">
			<img src="<c:url value="/images/main/home_c_title.gif" />" alt="뉴스" />
			<ul>
				<div id="priBotTab2" class="primaryBottomTabs_body">
					<ul>
						
						<c:forEach var="vo" items="${ boardList }">
						
						<li>
							<span class="tabTitle"><img src="<c:url value="/images/main/bullet_arrow.gif" />" align="absmiddle" border="0" alt="" />
							<a href="<c:url value="/cmmn/commonBoardView.do?board_seq=${ vo.board_seq }" />" title="<c:out value="Permalink to ${ vo.title }" />">
								<c:out value="${ ct:subStringBytes(vo.title, 50) }" />
							</a>
							</span>
						</li>
						
						</c:forEach>
						
					</ul>
				</div>
			</ul>

		</div>
	</div>

	<div class="home_right">
		<div class="home_r_bn">
			<ul>
				<li class="bg">
					<a href="<c:url value="/menu004/sub001/GRG_004_101.do" />" class="go" title="위원회 소개가기"><img src="<c:url value="/images/main/go.png" />" alt="go" /></a>
				</li>
				<li class="bg">
					<a href="<c:url value="/menu003/sub002/GRG_003_201.do" />" class="go" title="녹색법령가기"><img src="<c:url value="/images/main/go.png" />" alt="go" /></a>
				</li>
				<li class="bg2">
					<a href="<c:url value="/cmmn/commonBoardList.do?category_seq=1" />" class="go" title="위원회 활동"><img src="<c:url value="/images/main/go.png" />" alt="go" /></a>
				</li>
				<li class="bg2">
					<a href="<c:url value="/menu001/sub002/GRG_001_201.do" />" class="go" title="국가계획"><img src="<c:url value="/images/main/go.png" />" alt="go" /></a>
				</li>
			</ul>
		</div>

		<div class="home_r_weather">
			<div class="weather_tit">
				<img src="<c:url value="/images/main/home_r_title01.gif" />" alt="날씨" />
			</div>
			<div style="color: #d3d3d3;">
				<iframe src="<c:url value="/main/weatherTicker.do" />" name="myframe" width="300px" height="18px" marginwidth="0" marginheight="0" frameborder="no" scrolling="no"></iframe>
			</div>
		</div>

		<div class="home_r_bn2">
			<a href="<c:url value="/download/green-eng-bro.pdf" />" target="_blank"><img src="<c:url value="/images/main/e-book_22.jpg" />" alt="녹색성장 더 큰 대한민국" /></a>
		</div>

	</div>

</div>

<%@ include file="/WEB-INF/jsp/egovframework/include/footer.jsp" %>