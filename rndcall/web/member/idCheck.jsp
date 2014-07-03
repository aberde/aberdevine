<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/include/top_pop.jsp"%>

	<bean:define name="memberForm" property="searchVO.login_id" id="id" type="java.lang.String"/>
	<bean:define name="memberForm" property="searchVO.checkIdVal" id="checkIdVal" type="java.lang.String"/>
    
    <bean:define id="path" type="java.lang.String" value="/member.do"/>

    <title>아이디 중복 확인</title>

	<script type="text/javascript">
		var reg_idIsNull = "아이디를 입력하세요.";
		var reg_idIsNotExp = "아이디는 6~20자 이내 영문 또는 영문+숫자로만 입력하셔야 합니다.";
	
		/**
		* 아이디 사용
		*/
		function useCheckId( user_id ) {
			opener.document.fm.elements["vo.login_id"].value = "";
			opener.document.fm.elements["vo.login_id"].value = user_id;
			opener.document.fm.elements["vo.checkIdVal"].value = "<%=checkIdVal%>";
			opener.document.fm.elements["vo.password"].focus();
			opener.document.fm.elements["vo.login_id"].readOnly = true;
			opener.document.fm.elements["vo.login_id"].style.background = "#eeeeee";
			self.close();
		}
		
		/**
		* 아이디 중복 검색
		*/
		function reCheckUserId() {
			//var idcheckUrl = "/switch.do?prefix=/member&page=/member.do?method=getIdCheckForm";
			
			//1.아이디
			if ( chkNull( fm.elements["searchVO.login_id"].value ) ) {
				alert( reg_idIsNull );
				fm.elements["searchVO.login_id"].focus();
				return false;
			}
			//2.아이디 형식 체크
			if( !chkIdExp( fm.elements["searchVO.login_id"].value ) ) {
				alert( reg_idIsNotExp );
				fm.elements["searchVO.login_id"].value = "";
				fm.elements["searchVO.login_id"].focus();
				return false;
			}
			fm.elements["method"].value="getIdCheckForm";
			fm.submit();	
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
		
		/**
		* 아이디 정규표현식 체크
		*/
		function chkIdExp( str ) {
			chk1 = /^[a-zA-Z\d]{6,20}$/i;  //a-z와 0-9이외의 문자가 있는지 확인
		//    chk2 = /[a-z]/i;  //적어도 한개의 a-z 확인
		    return chk1.test( str ); //&& chk3.test( str );
		}
	</script>
</head>
<!--[if lt IE 7]>  <body class="no-js ie ie6 lte9 lte8 lte7" onload="onLoad()"> <![endif]-->
<!--[if IE 7]>     <body class="no-js ie ie7 lte9 lte8 lte7" onload="onLoad()"> <![endif]-->
<!--[if IE 8]>     <body class="no-js ie ie8 lte9 lte8" onload="onLoad()"> <![endif]-->
<!--[if IE 9]>     <body class="no-js ie ie9 lte9" onload="onLoad()"> <![endif]-->
<!--[if gt IE 9]>  <body class="no-js" onload="onLoad()">  <![endif]-->
<!--[if !IE]><!--> <body onload="onLoad()"> <!--<![endif]-->

<html:form action="/member" method="post" name="fm" type="kr.go.rndcall.mgnt.member.MemberForm" onsubmit="return checkOnSubmit(this)">
    <html:hidden name="memberForm" property="method" value="getIdCheckForm"/>

    <!-- pop -->
    <div id="pop-join">
        <div class="tit">
            <strong>아이디 중복확인</strong>
            <!-- <p>아이디 중복확인</p> -->
        </div>
        <!-- board-write -->
        <div class="board-write">
            <div class="board-box">
                <table summary="아이디 중복확인">
                    <caption>아이디 중복확인</caption>
                    <colgroup>
                        <col width="20%"/>
                        <col width="*"/>
                    </colgroup>
                    <tbody>
                        <tr>
                            <th scope="row"><label for="id">아이디</label></th>
                            <td>
                                <html:text name="memberForm" property="searchVO.login_id" style="width:200px" title="사용자ID" />
                                <span class="btn-set set2 s-blue"><a href="javascript:reCheckUserId();">중복확인</a><span class="zoom"></span></span>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" class="txt-c bdl-n" >
							    <%
							        if ( !checkIdVal.equals("Y") ) {
							    %>
							    <span>중복된 아이디입니다.<br />다른 아이디를 검색하세요.</span>
							    <%
							        } else {
							    %>
                                <span>아이디는 사용 가능한 아이디 입니다<br />아이디를 사용하시겠습니까?</span>
                                <%
							        }
                                %>
                            </td>
                        </tr>
                        
                    </tbody>
                 </table>
            </div>
        </div>
        <!-- // board-write -->
        <!-- btn-set -->
        <div class="btn-lst txt-c">
            <span class="btn-set green"><a href="javascript:useCheckId('<%=id%>');">아이디 사용</a></span>
            <span class="btn-set"><a href="#" onclick="popClose()">창닫기</a></span>
        </div>
        <!-- //btn-set-->

        <a href="#" class="btn-close" onclick="popClose()"><span >닫기</span></a>
    </div>
    <!-- // pop -->

</html:form>
</body>
</html>