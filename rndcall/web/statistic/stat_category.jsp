<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

    <bean:define id="path" type="java.lang.String" value="/Statistic .do"/>
    
<%
	int cnt1 = 0;
	int cnt2 = 0;
	int cnt3 = 0;
	int cnt4 = 0;
	int cnt5 = 0;
	int cnt1_1 = 0;
	int cnt2_1 = 0;
	int cnt3_1 = 0;
	int cnt4_1 = 0;
	int cnt5_1 = 0;
	
	int count=0;
	String old_cate="";
	String old_cate_nm="";
	String new_category2= "";
%>

	<script type="text/javascript" src="/js/FusionCharts.js"></script>
	<script type="text/javascript" src="/js/rowspan-bacchusl.js"></script>
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
		activeDebug = true;  
		module = '/switch.do?prefix=/statistic&method=getStatCategory&page=';
				
		window.onload=function(){
// 			cellMergeChk(iaAdminSubjSub, 2, 0);		//첫번째 td 처리
		}
	
		function goCategoryL(){
			fm.elements["method"].value="getStatCategory";
			fm.submit();
		}
		
		function goDateL(){
			fm.elements["method"].value="getStatDate";
			fm.submit();
		}
		
		function goVisitL(){
			fm.elements["searchVO.stat_type"].value="YY";
			fm.elements["method"].value="getStatVisit";
			fm.submit();
		}
		
		function goExcelDown(arg){
			fm.elements["searchVO.down_type"].value=arg;	
			fm.elements["method"].value="getStatExcelDownLoad";
			fm.submit();
		}
		
		function goStatList(arg1, arg2, arg3){
			var width = '900';
		    var height = '560';
		    var left = (screen.width - width)/2;
		    var top = (screen.height - height)/2;
		   	var winNM = 'getStatList';
		   	var url = '/switch.do?prefix=/statistic&method=getStatCategory&page=/Statistic.do?method=getStatList&searchVO.whichSearch='+arg1+'&searchVO.category1='+arg2+'&searchVO.category2='+arg3;  
		    var windowFeatures = "width=" + width + ",height=" + height +
		        ",status,resizable,scrollbars=yes,left=" + left + ",top=" + top +
		        ",screenX=" + left + ",screenY=" + top;
		   	var formObjNM = 'fm';
		    var win = window.open(url, winNM, windowFeatures);
		}
		
		function showChart(){
			var chartObj = new FusionCharts("/charts/MSColumn3D.swf","chart0", "654", "200", "0", "30");
			var chartUrl = "/statistic/charts/chart0.jsp";
	
			chartObj.setDataURL(chartUrl);
			chartObj.render("chart0");
		}
			
		function goSearch(){
			fm.elements["method"].value="getStatCategory";
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
                <li><a href="/switch.do?prefix=/offer&page=/Offer.do?method=adminOfferList&searchVO.menu_sn=14">R&amp;D 신문고</a></li>
                <li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.menu_sn=09">회원관리</a></li>
                <li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=09">질문분야관리</a></li>
                <li><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm&searchVO.menu_sn=09">오프라인자료 등록</a></li>
                <li class="on"><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatCategory&searchVO.menu_sn=09">통계정보</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li class="on"><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatCategory&searchVO.menu_sn=09">관리자</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>통계정보</h3>
                    <p>R&amp;D 도우미센터의 관리자 화면입니다.</p>
                </div>
                <!-- board-write -->
                <div class="board-write mt30">
                    <div class="board-box">
                        <table summary="시작년도, 시작월, 종료년도, 종료월 검색 페이지">
                            <caption>검색 페이지</caption>
                            <colgroup>
                                <col width="16%"/>
                                <col width="*"/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th scope="row"><label for="info1">검색</label></th>
                                    <td>
                                        <html:select name="StatisticForm" property="searchVO.start_yy" styleId="searchVO.start_yy" style="width:100px;" title="시작년도" alt="시작년도">
                                            <html:options name="StatisticForm" property="yearListDesc"/>
                                        </html:select>
                                        <label for="searchVO.start_yy">년</label>
                                        <html:select name="StatisticForm" property="searchVO.start_mm" styleId="searchVO.start_mm" title="시작월" alt="시작월">
                                            <html:options name="StatisticForm" property="mon_list"/>
                                        </html:select>
                                        <label for="searchVO.start_mm">월</label>
                                        ~
                                        <html:select name="StatisticForm" property="searchVO.end_yy" styleId="searchVO.end_yy" style="width:100px;" title="종료년도" alt="종료년도">
                                            <html:options name="StatisticForm" property="yearListDesc"/>
                                        </html:select>
                                        <label for="searchVO.end_yy">년</label>
                                        <html:select name="StatisticForm" property="searchVO.end_mm" styleId="searchVO.end_mm" title="종료월" alt="종료월">
                                            <html:options name="StatisticForm" property="mon_list"/>
                                        </html:select>
                                        <label for="searchVO.end_mm">월</label>
                                        <span class="btn-set set2 navy"><a href="javascript:goSearch()">검색</a><span class="zoom">아이콘 실제 사용시 텍스트 삭제</span></span>
                                    </td>
                                </tr>
                            </tbody>
                          </table>
                    </div>
                </div>
                <!-- // board-write -->
                <!-- tbl-type01 -->
                <div class="tbl-type01 mt30">
                    <table border="0" summary="질의자 소속기관별, 대분류별, 소분류별, 등록건수, 처리건수, 미처리건수 목록 페이지">
                        <caption>통계정보 목록 페이지</caption>
                        <colgroup>
                            <col width="*" />
                            <col width="11%" />
                            <col width="11%" />
                            <col width="9%" />
                            <col width="9%" />
                            <col width="9%" />
                            <col width="11%" />
                            <col width="11%" />
                        </colgroup>
                        <thead>
                            <tr>
                                <th scope="col" rowspan="2">질의자 소속기관별</th>
                                <th scope="col" rowspan="2">대분류별</th>
                                <th scope="col" rowspan="2">소분류별</th>
                                <th scope="col" colspan="3">등록건수</th>
                                <th scope="col" rowspan="2">처리건수</th>
                                <th scope="col" rowspan="2">미처리건수</th>
                            </tr>
                            <tr class="on">
                                <th scope="col" class="line-l">전체건수</th>
                                <th scope="col">온라인</th>
                                <th scope="col">오프라인</th>
                            </tr>
                                
                        </thead>
                        <tbody>
                            <logic:empty name="StatisticForm" property="voList">
                                <tr><td colspan="8" style="text-align: center;">등록된 정보가 없습니다.</td></tr>
                            </logic:empty>
                            <logic:notEmpty name="StatisticForm" property="voList">
                                <logic:iterate name="StatisticForm" property="voList" indexId="rowNum" id="vo">
									<bean:define name="vo" property="category1" id="category1" type="java.lang.String"/>
									<bean:define name="vo" property="category2" id="category2" type="java.lang.String"/>                    
									<bean:define name="vo" property="category1_nm" id="category1_nm" type="java.lang.String"/>
									<bean:define name="vo" property="total_cnt" id="total_cnt" type="java.lang.Integer"/>
									<bean:define name="vo" property="online_cnt" id="online_cnt" type="java.lang.Integer"/>
									<bean:define name="vo" property="offline_cnt" id="offline_cnt" type="java.lang.Integer"/>
									<bean:define name="vo" property="disposal_cnt" id="disposal_cnt" type="java.lang.Integer"/>                     
									<bean:define name="vo" property="undisposal_cnt" id="undisposal_cnt" type="java.lang.Integer"/>
                                    <%
										cnt1 += total_cnt.intValue();
										cnt2 += online_cnt.intValue();
										cnt3 += offline_cnt.intValue();
										cnt4 += disposal_cnt.intValue();
										cnt5 += undisposal_cnt.intValue();
										if ( category1.equals(category2) ) {
										    new_category2 ="";
										} else {
										    new_category2 = category2;
										}
										if ( category1.equals(old_cate) ) {
										    cnt1_1 += total_cnt.intValue();
										    cnt2_1 += online_cnt.intValue();
										    cnt3_1 += offline_cnt.intValue();
										    cnt4_1 += disposal_cnt.intValue();
										    cnt5_1 += undisposal_cnt.intValue();
										} else {
										    if ( !old_cate.equals("") && !old_cate.equals("1") && !old_cate.equals("2") && !old_cate.equals("6") ) {
                                    %>
<!--                                     <tr> -->
<%-- 										<td style="text-align:left;"><%=old_cate_nm %></td> --%>
<!-- 										<td style="text-align:left;"><b>소계</b></td> -->
<%-- 										<td style="text-align:center;"><a href="JavaScript:goStatList('SUB_T','<%=old_cate%>','')"><b><%=cnt1_1%></b></a></td> --%>
<%-- 										<td style="text-align:center;"><a href="JavaScript:goStatList('SUB_ON','<%=old_cate%>','')"><b><%=cnt2_1%></b></a></td> --%>
<%-- 										<td style="text-align:center;"><a href="JavaScript:goStatList('SUB_OFF','<%=old_cate%>','')"><b><%=cnt3_1%></b></a></td> --%>
<%-- 										<td style="text-align:center;"><a href="JavaScript:goStatList('SUB_Y','<%=old_cate%>','')"><b><%=cnt4_1%></b></a></td>                       --%>
<%-- 										<td style="text-align:center;"><a href="JavaScript:goStatList('SUB_N','<%=old_cate%>','')"><b><%=cnt5_1%></b></a></td>   --%>
<!--                                     </tr> -->
                                    <%
	                                        }
											cnt1_1 = 0;
											cnt2_1 = 0;
											cnt3_1 = 0;
											cnt4_1 = 0;
											cnt5_1 = 0;
											cnt1_1 += total_cnt.intValue();
											cnt2_1 += online_cnt.intValue();
											cnt3_1 += offline_cnt.intValue();
											cnt4_1 += disposal_cnt.intValue();
											cnt5_1 += undisposal_cnt.intValue();
											old_cate =category1;
											old_cate_nm=category1_nm;
										}
                                    %>
									<tr <%= rowNum.intValue() % 2 == 1 ? "class=\"on\"" : "" %>>
										<td><bean:write name="vo" property="category1_nm"/></td>
										<td><bean:write name="vo" property="category2_nm"/></td>
										<td><a href="JavaScript:goStatList('T','<bean:write name="vo" property="category1"/>','<%=new_category2%>')"><bean:write name="vo" property="total_cnt"/></a></td>
										<td><a href="JavaScript:goStatList('ON','<bean:write name="vo" property="category1"/>','<%=new_category2%>')"><bean:write name="vo" property="online_cnt"/></a></td>
										<td><a href="JavaScript:goStatList('OFF','<bean:write name="vo" property="category1"/>','<%=new_category2%>')"><bean:write name="vo" property="offline_cnt"/></a></td>
										<td><a href="JavaScript:goStatList('Y','<bean:write name="vo" property="category1"/>','<%=new_category2%>')"><bean:write name="vo" property="disposal_cnt"/></a></td>                        
										<td><a href="JavaScript:goStatList('N','<bean:write name="vo" property="category1"/>','<%=new_category2%>')"><bean:write name="vo" property="undisposal_cnt"/></a></td>
									</tr>
                                </logic:iterate>
                            </logic:notEmpty>
<!--                             // TODO 총건수 확인 필요 -->
<!--                             <tr bgcolor="#e0ffff"><td class="center" style="display:none"></td> -->
<!-- 								<td style="text-align:left;" colspan="2"><b>총건수</b></td> -->
<%-- 								<td style="text-align:center;"><b><%=cnt1%></b></td> --%>
<%-- 								<td style="text-align:center;"><b><%=cnt2%></b></td> --%>
<%-- 								<td style="text-align:center;"><b><%=cnt3%></b></td> --%>
<%-- 								<td style="text-align:center;"><b><%=cnt4%></b></td>                         --%>
<%-- 								<td style="text-align:center;"><b><%=cnt5%></b></td> --%>
<!--                             </tr> -->
                        </tbody>
                    </table>
                </div>
                <!-- // board-type01 -->
            </div>  
            <!-- //section -->  
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->
    
<%@include file="/include/bottom.jsp"%>	