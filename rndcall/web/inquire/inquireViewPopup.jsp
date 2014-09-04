<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="kr.go.rndcall.mgnt.inquire.vo.*" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@ taglib uri="/tags/taglibs-log" prefix="log"%>
<%@ page import="kr.go.rndcall.mgnt.common.Util" %>
<%@ page import="kr.go.rndcall.mgnt.login.LoginVO" %>
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
<bean:define name="InquireForm" property="searchVO" id="searchVO" type="kr.go.rndcall.mgnt.inquire.vo.InquireSearchVO"/>
<bean:define name="InquireForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="InquireForm" property="vo.public_trans_yn" id="public_trans_yn" type="java.lang.String"/>
<bean:define name="InquireForm" property="vo.stat" id="stat" type="java.lang.String"/>
<bean:define name="InquireForm" property="voList" id="file_list" type="java.util.ArrayList"/>
<bean:define name="InquireForm" property="voList2" id="cateL" type="java.util.ArrayList"/>
<bean:define id="path" type="java.lang.String" value="/Inquire.do"/>

<%@ include file="/include/top_pop.jsp"%>

    <script type="text/javascript">
    <!--        
    function goPrint(){
        window.print();
    }
    function downLoad(fileNM, saveFileNM, filePath, yn){
        fileDownLoad.elements["fileNM"].value = fileNM;
        fileDownLoad.elements["saveFileNM"].value = saveFileNM;
        fileDownLoad.elements["filePath"].value = filePath;
        fileDownLoad.elements["desCipher"].value = yn;
        fileDownLoad.submit();
    }
    
    //-->
    </script>
</head>
<body>
    <form name="fileDownLoad" method="post" action="/downLoad.do" >
        <input type="hidden" name="fileNM"/>
        <input type="hidden" name="saveFileNM"/>
        <input type="hidden" name="filePath"/>
        <input type="hidden" name="desCipher" value="N"/>
    </form>

    <!-- section -->
    <div class="section">       
        <div class="tit-area">
            <h3>온라인 상담</h3>
            <!-- <p>R&amp;D 관련 규정 및 제도에 대해 궁금하신 사항에 담당자가 답변해 드립니다.기존 답변을 검색 후 질의해주세요.</p> -->
        </div>
        <!--  explain-bx -->
        <div class="explain-bx mt10">
            <strong>국가연구개발사업 관련 공통법령 및 제도에 대해 궁금하신 사항에 답변해드립니다.</strong>
            <p>*기존 질의응답을 검색 후 질의해주시기 바랍니다.</p>
        </div>
        <!--  //explain-bx -->
        
        <html:form action="/Inquire" method="post" name="fm" type="kr.go.rndcall.mgnt.inquire.form.InquireForm">
            <html:hidden name="InquireForm" property="method" value="getInquireInsert"/>
            <html:hidden name="InquireForm" property="vo.cell_number"/>
            <html:hidden name="InquireForm" property="vo.email"/>
            <html:hidden name="InquireForm" property="searchVO.loginId"/>
            <html:hidden name="InquireForm" property="searchVO.name"/>
            <html:hidden name="InquireForm" property="searchVO.seq"/>
            <html:hidden name="InquireForm" property="searchVO.board_type"/>
            <html:hidden name="InquireForm" property="searchVO.searchCategory"/>
            <html:hidden name="InquireForm" property="searchVO.whichSearch"/>
            <html:hidden name="InquireForm" property="searchVO.searchTxt"/>             
            <html:hidden name="InquireForm" property="searchVO.menu_sn"/>
            <html:hidden name="InquireForm" property="searchVO.uni"/>
            <%
                if ( mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C") ) {
            %>
            <html:hidden name="InquireForm" property="searchVO.start_yy"/>
            <html:hidden name="InquireForm" property="searchVO.start_mm"/>
            <html:hidden name="InquireForm" property="searchVO.end_yy"/>
            <html:hidden name="InquireForm" property="searchVO.end_mm"/>
            
            <!-- board-detail -->
            <div class="board-detail mt30">
                <h3>등록자 정보</h3>
                <div class="board-box">
                    <table summary="제목, 분류, 질의내용, 답변내용  페이지">
                        <caption>자주묻는 질문 페이지</caption>
                        <colgroup>
                            <col width="15%"/>
                            <col width="20%"/>
                            <col width="15%"/>
                            <col width="20%"/>
                            <col width="15%"/>
                            <col width="*"/>
                        </colgroup>
                        <tbody>
                            <tr>
                                <th scope="row">아이디</th>
                                <td>
                                    <logic:empty name="InquireForm" property="vo.reg_id">
                                           비회원
                                    </logic:empty>
                                    <logic:notEmpty name="InquireForm" property="vo.reg_id">
                                        <bean:write name="InquireForm" property="vo.reg_id"/>
                                    </logic:notEmpty>
                                </td>
                                <th scope="row">소속</th>
                                <td>
                                    <logic:equal name="InquireForm" property="vo.query_user_info" value="1">중앙행정기관</logic:equal>
                                    <logic:equal name="InquireForm" property="vo.query_user_info" value="2">전문기관</logic:equal>
                                    <logic:equal name="InquireForm" property="vo.query_user_info" value="3">정부출연연구기관</logic:equal>
                                    <logic:equal name="InquireForm" property="vo.query_user_info" value="4">대학</logic:equal>
                                    <logic:equal name="InquireForm" property="vo.query_user_info" value="5">기업</logic:equal>
                                    <logic:equal name="InquireForm" property="vo.query_user_info" value="6">기타</logic:equal>
                                </td>
                                <th scope="row">공개여부</th>
                                <td>
                                    <bean:define name="InquireForm" property="vo.open_yn" id="open_yn" type="java.lang.String"/>
                                    <%
                                        if(open_yn.equals("Y")){
                                            out.println("공개");
                                        }else{
                                            out.println("비공개");
                                        }
                                    %>                  
                                </td>
                            </tr>
                        </tbody>
                     </table>
                </div>
            </div>
            <!-- //board-detail -->
            <%
                }
            %>
            
            <!-- board-detail -->
            <div class="board-detail mt10">
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
                                <td><bean:write name="InquireForm" property="vo.title" filter="false"/></td>
                            </tr>
                            <tr>
                                <th scope="row">등록일</th>
                                <td><bean:write name="InquireForm" property="vo.reg_dt"/></td>
                            </tr>
                            <tr class="comment">
                                <th scope="row">질의내용</th>
                                <td>
                                    <bean:write name="InquireForm" property="vo.contents" filter="false"/>
                                       <br/><br/>
                                       <logic:notEmpty name="InquireForm" property="voList">
                                               첨부파일 : 
                                           <logic:iterate name="InquireForm" property="voList" indexId="rowNum" id="attachVO">
                                               <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
                                           </logic:iterate>
                                       </logic:notEmpty>
                                </td>
                            </tr>
                            <tr class="comment">
                                <th scope="row" class="txt-blue">답변내용</th>
                                <td class="txt-blue">
                                    <bean:write name="InquireForm" property="vo.answerContents" filter="false"/>
                                       <br/><br/>
                                       <logic:notEmpty name="InquireForm" property="voList1">
                                               첨부파일 : 
                                           <logic:iterate name="InquireForm" property="voList1" indexId="rowNum1" id="attachVO">
                                               <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
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
                <span class="btn-set pink"><a href="JavaScript:goPrint();">인쇄</a></span>
                <span class="btn-set pink"><a href="JavaScript:self.close()">창닫기</a></span>
            </div>
            <!-- //btn-set-->
            
        </html:form>
    
    </div>
    <!-- //section -->

</body>