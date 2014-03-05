/**
 * #########################################
 * 비밀번호 확인 Popup
 * #########################################
 */
function passwdPopup(passwdData) {
	// #########################################
	// ## layout에 windows 생성
	// #########################################
	var passwdPopupWindowsData = {
		layout: whoyaGlobalData.layout
		, id: "passwdPopup"
		, width: 400
		, height: 140
		, setText: "작성 비밀번호 확인"
	};
	whoyaGlobalData.passwdPopupWindows = whoya.dhtmlx.layout.windows(passwdPopupWindowsData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 레이아웃생성
	// #########################################
	var passwdPopupLayoutData = {
		layout_target: whoyaGlobalData.passwdPopupWindows
		, layout_Pattern: "1C"
	};
	whoyaGlobalData.passwdPopupLayout = whoya.dhtmlx.layout.init(passwdPopupLayoutData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout cell a
	// #########################################
	var passwdPopupaCellData = {
		layout: whoyaGlobalData.passwdPopupLayout
	};
	// 팝업창 화면 layout의 해당 cell 정의 
	whoyaGlobalData.passwdPopupaCell = whoya.dhtmlx.layout.cell.init(passwdPopupaCellData);
	// #########################################
	
	
	// #########################################
	// ## layout cell a에 form생성
	// #########################################
	// 비밀번호 확인 폼
	whoyaGlobalData.aCellPasswdFormData = {
		cell: whoyaGlobalData.passwdPopupaCell
		, formData: [
   			{ type: "fieldset", name: "formField", label: "작성 비밀번호 확인", list: [
   				{ type: "settings", labelWidth: 150, inputWidth: 170 },
   				{ type: "password", label: "작성글 비밀번호", name: "writngPassword", value: "" },
   				{ type: "label", list: [
					{ type: "button", name: "okBtn", value: "확인" },
					{ type: "newcolumn" },
					{ type: "button", name: "cancleBtn", value: "취소" }
				] }
   			] }
   		]
	};
	// 화면 layout cell a에 dhtmlxForm 객체 생성.
	whoyaGlobalData.aCellPasswdForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.aCellPasswdFormData);
	passwdFormEvent(passwdData);
	// #########################################
}

//비밀번호 확인 Popup form event 생성
function passwdFormEvent(passwdData) {
	whoyaGlobalData.aCellPasswdForm.attachEvent("onButtonClick", function(name) {
		if ( name == "okBtn" ) {  // 확인
			var qaId = whoyaGlobalData.bForm.getFormData().qaId;
			$.ajax({
				url: whoya.context + "/whoya/uss/olh/qna/QnaPasswordConfirm.do"
				, type: "POST"
				, data: {
					qaId: qaId
					, writngPassword: whoyaGlobalData.aCellPasswdForm.getItemValue("writngPassword")
				}
				, success: function(data, textStatus, jqXHR) {
					if ( data.status == "SUCCESS" ){
						whoyaGlobalData.passwdPopupWindows.close();
						if ( passwdData.mode == "update" ) {
							whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellUpdateFormData);
							formEvent();
							$.ajax({
								url: whoya.context + "/whoya/uss/olh/qna/QnaDetailInqire.do"
								, type: "POST"
								, data: {
									qaId: qaId
									, passwordConfirmAt: ""
								}
								, success: function(data, textStatus, jqXHR) {
									whoyaGlobalData.bForm.setFormData(data);
									// template로 구성된 폼데이터에 서버에서 전송받은 데이터 입력.
									$("#areaNo").val(data.areaNo);
									$("#middleTelno").val(data.middleTelno);
									$("#endTelno").val(data.endTelno);
									$("#emailAdres").val(data.emailAdres);
									if ( data.emailAnswerAt == "Y" ) {
										$("#emailAnswerAt").attr("checked", "checked");
									}
								}
								, error: function(jqXHR, textStatus, errorThrown) {
									console.log(jqXHR);
									console.log(textStatus);
									console.log(errorThrown);
									alert(errorThrown);
								}
							});
						} else if ( passwdData.mode == "delete" ) {
							$.ajax({
								url: whoya.context + "/whoya/uss/olh/qna/QnaCnDelete.do"
								, data: {
									qaId: whoyaGlobalData.bForm.getFormData().qaId
								}
								, success: function(data, textStatus, jqXHR) {
									if ( data.status == "SUCCESS" ) {
										alert("삭제하였습니다.");
										search();
										whoyaGlobalData.bCell.attachForm("");
									} else {
										alert("삭제에 실패하였습니다.");
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
					} else {
						alert("작성 비밀번호를 확인 바랍니다.");
					}
				}
				, error: function(jqXHR, textStatus, errorThrown) {
					console.log(jqXHR);
					console.log(textStatus);
					console.log(errorThrown);
					alert(errorThrown);
				}
			});
		} else if ( name == "cancleBtn" ) {  // 취소
			whoyaGlobalData.passwdPopupWindows.close();
		}
	});
}
/**
 * #########################################
 */


/**
 * #########################################
 * 보고대상자 목록 Popup
 * #########################################
 */
function reportrPopup() {
	// #########################################
	// ## layout에 windows 생성
	// #########################################
	var reportrPopupWindowsData = {
		layout: whoyaGlobalData.layout
		, id: "reportrPopup"
		, setText: "보고대상자 선택"
	};
	whoyaGlobalData.reportrPopupWindows = whoya.dhtmlx.layout.windows(reportrPopupWindowsData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 레이아웃생성
	// #########################################
	var reportrPopupLayoutData = {
		layout_target: whoyaGlobalData.reportrPopupWindows
		, layout_Pattern: "1C"
	};
	whoyaGlobalData.reportrPopupLayout = whoya.dhtmlx.layout.init(reportrPopupLayoutData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 툴바 생성
	// #########################################
	var reportrPopupToolbarData = {
		layout: whoyaGlobalData.reportrPopupLayout
	};
	whoyaGlobalData.reportrPopupToolbar = whoya.dhtmlx.layout.toolbar.init(reportrPopupToolbarData);
	whoyaGlobalData.reportrPopupToolbar.addText("lbl_searchCnd", 1, "조회조건");
	whoyaGlobalData.reportrPopupToolbar.addText("searchCnd", 2, "");
	whoyaGlobalData.reportrPopupToolbar.addInput("searchWrd", 3, "", 200);

	// selectBox 생성
	var comboDIV = whoyaGlobalData.reportrPopupToolbar.objPull[whoyaGlobalData.reportrPopupToolbar.idPrefix+"searchCnd"].obj;
	whoyaGlobalData.reportrPopupToolbar.objPull[whoyaGlobalData.reportrPopupToolbar.idPrefix+"searchCnd"].obj.innerHTML = "";
	whoyaGlobalData.reportrPopupCombo = new dhtmlXCombo(comboDIV,"alfa",140);
	whoyaGlobalData.reportrPopupCombo.addOption([
   		["", "--선택하세요--"]
   		, ["0", "부서명"]
   		, ["1", "사원명"]
   	]);
	whoyaGlobalData.reportrPopupCombo.selectOption(0);
	
	// toolbar의 Button정의
	var reportrPopupToolbarAddButton = {
		toolbar: whoyaGlobalData.reportrPopupToolbar
		, btn_Open: true
	};
	whoya.dhtmlx.layout.toolbar.addButton(reportrPopupToolbarAddButton);
	reportrPopupToolbarEvent();
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout cell a 
	// #########################################
	var reportrPopupaCellData = {
		layout: whoyaGlobalData.reportrPopupLayout
	};
	// 팝업창 화면 layout의 해당 cell 정의 
	whoyaGlobalData.reportrPopupaCell = whoya.dhtmlx.layout.cell.init(reportrPopupaCellData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout cell a에 grid생성
	// #########################################
	var reportrPopupaCellGridData = {
		cell: whoyaGlobalData.reportrPopupaCell
		, setHeader: "번호,부서,직위,사번,사원명,선택"
		, setColumnIds: "no,orgnztNm,ofcpsNm,emplNo,emplyrNm,selectLink"
		, setInitWidths: "100,*,100,100,100,100"
		, setColAlign: "center,center,center,center,center,center"
		, setColTypes: "ro,ro,ro,ro,ro,img"
		, enableResizing: "true,true,true,,true,true,true"
		, enableTooltips: "false,false,false,false,false,false"
		, setColSorting: "str,str,str,str,str,str"
	};
	// 팝업창 화면 layout cell a에 dhtmlxGrid 객체 생성.
	whoyaGlobalData.reportrPopupaGrid = whoya.dhtmlx.layout.cell.grid(reportrPopupaCellGridData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout에 statusbar 생성
	// #########################################
	var reportrPopupStatusbarData = {
		layout: whoyaGlobalData.reportrPopupLayout
		, id: "reportrPopup"
	};
	whoyaGlobalData.reportrPopupStatusbar = whoya.dhtmlx.statusbar(reportrPopupStatusbarData);
	// #########################################
}

// reportrPopup toolbar event 생성
function reportrPopupToolbarEvent() {
	whoyaGlobalData.reportrPopupToolbar.attachEvent("onClick", function(id) {
		if(id == "btn_Open"){
			reportrPopupSearch();
		}
    });
}

// 조회(reportrPopup용)
function reportrPopupSearch() {
	whoyaGlobalData.reportrPopupLayout.progressOn();
	whoyaGlobalData.reportrPopupaGrid.clearAll();
	document.getElementById("reportrPopupactiveStatusBar").innerHTML = "";
	$.ajax({
		url: whoya.context + "/whoya/cop/smt/wmr/selectReportrList.do"
		, type: "POST"
		, data: {
			searchCnd : whoyaGlobalData.reportrPopupCombo.getSelectedValue()
			, searchWrd : whoyaGlobalData.reportrPopupToolbar.getValue("searchWrd")
		}
		, success: function(data, textStatus, jqXHR) {
			whoyaGlobalData.reportrPopupaGrid.attachEvent("onXLE", function(){
				whoyaGlobalData.reportrPopupLayout.progressOff();
    		});
    	  	
			whoyaGlobalData.reportrPopupaGrid.clearAll();
			whoyaGlobalData.reportrPopupaGrid.parse(data.list, "json");
			whoyaGlobalData.reportrPopupaGrid.setSelectedRow(0);
    		document.getElementById("reportrPopupactiveStatusBar").innerHTML = "조회되었습니다";
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
 * #########################################
 */


/**
 * #########################################
 * 템플릿 정보 목록 Popup
 * #########################################
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
		, btn_Open: true
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

// tmplatPopup toolbar event 생성
function tmplatPopupToolbarEvent() {
	whoyaGlobalData.tmplatPopupToolbar.attachEvent("onClick", function(id) {
		if(id == "btn_Open"){
			tmplatPopupSearch();
		}
    });
}

// 조회(tmplatPopup용)
function tmplatPopupSearch() {
	whoyaGlobalData.tmplatPopupLayout.progressOn();
	whoyaGlobalData.tmplatPopupaGrid.clearAll();
	document.getElementById("tmplatPopupactiveStatusBar").innerHTML = "";
	$.ajax({
		url: whoya.context + "/whoya/cop/tpl/selectTemplateInfsPop.do"
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
 * #########################################
 */


/**
 * #########################################
 * 커뮤니티 관리자 목록 Popup
 * #########################################
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
		, btn_Open: true
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

// emplyrPopup toolbar event 생성
function emplyrPopupToolbarEvent() {
	whoyaGlobalData.emplyrPopupToolbar.attachEvent("onClick", function(id) {
		if(id == "btn_Open"){
			emplyrPopupSearch();
		}
    });
}

// 조회(emplyrPopup용)
function emplyrPopupSearch() {
	whoyaGlobalData.emplyrPopupLayout.progressOn();
	whoyaGlobalData.emplyrPopupaGrid.clearAll();
	document.getElementById("emplyrPopupactiveStatusBar").innerHTML = "";
	$.ajax({
		url: whoya.context + "/whoya/cop/com/selectUserList.do"
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
 * #########################################
 */


/**
 * #########################################
 * 커뮤니티 정보조회 Popup
 * #########################################
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
		url: whoya.context + "/whoya/cop/cmy/selectCmmntyInf.do"
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

// cmmntyPopup form event 생성
function cmmntyPopupFormEvent(cmmntyId) {
	whoyaGlobalData.cmmntyPopupaForm.attachEvent("onButtonClick", function(name) {
		if ( name == "uptBtn" ) {
			// 화면 layout cell b에 dhtmlxForm 객체 생성.
			whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellUpdateFormData);
			formEvent();
			$.ajax({
				url: whoya.context + "/whoya/cop/cmy/forUpdateCmmntyInf.do"
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

// 조회(cmmntyPopup용)
function cmmntyPopupSearch(cmmntyId) {
	whoyaGlobalData.cmmntyPopupLayout.progressOn();
	whoyaGlobalData.cmmntyPopupbGrid.clearAll();
	document.getElementById("cmmntyPopupactiveStatusBar").innerHTML = "";
	$.ajax({
		url: whoya.context + "/whoya/cop/clb/selectClubInfByCmmntyId.do"
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
/**
 * #########################################
 */


/**
 * #########################################
 * 게시판 정보 목록 Popup
 * #########################################
 */
function boardPopup() {
 	// #########################################
 	// ## layout에 windows 생성
 	// #########################################
	var boardPopupWindowsData = {
 		layout: whoyaGlobalData.layout
 		, id: "boardPopup"
 		, setText: "게시판 정보"
 	};
 	whoyaGlobalData.boardPopupWindows = whoya.dhtmlx.layout.windows(boardPopupWindowsData);
 	// #########################################
 	
 	
 	// #########################################
 	// ## 팝업창 레이아웃생성
 	// #########################################
 	var boardPopupLayoutData = {
 		layout_target: whoyaGlobalData.boardPopupWindows
 		, layout_Pattern: "1C"
 	};
 	whoyaGlobalData.boardPopupLayout = whoya.dhtmlx.layout.init(boardPopupLayoutData);
 	// #########################################
 	
 	
 	// #########################################
 	// ## 팝업창 툴바 생성
 	// #########################################
 	var boardPopupToolbarData = {
 		layout: whoyaGlobalData.boardPopupLayout
 	};
 	whoyaGlobalData.boardPopupToolbar = whoya.dhtmlx.layout.toolbar.init(boardPopupToolbarData);
 	whoyaGlobalData.boardPopupToolbar.addText("searchCnd", 1, "");
 	whoyaGlobalData.boardPopupToolbar.addInput("searchWrd", 2, "", 200);

 	// selectBox 생성
 	var comboDIV = whoyaGlobalData.boardPopupToolbar.objPull[whoyaGlobalData.boardPopupToolbar.idPrefix+"searchCnd"].obj;
 	whoyaGlobalData.boardPopupToolbar.objPull[whoyaGlobalData.boardPopupToolbar.idPrefix+"searchCnd"].obj.innerHTML = "";
 	whoyaGlobalData.boardPopupCombo = new dhtmlXCombo(comboDIV,"alfa",140);
 	whoyaGlobalData.boardPopupCombo.addOption([
		["0", "게시판명"]
		, ["1", "게시판유형"]
	]);
 	whoyaGlobalData.boardPopupCombo.selectOption(0);
 	
 	// toolbar의 Button정의
 	var boardPopupToolbarAddButton = {
 		toolbar: whoyaGlobalData.boardPopupToolbar
 		, btn_Open: true
 	};
 	whoya.dhtmlx.layout.toolbar.addButton(boardPopupToolbarAddButton);
 	boardPopupToolbarEvent();
 	// #########################################
 	
 	
 	// #########################################
 	// ## 팝업창 layout cell a 
 	// #########################################
 	var boardPopupaCellData = {
 		layout: whoyaGlobalData.boardPopupLayout
 	};
 	// 팝업창 화면 layout의 해당 cell 정의 
 	whoyaGlobalData.boardPopupaCell = whoya.dhtmlx.layout.cell.init(boardPopupaCellData);
 	// #########################################
 	
 	
 	// #########################################
 	// ## 팝업창 layout cell a에 grid생성
 	// #########################################
 	var boardPopupaCellGridData = {
 		cell: whoyaGlobalData.boardPopupaCell
 		, setHeader: "번호,게시판명,게시판유형,게시판속성,생성일,사용여부,선택"
 		, setColumnIds: "no,bbsNm,bbsTyCodeNm,bbsAttrbCodeNm,frstRegisterPnttm,useAt,selectLink"
 		, setInitWidths: "100,*,100,100,100,100,100"
 		, setColAlign: "center,center,center,center,center,center,center"
 		, setColTypes: "ro,ro,ro,ro,ro,ro,img"
 		, enableResizing: "true,true,true,true,true,true,true"
 		, enableTooltips: "false,false,false,false,false,false,false"
 		, setColSorting: "str,str,str,str,str,str,str"
 	};
 	// 팝업창 화면 layout cell a에 dhtmlxGrid 객체 생성.
 	whoyaGlobalData.boardPopupaGrid = whoya.dhtmlx.layout.cell.grid(boardPopupaCellGridData);
 	// #########################################
 	
 	
 	// #########################################
 	// ## 팝업창 layout에 statusbar 생성
 	// #########################################
 	var boardPopupStatusbarData = {
 		layout: whoyaGlobalData.boardPopupLayout
 		, id: "boardPopup"
 	};
 	whoyaGlobalData.boardPopupStatusbar = whoya.dhtmlx.statusbar(boardPopupStatusbarData);
 	// #########################################
}

// boardPopup toolbar event 생성
function boardPopupToolbarEvent() {
	whoyaGlobalData.boardPopupToolbar.attachEvent("onClick", function(id) {
		if(id == "btn_Open"){
			boardPopupSearch();
		}
    });
}

// 조회(boardPopup popup용)
function boardPopupSearch() {
	whoyaGlobalData.boardPopupLayout.progressOn();
	whoyaGlobalData.boardPopupaGrid.clearAll();
	document.getElementById("boardPopupactiveStatusBar").innerHTML = "";

	$.ajax({
		url: whoya.context + "/whoya/cop/bbs/SelectBBSMasterInfsPop.do"
		, data: {
			searchCnd : whoyaGlobalData.boardPopupCombo.getSelectedValue()
			, searchWrd : whoyaGlobalData.boardPopupToolbar.getValue("searchWrd")
		}
		, dataType: "json"
		, success: function(data, textStatus, jqXHR) {
			whoyaGlobalData.boardPopupaGrid.attachEvent("onXLE", function(){
				whoyaGlobalData.boardPopupLayout.progressOff();
    		});
    	  	
    	  	whoyaGlobalData.boardPopupaGrid.clearAll();
    	  	whoyaGlobalData.boardPopupaGrid.parse(data.list, "json");
    	  	whoyaGlobalData.boardPopupaGrid.setSelectedRow(0);
    		document.getElementById("boardPopupactiveStatusBar").innerHTML = "조회되었습니다";
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
 * #########################################
 */


/**
 * #########################################
 * 커뮤니티 정보 목록 Popup
 * #########################################
 */
function communityPopup() {
 	// #########################################
 	// ## layout에 windows 생성
 	// #########################################
	var communityPopupWindowsData = {
 		layout: whoyaGlobalData.layout
 		, id: "communityPopup"
 		, setText: "커뮤니티 정보"
 	};
 	whoyaGlobalData.communityPopupWindows = whoya.dhtmlx.layout.windows(communityPopupWindowsData);
 	// #########################################
 	
 	
 	// #########################################
 	// ## 팝업창 레이아웃생성
 	// #########################################
 	var communityPopupLayoutData = {
 		layout_target: whoyaGlobalData.communityPopupWindows
 		, layout_Pattern: "1C"
 	};
 	whoyaGlobalData.communityPopupLayout = whoya.dhtmlx.layout.init(communityPopupLayoutData);
 	// #########################################
 	
 	
 	// #########################################
 	// ## 팝업창 툴바 생성
 	// #########################################
 	var communityPopupToolbarData = {
 		layout: whoyaGlobalData.communityPopupLayout
 	};
 	whoyaGlobalData.communityPopupToolbar = whoya.dhtmlx.layout.toolbar.init(communityPopupToolbarData);
 	whoyaGlobalData.communityPopupToolbar.addText("searchCnd", 1, "");
 	whoyaGlobalData.communityPopupToolbar.addInput("searchWrd", 2, "", 200);

 	// selectBox 생성
 	var comboDIV = whoyaGlobalData.communityPopupToolbar.objPull[whoyaGlobalData.communityPopupToolbar.idPrefix+"searchCnd"].obj;
 	whoyaGlobalData.communityPopupToolbar.objPull[whoyaGlobalData.communityPopupToolbar.idPrefix+"searchCnd"].obj.innerHTML = "";
 	whoyaGlobalData.communityPopupCombo = new dhtmlXCombo(comboDIV,"alfa",140);
 	whoyaGlobalData.communityPopupCombo.addOption([
		["0", "커뮤니티명"]
	]);
 	whoyaGlobalData.communityPopupCombo.selectOption(0);
 	
 	// toolbar의 Button정의
 	var communityPopupToolbarAddButton = {
 		toolbar: whoyaGlobalData.communityPopupToolbar
 		, btn_Open: true
 	};
 	whoya.dhtmlx.layout.toolbar.addButton(communityPopupToolbarAddButton);
 	communityPopupToolbarEvent();
 	// #########################################
 	
 	
 	// #########################################
 	// ## 팝업창 layout cell a 
 	// #########################################
 	var communityPopupaCellData = {
 		layout: whoyaGlobalData.communityPopupLayout
 	};
 	// 팝업창 화면 layout의 해당 cell 정의 
 	whoyaGlobalData.communityPopupaCell = whoya.dhtmlx.layout.cell.init(communityPopupaCellData);
 	// #########################################
 	
 	
 	// #########################################
 	// ## 팝업창 layout cell a에 grid생성
 	// #########################################
 	var communityPopupaCellGridData = {
 		cell: whoyaGlobalData.communityPopupaCell
 		, setHeader: "번호,커뮤니티명,생성자,생성일,사용여부,선택"
 		, setColumnIds: "no,cmmntyNm,frstRegisterNm,frstRegisterPnttm,useAt,selectLink"
 		, setInitWidths: "100,*,100,100,100,100"
 		, setColAlign: "center,center,center,center,center,center"
 		, setColTypes: "ro,ro,ro,ro,ro,img"
 		, enableResizing: "true,true,true,true,true,true"
 		, enableTooltips: "false,false,false,false,false,false"
 		, setColSorting: "str,str,str,str,str,str"
 	};
 	// 팝업창 화면 layout cell a에 dhtmlxGrid 객체 생성.
 	whoyaGlobalData.communityPopupaGrid = whoya.dhtmlx.layout.cell.grid(communityPopupaCellGridData);
 	// #########################################
 	
 	
 	// #########################################
 	// ## 팝업창 layout에 statusbar 생성
 	// #########################################
 	var communityPopupStatusbarData = {
 		layout: whoyaGlobalData.communityPopupLayout
 		, id: "communityPopup"
 	};
 	whoyaGlobalData.communityPopupStatusbar = whoya.dhtmlx.statusbar(communityPopupStatusbarData);
 	// #########################################
}

// communityPopup toolbar event 생성
function communityPopupToolbarEvent() {
	whoyaGlobalData.communityPopupToolbar.attachEvent("onClick", function(id) {
		if(id == "btn_Open"){
			communityPopupSearch();
		}
    });
}

// 조회(communityPopup popup용)
function communityPopupSearch() {
	whoyaGlobalData.communityPopupLayout.progressOn();
	whoyaGlobalData.communityPopupaGrid.clearAll();
	document.getElementById("communityPopupactiveStatusBar").innerHTML = "";

	$.ajax({
		url: whoya.context + "/whoya/cop/cmy/selectCmmntyInfsByPop.do"
		, data: {
			searchCnd : whoyaGlobalData.communityPopupCombo.getSelectedValue()
			, searchWrd : whoyaGlobalData.communityPopupToolbar.getValue("searchWrd")
		}
		, dataType: "json"
		, success: function(data, textStatus, jqXHR) {
			whoyaGlobalData.communityPopupaGrid.attachEvent("onXLE", function(){
				whoyaGlobalData.communityPopupLayout.progressOff();
    		});
    	  	
    	  	whoyaGlobalData.communityPopupaGrid.clearAll();
    	  	whoyaGlobalData.communityPopupaGrid.parse(data.list, "json");
    	  	whoyaGlobalData.communityPopupaGrid.setSelectedRow(0);
    		document.getElementById("communityPopupactiveStatusBar").innerHTML = "조회되었습니다";
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
 * #########################################
 */


/**
 * #########################################
 * 동호회 정보 목록 Popup
 * #########################################
 */
function clubPopup() {
 	// #########################################
 	// ## layout에 windows 생성
 	// #########################################
	var clubPopupWindowsData = {
 		layout: whoyaGlobalData.layout
 		, id: "clubPopup"
 		, setText: "동호회 정보"
 	};
 	whoyaGlobalData.clubPopupWindows = whoya.dhtmlx.layout.windows(clubPopupWindowsData);
 	// #########################################
 	
 	
 	// #########################################
 	// ## 팝업창 레이아웃생성
 	// #########################################
 	var clubPopupLayoutData = {
 		layout_target: whoyaGlobalData.clubPopupWindows
 		, layout_Pattern: "1C"
 	};
 	whoyaGlobalData.clubPopupLayout = whoya.dhtmlx.layout.init(clubPopupLayoutData);
 	// #########################################
 	
 	
 	// #########################################
 	// ## 팝업창 툴바 생성
 	// #########################################
 	var clubPopupToolbarData = {
 		layout: whoyaGlobalData.clubPopupLayout
 	};
 	whoyaGlobalData.clubPopupToolbar = whoya.dhtmlx.layout.toolbar.init(clubPopupToolbarData);
 	whoyaGlobalData.clubPopupToolbar.addText("searchCnd", 1, "");
 	whoyaGlobalData.clubPopupToolbar.addInput("searchWrd", 2, "", 200);

 	// selectBox 생성
 	var comboDIV = whoyaGlobalData.clubPopupToolbar.objPull[whoyaGlobalData.clubPopupToolbar.idPrefix+"searchCnd"].obj;
 	whoyaGlobalData.clubPopupToolbar.objPull[whoyaGlobalData.clubPopupToolbar.idPrefix+"searchCnd"].obj.innerHTML = "";
 	whoyaGlobalData.clubPopupCombo = new dhtmlXCombo(comboDIV,"alfa",140);
 	whoyaGlobalData.clubPopupCombo.addOption([
		["0", "동호회명"]
		, ["1", "커뮤니티명"]
	]);
 	whoyaGlobalData.clubPopupCombo.selectOption(0);
 	
 	// toolbar의 Button정의
 	var clubPopupToolbarAddButton = {
 		toolbar: whoyaGlobalData.clubPopupToolbar
 		, btn_Open: true
 	};
 	whoya.dhtmlx.layout.toolbar.addButton(clubPopupToolbarAddButton);
 	clubPopupToolbarEvent();
 	// #########################################
 	
 	
 	// #########################################
 	// ## 팝업창 layout cell a 
 	// #########################################
 	var clubPopupaCellData = {
 		layout: whoyaGlobalData.clubPopupLayout
 	};
 	// 팝업창 화면 layout의 해당 cell 정의 
 	whoyaGlobalData.clubPopupaCell = whoya.dhtmlx.layout.cell.init(clubPopupaCellData);
 	// #########################################
 	
 	
 	// #########################################
 	// ## 팝업창 layout cell a에 grid생성
 	// #########################################
 	var clubPopupaCellGridData = {
 		cell: whoyaGlobalData.clubPopupaCell
 		, setHeader: "번호,동호회명,커뮤니티명,등록일,선택"
 		, setColumnIds: "no,clbNm,cmmntyNm,bbsAttrbCodeNm,frstRegisterPnttm,selectLink"
 		, setInitWidths: "100,*,100,100,100"
 		, setColAlign: "center,center,center,center,center"
 		, setColTypes: "ro,ro,ro,ro,img"
 		, enableResizing: "true,true,true,true,true"
 		, enableTooltips: "false,false,false,false,false"
 		, setColSorting: "str,str,str,str,str"
 	};
 	// 팝업창 화면 layout cell a에 dhtmlxGrid 객체 생성.
 	whoyaGlobalData.clubPopupaGrid = whoya.dhtmlx.layout.cell.grid(clubPopupaCellGridData);
 	// #########################################
 	
 	
 	// #########################################
 	// ## 팝업창 layout에 statusbar 생성
 	// #########################################
 	var clubPopupStatusbarData = {
 		layout: whoyaGlobalData.clubPopupLayout
 		, id: "clubPopup"
 	};
 	whoyaGlobalData.clubPopupStatusbar = whoya.dhtmlx.statusbar(clubPopupStatusbarData);
 	// #########################################
}

// clubPopup toolbar event 생성
function clubPopupToolbarEvent() {
	whoyaGlobalData.clubPopupToolbar.attachEvent("onClick", function(id) {
		if(id == "btn_Open"){
			clubPopupSearch();
		}
    });
}

// 조회(clubPopup popup용)
function clubPopupSearch() {
	whoyaGlobalData.clubPopupLayout.progressOn();
	whoyaGlobalData.clubPopupaGrid.clearAll();
	document.getElementById("clubPopupactiveStatusBar").innerHTML = "";

	$.ajax({
		url: whoya.context + "/whoya/cop/clb/selectClubInfsByPop.do"
		, data: {
			searchCnd : whoyaGlobalData.clubPopupCombo.getSelectedValue()
			, searchWrd : whoyaGlobalData.clubPopupToolbar.getValue("searchWrd")
		}
		, dataType: "json"
		, success: function(data, textStatus, jqXHR) {
			whoyaGlobalData.clubPopupaGrid.attachEvent("onXLE", function(){
				whoyaGlobalData.clubPopupLayout.progressOff();
    		});
    	  	
    	  	whoyaGlobalData.clubPopupaGrid.clearAll();
    	  	whoyaGlobalData.clubPopupaGrid.parse(data.list, "json");
    	  	whoyaGlobalData.clubPopupaGrid.setSelectedRow(0);
    		document.getElementById("clubPopupactiveStatusBar").innerHTML = "조회되었습니다";
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
 * #########################################
 */


/**
 * #########################################
 * 설문관리 목록 Popup
 * #########################################
 */
function qestnrPopup() {
	// #########################################
	// ## layout에 windows 생성
	// #########################################
	var qestnrPopupWindowsData = {
			layout: whoyaGlobalData.layout
			, id: "qestnrPopup"
				, setText: "설문관리 정보"
	};
	whoyaGlobalData.qestnrPopupWindows = whoya.dhtmlx.layout.windows(qestnrPopupWindowsData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 레이아웃생성
	// #########################################
	var qestnrPopupLayoutData = {
			layout_target: whoyaGlobalData.qestnrPopupWindows
			, layout_Pattern: "1C"
	};
	whoyaGlobalData.qestnrPopupLayout = whoya.dhtmlx.layout.init(qestnrPopupLayoutData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 툴바 생성
	// #########################################
	var qestnrPopupToolbarData = {
			layout: whoyaGlobalData.qestnrPopupLayout
	};
	whoyaGlobalData.qestnrPopupToolbar = whoya.dhtmlx.layout.toolbar.init(qestnrPopupToolbarData);
	whoyaGlobalData.qestnrPopupToolbar.addText("searchCondition", 1, "");
	whoyaGlobalData.qestnrPopupToolbar.addInput("searchKeyword", 2, "", 200);
	
	// selectBox 생성
	var comboDIV = whoyaGlobalData.qestnrPopupToolbar.objPull[whoyaGlobalData.qestnrPopupToolbar.idPrefix+"searchCondition"].obj;
	whoyaGlobalData.qestnrPopupToolbar.objPull[whoyaGlobalData.qestnrPopupToolbar.idPrefix+"searchCondition"].obj.innerHTML = "";
	whoyaGlobalData.qestnrPopupCombo = new dhtmlXCombo(comboDIV,"alfa",140);
	whoyaGlobalData.qestnrPopupCombo.addOption([
	    ["", "--선택하세요--"]
        , ["QESTNR_TMPLAT_CN", "템플릿설명"]
        , ["QESTNR_TMPLAT_TY", "템플릿유형"]
    ]);
	whoyaGlobalData.qestnrPopupCombo.selectOption(0);
	
	// toolbar의 Button정의
	var qestnrPopupToolbarAddButton = {
			toolbar: whoyaGlobalData.qestnrPopupToolbar
			, btn_Open: true
	};
	whoya.dhtmlx.layout.toolbar.addButton(qestnrPopupToolbarAddButton);
	qestnrPopupToolbarEvent();
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout cell a 
	// #########################################
	var qestnrPopupaCellData = {
			layout: whoyaGlobalData.qestnrPopupLayout
	};
	// 팝업창 화면 layout의 해당 cell 정의 
	whoyaGlobalData.qestnrPopupaCell = whoya.dhtmlx.layout.cell.init(qestnrPopupaCellData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout cell a에 grid생성
	// #########################################
	var qestnrPopupaCellGridData = {
			cell: whoyaGlobalData.qestnrPopupaCell
			, setHeader: "번호,설문제목,설문기간,등록자,등록일자,선택"
			, setColumnIds: "no,qestnrSj,qestnrDe,frstRegisterNm,frstRegisterPnttm,selectLink"
			, setInitWidths: "100,*,100,100,100,100"
			, setColAlign: "center,center,center,center,center,center"
			, setColTypes: "ro,ro,ro,ro,ro,img"
			, enableResizing: "true,true,true,true,true,true"
			, enableTooltips: "false,false,false,false,false,false"
			, setColSorting: "str,str,str,str,str,str"
	};
	// 팝업창 화면 layout cell a에 dhtmlxGrid 객체 생성.
	whoyaGlobalData.qestnrPopupaGrid = whoya.dhtmlx.layout.cell.grid(qestnrPopupaCellGridData);
	// #########################################
	
	
	// #########################################
	// ## 팝업창 layout에 statusbar 생성
	// #########################################
	var qestnrPopupStatusbarData = {
			layout: whoyaGlobalData.qestnrPopupLayout
			, id: "qestnrPopup"
	};
	whoyaGlobalData.qestnrPopupStatusbar = whoya.dhtmlx.statusbar(qestnrPopupStatusbarData);
	// #########################################
}

// clubPopup toolbar event 생성
function qestnrPopupToolbarEvent() {
	whoyaGlobalData.qestnrPopupToolbar.attachEvent("onClick", function(id) {
		if(id == "btn_Open"){
			qestnrPopupSearch();
		}
	});
}

// 조회(qestnrPopup popup용)
function qestnrPopupSearch() {
	whoyaGlobalData.qestnrPopupLayout.progressOn();
	whoyaGlobalData.qestnrPopupaGrid.clearAll();
	document.getElementById("qestnrPopupactiveStatusBar").innerHTML = "";
	
	$.ajax({
		url: whoya.context + "/whoya/uss/olp/qmc/EgovQustnrManageListPopup.do"
		, data: {
			searchCondition : whoyaGlobalData.qestnrPopupCombo.getSelectedValue()
			, searchKeyword : whoyaGlobalData.qestnrPopupToolbar.getValue("searchKeyword")
		}
	, dataType: "json"
		, success: function(data, textStatus, jqXHR) {
			whoyaGlobalData.qestnrPopupaGrid.attachEvent("onXLE", function(){
				whoyaGlobalData.qestnrPopupLayout.progressOff();
			});
			
			whoyaGlobalData.qestnrPopupaGrid.clearAll();
			whoyaGlobalData.qestnrPopupaGrid.parse(data.list, "json");
			whoyaGlobalData.qestnrPopupaGrid.setSelectedRow(0);
			document.getElementById("qestnrPopupactiveStatusBar").innerHTML = "조회되었습니다";
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
 * #########################################
 */