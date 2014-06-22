<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
### 경로   : <%= request.getRequestURI() %>
### 사업명 : 11년도 개방형 연구성과분석지원서비스
### 사업일 : 2011-07-10 ~ 2011-08-31
### 개발일 : 2011-07-14
### 개발자 : 박인선
-->
<%@ page contentType="text/html; charset=utf-8"%>

<script>
opener.location.reload();
<!--
function login(idx) {
	var winfeatures = "toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=10,height=10";
	var url = "";
	switch (idx) {
		case 1 : url="/switch.do?prefix=&page=/login.do?method=login&vo.login_id=nstc01&vo.password=nstc1011!";
				 break;
		case 2 : url="/switch.do?prefix=&page=/login.do?method=login&vo.login_id=nstc02&vo.password=nstc1011!";
				 break;
		case 3 : url="/switch.do?prefix=&page=/login.do?method=login&vo.login_id=nstc03&vo.password=nstc1011!";
				 break;
		case 4 : url="/switch.do?prefix=&page=/login.do?method=login&vo.login_id=admin&vo.password=nstc1011!";
				 break;
	}
	
	adwin = window.open(url, "log", winfeatures);
}

//-->
</script>
<body>
<input type="button" name="btn1" value="일반사용자" onclick="login(1)"/>
<br />
<input type="button" name="btn1" value="부처담당자" onclick="login(2)"/>
<br />
<input type="button" name="btn1" value="운영자" onclick="login(3)"/>
<br />
<!-- <input type="button" name="btn1" value="시스템관리자" onclick="login(4)"/> -->

</body>
</html>
