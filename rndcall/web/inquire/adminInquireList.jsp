<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.inquire.vo.*" %>

<%@include file="/include/top.jsp"%>

	<bean:define name="InquireForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
	<bean:define name="InquireForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
	<bean:define name="InquireForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
	<bean:define name="InquireForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
	<bean:define name="InquireForm" property="searchVO.searchCategory" id="searchCategory" type="java.lang.String"/>
	<bean:define name="InquireForm" property="voList2" id="cateL" type="java.util.ArrayList"/>

    <bean:define id="path" type="java.lang.String" value="/Inquire.do"/>

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
        if (mainLoginVO == null || !mainIsLogin) {  
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
    
    <script type="text/javascript">
	<!--
		activeDebug = true;  
		module = '/switch.do?prefix=/inquire&method=getAdminInquireList&page=';
		
		
		function goInquireMainList(){
			fm.elements["method"].value="getInquireMainList";
			fm.submit();
		}
		
		function goCate(arg){
        <%
            if ( mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C") ) {
        %>
			fm.elements["searchVO.start_yy"].value="";
			fm.elements["searchVO.start_mm"].value="";
			fm.elements["searchVO.end_yy"].value="";
			fm.elements["searchVO.end_mm"].value="";
        <%
            }
        %>
			fm.elements["searchVO.searchTxt"].value="";
			fm.elements["searchVO.whichSearch"].value="";
			fm.elements["searchVO.searchCategory"].value=arg;
			fm.elements["searchVO.type"].value="";
			fm.elements["method"].value="getInquireList";
			fm.submit();
		}

		function goInquireView(arg1, arg2){
			fm.elements["searchVO.seq"].value=arg2;	
			fm.elements["searchVO.board_type"].value=arg1;	
			fm.elements["method"].value="getInquireView";
			fm.submit();
		}

		function goStatList(arg1, arg2){
			fm.elements["searchVO.board_type"].value=arg1;
			fm.elements["searchVO.type"].value=arg2;
			fm.elements["searchVO.searchCategory"].value="";
			fm.elements["method"].value="getInquireList";
			fm.elements["searchVO.menu_sn"].value = "14";
			fm.submit();
		}

		function goStatList1(arg1, arg2){
			var url ="/switch.do?prefix=/offer&page=/Offer.do?method=offerList&searchVO.board_type="+arg1+"&searchVO.type="+arg2+"&searchVO.menu_sn=14";
			document.location.href = url;
		}

		function goSearch(){
		//	fm.elements["searchVO.searchCategory"].value=fm.elements["catr_list"].value;
			fm.elements["method"].value="getAdminInquireList";
			fm.submit();
		}


		function goExcelDown(){
			fm.elements["searchVO.board_type"].value='QNA';
			fm.elements["method"].value="getInquireExcelDown";
			fm.submit();
		}
		
		function goTrans_info(arg){
			var width = '430';
		    var height = '350';
		    var left = (screen.width - width)/2;
		    var top = (screen.height - height)/2;
		   	var winNM = 'trans_info';
		   	var url = '/switch.do?prefix=/inquire&page=/Inquire.do?method=getTrans_info&searchVO.trans_id='+arg;  
		    var windowFeatures = "width=" + width + ",height=" + height +
		        ",status,resizable,scrollbars=N,left=" + left + ",top=" + top +
		        ",screenX=" + left + ",screenY=" + top;
		   	window.open(url, winNM, windowFeatures);
		}
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
                <li class="on"><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getAdminInquireList&searchVO.board_type=QNA&searchVO.type=&searchVO.searchCategory=&searchVO.menu_sn=01">온라인 상담</a></li>
                <li><a href="/switch.do?prefix=/offer&page=/Offer.do?method=adminOfferList&searchVO.menu_sn=14">R&amp;D 신문고</a></li>
                <li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.roleCD=&searchVO.search_sel=&searchVO.search_word=&searchVO.menu_sn=09">회원관리</a></li>
                <li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=09">질문분야관리</a></li>
                <li><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm&searchVO.menu_sn=09">오프라인자료 등록</a></li>
                <li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatCategory&searchVO.menu_sn=09">통계정보</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="#none;"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li class="on"><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getAdminInquireList&searchVO.board_type=QNA&searchVO.type=&searchVO.searchCategory=&searchVO.menu_sn=01">관리자</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>온라인 상담</h3>
                </div>
                
                <html:form action="/Inquire" method="post" name="fm" type="kr.go.rndcall.mgnt.inquire.form.InquireForm">
		            <html:hidden name="InquireForm" property="method" value="getAdminInquireList"/>
		            <html:hidden name="InquireForm" property="vo.cell_number"/>
		            <html:hidden name="InquireForm" property="vo.email"/>
		            <html:hidden name="InquireForm" property="searchVO.loginId"/>
		            <html:hidden name="InquireForm" property="searchVO.name"/>
		            <html:hidden name="InquireForm" property="searchVO.board_type"/>
		            <html:hidden name="InquireForm" property="searchVO.seq"/>
		            <html:hidden name="InquireForm" property="searchVO.type"/>          
		            <html:hidden name="InquireForm" property="searchVO.menu_sn"/>
            
	                <!-- search-box -->
	                <div class="search-box mt10">
	                    <div class="search-form">
	                        <html:select name="InquireForm" property="searchVO.whichSearch" style="width:90px;">
                                <html:option value="reg_id">글쓴이</html:option>
                                <html:option value="title">제목</html:option>
                                <html:option value="contents">내용</html:option>
                                <html:option value="all">제목+내용</html:option>
                            </html:select>
                            <html:text name="InquireForm" property="searchVO.searchTxt" title="검색어를 입력하세요" maxlength="35" onchange="trim(this)"  />
	                        <a href="javascript:goSearch()" class="search-btn"><img src="/img/sub/icon_zoom.gif" alt="검색" /></a>
	                    </div>
	                </div>
	                <!-- //search-box -->
	                <!-- board-type01 -->
	                <div class="board-type01 mt10">
	                    <div class="board-box">
	                        <table border="0" summary="번호, 제목, 글쓴이, 등록일, 상태, 조회수 목록 페이지">
	                            <caption>온라인 상담 목록 페이지</caption>
	                            <colgroup>
	                                <col width="7%" />
	                                <col width="*" />
	                                <col width="8%" />
	                                <col width="12%" />
	                                <col width="12%" />
	                                <col width="8%" />
	                            </colgroup>
	                            <thead>
	                                <tr>
	                                    <th scope="col">번호</th>
	                                    <th scope="col">제목</th>
	                                    <th scope="col">글쓴이</th>
	                                    <th scope="col">등록일</th>
	                                    <th scope="col">상태</th>
	                                    <th scope="col">조회수</th>
	                                </tr>
	                            </thead>
	                            <tbody>
                                    <logic:empty name="InquireForm" property="voList">
                                        <tr><td colspan="6">등록된 정보가 없습니다.</td></tr>
                                    </logic:empty>
                                    <logic:notEmpty name="InquireForm" property="voList">
                                        <logic:iterate name="InquireForm" property="voList" indexId="rowNum" id="vo">
                                            <tr>
                                                <td><%= totRowCount.intValue() - rowNum.intValue() -  Util.replaceNull((String)pagerOffset, 0) %></td>
                                                <td class="txt-l">
                                                    <bean:define name="vo" property="title" id="title" type="java.lang.String"/>
                                                    <bean:define name="vo" property="reg_id" id="reg_id" type="java.lang.String"/>
                                                    <bean:define name="vo" property="open_yn" id="open_yn" type="java.lang.String"/>
                                                    <bean:define name="vo" property="reg_dt" id="reg_dt" type="java.lang.String"/>
                                                    <%
                                                        String title_n = "";
                                                        int len = 24;
                                                        if ( title.length() > len ) {
                                                            title_n = title.substring(0,len) + "...";
                                                        } else {
                                                            title_n = title;
                                                        }
                                                    %>
                                                    <%
                                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
                                                        Date compareDay = simpleDateFormat.parse(reg_dt);
                                                        Calendar cal = Calendar.getInstance();
                                                        cal.add(Calendar.DATE, -1);
                                                        
                                                        String new_img = "";
                                                        if ( compareDay.after(cal.getTime()) ) {
                                                            new_img = "<span><img src=\"/img/icon/new_icon.png\" alt=\"new\" /></span>";
                                                        }
                                                    %>
                                                    
                                                    <%
                                                        if ( "Y".equals(open_yn) ) {
                                                    %>
                                                    <a href="JavaScript:goInquireView('<bean:write name="vo" property="board_type"/>',<bean:write name="vo" property="seq"/>)"><%=title_n %> <%= new_img %></a>
                                                    <%
                                                        } else {
                                                            if ( (login_id != null && !login_id.isEmpty() && login_id.equals(reg_id)) || mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C") ) {
                                                    %>
                                                    <a href="JavaScript:goInquireView('<bean:write name="vo" property="board_type"/>',<bean:write name="vo" property="seq"/>)"><%=title_n %> <%= new_img %></a>
                                                    <%
                                                            } else {
                                                    %>
                                                    <span style="padding-left: 20px;">[비공개] <%=title_n %> <%= new_img %></span>
                                                    <%
                                                            }
                                                        }
                                                    %>               
                                                </td>
                                                <td>
                                                    <logic:notEmpty name="vo" property="reg_id">
                                                        <bean:write name="vo" property="reg_id"/>
                                                    </logic:notEmpty>
                                                    <logic:empty name="vo" property="reg_id">
                                                                비회원
                                                    </logic:empty>
                                                </td>
                                                <td><bean:write name="vo" property="reg_dt"/></td>
                                                <td>
                                                    <bean:define name="vo" property="stat" id="stat" type="java.lang.String"/>
                                                    <%
                                                        if ( stat.equals("Y") ) {
                                                            out.print("<span class=\"btn-set set4 gray\">답변완료</span>");
                                                        } else {
                                                            out.print("<span class=\"btn-set set4 yellow\">접수중</span>");
                                                        }
                                                    %>
                                                </td>
                                                <td><bean:write name="vo" property="read_count"/></td>
                                            </tr>
                                        </logic:iterate>
                                    </logic:notEmpty>
	                            </tbody>
	                        </table>
	                    </div>
	                </div>
	                <!-- // board-type01 -->
	                <!-- page-area-->
	                <%@include file="/include/page.jsp"%>
	                <!-- //page-area -->
	                <!-- btn-set-->
	                <div class="btn-lst txt-r">
	                    <span class="btn-set pink"><a href="JavaScript:goAdminInquireForm()">상담하기</a></span>
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