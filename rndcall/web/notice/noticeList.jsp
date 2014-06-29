<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

    <bean:define name="NoticeForm" property="totRowCount" id="totRowCount" type="java.lang.Integer" />
    <bean:define name="NoticeForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String" />
    <bean:define name="NoticeForm" property="maxPageItems" id="maxPageItems" type="java.lang.String" />
    <bean:define name="NoticeForm" property="pagerOffset" id="pagerOffset" type="java.lang.String" />
    <bean:define name="NoticeForm" property="searchVO.loginId" id="loginId" type="java.lang.String" />
    <bean:define name="NoticeForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String" />
 
    <bean:define id="path" type="java.lang.String" value="/Notice.do" />

    <script type="text/javascript">
    <!--
        var data = {
            num : 3 // 위치순번
        };
        // 현재메뉴 위치.
        menuFocus(data);
    //-->
    </script>
 
    <script type="text/javascript">
    <!--
        activeDebug = true;  
        module = '/switch.do?prefix=/notice&method=noticeList&page=';
  
        function noticeDetailView(arg1, arg2){
            fm.elements["searchVO.seq"].value=arg2; 
            fm.elements["searchVO.board_type"].value=arg1; 
            fm.elements["method"].value="noticeDetailView";
            fm.submit();
        }
  
        function goSearch(){
            fm.elements["method"].value="noticeList";
            fm.submit();
        }
    //-->
    </script>
 
    <!-- container -->
    <div id="container">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>알림</h2>
                <span><img src="/img/common/h2_entxt04.gif" alt="알림" /></span>
            </div>
            <ul class="lnb-lst">
                <li class="on"><a href="JavaScript:goNotice()">알림</a></li>
            </ul>
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">  
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href="JavaScript:goNotice()">알림</a></li>
                    <li class="on"><a href="JavaScript:goNotice()">알림</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">  
                <div class="tit-area">
                    <h3>알림</h3>
                    <p>규정 및 제도에 대한 개선사항을 건의해주세요</p>
                </div>
    
                <html:form action="/Notice" method="post" name="fm" type="kr.go.rndcall.mgnt.notice.form.NoticeForm">
                    <html:hidden name="NoticeForm" property="method" value="noticeList" />
                    <html:hidden name="NoticeForm" property="searchVO.loginId" />
                    <html:hidden name="NoticeForm" property="searchVO.name" />
                    <html:hidden name="NoticeForm" property="searchVO.board_type" />
                    <html:hidden name="NoticeForm" property="searchVO.seq" />
                    <html:hidden name="NoticeForm" property="searchVO.type" />
                    <html:hidden name="NoticeForm" property="searchVO.menu_sn" />
                    <html:hidden name="NoticeForm" property="searchVO.pagerOffset"/>
 
                    <!-- search-box -->
                    <div class="search-box">
                        <div class="search-form">
                            <html:select name="NoticeForm" property="searchVO.whichSearch" style="width:90px;">
                                <html:option value="title">제목</html:option>
                                <html:option value="contents">내용</html:option>
                                <html:option value="all">제목+내용</html:option>
                            </html:select>
                            <html:text name="NoticeForm" property="searchVO.searchTxt" title="검색어를 입력하세요" maxlength="35" onchange="trim(this)" />
                            <a href="javascript:goSearch()" class="search-btn"><img src="/img/sub/icon_zoom.gif" alt="검색" /></a>
                        </div>
                    </div>
                    <!-- //search-box -->
                    <!-- board-type01 -->
                    <div class="board-type01 mt20">    
                        <div class="board-box">
                            <table border="0" summary="번호, 제목, 첨부파일, 등록일, 조회수 목록페이지">
                                <caption>알림 목록 페이지</caption>
                                <colgroup>
                                    <col width="7%" />
                                    <col width="*" />
                                    <col width="12%" />
                                    <col width="12%" />
                                    <col width="8%" />
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th scope="col">번호</th>
                                        <th scope="col">제목</th>
                                        <th scope="col">첨부파일</th>
                                        <th scope="col">등록일</th>
                                        <th scope="col">조회수</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <logic:empty name="NoticeForm" property="voList">
                                        <tr>
                                            <td colspan="5">등록된 정보가 없습니다.</td>
                                        </tr>
                                    </logic:empty>
                                    <logic:notEmpty name="NoticeForm" property="voList">
                                        <logic:iterate name="NoticeForm" property="voList" indexId="rowNum" id="vo">
                                            <tr <%= rowNum.intValue() % 2 == 1 ? "class=\"on\"" : "" %>>
                                                <td><%=totRowCount.intValue() - rowNum.intValue() - Util.replaceNull((String) pagerOffset, 0)%></td>
                                                <td class="txt-l"><a href="JavaScript:noticeDetailView('NOTICE',<bean:write name="vo" property="seq"/>)"><bean:write name="vo" property="title" /></a></td>
                                                <td>
                                                    <logic:equal name="vo" property="file_id" value="N">
                                                        <span class="btn-set save">첨부파일</span>
                                                    </logic:equal>
                                                    <logic:notEqual name="vo" property="file_id" value="N">
                                                    </logic:notEqual>
                                                </td>
                                                <td><bean:write name="vo" property="reg_dt" /></td>
                                                <td><bean:write name="vo" property="read_count" /></td>
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
     
                    <%
                        if ( roleCd.equals("0000Z") || roleCd.equals("0000C") ) {
                    %>
                    <!-- btn-set-->
                    <div class="btn-lst txt-r">
                        <span class="btn-set pink"><a href="/switch.do?prefix=/notice&page=/Notice.do?method=noticeInsertForm&searchVO.menu_sn=04">알림등록</a></span>
                    </div>
                    <!-- //btn-set-->
                    <%
                        }
                    %>
                    
                </html:form>
    
            </div>
            <!-- //section --> 
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->

<%@include file="/include/bottom.jsp"%>