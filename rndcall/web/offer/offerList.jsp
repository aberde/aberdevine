<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

    <bean:define name="OfferForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
    <bean:define name="OfferForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
    <bean:define name="OfferForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
    <bean:define name="OfferForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
    <bean:define name="OfferForm" property="searchVO.loginId" id="loginId" type="java.lang.String"/>
    <bean:define name="OfferForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String"/>
 
    <bean:define id="path" type="java.lang.String" value="/Offer.do"/>
 
    <script type="text/javascript">
    <!--
        var data = {
           num : 2 // 위치순번
        };
        // 현재메뉴 위치.
        menuFocus(data);
    //-->
    </script>
 
    <script type="text/javascript">
    <!--
        activeDebug = true;  
        module = '/switch.do?prefix=/offer&method=offerList&page=';
        function offerDetailView(arg1, arg2){
            fm.elements["searchVO.seq"].value=arg2; 
            fm.elements["searchVO.board_type"].value=arg1; 
            fm.elements["method"].value="offerDetailView";
            fm.submit();
        }
     
        function goSearch(){
            fm.elements["method"].value="offerList";
            fm.submit();
        }
        
        function goStatList(arg1, arg2){
            fm.elements["searchVO.whichSearch"].value="";
            fm.elements["searchVO.searchTxt"].value="";
            fm.elements["searchVO.board_type"].value=arg1;
            fm.elements["searchVO.type"].value=arg2;
            fm.elements["searchVO.searchCategory"].value="";
            fm.elements["method"].value="offerList";
            fm.submit();
        }
        
        function goStatList1(arg1, arg2){
            var url ="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireList&searchVO.board_type="+arg1+"&searchVO.type="+arg2+"&searchVO.menu_sn=01";
            document.location.href = url;
        }
        //function goInquireForm(){
        // if("<%=loginId%>" == ""){
        //  alert("로그인 후 이용하실 수 있습니다.");
        //  return;
        // }else{
        //  var url ="/switch.do?prefix=&page=/Inquire.do?method=getInquireForm";
        //  document.location.href = url;
        // }
        //}
     
        //function goInquireList(arg){
        // var url ="/switch.do?prefix=&page=/Inquire.do?method=getInquireList&searchVO.board_type="+arg;
        // document.location.href = url;
        //}
     
        //function goFaq(){
        //  var url ="/switch.do?prefix=&page=/Faq.do?method=faqList";
        //  document.location.href = url;
        //}
    //-->
    </script>
 
    <!-- container -->
    <div id="container">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>R&amp;D 신문고</h2>
                <span><img src="/img/common/h2_entxt03.gif" alt="Online Sinmungo" /></span>
            </div>
            <ul class="lnb-lst">
                <li class="on"><a href="JavaScript:goOffer()">R&amp;D 신문고</a></li>
            </ul>
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">  
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href="JavaScript:goOffer()">R&amp;D 신문고</a></li>
                    <li class="on"><a href="JavaScript:goOffer()">R&amp;D 신문고</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">  
                <div class="sinmungo01 mt60">
                    <h4>R&amp;D 신문고 제안 처리 절차</h4>
                    <ul class="clearfix">
                        <li>의견제시</li>
                        <li>접수중</li>
                        <li>검토중</li>
                        <li>답변완료</li>
                    </ul>
                </div>
    
                <html:form action="/Offer" method="post" name="fm" type="kr.go.rndcall.mgnt.offer.form.OfferForm">
                    <html:hidden name="OfferForm" property="method" value="offerList"/>
                    <html:hidden name="OfferForm" property="vo.cell_number"/>
                    <html:hidden name="OfferForm" property="vo.email"/>
                    <html:hidden name="OfferForm" property="searchVO.loginId"/>
                    <html:hidden name="OfferForm" property="searchVO.name"/>
                    <html:hidden name="OfferForm" property="searchVO.board_type"/>
                    <html:hidden name="OfferForm" property="searchVO.searchCategory"/>
                    <html:hidden name="OfferForm" property="searchVO.seq"/>
                    <html:hidden name="OfferForm" property="searchVO.type"/>
                    <html:hidden name="OfferForm" property="searchVO.menu_sn"/>
                    <html:hidden name="OfferForm" property="searchVO.pagerOffset"/>

                    <!-- search-box -->
                    <div class="search-box">
                        <div class="search-form">
                            <html:select name="OfferForm" property="searchVO.whichSearch" style="width:90px;">
                                <html:option value="title">제목</html:option>
                                <html:option value="contents">내용</html:option>
                                <html:option value="all">전체</html:option>
                            </html:select>
                            <html:text name="OfferForm" property="searchVO.searchTxt" title="검색어를 입력하세요" maxlength="35"onchange="trim(this)"  />
                            <a href="javascript:goSearch()" class="search-btn"><img src="/img/sub/icon_zoom.gif" alt="검색" /></a>
                        </div>
                    </div>
                    <!-- //search-box -->
                    <!-- board-type01 -->
                    <div class="board-type01 mt20">
                        <div class="board-box">
                            <table border="0" summary="번호, 제목, 글쓴이, 등록일, 상태, 조회수 목록페이지">
                                <caption>R&amp;D 신문고 목록페이지</caption>
                                <colgroup>
                                    <col width="7%" />
                                    <col width="*" />
                                    <col width="8%" />
                                    <col width="12%" />
                                    <col width="12%" />
                                    <col width="8%" />
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th scope="col">번호</th>
                                        <th scope="col">제목</th>
                                        <th scope="col">글쓴이</th>
                                        <th scope="col">등록일</th>
                                        <th scope="col">상태</th>
                                        <th scope="col">조회수</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <logic:empty name="OfferForm" property="voList">
                                        <tr><td colspan="6">등록된 정보가 없습니다.</td></tr>
                                    </logic:empty>
                                    <logic:notEmpty name="OfferForm" property="voList">
                                        <logic:iterate name="OfferForm" property="voList" indexId="rowNum" id="vo">
                                            <tr <%= rowNum.intValue() % 2 == 1 ? "class=\"on\"" : "" %>>
                                                <td><%= totRowCount.intValue() - rowNum.intValue() -  Util.replaceNull((String)pagerOffset, 0) %></td>
                                                <td class="txt-l">
                                                    <bean:define name="vo" property="title" id="title" type="java.lang.String"/>
                                                    <%
                                                        String title_n = "";
                                                        int len = 14;
                                                        if ( title.length() > len ) {
                                                            title_n = title.substring(0,len) + "...";
                                                        } else {
                                                            title_n = title;
                                                        }
                                                    %>
                                                    <a href="JavaScript:offerDetailView('OFFER',<bean:write name="vo" property="seq"/>)"><bean:write name="vo" property="title"/></a>
                                                </td>
                                                <td>
                                                    <logic:notEmpty name="vo" property="reg_nm">
	                                                    <bean:write name="vo" property="reg_nm"/>
                                                    </logic:notEmpty>
                                                    <logic:empty name="vo" property="reg_nm">
                                                                비회원
                                                    </logic:empty>
                                                </td>
                                                <td>
                                                    <bean:write name="vo" property="reg_dt"/>
                                                </td>
                                                <td class="center">
                                                    <bean:define name="vo" property="stat" id="stat" type="java.lang.String"/>
                                                    <%
                                                        if ( stat.equals("Y") ) {
                                                            out.print("<span class=\"btn-set set4 gray\">답변완료</span>");
                                                        } else {
                                                            out.print("<span class=\"btn-set set4 yellow\">접수중</span>");
                                                        }
                                                    %>
                                                </td>
                                                <td>
                                                    <bean:write name="vo" property="read_count"/>
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
    
                    <div class="btn-lst txt-r">
                        <span class="btn-set pink"><a href="/switch.do?prefix=/offer&page=/Offer.do?method=offerInsertForm&searchVO.menu_sn=14">글쓰기</a></span>
                    </div>
                    
                </html:form>
    
            </div> 
            <!-- //section --> 
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->
 
<%@include file="/include/bottom.jsp"%>