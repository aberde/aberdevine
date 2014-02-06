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
			{ type: "fieldset", name: "formField", label: "게시판 사용등록", list: [
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 100 },
					{ type: "label", label: "게시판명" },
					{ type: "newcolumn" },
					{ type: "hidden", name: "bbsId", value: "" },
					{ type: "input", name: "bbsNm", value: "", readonly: true },
					{ type: "newcolumn" },
					{ type: "button", name: "bbsSearch", value: "게시판 찾기" }
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 100 },
					{ type: "label", label: "커뮤니티/동호회 정보" },
					{ type: "newcolumn" },
					{ type: "select", name: "trgetType", options: [
						{ value: "", text: "--선택하세요--" },
						{ value: "CMMNTY", text: "커뮤니티" },
						{ value: "CLUB", text: "동호회" },
						{ value: "SYSTEM", text: "시스템" }
					] },
					{ type: "newcolumn" },
					{ type: "hidden", name: "trgetId", value: "" },
					{ type: "input", name: "trgetNm", value: "", readonly: true }
				] },
				{ type: "block", list: [
					{ type: "button", name: "regBtn", value: "등록" },
				] }
			] }
		];
		
		var oProcB = oProc.cells("b");
		oProcB.hideHeader();
		form = oProcB.attachForm(formData);
		form.setFontSize("11px");
		
		// 버튼 클릭 이벤트.
		form.attachEvent("onButtonClick", function(name) {
			if ( name == "bbsSearch" ) {  // 게시판 찾기.
				boardPopup();
			} else if ( name == "regBtn" ) {
				if ( confirm("저장하시겠습니까?") ) {
					document.getElementById("activeStatusBar").innerHTML = "";
					
					var DataProcessor = new dataProcessor("insertBBSUseInf.do");
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
		
		// selectbox onChange 이벤트.
		form.attachEvent("onChange", function(name, value) {
			// 커뮤니티/동호회 정보 selectbox
			if ( name == "trgetType" ) {
				if ( value == "CMMNTY" ) {  // 커뮤니티 선택.
					communityPopup();
				} else if ( value == "CLUB" ) {  //  동호회 선택.
					clubPopup();
				} else if ( value == "SYSTEM" ) {  // 시스템 선택.
					form.setItemValue("trgetId", "SYSTEM_DEFAULT_BOARD");
					form.setItemValue("trgetNm", "시스템 활용");
				}
			}
	    });
	}

	/** 
	 * 수정될 formData UI 구성.
	 */
	function updateFormData() {
		var formData = [
			{ type: "fieldset", name: "formField", label: "게시판 사용정보 수정", list: [
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 100 },
					{ type: "label", label: "게시판명" },
					{ type: "newcolumn" },
					{ type: "hidden", name: "bbsId", value: "" },
					{ type: "input", name: "bbsNm", value: "", readonly: true },
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 100 },
					{ type: "label", label: "커뮤니티/동호회 정보" },
					{ type: "newcolumn" },
					{ type: "hidden", name: "trgetId", value: "" },
					{ type: "input", name: "cmmntyNm", value: "", readonly: true, hidden: true },
					{ type: "input", name: "clbNm", value: "", readonly: true, hidden: true },
					{ type: "input", name: "trgetNm", value: "(시스템  활용)", readonly: true }
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 70, inputWidth: 100, position: "label-right" },
					{ type: "label", label: "사용여부", labelWidth: 150},
					{ type: "newcolumn" },
					{ type: "radio", label: "사용", name: "useAt", value: "Y" },
					{ type: "newcolumn" },
					{ type: "radio", label: "사용중지", name: "useAt", value: "N" }
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 100 },
					{ type: "label", label: "제공 URL" },
					{ type: "newcolumn" },
					{ type: "input", name: "provdUrl", value: "", readonly: true },
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
			if ( name == "bbsSearch" ) {  // 게시판 찾기.
				boardPopup();
			} else if ( name == "uptBtn" ) {
				if ( confirm("저장하시겠습니까?") ) {
					document.getElementById("activeStatusBar").innerHTML = "";
					
					var DataProcessor = new dataProcessor("updateBBSUseInf.do");
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
			["0", "게시판명"]
			, ["1", "사용 커뮤니티명"]
			, ["2", "사용 동호회명"]
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
		$.post( 'selectBBSUseJSONInfs.do'
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
		
		grid.setHeader("게시판명,사용 커뮤니티 명,사용 동호회 명,등록일시,사용여부,게시판 아이디,대상시스템 아이디");
		grid.setColumnIds("bbsNm,cmmntyNm,clbNm,frstRegisterPnttm,useAt,bbsId,trgetId");
		grid.setInitWidths("*,*,*,100,100,100,100");
		grid.setColAlign("center,center,center,center,center,center,center");
		grid.setColTypes("ro,ro,ro,ro,ro,ro,ro");
		grid.enableResizing("false,true,false,false,false,true,false");
		grid.enableTooltips("false,false,false,false,false,false,false");
		grid.setColSorting("str,str,str,str,str,str,str");
		
		grid.enableMultiselect("true"); 
		grid.enableBlockSelection("false");
		grid.enableUndoRedo();
		grid.enableSmartRendering(true, 100);
		
		grid.setColumnHidden(5,true);
		grid.setColumnHidden(6,true);
		
		grid.init();
	
		grid.attachEvent("onRowSelect", function(id, ind) {
			updateFormData();
			$.ajax({
				url: "/whoya/cop/com/selectBBSUseInf.do"
				, data: {
					bbsId: grid.cells(id, 5).getValue()
					, trgetId: grid.cells(id, 6).getValue()
				}
				, success: function(data, textStatus, jqXHR) {
					form.setFormData(data);
					if ( data.cmmntyNm ) {
						form.showItem("cmmntyNm");
						form.hideItem("clbNm");
						form.hideItem("trgetNm");
					} else if ( data.clbNm ) {
						form.hideItem("cmmntyNm");
						form.showItem("clbNm");
						form.hideItem("trgetNm");
					} else {
						form.hideItem("cmmntyNm");
						form.hideItem("clbNm");
						form.showItem("trgetNm");
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
		
	/**
	 * layout a cell
	 */
	function fn_layout_b() {
		var oProcB = oProc.cells("b");
		oProcB.hideHeader();
	}
	
	/** 
	 * log
	 */
	function log(message, args) {
	    var div = document.createElement("div");
	    for (var i = 0; i < 3; i++) {
	        message += "<li>" + args[i];
	    };
	    message += "<br/>";
	    console.log(message);
	    div.innerHTML = message;
	    document.getElementById('zoneA').appendChild(div);
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
	 * 게시판 정보 목록 Popup
	 */
	function boardPopup() {
		var boardPopup = oProc.dhxWins.createWindow("boardPopup", 20, 30, 800, 240);
		boardPopup.setText("게시판 정보");
		boardPopup.setModal(true);
		boardPopup.keepInViewport(true);
		boardPopup.centerOnScreen();
		
		// 팝업창 레이아웃 생성.
	    var layoutPopup = new dhtmlXLayoutObject(boardPopup, "1C");
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
				["0", "게시판명"]
				, ["1", "게시판유형"]
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
			document.getElementById("activeStatusBar").innerHTML = "";
			$.post( '/whoya/cop/bbs/SelectBBSMasterInfsPop.do'
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
			    		document.getElementById("activeStatusBar").innerHTML = "조회되었습니다";
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
			
			gridPopup.setHeader("번호,게시판명,게시판유형,게시판속성,생성일,사용여부,선택");
			gridPopup.setColumnIds("no,bbsNm,bbsTyCodeNm,bbsAttrbCodeNm,frstRegisterPnttm,useAt,selectLink");
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
			
			//grid.enablePaging(true, 10, 2, "activeStatusBar");
			//grid.setPagingSkin("toolbar","dhx_skyblue");
	
			gridPopup.init();
			//grid.enableRowsHover(true,'grid_hover');
			//grid.enableLightMouseNavigation(true);
			//grid.enableKeyboardSupport(true);
			//grid.loadXML("/whoya/dhtmlx/data/accountCode.xml");
	
			gridPopup.attachEvent("onKeyPress", function(code, ctrl, shift){                        
				//comKeyPress(grid, code, ctrl, shift, ",,,,0,,1");	
		    });
		}
		
		function log(message, args) {
		    var div = document.createElement("div");
		    for (var i = 0; i < 3; i++) {
		        message += "<li>" + args[i];
		    };

		    message += "<br/>";
		    div.innerHTML = message;
		    document.getElementById('zoneA').appendChild(div);
		}

		/**
	     * layout status bar(popup용)
	     */
	    function fn_layout_statusbar_popup() {
			var main_status = layoutPopup.attachStatusBar();
			main_status.setText("<div><table><td id='activeImg'><img src='<c:url value="/dhtmlx/dhtmlx_pro_full/imgs/run_exc.gif" />'></td><td id='activeStatusBar' valign='middle'></td></table></div>");
		}
		
	    fn_toolbar_popup();
	    fn_layout_a_popup();
	    fn_layout_statusbar_popup();
	}
	
	
	/**
	 * 커뮤니티 정보 목록 Popup
	 */
	function communityPopup() {
		var communityPopup = oProc.dhxWins.createWindow("communityPopup", 20, 30, 800, 240);
		communityPopup.setText("커뮤니티 정보");
		communityPopup.setModal(true);
		communityPopup.keepInViewport(true);
		communityPopup.centerOnScreen();
		
		// 팝업창 레이아웃 생성.
	    var layoutPopup = new dhtmlXLayoutObject(communityPopup, "1C");
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
				["0", "커뮤니티명"]
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
			document.getElementById("activeStatusBar").innerHTML = "";
			$.post( '/whoya/cop/cmy/selectCmmntyInfsByPop.do'
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
			    		document.getElementById("activeStatusBar").innerHTML = "조회되었습니다";
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
			
			gridPopup.setHeader("번호,게시판명,게시판유형,게시판속성,생성일,사용여부,선택");
			gridPopup.setColumnIds("no,bbsNm,bbsTyCodeNm,bbsAttrbCodeNm,frstRegisterPnttm,useAt,selectLink");
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
			
			//grid.enablePaging(true, 10, 2, "activeStatusBar");
			//grid.setPagingSkin("toolbar","dhx_skyblue");
	
			gridPopup.init();
			//grid.enableRowsHover(true,'grid_hover');
			//grid.enableLightMouseNavigation(true);
			//grid.enableKeyboardSupport(true);
			//grid.loadXML("/whoya/dhtmlx/data/accountCode.xml");
	
			gridPopup.attachEvent("onKeyPress", function(code, ctrl, shift){                        
				//comKeyPress(grid, code, ctrl, shift, ",,,,0,,1");	
		    });
		}
		
		function log(message, args) {
		    var div = document.createElement("div");
		    for (var i = 0; i < 3; i++) {
		        message += "<li>" + args[i];
		    };

		    message += "<br/>";
		    div.innerHTML = message;
		    document.getElementById('zoneA').appendChild(div);
		}

		/**
	     * layout status bar(popup용)
	     */
	    function fn_layout_statusbar_popup() {
			var main_status = layoutPopup.attachStatusBar();
			main_status.setText("<div><table><td id='activeImg'><img src='<c:url value="/dhtmlx/dhtmlx_pro_full/imgs/run_exc.gif" />'></td><td id='activeStatusBar' valign='middle'></td></table></div>");
		}
		
	    fn_toolbar_popup();
	    fn_layout_a_popup();
	    fn_layout_statusbar_popup();
	}
	
	
	/**
	 * 동호회 정보 목록 Popup
	 */
	function clubPopup() {
		var clubPopup = oProc.dhxWins.createWindow("clubPopup", 20, 30, 800, 240);
		clubPopup.setText("동호회 정보");
		clubPopup.setModal(true);
		clubPopup.keepInViewport(true);
		clubPopup.centerOnScreen();
		
		// 팝업창 레이아웃 생성.
	    var layoutPopup = new dhtmlXLayoutObject(clubPopup, "1C");
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
				["0", "동호회명"]
				, ["1", "커뮤니티명"]
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
			document.getElementById("activeStatusBar").innerHTML = "";
			$.post( '/whoya/cop/clb/selectClubInfsByPop.do'
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
			    		document.getElementById("activeStatusBar").innerHTML = "조회되었습니다";
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
			
			gridPopup.setHeader("번호,동호회명,커뮤니티명,등록일,선택");
			gridPopup.setColumnIds("no,clbNm,cmmntyNm,bbsAttrbCodeNm,frstRegisterPnttm,selectLink");
			gridPopup.setInitWidths("100,*,100,100,100");
			gridPopup.setColAlign("center,center,center,center,center");
			gridPopup.setColTypes("ro,ro,ro,ro,img");
			gridPopup.enableResizing("true,true,true,true,true");
			gridPopup.enableTooltips("false,false,false,false,false");
			gridPopup.setColSorting("str,str,str,str,str");
			
			gridPopup.enableMultiselect("true"); 
			gridPopup.enableBlockSelection("false")
			gridPopup.enableUndoRedo();
			gridPopup.enableSmartRendering(true, 100);
			
			//grid.enablePaging(true, 10, 2, "activeStatusBar");
			//grid.setPagingSkin("toolbar","dhx_skyblue");
	
			gridPopup.init();
			//grid.enableRowsHover(true,'grid_hover');
			//grid.enableLightMouseNavigation(true);
			//grid.enableKeyboardSupport(true);
			//grid.loadXML("/whoya/dhtmlx/data/accountCode.xml");
	
			gridPopup.attachEvent("onKeyPress", function(code, ctrl, shift){                        
				//comKeyPress(grid, code, ctrl, shift, ",,,,0,,1");	
		    });
		}
		
		function log(message, args) {
		    var div = document.createElement("div");
		    for (var i = 0; i < 3; i++) {
		        message += "<li>" + args[i];
		    };

		    message += "<br/>";
		    div.innerHTML = message;
		    document.getElementById('zoneA').appendChild(div);
		}

		/**
	     * layout status bar(popup용)
	     */
	    function fn_layout_statusbar_popup() {
			var main_status = layoutPopup.attachStatusBar();
			main_status.setText("<div><table><td id='activeImg'><img src='<c:url value="/dhtmlx/dhtmlx_pro_full/imgs/run_exc.gif" />'></td><td id='activeStatusBar' valign='middle'></td></table></div>");
		}
		
	    fn_toolbar_popup();
	    fn_layout_a_popup();
	    fn_layout_statusbar_popup();
	}
}

$(document).ready(function() {
	init();
});

	/**
	 * 게시판 정보(팝업)에서 선택된 값 저장. 
	 */
	function boardSelect(bbsId, bbsNm) {
		form.setItemValue("bbsId", bbsId);
		form.setItemValue("bbsNm", bbsNm);
		oProc.dhxWins.window("boardPopup").close();
	}

	/**
	 * 커뮤니티 정보(팝업)에서 선택된 값 저장. 
	 */
	function communitySelect(cmmntyId, cmmntyNm) {
		form.setItemValue("trgetId", cmmntyId);
		form.setItemValue("trgetNm", cmmntyNm);
		oProc.dhxWins.window("communityPopup").close();
	}

	/**
	 * 커뮤니티 정보(팝업)에서 선택된 값 저장. 
	 */
	function clulbSelect(clbId, clbNm) {
		form.setItemValue("trgetId", clbId);
		form.setItemValue("trgetNm", clbNm);
		oProc.dhxWins.window("clubPopup").close();
	}
</script>
</head>
<body>
</body>
</html>