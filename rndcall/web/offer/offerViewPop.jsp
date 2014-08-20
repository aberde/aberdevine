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
<bean:define name="OfferForm" property="searchVO" id="searchVO" type="kr.go.rndcall.mgnt.offer.vo.OfferSearchVO"/>
<bean:define name="OfferForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="OfferForm" property="vo.public_trans_yn" id="public_trans_yn" type="java.lang.String"/>
<bean:define name="OfferForm" property="vo.stat" id="stat" type="java.lang.String"/>
<bean:define name="OfferForm" property="voList" id="file_list" type="java.util.ArrayList"/>
<bean:define name="OfferForm" property="voList2" id="cateL" type="java.util.ArrayList"/>
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
        <html:form action="/Offer" method="post" name="fm" type="kr.go.rndcall.mgnt.offer.form.OfferForm">
            <html:hidden name="OfferForm" property="method" value="getInquireInsert"/>
            <html:hidden name="OfferForm" property="vo.cell_number"/>
            <html:hidden name="OfferForm" property="vo.email"/>
            <html:hidden name="OfferForm" property="searchVO.loginId"/>
            <html:hidden name="OfferForm" property="searchVO.name"/>
            <html:hidden name="OfferForm" property="searchVO.seq"/>
            <html:hidden name="OfferForm" property="searchVO.board_type"/>
            <html:hidden name="OfferForm" property="searchVO.searchCategory"/>
            <html:hidden name="OfferForm" property="searchVO.menu_sn"/>
            <html:hidden name="OfferForm" property="searchVO.pagerOffset"/>
            
            <!-- board-detail -->
            <div class="board-detail mt60">
                <div class="board-box">
                    <table summary="규정 및 제도에 대한 개선사항을 건의 페이지">
                        <caption>R&amp;D 신문고 페이지</caption>
                        <colgroup>
                            <col width="16%"/>
                            <col width="*"/>
                        </colgroup>
                        <tbody>
                            <tr>
                                <th scope="row">아이디</th>
                                <td>
                                    <logic:empty name="OfferForm" property="vo.reg_id">
                                           비회원
                                    </logic:empty>
                                    <logic:notEmpty name="OfferForm" property="vo.reg_id">
                                        <bean:write name="OfferForm" property="vo.reg_id" filter="false"/>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row">제목</th>
                                <td><bean:write name="OfferForm" property="vo.title" filter="false"/></td>
                            </tr>
                            <tr>
                                <th scope="row">등록일</th>
                                <td><bean:write name="OfferForm" property="vo.reg_dt"/></td>
                            </tr>
                            <tr class="comment">
                                <th scope="row">질의내용</th>
                                <td>
                                    <bean:write name="OfferForm" property="vo.contents" filter="false"/>
                                    <br/><br/>
                                    <logic:notEmpty name="OfferForm" property="voList">
                                            첨부파일 : 
                                        <logic:iterate name="OfferForm" property="voList" indexId="rowNum" id="attachVO">
                                            <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <%
                                if ( stat.equals("Y") ) {
                            %>
                            <tr class="comment">
                                <th scope="row" class="txt-blue">답변내용</th>
                                <td class="txt-blue">
                                    <bean:write name="OfferForm" property="vo.answerContents" filter="false"/>
                                    <br/><br/>
                                    <logic:notEmpty name="OfferForm" property="voList1">
                                            첨부파일 : 
                                        <logic:iterate name="OfferForm" property="voList1" indexId="rowNum" id="attachVO">
                                            <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <%
                                }
                            %>
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