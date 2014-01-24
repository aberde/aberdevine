<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>인사기록부관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/include/header.jsp" />

<script type="text/javascript">
function init() {
	dhtmlx.image_path = "<c:url value='/dhtmlx/dhtmlx_pro_full/imgs/'/>";
	
    var oProc = new dhtmlXLayoutObject(document.body, '2E');
	var oProcA = oProc.cells('a'); oProcA.hideHeader(); 
	var oProcB = oProc.cells('b');

	formData = [{ type: "settings", position: "absolute", labelAlign: "right", labelWidth: 80, inputWidth: 120 }
	           ,{ type: "input"   , label: "사원번호", labelTop:  5, labelLeft:   0, labelWidth: 50, inputTop:  5, inputLeft:  60, inputWidth:  60 }
	           ,{ type: "input"   , label:    "성명", labelTop:  5, labelLeft: 120, labelWidth: 50, inputTop:  5, inputLeft: 180, inputWidth:  60 }
	           ,{ type: "combo", label: "재직구분", labelTop:  5, labelLeft: 316, labelWidth: 50, inputTop:  5, inputLeft: 376, inputWidth:  80, connector:"HRI0010Rc_jgicCd.do" }
	           ,{ type: "combo", label:    "직급", labelTop:  5, labelLeft: 450, labelWidth: 50, inputTop:  5, inputLeft: 512, inputWidth:  80, connector:"HRI0010Rc_rolCd.do"}
	           ,{ type: "input"   , label:    "직명", labelTop:  5, labelLeft: 590, labelWidth: 50, inputTop:  5, inputLeft: 652, inputWidth: 120 }
	           ,{ type: "input"   , label: "부서코드", labelTop: 25, labelLeft:   0, labelWidth: 50, inputTop: 25, inputLeft:  60, inputWidth:  60 }
	           ,{ type: "input"   , label:  "부서명", labelTop: 25, labelLeft: 120, labelWidth: 50, inputTop: 25, inputLeft: 180, inputWidth: 120 }
	           ,{ type: "input"   , label: "입사일자", labelTop: 25, labelLeft: 316, labelWidth: 50, inputTop: 25, inputLeft: 376, inputWidth: 60 }
	           ,{ type: "input"   , label: "-"     , labelTop: 25, labelLeft: 450, labelWidth: 50, inputTop: 25, inputLeft: 512, inputWidth: 60 }
	           ,{ type: "input"   , label: "직원구분", labelTop: 25, labelLeft: 590, labelWidth: 50, inputTop: 25, inputLeft: 652, inputWidth: 120 }
               ];
	var myFormCond = oProcA.attachForm();
	myFormCond.loadStruct(formData, "json");
	myFormCond.setFontSize("11px");
	//oProcA.vs[myFormCond].dhxcont.style.backgroundColor = "#ffe0e0";
	/*
	$.post( "COI0010Rc_dptCd.do"
	      , function(data, status, xhr){
	    	  combo.addOption(data.list); 
	    	  combo.setComboValue("");
	    	  comboDataPut(grid, 6, data.list); 
		    }
	      , "json"
	).error(function(x,s,t) {httpError(x, s, t);});
	*/
    var hrLayout = oProcB.attachLayout('3L');
	var hrLayoutA = hrLayout.cells('a'); hrLayoutA.hideHeader();
	var hrLayoutB = hrLayout.cells('b'); hrLayoutB.hideHeader();
	var hrLayoutC = hrLayout.cells('c'); hrLayoutC.hideHeader();
	
	formData = [{ type:"settings", position:"absolute", labelAlign:"right", labelWidth:80, inputWidth:100 }
               ,{ type:"container",     name:"PNGDIR", inputTop: 5, inputLeft:10, inputWidth:50  }
               ,{ type:"button",   value:" 사진업로드 ",  name:"picUpload", inputTop: 150, inputLeft:10, width:114  }
               ,{ type:"input"    , label:"      사원번호" , name:"EMPNO"     , labelTop: 5, labelLeft:120, labelWidth:60, inputTop: 5, inputLeft:190, inputWidth:80, required: true }
               ,{ type:"input"    , label:"    성명(한글)" , name:"EMPNMKO", labelTop: 5, labelLeft:280, labelWidth:60, inputTop: 5, inputLeft:350, inputWidth:80, required: true }
               ,{ type:"calendar", label:"      입사일자" , name:"INTDD"      , labelTop: 5, labelLeft:480, labelWidth:70, inputTop: 5, inputLeft:560, inputWidth:64, dateFormat:"%Y-%m-%d" }
	           ,{ type:"input"    , label:"          (한자)" , name:"EMPNMHN", labelTop:25, labelLeft:120, labelWidth:60, inputTop:25, inputLeft:190, inputWidth:80 }
	           ,{ type:"input"    , label:"          (영문)" , name:"EMPNMEN" , labelTop:25, labelLeft:280, labelWidth:60, inputTop:25, inputLeft:350, inputWidth:120 }
			   ,{ type:"radio"    , label:"        성별 남" , name:"SEX"           , labelTop:25, labelLeft:524, labelWidth:40, inputTop:20, inputLeft:565, inputWidth:10, value:"X" }     
			   ,{ type:"radio"    , label:"               여" , name:"SEX"           , labelTop:25, labelLeft:590, labelWidth:10, inputTop:20, inputLeft:602, inputWidth:10, value:"Y" }     
			   ,{ type:"input"    , label:"      주민번호" , name:"RSNO"        , labelTop:45, labelLeft:120, labelWidth:60, inputTop:45, inputLeft:190, inputWidth:100 }
			   ,{ type:"calendar", label:"      생년월일" , name:"BIRDD"       , labelTop:45, labelLeft:280, labelWidth:60, inputTop:45, inputLeft:350, inputWidth:64, dateFormat:"%Y-%m-%d" }
			   ,{ type:"radio"    , label:"양음구분 양력", name:"BIRDV"        , labelTop:45, labelLeft:495, labelWidth:80, inputTop:40, inputLeft:580, inputWidth:10, value:"1" }     
			   ,{ type:"radio"    , label:"             음력", name:"BIRDV"        , labelTop:45, labelLeft:600, labelWidth:30, inputTop:40, inputLeft:630, inputWidth:10, value:"2" }     
			   ,{ type:"combo" , label:"       근무부서", name:"WRKDPTCD" , labelTop:65, labelLeft:120, labelWidth:60, inputTop:65, inputLeft:190, inputWidth:100, connector: "HRI0010Rc_dptCd.do" }
			   ,{ type:"combo" , label:"             직위", name:"POSID"        , labelTop:65, labelLeft:280, labelWidth:60, inputTop:65, inputLeft:350, inputWidth:64, connector:"HRI0010Rc_rolCd.do" }
			   ,{ type:"input"    , label:"       직원구분", name:"WRKDV"      , labelTop:65, labelLeft:480, labelWidth:70, inputTop:65, inputLeft:560, inputWidth:100 }
			   ,{ type:"input"    , label:"             직급", name:"JGBID"         , labelTop:85, labelLeft:120, labelWidth:60, inputTop:85, inputLeft:190, inputWidth:80 }
			   ,{ type:"input"    , label:"             직군", name:"RGNID"        , labelTop:85, labelLeft:280, labelWidth:60, inputTop:85, inputLeft:350, inputWidth:120 }
			   ,{ type:"input"    , label:"    기간제구분", name:"DNDDV"      , labelTop:85, labelLeft:480, labelWidth:70, inputTop:85, inputLeft:560, inputWidth:100 }
			   ,{ type:"input"    , label:"             직무", name:"ROLID"        , labelTop:105, labelLeft:120, labelWidth:60, inputTop:105, inputLeft:190, inputWidth:80 }
			   ,{ type:"input"    , label:"             직명", name:"RNMID"       , labelTop:105, labelLeft:280, labelWidth:60, inputTop:105, inputLeft:350, inputWidth:120 }
			   ,{ type:"input"    , label:" 탄력근무유형", name:"TANDV"       , labelTop:105, labelLeft:480, labelWidth:70, inputTop:105, inputLeft:560, inputWidth:100 }
			   ,{ type:"input"    , label:"       채용구분", name:"RCUDV"       , labelTop:125, labelLeft:120, labelWidth:60, inputTop:125, inputLeft:190, inputWidth:80 }
			   ,{ type:"input"    ,                               name:"RCUCNTN"    , inputTop:125, inputLeft:280, inputWidth:180 }
			   ,{ type:"calendar", label:" 현직급임용일", name:"ROLSETDD"  , labelTop:125, labelLeft:480, labelWidth:70, inputTop:125, inputLeft:560, inputWidth:64, dateFormat:"%Y-%m-%d" }
			   ,{ type:"calendar", label:"       퇴직일자", name:"OUTDD"       , labelTop:145, labelLeft:120, labelWidth:60, inputTop:145, inputLeft:190, inputWidth:64, dateFormat:"%Y-%m-%d" }
			   ,{ type:"input"    , label:"       퇴직사유", name:"OUTCNTN"    , labelTop:145, labelLeft:280, labelWidth:60, inputTop:145, inputLeft:350, inputWidth:120 }
			   ,{ type:"calendar", label:" 현부서발령일", name:"DPTSETDD"  , labelTop:145, labelLeft:480, labelWidth:70, inputTop:145, inputLeft:560, inputWidth:64, dateFormat:"%Y-%m-%d" }
			   ,{ type:"combo"  , label:"       겸임부서", name:"ADDDPTCD", labelTop:165, labelLeft:120, labelWidth:60, inputTop:165, inputLeft:190, inputWidth:100, connector: "HRI0010Rc_dptCd.do" }
			   ,{ type:"combo"  , label:"       지원부서", name:"SUPDPTCD" , labelTop:165, labelLeft:280, labelWidth:60, inputTop:165, inputLeft:350, inputWidth:100, connector: "HRI0010Rc_dptCd.do" }
			   ,{ type:"combo"  , label:"       파견부서", name:"SENDPTCD" , labelTop:165, labelLeft:480, labelWidth:70, inputTop:165, inputLeft:560, inputWidth:100, connector: "HRI0010Rc_dptCd.do" }
			   ,{ type:"input"    , label:"       우편번호", name:"POSTNO"     , labelTop:185, labelLeft:120, labelWidth:60, inputTop:185, inputLeft:190, inputWidth:40 }
			   ,{ type:"input"    , label:"       자택주소", name:"ADDR1"       , labelTop:185, labelLeft:280, labelWidth:60, inputTop:185, inputLeft:350, inputWidth:150 }
			   ,{ type:"input"    ,                               name:"ADDR2"       , labelTop:185, inputTop:185, inputLeft:505, inputWidth:155 }
			   ,{ type:"input"    , label:"       휴대전화", name:"HPTELNO"    , labelTop:205, labelLeft:120, labelWidth:60, inputTop:205, inputLeft:190, inputWidth:90 }
			   ,{ type:"input"    , label:"       회사전화", name:"COTELNO"    , labelTop:205, labelLeft:280, labelWidth:60, inputTop:205, inputLeft:350, inputWidth:90 }
			   ,{ type:"input"    , label:"       내선번호", name:"LNTELNO"    , labelTop:205, labelLeft:480, labelWidth:70, inputTop:205, inputLeft:560, inputWidth:90 }
			   ];
	var myFormGrid = hrLayoutB.attachForm();
	myFormGrid.loadStruct(formData, "json");
	myFormGrid.setFontSize("11px");
	
	hideInputForm(myFormGrid, formData);
	
	toolbar = oProc.attachToolbar(); /*툴바*/
	toolbar.setIconsPath(dhtmlx.image_path);

	toolbar.addText("labelDptNm", 1, "소속부서");
	toolbar.addText("comboDptCd", 2, ""); 
	toolbar.addSeparator("button_Separator", 3); 
	toolbar.addText("labelEmpNm", 4, "성명");
	toolbar.addInput("inputEmpNm", 5, "", 80); 
	toolbar.addSeparator("button_Separator", 6); 

	var comboDIV = toolbar.objPull[toolbar.idPrefix+"comboDptCd"].obj;
	toolbar.objPull[toolbar.idPrefix+"comboDptCd"].obj.innerHTML = "";
	var combo = new dhtmlXCombo(comboDIV,"alfa",140);
	
	$.post( "HRI0010Rc_dptCd2.do"
		      , function(data, status, xhr){
		    	  combo.addOption(data.list); /*소속부서콤보*/
		    	  combo.setComboValue("");
		    	  comboDataPut(grid, 17, data.list); /*소속부서그리드콤보*/
			    }
		      , "json"
	).error(function(x,s,t) {httpError(x, s, t);});

	/*기능버튼*/
	comToolbarButton(toolbar);
	toolbar.attachEvent("onClick", function(id){
		if(id == "btn_Open"){  /* 조회 */ 
			//document.getElementById("activeStatusBar").innerHTML = "";
			$.post( "HRI0010Rs.do"
				  , { wrkDptCd: combo.getSelectedValue()
				     ,empNmKo: toolbar.getValue("inputEmpNm")
				    }
			      , function(data, status, xhr){
			    		grid.clearAll();
			    		grid.parse(data.list,"json");
			    		if ( grid.getRowsNum() > 0 ) {
				    		grid.setSelectedRow(1);
				    		showInputForm(myFormGrid, formData);
			    		}
				    }
			      , "json"
			 ).error(function(x,s,t) {httpError(x, s, t);});
		}
		if(id == "btn_Append"){  /* 추가 */
			if(grid.getRowIndex(grid.getSelectedRowId()) < 0){ grid.addRow(grid.getUID(), ",,,"); }
           	else { grid.addRowAfter(grid.getUID(), ",,,", grid.getSelectedRowId()); }
			if ( grid.getSelectedRowId() ) {
				grid.selectRow(parseInt(grid.getSelectedRowId()));
			} else {
				grid.selectRow(grid.getRowsNum()-1);
			}
			showInputForm(myFormGrid, formData);
		}	
		if(id == "btn_Delete") comEditGrid(grid, "Delete");	 /* 삭제 */
		if(id == "btn_Undo") comEditGrid(grid, "Undo");	/* 취소 */
		if(id == "btn_Save"){  /* 저장 */
			if ( myFormGrid.validate() ) {
				document.getElementById("activeStatusBar").innerHTML = "";
				dProcessor.sendData();
			}
			//myFormGrid.save(grid.getSelectedRowId());
		}		
		if(id == "btn_Print"){ grid.printView(); /* 인쇄 */
		}
    });

	var grid = hrLayoutA.attachGrid();
	oProcA.setHeight("50");
	hrLayoutA.setWidth("330");
	hrLayoutB.setHeight("250");
	grid.setIconsPath(dhtmlx.image_path);

	grid.setHeader(
			        "사원번호,성명(한글),성명(한자),성명(영문),주민번호,사진경로,성별,생년월일,양음구분,결혼여부,결혼일자,"
			      +"직위ID,직급ID,직무ID,호봉,직군ID,직명ID,근무부서코드,겸임부서코드,지원부서코드,파견부서코드,이메일,회사전화번호,내선전화번호,휴대전화번호,자택전화번호,"
                  +"우편번호,주소1,주소2,근무구분,기간제구분,탄력근무구분,채용구분,채용내역,현직급임용일,현부서발령일,재직구분,"
                  +"입사일자,퇴사일자,퇴직사유"
	);
	grid.setColumnIds(
			        "EMPNO,EMPNMKO,EMPNMHN,EMPNMEN,RSNO,PNGDIR,SEX,BIRDD,BIRDV,MARYN,MARDD,"
			      +"POSID,JGBID,ROLID,HBG,RGNID,RNMID,WRKDPTCD,ADDDPTCD,SUPDPTCD,SENDPTCD,EMAIL,COTELNO,LNTELNO,HPTELNO,HMTELNO,"
                  +"POSTNO,ADDR1,ADDR2,WRKDV,DNDDV,TANDV,RCUDV,RCUCNTN,ROLSETDD,DPTSETDD,HOLDV,"
                  +"INTDD,OUTDD,OUTCNTN"
    );
	grid.setInitWidths(
		            "60,70,70,70,84,80,40,60,60,60,60,"
			      +"60,60,60,60,60,60,120,120,120,120,60,80,80,80,80,"
			      +"60,60,60,60,80,80,60,60,80,80,60,"
			      +"60,60,60"
    );	
	grid.setColTypes(
			        "co,ed,ed,ro,ro,ro,ro,dhxCalendar,ro,ro,dhxCalendar,"
			      +"ro,ro,ro,ro,ro,ro,co,co,co,co,ro,ro,ro,ro,ro,"
			      +"ro,ro,ro,ro,ro,ro,ro,ro,dhxCalendar,dhxCalendar,ro,"
			      +"dhxCalendar,dhxCalendar,ro"
	);
	grid.setColAlign("left,center,left,center,left,left,left,left,left,left,left,"
			        +"left,left,left,center,left,left,left,left,left,left,left,left,left,left,left,"
			        +"left,left,left,center,left,left,left,left,left,left,left,"
			        +"left,left,left"
	);
	grid.enableResizing("false,false,false,false,false,false,false,false,false,false,false,"
			           +"false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,"			
			           +"false,false,false,false,false,false,false,false,false,false,false,"			
			           +"false,false,false"			
	);
	grid.enableTooltips("false,false,false,false,false,false,false,false,false,false,false,"
    				   +"false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,"			
                       +"false,false,false,false,false,false,false,false,false,false,false,"			
                       +"false,false,false"
    );                   
	grid.setColSorting("str,str,str,str,str,str,str,date,str,str,date,"
			          +"str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,"
			          +"str,str,str,str,str,str,str,str,date,date,str,"
			          +"date,date,str"
	);

	grid.enableMultiselect("true"); 
	grid.enableBlockSelection("false");
	grid.enableUndoRedo();
	grid.enableSmartRendering(true, 100);
	grid.setDateFormat("%Y-%m-%d");
	grid.init();
	
	//grid.enableLightMouseNavigation(true); /* 핫입력 */
	
	dProcessor = new dataProcessor("HRI0010Ru.do");
	dProcessor.setTransactionMode("POST", true);
	dProcessor.setUpdateMode("off");
	dProcessor.enableDataNames(true);
	dProcessor.init(grid);

	myFormGrid.bind(grid);
	grid.attachEvent("onBeforeSelect", function(new_row,old_row) {
		myFormGrid.getContainer("PNGDIR").innerHTML = "<img src='"+grid.cells(new_row, 5).getValue()+"'/>"; 
		return true;
    });
	
	myFormGrid.attachEvent("onChange", function(name, val, inp) {
		grid.cells(grid.getSelectedRowId(), grid.getColIndexById(name)).setValue(val);
		dProcessor.setUpdated(grid.getSelectedRowId(),true);
    });
	myFormGrid.attachEvent("onInputChange", function(name, val, inp) {
		grid.cells(grid.getSelectedRowId(), grid.getColIndexById(name)).setValue(val);
		dProcessor.setUpdated(grid.getSelectedRowId(),true);
    });
	
	
	var myTabbar = hrLayoutC.attachTabbar();
	myTabbar.setHrefMode("iframes-on-demand");
	
	myTabbar.addTab("tab01", "기타", "50");
	myTabbar.addTab("tab02", "발령", "50");
	myTabbar.addTab("tab03", "가족", "50");
	myTabbar.addTab("tab04", "학력", "50");
	myTabbar.addTab("tab05", "경력", "50");
	myTabbar.addTab("tab06", "병역", "50");
	myTabbar.addTab("tab07", "자격면허", "70");
	myTabbar.addTab("tab08", "교육", "50");
	myTabbar.addTab("tab09", "포상", "50");
	myTabbar.addTab("tab10", "징계", "50");
	myTabbar.addTab("tab11", "외국어", "60");
	myTabbar.addTab("tab12", "업무분장", "70");

	//var grid1 = myTabbar.cells("tab01").attachForm();	
	//grid1.loadStruct(formData, "json");
	
	toolbar2 = myTabbar.cells("tab02").attachToolbar(); /*툴바*/
	toolbar2.setIconsPath(dhtmlx.image_path);

	var grid2 = myTabbar.cells("tab02").attachGrid();
	grid2.setHeader("부서,사원번호,성명,생년월일");
	grid2.setColumnIds("DPTCD,EMPNO,EMPNMKO,BIRDD");
	grid2.setInitWidths("120,60,60,70");	
	grid2.setColTypes("co,ro,ro,ro");
	grid2.setColAlign("left,center,left,center");
	grid2.enableResizing("false,false,false,false");
	grid2.enableTooltips("false,false,false,false");
	grid2.setColSorting("str,str,str,str");

	grid2.enableMultiselect("true"); 
	grid2.enableBlockSelection("false")
	grid2.enableUndoRedo();
	grid2.enableSmartRendering(true, 100);
	grid2.init();
	
	grid2.enableLightMouseNavigation(true); /* 핫입력 */
	
	/* 하단 상태바 */
	var main_status = oProc.attachStatusBar();
	main_status.setText("<div><table><td id='activeImg'></td><td id='activeStatusBar' valign='middle'></td></table></div>"); 
}

function hrPic(name, value) {
	return "<div><table><td id='activeImg'><img src='/whoya/dhtmlx/dhtmlx_pro_full/imgs/run_exc.gif'></td><td id='activeStatusBar' valign='middle'></td></table></div>";
}

// 입력폼 숨기기.
function hideInputForm(myFormGrid, formData) {
	$.each(formData, function(idx, data) {
		if ( data.type == "radio" ) {
			myFormGrid.hideItem(data.name, data.value);
		} else {
			myFormGrid.hideItem(data.name);
		}
	});
}

//입력폼 보이기.
function showInputForm(myFormGrid, formData) {
	$.each(formData, function(idx, data) {
		if ( data.type == "radio" ) {
			myFormGrid.showItem(data.name, data.value);
		} else {
			myFormGrid.showItem(data.name);
		}
	});
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