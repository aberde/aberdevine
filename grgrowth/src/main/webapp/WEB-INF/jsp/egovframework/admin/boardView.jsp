<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/customTag.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="content-language" content="ko">
<title>게시판상세조회</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/layout.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/green.css" />">
<script type="text/javaScript">
	/**
	 * 수정화면으로 이동
	 */
	function fnForm() {
		document.frm.action = "<c:url value='/admin/boardForm.do'/>";
		document.frm.submit();
	}
	
	/**
	 * 목록화면으로 이동
	 */
	function fnList() {
		document.frm.action = "<c:url value='/admin/boardList.do'/>";
		document.frm.submit();
	}
	
	/**
	 * 삭제 처리
	 */
	function fnDelete() {
		if ( confirm("삭제하시겠습니까?") ) {
			document.frm.action = "<c:url value='/admin/boardDeleteProc.do'/>";
			document.frm.submit();
		}
	}
</script>
</head>

<body style="text-align: center; margin: 0 auto; display: inline; padding-top: 100px;">
	<div id="content_pop" style="width: 800px;">
		<form:form name="frm" commandName="vo" method="post">
			<form:hidden path="board_seq" />
			
			<div id="home" style="text-align: right;">
				<span class="btn_blue_l" title="홈으로">
					<a href="<c:url value="/" />">홈으로</a>
					<a href="<c:url value="/admin/adminLogoutProc.do" />">로그아웃</a>
				</span>
			</div>
			<div id="title2" style="text-align: left;">
				<ul>
					<li><img src="<c:url value='/images/egovframework/rte/title_dot.gif'/>" alt="" /> 게시판 상세정보</li>
				</ul>
			</div>
			<div id="table">
				<table width="100%" border="1" cellpadding="0" cellspacing="0" style="border-top:#C2D0DB 2px solid; border-left:#ffffff 1px solid; border-right:#ffffff 1px solid; border-bottom:#C2D0DB 1px solid; border-collapse: collapse;" summary="게시판정보를 확인할 수 있다.">
					<caption>게시판정보 조회</caption>
					<colgroup>
						<col width="120">
						<col width="680"/>
					</colgroup>
					<tr>
						<td class="tbtd_caption">게시판 일련번호</td>
						<td class="tbtd_content"><c:out value="${ commonBoardVO.board_seq }" /></td>
					</tr>
					<tr>
						<td class="tbtd_caption">카테고리</td>
						<td class="tbtd_content"><c:out value="${ commonBoardVO.category_nm }" /></td>
					</tr>
					<tr>
						<td class="tbtd_caption">등록일</td>
						<td class="tbtd_content"><c:out value="${ commonBoardVO.regdate }" /></td>
					</tr>
					<tr>
						<td class="tbtd_caption">제목</td>
						<td class="tbtd_content"><c:out value="${ commonBoardVO.title }" /></td>
					</tr>
					<tr>
						<td class="tbtd_caption">내용</td>
						<td class="tbtd_content"><c:out value="${ ct:rowInsertPTag(commonBoardVO.content) }" escapeXml="false" /></td>
					</tr>
					<tr>
						<td class="tbtd_caption">파일</td>
						<td class="tbtd_content">
							<c:forEach var="fileInfo" items="${ commonBoardVO.fileInfoList }">
								<a href="<c:url value="/cmmn/downloadFile.do?file_seq=${ fileInfo.file_seq }&file_sn=${ fileInfo.file_sn }" />"><c:out value="${ fileInfo.orignl_file_nm }" /></a><br />
							</c:forEach>
						</td>
					</tr>
				</table>
			</div>
			<div id="sysbtn" style="text-align: right;">
				<ul>
					<li>
						<!-- 수정 버튼 -->
						<span class="btn_blue_l" title="수정">
							<a href="#link" onClick="fnForm(); return false;">수정</a>
						</span>
						<!-- 목록 버튼 -->
						<span class="btn_blue_l" title="목록">
							<a href="#link" onClick="fnList(); return false;">목록</a>
						</span>
						<!--  삭제 버튼 -->
						<span class="btn_blue_l" title="삭제">
							<a href="#link" onClick="fnDelete(); return false;">삭제</a>
						</span>
					</li>
				</ul>
			</div>
			
			<!-- 검색조건 유지 -->
			<form:hidden path="category_seq" />
			<form:hidden path="searchCondition" />
			<form:hidden path="searchKeyword" />
			<form:hidden path="pageIndex" />
		</form:form>
	</div>
</body>
</html>