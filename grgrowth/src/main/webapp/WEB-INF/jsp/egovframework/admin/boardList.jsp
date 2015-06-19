<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="content-language" content="ko">
<title>게시판조회목록 </title>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/egov.css'/>">
<script type="text/javaScript" language="javascript">
	/**
	 * 게시판 목록으로 이동.
	 */
	function fnList() {
		document.frm.pageIndex.value='1';
		document.frm.action = "<c:url value='/admin/boardList.do'/>";
		document.frm.submit();
	}
	
	/**
	 * 게시판 상세정보로 이동.
	 */
	function fnView(board_seq) {
		document.frm.board_seq.value = board_seq;
		document.frm.action = "<c:url value='/admin/boardView.do'/>";
		document.frm.submit();
	}
	
	/**
	 * 게시판 등록화면으로 이동.
	 */
	function fnForm() {
		document.frm.action = "<c:url value='/admin/boardForm.do'/>";
		document.frm.board_seq.value = "0";  // 게시판등록으로 이동시 게시판 일련번호 초기화
		document.frm.submit();
	}
	
	/**
	 * 페이징
	 */
	function linkToPage(pageNo) {
		document.frm.board_seq.value = "0";  // 게시판등록으로 이동시 게시판 일련번호 초기화
		document.frm.pageIndex.value = pageNo;
		document.frm.action = "<c:url value='/admin/boardList.do'/>";
		document.frm.submit();
	}
</script>
</head>

<body style="margin:0; padding:0;text-align: center; margin: 0 auto; display: inline; padding-top: 100px;">
	<div id="content_pop">
		<form:form name="frm" commandName="vo" method="post" onsubmit="fnList()">
			<input type="hidden" name="board_seq" />
			
			<div id="home">
				<span class="btn_blue_l" title="홈으로">
					<a href="<c:url value="/" />">홈으로</a>
					<img src="<c:url value='/images/egovframework/rte/btn_bg_r.gif'/>" alt="홈으로" style="margin-left:6px;" />
				</span>
			</div>
			<div id="title2">
				<ul>
					<li>
						<img src="<c:url value='/images/egovframework/rte/title_dot.gif'/>" alt="" /> 게시판 목록
						<span style="font-size: 13px; padding-left: 5px;">(<c:out value='${ paginationInfo.totalRecordCount }' />건)</span>
					</li>
				</ul>
			</div>
			<div id="search">
				<ul>
					<li>
						<form:select path="searchCondition" cssClass="use" title="search">
							<form:option value="1" label="부서번호">부서번호</form:option>
							<form:option value="2" label="부서명">부서명</form:option>
						</form:select>
					</li> 
					<li><form:input path="searchKeyword" cssClass="txt" title="검색어"/></li>
					<!-- 검색 버튼 -->
					<li>
						<span class="btn_blue_l"	title="검색">
							<a href="javascript:fnList();">검색</a>
							<img src="<c:url value='/images/egovframework/rte/btn_bg_r.gif'/>" style="margin-left: 6px;" alt="검색" />
						</span>
					</li>
				</ul>
			</div>
			<div id="table">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" summary="게시판 목록을 조회할 수 있다.">
					<caption>게시판목록조회</caption>
					<colgroup>
						<col width="40">
						<col width="120">
						<col width="100">
						<col />
						<col width="100">
					</colgroup>
					<thead>
						<tr align="center">
							<th>번호</th>
							<th>게시판 일련번호</th>
							<th>카테고리명</th>
							<th>제목</th>
							<th>등록일</th>
						</tr>
					</thead>
					<tbody align="center">
						<c:if test="${ fn:length(boardList) == 0 }">
							<tr>
								<td class="listtd" colspan="5">해당 데이터가 없습니다.</td>
							</tr>
	    				</c:if>
						<c:forEach var="board" items="${ boardList }" varStatus="status">
							<tr>
								<td class="listtd"><c:out value="${ (paginationInfo.currentPageNo - 1) * paginationInfo.recordCountPerPage + status.count }" /></td>
								<td class="listtd"><c:out value="${ board.board_seq }" /></td>
								<td class="listtd"><c:out value="${ board.category_nm }" /></td>
								<td class="listtd"><a href="javascript:fnView('${ board.board_seq }');"><c:out value="${ board.title }" /></a></td>
								<td class="listtd"><c:out value="${ board.regdate }" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<div id="paging" style="margin-top: 10px;">
					<ui:pagination paginationInfo="${ paginationInfo }" type="text" jsFunction="linkToPage" />
					<form:hidden path="pageIndex" />
				</div>
				
			</div>
		</form:form>
		
		<div id="sysbtn">
			<ul>
				<!-- 등록 버튼 -->
				<li>
					<span class="btn_blue_l" title="등록">
						<a href="#link" onclick="fnForm(); return false;">등록</a>
						<img src="<c:url value='/images/egovframework/rte/btn_bg_r.gif'/>" style="margin-left: 8px;" alt="등록" />
					</span>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>