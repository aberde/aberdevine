<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>응답자관리</title>
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
        , ["RESPOND_NM", "응답자명"]
        , ["BRTH", "생년월일"]
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
        , setHeader: "순번,설문지정보(제목),응답자명,등록자,등록일자,설문응답자아이디"
        , setColumnIds: "no,qestnrSj,respondNm,frstRegisterNm,frstRegisterPnttm,qestnrRespondId"
        , setInitWidths: "100,*,100,100,100,100"
        , setColAlign: "center,center,center,center,center,center"
        , setColTypes: "ro,ro,ro,ro,ro,ro"
        , enableResizing: "false,false,false,false,false,false"
        , enableTooltips: "false,false,false,false,false,false"
        , setColSorting: "str,str,str,str,str,str"
        , setColumnHidden: [
            { id: 5 }
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
            { type: "fieldset", name: "formField", label: "응답자정보 등록", list: [
                { type: "settings", labelWidth: 150, inputWidth: 170 },
                { type: "template", label: "설문지정보", name: "qestnrCn", format: whoya.dhtmlx.form.format.qestnrInfo },
                { type: "select", label: "성별", name: "sexdstnCode", options: [
                    { value: "", text: "선택" }
                ] },
                { type: "select", label: "직업", name: "occpTyCode", options: [
                    { value: "", text: "선택" }
                ] },
                { type: "calendar", label: "생년월일", name: "brth", value: "" },
                { type: "input", label: "응답자명", name: "respondNm", value: "" },
                { type: "template", label: "전화번호", format: whoya.dhtmlx.form.format.telInputForm },
                { type: "button", name: "regBtn", value: "등록" }
            ] }
        ]
    };

    // 상세폼.
    whoyaGlobalData.bCellDetailFormData = {
        cell: whoyaGlobalData.bCell
        , formData: [
            { type: "fieldset", name: "formField", label: "응답자정보 상세보기", list: [
                { type: "settings", labelWidth: 150, inputWidth: 170 },
                { type: "template", label: "설문관리정보", name: "qestnrSj", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "성별", name: "sexdstnCode", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "직업", name: "occpTyCode", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "생년월일", name: "brth", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "응답자명", name: "respondNm", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "전화번호", format: whoya.dhtmlx.form.format.telPrintForm },
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
            { type: "fieldset", name: "formField", label: "응답자정보 등록", list: [
                { type: "settings", labelWidth: 150, inputWidth: 170 },
                { type: "template", label: "설문지정보", name: "qestnrSj", format: whoya.dhtmlx.form.format.printData },
                { type: "select", label: "성별", name: "sexdstnCode", options: [
                    { value: "", text: "선택" }
                ] },
                { type: "select", label: "직업", name: "occpTyCode", options: [
                    { value: "", text: "선택" }
                ] },
                { type: "calendar", label: "생년월일", name: "brth", value: "" },
                { type: "input", label: "응답자명", name: "respondNm", value: "" },
                { type: "template", label: "전화번호", format: whoya.dhtmlx.form.format.telInputForm },
                { type: "button", name: "uptBtn", value: "수정" }
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
            getSexdstnCode();
            getOccpTyCode();
        }
    });
}

// grid event 생성
function gridEvent() {
    whoyaGlobalData.aGrid.attachEvent("onRowSelect", function(id, ind) {
        whoyaGlobalData.bCell.attachForm("");
        // 화면 layout cell b에 dhtmlxForm 객체 생성.
        whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellDetailFormData);
        formEvent();
        
        $.ajax({
            url: "<c:url value='/whoya/uss/olp/qrm/EgovQustnrRespondManageDetail.do' />"
            , type: "POST"
            , data: {
            	qestnrRespondId: whoyaGlobalData.aGrid.cells(id, 5).getValue()
            }
            , success: function(data, textStatus, jqXHR) {
            	data.brth = data.brthdy.substring(0, 4) + " - " + data.brthdy.substring(4, 6) + " - " + data.brthdy.substring(6);
                whoyaGlobalData.bForm.setFormData(data);
                $("#areaNo").text(data.areaNo);
                $("#middleTelno").text(data.middleTelno);
                $("#endTelno").text(data.endTelno);
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
                data.brth = data.brth.replace(/\-/gi, "");
                data.areaNo = $("#areaNo").val();
                data.middleTelno = $("#middleTelno").val();
                data.endTelno = $("#endTelno").val();
                
                $.ajax({
                    url: "<c:url value='/whoya/uss/olp/qrm/EgovQustnrRespondManageRegist.do' />"
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
                url: "<c:url value='/whoya/uss/olp/qrm/EgovQustnrRespondManageDetail.do' />"
                , type: "POST"
                , data: {
                	qestnrRespondId: whoyaGlobalData.bForm.getFormData().qestnrRespondId
                }
                , success: function(data, textStatus, jqXHR) {
                    // 화면 layout cell b에 dhtmlxForm 객체 생성.
                    whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellUpdateFormData);
                    formEvent();
                    getSexdstnCode();
                    getOccpTyCode();
                    data.brth = data.brthdy.substring(0, 4) + " - " + data.brthdy.substring(4, 6) + " - " + data.brthdy.substring(6);
                    whoyaGlobalData.bForm.setFormData(data);
                    $("#areaNo").val(data.areaNo);
                    $("#middleTelno").val(data.middleTelno);
                    $("#endTelno").val(data.endTelno);
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
                
                var data = whoyaGlobalData.bForm.getFormData(true);
                data.brth = data.brth.replace(/\-/gi, "");
                data.areaNo = $("#areaNo").val();
                data.middleTelno = $("#middleTelno").val();
                data.endTelno = $("#endTelno").val();
                
                $.ajax({
                    url: "<c:url value='/whoya/uss/olp/qrm/EgovQustnrRespondManageModify.do' />"
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
        } else if ( name == "dltBtn" ) {  // 삭제
            if ( confirm("삭제하시겠습니까?") ) {
                $.ajax({
                    url: "<c:url value='/whoya/uss/olp/qrm/EgovQustnrRespondManageDelete.do' />"
                    , type: "POST"
                    , data: {
                    	qestnrRespondId: whoyaGlobalData.bForm.getFormData().qestnrRespondId
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
        url: "<c:url value='/whoya/uss/olp/qrm/EgovQustnrRespondManageListJSON.do' />"
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
 * 성별 목록 가져오기.
 */
function getSexdstnCode() {
    $.ajax({
        url: "<c:url value='/whoya/uss/olp/qrm/selectSexdstnCodeList.do' />"
        , async: false
        , dataType: "json"
        , data: {
            codeId: "COM014"
        }
        , success: function(data, textStatus, jqXHR) {
            var sexdstnCode = whoyaGlobalData.bForm.getOptions("sexdstnCode");
            $.each(data, function() {
                sexdstnCode.add(new Option($(this)[0].codeNm, $(this)[0].code));
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

/**
 * 성별 목록 가져오기.
 */
function getOccpTyCode() {
    $.ajax({
        url: "<c:url value='/whoya/uss/olp/qrm/selectOccpTyCodeList.do' />"
        , async: false
        , dataType: "json"
        , data: {
            codeId: "COM034"
        }
        , success: function(data, textStatus, jqXHR) {
            var occpTyCode = whoyaGlobalData.bForm.getOptions("occpTyCode");
            $.each(data, function() {
                occpTyCode.add(new Option($(this)[0].codeNm, $(this)[0].code));
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
