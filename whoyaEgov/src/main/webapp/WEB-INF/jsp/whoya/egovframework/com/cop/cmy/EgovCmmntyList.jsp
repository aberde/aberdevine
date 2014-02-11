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
var oProc;
var form;

function init() {
	dhtmlx.image_path = "<c:url value='/dhtmlx/dhtmlx_pro_full/imgs/'/>";
    oProc = new dhtmlXLayoutObject(document.body, "2U");
    oProc.dhxWins.setEffect("move", true);
    
    var toolbar;
    var combo;
    var grid;
	
	/** 
	 * 등록될 formData UI 구성.
	 */
	function inputFormData() {
		var formData = [
			{ type: "fieldset", name: "formField", label: "커뮤니티 생성", list: [
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 170 },
					{ type: "label", label: "커뮤니티명" },
					{ type: "newcolumn" },
					{ type: "input", name: "cmmntyNm", value: "" }
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 170 },
					{ type: "label", label: "커뮤니티 소개" },
					{ type: "newcolumn" },
					{ type: "input", name: "cmmntyIntrcn", value: "", rows: "3" }
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 170 },
					{ type: "label", label: "템플릿 정보" },
					{ type: "newcolumn" },
					{ type: "input", name: "tmplatId", value: "", hidden: true },
					{ type: "input", name: "tmplatNm", value: "", readonly: true },
					{ type: "newcolumn" },
					{ type: "button", name: "tmplatSearch", value: "템플릿 정보 찾기" }
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 170 },
					{ type: "label", label: "커뮤니티 관리자" },
					{ type: "newcolumn" },
					{ type: "input", name: "emplyrId", value: "", hidden: true },
					{ type: "input", name: "emplyrNm", value: "", readonly: true },
					{ type: "newcolumn" },
					{ type: "button", name: "emplyrSearch", value: "커뮤니티 관리자 찾기" }
				] },
				{ type: "block", list: [
					{ type: "button", name: "regBtn", value: "등록" }
				] }
			] }
		];
		
		var oProcB = oProc.cells("b");
		oProcB.hideHeader();
		form = oProcB.attachForm(formData);
		form.setFontSize("11px");
		
		// 버튼 클릭 이벤트.
		form.attachEvent("onButtonClick", function(name) {
			if ( name == "tmplatSearch" ) {  // 템플릿 찾기.
				tmplatPopup();
			} else if ( name == "emplyrSearch" ) {  // 커뮤니티 관리자 찾기.
				emplyrPopup();
			} else if ( name == "regBtn" ) {
				if ( confirm("저장하시겠습니까?") ) {
					document.getElementById("activeStatusBar").innerHTML = "";
					
					var DataProcessor = new dataProcessor("insertCmmntyInf.do");
					DataProcessor.setTransactionMode("POST", false);
					DataProcessor.setUpdateMode("off");
					DataProcessor.enableDataNames(true);
					//DataProcessor.enablePartialDataSend(true);
					DataProcessor.init(form);
					
					DataProcessor.attachEvent("onAfterUpdateFinish", function() {
						alert("저장하였습니다.");
						form = oProcB.attachForm("");
						search();
					});
					
					DataProcessor.sendData();
				}
			}
		});
	}

	/** 
	 * 수정될 formData UI 구성.
	 */
	function updateFormData() {
		var formData = [
   			{ type: "fieldset", name: "formField", label: "커뮤니티 정보수정", list: [
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 150, inputWidth: 170 },
   					{ type: "label", label: "커뮤니티명" },
   					{ type: "newcolumn" },
   					{ type: "input", name: "cmmntyNm", value: "" }
   				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 150, inputWidth: 170 },
   					{ type: "label", label: "커뮤니티 소개" },
   					{ type: "newcolumn" },
   					{ type: "input", name: "cmmntyIntrcn", value: "", rows: "3" }
   				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 150, inputWidth: 170 },
   					{ type: "label", label: "게시판 정보" },
   					{ type: "newcolumn" },
   					{ type: "input", name: "bbsNmList", value: "", readonly: true }
   				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 150, inputWidth: 170 },
   					{ type: "label", label: "템플릿 정보" },
   					{ type: "newcolumn" },
   					{ type: "input", name: "tmplatId", value: "", hidden: true },
					{ type: "input", name: "tmplatNm", value: "", readonly: true },
					{ type: "newcolumn" },
					{ type: "button", name: "tmplatSearch", value: "템플릿 정보 찾기" }
   				] },
   				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 170 },
					{ type: "label", label: "커뮤니티 관리자" },
					{ type: "newcolumn" },
					{ type: "input", name: "emplyrId", value: "", hidden: true },
					{ type: "input", name: "emplyrNm", value: "", readonly: true },
					{ type: "newcolumn" },
					{ type: "button", name: "emplyrSearch", value: "커뮤니티 관리자 찾기" }
				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 70, inputWidth: 170, position: "label-right" },
   					{ type: "label", label: "사용여부", labelWidth: 150 },
   					{ type: "newcolumn" },
   					{ type: "radio", name: "useAt", label: "사용", value: "Y" },
   					{ type: "newcolumn" },
   					{ type: "radio", name: "useAt", label: "사용중지", value: "N" }
   				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 150, inputWidth: 170 },
   					{ type: "label", label: "생성자" },
   					{ type: "newcolumn" },
   					{ type: "input", name: "frstRegisterNm", value: "", readonly: true }
   				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 150, inputWidth: 170 },
   					{ type: "label", label: "생성일시" },
   					{ type: "newcolumn" },
   					{ type: "input", name: "frstRegisterPnttm", value: "", readonly: true }
   				] },
   				{ type: "block", list: [
   					{ type: "button", name: "uptBtn", value: "수정" }
   				] }
   			] }
   		];
		
		var oProcB = oProc.cells("b");
		oProcB.hideHeader();
		form = oProcB.attachForm(formData);
		form.setFontSize("11px");
		
		// 버튼 클릭 이벤트.
		form.attachEvent("onButtonClick", function(name) {
			if ( name == "tmplatSearch" ) {  // 템플릿 찾기.
				tmplatPopup();
			} else if ( name == "emplyrSearch" ) {  // 커뮤니티 관리자 찾기.
				emplyrPopup();
			}else if ( name == "uptBtn" ) {
				if ( confirm("저장하시겠습니까?") ) {
					document.getElementById("activeStatusBar").innerHTML = "";
					
					var DataProcessor = new dataProcessor("updtCmmntyInf.do");
					DataProcessor.setTransactionMode("POST", false);
					DataProcessor.setUpdateMode("off");
					DataProcessor.enableDataNames(true);
					//DataProcessor.enablePartialDataSend(true);
					DataProcessor.init(form);
					
					DataProcessor.attachEvent("onAfterUpdateFinish", function() {
						alert("저장하였습니다.");
						form = oProcB.attachForm("");
						search();
					});
					
					DataProcessor.sendData();
				}
			}
		});
	}
	
    /**
     * 툴바 생성.
     */
    function fn_toolbar() {
		toolbar = oProc.attachToolbar(); /*툴바*/
		toolbar.setIconsPath(dhtmlx.image_path);
	
		toolbar.addText("searchCnd", 1, "");
		toolbar.addInput("searchWrd", 2, "", 200);
		toolbar.addSeparator("button_Separator", 3);
	
		// selectBox 생성
		var comboDIV = toolbar.objPull[toolbar.idPrefix+"searchCnd"].obj;
		toolbar.objPull[toolbar.idPrefix+"searchCnd"].obj.innerHTML = "";
		combo = new dhtmlXCombo(comboDIV,"alfa",140);
		combo.addOption([
       		["0", "커뮤니티명"]
       	]);
		combo.selectOption(0);
	
		var hideBtn = {
			btn_Delete: false
			, btn_Undo: false
			, btn_Save: false
			, btn_Print: false
			, btn_Excel: false
		};
		comToolbarButton(toolbar, hideBtn);
		
		// event bar 생성
		toolbar.attachEvent("onClick", function(id){
			if(id == "btn_Open"){
				search();
			}
			if(id == "btn_Append"){
				inputFormData();
			}
	    });
    }
    
    /**
     * 조회
     */
    function search() {
    	oProc.progressOn();
		grid.clearAll();
		document.getElementById("activeStatusBar").innerHTML = "";
		$.post( 'selectCmmntyJSONInfs.do'
			  , { searchCnd : combo.getSelectedValue()
				, searchWrd : toolbar.getValue("searchWrd") }
		      , function(data, status, xhr){
		    	  	//jsonAlert(data.list);
		    	  	grid.attachEvent("onXLE", function(){
		    			oProc.progressOff();
		    		});
		    	  	
		    		grid.clearAll();
		    		grid.parse(data.list, "json");
		    		grid.setSelectedRow(0);
		    		document.getElementById("activeStatusBar").innerHTML = "조회되었습니다";
			    }
		      , 'json'
		 ).error(function(x,s,t) {httpError(x, s, t);});
    }
    
	/**
	 * layout a cell
	 */
    function fn_layout_a() {
		var oProcA = oProc.cells("a");
		oProcA.setWidth($(document).width() / 10 * 7);
		oProcA.hideHeader();
		
		grid = oProcA.attachGrid();
		grid.setIconsPath(dhtmlx.image_path);
		
		grid.setHeader("번호,커뮤니티명,생성자,생성일,사용여부,커뮤니티 아이디");
		grid.setColumnIds("no,cmmntyNm,frstRegisterNm,frstRegisterPnttm,useAt,cmmntyId");
		grid.setInitWidths("100,*,100,100,100,100");
		grid.setColAlign("center,center,center,center,center,center");
		grid.setColTypes("ro,ro,ro,ro,ro,ro");
		grid.enableResizing("false,false,false,false,false,false");
		grid.enableTooltips("false,false,false,false,false,false");
		grid.setColSorting("str,str,str,str,str,str");
		
		grid.enableMultiselect("true"); 
		grid.enableBlockSelection("false");
		grid.enableUndoRedo();
		grid.enableSmartRendering(true, 100);
		
		grid.setColumnHidden(5,true);
		
		grid.init();
	
		grid.attachEvent("onRowSelect", function(id, ind) {
			cmmntyPopup(grid.cells(id, 5).getValue());
		});
    }
		
	/**
	 * layout a cell
	 */
	function fn_layout_b() {
		var oProcB = oProc.cells("b");
		oProcB.hideHeader();
	}
	
    /**
     * layout status bar
     */
    function fn_layout_statusbar() {
		var main_status = oProc.attachStatusBar();
		main_status.setText("<div><table><td id='activeImg'><img src='<c:url value="/dhtmlx/dhtmlx_pro_full/imgs/run_exc.gif" />'></td><td id='activeStatusBar' valign='middle'></td></table></div>"); 
    }
    
    fn_toolbar();
    fn_layout_a();
    fn_layout_b();
    fn_layout_statusbar();
    
    /**
	 * 템플릿 정보 목록 Popup
	 */
	function tmplatPopup() {
		var tmplatPopup = oProc.dhxWins.createWindow("tmplatPopup", 20, 30, 800, 240);
		tmplatPopup.setText("템플릿 정보");
		tmplatPopup.setModal(true);
		tmplatPopup.keepInViewport(true);
		tmplatPopup.centerOnScreen();
		
		// 팝업창 레이아웃 생성.
	    var layoutPopup = new dhtmlXLayoutObject(tmplatPopup, "1C");
	    var toolbarPopup;
	    var comboPopup;
		var gridPopup;
		
		/**
		 * 툴바생성(popup용)
		 */
		function fn_toolbar_popup() {
			toolbarPopup = layoutPopup.attachToolbar(); /*툴바*/
			toolbarPopup.setIconsPath(dhtmlx.image_path);
	
			toolbarPopup.addText("searchCnd", 1, "");
			toolbarPopup.addInput("searchWrd", 2, "", 80);
			toolbarPopup.addSeparator("button_Separator", 3);
	
			var comboDIV = toolbarPopup.objPull[toolbarPopup.idPrefix+"searchCnd"].obj;
			toolbarPopup.objPull[toolbarPopup.idPrefix+"searchCnd"].obj.innerHTML = "";
			comboPopup = new dhtmlXCombo(comboDIV,"alfa",140);
			comboPopup.addOption([
				["0", "템플릿명"]
				, ["1", "템플릿구분"]
			]);
			comboPopup.selectOption(0);
			
			comToolbarButton(toolbarPopup);
			
			toolbarPopup.attachEvent("onClick", function(id){
				if(id == "btn_Open"){
					search_popup();
				}
		    });	
		}
		
		/**
		 * 조회(popup용)
		 */
		function search_popup() {
			layoutPopup.progressOn();
			gridPopup.clearAll();
			document.getElementById("tmplatActiveStatusBar").innerHTML = "";
			$.post( '/whoya/cop/tpl/selectTemplateInfsPop.do'
				  , { searchCnd : comboPopup.getSelectedValue()
					, searchWrd : toolbarPopup.getValue("searchWrd")
					, typeFlag: "CMY" }
			      , function(data, status, xhr){
			    	  	//jsonAlert(data.list);
			    	  	gridPopup.attachEvent("onXLE", function(){
			    	  		layoutPopup.progressOff();
			    		});
			    	  	
			    	  	gridPopup.clearAll();
			    	  	gridPopup.parse(data.list, "json");
			    	  	gridPopup.setSelectedRow(0);
			    		document.getElementById("tmplatActiveStatusBar").innerHTML = "조회되었습니다";
				    }
			      , 'json'
			 ).error(function(x,s,t) {httpError(x, s, t);});
		}
		
		/**
		 * layout a cell(popup용)
		 */
		function fn_layout_a_popup() {
			var oProcA = layoutPopup.cells("a");
			oProcA.hideHeader();
			
			gridPopup = oProcA.attachGrid();
			gridPopup.setIconsPath(dhtmlx.image_path);
			
			gridPopup.setHeader("번호,템플릿명,템플릿구분,템플릿경로,사용여부,등록일자,선택");
			gridPopup.setColumnIds("no,tmplatNm,tmplatSeCodeNm,tmplatCours,useAt,frstRegisterPnttm,selectLink");
			gridPopup.setInitWidths("100,*,100,100,100,100,100");
			gridPopup.setColAlign("center,center,center,center,center,center,center");
			gridPopup.setColTypes("ro,ro,ro,ro,ro,ro,img");
			gridPopup.enableResizing("true,true,true,true,true,true,true");
			gridPopup.enableTooltips("false,false,false,false,false,false,false");
			gridPopup.setColSorting("str,str,str,str,str,str,str");
			
			gridPopup.enableMultiselect("true"); 
			gridPopup.enableBlockSelection("false")
			gridPopup.enableUndoRedo();
			gridPopup.enableSmartRendering(true, 100);
			
			gridPopup.init();
		}
		
		/**
	     * layout status bar(popup용)
	     */
	    function fn_layout_statusbar_popup() {
			var main_status = layoutPopup.attachStatusBar();
			main_status.setText("<div><table><td id='tmplatActiveImg'><img src='<c:url value="/dhtmlx/dhtmlx_pro_full/imgs/run_exc.gif" />'></td><td id='tmplatActiveStatusBar' valign='middle'></td></table></div>");
		}
		
	    fn_toolbar_popup();
	    fn_layout_a_popup();
	    fn_layout_statusbar_popup();
	}
    
	/**
	 * 커뮤니티 관리자 목록 Popup
	 */
	function emplyrPopup() {
		var emplyrPopup = oProc.dhxWins.createWindow("emplyrPopup", 20, 30, 800, 240);
		emplyrPopup.setText("템플릿 정보");
		emplyrPopup.setModal(true);
		emplyrPopup.keepInViewport(true);
		emplyrPopup.centerOnScreen();
		
		// 팝업창 레이아웃 생성.
	    var layoutPopup = new dhtmlXLayoutObject(emplyrPopup, "1C");
	    var toolbarPopup;
	    var comboPopup;
		var gridPopup;
		
		/**
		 * 툴바생성(popup용)
		 */
		function fn_toolbar_popup() {
			toolbarPopup = layoutPopup.attachToolbar(); /*툴바*/
			toolbarPopup.setIconsPath(dhtmlx.image_path);
	
			toolbarPopup.addText("searchCnd", 1, "");
			toolbarPopup.addInput("searchWrd", 2, "", 80);
			toolbarPopup.addSeparator("button_Separator", 3);
	
			var comboDIV = toolbarPopup.objPull[toolbarPopup.idPrefix+"searchCnd"].obj;
			toolbarPopup.objPull[toolbarPopup.idPrefix+"searchCnd"].obj.innerHTML = "";
			comboPopup = new dhtmlXCombo(comboDIV,"alfa",140);
			comboPopup.addOption([
				["0", "사용자명"]
			]);
			comboPopup.selectOption(0);
			
			comToolbarButton(toolbarPopup);
			
			toolbarPopup.attachEvent("onClick", function(id){
				if(id == "btn_Open"){
					search_popup();
				}
		    });	
		}
		
		/**
		 * 조회(popup용)
		 */
		function search_popup() {
			layoutPopup.progressOn();
			gridPopup.clearAll();
			document.getElementById("emplyrActiveStatusBar").innerHTML = "";
			$.post( '/whoya/cop/com/selectUserList.do'
				  , { searchCnd : comboPopup.getSelectedValue()
					, searchWrd : toolbarPopup.getValue("searchWrd") }
			      , function(data, status, xhr){
			    	  	//jsonAlert(data.list);
			    	  	gridPopup.attachEvent("onXLE", function(){
			    	  		layoutPopup.progressOff();
			    		});
			    	  	
			    	  	gridPopup.clearAll();
			    	  	gridPopup.parse(data.list, "json");
			    	  	gridPopup.setSelectedRow(0);
			    		document.getElementById("emplyrActiveStatusBar").innerHTML = "조회되었습니다";
				    }
			      , 'json'
			 ).error(function(x,s,t) {httpError(x, s, t);});
		}
		
		/**
		 * layout a cell(popup용)
		 */
		function fn_layout_a_popup() {
			var oProcA = layoutPopup.cells("a");
			oProcA.hideHeader();
			
			gridPopup = oProcA.attachGrid();
			gridPopup.setIconsPath(dhtmlx.image_path);
			
			gridPopup.setHeader("번호,사용자아이디,사용자명,주소,이메일,사용여부,선택");
			gridPopup.setColumnIds("no,userId,userNm,userAdres,userEmail,useAt,selectLink");
			gridPopup.setInitWidths("100,100,100,*,100,100,100");
			gridPopup.setColAlign("center,center,center,center,center,center,center");
			gridPopup.setColTypes("ro,ro,ro,ro,ro,ro,img");
			gridPopup.enableResizing("true,true,true,true,true,true,true");
			gridPopup.enableTooltips("false,false,false,false,false,false,false");
			gridPopup.setColSorting("str,str,str,str,str,str,str");
			
			gridPopup.enableMultiselect("true"); 
			gridPopup.enableBlockSelection("false")
			gridPopup.enableUndoRedo();
			gridPopup.enableSmartRendering(true, 100);
			
			gridPopup.init();
		}
		
		/**
	     * layout status bar(popup용)
	     */
	    function fn_layout_statusbar_popup() {
			var main_status = layoutPopup.attachStatusBar();
			main_status.setText("<div><table><td id='emplyrActiveImg'><img src='<c:url value="/dhtmlx/dhtmlx_pro_full/imgs/run_exc.gif" />'></td><td id='emplyrActiveStatusBar' valign='middle'></td></table></div>");
		}
		
	    fn_toolbar_popup();
	    fn_layout_a_popup();
	    fn_layout_statusbar_popup();
	}
	
	/**
	 * 커뮤니티 정보조회 Popup
	 */
	function cmmntyPopup(cmmntyId) {
		var cmmntyPopup = oProc.dhxWins.createWindow("cmmntyPopup", 20, 30, 800, 240);
		cmmntyPopup.setText("커뮤니티 정보조회");
		cmmntyPopup.setModal(true);
		cmmntyPopup.keepInViewport(true);
		cmmntyPopup.centerOnScreen();
		
		// 팝업창 레이아웃 생성.
	    var layoutPopup = new dhtmlXLayoutObject(cmmntyPopup, "2E");
		var gridPopup;
		var formPopup;
		
		/**
		 * 조회(popup용)
		 */
		function search_popup() {
			layoutPopup.progressOn();
			gridPopup.clearAll();
			document.getElementById("cmmntyActiveStatusBar").innerHTML = "";
			$.post( '/whoya/cop/clb/selectClubInfByCmmntyId.do'
				  , { cmmntyId: cmmntyId }
			      , function(data, status, xhr){
			    	  	//jsonAlert(data.list);
			    	  	gridPopup.attachEvent("onXLE", function(){
			    	  		layoutPopup.progressOff();
			    		});
			    	  	
			    	  	gridPopup.clearAll();
			    	  	gridPopup.parse(data.list, "json");
			    	  	gridPopup.setSelectedRow(0);
			    		document.getElementById("cmmntyActiveStatusBar").innerHTML = "조회되었습니다";
				    }
			      , 'json'
			 ).error(function(x,s,t) {httpError(x, s, t);});
		}
		
		/**
		 * layout a cell(popup용)
		 */
		function fn_layout_a_popup() {
			var formData = [
    			{ type: "fieldset", name: "formField", label: "커뮤니티 정보조회", list: [
    				{ type: "block", list: [
    					{ type: "settings", labelWidth: 150, inputWidth: 170 },
    					{ type: "label", label: "커뮤니티명" },
    					{ type: "newcolumn" },
    					{ type: "input", name: "cmmntyNm", value: "", readonly: true }
    				] },
    				{ type: "block", list: [
    					{ type: "settings", labelWidth: 150, inputWidth: 170 },
    					{ type: "label", label: "커뮤니티 소개" },
    					{ type: "newcolumn" },
    					{ type: "input", name: "cmmntyIntrcn", value: "", rows: "3", readonly: true }
    				] },
    				{ type: "block", list: [
    					{ type: "settings", labelWidth: 150, inputWidth: 170 },
    					{ type: "label", label: "템플릿 정보" },
    					{ type: "newcolumn" },
    					{ type: "input", name: "tmplatNm", value: "", readonly: true }
    				] },
    				{ type: "block", list: [
    					{ type: "settings", labelWidth: 150, inputWidth: 170 },
    					{ type: "label", label: "게시판 정보" },
    					{ type: "newcolumn" },
    					{ type: "input", name: "bbsNmList", value: "", readonly: true }
    				] },
    				{ type: "block", list: [
    					{ type: "settings", labelWidth: 150, inputWidth: 170 },
    					{ type: "label", label: "사용여부" },
    					{ type: "newcolumn" },
    					{ type: "input", name: "useAt", value: "", readonly: true }
    				] },
    				{ type: "block", list: [
    					{ type: "settings", labelWidth: 150, inputWidth: 170 },
    					{ type: "label", label: "생성자" },
    					{ type: "newcolumn" },
    					{ type: "input", name: "frstRegisterNm", value: "", readonly: true }
    				] },
    				{ type: "block", list: [
    					{ type: "settings", labelWidth: 150, inputWidth: 170 },
    					{ type: "label", label: "생성일시" },
    					{ type: "newcolumn" },
    					{ type: "input", name: "frstRegisterPnttm", value: "", readonly: true }
    				] },
    				{ type: "block", list: [
    					{ type: "settings", labelWidth: 150, inputWidth: 170 },
    					{ type: "label", label: "제공 URL" },
    					{ type: "newcolumn" },
    					{ type: "input", name: "provdUrl", value: "", readonly: true }
    				] },
    				{ type: "block", list: [
	   					{ type: "button", name: "uptBtn", value: "수정" }
	   				] }
    			] }
    		];
    		
			var oProcA = layoutPopup.cells("a");
			oProcA.setText("커뮤니티 정보조회");
			formPopup = oProcA.attachForm(formData);
			formPopup.setFontSize("11px");
    		
			// 버튼 클릭 이벤트.
			formPopup.attachEvent("onButtonClick", function(name) {
				if ( name == "uptBtn" ) {
					updateFormData();
					$.ajax({
						url: "/whoya/cop/cmy/forUpdateCmmntyInf.do"
						, data: {
							cmmntyId: cmmntyId
						}
						, success: function(data, textStatus, jqXHR) {
							var bbsNmList = "";
							$.each(data.bbsNmList, function() {
								bbsNmList += this.bbsNm + " / ";
							});
							data.bbsNmList = bbsNmList;
							form.setFormData(data);
						}
						, error: function(jqXHR, textStatus, errorThrown) {
							console.log(jqXHR);
							console.log(textStatus);
							console.log(errorThrown);
							alert(errorThrown);
						}
					});
					cmmntyPopup.close();
				}
			});
			
			$.ajax({
				url: "/whoya/cop/cmy/selectCmmntyInf.do"
				, data: {
					cmmntyId: cmmntyId
				}
				, success: function(data, textStatus, jqXHR) {
					var bbsNmList = "";
					$.each(data.bbsNmList, function() {
						bbsNmList += this.bbsNm + " / ";
					});
					data.bbsNmList = bbsNmList;
					formPopup.setFormData(data);
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
		 * layout b cell(popup용)
		 */
		function fn_layout_b_popup() {
			var oProcB = layoutPopup.cells("b");
			oProcB.setText("소속 동호회 목록");
			
			gridPopup = oProcB.attachGrid();
			gridPopup.setIconsPath(dhtmlx.image_path);
			
			gridPopup.setHeader("번호,동호회명,등록일,사용여부");
			gridPopup.setColumnIds("no,clbNm,frstRegisterPnttm,useAt");
			gridPopup.setInitWidths("100,*,100,100");
			gridPopup.setColAlign("center,center,center,center");
			gridPopup.setColTypes("ro,ro,ro,ro");
			gridPopup.enableResizing("true,true,true,true");
			gridPopup.enableTooltips("false,false,false,false");
			gridPopup.setColSorting("str,str,str,str");
			
			gridPopup.enableMultiselect("true"); 
			gridPopup.enableBlockSelection("false")
			gridPopup.enableUndoRedo();
			gridPopup.enableSmartRendering(true, 100);
			
			gridPopup.init();
		}
		
		/**
	     * layout status bar(popup용)
	     */
	    function fn_layout_statusbar_popup() {
			var main_status = layoutPopup.attachStatusBar();
			main_status.setText("<div><table><td id='cmmntyActiveImg'><img src='<c:url value="/dhtmlx/dhtmlx_pro_full/imgs/run_exc.gif" />'></td><td id='cmmntyActiveStatusBar' valign='middle'></td></table></div>");
		}
		
	    fn_layout_a_popup();
	    fn_layout_b_popup();
	    fn_layout_statusbar_popup();
	    search_popup();
	}
}

$(document).ready(function() {
	init();
});

	/**
	 * 템플릿 정보(팝업)에서 선택된 값 저장. 
	 */
	function tmplatSelect(tmplatId, tmplatNm) {
		form.setItemValue("tmplatId", tmplatId);
		form.setItemValue("tmplatNm", tmplatNm);
		oProc.dhxWins.window("tmplatPopup").close();
	}
	
	/**
	 * 커뮤니티 관리자 정보(팝업)에서 선택된 값 저장. 
	 */
	function emplyrSelect(emplyrId, emplyrNm) {
		form.setItemValue("emplyrId", emplyrId);
		form.setItemValue("emplyrNm", emplyrNm);
		oProc.dhxWins.window("emplyrPopup").close();
	}
</script>
</head>
<body>
</body>
</html>