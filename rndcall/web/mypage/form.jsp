<%@page contentType="text/html; charset=utf-8" %>

<%@include file="/include/top.jsp"%>

    <bean:define name="MypageForm" property="errCd" id="errCd" type="java.lang.String"/>
    
    <bean:define id="path" type="java.lang.String" value="/Mypage.do"/>

    <script language="JavaScript" src="/js/file.js"></script>
    
<%  
    String size = "70"; // default size
    if(!Util.isNull(request.getParameter("size"))) {
        size = request.getParameter("size");
    }       
%>
	<script type="text/javascript">
	<!--
	    window.onload= function() { // onload 시 호출. 데이터 초기화.
	        if("<%=errCd%>" == "true"){
	            alert("정상적으로 수정되었습니다.");    
	            fm.elements["method"].value="getMypageView";
	            fm.submit();
	        }else if("<%=errCd%>" == "false"){      
	            alert("수정이 실패하였습니다.");
	            return;
	        }
	    }
	
	    function goUpdate(){
	        if(validate()){
	            if(confirm("수정하시겠습니까?")){
	                fm.elements["vo.file_id"].value=fm.elements["vo.question_file_id"].value;
	                fm.elements["method"].value="getMypageUpdate";
	                fm.submit();
	            }
	        }
	    }
	    
	    function goCancel(){
	        history.back(-1);
	//      document.location.href="/index.jsp";
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
// 	        //SMS수신 체크시 핸드폰번호 체크
// 	        var sms="";     
// 	        for(i=0; i < fm.elements["vo.call_receive_yn"].length; i++){
// 	            if(fm.elements["vo.call_receive_yn"][i].checked){
// 	                sms = fm.elements["vo.call_receive_yn"][i].value;
// 	            }
// 	        }
	        
// 	        if(sms == "Y"){
// 	            fm.elements["vo.cell_number"].value = fm.elements["cell_no1"].value+ "-" +fm.elements["cell_no2"].value+ "-" +fm.elements["cell_no3"].value;
// 	            if (!isNotValidTel(fm.elements["vo.cell_number"])) return false;
// 	        }       
	        
// 	        //EMAIL수신 체크시 EMAIL 체크
// 	        var email="";       
// 	        for(i=0; i < fm.elements["vo.email_receive_yn"].length; i++){
// 	            if(fm.elements["vo.email_receive_yn"][i].checked){
// 	                email = fm.elements["vo.email_receive_yn"][i].value;
// 	            }
// 	        }
	        
// 	        if(email == "Y"){
// 	            fm.elements["vo.email"].value = fm.elements["email1"].value + "@" + fm.elements["email2"].value;
// 	            if(!isEMailAddr(fm.elements["vo.email"])){
// 	                return false;
// 	            }
// 	        }
	        
	        
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
                <h2>마이페이지</h2>
                <span><img src="/img/common/h2_mypage.gif" alt="Online Consultation" /></span>
            </div>
            <ul class="lnb-lst">
                <li class="on"><a href="JavaScript:goMypage()">마이페이지</a></li>
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
                
                <html:form action="/Mypage" method="post" name="fm" enctype="multipart/form-data" type="kr.go.rndcall.mgnt.mypage.form.MypageForm">
			        <html:hidden name="MypageForm" property="method" value="getMypageUpdate"/>
			        <html:hidden name="MypageForm" property="vo.cell_number"/>
			        <html:hidden name="MypageForm" property="vo.email"/>
			        <html:hidden name="MypageForm" property="vo.question_file_id"/>
			        <html:hidden name="MypageForm" property="vo.file_id"/>
			        <html:hidden name="MypageForm" property="searchVO.loginId"/>
			        <html:hidden name="MypageForm" property="searchVO.name"/>
			        <html:hidden name="MypageForm" property="searchVO.seq"/>
			        <html:hidden name="MypageForm" property="searchVO.board_type"/>
			        <html:hidden name="MypageForm" property="searchVO.type"/>
			        <html:hidden name="MypageForm" property="searchVO.menu_sn"/>
			        
	                <!-- board-write -->
	                <div class="board-write mt30">
	                    <div class="board-box">
	                        <table summary="질의자 정보, 공개여부, 제목, 내용 보기 등록 페이지">
	                            <caption>오프라인자료등록</caption>
	                            <colgroup>
	                                <col width="16%"/>
	                                <col width="*"/>
	                            </colgroup>
	                            <tbody>
	                                <tr>
	                                    <th scope="col"><label for="title">제목</label></th>
	                                    <td>
	                                        <html:text name="MypageForm" property="vo.title" style="width:98%" alt="제목" title="제목"/>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="col"><label for="qu">공개여부</label></th>
	                                    <td>
	                                        <html:radio name="MypageForm" styleId="qu" property="vo.open_yn" value="Y"></html:radio> <label for="qu">공개</label>
                                            <html:radio name="MypageForm" styleId="qu01" property="vo.open_yn" value="N"></html:radio> <label for="qu01">비공개</label>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row" ><label for="txtarea1">질문내용</label></th>
	                                    <td>
	                                        <html:textarea styleId="txtarea1" name="MypageForm" property="vo.contents" cols="0" rows="0" style="width:97%; min-height:220px;" alt="질의 내용" title="질의 내용"/> 
	                                    </td>
	                                </tr>
	                                <logic:notEmpty name="MypageForm" property="voList">
						            <tr>
						                <th scope="row">기존파일 삭제여부</th>
						                <td>
						                    <logic:iterate name="MypageForm" property="voList" indexId="rowNum" id="attachVO">                      
						                        <bean:define name="attachVO" property="file_id" id="file_id" type="java.lang.String"/>
						                        <bean:define name="attachVO" property="seq" id="seq" type="java.lang.String"/>
						<%
						                        String f_id = file_id+"-"+seq;
						%>
						                        <input type="checkbox" name="file_del" value="<%=f_id%>"/>
						                        <bean:write name="attachVO" property="file_nm" /><a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');" class="btn_TFileDw"><strong>파일다운로드</strong></a><br/>
						                    </logic:iterate>
						                </td>
						            </tr>
						            </logic:notEmpty>
	                                <tr>
	                                    <th scope="row">첨부</th>
	                                    <td>
	                                        <span class="btn-set set2 green"><a href="javascript:fncFileAddLenChk('fileArea', '<%=size%>')">파일추가 +</a></span>
	                                        <span class="btn-set set2 black"><a href="javascript:fncFileDel('fileArea')">파일제거 -</a></span>
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
    
    <form name="fileDownLoad" method="post" action="/downLoad.do" >
	    <input type="hidden" name="fileNM"/>
	    <input type="hidden" name="saveFileNM"/>
	    <input type="hidden" name="filePath"/>
	    <input type="hidden" name="desCipher" value="N"/>
	</form> 

<%@include file="/include/bottom.jsp"%>