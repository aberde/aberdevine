<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>공통분류코드</title>
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
        , ["1", "분류코드"]
        , ["2", "분류코드명"]
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
        , setHeader: "순번,분류코드,분류코드명,사용여부"
        , setColumnIds: "no,clCode,clCodeNm,useAt"
        , setInitWidths: "100,150,*,100"
        , setColAlign: "center,center,center,center"
        , setColTypes: "ro,ro,ro,ro"
        , enableResizing: "false,true,false,false"
        , enableTooltips: "false,false,false,false"
        , setColSorting: "str,str,str,str"
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
            { type: "fieldset", name: "formField", label: "공통분류코드 등록", list: [
                { type: "settings", labelWidth: 150, inputWidth: 170 },
                { type: "input", label: "분류코드", name: "clCode", value: "" },
                { type: "input", label: "분류코드명", name: "clCodeNm", value: "" },
                { type: "input", label: "분류코드설명", name: "clCodeDc", value: "" },
                { type: "select", label: "사용여부", name: "useAt", options: [
                    { value: "Y", text: "Yes" }
                    , { value: "N", text: "No" }
                ] },
                { type: "button", name: "regBtn", value: "등록" }
            ] }
        ]
    };
    
    // 상세 폼
    whoyaGlobalData.bCellDetailFormData = {
        cell: whoyaGlobalData.bCell
        , formData: [
            { type: "fieldset", name: "formField", label: "공통분류코드 상세보기", list: [
                { type: "settings", labelWidth: 150, inputWidth: 170 },
                { type: "template", label: "분류코드", name: "clCode", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "분류코드명", name: "clCodeNm", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "분류코드설명", name: "clCodeDc", format: whoya.dhtmlx.form.format.printData },
                { type: "template", label: "사용여부", name: "useAt", format: whoya.dhtmlx.form.format.printData },
                { type: "template", list: [
                    { type: "button", name: "uptViewBtn", value: "수정" }
                    , { type: "newcolumn" }
                    , { type: "button", name: "dltBtn", value: "삭제" }
                ]}
            ] }
        ]
    };

    // 수정폼.
    whoyaGlobalData.bCellUpdateFormData = {
        cell: whoyaGlobalData.bCell
        , formData: [
            { type: "fieldset", name: "formField", label: "공통분류코드 수정", list: [
                { type: "settings", labelWidth: 150, inputWidth: 170 },
                { type: "template", label: "분류코드", name: "clCode", format: whoya.dhtmlx.form.format.printData },
                { type: "input", label: "분류코드명", name: "clCodeNm", value: "" },
                { type: "input", label: "분류코드설명", name: "clCodeDc", value: "" },
                { type: "select", label: "사용여부", name: "useAt", options: [
                    { value: "Y", text: "Yes" }
                    , { value: "N", text: "No" }
                ] },
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
            whoyaGlobalData.bForm = whoyaGlobalData.bCell.attachForm("");
            search();
        }
        if(id == "btn_Append"){
            // 화면 layout cell b에 dhtmlxForm 객체 생성.
            whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellRegFormData);
            formEvent();
        }
    });
}

// grid event 생성
function gridEvent() {
    whoyaGlobalData.aGrid.attachEvent("onRowSelect", function(id, ind) {
        // 화면 layout cell b에 dhtmlxForm 객체 생성.
        whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellDetailFormData);
        formEvent();
        
        $.ajax({
            url: "<c:url value='/whoya/sym/ccm/ccc/EgovCcmCmmnClCodeDetail.do' />"
            , data: {
            	clCode: whoyaGlobalData.aGrid.cells(id, 1).getValue()
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
    });
}

// form event 생성
function formEvent() {
    whoyaGlobalData.bForm.attachEvent("onButtonClick", function(name) {
        if ( name == "regBtn" ) {  // 등록
            if ( confirm("저장하시겠습니까?") ) {
                document.getElementById("activeStatusBar").innerHTML = "";
                
                $.ajax({
                    url: "<c:url value='/whoya/sym/ccm/ccc/EgovCcmCmmnClCodeRegist.do' />"
                    , data: whoyaGlobalData.bForm.getFormData()
                    , dataType: 'json'
                    , success: function (data, status) {
                        if ( data.status == "SUCCESS" ) {
                            alert("저장하였습니다.");
                            whoyaGlobalData.bForm = whoyaGlobalData.bCell.attachForm("");
                            search();
                        } else {
                            alert("저장에 실패하였습니다.");
                        }
                    }
                    , error: function (data, status, e) {
                        alert(e);
                    }
                });
            }
        } else if ( name == "uptViewBtn" ) {  // 수정
            $.ajax({
                url: "<c:url value='/whoya/sym/ccm/ccc/EgovCcmCmmnClCodeDetail.do' />"
                , data: {
                	clCode: whoyaGlobalData.bForm.getFormData().clCode
                }
                , success: function(data, textStatus, jqXHR) {
                    // 화면 layout cell b에 dhtmlxForm 객체 생성.
                    whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellUpdateFormData);
                    formEvent();
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
                    url: "<c:url value='/whoya/sym/ccm/ccc/EgovCcmCmmnClCodeModify.do' />"
                    , data: whoyaGlobalData.bForm.getFormData()
                    , dataType: 'json'
                    , success: function (data, status) {
                        if ( data.status == "SUCCESS" ) {
                            alert("저장하였습니다.");
                            whoyaGlobalData.bForm = whoyaGlobalData.bCell.attachForm("");
                            search();
                        } else {
                            alert("저장에 실패하였습니다.");
                        }
                    }
                    , error: function (data, status, e) {
                        alert(e);
                    }
                });
            }
        } else if ( name == "dltBtn" ) {  // 삭제
            if ( confirm("삭제하시겠습니까?") ) {
                document.getElementById("activeStatusBar").innerHTML = "";
                
                $.ajax({
                    url: "<c:url value='/whoya/sym/ccm/ccc/EgovCcmCmmnClCodeRemove.do' />"
                    , data: whoyaGlobalData.bForm.getFormData()
                    , dataType: 'json'
                    , success: function (data, status) {
                        if ( data.status == "SUCCESS" ) {
                            alert("삭제하였습니다.");
                            whoyaGlobalData.bForm = whoyaGlobalData.bCell.attachForm("");
                            search();
                        } else {
                            alert("삭제에 실패하였습니다.");
                        }
                    }
                    , error: function (data, status, e) {
                        alert(e);
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
        url: "<c:url value='/whoya/sym/ccm/ccc/EgovCcmCmmnClCodeListJSON.do' />"
        , data: {
            searchCondition : whoyaGlobalData.combo.getSelectedValue()
            , searchKeyword : whoyaGlobalData.toolbar.getValue("searchKeyword")
        }
        , dataType: "json"
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


$(function(document) {
    init();
});
</script>
</head>
<body>
<form name="testFrm">
</form>
</body>
</html>
