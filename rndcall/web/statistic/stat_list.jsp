<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@ taglib uri="/tags/taglibs-log" prefix="log"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>

<bean:define name="StatisticForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
<bean:define name="StatisticForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
<bean:define name="StatisticForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
<bean:define name="StatisticForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="StatisticForm" property="searchVO.loginId" id="loginId" type="java.lang.String"/>
<bean:define name="StatisticForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String"/>
<bean:define id="path" type="java.lang.String" value="/Statistic.do"/>

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

<script language="JavaScript" src="/js/validate.js"></script>
<script language="JavaScript" src="/js/common.js"></script>
<script type="text/javascript">
<!--
activeDebug = true;  
module = '/switch.do?prefix=/statistic&method=getStatList&page=';
		
function goStatisticForm(){
	fm.elements["method"].value="getStatisticForm";
	fm.submit();
}

function goInquireMainList(){
	fm.elements["method"].value="getInquireMainList";
	fm.submit();
}

function goCate(arg){
<%
	if(roleCd.equals("0000Z") || roleCd.equals("0000C")){
%>	
		fm.elements["searchVO.start_yy"].value="";
		fm.elements["searchVO.start_mm"].value="";
		fm.elements["searchVO.end_yy"].value="";
		fm.elements["searchVO.end_mm"].value="";
<%
	}
%>
	
	fm.elements["searchVO.searchCategory"].value=arg;
	fm.elements["searchVO.type"].value="";
	fm.elements["method"].value="getInquireList";
	fm.submit();
}

function goInquireView(arg1, arg2){
	fm.elements["searchVO.seq"].value=arg2;	
	fm.elements["searchVO.board_type"].value=arg1;	
	fm.elements["method"].value="getInquireView";
	fm.submit();
}

function goStatList(arg1, arg2){
	fm.elements["searchVO.board_type"].value=arg1;
	fm.elements["searchVO.type"].value=arg2;
	fm.elements["searchVO.searchCategory"].value="";
	fm.elements["method"].value="getInquireList";
	fm.submit();
}

function goStatList1(arg1, arg2){
	var url ="/switch.do?prefix=&page=/Offer.do?method=offerList&searchVO.board_type="+arg1+"&searchVO.type="+arg2;
	document.location.href = url;
}

function goSearch(){
	fm.elements["method"].value="getStatList";
	fm.submit();
}


function goExcelDown(){
	fm.elements["method"].value="getStatListExcel";
	fm.submit();
}

function goStatView(arg){

	fm.elements["searchVO.seq"].value=arg;
	fm.elements["method"].value="getStatView";
	fm.submit();
}
//-->
</script>
</head>
<!--[if lt IE 7]>  <body class="no-js ie ie6 lte9 lte8 lte7"> <![endif]-->
<!--[if IE 7]>     <body class="no-js ie ie7 lte9 lte8 lte7"> <![endif]-->
<!--[if IE 8]>     <body class="no-js ie ie8 lte9 lte8"> <![endif]-->
<!--[if IE 9]>     <body class="no-js ie ie9 lte9"> <![endif]-->
<!--[if gt IE 9]>  <body class="no-js">  <![endif]-->
<!--[if !IE]><!--> <body> <!--<![endif]-->

    <!-- pop -->
    <div id="pop-detail" >
        <div class="tit">
            <strong>통계정보 상세리스트</strong>
        </div>
        
        <html:form action="/Statistic" method="post" name="fm" type="kr.go.rndcall.mgnt.statistic.form.StatisticForm">
	        <html:hidden name="StatisticForm" property="method" value="getStatList"/>
	        <html:hidden name="StatisticForm" property="searchVO.category1"/>
	        <html:hidden name="StatisticForm" property="searchVO.category2"/>
	        <html:hidden name="StatisticForm" property="searchVO.whichSearch"/>
	        <html:hidden name="StatisticForm" property="searchVO.seq"/>
	        <html:hidden name="StatisticForm" property="searchVO.start_yy"/>        
	        <html:hidden name="StatisticForm" property="searchVO.start_mm"/>    
	        <html:hidden name="StatisticForm" property="searchVO.end_mm"/>
	        <html:hidden name="StatisticForm" property="searchVO.crud"/>
	        <html:hidden name="StatisticForm" property="searchVO.board_type"/>
	        
	        <!-- search-box -->
	        <div class="search-box" style="margin:0;">
	            <div class="search-form">
	                <html:select name="StatisticForm" property="searchVO.whichSearch1" title="검색분류" style="width:90px;">
	                    <html:option value="all">전체</html:option>
	                    <html:option value="title">제목</html:option>
	                    <html:option value="contents">내용</html:option>
                    </html:select>
	                <html:text name="StatisticForm" property="searchVO.searchTxt" title="검색어" size="40"/>
	                <a href="javascript:goSearch();" class="search-btn"><img src="/img/sub/icon_zoom.gif" alt="검색" border="0" /></a>
	                <span class="btn-set set2 navy search-excel"><a href="javascript:goExcelDown();">엑셀다운로드</a></span>
	            </div>
	        </div>
	        <!-- //search-box -->
	        <!-- board-type01 -->
	        <div class="board-type01 mt20">
	            <div class="board-box">
	                <table border="0" summary="작성자, 제목, 이메일, 내용 쓰기페이지">
	                    <caption>쓰기 페이지</caption>
	                    <colgroup>
	                        <col width="7%" />
	                        <col width="12%" />
	                        <col width="*" />
	                        <col width="8%" />
	                        <col width="12%" />
	                        <col width="12%" />
	                        <col width="10%" />
	                    </colgroup>
	                    <thead>
	                        <tr>
	                            <th scope="col">번호</th>
	                            <th scope="col">분류</th>
	                            <th scope="col">제목</th>
	                            <th scope="col">글쓴이</th>
	                            <th scope="col">등록일</th>
	                            <th scope="col">상태</th>
	                            <th scope="col">조회수</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <logic:empty name="StatisticForm" property="voList">
                                <tr><td style="border-right:0px;text-align:center; height=:50px;" colspan="99">등록된 정보가 없습니다.</td></tr>
                            </logic:empty>
                            <logic:notEmpty name="StatisticForm" property="voList">
                                <logic:iterate name="StatisticForm" property="voList" indexId="rowNum" id="vo">
                                    <tr>
                                        <td><%= totRowCount.intValue() - rowNum.intValue() -  Util.replaceNull((String)pagerOffset, 0) %></td>
                                        <td><bean:write name="vo" property="category1_nm"/></td>
                                        <td class="txt-l">
                                            <bean:define name="vo" property="title" id="title" type="java.lang.String"/>
                                            <%
					                            String title_n= "";
					                            if(title.length() > 16){
					                                title_n = title.substring(0,16)+"...";
					                            }else{
					                                title_n = title;
					                            }
                                            %>
                                            <a href="JavaScript:goStatView('<bean:write name="vo" property="seq"/>');"><%=title_n %></a>
                                        </td>
			                            <td><bean:write name="vo" property="reg_nm"/></td>
			                            <td><bean:write name="vo" property="reg_dt"/></td>
			                            <td>
                                            <bean:define name="vo" property="stat" id="stat" type="java.lang.String"/>
                                            <%
                                                if ( stat.equals("Y") ) {
                                                    out.print("<span class=\"btn-set set4 green\">답변완료</span>");
                                                } else if ( stat.equals("S") ) {
                                                    out.print("<span class=\"btn-set set4 yellow\">검토중</span>");
                                                } else {
                                                    out.print("<span class=\"btn-set set4 gray\">접수중</span>");
                                                }
                                            %>
                                        </td>
                                        <td><bean:write name="vo" property="read_count"/></td>
                                    </tr>
                                </logic:iterate>
                            </logic:notEmpty>
	                    </tbody>
	                </table>
	            </div>
	        </div>
	        <!-- // board-type01 -->
	        <!-- page-area-->
	        <%@ include file="/include/page.jsp"%>
	        <!-- //page-area --> 
        
        </html:form>

        <a href="#" class="btn-close" onclick="window.close();"><span >닫기</span></a>
    </div>
    <!-- // pop -->

</body>	
</html>