<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

	<bean:define id="path" type="java.lang.String" value="/Faq.do"/>
	
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
	<script type="text/JavaScript">
		function fncFileAddLenChk(fileObjName, size){
		   
		    var fileArea = document.getElementById(fileObjName);
		    var childNds = fileArea.childNodes;
		    
		    //alert(childNds.length);
		    if(childNds.length < 20) //최대 3개까지 첨부 가능
		        fncFileAdd(fileObjName, size);
		}
		function Save(){
			var cate = fm.elements["vo.analysis_yn"];
			//alert(cate);
			if( cate && cate.checked ){
				if(validate()) {
					fm.elements["method"].value="faqInsert";
					fm.submit();
					var width = '480';
				    var height = '630';
				    var left = (screen.width - width)/2;
				    var top = (screen.height - height)/2;
				   	var winNM = 'FaqForm';
				   	var url = '/switch.do?prefix=&page=/Faq.do?method=smsPopForm';  
				    var windowFeatures = "width=" + width + ",height=" + height + ",status,resizable,scrollbars=N,left=" + left + ",top=" + top + ",screenX=" + left + ",screenY=" + top;
				   	window.open("", winNM, windowFeatures);
					fm.action= url;
					fm.target= winNM;
					fm.submit();
					
				}
			}else{
				if(validate()){
					fm.elements["method"].value="faqInsert";
					fm.elements["searchVO.menu_sn"].value="01";
					fm.submit();
				}
			}
		}
		
		
		function goCancel(){
			document.location.href="/index.jsp";
		}
		
		
		function validate() {
			if (isRequired(fm.elements["vo.category1"])){
				return false;
			}
			//제목 필수 입력 체크
			if (isRequired(fm.elements["vo.title"])){
				return false;
			}
			//질의내용 필수 입력 체크
			if (isRequired(fm.elements["vo.contents"])){
				return false;
			}
			//답변내용
			if (isRequired(fm.elements["vo.answer_cont"])){
				return false;
			}
		
		 	return true;
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
		
		function init_data() { // onload 시 호출. 데이터 초기화.
		    <logic:iterate name="FaqForm" property="voList2" id="mCode" indexId="comRowNm">
				categoryL[<%=comRowNm.intValue()%>] = new category_obejct("<bean:write name="mCode" property="code_nm" />", "<bean:write name="mCode" property="code" />", "<bean:write name="mCode" property="subjectNo" />");
		    </logic:iterate>
		    <logic:iterate name="FaqForm" property="voList3" id="mCode" indexId="comRowNm">
				categoryM[<%=comRowNm.intValue()%>] = new category_obejct("<bean:write name="mCode" property="code_nm" />", "<bean:write name="mCode" property="code" />", "<bean:write name="mCode" property="p_code" />");
		    </logic:iterate>
		    
		    //f_cate_change2(fmDetail.elements["searchVO.bz_id"].value);
		    f_cate_change2();
		}
		
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
			for ( var i = 0; i < categoryL.length; i++ ) { // 중분류객체들 모두 조사
		    /*
		    if(categoryL[i].parentCode == value) { // 중분류객체의 parentCode 와 대분류값 비교.. 같다면..
		      // option 생성하여 현재 객체의 codeName, codeValue 추가.
		      cateL[idx++] = new Option(categoryL[i].codeName, categoryL[i].codeValue); 
		    } // if끝
		    */
		    //if(categoryL[i].parentCode == value) { // 중분류객체의 parentCode 와 대분류값 비교.. 같다면..
		      // option 생성하여 현재 객체의 codeName, codeValue 추가.
				cateL[idx++] = new Option(categoryL[i].codeName, categoryL[i].codeValue); 
		    //} // if끝
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
					cateM[idx++] = new Option(categoryM[i].codeName, categoryM[i].codeValue); 
				} // if끝
			} // for끝
		} // function f_cate_change 끝
		
		window.onload = function() { // onload 시 호출. 데이터 초기화.
			init_data();
		};
	</script>
	
	<!-- container -->
    <div id="container" class="advice">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>온라인 상담</h2>
                <span><img src="/img/common/h2_entxt02.gif" alt="Online Consultation" /></span>
            </div>
            <ul class="lnb-lst">
                <li><a href="JavaScript:goInquireMainList()">온라인 상담</a></li>
                <li class="on"><a href="JavaScript:goFaq()">자주 묻는 질문</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href="JavaScript:goInquireMainList()">온라인 상담</a></li>
                    <li class="on"><a href="JavaScript:goFaq()">자주 묻는 질문</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>자주 묻는 질문</h3>
                    <p>가장 많이 묻는 질의 응답을 카테고리별로 검색이 가능합니다.</p>
                </div>
                
                <html:form action="/Faq" method="post" name="fm" type="kr.go.rndcall.mgnt.faq.form.FaqForm" enctype="multipart/form-data" onsubmit="return checkOnSubmit(this)">
                    <html:hidden name="FaqForm" property="method" value="faqInsert"/>
                    <html:hidden name="FaqForm" property="vo.cell_number"/>
                    <html:hidden name="FaqForm" property="vo.email"/>
                    <html:hidden name="FaqForm" property="searchVO.loginId"/>
                    <html:hidden name="FaqForm" property="searchVO.name"/>
                    <html:hidden name="FaqForm" property="searchVO.menu_sn"/>
                    
	                <!-- board-detail -->
	                <div class="board-detail mt30">
	                    <div class="board-box">
	                        <table summary="제목, 분류, 질의내용, 답변내용  페이지">
	                            <caption>자주묻는 질문 페이지</caption>
	                            <colgroup>
	                                <col width="16%"/>
	                                <col width="*"/>
	                            </colgroup>
	                            <tbody>
	                                <tr>
	                                    <th scope="row">제목</th>
	                                    <td><input type="text" name="vo.title" title="제목" style="width:98%" title="제목"/></td>
	                                </tr>
	                                <tr>
	                                    <th scope="row">분류</th>
	                                    <td>
                                            <html:select name="FaqForm" property="vo.category1" styleId="category1" title="대분류" onchange="f_cate_change(this.value)">
                                                <html:option value="">::: 선택 :::</html:option>
                                                <html:optionsCollection name="FaqForm" property="voList" label="cd_nm" value="cd_nm"/>
                                            </html:select>
                                            <html:select name="FaqForm" property="vo.category2" styleId="category2" title="소분류">
                                                <html:option value="">::: 선택 :::</html:option>
                                                <html:optionsCollection name="FaqForm" property="voList" label="cd_nm" value="cd_nm"/>
                                            </html:select>
                                        </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row"><label for="txtarea1">질의내용</label></th>
	                                    <td>
	                                        <textarea id="vo.contents" name="vo.contents" cols="0" rows="0" style="width:97%; min-height:154px;" title="자주하는질문 내용" >*원활한 답변 처리를 위해 질의 등록 후 24시간 이내에만 수정, 삭제가 가능하오니 양해바랍니다.*답변을 이메일로 받고자 하는 경우에는 내용에 이메일 주소를 남겨주시기 바랍니다.
	                                        </textarea>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row"><label for="txtarea2">답변내용</label></th>
	                                    <td>
	                                        <textarea id="vo.answer_cont" name="vo.answer_cont" cols="0" rows="0" style="width:97%; min-height:154px;" title="자주하는질문 내용답변" >*원활한 답변 처리를 위해 질의 등록 후 24시간 이내에만 수정, 삭제가 가능하오니 양해바랍니다.*답변을 이메일로 받고자 하는 경우에는 내용에 이메일 주소를 남겨주시기 바랍니다.
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
	                <!-- //board-detail -->
	                <!-- btn-set-->
	                <div class="btn-lst txt-r">
	                    <span class="btn-set pink"><a href="JavaScript:Save()">등록하기</a></span>
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