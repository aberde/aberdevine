<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<head>
<title>로그인</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/include/header.jsp" />

<script type="text/javascript">

function login(e) {

	if ($('#usrId').val() == "") {
		alert("아이디를 입력해주십시오");
		$('#usrId').focus();
		return;
	}
	
	if ($('#pwd').val() == "") {
		alert("비밀번호를 입력해주십시오");
		$('#pwd').focus();
		return;
	}

	var url = 'content/loginChk.do';
	var query = '${param.redirect != null ? "?redirect=" : ""}' + '${param.redirect}';

	$.post(   url + query
		    , { usrId:	$('#usrId').val()
		       ,pwd:	$('#pwd').val()
	          }
	        , function(data, status, xhr) {
		
		      		//login 후 처리		
					if (data.status==='SUCCESS') {
						if (data.redirect) {
							window.location.href = data.redirect;
						} else {
							window.location.href = 'content/main.do';
						}
					} else {
						alert(data.message);
					}
			   }
	        , 'json'
	 ).error(function(x,s,t) {httpError(x, s, t);});
}

$(document).ready(function() {
	$('#usrId').focus();
	$('#btnLogin').click(login);
});
</script>
</head>

<body background="<c:url value='/images/login/login_bg.jpg'/>">
<table width="100%" height="100%" border="0">
	<tr>
		<td>
			<form:form method="post" name="loginForm" action="main.html">
			<table width="680px" border="0" align="center" cellpadding="0" cellspacing="0" class="mar_t20b30">
				<tr>
					<td style="padding:40px 0 40px 0; text-align:center" background="<c:url value='/images/login/login_box.png'/>" height="305px">
						<table width="530" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
							<td align="right"><img src="<c:url value='/images/login/login_text1.gif'/>">
						    <input type="text" name="usrId" id="usrId" style="width:160px" tabindex="1" /></td>
							<td width="84" rowspan="2" align="center"><a id="btnLogin" href="#"><img src="<c:url value='/images/login/login_btn.gif'/>" border="0" tabindex="3" ></a></td>
							</tr>
							<tr>
							<td align="right"><img src="<c:url value='/images/login/login_text2.gif'/>">
						    <input type="password" name="pwd " id="pwd" style="width:160px" tabindex="2" /></td>
						    </tr>
							<tr>
							<td colspan="2" align="right" height="15px">&nbsp;</td>
						    </tr>
						</table>
				  </td>
				</tr>
			</table>
            </form:form>
		</td>
	</tr>
</table>

</body>
</html>
