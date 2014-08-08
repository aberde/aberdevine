<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.common.Util" %>

<%@include file="/include/top.jsp"%>

    <bean:define name="memberForm" property="errCd" id="errCd" type="java.lang.String"/>
    <bean:define name="memberForm" property="errMsg" id="errMsg" type="java.lang.String"/>

    <bean:define id="path" type="java.lang.String" value="/member.do"/>
    
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
	    var mod_newPwIsNotExp = "변경 비밀번호는 9~20자 이내 영문 ,숫자 및 특수문자로만 입력하셔야 합니다";
	    var mod_newPw2IsNull = "변경비밀번호를 한번 더 입력하세요.";
	    var mod_newPwNotEquals = "변경비밀번호 확인이 일치하지 않습니다. 다시 입력하여 주십시오";
	    var reg_idIsNull = "아이디를 입력하세요.";
	    var reg_idIsNotExp = "아이디는 6~20자 이내 영문 또는 영문+숫자로만 입력하셔야 합니다.";
	    var reg_notCheckId = "아이디 중복을 체크하세요.";
	    var reg_pwIsNotExp = "비밀번호는 영문자, 숫자 또는 특수문자를 조합하여 9자리 이상 입력하셔야 합니다.";
	    var reg_pwIsNotExp1 = "비밀번호는 9~20자 이내로 입력하셔야 합니다.";
	    var reg_pwIsNotExp2 = "비밀번호는 사용자ID와 중복 될 수 없습니다. 다시 입력하여 주십시오.";
	    var reg_pwIsNull = "비밀번호를 입력하세요.";
	    var reg_pw2IsNull = "비밀번호를 한번 더 입력하세요.";
	    var reg_pwNotEquals = "비밀번호 불일치. 다시 입력하세요.";
	    var reg_pwEquals = "비밀번호 확인.";
	    var idcheckWin;
	    var chk_yakgwan = "약관에 동의하셔야 합니다.";
	    var chk_gaeinjeongbo = "개인정보 활용에 동의하셔야 합니다.";
	    /**
	    * 이메일 도메인 선택
	    */
	    function selectEmailDomain( form ) {
	        var f = form;
	        f.email_domain.value = "";
	        if( f.email_domain_sel.value != "none" ) {
	            f.email_domain.value = f.email_domain_sel.value;
	            f.email_domain.readOnly = true;
	        }else{
	            f.email_domain.readOnly = false;
	            f.email_domain.focus();
	        }
	    }
	
	    /**
	    * 입력여부 체크
	    */
	    function chkNull( str ) {
	        if( str.length < 1 || str == "" || str == null ) {
	            return true;
	        } else {
	            return false;
	        }
	    }
	
	
	    function goMemberUpdate(){
	        if(fnCompare()){
	            if(confirm("회원정보를 수정하시겠습니까?")){
// 	                fm.elements["vo.email"].value = fm.email_id.value+"@"+fm.email_domain.value;
	                fm.elements["method"].value = "getUpdate";
	                fm.submit();
	            }
	        }
	    }
	
	    function validate(){
	
	        if (isRequired(fm.elements["vo.login_id"])){
	            return false;
	        } else if (!fnCompare()) {
	            return false;
	        } else if (isRequired(fm.elements["vo.mobile"])){
	            return false;
	        } else if (isRequired(fm.elements["email_id"])||isRequired(fm.elements["email_domain"])){
	            return false;
	        } else {
	            return true;
	        }
	    }
	
	    function ready() {
	        alert("준비중 입니다.");
	    }
	
	    function goIndex() {
	        document.location.href = "/index.jsp";
	    }
	
	    SEPARATOR_DEFAULT = '-';
	    String.prototype.maskPhoneNo = function() {
	     var phoneNo = this.removeAll(SEPARATOR_DEFAULT);
	
	     var idx = 0;
	     var result = "";
	
	     if (phoneNo.substr(0, 2) == "02") {
	      result += (phoneNo.substr(0, 2) + SEPARATOR_DEFAULT);
	      idx = 2;
	     } else if (phoneNo.charAt(0) == "0") {
	      result += (phoneNo.substr(0, 3) + SEPARATOR_DEFAULT);
	      idx = 3;
	     }
	
	     if (phoneNo.substr(idx).length < 4) {
	      result += phoneNo.substr(idx);
	     } else if (phoneNo.substr(idx).length < 8) {
	      result += (phoneNo.substr(idx, 3)
	              + SEPARATOR_DEFAULT
	                 + phoneNo.substr(idx + 3));
	     } else {
	      result += (phoneNo.substr(idx, 4) 
	              + SEPARATOR_DEFAULT
	                 + phoneNo.substr(idx + 4));
	     }
	
	     return result;
	    }
	
	    function numeralsOnly(evt) {
	        evt = (evt) ? evt : event;
	        var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :
	            ((evt.which) ? evt.which : 0));
	        if (charCode > 31 && (charCode < 48 || charCode > 57)) {
	            alert("숫자만 입력하십시오.");
	            event.returnValue = false;
	            return event.returnValue;
	        }
	        return true;
	    }
	
	    // 입력한두개의  비밀번호가 같은지 비교
	    function fnCompare(){
	        if(fm.elements["vo.password"].value != fm.elements["vo.re_password"].value){
	         alert(mod_newPwNotEquals);
// 	            document.getElementById("repw").style.color = '#FF0000';
// 	            document.getElementById("repw").innerHTML = '<font="red">* ' + reg_pwNotEquals + '</font>';
	//          fm.elements["vo.password"].value ="";
	            fm.elements["vo.re_password"].value = "";
	            fm.elements["vo.re_password"].focus();
	            return false;
	        } else {
// 	            document.getElementById("repw").innerHTML = '<font="blue">* ' + reg_pwEquals + '</font>';
	            return true;
	        }
	    }
    //-->
    </script>
    
    <!-- container -->
    <div id="container">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>마이페이지</h2>
                <span><img src="/img/common/h2_mypage.gif" alt="Online Consultation" /></span>
            </div>
            <ul class="lnb-lst">
                <li><a href="JavaScript:goMypage()">마이페이지</a></li>
                <li class="on"><a href="javascript:goUserUpdate();">내정보</a></li>
            </ul>
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="../../img/common/location_home.gif" alt="home" /></a></li>
                    <li><a href="JavaScript:goMypage()">마이페이지</a></li>
                    <li class="on"><a href="javascript:goUserUpdate();">내정보</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section mt60">
                
                <html:form action="/member" method="post" name="fm" type="kr.go.rndcall.mgnt.member.MemberForm">
					<html:hidden name="memberForm" property="method" value="getUpdate"/>
					<html:hidden name="memberForm" property="vo.name"/>
					<html:hidden name="memberForm" property="vo.email"/>
					<html:hidden name="memberForm" property="vo.login_id"/>
					<html:hidden name="memberForm" property="searchVO.menu_sn"/>
					<bean:define name="memberForm" property="vo.email" id="email" type="java.lang.String"/>
            
	                <!--  explain-bx -->
	                <div class="explain-bx mt60">
	                    <strong>회원가입 후 로그인하시면, 마이페이지에서 작성글 및 답변내용 확인이 가능합니다.</strong>
	                </div>
	                <!--  //explain-bx -->
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
	                                    <th scope="row"><label for="pw">비밀번호</label></th>
	                                    <td>
	                                        <html:password styleId="pw" name="memberForm" property="vo.password" style="width:150px" title="비밀번호" tabindex="3"/>
	                                        <span class="small-txt mt10"> * 4자리 이상</span>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row"><label for="pw01">비밀번호 확인</label></th>
	                                    <td>
	                                        <html:password styleId="pw01" name="memberForm" property="vo.re_password" style="width:150px" title="비밀번호확인" tabindex="4"/>
	                                        <span class="small-txt"> * 비밀번호를 한 번 더 입력하십시오</span>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row"><label for="be1">소속</label></th>
	                                    <td>
	                                        <html:radio styleId="info1" name="memberForm" property="vo.sector" value="1"></html:radio> <label for="info1">중앙행정기관</label>
	                                        <html:radio styleId="info2" name="memberForm" property="vo.sector" value="2"></html:radio> <label for="info2">전문기관</label>
	                                        <html:radio styleId="info3" name="memberForm" property="vo.sector" value="3"></html:radio> <label for="info3">정부출연연구기관</label>
	                                        <html:radio styleId="info4" name="memberForm" property="vo.sector" value="4"></html:radio> <label for="info4">대학</label>
	                                        <html:radio styleId="info5" name="memberForm" property="vo.sector" value="5"></html:radio> <label for="info5">기업</label>
	                                        <html:radio styleId="info6" name="memberForm" property="vo.sector" value="6"></html:radio> <label for="info6">기타</label>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row"><label for="id01">아이디찾기 질문</label></th>
	                                    <td>
	                                        <html:select styleId="vo.idFindQuestion" name="memberForm" property="vo.idFindQuestion" style="width:230px;">
	                                            <html:option value="1">기억에 남은 추억의 장소는</html:option>
	                                            <html:option value="2">내가 다닌 초등학교는?</html:option>
	                                            <html:option value="3">자신 인생의 좌우명은?</html:option>
	                                            <html:option value="4">자신의 보물 제1호는?</html:option>
	                                            <html:option value="5">가장 기억에 남는 영화 이름은?</html:option>
	                                            <html:option value="6">자신만의 비밀이 있다면?</html:option>
	                                            <html:option value="7">추억하고 싶은 날짜가 있다면?</html:option>
	                                            <html:option value="8">받은 선물 중 기억에 남는 선물은?</html:option>
	                                            <html:option value="9">인상 깊게 읽은 책 이름은?</html:option>
	                                            <html:option value="10">내가 좋아 하는 케릭터 이름은?</html:option>
	                                            <html:option value="11">자신의 소속기관은?</html:option>
	                                        </html:select> 
	                                        <html:text styleId="vo.idFindAnswer" name="memberForm" property="vo.idFindAnswer" title="아이디 질문을 입력해주세요." style="width:310px"></html:text>
	                                    </td>
	                                </tr>
	                                            <tr>
	                                    <th scope="row"><label for="pw02">비밀번호 찾기 질문</label></th>
	                                    <td>
	                                        <html:select styleId="vo.pwFindQuestion" name="memberForm" property="vo.pwFindQuestion" style="width:230px;">
	                                            <option value="1">기억에 남은 추억의 장소는</option>
	                                            <option value="2">내가 다닌 초등학교는?</option>
	                                            <option value="3">자신 인생의 좌우명은?</option>
	                                            <option value="4">자신의 보물 제1호는?</option>
	                                            <option value="5">가장 기억에 남는 영화 이름은?</option>
	                                            <option value="6">자신만의 비밀이 있다면?</option>
	                                            <option value="7">추억하고 싶은 날짜가 있다면?</option>
	                                            <option value="8">받은 선물 중 기억에 남는 선물은?</option>
	                                            <option value="9">인상 깊게 읽은 책 이름은?</option>
	                                            <option value="10">내가 좋아 하는 케릭터 이름은?</option>
	                                            <option value="11">자신의 소속기관은?</option>
	                                        </html:select> 
	                                        <html:text styleId="vo.pwFindAnswer" name="memberForm" property="vo.pwFindAnswer" title="비밀번호 질문을 입력해주세요." style="width:310px"></html:text>
	                                    </td>
	                                </tr>
	                            </tbody>
	                         </table>
	                    </div>
	                </div>
	                <!-- // board-write -->
	                <!-- btn-set -->
	                <div class="btn-lst txt-r">
	                    <span class="btn-set green"><a href="javascript:goMemberUpdate();">저장</a></span>
	                    <span class="btn-set"><a href="javascript:goIndex();">취소</a></span>
	                </div>
	                <!-- //btn-set-->
	                
	            </html:form>
	            
            </div>  
            <!-- //section -->  
        </div>
        <!-- //content -->
    </div>
    <!-- // container -->
    
    <html:form action="/member" method="post" name="fmDoc" type="kr.go.rndcall.mgnt.member.MemberForm">
	    <html:hidden name="memberForm" property="method" value="getOldDocList"/>
	    <html:hidden name="memberForm" property="vo.email"/>
	    <html:hidden name="memberForm" property="vo.name"/>
	    <html:hidden name="memberForm" property="vo.login_id"/>
	    <html:hidden name="memberForm" property="searchVO.menu_sn" value="06"/>
	</html:form>
    
<%@include file="/include/bottom.jsp"%>