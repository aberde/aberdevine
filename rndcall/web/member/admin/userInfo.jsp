<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/include/top.jsp"%>

	<bean:define name="memberAdminForm" property="errCd" id="errCd" type="java.lang.String"/>
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
	}
%>

    <script type="text/javascript" src="/js/validate.js"></script>
    <script type="text/javascript">
    <!--
        window.onload = function() {
    	   if ('<bean:write name="memberAdminForm" property="searchVO.crud"/>' == 'UDC' && 'Y' == '<%= errCd %>' ) {
    		   alert('권한이 정상적으로 수정되었습니다.');
  		   }
        };

		function save(){
			fm.method.value = "getUserInfo";
			fm.elements["searchVO.crud"].value = "UPDT";
			fm.submit();
		}
		
		function gotoList() {
			fm.method.value = "getUserList";
			fm.submit();
		}
	//-->
    </script>
    
    <!-- container -->
    <div id="container">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>관리자</h2>
                <span><img src="/img/common/h2_admin_entxt.gif" alt="Online Consultation" /></span>
            </div>
            <ul class="lnb-lst">
                <li><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getAdminInquireList&searchVO.board_type=QNA&searchVO.type=&searchVO.searchCategory=&searchVO.menu_sn=01">온라인 상담</a></li>
                <li><a href="#none;">R&amp;D 신문고</a></li>
                <li class="on"><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.roleCD=&searchVO.search_sel=&searchVO.search_word=&searchVO.menu_sn=09">회원관리</a></li>
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
                    <li class="on"><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatBoardType&searchVO.menu_sn=09">관리자</a></li>
                </ul>
            </div>
            
            <html:form action="/memberAdmin" method="post" name="fm" type="kr.go.rndcall.mgnt.member.admin.MemberAdminForm">
			    <html:hidden name="memberAdminForm" property="method" value="getUserInfo"/>
			    <html:hidden name="memberAdminForm" property="orderByTarget"/>
			    <html:hidden name="memberAdminForm" property="orderByMethod"/>
			    <html:hidden name="memberAdminForm" property="maxIndexPages"/>
			    <html:hidden name="memberAdminForm" property="maxPageItems"/>
			    <html:hidden name="memberAdminForm" property="pagerOffset"/>
			    <html:hidden name="memberAdminForm" property="vo.auth_id"/>
			    <html:hidden name="memberAdminForm" property="searchVO.name"/>
			    <html:hidden name="memberAdminForm" property="searchVO.login_id"/>
			    <html:hidden name="memberAdminForm" property="searchVO.org_cd"/>
			    <html:hidden name="memberAdminForm" property="searchVO.roleCD"/>
			    <html:hidden name="memberAdminForm" property="searchVO.crud"/>
			    <html:hidden name="memberAdminForm" property="searchVO.menu_sn" value="<%= menu_sn %>"/>
			    
	            <!-- section -->
	            <div class="section">
	                <div class="tit-area">
	                    <h3>회원관리</h3>
	                    <p>R&amp;D 도우미센터의 관리자 화면입니다.</p>
	                </div>
	                <!-- board-write -->
	                <div class="board-write mt10">
	                    <div class="board-box">
	                        <table summary="회원가입">
	                            <caption>회원가입</caption>
	                            <colgroup>
	                                <col width="20%"/>
	                                <col width="*"/>
	                            </colgroup>
	                            <tbody>
	                                <tr>
	                                    <th scope="row">사용자 아이디</th>
	                                    <td>
	                                        <strong><bean:write name="memberAdminForm" property="vo.login_id" /></strong>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row"><label for="se02">소속기관</label></th>
	                                    <td>
	                                        <html:select styleId="se02" name="memberAdminForm" property="vo.org_cd" style="width:160px;">
							                    <html:option value="">없음</html:option>
							                    <html:optionsCollection name="memberAdminForm" property="orgCdList" label="org_nm" value="org_cd"/>
							                </html:select>
	                                    </td>
	                                </tr>
	                            </tbody>
	                         </table>
	                    </div>
	                </div>
	                <!-- // board-write -->
	                <!-- btn-set -->
	                <div class="btn-lst txt-r">
	                    <span class="btn-set green"><a href="javascript:save();">저장</a></span>
	                    <span class="btn-set"><a href="javascript:fm.reset();">취소</a></span>
	                    <span class="btn-set"><a href="javascript:gotoList();">목록</a></span>
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
