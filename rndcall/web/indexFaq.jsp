<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@ taglib uri="/tags/taglibs-log" prefix="log"%>
<html>
<title>테스트</title>
<script>
activeDebug = true;  
module = '/switch.do?prefix=/faq&page=';

function gofaq(){
	fm.elements["method"].value="faqList";
	fm.submit();
}
</script>
<body>
<html:form action="/Faq" method="post" name="fm" type="kr.go.rndcall.mgnt.faq.form.FaqForm">
<html:hidden name="FaqForm" property="method" value="faqList"/>
<a href="JavaScript:gofaq()">자주하는질문</a><br/>
<a href="http://rndgatepc.ntis.go.kr:9998/switch.do?prefix=&page=/Offer.do?method=offerInsertForm">제안하기</a>
</html:form>
</body>
</html>