<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>

<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@ taglib uri="/tags/taglibs-log" prefix="log"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %> 
<bean:define name="FaqForm" property="errCd" id="errCd" type="java.lang.String"/>
<link href="/css/basic.css" rel="stylesheet" type="text/css" />
<link href="/css/Content.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="/js/validate.js"></script>
<script language="JavaScript" src="/js/common.js"></script> 
<bean:define id="path" type="java.lang.String" value="/Faq.do"/>
<style type="text/css">
<!--
@import url("../css/Base.css");
@import url("../css/Layout.css");
@import url("../css/Header.css");
@import url("../css/Content.css");
@import url("../css/Footer.css");
@import url("../css/Button.css");

-->
</style>
<script language="JavaScript">
	window.onload=function(){
		if("<%=errCd%>" == "true"){
			alert("발신함.");			
			//refreshOpener();
//			self.close();
		}else if("<%=errCd%>" == "false"){
			alert("실패함.");			
			//refreshOpener();
//			self.close();
			
		}
	}
	function commit(){
		fm.elements["method"].value="smsCommit";
		fm.target="FaqForm";
		fm.submit();
//		self.close();
	}

	function orgTelNum() {
	 	var url = "/switch.do?prefix=&page=/Faq.do?method=orgTelNum";
	 	window.open(url, "orgTelNum", "width=640, height=490,  resizable=no, toolbar=true, scrollbars=yes, status=yes");
	}
	
	function setPut(tel,email){
		fm.elements["vo.cellNum"].value=tel;
		fm.elements["vo.cellEmail"].value=email;
	}

</script>
<div class="LY-PopLayout" style="width:410px;">
		<!-- start # 타이틀 -->
		<ul class="Header">
				<li class="Title">SMS 발송하기</li>
				<li class="Img"></li>
		</ul>
		<html:form action="/Faq" method="post" name="fm" type="kr.go.rndcall.mgnt.faq.form.FaqForm" onsubmit="return checkOnSubmit(this)">
		<html:hidden name="FaqForm" property="method" value="smsCommit"/>
		<!-- start # 컨텐츠 -->
		<ul class="Content">
			<li>
			<table border="0" cellspacing="0" cellpadding="0" class="SMS-Send">
  				<tr>
   					<td>
						<div class="SMS-SendInput">
							<ul>
								<li class="Title"><img src="../images/icon/icon_SMS.gif" alt="SMS" /> 휴대폰 번호</li>&nbsp;&nbsp;[<a href="javascript:orgTelNum();"><font color="blue">연락처 선택</font></a>]
								<li class="Num"><textarea name="vo.cellNum"  style="height:90px;"></textarea></li>
								<li class="Title"><img src="../images/icon/icon_advice.gif" alt="내용" /> SMS내용(<input type="text" size="4" name="basicFormLength1" value="0"/>byte)</li>
								<li class="Num"><html:textarea name="FaqForm" property="vo.smsCont"  value="『유권해석이 등록되었습니다. R&D도우미센터에 접속하여 확인 하십시오.』" style="height:90px;" title="sms내용" onkeyup="countByte(this, basicFormLength1, 80)" onchange="trim(this)"/></li>										
								<li class="Title"><img src="../images/icon/icon_Member01.gif" alt="SMS" /> 이메일</li>
								<li class="Num"><textarea name="vo.cellEmail"  style="height:90px;"></textarea></li>
							</ul>
						</div>
						<div class="Bottom"><ul><li></li></ul></div>
					</td>
  				</tr>
			</table>
			</li>
		</ul>
		<!-- start # 버튼 -->
		<ul class="Button">
			<li><a href="javascript:commit();" class="btn_BSMS"><strong>발송</strong></a> <a href="javascript:self.close()" class="btn_BClose"><strong>닫기</strong></a></li>
		</ul>
	</html:form>
</div>
<!-- end .container -->
<script>
	fm.elements["basicFormLength1"].value = getValByte(fm.elements["vo.smsCont"].value);
</script>