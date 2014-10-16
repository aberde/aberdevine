<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>
<%@page import="kr.go.rndcall.mgnt.common.DesCipher" %>

<%@include file="/include/top.jsp"%>

    <bean:define name="AdminForm" property="errCd" id="errCd" type="java.lang.String"/>

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
    if ( !Util.isNull(request.getParameter("size")) ) {
    	size = request.getParameter("size");
    }
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

    <script type="text/javascript" src="/js/file.js"></script>
	<script type="text/JavaScript">
	   $(document).ready(function() {
			if("<%=errCd%>" == "1"){
				alert("오프라인자료등록이 성공하였습니다. \n 온라인 상담에서 확인하실수 있습니다.");
				return;
			}else if("<%=errCd%>" == "-1"){
				alert("오프라인자료등록이 등록이 실패하였습니다.");
				return;
			}
		});
	
		function fncFileAddLenChk(fileObjName, size){
		    var fileArea = document.getElementById(fileObjName);
		    var childNds = fileArea.childNodes;
		    
		    //alert(childNds.length);
		    if(childNds.length < 3) //최대 3개까지 첨부 가능
		        fncFileAdd(fileObjName, size);
		}
	
		function goInsert(){
			if(validate()) {
				if(confirm("등록하시겠습니까?")){
					fm.elements["method"].value = "getOfflineDataInsert";
					fm.submit();
				}
			}
		}
	
		function validate() {
		/*
		 	if (isRequired(fm.elements["vo.bd_title"])){
				return false;
			}
			
			if (isRequired2(fm.elements["vo.step_nm"])){
				return false;
			}
			
			if (isRequired2(fm.elements["vo.bd_contents"])){
				return false;
			}
		*/	
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
	
		window.onload= function() { // onload 시 호출. 데이터 초기화.
		    <logic:iterate name="AdminForm" property="voList2" id="mCode" indexId="comRowNm">
		    categoryL[<%=comRowNm.intValue()%>] = new category_obejct("<bean:write name="mCode" property="code_nm" />", "<bean:write name="mCode" property="code" />", "<bean:write name="mCode" property="subjectNo" />");
		    </logic:iterate>
		    <logic:iterate name="AdminForm" property="voList3" id="mCode" indexId="comRowNm">
		    categoryM[<%=comRowNm.intValue()%>] = new category_obejct("<bean:write name="mCode" property="code_nm" />", "<bean:write name="mCode" property="code" />", "<bean:write name="mCode" property="p_code" />");
		    </logic:iterate>
		    
		    //f_cate_change2(fmDetail.elements["searchVO.bz_id"].value);
		    f_cate_change2();
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
	            cateL[idx++] = new Option(categoryL[i].codeName, categoryL[i].codeValue);
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
	
		function downLoad(fileNM, saveFileNM, filePath, yn){
		    fileDownLoad.elements["fileNM"].value = fileNM;
		    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
		    fileDownLoad.elements["filePath"].value = filePath;
		    fileDownLoad.elements["desCipher"].value = yn;
		    fileDownLoad.submit();
		}
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
                <li><a href="/switch.do?prefix=/offer&page=/Offer.do?method=adminOfferList&searchVO.menu_sn=14">R&amp;D 신문고</a></li>
                <li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=09">회원관리</a></li>
                <li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=09">질문분야관리</a></li>
                <li class="on"><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm&searchVO.menu_sn=09">오프라인자료등록</a></li>
                <li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatBoardType&searchVO.menu_sn=09">통계정보</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li class="on"><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=09">관리자</a></li>
                </ul>
            </div>
            
            <html:form action="/Admin" method="post" name="fm" type="kr.go.rndcall.mgnt.admin.form.AdminForm" enctype="multipart/form-data" onsubmit="return checkOnSubmit(this)">
			    <html:hidden name="AdminForm" property="method" value="getOfflineDataInsert"/>
			    <html:hidden name="AdminForm" property="searchVO.menu_sn"/>
    
	            <!-- section -->
	            <div class="section">
	                <div class="tit-area">
	                    <h3>오프라인자료등록</h3>
	                    <p>R&amp;D 도우미센터의 관리자 화면입니다.</p>
	                </div>
	
	                <!-- board-write -->
	                <div class="board-write mt30">
	                    <div class="board-box">
	                        <table summary="질의자 정보, 공개여부, 제목, 내용 보기 등록 페이지">
	                            <caption>오프라인자료등록</caption>
	                            <colgroup>
	                                <col width="16%"/>
	                                <col width="32%"/>
	                                <col width="16%"/>
	                                <col width="32%"/>
	                            </colgroup>
	                            <tbody>
	                                <tr>
	                                    <th scope="col"><label for="title">제목</label></th>
	                                    <td colspan="3">
	                                        <input type="text" name="vo.title" title="제목" style="width:98%" />
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="col"><label for="qu">질문일자</label></th>
	                                    <td><input type="text" name="vo.reg_dt" style="width:120px" maxlength="10" /><span class="ml10"> 예) 2012-01-01</span></td>
	                                    <th scope="col"><label for="qt">질문시간</label></th>
	                                    <td><input type="text" name="vo.reg_time" style="width:120px" maxlength="5" /><span class="ml10"> 예) 15:23</span></td>
	                                </tr>
	                                <tr>
	                                    <th scope="row" >질문내용</th>
	                                    <td colspan="3"><textarea name="vo.question_contents" id="question_contents" rows="0" cols="0" style="width:97%; min-height:220px;" title="질문내용"/></textarea> </td>
	                                </tr>
	                                <tr>
                                        <th scope="row"><label for="category1">분야 선택</label></th>
                                        <td colspan="3">
                                            <select id="category1" name="vo.category1" title="대분류" onchange="f_cate_change(this.value)">
                                                <option value="">::: 선택 :::</option>
                                            </select>
                                            <select id="category2" name="vo.category2" title="소분류">
                                                <option value="">::: 선택 :::</option>
                                            </select>
                                        </td>
                                    </tr>
	                                <tr>
	                                    <th scope="row">답변내용</th>
	                                    <td colspan="3"><textarea name="vo.answer_contents" id="contents" rows="0" cols="0" style="width:97%; min-height:200px;" title="답변내용"/></textarea></td>
	                                </tr>
	                                <tr>
	                                    <th scope="row">첨부</th>
	                                    <td>
	                                        <span class="btn-set set2 green"><a href="Javascript:fncFileAddLenChk('fileArea', '<%=size%>')">파일추가 +</a></span>
	                                        <span class="btn-set set2 black"><a href="Javascript:fncFileDel('fileArea')">파일제거 -</a></span>
	                                    </td>
	                                </tr>
	                            </tbody>
	                          </table>
	                    </div>
	                </div>
	                <!-- // board-write -->
	
	                <!-- btn-lst -->
	                <div class="btn-lst txt-c">
	                    <%
	                        //엑셀양식 다운로드파일명
	                        DesCipher dc = new DesCipher();
	                        String saveFileNM = "e_relation01.xls"; 
	                        saveFileNM = dc.Encode(saveFileNM);
	                    %>
	                    <span class="btn-set gray"><a href="javascript:downLoad('오프라인자료등록(양식).xls', '<%=saveFileNM%>', '/FILE/rndcall/', 'Y');">양식다운로드</a></span>
	                </div>
	                <!-- //btn-lst -->
	
	                <!-- board-write -->
	                <div class="board-write mt30">
	                    <div class="board-box">
	                        <table>
	                            <caption>일괄등록첨부</caption>
	                            <colgroup>
	                                <col width="16%"/>
	                                <col width="*"/>
	                            </colgroup>
	                            <tbody>
	                                <tr>
	                                    <th scope="row"><label for="file">일괄등록첨부</label></th>
	                                    <td>
		                                    <input type="file" name="vo.putFile" id="putFile" title="파일선택" style="width:80%" maxlength="250"/>
	<!--                                         <input type="text" id="file" style="width:80%"/> -->
	<!--                                         <span class="btn-set set2 navy ml10"><a href="#">찾아보기</a><span class="zoom"></span></span> -->
	                                    </td>
	                                </tr>
	                            </tbody>
	                          </table>
	                    </div>
	                </div>
	                <!-- // board-write -->
	
	                <!-- btn-set -->
	                <div class="btn-lst txt-r">
	                    <span class="btn-set pink"><a href="JavaScript:goInsert()">등록</a></span>
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

<form name="fileDownLoad" method="post" action="/downLoad.do" >
    <input type="hidden" name="fileNM"/>
    <input type="hidden" name="saveFileNM"/>
    <input type="hidden" name="filePath"/>
    <input type="hidden" name="desCipher" value="N"/>
</form>

<%@include file="/include/bottom.jsp"%>