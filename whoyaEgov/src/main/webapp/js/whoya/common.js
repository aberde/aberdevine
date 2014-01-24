/*콤보데이터 업로드*/
function comboDataPut(grid, ColunmNo, data) {
	for(var i=0; i<data.length; i++) {
		if(data[i][0]!=""){
			grid.getCombo(ColunmNo).put(data[i][0],data[i][1]);
		}	
	}
}

//Grig편집메뉴
function comEditGrid(grid, menuItemId, editValues){

	var i = grid.getRowIndex(grid.getSelectedRowId());

	if(menuItemId == "Append"){ //추가
        if(i < 0) { grid.addRow(0, editValues,1);}
           else { grid.addRowAfter(grid.getUID(), editValues, grid.getSelectedRowId());
		}
	}
	if(menuItemId == "Delete"){ //삭제
		//grid.deleteRow(grid.getSelectedRowId());
		grid.deleteSelectedRows();
		grid.selectRow(i,false,false,false);
	}
	if(menuItemId == "Undo"){ //취소
		//grid.deleteRow(grid.getSelectedRowId());
		grid.doUndo("Undo");
	}
}

//ClipBoard복사
function comKeyPress(grid, code, ctrl, shift, keyVal){
	if(ctrl){ 
		//alert(code);
		
		if(code == 45){ // Ctrl+Insert키
			comEditGrid(grid, "Append", keyVal);	
		}
		if(code == 46){ // Ctrl+Delete키
			comEditGrid(grid, "Delete");	
		}
		if(code == 37){ // Ctrl+Delete키
			comEditGrid(grid, "Undo");	
		}
        if(code == 67){
			if(!grid._selectionArea)
				return alert("복사할 대상이 선택되지않았습니다.");
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
}

/*단위화면 기능키표시*/
function comToolbarButton(toolbar){
	toolbar.addSeparator("sep_Open", 11); 
	toolbar.addButton("btn_Open", 12, "조회", "open.gif");
	toolbar.addSeparator("sep_Save", 13); 
	toolbar.addButton("btn_Append", 14, "추가", "append.gif");
	toolbar.addSeparator("sep_Save", 15); 
	toolbar.addButton("btn_Delete", 16, "삭제", "close.gif");
	toolbar.addSeparator("sep_Save", 17); 
	toolbar.addButton("btn_Undo", 18, "취소", "undo.gif");
	toolbar.addSeparator("sep_Save", 19); 
	toolbar.addButton("btn_Save", 20, "저장", "save.gif");
	toolbar.addSeparator("sep_Print", 21);
	toolbar.addButton("btn_Print", 22, "인쇄", "print.gif");
	toolbar.addSeparator("sep_Excel", 23);
	toolbar.addButton("btn_Excel", 24, "엑셀", "excel.gif");
	toolbar.forEachItem(function(id) {
		if (id == "button_Separator") toolbar.addSpacer(id);
        
	});

	//window.setTimeout(function(){
	//	toolbar.objPull[toolbar.idPrefix+"info"].obj.className = toolbar.objPull[toolbar.idPrefix+"info"].obj.className;
	//},1)
}

function comGridControl(menu, grid, txtAppendInit){

	//menu.setIconsPath(dhtmlx.image_path);
	//menu.renderAsContextMenu();

	return menu;
}

/*FORM을 만드는 함수*/
function createFormClass(name, action, method){  
    var form = document.createElement("form");  
    form.name = name;  
    form.action = action;  
    form.method = method;  
    return form;  
} 

/*INPUT을 만들고 form에 추가시키는 함수*/ 
function insertFromAttrib(form, name, value){  
    var input = document.createElement("input");  
    input.type = "hidden";  
    input.name = name;  
    input.value = value;  
    form.insertBefore(input, null);  
    return form;  
}

/*서버오류 메시지 알림창*/
function httpError(x, s, t) {
	try {eval('var d = ' + x.responseText);
		if (x.status=='406') {
			alert(d.massage ? d.massage : d.status);
		}
	} catch(e) {
		alert('정상적으로 처리되지 못했습니다. (ERROR_CODE: ' + x.status + ')');
	}
}

function closeLayerPopupIframe() {
	parent.window.$.unblockUI();
}

function layerPopupIframe(url, width, height) {
	
	var TOP_DEFAULT = 40;
	var LEFT_DEFAULT = 40;
	var FIX = $.msie ? 25 : 20;
	
	height = (height === undefined) ? 500 : height;
	width = (width === undefined) ? 800 + FIX : width + FIX;
	
	var con = $('<iframe frameborder="0" marginwidth="0" marginheight="0"/>').css({
								width: '100%'
						        ,height: height + 'px'
						        ,'overflow-x': 'hidden'
							}).attr('src', url);

	var top = ($(window).height() - height) / 2;
	var left = ($(window).width() - width) / 2;
	
	if (top <= TOP_DEFAULT) {
		top = TOP_DEFAULT + 'px';
	} else {
		top = top + 'px'; 
	}
	if (left <= LEFT_DEFAULT) {
		left = LEFT_DEFAULT + 'px';
	} else {
		left = left + 'px'; 
	}
	
	$.blockUI({
		message: con
		,css: {
			width: width + 'px'
			,height: height + 'px'
			,top: top
			,left: left
		}
	});
}

function listOptionChange(el, url) {
	var regexp = /rowsPerPage=\d+/;
	var regexp2 = /page=\d+/;
	
	if (regexp.test(url)) {
		url = url.replace(regexp, 'rowsPerPage=' + el.value);
	} else {
		url += ('&rowsPerPage=' + el.value);
	}
	
	if (regexp2.test(url)) {
		url = url.replace(regexp2, 'page=1');
	}
	
	window.location.href = url;
}
/*유틸 ----------------------------------------------------------------------*/
/*JSON객체스트링변환*/
function jsonString(data){ 
	return JSON.stringify(data, function(key, value){
			if (typeof value === 'number' && !isFinite(value)) {
					return String(value);
				}
			return value;
		   });
}