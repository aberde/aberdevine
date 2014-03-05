<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>게시판사용정보</title>
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
	whoyaGlobalData.toolbar.addText("searchCnd", 1, "");
	whoyaGlobalData.toolbar.addInput("searchWrd", 2, "", 200);

	// selectBox 생성
	var comboDIV = whoyaGlobalData.toolbar.objPull[whoyaGlobalData.toolbar.idPrefix+"searchCnd"].obj;
	whoyaGlobalData.toolbar.objPull[whoyaGlobalData.toolbar.idPrefix+"searchCnd"].obj.innerHTML = "";
	whoyaGlobalData.combo = new dhtmlXCombo(comboDIV,"alfa",140);
	whoyaGlobalData.combo.addOption([
   		["0", "게시판명"]
		, ["1", "사용 커뮤니티명"]
		, ["2", "사용 동호회명"]
   	]);
	whoyaGlobalData.combo.selectOption(0);
	
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
		, setHeader: "번호,게시판명,사용 커뮤니티 명,사용 동호회 명,등록일시,사용여부,게시판 아이디,대상시스템 아이디"
		, setColumnIds: "no,bbsNm,cmmntyNm,clbNm,frstRegisterPnttm,useAt,bbsId,trgetId"
		, setInitWidths: "100,*,*,*,100,100,100,100"
		, setColAlign: "center,center,center,center,center,center,center,center"
		, setColTypes: "ro,ro,ro,ro,ro,ro,ro,ro"
		, enableResizing: "false,true,false,false,false,true,false,false"
		, enableTooltips: "false,false,false,false,false,false,false,false"
		, setColSorting: "str,str,str,str,str,str,str,str"
		, setColumnHidden: [
			{ id: 6 }
			, { id: 7 }
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
			{ type: "fieldset", name: "formField", label: "게시판 사용등록", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "template", label: "게시판명", name: "bbsId", format: whoya.dhtmlx.form.format.boardInfo },
				{ type: "template", label: "커뮤니티/동호회 정보", list: [
					{ type: "label", labelWidth: 125 },
					{ type: "newcolumn" },
					{ type: "select", name: "trgetType", inputWidth: 90, options: [
						{ value: "", text: "--선택하세요--" },
						{ value: "CMMNTY", text: "커뮤니티" },
						{ value: "CLUB", text: "동호회" },
						{ value: "SYSTEM", text: "시스템" }
					] },
					{ type: "newcolumn" },
					{ type: "hidden", name: "trgetId", value: "" },
					{ type: "input", name: "trgetNm", value: "", inputWidth: 90, readonly: true }
				] },
				{ type: "button", name: "regBtn", value: "등록" }
			] }
   		]
	};

	// 수정폼.
	whoyaGlobalData.bCellUpdateFormData = {
		cell: whoyaGlobalData.bCell
		, formData: [
   			{ type: "fieldset", name: "formField", label: "게시판 사용정보 수정", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "template", label: "게시판명", name: "bbsNm", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "커뮤니티/동호회 정보", list: [
					{ type: "label", labelWidth: 125 },
					{ type: "newcolumn" },
					{ type: "hidden", name: "trgetId", value: "" },
					{ type: "template", name: "cmmntyNm", value: "", hidden: true, format: whoya.dhtmlx.form.format.printData },
					{ type: "template", name: "clbNm", value: "", hidden: true, format: whoya.dhtmlx.form.format.printData },
					{ type: "template", name: "trgetNm", value: "(시스템 활용)", format: whoya.dhtmlx.form.format.printData }
				] },
				{ type: "template", label: "사용여부", list: [
					{ type: "settings", labelWidth: 50, inputWidth: 170, position: "label-right" },
					{ type: "label", labelWidth: 125 },
					{ type: "newcolumn" },
					{ type: "radio", label: "사용", name: "useAt", value: "Y" },
					{ type: "newcolumn" },
					{ type: "radio", label: "사용중지", name: "useAt", value: "N" }
  				] },
				{ type: "template", label: "제공 URL", name: "provdUrl", value: "", format: whoya.dhtmlx.form.format.printData },
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
		}
    });
}

// grid event 생성
function gridEvent() {
	whoyaGlobalData.aGrid.attachEvent("onRowSelect", function(id, ind) {
		// 화면 layout cell b에 dhtmlxForm 객체 생성.
		whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellUpdateFormData);
		formEvent();
		
		$.ajax({
			url: "<c:url value='/whoya/cop/com/selectBBSUseInf.do' />"
			, data: {
				bbsId: whoyaGlobalData.aGrid.cells(id, 5).getValue()
				, trgetId: whoyaGlobalData.aGrid.cells(id, 6).getValue()
			}
			, success: function(data, textStatus, jqXHR) {
				whoyaGlobalData.bForm.setFormData(data);
				if ( data.cmmntyNm ) {
					whoyaGlobalData.bForm.showItem("cmmntyNm");
					whoyaGlobalData.bForm.hideItem("clbNm");
					whoyaGlobalData.bForm.hideItem("trgetNm");
				} else if ( data.clbNm ) {
					whoyaGlobalData.bForm.hideItem("cmmntyNm");
					whoyaGlobalData.bForm.showItem("clbNm");
					whoyaGlobalData.bForm.hideItem("trgetNm");
				} else {
					whoyaGlobalData.bForm.hideItem("cmmntyNm");
					whoyaGlobalData.bForm.hideItem("clbNm");
					whoyaGlobalData.bForm.showItem("trgetNm");
				}
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
				
				var dpData = {
					url: "<c:url value='/whoya/cop/com/insertBBSUseInf.do' />"
					, obj: whoyaGlobalData.bForm
				};
				whoyaGlobalData.dp = whoya.dhtmlx.dataProcessor(dpData);
				
				whoyaGlobalData.dp.attachEvent("onAfterUpdateFinish", function() {
					alert("저장하였습니다.");
					whoyaGlobalData.bForm = whoyaGlobalData.bCell.attachForm("");
					search();
				});
				
				whoyaGlobalData.dp.sendData();
			}
		} else if ( name == "uptBtn" ) {  // 수정
			if ( confirm("저장하시겠습니까?") ) {
				document.getElementById("activeStatusBar").innerHTML = "";
				
				var dpData = {
					url: "<c:url value='/whoya/cop/com/updateBBSUseInf.do' />"
					, obj: whoyaGlobalData.bForm
				};
				whoyaGlobalData.dp = whoya.dhtmlx.dataProcessor(dpData);
				
				whoyaGlobalData.dp.attachEvent("onAfterUpdateFinish", function() {
					alert("저장하였습니다.");
					whoyaGlobalData.bForm = whoyaGlobalData.bCell.attachForm("");
					search();
				});
				
				whoyaGlobalData.dp.sendData();
			}
		}
	});
	
	// selectbox onChange 이벤트.
	whoyaGlobalData.bForm.attachEvent("onChange", function(name, value) {
		// 커뮤니티/동호회 정보 selectbox
		if ( name == "trgetType" ) {
			if ( value == "CMMNTY" ) {  // 커뮤니티 선택.
				communityPopup();
			} else if ( value == "CLUB" ) {  //  동호회 선택.
				clubPopup();
			} else if ( value == "SYSTEM" ) {  // 시스템 선택.
				whoyaGlobalData.bForm.setItemValue("trgetId", "SYSTEM_DEFAULT_BOARD");
				whoyaGlobalData.bForm.setItemValue("trgetNm", "시스템 활용");
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
		url: "<c:url value='/whoya/cop/com/selectBBSUseJSONInfs.do' />"
		, data: {
			searchCnd : whoyaGlobalData.combo.getSelectedValue()
			, searchWrd : whoyaGlobalData.toolbar.getValue("searchWrd")
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
