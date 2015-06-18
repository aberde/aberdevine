<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" session="false"%>

<%@ include file="/WEB-INF/jsp/egovframework/include/header.jsp" %>

<div id="container">

	<div class="page type-page status-publish hentry entry">
		<h1 class="entry_title">
			<c:out value="${ categoryView.category_nm }" />
		</h1>
	</div>
	
	<form:form name="frm" commandName="vo" method="post" onsubmit="">
		<c:forEach var="board" items="${ boardList }">
			<c:set var="contentList" value="${ ct:getImageTag(board.content, 'N') }" />
			
			<c:choose>
				<c:when test="${ fn:length(contentList) > 0 }">  <%-- 내용에 이미지 존재시 --%>
					
					<div id="post-<c:out value="${ board.board_seq }" />" class="post type-post status-publish format-standard hentry category-archive-2-2 entry">
						<div class="targetImage">
							<a href="<c:url value="/menu002/sub001/GRG_002_101V.do?board_seq=${ board.board_seq }" />" rel="bookmark" title="<c:out value="Permalink to ${ board.title }" />" >
								<img src="<c:out value="${ contentList[0] }" escapeXml="false" />" style="height: 100px; width: 178px;" />  <%-- 이미지 한장만 출력 --%>
							</a>
						</div>
						
						<div class="targetContent">
							<h2 class="targetTitleTwo">
								<a href="<c:url value="/menu002/sub001/GRG_002_101V.do?board_seq=${ board.board_seq }" />" rel="bookmark" title="<c:out value="Permalink to ${ board.title }" />" >
									<c:out value="${ board.title }" />
								</a>
							</h2>
							<div class="clear"></div>
							<a href="<c:url value="/menu002/sub001/GRG_002_101V.do?board_seq=${ board.board_seq }" />" rel="bookmark" title="<c:out value="Permalink to ${ board.title }" />" >
								<span class="txtGray"><c:out value="${ ct:subStringBytes(ct:removeTag(board.content), 300) }…" escapeXml="false" /></span>
							</a>
							<div class="clear"></div>
							<span class="targetDate">
								<a href="<c:url value="/menu002/sub001/GRG_002_101L.do?category_seq=${ board.category_seq }" />" rel="bookmark" title="<c:out value="${ board.category_nm }의 글 모두 보기" />" ><c:out value="${ board.category_nm }" /></a>&nbsp;&nbsp;&nbsp;
								<a href="<c:url value="/menu002/sub001/GRG_002_101V.do?board_seq=${ board.board_seq }" />" rel="bookmark" title="<c:out value="Permalink to ${ board.title }" />" ></a>
							</span>
							<span class="targetCategory">
								<c:out value="${ board.regdate }" />&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
							</span>
						</div>
						<div class="clear"></div>
					</div>
					
				</c:when>
				<c:otherwise>  <%-- 내용에 이미지 미존재시 --%>
					
					<div id="post-<c:out value="${ board.board_seq }" />" class="post type-post status-publish format-standard hentry category-archive-2-2 entry">
						<h2 class="targetTitle">
							<a href="<c:url value="/menu002/sub001/GRG_002_101V.do?board_seq=${ board.board_seq }" />" rel="bookmark" title="<c:out value="${ board.title }" />" ><c:out value="${ board.title }" /></a>
						</h2>
						<div class="clear"></div>
						<a href="<c:url value="/menu002/sub001/GRG_002_101V.do?board_seq=${ board.board_seq }" />" rel="bookmark" title="<c:out value="${ board.title }" />" >
							<span class="txtGray"><c:out value="${ ct:subStringBytes(ct:removeTag(board.content), 300) }…" escapeXml="false" /></span>
						</a>
						<div class="clear"></div>
						<span class="targetDate">
							<a href="<c:url value="/menu002/sub001/GRG_002_101L.do?category_seq=${ board.category_seq }" />" rel="bookmark" title="<c:out value="${ board.category_nm }의 글 모두 보기" />" ><c:out value="${ board.category_nm }" /></a>&nbsp;&nbsp;&nbsp;
							<a href="<c:url value="/menu002/sub001/GRG_002_101V.do?board_seq=${ board.board_seq }" />" rel="bookmark" title="<c:out value="${ board.title }" />" ></a>
						</span>
						<span class="targetCategory">
							<c:out value="${ board.regdate }" />&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
						</span>
					</div>
					
				</c:otherwise>
			</c:choose>
			
		</c:forEach>
 
	 	<div id="paging" style="text-align: center; margin-bottom: 10px;">
			<ui:pagination paginationInfo="${ paginationInfo }" type="text" jsFunction="linkToPage" />
			<form:hidden path="pageIndex" />
		</div>
		
	</form:form>
	
</div>

<%@ include file="/WEB-INF/jsp/egovframework/include/sidebar.jsp" %>

<div class="clear"></div>

<%@ include file="/WEB-INF/jsp/egovframework/include/footer.jsp" %>