<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="contect-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./dhtmlx/dhtmlx.css">

<script type="text/javascript" src="./dhtmlx/dhtmlx.js"></script>
<script type="text/javascript" src="./dhtmlx/dhtmlxgrid_keymap_excel.js"></script>

<script type="text/javascript" src="./dhtmlx/dhtmlxSuite_v30_pro_110707/dhtmlxTabbar/sources/dhtmlxtabbar.js"></script>
<script type="text/javascript" src="./dhtmlx/dhtmlxSuite_v30_pro_110707/dhtmlxTabbar/sources/dhtmlxcommon.js"></script>


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
/* 사용자권한관리 --------------------------------------------------------------*/

	dhtmlx.image_path='./dhtmlx/preview/codebase/imgs/';

    var oProc = new dhtmlXLayoutObject(document.body, '1C');
	var oProcA = oProc.cells('a');
	oProcA.hideHeader();

	toolbar = oProc.attachToolbar(); /*툴바*/
	toolbar.setIconsPath(dhtmlx.image_path);
	/* toolbar.loadXML('./dhtmlx/preview/data/toolbar.xml', function(){}); */

	toolbar.addText("txt_Acnm", 1, "직무");
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
	
	grid.setHeader(["프로그램명","기능","레벨","입력여부","출력여부","조회여부"]);
	grid.setColTypes("ro,ro,ro,ch,ch,ch");
	
	grid.setColAlign('left,left,center,center,center,center');
	grid.enableResizing('false,false,false,false,false,false');
	grid.enableTooltips('false,false,false,false,false,false');
	grid.setColSorting('str,str,str,str,str,str');
	grid.setInitWidths("300,80,42,70,70,70");	
	
	grid.enableMultiselect("true"); 
	grid.enableBlockSelection("false")
	grid.init();
	grid.load('./dhtmlx/preview/data/grid.xml', 'xml');
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
</body>
</html>