<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>설문관리</title>
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
   		["", "--선택하세요--"]
   		, ["QUSTNR_SJ", "설문제목"]
		, ["FRST_REGISTER_ID", "등록자"]
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
		, setHeader: "순번,설문제목,설문기간,설문응답자정보,설문문항,설문조사,통계,등록자,등록일자,설문지ID"
		, setColumnIds: "no,qestnrSj,qestnrDe,qustnrQRM,qustnrQQM,qustnrQRI,qustnrQST,frstRegisterNm,frstRegisterPnttm,qestnrId"
		, setInitWidths: "100,*,*,100,100,100,100,100,100,100"
		, setColAlign: "center,center,center,center,center,center,center,center,center,center"
		, setColTypes: "ro,ro,ro,img,img,img,img,ro,ro,ro"
		, enableResizing: "false,true,false,false,false,false,false,false,false,false"
		, enableTooltips: "false,false,false,false,false,false,false,false,false,false"
		, setColSorting: "str,str,str,str,str,str,str,str,str,str"
		, setColumnHidden: [
			{ id: 9 }
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
			{ type: "fieldset", name: "formField", label: "설문관리 등록", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "input", label: "설문제목", name: "qestnrSj", value: "" },
				{ type: "input", label: "설문목적", name: "qestnrPurps", value: "", rows: 3 },
				{ type: "input", label: "설문작성안내 내용", name: "qestnrWritngGuidanceCn", value: "", rows: 3 },
				{ type: "select", label: "설문대상", name: "qestnrTrget", options: [
					{ value: "", text: "선택" }
				] },
				{ type: "template", label: "설문기간", list: [
					{ type: "settings", labelWidth: 50, inputWidth: 70, position: "label-right" },
					{ type: "label", labelWidth: 130 },
					{ type: "newcolumn" },
					{ type: "calendar", dateFormat: "%Y-%m-%d", name: "qestnrBeginDe" },
					{ type: "newcolumn" },
					{ type: "label", label: "~", labelWidth: 15 },
					{ type: "newcolumn" },
					{ type: "calendar", dateFormat: "%Y-%m-%d", name: "qestnrEndDe" }
				] },
				{ type: "template", label: "템플릿 유형", name: "qestnrTmplatId", format: whoya.dhtmlx.form.format.qustnrTmplatList },
				{ type: "button", name: "regBtn", value: "등록" }
			] }
		]
	};

	// 상세 폼
	whoyaGlobalData.bCellDetailFormData = {
		cell: whoyaGlobalData.bCell
		, formData: [
			{ type: "fieldset", name: "formField", label: "설문관리 상세보기", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "template", label: "설문제목", name: "qestnrSj", foramt: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "설문목적", name: "qestnrPurps", foramt: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "설문작성안내 내용", name: "qestnrWritngGuidanceCn", foramt: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "설문대상", name: "qestnrTrget", foramt: whoya.dhtmlx.form.format.printData },
				{ type: "template", label: "설문기간", list: [
					{ type: "settings", labelWidth: 50, inputWidth: 70 },
					{ type: "label", labelWidth: 130 },
					{ type: "newcolumn" },
					{ type: "template", name: "qestnrBeginDe", foramt: whoya.dhtmlx.form.format.printData },
					{ type: "newcolumn" },
					{ type: "label", label: "~", labelWidth: 15 },
					{ type: "newcolumn" },
					{ type: "template", name: "qestnrEndDe", foramt: whoya.dhtmlx.form.format.printData }
				] },
				{ type: "template", label: "템플릿 유형", name: "qestnrTmplatId", format: whoya.dhtmlx.form.format.qustnrTmplat },
				{ type: "template", list: [
					{ type: "button", name: "uptViewBtn", value: "수정" },
					{ type: "newcolumn" },
					{ type: "button", name: "dltBtn", value: "삭제" }
				] }
			] }
		]
	};
	
	// 수정 폼
	whoyaGlobalData.bCellUpdateFormData = {
		cell: whoyaGlobalData.bCell
		, formData: [
			{ type: "fieldset", name: "formField", label: "설문관리 수정", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
				{ type: "input", label: "설문제목", name: "qestnrSj", value: "" },
				{ type: "input", label: "설문목적", name: "qestnrPurps", value: "", rows: 3 },
				{ type: "input", label: "설문작성안내 내용", name: "qestnrWritngGuidanceCn", value: "", rows: 3 },
				{ type: "select", label: "설문대상", name: "qestnrTrget", options: [
					{ value: "", text: "선택" }
				] },
				{ type: "template", label: "설문기간", list: [
					{ type: "settings", labelWidth: 50, inputWidth: 70, position: "label-right" },
					{ type: "label", labelWidth: 130 },
					{ type: "newcolumn" },
					{ type: "calendar", dateFormat: "%Y-%m-%d", name: "qestnrBeginDe" },
					{ type: "newcolumn" },
					{ type: "label", label: "~", labelWidth: 15 },
					{ type: "newcolumn" },
					{ type: "calendar", dateFormat: "%Y-%m-%d", name: "qestnrEndDe" }
				] },
				{ type: "template", label: "템플릿 유형", name: "qestnrTmplatId", format: whoya.dhtmlx.form.format.qustnrTmplatList },
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
			getQestnrTrget();
			formEvent();
		}
    });
}

// grid event 생성
function gridEvent() {
	whoyaGlobalData.aGrid.attachEvent("onRowSelect", function(id, ind) {
		// 화면 layout cell b에 dhtmlxForm 객체 생성.
		whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellDetailFormData);
		formEvent();
		
		$.ajax({
			url: "<c:url value='/whoya/uss/olp/qmc/EgovQustnrManageDetail.do' />"
			, data: {
				qestnrId: whoyaGlobalData.aGrid.cells(id, 9).getValue()
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
				
				var data = whoyaGlobalData.bForm.getFormData();
				data.qestnrTmplatId = $("input[name='qestnrTmplatId_rdo']:checked").val();
				data.qestnrBeginDe = $("input[name='qestnrBeginDe']").val();
				data.qestnrEndDe = $("input[name='qestnrEndDe']").val();
				
				$.ajax({
					url: "<c:url value='/whoya/uss/olp/qmc/EgovQustnrManageRegist.do' />"
					, data: data
					, type: "POST"
					, dataType: "json"
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
		} else if ( name == "uptViewBtn" ) {  // 수정화면
			$.ajax({
				url: "<c:url value='/whoya/uss/olp/qmc/EgovQustnrManageDetail.do' />"
				, data: {
					qestnrId: whoyaGlobalData.bForm.getFormData().qestnrId
				}
				, success: function(data, textStatus, jqXHR) {
					// 화면 layout cell b에 dhtmlxForm 객체 생성.
					whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellUpdateFormData);
					getQestnrTrget();
					formEvent();
					whoyaGlobalData.bForm.setFormData(data);
					$("select[name='qestnrTrget']").val(data.qestnrTrget);
					$("input[name='qestnrTmplatId_rdo'][value='" + data.qestnrTmplatId + "']").attr("checked", true);
					$("input[name='qestnrTmplatId_rdo']").attr("disabled", true);
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
				data.qestnrTmplatId = $("input[name='qestnrTmplatId_rdo']:checked").val();
				data.qestnrBeginDe = $("input[name='qestnrBeginDe']").val();
				data.qestnrEndDe = $("input[name='qestnrEndDe']").val();
				
				$.ajax({
					url: "<c:url value='/whoya/uss/olp/qmc/EgovQustnrManageModify.do' />"
					, data: data
					, type: "POST"
					, dataType: "json"
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
			if ( confirm("저장하시겠습니까?") ) {
				document.getElementById("activeStatusBar").innerHTML = "";
				
				var data = whoyaGlobalData.bForm.getFormData();
				
				$.ajax({
					url: "<c:url value='/whoya/uss/olp/qmc/EgovQustnrManageDelete.do' />"
					, data: data
					, type: "POST"
					, dataType: "json"
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
		url: "<c:url value='/whoya/uss/olp/qmc/EgovQustnrManageJSONList.do' />"
		, data: {
			searchCondition : whoyaGlobalData.combo.getSelectedValue()
			, searchKeyword : whoyaGlobalData.toolbar.getValue("searchKeyword")
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
 * 설문대상 목록 가져오기.
 */
function getQestnrTrget() {
	$.ajax({
		url: "<c:url value='/whoya/uss/olp/qmc/selectQestnrTrgetList.do' />"
		, async: false
		, dataType: "json"
		, data: {
			codeId: "COM034"
		}
		, success: function(data, textStatus, jqXHR) {
			var qestnrTrget = whoyaGlobalData.bForm.getOptions("qestnrTrget");
			$.each(data, function() {
				qestnrTrget.add(new Option($(this)[0].codeNm, $(this)[0].code));
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
