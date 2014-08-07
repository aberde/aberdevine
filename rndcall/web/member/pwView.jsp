<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/include/top_pop.jsp"%>

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
                <table summary="비밀번호 ">
                    <caption>비밀번호 결과</caption>
                    <colgroup>
                        <col width="100%"/>
                    </colgroup>
                    <tbody>
                        <tr>
                            <td class="txt-c bdl-n" >
                                <logic:equal name="memberForm" property="vo.password" value="">
                                    <div class="pw-bx">입력하신 정보가 일치하지 않습니다.<br />다시 확인 후 입력하여 주시기 바랍니다.</div>
                                </logic:equal>
                                <logic:notEqual name="memberForm" property="vo.password" value="">
                                    <div class="pw-bx">등록하신 비밀번호는<strong><bean:write name="memberForm" property="vo.password"/></strong> 입니다</div>
                                </logic:notEqual>
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