<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/include/top_pop.jsp"%>

	<bean:define name="categoryForm" property="errCd" id="errCd" type="java.lang.String"/>
	<bean:define name="categoryForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>

    <bean:define id="path" type="java.lang.String" value="/category.do"/>

<%
    boolean mainIsLogin =false;
	LoginVO mainLoginVO = (LoginVO) session.getAttribute("loginUserVO");
	if(mainLoginVO != null && mainLoginVO.getLogin_id()!=null && !mainLoginVO.getLogin_id().equals("guest")) {
	    mainIsLogin = true;
	}

	String mainRoleCD = "guest";
	String nameKO = "";
	String login_id = "";
	String menu_sn = "";
  
	menu_sn = (String) request.getParameter("searchVO.menu_sn");
	System.out.println("111111111111111111111111"+ (String)request.getAttribute("searchVO.menu_sn"));
    if(menu_sn =="" || menu_sn==null || menu_sn == "null") menu_sn = (String)request.getAttribute("searchVO.menu_sn");
  
    System.out.println(" top 페이지 menu_sn::"+menu_sn);

	if (mainLoginVO != null && mainIsLogin) {	
		mainRoleCD = mainLoginVO.getRoleCD();
		nameKO = mainLoginVO.getName();
		login_id = mainLoginVO.getLogin_id();
	}
%>

<%
	if (mainLoginVO == null || !mainIsLogin) {	
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
		function onLoad() {
			if ('UPDATE' == '<%= errCd %>') {
				alert('정상적으로 수정되었습니다.');
				opener.location.reload();
			}
		}	
		
		function update(id) {
			if(confirm("질문분야를 수정하시겠습니까?")){
				fm.elements["searchVO.crud"].value = "UPDATE";
				fm.method.value = "getCategoryInfo";
				fm.submit();
			}
		}
	//-->
	</script>

</head>
<!--[if lt IE 7]>  <body onload="onLoad()" class="no-js ie ie6 lte9 lte8 lte7"> <![endif]-->
<!--[if IE 7]>     <body onload="onLoad()" class="no-js ie ie7 lte9 lte8 lte7"> <![endif]-->
<!--[if IE 8]>     <body onload="onLoad()" class="no-js ie ie8 lte9 lte8"> <![endif]-->
<!--[if IE 9]>     <body onload="onLoad()" class="no-js ie ie9 lte9"> <![endif]-->
<!--[if gt IE 9]>  <body onload="onLoad()" class="no-js">  <![endif]-->
<!--[if !IE]><!--> <body onload="onLoad()"> <!--<![endif]-->

    <html:form action="/category" method="post" name="fm" type="kr.go.rndcall.mgnt.category.CategoryForm">
	    <html:hidden name="categoryForm" property="method" value="getCategoryInfo"/>
	    <html:hidden name="categoryForm" property="searchVO.crud"/>
	    <html:hidden name="categoryForm" property="vo.category_id"/>
    
	    <!-- pop -->
	    <div id="pop-join">
	        <div class="tit">
	            <strong>분야정보 수정</strong>
	            <!-- <p>아이디 중복확인</p> -->
	        </div>
	        <!-- board-write -->
	        <div class="board-write">
	            <div class="board-box">
	                <table summary="분야정보 수정">
	                    <caption>분야정보 수정</caption>
	                    <colgroup>
	                        <col width="20%"/>
	                        <col width="*"/>
	                    </colgroup>
	                    <tbody>
	                        <tr>
	                            <th scope="row"><label for="ip1">번호</label></th>
	                            <td>
	                                <html:text styleId="ip1" name="categoryForm" property="vo.order_no" maxlength="4" style="width:50px" />
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row"><label for="ip2">분야명</label></th>
	                            <td>
	                                <html:text styleId="ip2" name="categoryForm" property="vo.category_nm" maxlength="100" style="width:200px" />
	                            </td>
	                        </tr>
	                    </tbody>
	                 </table>
	            </div>
	        </div>
	        <!-- // board-write -->
	        <!-- btn-set -->
	        <div class="btn-lst txt-c">
	            <span class="btn-set green"><a href="javascript:update();">저장</a></span>
	            <span class="btn-set"><a href="javascript:window.close();" onclick="popClose()">창닫기</a></span>
	        </div>
	        <!-- //btn-set-->
	        <a href="#" class="btn-close" onclick="window.close();"><span >닫기</span></a>
	    </div>
	    <!-- // pop -->
	    
	</html:form>
    
</body>
</html>
