<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>기업회원관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/whoya/include/header.jsp" />

<script type="text/javascript">
function init() {
	dhtmlx.image_path = "<c:url value='/dhtmlx/dhtmlx_pro_full/imgs/'/>";
    var oProc = new dhtmlXLayoutObject(document.body, "2U");

    /**
     * 툴바 생성.
     */
    function fn_toolbar() {
		toolbar = oProc.attachToolbar(); /*툴바*/
		toolbar.setIconsPath(dhtmlx.image_path);
	
		toolbar.addText("sbscrbSttus", 1, "");
		toolbar.addText("searchCondition", 2, "");
		toolbar.addInput("searchKeyword", 3, "", 80);
		toolbar.addSeparator("button_Separator", 4);
	
		// selectBox 생성
		var comboDIV = toolbar.objPull[toolbar.idPrefix+"sbscrbSttus"].obj;
		toolbar.objPull[toolbar.idPrefix+"sbscrbSttus"].obj.innerHTML = "";
		var combo = new dhtmlXCombo(comboDIV,"alfa",140);
		combo.addOption([
			["0", "상태(전체)"]
			, ["A", "가입신청"]
			, ["D", "삭제"]
			, ["P", "승인"]
		]);
		combo.selectOption(0);
	
		// selectBox 생성
		var comboDIV2 = toolbar.objPull[toolbar.idPrefix+"searchCondition"].obj;
		toolbar.objPull[toolbar.idPrefix+"searchCondition"].obj.innerHTML = "";
		var combo2 = new dhtmlXCombo(comboDIV2,"alfa",140);
		combo2.addOption([
			["0", "ID"]
			, ["1", "Name"]
		]);
		combo2.selectOption(1);
		
		comToolbarButton(toolbar);
		
		// event bar 생성
		toolbar.attachEvent("onClick", function(id){
			if(id == "btn_Open"){
				oProc.progressOn();
				grid.clearAll();
				document.getElementById("activeStatusBar").innerHTML = "";
				$.post( 'EgovEntrprsMberManageJSON.do'
					  , { sbscrbSttus : combo.getSelectedValue()
						  , searchCondition : combo2.getSelectedValue()
						  , searchKeyword : toolbar.getValue("searchKeyword") }
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
    }
    
	/**
	 * layout a cell
	 */
    function fn_layout_a() {
		var oProcA = oProc.cells("a");
		oProcA.setWidth($(document).width() / 10 * 7);
		oProcA.hideHeader();
		
		var grid = oProcA.attachGrid();
		grid.setIconsPath(dhtmlx.image_path);
		
		grid.setHeader("아이디,회사명,신청자이름,신청자이메일,전화번호,등록일,가입상태,사용자고유아이디,사용자 유형,그룹 ID");
		grid.setColumnIds("userId,cmpnyNm,userNm,emailAdres,tel,sbscrbDe,sttus,uniqId,userTy,groupId");
		grid.setInitWidths("100,100,100,*,100,100,100,100,100,100");
		grid.setColAlign("center,center,center,center,center,center,center,center,center,center");
		grid.setColTypes("ro,ed,ed,ed,ed,ed,ed,ed,ed,ed");
		grid.enableResizing("false,true,false,false,false,true,false,false,false,true");
		grid.enableTooltips("false,false,false,false,false,false,false,false,false,false");
		grid.setColSorting("str,str,str,str,str,str,str,str,str,str");
		
		grid.enableMultiselect("true"); 
		grid.enableBlockSelection("false");
		grid.enableUndoRedo();
		grid.enableSmartRendering(true, 100);
		
		grid.setColumnHidden(7,true);
		grid.setColumnHidden(8,true);
		grid.setColumnHidden(9,true);
		
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
		DataProcessor = new dataProcessor("saveEntrprsMber.do");
		DataProcessor.setTransactionMode("POST", true);
		DataProcessor.setUpdateMode("off");
		DataProcessor.enableDataNames(true);
		//DataProcessor.enablePartialDataSend(true);
		DataProcessor.init(grid);
		
		DataProcessor.attachEvent("onAfterUpdateFinish", function() {
		    log("onAfterUpdateFinish", arguments);
		    return true;
		});
    }
	
	/**
	 * layout a cell
	 */
	function fn_layout_b() {
		var oProcB = oProc.cells("b");
		oProcB.hideHeader();
		var formData = [
			{ type: "settings", position: "label-left", labelWidth: 100, inputWidth: 120 },
			{ type: "input", label: "기업회원아이디", name: "entrprsmberId",           value: "" },
			{ type: "input", label: "회사명",        name: "cmpnyNm",                 value: "" },
			{ type: "input", label: "비밀번호",      name: "entrprsMberPassword",     value: "" },
			{ type: "input", label: "비밀번호확인",   name: "entrprsMberPassword2",    value: "" },
			{ type: "input", label: "비밀번호힌트",   name: "entrprsMberPasswordHint", value: "" },
			{ type: "input", label: "비밀번호정답",   name: "entrprsMberPasswordCnsr", value: "" },
			{ type: "input", label: "업종코드",      name: "indutyCode",              value: "" },
			{ type: "input", label: "기업구분코드",   name: "entrprsSeCode",           value: "" },
			{ type: "input", label: "대표이사이름",   name: "cxfc",                    value: "" },
			{ type: "input", label: "사업자등록번호", name: "bizrno",                  value: "" },
			{ type: "input", label: "법인등록번호",   name: "jurirno",                 value: "" },
			{ type: "label", label: "회사전화번호",   list: [
				{ type: "settings", labelWidth: 10, inputWidth: 50 },
				{ type: "input", name: "areaNo",                                     value: "" },
				{ type: "newcolumn" },
				{ type: "label", label: "-" },
				{ type: "newcolumn" },
				{ type: "input", name: "entrprsMiddleTelno",                         value: "" },
				{ type: "newcolumn" },
				{ type: "label", label: "-" },
				{ type: "newcolumn" },
				{ type: "input", name: "entrprsEndTelno",                            value: "" }] }
		];
		var form = oProcB.attachForm(formData);
	}
	
	/** 
	 * log
	 */
	function log(message, args) {
	    var div = document.createElement("div");
	    for (var i = 0; i < 3; i++) {
	        message += "<li>" + args[i];
	    };
	    message += "<br/>";
	    div.innerHTML = message;
	    document.getElementById('zoneA').appendChild(div);
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
    fn_layout_b();
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