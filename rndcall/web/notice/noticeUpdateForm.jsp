<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

    <bean:define name="NoticeForm" property="voList" id="file_list" type="java.util.ArrayList"/>
    
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
		function noticeUpdate(){
			var del_file_id = "";
			if(validate()){
                <%
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
                %>
	   			fm.elements["vo.del_file_id"].value=del_file_id;
				fm.elements["method"].value="noticeUpdate";
				fm.submit();
			}
		}
	
		function fncFileAddLenChk(fileObjName, size){
		    var fileArea = document.getElementById(fileObjName);
		    var childNds = fileArea.childNodes;
		    
		    //alert(childNds.length);
		    if(childNds.length < 10) //최대 3개까지 첨부 가능
		        fncFileAdd(fileObjName, size);
		}
		
		function validate() {
			
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
		
		window.onload= function() { // onload 시 호출. 데이터 초기화.
		}
	//-->
	</script>

    <!-- container -->
    <div id="container">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>알림</h2>
                <span><img src="/img/common/h2_entxt04.gif" alt="알림" /></span>
            </div>
            <ul class="lnb-lst">
                <li class="on"><a href="JavaScript:goNotice()">알림</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href="JavaScript:goNotice()">알림</a></li>
                    <li class="on"><a href="JavaScript:goNotice()">알림</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>알림</h3>
                    <p>규정 및 제도에 대한 개선사항을 건의해주세요</p>
                </div>

                <html:form action="/Notice" method="post" name="fm" enctype="multipart/form-data" type="kr.go.rndcall.mgnt.notice.form.NoticeForm" onsubmit="return checkOnSubmit(this)">
				    <html:hidden name="NoticeForm" property="method" value="noticeUpdate"/>
				    <html:hidden name="NoticeForm" property="searchVO.loginId"/>
				    <html:hidden name="NoticeForm" property="searchVO.name"/>
				    <html:hidden name="NoticeForm" property="searchVO.seq"/>
				    <html:hidden name="NoticeForm" property="searchVO.board_type"/>
				    <html:hidden name="NoticeForm" property="searchVO.menu_sn"/>
				    <html:hidden name="NoticeForm" property="vo.del_file_id"/>
				    <html:hidden name="NoticeForm" property="vo.file_id"/>
    
                    <!-- board-detail -->
                    <div class="board-detail mt30">
                        <div class="board-box">
                            <table summary="알림 페이지">
                                <caption>알림 페이지</caption>
                                <colgroup>
                                    <col width="16%"/>
                                    <col width="*"/>
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="row"><label for="vo.title">제목</label></th>
                                        <td>
                                            <select style="width:90px;" id="ip1" >
                                                <option>공지</option>
                                                <option>행사</option>
                                                <option>기타</option>
                                            </select>
                                            <html:text name="NoticeForm" styleId="vo.title" property="vo.title" style="width:82%;" alt="제목" title="제목"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><label for="vo.contents">내용</label></th>
                                        <td>
                                            <html:textarea name="NoticeForm" styleId="vo.contents" property="vo.contents" cols="0" rows="0" style="width:97%; min-height:154px;" alt="질의 내용" title="질의 내용"/>
                                        </td>
                                    </tr>
                                    <logic:notEmpty name="NoticeForm" property="voList">
                                    <tr>
                                        <th scope="row"><label for="txtarea1">파일 삭제여부</label></th>
                                        <td>
                                            <logic:iterate name="NoticeForm" property="voList" indexId="rowNum" id="attachVO">                      
                                                <bean:define name="attachVO" property="file_id" id="file_id" type="java.lang.String"/>
                                                <bean:define name="attachVO" property="seq" id="seq" type="java.lang.String"/>
                                                <%
                                                    String f_id = file_id+"-"+seq;
                                                %>
                                                <input type="checkbox" name="file_del" value="<%=f_id%>"/><a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><img src="/images/icon/disk01.gif" alt="첨부파일"/><bean:write name="attachVO" property="file_nm" /></a><br/>
                                            </logic:iterate>
                                        </td>
                                    </tr>
                                    </logic:notEmpty>
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
                        <span class="btn-set pink"><a href="JavaScript:noticeUpdate()">저장</a></span>
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