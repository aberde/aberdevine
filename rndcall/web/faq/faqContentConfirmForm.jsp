<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

	<bean:define name="FaqForm" property="voList" id="file_list" type="java.util.ArrayList"/>
	<bean:define name="FaqForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String"/>
	<bean:define name="FaqForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
	<bean:define name="FaqForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
	<bean:define name="FaqForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
	<bean:define name="FaqForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
	<bean:define name="FaqForm" property="searchVO.loginId" id="loginId" type="java.lang.String"/>
	<bean:define name="FaqForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
	
	<bean:define id="path" type="java.lang.String" value="/Faq.do"/>

    <script type="text/javascript">
    <!--
        var data = {
            num : 1 // 위치순번
        };
        // 현재메뉴 위치.
        menuFocus(data);
    //-->
    </script>
    
    <script type="text/javascript" src="/js/file.js"></script>
<%  
    String size = "70"; // default size
    if ( !Util.isNull(request.getParameter("size")) ) {
    	size = request.getParameter("size");
    }
%>
<%
	if ( mainLoginVO == null || !mainIsLogin ) {	
%>
	<script type="text/javascript">
		alert('로그인이 필요한 메뉴입니다.');
		document.location.href = "/index.jsp";
	</script>
<%
	} else if ( !mainRoleCD.equals("0000C") && !mainRoleCD.equals("0000Z") ) {
%>
	<script type="text/javascript">
		alert('권한이 없습니다.');
		document.location.href = "/index.jsp";
	</script>
<%		
	}
%>
	<script type="text/javascript">
		function Save(){
		    var del_file_id = "";
			if(validate()){
                <%
                    if ( file_list.size() > 0 ) {
                %>
                if(document.fm.file_del.value == undefined){
                    for(i=0; i < fm.file_del.length; i++){
                        if(fm.file_del[i].checked){
                            del_file_id += fm.file_del[i].value+",";
                        }
                    }
                    del_file_id = del_file_id.substr(0,(del_file_id.length-1));
                }else{
                    if(fm.file_del.checked){
                        del_file_id = fm.file_del.value;
                    }
                }
                <%
                    }
                %>
                fm.elements["vo.del_file_id"].value=del_file_id;
				fm.elements["method"].value="faqUpdate";
				fm.submit();
			}
		}
		
		function fncFileAddLenChk(fileObjName, size){
		    var fileArea = document.getElementById(fileObjName);
		    var childNds = fileArea.childNodes;
		    
		    //alert(childNds.length);
		    if(childNds.length < 10) //최대 3개까지 첨부 가능
		        fncFileAdd(fileObjName, size);
		}
		
		function goCancel(){
			document.location.href="/index.jsp";
		}
		
		function validate() {
			//제목 필수 입력 체크
			if (isRequired(fm.elements["vo.title"])){
				return false;
			}
			//질의내용 필수 입력 체크
			if (isRequired(fm.elements["vo.contents"])){
				return false;
			}
			//답변 필수 입력 체크
			if (isRequired(fm.elements["vo.answerContents"])){
				return false;
			}
		 	return true;
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
                    <li><a href="JavaScript:goInquireMainList()">온라인상담</a></li>
                    <li class="on"><a href="JavaScript:goFaq()">자주묻는질문</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>자주묻는질문</h3>
                    <p>가장 많이 묻는 질의 응답을 카테고리별로 검색이 가능합니다.</p>
                </div>
                
                <html:form action="/Faq" method="post" name="fm" enctype="multipart/form-data" type="kr.go.rndcall.mgnt.faq.form.FaqForm" onsubmit="return checkOnSubmit(this)">
			        <html:hidden name="FaqForm" property="method" value="faqInsert"/>
			        <html:hidden name="FaqForm" property="vo.cell_number"/>
			        <html:hidden name="FaqForm" property="vo.email"/>
			        <html:hidden name="FaqForm" property="searchVO.loginId"/>
			        <html:hidden name="FaqForm" property="searchVO.name"/>
			        <html:hidden name="FaqForm" property="searchVO.seq"/>
			        <html:hidden name="FaqForm" property="searchVO.board_type"/>
			        <html:hidden name="FaqForm" property="searchVO.searchCategory"/>
			        <html:hidden name="FaqForm" property="searchVO.menu_sn"/>
			        <html:hidden name="FaqForm" property="vo.del_file_id"/>
			        <html:hidden name="FaqForm" property="vo.file_id"/>
                    
                    <!-- board-write -->
                    <div class="board-write mt30">
                        <div class="board-box">
                            <table summary="질의자 정보, 공개여부, 제목, 내용 보기 등록 페이지">
                                <caption>자주묻는질문 수정 페이지 </caption>
                                <colgroup>
                                    <col width="16%"/>
                                    <col width="*"/>
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="row"><label for="title">제목</label></th>
                                        <td>
                                            <html:text name="FaqForm" property="vo.title" style="width:98%" alt="제목" title="제목"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><label for="vo.contents">질의내용</label></th>
                                        <td>
                                            <html:textarea name="FaqForm" styleId="vo.contents" property="vo.contents" cols="0" rows="0" style="width:97%; min-height:254px;" alt="질의 내용" title="질의 내용"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><label for="vo.answerContents">답변내용</label></th>
                                        <td>
                                            <html:textarea name="FaqForm" styleId="vo.answerContents" property="vo.answerContents" cols="0" rows="0" style="width:97%; min-height:254px;" alt="질의 내용" title="질의 내용"/>
                                        </td>
                                    </tr>
                                    <logic:notEmpty name="FaqForm" property="voList">
                                    <tr>
                                        <th scope="row"><label for="vo.answerContents">파일 삭제여부</label></th>
                                        <td>
                                            <logic:iterate name="FaqForm" property="voList" indexId="rowNum" id="attachVO">                     
                                                <bean:define name="attachVO" property="file_id" id="file_id" type="java.lang.String"/>
                                                <bean:define name="attachVO" property="seq" id="seq" type="java.lang.String"/>
                                                <%
                                                    String f_id = file_id+"-"+seq;
                                                %>
                                                <input type="checkbox" name="file_del" value="<%=f_id%>"/><a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><img src="/images/icon/disk01.gif" alt="첨부파일"/><bean:write name="attachVO" property="file_nm" /></a><br/>
                                            </logic:iterate>
                                        </td>
                                    </tr>
                                    </logic:notEmpty>
                                    <tr>
                                        <th scope="row"><label for="file">첨부파일</label></th>
                                        <td>
                                            <span class="btn-set set2 black"><a href="javascript:fncFileAddLenChk('fileArea', '<%=size%>');">파일첨부</a></span>
                                            <span class="btn-set set2 black"><a href="javascript:fncFileDel('fileArea');">파일제거</a></span>
                                            <div id="fileArea"></div>
                                        </td>
                                    </tr>
                                </tbody>
                              </table>
                        </div>
                    </div>
                    <!-- // board-write -->
                    <!-- btn-set -->
                    <div class="btn-lst txt-r">
                        <span class="btn-set pink"><a href="JavaScript:Save()">저장</a></span>
                        <span class="btn-set"><a href="JavaScript:history.back()">취소</a></span>
                    </div>
                    <!-- //btn-set-->
                    
                </html:form>
                
            </div>
            <!-- //section -->  
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->

<%@include file="/include/bottom.jsp"%>