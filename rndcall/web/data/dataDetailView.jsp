<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

	<bean:define name="DataForm" property="searchVO" id="searchVO" type="kr.go.rndcall.mgnt.data.vo.DataSearchVO"/>
	<bean:define name="DataForm" property="totRowCount" id="totRowCount" type="java.lang.Integer" />
	<bean:define name="DataForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String" />
	<bean:define name="DataForm" property="maxPageItems" id="maxPageItems" type="java.lang.String" />
	<bean:define name="DataForm" property="pagerOffset" id="pagerOffset" type="java.lang.String" />
	<bean:define name="DataForm" property="searchVO.loginId" id="loginId" type="java.lang.String" />
	<bean:define name="DataForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String" />
	<bean:define name="DataForm" property="searchVO.board_type" id="board_type" type="java.lang.String" />
	
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
    
<%
	String uni = searchVO.getUni();
%>
    <script type="text/javascript">
	<!--		
		function goDataList(){
			//fm.elements["method"].value="faqList";
			fm.action = "/switch.do?prefix=/data&method=dataList&page=/Data.do?pager.offset=" + fm.elements["searchVO.pagerOffset"].value;
			fm.submit();
		}
	
		function downLoad(fileNM, saveFileNM, filePath, yn){
		    fileDownLoad.elements["fileNM"].value = fileNM;
		    fileDownLoad.elements["saveFileNM"].value = saveFileNM;
		    fileDownLoad.elements["filePath"].value = filePath;
		    fileDownLoad.elements["desCipher"].value = yn;
		    fileDownLoad.submit();
		}
		
		function goDataUpdateForm(){
			fm.elements["method"].value="dataUpdateForm";
			fm.submit();
		}
		
		function goDataDelete(){
			if (confirm("정말로 삭제 하시겠습니까?")) {
				fm.elements["method"].value="dataDelete";
				fm.submit();
			}
		}
		
		function goIns(arg){
			fm.elements["searchVO.whichSearch"].value="";
			fm.elements["searchVO.searchTxt"].value="";
			fm.elements["searchVO.board_type"].value=arg;	
			fm.elements["method"].value="dataList";
			fm.submit();
		}
		
		function MM_findObj(n, d) { //v4.01
		  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
		  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
		  if(!x && d.getElementById) x=d.getElementById(n); return x;
		}
		
		function MM_swapImgRestore() { //v3.0
		  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
		}
		
		function MM_preloadImages() { //v3.0
		  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
		    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
		    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
		}
		
		function MM_swapImage() { //v3.0
		  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
		   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
		}
		
		function goIns(arg){
			fm.elements["searchVO.board_type"].value=arg;	
			if ( arg == "SYSTEM" ) {
                fm.elements["method"].value="dataSystemList";
            } else {
                fm.elements["method"].value="dataList";
            }
			fm.submit();
		}
	//-->
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
                <li><a href="JavaScript:goIns('SYSTEM')">연구관리제도</a></li>
                <li  class="on"><a href="JavaScript:goIns('DATA')">기타</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
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
                
                <html:form action="Data" method="post" name="fm" type="kr.go.rndcall.mgnt.data.form.DataForm">
				    <html:hidden name="DataForm" property="method" value="dataDetailView"/>
				    <html:hidden name="DataForm" property="searchVO.loginId"/>
				    <html:hidden name="DataForm" property="searchVO.name"/>
				    <html:hidden name="DataForm" property="searchVO.seq"/>
				    <html:hidden name="DataForm" property="searchVO.board_type"/>
				    <html:hidden name="DataForm" property="searchVO.whichSearch"/>
				    <html:hidden name="DataForm" property="searchVO.searchTxt"/>
				    <html:hidden name="DataForm" property="searchVO.menu_sn"/>
				    <html:hidden name="DataForm" property="searchVO.uni"/>
				    <html:hidden name="DataForm" property="searchVO.pagerOffset"/>
            
                    <!-- board-detail -->
                    <div class="board-detail mt30">
                        <div class="board-box">
                            <table summary="제목, 등록일, 질의내용, 답변내용 페이지">
                                <caption>온라인 상담 상세 페이지</caption>
                                <colgroup>
                                    <col width="16%"/>
                                    <col width="*"/>
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="row">제목</th>
                                        <td><bean:write name="DataForm" property="vo.title" filter="false"/></td>
                                    </tr>
                                    <tr class="comment">
                                        <th scope="row">내용</th>
                                        <td>
                                            <bean:write name="DataForm" property="vo.contents" filter="false"/>
                                            <br/><br/>
                                            <logic:notEmpty name="DataForm" property="voList">
                                                    첨부파일 : 
                                                <logic:iterate name="DataForm" property="voList" indexId="rowNum" id="attachVO">
                                                    <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');" ><bean:write name="attachVO" property="file_nm" /></a><br/>
                                                </logic:iterate>
                                            </logic:notEmpty>
                                        </td>
                                    </tr>
                                </tbody>
                             </table>
                        </div>
                    </div>      
                    <!-- //board-detail -->
                    <!-- btn-set-->
                    <div class="btn-lst txt-r">
                        <%
                            if ( roleCd.equals("0000Z") || roleCd.equals("0000C") ) {
                        %>
                        <span class="btn-set"><a href="JavaScript:goDataUpdateForm()">수정</a></span>
                        <span class="btn-set"><a href="JavaScript:goDataDelete()">삭제</a></span>
                        <% 
                            }
                        %>
                        <%
                            if ( uni.equals("uni") ) {
                        %>
                        <span class="btn-set"><a href="JavaScript:history.back()">목록</a></span>
                        <%
                            } else {
                        %>
                        <span class="btn-set"><a href="JavaScript:goDataList()">목록</a></span>
                        <%
                            }
                        %>
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