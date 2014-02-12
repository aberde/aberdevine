<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>템플릿관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/whoya/include/header.jsp" />

<script type="text/javascript">
var oProc;
var form;

function init() {
	dhtmlx.image_path = "<c:url value='/dhtmlx/dhtmlx_pro_full/imgs/'/>";
    oProc = new dhtmlXLayoutObject(document.body, "1C");
    oProc.dhxWins.setEffect("move", true);
    
    var toolbar;
    var combo;
    var grid;
	
	/** 
	 * 등록될 formData UI 구성.
	 */
	function inputFormData() {
		var formData = [
			{ type: "fieldset", name: "formField", label: "발송메일 등록", list: [
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 170 },
					{ type: "label", label: "받는사람" },
					{ type: "newcolumn" },
					{ type: "input", name: "recptnPerson", value: "" }
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 170 },
					{ type: "label", label: "제목" },
					{ type: "newcolumn" },
					{ type: "input", name: "sj", value: "" }
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 170 },
					{ type: "label", label: "파일첨부" },
					{ type: "newcolumn" },
					{ type: "file", name: "file_1", value: "" }
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 170 },
					{ type: "label", label: "발신내용" },
					{ type: "newcolumn" },
					{ type: "input", name: "emailCn", value: "", rows: 25 }
				] },
				{ type: "block", list: [
					{ type: "button", name: "regBtn", value: "등록" }
				] }
			] }
		];
		
		var oProcA = oProc.cells("a");
		oProcA.hideHeader();
		form = oProcA.attachForm(formData);
		form.setFontSize("11px");
		
		// 버튼 클릭 이벤트.
		form.attachEvent("onButtonClick", function(name) {
			if ( name == "regBtn" ) {
				if ( confirm("저장하시겠습니까?") ) {
					document.getElementById("activeStatusBar").innerHTML = "";
					
					$.ajaxFileUpload({
						url: "/whoya/cop/ems/insertSndngMail.do"
						, secureuri: false
						, fileElementId: ["file_1"]
						, data: form.getFormData()
						, dataType: 'json'
						, success: function (data, status) {
							if(typeof(data.error) != 'undefined') {
								if(data.error != '') {
									alert(data.error);
								} else {
									alert(data.msg);
								}
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

    /**
     * layout status bar
     */
    function fn_layout_statusbar() {
		var main_status = oProc.attachStatusBar();
		main_status.setText("<div><table><td id='activeImg'><img src='<c:url value="/dhtmlx/dhtmlx_pro_full/imgs/run_exc.gif" />'></td><td id='activeStatusBar' valign='middle'></td></table></div>"); 
    }
    
    inputFormData();
    fn_layout_statusbar();
}

$(document).ready(function() {
	init();
});
</script>
</head>
<body>
<form name="frm" method="post" enctype="multipart/form-data">
</form>
</body>
</html>