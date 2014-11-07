<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>계정과목관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/whoya/include/header.jsp" />

<script type="text/javascript">
function init() {
	dhtmlx.image_path = "<c:url value='/dhtmlx/dhtmlx_pro_full/imgs/'/>";
    var oProc = new dhtmlXLayoutObject(document.body, "1C");

	toolbar = oProc.attachToolbar(); /*툴바*/
	toolbar.setIconsPath(dhtmlx.image_path);

	toolbar.addText("txt_Acnm", 1, "계정과목");
	toolbar.addText("cmb_Acnm", 2, ""); 
	toolbar.addSeparator("button_Separator", 3); 

	var comboDIV = toolbar.objPull[toolbar.idPrefix+"cmb_Acnm"].obj;
	toolbar.objPull[toolbar.idPrefix+"cmb_Acnm"].obj.innerHTML = "";
	var combo = new dhtmlXCombo(comboDIV,"alfa",140);
	
	//서버로부터 계정과목콤보정보를 가져온다 
	$.post( "ACI0010_Rc_acCd.do" 
	        ,function(data, status, xhr){
	    	 	combo.addOption(data.list);
	    	  	combo.setComboValue("");
	    	  	comboDataPut(grid, 2, data.list);
		    }
	      , "json"
	).error(function(x,s,t) {httpError(x, s, t);});
	
	comToolbarButton(toolbar);
	toolbar.attachEvent("onClick", function(id){
		if(id == "btn_Open"){ 
			grid.clearAll();
			document.getElementById("activeStatusBar").innerHTML = "";
			$.post( 'ACI0010_Rs.do'
				  , { acCd:	combo.getSelectedValue() }
			      , function(data, status, xhr){
			    	  	//jsonAlert(data.list);
			    		grid.clearAll();
			    		grid.parse(data.list,"json");
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
	
	grid.setHeader("레벨,계정코드,계정과목명,상위계정,차대구분,기표여부,통제여부,사용여부");
	grid.setColumnIds("LV,ACCD,ACNM,HACCD,DCDV,WRTYN,CONYN,USEYN");
	grid.setInitWidths("42,64,*,70,64,64,64,64");	
	grid.setColAlign("center,center,left,center,center,center,center,center");
	grid.setColTypes("ro,ed,ed,ed,coro,ch,ch,ch");
	grid.enableResizing("false,true,false,false,false,false,false,false");
	grid.enableTooltips("false,false,false,false,false,false,false,false");
	grid.setColSorting("str,str,str,str,str,str,str,str");
	
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
	DataProcessor = new dataProcessor("ACI0010_U.do");
	DataProcessor.setTransactionMode("POST", true);
	DataProcessor.setUpdateMode("off");
	DataProcessor.enableDataNames(true);
	//DataProcessor.enablePartialDataSend(true);
	DataProcessor.init(grid);
	

	var main_status = oProc.attachStatusBar();
	main_status.setText("<div><table><td id='activeImg'><img src='<c:url value='/dhtmlx/dhtmlx_pro_full/imgs/run_exc.gif' />'></td><td id='activeStatusBar' valign='middle'></td></table></div>"); 
}

$(document).ready(function() {
	init();
});
</script>
</head>
<body>
</body>
</html>