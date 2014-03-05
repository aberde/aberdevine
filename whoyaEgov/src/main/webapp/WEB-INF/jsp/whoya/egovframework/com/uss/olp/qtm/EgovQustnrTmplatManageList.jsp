<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>설문템플릿관리</title>
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
	whoyaGlobalData.toolbar.addText("searchCondition", 1, "");
	whoyaGlobalData.toolbar.addInput("searchKeyword", 2, "", 200);

	// selectBox 생성
	var comboDIV = whoyaGlobalData.toolbar.objPull[whoyaGlobalData.toolbar.idPrefix+"searchCondition"].obj;
	whoyaGlobalData.toolbar.objPull[whoyaGlobalData.toolbar.idPrefix+"searchCondition"].obj.innerHTML = "";
	whoyaGlobalData.combo = new dhtmlXCombo(comboDIV,"alfa",140);
	whoyaGlobalData.combo.addOption([
   		["", "선택하세요"]
   		, ["QUSTNR_TMPLAT_DC", "템플릿설명"]
   		, ["QUSTNR_TMPLAT_TY", "템플릿유형"]
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
		, setHeader: "순번,템플릿유형,템플릿유형이미지정보,템플릿설명,작성자명,등록일자,설문템플릿 아이디"
		, setColumnIds: "no,qestnrTmplatTy,tmplatImg,qestnrTmplatCn,frstRegisterNm,frstRegisterPnttm,qestnrTmplatId"
		, setInitWidths: "100,100,150,*,100,100,100"
		, setColAlign: "center,center,center,center,center,center,center"
		, setColTypes: "ro,ro,img,ro,ro,ro,ro"
		, enableResizing: "false,false,false,false,false,false,false"
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
   			{ type: "fieldset", name: "formField", label: "설문템플릿 등록", list: [
   				{ type: "settings", labelWidth: 150, inputWidth: 170 },
   				{ type: "input", label: "템플릿유형", name: "qestnrTmplatTy", value: "" },
   				{ type: "file", label: "템플릿유형이미지", name: "qestnrTmplatImage", value: "" },
   				{ type: "input", label: "템플릿설명", name: "qestnrTmplatCn", rows: 3 },
   				{ type: "input", label: "템플릿파일(경로)", name: "qestnrTmplatCours", value: "" },
   				{ type: "button", name: "regBtn", value: "등록" }
   			] }
   		]
	};

	// 상세폼.
	whoyaGlobalData.bCellDetailFormData = {
		cell: whoyaGlobalData.bCell
		, formData: [
  			{ type: "fieldset", name: "formField", label: "설문템플릿 상세보기", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "template", label: "템플릿유형", name: "qestnrTmplatTy", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "템플릿유형이미지", name: "qestnrTmplatId", format: whoya.dhtmlx.form.format.qustnrTmplat },
				{ type: "template", label: "템플릿설명", name: "qestnrTmplatCn", format: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "템플릿파일(경로)", name: "qestnrTmplatCours", format: whoya.dhtmlx.form.format.printData },
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
 			{ type: "fieldset", name: "formField", label: "설문템플릿 수정", list: [
 				{ type: "settings", labelWidth: 150, inputWidth: 170 },
 				{ type: "input", label: "템플릿유형", name: "qestnrTmplatTy", value: "" },
 				{ type: "template", label: "템플릿유형이미지", list: [
	 				{ type: "settings", labelWidth: 50, inputWidth: 70 },
					{ type: "label", labelWidth: 125 },
					{ type: "newcolumn" },
	 				{ type: "file", name: "qestnrTmplatImage", value: "", inputWidth: 70 },
					{ type: "template", name: "qestnrTmplatId", format: whoya.dhtmlx.form.format.qustnrTmplat }
				] },
 				{ type: "input", label: "템플릿설명", name: "qestnrTmplatCn", rows: 3 },
 				{ type: "input", label: "템플릿파일(경로)", name: "qestnrTmplatCours", value: "" },
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
			search();
			whoyaGlobalData.bCell.attachForm("");
		}
		if(id == "btn_Append"){
			// 화면 layout cell b에 dhtmlxForm 객체 생성.
			whoyaGlobalData.bCell.attachForm("");
			whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellRegFormData);
			formEvent();
			
			// 등록화면진입시 로그인정보의 작성자명과 이메일정보 가져와 출력.
			$.ajax({
				url: "<c:url value='/whoya/uss/olh/qna/QnaCnRegistView.do' />"
				, type: "POST"
				, success: function(data, textStatus, jqXHR) {
					whoyaGlobalData.bForm.setItemValue("wrterNm", data.result.wrterNm);
					$("#emailAdres").val(data.result.emailAdres);
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
		whoyaGlobalData.bCell.attachForm("");
		// 화면 layout cell b에 dhtmlxForm 객체 생성.
		whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellDetailFormData);
		formEvent();
		
		$.ajax({
			url: "<c:url value='/whoya/uss/olp/qtm/EgovQustnrTmplatManageDetail.do' />"
			, type: "POST"
			, data: {
				qestnrTmplatId: whoyaGlobalData.aGrid.cells(id, 6).getValue()
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
				
				$.ajaxFileUpload({
					url: "<c:url value='/whoya/uss/olp/qtm/EgovQustnrTmplatManageRegistActor.do' />"
					, secureuri: false
					, fileElementId: ["qestnrTmplatImage"]
					, data: whoyaGlobalData.bForm.getFormData()
					, dataType: 'json'
					, success: function (data, status) {
						if ( data.status == "SUCCESS" ) {
							alert("저장하였습니다.");
							search();
							whoyaGlobalData.bCell.attachForm("");
						} else {
							alert("저장에 실패하였습니다.");
						}
					}
					, error: function (data, status, e) {
						alert(e);
					}
				});
			}
		} else if ( name == "uptViewBtn" ) {  // 수정화면이동.
			$.ajax({
				url: "<c:url value='/whoya/uss/olp/qtm/EgovQustnrTmplatManageDetail.do' />"
				, type: "POST"
				, data: {
					qestnrTmplatId: whoyaGlobalData.bForm.getFormData().qestnrTmplatId
				}
				, success: function(data, textStatus, jqXHR) {
					// 화면 layout cell b에 dhtmlxForm 객체 생성.
					whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellUpdateFormData);
					formEvent();
					whoyaGlobalData.bForm.setFormData(data);
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
				var data = whoyaGlobalData.bForm.getFormData();
				// 수정파일 존재여부 판단하는 qestnrTmplatImagepathnm 변수 삭제  
				delete data.qestnrTmplatImagepathnm;
				
				$.ajaxFileUpload({
					url: "<c:url value='/whoya/uss/olp/qtm/EgovQustnrTmplatManageModifyActor.do' />"
					, secureuri: false
					, fileElementId: ["qestnrTmplatImage"]
					, data: data
					, dataType: 'json'
					, success: function (data, status) {
						if ( data.status == "SUCCESS" ) {
							alert("저장하였습니다.");
							search();
							whoyaGlobalData.bCell.attachForm("");
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
					url: "<c:url value='/whoya/uss/olp/qtm/EgovQustnrTmplatManageDeleteActor.do' />"
					, type: "POST"
					, data: {
						qestnrTmplatId: whoyaGlobalData.bForm.getFormData().qestnrTmplatId
					}
					, success: function(data, textStatus, jqXHR) {
						if ( data.status == "SUCCESS" ) {
							alert("저장하였습니다.");
							search();
							whoyaGlobalData.bCell.attachForm("");
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
		url: "<c:url value='/whoya/uss/olp/qtm/EgovQustnrTmplatManageListJSON.do' />"
		, data: {
			searchCondition : whoyaGlobalData.combo.getSelectedValue()
			, searchKeyword : whoyaGlobalData.toolbar.getValue("searchKeyword")
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


$(function(document) {
	init();
});
</script>
</head>
<body>
</body>
</html>
