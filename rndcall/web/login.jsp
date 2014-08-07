<%@page contentType="text/html; charset=utf-8"%>
<%@page import="kr.go.rndcall.mgnt.login.LoginVO" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>:: R&amp;D도우미센터 ::</title>
    <link rel="stylesheet" type="text/css" href="/css/common.css" />
    <link rel="stylesheet" type="text/css" href="/css/layout.css" />
    <!--[if lt IE 7]>
    <style media="screen" type="text/css">
    #container {
        height:100%;
    }
    </style>
    <![endif]-->
<%
    LoginVO mainLoginVO = (LoginVO) session.getAttribute("loginUserVO");
    if ( mainLoginVO != null && mainLoginVO.getLogin_id() != null && !mainLoginVO.getLogin_id().equals("guest") ) {
        System.out.println("======>>>" + mainLoginVO.getLogin_id());
%>
    <script type="text/javascript">
//         opener.document.location.reload();
        opener.document.location.href = "/index.jsp";
        window.close();
    </script>
<%
    } else {
        System.out.println("Not Login!!!!");
        if ( request.getAttribute("errCd") != null && request.getAttribute("errCd").equals("N") ) {
%>
    <script type="text/javascript">
        alert('아이디 또는 비밀번호가 다릅니다.');
    </script>
<%
        }
    }
%>
	<script type="text/javascript">
	<!--
		var reg_idIsNull = "아이디를 입력하세요.";
		var reg_pwIsNull = "비밀번호를 입력하세요.";
		
		function onLoad() {
		    document.all.id.focus();
		}
		/**
		* 입력여부 체크
		*/
		function chkNull( str ) {
		    if( str.length < 1 || str == "" || str == null ) {
		        return true;
		    } else {
		        return false;
		    }
		}
		
		function onEnter(){
		    if(event.keyCode==13){
		        login();
		        event.returnValue=false;
		    }
		}
		
		function login() {  
		
		    //1.아이디
		    if ( chkNull( document.all.id.value ) ) {
		        alert( reg_idIsNull );
		        document.all.id.focus();
		        return;
		    }
		    //2.아이디 형식 체크
		    if ( chkNull( document.all.password.value ) ) {
		        alert( reg_pwIsNull );
		        document.all.password.focus();
		        return;
		    } 
		    
		    document.fm2.elements["vo.login_id"].value = document.all.id.value;
		    document.fm2.elements["vo.password"].value = document.all.password.value;
		    document.fm2.submit();
		    
		}
		
		function goInsert() {
		    opener.document.location.href = "/switch.do?prefix=&page=/member.do?method=getUserInsertForm&searchVO.menu_sn=06";
		    window.close();
		}
	//-->
	</script>
</head>
<!--[if lt IE 7]>  <body class="no-js ie ie6 lte9 lte8 lte7" onload="onLoad()"> <![endif]-->
<!--[if IE 7]>     <body class="no-js ie ie7 lte9 lte8 lte7" > <![endif]-->
<!--[if IE 8]>     <body class="no-js ie ie8 lte9 lte8" onload="onLoad()"> <![endif]-->
<!--[if IE 9]>     <body class="no-js ie ie9 lte9"  onload="onLoad()"> <![endif]-->
<!--[if gt IE 9]>  <body class="no-js" onload="onLoad()">  <![endif]-->
<!--[if !IE]><!--> <body onload="onLoad()"> <!--<![endif]-->

    <form name="fm2" method="post" action="/login.do" >
		<input type="hidden" name="method" value="login"/>
		<input type="hidden" name="vo.login_id"/>
		<input type="hidden" name="vo.password"/>
    </form>

    <!-- pop -->
    <div id="pop-join">
        <div class="tit">
            <strong>로그인</strong>
        </div>
        <!-- board-write -->
        <div class="board-write">
            <div class="board-box">
                <table summary="로그인">
                    <caption>로그인</caption>
                    <colgroup>
                        <col width="20%"/>
                        <col width="44%"/>
                        <col width="*"/>
                    </colgroup>
                    <tbody>
                        <tr>
                            <th scope="row"><label for="id">아이디</label></th>
                            <td>
                                <%
                                    String id = request.getParameter("id") == null ? "" : request.getParameter("id");
                                %>
                                <input name="id" type="text" style="width:190px" value="<%= id %>" onkeypress="onEnter()"/>
                            </td>
                            <td rowspan="2">
                                <span class="btn-set login"><a href="javascript:login();">로그인</a><span class="bullet"></span></span>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="pw">비밀번호</label></th>
                            <td>
                                <input name="password" type="password" style="width:190px" onkeypress="onEnter()"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3" class=" bdl-n " >
                                <span class="ml10">아이디 / 비밀번호를 잊으셨나요?</span>
                                <span class="btn-set ml70"><a href="/member/idFind.jsp">아이디 찾기</a></span>
                                <span class="btn-set"><a href="/member/pwFind.jsp">비밀번호 찾기</a></span>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3" class=" bdl-n " >
                                <span class="ml10">아이디가 없으신 분은 회원가입을 해주세요 </span>
                                <span class="btn-set ml20"><a href="javascript:goInsert();">회원가입</a></span>

                            </td>
                        </tr>
                        
                    </tbody>
                 </table>
            </div>
        </div>
        <!-- // board-write -->
        
        <a href="#" class="btn-close" onclick="window.close()"><span >닫기</span></a>
    </div>
    <!-- // pop -->
</body>
</html>