<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

	<bean:define name="FaqForm" property="searchVO" id="searchVO" type="kr.go.rndcall.mgnt.faq.vo.FaqSearchVO"/>
	<bean:define name="FaqForm" property="searchVO.loginId" id="loginId" type="java.lang.String" />
	<bean:define name="FaqForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String" />

    <bean:define id="path" type="java.lang.String" value="/Faq.do"/>

<%
	String uni = searchVO.getUni();
%>
    <script type="text/javascript">
    <!--
        var data = {
            num : 1 // 위치순번
        };
        // 현재메뉴 위치.
        menuFocus(data);
    //-->
    </script>
    
	<script type="text/javascript">
		function goCancel(){
			document.location.href="/index.jsp";
		}
			
		function create(){
			fm.elements["method"].value="faqInsertForm";
			fm.submit();
		}
		
		function detail(elem, seq) {
			document.fm.elements["method"].value = "faqDetailView";
			document.fm.elements["searchVO.seq"].value = seq;
			document.fm.submit();
		}
		
		function faqAnswerInsert(){
			var aa = fm.elements["vo.answer_cont"].value
			var aa2 = fm.elements["vo.title"].value
			fm.elements["method"].value="faqAnswerInsert";
			fm.submit();
		}
		
		function goSatiInsert(){
			if("<%=login_id%>" ==""){
				alert("로그인후 이용하실수 있습니다.");
				return;
			}else{
				fm.elements["method"].value="faqSatiInsert";
				fm.submit();
			}
		}
		
		function goFaq(){
			//fm.elements["method"].value="faqList";
			fmgotoList.action = "/switch.do?prefix=/faq&method=faqList&page=/Faq.do?pager.offset=" + fm.elements["searchVO.pagerOffset"].value;
			fmgotoList.submit();
		}
		
		function faqContentConfirm(){
			fm.elements["searchVO.searchCategory"].value="";
			fm.elements["method"].value="faqContentConfirm";
			fm.submit();
		}
		
		function faqContentDelete(){
			fm.elements["searchVO.searchCategory"].value="";
			fm.elements["method"].value="faqDelete";
			fm.submit();
		}
		
		function downLoad(fileNM, saveFileNM, filePath, yn){
		    fileDownLoad.elements["fileNM"].value = fileNM;
		    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
		    fileDownLoad.elements["filePath"].value = filePath;
		    fileDownLoad.elements["desCipher"].value = yn;
		    fileDownLoad.submit();
		}
	</script>
	
    <!-- container -->
    <div id="container">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>온라인상담</h2>
                <span><img src="/img/common/h2_entxt02.gif" alt="Online Consultation" /></span>
            </div>
            <ul class="lnb-lst">
                <li><a href="JavaScript:goInquireMainList()">온라인상담</a></li>
                <li class="on"><a href="JavaScript:goFaq()">자주묻는질문</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href=JavaScript:goInquireMainList()>온라인상담</a></li>
                    <li class="on"><a href="JavaScript:goFaq()">자주묻는 질문</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>자주묻는 질문</h3>
                    <p>가장 많이 묻는 질의 응답을 카테고리별로 검색이 가능합니다.</p>
                </div>
                
                <html:form action="/Faq" method="post" name="fm" type="kr.go.rndcall.mgnt.faq.form.FaqForm" scope="request">
					<html:hidden name="FaqForm" property="method" value="faqInsert"/>
					<html:hidden name="FaqForm" property="vo.cell_number"/>
					<html:hidden name="FaqForm" property="vo.email"/>
					<html:hidden name="FaqForm" property="vo.title"/>
					<html:hidden name="FaqForm" property="searchVO.loginId"/>
					<html:hidden name="FaqForm" property="searchVO.name"/>
					<html:hidden name="FaqForm" property="searchVO.seq"/>
					<html:hidden name="FaqForm" property="searchVO.board_type"/>
					<html:hidden name="FaqForm" property="searchVO.searchCategory"/>
					<html:hidden name="FaqForm" property="searchVO.menu_sn"/>
					<html:hidden name="FaqForm" property="searchVO.uni"/>
					<html:hidden name="FaqForm" property="searchVO.pagerOffset"/>
            
	                <!-- board-detail -->
	                <div class="board-detail mt30">
	                    <div class="board-box">
	                        <table summary="제목, 등록일, 질의내용, 답변내용 페이지">
	                            <caption>온라인 상담 상세 페이지</caption>
	                            <colgroup>
	                                <col width="16%"/>
	                                <col width="*"/>
	                            </colgroup>
	                            <tbody>
	                                <tr>
	                                    <th scope="row">제목</th>
	                                    <td><bean:write name="FaqForm" property="vo.title" filter="false"/></td>
	                                </tr>
	                                <tr>
	                                    <th scope="row">분류</th>
	                                    <td>
	                                        <bean:write name="FaqForm" property="vo.category1"/>
                                            <logic:notEqual name="FaqForm" property="vo.category2" value="">
                                            - <bean:write name="FaqForm" property="vo.category2"/>
                                            </logic:notEqual>
                                            <logic:equal name="FaqForm" property="vo.analysis_yn" value="Y">
                                            &nbsp;(유권해석)
                                            </logic:equal>
	                                    </td>
	                                </tr>
	                                <tr class="comment">
	                                    <th scope="row">질의내용</th>
	                                    <td>
	                                        <bean:write name="FaqForm" property="vo.contents" filter="false"/>
                                            <br/><br/>
                                            <logic:notEmpty name="FaqForm" property="voList1">
                                                    첨부파일 :
                                                <logic:iterate name="FaqForm" property="voList1" indexId="rowNum" id="attachVO">
                                                    <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');" ><bean:write name="attachVO" property="file_nm" /></a><br/>
                                                </logic:iterate>
                                            </logic:notEmpty>
	                                    </td>
	                                </tr>
	                                <tr class="comment">
	                                    <th scope="row" class="txt-blue">답변내용</th>
	                                    <td class="txt-blue">
	                                        <bean:write name="FaqForm" property="vo.answer_cont" filter="false"/>
	                                    </td>
	                                </tr>
	                            </tbody>
	                         </table>
	                    </div>
	                </div>      
	                <!-- //board-detail -->
	                <!-- btn-set-->
	                <div class="btn-lst txt-r">
	                    <%
	                        if ( roleCd.equals("0000Z") || roleCd.equals("0000C") ) {
                        %>
	                    <span class="btn-set"><a href="javascript:faqContentConfirm();">수정</a></span>
	                    <span class="btn-set"><a href="JavaScript:faqContentDelete()">삭제</a></span>
	                    <% 
	                        }
                        %>
	                    <%
	                        if ( uni.equals("uni") ) {
                        %>
	                    <span class="btn-set"><a href="JavaScript:history.back()">목록</a></span>
	                    <%
	                        } else {
                        %>
	                    <span class="btn-set"><a href="JavaScript:goFaq()">목록</a></span>
	                    <%
	                        }
                        %>
	                </div>
	                <!-- //btn-set-->
	                
	            </html:form>
	            
            </div>
            <!-- //section -->  
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->

	<form name="fileDownLoad" method="post" action="/downLoad.do" >
	    <input type="hidden" name="fileNM"/>
	    <input type="hidden" name="saveFileNM"/>
	    <input type="hidden" name="filePath"/>
	    <input type="hidden" name="desCipher" value="N"/>
	</form>
	
	<html:form action="/Faq" method="post" name="fmgotoList" type="kr.go.rndcall.mgnt.faq.form.FaqForm" scope="request">
	    <html:hidden name="FaqForm" property="method" value="faqInsert"/>
	    <html:hidden name="FaqForm" property="vo.cell_number"/>
	    <html:hidden name="FaqForm" property="vo.email"/>
	    <html:hidden name="FaqForm" property="vo.title"/>
	    <html:hidden name="FaqForm" property="searchVO.loginId"/>
	    <html:hidden name="FaqForm" property="searchVO.name"/>
	    <html:hidden name="FaqForm" property="searchVO.seq"/>
	    <html:hidden name="FaqForm" property="searchVO.board_type"/>
	    <html:hidden name="FaqForm" property="searchVO.searchCategory"/>
	    <html:hidden name="FaqForm" property="searchVO.menu_sn"/>
	    <html:hidden name="FaqForm" property="searchVO.uni"/>
	    <html:hidden name="FaqForm" property="pagerOffset"/>
	    <html:hidden name="FaqForm" property="searchVO.pagerOffset"/>
	    <html:hidden name="FaqForm" property="searchVO.searchTxt"/>
    </html:form>

<%@include file="/include/bottom.jsp"%>