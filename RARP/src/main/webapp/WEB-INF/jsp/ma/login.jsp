<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>::3D PLM 기반 스마트 철도차량 유지보수 시스템::</title>
<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.3.custom.js"></script>
<script src="${pageContext.request.contextPath}/grid/js/i18n/grid.locale-kr.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/grid/js/jquery.jqGrid.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
<script type="text/javaScript" language="javascript">
<!--
$(function() { 
   $('#userId').focus();
});

function runScript(e) {
    if (e.keyCode == 13) {
    	fnLoginAction();
        return false;
    }
}
function fnLoginAction() {  
	if($('#userId').val() == "")
    {
	    alert("아이디를 입력해 주세요.");
	    $('#userId').focus();
	    return;
	}
	if($('#userPw').val() == "")
    {
	    alert("패스워드를 입력해 주세요.");
	    $('#userPw').focus();
	    return;
	}
	 jQuery.ajax({
           type:"POST",
           url:"${pageContext.request.contextPath}/ma/loginAction.do",
           data: $("#LoginForm").serialize() ,
           dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
           success : function(data) {
             // 통신이 성공적으로 이루어졌을 때 이 함수를 타게 된다.
			$.each(data, function(key, value) {
			   if(value =="200")
				   location.href = "${pageContext.request.contextPath}/ma/main.do";
			   else
			   {
				   $('#userId').val("");
				   $('#userPw').val("");
				   $('#userId').focus();
				   alert("로그인 정보가 일치하지 않습니다.")
				}
				  
					  
			})
           },
           complete : function(data) {
                 // 통신이 실패했어도 완료가 되었을 때 이 함수를 타게 된다.

                 // TODO
           },
           error : function(xhr, status, error) {
        	   alert("로그인 인증시 애러가 발생 하였습니다.")
           }

     });
}
function OnEnter( field ) { if( field.value == field.defaultValue ) { field.value = ""; } } 
function OnExit( field ) { if( field.value == "" ) { field.value = field.defaultValue; } } 
//-->
</script>
    <style type="text/css">

    </style>
</head>
<body> 
<form id="LoginForm"  method="post">
<div class="login01">
  <div class="login02">
    <div class="clear_both header00">
      <div class="float_left header06">
        <h1 class="float_left">
          <p class="font01"> 3D PLM 기반<br />
            <strong class="font02"><b>스마트 철도차량 유지보수 지원 시스템</b></strong></p>
        </h1>
      </div>
    </div>
    <div>
    <dl class="clear_both login03">
    <dt class="font17">User Login</dt>
    <dd class="float_left login04 login05"><input type="text" id="userId" name="userId" value="" /> </dd>
    <dd class="float_left login04"><input type="password" id="userPw" name="userPw" value="" onkeypress="return runScript(event)"  style="none" /></dd>
     <a href="javascript:fnLoginAction()" id="btnLogin"><dd class="btn05 float_left">LOGIN</dd></a>
    </dl>
    </div>
    
  </div>
  <div class="clear_both login06"><p class="float_left"><img  src="${pageContext.request.contextPath}/images/logo_lg.png" /></p>
    <p class="font11 float_left login07"> Copyright(c)2014 LG히다찌 All rights reserved.</p>   
    </div>
</div>
</form>
</body>
</html>
