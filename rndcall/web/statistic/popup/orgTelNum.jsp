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
			self.close();
		}else if("<%=errCd%>" == "false"){
			alert("실패함.");			
			//refreshOpener();
			self.close();
			
		}
	}
	
	
	function put(){
		var tel="";
		var email = "";
		var val="";
		var arr1 = new Array(); 		
		var arr2 = new Array(); 
		
		if(fm2.chk.length == undefined){
			if(fm2.chk.checked){
				val= fm2.chk.value;
				arr2 = val.split(";");
				tel = arr2[0];
				email = arr2[1];
			}	
		} else {
			for(i=0; i < fm2.chk.length; i++){
				if(fm2.chk[i].checked){
					val += fm2.chk[i].value+",";
				}
			}
			arr1 = val.substring(0,(val.length-1)).split(",");
			//alert("arr1.length::"+arr1.length);
			for(j=0; j<arr1.length; j++){
				var value2 =  arr1[j];
				//alert("arr1[j]::"+arr1[j]);
				arr2 = value2.split(";");
				tel += arr2[0]+"\n";
				email += arr2[1]+"\n";
			}
		}
		
		//alert(value);
		
			
		//opener.setPut(tel, email);
		
		opener.document.fm.elements["vo.cellNum"].value=tel;
		opener.document.fm.elements["vo.cellEmail"].value=email;
		
		self.close();
	}
</script>
<div class="LY-PopLayout1" style="width:600px;">
		<!-- start # 타이틀 -->
		<ul class="Header1">
			<li class="Title1">부처담당자 리스트</li>
			<li class="Img1"></li>
		</ul>
		<html:form action="/Faq" method="post" name="fm2"  type="kr.go.rndcall.mgnt.faq.form.FaqForm" onsubmit="return checkOnSubmit(this)">
		<html:hidden name="FaqForm" property="method" value="smsCommit"/>
		<html:hidden name="FaqForm" property="vo.auth_id"/>
		<!-- start # 컨텐츠 -->
		<ul class="Content1">
			<li>
				<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
						<colgroup>
							<col width="30px" />
							<col width="180px" />
							<col width="100px" />
							<col width="160px" />
							<col width="160px" />
						</colgroup>
						<thead>
							<tr>
								<th class="Btmline">선택</th>
								<th class="Btmline">부처</th>
								<th class="Btmline">연락처</th>
								<th class="Btmline">이름</th>
								<th class="Btmline">이메일</th>
							</tr>
						</thead>						   						    
						<logic:empty name="FaqForm" property="voList">
							<tr>
								<td class="center" colspan="5" height="30">검색된 결과가 없습니다.</td>
							</tr>
						</logic:empty>
						<logic:notEmpty name="FaqForm" property="voList">
							<logic:iterate name="FaqForm" property="voList" id="vo" indexId="rowNum">
								<tr>
									<td class="Btmline">
										<input type="checkbox" name="chk" id="<bean:write name='vo' property='auth_id'/>"  value="<bean:write name='vo' property='tel'/>;<bean:write name='vo' property='email'/>"/>
									</td>	
									<td><bean:write name="vo" property="org_nm"/></td>	
									<td><bean:write name="vo" property="tel"/></td>	
									<td><bean:write name="vo" property="name"/></td>	
									<td><bean:write name="vo" property="email"/></td>	
								</tr>
							</logic:iterate>
						</logic:notEmpty>   
	    		</table>
			</li>
		</ul>
		<!-- start # 버튼 -->
		<ul class="Button1">
			<li>&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;<a href="javascript:put();" class="btn_BSMS"><strong>선택</strong></a> <a href="javascript:self.close()" class="btn_BClose"><strong>닫기</strong></a></li>
		</ul>
	</html:form>
	</td>
</div>
<!-- end .container -->
</div>
