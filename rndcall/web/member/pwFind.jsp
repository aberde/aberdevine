<%@page contentType="text/html; charset=utf-8"%>

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
    
    <script type="text/javascript">
	<!--
		function onLoad() {
		    
		}
		
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
		
		function idFind() { 
		    //1.이름
// 		    if ( chkNull( document.all.name.value ) ) {
// 		        alert("이름을 입력하세요.");
// 		        document.all.name.focus();
// 		        return;
// 		    }
		    //2.아이디
		    if ( chkNull( document.all.id.value ) ) {
		        alert("아이디를 입력하세요.");
		        document.all.id.focus();
		        return;
		    }
		    //3.이메일
// 		    if ( chkNull( document.all.email_id.value) || chkNull( document.all.email_domain.value) ) {
// 		        alert( "이메일 주소를 입력하세요.");
// 		        document.all.email_id.focus();
// 		        return;
// 		    }
		    
		    document.fm.elements["vo.login_id"].value = document.all.id.value;
// 		    document.fm.elements["vo.name"].value = document.all.name.value;
// 		    document.fm.elements["vo.email"].value = document.all.email_id.value+"@"+document.all.email_domain.value;
		
		    var winfeatures = "toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=580,height=340";
// 		    var url = "/switch.do?prefix=&page=/member.do?method=pwFind&vo.login_id=" + document.all.id.value + "&vo.name=" + document.all.name.value + "&vo.email=" + escape(document.all.email_id.value+"@"+document.all.email_domain.value);
		    var url = "/switch.do?prefix=&page=/member.do?method=pwFind&vo.login_id=" + document.all.id.value + "&vo.pwFindQuestion=" + document.all.pwFindQuestion.value + "&vo.pwFindAnswer=" + document.all.pwFindAnswer.value;
		
		    idFindWin = window.open( url, "idFind", winfeatures);
		
		    self.close();
		    
		}
		
		/**
		* 이메일 도메인 선택
		*/
		function selectEmailDomain( form ) {
		    var f = form;
		    f.email_domain.value = "";
		    if( f.email_domain_sel.value != "none" ) {
		        f.email_domain.value = f.email_domain_sel.value;
		        f.email_domain.readOnly = true;
		    }else{
		        f.email_domain.readOnly = false;
		        f.email_domain.focus();
		    }
		}
	//-->
	</script>
</head>
<!--[if lt IE 7]>  <body class="no-js ie ie6 lte9 lte8 lte7" onload="onLoad()"> <![endif]-->
<!--[if IE 7]>     <body class="no-js ie ie7 lte9 lte8 lte7" onload="onLoad()"> <![endif]-->
<!--[if IE 8]>     <body class="no-js ie ie8 lte9 lte8" onload="onLoad()"> <![endif]-->
<!--[if IE 9]>     <body class="no-js ie ie9 lte9" onload="onLoad()"> <![endif]-->
<!--[if gt IE 9]>  <body class="no-js" onload="onLoad()">  <![endif]-->
<!--[if !IE]><!--> <body onload="onLoad()"> <!--<![endif]-->

    <!-- pop -->
    <div id="pop-join">
        <div class="tit">
            <strong>비밀번호 찾기</strong>
        </div>
        <!-- board-write -->
        <div class="board-write">
            <div class="board-box">
                <table summary="비밀번호 찾기">
                    <caption>비밀번호 찾기</caption>
                    <colgroup>
                        <col width="20%"/>
                        <col width="50%"/>
                        <col width="*"/>
                    </colgroup>
                    <tbody>
                        <tr>
                            <th scope="row"><label for="id">아이디</label></th>
                            <td><input type="text" id="id" style="width:200px"/></td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="pwFindQuestion">질문</label></th>
                            <td>
                                <select id="pwFindQuestion" name="pwFindQuestion">
                                    <option value="1">기억에 남은 추억의 장소는</option>
                                    <option value="2">내가 다닌 초등학교는?</option>
                                    <option value="3">자신 인생의 좌우명은?</option>
                                    <option value="4">자신의 보물 제1호는?</option>
                                    <option value="5">가장 기억에 남는 영화 이름은?</option>
                                    <option value="6">자신만의 비밀이 있다면?</option>
                                    <option value="7">추억하고 싶은 날짜가 있다면?</option>
                                    <option value="8">받은 선물 중 기억에 남는 선물은?</option>
                                    <option value="9">인상 깊게 읽은 책 이름은?</option>
                                    <option value="10">내가 좋아 하는 케릭터 이름은?</option>
                                    <option value="11">자신의 소속기관은?</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="pwFindAnswer">답변</label></th>
                            <td><input type="text" id="pwFindAnswer" name="pwFindAnswer" style="width:200px"/></td>
                        </tr>
                    </tbody>
                 </table>
            </div>
        </div>
        <!-- // board-write -->
        <!-- btn-set-->
        <div class="btn-lst txt-r">
            <span class="btn-set "><a href="javascript:idFind();">확인</a></span>
            <span class="btn-set "><a href="window.close();">취소</a></span>
        </div>
        <!-- //btn-set-->   

        <a href="#" class="btn-close" onclick="window.close();"><span >닫기</span></a>
    </div>
    <!-- // pop -->
    
    <form name="fm" method="post" action="/member.do" >
		<input type="hidden" name="method" value="pwFind"/>
		<input type="hidden" name="vo.login_id"/>
		<input type="hidden" name="vo.name"/>
		<input type="hidden" name="vo.email"/>
    </form>

</body>
</html>