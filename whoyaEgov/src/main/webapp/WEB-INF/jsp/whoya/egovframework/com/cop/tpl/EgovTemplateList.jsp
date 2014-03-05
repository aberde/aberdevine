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
   		["0", "템플릿명"]
		, ["1", "템플릿구분"]
   	]);
	whoyaGlobalData.combo.selectOption(0);
	
	// toolbar의 Button정의
	var toolbarAddButton = {
		toolbar: whoyaGlobalData.toolbar
		, btn_Open: true
		, btn_Append: true
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
		, setHeader: "번호,템플릿명,템플릿구분,템플릿경로,사용여부,등록일자,템플릿 아이디"
		, setColumnIds: "no,tmplatNm,tmplatSeCodeNm,tmplatCours,useAt,frstRegisterPnttm,tmplatId"
		, setInitWidths: "100,150,150,*,100,100,100"
		, setColAlign: "center,center,center,center,center,center,center"
		, setColTypes: "ro,ro,ro,ro,ro,ro,ro"
		, enableResizing: "false,true,false,false,false,false,false"
		, enableTooltips: "false,false,false,false,false,false,false"
		, setColSorting: "str,str,str,str,str,str,str"
		, setColumnHidden: [
			{ id: 6 }
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
			{ type: "fieldset", name: "formField", label: "템플릿 정보등록", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "input", label: "템플릿명", name: "tmplatNm", value: "" },
				{ type: "select", label: "템플릿 구분", name: "tmplatSeCode", options: [
					{ value: "", text: "--선택하세요--" }
				] },
				{ type: "input", label: "템플릿경로", name: "tmplatCours", value: "" },
				{ type: "template", label: "사용여부", list: [
					{ type: "settings", labelWidth: 50, inputWidth: 170, position: "label-right" },
					{ type: "label", labelWidth: 130 },
					{ type: "newcolumn" },
					{ type: "radio", label: "사용", name: "useAt", value: "Y" },
					{ type: "newcolumn" },
					{ type: "radio", label: "미사용", name: "useAt", value: "N" }
				] },
				{ type: "button", name: "regBtn", value: "등록" }
			] }
		]
	};

	// 수정폼.
	whoyaGlobalData.bCellUpdateFormData = {
		cell: whoyaGlobalData.bCell
		, formData: [
   			{ type: "fieldset", name: "formField", label: "템플릿 정보수정", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "input", label: "템플릿명", name: "tmplatNm", value: "", readonly: true },
				{ type: "select", label: "템플릿 구분", name: "tmplatSeCode", options: [
					{ value: "", text: "--선택하세요--" }
				] },
				{ type: "input", label: "템플릿경로", name: "tmplatCours", value: "" },
				{ type: "template", label: "사용여부", list: [
  					{ type: "settings", labelWidth: 50, inputWidth: 170, position: "label-right" },
					{ type: "label", labelWidth: 130 },
					{ type: "newcolumn" },
  					{ type: "radio", label: "사용", name: "useAt", value: "Y" },
  					{ type: "newcolumn" },
  					{ type: "radio", label: "미사용", name: "useAt", value: "N" }
  				] },
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
			url: "<c:url value='/whoya/cop/tpl/selectTemplateInf.do' />"
			, data: {
				tmplatId: whoyaGlobalData.aGrid.cells(id, 6).getValue()
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
	getTmplatSeCode();
	
	whoyaGlobalData.bForm.attachEvent("onButtonClick", function(name) {
		if ( name == "regBtn" ) {  // 등록
			if ( confirm("저장하시겠습니까?") ) {
				document.getElementById("activeStatusBar").innerHTML = "";
				
				var dpData = {
					url: "<c:url value='/whoya/cop/tpl/insertTemplateInf.do' />"
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
					url: "<c:url value='/whoya/cop/tpl/updateTemplateInf.do' />"
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
		url: "<c:url value='/whoya/cop/tpl/selectTemplateJSONInfs.do' />"
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

/**
 * 탬플릿 구분 목록 가져오기.
 */
function getTmplatSeCode() {
	$.ajax({
		url: "<c:url value='/whoya/cop/tpl/selectTemplateCodeList.do' />"
		, dataType: "json"
		, data: {
			codeId: "COM005"
		}
		, success: function(data, textStatus, jqXHR) {
			var tmplatSeCode = whoyaGlobalData.bForm.getOptions("tmplatSeCode");
			$.each(data, function() {
				tmplatSeCode.add(new Option($(this)[0].codeNm, $(this)[0].code));
			});
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
