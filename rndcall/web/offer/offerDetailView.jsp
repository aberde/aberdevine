<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

	<bean:define name="OfferForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
	<bean:define name="OfferForm" property="searchVO.loginId" id="loginId" type="java.lang.String"/>
	<bean:define name="OfferForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String"/>
	<bean:define name="OfferForm" property="vo" id="vo" type="kr.go.rndcall.mgnt.offer.vo.OfferVO"/>
	<bean:define name="OfferForm" property="vo.stat" id="stat" type="java.lang.String"/>
	
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
		function goOfferList(){
			fm.action = "/switch.do?prefix=/offer&method=offerList&page=/Offer.do?pager.offset=" + fm.elements["searchVO.pagerOffset"].value;
			fm.submit();
		}
		
		function goSatiInsert(){
			if("<%=loginId%>" ==""){
				alert("로그인후 이용하실수 있습니다.");
				return;
			}else{
			fm.elements["method"].value="offerSatiInsert";
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
			fm.elements["searchVO.searchCategory"].value=arg;
			fm.elements["method"].value="getInquireList";
			fm.submit();
		}
		
		function goOfferUpdate(){
			fm.elements["searchVO.searchCategory"].value="";
			fm.elements["method"].value="offerUpdateForm";
			fm.submit();
		}
		
		function goDelete(){
			fm.elements["searchVO.searchCategory"].value="";
			fm.elements["method"].value="getInquireDelete";
			fm.submit();
		}
		
		function goOfferAnswerInsert(){
				fm.elements["searchVO.searchCategory"].value="";
				fm.elements["method"].value="offerAnswerInsertForm";
				fm.elements["searchVO.flag"].value="A";
				fm.submit();
		}
		function goOfferDelete(){
			if(confirm("정말로 삭제 하시겠습니까?")){
				fm.elements["searchVO.searchCategory"].value="";
				fm.elements["method"].value="offerDelete";
				fm.submit();
			}
		}
		
		function goPrint(arg1, arg2){
            window.open("/switch.do?prefix=/offer&page=/Offer.do?method=getOfferViewPop&searchVO.board_type=" + arg1 + "&searchVO.seq=" + arg2, "techpop","toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=800,height=800");
        }
		
		function goScrap(arg1, arg2){
            if("<%=login_id%>" ==""){
                alert("로그인후 이용하실수 있습니다.");
                return;
            }else{
                fm.elements["searchVO.board_type"].value=arg1;  
                fm.elements["searchVO.seq"].value=arg2; 
                fm.elements["method"].value="getInquireScrap";
                fm.action = "/inquire/Inquire.do";
                fm.submit();
            }
        }
		
		function goDocDownload() {
            fm.elements["method"].value="offerDetailViewDoc";
            fm.submit();
        }
	//-->
	</script>
	
	<!-- container -->
    <div id="container" class="sinmungo">
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
                <html:form action="/Offer" method="post" name="fm" type="kr.go.rndcall.mgnt.offer.form.OfferForm">
				    <html:hidden name="OfferForm" property="method" value="getInquireInsert"/>
				    <html:hidden name="OfferForm" property="vo.cell_number"/>
				    <html:hidden name="OfferForm" property="vo.email"/>
				    <html:hidden name="OfferForm" property="searchVO.loginId"/>
				    <html:hidden name="OfferForm" property="searchVO.name"/>
				    <html:hidden name="OfferForm" property="searchVO.seq"/>
				    <html:hidden name="OfferForm" property="searchVO.board_type"/>
				    <html:hidden name="OfferForm" property="searchVO.searchCategory"/>
				    <html:hidden name="OfferForm" property="searchVO.menu_sn"/>
				    <html:hidden name="OfferForm" property="searchVO.pagerOffset"/>
				    <html:hidden name="OfferForm" property="searchVO.flag"/>
				    
	                <!-- board-detail -->
	                <div class="board-detail mt60">
	                    <div class="board-box">
	                        <table summary="규정 및 제도에 대한 개선사항을 건의 페이지">
	                            <caption>R&amp;D 신문고 페이지</caption>
	                            <colgroup>
	                                <col width="16%"/>
	                                <col width="*"/>
	                            </colgroup>
	                            <tbody>
	                                <tr>
	                                    <th scope="row">아이디</th>
	                                    <td>
	                                        <logic:empty name="OfferForm" property="vo.reg_id">
	                                               비회원
	                                        </logic:empty>
	                                        <logic:notEmpty name="OfferForm" property="vo.reg_id">
		                                        <bean:write name="OfferForm" property="vo.reg_id" filter="false"/>
	                                        </logic:notEmpty>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row">제목</th>
	                                    <td><bean:write name="OfferForm" property="vo.title" filter="false"/></td>
	                                </tr>
	                                <tr>
	                                    <th scope="row">등록일</th>
	                                    <td><bean:write name="OfferForm" property="vo.reg_dt"/></td>
	                                </tr>
	                                <tr class="comment">
	                                    <th scope="row">질의내용</th>
	                                    <td>
	                                        <bean:write name="OfferForm" property="vo.contents" filter="false"/>
	                                        <br/><br/>
	                                        <logic:notEmpty name="OfferForm" property="voList">
	                                                첨부파일 : 
	                                            <logic:iterate name="OfferForm" property="voList" indexId="rowNum" id="attachVO">
	                                                <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
	                                            </logic:iterate>
	                                        </logic:notEmpty>
	                                    </td>
	                                </tr>
	                                <%
	                                    if ( stat.equals("Y") ) {
	                                %>
	                                <tr class="comment">
	                                    <th scope="row" class="txt-blue">답변내용</th>
	                                    <td class="txt-blue">
	                                        <bean:write name="OfferForm" property="vo.answerContents" filter="false"/>
	                                        <br/><br/>
	                                        <logic:notEmpty name="OfferForm" property="voList1">
	                                                첨부파일 : 
	                                            <logic:iterate name="OfferForm" property="voList1" indexId="rowNum" id="attachVO">
	                                                <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
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
	                <!-- //board-detail -->
	                <!-- btn-set -->
                    <div class="btn-lst txt-r">
                        <bean:define name="OfferForm" property="vo.up_del_stat" id="up_del_stat" type="java.lang.String"/>
                        <bean:define name="OfferForm" property="vo.stat" id="stat" type="java.lang.String"/>    
                        <span class="btn-set"><a href="JavaScript:goDocDownload()">파일저장</a></span>
                        <%
                            if ( roleCd.equals("0000Z") || roleCd.equals("0000C") ) {
                                if(stat.equals("N")){
                        %>
                        <span class="btn-set pink"><a href="JavaScript:goOfferAnswerInsert()">답변달기</a></span>
                        <span class="btn-set"><a href="JavaScript:goPrint('OFFER','<bean:write name="vo" property="seq"/>');">인쇄</a></span>
                        <%
                                }else{
                        %>
                        <span class="btn-set pink"><a href="JavaScript:goOfferAnswerInsert()">답변수정</a></span>
                        <span class="btn-set"><a href="JavaScript:goPrint('OFFER','<bean:write name="vo" property="seq"/>');">인쇄</a></span>
                        <span class="btn-set"><a href="JavaScript:goOfferDelete()">삭제</a></span>
                        <% 
                                }
                            }
                        %>
                        <%
                            if ( login_id != null && !login_id.isEmpty() && login_id.equals(vo.getReg_id()) ) {
                        %>
                        <span class="btn-set"><a href="JavaScript:goOfferUpdate()">질의수정</a></span>
                        <%
                            } else if ( vo.getReg_id() == null || vo.getReg_id().isEmpty() ) {
                        %>
                            <span class="btn-set"><a href="javascript:goPasswordCheckForm('<bean:write name="vo" property="board_type"/>',<bean:write name="vo" property="seq"/>, 'goOfferUpdate()')">질의수정</a></span>
                        <%
                            }
                        %>
                        <%
                            if ( mainLoginVO != null && mainIsLogin ) {
                        %>
                        <span class="btn-set"><a href="JavaScript:goScrap('OFFER','<bean:write name="vo" property="seq"/>');">스크랩</a></span>
                        <%
                            }
                        %>
                        <span class="btn-set"><a href="JavaScript:goOfferList()">목록</a></span>
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