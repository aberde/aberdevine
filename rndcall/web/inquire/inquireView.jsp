<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.inquire.vo.*" %>

<%@include file="/include/top.jsp"%>

    <bean:define name="InquireForm" property="searchVO" id="searchVO" type="kr.go.rndcall.mgnt.inquire.vo.InquireSearchVO"/>
    <bean:define name="InquireForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
    <bean:define name="InquireForm" property="voList" id="file_list" type="java.util.ArrayList"/>
    <bean:define name="InquireForm" property="voList2" id="cateL" type="java.util.ArrayList"/>

    <bean:define id="path" type="java.lang.String" value="/Inquire.do"/>

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
    <!--        
        //function goInquireForm(){
        //  fm.elements["method"].value="getInquireForm";
        //  fm.submit();
        //}
        
        function goInquireView(arg1, arg2){
            fm.elements["seq"].value=arg2;  
            fm.elements["board_type"].value=arg1;   
            fm.elements["method"].value="getInquireView";
            fm.submit();
        }
        
        function goSatiInsert(){
            if("<%=login_id%>" ==""){
                alert("로그인후 이용하실수 있습니다.");
                return;
            }else{
                fm.elements["method"].value="getSatiInsert";
                fm.submit();
            }
        }
        
        function downLoad(fileNM, saveFileNM, filePath, yn){
            fileDownLoad.elements["fileNM"].value = fileNM;
            fileDownLoad.elements["saveFileNM"].value = saveFileNM;
            fileDownLoad.elements["filePath"].value = filePath;
            fileDownLoad.elements["desCipher"].value = yn;
            fileDownLoad.submit();
        }
        
        function goCate(arg){
        <%
            if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")){
        %>  
                fm.elements["searchVO.start_yy"].value="";
                fm.elements["searchVO.start_mm"].value="";
                fm.elements["searchVO.end_yy"].value="";
                fm.elements["searchVO.end_mm"].value="";
        <%
            }
        %>
            fm.elements["searchVO.searchCategory"].value=arg;
            fm.elements["method"].value="getInquireList";
            fm.submit();
        }
        
        function goUpdate(){
            fm.elements["searchVO.searchCategory"].value="";
            fm.elements["method"].value="getInquireUpdateForm";
            fm.submit();
        }
        
        function goDelete(){
            if(confirm("정말로 삭제 하시겠습니까?")){
                fm.elements["searchVO.searchCategory"].value="";
                fm.elements["method"].value="getInquireDelete";
                fm.submit();
            }
        }
        
        function goAnswerInsert(){
        <%
            if(mainRoleCD.equals("0000Z")){
        %>      
                fm.elements["searchVO.searchCategory"].value="";
                fm.elements["method"].value="getAnswerInsertForm";
                fm.submit();
        <%      
            }else{
        %>
                if('<bean:write name="InquireForm" property="vo.stat"/>' == "N" && '<bean:write name="InquireForm" property="vo.public_trans_yn"/>' == "Y"){
                    alert("타기관 담당자에게 위임된 문의사항입니다.");
                }else{
                    fm.elements["searchVO.searchCategory"].value="";
                    fm.elements["method"].value="getAnswerInsertForm";
                    fm.submit();
                }
        <%
            }
        %>      
        }
        
        function goOrgTransForm(arg){
            var width = '430';
            var height = '250';
            var left = (screen.width - width)/2;
            var top = (screen.height - height)/2;
            var winNM = 'orgTransForm';
            var url = '/switch.do?prefix=/inquire&page=/Inquire.do?method=getOrgTransForm&searchVO.seq='+arg;  
            var windowFeatures = "width=" + width + ",height=" + height +
                ",status,resizable,scrollbars=N,left=" + left + ",top=" + top +
                ",screenX=" + left + ",screenY=" + top;
            window.open(url, winNM, windowFeatures);
        }
        
        function goPrint(arg1, arg2){
            window.open("/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireViewPop&searchVO.board_type=" + arg1 + "&searchVO.seq=" + arg2, "techpop","toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=800,height=800");
        }
        
        function goScrap(arg1, arg2){
            if("<%=login_id%>" ==""){
                alert("로그인후 이용하실수 있습니다.");
                return;
            }else{
                fm.elements["searchVO.board_type"].value=arg1;  
                fm.elements["searchVO.seq"].value=arg2; 
                fm.elements["method"].value="getInquireScrap";
                fm.submit();
            }
        }
    //-->
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
                <li class="on"><a href="JavaScript:goInquireMainList()">온라인상담</a></li>
                <li><a href="JavaScript:goFaq()">자주묻는질문</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href="JavaScript:goInquireMainList()">온라인상담</a></li>
                    <li class="on"><a href="JavaScript:goInquireMainList()">온라인상담</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>온라인상담</h3>
                    <!-- <p>R&amp;D 관련 규정 및 제도에 대해 궁금하신 사항에 담당자가 답변해 드립니다.기존 답변을 검색 후 질의해주세요.</p> -->
                </div>
	            <!--  explain-bx -->
	            <div class="explain-bx mt10">
	                <strong>국가연구개발사업 관련 공통법령 및 제도에 대해 궁금하신 사항에 답변해드립니다.</strong>
	                <p>*기존 질의응답을 검색 후 질의해주시기 바랍니다.</p>
	            </div>
	            <!--  //explain-bx -->
                
	            <html:form action="/Inquire" method="post" name="fm" type="kr.go.rndcall.mgnt.inquire.form.InquireForm">
	                <html:hidden name="InquireForm" property="method" value="getInquireInsert"/>
	                <html:hidden name="InquireForm" property="vo.cell_number"/>
	                <html:hidden name="InquireForm" property="vo.email"/>
	                <html:hidden name="InquireForm" property="searchVO.loginId"/>
	                <html:hidden name="InquireForm" property="searchVO.name"/>
	                <html:hidden name="InquireForm" property="searchVO.seq"/>
	                <html:hidden name="InquireForm" property="searchVO.board_type"/>
	                <html:hidden name="InquireForm" property="searchVO.searchCategory"/>
	                <html:hidden name="InquireForm" property="searchVO.whichSearch"/>
	                <html:hidden name="InquireForm" property="searchVO.searchTxt"/>             
	                <html:hidden name="InquireForm" property="searchVO.menu_sn"/>
	                <html:hidden name="InquireForm" property="searchVO.uni"/>
	                <%
	                    if ( mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C") ) {
	                %>
	                <html:hidden name="InquireForm" property="searchVO.start_yy"/>
	                <html:hidden name="InquireForm" property="searchVO.start_mm"/>
	                <html:hidden name="InquireForm" property="searchVO.end_yy"/>
	                <html:hidden name="InquireForm" property="searchVO.end_mm"/>
	                <%
	                    }
	                %>
	                
	                <!-- board-detail -->
	                <div class="board-detail mt10">
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
		                                <td><bean:write name="InquireForm" property="vo.title" filter="false"/></td>
		                            </tr>
		                            <tr>
		                                <th scope="row">등록일</th>
		                                <td><bean:write name="InquireForm" property="vo.reg_dt"/></td>
		                            </tr>
		                            <tr class="comment">
		                                <th scope="row">질의내용</th>
		                                <td>
		                                    <bean:write name="InquireForm" property="vo.contents" filter="false"/>
		                                       <br/><br/>
		                                       <logic:notEmpty name="InquireForm" property="voList">
		                                               첨부파일 : 
		                                           <logic:iterate name="InquireForm" property="voList" indexId="rowNum" id="attachVO">
		                                               <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
		                                           </logic:iterate>
		                                       </logic:notEmpty>
		                                </td>
		                            </tr>
		                            <tr class="comment">
		                                <th scope="row" class="txt-blue">답변내용</th>
		                                <td class="txt-blue">
		                                    <bean:write name="InquireForm" property="vo.answerContents" filter="false"/>
		                                       <br/><br/>
		                                       <logic:notEmpty name="InquireForm" property="voList1">
		                                               첨부파일 : 
		                                           <logic:iterate name="InquireForm" property="voList1" indexId="rowNum1" id="attachVO">
		                                               <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
		                                           </logic:iterate>
		                                       </logic:notEmpty>
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
	                        if ( mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C") ) {
	                    %>
	                    <logic:equal name="InquireForm" property="vo.stat" value="N">
	                    <span class="btn-set"><a href="JavaScript:goAnswerInsert()">답변</a></span>
	                    </logic:equal>
	                    <span class="btn-set"><a href="JavaScript:goUpdate()">수정</a></span>
	                    <%
	                        } else {
	                    %>
	                    <logic:equal name="InquireForm" property="vo.up_del_stat" value="Y">
	                    <span class="btn-set"><a href="JavaScript:goUpdate()">수정</a></span>
	                    </logic:equal>
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
	                    <span class="btn-set"><a href="JavaScript:goInquireList()">목록</a></span>
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

<%@include file="/include/bottom.jsp"%>