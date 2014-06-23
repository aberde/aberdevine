<!--
/** 
 * <%= request.getRequestURI() %> 파일입니다. 
 */
--> 

<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>

<html>
<head>
<title></title>

<logic:greaterThan name="result" scope="request" value="0">
<script type="text/javascript">
<!--
alert("성공적으로 입력되었습니다.");
document.location.href = "/switch.do?prefix=/faq&page=/Faq.do?method=faqList&searchVO.menu_sn=01";
//-->
</script>

</logic:greaterThan>
<logic:lessEqual name="result" scope="request" value="0">
<script type="text/javascript">
alert('입력실패하였습니다.\n\n다시 시도해 주세요.');
history.go(-1);
//-->
</script>
</logic:lessEqual>

</head>
<body>
</body>
</html>