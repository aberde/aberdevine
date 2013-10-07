<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 

<head>
<title>사용자관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/include/header.jsp" />

<script type="text/javascript">
function init() {
	dhtmlx.image_path = "<c:url value='/dhtmlx/imgs/'/>";
	
    var oProc = new dhtmlXLayoutObject(document.body, '2U');
	var oProcA = oProc.cells('a');
	oProcA.hideHeader();
	var oProcB = oProc.cells('b');
	
	/*
	oProcB.hideHeader();
	b.setText("메뉴");
	oProcB.setWidth("300");
	
	formData = [{ type: "settings", position: "label-left", labelWidth: 80, inputWidth: 120 }
	           ,{ type: "fieldset", label: "상세정보", inputWidth: "auto",
         	list: [{ type: "input",    label: "사용자ID" }
               	  ,{ type: "input",    label: "성명"    }
               	  ,{ type: "password", label: "비밀번호" }
                  ,{ type: "combo",    label: "직위",    inputWidth: 120,
            			options: [{	value: "tahoma", text: "Tahoma" }
            		             ,{ value: "arial",  text: "Arial", selected: true }
            		             ,{ value: "verdana", text: "Verdana"
	            			     }]
	              }]
 	           }];
	var myForm = oProcB.attachForm();
    myForm.loadStruct(formData, "json");
	*/
	toolbar = oProc.attachToolbar(); /*툴바*/
	toolbar.setIconsPath(dhtmlx.image_path);

	toolbar.addText("labelDptNm", 1, "소속부서");
	toolbar.addText("comboDptCd", 2, ""); 
	toolbar.addSeparator("button_Separator", 3); 
	toolbar.addText("labelUsrNm", 4, "사용자이름");
	toolbar.addInput("inputUsrNm", 5, "", 80); 
	toolbar.addSeparator("button_Separator", 6); 

	var comboDIV = toolbar.objPull[toolbar.idPrefix+"comboDptCd"].obj;
	toolbar.objPull[toolbar.idPrefix+"comboDptCd"].obj.innerHTML = "";
	var combo = new dhtmlXCombo(comboDIV,"alfa",140);
	
	$.post( "COI0010Rc_rolCd.do"
			  , function(data, status, xhr){
			   	  comboDataPut(grid, 3, data.list); //직위 그리드콤보
			    }
			  , "json"
	).error(function(x,s,t) {httpError(x, s, t);});

	$.post( "COI0010Rc_rolId.do"
			  , function(data, status, xhr){
			   	  comboDataPut(grid, 4, data.list); //직무 그리드콤보
			    }
			  , "json"
	).error(function(x,s,t) {httpError(x, s, t);});
	
	$.post( "COI0010Rc_grpId.do"
			  , function(data, status, xhr){
			   	  comboDataPut(grid, 5, data.list); //작업그룹 그리드콤보
			    }
			  , "json"
	).error(function(x,s,t) {httpError(x, s, t);});

	$.post( "COI0010Rc_dptCd.do"
		      , function(data, status, xhr){
		    	  combo.addOption(data.list); /*소속부서콤보*/
		    	  combo.setComboValue("");
		    	  comboDataPut(grid, 6, data.list); /*소속부서그리드콤보*/
			    }
		      , "json"
	).error(function(x,s,t) {httpError(x, s, t);});

	/*기능버튼*/
	comToolbarButton(toolbar);
	toolbar.attachEvent("onClick", function(id){
		if(id == "btn_Open"){ /* 조회 */ 
			document.getElementById("activeStatusBar").innerHTML = "";
			$.post( "COI0010Rs.do"
				  , { dptCd: combo.getSelectedValue()
				     ,usrNm: toolbar.getValue("inputUsrNm")
				    }
			      , function(data, status, xhr){
			    		grid.clearAll();
			    		grid.parse(data.list,"json");
			    		grid.setSelectedRow(0);
			    		document.getElementById("activeStatusBar").innerHTML = data.message;
				    }
			      , "json"
			 ).error(function(x,s,t) {httpError(x, s, t);});
		}
		if(id == "btn_Append"){ 
			if(grid.getRowIndex(grid.getSelectedRowId()) < 0){ grid.addRow(grid.getUID(), ",,,,,,,,,1"); }
           	else { grid.addRowAfter(grid.getUID(), ",,,,,,,,,1", grid.getSelectedRowId()); }
			grid.selectRow(parseInt(grid.getSelectedRowId())+1);
		}	
		if(id == "btn_Delete") comEditGrid(grid, "Delete");	
		if(id == "btn_Undo") comEditGrid(grid, "Undo");	
		if(id == "btn_Save"){ /* 저장 */
			document.getElementById("activeStatusBar").innerHTML = "";
			DataProcessor.sendData();
		}		
		if(id == "btn_Print"){ grid.printView();
		}
    });

	var grid = oProcA.attachGrid();
	grid.setIconsPath(dhtmlx.image_path);

	grid.setHeader("사용자 ID,성명,비밀번호,직위,직무,작업그룹,소속부서,대표전화번호,휴대전화번호,사용여부");
	grid.setColumnIds("USRID,USRNM,PWD,ROLCD,ROLID,GRPID,DPTCD,TELNO,HPNNO,USEYN");
	grid.setInitWidths("80,100,80,60,100,100,160,120,120,70");	
	grid.setColTypes("ed,ed,ed,co,co,co,co,ed,ed,ch");
	grid.setColAlign("center,left,center,left,left,left,left,center,center,center");
	grid.enableResizing("false,false,false,false,false,false,false,false,false,false");
	grid.enableTooltips("false,false,false,false,false,false,false,false,false,false");
	grid.setColSorting("str,str,str,str,str,str,str,str,str,str");


	//grid.enablePaging(true, 10, 2, "activeStatusBar");
	//grid.setPagingSkin("toolbar","dhx_skyblue");
	
	grid.enableMultiselect("true"); 
	grid.enableBlockSelection("false")
	//grid.enableUndoRedo();
	grid.enableSmartRendering(true, 100);
	//grid.enableContextMenu(menu);
	grid.init();
	//grid.splitAt(2); /* 틀고정 */
	//grid.enableLightMouseNavigation(true); /* 핫입력 */
	//grid.enableRowsHover(true,'grid_hover'); /* 따라다니는 ROW포커스 */
	//grid.enableKeyboardSupport(true);
	grid.attachEvent("onKeyPress", function(code, ctrl, shift){      
		comKeyPress(grid, code, ctrl, shift, ",,,,,,,,,0");	
    });    
	
	DataProcessor = new dataProcessor("COI0010Ru.do");
	DataProcessor.setTransactionMode("POST", true);
	DataProcessor.setUpdateMode("off");
	DataProcessor.enableDataNames(true);
	//DataProcessor.enablePartialDataSend(true);
	DataProcessor.init(grid);
	

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