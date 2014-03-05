<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>권한관리</title>
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
	whoyaGlobalData.toolbar.addText("lbl_searchKeyword", 1, "권한 명");
	whoyaGlobalData.toolbar.addInput("searchKeyword", 2, "", 200);

	// toolbar의 Button정의
	var toolbarAddButton = {
		toolbar: whoyaGlobalData.toolbar
		, btn_Open: true
		, btn_Append: true
		, btn_Delete: true
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
		, setHeader: "권한코드,권한 명,권한코드설명,권한등록일자,롤 정보"
		, setColumnIds: "authorCode,authorNm,authorDc,authorCreatDe,roleLink"
		, setInitWidths: "150,150,*,150,150"
		, setColAlign: "center,center,center,center,center"
		, setColTypes: "ro,ro,ro,ro,img"
		, enableResizing: "false,false,false,false,false"
		, enableTooltips: "false,false,false,false,false"
		, setColSorting: "str,str,str,str,str"
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
	// 등록폼.
	whoyaGlobalData.bCellRegFormData = {
		cell: whoyaGlobalData.bCell
		, formData: [
			{ type: "fieldset", name: "formField", label: "권한 등록", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "input", label: "권한 코드", name: "authorCode", value: "" },
				{ type: "input", label: "권한 명", name: "authorNm", value: "" },
				{ type: "input", label: "설명", name: "authorDc", value: "" },
				{ type: "template", label: "등록일자", name: "authorCreatDe", format: whoya.dhtmlx.form.format.printData },
				{ type: "button", name: "regBtn", value: "등록" }
			] }
		]
	};
	
	// 수정폼.
	whoyaGlobalData.bCellUpdateFormData = {
		cell: whoyaGlobalData.bCell
		, formData: [
			{ type: "fieldset", name: "formField", label: "권한 수정", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "template", label: "권한 코드", name: "authorCode", format: whoya.dhtmlx.form.format.printData },
				{ type: "input", label: "권한 명", name: "authorNm", value: "" },
				{ type: "input", label: "설명", name: "authorDc", value: "" },
				{ type: "template", label: "등록일자", name: "authorCreatDe", format: whoya.dhtmlx.form.format.printData },
				{ type: "label", list: [
					{ type: "button", name: "uptBtn", value: "수정" },
					{ type: "newcolumn" },
					{ type: "button", name: "dltBtn", value: "삭제" }
				] }
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
			search();
			whoyaGlobalData.bCell.attachForm("");
		}
		if(id == "btn_Append"){
			// 화면 layout cell b에 dhtmlxForm 객체 생성.
			whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellRegFormData);
			formEvent();
		}
		if(id == "btn_Delete"){
			if ( confirm("삭제하시겠습니까?") ) {
				// 화면 layout cell b에 dhtmlxForm 객체 생성.
				whoyaGlobalData.bForm = whoyaGlobalData.bCell.attachForm("");
				
				var dpData = {
					url: "<c:url value='/whoya/sec/ram/EgovAuthorListDelete.do' />"
					, obj: whoyaGlobalData.aGrid
				};
				whoyaGlobalData.dp = whoya.dhtmlx.dataProcessor(dpData);
				
				whoyaGlobalData.aGrid.deleteSelectedRows();
				
				whoyaGlobalData.dp.attachEvent("onAfterUpdateFinish", function() {
					alert("저장하였습니다.");
					whoyaGlobalData.bForm = whoyaGlobalData.bCell.attachForm("");
					search();
				});
				
				whoyaGlobalData.dp.sendData();
			}
		}
    });
}

// grid event 생성
function gridEvent() {
	whoyaGlobalData.aGrid.attachEvent("onRowSelect", function(id, ind) {
		whoyaGlobalData.bCell.attachForm("");
		// 화면 layout cell b에 dhtmlxForm 객체 생성.
		whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellUpdateFormData);
		formEvent();
		
		$.ajax({
			url: "<c:url value='/whoya/sec/ram/EgovAuthor.do' />"
			, type: "POST"
			, data: {
				authorCode: whoyaGlobalData.aGrid.cells(id, 0).getValue()
			}
			, dataType: "json"
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
				
				$.ajax({
					url: "<c:url value='/whoya/sec/ram/EgovAuthorInsert.do' />"
					, type: "POST"
					, data: whoyaGlobalData.bForm.getFormData()
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
		} else if ( name == "uptBtn" ) {  // 수정
			if ( confirm("저장하시겠습니까?") ) {
				document.getElementById("activeStatusBar").innerHTML = "";
				
				$.ajax({
					url: "<c:url value='/whoya/sec/ram/EgovAuthorUpdate.do' />"
					, type: "POST"
					, data: whoyaGlobalData.bForm.getFormData()
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
		} else if ( name == "dltBtn" ) {  // 삭제
			if ( confirm("삭제하시겠습니까?") ) {
				document.getElementById("activeStatusBar").innerHTML = "";
				
				$.ajax({
					url: "<c:url value='/whoya/sec/ram/EgovAuthorDelete.do' />"
					, type: "POST"
					, data: whoyaGlobalData.bForm.getFormData()
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
		url: "<c:url value='/whoya/sec/ram/EgovAuthorJSONList.do' />"
		, data: {
			searchKeyword : whoyaGlobalData.toolbar.getValue("searchKeyword")
			, searchCondition : 1
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
