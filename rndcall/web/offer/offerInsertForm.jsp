<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

    <bean:define id="path" type="java.lang.String" value="/Offer.do"/>

    <script type="text/javascript" src="/js/file.js"></script>
    
    <%  
        String size = "70"; // default size
        if ( !Util.isNull(request.getParameter("size")) ) {
            size = request.getParameter("size");
        }
    %>
    <script type="text/JavaScript">
		function fncFileAddLenChk(fileObjName, size){
		   
		    var fileArea = document.getElementById(fileObjName);
		    var childNds = fileArea.childNodes;
		    
		    //alert(childNds.length);
		    if(childNds.length < 3) //최대 3개까지 첨부 가능
		        fncFileAdd(fileObjName, size);
		}
		
		function Save(){ 
			if(validate()){
				fm.elements["method"].value="offerInsert";
				fm.elements["searchVO.type"].value="03";
				fm.submit();
				<%
				    if ( mainLoginVO != null && mainIsLogin ) {    
				%>
				alert("마이페이지에서 등록한 글에대한 정보를 확인할수 있습니다.");
				<%
				    }
				%>
			}
		}
		
		function goCancel(){
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
                <!--  explain-bx -->
                <div class="explain-bx mt60">
                    <strong>연구현장의 불합리한 제도나 관행 등 제도개선 건의 또는 기타 연구제도 관련 불편·불만사항을 건의해주세요.</strong>
                    <p>*원활한 제안 처리를 위해 등록 후 24시간 이내에만 수정, 삭제가 가능하오니 양해바랍니다.</p>
                    <p>*답변을 이메일로 받고자 하는 경우에는 내용에 이메일 주소를 남겨주시기 바랍니다.</p>
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
	                                <%
                                        if ( mainLoginVO == null ) {
                                    %>
	                                <tr>
	                                    <th scope="row"><label for="info0">비밀번호</label></th>
	                                    <td>
	                                        <input type="password" id="vo.password" name="vo.password" /> 
	                                    </td>
	                                </tr>
	                                <%
                                        }
                                    %>
	                                <tr <%= mainLoginVO == null ? "" : "style=\"display: none;\"" %>>
                                        <th scope="row"><label for="info1">질의자 정보</label></th>
                                        <td>
                                            <input type="radio" id="info1" name="vo.query_user_info" value="1" <%= mainLoginVO != null && "1".equals(mainLoginVO.getSector()) ? "checked=\"checked\"" : "" %> /> <label for="info1">중앙행정기관</label>
                                            <input type="radio" id="info2" name="vo.query_user_info" value="2" <%= mainLoginVO != null && "2".equals(mainLoginVO.getSector()) ? "checked=\"checked\"" : "" %> /> <label for="info2">전문기관</label>
                                            <input type="radio" id="info3" name="vo.query_user_info" value="3" <%= mainLoginVO != null && "3".equals(mainLoginVO.getSector()) ? "checked=\"checked\"" : "" %> /> <label for="info3">정부출연연구기관</label>
                                            <input type="radio" id="info4" name="vo.query_user_info" value="4" <%= mainLoginVO != null && "4".equals(mainLoginVO.getSector()) ? "checked=\"checked\"" : "" %> /> <label for="info4">대학</label>
                                            <input type="radio" id="info5" name="vo.query_user_info" value="5" <%= mainLoginVO != null && "5".equals(mainLoginVO.getSector()) ? "checked=\"checked\"" : "" %> /> <label for="info5">기업</label>
                                            <input type="radio" id="info6" name="vo.query_user_info" value="6" <%= mainLoginVO != null && "6".equals(mainLoginVO.getSector()) ? "checked=\"checked\"" : "" %> /> <label for="info6">기타</label>
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
	                                        <input type="text" name="vo.title" style="width:98%" title="제목"/>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row"><label for="txtarea1">내용</label></th>
	                                    <td>
	                                        <textarea name="vo.contents" cols="0" rows="0" style="width:97%; min-height:254px;" title="자주하는질문 내용" ></textarea>
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
	                    <span class="btn-set pink"><a href="JavaScript:Save()">등록</a></span>
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