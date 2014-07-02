<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

	<bean:define name="DataForm" property="totRowCount" id="totRowCount" type="java.lang.Integer" />
	<bean:define name="DataForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String" />
	<bean:define name="DataForm" property="maxPageItems" id="maxPageItems" type="java.lang.String" />
	<bean:define name="DataForm" property="pagerOffset" id="pagerOffset" type="java.lang.String" />
	<bean:define name="DataForm" property="searchVO.loginId" id="loginId" type="java.lang.String" />
	<bean:define name="DataForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String" />
	<bean:define name="DataForm" property="searchVO.board_type" id="board_type" type="java.lang.String" />

    <bean:define id="path" type="java.lang.String" value="/Data.do" />
    
    <script type="text/javascript">
    <!--
        var data = {
            num : 4 // 위치순번
        };
        // 현재메뉴 위치.
        menuFocus(data);
    //-->
    </script>
    
    <script type="text/javascript">
        activeDebug = true;  
        module = '/switch.do?prefix=/data&method=dataList&page=';

		function goSearch(){
			fm.elements["method"].value="dataList";
			fm.submit();
		}
		
		function goIns(arg){
			fm.elements["searchVO.whichSearch"].value="";
			fm.elements["searchVO.searchTxt"].value="";
			fm.elements["searchVO.board_type"].value=arg;	
			fm.elements["method"].value="dataList";
			fm.submit();
		}
		
		function dataDetailView(arg){
			fm.elements["searchVO.seq"].value=arg;	
			fm.elements["method"].value="dataDetailView";
			fm.submit();
		}
	</script>

    <!-- container -->
    <div id="container">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>자료실</h2>
                <span><img src="/img/common/h2_data_entxt01.gif" alt="Library" /></span>
            </div>
            <ul class="lnb-lst">
                <li><a href="Javascript:goLawInfo()">법령 및 행정규칙</a></li>
<!--                 // TODO 제도 링크 확인. -->
                <li><a href="#none;">제도</a></li>
                <li  class="on"><a href="JavaScript:goIns('DATA')">기타</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href="JavaScript:goIns('DATA')">자료실</a></li>
                    <li class="on"><a href="JavaScript:goIns('DATA')">기타</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>기타</h3>
                    <p>국가연구개발사업 관련 법령,제도 외 참고자료 검색이 가능합니다.</p>
                </div>
                
                <html:form action="/Data" method="post" name="fm" type="kr.go.rndcall.mgnt.data.form.DataForm">
					<html:hidden name="DataForm" property="method" value="dataList" />
					<html:hidden name="DataForm" property="searchVO.loginId" />
					<html:hidden name="DataForm" property="searchVO.name" />
					<html:hidden name="DataForm" property="searchVO.board_type" />
					<html:hidden name="DataForm" property="searchVO.seq" />
					<html:hidden name="DataForm" property="searchVO.type" />
					<html:hidden name="DataForm" property="searchVO.menu_sn"/>
					<html:hidden name="DataForm" property="searchVO.pagerOffset"/>
                
	                <!-- search-box -->
	                <div class="search-box">
	                    <div class="search-form">
	                        <html:select name="DataForm" property="searchVO.whichSearch" style="width:90px;">
                                <html:option value="all">전체</html:option>
                                <html:option value="title">제목</html:option>
                                <html:option value="contents">내용</html:option>
                            </html:select>
                            <html:text name="DataForm" property="searchVO.searchTxt" title="검색어를 입력하세요" maxlength="35" onchange="trim(this)"  />
	                        <a href="javascript:goSearch()" class="search-btn"><img src="/img/sub/icon_zoom.gif" alt="검색" /></a>
	                    </div>
	                </div>
	                <!-- //search-box -->
	                <!-- board-type01 -->
	                <div class="board-type01 mt20">
	                    <div class="board-box">
	                        <table border="0" summary="국가연구개발사업 관련 주요제도 자료 리스트">
	                            <caption>자료 리스트</caption>
	                            <colgroup>
	                                <col width="7%" />
	                                <col width="*" />
	                                <col width="12%" />
	                                <col width="15%" />
	                            </colgroup>
	                            <thead>
	                                <tr>
	                                    <th scope="col">번호</th>
	                                    <th scope="col">제목</th>
	                                    <th scope="col">첨부파일</th>
	                                    <th scope="col">등록일</th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                            
                                    <logic:empty name="DataForm" property="voList">
                                        <tr>
                                            <td colspan="4">등록된 정보가 없습니다.</td>
                                        </tr>
                                    </logic:empty>
                                    <logic:notEmpty name="DataForm" property="voList">
                                        <logic:iterate name="DataForm" property="voList" indexId="rowNum" id="vo">
                                            <tr <%= rowNum.intValue() % 2 == 1 ? "class=\"on\"" : "" %>>
                                                <td><%=totRowCount.intValue() - rowNum.intValue() - Util.replaceNull((String) pagerOffset, 0)%></td>
                                                <td class="txt-l">
                                                    <bean:define name="vo" property="title" id="title" type="java.lang.String"/>
                                                    <%
                                                        String title_n = "";
                                                        int len = 36;
                                                        if ( title.length() > len ) {
                                                            title_n = title.substring(0,len) + "...";
                                                        } else {
                                                            title_n = title;
                                                        }
                                                    %>
                                                    <a href="JavaScript:dataDetailView('<bean:write name="vo" property="seq"/>')"><%=title_n %></a>
                                                </td>
                                                <td>
	                                                <logic:equal name="vo" property="file_id" value="N">
	                                                    <span class="btn-set save">첨부파일</span>
	                                                </logic:equal>
	                                                <logic:notEqual name="vo" property="file_id" value="N">
	                                                </logic:notEqual>
                                                </td>
                                                <td><bean:write name="vo" property="reg_dt" /></td>
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
                    <div class="btn-lst txt-r">
                        <span class="btn-set pink"><a href="/switch.do?prefix=/data&page=/Data.do?method=dataInsertForm&searchVO.menu_sn=02">자료실등록</a></span>
                    </div>
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
