<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>권한롤관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/whoya/include/header.jsp" />

<script type="text/javascript">
function init() {
	// #########################################
	// ## 레이아웃생성
	// #########################################
	var layoutData = {
		layout_Pattern: "1C"
	};
	whoyaGlobalData.layout = whoya.dhtmlx.layout.init(layoutData);
	// #########################################
	
	
	// #########################################
	// ## 툴바 생성
	// #########################################
	var toolbarData = {
		layout: whoyaGlobalData.layout
	};
	whoyaGlobalData.toolbar = whoya.dhtmlx.layout.toolbar.init(toolbarData);
	whoyaGlobalData.toolbar.addText("lbl_searchKeyword", 1, "권한코드");
	whoyaGlobalData.toolbar.addInput("searchKeyword", 2, "${param.authorCode}", 200);

	// toolbar의 Button정의
	var toolbarAddButton = {
		toolbar: whoyaGlobalData.toolbar
		, btn_Open: true
		, btn_Save: true
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
		, setHeader: "롤 ID,롤 명,롤 타입,롤 Sort,롤 설명,등록일자,등록여부,권한코드,롤 패턴"
		, setColumnIds: "roleCode,roleNm,roleTyp,roleSort,roleDc,creatDt,regYn,authorCode,rolePtn"
		, setInitWidths: "150,150,150,150,*,150,150,0,0"
		, setColAlign: "center,center,center,center,center,center,center,center,center"
		, setColTypes: "ro,ro,ro,ro,ro,ro,ed,ro,ro"
		, enableResizing: "false,false,false,false,false,false,false,false"
		, enableTooltips: "false,false,false,false,false,false,false,false"
		, setColSorting: "str,str,false,false,false,false,false,false"
		, setColumnHidden: [
			{ id: 7 },
			{ id: 8 }
		]
	};
	// 화면 layout cell a에 dhtmlxGrid 객체 생성.
	whoyaGlobalData.aGrid = whoya.dhtmlx.layout.cell.grid(aCellGridData);
	// #########################################
	
	
	// #########################################
	// ## dataProcessor생성
	// #########################################
	var dpData = {
		url: "<c:url value='/whoya/sec/ram/saveAuthorRole.do' />"
		, obj: whoyaGlobalData.aGrid
	};
	whoyaGlobalData.dp = whoya.dhtmlx.dataProcessor(dpData);
	
	whoyaGlobalData.dp.attachEvent("onAfterUpdateFinish", function() {
		alert("저장하였습니다.");
		search();
	});
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
		if(id == "btn_Save"){
			if ( confirm("저장하시겠습니까?") ) {
				whoyaGlobalData.dp.sendData();
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
		url: "<c:url value='/whoya/sec/ram/EgovAuthorRoleJSONList.do' />"
		, data: {
			searchKeyword : whoyaGlobalData.toolbar.getValue("searchKeyword")
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


$(function(document) {
	init();
});
</script>
</head>
<body>
</body>
</html>
