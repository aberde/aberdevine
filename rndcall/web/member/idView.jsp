<%@page contentType="text/html; charset=utf-8" %>
<%@include file="/include/top_pop.jsp"%>

</head>
<!--[if lt IE 7]>  <body class="no-js ie ie6 lte9 lte8 lte7"> <![endif]-->
<!--[if IE 7]>     <body class="no-js ie ie7 lte9 lte8 lte7"> <![endif]-->
<!--[if IE 8]>     <body class="no-js ie ie8 lte9 lte8"> <![endif]-->
<!--[if IE 9]>     <body class="no-js ie ie9 lte9"> <![endif]-->
<!--[if gt IE 9]>  <body class="no-js">  <![endif]-->
<!--[if !IE]><!--> <body> <!--<![endif]-->

    <!-- pop -->
    <div id="pop-join">
        <div class="tit">
            <strong>아이디 찾기</strong>
            <!-- <p>아이디 중복확인</p> -->
        </div>
        <!-- board-write -->
        <div class="board-write">
            <div class="board-box">
                <table summary="아이디 ">
                    <caption>아이디 찾기</caption>
                    <colgroup>
                        <col width="20%"/>
                        <col width="*"/>
                    </colgroup>
                    <tbody>
                        <logic:empty name="memberForm" property="voList">
                        <tr>
                            <td colspan="2" class="txt-c bdl-n" >
                                <span>
                                    입력하신 정보가 일치하지 않습니다.<br />
                                    다시 확인 후 입력하여 주시기 바랍니다.
                                </span>
                            </td>
                        </tr>
                        </logic:empty>
                        <tr>
                            <td colspan="2" class="txt-c bdl-n" >
                                <span>
                                    가입하신 아이디는 아래와 같습니다.
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="id">아이디</label></th>
                            <td>
                                <logic:iterate name="memberForm" property="voList" id="vo">
                                    <span class="tbox"><bean:write name="vo" property="login_id"/></span>
                                </logic:iterate>
                            </td>
                        </tr>
                    </tbody>
                 </table>
            </div>
        </div>
        <!-- // board-write -->
        <!-- btn-set -->
        <div class="btn-lst txt-c">
            <span class="btn-set green"><a href="javascript:window.close();">확인</a></span>
        </div>
        <!-- //btn-set-->

        <a href="#" class="btn-close" onclick="javascript:window.close();"><span >닫기</span></a>
    </div>
    <!-- // pop -->


</body>
</html>
