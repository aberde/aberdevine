<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

	<bean:define name="NoticeForm" property="searchVO" id="searchVO" type="kr.go.rndcall.mgnt.notice.vo.NoticeSearchVO"/>
	<bean:define name="NoticeForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
	<bean:define name="NoticeForm" property="searchVO.loginId" id="loginId" type="java.lang.String"/>
	<bean:define name="NoticeForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String"/>
	
	<bean:define id="path" type="java.lang.String" value="/Notice.do"/>
<%
	String uni = searchVO.getUni();
%>
	<script type="text/javascript">
	<!--		
		function goNoticeList(){
			fm.action = "/switch.do?prefix=/notice&method=noticeList&page=/Notice.do?pager.offset=" + fm.elements["searchVO.pagerOffset"].value;
			fm.submit();
		}
		
		function downLoad(fileNM, saveFileNM, filePath, yn){
		    fileDownLoad.elements["fileNM"].value = fileNM;
		    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
		    fileDownLoad.elements["filePath"].value = filePath;
		    fileDownLoad.elements["desCipher"].value = yn;
		    fileDownLoad.submit();
		}
		
		
		function goNoticeUpdateForm(){
			fm.elements["method"].value="noticeUpdateForm";
			fm.submit();
		}
		function goNoticeDelete(){
			if (confirm("정말로 삭제 하시겠습니까?")) {
				fm.elements["method"].value="noticeDelete";
				fm.submit();
			}
		}
	//-->
	</script>

	<!-- container -->
    <div id="container" class="notice">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>새소식</h2>
                <span><img src="/img/common/h2_entxt04.gif" alt="새소식" /></span>
            </div>
            <ul class="lnb-lst">
                <li class="on"><a href="JavaScript:goNotice()">새소식</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href="JavaScript:goNotice()">새소식</a></li>
                    <li class="on"><a href="JavaScript:goNotice()">새소식</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <html:form action="/Notice" method="post" name="fm" type="kr.go.rndcall.mgnt.notice.form.NoticeForm">
				    <html:hidden name="NoticeForm" property="method" value="getInquireInsert"/>
				    <html:hidden name="NoticeForm" property="searchVO.loginId"/>
				    <html:hidden name="NoticeForm" property="searchVO.name"/>
				    <html:hidden name="NoticeForm" property="searchVO.seq"/>
				    <html:hidden name="NoticeForm" property="searchVO.board_type"/>
				    <html:hidden name="NoticeForm" property="searchVO.menu_sn"/>
				    <html:hidden name="NoticeForm" property="searchVO.uni"/>
				    <html:hidden name="NoticeForm" property="searchVO.pagerOffset"/>
            
                    <!-- board-detail -->
                    <div class="board-detail mt30">
                        <div class="board-box">
                            <table summary="제목, 등록일, 질의내용, 답변내용 페이지">
                                <caption>알림 상세 페이지</caption>
                                <colgroup>
                                    <col width="16%"/>
                                    <col width="*"/>
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="row">제목</th>
                                        <td><bean:write name="NoticeForm" property="vo.title" filter="false"/></td>
                                    </tr>
<!--                                     <tr> -->
<!--                                         <th scope="row">작성자</th> -->
<%--                                         <td><bean:write name="NoticeForm" property="vo.reg_nm"/></td> --%>
<!--                                     </tr> -->
                                    <tr class="comment">
                                        <th scope="row">내용</th>
                                        <td>
                                            <bean:write name="NoticeForm" property="vo.contents" filter="false"/>
                                            <br/><br/>
                                            <logic:notEmpty name="NoticeForm" property="voList">
                                                    첨부파일 : 
                                                <logic:iterate name="NoticeForm" property="voList" indexId="rowNum" id="attachVO">
                                                    <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');" ><bean:write name="attachVO" property="file_nm" /></a><br/>
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
                            if ( roleCd.equals("0000Z") || roleCd.equals("0000C") ) {
                        %>
                        <span class="btn-set"><a href="JavaScript:goNoticeUpdateForm()">수정</a></span>
                        <span class="btn-set"><a href="JavaScript:goNoticeDelete()">삭제</a></span>
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
                        <span class="btn-set"><a href="JavaScript:goNoticeList()">목록</a></span>
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