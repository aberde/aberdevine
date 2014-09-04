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
    <bean:define name="FaqForm" property="searchVO" id="searchVO" type="kr.go.rndcall.mgnt.faq.vo.FaqSearchVO"/>
    <bean:define name="FaqForm" property="searchVO.loginId" id="loginId" type="java.lang.String" />
    <bean:define name="FaqForm" property="searchVO.roleCD" id="roleCd" type="java.lang.String" />

    <bean:define id="path" type="java.lang.String" value="/Faq.do"/>

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
            <h3>자주묻는 질문</h3>
            <p>가장 많이 묻는 질의 응답을 카테고리별로 검색이 가능합니다.</p>
        </div>
        
        <html:form action="/Faq" method="post" name="fm" type="kr.go.rndcall.mgnt.faq.form.FaqForm" scope="request">
            <html:hidden name="FaqForm" property="method" value="faqInsert"/>
            <html:hidden name="FaqForm" property="vo.cell_number"/>
            <html:hidden name="FaqForm" property="vo.email"/>
            <html:hidden name="FaqForm" property="vo.title"/>
            <html:hidden name="FaqForm" property="searchVO.loginId"/>
            <html:hidden name="FaqForm" property="searchVO.name"/>
            <html:hidden name="FaqForm" property="searchVO.seq"/>
            <html:hidden name="FaqForm" property="searchVO.board_type"/>
            <html:hidden name="FaqForm" property="searchVO.searchCategory"/>
            <html:hidden name="FaqForm" property="searchVO.menu_sn"/>
            <html:hidden name="FaqForm" property="searchVO.uni"/>
            <html:hidden name="FaqForm" property="searchVO.pagerOffset"/>
    
            <!-- board-detail -->
            <div class="board-detail mt30">
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
                                <td><bean:write name="FaqForm" property="vo.title" filter="false"/></td>
                            </tr>
                            <tr>
                                <th scope="row">분류</th>
                                <td>
                                    <bean:write name="FaqForm" property="vo.category1"/>
                                    <logic:notEqual name="FaqForm" property="vo.category2" value="">
                                    - <bean:write name="FaqForm" property="vo.category2"/>
                                    </logic:notEqual>
                                    <logic:equal name="FaqForm" property="vo.analysis_yn" value="Y">
                                    &nbsp;(유권해석)
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr class="comment">
                                <th scope="row">질의내용</th>
                                <td>
                                    <bean:write name="FaqForm" property="vo.contents" filter="false"/>
                                    <br/><br/>
                                    <logic:notEmpty name="FaqForm" property="voList1">
                                            첨부파일 :
                                        <logic:iterate name="FaqForm" property="voList1" indexId="rowNum" id="attachVO">
                                            <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');" ><bean:write name="attachVO" property="file_nm" /></a><br/>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <tr class="comment">
                                <th scope="row" class="txt-blue">답변내용</th>
                                <td class="txt-blue">
                                    <bean:write name="FaqForm" property="vo.answer_cont" filter="false"/>
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