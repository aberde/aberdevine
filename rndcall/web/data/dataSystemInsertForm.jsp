<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

    <bean:define id="path" type="java.lang.String" value="/Data.do"/>

    <script type="text/javascript">
    <!--
        var data = {
            num : 4 // 위치순번
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
			if(validate()){
				fm.elements["method"].value="dataSystemInsert";
				fm.submit();
			}
		}
		
		function goCancel(){
			document.location.href=history.back(-1);
		}
		
		function goIns(arg){
			fm.elements["searchVO.whichSearch"].value="";
			fm.elements["searchVO.searchTxt"].value="";
			fm.elements["searchVO.board_type"].value=arg;	
			if ( arg == "SYSTEM" ) {
                fm.elements["method"].value="dataSystemList";
            } else {
                fm.elements["method"].value="dataList";
            }
			fm.submit();
		}
		
		function validate() {
			//SMS수신 체크시 핸드폰번호 체크
			
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
    <div id="container" class="dataroom">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>자료실</h2>
                <span><img src="/img/common/h2_data_entxt01.gif" alt="Online Consultation" /></span>
            </div>
            <ul class="lnb-lst">
                <li><a href="Javascript:goLawInfo()">법령 및 행정규칙</a></li>
                <li class="on"><a href="JavaScript:goIns('SYSTEM')">연구관리제도</a></li>
                <li><a href="JavaScript:goIns('DATA')">기타</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href="JavaScript:goIns('DATA')">자료실</a></li>
                    <li class="on"><a href="JavaScript:goIns('SYSTEM')">연구관리제도</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>연구관리제도</h3>
                    <p>국가연구개발사업 관련 주요 연구관리제도 자료 검색이 가능합니다.</p>
                </div>
                
                <html:form action="/Data" method="post" name="fm" type="kr.go.rndcall.mgnt.data.form.DataForm" enctype="multipart/form-data" onsubmit="return checkOnSubmit(this)">
				    <html:hidden name="DataForm" property="method" value="dataInsert"/>
				    <html:hidden name="DataForm" property="searchVO.loginId"/>
				    <html:hidden name="DataForm" property="searchVO.name"/>
				    <html:hidden name="DataForm" property="searchVO.menu_sn"/>
				    <html:hidden name="DataForm" property="searchVO.board_type"/>
				    <html:hidden name="DataForm" property="searchVO.whichSearch"/>
				    <html:hidden name="DataForm" property="searchVO.searchTxt"/>
    
	                <!-- board-detail -->
	                <div class="board-detail mt30">
	                    <div class="board-box">
	                        <table summary="기타, 답변내용, 첨부파일 등록페이지">
	                            <caption>기타 상담 등록페이지</caption>
	                            <colgroup>
	                                <col width="16%"/>
	                                <col width="*"/>
	                            </colgroup>
	                            <tbody>
	                                <tr>
	                                    <th scope="row">제목</th>
	                                    <td><input type="text" name="vo.title" style="width:98%;" title="제목"/></td>
	                                </tr>
	                                <tr>
                                        <th scope="row">게시판타입</th>
                                        <td>
                                            <select name="vo.board_type">
                                                <option value="SYSTEM">연구관리제도</option>
                                                <option value="DATA">기타</option>
                                            </select>
                                        </td>
                                    </tr>
	                                <tr>
	                                    <th scope="row"><label for="txtarea1">답변내용</label></th>
	                                    <td>
	                                        <textarea id="txtarea1" name="vo.contents" cols="0" rows="0" value=""  style="width:97%; min-height:254px; ">
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