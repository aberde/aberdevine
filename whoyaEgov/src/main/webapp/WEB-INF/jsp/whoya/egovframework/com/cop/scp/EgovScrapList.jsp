<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>스크랩 목록</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/whoya/include/header.jsp" />

<script type="text/javascript">
var oProc;
var form;

function init() {
	dhtmlx.image_path = "<c:url value='/dhtmlx/dhtmlx_pro_full/imgs/'/>";
    oProc = new dhtmlXLayoutObject(document.body, "1C");
    oProc.dhxWins.setEffect("move", true);
    
    var toolbar;
    var combo;
    var grid;
	
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
       		["0", "스크랩명"]
       	]);
		combo.selectOption(0);
	
		var hideBtn = {
			btn_Append: false
			, btn_Delete: false
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
	    });
    }
    
    /**
     * 조회
     */
    function search() {
    	oProc.progressOn();
		grid.clearAll();
		document.getElementById("activeStatusBar").innerHTML = "";
		$.post( 'selectScrapJSONList.do'
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
		
		grid.setHeader("번호,스크랩명,등록자,등록일");
		grid.setColumnIds("no,scrapNm,frstRegisterNm,frstRegisterPnttm");
		grid.setInitWidths("100,*,*,*");
		grid.setColAlign("center,center,center,center");
		grid.setColTypes("ro,ro,ro,ro");
		grid.enableResizing("false,true,false,false");
		grid.enableTooltips("false,false,false,false");
		grid.setColSorting("str,str,str,str");
		
		grid.enableMultiselect("true"); 
		grid.enableBlockSelection("false");
		grid.enableUndoRedo();
		grid.enableSmartRendering(true, 100);
		
		grid.init();
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
    fn_layout_statusbar();
}

$(document).ready(function() {
	init();
});
</script>
</head>
<body>
</body>
</html>