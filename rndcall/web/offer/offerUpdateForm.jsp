<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

    <bean:define id="path" type="java.lang.String" value="/Offer.do"/>
    
    <script type="text/javascript" src="/js/file.js"></script>
    
<%  
    String size = "70"; // default size
    if(!Util.isNull(request.getParameter("size"))) {
    	size = request.getParameter("size");
    }		
%>
<%
	if (mainLoginVO == null || !mainIsLogin) {	
%>
	<script type="text/javascript">
		alert('로그인이 필요한 메뉴입니다.');
		document.location.href = "/index.jsp";
	</script>
<%
	}
%>
	<script type="text/javascript">
	<!--		
		function offerUpdate(){
			if(validate()){
				fm.elements["method"].value="offerUpdate";
				fm.elements["vo.file_id"].value = fm.elements["vo.question_file_id"].value;
				fm.submit();
			}
		}
		function fncFileAddLenChk(fileObjName, size){
		   
		    var fileArea = document.getElementById(fileObjName);
		    var childNds = fileArea.childNodes;
		    
		    //alert(childNds.length);
		    //최대 3개까지 첨부 가능
		    if(childNds.length < 3){
		        fncFileAdd(fileObjName, size);
		    }else{
		    	alert("첨부파일은 3개까지 가능합니다.");
		    	return;
		    }
		}
		function downLoad(fileNM, saveFileNM, filePath, yn){
		    fileDownLoad.elements["fileNM"].value = fileNM;
		    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
		    fileDownLoad.elements["filePath"].value = filePath;
		    fileDownLoad.elements["desCipher"].value = yn;
		    fileDownLoad.submit();
		}
		
		function offerUpdateCancle(){
			document.location.href="/index.jsp";
		}
		
		function putEmailHost(){
			if(fm.elements["putEmail"].value != ""){		
				fm.elements["email1"].focus();
				fm.elements["email2"].readonly=true;
				fm.elements["email2"].value = fm.elements["putEmail"].value;
			}else{
				fm.elements["email1"].focus();
				fm.elements["email2"].value = "";
				fm.elements["email2"].readonly=false;
			}
		}
		
		function validate() {
// 			//SMS수신 체크시 핸드폰번호 체크
// 			var sms="";		
// 			for(i=0; i < fm.elements["vo.call_receive_yn"].length; i++){
// 	     		if(fm.elements["vo.call_receive_yn"][i].checked){
// 	     			sms = fm.elements["vo.call_receive_yn"][i].value;
// 	    		}
// 	   		}
			
// 			if(sms == "Y"){
// 				fm.elements["vo.cell_number"].value = fm.elements["cell_no1"].value+ "-" +fm.elements["cell_no2"].value+ "-" +fm.elements["cell_no3"].value;
// 				if (!isNotValidTel(fm.elements["vo.cell_number"])) return false;
// 			}		
			
// 			//EMAIL수신 체크시 EMAIL 체크
// 			var email="";		
// 			for(i=0; i < fm.elements["vo.email_receive_yn"].length; i++){
// 	     		if(fm.elements["vo.email_receive_yn"][i].checked){
// 	     			email = fm.elements["vo.email_receive_yn"][i].value;
// 	    		}
// 	   		}
	   		
// 	   		if(email == "Y"){
// 				fm.elements["vo.email"].value = fm.elements["email1"].value + "@" + fm.elements["email2"].value;
// 				if(!isEMailAddr(fm.elements["vo.email"])){
// 					return false;
// 				}
// 			}
			
			
			//제목 필수 입력 체크
			if (isRequired(fm.elements["vo.title"])){
				return false;
			}
			//질의내용 필수 입력 체크
			if (isRequired(fm.elements["vo.contents"])){
				return false;
			}
		
		 	return true;
		}
		
		function goInquireMainList(){
			fm.elements["method"].value="getInquireMainList";
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
            
            <html:form action="/Offer" method="post" name="fm" enctype="multipart/form-data" type="kr.go.rndcall.mgnt.offer.form.OfferForm">
			    <html:hidden name="OfferForm" property="method" value="offerUpdate"/>
			    <html:hidden name="OfferForm" property="vo.cell_number"/>
			    <html:hidden name="OfferForm" property="vo.email"/>
			    <html:hidden name="OfferForm" property="vo.file_id"/>
			    <html:hidden name="OfferForm" property="vo.question_file_id"/>
			    <html:hidden name="OfferForm" property="searchVO.loginId"/>
			    <html:hidden name="OfferForm" property="searchVO.name"/>
			    <html:hidden name="OfferForm" property="searchVO.seq"/>
			    <html:hidden name="OfferForm" property="searchVO.board_type"/>
			    <html:hidden name="OfferForm" property="searchVO.menu_sn"/>
    
	            <!-- section -->
	            <div class="section">       
	                <!--  explain-bx -->
	                <div class="explain-bx mt60">
	                    <strong>연구현장의 불합리한 제도나 관행 등 제도개선 건의 또는 기타 연구제도 관련 불편·불만사항을 건의해주세요.</strong>
	                    <p>*원활한 제안 처리를 위해 등록 후 24시간 이내에만 수정, 삭제가 가능하오니 양해바랍니다.</p>
	                </div>
	                <!--  //explain-bx -->
	                
	                <html:form action="/Offer" method="post" name="fm" type="kr.go.rndcall.mgnt.offer.form.OfferForm" enctype="multipart/form-data" onsubmit="return checkOnSubmit(this)">
	                    <html:hidden name="OfferForm" property="method" value="offerInsert"/>
	                    <html:hidden name="OfferForm" property="vo.cell_number"/>
	                    <html:hidden name="OfferForm" property="vo.email"/>
	                    <html:hidden name="OfferForm" property="searchVO.loginId"/>
	                    <html:hidden name="OfferForm" property="searchVO.name"/>
	                    <html:hidden name="OfferForm" property="searchVO.menu_sn"/>
	                    <html:hidden name="OfferForm" property="searchVO.type"/>
	    
	                    <!-- board-write -->
	                    <div class="board-write mt10">
	                        <div class="board-box">
	                            <table summary="R&amp;D 신문고 페이지">
	                                <caption>R&amp;D 신문고 페이지 </caption>
	                                <colgroup>
	                                    <col width="16%"/>
	                                    <col width="*"/>
	                                </colgroup>
	                                <tbody>
	                                    <tr <%= mainLoginVO == null ? "" : "style=\"display: none;\"" %>>
	                                        <th scope="row"><label for="info1">질의자 정보</label></th>
	                                        <td>
	                                            <html:radio styleId="info1" name="OfferForm" property="vo.query_user_info" value="1"></html:radio> <label for="info1">중앙행정기관</label>
	                                            <html:radio styleId="info2" name="OfferForm" property="vo.query_user_info" value="2"></html:radio> <label for="info2">전문기관</label>
	                                            <html:radio styleId="info3" name="OfferForm" property="vo.query_user_info" value="3"></html:radio> <label for="info3">정부출연연구기관</label>
	                                            <html:radio styleId="info4" name="OfferForm" property="vo.query_user_info" value="4"></html:radio> <label for="info4">대학</label>
	                                            <html:radio styleId="info5" name="OfferForm" property="vo.query_user_info" value="5"></html:radio> <label for="info5">기업</label>
	                                            <html:radio styleId="info6" name="OfferForm" property="vo.query_user_info" value="6"></html:radio> <label for="info6">기타</label>
	                                        </td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row"><label for="open1">공개여부</label></th>
	                                        <td>
	                                            <html:radio name="OfferForm" property="vo.open_yn" styleId="vo.open_yn1" value="Y"></html:radio> <label for="vo.open_yn1">공개</label>
	                                            <html:radio name="OfferForm" property="vo.open_yn" styleId="vo.open_yn2" value="N"></html:radio> <label for="vo.open_yn2">비공개</label>
	                                        </td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row"><label for="title">제목</label></th>
	                                        <td>
	                                            <html:text name="OfferForm" property="vo.title" style="width:98%" alt="제목" title="제목"/>
	                                        </td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row"><label for="txtarea1">내용</label></th>
	                                        <td>
	                                            <html:textarea name="OfferForm" property="vo.contents" cols="0" rows="0" style="width:97%; min-height:254px;" alt="질의 내용" title="질의 내용"/>
	                                        </td>
	                                    </tr>
	                                    <logic:notEmpty name="OfferForm" property="voList">
                                        <tr>
                                            <th scope="row">기존파일 삭제여부</th>
                                            <td>
                                                <logic:iterate name="OfferForm" property="voList" indexId="rowNum" id="attachVO">                       
                                                    <bean:define name="attachVO" property="file_id" id="file_id" type="java.lang.String"/>
                                                    <bean:define name="attachVO" property="seq" id="seq" type="java.lang.String"/>
                                                    <%
                                                        String f_id = file_id+"-"+seq;
                                                    %>
                                                    <input type="checkbox" name="file_del" value="<%=f_id%>"/>
                                                    <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');" ><bean:write name="attachVO" property="file_nm" /></a><br/>
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
	                        <span class="btn-set pink"><a href="JavaScript:offerUpdate()">수정</a></span>
	                        <span class="btn-set"><a href="JavaScript:history.back()">취소</a></span>
	                    </div>
	                    <!-- //btn-set-->
	                    
	                </html:form>
	                
	            </div>
	            <!-- //section -->
	            
	        </html:form>
	        
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->
    
<%@include file="/include/bottom.jsp"%>