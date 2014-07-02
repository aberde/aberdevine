<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

    <bean:define id="path" type="java.lang.String" value="/Data.do"/>

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
				fm.elements["method"].value="dataInsert";
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
			fm.elements["method"].value="dataList";
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
    <div id="container">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>자료실</h2>
                <span><img src="/img/common/h2_data_entxt01.gif" alt="Online Consultation" /></span>
            </div>
            <ul class="lnb-lst">
                <li><a href="Javascript:goLawInfo()">법령 및 행정규칙</a></li>
                <li><a href="#none;">제도</a></li>
                <li  class="on"><a href="JavaScript:goIns('DATA')">기타</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="../../img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href="JavaScript:goIns('DATA')">자료실</a></li>
                    <li class="on"><a href="JavaScript:goIns('DATA')">기타</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>기타</h3>
                    <p>국가연구개발사업 관련 법령,제도 외 참고자료 검색이 가능합니다.</p>
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
	                                    <th scope="row">기타</th>
	                                    <td>부설연구소는 주관연구기관이 될수 없는지요?</td>
	                                </tr>
	                                <tr>
	                                    <th scope="row"><label for="txtarea1">답변내용</label></th>
	                                    <td>
	                                        <textarea id="txtarea1" cols="0" rows="0" value=""  style="width:97%; min-height:254px; ">
	                                        </textarea>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row"><label for="file">첨부파일</label></th>
	                                    <td>
	                                        <span class="btn-set set2 black"><a href="#">파일첨부</a></span>
	                                        <span class="btn-set set2 black"><a href="#">파일제거</a></span>
	                                    </td>
	                                </tr>
	                            </tbody>
	                         </table>
	                    </div>
	                </div>      
	                <!-- //board-detail -->
	                <!-- btn-set-->
	                <div class="btn-lst txt-r">
	                    <span class="btn-set pink"><a href="#">등록하기</a></span>
	                    <span class="btn-set"><a href="#">취소</a></span>
	                </div>
	                <!-- //btn-set-->
	                
	            </html:form>
	            
            </div>
            <!-- //section -->  
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->
    










<div class="LY-Container">
	<html:form action="/Data" method="post" name="fm" type="kr.go.rndcall.mgnt.data.form.DataForm" enctype="multipart/form-data" onsubmit="return checkOnSubmit(this)">
    <html:hidden name="DataForm" property="method" value="dataInsert"/>
	<html:hidden name="DataForm" property="searchVO.loginId"/>
	<html:hidden name="DataForm" property="searchVO.name"/>
	<html:hidden name="DataForm" property="searchVO.menu_sn"/>
	<html:hidden name="DataForm" property="searchVO.board_type"/>
	<html:hidden name="DataForm" property="searchVO.whichSearch"/>
	<html:hidden name="DataForm" property="searchVO.searchTxt"/>
	<!-- start # 레프트 메뉴 -->
	<div id="LY-Left">
		<!-- start # left menu  -->
		<ul id="Left-Menu">
			<li><a href="#" onclick="goLawInfo()"; onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image8','','/images/Menu/left/lm02_01_on.gif',8)"><img src="/images/Menu/left/lm02_01_off.gif" name="Image8" border="0" id="Image8" alt="기타자료실" /></a></li>
			<li><a href="JavaScript:goIns('DATA')"><img src="../images/Menu/left/lm02_03_off.gif" alt="기타자료" /></a></li>
			</ul>
			<SCRIPT type=text/javascript>
			<!--
			var ObjEventLeftMenu = new EventLeftMenu(2, 2, 0);
			//-->
			</SCRIPT>
	</div>
	<!-- end # 레프트 메뉴 -->
	<!-- start # 컨텐츠 영역 -->
	<div class="LY-Content">
	<div class="LY-ContentTitle">
		<h1><img src="/images/content/Content_Title02_3.gif" alt="기타자료 - R&amp;D법령 관련 기타 규정 및 안내자료를 조회할 수 있습니다." /></h1>
	</div>
	<table border="0" cellspacing="0" cellpadding="0" class="Basic-Write">
		<colgroup>
			<col width="130px" />
			<col width="*" />
		</colgroup>
		<tr>
			<th class="Btmline">제목</th>
			<td>
				<input type="text" name="vo.title" class="Out_lineY W90" size="50" title="제목"/>
			</td>
		</tr>
		<tr>
			<th class="Btmline">내용</th>
			<td><textarea name="vo.contents" rows="18" class="Out_line W90" title="내용" ></textarea></td>
		</tr>
		<tr>
			<th class="Btmline">첨부파일</th>
			<td colspan="3">
				<a href="#" onclick="fncFileAddLenChk('fileArea', '<%=size%>')" class="btn_TLadd"><strong>파일추가</strong></a>
				<a href="#" onclick="fncFileDel('fileArea')" class="btn_TLdel"><strong>파일제거</strong></a>
				<div id="fileArea"></div>
			</td>
		</tr>
	</table>
	<div style="margin:10px 0; display:block">
		<ul class="Center">
			<div class="Basic-Button">
				<ul class="Center">
					<li><a href="JavaScript:Save()"  class="btn_Basic"><strong>등록</strong></a>
					<a href="JavaScript:history.back()" class="btn_Basic"><strong>취소</strong></a></li>
				</ul>	
			</div>
		</ul>
	</div>	
	</html:form>
	<!-- end 검색 테이블 감싸기 -->
	<br />
	<!-- end .ntiscontents -->
	</div>
<!-- end .container -->
</div>
<%@include file="/include/bottom.jsp"%>