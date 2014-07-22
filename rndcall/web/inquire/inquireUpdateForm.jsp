<%@page contentType="text/html; charset=utf-8" %>

<%@include file="/include/top.jsp"%>

    <bean:define id="path" type="java.lang.String" value="/Inquire.do"/>

<%
    String size = "70";
%>
    
    <script type="text/javascript" src="/js/file.js"></script>
    
<%
	if (mainLoginVO == null || !mainIsLogin) {	
%>
	<script type="text/javascript">
		alert('로그인이 필요한 메뉴입니다.');
		document.location.href = "/index.jsp";
	</script>
<%
	} else if (!mainRoleCD.equals("0000B") && !mainRoleCD.equals("0000C") && !mainRoleCD.equals("0000Z")) {
%>
	<script type="text/javascript">
		alert('권한이 없습니다.');
		document.location.href = "/index.jsp";
	</script>
<%		
	}
%>


	<script type="text/javascript">
	<!--		
		function goUpdate(){
			if(validate()){
				if(confirm("수정하시겠습니까?")){
					fm.elements["method"].value="getInquireUpdate";
					fm.elements["vo.file_id"].value = fm.elements["vo.question_file_id"].value;
					fm.submit();
				}
			}
		}
		
		function goCancel(){
			fm.elements["method"].value="getInquireView";
			fm.elements["searchVO.menu_sn"].value = "01";
			fm.submit();
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
			//SMS수신 체크시 핸드폰번호 체크
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
			
			//EMAIL수신 체크시 EMAIL 체크
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
                <li class="on"><a href="JavaScript:goInquireForm()">온라인상담</a></li>
                <li><a href="JavaScript:goFaq()">자주묻는질문</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href="JavaScript:goInquireForm()">온라인상담</a></li>
                    <li class="on"><a href="JavaScript:goInquireForm()">온라인상담</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>온라인상담</h3>
                    <p>R&amp;D 관련 규정 및 제도에 대해 궁금하신 사항에 담당자가 답변해 드립니다.기존 답변을 검색 후 질의해주세요.</p>
                </div>
                
                <html:form action="/Inquire" method="post" name="fm" enctype="multipart/form-data" type="kr.go.rndcall.mgnt.inquire.form.InquireForm">
			        <html:hidden name="InquireForm" property="method" value="getInquireUpdate"/>
			        <html:hidden name="InquireForm" property="vo.cell_number"/>
			        <html:hidden name="InquireForm" property="vo.email"/>
			        <html:hidden name="InquireForm" property="vo.question_file_id"/>
			        <html:hidden name="InquireForm" property="vo.file_id"/>
			        <html:hidden name="InquireForm" property="searchVO.loginId"/>
			        <html:hidden name="InquireForm" property="searchVO.name"/>
			        <html:hidden name="InquireForm" property="searchVO.seq"/>
			        <html:hidden name="InquireForm" property="searchVO.board_type"/>
			        <html:hidden name="InquireForm" property="searchVO.menu_sn"/>
                    
                    <!-- board-write -->
                    <div class="board-write mt30">
                        <div class="board-box">
                            <table summary="질의자 정보, 공개여부, 제목, 내용 보기 등록 페이지">
                                <caption>온라인상담 등록 페이지 </caption>
                                <colgroup>
                                    <col width="16%"/>
                                    <col width="*"/>
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="row"><label for="info1">질의자 정보</label></th>
                                        <td>
                                            <html:radio name="InquireForm" styleId="info1" property="vo.query_user_info" value="1"></html:radio> <label for="info1">중앙행정기관</label>
                                            <html:radio name="InquireForm" styleId="info2" property="vo.query_user_info" value="2"></html:radio> <label for="info2">전문기관</label>
                                            <html:radio name="InquireForm" styleId="info3" property="vo.query_user_info" value="3"></html:radio> <label for="info3">정부출연연구기관</label>
                                            <html:radio name="InquireForm" styleId="info4" property="vo.query_user_info" value="4"></html:radio> <label for="info4">대학</label>
                                            <html:radio name="InquireForm" styleId="info5" property="vo.query_user_info" value="5"></html:radio> <label for="info5">기업</label>
                                            <html:radio name="InquireForm" styleId="info6" property="vo.query_user_info" value="6"></html:radio> <label for="info6">기타</label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><label for="open1">공개여부</label></th>
                                        <td>
                                            <html:radio name="InquireForm" styleId="vo.open_yn1" property="vo.open_yn" value="Y"></html:radio>
                                            <label for="vo.open_yn1">공개</label>
                                            <html:radio name="InquireForm" styleId="vo.open_yn2" property="vo.open_yn" value="N"></html:radio>
                                            <label for="vo.open_yn2">비공개</label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><label for="title">제목</label></th>
                                        <td>
                                            <html:text name="InquireForm" property="vo.title" alt="제목" title="제목" style="width:98%" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><label for="txtarea1">내용</label></th>
                                        <td>
                                            <html:textarea name="InquireForm" property="vo.contents" cols="0" rows="0" style="width:97%; min-height:254px;" alt="질의 내용" title="질의 내용"/>
                                        </td>
                                    </tr>
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
                        <span class="btn-set pink"><a href="JavaScript:goUpdate()">수정</a></span>
                        <span class="btn-set"><a href="JavaScript:goCancel()">취소</a></span>
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