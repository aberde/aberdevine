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
	int cnt6 = 0;
	int cnt7 = 0;
	int cnt8 = 0;
	int cnt9 = 0;
	int cnt1_1 = 0;
	int cnt2_1 = 0;
	int cnt3_1 = 0;
	int cnt4_1 = 0;
	int cnt5_1 = 0;
	int cnt6_1 = 0;
	int cnt7_1 = 0;
	int cnt8_1 = 0;
	int cnt9_1 = 0;
	
	int count=0;
	String old_cate="";
    String old_cate_nm="";
    String new_category2= "";
    String old_cate2="";
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

		function goCategory2L(){
			fm.elements["method"].value="getStatCategory2";
			fm.submit();
		}

		function goBoardTypeL(){
			fm.elements["method"].value="getStatBoardType";
			fm.submit();
		}

		function goQueryUserInfoL(){
			fm.elements["method"].value="getStatQueryUserInfo";
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
			fm.elements["method"].value="getStatCategory";
			fm.submit();
		}
		
		function goUserSectorL(){
            fm.elements["method"].value="getStatUserSector";
            fm.submit();
        }
		
		$(document).ready(function (){
			mergeCell(document.getElementById('tbl0'), '1', '0', '1');
		});
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
                        <li class="on"><a href="JavaScript:goCategory2L()">분류별 통계2</a></li>
                        <li><a href="JavaScript:goQueryUserInfoL()">소속별 통계</a></li>
                        <li><a href="JavaScript:goVisitL()">접속자 현황</a></li>
                        <li><a href="JavaScript:goUserSectorL()">사용자 소속기관별 통계</a></li>
                    </ul>
                </div>
                
                <html:form action="/Statistic" method="post" name="fm" type="kr.go.rndcall.mgnt.statistic.form.StatisticForm">
				    <html:hidden name="StatisticForm" property="method" value="getStatCategory"/>
				    <html:hidden name="StatisticForm" property="searchVO.stat_type"/>
				    <html:hidden name="StatisticForm" property="searchVO.down_type"/>
				    <html:hidden name="StatisticForm" property="searchVO.menu_sn"/>
				    <html:hidden name="StatisticForm" property="searchVO.excelForm"/>
    
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
	                <div id="excelDiv" class="tbl-type02 mt30">
	                    <table id="tbl0" summary="질의자 소속기관별, 대분류별, 소분류별, 등록건수, 처리건수, 미처리건수 목록 페이지">
	                        <colgroup>
	                            <col width="12%" />
	                            <col width="*%" />
	                            <col width="7%" />
	                            <col width="7%" />
	                            <col width="7%" />
	                            <col width="7%" />
	                            <col width="7%" />
	                            <col width="7%" />
	                            <col width="7%" />
	                            <col width="7%" />
	                            <col width="7%" />
	                        </colgroup>
	                        <thead>
	                            <tr>
	                                <th scope="col" rowspan="3">대분류별</th>
	                                <th scope="col" rowspan="3">소분류별</th>
	                                <th scope="col" colspan="5">등록건수</th>
	                                <th scope="col" rowspan="2" colspan="2">처리</th>
	                                <th scope="col" rowspan="2" colspan="2">미처리</th>
	                            </tr>
	                            <tr class="on">
	                                <th scope="col" colspan="3" class="line-l">온라인</th>
	                                <th scope="col" rowspan="2">오프라인</th>
	                                <th scope="col" rowspan="2">총계</th>
	                            </tr>
	                            <tr class="on">
	                                <th scope="col" class="line-l">상담</th>
	                                <th scope="col">신문고</th>
	                                <th scope="col">합계</th>
	                                <th scope="col">상담</th>
	                                <th scope="col">신문고</th>
	                                <th scope="col">상담</th>
	                                <th scope="col">신문고</th>
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
										<bean:define name="vo" property="online_cnt" id="online_cnt" type="java.lang.Integer"/>
										<bean:define name="vo" property="online_rnd_cnt" id="online_rnd_cnt" type="java.lang.Integer"/>
										<bean:define name="vo" property="online_tot_cnt" id="online_tot_cnt" type="java.lang.Integer"/>
										<bean:define name="vo" property="offline_cnt" id="offline_cnt" type="java.lang.Integer"/>
										<bean:define name="vo" property="total_cnt" id="total_cnt" type="java.lang.Integer"/>
										<bean:define name="vo" property="answer_y_cnt" id="answer_y_cnt" type="java.lang.Integer"/>                     
										<bean:define name="vo" property="answer_rnd_y_cnt" id="answer_rnd_y_cnt" type="java.lang.Integer"/>
										<bean:define name="vo" property="answer_n_cnt" id="answer_n_cnt" type="java.lang.Integer"/>                     
										<bean:define name="vo" property="answer_rnd_n_cnt" id="answer_rnd_n_cnt" type="java.lang.Integer"/>
	                                    <%
		                                    cnt1 += online_cnt.intValue();
		                                    cnt2 += online_rnd_cnt.intValue();
		                                    cnt3 += online_tot_cnt.intValue();
		                                    cnt4 += offline_cnt.intValue();
		                                    cnt5 += total_cnt.intValue();
		                                    cnt6 += answer_y_cnt.intValue();
		                                    cnt7 += answer_rnd_y_cnt.intValue();
		                                    cnt8 += answer_n_cnt.intValue();
		                                    cnt9 += answer_rnd_n_cnt.intValue();
		                                    if(category1.equals(category2)){
		                                        new_category2 ="";
		                                    }else{
		                                        new_category2 = category2;
		                                    }
		                                    if(category1.equals(old_cate)){
		                                        cnt1_1 += online_cnt.intValue();
		                                        cnt2_1 += online_rnd_cnt.intValue();
		                                        cnt3_1 += online_tot_cnt.intValue();
		                                        cnt4_1 += offline_cnt.intValue();
		                                        cnt5_1 += total_cnt.intValue();
		                                        cnt6_1 += answer_y_cnt.intValue();
		                                        cnt7_1 += answer_rnd_y_cnt.intValue();
		                                        cnt8_1 += answer_n_cnt.intValue();
		                                        cnt9_1 += answer_rnd_n_cnt.intValue();
											} else {
											    if(!old_cate.equals("")&& !old_cate.equals("1")&&!old_cate.equals("2")&&!old_cate.equals("6") && !old_cate2.equals("")){
	                                    %>
	                                    <tr>
											<td><%=old_cate_nm %></td>
											<td>계</td>
											<td class="txt-r"><%= Util.getNumberFormat(cnt1_1) %></td>
											<td class="txt-r"><%= Util.getNumberFormat(cnt2_1) %></td>
											<td class="txt-r"><%= Util.getNumberFormat(cnt3_1) %></td>
											<td class="txt-r"><%= Util.getNumberFormat(cnt4_1) %></td>                      
											<td class="txt-r"><%= Util.getNumberFormat(cnt5_1) %></td>  
											<td class="txt-r"><%= Util.getNumberFormat(cnt6_1) %></td>  
											<td class="txt-r"><%= Util.getNumberFormat(cnt7_1) %></td>  
											<td class="txt-r"><%= Util.getNumberFormat(cnt8_1) %></td>  
											<td class="txt-r"><%= Util.getNumberFormat(cnt9_1) %></td>  
	                                    </tr>
	                                    <%
		                                        }
												cnt1_1 = 0;
												cnt2_1 = 0;
												cnt3_1 = 0;
												cnt4_1 = 0;
												cnt5_1 = 0;
												cnt6_1 = 0;
												cnt7_1 = 0;
												cnt8_1 = 0;
												cnt9_1 = 0;
												cnt1_1 += online_cnt.intValue();
                                                cnt2_1 += online_rnd_cnt.intValue();
                                                cnt3_1 += online_tot_cnt.intValue();
                                                cnt4_1 += offline_cnt.intValue();
                                                cnt5_1 += total_cnt.intValue();
                                                cnt6_1 += answer_y_cnt.intValue();
                                                cnt7_1 += answer_rnd_y_cnt.intValue();
                                                cnt8_1 += answer_n_cnt.intValue();
                                                cnt9_1 += answer_rnd_n_cnt.intValue();
												old_cate =category1;
												old_cate_nm=category1_nm;
											}
		                                    
		                                    old_cate2 = category2;
	                                    %>
										<tr <%= rowNum.intValue() % 2 == 1 ? "class=\"on\"" : "" %>>
											<td><bean:write name="vo" property="category1_nm"/></td>
											<td>
											    <logic:empty name="vo" property="category2_nm">
                                                        계
                                                </logic:empty>
                                                <logic:notEmpty name="vo" property="category2_nm">
                                                    <bean:write name="vo" property="category2_nm"/>
                                                </logic:notEmpty>
											</td>
											<td class="txt-r"><%= Util.getNumberFormat(online_cnt) %></td>
											<td class="txt-r"><%= Util.getNumberFormat(online_rnd_cnt) %></td>
											<td class="txt-r"><%= Util.getNumberFormat(online_tot_cnt) %></td>
											<td class="txt-r"><%= Util.getNumberFormat(offline_cnt) %></td>                        
											<td class="txt-r"><%= Util.getNumberFormat(total_cnt) %></td>
											<td class="txt-r"><%= Util.getNumberFormat(answer_y_cnt) %></td>
											<td class="txt-r"><%= Util.getNumberFormat(answer_rnd_y_cnt) %></td>
											<td class="txt-r"><%= Util.getNumberFormat(answer_n_cnt) %></td>
											<td class="txt-r"><%= Util.getNumberFormat(answer_rnd_n_cnt) %></td>
										</tr>
	                                </logic:iterate>
	                            </logic:notEmpty>
	                            <tr>
	                                <td colspan="2">총건수</td>
									<td class="txt-r"><%= Util.getNumberFormat(cnt1) %></td>
									<td class="txt-r"><%= Util.getNumberFormat(cnt2) %></td>
									<td class="txt-r"><%= Util.getNumberFormat(cnt3) %></td>
									<td class="txt-r"><%= Util.getNumberFormat(cnt4) %></td>                        
									<td class="txt-r"><%= Util.getNumberFormat(cnt5) %></td>
									<td class="txt-r"><%= Util.getNumberFormat(cnt6) %></td>
									<td class="txt-r"><%= Util.getNumberFormat(cnt7) %></td>
									<td class="txt-r"><%= Util.getNumberFormat(cnt8) %></td>
									<td class="txt-r"><%= Util.getNumberFormat(cnt9) %></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	                <!-- // board-type01 -->
	                
	                <div class="btn-lst txt-r">
                        <span class="btn-set green"><a href="javascript:goExcelDown('category2');">엑셀 다운로드</a></span>
                    </div>
	            </html:form>
	                
            </div>  
            <!-- //section -->  
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->
    
<%@include file="/include/bottom.jsp"%>	