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
<title>관리자 로그인</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/layout.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/green.css" />">
<script type="text/javascript" src="<c:url value="/js/jquery-1.11.3.js" />"></script>
<script type="text/javaScript">
	/**
	 * 로그인 처리.
	 */
	function fnLoginProc() {
		if ( !$("#user_id").val() ) {
			alert("사용자 아이디를 입력해주세요.");
			$("#user_id").focus();
			return;
		}
		if ( !$("#user_pw").val() ) {
			alert("사용자 비밀번호를 입력해주세요.");
			$("#user_pw").focus();
			return;
		}
		
		document.frm.action = "<c:url value='/admin/adminLoginProc.do'/>";
		document.frm.submit();
	}
	
	$(document).ready(function() {
		var err_msg = "${ vo.err_msg }";
		
		if ( err_msg ) {
			alert(err_msg);
		}
	});
</script>
</head>

<body style="text-align: center; margin: 0 auto; display: inline; padding-top: 100px;">
	<div id="content_pop" style="width: 800px;">
		<form:form name="frm" commandName="vo" method="post">
			 <div id="home" style="text-align: right;">
				<span class="btn_blue_l" title="홈으로">
					<a href="<c:url value="/" />">홈으로</a>
					<a href="<c:url value="/admin/adminLogoutProc.do" />">로그아웃</a>
				</span>
			</div>
			<div id="table">
				<table border="1" cellpadding="0" cellspacing="0" style="width:500px; border-top:#C2D0DB 2px solid; border-left:#ffffff 1px solid; border-right:#ffffff 1px solid; border-bottom:#C2D0DB 1px solid; border-collapse: collapse;" summary="로그인">
					<caption>로그인</caption>
					<colgroup>
						<col width="200" />
						<col width="200" />
						<col width="100" />
					</colgroup>
					<tr>
						<td class="tbtd_caption">ID</td>
						<td class="tbtd_content"><form:input path="user_id" /></td>
						<td class="tbtd_content" rowspan="2">
							<span class="btn_blue_l" title="저장">
								<a href="#" onClick="fnLoginProc(); return false;">로그인</a>
							</span>
						</td>
					</tr>
					<tr>
						<td class="tbtd_caption">PW</td>
						<td class="tbtd_content"><form:input path="user_pw" /></td>
					</tr>
				</table>
			</div>
		</form:form>
	</div>
</body>
</html>