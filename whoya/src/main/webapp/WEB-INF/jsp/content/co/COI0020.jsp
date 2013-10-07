<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>메뉴관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/include/header.jsp" />

<script type="text/javascript">
function init() {
	dhtmlx.image_path = "<c:url value='/dhtmlx/imgs/'/>";
	
    var oProc = new dhtmlXLayoutObject(document.body, '1C');
	var oProcA = oProc.cells('a');
	oProcA.hideHeader();

	toolbar = oProc.attachToolbar(); /*툴바*/
	toolbar.setIconsPath(dhtmlx.image_path);

	/*시스템구분 콤보*/
	toolbar.addText("labelMnuDv", 1, "시스템구분");
	toolbar.addText("comboMnuNm", 2, ""); 
	toolbar.addSeparator("button_Separator", 3);
	
	var comboDIV = toolbar.objPull[toolbar.idPrefix+"comboMnuNm"].obj;
	toolbar.objPull[toolbar.idPrefix+"comboMnuNm"].obj.innerHTML = "";

	var combo = new dhtmlXCombo(comboDIV,"alfa",100);
	
	$.post( "COI0020Rc_mnuDv.do"
	      , function(data, status, xhr){
	    	  combo.addOption(data.list); /*시스템구분 콤보*/
	    	  combo.setComboValue("");
	    	  //comboDataPut(grid, 5, data.list); /*시스템구분 그리드콤보*/
		    }
	      , "json"
	).error(function(x,s,t) {httpError(x, s, t);});

	/*
	$.post( "COI0020Rc_mnuFnc.do"
		  , function(data, status, xhr){
		   	  comboDataPut(grid, 6, data.list); //프로그램기능구분 그리드콤보
		    }
		  , "json"
	).error(function(x,s,t) {httpError(x, s, t);});
    */
	
	/*기능버튼*/
	comInitMenuButton(toolbar);
	toolbar.attachEvent("onClick", function(id){
		if(id == "btn_Open"){ 
			document.getElementById("activeStatusBar").innerHTML = "";
            
			$.post( "COI0020Rs.do"
				  , { mnuDv: combo.getSelectedValue() }
			      , function(data, status, xhr){
			    		grid.clearAll();
			    		//grid.parse(data.list,"json");
			    		grid.parse(data.list,"json");
			    		grid.setSelectedRow(0);
			    		document.getElementById("activeStatusBar").innerHTML = data.message;
				    }
			      , "json"
			 ).error(function(x,s,t) {httpError(x, s, t);});
		}
    });

	menu = new dhtmlXMenuObject();
	menu.setIconsPath(dhtmlx.image_path);
	menu.renderAsContextMenu();
    menu.addNewChild(menu.topId, 0, "Append", "추가 Ctrl+Ins", false, "append.gif");
    menu.addNewChild(menu.topId, 1, "Delete", "삭제 Ctrl+Del", false, "close.gif");
    menu.addNewChild(menu.topId, 2, "Undo", "취소 Ctrl+X", false, "close.gif");
	
	menu.attachEvent("onClick", function(menuItemId, type){
			if(menuItemId == "Append") comEditGrid(grid, "Append", ",,,,0,,1");	
			if(menuItemId == "Delete") comEditGrid(grid, "Delete");	
			if(menuItemId == "Undo") comEditGrid(grid, "Undo");	
		    return true;
	});
	
	var grid = oProcA.attachGrid();
	grid.setIconsPath(dhtmlx.image_path);
	
	grid.setHeader(["레벨","순서","프로그램 ID","프로그램명","상위 ID","기능구분","기능내용","등록일시","등록자ID"]);
	grid.setInitWidths("42,42,76,*,76,60,200,90,70");	
	grid.setColTypes("ro,ed,ed,ed,ed,co,co,ro,ro");
	grid.setColAlign("center,center,left,left,left,left,left,left,center");
	grid.enableResizing("false,false,false,false,false,false,false,false,false");
	grid.enableTooltips("false,false,false,false,false,false,false,false,false");
	grid.setColSorting("str,str,str,str,str,str,str,str,str");

	grid.enableMultiselect("true"); 
	grid.enableBlockSelection("false")
	grid.enableUndoRedo();
	grid.enableContextMenu(menu);
	grid.enableSmartRendering(true, 100);

	//grid.enablePaging(true, 10, 2, "activeStatusBar");
	//grid.setPagingSkin("toolbar","dhx_skyblue");

	grid.init();
	//grid.enableRowsHover(true,'grid_hover');
	//grid.enableLightMouseNavigation(true);
	//grid.enableKeyboardSupport(true);

	grid.attachEvent("onKeyPress", function(code, ctrl, shift){                        
		//comKeyPress(grid, code, ctrl, shift, ",,,,0,,1");	
    });    
	
	/* 하단 상태바 */
	var main_status = oProc.attachStatusBar();
	main_status.setText("<div><table><td id='activeImg'><img src='/whoya/dhtmlx/imgs/run_exc.gif'></td><td id='activeStatusBar' valign='middle'></td></table></div>"); 
}


$(document).ready(function() {
	init();
});

</script>
</head>
<body>
<div id="ProgBody" style="width:100%; height:100%;"></div>
</body>
</html>