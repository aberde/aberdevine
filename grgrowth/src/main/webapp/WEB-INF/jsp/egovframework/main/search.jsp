<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" session="false"%>

<%@ include file="/WEB-INF/jsp/egovframework/include/header.jsp" %>

<script type="text/javascript">
	/**
	 * 페이징
	 */
	function linkToPage(pageNo) {
		document.frm.pageIndex.value = pageNo;
		document.frm.action = "<c:url value='/main/search.do'/>";
		document.frm.submit();
	}
</script>

<div id="sectionWrap">

	<div id="sectionTab">
		<div class="sectionTab<c:out value="${ empty vo.searchCondition ? 'On' : 'Off' }" />" id="searchTotal" onclick="location.href='<c:url value="/main/search.do" />?searchKeyword=<c:out value="${ vo.searchKeyword }" />';">
			<div class="imgTag"><span>통합검색</span></div>
			<div class="arrowTag"></div>
		</div>
		<div class="sectionTab<c:out value="${ vo.searchCondition eq '1' ? 'On' : 'Off' }" />" id="searchPolicy" onclick="location.href='<c:url value="/main/search.do" />?searchKeyword=<c:out value="${ vo.searchKeyword }" />&searchCondition=1';">
			<div class="imgTag"><span>위원회활동</span></div>
			<div class="arrowTag"></div>
		</div>
		<div class="sectionTab<c:out value="${ vo.searchCondition eq '2' ? 'On' : 'Off' }" />" id="searchNews" onclick="location.href='<c:url value="/main/search.do" />?searchKeyword=<c:out value="${ vo.searchKeyword }" />&searchCondition=2';">
			<div class="imgTag"><span>주요소식</span></div>
			<div class="arrowTag"></div>
		</div>
		<div class="sectionTab<c:out value="${ vo.searchCondition eq '3' ? 'On' : 'Off' }" />" id="searchMultimedia" onclick="location.href='<c:url value="/main/search.do" />?searchKeyword=<c:out value="${ vo.searchKeyword }" />&searchCondition=3';">
			<div class="imgTag"><span>회의자료(2013.03~)</span></div>
			<div class="arrowTag"></div>
		</div>
		<div class="sectionTab<c:out value="${ vo.searchCondition eq '4' ? 'On' : 'Off' }" />" id="searchEdudata" onclick="location.href='<c:url value="/main/search.do" />?searchKeyword=<c:out value="${ vo.searchKeyword }" />&searchCondition=4';">
			<div class="imgTag"><span>회의자료(~2013.02)</span></div>
			<div class="arrowTag"></div>
		</div>
		<div class="sectionTab<c:out value="${ vo.searchCondition eq '5' ? 'On' : 'Off' }" />" id="searchPage" onclick="location.href='<c:url value="/main/search.do" />?searchKeyword=<c:out value="${ vo.searchKeyword }" />&searchCondition=5';">
			<div class="imgTag"><span>용어사전</span></div>
			<div class="arrowTag"></div>
		</div>
	</div>

	<div id="sectionContainer">
		
		<c:forEach var="data" items="${ list }">
		
			<!-- 검색구분명 -->
			<div class="sectionTabTitle">
				<img src="<c:url value="/images/search/section_${ data.searchCondition }.gif" />" border="0" alt="" />
			</div>

			<!-- 검색목록 -->
			<c:forEach var="board" items="${ data.searchList }">
			
				<div id="post-<c:out value="${ board.board_seq }" />" class="post-<c:out value="${ board.board_seq }" /> post type-post status-publish format-standard hentry category-green-sido-data entry">
					<h2 class="archiveTitle"><a href="<c:url value="/cmmn/commonBoardView.do?category_seq=${ board.category_seq }&board_seq=${ board.board_seq }" />" rel="bookmark" title="<c:out value="${ board.title }" />"><c:out value="${ board.title }" /></a></h2> &nbsp;&nbsp;
					<span class="postDate">
						<c:out value="${ board.regdate }" />
					</span>
					<span class="postCategory">
						<c:out value="${ board.category_nm }" />&nbsp;|&nbsp;
					</span>
					<div class="clear"></div>
					<c:out value="${ ct:subStringBytes(ct:removeTag(board.content), 280) }" />
				</div>
				
			</c:forEach>
			
			<c:choose>
				<c:when test="${ empty vo.searchCondition }">
					<!-- 더보기 -->
					<div id="nav-below" class="navigation">
						<div class="right"><a href="<c:url value="/main/search.do" />?searchKeyword=<c:out value="${ vo.searchKeyword }" />&searchCondition=<c:out value="${ data.searchCondition }" />">검색결과 더보기 <img src="<c:url value="/images/search/bullet_more.gif" />" border="0" align="absmiddle" alt="검색결과 더보기" /></a></div>
						<div class="clear"></div>
					</div>
				</c:when>
				<c:otherwise>
					<!-- 페이징 -->
					<form:form name="frm" commandName="vo" method="post" onsubmit="">
						<form:hidden path="searchCondition" />
						<form:hidden path="searchKeyword" />
						
						<div id="paging" style="text-align: center; margin-bottom: 10px;">
							<ui:pagination paginationInfo="${ paginationInfo }" type="text" jsFunction="linkToPage" />
							<form:hidden path="pageIndex" />
						</div>
					</form:form>
				</c:otherwise>
			</c:choose>
			
		</c:forEach>

	</div>
	
	<div id="sectionSidebar">
		<div class="interestTag" id="interestTag">
			<div class="interestTag_title">
				<img src="<c:url value="/images/search/interest_tag.jpg" />" border="0" alt="인기태그" />
			</div>
			<div class="ulDiv">
			</div>
		</div>
	</div>
	
</div>

<%@ include file="/WEB-INF/jsp/egovframework/include/footer.jsp"%>