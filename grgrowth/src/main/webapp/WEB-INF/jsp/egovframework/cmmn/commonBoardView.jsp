<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" session="false"%>

<%@ include file="/WEB-INF/jsp/egovframework/include/header.jsp" %>

<script type="text/javascript">
	$(document).ready(function() {
		GisaFont = new SetFontSize();
		GisaFont.name = "GisaFont";
	});
	
	/**
	 * 트위터 링크
	 */
	function winTwitter() {
		var status = encodeURIComponent($("#subject_con2").val() + "   " + $("#board_url2").val());
		var url = "http://twitter.com/home/?status=" + status;
		window.open(url, "twitter", "");
	}
	
	/**
	 * 페이스북 링크
	 */
	function winFacebook() {
		var url = "http://www.facebook.com/sharer.php?u=" + encodeURIComponent($("#board_url2").val()) + "&t=" + encodeURIComponent($("#subject_con2").val());
		window.open(url, "facebook", "");
	}
	
	/**
	 * 출력
	 * @param board_seq 게시판 일련번호
	 */
	function printPost(board_seq) {
		$("#post-" + board_seq).print();
	}
	
	/**
	 * 첨부파일 열기
	 */
	function fileopen() {
		var obj = document.getElementById("attachfile");
		obj.style.display = "block";
	}

	/**
	 * 첨부파일 닫기
	 */
	function fileclose() {
		var obj = document.getElementById("attachfile");
		obj.style.display = "none";
	}
</script>

<div id="container">

	<form:form name="frm" commandName="vo" method="post" onsubmit="">
		<form:hidden path="category_seq" />
		<form:hidden path="board_seq" />
<%-- 		<input type="hidden" id="subject_con2" name="subject_con2" value="<c:out value="${ commonBoardVO.title }" />" /> --%>
<%-- 		<input type="hidden" id="board_url2" name="board_url2" value="<c:url value="${ pageContext.request.scheme }://${ pageContext.request.serverName }:${ pageContext.request.serverPort }${ pageContext.request.contextPath }/commonBoardView.do?${ pageContext.request.queryString }" />" /> --%>
	
		<div id="post-<c:out value="${ commonBoardVO.board_seq }" />" class="post type-post status-publish format-standard hentry category-archive-2-2 entry">
			<h1 class="entry_title"><c:out value="${ commonBoardVO.title }" /></h1>
			<div class="metasingle">
				<div class="metaLeft">
					<span class="postDate"><c:out value="${ commonBoardVO.regdate }" /></span>
					<span class="postCategory">&nbsp;&nbsp;|&nbsp;&nbsp;<c:out value="${ commonBoardVO.category_nm }" />&nbsp;&nbsp;|&nbsp;&nbsp;</span>
				</div>
				<div class="metaRight printHide">
<%-- 					&nbsp;&nbsp;<a href="javascript:winTwitter();"><img src="<c:url value="/images/icon_twitter.gif" />" border="0" alt="이 글을 twitter로 보내기"></a> --%>
<%-- 					&nbsp;&nbsp;<a href="javascript:winFacebook();"><img src="<c:url value="/images/icon_facebook.gif" />" border="0" alt="이 글을 facebook으로 보내기"></a> --%>
					&nbsp;&nbsp;<a href="javascript:printPost('<c:out value="${ commonBoardVO.board_seq }" />');"><img src="<c:url value="/images/icon_print.gif" />" alt="이 글을 인쇄하기" border="0"></a>
					&nbsp;<a href="javascript:GisaFont.scaleFont(1);"><img src="<c:url value="/images/icon_plus.gif" />" border="0" alt="글자확대" title="글자확대" align="absmiddle" style="margin-bottom:3px;" /></a>
					&nbsp;<a href="javascript:GisaFont.scaleFont(-1);"><img src="<c:url value="/images/icon_minus.gif" />" border="0" alt="글자축소" title="글자축소" align="absmiddle" style="margin-bottom:3px;" /></a>
				</div>
			</div>

			<div class="metafile" style="text-align:right; position:relative;">
				<c:if test="${ fn:length(commonBoardVO.fileInfoList) > 0 }">
					<div id="attachfile" style="display:none; text-align:left; position:absolute; top:10px; right:5px; border:1px solid #d9d9d9; padding:5px 10px; background:#fff;">
						<div style="float:left; line-heigt:100%;">
							
							<c:forEach var="fileInfo" items="${ commonBoardVO.fileInfoList }">
								<a href="<c:url value="/cmmn/downloadFile.do?file_seq=${ fileInfo.file_seq }&file_sn=${ fileInfo.file_sn }" />"><img src="<c:url value="/images/disk.png" />" height="14" border="0" alt="첨부파일" align="absmiddle"> <c:out value="${ fileInfo.orignl_file_nm }" /></a><br />
							</c:forEach>
						
						</div>
						<div style="float:right; margin-left:5px; margin-top:3px;">
							<a href="javascript:fileclose();" title="닫기"><span style="color:#666; font-weight:bold; font-size:12px;">×</span></a>
						</div>
					</div>
					<span style="font-weight:bold;"><a href="javascript:fileopen();">첨부파일 (<c:out value="${ fn:length(commonBoardVO.fileInfoList) }" />) <img src="<c:url value="/images/btn_download.gif" />" border="0" alt="첨부파일 다운로드" align="absmiddle"></a></span>
				</c:if>
			</div>
			
			<div id="content_container">
				<c:out value="${ ct:rowInsertPTag(commonBoardVO.content) }" escapeXml="false" />
			</div>
			
			<div class="clear"></div>
			
			<div class="fileAdd">
				<c:if test="${ fn:length(commonBoardVO.fileInfoList) > 0 }">
					<c:forEach var="fileInfo" items="${ commonBoardVO.fileInfoList }">
						<a href="<c:url value="/cmmn/downloadFile.do?file_seq=${ fileInfo.file_seq }&file_sn=${ fileInfo.file_sn }" />"><img src="<c:url value="/images/disk.png" />" height="14" border="0" alt="첨부파일" align="absmiddle"> <c:out value="${ fileInfo.orignl_file_nm }" /></a><br />
					</c:forEach>
				</c:if>
			</div>
			
			<div class="metaAdd">
				<div class="metaAddLevel2" style="float:right;">
					<a href="javascript:scroll(0,0);"><img src="<c:url value="/images/btn_top.gif" />" alt="위로가기" title="위로가기" align="absmiddle" border="0"></a>
				</div>
			</div>

			<div class="metaAddLevel2" style="float:right;">
				<a href="<c:url value="/cmmn/commonBoardList.do?category_seq=${ commonBoardVO.category_seq }&pageIndex=${ param.pageIndex }" />"><img src="<c:url value="/images/bk_list.jpg" />" alt="목록가기" title="목록가기" align="absmiddle" border="0"></a>
			</div>
		</div>
					
	</form:form>
	
</div>

<%@ include file="/WEB-INF/jsp/egovframework/include/sidebar.jsp" %>

<div class="clear"></div>

<%@ include file="/WEB-INF/jsp/egovframework/include/footer.jsp" %>