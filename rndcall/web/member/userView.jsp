<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

	<bean:define name="memberForm" property="errCd" id="errCd" type="java.lang.String"/>
	<bean:define name="memberForm" property="errMsg" id="errMsg" type="java.lang.String"/>

    <bean:define id="path" type="java.lang.String" value="/member.do"/>
    
<%
    if (mainLoginVO == null || !mainIsLogin) {  
%>
    <script type="text/javascript">
        alert('로그인이 필요한 메뉴입니다.');
        document.location.href = "/index.jsp";
    </script>
<%      
    }
%>

<%
	String board_nm = "";
%>

    <script type="text/javascript">
    <!--
		activeDebug = true;  
		module = '/switch.do?prefix=/mypage&method=getMypageList&page=';

		function goMypageView(arg1, arg2){
			fm.elements["searchVO.seq"].value=arg2;	
			fm.elements["searchVO.board_type"].value=arg1;
			fm.elements["searchVO.menu_sn"].value = "03";	
			fm.elements["method"].value="getMypageView";
			fm.submit();
		}

		function goStatList(arg1, arg2){
			fm.elements["searchVO.type"].value=arg2;	
			fm.elements["searchVO.board_type"].value=arg1;	
			fm.elements["searchVO.menu_sn"].value = "03";
			fm.elements["method"].value="getMypageList";
			fm.submit();
		}
	//-->
    </script>
	
	<!-- container -->
    <div id="container">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>마이페이지</h2>
                <span><img src="/img/common/h2_mypage.gif" alt="Online Consultation" /></span>
            </div>
            <ul class="lnb-lst">
                <li><a href="JavaScript:goMypage()">마이페이지</a></li>
                <li class="on"><a href="javascript:goUserUpdate();">내정보</a></li>
            </ul>
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="../../img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href="JavaScript:goMypage()">마이페이지</a></li>
                    <li class="on"><a href="javascript:goUserUpdate();">내정보</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section mt60">
                <!-- board-write -->
                <div class="board-write mt10">
                    <div class="board-box">
                        <table summary="회원가입">
                            <caption>회원가입</caption>
                            <colgroup>
                                <col width="20%"/>
                                <col width="*"/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th scope="row">사용자 아이디</th>
                                    <td><bean:write name="memberForm" property="vo.login_id" /></td>
                                </tr>
                                <tr>
                                    <th scope="row">소속</th>
                                    <td>
                                        <logic:equal name="memberForm" property="vo.sector" value="1">중앙행정기관</logic:equal>
                                        <logic:equal name="memberForm" property="vo.sector" value="2">전문기관</logic:equal>
                                        <logic:equal name="memberForm" property="vo.sector" value="3">정부출연연구기관</logic:equal>
                                        <logic:equal name="memberForm" property="vo.sector" value="4">대학</logic:equal>
                                        <logic:equal name="memberForm" property="vo.sector" value="5">기업</logic:equal>
                                        <logic:equal name="memberForm" property="vo.sector" value="6">기타</logic:equal>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">아이디 찾기 답변</th>
                                    <td>
                                        <html:select styleId="vo.idFindQuestion" name="memberForm" property="vo.idFindQuestion" style="width:230px;">
                                            <html:option value="1">기억에 남은 추억의 장소는</html:option>
                                            <html:option value="2">내가 다닌 초등학교는?</html:option>
                                            <html:option value="3">자신 인생의 좌우명은?</html:option>
                                            <html:option value="4">자신의 보물 제1호는?</html:option>
                                            <html:option value="5">가장 기억에 남는 영화 이름은?</html:option>
                                            <html:option value="6">자신만의 비밀이 있다면?</html:option>
                                            <html:option value="7">추억하고 싶은 날짜가 있다면?</html:option>
                                            <html:option value="8">받은 선물 중 기억에 남는 선물은?</html:option>
                                            <html:option value="9">인상 깊게 읽은 책 이름은?</html:option>
                                            <html:option value="10">내가 좋아 하는 케릭터 이름은?</html:option>
                                            <html:option value="11">자신의 소속기관은?</html:option>
                                        </html:select> 
                                        <html:text styleId="vo.idFindAnswer" name="memberForm" property="vo.idFindAnswer" title="아이디 질문을 입력해주세요." style="width:310px"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">비밀번호 찾기 답변</th>
                                    <td>
                                        <html:select styleId="vo.pwFindQuestion" name="memberForm" property="vo.pwFindQuestion" style="width:230px;">
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
                                        </html:select> 
                                        <html:text styleId="vo.pwFindAnswer" name="memberForm" property="vo.pwFindAnswer" title="비밀번호 질문을 입력해주세요." style="width:310px"></html:text>
                                    </td>
                                    
                                </tr>
                            </tbody>
                         </table>
                    </div>
                </div>
                <!-- // board-write -->
                <!-- btn-set -->
                <div class="btn-lst txt-r">
                    <span class="btn-set green"><a href="javascript:update();">수정</a></span>
                </div>
                <!-- //btn-set-->
            </div>  
            <!-- //section -->  
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->
	
<%@include file="/include/bottom.jsp"%>