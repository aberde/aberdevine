<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>발송메일내역</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/whoya/include/header.jsp" />
<script type="text/javascript" src="<c:out value='/js/whoya/whoya.dhtmlx.ui.js' />"></script>

<script type="text/javascript">
function init() {
	// #########################################
	// ## 레이아웃생성
	// #########################################
	var layout = whoya.dhtmlx.layout();
	// #########################################
	
	
	// #########################################
	// ## 툴바 생성
	// #########################################
	var toolbarData = {
		layout: layout
	};
	var toolbar = whoya.dhtmlx.layout.toolbar(toolbarData);
	toolbar.addText("searchCondition", 1, "");
	toolbar.addInput("searchKeyword", 2, "", 200);
	toolbar.addSeparator("button_Separator", 3);

	// selectBox 생성
	var comboDIV = toolbar.objPull[toolbar.idPrefix+"searchCondition"].obj;
	toolbar.objPull[toolbar.idPrefix+"searchCondition"].obj.innerHTML = "";
	combo = new dhtmlXCombo(comboDIV,"alfa",140);
	combo.addOption([
   		["", "--선택하세요--"]
   		, ["1", "제목"]
   		, ["2", "내용"]
   		, ["3", "보낸이"]
   	]);
	combo.selectOption(0);

	var hideBtn = {
		btn_Undo: false
		, btn_Save: false
		, btn_Print: false
		, btn_Excel: false
	};
	comToolbarButton(toolbar, hideBtn);
	
	// event bar 생성
	toolbar.attachEvent("onClick", function(id){
		if(id == "btn_Open"){
			var searchData = {
				layout: layout
				, toolbar: toolbar
				, grid: grid
				, combo: combo
			};
			search(searchData);
		}
		if(id == "btn_Append"){
			location.href = "/whoya/cop/ems/insertSndngMailView.do";
		}
		if(id == "btn_Delete") {
			if ( confirm("삭제하시겠습니까?") ) {
				
// 				document.getElementById("activeStatusBar").innerHTML = "";
				
// 				grid.deleteSelectedRows();
				
// 				DataProcessor.sendData();
			}
		}
    });
	// #########################################

	
	// #########################################
	// ## layout cell a 
	// #########################################
	var cellData = {};
	cellData.layout = layout;
	// 화면 layout의 해당 cell 정의 
	var cell = whoya.dhtmlx.layout.cell(cellData);
	// #########################################
	
	
	// #########################################
	// ## layout cell a에 grid생성
	// #########################################
	var aCellGridData = {};
	aCellGridData.cell = cell;
	aCellGridData.setHeader = "상태,발신자,수신자,제목,날짜,메세지ID";
	aCellGridData.setColumnIds = "sndngResultCode,dsptchPerson,recptnPerson,sj,sndngDe,mssageId";
	aCellGridData.setInitWidths = "100,100,150,*,100,100";
	aCellGridData.setColAlign = "center,center,center,center,center,center";
	aCellGridData.setColTypes = "ro,ro,ro,ro,ro,ro";
	aCellGridData.enableResizing = "false,true,false,false,false,false";
	aCellGridData.enableTooltips = "false,false,false,false,false,false";
	aCellGridData.setColSorting = "str,str,str,str,str,str";
	aCellGridData.setColumnHidden = [
		{ id: 5 }
	];
	// 화면 layout cell a에 dhtmlxGrid 객체 생성.
	var grid = whoya.dhtmlx.layout.cell.grid(aCellGridData);
	// #########################################
	
	
	// #########################################
	// ## grid 또는 form 등의 객체에서 서버로 저장
	// #########################################
	var dpData = {
		url: "/whoya/cop/ems/deleteSndngMailList.do"
		, obj: grid
	};
	var dp = whoya.dhtmlx.dataProcessor(dpData);
	// #########################################

	
	// #########################################
	// ## layout에 statusbar 생성
	// #########################################
	var statusbarData = {
		layout: layout	
	};
	var statusbar = whoya.dhtmlx.statusbar(statusbarData);
	// #########################################
}

/**
 * 조회버튼 클릭시
 * @param data JSON형식의 UI셋팅 데이터
 *   layout: layout  // dhtmlXLayoutObject 객체
 *   toolbar: toolbar  // dhtmlXLayoutObject의 toolbar 객체
 *   grid: grid  // dhtmlxGrid 객체
 *   combo: combo  // toolbar에 추가한 dhtmlxComboBox 객체 
 */
function search(data) {
	var whoyaData = {
	};
	$.extend(whoyaData, data);
	
	whoyaData.layout.progressOn();
	whoyaData.grid.clearAll();
	document.getElementById("activeStatusBar").innerHTML = "";

	$.ajax({
		url: "/whoya/cop/ems/selectSndngMailJSONList.do"
		, data: {
			searchCondition : whoyaData.combo.getSelectedValue()
			, searchKeyword : whoyaData.toolbar.getValue("searchKeyword")
		}
		, success: function(data, textStatus, jqXHR) {
    	  	whoyaData.grid.attachEvent("onXLE", function(){
    	  		whoyaData.layout.progressOff();
    		});
    	  	
    	  	whoyaData.grid.clearAll();
    	  	whoyaData.grid.parse(data.list, "json");
    	  	whoyaData.grid.setSelectedRow(0);
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
