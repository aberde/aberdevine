<%@page contentType="text/html; charset=utf-8" %>

<%@include file="/include/top.jsp"%>

	<bean:define name="InquireForm" property="errCd" id="errCd" type="java.lang.String"/>
	<bean:define id="path" type="java.lang.String" value="/Inquire.do"/>

	<%
		String size = "70";
	%>

	<script type="text/javascript">
	<!--
		var data = {
			num : 1	// 위치순번
		};
		// 현재메뉴 위치.
		menuFocus(data);
	//-->
	</script>
	
	<script type="text/javascript" src="/js/file.js"></script>
	<script type="javascript">
		alert('<bean:write name="InquireForm" property="loginVO.mobile"/>');
	</script>
	<script type="text/javascript">
	<!--
		window.onload = function() { // onload 시 호출. 데이터 초기화.
			if ( "<%=errCd%>" == "true" ) {
				alert("정상적으로 등록되었습니다.");
				document.location.href="/switch.do?prefix=/mypage&page=/Mypage.do?method=getMypageList&searchVO.menu_sn=03";
			} else if ( "<%=errCd%>" == "false" ) {    	
	    		alert("등록이 실패하였습니다.");
	    		return;
			}
		};
		
		function goCreate(){
			if(validate()){
				if(confirm("등록하시겠습니까?")){
					fm.elements["method"].value="getInquireInsert";
					fm.submit();
				}
			}
		}
	
		function goCancel(){
			document.location.href="/index.jsp";
		}
	
		function putEmailHost(){
			if ( fm.elements["putEmail"].value != "" ) {		
				fm.elements["email1"].focus();
				fm.elements["email2"].readonly=true;
				fm.elements["email2"].value = fm.elements["putEmail"].value;
			} else {
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
                    <html:hidden name="InquireForm" property="method" value="getInquireInsert"/>
                    <html:hidden name="InquireForm" property="vo.cell_number"/>
                    <html:hidden name="InquireForm" property="vo.email"/>
                    <html:hidden name="InquireForm" property="searchVO.loginId"/>
                    <html:hidden name="InquireForm" property="searchVO.name"/>
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
	                                    <th scope="row"><label for="info0">비밀번호</label></th>
	                                    <td>
	                                        <input type="password" id="info0" name="su0" /> 
	                                        <span class="btn-set set2 black"><a href="#">확인</a></span>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row"><label for="info1">질의자 정보</label></th>
	                                    <td>
	                                        <input type="radio" id="info1" name="su" checked /> <label for="info1">중앙행정기관</label>
	                                        <input type="radio" id="info2" name="su" /> <label for="info2">전문기관</label>
	                                        <input type="radio" id="info3" name="su" /> <label for="info3">정부출연연구기관</label>
	                                        <input type="radio" id="info4" name="su" /> <label for="info4">대학</label>
	                                        <input type="radio" id="info5" name="su" /> <label for="info5">기업</label>
	                                        <input type="radio" id="info6" name="su" /> <label for="info6">기타</label>
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
	                                        <input type="text" name="vo.title" title="제목" style="width:98%"/>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row"><label for="txtarea1">내용</label></th>
	                                    <td>
	                                        <textarea name="vo.contents" cols="0" rows="0" style="width:97%; min-height:254px;" title="질문 내용">*원활한 답변 처리를 위해 질의 등록 후 24시간 이내에만 수정, 삭제가 가능하오니 양해바랍니다.*답변을 이메일로 받고자 하는 경우에는 내용에 이메일 주소를 남겨주시기 바랍니다.
                                            </textarea>
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
	                    <span class="btn-set pink"><a href="JavaScript:goCreate()">등록</a></span>
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