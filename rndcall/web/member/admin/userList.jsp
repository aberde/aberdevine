<%@page contentType="text/html; charset=utf-8"%>

<%@include file="/include/top.jsp"%>

	<bean:define name="memberAdminForm" property="errCd" id="errCd" type="java.lang.String"/>
	<bean:define name="memberAdminForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
	<bean:define name="memberAdminForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
	<bean:define name="memberAdminForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
	<bean:define name="memberAdminForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>

    <bean:define id="path" type="java.lang.String" value="/memberAdmin.do"/>

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
        module = '/switch.do?prefix=&page=';

		function search() {
			fm.method.value = "getUserList";
			fm.submit();
		}

		function getUserInfo(id) {
			fm.elements["searchVO.auth_id"].value = id;
			fm.method.value = "getUserInfo";
			fm.submit();
		}
		
		function pageSubmit(fmObj, module, pageUrl) {
		    fmObj.action = module + pageUrl;
		    fmObj.method.value = "getUserList";
		    fmObj.submit();
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
                <li class="on"><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.roleCD=&searchVO.search_sel=&searchVO.search_word=&searchVO.menu_sn=09">회원관리</a></li>
                <li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=09">질문분야관리</a></li>
                <li><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm&searchVO.menu_sn=09">오프라인자료 등록</a></li>
                <li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatCategory&searchVO.menu_sn=09">통계정보</a></li>
                <li><a href="#none;">온라인 상담</a></li>
                <li><a href="#none;">R&D 신문고</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="#none;"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li class="on"><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatCategory&searchVO.menu_sn=09">관리자</a></li>
                </ul>
            </div>
            
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>회원관리</h3>
                    <p>R&amp;D 도우미센터의 관리자 화면입니다.</p>
                </div>
                
                <html:form action="/memberAdmin" method="post" name="fm" type="kr.go.rndcall.mgnt.member.admin.MemberAdminForm">
	                <html:hidden name="memberAdminForm" property="method" value="getUserList"/>
	                <html:hidden name="memberAdminForm" property="orderByTarget"/>
	                <html:hidden name="memberAdminForm" property="orderByMethod"/>
	                <html:hidden name="memberAdminForm" property="maxIndexPages"/>
	                <html:hidden name="memberAdminForm" property="maxPageItems"/>
	                <html:hidden name="memberAdminForm" property="pagerOffset"/>
	                <html:hidden name="memberAdminForm" property="searchVO.auth_id"/>
	                <html:hidden name="memberAdminForm" property="searchVO.menu_sn" value="<%= menu_sn %>"/>
                
	                <!-- search-box -->
	                <div class="search-box">
	                    <div class="search-form">
	                        <html:select name="memberAdminForm" property="searchVO.search_sel" style="width:90px;">
			                    <html:option value="">전체</html:option>
			                    <html:option value="nm">이름</html:option>
			                    <html:option value="id">아이디</html:option>
			                </html:select>
	                        
	                        <html:text name="memberAdminForm" property="searchVO.search_word" maxlength="30" title="검색어를 입력하세요" />
	                        <a href="javascript:search();" class="search-btn"><img src="/img/sub/icon_zoom.gif" alt="검색" /></a>
	                    </div>
	                </div>
	                <!-- //search-box -->
	                <!-- board-type01 -->
	                <div class="board-type01 mt20">
	                    <div class="board-box">
	                        <table border="0" summary="번호, ID, 이름, 소속기관, 부서, 권한, 목록 페이지">
	                            <caption>회원관리 목록 페이지</caption>
	                            <colgroup>
	                                <col width="7%" />
	                                <col width="10%" />
<!-- 	                                <col width="12%" /> -->
	                                <col width="*" />
	                                <col width="12%" />
	                                <col width="12%" />
	                                <col width="12%" />
	                            </colgroup>
	                            <thead>
	                                <tr>
	                                    <th scope="col">번호</th>
	                                    <th scope="col">ID</th>
<!-- 	                                    <th scope="col">이름</th> -->
	                                    <th scope="col">소속기관</th>
	                                    <th scope="col">부서</th>
	                                    <th scope="col">권한</th>
	                                    <th scope="col">수정</th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                                <logic:empty name="memberAdminForm" property="voList">
                                        <tr>
                                            <td colspan="7">조건에 해당하는 회원이 없습니다.</td>
                                        </tr>
                                    </logic:empty>  
                                    <logic:notEmpty name="memberAdminForm" property="voList">
                                        <logic:iterate name="memberAdminForm" property="voList" id="vo" indexId="rowNum">
                                            <tr <%= rowNum.intValue() % 2 == 1 ? "class=\"on\"" : "" %>>
                                                <td><%= Integer.valueOf(pagerOffset).intValue() + rowNum.intValue() + 1 %></td>
                                                <td><bean:write name="vo" property="login_id"/></td>
<%--                                                 <td><bean:write name="vo" property="name"/></td> --%>
                                                <td><bean:write name="vo" property="org_nm"/></td>
                                                <td><bean:write name="vo" property="attached_nm"/></td>
                                                <td><bean:write name="vo" property="roleCD"/></td>
                                                <td><span class="btn-set"><a href="javascript:getUserInfo('<bean:write name="vo" property="auth_id"/>');">수정</a></span></td>
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
	                
	            </html:form>
	            
            </div>  
            <!-- //section -->  
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->
    
<%@include file="/include/bottom.jsp"%>