<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

	<bean:define name="MypageForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
	<bean:define name="MypageForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
	<bean:define name="MypageForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
	<bean:define name="MypageForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>

    <bean:define id="path" type="java.lang.String" value="/Mypage.do"/>
    
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
                <li class="on"><a href="JavaScript:goMypage()">마이페이지</a></li>
                <li><a href="javascript:goUserUpdate();">내정보</a></li>
            </ul>
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li class="on"><a href="JavaScript:goMypage()">마이페이지</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">
                <!-- my-bx -->
                <div class="my-bx mt60 clearfix">
                    <div class="bx">
                        <div class="inquiry">
                            <h4 class="red">온라인 상담</h4>
                            <ul>
                                <li class="bg01">전체 : <span><a href="JavaScript:goStatList('QNA','1')"><bean:write name="MypageForm" property="vo.statCnt1"/>건</a></span></li>
                                <li class="bg02">접수중 : <span><a href="JavaScript:goStatList('QNA','2')"><bean:write name="MypageForm" property="vo.statCnt2"/>건</a></span></li>
                                <li class="bg03">완료 : <span><a href="JavaScript:goStatList('QNA','3')"><bean:write name="MypageForm" property="vo.statCnt3"/>건</a></span></li>
                            </ul>
                        </div>
                        <div class="sinmungo">
                            <h4 class="blue">R&amp;D 신문고</h4>
                            <ul>
                                <li class="bg01">전체 : <span><a href="JavaScript:goStatList('OFFER','1')"><bean:write name="MypageForm" property="vo.statCnt4"/>건</a></span></li>
                                <li class="bg02">접수중 : <span><a href="JavaScript:goStatList('OFFER','2')"><bean:write name="MypageForm" property="vo.statCnt5"/>건</a></span></li>
                                <li class="bg03">검토중 : <span><a href="JavaScript:goStatList('OFFER','3')"><bean:write name="MypageForm" property="vo.statCnt6"/>건</a></span></li>
                                <li class="bg04">완료 : <span><a href="JavaScript:goStatList('OFFER','5')"><bean:write name="MypageForm" property="vo.statCnt8"/>건</a></span></li>
                            </ul>
                        </div>
                    </div>
                    <div class="scrap">
                        <h4 class="orange">스크랩현황</h4>
                        <p class="number">건수 : <span><a href="JavaScript:goStatList('','4')"><bean:write name="MypageForm" property="vo.statCnt7"/>건</a></span></p>
                    </div>
                </div>
                <!-- my-bx -->
                
                <html:form action="/Mypage" method="post" name="fm" type="kr.go.rndcall.mgnt.mypage.form.MypageForm">
					<html:hidden name="MypageForm" property="method" value="getMypageList"/>
					<html:hidden name="MypageForm" property="vo.cell_number"/>
					<html:hidden name="MypageForm" property="vo.email"/>
					<html:hidden name="MypageForm" property="searchVO.loginId"/>
					<html:hidden name="MypageForm" property="searchVO.name"/>
					<html:hidden name="MypageForm" property="searchVO.board_type"/>
					<html:hidden name="MypageForm" property="searchVO.searchCategory"/>
					<html:hidden name="MypageForm" property="searchVO.seq"/>
					<html:hidden name="MypageForm" property="searchVO.type"/>   
					<html:hidden name="MypageForm" property="searchVO.menu_sn"/>

<!--                     // TODO 검색기능 추가. -->
	                <!-- search-box -->
	                <div class="search-box mt20">
	                    <div class="search-form">
	                        <select style="width:90px;">
	                            <option>전체</option>
	                            <option>제목</option>
	                            <option>내용</option>
	                        </select>
	                        <input type="text" id="board-search" title="검색어를 입력하세요" />
	                        <a href="#" class="search-btn"><img src="/img/sub/icon_zoom.gif" alt="검색" /></a>
	                    </div>
	                </div>
	                <!-- //search-box -->
	                <!-- board-type01 -->
	                <div class="board-type01 mt20">
	                    <div class="board-box">
	                        <table border="0" summary="번호, 구분, 분류, 제목, 등록일, 처리일, 진행상태 목록페이지">
	                            <caption>마이페이지 목록 페이지</caption>
	                            <colgroup>
	                                <col width="6%" />
	                                <col width="12%" />
	                                <col width="14%" />
	                                <col width="*" />
	                                <col width="12%" />
	                                <col width="12%" />
	                                <col width="10%" />
	                            </colgroup>
	                            <thead>
	                                <tr>
	                                    <th scope="col">번호</th>
	                                    <th scope="col">구분</th>
	                                    <th scope="col">분류</th>
	                                    <th scope="col">제목</th>
	                                    <th scope="col">등록일</th>
	                                    <th scope="col">처리일</th>
	                                    <th scope="col">진행상태</th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                                <logic:empty name="MypageForm" property="voList">
                                        <tr><td colspan="7">등록된 정보가 없습니다.</td></tr>
                                    </logic:empty>
                                    <logic:notEmpty name="MypageForm" property="voList">
                                        <logic:iterate name="MypageForm" property="voList" indexId="rowNum" id="vo">
                                            <tr <%= rowNum.intValue() % 2 == 1 ? "class=\"on\"" : "" %>>
                                                <td><%= rowNum.intValue() + Util.replaceNull((String)pagerOffset, 0) + 1 %></td>
                                                <bean:define name="vo" property="board_type" id="board_type" type="java.lang.String"/>
                                                <%
                                                    if ( board_type.equals("QNA") ) {
                                                %>
                                                <td><span class="icon mr0">상담</span></td>
                                                <%
                                                    } else if ( board_type.equals("OFFER") ) {
                                                %>
                                                <td><span class="icon icon01 mr0">신문고</span></td>
                                                <%
                                                    }
                                                %>
                                                <bean:define name="vo" property="category1" id="category1" type="java.lang.String"/>
                                                <%
                                                    if ( !category1.equals("-") ) {
                                                %>
                                                <td><bean:write name='vo' property='category1'/></td>
                                                <%
                                                    } else {
                                                %>
                                                <td>&nbsp;</td>
                                                <%
                                                    }
                                                %>
                                                <td class="txt-l">
                                                    <bean:define name="vo" property="title" id="title" type="java.lang.String"/>
                                                    <%
                                                        String title_n = "";
                                                        if ( title.length() > 26 ) {
                                                            title_n = title.substring(0,26) + "...";
                                                        } else {
                                                            title_n = title;
                                                        }
                                                    %>
                                                    <a href="JavaScript:goMypageView('<bean:write name="vo" property="board_type"/>',<bean:write name="vo" property="seq"/>)"><%=title_n %></a>
                                                </td>
                                                <td><bean:write name="vo" property="reg_dt"/></td>
                                                <td><bean:write name="vo" property="answer_reg_dt"/></td>
                                                <td>
                                                    <bean:define name="vo" property="stat" id="stat" type="java.lang.String"/>
                                                    <%
                                                        if ( stat.equals("Y") ) {
                                                    %>
                                                    <span class="btn-set set4 green">답변완료</span>
                                                    <%
                                                        } else if ( stat.equals("S") ) {
                                                    %>
                                                    <span class="btn-set set4 yellow">검토중</span>
                                                    <%
                                                        } else {
                                                    %>
                                                    <span class="btn-set set4 gray">접수중</span>
                                                    <%
                                                        }
                                                    %>
                                                </td>
                                            </tr>
                                        </logic:iterate>
                                    </logic:notEmpty>
	                            </tbody>
	                        </table>
	                    </div>
	                </div>
	                <!-- // board-type01 -->
	                <!-- page-area-->
	                <%@include file="/include/page.jsp"%>
	                <!-- //page-area -->
	                
	           </html:form>
	           
            </div>
            <!-- //section -->  
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->
	
<%@include file="/include/bottom.jsp"%>