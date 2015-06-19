<%@ page language="java" pageEncoding="utf-8" 	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="content-language" content="ko">
<c:set var="flag" value="${ vo.board_seq > 0 ? '수정' : '등록' }"/>
<title>게시판 정보 <c:out value="${ flag }"/></title>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/egov.css'/>">
<script type="text/javascript" src="<c:url value="/js/jquery-1.11.3.js" />"></script>
<script type="text/javaScript">
	_editor_url = "<c:url value='/html/egovframework/com/cmm/utl/htmlarea3.0/'/>";
	_editor_area = "content";
	
	$(document).ready(function() {
		HTMLArea.init();
		HTMLArea.onload = initEditor;
	});
	
	/**
	 * 게시판 저장
	 */
	function fnSave() {
		frm = document.frm;
		frm.onsubmit();

		if(frm.category_seq.value == ""){
			alert("카테고리를 입력하세요.");
			return;
		}
		if(frm.title.value == ""){
			alert("제목을 입력하세요.");
			return;
		}
		if(frm.content.value == ""){
			alert("내용을 입력하세요.");
			return;
		}
		
   		if ( confirm("저장하시겠습니까?") ) {
   			frm.action = "<c:url value='/admin/boardSaveProc.do'/>";
   			frm.submit();
   		}
	}
	
	/**
	 * 게시판 목록으로 이동.
	 */
	function fnList() {
		document.frm.category_seq.value = "0";  // 게시판목록으로 이동시 카테고리 초기화
		document.frm.action = "<c:url value='/admin/boardList.do'/>";
		document.frm.submit();
	}
</script>
<script type="text/javascript" src="<c:url value='/html/egovframework/com/cmm/utl/htmlarea3.0/htmlarea.js'/>"></script>
</head>

<body style="text-align: center; margin: 0 auto; display: inline; padding-top: 100px;">
	<div id="content_pop">
		<form:form name="frm" commandName="vo" method="post">
			 <div id="home">
				<span class="btn_blue_l" title="홈으로">
					<a href="<c:url value="/" />">홈으로</a>
					<img src="<c:url value='/images/egovframework/rte/btn_bg_r.gif'/>" alt="홈으로" style="margin-left:6px;" />
				</span>
			</div>
			<div id="title2">
				<ul>
					<li><img src="<c:url value='/images/egovframework/rte/title_dot.gif'/>" alt="" /> 게시판 정보 <c:out value="${ flag }"/></li>
				</ul>
			</div>
			<div id="table">
				<table width="100%" border="1" cellpadding="0" cellspacing="0" style="border-top:#C2D0DB 2px solid; border-left:#ffffff 1px solid; border-right:#ffffff 1px solid; border-bottom:#C2D0DB 1px solid; border-collapse: collapse;" summary="게시판정보를 등록하고 수정할 수 있다.">
					<caption>게시판정보 등록 및 수정</caption>
					<colgroup>
						<col width="120">
						<col width="680"/>
					</colgroup>
					
					<c:if test="${ flag == '수정'}">
					<tr>
						<td class="tbtd_caption">게시판 일련번호</td>
						<td class="tbtd_content"><form:hidden path="board_seq" /><c:out value="${ vo.board_seq }" /></td>
					</tr>
					</c:if>
					
					<tr>
						<td class="tbtd_caption">카테고리</td>
						<td class="tbtd_content">
							<form:select path="category_seq" cssClass="use">
								<form:option value="">선택하세요</form:option>
								<form:options items="${ categoryList }" itemLabel="category_nm" itemValue="category_seq" />
							</form:select>
						</td>
					</tr>
					<tr>
						<td class="tbtd_caption">제목</td>
						<td class="tbtd_content"><form:input path="title" cssStyle="width: 100%;" /></td>
					</tr>
					<tr>
						<td class="tbtd_caption">내용</td>
						<td class="tbtd_content"><form:textarea path="content" cssStyle="height: 200px;" /></td>
					</tr>
				</table>
			</div>
			<div id="sysbtn">
				<ul>
					<!-- 저장 버튼 -->
					<li>
						<span class="btn_blue_l" title="저장">
							<a href="#link" onClick="fnSave(); return false;">저장</a>
							<img src="<c:url value='/images/egovframework/rte/btn_bg_r.gif'/>" alt="저장" style="margin-left:6px;" />
						</span>
					</li>
					<!-- 목록 버튼 -->
					<li>
						<span class="btn_blue_l" title="목록">
							<a href="#link" onClick="fnList(); return false;">목록</a>
							<img src="<c:url value='/images/egovframework/rte/btn_bg_r.gif'/>" alt="목록" style="margin-left:6px;" />
						</span>
					</li>
					<!-- 초기화 버튼 -->
					<li>
						<span class="btn_blue_l" title="초기화">
							<a href="#link" onClick="document.frm.reset(); return false;">초기화</a>
							<img src="<c:url value='/images/egovframework/rte/btn_bg_r.gif'/>" alt="초기화" style="margin-left:6px;" />
						</span>
					</li>
				</ul>
			</div>
			
			<!-- 검색조건 유지 -->
			<form:hidden path="searchCondition" />
			<form:hidden path="searchKeyword" />
			<form:hidden path="pageIndex" />
		</form:form>
	</div>
</body>
</html>