<%@page contentType="text/html; charset=utf-8"%>

<%@include file="/include/top.jsp"%>

	<bean:define name="categoryForm" property="errCd" id="errCd" type="java.lang.String"/>
	<bean:define name="categoryForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>

    <bean:define id="path" type="java.lang.String" value="/category.do"/>

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
    <script type="text/javascript">
    <!--
		function fn_delete(id) {
			if (confirm("하위 카테고리도 함께 삭제됩니다.\n그래도 삭제하시겠습니까?")) {
				fm.elements["vo.category_id"].value = id;
				fm.elements["searchVO.crud"].value = "DELETE";
				fm.method.value = "getCategoryList";
				fm.submit();
			}
		}

		function cate_update(id) {
			var url = "/switch.do?prefix=&page=/category.do?method=getCategoryInfo&searchVO.category_id=" + id; 
			popWinCenter(url,'Info',580,305,0,0,0,1);
		}

		function getDtlList(id) {
			fm.elements["searchVO.parent_id"].value = id;
			fm.elements["searchVO.crud"].value = "";
			fm.method.value = "getCategoryListDtl";
			fm.submit();
		}

		function insert() {
			if (fm.elements["vo.order_no"].value == "") {
				alert('정렬순번을 입력하십시오.');
			} else if (fm.elements["vo.category_nm"].value == "") {
				alert('카테고리명을 입력하십시오.');
			} else {
				if(confirm("질문분야를 등록하시겠습니까?")){
					fm.elements["searchVO.crud"].value = "INSERT";
					fm.elements["vo.tree_level"].value = "1";
					fm.elements["vo.parent_id"].value = "-1";
					fm.method.value = "getCategoryList";
					fm.submit();
				}
			}
		}

	    function popWinCenter(wname,fname,width,height,resizable,toolbar,scrollbar, type) {
	        var str = "height=" + height + ",innerHeight=" + height;
	        str += ",width=" + width + ",innerWidth=" + width;
	        var xc = 0;
	        var yc = 0;
	        if (window.screen) {
				var aw = screen.availWidth - 10;
				var ah = screen.availHeight - 30;

			    if( type == 1 ) {
			        var xc = 100;
			        var yc = (ah / 2) - 350;
			    } else if ( type == 2 ) {
			        var xc = 550;
			        var yc = (ah / 2) - 350;
			    }

				str += ",left=" + xc + ",screenX=" + xc;
				str += ",top=" + yc + ",screenY=" + yc;
			}

	        var opn=window.open(wname,fname,str+", resizable="+resizable+", toolbar="+toolbar+", scrollbars="+scrollbar);
	        opn.focus();
	    }

	    function search() {
	        fm.method.value = "getCategoryList";
	        fm.submit();
	    }

	    function checkNum(objNumBox){
	        var numBoxValue = objNumBox.value;
	
	        for(var i=0;i<numBoxValue.length;i++){
	            if(isNaN(numBoxValue.charAt(i))){
	                alert("숫자만 입력해주세요.");
	                objNumBox.value = '';
	                for(var j=0;j<i;j++){
	                    objNumBox.value += numBoxValue.charAt(j);
	                }
	                return;
	            }
	        }
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
                <li><a href="/switch.do?prefix=/offer&page=/Offer.do?method=adminOfferList&searchVO.menu_sn=14">R&amp;D 신문고</a></li>
                <li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=09">회원관리</a></li>
                <li class="on"><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=09">질문분야관리</a></li>
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
                    <li class="on"><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=09">관리자</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>질문분야관리</h3>
                    <p>R&amp;D 도우미센터의 관리자 화면입니다.</p>
                </div>
                
                <html:form action="/category" method="post" name="fm" type="kr.go.rndcall.mgnt.category.CategoryForm">
					<html:hidden name="categoryForm" property="method" value="getCategoryList"/>
					<html:hidden name="categoryForm" property="searchVO.parent_id"/>
					<html:hidden name="categoryForm" property="searchVO.category_id"/>
					<html:hidden name="categoryForm" property="searchVO.crud"/>
					<html:hidden name="categoryForm" property="vo.category_id"/>
					<html:hidden name="categoryForm" property="vo.parent_id"/>
					<html:hidden name="categoryForm" property="vo.tree_level"/>
					<html:hidden name="categoryForm" property="searchVO.menu_sn" value="<%= menu_sn %>"/>
    
	                <!-- search-box -->
	                <div class="search-box">
	                    <div class="search-form">
	                        <html:select name="categoryForm" property="searchVO.search_sel" style="width:90px;">
	                            <html:option value="nm">분야명</html:option>
	                        </html:select>
	                        <html:text name="categoryForm" property="searchVO.search_word" title="검색어를 입력하세요" maxlength="35" onchange="trim(this)"  />
	                        <a href="javascript:search()" class="search-btn"><img src="/img/sub/icon_zoom.gif" alt="검색" /></a>
	                    </div>
	                </div>
	                <!-- //search-box -->
	                <!-- board-type01 -->
	                <div class="board-type01 mt20">
	                    <div class="board-box">
	                        <table border="0" summary="번호, 분야코드, 분야명, 하위분야 목록 페이지">
	                            <caption>질문분야관리 목록 페이지</caption>
	                            <colgroup>
	                                <col width="7%" />
	                                <col width="13%" />
	                                <col width="*" />
	                                <col width="18%" />
	                                <col width="12%" />
	                                <col width="12%" />
	                            </colgroup>
	                            <thead>
	                                <tr>
	                                    <th scope="col">번호</th>
	                                    <th scope="col">분야코드</th>
	                                    <th scope="col">분야명</th>
	                                    <th scope="col">하위분야</th>
	                                    <th scope="col">수정</th>
	                                    <th scope="col">삭제</th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                                <logic:empty name="categoryForm" property="voList">
	                                    <tr>
	                                        <td colspan="6">등록된 카테고리가 없습니다.</td>
	                                    </tr>
	                                </logic:empty>
	                                <logic:notEmpty name="categoryForm" property="voList">
	                                    <logic:iterate name="categoryForm" property="voList" id="vo" indexId="rowNum">
	                                        <tr <%= rowNum.intValue() % 2 == 1 ? "class=\"on\"" : "" %>>
	                                            <td><bean:write name="vo" property="order_no"/></td>
	                                            <td><bean:write name="vo" property="category_id"/></td>
	                                            <td><bean:write name="vo" property="category_nm"/></td>
												<td><a href="javascript:getDtlList('<bean:write name="vo" property="category_id"/>');"><bean:write name="vo" property="dtl_cnt"/>건</a></td>
												<td><span class="btn-set"><a href="javascript:cate_update('<bean:write name="vo" property="category_id"/>');">수정</a></span></td>
												<td><span class="btn-set"><a href="javascript:fn_delete('<bean:write name="vo" property="category_id"/>');">삭제</a></span></td>
	                                        </tr>
	                                    </logic:iterate>
	                                </logic:notEmpty>
	                            </tbody>
	                        </table>
	                    </div>
	                </div>
	                <!-- // board-type01 -->
	                <div class="mt10">
	                    <html:text name="categoryForm" property="vo.order_no" size="4" maxlength="4" onkeyup="checkNum(this)"/>
	                    <span class="input-set"><html:text name="categoryForm" property="vo.category_nm" style="width:290px;" maxlength="70"/></span>              
	                    <span class="btn-set set2 green"><a href="javascript:insert();">저장</a></span>
	                </div>
	                <!-- page-area-->
<%-- 	                <%@include file="/include/page.jsp"%> --%>
	                <!-- //page-area -->
	                
	            </html:form>
	            
            </div>  
            <!-- //section -->  
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->

<%@include file="/include/bottom.jsp"%>
