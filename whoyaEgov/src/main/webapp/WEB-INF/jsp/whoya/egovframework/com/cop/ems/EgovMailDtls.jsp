<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>발송메일내역</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/whoya/include/header.jsp" />

<script type="text/javascript">
var oProc;
var form;

function init() {
	dhtmlx.image_path = "<c:url value='/dhtmlx/dhtmlx_pro_full/imgs/'/>";
    oProc = new dhtmlXLayoutObject(document.body, "2U");
    oProc.dhxWins.setEffect("move", true);
    
    var toolbar;
    var combo;
    var grid;
	
    /** 
	 * 상세화면 formData UI 구성.
	 */
	function detailFormData() {
		var formData = [
   			{ type: "fieldset", name: "formField", label: "발송메일 상세조회", list: [
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 150, inputWidth: 170 },
   					{ type: "label", label: "보내는사람" },
   					{ type: "newcolumn" },
   					{ type: "input", name: "tmplatNm", value: "", readonly: true }
   				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 150, inputWidth: 170 },
   					{ type: "label", label: "받는사람" },
   					{ type: "newcolumn" },
   					{ type: "select", name: "tmplatSeCode", options: [
      						{ value: "", text: "--선택하세요--" }
      					] }
   				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 150, inputWidth: 170 },
   					{ type: "label", label: "템플릿경로" },
   					{ type: "newcolumn" },
   					{ type: "input", name: "tmplatCours", value: "" }
   				] },
   				{ type: "block", list: [
   					{ type: "settings", labelWidth: 70, inputWidth: 170, position: "label-right" },
   					{ type: "label", label: "사용여부", labelWidth: 150 },
   					{ type: "newcolumn" },
   					{ type: "radio", label: "사용", name: "useAt", value: "Y" },
   					{ type: "newcolumn" },
   					{ type: "radio", label: "미사용", name: "useAt", value: "N" }
   				] },
   				{ type: "block", list: [
   					{ type: "button", name: "uptBtn", value: "수정" }
   				] }
   			] }
   		];
		
		var oProcB = oProc.cells("b");
		oProcB.hideHeader();
		form = oProcB.attachForm(formData);
		form.setFontSize("11px");
		
		getTmplatSeCode();
		
		// 버튼 클릭 이벤트.
		form.attachEvent("onButtonClick", function(name) {
			if ( name == "uptBtn" ) {
				if ( confirm("저장하시겠습니까?") ) {
					document.getElementById("activeStatusBar").innerHTML = "";
					
					var DataProcessor = new dataProcessor("updateTemplateInf.do");
					DataProcessor.setTransactionMode("POST", false);
					DataProcessor.setUpdateMode("off");
					DataProcessor.enableDataNames(true);
					//DataProcessor.enablePartialDataSend(true);
					DataProcessor.init(form);
					
					DataProcessor.attachEvent("onAfterUpdateFinish", function() {
						alert("저장하였습니다.");
						form = oProcB.attachForm("");
						search();
					});
					
					DataProcessor.sendData();
				}
			}
		});
	}
    
    /**
     * 툴바 생성.
     */
    function fn_toolbar() {
		toolbar = oProc.attachToolbar(); /*툴바*/
		toolbar.setIconsPath(dhtmlx.image_path);
	
		toolbar.addText("searchCondition", 1, "");
		toolbar.addInput("searchKeyword", 2, "", 200);
		toolbar.addSeparator("button_Separator", 3);
	
		// selectBox 생성
		var comboDIV = toolbar.objPull[toolbar.idPrefix+"searchCondition"].obj;
		toolbar.objPull[toolbar.idPrefix+"searchCondition"].obj.innerHTML = "";
		combo = new dhtmlXCombo(comboDIV,"alfa",140);
		combo.addOption([
       		["", "--선택하세요--"]
       		, ["1", "제목"]
       		, ["2", "내용"]
       		, ["3", "보낸이"]
       	]);
		combo.selectOption(0);
	
		var hideBtn = {
			btn_Undo: false
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
			if(id == "btn_Append"){
				location.href = "/whoya/cop/ems/insertSndngMailView.do";
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
		$.post( 'selectSndngMailJSONList.do'
			  , { searchCondition : combo.getSelectedValue()
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
    
	/**
	 * layout a cell
	 */
    function fn_layout_a() {
		var oProcA = oProc.cells("a");
		oProcA.setWidth($(document).width() / 10 * 7);
		oProcA.hideHeader();
		
		grid = oProcA.attachGrid();
		grid.setIconsPath(dhtmlx.image_path);
		
		grid.setHeader("상태,발신자,수신자,제목,날짜");
		grid.setColumnIds("sndngResultCode,dsptchPerson,recptnPerson,sj,sndngDe");
		grid.setInitWidths("100,100,150,*,100");
		grid.setColAlign("center,center,center,center,center");
		grid.setColTypes("ro,ro,ro,ro,ro");
		grid.enableResizing("false,true,false,false,false");
		grid.enableTooltips("false,false,false,false,false");
		grid.setColSorting("str,str,str,str,str");
		
		grid.enableMultiselect("true"); 
		grid.enableBlockSelection("false");
		grid.enableUndoRedo();
		grid.enableSmartRendering(true, 100);
		
// 		grid.setColumnHidden(6,true);
		
		grid.init();
	
		grid.attachEvent("onRowSelect", function(id, ind) {
			updateFormData();
			$.ajax({
				url: "/whoya/cop/tpl/selectTemplateInf.do"
				, data: {
					tmplatId: grid.cells(id, 6).getValue()
				}
				, success: function(data, textStatus, jqXHR) {
					form.setFormData(data);
				}
				, error: function(jqXHR, textStatus, errorThrown) {
					console.log(jqXHR);
					console.log(textStatus);
					console.log(errorThrown);
					alert(errorThrown);
				}
			});
		});
    }
		
	/**
	 * layout a cell
	 */
	function fn_layout_b() {
		var oProcB = oProc.cells("b");
		oProcB.hideHeader();
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