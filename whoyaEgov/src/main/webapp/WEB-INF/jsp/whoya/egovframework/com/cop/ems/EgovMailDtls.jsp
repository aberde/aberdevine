<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>발송메일내역</title>
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
 *   bCellFormData: bCellFormData  // dhtmlxForm의 UI데이터
 *   dp: dp  // dataProcessor 객체
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
   		, ["1", "제목"]
   		, ["2", "내용"]
   		, ["3", "보낸이"]
   	]);
	whoyaGlobalData.combo.selectOption(0);
	
	// toolbar의 Button정의
	var toolbarAddButton = {
		toolbar: whoyaGlobalData.toolbar
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
		, setHeader: "상태,발신자,수신자,제목,날짜,메세지ID"
		, setColumnIds: "sndngResultCode,dsptchPerson,recptnPerson,sj,sndngDe,mssageId"
		, setInitWidths: "100,100,150,*,100,100"
		, setColAlign: "center,center,center,center,center,center"
		, setColTypes: "ro,ro,ro,ro,ro,ro"
		, enableResizing: "false,true,false,false,false,false"
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
	whoyaGlobalData.bCellFormData = {
		cell: whoyaGlobalData.bCell
		, formData: [
   			{ type: "fieldset", name: "formField", label: "발송메일 상세조회", list: [
   				{ type: "settings", labelWidth: 150, inputWidth: 170 },
   				{ type: "template", label: "보내는사람", name: "dsptchPerson", value: "", format: whoya.dhtmlx.form.format.printData },
   				{ type: "template", label: "받는사람", name: "recptnPerson", value: "", format: whoya.dhtmlx.form.format.printData },
   				{ type: "template", label: "제목", name: "sj", value: "", format: whoya.dhtmlx.form.format.printData },
   				{ type: "template", label: "발신 내용", name: "emailCn", value: "", format: whoya.dhtmlx.form.format.printData },
   				{ type: "template", label: "발송 결과", name: "sndngResultCode", value: "", format: whoya.dhtmlx.form.format.printData },
   				{ type: "template", label: "XML메일보기", name: "xmlMailView", value: "", format: whoya.dhtmlx.form.format.xmlMailViewLink },
   				{ type: "template", label: "첨부파일", name: "atchFileId", value: "", format: whoya.dhtmlx.form.format.getFileList },
   				{ type: "button", name: "dltBtn", value: "삭제" }
   			] }
   		]
	};
	// #########################################
	
	
	// #########################################
	// ## grid 또는 form 등의 객체에서 서버로 저장
	// #########################################
	var dpData = {
		url: "<c:out value='/whoya/cop/ems/deleteSndngMailList.do' />"
		, obj: whoyaGlobalData.aGrid
	};
	whoyaGlobalData.dp = whoya.dhtmlx.dataProcessor(dpData);
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
		}
		if(id == "btn_Append"){
			location.href = "<c:out value='/whoya/cop/ems/insertSndngMailView.do' />";
		}
		if(id == "btn_Delete") {
			if ( confirm("삭제하시겠습니까?") ) {
				
				document.getElementById("activeStatusBar").innerHTML = "";
				whoyaGlobalData.aGrid.deleteSelectedRows();
				whoyaGlobalData.dp.sendData();
			}
		}
    });
}

// grid event 생성
function gridEvent() {
	whoyaGlobalData.aGrid.attachEvent("onRowSelect", function(id, ind) {
		// 화면 layout cell b에 dhtmlxForm 객체 생성.
		whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellFormData);
		
		$.ajax({
			url: "<c:out value='/whoya/cop/ems/selectSndngMailDetail.do' />"
			, data: {
				mssageId: whoyaGlobalData.aGrid.cells(id, 5).getValue()
			}
			, success: function(data, textStatus, jqXHR) {
				// XML메일보기 데이터 입력.
				data.xmlMailView = data.mssageId;
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
	});
}

// form event 생성
function formEvent() {
	whoyaGlobalData.bForm.attachEvent("onButtonClick", function(name) {
		if ( name == "dltBtn" ) {
			if ( confirm("삭제하시겠습니까?") ) {
				document.getElementById("activeStatusBar").innerHTML = "";
				
				$.ajax({
					url: "<c:out value='/whoya/cop/ems/deleteSndngMail.do' />"
					, data: whoyaGlobalData.bForm.getFormData()
					, success: function(data, textStatus, jqXHR) {
						whoyaGlobalData.bForm = whoyaGlobalData.bCell.attachForm("");
						search();
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
		url: "<c:out value='/whoya/cop/ems/selectSndngMailJSONList.do' />"
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
