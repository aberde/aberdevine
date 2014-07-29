<%@page contentType="text/html; charset=utf-8"%>
<%@page import="kr.go.rndcall.mgnt.common.Util"%>

<%@include file="/include/top.jsp"%>

	<bean:define name="UniSearchForm" property="totRowCount" id="totRowCount" type="java.lang.Integer" />
	<bean:define name="UniSearchForm" property="totRowCount1" id="totRowCount1" type="java.lang.Integer" />
	<bean:define name="UniSearchForm" property="totRowCount2" id="totRowCount2" type="java.lang.Integer" />
	<bean:define name="UniSearchForm" property="totRowCount3" id="totRowCount3" type="java.lang.Integer" />
	<bean:define name="UniSearchForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String" />
	<bean:define name="UniSearchForm" property="maxPageItems" id="maxPageItems" type="java.lang.String" />
	<bean:define name="UniSearchForm" property="pagerOffset" id="pagerOffset" type="java.lang.String" />
	<bean:define name="UniSearchForm" property="searchVO.loginId" id="loginId" type="java.lang.String" />
	<bean:define name="UniSearchForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String" />
	<bean:define name="UniSearchForm" property="searchVO.board_type" id="board_type" type="java.lang.String" />

    <bean:define id="path" type="java.lang.String" value="/UniSearch.do" />
    
	<script type="text/javascript">
		activeDebug = true;  
		module = '/switch.do?prefix=/unisearch&method=uniSearch&page=';
		
		function detailUniSearch(uni, board_type, seq){
			if(board_type== "QNA"){
				document.location.href = "/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireView&searchVO.board_type=" + board_type + "&searchVO.seq=" + seq + "&searchVO.uni=" + uni + "&searchCategory=&searchVO.menu_sn=01";
			}else if(board_type == "FAQ"){
				document.location.href = "/switch.do?prefix=/faq&page=/Faq.do?method=faqDetailView&searchVO.board_type=" + board_type + "&searchVO.seq=" + seq + "&searchVO.uni=" + uni + "&searchCategory=&searchVO.menu_sn=01";
			}else if(board_type == "NOTICE"){
				document.location.href = "/switch.do?prefix=/notice&page=/Notice.do?method=noticeDetailView&searchVO.board_type=" + board_type + "&searchVO.seq=" + seq + "&searchVO.uni=" + uni + "&searchCategory=&searchVO.menu_sn=04";
			}else if(board_type == "DATA"){
				document.location.href = "/switch.do?prefix=/data&page=/Data.do?method=dataDetailView&searchVO.board_type=" + board_type + "&searchVO.seq=" + seq + "&searchVO.uni=" + uni + "&searchCategory=&searchVO.menu_sn=02";
			}else if(board_type == "INS"){
				document.location.href = "/switch.do?prefix=/data&page=/Data.do?method=dataDetailView&searchVO.board_type=" + board_type + "&searchVO.seq=" + seq + "&searchVO.uni=" + uni + "&searchCategory=&searchVO.menu_sn=02";
			}
		}
	</script>

    <!-- container -->
    <div id="container">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2 class="develop-lnb">통합검색</h2>
            </div>
            <ul class="lnb-lst">
                <li class="on"><a href="#none;">통합검색</a></li>
            </ul>
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li class="on"><a href="#none;">통합검색</a></li>
                </ul>
            </div>
            
            <html:form action="/UniSearch" method="post" name="fm" type="kr.go.rndcall.mgnt.unisearch.form.UniSearchForm">
			    <html:hidden name="UniSearchForm" property="method" value="uniSearch" />
			    <html:hidden name="UniSearchForm" property="searchVO.loginId" />
			    <html:hidden name="UniSearchForm" property="searchVO.name" />
			    <html:hidden name="UniSearchForm" property="searchVO.board_type" />
			    <html:hidden name="UniSearchForm" property="searchVO.seq" />
			    <html:hidden name="UniSearchForm" property="searchVO.type" />
			    <html:hidden name="UniSearchForm" property="searchVO.word" />
			    <html:hidden name="UniSearchForm" property="searchVO.menu_sn" value="08" />
			    
	            <!-- section -->
	            <div class="section">
	                <div class="search mt60">
	                    <!-- search-item -->
	                    <div class="search-item">
	                        <h3>Q&amp;A 검색</h3>
	                        <dl>
	                            <logic:empty name="UniSearchForm" property="voList">
		                            <dt class="clearfix">[Q&amp;A] 에 검색된 정보가 없습니다.</dt>
		                            <dd></dd>
		                        </logic:empty>
                                <logic:notEmpty name="UniSearchForm" property="voList">
		                            <logic:iterate name="UniSearchForm" property="voList" indexId="rowNum" id="vo">
			                            <dt class="clearfix"><strong><%=totRowCount.intValue() + rowNum.intValue() + Util.replaceNull((String) pagerOffset, 0)%>. [Q&amp;A] <a href="javascript:detailUniSearch('uni','<bean:write name="vo" property="board_type"/>','<bean:write name="vo" property="seq"/>')"><bean:write name="vo" property="title" filter="false" /></a>]</strong><span class="date"><bean:write name="vo" property="reg_dt" /></span></dt>
			                            <dd><a href="javascript:detailUniSearch('uni','<bean:write name="vo" property="board_type"/>','<bean:write name="vo" property="seq"/>')"><bean:write name="vo" property="contents" filter="false" /></a></dd>
			                        </logic:iterate>
		                        </logic:notEmpty>
	                        </dl>
	                    </div>
	                    <!-- //search-item -->
	                    <!-- search-item -->
	                    <div class="search-item">
	                        <h3>자주하는 질문 검색</h3>
	                        <dl>
                                <logic:empty name="UniSearchForm" property="voList1">
		                            <dt class="clearfix">[자주하는 질문] 에 검색된 정보가 없습니다.</dt>
		                            <dd></dd>
		                        </logic:empty>
		                        <logic:notEmpty name="UniSearchForm" property="voList1">
		                            <logic:iterate name="UniSearchForm" property="voList1" indexId="rowNum" id="vo">
		                                <dt class="clearfix"><strong><%=totRowCount.intValue() + rowNum.intValue() + Util.replaceNull((String) pagerOffset, 0)%>. [Q&amp;A] <a href="javascript:detailUniSearch('uni','<bean:write name="vo" property="board_type"/>','<bean:write name="vo" property="seq"/>')"><bean:write name="vo" property="title" filter="false" /></a>]</strong><span class="date"><bean:write name="vo" property="reg_dt" /></span></dt>
                                        <dd><a href="javascript:detailUniSearch('uni','<bean:write name="vo" property="board_type"/>','<bean:write name="vo" property="seq"/>')"><bean:write name="vo" property="contents" filter="false" /></a></dd>
                                    </logic:iterate>
                                </logic:notEmpty>
	                        </dl>
	                    </div>
	                    <!-- //search-item -->
	                    <!-- search-item -->
	                    <div class="search-item">
	                        <h3>공지사항 검색</h3>
	                        <dl>
	                            <logic:empty name="UniSearchForm" property="voList2">
		                            <dt class="clearfix">[공지사항 검색] 에 검색된 정보가 없습니다.</dt>
		                            <dd></dd>
	                            </logic:empty>
	                            <logic:notEmpty name="UniSearchForm" property="voList2">
	                                <logic:iterate name="UniSearchForm" property="voList2" indexId="rowNum"    id="vo">
	                                    <dt class="clearfix"><strong><%=totRowCount.intValue() + rowNum.intValue() + Util.replaceNull((String) pagerOffset, 0)%>. [Q&amp;A] <a href="javascript:detailUniSearch('uni','<bean:write name="vo" property="board_type"/>','<bean:write name="vo" property="seq"/>')"><bean:write name="vo" property="title" filter="false" /></a>]</strong><span class="date"><bean:write name="vo" property="reg_dt" /></span></dt>
                                        <dd><a href="javascript:detailUniSearch('uni','<bean:write name="vo" property="board_type"/>','<bean:write name="vo" property="seq"/>')"><bean:write name="vo" property="contents" filter="false" /></a></dd>
                                    </logic:iterate>
	                            </logic:notEmpty>
	                        </dl>
	                    </div>
	                    <!-- //search-item -->
	                    <!-- search-item -->
	                    <div class="search-item">
	                        <h3>자료실 검색</h3>
	                        <dl>
	                            <logic:empty name="UniSearchForm" property="voList3">
		                            <dt >[자료실 검색] 에 검색된 정보가 없습니다.</dt>
		                            <dd></dd>
	                            </logic:empty>
	                            <logic:notEmpty name="UniSearchForm" property="voList3">
	                                <logic:iterate name="UniSearchForm" property="voList3" indexId="rowNum" id="vo">
		                                <dt class="clearfix"><strong><%=totRowCount.intValue() + rowNum.intValue() + Util.replaceNull((String) pagerOffset, 0)%>. [Q&amp;A] <a href="javascript:detailUniSearch('uni','<bean:write name="vo" property="board_type"/>','<bean:write name="vo" property="seq"/>')"><bean:write name="vo" property="title" filter="false" /></a>]</strong><span class="date"><bean:write name="vo" property="reg_dt" /></span></dt>
	                                    <dd><a href="javascript:detailUniSearch('uni','<bean:write name="vo" property="board_type"/>','<bean:write name="vo" property="seq"/>')"><bean:write name="vo" property="contents" filter="false" /></a></dd>
                                    </logic:iterate>
	                            </logic:notEmpty>
	                        </dl>
	                    </div>
	                    <!-- //search-item -->
	                </div>
	            </div>
	            <!-- //section -->
	            
	        </html:form>
	        
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->

<%@include file="/include/bottom.jsp"%>
