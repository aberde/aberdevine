<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

    <bean:define name="StatisticForm" property="searchVO.stat_type" id="stat_type" type="java.lang.String"/>
    
    <bean:define id="path" type="java.lang.String" value="/Statistic .do"/>
    
<%
    int total_cnt=0;
    int count=0;
%>

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
		
		function goVisitForm(arg){
		<%
		    if(stat_type.equals("MM")){
		%>
		        fm.elements["searchVO.start_yy"].value="";
		<%
		    }
		%>  
		<%
		    if(stat_type.equals("DD")){
		%>
		        fm.elements["searchVO.start_yy"].value="";
		        fm.elements["searchVO.start_mm"].value="";
		<%
		    }
		%>      
		    
		    fm.elements["searchVO.stat_type"].value=arg;
		    fm.elements["method"].value="getStatVisit";
		    fm.submit();
		}
		
		function goSearch(){
		    var stat = fm.elements["stat_type"].value;
		
		    if(stat == "YY"){
		        fm.elements["searchVO.start_yy"].value="";
		        fm.elements["searchVO.start_mm"].value="";
		    }
		    if(stat == "MM"){
		        fm.elements["searchVO.start_mm"].value="";
		    }
		    
		    fm.elements["searchVO.stat_type"].value = stat;
		    fm.elements["method"].value="getStatVisit";
		    fm.submit();
		}
		function goDateL(){
		    fm.elements["method"].value="getStatDate";
		    fm.submit();
		}
		function goExcelDown(arg){
		    fm.elements["searchVO.down_type"].value=arg;    
		    fm.elements["method"].value="getStatExcelDownLoad";
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
                
                <!--  tab-typ01 -->
                <div class="tab-type01 mt30">
                    <ul class="clearfix">
                        <li><a href="JavaScript:goCategoryL()">등록현황통계</a></li>
                        <li class="on"><a href="JavaScript:goVisitL()">접속자현황</a></li>
                    </ul>
                </div>
                
                <html:form action="/Statistic" method="post" name="fm" type="kr.go.rndcall.mgnt.statistic.form.StatisticForm">
				    <html:hidden name="StatisticForm" property="method" value="getStatVisit"/>
				    <html:hidden name="StatisticForm" property="searchVO.stat_type"/>
				    <html:hidden name="StatisticForm" property="searchVO.down_type"/>   
				    <html:hidden name="StatisticForm" property="searchVO.menu_sn"/>
    
                    <!-- board-write -->
	                <div class="board-write mt30">
	                    <div class="board-box">
	                        <table summary="질의자 정보, 공개여부, 제목, 내용 보기 등록 페이지">
	                            <caption>온라인상담 등록 페이지 </caption>
	                            <colgroup>
	                                <col width="16%"/>
	                                <col width="*"/>
	                            </colgroup>
	                            <tbody>
	                                <tr>
	                                    <th scope="row"><label for="info1">검색</label></th>
	                                    <td>
	                                        <select name="stat_type" title="검색분류" style="width:100px;">
						                        <option value="YY" <% if(stat_type.equals("YY")){ %> selected<% } %>>년도별</option>
						                        <option value="MM" <% if(stat_type.equals("MM")){ %> selected<% } %>>월별</option>
						                        <option value="DD" <% if(stat_type.equals("DD")){ %> selected<% } %>>일별</option>
						                    </select>
	                                        <html:select name="StatisticForm" property="searchVO.start_yy" title="시작년도" alt="시작년도" style="width:100px;">
						                        <option value="">전체</option>
						                        <html:options name="StatisticForm" property="yearListDesc"/>
						                    </html:select>년
	                                        <html:select name="StatisticForm" property="searchVO.start_mm" title="시작월" alt="시작월">
						                        <option value="">전체</option>
						                        <html:options name="StatisticForm" property="mon_list"/>
						                    </html:select>월
	                                        
	                                        <span class="btn-set set2 navy"><a href="javascript:goSearch()">검색</a><span class="zoom"></span></span>
	                                    </td>
	                                </tr>
	                            </tbody>
	                          </table>
	                    </div>
	                </div>
	                <!-- // board-write -->
	                <!-- board-type01 -->
	                <div class="board-type01 mt20">
	                    <div class="board-box">
	                        <table border="0" summary="작성자, 제목, 이메일, 내용 쓰기페이지">
	                            <caption>쓰기 페이지</caption>
	                            <colgroup>
	                                <col width="10%" />
	                                <col width="12%" />
	                                <col width="*" />
	                            <thead>
	                                <tr>
	                                    <th scope="col">순번</th>
	                                    <th scope="col">기준</th>
	                                    <th scope="col">방문자수</th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                                <logic:empty name="StatisticForm" property="voList">
						                <tr><td colspan="3">등록된 정보가 없습니다.</td></tr>
						            </logic:empty>
						            <logic:notEmpty name="StatisticForm" property="voList">
						                <logic:iterate name="StatisticForm" property="voList" indexId="rowNum" id="vo">                 
						                    <bean:define name="vo" property="cnt" id="cnt" type="java.lang.Integer"/>
						<%
						                        total_cnt +=  cnt.intValue();
						%>                  
						                    <tr>
						                        <td><%=++count %></td>
						                        <td><bean:write name="vo" property="code"/></td>
						                        <td><bean:write name="vo" property="cnt"/></td>
						                    </tr>
						                </logic:iterate>
						            </logic:notEmpty>
	                            </tbody>
						        <tfoor>
						            <tr>
						                <td></td>
						                <td>총계</td>
						                <td><%=total_cnt%></td>
						            </tr>
						        </tfoor>
	                        </table>
	                    </div>
	                </div>
	                <!-- // board-type01 -->
	                <!-- btn-set-->
	                <div class="btn-lst txt-r">
	                    <span class="btn-set green"><a href="javascript:goExcelDown('visit');">엑셀 다운로드</a></span>
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