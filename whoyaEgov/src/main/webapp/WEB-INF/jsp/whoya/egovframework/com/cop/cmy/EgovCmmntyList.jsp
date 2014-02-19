<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>커뮤니티관리</title>
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
 *   tmplatPopupWindows: tmplatPopupWindows  // dhtmlXLayoutObject의 dhtmlxWindows 객체
 *   tmplatPopupLayout: tmplatPopupLayout  // 템플릿 dhtmlxWindows의 dhtmlXLayoutObject 객체
 *   tmplatPopupToolbar: tmplatPopupToolbar  // 템플릿 dhtmlXLayoutObject의 toolbar 객체
 *   tmplatPopupCombo: tmplatPopupCombo  // 템플릿 dhtmlXCombo 객체
 *   tmplatPopupaCell: tmplatPopupaCell  // 템플릿 dhtmlXLayoutObject의 cell 객체 'a'
 *   tmplatPopupaGrid: tmplatPopupaGrid  // 템플릿 dhtmlXLayoutObject의 cell 객체 'a'의 dhtmlxGrid 객체
 *   tmplatPopupStatusbar: tmplatPopupStatusbar  // 템플릿 statusbar 객체
 *   emplyrPopupWindows: emplyrPopupWindows  // dhtmlXLayoutObject의 dhtmlxWindows 객체
 *   emplyrPopupLayout: emplyrPopupLayout  // 커뮤니티 관리자 dhtmlxWindows의 dhtmlXLayoutObject 객체
 *   emplyrPopupToolbar: emplyrPopupToolbar  // 커뮤니티 관리자 dhtmlXLayoutObject의 toolbar 객체
 *   emplyrPopupCombo: emplyrPopupCombo  // 커뮤니티 관리자 dhtmlXCombo 객체
 *   emplyrPopupaCell: emplyrPopupaCell  // 커뮤니티 관리자 dhtmlXLayoutObject의 cell 객체 'a'
 *   emplyrPopupaGrid: emplyrPopupaGrid  // 커뮤니티 관리자 dhtmlXLayoutObject의 cell 객체 'a'의 dhtmlxGrid 객체
 *   emplyrPopupStatusbar: emplyrPopupStatusbar  // 커뮤니티 관리자 statusbar 객체
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
   		["0", "커뮤니티명"]
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
		, setHeader: "번호,커뮤니티명,생성자,생성일,사용여부,커뮤니티 아이디"
		, setColumnIds: "no,cmmntyNm,frstRegisterNm,frstRegisterPnttm,useAt,cmmntyId"
		, setInitWidths: "100,*,100,100,100,100"
		, setColAlign: "center,center,center,center,center,center"
		, setColTypes: "ro,ro,ro,ro,ro,ro"
		, enableResizing: "false,false,false,false,false,false"
		, enableTooltips: "false,false,false,false,false,false"
		, setColSorting: "str,str,str,str,str,str"
		, setColumnHidden: [
			{ id: 5 }
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
			{ type: "fieldset", name: "formField", label: "커뮤니티 생성", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "input", label: "커뮤니티명", name: "cmmntyNm", value: "" },
				{ type: "input", label: "커뮤니티 소개", name: "cmmntyIntrcn", value: "", rows: "3" },
				{ type: "template", label: "템플릿 정보", name: "tmplatId", format: whoya.dhtmlx.form.format.tmplatInfo },
				{ type: "template", label: "커뮤니티 관리자", name: "emplyrId", format: whoya.dhtmlx.form.format.emplyrInfo },
				{ type: "button", name: "regBtn", value: "등록" }
  			] }
  		]
	};

	// 수정폼.
	whoyaGlobalData.bCellUpdateFormData = {
		cell: whoyaGlobalData.bCell
		, formData: [
			{ type: "fieldset", name: "formField", label: "커뮤니티 정보수정", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "input", label: "커뮤니티명", name: "cmmntyNm", value: "" },
				{ type: "input", label: "커뮤니티 소개", name: "cmmntyIntrcn", value: "", rows: "3" },
				{ type: "input", label: "게시판 정보", name: "bbsNmList", value: "", readonly: true },
				{ type: "template", label: "템플릿 정보", name: "tmplatId", format: whoya.dhtmlx.form.format.tmplatInfo },
				{ type: "template", label: "커뮤니티 관리자", name: "emplyrId", format: whoya.dhtmlx.form.format.emplyrInfo },
				{ type: "template", label: "사용여부", list: [
					{ type: "settings", labelWidth: 50, inputWidth: 170, position: "label-right" },
					{ type: "label", labelWidth: 130 },
					{ type: "newcolumn" },
					{ type: "radio", name: "useAt", label: "사용", value: "Y" },
					{ type: "newcolumn" },
					{ type: "radio", name: "useAt", label: "사용중지", value: "N" }
				] },
				{ type: "input", label: "생성자", name: "frstRegisterNm", value: "", readonly: true },
				{ type: "input", label: "생성일시", name: "frstRegisterPnttm", value: "", readonly: true },
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
 * 커뮤니티 관리자 목록 Popup
 */
function emplyrPopup() {
	// #########################################
	// ## layout에 windows 생성
	// #########################################
	var emplyrPopupWindowsData = {
		layout: whoyaGlobalData.layout
		, id: "emplyrPopup"
		, setText: "커뮤니티 관리자"
	};
	whoyaGlobalData.emplyrPopupWindows = whoya.dhtmlx.layout.windows(emplyrPopupWindowsData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 레이아웃생성
	// #########################################
	var emplyrPopupLayoutData = {
		layout_target: whoyaGlobalData.emplyrPopupWindows
		, layout_Pattern: "1C"
	};
	whoyaGlobalData.emplyrPopupLayout = whoya.dhtmlx.layout.init(emplyrPopupLayoutData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 툴바 생성
	// #########################################
	var emplyrPopupToolbarData = {
		layout: whoyaGlobalData.emplyrPopupLayout
	};
	whoyaGlobalData.emplyrPopupToolbar = whoya.dhtmlx.layout.toolbar.init(emplyrPopupToolbarData);
	whoyaGlobalData.emplyrPopupToolbar.addText("searchCnd", 1, "");
	whoyaGlobalData.emplyrPopupToolbar.addInput("searchWrd", 2, "", 200);

	// selectBox 생성
	var comboDIV = whoyaGlobalData.emplyrPopupToolbar.objPull[whoyaGlobalData.emplyrPopupToolbar.idPrefix+"searchCnd"].obj;
	whoyaGlobalData.emplyrPopupToolbar.objPull[whoyaGlobalData.emplyrPopupToolbar.idPrefix+"searchCnd"].obj.innerHTML = "";
	whoyaGlobalData.emplyrPopupCombo = new dhtmlXCombo(comboDIV,"alfa",140);
	whoyaGlobalData.emplyrPopupCombo.addOption([
   		["0", "사용자명"]
   	]);
	whoyaGlobalData.emplyrPopupCombo.selectOption(0);
	
	// toolbar의 Button정의
	var emplyrPopupToolbarAddButton = {
		toolbar: whoyaGlobalData.emplyrPopupToolbar
		, btn_Append: false
		, btn_Delete: false
		, btn_Undo: false
		, btn_Save: false
		, btn_Print: false
		, btn_Excel: false
	};
	whoya.dhtmlx.layout.toolbar.addButton(emplyrPopupToolbarAddButton);
	emplyrPopupToolbarEvent();
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout cell a 
	// #########################################
	var emplyrPopupaCellData = {
		layout: whoyaGlobalData.emplyrPopupLayout
	};
	// 팝업창 화면 layout의 해당 cell 정의 
	whoyaGlobalData.emplyrPopupaCell = whoya.dhtmlx.layout.cell.init(emplyrPopupaCellData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout cell a에 grid생성
	// #########################################
	var emplyrPopupaCellGridData = {
		cell: whoyaGlobalData.emplyrPopupaCell
		, setHeader: "번호,사용자아이디,사용자명,주소,이메일,사용여부,선택"
		, setColumnIds: "no,userId,userNm,userAdres,userEmail,useAt,selectLink"
		, setInitWidths: "100,100,100,*,100,100,100"
		, setColAlign: "center,center,center,center,center,center,center"
		, setColTypes: "ro,ro,ro,ro,ro,ro,img"
		, enableResizing: "true,true,true,true,true,true,true"
		, enableTooltips: "false,false,false,false,false,false,false"
		, setColSorting: "str,str,str,str,str,str,str"
	};
	// 팝업창 화면 layout cell a에 dhtmlxGrid 객체 생성.
	whoyaGlobalData.emplyrPopupaGrid = whoya.dhtmlx.layout.cell.grid(emplyrPopupaCellGridData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout에 statusbar 생성
	// #########################################
	var emplyrPopupStatusbarData = {
		layout: whoyaGlobalData.emplyrPopupLayout
		, id: "emplyrPopup"
	};
	whoyaGlobalData.emplyrPopupStatusbar = whoya.dhtmlx.statusbar(emplyrPopupStatusbarData);
	// #########################################
}

/**
 * 커뮤니티 정보조회 Popup
 */
function cmmntyPopup(cmmntyId) {
	// #########################################
	// ## layout에 windows 생성
	// #########################################
	var cmmntyPopupWindowsData = {
		layout: whoyaGlobalData.layout
		, id: "cmmntyPopup"
		, setText: "커뮤니티 정보조회"
	};
	whoyaGlobalData.cmmntyPopupWindows = whoya.dhtmlx.layout.windows(cmmntyPopupWindowsData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 레이아웃생성
	// #########################################
	var cmmntyPopupLayoutData = {
		layout_target: whoyaGlobalData.cmmntyPopupWindows
		, layout_Pattern: "2E"
	};
	whoyaGlobalData.cmmntyPopupLayout = whoya.dhtmlx.layout.init(cmmntyPopupLayoutData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout cell a 
	// #########################################
	var cmmntyPopupaCellData = {
		layout: whoyaGlobalData.cmmntyPopupLayout
		, width: ""
		, hideHeader: false
		, setText: "커뮤니티 정보조회"
	};
	// 팝업창 화면 layout의 해당 cell 정의 
	whoyaGlobalData.cmmntyPopupaCell = whoya.dhtmlx.layout.cell.init(cmmntyPopupaCellData);
	// #########################################

	
	// #########################################
	// ## layout cell a에 form생성
	// #########################################
	// 등록 폼
	whoyaGlobalData.cmmntyPopupaCellDetailFormData = {
		cell: whoyaGlobalData.cmmntyPopupaCell
		, formData: [
			{ type: "fieldset", name: "formField", label: "커뮤니티 정보조회", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "template", label: "커뮤니티명", name: "cmmntyNm", value: "", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "커뮤니티 소개", name: "cmmntyIntrcn", value: "", rows: "3", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "템플릿 정보", name: "tmplatNm", value: "", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "게시판 정보", name: "bbsNmList", value: "", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "사용여부", name: "useAt", value: "", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "생성자", name: "frstRegisterNm", value: "", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "생성일시", name: "frstRegisterPnttm", value: "", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "제공 URL", name: "provdUrl", value: "", format: whoya.dhtmlx.form.format.printData },
				{ type: "button", name: "uptBtn", value: "수정" }
			] }
		]
	};
	whoyaGlobalData.cmmntyPopupaForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.cmmntyPopupaCellDetailFormData);
	$.ajax({
		url: "<c:url value='/whoya/cop/cmy/selectCmmntyInf.do' />"
		, data: {
			cmmntyId: cmmntyId
		}
		, success: function(data, textStatus, jqXHR) {
			var bbsNmList = "";
			$.each(data.bbsNmList, function() {
				bbsNmList += this.bbsNm + " / ";
			});
			data.bbsNmList = bbsNmList;
			whoyaGlobalData.cmmntyPopupaForm.setFormData(data);
		}
		, error: function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR);
			console.log(textStatus);
			console.log(errorThrown);
			alert(errorThrown);
		}
	});
	cmmntyPopupFormEvent(cmmntyId);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout cell b 
	// #########################################
	var cmmntyPopupbCellData = {
		layout: whoyaGlobalData.cmmntyPopupLayout
		, cell_target: "b"
		, width: ""
		, hideHeader: false
		, setText: "소속 동호회 목록"
	};
	// 팝업창 화면 layout의 해당 cell 정의 
	whoyaGlobalData.cmmntyPopupbCell = whoya.dhtmlx.layout.cell.init(cmmntyPopupbCellData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout cell b에 grid생성
	// #########################################
	var cmmntyPopupbCellGridData = {
		cell: whoyaGlobalData.cmmntyPopupbCell
		, setHeader: "번호,동호회명,등록일,사용여부"
		, setColumnIds: "no,clbNm,frstRegisterPnttm,useAt"
		, setInitWidths: "100,*,100,100"
		, setColAlign: "center,center,center,center"
		, setColTypes: "ro,ro,ro,ro"
		, enableResizing: "true,true,true,true"
		, enableTooltips: "false,false,false,false"
		, setColSorting: "str,str,str,str"
	};
	
	// 팝업창 화면 layout cell b에 dhtmlxGrid 객체 생성.
	whoyaGlobalData.cmmntyPopupbGrid = whoya.dhtmlx.layout.cell.grid(cmmntyPopupbCellGridData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout에 statusbar 생성
	// #########################################
	var cmmntyPopupStatusbarData = {
		layout: whoyaGlobalData.cmmntyPopupLayout
		, id: "cmmntyPopup"
	};
	whoyaGlobalData.cmmntyPopupStatusbar = whoya.dhtmlx.statusbar(cmmntyPopupStatusbarData);
	cmmntyPopupSearch(cmmntyId);
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
		cmmntyPopup(whoyaGlobalData.aGrid.cells(id, 5).getValue());
	});
}

// form event 생성
function formEvent() {
	whoyaGlobalData.bForm.attachEvent("onButtonClick", function(name) {
		if ( name == "regBtn" ) {
			if ( confirm("저장하시겠습니까?") ) {
				document.getElementById("activeStatusBar").innerHTML = "";
				
				var dpData = {
					url: "<c:url value='/whoya/cop/cmy/insertCmmntyInf.do' />"
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
		} else if ( name == "uptBtn" ) {
			if ( confirm("저장하시겠습니까?") ) {
				document.getElementById("activeStatusBar").innerHTML = "";
				
				var dpData = {
					url: "<c:url value='/whoya/cop/cmy/updtCmmntyInf.do' />"
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

// tmplatPopup toolbar event 생성
function tmplatPopupToolbarEvent() {
	whoyaGlobalData.tmplatPopupToolbar.attachEvent("onClick", function(id) {
		if(id == "btn_Open"){
			tmplatPopupSearch();
		}
    });
}

// emplyrPopup toolbar event 생성
function emplyrPopupToolbarEvent() {
	whoyaGlobalData.emplyrPopupToolbar.attachEvent("onClick", function(id) {
		if(id == "btn_Open"){
			emplyrPopupSearch();
		}
    });
}

/**
 * cmmntyPopup form event 생성
 */
function cmmntyPopupFormEvent(cmmntyId) {
	whoyaGlobalData.cmmntyPopupaForm.attachEvent("onButtonClick", function(name) {
		if ( name == "uptBtn" ) {
			// 화면 layout cell b에 dhtmlxForm 객체 생성.
			whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellUpdateFormData);
			formEvent();
			$.ajax({
				url: "<c:url value='/whoya/cop/cmy/forUpdateCmmntyInf.do' />"
				, data: {
					cmmntyId: cmmntyId
				}
				, success: function(data, textStatus, jqXHR) {
					var bbsNmList = "";
					$.each(data.bbsNmList, function() {
						bbsNmList += this.bbsNm + " / ";
					});
					data.bbsNmList = bbsNmList;
					whoyaGlobalData.bForm.setFormData(data);
					$("#tmplatId").val(data.tmplatId);
					$("#tmplatNm").val(data.tmplatNm);
					$("#emplyrId").val(data.emplyrId);
					$("#emplyrNm").val(data.emplyrNm);
				}
				, error: function(jqXHR, textStatus, errorThrown) {
					console.log(jqXHR);
					console.log(textStatus);
					console.log(errorThrown);
					alert(errorThrown);
				}
			});
			whoyaGlobalData.cmmntyPopupWindows.close();
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
		url: "<c:url value='/whoya/cop/cmy/selectCmmntyJSONInfs.do' />"
		, data: {
			searchCondition : whoyaGlobalData.combo.getSelectedValue()
			, searchKeyword : whoyaGlobalData.toolbar.getValue("searchWrd")
		}
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
 * 조회(tmplatPopup용)
 */
function emplyrPopupSearch() {
	whoyaGlobalData.emplyrPopupLayout.progressOn();
	whoyaGlobalData.emplyrPopupaGrid.clearAll();
	document.getElementById("emplyrPopupactiveStatusBar").innerHTML = "";
	$.ajax({
		url: "<c:url value='/whoya/cop/com/selectUserList.do' />"
		, type: "POST"
		, data: {
			searchCnd : whoyaGlobalData.emplyrPopupCombo.getSelectedValue()
			, searchWrd : whoyaGlobalData.emplyrPopupToolbar.getValue("searchWrd")
		}
		, success: function(data, textStatus, jqXHR) {
			whoyaGlobalData.emplyrPopupaGrid.attachEvent("onXLE", function(){
				whoyaGlobalData.emplyrPopupLayout.progressOff();
    		});
    	  	
			whoyaGlobalData.emplyrPopupaGrid.clearAll();
			whoyaGlobalData.emplyrPopupaGrid.parse(data.list, "json");
			whoyaGlobalData.emplyrPopupaGrid.setSelectedRow(0);
    		document.getElementById("emplyrPopupactiveStatusBar").innerHTML = "조회되었습니다";
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
 * 조회(cmmntyPopup용)
 */
function cmmntyPopupSearch(cmmntyId) {
	whoyaGlobalData.cmmntyPopupLayout.progressOn();
	whoyaGlobalData.cmmntyPopupbGrid.clearAll();
	document.getElementById("cmmntyPopupactiveStatusBar").innerHTML = "";
	$.ajax({
		url: "<c:url value='/whoya/cop/clb/selectClubInfByCmmntyId.do' />"
		, type: "POST"
		, data: {
			cmmntyId: cmmntyId
		}
		, success: function(data, textStatus, jqXHR) {
			whoyaGlobalData.cmmntyPopupbGrid.attachEvent("onXLE", function(){
				whoyaGlobalData.cmmntyPopupLayout.progressOff();
    		});
    	  	
			whoyaGlobalData.cmmntyPopupbGrid.clearAll();
			whoyaGlobalData.cmmntyPopupbGrid.parse(data.list, "json");
			whoyaGlobalData.cmmntyPopupbGrid.setSelectedRow(0);
    		document.getElementById("cmmntyPopupactiveStatusBar").innerHTML = "조회되었습니다";
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
