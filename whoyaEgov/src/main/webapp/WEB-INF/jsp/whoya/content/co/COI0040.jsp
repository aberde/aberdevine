<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>공통코드관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/whoya/include/header.jsp" />

<script type="text/javascript">
function init() {
	dhtmlx.image_path = "<c:url value='/dhtmlx/imgs/'/>";
	
    var oProc = new dhtmlXLayoutObject(document.body, '2U');
	var oProcA = oProc.cells('a');
	var oProcB = oProc.cells('b');
	oProcA.hideHeader();
	oProcB.hideHeader();
	oProcA.setWidth('250');

	toolbar = oProc.attachToolbar(); /*툴바*/
	toolbar.setIconsPath(dhtmlx.image_path);
	
	/*시스템구분 콤보*/
	toolbar.addText("labelSysDv", 1, "시스템구분");
	toolbar.addText("comboSysDv", 2, ""); 
	toolbar.addSeparator("button_Separator", 3); 

	var comboDIV = toolbar.objPull[toolbar.idPrefix+"comboSysDv"].obj;
	toolbar.objPull[toolbar.idPrefix+"comboSysDv"].obj.innerHTML = "";
	var combo = new dhtmlXCombo(comboDIV,"alfa",100);

	$.post( "COI0040Rc_sysDv.do"
			  , function(data, status, xhr){
		    	  combo.addOption(data.list); /*시스템구분콤보*/
		    	  combo.setComboValue("");
			   	  comboDataPut(grid, 2, data.list); //직무 그리드콤보
			    }
			  , "json"
	).error(function(x,s,t) {httpError(x, s, t);});

	/* 좌측 트리메뉴 */	
	var cd_tree = oProcA.attachTree();
	cd_tree.setIconsPath(dhtmlx.image_path);
	cd_tree.setIconSize("", "");
	cd_tree.enableHighlighting("1");
	cd_tree.enableKeyboardNavigation(true);
	cd_tree.enableAutoSavingSelected(true);
	cd_tree.enableAutoTooltips(1);

	$.post( "COI0040Rt.do"
		  , { hCd: combo.getSelectedValue()
		    }
	      , function(data, status, xhr){
	    	  cd_tree.loadJSArray(data.list);
			  cd_tree.openAllItemsDynamic();
			  //cd_tree.selectItem("HR:HR");
			  //cd_tree.focusItem("HR:HR");
			  btnOpen(cd_tree, grid);
		    }
	      , "json"
	).error(function(x,s,t) {httpError(x, s, t);});

    cd_tree.setOnClickHandler(function (id) {
        grid.clearAll();
    	if(cd_tree.getItemText(id).indexOf(")")!=-1) {
			btnOpen(cd_tree, grid);
        }
    });
    
	/*기능버튼*/
	comInitMenuButton(toolbar);
	toolbar.attachEvent("onClick", function(id){
		if(id == "btn_Find"){
			cd_tree.findItem(toolbar.getValue("input_cdText"),0,1);
		} 
		if(id == "btn_Open"){
		    grid.clearAll();
			btnOpen(cd_tree, grid);
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
	
	var grid = oProcB.attachGrid();
	grid.setIconsPath(dhtmlx.image_path);
	
	grid.setHeader(["순서","코드","코드명","상위코드","관리항목1","관리항목2","관리항목3","관리항목4","관리항목5","사용여부"]);
	grid.setInitWidths("40,64,*,70,70,70,70,70,70,64");	
	grid.setColTypes("ed,ed,ed,ed,ed,ed,ed,ed,ed,ch");
	
	grid.setColAlign("right,center,left,center,left,left,left,left,left,center");
	grid.enableResizing("false,false,true,false,false,false,false,false,false,false");
	grid.enableTooltips("false,false,true,false,false,false,false,false,false,false");
	grid.setColSorting("number,str,str,str,str,str,str,str,str,str");

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

function btnOpen(cd_tree, grid) { 
	document.getElementById("activeStatusBar").innerHTML = "";
	$.post( "COI0040Rs.do"
	  	  , { sysDv: cd_tree.getSelectedItemId().substr(0, cd_tree.getSelectedItemId().indexOf(":"))
		    , hCd: cd_tree.getSelectedItemId().substr(cd_tree.getSelectedItemId().indexOf(":")+1) 
		    }
      	 , function(data, status, xhr){
    		  grid.parse(data.list,"json");
    		  grid.setSelectedRow(0);
    		  document.getElementById("activeStatusBar").innerHTML = data.message;
	       }
         , "json"
 	).error(function(x,s,t) {httpError(x, s, t);});
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