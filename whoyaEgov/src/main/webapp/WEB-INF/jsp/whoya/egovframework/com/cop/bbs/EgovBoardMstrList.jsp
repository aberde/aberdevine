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
			{ type: "fieldset", name: "formField", label: "게시판 생성", list: [
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 170 },
					{ type: "label", label: "게시판명" },
					{ type: "newcolumn" },
					{ type: "input", name: "bbsNm", value: "" },
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 170 },
					{ type: "label", label: "게시판소개" },
					{ type: "newcolumn" },
					{ type: "input", name: "bbsIntrcn", value: "", rows: 3 }
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 170 },
					{ type: "label", label: "게시판유형" },
					{ type: "newcolumn" },
					{ type: "select", name: "bbsTyCode", options: [
						{ value: "", text: "--선택하세요--" }
					] },
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 170 },
					{ type: "label", label: "게시판속성" },
					{ type: "newcolumn" },
					{ type: "select", name: "bbsAttrbCode", options: [
						{ value: "", text: "--선택하세요--" }
					] },
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 70, inputWidth: 170, position: "label-right" },
					{ type: "label", label: "답장가능여부", labelWidth: 150 },
					{ type: "newcolumn" },
					{ type: "radio", label: "가능", name: "replyPosblAt", value: "Y" },
					{ type: "newcolumn" },
					{ type: "radio", label: "불가능", name: "replyPosblAt", value: "N" }
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 70, inputWidth: 170, position: "label-right" },
					{ type: "label", label: "파일첨부가능여부", labelWidth: 150 },
					{ type: "newcolumn" },
					{ type: "radio", label: "가능", name: "fileAtchPosblAt", value: "Y" },
					{ type: "newcolumn" },
					{ type: "radio", label: "불가능", name: "fileAtchPosblAt", value: "N" }
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 170 },
					{ type: "label", label: "첨부가능파일 숫자" },
					{ type: "newcolumn" },
					{ type: "select", name: "posblAtchFileNumber", options: [
						{ value: "0", text: "없음" },
						{ value: "1", text: "1개" },
						{ value: "2", text: "2개" },
						{ value: "3", text: "3개" }
					] },
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 170 },
					{ type: "label", label: "템플릿 정보" },
					{ type: "newcolumn" },
					{ type: "input", name: "tmplatId", hidden: true },
					{ type: "input", name: "tmplatNm", readonly: true },
					{ type: "newcolumn" },
					{ type: "button", name: "tmplatSearch", value: "템플릿 찾기" }
				] },
				{ type: "block", list: [
					{ type: "settings", labelWidth: 150, inputWidth: 170 },
					{ type: "label", label: "추가 선택사항" },
					{ type: "newcolumn" },
					{ type: "select", name: "option", options: [
						{ value: "", text: "미선택" },
						{ value: "comment", text: "댓글" },
						{ value: "stsfdg", text: "만족도조사" }
					] },
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
		
		getBbsTyCode();
		getBbsAttrbCode();
		
		// 버튼 클릭 이벤트.
		form.attachEvent("onButtonClick", function(name) {
			if ( name == "tmplatSearch" ) {  // 템플릿 찾기.
				tmplatPopup();
			} else if ( name == "regBtn" ) {
				if ( confirm("저장하시겠습니까?") ) {
					document.getElementById("activeStatusBar").innerHTML = "";
					
					var DataProcessor = new dataProcessor("insertBBSMasterInf.do");
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
   			{ type: "fieldset", name: "formField", label: "게시판 생성", list: [
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 150, inputWidth: 170 },
   					{ type: "label", label: "게시판명" },
   					{ type: "newcolumn" },
   					{ type: "input", name: "bbsNm", value: "" },
   				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 150, inputWidth: 170 },
   					{ type: "label", label: "게시판소개" },
   					{ type: "newcolumn" },
   					{ type: "input", name: "bbsIntrcn", value: "", rows: 3 }
   				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 150, inputWidth: 170 },
   					{ type: "label", label: "게시판유형" },
   					{ type: "newcolumn" },
   					{ type: "select", name: "bbsTyCode", disabled: true, options: [
   						{ value: "", text: "--선택하세요--" }
   					] },
   				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 150, inputWidth: 170 },
   					{ type: "label", label: "게시판속성" },
   					{ type: "newcolumn" },
   					{ type: "select", name: "bbsAttrbCode", disabled: true, options: [
   						{ value: "", text: "--선택하세요--" }
   					] },
   				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 70, inputWidth: 170, position: "label-right" },
   					{ type: "label", label: "답장가능여부", labelWidth: 150 },
   					{ type: "newcolumn" },
   					{ type: "radio", label: "가능", name: "replyPosblAt", value: "Y", disabled: true },
   					{ type: "newcolumn" },
   					{ type: "radio", label: "불가능", name: "replyPosblAt", value: "N", disabled: true }
   				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 70, inputWidth: 170, position: "label-right" },
   					{ type: "label", label: "파일첨부가능여부", labelWidth: 150 },
   					{ type: "newcolumn" },
   					{ type: "radio", label: "가능", name: "fileAtchPosblAt", value: "Y" },
   					{ type: "newcolumn" },
   					{ type: "radio", label: "불가능", name: "fileAtchPosblAt", value: "N" }
   				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 150, inputWidth: 170 },
   					{ type: "label", label: "첨부가능파일 숫자" },
   					{ type: "newcolumn" },
   					{ type: "select", name: "posblAtchFileNumber", options: [
   						{ value: "0", text: "없음" },
   						{ value: "1", text: "1개" },
   						{ value: "2", text: "2개" },
   						{ value: "3", text: "3개" }
   					] },
   				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 150, inputWidth: 170 },
   					{ type: "label", label: "템플릿 정보" },
   					{ type: "newcolumn" },
   					{ type: "input", name: "tmplatId", hidden: true },
   					{ type: "input", name: "tmplatNm", readonly: true },
   					{ type: "newcolumn" },
   					{ type: "button", name: "tmplatSearch", value: "템플릿 찾기" }
   				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 150, inputWidth: 170 },
   					{ type: "label", label: "추가 선택사항" },
   					{ type: "newcolumn" },
   					{ type: "select", name: "option", options: [
   						{ value: "", text: "미선택" },
   						{ value: "comment", text: "댓글" },
   						{ value: "stsfdg", text: "만족도조사" }
   					] },
   				] },
   				{ type: "block", list: [
   					{ type: "button", name: "uptBtn", value: "수정" },
   					{ type: "newcolumn" },
   					{ type: "button", name: "dltBtn", value: "삭제" },
   				] }
   			] }
   		];
		
		var oProcB = oProc.cells("b");
		oProcB.hideHeader();
		form = oProcB.attachForm(formData);
		form.setFontSize("11px");
		
		getBbsTyCode();
		getBbsAttrbCode();
		
		// 버튼 클릭 이벤트.
		form.attachEvent("onButtonClick", function(name) {
			if ( name == "bbsSearch" ) {  // 게시판 찾기.
				boardPopup();
			} else if ( name == "uptBtn" ) {
				if ( confirm("저장하시겠습니까?") ) {
					document.getElementById("activeStatusBar").innerHTML = "";
					
					var DataProcessor = new dataProcessor("UpdateBBSMasterInf.do");
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
			} else if ( name == "dltBtn" ) {
				if ( confirm("삭제하시겠습니까?") ) {
					document.getElementById("activeStatusBar").innerHTML = "";
					
					var DataProcessor = new dataProcessor("DeleteBBSMasterInf.do");
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
	 * 게시판 유형 목록 가져오기.
	 */
	function getBbsTyCode() {
		$.ajax({
			url: "/whoya/cop/bbs/SelectBBSCodeList.do"
			, dataType: "json"
			, data: {
				codeId: "COM004"
			}
			, success: function(data, textStatus, jqXHR) {
				var bbsTyCode = form.getOptions("bbsTyCode");
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
			url: "/whoya/cop/bbs/SelectBBSCodeList.do"
			, dataType: "json"
			, data: {
				codeId: "COM009"
			}
			, success: function(data, textStatus, jqXHR) {
				var bbsAttrbCode = form.getOptions("bbsAttrbCode");
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
       		, ["1", "게시판유형"]
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
		$.post( 'SelectBBSMasterJSONInfs.do'
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
		
		grid.setHeader("번호,게시판명,게시판유형,게시판속성,생성일,사용여부,게시판 아이디,유일 아이디");
		grid.setColumnIds("no,bbsNm,bbsTyCodeNm,bbsAttrbCodeNm,frstRegisterPnttm,useAt,bbsId,uniqId");
		grid.setInitWidths("100,*,150,150,150,100,100,100");
		grid.setColAlign("center,center,center,center,center,center,center,center");
		grid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro");
		grid.enableResizing("false,true,false,false,false,false,false,false");
		grid.enableTooltips("false,false,false,false,false,false,false,false");
		grid.setColSorting("str,str,str,str,str,str,str,str");
		
		grid.enableMultiselect("true"); 
		grid.enableBlockSelection("false");
		grid.enableUndoRedo();
		grid.enableSmartRendering(true, 100);
		
		grid.setColumnHidden(6,true);
		grid.setColumnHidden(7,true);
		
		grid.init();
	
		grid.attachEvent("onRowSelect", function(id, ind) {
			updateFormData();
			$.ajax({
				url: "/whoya/cop/bbs/SelectBBSMasterInf.do"
				, data: {
					bbsId: grid.cells(id, 6).getValue()
					, uniqId: grid.cells(id, 7).getValue()
				}
				, success: function(data, textStatus, jqXHR) {
					form.setFormData(data);
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
			document.getElementById("activeStatusBar").innerHTML = "";
			$.post( '/whoya/cop/tpl/selectTemplateInfsPop.do'
				  , { searchCnd : comboPopup.getSelectedValue()
					, searchWrd : toolbarPopup.getValue("searchWrd")
					, typeFlag: "BBS" }
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
	 * 템플릿 정보(팝업)에서 선택된 값 저장. 
	 */
	function tmplatSelect(tmplatId, tmplatNm) {
		form.setItemValue("tmplatId", tmplatId);
		form.setItemValue("tmplatNm", tmplatNm);
		oProc.dhxWins.window("tmplatPopup").close();
	}
</script>
</head>
<body>
</body>
</html>