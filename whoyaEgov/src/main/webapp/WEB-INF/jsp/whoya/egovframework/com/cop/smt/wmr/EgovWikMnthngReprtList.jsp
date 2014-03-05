<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>주간/월간보고관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/whoya/include/header.jsp" />

<script type="text/javascript">
function init() {
	// #########################################
	// ## 레이아웃생성
	// #########################################
	whoyaGlobalData.layout = whoya.dhtmlx.layout.init();
	// #########################################
	
	
	// #########################################
	// ## 툴바 생성
	// #########################################
	var toolbarData = {
		layout: whoyaGlobalData.layout
	};
	whoyaGlobalData.toolbar = whoya.dhtmlx.layout.toolbar.init(toolbarData);
	whoyaGlobalData.toolbar.addText("lbl_searchSttus", 1, "조회조건");
	whoyaGlobalData.toolbar.addText("searchSttus", 2, "");
	whoyaGlobalData.toolbar.addText("searchDe", 3, "");
	whoyaGlobalData.toolbar.addInput("searchBgnDe", 4, "");
	whoyaGlobalData.toolbar.addText("lbl_fromTo", 5, "~");
	whoyaGlobalData.toolbar.addInput("searchEndDe", 6, "");
	whoyaGlobalData.toolbar.addText("searchSe", 7, "");
	whoyaGlobalData.toolbar.addText("searchCnd", 8, "");
	whoyaGlobalData.toolbar.addInput("searchWrd", 9, "");
	
	// selectBox 생성
	var comboDIV = whoyaGlobalData.toolbar.objPull[whoyaGlobalData.toolbar.idPrefix+"searchSttus"].obj;
	whoyaGlobalData.toolbar.objPull[whoyaGlobalData.toolbar.idPrefix+"searchSttus"].obj.innerHTML = "";
	whoyaGlobalData.combo = new dhtmlXCombo(comboDIV,"alfa",140);
	whoyaGlobalData.combo.addOption([
   		["", "--승인 여부--"]
   		, ["0", "미승인"]
		, ["1", "승인"]
   	]);
	whoyaGlobalData.combo.selectOption(0);

	// selectBox 생성
	var comboDIV2 = whoyaGlobalData.toolbar.objPull[whoyaGlobalData.toolbar.idPrefix+"searchDe"].obj;
	whoyaGlobalData.toolbar.objPull[whoyaGlobalData.toolbar.idPrefix+"searchDe"].obj.innerHTML = "";
	whoyaGlobalData.combo2 = new dhtmlXCombo(comboDIV2,"alfa",140);
	whoyaGlobalData.combo2.addOption([
   		["", "--일자조회조건--"]
		, ["0", "보고일자"]
		, ["1", "해당일자"]
   	]);
	whoyaGlobalData.combo2.selectOption(0);
	
	// calendar 생성
	whoyaGlobalData.calendar = new dhtmlXCalendarObject(whoyaGlobalData.toolbar.getInput("searchBgnDe"));
	whoyaGlobalData.calendar.setDateFormat("%Y-%m-%d");
	whoyaGlobalData.calendar.hideTime();
	whoyaGlobalData.calendar2 = new dhtmlXCalendarObject(whoyaGlobalData.toolbar.getInput("searchEndDe"));
	whoyaGlobalData.calendar2.setDateFormat("%Y-%m-%d");
	whoyaGlobalData.calendar2.hideTime();
	
	// selectBox 생성
	var comboDIV3 = whoyaGlobalData.toolbar.objPull[whoyaGlobalData.toolbar.idPrefix+"searchSe"].obj;
	whoyaGlobalData.toolbar.objPull[whoyaGlobalData.toolbar.idPrefix+"searchSe"].obj.innerHTML = "";
	whoyaGlobalData.combo3 = new dhtmlXCombo(comboDIV3,"alfa",140);
	whoyaGlobalData.combo3.addOption([
   		["", "--보고유형 조회조건--"]
   		, ["1", "주간보고"]
		, ["2", "월간보고"]
   	]);
	whoyaGlobalData.combo3.selectOption(0);

	// selectBox 생성
	var comboDIV4 = whoyaGlobalData.toolbar.objPull[whoyaGlobalData.toolbar.idPrefix+"searchCnd"].obj;
	whoyaGlobalData.toolbar.objPull[whoyaGlobalData.toolbar.idPrefix+"searchCnd"].obj.innerHTML = "";
	whoyaGlobalData.combo4 = new dhtmlXCombo(comboDIV4,"alfa",140);
	whoyaGlobalData.combo4.addOption([
   		["", "--제목/작성자 조회조건--"]
		, ["0", "제목"]
		, ["1", "작성자"]
   	]);
	whoyaGlobalData.combo4.selectOption(0);
	
	// toolbar의 Button정의
	var toolbarAddButton = {
		toolbar: whoyaGlobalData.toolbar
		, btn_Delete: false
		, btn_Undo: false
		, btn_Save: false
		, btn_Print: false
		, btn_Excel: false
	};
	whoya.dhtmlx.layout.toolbar.addButton(toolbarAddButton);
	toolbarEvent();
	// #########################################

	
	// #########################################
	// ## layout cell a 
	// #########################################
	var aCellData = {
		layout: whoyaGlobalData.layout
	};
	// 화면 layout의 해당 cell 정의 
	whoyaGlobalData.aCell = whoya.dhtmlx.layout.cell.init(aCellData);
	// #########################################
	
	
	// #########################################
	// ## layout cell a에 grid생성
	// #########################################
	var aCellGridData = {
		cell: whoyaGlobalData.aCell
		, setHeader: "번호,보고유형,보고일자,보고서제목,해당일자,작성자,승인,보고서ID"
		, setColumnIds: "no,reprtSe,reprtDe,reprtSj,reprtBgnEnd,wrterNm,confmDt,reprtId"
		, setInitWidths: "100,150,150,*,*,100,100,100"
		, setColAlign: "center,center,center,center,center,center,center,center"
		, setColTypes: "ro,ro,ro,ro,ro,ro,ro,ro"
		, enableResizing: "false,false,false,false,false,false,false,false"
		, enableTooltips: "false,false,false,false,false,false,false,false"
		, setColSorting: "str,str,str,str,str,str,str,str"
		, setColumnHidden: [
			{ id: 7 }
		]
	};
	// 화면 layout cell a에 dhtmlxGrid 객체 생성.
	whoyaGlobalData.aGrid = whoya.dhtmlx.layout.cell.grid(aCellGridData);
	gridEvent();
	// #########################################
	
	
	// #########################################
	// ## layout cell b
	// #########################################
	var bCellData = {
		cell_target: "b"
		, layout: whoyaGlobalData.layout
		, width: ""
	};
	// 화면 layout의 해당 cell 정의 
	whoyaGlobalData.bCell = whoya.dhtmlx.layout.cell.init(bCellData);
	// #########################################
	
	
	// #########################################
	// ## layout cell b에 form생성
	// #########################################
	// 등록 폼
	whoyaGlobalData.bCellRegFormData = {
		cell: whoyaGlobalData.bCell
		, formData: [
			{ type: "fieldset", name: "formField", label: "주간/월간보고 등록", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "template", label: "보고유형", list: [
					{ type: "settings", labelWidth: 50, inputWidth: 170, position: "label-right" },
					{ type: "label", labelWidth: 130 },
					{ type: "newcolumn" },
					{ type: "radio", label: "주간보고", name: "reprtSe", value: "1" },
					{ type: "newcolumn" },
					{ type: "radio", label: "월간보고", name: "reprtSe", value: "2" }
				] },
				{ type: "calendar", label: "보고일자", name: "reprtDe", dateFormat: "%Y-%m-%d" },
				{ type: "template", label: "해당일자", list: [
					{ type: "settings", labelWidth: 50, inputWidth: 70, position: "label-right" },
					{ type: "label", labelWidth: 130 },
					{ type: "newcolumn" },
					{ type: "calendar", name: "reprtBgnDe", dateFormat: "%Y-%m-%d" },
					{ type: "newcolumn" },
					{ type: "label", label: "~", labelWidth: 15 },
					{ type: "newcolumn" },
					{ type: "calendar", name: "reprtEndDe", dateFormat: "%Y-%m-%d" }
				] },
				{ type: "template", label: "작성자", name: "wrterNm", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "보고대상자", name: "reportrNm", format: whoya.dhtmlx.form.format.reportrInfo },
				{ type: "input", label: "보고서제목", name: "reprtSj" },
				{ type: "input", label: "금주보고내용", name: "reprtThswikCn", rows: 5 },
				{ type: "input", label: "차주보고내용", name: "reprtLesseeCn", rows: 5 },
				{ type: "input", label: "특이사항", name: "partclrMatter", rows: 5 },
				{ type: "file", label: "파일첨부", name: "file_1" },
				{ type: "button", name: "regBtn", value: "등록" }
			] }
		]
	};

	// 상세 폼
	whoyaGlobalData.bCellDetailFormData = {
		cell: whoyaGlobalData.bCell
		, formData: [
			{ type: "fieldset", name: "formField", label: "주간/월간보고 상세보기", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "template", label: "보고유형", name: "reprtSe", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "보고일자", name: "reprtDe", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "해당일자", list: [
					{ type: "settings", labelWidth: 50, inputWidth: 70 },
					{ type: "label", labelWidth: 125 },
					{ type: "newcolumn" },
					{ type: "template", name: "reprtBgnDe", format: whoya.dhtmlx.form.format.printData },
					{ type: "newcolumn" },
					{ type: "label", label: "~", labelWidth: 15 },
					{ type: "newcolumn" },
					{ type: "template", name: "reprtEndDe", format: whoya.dhtmlx.form.format.printData }
				] },
				{ type: "template", label: "작성자", name: "wrterNm", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "보고대상자", name: "reportrNm", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "보고서제목", name: "reprtSj", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "금주보고내용", name: "reprtThswikCn", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "차주보고내용", name: "reprtLesseeCn", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "특이사항", name: "partclrMatter", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "파일첨부", name: "atchFileId", format: whoya.dhtmlx.form.format.getFileList},
				{ type: "template", label: "보고서 상태", name: "reprtSttus", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", list: [
					{ type: "button", name: "uptViewBtn", value: "수정" },
					{ type: "newcolumn" },
					{ type: "button", name: "dltBtn", value: "삭제" }
				] }
			] }
		]
	};

	// 수정폼.
	whoyaGlobalData.bCellUpdateFormData = {
		cell: whoyaGlobalData.bCell
		, formData: [
			{ type: "fieldset", name: "formField", label: "주간/월간보고 수정", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "template", label: "보고유형", list: [
					{ type: "settings", labelWidth: 50, inputWidth: 170, position: "label-right" },
					{ type: "label", labelWidth: 130 },
					{ type: "newcolumn" },
					{ type: "radio", label: "주간보고", name: "reprtSe", value: "1" },
					{ type: "newcolumn" },
					{ type: "radio", label: "월간보고", name: "reprtSe", value: "2" }
				] },
				{ type: "calendar", label: "보고일자", name: "reprtDe", dateFormat: "%Y-%m-%d" },
				{ type: "template", label: "해당일자", list: [
					{ type: "settings", labelWidth: 50, inputWidth: 70, position: "label-right" },
					{ type: "label", labelWidth: 130 },
					{ type: "newcolumn" },
					{ type: "calendar", name: "reprtBgnDe", dateFormat: "%Y-%m-%d" },
					{ type: "newcolumn" },
					{ type: "label", label: "~", labelWidth: 15 },
					{ type: "newcolumn" },
					{ type: "calendar", name: "reprtEndDe", dateFormat: "%Y-%m-%d" }
				] },
				{ type: "template", label: "작성자", name: "wrterNm", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "보고대상자", name: "reportrNm", format: whoya.dhtmlx.form.format.reportrInfo },
				{ type: "input", label: "보고서제목", name: "reprtSj" },
				{ type: "input", label: "금주보고내용", name: "reprtThswikCn", rows: 5 },
				{ type: "input", label: "차주보고내용", name: "reprtLesseeCn", rows: 5 },
				{ type: "input", label: "특이사항", name: "partclrMatter", rows: 5 },
				{ type: "file", label: "파일첨부", name: "file_1" },
				{ type: "template", label: "보고서 상태", name: "reprtSttus", format: whoya.dhtmlx.form.format.printData },
				{ type: "button", name: "uptBtn", value: "수정" }
			] }
		]
	};
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
// toolbar event 생성
function toolbarEvent() {
	whoyaGlobalData.toolbar.attachEvent("onClick", function(id) {
		if(id == "btn_Open"){
			whoyaGlobalData.bForm = whoyaGlobalData.bCell.attachForm("");
			search();
		}
		if(id == "btn_Append"){
			// 화면 layout cell b에 dhtmlxForm 객체 생성.
			whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellRegFormData);
			formEvent();
			
			$.ajax({
				url: "<c:url value='/whoya/cop/smt/wmr/addWikMnthngReprt.do' />"
				, success: function(data, textStatus, jqXHR) {
					whoyaGlobalData.bForm.setFormData(data);
				}
				, error: function(jqXHR, textStatus, errorThrown) {
					console.log(jqXHR);
					console.log(textStatus);
					console.log(errorThrown);
					alert(errorThrown);
				}
			});
		}
    });
}

// grid event 생성
function gridEvent() {
	whoyaGlobalData.aGrid.attachEvent("onRowSelect", function(id, ind) {
		// 화면 layout cell b에 dhtmlxForm 객체 생성.
		whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellDetailFormData);
		formEvent();
		
		$.ajax({
			url: "<c:url value='/whoya/cop/smt/wmr/selectWikMnthngReprt.do' />"
			, data: {
				reprtId: whoyaGlobalData.aGrid.cells(id, 7).getValue()
			}
			, success: function(data, textStatus, jqXHR) {
				whoyaGlobalData.bForm.setFormData(data);
			}
			, error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR);
				console.log(textStatus);
				console.log(errorThrown);
				alert(errorThrown);
			}
		});
	});
}

// form event 생성
function formEvent() {
	whoyaGlobalData.bForm.attachEvent("onButtonClick", function(name) {
		if ( name == "regBtn" ) {  // 등록
			if ( confirm("저장하시겠습니까?") ) {
				document.getElementById("activeStatusBar").innerHTML = "";

				var data = whoyaGlobalData.bForm.getFormData(true);
				data.reportrId = $("#reportrId").val();
				data.reportrNm = $("#reportrNm").val();
				
				$.ajaxFileUpload({
					url: "<c:url value='/whoya/cop/smt/wmr/insertWikMnthngReprt.do' />"
					, secureuri: false
					, fileElementId: ["file_1"]
					, data: data
					, dataType: 'json'
					, success: function (data, status) {
						if ( data.status == "SUCCESS" ) {
							alert("저장하였습니다.");
							whoyaGlobalData.bForm = whoyaGlobalData.bCell.attachForm("");
							search();
						} else {
							alert("저장에 실패하였습니다.");
						}
					}
					, error: function (data, status, e) {
						alert(e);
					}
				});
			}
		} else if ( name == "uptViewBtn" ) {  // 수정화면
			$.ajax({
				url: "<c:url value='/whoya/cop/smt/wmr/selectWikMnthngReprt.do' />"
				, data: {
					reprtId: whoyaGlobalData.bForm.getFormData().reprtId
				}
				, success: function(data, textStatus, jqXHR) {
					// 화면 layout cell b에 dhtmlxForm 객체 생성.
					whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellUpdateFormData);
					formEvent();
					whoyaGlobalData.bForm.setFormData(data);
					$("#reportrId").val(data.reportrId);
					$("#reportrNm").val(data.reportrNm);
				}
				, error: function(jqXHR, textStatus, errorThrown) {
					console.log(jqXHR);
					console.log(textStatus);
					console.log(errorThrown);
					alert(errorThrown);
				}
			});
		} else if ( name == "uptBtn" ) {  // 수정
			if ( confirm("저장하시겠습니까?") ) {
				document.getElementById("activeStatusBar").innerHTML = "";

				var data = whoyaGlobalData.bForm.getFormData(true);
				data.reportrId = $("#reportrId").val();
				data.reportrNm = $("#reportrNm").val();
				
				$.ajaxFileUpload({
					url: "<c:url value='/whoya/cop/smt/wmr/updateWikMnthngReprt.do' />"
					, secureuri: false
					, fileElementId: ["file_1"]
					, data: data
					, dataType: 'json'
					, success: function (data, status) {
						if ( data.status == "SUCCESS" ) {
							alert("저장하였습니다.");
							whoyaGlobalData.bForm = whoyaGlobalData.bCell.attachForm("");
							search();
						} else {
							alert("저장에 실패하였습니다.");
						}
					}
					, error: function (data, status, e) {
						alert(e);
					}
				});
			}
		} else if ( name == "dltBtn" ) {  // 삭제
			if ( confirm("삭제하시겠습니까?") ) {
				$.ajax({
					url: "<c:url value='/whoya/cop/smt/wmr/deleteWikMnthngReprt.do' />"
					, data: whoyaGlobalData.bForm.getFormData()
					, type: "POST"
					, dataType: "json"
					, success: function(data, textStatus, jqXHR) {
						if ( data.status == "SUCCESS" ) {
							alert("저장하였습니다.");
							whoyaGlobalData.bForm = whoyaGlobalData.bCell.attachForm("");
							search();
						} else {
							alert("저장에 실패하였습니다.");
						}
					}
					, error: function(jqXHR, textStatus, errorThrown) {
						console.log(jqXHR);
						console.log(textStatus);
						console.log(errorThrown);
						alert(errorThrown);
					}
				});
			}
		}
	});
}
// #######################################################################



/**
 * 조회버튼 클릭시
 */
function search() {
	whoyaGlobalData.layout.progressOn();
	whoyaGlobalData.aGrid.clearAll();
	document.getElementById("activeStatusBar").innerHTML = "";

	$.ajax({
		url: "<c:url value='/whoya/cop/smt/wmr/selectWikMnthngReprtListJSON.do' />"
		, data: {
			searchSttus: whoyaGlobalData.combo.getSelectedValue()
			, searchDe: whoyaGlobalData.combo2.getSelectedValue()
			, searchBgnDe: whoyaGlobalData.toolbar.getValue("searchBgnDe")
			, searchEndDe: whoyaGlobalData.toolbar.getValue("searchEndDe")
			, searchSe: whoyaGlobalData.combo3.getSelectedValue()
			, searchCnd: whoyaGlobalData.combo4.getSelectedValue()
			, searchWrd: whoyaGlobalData.toolbar.getValue("searchWrd")
		}
		, dataType: "json"
		, success: function(data, textStatus, jqXHR) {
			whoyaGlobalData.aGrid.attachEvent("onXLE", function(){
				whoyaGlobalData.layout.progressOff();
    		});
    	  	
    	  	whoyaGlobalData.aGrid.clearAll();
    	  	whoyaGlobalData.aGrid.parse(data.list, "json");
    	  	whoyaGlobalData.aGrid.setSelectedRow(0);
    		document.getElementById("activeStatusBar").innerHTML = "조회되었습니다";
		}
		, error: function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR);
			console.log(textStatus);
			console.log(errorThrown);
			alert(errorThrown);
		}
	});
}


$(function(document) {
	init();
});
</script>
</head>
<body>
</body>
</html>
