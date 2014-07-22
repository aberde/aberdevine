<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

    <bean:define name="NoticeForm" property="errCd" id="errCd" type="java.lang.String"/>
    
    <bean:define id="path" type="java.lang.String" value="/Notice.do"/>

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
		window.onload= function() { // onload 시 호출. 데이터 초기화.
	    	if("<%=errCd%>" == "true"){
	    		alert("정상적으로 등록되었습니다.");
	    		document.location.href="/switch.do?prefix=/notice&page=/Notice.do?method=noticeList&searchVO.menu_sn=04";
	    	}else if("<%=errCd%>" == "false"){    	
	    		alert("등록이 실패하였습니다.");
	    		return;
	    	}
		};
		
		function fncFileAddLenChk(fileObjName, size){
		   
		    var fileArea = document.getElementById(fileObjName);
		    var childNds = fileArea.childNodes;
		    
		    //alert(childNds.length);
		    if(childNds.length < 20) //최대 3개까지 첨부 가능
		        fncFileAdd(fileObjName, size);
		}
		function Save(){
			if(validate()){
				fm.elements["method"].value="noticeInsert";
				fm.submit();
			}
		}
		
		function goCancel(){
			document.location.href="/index.jsp";
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
                <h2>새소식</h2>
                <span><img src="/img/common/h2_entxt04.gif" alt="새소식" /></span>
            </div>
            <ul class="lnb-lst">
                <li class="on"><a href="JavaScript:goNotice()">새소식</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href="JavaScript:goNotice()">새소식</a></li>
                    <li class="on"><a href="JavaScript:goNotice()">새소식</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <html:form action="/Notice" method="post" name="fm" type="kr.go.rndcall.mgnt.notice.form.NoticeForm" enctype="multipart/form-data" onsubmit="return checkOnSubmit(this)">
					<html:hidden name="NoticeForm" property="method" value="noticeInsert"/>
					<html:hidden name="NoticeForm" property="searchVO.loginId"/>
					<html:hidden name="NoticeForm" property="searchVO.name"/>
					<html:hidden name="NoticeForm" property="searchVO.menu_sn"/>
    
	                <!-- board-detail -->
	                <div class="board-detail mt60">
                        <div class="board-box">
                            <table summary="새소식 페이지">
                                <caption>새소식 페이지</caption>
	                            <colgroup>
	                                <col width="16%"/>
	                                <col width="*"/>
	                            </colgroup>
	                            <tbody>
	                                <tr>
	                                    <th scope="row"><label for="ip1">제목</label></th>
	                                    <td>
	                                        <select style="width:90px;" id="ip1" >
	                                            <option>공지</option>
	                                            <option>행사</option>
	                                            <option>기타</option>
	                                        </select>
	                                        <input type="text" name="vo.title" style="width:82%;" title="제목"/>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row"><label for="txtarea1">내용</label></th>
	                                    <td>
	                                        <textarea name="vo.contents" cols="0" rows="0" style="width:97%; min-height:154px;"></textarea>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row"><label for="file">첨부파일</label></th>
	                                    <td>
	                                        <span class="btn-set set2 black"><a href="javascript:fncFileAddLenChk('fileArea', '<%=size%>')">파일첨부</a></span>
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