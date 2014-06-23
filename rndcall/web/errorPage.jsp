<%
	response.setStatus(200);
%>	
<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>접속장애나 서버점검으로 인한 ERROR 페이지 </title>
</head>

<body onLoad>
<div align="center"><img src="/images/content/Img_SystemError.jpg" border="0" usemap="#Map">

<map name="Map" id="Map">
<area shape="rect" coords="131,550,332,586" href="javascript:history.back(-1);" alt="이전화면으로 가기" onFocus="this.blur()">
<area shape="rect" coords="369,550,570,586" href="/index.jsp" alt="초기화면으로 가기" onFocus="this.blur()">
</map>

</div>
</body>
</html>
