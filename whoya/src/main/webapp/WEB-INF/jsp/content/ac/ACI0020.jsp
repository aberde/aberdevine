<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>부서/조직코드관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/include/header.jsp" />

<style>
		/* toolbar modifications for 24px icons */
		.dhx_combo_box{
			margin-top:-2px;
			height:17px;
		}
	    .dhx_combo_img{
		    position:absolute;
			top:0;
			right:0;
			width:14px;
			height:15px;
		}
	</style>

<script>
function init() {
/* 부서조직코드 --------------------------------------------------------------*/

	dhtmlx.image_path = "<c:url value='/dhtmlx/imgs/'/>";

    var oProc = new dhtmlXLayoutObject(document.body, '1C');
	var oProcA = oProc.cells('a');
	oProcA.hideHeader();

	toolbar = oProc.attachToolbar(); /*툴바*/
	toolbar.setIconsPath(dhtmlx.image_path);
	/* toolbar.loadXML('./dhtmlx/preview/data/toolbar.xml', function(){}); */

	toolbar.addText("txt_Acnm", 1, "검색부서");
	toolbar.addText("cmb_Acnm", 2, ""); 

	var comboDIV = toolbar.objPull[toolbar.idPrefix+"cmb_Acnm"].obj;
	
	toolbar.objPull[toolbar.idPrefix+"cmb_Acnm"].obj.innerHTML = "";

	var combo = new dhtmlXCombo(comboDIV,"alfa",140);
	
	combo.addOption([[0,"전사"],[1,"관리부"],[2,"인사부"]]);

	toolbar.addSeparator("sep_Open", 3); 
	toolbar.addButton("btn_Open", 4, "조회", "open.gif");
	toolbar.addSeparator("sep_Save", 5); 
	toolbar.addButton("btn_Save", 6, "저장", "save.gif");
	toolbar.addSeparator("sep_Print", 7);
	toolbar.addButton("btn_Print", 8, "인쇄", "print.gif");
	toolbar.addSeparator("sep_Excel", 9);
	toolbar.addButton("btn_Excel", 10, "엑셀", "excel.gif");
	
	toolbar.forEachItem(function(id) {
		if (id == "cmb_Acnm") {
			toolbar.addSpacer(id);
        }
	});

	var grid = oProcA.attachGrid();
	grid.setIconsPath(dhtmlx.image_path);
	
	grid.setHeader(["부서코드","부서명","레벨","상위부서","부서폐쇄일자","전표기표여부"]);
	grid.setColTypes("ed,ed,ro,ed,dhxCalendar,ch");
	
	grid.setColAlign('center,left,center,center,center,center');
	grid.enableResizing('false,true,false,false,false,false');
	grid.enableTooltips('false,true,false,false,false,false');
	grid.setColSorting('str,str,str,str,str,str');
	grid.setInitWidths("64,*,42,70,90,90");	
	grid.setDateFormat("%Y-%m-%d");

	
	
	
	grid.enableMultiselect("true"); 
	grid.enableBlockSelection("false")
	grid.init();
	grid.load('/whoya/dhtmlx/preview/data/grid.xml', 'xml');
	grid.attachEvent("onKeyPress",function(code, ctrl, shift){                        
	
        if(code == 45){
				var i = grid.getSelectedId();
				grid.addRow(grid.getUID(), "0,", (grid.getRowIndex(grid.getSelectedId())));
				grid.SelectRow(-1,"","true","true");
				//grid.SelectRow(grid.getRowId(grid.getSelectedId()-1));
				//grid.setSelectedRow((grid.getRowIndex(grid.getSelectedId())));
				//var cnt = grid.getRowsNum();
				//for(var i=1; i<=cnt; i++){
					//grid.setRowId(i, i);
					//grid.cells(i,0).setValue(i);
			    //}
		}

        if (ctrl){ 
            if(code == 67){
                if (!grid._selectionArea)
                    return alert("복사할 셀을선택하십시오");
                grid.setCSVDelimiter("\t");
                grid.copyBlockToClipboard();
                grid._HideSelection();
            }
            if(code == 86){
        	    grid.setCSVDelimiter("\t");
                grid.pasteBlockFromClipboard();
                grid._HideSelection();
            }
        }
        
        return true;
    });    
	
	/* 하단 상태바 */
	var main_status = oProc.attachStatusBar();
	main_status.setText('');
}
</script>
</head>
<body onload='init()'>
<div id="ProgBody" style="width:100%; height:100%;"></div>
</body>
</html>