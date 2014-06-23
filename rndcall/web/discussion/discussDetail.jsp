<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="kr.go.rndcall.mgnt.discussion.DiscussionVO" %>
<%@ page import="kr.go.rndcall.mgnt.common.StringUtil" %>
<%@  include file="/include/top.jsp"%>

<bean:define name="discussionForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
<bean:define name="discussionForm" property="totRowCount" id="totRowCount" type="java.lang.Integer"/>
<bean:define name="discussionForm" property="maxIndexPages" id="maxIndexPages" type="java.lang.String"/>
<bean:define name="discussionForm" property="maxPageItems" id="maxPageItems" type="java.lang.String"/>
<bean:define name="discussionForm" property="is_update" id="is_update" type="java.lang.String"/>

<bean:define id="path" type="java.lang.String" value="/discussion.do" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
    boolean isLogin =false;
    LoginVO loginVODis = (LoginVO) session.getAttribute("loginUserVO");
    if(loginVODis != null && loginVODis.getRoleCD()!= null && !loginVODis.getRoleCD().equals("") && !loginVODis.getRoleCD().equals("guest")) {
	    isLogin = true;
    }

    String disId = "";
	String disRoleNM = "";
	String disRoleCD = "00000";
	String disName = "";
	String disEmail = "";
	if (loginVODis != null) {
		disId = loginVODis.getLogin_id();
		disRoleNM = loginVODis.getRoleNM();
		disRoleCD = loginVODis.getRoleCD();
		disName = loginVODis.getName();
		disEmail = loginVODis.getEmail();
	}
	
%>
<script language="JavaScript" src="/js/jquery.min.js"></script>
<%
	if (mainLoginVO == null || !mainIsLogin) {	
%>
	<script type="text/javascript">
		alert('로그인이 필요한 메뉴입니다.');
		document.location.href = "/index.jsp";
	</script>
<%
	} else if (!mainRoleCD.equals("0000B") && !mainRoleCD.equals("0000C") && !mainRoleCD.equals("0000Z")) {
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
module = '/switch.do?prefix=&page=';

var xmlHttp3 = null; // ajax 용 XMLHttpRequest 객체를 담을 변수
/*
 * XMLHttpRequest 객체생성
 */
function createHttp(){ //서버와 연동할 객체를 생성하는 함수
    if(window.ActiveXObject){ //window 용
	    xmlHttp3 = new ActiveXObject("Microsoft.XMLHTTP");
	}else{ //그 외
		xmlHttp3 = new XMLHttpRequest();
	}
}
 
/*
 * onload 시 초기화 설정
 */
window.onload = function() {
    <%
        String now_discuss_op_id = (String)request.getAttribute("now_discuss_op_id");
        if(now_discuss_op_id != null && !now_discuss_op_id.equals("")) { %>
            document.getElementById("opin<%=now_discuss_op_id%>").style.display = "";
		    document.getElementById("reply<%=now_discuss_op_id%>").style.display = "";
		    document.getElementById("shortComment<%=now_discuss_op_id%>").style.display = "none";
		    document.getElementById("add_reg_dt<%=now_discuss_op_id%>").style.display = "";
		    window.location = "#opinLink<%=now_discuss_op_id%>";
   <%   }   %>
    InitializeStaticMenu();
	<% if(!mainIsLogin) { %>
		alert("로그인이 필요한 기능입니다.");
		document.location.href = "/index.jsp";
	<% } %>
} 

/* 
 * 의견등록 
 */
function fncSave() {
	if(fm.elements["vo.dis_comment"].value == "") {
	    alert("등록할 내용이 없습니다");
	    return;
	}
	if(!confirm("저장하시겠습니까?")) return;
    fm.submit();
}

/*
 * 답글등록폼 열고닫기
 */
function fncAddReply(obj, dis_op_id) {
<% if(!mainIsLogin) { %>
       alert("로그인이 필요한 기능입니다.");
       return;
<% } %>
	// 현재 선택한 답글화면 현재꺼면 열거나 닫음.
	var oid = document.getElementById(obj);
	if(oid.style.display == "none") {	    
	    oid.style.display="";
	} else {
	    oid.style.display="none";
	}
		
	// 현재 선택한거 말고 모두 닫음.	
	$("div[id^=replyArea]").filter(function() {
	    return this.id != obj;	    
	}).css("display", "none");
	
	$("div[id^=bestReplyArea]").filter(function() {
	    return this.id != obj;	    
	}).css("display", "none");
}

/*
 * 답글등록
 */
function fncReplySave(replyArea, discuss_op_id) {
	// 내용있는지 검사
	var txtAr_obj = $("#"+replyArea + " textarea")[0].firstChild;
	if(txtAr_obj == null) {
	    alert("내용을 입력해 주십시오.");
	    return;
	}
	if(txtAr_obj != null && txtAr_obj.nodeValue.trim() == "") {
	    alert("내용을 입력해 주십시오.");
	    return;
	}
		
	$("tr[id^=replyArea]").filter(function() {
	    return this.id != replyArea;	    
	}).remove();	
	
	//alert(fmOpin.elements["vo.dis_comment_reply"].value);
	// discuss_op_id 를 설정해야 함!!!!!!!!!!!!!!!!!!!!!
	fmOpin.elements["vo.discuss_op_id"].value = discuss_op_id;
	
	fmOpin.action = module + "/discussion.do?pager.offset="+<%=pagerOffset%>;
	
    fmOpin.submit();
}

/*
 * 의견 또는 답글 삭제
 */
function fncDelOpinion(discuss_op_id, reply_area, p_dis_id) {

    // 답글이 있으면 삭제하지 못하도록 막는부분
	if($("#"+reply_area+" tr.Comment").length > 0) {
	    alert("답글이 존재하는 글은 삭제할 수 없습니다.");
	    return;
	}
    if(!confirm("삭제하시겠습니까?")) return;
    fmDelete.elements["searchVO.discuss_op_id"].value = discuss_op_id;
    
    if(p_dis_id != undefined) {    
    	fmDelete.elements["searchVO.p_discuss_op_id"].value = p_dis_id;
    } else {
        fmDelete.elements["searchVO.p_discuss_op_id"].value = "";
    }
    fmDelete.action = module + "/discussion.do?pager.offset="+<%=pagerOffset%>;
    fmDelete.submit();
}

 
 
 // 답글수정
 function comUpdateOpinion(id, discuss_op_id, p_discuss_op_id) {
     var upDivId = document.getElementById(id);
     var hasChild = upDivId.hasChildNodes();    
     if(hasChild) {
         var chd = upDivId.firstChild;        
         upDivId.removeChild(chd);
         return;
     }
	 var sId = "replUpdateArea"; // 답글수정
	 var aId = "opUpdateArea"; // 의견수정
     var bId = "introUpdateArea"; // 이슈내용 수정
     
     var dId = "bestReplUpdateArea"; // 베스트 답글수정
     
     //var ch1 = document.getElementById(id).childNodes;
     
     //alert(ch1);
    
     
     var aReturn = null;
     var index = 0;
 	
		//1) XMLHTTPRequest 생성
		createHttp();
		//2) 콜백함수 등록(정의)
		xmlHttp3.onreadystatechange = function() {
			if(xmlHttp3.readyState == 4){ //요청상태 complete
				if(xmlHttp3.status == 200){ //응답완료			
					document.getElementById(id).innerHTML = xmlHttp3.responseText;
				}
			}			
		}
		//3) 서버호출			

		var url = "/switch.do?prefix=&page=/discussion.do?method=updateCommentContentsForm&searchVO.discuss_op_id="
		          +discuss_op_id;
	
		xmlHttp3.open("POST", url, true);
		xmlHttp3.send(null);
		
		
		
		 for(var i=0; i<document.all.length; ++i)
	     {
	        if(document.all[i].id.search(sId) != -1)
	        {
		        if(aReturn == null) aReturn = new Array();
	            aReturn[index++] = document.all[i].id;
	         }
	         
	        if(document.all[i].id.search(aId) != -1)
	        {
		        if(aReturn == null) aReturn = new Array();
	            aReturn[index++] = document.all[i].id;
	         }
	         
	        if(document.all[i].id.search(bId) != -1)
	        {
		        if(aReturn == null) aReturn = new Array();
	            aReturn[index++] = document.all[i].id;
        		document.getElementById("introUpdate_").style.display = "block";
	         }
	         
	         if(document.all[i].id.search(dId) != -1)
	        {
		        if(aReturn == null) aReturn = new Array();
	            aReturn[index++] = document.all[i].id;
	         }
	     }
		 
		 for(var i=0; i<aReturn.length; i++) {	 	
		 	document.getElementById(aReturn[i]).innerHTML = "";
		 }
 }
 
 
 // 의견수정
function fncUpdateOpinion(id,discuss_op_id) {
    var upDivId = document.getElementById(id)    
    var hasChild = upDivId.hasChildNodes();    
    if(hasChild) {
        var chd = upDivId.firstChild;        
        upDivId.removeChild(chd);
        return;
    }     
     var sId = "opUpdateArea"; // 의견수정
     var bId = "introUpdateArea"; // 이슈내용 수정
     var cId = "replUpdateArea"; // 답글수정

     var aReturn = null;
     var index = 0;	 	

		//1) XMLHTTPRequest 생성
		createHttp();
		//2) 콜백함수 등록(정의)
		xmlHttp3.onreadystatechange = function() {
			if(xmlHttp3.readyState == 4){ //요청상태 complete
				if(xmlHttp3.status == 200){ //응답완료
					document.getElementById(id).innerHTML = xmlHttp3.responseText;
				}
			}			
		}
		
		//3) 서버호출
		var url = "/switch.do?prefix=&page=/discussion.do?method=updateDiscussContentsForm&searchVO.discuss_op_id="
		          +discuss_op_id;
	
		xmlHttp3.open("POST", url, true);
		xmlHttp3.send(null);
		
		for(var i=0; i<document.all.length; ++i)
	     {
	        if(document.all[i].id.search(sId) != -1)
	        {
		        if(aReturn == null) aReturn = new Array();
	            aReturn[index++] = document.all[i].id;
	         }
	         
	         if(document.all[i].id.search(bId) != -1)
	        {
		        if(aReturn == null) aReturn = new Array();
	            aReturn[index++] = document.all[i].id;
        		document.getElementById("introUpdate_").style.display = "block";
	         }
	         
           if(document.all[i].id.search(cId) != -1)
	        {
		        if(aReturn == null) aReturn = new Array();
	            aReturn[index++] = document.all[i].id;
	         }
	     }
		 
		 for(var i=0; i<aReturn.length; i++) {	 	
		 	document.getElementById(aReturn[i]).innerHTML = "";
		 }
	
		 
}

function ap_op_count(spanId,discuss_op_id, ap_op) {
    
    var url = "/switch.do?prefix=&page=/discussion.do?method=createDisApop"             
             +"&searchVO.span_id="+spanId
	         +"&searchVO.discuss_op_id="+discuss_op_id
	         +"&searchVO.ap_op="+ap_op;
	// Ajax 호출(/js/ajax.js참조)
	cfXmlHttpRequestSend2(url, "POST", null);
}
// 서버에 갔다온 이후 처리하는 함수(/js/ajax.js참조)
function processBiz() {    
	    var ap_op = "";
	    var rtn = xmlHttpRequest.responseXML.getElementsByTagName("data");
	    var data = rtn.item(0);
	    var rtnCode = data.getElementsByTagName("returnCode")[0].firstChild.nodeValue;
	    var rtnValue = data.getElementsByTagName("returnValue")[0].firstChild.nodeValue;
	    var rtnApOp = data.getElementsByTagName("returnApOp")[0].firstChild.nodeValue;
	    
	    if(rtnValue == "noLogin") {
	        alert("로그인이 필요한 기능입니다.");
	        //popupLogin();
	        return;
	    }
	    if(rtnValue == "existData") {
	        alert("이미 참여 하셨습니다.");
	        return;
	    }
	    if(rtnValue == "self") {
	        alert("본인글에 참여할 수 없습니다.");
	        return;
	    }
	    var nowCntObj = document.getElementById("ap_cnt_"+rtnCode);
	    if(nowCntObj) {
	        if(rtnApOp == "10501") {
	            var apObj = document.getElementById("ap_cnt_"+rtnCode);
		    	var apNowCntValue = apObj.firstChild.nodeValue;    
		    	var acount = eval(apNowCntValue)+1;
		    	apObj.innerText = acount;
		    } else {
		        var opObj = document.getElementById("op_cnt_"+rtnCode);
		    	var opNowCntValue = opObj.firstChild.nodeValue;    
		    	var ocount = eval(opNowCntValue)+1;
		    	opObj.innerText = ocount;
		    }
	    }
	    
	    var bestCntObj = document.getElementById("ap_"+rtnCode);    
	    if(bestCntObj) {    
	    	if(rtnApOp == "10501") {
	    	    var bestApCntObj = document.getElementById("ap_"+rtnCode);
	    	    var bestApCntValue = bestApCntObj.firstChild.nodeValue;    	    
	    		var apCount = eval(bestApCntValue)+1;
	    		bestApCntObj.innerText = apCount;
	    	} else {
	    	    var bestOpCntObj = document.getElementById("op_"+rtnCode);
	    	    var bestOpCntValue = bestOpCntObj.firstChild.nodeValue;    
	    		var opCount = eval(bestOpCntValue)+1;
	    		bestOpCntObj.innerText = opCount;
	    	}
	    }
	    
	    var rep_ap = document.getElementsByName("rep_ap_"+rtnCode)[0];
	    
	    if(rep_ap) {
	        alert(rep_ap.value);
	    }	
} 

function fncDisDel() {
    if(!confirm("삭제하시겠습니까?")) return;
    fmDisDelete.submit();
}

function fncDisUpdate() {
    if(!confirm("수정하시겠습니까?")) return;
    fmDisUpdate.submit();
}


function deleteContents(dis_cont_id) {
    if(!confirm("삭제하시겠습니까?")) return;
    fmDelCont.elements["searchVO.dis_cont_id"].value = dis_cont_id;
    fmDelCont.submit();
}

function fncDisContentCreate() {    
	var cont = document.getElementById("contents_editor2");
	
	if(cont.style.display == "none") {	
	    cont.style.display = "";
	} else {
	    cont.style.display = "none";
	}		
}

function removeAllChild(obj) {
	
	if (obj.hasChildNodes()) {
		var chds = obj.childNodes;
		for (var i = 0; i < chds.length; i++) {
			obj.removeChild(chds[i]);
		}
	}
}
// 이슈 본문 수정
function updateContents(discuss_id,dis_cont_id, id) {
    var upDivId = document.getElementById(id);
    var hasChild = upDivId.hasChildNodes();
    if(hasChild) {
        //글작성부분
//        var chd1 = upDivId.firstChild;
//        var chd2 = upDivId.childNodes;
//        upDivId.removeChild(chd1);
		removeAllChild(upDivId);
      	document.getElementById("introUpdate_").style.display = "block";
        return;
    	 
    } else {
   		document.getElementById("introUpdate_").style.display = "none";
    }
	var sId = "introUpdateArea"; // 이슈내용 수정
	var aId = "opUpdateArea"; // 의견수정
	var cId = "replUpdateArea"; // 답글수정
	
	var obj = document.getElementById(id).childNodes;
			
    if(obj.length == 0) {	
		var aReturn = null;
    	var index = 0;    
    	 
	 	//1) XMLHTTPRequest 생성
		createHttp();
		//2) 콜백함수 등록(정의)
		xmlHttp3.onreadystatechange = function() {
			if(xmlHttp3.readyState == 4){ //요청상태 complete
				if(xmlHttp3.status == 200){ //응답완료			
					document.getElementById(id).innerHTML = xmlHttp3.responseText;
				}
			}			
		}

		//3) 서버호출			
		var url = "/switch.do?prefix=&page=/discussion.do?method=updateIssueDetailsForm&searchVO.dis_cont_id="
		          +dis_cont_id+"&searchVO.discuss_id="+discuss_id;
	
		xmlHttp3.open("POST", url, true);
		xmlHttp3.send(null);
    } 
		
	for(var i=0; i<document.all.length; ++i)
     {     
        if(document.all[i].id.search(sId) != -1)
        {
	        if(aReturn == null) aReturn = new Array();
            aReturn[index++] = document.all[i].id;
         }
         
        if(document.all[i].id.search(aId) != -1)
        {
	        if(aReturn == null) aReturn = new Array();
            aReturn[index++] = document.all[i].id;
         }
         
        if(document.all[i].id.search(cId) != -1)
        {
	        if(aReturn == null) aReturn = new Array();
            aReturn[index++] = document.all[i].id;
         }
     }
	
	 for(var i=0; i<aReturn.length; i++) {	 
	 	if(aReturn[i] != id) {	
		 	document.getElementById(aReturn[i]).innerHTML = "";
	 	}
	 }
	 
   
}

function send_mail(dis_op_id) {
    var url = "/switch.do?prefix=&page=/discussion.do?method=sendReplyMailForm&menuId=400&vo.discuss_op_id="+dis_op_id;
    var ee = new Popup(url, 750, 490, "window").open();
}

function cfGetElementsBySubId(sId, oDocument)
{
    if(oDocument == null) oDocument = document;
    var aReturn = null;
    var index = 0;
    for(var i=0; i<oDocument.all.length; ++i)
    {
        if(oDocument.all[i].id.search(sId) != -1)
        {
            if(aReturn == null) aReturn = new Array();
            aReturn[index++] = oDocument.all[i];
        }
    }
    return aReturn;
}

function fncSearch() {
    fmDetail.submit();
}

function downLoad_Des(fileNM, saveFileNM, yn){
    document.fileDownLoad.elements["fileNM"].value = fileNM;
    document.fileDownLoad.elements["saveFileNM"].value = saveFileNM;    
    document.fileDownLoad.elements["desCipher"].value = yn;    
    document.fileDownLoad.submit();
}

function fncAddBestReply(obj, dis_op_id) {
    <% if(!mainIsLogin) { %>
       alert("로그인이 필요한 기능입니다.");
       return;
    <% } %>
    var oid = document.getElementById(obj);
	
	if(oid.style.display == "none") {
	    oid.style.display="";
	} else {
	    oid.style.display="none";
	}
		
	// 현재 선택한거 말고 모두 닫음.	
	$("div[id^=replyArea]").filter(function() {
	    return this.id != obj;	    
	}).css("display", "none");
	
	$("div[id^=bestReplyArea]").filter(function() {
	    return this.id != obj;	    
	}).css("display", "none");
}

function fncBestReplyView(dd) {
    var ee = document.getElementById(dd);
    if(ee.style.display == "none") ee.style.display = "";
    else ee.style.display = "none";
}

function fncShowEditor() {
<% if(!mainIsLogin) { %>
       alert("로그인이 필요한 기능입니다.");
       return;
<% } else { %>
    var ot = document.getElementById("fopin_table");
    ot.style.display = "";
    window.location = "#opin_table";
<% } %>
}

function fncContSave() {
    if(fmCont.elements["vo.dis_contents"].value == "") {
	    alert("등록할 내용이 없습니다.");
	    return;
	}
	fmCont.elements["vo.open_cd"].value = "10701";
	fmCont.submit();
}

function fncContSave2(id) {
	if(fmOpin.elements["vo.dis_comment"].value == "") {
	    alert("등록할 내용이 없습니다.");
	    return;
	}
	if(!confirm("수정하시겠습니까?")) return;
	fmOpin.elements["method"].value = "updateDiscussContents";
	fmOpin.elements["searchVO.discuss_op_id"].value = id;
	fmOpin.action = module + "/discussion.do?pager.offset="+<%=pagerOffset%>;
	fmOpin.submit();
}

function fncContSave3(dis_cont_id, discuss_id) {
	if(fmCont.elements["vo.dis_contents"].value == "") {
	    alert("등록할 내용이 없습니다.");
	    return;
	}
	if(!confirm("수정하시겠습니까?")) return;
	var open_cd = "10701";	
	fmCont.elements["method"].value = "updateIssueDetailsContents";
	fmCont.elements["searchVO.discuss_id"].value = discuss_id;
	fmCont.elements["searchVO.dis_cont_id"].value =	dis_cont_id;
//	fmCont.elements["vo.open_cd"].value = open_cd;
	fmCont.submit();
}

function fncCommentSave(id, p_id) {
	if(fmOpin.elements["vo.dis_comment"].value == "") {
	    alert("등록할 내용이 없습니다.");
	    return;
	}
	fmOpin.elements["method"].value = "updateCommentContents";
	fmOpin.elements["searchVO.discuss_op_id"].value = id;
	fmOpin.elements["searchVO.p_discuss_op_id"].value = p_id;
	fmOpin.action = module + "/discussion.do?pager.offset="+<%=pagerOffset%>;
	fmOpin.submit();
}

function closeHelp() {    
    $(".divpop").css("display", "none");
}

function view_help(helpId) {
    var strHelpId = "#"+helpId;
    $(strHelpId).css("display", "");
}

/**
 * 파일 입력폼을 추가한다.
 * 사용법 : 1. 파일입력폼이 추가되기 원하는 곳에 <div> 태그를 만들고 id 를 지정한다.
 *        예) <div id="fileArea"></div>
 *        2. 위에서 만든 id 와 크기로 fncFileAdd 함수를 호출한다.
 *        예) fncFileAdd('fileArea', '70');
 *        3. 그러면 fileArea 안에 파일입력폼이 하나씩 추가된다.
 */
 var fileId = 1;
function funcFileAdd(fileObjName, size) {
    var divEl = document.createElement("DIV");
    var fileArea = document.getElementById(fileObjName);
    var inputEl = document.createElement("INPUT");
    inputEl.setAttribute("type", "file");
    inputEl.setAttribute("size", size);
    fileId = fileId + 1;
    inputEl.setAttribute("name", "files"+fileId);
    inputEl.className = "fileForm";
    divEl.style.marginTop = "4px";
    divEl.style.marginBottom = "2px";
    divEl.appendChild(inputEl);
    fileArea.appendChild(divEl);
}
/*
 * 파일 입력폼을 삭제한다.
 * 사용법 : 1. 삭제되길 원하는 파일입력폼 이름을 파라미터로 해서 함수를 호출한다.
 *        예) fncFileDel('fileArea');
 */
function funcFileDel(fileObjName) {
    var fileArea = document.getElementById(fileObjName);
    var childNds = fileArea.childNodes;
    if(childNds.length != 0) {
        fileArea.removeChild(childNds[childNds.length-1]);
    }
    /*
    var inputFileLength = $("input[type=file]").length;    
    if(inputFileLength != 0) {
        var el = $("input[type=file]").get(inputFileLength-1);
        fileArea.removeChild(el);
    }
    */
}
//-->
</script>
<script type="text/javascript">
 var stmnLEFT = 10; // 오른쪽 여백 
 var stmnGAP1 = 200; // 위쪽 여백 
 var stmnGAP2 = 245; // 스크롤시 브라우저 위쪽과 떨어지는 거리 
 var stmnBASE = 245; // 스크롤 시작위치 
 var stmnActivateSpeed = 15; //스크롤을 인식하는 딜레이 (숫자가 클수록 느리게 인식)
 var stmnScrollSpeed = 9; //스크롤 속도 (클수록 느림)
 var stmnTimer; 
 
 function RefreshStaticMenu() { 
  var stmnStartPoint, stmnEndPoint;
  
  stmnStartPoint = parseInt(document.getElementById('STATICMENU').style.top, 10); 
  stmnEndPoint = Math.max(document.documentElement.scrollTop, document.body.scrollTop) + stmnGAP2; 
  if (stmnEndPoint < stmnGAP1) stmnEndPoint = stmnGAP1; 
  if (stmnStartPoint != stmnEndPoint) { 
   stmnScrollAmount = Math.ceil( Math.abs( stmnEndPoint - stmnStartPoint ) / 15 ); 
   document.getElementById('STATICMENU').style.top = parseInt(document.getElementById('STATICMENU').style.top, 10) + ( ( stmnEndPoint<stmnStartPoint ) ? -stmnScrollAmount : stmnScrollAmount ) + 'px'; 
   stmnRefreshTimer = stmnScrollSpeed; 
   }
  stmnTimer = setTimeout("RefreshStaticMenu();", stmnActivateSpeed); 
  } 
 function InitializeStaticMenu() {
  document.getElementById('STATICMENU').style.right = stmnLEFT + 'px';  //처음에 오른쪽에 위치. left로 바꿔도.
  document.getElementById('STATICMENU').style.top = document.body.scrollTop + stmnBASE + 'px'; 
  RefreshStaticMenu();
  }
</script>
<script type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

function showContents(showId) {
    document.getElementById("opin"+showId).style.display = "";
    document.getElementById("reply"+showId).style.display = "";
    document.getElementById("shortComment"+showId).style.display = "none";
    document.getElementById("add_reg_dt"+showId).style.display = "";    
}

function hiddenComment(hiddenId) {
    document.getElementById("opin"+hiddenId).style.display = "none";
    document.getElementById("reply"+hiddenId).style.display = "none";
    document.getElementById("shortComment"+hiddenId).style.display = "";
    document.getElementById("add_reg_dt"+hiddenId).style.display = "none";
}

function allClose() {
    $("span[id^=shortComment]").css("display", "");
    $("div[id^=opin]").css("display", "none");
    $("tbody[id^=reply]").css("display", "none");
    $("span[id^=add_reg_dt]").css("display", "none");
}
function allView() {
    $("span[id^=shortComment]").css("display", "none");
    $("div[id^=opin]").css("display", "");
    $("tbody[id^=reply]").css("display", "");
    $("span[id^=add_reg_dt]").css("display", "");
}

function fncViewReply(reply_id) {
    <% if(!isLogin) { %>
       alert("로그인이 필요한 기능입니다.");
       return;
    <% } %>
    var repl = document.getElementById(reply_id);
    if(repl.style.display == "none") {
        repl.style.display = "";
    } else {
        repl.style.display = "none";
    }
    window.location="#"+reply_id;
}

function pageSubmit2(fmObj, module, pageUrl) {
    
    fmObj.action = module + pageUrl;
    fmObj.submit();
}
//-->
</script>
<style type="text/css">
#Sub-page01-Left { position:relative; width:1000px; margin:0 auto;}
#Sub-page01-Left .LY-Sub-Quickmenu { position:absolute;  top:0px; left:980px; }
#Sub-page01-Left .LY-Sub-Quickmenu li { list-style:none; float:left; padding-right:1px;}
a.cont:link {text-decoration: none} 
a.cont:visited {text-decoration: none} 
a.cont:active {text-decoration: none} 
a.cont:hover {text-decoration:none}
</style>
</head>
<div id="Sub-page01-Left">
  <div>
    <ul class="LY-Sub-Quickmenu" id="STATICMENU">
	  <li> <a href="javascript:fncShowEditor();">
		   <img src="/images/btn/btn_Com_save01.gif" alt="의견등록" name="Image4" border="0" id="Image4"/></a></li>
    
	</ul>
  </div>
</div>

<!-- start # LY-Container -->
<div class="LY-Container">
	<!-- srart 검색 테이블 감싸기 -->
	<html:form action="/discussion" method="post" name="fmCont" type="kr.go.rndcall.mgnt.discussion.DiscussionForm" enctype="multipart/form-data" >
		<html:hidden name="discussionForm" property="method" value="createDiscussContents"/>
		<html:hidden name="discussionForm" property="vo.discuss_id" value="1"/>
		<html:hidden name="discussionForm" property="searchVO.discuss_id"/>
		<html:hidden name="discussionForm" property="searchVO.dis_cont_id" value="1"/>
		<html:hidden name="discussionForm" property="searchVO.discuss_op_id"/>
		<html:hidden name="discussionForm" property="searchVO.category_id"/>
		<html:hidden name="discussionForm" property="searchVO.menu_sn"/>
		<html:hidden name="discussionForm" property="vo.dis_comment"/>
		
		<% if (mainRoleCD.equals("0000B")) { %>
		<!-- start # 기관 담당자 텝 -->
		<div class="Gi-Tap">
			<ul>
					<li><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireOrgList&searchVO.board_type=QNA&searchVO.menu_sn=12"><img src="../images/content/Gg_Tap01_dw.gif" alt="기관담당자" border="0" /></a></li>
					<li><img src="../images/content/Gg_Tap02_up.gif" alt="함께 생각하세요" border="0" /></li>
			</ul>
		</div>
		<!-- end # 기관 담당자 텝 -->
		
		<br />
		<% } %>
		<logic:notEmpty name="discussionForm" property="voList">
			<logic:iterate name="discussionForm" property="voList" id="contentVO" indexId="rowNum">
			<!-- start # 안내글 -->
			<div class="Gi-Guide">
				<ul class="Guide">
					<li class="Title"><img src="../images/content/Gg_GuideTitle.gif" alt="안내" /></li>
					<li class="Content">
						<bean:define name="contentVO" property="dis_contents" id="dis_con" type="java.lang.String"/>
						<%
							String disCont = dis_con.replaceAll("\r\n", "<br/>");
						%>
						<%=disCont%>
					</li>
				</ul>
				<% if(mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C")) { %>
				<div id="introUpdate_">
				<ul class="GText-input">
					<li class="top">
						<a href="javascript:updateContents('<bean:write name="contentVO" property="discuss_id"/>','<bean:write name="contentVO" property="dis_cont_id"/>', 'introUpdateArea<%=rowNum.intValue()%>')" class="btn_TWrite"><strong>수정</strong></a>
					</li>
				</ul>
				</div>
				<div id="introUpdateArea<%=rowNum.intValue()%>"></div>
				<% } else { %>
				<ul class="GText-end">
					<li>&nbsp;</li>
				</ul>
				<% } %>
			</div>	
			<!-- end # 안내글 -->
			</logic:iterate>
		</logic:notEmpty>	
	</html:form>
	<br />
		
	<!-- start # 의견등록 -->
	<html:form action="/discussion" enctype="multipart/form-data" method="post" name="fm" type="kr.go.rndcall.mgnt.discussion.DiscussionForm">
		<html:hidden name="discussionForm" property="method" value="createDiscussOpin"/>
		<html:hidden name="discussionForm" property="vo.discuss_id" value="1"/>
		<html:hidden name="discussionForm" property="searchVO.discuss_id"/>
		<html:hidden name="discussionForm" property="searchVO.menu_sn"/>
		<div class="Gi-Comment" id="fopin_table" style="display:none">
			<ul>
				<li><img src="../images/content/Gg_Con_Title.gif" alt="의견등록" /></li>
				<li class="Comment">
					<textarea name="vo.dis_comment" cols="0"  rows="0" id="reply_txt" style="overflow-y:auto;"></textarea>
		    		<a href="javascript:fncSave()"><img src="../images/btn/btn_Com_save.gif" alt="의견등록" border="0" /></a></li>
				<li class="Btn_File"><a href="javascript:funcFileAdd('fileArea', '100');" class="btn_TFile"><strong>파일추가</strong></a> <a href="javascript:funcFileDel('fileArea')" class="btn_TDel"><strong>파일삭제</strong></a></li>
				<li class="File"><div id="fileArea" style="margin-left:37px;padding-left:1px;"></div></li>
				<li class="End"></li>
			</ul>
		</div>
	<!-- end # 의견등록 -->
	</html:form>
	<br />
	<br />
	
	<!-- start # Basic-List -->
	<div class="Basic-List-area">
		<!-- start # 총건수 -->
		<ul class="Num">
			<li>총건수 : <span><bean:write name="discussionForm" property="totRowCount"/></span>건 </li>
		</ul>
		<!-- start # 검색 -->
		<html:form action="/discussion" method="post" name="fmDetail" type="kr.go.rndcall.mgnt.discussion.DiscussionForm">
			<html:hidden name="discussionForm" property="method" value="retrieveDiscussDetail"/>
			<html:hidden name="discussionForm" property="searchVO.discuss_id"/>
			<html:hidden name="discussionForm" property="searchVO.discuss_op_id"/>
			<html:hidden name="discussionForm" property="searchVO.menu_sn"/>
			<html:hidden name="discussionForm" property="orderByTarget"/>
			<html:hidden name="discussionForm" property="orderByMethod"/>
			<html:hidden name="discussionForm" property="maxIndexPages"/>
			<html:hidden name="discussionForm" property="maxPageItems"/>
			<html:hidden name="discussionForm" property="pagerOffset"/>
			<!-- start # 리스트 검색 -->
			<ul class="List-Search">
				<li class="Day">
				<html:select name="discussionForm" property="searchVO.search_type" title="검색선택">
					<html:option value="10601">작성자</html:option> 
					<html:option value="10602">내용</html:option> 
				</html:select>
				</li>
				<li><html:text name="discussionForm" property="searchVO.search_text"/></li>
				<li><a href="javascript:fncSearch()" class="btn_TSearch"><strong>검색</strong></a></li>
			</ul>
		</html:form>

		<html:form action="/discussion" enctype="multipart/form-data" method="post" name="fmOpin" type="kr.go.rndcall.mgnt.discussion.DiscussionForm">
			<html:hidden name="discussionForm" property="method" value="createDiscussOpinOpin"/>		            
			<html:hidden name="discussionForm" property="searchVO.discuss_id"/>
			<html:hidden name="discussionForm" property="searchVO.dis_cont_id"/>
			<html:hidden name="discussionForm" property="searchVO.discuss_op_id"/>
			<html:hidden name="discussionForm" property="searchVO.p_discuss_op_id"/>
			<html:hidden name="discussionForm" property="searchVO.menu_sn"/>
			<html:hidden name="discussionForm" property="vo.discuss_op_id"/>
			<html:hidden name="discussionForm" property="vo.discuss_id" value="1"/>
			<html:hidden name="discussionForm" property="orderByTarget"/>
			<html:hidden name="discussionForm" property="orderByMethod"/>
			<html:hidden name="discussionForm" property="maxIndexPages"/>
			<html:hidden name="discussionForm" property="maxPageItems"/>
			<html:hidden name="discussionForm" property="pagerOffset"/>
			<!-- start # 리스트 목록 -->
			<ul class="List">
				<li>
					<table border="0" cellspacing="0" cellpadding="0" class="Gi-Comnent-List">
						<thead>
  							<tr>
								<th class="Btmline" width="12%">작성자ID</th>
								<th class="Btmline" width="53%">내용 <a href="javascript:allView()"><img src="/images/btn/Line_Add.gif" title="전체열기"/></a>
		 						   <a href="javascript:allClose()"><img src="/images/btn/Line_Del.gif" title="전체닫기"/></a></th>
								<th class="Btmline" width="13%">의견게시일</th>
								<th class="Btmline" width="22%">추천여부</th>
				            </tr>
						</thead>
						<logic:notEmpty name="discussionForm" property="voList1">
							<logic:iterate name="discussionForm" property="voList1" id="repVo" indexId="commRowNum">
							<tbody>
	  							<tr>
									<td><strong><bean:write name="repVo" property="reg_id" /></strong></td>
									<td class="Left">
		 						       <!-- 내용 -->
		 						       <% DiscussionVO dvo = (DiscussionVO)repVo; %>
									    <span id="shortComment<bean:write name="repVo" property="discuss_op_id" />">				    
									    <a href="javascript:showContents('<bean:write name="repVo" property="discuss_op_id" />')">
									        <%= StringUtil.getStrCut(dvo.getDis_comment(), 70, "..")%>&nbsp;<font color="#fc5a0e">(<bean:write name="repVo" property="reply_cnt"/>)</font></a></span>
									    <div id="opin<bean:write name="repVo" property="discuss_op_id" />" style="display:none;">
									    <a class="cont" name="opinLink<bean:write name="repVo" property="discuss_op_id" />" href="javascript:hiddenComment('<bean:write name="repVo" property="discuss_op_id" />')" onFocus="this.blur()"><%= StringUtil.toHtmlString(dvo.getDis_comment()) %>&nbsp;<font color="#fc5a0e">(<bean:write name="repVo" property="reply_cnt"/>)</font></a>
									    <!-- 첨부파일 --> 
										<logic:notEmpty name="repVo" property="reply_file_list">
										  <ul style="font-weight:normal;list-style:none;line-height:100%">
										    <logic:iterate name="repVo" property="reply_file_list" id="repFileVo" indexId="commRowNum1">
										      <li style="padding-top:3px"><a href="javascript:downLoad_Des('<bean:write name="repFileVo" property="file_nm" />','<bean:write name="repFileVo" property="file_path" />','Y')"><img src="/images/icon/disk.gif"/> <span  style="font-size:10pt;"><bean:write name="repFileVo" property="file_nm" /> (<bean:write name="repFileVo" property="file_size" /> bytes)</span></a></li>
										    </logic:iterate>				            
										  </ul>
										</logic:notEmpty>
										<!-- 첨부파일 끝 -->
		 						       <!-- 내용 끝 -->
		 						       
		 						       
									   <div>
									   <%
								            DiscussionVO v1 = (DiscussionVO)repVo;				            
								        %>
								        <% if(disId.equals(v1.getReg_id()) || mainRoleCD.equals("0000C") || mainRoleCD.equals("0000Z")) { %>
									   <a href="javascript:fncUpdateOpinion('opUpdateArea<%=commRowNum.intValue()%>','<bean:write name="repVo" property="discuss_op_id" />')"><img src="../images/btn/btn_Pinion_ListRe.gif" alt="수정" border="0" /></a> 
									   <a href="javascript:fncDelOpinion('<bean:write name="repVo" property="discuss_op_id" />', 'reply<bean:write name="repVo" property="discuss_op_id" />')"><img src="../images/btn/btn_Pinion_ListDel.gif" alt="삭제" border="0" /></a>
									   	<% } %>
									   	 <a href="javascript:fncViewReply('replyArea<%=commRowNum.intValue()%>')"><img style="vertical-align:top;" src="../images/btn/btn_Pinion_Write.gif" alt="답글등록" border="0" /></a>
									   </div>	
				
									   <!-- start # 의견수정영역 -->
										<div id="opUpdateArea<%=commRowNum.intValue()%>" style="margin-top:4px"></div>
										<!-- end # 의견수정영역 -->
										</div>				   
									</td>
									<td>
										<bean:write name="repVo" property="ymd_reg_dt" />
										<span id="add_reg_dt<bean:write name="repVo" property="discuss_op_id" />" style="display:none;"><br/><bean:write name="repVo" property="reg_dt" /></span>
									</td>
									<td>
										<a class="btn_Recommend" href="javascript:ap_op_count('ap_cnt_<bean:write name="repVo" property="discuss_op_id" />', '<bean:write name="repVo" property="discuss_op_id" />','10501');"><span id="ap_cnt_<bean:write name="repVo" property="discuss_op_id" />"><bean:write name="repVo" property="recom_cnt" /></span></a>&nbsp;
										<a class="btn_Opposite" href="javascript:ap_op_count('op_cnt_<bean:write name="repVo" property="discuss_op_id" />', '<bean:write name="repVo" property="discuss_op_id" />','10502');"><span id="op_cnt_<bean:write name="repVo" property="discuss_op_id" />"><bean:write name="repVo" property="opp_cnt" /></span></a>
									</td>
						        </tr>
							</tbody>
							<tbody id="reply<bean:write name="repVo" property="discuss_op_id" />" style="display:none;">
								<logic:notEmpty name="repVo" property="reply_list">
								    <logic:iterate name="repVo" property="reply_list" id="repList" indexId="replyNum">
		  							<tr class="Comment">
										<td class="ID Text_L" width="12%" noWrap><strong><bean:write name="repList" property="reg_id" /></strong></td>
										<td colspan="2" width="66%" class="Text_L Content Left">
											<%
											  DiscussionVO reDvo = (DiscussionVO)repList;
											%>
											<%= StringUtil.toHtmlString(reDvo.getDis_comment()) %>
											<div class="FileDown">
										   		<!-- 첨부파일 -->
										   		<logic:notEmpty name="repList" property="reply_reply_file_list">
										   		<ul class="File">
										   		    <logic:iterate name="repList" property="reply_reply_file_list" id="repFilelistVo" indexId="fileNum">
													<li><a href="javascript:downLoad_Des('<bean:write name="repFilelistVo" property="file_nm" />','<bean:write name="repFilelistVo" property="file_path" />','Y')"><img src="/images/icon/disk.gif"/> <span style="font-size:10pt;"><bean:write name="repFilelistVo" property="file_nm" /> (<bean:write name="repFilelistVo" property="file_size" /> bytes)</span></a></li>
													</logic:iterate>
												</ul>
												</logic:notEmpty>
												<!-- start # 답글수정영역 -->				          	          
											    <div id="replUpdateArea<bean:write name="repList" property="discuss_op_id" />"></div>
											    <!-- end # 답글수정영역 -->
												<%
									              DiscussionVO v = (DiscussionVO)repVo;
									              if(disId.equals(v.getReg_id()) || mainRoleCD.equals("0000C") || mainRoleCD.equals("0000Z")) { 
									             %>
												<!-- 수정삭제 버튼 -->
												<ul class="Btn">
													<li><a href="javascript:comUpdateOpinion('replUpdateArea<bean:write name="repList" property="discuss_op_id" />','<bean:write name="repList" property="discuss_op_id" />','<bean:write name="repVo" property="discuss_op_id" />')"><img src="../images/btn/btn_Pinion_ListRe.gif" alt="수정" border="0" /></a></li>
													<li><a href="javascript:fncDelOpinion('<bean:write name="repList" property="discuss_op_id" />',null,'<bean:write name="repVo" property="discuss_op_id" />')"><img src="../images/btn/btn_Pinion_ListDel.gif" alt="삭제" border="0" /></a></li>
												</ul>										
												<% } %>										
												<!-- 수정삭제 버튼 -->
											</div>
										</td>
										<td class="Day" width="22%" noWrap><bean:write name="repList" property="reg_dt" /></td>
		  							</tr>
									</logic:iterate>
								</logic:notEmpty>
						  		<tr class="Comment_AreaUp" id="replyArea<%=commRowNum.intValue()%>" style="display:none;">
									<td colspan="4">
										<!-- start # 댓글 달기 -->	
								   		<div class="Comment_Write" style="text-align:left">
								      		<ul>
									  			 <li class="Title">댓글</li>
									  			 <li class="Write">
									  			 	<textarea name="vo.dis_comment_reply" cols="0"  rows="0" style="overflow-y:auto;"></textarea>
												   <a href="javascript:fncReplySave('replyArea<%=commRowNum.intValue()%>','<bean:write name="repVo" property="discuss_op_id"/>')"><img src="../images/btn/btn_Comment_Write.gif" alt="등록하기" border="0" /></a></li>
									  	 		<li class="Btn"><a href="javascript:funcFileAdd('replyfileArea<%=commRowNum.intValue()%>', '90')"><img src="../images/btn/Btn_file_Add.gif"  border="0" alt="파일추가" /></a> <a href="javascript:funcFileDel('replyfileArea<%=commRowNum.intValue()%>')"><img src="../images/btn/Btn_file_Del.gif" border="0" alt="파일삭제" /></a> </li>
									 		</ul>
									 		<br />
									 		<br />
								  	 		<div id="replyfileArea<%=commRowNum.intValue()%>" style="margin-left:70px;margin-bottom:3px;position:relative;"></div>
								   		</div>
									</td>
					      		</tr>
							</tbody>
							</logic:iterate>
						</logic:notEmpty>
						<logic:empty name="discussionForm" property="voList1">
							<tr>
							  <td colspan="4">등록된 내용이 없습니다.</td>
							</tr>
						</logic:empty>
					</table>
				</li>
			</ul>
			<ul class="Page-Num">
				<%@ include file="/include/page.jsp"%>
			</ul>
		</html:form>
		<!-- end # Basic-List -->
	</div>
</div>
</div>
<!-- end # LY-Container -->
<%@  include file="/include/bottom.jsp"%>

<html:form action="/discussion" method="post" name="fmReload" type="kr.go.rndcall.mgnt.discussion.DiscussionForm">
	<html:hidden name="discussionForm" property="method" value="retrieveDiscussDetail"/>
	<html:hidden name="discussionForm" property="searchVO.discuss_id"/>
	<html:hidden name="discussionForm" property="searchVO.category_id"/>
	<html:hidden name="discussionForm" property="orderByTarget"/>
	<html:hidden name="discussionForm" property="orderByMethod"/>
	<html:hidden name="discussionForm" property="maxIndexPages"/>
	<html:hidden name="discussionForm" property="maxPageItems"/>
	<html:hidden name="discussionForm" property="pagerOffset"/>
	<html:hidden name="discussionForm" property="searchVO.menu_sn"/>
</html:form>
    
<html:form action="/discussion" method="post" name="fmDelete" type="kr.go.rndcall.mgnt.discussion.DiscussionForm">
	<html:hidden name="discussionForm" property="method" value="deleteDiscussReply"/>
	<html:hidden name="discussionForm" property="searchVO.discuss_op_id"/>
	<html:hidden name="discussionForm" property="searchVO.discuss_id"/>
	<html:hidden name="discussionForm" property="searchVO.category_id"/>
	<html:hidden name="discussionForm" property="searchVO.p_discuss_op_id"/>  
	<html:hidden name="discussionForm" property="orderByTarget"/>
	<html:hidden name="discussionForm" property="orderByMethod"/>
	<html:hidden name="discussionForm" property="maxIndexPages"/>
	<html:hidden name="discussionForm" property="maxPageItems"/>
	<html:hidden name="discussionForm" property="pagerOffset"/>
	<html:hidden name="discussionForm" property="searchVO.menu_sn"/>
</html:form>
     
<html:form action="/discussion" method="post" name="fmDisDelete" type="kr.go.rndcall.mgnt.discussion.DiscussionForm">
	<html:hidden name="discussionForm" property="method" value="deleteDiscuss"/>      			
	<html:hidden name="discussionForm" property="searchVO.discuss_id"/>
	<html:hidden name="discussionForm" property="searchVO.category_id"/>
	<html:hidden name="discussionForm" property="orderByTarget"/>
	<html:hidden name="discussionForm" property="orderByMethod"/>
	<html:hidden name="discussionForm" property="maxIndexPages"/>
	<html:hidden name="discussionForm" property="maxPageItems"/>
	<html:hidden name="discussionForm" property="pagerOffset"/>
</html:form>
    
<html:form action="/discussion" method="post" name="fmDisUpdate" type="kr.go.rndcall.mgnt.discussion.DiscussionForm">
	<html:hidden name="discussionForm" property="method" value="retrieveDiscussUpdateForm"/>      			
	<html:hidden name="discussionForm" property="searchVO.discuss_id"/>
	<html:hidden name="discussionForm" property="searchVO.category_id"/>
	<html:hidden name="discussionForm" property="orderByTarget"/>
	<html:hidden name="discussionForm" property="orderByMethod"/>
	<html:hidden name="discussionForm" property="maxIndexPages"/>
	<html:hidden name="discussionForm" property="maxPageItems"/>
	<html:hidden name="discussionForm" property="pagerOffset"/>
	<html:hidden name="discussionForm" property="searchVO.menu_sn"/>
</html:form> 
    
<html:form action="/discussion" method="post" name="fmDelCont" type="kr.go.rndcall.mgnt.discussion.DiscussionForm">
	<html:hidden name="discussionForm" property="method" value="deleteDiscussContents"/>      			
	<html:hidden name="discussionForm" property="searchVO.discuss_id"/>
	<html:hidden name="discussionForm" property="searchVO.category_id"/>
	<html:hidden name="discussionForm" property="searchVO.dis_cont_id"/>
	<html:hidden name="discussionForm" property="orderByTarget"/>
	<html:hidden name="discussionForm" property="orderByMethod"/>
	<html:hidden name="discussionForm" property="maxIndexPages"/>
	<html:hidden name="discussionForm" property="maxPageItems"/>
	<html:hidden name="discussionForm" property="pagerOffset"/>
	<html:hidden name="discussionForm" property="searchVO.menu_sn"/>
</html:form>
<form name="fileDownLoad" method="post" action="/downLoad.do" >
  <input type="hidden" name="fileNM"/>
  <input type="hidden" name="saveFileNM"/>
  <input type="hidden" name="filePath"/>
  <input type="hidden" name="desCipher" value="Y"/>
  <input type="hidden" name="imgYn" value="N"/>
  <input type="hidden" name="tcoFileYn" value="Y"/>
</form>
