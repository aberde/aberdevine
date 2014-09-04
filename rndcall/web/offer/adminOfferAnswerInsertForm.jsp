<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

	<bean:define name="OfferForm" property="vo.category1" id="category1" type="java.lang.String"/>
	<bean:define name="OfferForm" property="vo.category2" id="category2" type="java.lang.String"/>
	<bean:define name="OfferForm" property="vo.stat" id="stat" type="java.lang.String"/>
	<bean:define name="OfferForm" property="voList1" id="file_list" type="java.util.ArrayList"/>

    <bean:define id="path" type="java.lang.String" value="/Offer.do"/>

    <script type="text/javascript" src="/js/file.js"></script>
    
    <script type="text/javascript">
    <!--
        var data = {
           num : 6 // 위치순번
        };
        // 현재메뉴 위치.
        menuFocus(data);
    //-->
    </script>
    
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
	} else if (!mainRoleCD.equals("0000C") && !mainRoleCD.equals("0000Z")) {
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
					if(file_list.size() > 0){
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
	<%
				if(stat.equals("N")){
	%>   			
					fm.elements["method"].value="adminOfferAnswerInsert";
	<%
				}else{
	%>
					fm.elements["method"].value="adminOfferAnswerUpdate";
					fm.elements["vo.file_id"].value = fm.elements["vo.answer_file_id"].value;
	<%
				}
	%>			
				fm.submit();
			}
		}
		
		function goCancel(){
			document.location.href="/index.jsp";
		}
		
		function validate() {
			//질의내용 필수 입력 체크
			if (isRequired(fm.elements["vo.answerContents"])){
				return false;
			}
		 	return true;
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
            <logic:iterate name="OfferForm" property="voList2" id="mCode" indexId="comRowNm">
            categoryL[<%=comRowNm.intValue()%>] = new category_obejct("<bean:write name="mCode" property="code_nm" />", "<bean:write name="mCode" property="code" />", "<bean:write name="mCode" property="subjectNo" />");
            </logic:iterate>
            <logic:iterate name="OfferForm" property="voList3" id="mCode" indexId="comRowNm">
            categoryM[<%=comRowNm.intValue()%>] = new category_obejct("<bean:write name="mCode" property="code_nm" />", "<bean:write name="mCode" property="code" />", "<bean:write name="mCode" property="p_code" />");
            </logic:iterate>
            
            //f_cate_change2(fmDetail.elements["searchVO.bz_id"].value);
            f_cate_change2();
            f_cate_change(<%=category1%>);
        }
    
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
	
	//-->
	</script>
	
	<!-- container -->
    <div id="container">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>관리자</h2>
                <span><img src="/img/common/h2_admin_entxt.gif" alt="Admin" /></span>
            </div>
            <ul class="lnb-lst">
                <li><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getAdminInquireList&searchVO.board_type=QNA&searchVO.type=&searchVO.searchCategory=&searchVO.menu_sn=01">온라인 상담</a></li>
                <li class="on"><a href="/switch.do?prefix=/offer&page=/Offer.do?method=adminOfferList&searchVO.menu_sn=14">R&amp;D 신문고</a></li>
                <li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.roleCD=&searchVO.search_sel=&searchVO.search_word=&searchVO.menu_sn=09">회원관리</a></li>
                <li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=09">질문분야관리</a></li>
                <li><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm&searchVO.menu_sn=09">오프라인자료 등록</a></li>
                <li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatBoardType&searchVO.menu_sn=09">통계정보</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li class="on"><a href="/switch.do?prefix=/offer&page=/Offer.do?method=adminOfferList&searchVO.menu_sn=14">관리자</a></li>
                </ul>
            </div>
            
            <html:form action="/Offer" method="post" name="fm" enctype="multipart/form-data" type="kr.go.rndcall.mgnt.offer.form.OfferForm" onsubmit="return checkOnSubmit(this)">
			    <html:hidden name="OfferForm" property="method" value="getAnswerInsert"/>
			    <html:hidden name="OfferForm" property="vo.cell_number"/>
			    <html:hidden name="OfferForm" property="vo.email"/>
			    <html:hidden name="OfferForm" property="searchVO.loginId"/>
			    <html:hidden name="OfferForm" property="searchVO.name"/>
			    <html:hidden name="OfferForm" property="searchVO.seq"/>
			    <html:hidden name="OfferForm" property="searchVO.board_type"/>
			    <html:hidden name="OfferForm" property="vo.del_file_id"/>
			    <html:hidden name="OfferForm" property="vo.answer_seq"/>
			    <html:hidden name="OfferForm" property="vo.title"/>
			    <html:hidden name="OfferForm" property="vo.file_id"/>
			    <html:hidden name="OfferForm" property="vo.answer_file_id"/>
			    <html:hidden name="OfferForm" property="searchVO.menu_sn"/>
    
	            <!-- section -->
	            <div class="section">
	                <!-- board-detail -->
	                <div class="board-detail mt60">
	                    <div class="board-box">
	                        <table summary="규정 및 제도에 대한 개선사항 답변페이지">
	                            <caption>온라인 신문고 답변페이지</caption>
	                            <colgroup>
	                                <col width="16%"/>
	                                <col width="32%"/>
	                                <col width="16%"/>
	                                <col width="32%"/>
	                            </colgroup>
	                            <tbody>
	                                <tr>
	                                    <th scope="row">제목</th>
	                                    <td colspan="3"><bean:write name="OfferForm" property="vo.title" filter="false"/></td>
	                                </tr>
	                                <tr>
	                                    <th scope="row">아이디</th>
	                                    <td><bean:write name="OfferForm" property="vo.reg_id"/></td>
	                                    <th scope="row">상태</th>
	                                    <td>
	                                        <bean:define name="OfferForm" property="vo.stat" id="stat" type="java.lang.String"/>
	                                        <%
	                                            if(stat.equals("Y")){
	                                                out.print("완료");
	                                            }else{
	                                                out.print("처리중");
	                                            }
                                            %>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row">조회수</th>
	                                    <td><bean:write name="OfferForm" property="vo.read_count"/></td>
	                                    <th scope="row">공개여부</th>
	                                    <td>
	                                        <bean:define name="OfferForm" property="vo.open_yn" id="open_yn" type="java.lang.String"/>
                                            <%
                                                if(open_yn.equals("Y")){
                                                    out.println("공개");
                                                }else{
                                                    out.println("비공개");
                                                }
                                            %>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row">등록일</th>
	                                    <td colspan="3"><bean:write name="OfferForm" property="vo.reg_dt"/></td>
	                                </tr>
	
	                                <tr class="comment">
	                                    <th scope="row">질의내용</th>
	                                    <td colspan="3">
                                            <bean:write name="OfferForm" property="vo.contents" filter="false"/>
	                                    </td>
	                                </tr>
	                                <tr>
                                        <th scope="row"><label for="open1">공개여부</label></th>
                                        <td>
                                            <html:radio name="OfferForm" styleId="vo.open_yn1" property="vo.open_yn" value="Y"></html:radio>
                                            <label for="vo.open_yn1">공개</label>
                                            <html:radio name="OfferForm" styleId="vo.open_yn2" property="vo.open_yn" value="N"></html:radio>
                                            <label for="vo.open_yn2">비공개</label>
                                        </td>
                                    </tr>
	                                <tr>
                                        <th scope="row">분야 선택</th>
                                        <td colspan="3">
                                            <html:select name="OfferForm" property="vo.category1" styleId="category1" title="대분류" onchange="f_cate_change(this.value)">
                                                <html:option value="">::: 선택 :::</html:option>
                                            </html:select>
                                            <html:select name="OfferForm" property="vo.category2" styleId="category2" title="소분류">
                                                <html:option value="">::: 선택 :::</html:option>
                                            </html:select>
                                        </td>
                                    </tr>
	                                <tr class="comment">
	                                    <th scope="row" class="txt-blue">답변내용</th>
	                                    <td colspan="3" class="txt-blue">
	                                        <html:textarea styleId="txtarea1" name="OfferForm" property="vo.answerContents" cols="0" rows="0" style="width:97%; min-height:254px; " alt="답변 내용" title="답변 내용"/>
	                                    </td>
	                                </tr>
	                                <tr class="comment">
	                                    <th scope="row" class="txt-blue">답변완료여부</th>
	                                    <td colspan="3" class="txt-blue">
	                                        <html:checkbox name="OfferForm" property="vo.complete_yn" value="Y" styleId="complete_yn"></html:checkbox> <label for="complete_yn">답변완료여부</label>
	                                    </td>
	                                </tr>
	                                <logic:notEmpty name="OfferForm" property="voList1">
                                    <tr>
                                        <th scope="row">기존파일 삭제여부</th>
                                        <td>
                                            <logic:iterate name="OfferForm" property="voList1" indexId="rowNum" id="attachVO">                      
						                        <bean:define name="attachVO" property="file_id" id="file_id" type="java.lang.String"/>
						                        <bean:define name="attachVO" property="seq" id="seq" type="java.lang.String"/>
                                                <%
                                                    String f_id = file_id+"-"+seq;
                                                %>
                                                <input type="checkbox" name="file_del" value="<%=f_id%>"/>
                                                <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><img src="/images/icon/disk01.gif" alt="첨부파일"/><bean:write name="attachVO" property="file_nm" /></a><br/>
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
	                <!-- //board-detail -->
	                <!-- btn-set -->
	                <div class="btn-lst txt-r">
	                    <span class="btn-set pink"><a href="JavaScript:goAnswerCreate()">등록</a></span>
	                    <span class="btn-set"><a href="JavaScript:history.back()">취소</a></span>
	                </div>
	                <!-- //btn-set-->
		        </div>
	            <!-- //section -->
	            
	        </html:form>
	        
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->
	
<%@include file="/include/bottom.jsp"%>