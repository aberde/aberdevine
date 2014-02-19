<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>메일발송</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/whoya/include/header.jsp" />

<script type="text/javascript">
/**
 * 전역변수로 사용할 데이터
 * JSON형식의 데이터
 *   layout: layout  // dhtmlXLayoutObject 객체
 *   aCell: aCell  // dhtmlXLayoutObject의 cell 객체 'a'
 *   aForm: aForm  // dhtmlXLayoutObject의 cell 객체 'a'의 dhtmlxForm 객체
 *   aCellRegFormData: aCellRegFormData  // dhtmlxForm의 UI데이터
 *   statusbar: statusbar  // statusbar 객체
 */
var whoyaGlobalData = {};

function init() {
	// #########################################
	// ## 레이아웃생성
	// #########################################
	var layoutData = {
		layout_Pattern: "1C"
	};
	whoyaGlobalData.layout = whoya.dhtmlx.layout.init(layoutData);
	// #########################################
	
	
	// #########################################
	// ## layout cell a 
	// #########################################
	var aCellData = {
		layout: whoyaGlobalData.layout
		, width: ""
	};
	// 화면 layout의 해당 cell 정의 
	whoyaGlobalData.aCell = whoya.dhtmlx.layout.cell.init(aCellData);
	// #########################################
	
	
	// #########################################
	// ## layout cell a에 form생성
	// #########################################
	// 등록 폼
	whoyaGlobalData.aCellRegFormData = {
		cell: whoyaGlobalData.aCell
		, formData: [
   			{ type: "fieldset", name: "formField", label: "발송메일 등록", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "input", label: "받는사람", name: "recptnPerson", value: "" },
				{ type: "input", label: "제목", name: "sj", value: "" },
				{ type: "file", label: "파일첨부", name: "file_1", value: "" },
				{ type: "input", label: "발신내용", name: "emailCn", value: "", rows: 25 },
				{ type: "button", name: "regBtn", value: "등록" }
			] }
   		]
	};
	// 화면 layout cell a에 dhtmlxForm 객체 생성.
	whoyaGlobalData.aForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.aCellRegFormData);
	formEvent();
	// #########################################
	
	
	// #########################################
	// ## layout에 statusbar 생성
	// #########################################
	var statusbarData = {
		layout: whoyaGlobalData.layout	
	};
	whoyaGlobalData.statusbar = whoya.dhtmlx.statusbar(statusbarData);
	// #########################################
}


// #######################################################################
// ## event 생성
// #######################################################################
// form event 생성
function formEvent() {
	whoyaGlobalData.aForm.attachEvent("onButtonClick", function(name) {
		if ( name == "regBtn" ) {  // 등록
			if ( confirm("저장하시겠습니까?") ) {
				document.getElementById("activeStatusBar").innerHTML = "";
				
				$.ajaxFileUpload({
					url: "<c:url value='/whoya/cop/ems/insertSndngMail.do' />"
					, secureuri: false
					, fileElementId: ["file_1"]
					, data: whoyaGlobalData.aForm.getFormData()
					, dataType: 'json'
					, success: function (data, status) {
						if ( data.status == "SUCCESS" ) {
							alert("저장하였습니다.");
						} else {
							alert("저장에 실패하였습니다.");
						}
					}
					, error: function (data, status, e) {
						alert(e);
					}
				});
			}
		}
	});
}
// #######################################################################

$(function(document) {
	init();
});
</script>
</head>
<body>
</body>
</html>
