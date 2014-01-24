<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>로그인정책관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/whoya/include/header.jsp" />

<script type="text/javascript">
function init() {
	dhtmlx.image_path = "<c:url value='/dhtmlx/dhtmlx_pro_full/imgs/'/>";
    var oProc = new dhtmlXLayoutObject(document.body, "1C");

	toolbar = oProc.attachToolbar(); /*툴바*/
	toolbar.setIconsPath(dhtmlx.image_path);

	toolbar.addText("lbl_searchKeyword", 1, "사용자명");
	toolbar.addInput("searchKeyword", 2, "", 80);
	toolbar.addSeparator("button_Separator", 3);

	comToolbarButton(toolbar);
	
	toolbar.attachEvent("onClick", function(id){
		if(id == "btn_Open"){
			oProc.progressOn();
			grid.clearAll();
			document.getElementById("activeStatusBar").innerHTML = "";
			$.post( 'selectLoginPolicyJSONList.do'
				  , { searchKeyword : toolbar.getValue("searchKeyword")
					  , searchCondition : 1 }
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
		if(id == "btn_Append"){ 
			if(grid.getRowIndex(grid.getSelectedRowId()) < 0){ grid.addRow(grid.getUID());}
           	else { grid.addRowAfter(grid.getUID(), "", grid.getSelectedRowId());}
		}	
		if(id == "btn_Delete"){
			grid.deleteSelectedRows();
			grid.selectRow(i,false,false,false);
		}
		if(id == "btn_Undo"){
			grid.doUndo("Undo");
		}
		if(id == "btn_Save"){ /* 저장 */
			document.getElementById("activeStatusBar").innerHTML = "";
			DataProcessor.sendData();
		}		
		if(id == "btn_Print"){ grid.printView();}
    });	
	var oProcA = oProc.cells("a");
	oProcA.hideHeader();
	
	var grid = oProcA.attachGrid();
	grid.setIconsPath(dhtmlx.image_path);
	
	grid.setHeader("사용자ID,사용자명,IP정보,제한여부");
	grid.setColumnIds("emplyrId,emplyrNm,ipInfo,lmttAt");
	grid.setInitWidths("100,*,100,100");
	grid.setColAlign("center,center,center,center");
	grid.setColTypes("ed,ed,ed,ed");
	grid.enableResizing("false,true,false,false");
	grid.enableTooltips("false,false,false,false");
	grid.setColSorting("str,str,str,str");
	
	grid.enableMultiselect("true"); 
	grid.enableBlockSelection("false")
	grid.enableUndoRedo();
	grid.enableSmartRendering(true, 100);
	
	//grid.enablePaging(true, 10, 2, "activeStatusBar");
	//grid.setPagingSkin("toolbar","dhx_skyblue");

	grid.init();
	//grid.enableRowsHover(true,'grid_hover');
	//grid.enableLightMouseNavigation(true);
	//grid.enableKeyboardSupport(true);
	//grid.loadXML("/whoya/dhtmlx/data/accountCode.xml");

	grid.attachEvent("onKeyPress", function(code, ctrl, shift){                        
		//comKeyPress(grid, code, ctrl, shift, ",,,,0,,1");	
    });    
	DataProcessor = new dataProcessor("saveLoginPolicy.do");
	DataProcessor.setTransactionMode("POST", true);
	DataProcessor.setUpdateMode("off");
	DataProcessor.enableDataNames(true);
	//DataProcessor.enablePartialDataSend(true);
	DataProcessor.init(grid);
	
	DataProcessor.attachEvent("onAfterUpdateFinish", function() {
	    log("onAfterUpdateFinish", arguments);
	    return true;
	});
	
	function log(message, args) {
	    var div = document.createElement("div");
	    for (var i = 0; i < 3; i++) {
	        message += "<li>" + args[i];
	    };
	    alert('aaaaaa', message);
	    message += "<br/>";
	    div.innerHTML = message;
	    document.getElementById('zoneA').appendChild(div);
	}

	var main_status = oProc.attachStatusBar();
	main_status.setText("<div><table><td id='activeImg'><img src='<c:url value="/dhtmlx/dhtmlx_pro_full/imgs/run_exc.gif" />'></td><td id='activeStatusBar' valign='middle'></td></table></div>"); 
}

$(document).ready(function() {
	init();
});
</script>
</head>
<body>
</body>
</html>