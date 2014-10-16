<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

    <bean:define id="path" type="java.lang.String" value="/Statistic .do"/>
    
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
		
		function goCategory2L(){
            fm.elements["method"].value="getStatCategory2";
            fm.submit();
        }
		
		function goBoardTypeL(){
            fm.elements["method"].value="getStatBoardType";
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

		function goUserSectorL(){
            fm.elements["method"].value="getStatUserSector";
            fm.submit();
        }
		
		function goExcelDown(arg){
			fm.elements["searchVO.down_type"].value=arg;	
			fm.elements["method"].value="getStatExcelDownLoad";
			fm.elements["searchVO.excelForm"].value=$("#excelDiv").html();
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
			fm.elements["method"].value="getStatBoardType";
			fm.submit();
		}
		
		function goQueryUserInfoL(){
            fm.elements["method"].value="getStatQueryUserInfo";
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
                <li class="on"><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatBoardType&searchVO.menu_sn=09">통계정보</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li class="on"><a href="JavaScript:goBoardTypeL()">관리자</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <div class="tit-area">
                    <h3>통계정보</h3>
                    <p>R&amp;D 도우미센터의 관리자 화면입니다.</p>
                </div>
                
                <!--  tab-typ01 -->
                <div class="tab-type01 mt30">
                    <ul class="clearfix">
                        <li><a href="JavaScript:goBoardTypeL()">전체 통계</a></li>
                        <li><a href="JavaScript:goCategoryL()">분류별 통계</a></li>
                        <li><a href="JavaScript:goCategory2L()">분류별 통계</a></li>
                        <li><a href="JavaScript:goQueryUserInfoL()">소속별 통계</a></li>
                        <li><a href="JavaScript:goVisitL()">접속자 현황</a></li>
                        <li class="on"><a href="JavaScript:goUserSectorL()">사용자 소속기관별 통계</a></li>
                    </ul>
                </div>
                
                <div class="mt30">· 작성기준 : 가입회원 기준</div>
                
                <html:form action="/Statistic" method="post" name="fm" type="kr.go.rndcall.mgnt.statistic.form.StatisticForm">
				    <html:hidden name="StatisticForm" property="method" value="getStatCategory"/>
				    <html:hidden name="StatisticForm" property="searchVO.stat_type"/>
				    <html:hidden name="StatisticForm" property="searchVO.down_type"/>
				    <html:hidden name="StatisticForm" property="searchVO.menu_sn"/>
				    <html:hidden name="StatisticForm" property="searchVO.excelForm"/>
    
	                <!-- tbl-type01 -->
	                <div id="excelDiv" class="tbl-type02">
	                    <table id="tbl0" summary="질의자 소속기관별, 대분류별, 소분류별, 등록건수, 처리건수, 미처리건수 목록 페이지">
	                        <colgroup>
	                            <col width="15%" />
	                            <col width="14%" />
	                            <col width="15%" />
	                            <col width="14%" />
	                            <col width="14%" />
	                            <col width="14%" />
	                            <col width="14%" />
	                        </colgroup>
	                        <thead>
	                            <tr>
	                                <th scope="col">중앙행정기관</th>
	                                <th scope="col">전문기관</th>
	                                <th scope="col">정부출연연구기관</th>
	                                <th scope="col">대학</th>
	                                <th scope="col">기업</th>
	                                <th scope="col">기타</th>
	                                <th scope="col">무소속</th>
	                            </tr>
	                        </thead>
	                        <tbody>
	                            <logic:empty name="StatisticForm" property="voList">
	                                <tr><td colspan="6" style="text-align: center;">등록된 정보가 없습니다.</td></tr>
	                            </logic:empty>
	                            <logic:notEmpty name="StatisticForm" property="voList">
	                                <logic:iterate name="StatisticForm" property="voList" indexId="rowNum" id="vo">
										<bean:define name="vo" property="sector1" id="sector1" type="java.lang.Integer"/>
										<bean:define name="vo" property="sector2" id="sector2" type="java.lang.Integer"/>
										<bean:define name="vo" property="sector3" id="sector3" type="java.lang.Integer"/>
										<bean:define name="vo" property="sector4" id="sector4" type="java.lang.Integer"/>
										<bean:define name="vo" property="sector5" id="sector5" type="java.lang.Integer"/>
										<bean:define name="vo" property="sector6" id="sector6" type="java.lang.Integer"/>
										<bean:define name="vo" property="sector7" id="sector7" type="java.lang.Integer"/>
										
										<tr <%= rowNum.intValue() % 2 == 1 ? "class=\"on\"" : "" %>>
											<td class="txt-r"><%= Util.getNumberFormat(sector1) %></td>
											<td class="txt-r"><%= Util.getNumberFormat(sector2) %></td>
											<td class="txt-r"><%= Util.getNumberFormat(sector3) %></td>
											<td class="txt-r"><%= Util.getNumberFormat(sector4) %></td>                        
											<td class="txt-r"><%= Util.getNumberFormat(sector5) %></td>
											<td class="txt-r"><%= Util.getNumberFormat(sector6) %></td>
											<td class="txt-r"><%= Util.getNumberFormat(sector7) %></td>
										</tr>
	                                </logic:iterate>
	                            </logic:notEmpty>
	                        </tbody>
	                    </table>
	                </div>
	                <!-- // board-type01 -->
	                
	                <div class="btn-lst txt-r">
                        <span class="btn-set green"><a href="javascript:goExcelDown('user_sector');">엑셀 다운로드</a></span>
                    </div>
	            </html:form>
	                
            </div>  
            <!-- //section -->  
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->
    
<%@include file="/include/bottom.jsp"%>	