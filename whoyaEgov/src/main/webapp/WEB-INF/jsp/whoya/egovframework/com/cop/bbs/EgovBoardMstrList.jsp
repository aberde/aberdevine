<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>게시판속성관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/whoya/include/header.jsp" />

<script type="text/javascript">
/**
 * 전역변수로 사용할 데이터
 * JSON형식의 데이터
 *   layout: layout  // dhtmlXLayoutObject 객체
 *   toolbar: toolbar // dhtmlXLayoutObject의 toolbar 객체
 *   aCell: aCell  // dhtmlXLayoutObject의 cell 객체 'a'
 *   aGrid: aGrid  // dhtmlXLayoutObject의 cell 객체 'a'의 dhtmlxGrid 객체
 *   bCell: bCell  // dhtmlXLayoutObject의 cell 객체 'b'
 *   bForm: bForm  // dhtmlXLayoutObject의 cell 객체 'b'의 dhtmlxForm 객체
 *   bCellRegFormData: bCellRegFormData  // dhtmlxForm의 UI데이터
 *   bCellDetailFormData: bCellDetailFormData  // dhtmlxForm의 UI데이터
 *   bCellUpdateFormData: bCellUpdateFormData  // dhtmlxForm의 UI데이터
 *   statusbar: statusbar  // statusbar 객체
 *   combo: combo  //  dhtmlXCombo 객체
 */
var whoyaGlobalData = {};

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
		, ["1", "게시판유형"]
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
		, setHeader: "번호,게시판명,게시판유형,게시판속성,생성일,사용여부,게시판 아이디,유일 아이디"
		, setColumnIds: "no,bbsNm,bbsTyCodeNm,bbsAttrbCodeNm,frstRegisterPnttm,useAt,bbsId,uniqId"
		, setInitWidths: "100,*,150,150,150,100,100,100"
		, setColAlign: "center,center,center,center,center,center,center,center"
		, setColTypes: "ro,ro,ro,ro,ro,ro,ro,ro"
		, enableResizing: "false,true,false,false,false,false,false,false"
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
	// 등록폼.
	whoyaGlobalData.bCellRegFormData = {
		cell: whoyaGlobalData.bCell
		, formData: [
 			{ type: "fieldset", name: "formField", label: "게시판 생성", list: [
 				{ type: "settings", labelWidth: 150, inputWidth: 170 },
 				{ type: "input", label: "게시판명", name: "bbsNm", value: "" },
 				{ type: "input", label: "게시판소개", name: "bbsIntrcn", value: "", rows: 3 },
 				{ type: "select", label: "게시판유형", name: "bbsTyCode", options: [
 					{ value: "", text: "--선택하세요--" }
 				] },
 				{ type: "select", label: "게시판속성", name: "bbsAttrbCode", options: [
 					{ value: "", text: "--선택하세요--" }
 				] },
 				{ type: "template", label: "답장가능여부", list: [
 					{ type: "settings", labelWidth: 70, inputWidth: 170, position: "label-right" },
 					{ type: "label", labelWidth: 125 },
 					{ type: "newcolumn" },
 					{ type: "radio", label: "가능", name: "replyPosblAt", value: "Y" },
 					{ type: "newcolumn" },
 					{ type: "radio", label: "불가능", name: "replyPosblAt", value: "N" }
 				] },
 				{ type: "template", label: "파일첨부가능여부", list: [
 					{ type: "settings", labelWidth: 70, inputWidth: 170, position: "label-right" },
 					{ type: "label", labelWidth: 125 },
 					{ type: "newcolumn" },
 					{ type: "radio", label: "가능", name: "fileAtchPosblAt", value: "Y" },
 					{ type: "newcolumn" },
 					{ type: "radio", label: "불가능", name: "fileAtchPosblAt", value: "N" }
 				] },
 				{ type: "select", label: "첨부가능파일 숫자", name: "posblAtchFileNumber", options: [
 					{ value: "0", text: "없음" },
 					{ value: "1", text: "1개" },
 					{ value: "2", text: "2개" },
 					{ value: "3", text: "3개" }
 				] },
 				{ type: "template", label: "템플릿 정보", format: whoya.dhtmlx.form.format.tmplatInfo },
 				{ type: "select", label: "추가 선택사항", name: "option", options: [
 					{ value: "", text: "미선택" },
 					{ value: "comment", text: "댓글" },
 					{ value: "stsfdg", text: "만족도조사" }
 				] },
 				{ type: "button", name: "regBtn", value: "등록" }
 			] }
 		]
	};

	// 수정폼.
	whoyaGlobalData.bCellUpdateFormData = {
		cell: whoyaGlobalData.bCell
		, formData: [
			{ type: "fieldset", name: "formField", label: "게시판 생성", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "input", label: "게시판명", name: "bbsNm", value: "" },
				{ type: "input", label: "게시판소개", name: "bbsIntrcn", value: "", rows: 3 },
				{ type: "select", label: "게시판유형", name: "bbsTyCode", disabled: true, options: [
					{ value: "", text: "--선택하세요--" }
				] },
				{ type: "select", label: "게시판속성", name: "bbsAttrbCode", disabled: true, options: [
					{ value: "", text: "--선택하세요--" }
				] },
				{ type: "template", label: "답장가능여부", list: [
   					{ type: "settings", labelWidth: 70, inputWidth: 170, position: "label-right" },
   					{ type: "label", labelWidth: 125 },
   					{ type: "newcolumn" },
   					{ type: "radio", label: "가능", name: "replyPosblAt", value: "Y", disabled: true },
   					{ type: "newcolumn" },
   					{ type: "radio", label: "불가능", name: "replyPosblAt", value: "N", disabled: true }
   				] },
   				{ type: "template", label: "파일첨부가능여부", list: [
   					{ type: "settings", labelWidth: 70, inputWidth: 170, position: "label-right" },
   					{ type: "label", labelWidth: 125 },
   					{ type: "newcolumn" },
   					{ type: "radio", label: "가능", name: "fileAtchPosblAt", value: "Y" },
   					{ type: "newcolumn" },
   					{ type: "radio", label: "불가능", name: "fileAtchPosblAt", value: "N" }
   				] },
   				
   				{ type: "select", label: "첨부가능파일 숫자", name: "posblAtchFileNumber", options: [
   					{ value: "0", text: "없음" },
   					{ value: "1", text: "1개" },
   					{ value: "2", text: "2개" },
   					{ value: "3", text: "3개" }
   				] },
   				{ type: "template", label: "템플릿 정보", format: whoya.dhtmlx.form.format.tmplatInfo },
   				{ type: "select", label: "추가 선택사항", name: "option", options: [
   					{ value: "", text: "미선택" },
   					{ value: "comment", text: "댓글" },
   					{ value: "stsfdg", text: "만족도조사" }
   				] },
  				{ type: "template", list: [
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
			getBbsTyCode();
			getBbsAttrbCode();
			formEvent();
		}
    });
}

// grid event 생성
function gridEvent() {
	whoyaGlobalData.aGrid.attachEvent("onRowSelect", function(id, ind) {
		whoyaGlobalData.bCell.attachForm("");
		// 화면 layout cell b에 dhtmlxForm 객체 생성.
		whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellUpdateFormData);
		getBbsTyCode();
		getBbsAttrbCode();
		formEvent();
		
		$.ajax({
			url: "<c:url value='/whoya/cop/bbs/SelectBBSMasterInf.do' />"
			, type: "POST"
			, data: {
				bbsId: whoyaGlobalData.aGrid.cells(id, 6).getValue()
				, uniqId: whoyaGlobalData.aGrid.cells(id, 7).getValue()
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
				
				var dpData = {
					url: "<c:url value='/whoya/cop/bbs/insertBBSMasterInf.do' />"
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
					url: "<c:url value='/whoya/cop/bbs/UpdateBBSMasterInf.do' />"
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
		} else if ( name == "dltBtn" ) {  // 삭제
			if ( confirm("삭제하시겠습니까?") ) {
				document.getElementById("activeStatusBar").innerHTML = "";
				
				var dpData = {
					url: "<c:url value='/whoya/cop/bbs/DeleteBBSMasterInf.do' />"
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

//tmplatPopup toolbar event 생성
function tmplatPopupToolbarEvent() {
	whoyaGlobalData.tmplatPopupToolbar.attachEvent("onClick", function(id) {
		if(id == "btn_Open"){
			tmplatPopupSearch();
		}
    });
}
// #######################################################################


/**
 * 템플릿 정보 목록 Popup
 */
function tmplatPopup() {
	// #########################################
	// ## layout에 windows 생성
	// #########################################
	var tmplatPopupWindowsData = {
		layout: whoyaGlobalData.layout
		, id: "tmplatPopup"
		, setText: "템플릿 정보"
	};
	whoyaGlobalData.tmplatPopupWindows = whoya.dhtmlx.layout.windows(tmplatPopupWindowsData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 레이아웃생성
	// #########################################
	var tmplatPopupLayoutData = {
		layout_target: whoyaGlobalData.tmplatPopupWindows
		, layout_Pattern: "1C"
	};
	whoyaGlobalData.tmplatPopupLayout = whoya.dhtmlx.layout.init(tmplatPopupLayoutData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 툴바 생성
	// #########################################
	var tmplatPopupToolbarData = {
		layout: whoyaGlobalData.tmplatPopupLayout
	};
	whoyaGlobalData.tmplatPopupToolbar = whoya.dhtmlx.layout.toolbar.init(tmplatPopupToolbarData);
	whoyaGlobalData.tmplatPopupToolbar.addText("searchCnd", 1, "");
	whoyaGlobalData.tmplatPopupToolbar.addInput("searchWrd", 2, "", 200);

	// selectBox 생성
	var comboDIV = whoyaGlobalData.tmplatPopupToolbar.objPull[whoyaGlobalData.tmplatPopupToolbar.idPrefix+"searchCnd"].obj;
	whoyaGlobalData.tmplatPopupToolbar.objPull[whoyaGlobalData.tmplatPopupToolbar.idPrefix+"searchCnd"].obj.innerHTML = "";
	whoyaGlobalData.tmplatPopupCombo = new dhtmlXCombo(comboDIV,"alfa",140);
	whoyaGlobalData.tmplatPopupCombo.addOption([
   		["0", "커뮤니티명"]
   	]);
	whoyaGlobalData.tmplatPopupCombo.selectOption(0);
	
	// toolbar의 Button정의
	var tmplatPopupToolbarAddButton = {
		toolbar: whoyaGlobalData.tmplatPopupToolbar
		, btn_Append: false
		, btn_Delete: false
		, btn_Undo: false
		, btn_Save: false
		, btn_Print: false
		, btn_Excel: false
	};
	whoya.dhtmlx.layout.toolbar.addButton(tmplatPopupToolbarAddButton);
	tmplatPopupToolbarEvent();
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout cell a 
	// #########################################
	var tmplatPopupaCellData = {
		layout: whoyaGlobalData.tmplatPopupLayout
	};
	// 팝업창 화면 layout의 해당 cell 정의 
	whoyaGlobalData.tmplatPopupaCell = whoya.dhtmlx.layout.cell.init(tmplatPopupaCellData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout cell a에 grid생성
	// #########################################
	var tmplatPopupaCellGridData = {
		cell: whoyaGlobalData.tmplatPopupaCell
		, setHeader: "번호,템플릿명,템플릿구분,템플릿경로,사용여부,등록일자,선택"
		, setColumnIds: "no,tmplatNm,tmplatSeCodeNm,tmplatCours,useAt,frstRegisterPnttm,selectLink"
		, setInitWidths: "100,*,100,100,100,100,100"
		, setColAlign: "center,center,center,center,center,center,center"
		, setColTypes: "ro,ro,ro,ro,ro,ro,img"
		, enableResizing: "true,true,true,true,true,true,true"
		, enableTooltips: "false,false,false,false,false,false,false"
		, setColSorting: "str,str,str,str,str,str,str"
	};
	// 팝업창 화면 layout cell a에 dhtmlxGrid 객체 생성.
	whoyaGlobalData.tmplatPopupaGrid = whoya.dhtmlx.layout.cell.grid(tmplatPopupaCellGridData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout에 statusbar 생성
	// #########################################
	var tmplatPopupStatusbarData = {
		layout: whoyaGlobalData.tmplatPopupLayout
		, id: "tmplatPopup"
	};
	whoyaGlobalData.tmplatPopupStatusbar = whoya.dhtmlx.statusbar(tmplatPopupStatusbarData);
	// #########################################
}


/**
 * 조회버튼 클릭시
 */
function search() {
	whoyaGlobalData.layout.progressOn();
	whoyaGlobalData.aGrid.clearAll();
	document.getElementById("activeStatusBar").innerHTML = "";

	$.ajax({
		url: "<c:url value='/whoya/cop/bbs/SelectBBSMasterJSONInfs.do' />"
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
 * 조회(tmplatPopup용)
 */
function tmplatPopupSearch() {
	whoyaGlobalData.tmplatPopupLayout.progressOn();
	whoyaGlobalData.tmplatPopupaGrid.clearAll();
	document.getElementById("tmplatPopupactiveStatusBar").innerHTML = "";
	$.ajax({
		url: "<c:url value='/whoya/cop/tpl/selectTemplateInfsPop.do' />"
		, type: "POST"
		, data: {
			searchCnd : whoyaGlobalData.tmplatPopupCombo.getSelectedValue()
			, searchWrd : whoyaGlobalData.tmplatPopupToolbar.getValue("searchWrd")
			, typeFlag: "CMY"
		}
		, success: function(data, textStatus, jqXHR) {
			whoyaGlobalData.tmplatPopupaGrid.attachEvent("onXLE", function(){
				whoyaGlobalData.tmplatPopupLayout.progressOff();
    		});
    	  	
			whoyaGlobalData.tmplatPopupaGrid.clearAll();
			whoyaGlobalData.tmplatPopupaGrid.parse(data.list, "json");
			whoyaGlobalData.tmplatPopupaGrid.setSelectedRow(0);
    		document.getElementById("tmplatPopupactiveStatusBar").innerHTML = "조회되었습니다";
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
 * 게시판 유형 목록 가져오기.
 */
function getBbsTyCode() {
	$.ajax({
		url: "<c:url value='/whoya/cop/bbs/SelectBBSCodeList.do' />"
		, dataType: "json"
		, data: {
			codeId: "COM004"
		}
		, success: function(data, textStatus, jqXHR) {
			var bbsTyCode = whoyaGlobalData.bForm.getOptions("bbsTyCode");
			$.each(data, function() {
				bbsTyCode.add(new Option($(this)[0].codeNm, $(this)[0].code));
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

/**
 * 게시판 속성 목록 가져오기.
 */
function getBbsAttrbCode() {
	$.ajax({
		url: "<c:url value='/whoya/cop/bbs/SelectBBSCodeList.do' />"
		, dataType: "json"
		, data: {
			codeId: "COM009"
		}
		, success: function(data, textStatus, jqXHR) {
			var bbsAttrbCode = whoyaGlobalData.bForm.getOptions("bbsAttrbCode");
			$.each(data, function() {
				bbsAttrbCode.add(new Option($(this)[0].codeNm, $(this)[0].code));
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
