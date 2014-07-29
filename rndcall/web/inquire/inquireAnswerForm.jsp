<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.inquire.vo.*" %>

<%@include file="/include/top.jsp"%>

	<bean:define name="InquireForm" property="vo.category1" id="category1" type="java.lang.String"/>
	<bean:define name="InquireForm" property="vo.category2" id="category2" type="java.lang.String"/>
	<bean:define name="InquireForm" property="vo.stat" id="stat" type="java.lang.String"/>
	<bean:define name="InquireForm" property="voList" id="q_file_list" type="java.util.ArrayList"/>
	<bean:define name="InquireForm" property="voList1" id="a_file_list" type="java.util.ArrayList"/>
    <bean:define name="InquireForm" property="voList2" id="cateL" type="java.util.ArrayList"/>

    <bean:define id="path" type="java.lang.String" value="/Inquire.do"/>
    
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
	} else if ( !mainRoleCD.equals("0000B") && !mainRoleCD.equals("0000C") && !mainRoleCD.equals("0000Z") ) {
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
		function goAnswerCreate(){
			var del_file_id = "";
			if(validate()){
                <%
	               if(!stat.equals("N")){
	                   if(a_file_list.size() > 0){
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
	                }
                %>
                fm.elements["vo.del_file_id"].value=del_file_id;
	   			if(confirm("답변을 등록하시겠습니까?")){
                    <%
                        if(stat.equals("N")){
                    %>
                    fm.elements["method"].value="getAnswerInsert";
                    <%
                        } else {
                    %>
                    fm.elements["method"].value="getAnswerUpdate";
                    fm.elements["vo.file_id"].value = fm.elements["vo.answer_file_id"].value;
                    <%
                        }
                    %>
                    fm.submit();
                }
			}
		}
		
		function goCancel(){
	//		document.location.href="/index.jsp";
			fm.elements["method"].value="getInquireView";
			fm.elements["searchVO.menu_sn"].value = "01";
			fm.submit();
		}
		
		function validate() {
					
			if(isRequired(fm.elements["vo.category1"])){
				return false;
			}
			if(fm.elements["vo.category2"].options.length > 1){
				if(isRequired(fm.elements["vo.category2"])){
					return false;
				}
			}
			
			//질의내용 필수 입력 체크
			if (isRequired(fm.elements["vo.answerContents"])){
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
		    if(childNds.length < 3) //최대 3개까지 첨부 가능
		        fncFileAdd(fileObjName, size);
		}
		
		// 중분류 자바스크립트 객체를 저장할 배열변수
		var categoryL = new Array();
		var categoryM = new Array();
		// 자바스크립트 사용자정의 객체
		function category_obejct(codeName, codeValue, parentCode) {
		    this.codeName = codeName;
		    this.codeValue = codeValue;
		    this.parentCode = parentCode;
		}
	
	
		window.onload= function() { // onload 시 호출. 데이터 초기화.
		    <logic:iterate name="InquireForm" property="voList2" id="mCode" indexId="comRowNm">
		    categoryL[<%=comRowNm.intValue()%>] = new category_obejct("<bean:write name="mCode" property="code_nm" />", "<bean:write name="mCode" property="code" />", "<bean:write name="mCode" property="subjectNo" />");
		    </logic:iterate>
		    <logic:iterate name="InquireForm" property="voList3" id="mCode" indexId="comRowNm">
		    categoryM[<%=comRowNm.intValue()%>] = new category_obejct("<bean:write name="mCode" property="code_nm" />", "<bean:write name="mCode" property="code" />", "<bean:write name="mCode" property="p_code" />");
		    </logic:iterate>
		    
		    //f_cate_change2(fmDetail.elements["searchVO.bz_id"].value);
		    f_cate_change2();
		    f_cate_change(<%=category1%>);
		};
	
		// 대분류 셀렉트박스의 onChange 이벤트에 설정.
		
		//function f_cate_change2(value) { // 대분류 값 가져옴. 중분류 객체의 parentCode 에 해당하는 값.
		function f_cate_change2() { // 대분류 값 가져옴. 중분류 객체의 parentCode 에 해당하는 값.
		  	// 모든 option 제거.
		   	var cateL = document.getElementById("category1"); //중분류 select 박스 객체
		   	var opts = cateL.options; // select 박스의 모든 option 을 가져옴.
		   	while(opts.length>1) { // 최초 == 선택 == 이라고 된 부분 제외하고 모두 삭제
		   		opts[1]=null;
		   	}
		   	
		  	var idx = opts.length; // 남은 option 갯수. 여기선 당연히 1 이겠지만 다른곳에서 응용을 위해..
		  	for(var i=0; i<categoryL.length; i++) { // 중분류객체들 모두 조사      	
				if(categoryL[i].codeValue =="<%=category1%>"){
					cateL[idx++] = new Option(categoryL[i].codeName, categoryL[i].codeValue,true,true);
				}else{
					cateL[idx++] = new Option(categoryL[i].codeName, categoryL[i].codeValue);
				}
		  	} // for끝
		} // function f_cate_change 끝
		
		function f_cate_change(value) { // 대분류 값 가져옴. 중분류 객체의 parentCode 에 해당하는 값.
		  	// 모든 option 제거.
		   	var cateM = document.getElementById("category2"); //중분류 select 박스 객체
		   	var opts = cateM.options; // select 박스의 모든 option 을 가져옴.
		   	while(opts.length>1) { // 최초 == 선택 == 이라고 된 부분 제외하고 모두 삭제
		   		opts[1]=null;
		   	}
		
		  	var idx = opts.length; // 남은 option 갯수. 여기선 당연히 1 이겠지만 다른곳에서 응용을 위해..
		  	for(var i=0; i<categoryM.length; i++) { // 중분류객체들 모두 조사
		    	if(categoryM[i].parentCode == value) { // 중분류객체의 parentCode 와 대분류값 비교.. 같다면..
		      	// option 생성하여 현재 객체의 codeName, codeValue 추가.
			      	if(categoryM[i].codeValue =="<%=category2%>"){
						cateM[idx++] = new Option(categoryM[i].codeName, categoryM[i].codeValue,true,true);
					}else{
						cateM[idx++] = new Option(categoryM[i].codeName, categoryM[i].codeValue); 
					}
		    	} // if끝
		  	} // for끝
		} // function f_cate_change 끝
	
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
                    
                    <!-- board-detail -->
	                <div class="board-detail mt30">
	                    <h3>등록자 정보</h3>
	                    <div class="board-box">
	                        <table summary="제목, 분류, 질의내용, 답변내용  페이지">
	                            <caption>자주묻는 질문 페이지</caption>
	                            <colgroup>
	                                <col width="15%"/>
	                                <col width="35%"/>
	                                <col width="15%"/>
	                                <col width="*"/>
	                            </colgroup>
	                            <tbody>
	                                <tr>
	                                    <th scope="row">이름</th>
	                                    <td><bean:write name="InquireForm" property="vo.reg_nm"/></td>
	                                    <th scope="row">공개여부</th>
	                                    <td>
	                                        <bean:define name="InquireForm" property="vo.open_yn" id="open_yn" type="java.lang.String"/>
                                            <%
                                                if(open_yn.equals("Y")){
                                                    out.println("공개");
                                                }else{
                                                    out.println("비공개");
                                                }
                                            %>                  
	                                    </td>
	                                </tr>
	                            </tbody>
	                         </table>
	                    </div>
	                </div>
	                <!-- //board-detail -->
                    
                    <!-- btn-set -->
                    <div class="btn-lst txt-r">
                        <span class="btn-set pink"><a href="JavaScript:goAnswerCreate()">답변등록</a></span>
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