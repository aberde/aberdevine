<%@page contentType="text/html; charset=utf-8" %>
<%@include file="/include/top.jsp"%>

	<bean:define name="MypageForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
	<bean:define name="MypageForm" property="vo.public_trans_yn" id="public_trans_yn" type="java.lang.String"/>
	<bean:define name="MypageForm" property="vo.stat" id="stat" type="java.lang.String"/>

    <bean:define id="path" type="java.lang.String" value="/Mypage.do"/>

	<script type="text/javascript">
	<!--		
		function goList(){
			fm.elements["method"].value="getMypageList";
			fm.submit();
		}
		
		function downLoad(fileNM, saveFileNM, filePath, yn){
		    fileDownLoad.elements["fileNM"].value = fileNM;
		    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
		    fileDownLoad.elements["filePath"].value = filePath;
		    fileDownLoad.elements["desCipher"].value = yn;
		    fileDownLoad.submit();
		}
		
		function goUpdate(){
			fm.elements["method"].value="getMypageUpdateForm";
			fm.submit();
		}
		
		function goScrapDel(arg1){
			if(confirm("스크랩 내용을 삭제하시겠습니까?")){
				fm.elements["searchVO.scrap_id"].value=arg1;	
				fm.elements["method"].value="getMypageScrapDelete";
				fm.submit();
			}
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
	//-->
	</script>
	
	<!-- container -->
    <div id="container">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>마이페이지</h2>
                <span><img src="/img/common/h2_mypage.gif" alt="Online Consultation" /></span>
            </div>
            <ul class="lnb-lst">
                <li class="on"><a href="JavaScript:goMypage()">마이페이지</a></li>
                <li><a href="javascript:goUserUpdate();">내정보</a></li>
            </ul>
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <!-- <li><a href="#none;">마이페이지</a></li> -->
                    <li class="on"><a href="JavaScript:goMypage()">마이페이지</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section mt60">
                <html:form action="/Mypage" method="post" name="fm" type="kr.go.rndcall.mgnt.mypage.form.MypageForm">
			        <html:hidden name="MypageForm" property="method" value="getMypageView"/>
			        <html:hidden name="MypageForm" property="searchVO.loginId"/>
			        <html:hidden name="MypageForm" property="searchVO.name"/>
			        <html:hidden name="MypageForm" property="searchVO.seq"/>
			        <html:hidden name="MypageForm" property="searchVO.board_type"/>
			        <html:hidden name="MypageForm" property="searchVO.type"/>
			        <html:hidden name="MypageForm" property="searchVO.menu_sn"/>
			        <html:hidden name="MypageForm" property="searchVO.scrap_id"/>
        
                    <!-- board-detail -->
                    <div class="board-detail mt30">
                        <div class="board-box">
                            <table summary="마이페이지">
                                <caption>마이페이지</caption>
                                <colgroup>
                                    <col width="16%"/>
                                    <col width="*"/>
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="row">스크랩 일시</th>
                                        <td>
                                            <bean:write name="MypageForm" property="vo.regdt_scrap"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row">제목</th>
                                        <td>
                                            <bean:define name="MypageForm" property="vo.board_type" id="board_type" type="java.lang.String"/>
                                            <%
                                                if ( board_type.equals("QNA") ) {
                                            %>
                                            [문의]
                                            <%
                                                } else if ( board_type.equals("OFFER") ) {
                                            %>
                                            [기타]
                                            <%
                                                }
                                            %>
                                            <bean:write name="MypageForm" property="vo.title" filter="false"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row">등록일</th>
                                        <td><bean:write name="MypageForm" property="vo.reg_dt"/></td>
                                    </tr>
                                    <tr class="comment">
                                        <th scope="row">질의내용</th>
                                        <td>
                                            <p>
                                                <bean:write name="MypageForm" property="vo.contents" filter="false"/>
                                                <br/><br/>
                                                <logic:notEmpty name="MypageForm" property="voList">
                                                            첨부파일 : 
                                                    <logic:iterate name="MypageForm" property="voList" indexId="rowNum" id="attachVO">
                                                        <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
                                                    </logic:iterate>
                                                </logic:notEmpty>
                                            </p>
                                        </td>
                                    </tr>
                                    <%
                                        if ( stat.equals("Y") ) {
                                    %>
                                    <tr class="comment">
                                        <th scope="row" class="txt-blue">답변내용</th>
                                        <td class="txt-blue">
                                            <p>
                                                <bean:write name="MypageForm" property="vo.answerContents" filter="false"/>
                                                <br/><br/>
                                                <logic:notEmpty name="MypageForm" property="voList1">
                                                            첨부파일 : 
                                                    <logic:iterate name="MypageForm" property="voList1" indexId="rowNum1" id="attachVO1">
                                                        <a href="javascript:downLoad('<bean:write name="attachVO1" property="file_nm"/>', '<bean:write name="attachVO1" property="file_path"/>', '', 'Y');"><bean:write name="attachVO1" property="file_nm" /></a><br/>
                                                    </logic:iterate>
                                                </logic:notEmpty>
                                            </p>
                                        </td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                             </table>
                        </div>
                    </div>      
                    <!-- //board-detail -->
                    <!-- btn-set-->
                    <div class="btn-lst txt-r">
                        <span class="btn-set"><a href="JavaScript:JavaScript:goScrapDel('<bean:write name="MypageForm" property="vo.scrap_id"/>')">스크랩삭제</a></span>
                        <span class="btn-set"><a href="JavaScript:JavaScript:goList()')">목록</a></span>
                        
                        <bean:define name="MypageForm" property="vo.up_del_stat" id="up_del_stat" type="java.lang.String"/>
                        <bean:define name="MypageForm" property="vo.stat" id="stat" type="java.lang.String"/>
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