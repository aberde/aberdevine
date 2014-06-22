<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
### 경로   : <%= request.getRequestURI() %>
### 사업명 : 11년도 개방형 연구성과분석지원서비스
### 사업일 : 2011-07-10 ~ 2011-08-31
### 개발일 : 2011-07-14
### 개발자 : 박인선
-->
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/include/top_pop.jsp"%>

<title>R&amp;D도우미센터 - 비밀번호찾기</title>

<body>

<div class="Pop-Warning">
	<logic:empty name="memberForm" property="voList">
		<ul>
			<li class="Text"> 
				입력하신 정보가 일치하지 않습니다.<br />
				다시 확인 후 입력하여 주시기 바랍니다.
			</li>
			<li class="Btn"><a href="javascript:window.close();" class="btn_Basic" ><strong>확인</strong></a></li>
		</ul>
	</logic:empty>
	<logic:notEmpty name="memberForm" property="voList">
		<ul>
			<li class="Text">
				가입하신 아이디는 아래와 같습니다.<br />
				<logic:iterate name="memberForm" property="voList" id="vo">
				<span class="TextInfor"><bean:write name="vo" property="login_id"/></span><br />
				</logic:iterate>
			</li>
			<li class="Btn"><a href="javascript:window.close();" class="btn_Basic" ><strong>확인</strong></a></li>
		</ul>
	</logic:notEmpty>
</div>

</body>
</html>
