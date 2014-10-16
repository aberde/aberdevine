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
<bean:define name="StatisticForm" property="searchVO.crud" id="crud" type="java.lang.String"/>
<bean:define name="StatisticForm" property="vo1.stat" id="stat" type="java.lang.String"/>
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
		


function goStatList(){
<%
	if(crud.equals("date")){
%>
		fm.elements["method"].value="getStatDataList";
<%
	}else{
%>
		fm.elements["method"].value="getStatList";
<%
	}
%>
	fm.submit();
}

function downLoad(fileNM, saveFileNM, filePath, yn){
    fileDownLoad.elements["fileNM"].value = fileNM;
    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
    fileDownLoad.elements["filePath"].value = filePath;
    fileDownLoad.elements["desCipher"].value = yn;
    fileDownLoad.submit();
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

<form name="fileDownLoad" method="post" action="/downLoad.do" >
    <input type="hidden" name="fileNM"/>
    <input type="hidden" name="saveFileNM"/>
    <input type="hidden" name="filePath"/>
	<input type="hidden" name="desCipher" value="N"/>
</form>

    <!-- pop -->
    <div id="pop-detail" >
        <div class="tit">
            <strong>통계정보 상세 화면</strong>
        </div>
        
        <html:form action="/Statistic" method="post" name="fm" type="kr.go.rndcall.mgnt.statistic.form.StatisticForm">
	        <html:hidden name="StatisticForm" property="method" value="getStatList"/>
	        <html:hidden name="StatisticForm" property="searchVO.category1"/>
	        <html:hidden name="StatisticForm" property="searchVO.category2"/>
	        <html:hidden name="StatisticForm" property="searchVO.whichSearch"/>     
	        <html:hidden name="StatisticForm" property="searchVO.whichSearch1"/>    
	        <html:hidden name="StatisticForm" property="searchVO.searchTxt"/>   
	        <html:hidden name="StatisticForm" property="searchVO.searchTxt1"/>
	        <html:hidden name="StatisticForm" property="searchVO.start_yy"/>        
	        <html:hidden name="StatisticForm" property="searchVO.start_mm"/>    
	        <html:hidden name="StatisticForm" property="searchVO.end_mm"/>
	        <html:hidden name="StatisticForm" property="searchVO.crud"/>
	        <html:hidden name="StatisticForm" property="searchVO.board_type"/>
        
	        <!-- board-view -->
	        <div class="board-detail">
	            <div class="board-box">
	                <table summary="통계정보 상세화면">
	                    <caption>통계정보 상세화면</caption>
	                    <colgroup>
	                        <col width="20%"/>
	                        <col width="*"/>
	                    </colgroup>
	                    <tbody>
	                        <tr>
	                            <th scope="row"><label for="id">제목</label></th>
	                            <td><strong>[<bean:write name="StatisticForm" property="vo1.category1_nm"/>]</strong> <bean:write name="StatisticForm" property="vo1.title"/></td>
	                        </tr>
	                        <tr>
	                            <th scope="row"><label for="id">등록일</label></th>
	                            <td><bean:write name="StatisticForm" property="vo1.reg_dt"/></td>
	                        </tr>
	                        <tr>
	                            <td colspan="2" class="bdl-n"><bean:write name="StatisticForm" property="vo1.contents" filter="false"/></td>
	                        </tr>
	                        <tr>
	                            <th scope="row"><label for="id">첨부파일</label></th>
	                            <td>
	                                <logic:notEmpty name="StatisticForm" property="voList">
	                                    <logic:iterate name="StatisticForm" property="voList" indexId="rowNum" id="attachVO">
	                                        <bean:write name="attachVO" property="file_nm" /><span class="btn-set set2 s-blue"><a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');" class="btn_TFileDw">파일 다운로드</a></span><br/>
	                                    </logic:iterate>
	                                </logic:notEmpty>
	                            </td>
	                        </tr>
	                        <%
	                            if(stat.equals("Y")){
							%>
	                        <tr>
	                            <td colspan="2" class="txt-l bdl-n" >
	                                <span class="btn-set set2 yellow"><a href="#">답변</a></span>
	                                <bean:write name="StatisticForm" property="vo1.answerContents" filter="false"/>
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row"><label for="id">첨부파일</label></th>
	                            <td>
	                                <logic:notEmpty name="StatisticForm" property="voList1">
				                        <logic:iterate name="StatisticForm" property="voList1" indexId="rowNum1" id="attachVO">
				                            <bean:write name="attachVO" property="file_nm" /><span class="btn-set set2 s-blue"><a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');" class="btn_TFileDw">파일 다운로드</a></span><br/>
				                        </logic:iterate>
				                    </logic:notEmpty>
	                            </td>
	                        </tr>
	                        <%
	                            }
	                        %>
	                    </tbody>
	                 </table>
	            </div>
	        </div>
	        <!-- // board-write -->

        </html:form>
        
        <!-- btn-set-->
        <div class="btn-lst txt-r">
            <span class="btn-set "><a href="JavaScript:goStatList()">목록</a></span>
        </div>
        <!-- //btn-set-->   

        <a href="#" class="btn-close" onclick="window.close();"><span >닫기</span></a>
    </div>
    <!-- // pop -->

</body>	
</html>