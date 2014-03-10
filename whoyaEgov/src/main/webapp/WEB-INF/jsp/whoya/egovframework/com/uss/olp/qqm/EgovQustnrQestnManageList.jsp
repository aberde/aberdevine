<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>질문관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/whoya/include/header.jsp" />

<script type="text/javascript">
function init() {
    // #########################################
    // ## 레이아웃생성
    // #########################################
    whoyaGlobalData.layout = whoya.dhtmlx.layout.init();
    // #########################################
    
    
    // #########################################
    // ## 툴바 생성
    // #########################################
    var toolbarData = {
        layout: whoyaGlobalData.layout
    };
    whoyaGlobalData.toolbar = whoya.dhtmlx.layout.toolbar.init(toolbarData);
    whoyaGlobalData.toolbar.addText("searchCondition", 1, "");
    whoyaGlobalData.toolbar.addInput("searchKeyword", 2, "", 200);

    // selectBox 생성
    var comboDIV = whoyaGlobalData.toolbar.objPull[whoyaGlobalData.toolbar.idPrefix+"searchCondition"].obj;
    whoyaGlobalData.toolbar.objPull[whoyaGlobalData.toolbar.idPrefix+"searchCondition"].obj.innerHTML = "";
    whoyaGlobalData.combo = new dhtmlXCombo(comboDIV,"alfa",140);
    whoyaGlobalData.combo.addOption([
        ["", "--선택하세요--"]
        , ["QESTN_CN", "질문내용"]
        , ["MXMM_CHOISE_CO", "최대선택건수"]
    ]);
    whoyaGlobalData.combo.selectOption(0);
    
    // toolbar의 Button정의
    var toolbarAddButton = {
        toolbar: whoyaGlobalData.toolbar
        , btn_Open: true
        , btn_Append: true
    };
    whoya.dhtmlx.layout.toolbar.addButton(toolbarAddButton);
    toolbarEvent();
    // #########################################

    
    // #########################################
    // ## layout cell a 
    // #########################################
    var aCellData = {
        layout: whoyaGlobalData.layout
    };
    // 화면 layout의 해당 cell 정의 
    whoyaGlobalData.aCell = whoya.dhtmlx.layout.cell.init(aCellData);
    // #########################################
    
    
    // #########################################
    // ## layout cell a에 grid생성
    // #########################################
    var aCellGridData = {
        cell: whoyaGlobalData.aCell
        , setHeader: "순번,질문내용,질문유형,질문항목,통계,등록자,등록일자,설문문항 ID"
        , setColumnIds: "no,qestnCn,qestnTyCode,qustnrItem,qustnrStatistics,frstRegisterNm,frstRegisterPnttm,qestnrQesitmId"
        , setInitWidths: "100,*,100,100,100,100,100,100"
        , setColAlign: "center,center,center,center,center,center,center,center"
        , setColTypes: "ro,ro,ro,img,img,ro,ro,ro"
        , enableResizing: "false,false,false,false,false,false,false,false"
        , enableTooltips: "false,false,false,false,false,false,false,false"
        , setColSorting: "str,str,str,str,str,str,str,str"
        , setColumnHidden: [
            { id: 7 }
        ]
    };
    // 화면 layout cell a에 dhtmlxGrid 객체 생성.
    whoyaGlobalData.aGrid = whoya.dhtmlx.layout.cell.grid(aCellGridData);
    gridEvent();
    // #########################################
    
    
    // #########################################
    // ## layout cell b
    // #########################################
    var bCellData = {
        cell_target: "b"
        , layout: whoyaGlobalData.layout
        , width: ""
    };
    // 화면 layout의 해당 cell 정의 
    whoyaGlobalData.bCell = whoya.dhtmlx.layout.cell.init(bCellData);
    // #########################################
    
    
    // #########################################
    // ## layout cell b에 form생성
    // #########################################
    // 등록 폼
    whoyaGlobalData.bCellRegFormData = {
        cell: whoyaGlobalData.bCell
        , formData: [
            { type: "fieldset", name: "formField", label: "설문문항 등록", list: [
                { type: "settings", labelWidth: 150, inputWidth: 170 },
                { type: "template", label: "설문지정보(제목)", name: "qestnrCn", format: whoya.dhtmlx.form.format.qestnrInfo },
                { type: "input", label: "질문순번", name: "qestnSn" },
                { type: "select", label: "질문유형", name: "qestnTyCode", options: [
                    { value: "", text: "선택" }
                ] },
                { type: "input", label: "질문 내용", name: "qestnCn", value: "", rows: 3 },
                { type: "select", label: "최대선택건수", name: "mxmmChoiseCo", options: [
                    { value: "1", text: "1" },
                    { value: "2", text: "2" },
                    { value: "3", text: "3" },
                    { value: "4", text: "4" },
                    { value: "5", text: "5" },
                    { value: "6", text: "6" },
                    { value: "7", text: "7" },
                    { value: "8", text: "8" },
                    { value: "9", text: "9" },
                    { value: "10", text: "10" }
                ] },
                { type: "button", name: "regBtn", value: "등록" }
            ] }
        ]
    };

    // 상세폼.
    whoyaGlobalData.bCellDetailFormData = {
        cell: whoyaGlobalData.bCell
        , formData: [
            { type: "fieldset", name: "formField", label: "설문문항 상세보기", list: [
                { type: "settings", labelWidth: 150, inputWidth: 170 },
                { type: "template", label: "설문지정보(제목)", name: "qestnrSj", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "질문순번", name: "qestnSn", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "질문유형", name: "qestnTyCode", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "질문 내용", name: "qestnCn", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "최대선택건수", name: "mxmmChoiseCo", format: whoya.dhtmlx.form.format.printData },
                { type: "template", list: [
                    { type: "button", name: "uptViewBtn", value: "수정" },
                    { type: "newcolumn" },
                    { type: "button", name: "dltBtn", value: "삭제" }
                ] }
            ] }
        ]
    };

    // 수정폼.
    whoyaGlobalData.bCellUpdateFormData = {
        cell: whoyaGlobalData.bCell
        , formData: [
            { type: "fieldset", name: "formField", label: "설문문항 수정", list: [
                { type: "settings", labelWidth: 150, inputWidth: 170 },
                { type: "template", label: "설문지정보(제목)", name: "qestnrSj", format: whoya.dhtmlx.form.format.printData },
                { type: "input", label: "질문순번", name: "qestnSn" },
                { type: "select", label: "질문유형", name: "qestnTyCode", options: [
                    { value: "", text: "선택" }
                ] },
                { type: "input", label: "질문 내용", name: "qestnCn", value: "", rows: 3 },
                { type: "select", label: "최대선택건수", name: "mxmmChoiseCo", options: [
                    { value: "1", text: "1" },
                    { value: "2", text: "2" },
                    { value: "3", text: "3" },
                    { value: "4", text: "4" },
                    { value: "5", text: "5" },
                    { value: "6", text: "6" },
                    { value: "7", text: "7" },
                    { value: "8", text: "8" },
                    { value: "9", text: "9" },
                    { value: "10", text: "10" }
                ] },
                { type: "button", name: "uptBtn", value: "수정" }
            ] }
        ]
    };
    
    // 통계폼.
    whoyaGlobalData.bCellStatisticsFormData = {
        cell: whoyaGlobalData.bCell
        , formData: [
            { type: "fieldset", name: "formField", label: "설문문항 통계", list: [
                { type: "settings", labelWidth: 150, inputWidth: 170 },
                { type: "template", label: "설문지정보(제목)", name: "qestnrSj", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "질문순번", name: "qestnSn", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "질문유형", name: "qestnTyCode", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "질문 내용", name: "qestnCn", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "최대선택건수", name: "mxmmChoiseCo", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "설문항목 결과", name: "statisticsListIemCn", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "응답자답변내용 결과", name: "statisticsList2RespondAnswerCn", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "기타답변내용 결과", name: "statisticsList2EtcAnswerCn", format: whoya.dhtmlx.form.format.printData }
            ] }
        ]
    };
    // #########################################
    
    
    // #########################################
    // ## layout에 statusbar 생성
    // #########################################
    var statusbarData = {
        layout: whoyaGlobalData.layout  
    };
    whoyaGlobalData.statusbar = whoya.dhtmlx.statusbar(statusbarData);
    // #########################################
}


// #######################################################################
// ## event 생성
// #######################################################################
// toolbar event 생성
function toolbarEvent() {
    whoyaGlobalData.toolbar.attachEvent("onClick", function(id) {
        if(id == "btn_Open"){
            search();
            whoyaGlobalData.bCell.attachForm("");
        }
        if(id == "btn_Append"){
            // 화면 layout cell b에 dhtmlxForm 객체 생성.
            whoyaGlobalData.bCell.attachForm("");
            whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellRegFormData);
            formEvent();
            getQestnTyCode();
        }
    });
}

// grid event 생성
function gridEvent() {
    whoyaGlobalData.aGrid.attachEvent("onRowSelect", function(id, ind) {
    	if ( ind != 4 ) {  // 통계컬럼은 제외.
	        whoyaGlobalData.bCell.attachForm("");
	        // 화면 layout cell b에 dhtmlxForm 객체 생성.
	        whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellDetailFormData);
	        formEvent();
	        
	        $.ajax({
	            url: "<c:url value='/whoya/uss/olp/qqm/EgovQustnrQestnManageDetail.do' />"
	            , type: "POST"
	            , data: {
	            	qestnrQesitmId: whoyaGlobalData.aGrid.cells(id, 7).getValue()
	            }
	            , success: function(data, textStatus, jqXHR) {
	                whoyaGlobalData.bForm.setFormData(data);
	            }
	            , error: function(jqXHR, textStatus, errorThrown) {
	                console.log(jqXHR);
	                console.log(textStatus);
	                console.log(errorThrown);
	                alert(errorThrown);
	            }
	        });
    	}
    });
}

// form event 생성
function formEvent() {
    whoyaGlobalData.bForm.attachEvent("onButtonClick", function(name) {
        if ( name == "regBtn" ) {  // 등록
            if ( confirm("저장하시겠습니까?") ) {
                document.getElementById("activeStatusBar").innerHTML = "";
                
                var data = whoyaGlobalData.bForm.getFormData(true);
                data.qestnrCn = $("#qestnrCn").val();
                data.qestnrId = $("#qestnrId").val();
                data.qestnrTmplatId = $("#qestnrTmplatId").val();
                
                $.ajax({
                    url: "<c:url value='/whoya/uss/olp/qqm/EgovQustnrQestnManageRegist.do' />"
                    , data: data
                    , type: "POST"
                    , dataType: "json"
                    , success: function(data, textStatus, jqXHR) {
                        if ( data.status == "SUCCESS" ) {
                            alert("저장하였습니다.");
                            whoyaGlobalData.bForm = whoyaGlobalData.bCell.attachForm("");
                            search();
                        } else {
                            alert("저장에 실패하였습니다.");
                        }
                    }
                    , error: function(jqXHR, textStatus, errorThrown) {
                        console.log(jqXHR);
                        console.log(textStatus);
                        console.log(errorThrown);
                        alert(errorThrown);
                    }
                });
            }
        } else if ( name == "uptViewBtn" ) {  // 수정화면이동.
            $.ajax({
                url: "<c:url value='/whoya/uss/olp/qqm/EgovQustnrQestnManageDetail.do' />"
                , type: "POST"
                , data: {
                	qestnrQesitmId: whoyaGlobalData.bForm.getFormData().qestnrQesitmId
                }
                , success: function(data, textStatus, jqXHR) {
                    // 화면 layout cell b에 dhtmlxForm 객체 생성.
                    whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellUpdateFormData);
                    formEvent();
                    getQestnTyCode();
                    whoyaGlobalData.bForm.setFormData(data);
                }
                , error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR);
                    console.log(textStatus);
                    console.log(errorThrown);
                    alert(errorThrown);
                }
            });
        } else if ( name == "uptBtn" ) {  // 수정
            if ( confirm("저장하시겠습니까?") ) {
                document.getElementById("activeStatusBar").innerHTML = "";
                
                $.ajax({
                    url: "<c:url value='/whoya/uss/olp/qqm/EgovQustnrQestnManageModify.do' />"
                    , data: whoyaGlobalData.bForm.getFormData()
                    , type: "POST"
                    , dataType: "json"
                    , success: function(data, textStatus, jqXHR) {
                        if ( data.status == "SUCCESS" ) {
                            alert("저장하였습니다.");
                            whoyaGlobalData.bForm = whoyaGlobalData.bCell.attachForm("");
                            search();
                        } else {
                            alert("저장에 실패하였습니다.");
                        }
                    }
                    , error: function(jqXHR, textStatus, errorThrown) {
                        console.log(jqXHR);
                        console.log(textStatus);
                        console.log(errorThrown);
                        alert(errorThrown);
                    }
                });
            }
        } else if ( name == "dltBtn" ) {  // 삭제
            if ( confirm("삭제하시겠습니까?") ) {
                $.ajax({
                    url: "<c:url value='/whoya/uss/olp/qqm/EgovQustnrQestnManageDelete.do' />"
                    , type: "POST"
                    , data: {
                    	qestnrQesitmId: whoyaGlobalData.bForm.getFormData().qestnrQesitmId
                    }
                    , success: function(data, textStatus, jqXHR) {
                        if ( data.status == "SUCCESS" ) {
                            alert("저장하였습니다.");
                            search();
                            whoyaGlobalData.bCell.attachForm("");
                        } else {
                            alert("저장에 실패하였습니다.");
                        }
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
// #######################################################################


/**
 * 조회버튼 클릭시
 */
function search() {
    whoyaGlobalData.layout.progressOn();
    whoyaGlobalData.aGrid.clearAll();
    document.getElementById("activeStatusBar").innerHTML = "";

    $.ajax({
        url: "<c:url value='/whoya/uss/olp/qqm/EgovQustnrQestnManageListJSON.do' />"
        , data: {
            searchCondition : whoyaGlobalData.combo.getSelectedValue()
            , searchKeyword : whoyaGlobalData.toolbar.getValue("searchKeyword")
        }
        , success: function(data, textStatus, jqXHR) {
            whoyaGlobalData.aGrid.attachEvent("onXLE", function(){
                whoyaGlobalData.layout.progressOff();
            });
            
            whoyaGlobalData.aGrid.clearAll();
            whoyaGlobalData.aGrid.parse(data.list, "json");
            whoyaGlobalData.aGrid.setSelectedRow(0);
            document.getElementById("activeStatusBar").innerHTML = "조회되었습니다";
        }
        , error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
            alert(errorThrown);
        }
    });
}

/**
 * 질문유형 목록 가져오기.
 */
function getQestnTyCode() {
    $.ajax({
        url: "<c:url value='/whoya/uss/olp/qqm/selectQestnTyCodeList.do' />"
        , async: false
        , dataType: "json"
        , data: {
            codeId: "COM018"
        }
        , success: function(data, textStatus, jqXHR) {
            var qestnTyCode = whoyaGlobalData.bForm.getOptions("qestnTyCode");
            $.each(data, function() {
            	qestnTyCode.add(new Option($(this)[0].codeNm, $(this)[0].code));
            });
        }
        , error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
            alert(errorThrown);
        }
    });
}


$(function(document) {
    init();
});
</script>
</head>
<body>
</body>
</html>
