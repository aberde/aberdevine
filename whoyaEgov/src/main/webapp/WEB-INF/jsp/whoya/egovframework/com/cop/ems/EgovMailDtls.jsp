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
    var DataProcessor;
	
    /** 
	 * 상세화면 formData UI 구성.
	 */
	function detailFormData() {
		var formData = [
   			{ type: "fieldset", name: "formField", label: "발송메일 상세조회", list: [
				{ type: "settings", labelWidth: 150, inputWidth: 170 },
   				{ type: "template", label: "보내는사람", name: "dsptchPerson", value: "", format: printData },
				{ type: "template", label: "받는사람", name: "recptnPerson", value: "", format: printData },
				{ type: "template", label: "제목", name: "sj", value: "", format: printData },
				{ type: "template", label: "발신 내용", name: "emailCn", value: "", format: printData },
				{ type: "template", label: "발송 결과", name: "sndngResultCode", value: "", format: printData },
				{ type: "template", label: "XML메일보기", name: "xmlMailView", value: "", format: xmlMailViewLink },
				{ type: "template", label: "첨부파일", name: "atchFileId", value: "", format: getFileList },
				{ type: "button", name: "dltBtn", value: "삭제" }
   			] }
   		];
		
		/**
		 * 일반 form 데이터만 출력시.
		 */
		function printData(name, value) {
			return value;
		}
		
		/**
		 * 첨부파일 목록 가져오기.
		 */
		function getFileList(name, value) {
			var fileList = "";
			
			$.ajax({
				url: "/whoya/cmm/fms/selectFileInfs.do"
				, async: false
				, data: {
					atchFileId: value
				}
				, success: function(data, textStatus, jqXHR) {
					$.each(data, function() {
						fileList += "<a href=\"#\" onclick=\"fileDownload('" + this.atchFileId + "', '" + this.fileSn + "');return false;\" />" + this.orignlFileNm + " [ " + this.fileMg + " byte ]</a><br />";
					});
				}
				, error: function(jqXHR, textStatus, errorThrown) {
					console.log(jqXHR);
					console.log(textStatus);
					console.log(errorThrown);
					alert(errorThrown);
				}
			});
			return fileList;
		}
		
		/**
		 * XML메일보기 링크 만들기.
		 */
		function xmlMailViewLink(name, value) {
			return "<a href=\"#\"' onclick=\"xmlMailView('" + value + "');\">" + value + ".xml</a>";
		}
		
		var oProcB = oProc.cells("b");
		oProcB.hideHeader();
		form = oProcB.attachForm(formData);
		form.setFontSize("11px");
		
		// 버튼 클릭 이벤트.
		form.attachEvent("onButtonClick", function(name) {
			if ( name == "dltBtn" ) {
				if ( confirm("삭제하시겠습니까?") ) {
					document.getElementById("activeStatusBar").innerHTML = "";
					
					$.ajax({
						url: "/whoya/cop/ems/deleteSndngMail.do"
						, data: form.getFormData()
						, success: function(data, textStatus, jqXHR) {
							form = oProcB.attachForm("");
							search();
						}
						, error: function(jqXHR, textStatus, errorThrown) {
							console.log(jqXHR);
							console.log(textStatus);
							console.log(errorThrown);
							alert(errorThrown);
						}
					});
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
			if(id == "btn_Delete") {
				if ( confirm("삭제하시겠습니까?") ) {
					
					document.getElementById("activeStatusBar").innerHTML = "";
					
					grid.deleteSelectedRows();
					
					DataProcessor.sendData();
				}
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
		
		grid.setHeader("상태,발신자,수신자,제목,날짜,메세지ID");
		grid.setColumnIds("sndngResultCode,dsptchPerson,recptnPerson,sj,sndngDe,mssageId");
		grid.setInitWidths("100,100,150,*,100,100");
		grid.setColAlign("center,center,center,center,center,center");
		grid.setColTypes("ro,ro,ro,ro,ro,ro");
		grid.enableResizing("false,true,false,false,false,false");
		grid.enableTooltips("false,false,false,false,false,false");
		grid.setColSorting("str,str,str,str,str,str");
		
		grid.enableMultiselect("true"); 
		grid.enableBlockSelection("false");
		grid.enableUndoRedo();
		grid.enableSmartRendering(true, 100);
		
		grid.setColumnHidden(5,true);
		
		grid.init();
	
		grid.attachEvent("onRowSelect", function(id, ind) {
			detailFormData();
			$.ajax({
				url: "/whoya/cop/ems/selectSndngMailDetail.do"
				, data: {
					mssageId: grid.cells(id, 5).getValue()
				}
				, success: function(data, textStatus, jqXHR) {
					// XML메일보기 데이터 입력.
					data.xmlMailView = data.mssageId;
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
		
		DataProcessor = new dataProcessor("deleteSndngMailList.do");
		DataProcessor.setTransactionMode("POST", true);
		DataProcessor.setUpdateMode("off");
		DataProcessor.enableDataNames(true);
		//DataProcessor.enablePartialDataSend(true);
		DataProcessor.init(grid);
		
		DataProcessor.attachEvent("onAfterUpdateFinish", function() {
			alert("저장하였습니다.");
			search();
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

/**
 * XML메일보기
 */
function xmlMailView(mssageId) {
	window.location = "/whoya/cop/ems/selectSndngMailXml.do?mssageId=" + mssageId;
}

/**
 * 첨부파일 다운로드
 */
function fileDownload(atchFileId, fileSn) {
	window.location = "/whoya/cmm/fms/FileDown.do?atchFileId=" + atchFileId + "&fileSn=" + fileSn;
}
</script>
</head>
<body>
</body>
</html>