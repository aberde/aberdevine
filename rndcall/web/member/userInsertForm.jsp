<%@page contentType="text/html; charset=utf-8"%>

<%@include file="/include/top.jsp"%>

	<bean:define name="memberForm" property="errCd" id="errCd" type="java.lang.String"/>
	<bean:define name="memberForm" property="errMsg" id="errMsg" type="java.lang.String"/>

    <bean:define id="path" type="java.lang.String" value="/member.do"/>

    <html:form action="/member" method="post" name="fmDoc" type="kr.go.rndcall.mgnt.member.MemberForm">
        <html:hidden name="memberForm" property="method" value="getOldDocList"/>
        <html:hidden name="memberForm" property="vo.login_id"/>
        <html:hidden name="memberForm" property="searchVO.menu_sn" value="06"/>
    </html:form>
    
    <%
        if ( mainLoginVO != null && mainLoginVO.getLogin_id() != null && !mainLoginVO.getLogin_id().equals("guest") ) {
    %>
    <script type="text/javascript">
        if ("<%= errMsg %>" == "0") {
            document.location.href = "/index.jsp";
        } else {
            document.location.href = "/member/join02.jsp";
// 			fmDoc.method.value = "getOldDocList";
// 			fmDoc.submit();
		}
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
		var reg_pwIsNotExp3 = "비밀번호는 4자리이상으로 입력하셔야 합니다.";
		var reg_pwIsNull = "비밀번호를 입력하세요.";
		var reg_pw2IsNull = "비밀번호를 한번 더 입력하세요.";
		var reg_pwNotEquals = "비밀번호 불일치. 다시 입력하세요.";
		var reg_pwEquals = "비밀번호 확인.";
		var idcheckWin;
		var chk_yakgwan = "약관에 동의하셔야 합니다.";
		var chk_gaeinjeongbo = "개인정보 활용에 동의하셔야 합니다.";

		/**
		* 아이디 중복확인 팝업 오픈
		*/
		function openCheckUserIdPop() {
			var idcheckUrl = "/switch.do?prefix=&page=/member.do?method=getIdCheckForm";
			
			//1.아이디
			if ( chkNull( fm.elements["vo.login_id"].value ) ) {
				alert( reg_idIsNull );
				fm.elements["vo.login_id"].focus();
				return;
			}
			//2.아이디 형식 체크
			if( !chkIdExp( fm.elements["vo.login_id"].value ) ) {
				alert( reg_idIsNotExp );
				fm.elements["vo.login_id"].value = "";
				fm.elements["vo.login_id"].focus();
				return;
			}
			var winfeatures = "toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=580,height=280";
			var url = idcheckUrl + "&searchVO.login_id=" + fm.elements["vo.login_id"].value;
			//var url = idcheckUrl;
			if( idcheckWin != null ) {
				idcheckWin.close();
				idcheckWin = null;
			}
			idcheckWin = window.open( url, "idcheck", winfeatures);
		}

		function fnCheck(){
			var str = fm.elements["vo.password"].value;	
			
			if( str.length < 4 ) {
				alert(reg_pwIsNotExp3);
				fm.elements["vo.password"].value = "";
				fm.elements["vo.password"].focus();
				return true;
			}
		
		}

		// 입력한두개의  비밀번호가 같은지 비교
		function fnCompare(){
			if(fm.elements["vo.password"].value != fm.elements["vo.re_password"].value){
				alert(reg_newPwNotEquals);
// 				document.getElementById("repw").style.color = '#FF0000';
// 				document.getElementById("repw").innerHTML = '<font="red">* ' + reg_pwNotEquals + '</font>';
		//		fm.elements["vo.password"].value ="";
				fm.elements["vo.re_password"].value = "";
				fm.elements["vo.re_password"].focus();
				return false;
			} else {
// 				document.getElementById("repw").innerHTML = '<font="blue">* ' + reg_pwEquals + '</font>';
				return true;
			}
		}

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

		/**
		* 아이디 정규표현식 체크
		*/
		function chkIdExp( str ) {
			chk1 = /^[a-zA-Z\d]{4,20}$/i;  //a-z와 0-9이외의 문자가 있는지 확인
		    chk2 = /[a-z]/i;  //적어도 한개의 a-z 확인
		    return chk1.test( str ) && chk2.test( str ); //&& chk3.test( str );
		}

		// 비밀번호 정규표현식 체크
		function chkPWExp( str ) {
		    
			chk1 = /[a-zA-Z]/i;  //적어도 한개의 영문
			chk2 = /[0-9]/i;     //적어도 한개의 숫자
		    chk3 = /[~!@\#$%^&*\()\-=+_]/i;  //적어도 한개의 툭수문자 확인
		    chk4 = /[a-zA-Z0-9~!@\#$%^&*\()\-=+_]{9,30}/i
		
		    return chk1.test( str ) && chk2.test( str ) && chk3.test( str ) && chk4.test( str );
		}

		function checkRegisterForm(){
// 			fm.elements["vo.email"].value = fm.email_id.value+"@"+fm.email_domain.value;
			
			if(validate()) {
				if(fm.elements["vo.checkIdVal"].value !="Y"){
					alert(reg_notCheckId);
					return;
				}else{
					fm.elements["method"].value = "getInsert";
					fm.submit();
				}
			}
		}

		function validate(){
		
			if (isRequired(fm.elements["vo.login_id"])){
				return false;
			} else  if (isRequired(fm.elements["vo.password"])){
				return false;
			} else if (fnCheck()) {
				return false;
			} else if (isRequired(fm.elements["vo.re_password"])){
				return false;
			} else if (!fnCompare()) {
				return false;
// 			} else if (isRequired(fm.elements["vo.name"])){
// 				return false;
		//	} else if (isRequired(fm.elements["email_id"])||isRequired(fm.elements["email_domain"])){
		//		return false;
			} else if (isRequired(fm.elements["vo.sector"])){
				return false;
			} else if (isRequired(fm.elements["vo.idFindAnswer"])){
				return false;
			} else if (isRequired(fm.elements["vo.pwFindAnswer"])){
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
	            result += (phoneNo.substr(idx, 3) + SEPARATOR_DEFAULT + phoneNo.substr(idx + 3));
	        } else {
	            result += (phoneNo.substr(idx, 4) + SEPARATOR_DEFAULT + phoneNo.substr(idx + 4));
	        }

	        return result;
	    };

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

	//-->
    </script>

    <!-- container -->
    <div id="container">
        <!-- lnb -->
        <div class="lnb">
            <div class="tit-area">
                <h2>회원가입</h2>
                <span><img src="/img/common/h2_membership.gif" alt="Membership" /></span>
            </div>
            <ul class="lnb-lst">
                <li class="on"><a href="/switch.do?prefix=&page=/member.do?method=getUserInsertForm&searchVO.menu_sn=06">회원가입</a></li>
            </ul>               
        </div>
        <!-- //lnb -->
        <!-- content -->
        <div class="content clearfix">
            <div class="location txt-r">        
                <ul class="fr clearfix">
                    <li><a href="/index.jsp"><img src="/img/common/location_home.gif" alt="home" /></a></li>
                    <li class="on"><a href="/switch.do?prefix=&page=/member.do?method=getUserInsertForm&searchVO.menu_sn=06">회원가입</a></li>
                </ul>
            </div>
            <!-- section -->
            <div class="section">       
                <!--  explain-bx -->
                <div class="explain-bx mt60">
                    <strong>회원가입 후 로그인하시면, 마이페이지에서 작성글 및 답변내용 확인이 가능합니다.</strong>
                </div>
                <!--  //explain-bx -->
                
                <html:form action="/member" method="post" name="fm" type="kr.go.rndcall.mgnt.member.MemberForm">
					<html:hidden name="memberForm" property="method" value="getInsert"/>
					<html:hidden name="memberForm" property="vo.email"/>
					<html:hidden name="memberForm" property="vo.checkIdVal"/>
					<html:hidden name="memberForm" property="searchVO.menu_sn" value="06"/>
    
	                <!-- board-write -->
	                <div class="board-write mt10">
	                    <div class="board-box">
	                        <table summary="질의자 정보, 공개여부, 제목, 내용 보기 등록 페이지">
	                            <caption>온라인상담 등록 페이지 </caption>
	                            <colgroup>
	                                <col width="20%"/>
	                                <col width="*"/>
	                            </colgroup>
	                            <tbody>
	                                <tr>
	                                    <th scope="row"><label for="vo.login_id">사용자 아이디</label></th>
	                                    <td>
	                                        <html:text name="memberForm" styleId="vo.login_id" property="vo.login_id" style="width:150px" title="사용자 ID" tabindex="2"/>
	                                        <span class="btn-set set2 s-blue"><a href="javaScript:openCheckUserIdPop();">아이디 중복확인</a><span class="zoom"></span></span>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row"><label for="vo.password">비밀번호</label></th>
	                                    <td>
	                                        <html:password name="memberForm" styleId="vo.password" property="vo.password" style="width:150px" title="비밀번호" tabindex="3"/>
	                                        <span class="small-txt mt10"> * 4자리 이상</span>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row"><label for="vo.re_password">비밀번호 확인</label></th>
	                                    <td>
	                                        <html:password name="memberForm" styleId="vo.re_password" property="vo.re_password" style="width:150px" title="비밀번호확인" tabindex="4"/>
	                                        <span class="small-txt"> * 비밀번호를 한 번 더 입력하십시오</span>
	                                    </td>
	                                </tr>
									<tr>
                                        <th scope="row"><label for="be1">소속</label></th>
                                        <td>
											<input type="radio" id="info1" name="vo.sector" value="1" checked /> <label for="info1">중앙행정기관</label>
                                            <input type="radio" id="info2" name="vo.sector" value="2" /> <label for="info2">전문기관</label>
                                            <input type="radio" id="info3" name="vo.sector" value="3" /> <label for="info3">정부출연연구기관</label>
                                            <input type="radio" id="info4" name="vo.sector" value="4" /> <label for="info4">대학</label>
                                            <input type="radio" id="info5" name="vo.sector" value="5" /> <label for="info5">기업</label>
                                            <input type="radio" id="info6" name="vo.sector" value="6" /> <label for="info6">기타</label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><label for="se02">소속기관</label></th>
                                        <td>
                                            <html:select styleId="se02" name="memberForm" property="vo.org_cd" style="width:160px;">
                                                <html:option value="">없음</html:option>
                                                <html:optionsCollection name="memberForm" property="orgCdList" label="org_nm" value="org_cd"/>
                                            </html:select>
                                        </td>
                                    </tr>
	                                <tr>
	                                    <th scope="row"><label for="vo.idFindQuestion">아이디찾기 질문</label></th>
	                                    <td>
	                                        <select id="vo.idFindQuestion" name="vo.idFindQuestion" style="width:230px;">
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
	                                        </select> 
	                                        <input type="text" id="vo.idFindAnswer" name="vo.idFindAnswer" title="아이디 질문을 입력해주세요." style="width:310px"/>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row"><label for="vo.pwFindQuestion">비밀번호 찾기 질문</label></th>
	                                    <td>
	                                        <select id="vo.pwFindQuestion" name="vo.pwFindQuestion" style="width:230px;">
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
	                                        </select> 
	                                        <input type="text" id="vo.pwFindAnswer" name="vo.pwFindAnswer" title="비밀번호 질문을 입력해주세요." style="width:310px"/>
	                                    </td>
	                                </tr>
	                            </tbody>
	                         </table>
	                    </div>
	                </div>
	                <!-- // board-write -->
	                <!-- btn-set -->
	                <div class="btn-lst txt-r">
	                    <span class="btn-set green"><a href="javascript:checkRegisterForm();">저장</a></span>
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

<%@include file="/include/bottom.jsp"%>
