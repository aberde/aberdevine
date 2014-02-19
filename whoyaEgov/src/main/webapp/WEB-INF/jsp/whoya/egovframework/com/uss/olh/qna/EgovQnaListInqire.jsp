<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>Q&A관리</title>
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
	whoyaGlobalData.toolbar.addText("searchCondition", 1, "");
	whoyaGlobalData.toolbar.addInput("searchKeyword", 2, "", 200);

	// selectBox 생성
	var comboDIV = whoyaGlobalData.toolbar.objPull[whoyaGlobalData.toolbar.idPrefix+"searchCondition"].obj;
	whoyaGlobalData.toolbar.objPull[whoyaGlobalData.toolbar.idPrefix+"searchCondition"].obj.innerHTML = "";
	whoyaGlobalData.combo = new dhtmlXCombo(comboDIV,"alfa",140);
	whoyaGlobalData.combo.addOption([
   		["", "--선택하세요--"]
   		, ["wrterNm", "작성자명"]
   		, ["qestnSj", "질문제목"]
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
		, setHeader: "순번,질문제목,작성자,진행상태,조회수,작성일자,qaId"
		, setColumnIds: "no,qestnSj,wrterNm,qnaProcessSttusCodeNm,inqireCo,writngDe,qaId"
		, setInitWidths: "100,*,100,100,100,100,100"
		, setColAlign: "center,center,center,center,center,center,center"
		, setColTypes: "ro,ro,ro,ro,ro,ro,ro"
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
   			{ type: "fieldset", name: "formField", label: "Q&A내용등록", list: [
   				{ type: "settings", labelWidth: 150, inputWidth: 170 },
   				{ type: "input", label: "작성자명", name: "wrterNm", value: "" },
   				{ type: "password", label: "작성 비밀번호", name: "writngPassword", value: "" },
   				{ type: "template", label: "전화번호", name: "telForm", format: whoya.dhtmlx.form.format.telInputForm },
   				{ type: "template", label: "이메일", name: "emailForm", format: whoya.dhtmlx.form.format.emailInputForm },
   				{ type: "input", label: "질문제목", name: "qestnSj", value: "" },
   				{ type: "input", label: "질문내용", name: "qestnCn", value: "", rows: 10 },
   				{ type: "button", name: "regBtn", value: "등록" }
   			] }
   		]
	};

	// 상세폼.
	whoyaGlobalData.bCellDetailFormData = {
		cell: whoyaGlobalData.bCell
		, formData: [
   			{ type: "fieldset", name: "formField", label: "Q&A상세조회", list: [
   				{ type: "settings", labelWidth: 150, inputWidth: 170 },
   				{ type: "template", label: "작성자명", name: "wrterNm", value: "", format: whoya.dhtmlx.form.format.printData },
   				{ type: "template", label: "전화번호", name: "telForm", format: whoya.dhtmlx.form.format.telPrintForm },
   				{ type: "template", label: "이메일", name: "emailForm", format: whoya.dhtmlx.form.format.emailPrintForm },
   				{ type: "template", label: "작성일자", name: "writngDe", format: whoya.dhtmlx.form.format.printData },
   				{ type: "template", label: "조회횟수", name: "inqireCo", format: whoya.dhtmlx.form.format.printData },
   				{ type: "template", label: "질의응답처리상태", name: "qnaProcessSttusCodeNm", format: whoya.dhtmlx.form.format.printData },
   				{ type: "template", label: "질문제목", name: "qestnSj", value: "", format: whoya.dhtmlx.form.format.printData },
   				{ type: "input", label: "질문내용", name: "qestnCn", value: "", rows: 10, readonly: true },
   				{ type: "label", list: [
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
   			{ type: "fieldset", name: "formField", label: "FAQ 상세조회", list: [
   				{ type: "settings", labelWidth: 150, inputWidth: 170 },
   				{ type: "input", label: "질문제목", name: "qestnSj", value: "" },
   				{ type: "input", label: "질문내용", name: "qestnCn", value: "", rows: 3 },
   				{ type: "input", label: "답변내용", name: "answerCn", value: "", rows: 3 },
   				{ type: "template", label: "첨부파일 목록", name: "atchFileId", value: "", format: whoya.dhtmlx.form.format.getFileList },
   				{ type: "input", name: "atchFileAt", value: "", hidden: true },
   				{ type: "file", label: "파일첨부", name: "file_1", value: "" },
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
		// 화면 layout cell b에 dhtmlxForm 객체 생성.
		whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellDetailFormData);
		
		$.ajax({
			url: "<c:url value='/whoya/uss/olh/qna/QnaDetailInqire.do' />"
			, type: "POST"
			, data: {
				qaId: whoyaGlobalData.aGrid.cells(id, 6).getValue()
				, passwordConfirmAt: ""
			}
			, success: function(data, textStatus, jqXHR) {
				whoyaGlobalData.bForm.setFormData(data);
				// template로 구성된 폼데이터에 서버에서 전송받은 데이터 입력.
				$("#areaNo").text(data.areaNo);
				$("#middleTelno").text(data.middleTelno);
				$("#endTelno").text(data.endTelno);
				$("#emailAdres").text(data.emailAdres);
				if ( data.emailAnswerAt == "Y" ) {
					$("#emailAnswerAt").attr("checked", "checked");
				}
				formEvent();
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
				// template로 구성된 폼데이터 서버에 전송하기 위해 데이터 입력.
				data.areaNo = $("#areaNo").val();
				data.middleTelno = $("#middleTelno").val();
				data.endTelno = $("#endTelno").val();
				data.emailAdres = $("#emailAdres").val();
				data.emailAnswerAt = $("#emailAnswerAt:checked").val();
				
				$.ajax({
					url: "<c:url value='/whoya/uss/olh/qna/QnaCnRegist.do' />"
					, type: "POST"
					, data: data
					, success: function(data, textStatus, jqXHR) {
						alert("저장하였습니다.");
						search();
						whoyaGlobalData.bCell.attachForm("");
					}
					, error: function(jqXHR, textStatus, errorThrown) {
						console.log(jqXHR);
						console.log(textStatus);
						console.log(errorThrown);
						alert(errorThrown);
					}
				});
			}
		} else if ( name == "uptViewBtn" ) {  // 수정화면이동.
			$.ajax({
				url: "<c:url value='/whoya/uss/olh/faq/FaqListDetailInqire.do' />"
				, data: {
					faqId: whoyaGlobalData.bForm.getFormData().faqId
				}
				, success: function(data, textStatus, jqXHR) {
					// 화면 layout cell b에 dhtmlxForm 객체 생성.
					whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellUpdateFormData);
					
					// atchFileAt(?)
					if ( data.atchFileId ) {
						data.atchFileAt = "Y";
					} else {
						data.atchFileAt = "N";
					}
					
					whoyaGlobalData.bForm.setFormData(data);
					formEvent();
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
				
				$.ajaxFileUpload({
					url: "<c:url value='/whoya/uss/olh/faq/FaqCnUpdt.do' />"
					, secureuri: false
					, fileElementId: ["file_1"]
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
		} else if ( name == "dltBtn" ) {  // 삭제
			if ( confirm("삭제하시겠습니까?") ) {
				document.getElementById("activeStatusBar").innerHTML = "";
				
				$.ajax({
					url: "<c:url value='/whoya/uss/olh/faq/FaqCnDelete.do' />"
					, data: {
						faqId: whoyaGlobalData.bForm.getFormData().faqId
					}
					, success: function(data, textStatus, jqXHR) {
						alert("삭제하였습니다.");
						search();
						whoyaGlobalData.bCell.attachForm("");
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
		url: "<c:url value='/whoya/uss/olh/qna/QnaListInqireJSON.do' />"
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
