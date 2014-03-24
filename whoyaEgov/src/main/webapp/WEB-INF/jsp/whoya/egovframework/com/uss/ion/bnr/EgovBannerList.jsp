<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>배너관리</title>
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
    whoyaGlobalData.toolbar.addText("", 1, "배너명");
    whoyaGlobalData.toolbar.addInput("searchKeyword", 2, "", 200);

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
        , setHeader: "배너 명,링크 URL,배너 설명,반영여부,배너아이디"
        , setColumnIds: "bannerNm,linkUrl,bannerDc,reflctAt,bannerId"
        , setInitWidths: "100,150,*,100,100"
        , setColAlign: "center,center,center,center,center"
        , setColTypes: "ro,ro,ro,ro,ro"
        , enableResizing: "false,true,false,false,false"
        , enableTooltips: "false,false,false,false,false"
        , setColSorting: "str,str,str,str,str"
        , setColumnHidden: [
            { id: 4 }
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
            { type: "fieldset", name: "formField", label: "배너 등록", list: [
                { type: "settings", labelWidth: 150, inputWidth: 170 },
                { type: "input", label: "배너명", name: "bannerNm", value: "" },
                { type: "input", label: "링크URL", name: "linkUrl", value: "" },
                { type: "file", label: "배너이미지", name: "file_1", value: "" },
                { type: "input", label: "배너설명", name: "bannerDc", value: "" },
                { type: "input", label: "정렬순서", name: "sortOrdr", value: "" },
                { type: "select", label: "반영여부", name: "reflctAt", options: [
                    { value: "Y", text: "Y" }
                    , { value: "N", text: "N" }
                ] },
                { type: "button", name: "regBtn", value: "등록" }
            ] }
        ]
    };

    // 수정폼.
    whoyaGlobalData.bCellUpdateFormData = {
        cell: whoyaGlobalData.bCell
        , formData: [
            { type: "fieldset", name: "formField", label: "배너 수정", list: [
                { type: "settings", labelWidth: 150, inputWidth: 170 },
                { type: "input", label: "배너명", name: "bannerNm", value: "" },
                { type: "input", label: "링크URL", name: "linkUrl", value: "" },
                { type: "file", label: "배너이미지", name: "file_1", value: "" },
                { type: "input", label: "배너설명", name: "bannerDc", value: "" },
                { type: "input", label: "정렬순서", name: "sortOrdr", value: "" },
                { type: "select", label: "반영여부", name: "reflctAt", options: [
                    { value: "Y", text: "Y" }
                    , { value: "N", text: "N" }
                ] },
                { type: "template", label: "등록일시", name: "regDate", format: whoya.dhtmlx.form.format.printData },
                { type: "template", list: [
	                { type: "button", name: "uptBtn", value: "수정" }
	                , { type: "newcolumn" }
	                , { type: "button", name: "dltBtn", value: "삭제" }
                ]}
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
        whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellUpdateFormData);
        formEvent();
        
        $.ajax({
            url: "<c:url value='/whoya/uss/ion/bnr/getBanner.do' />"
            , data: {
            	bannerId: whoyaGlobalData.aGrid.cells(id, 4).getValue()
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
                
                $.ajaxFileUpload({
                    url: "<c:url value='/whoya/uss/ion/bnr/addBanner.do' />"
                    , secureuri: false
                    , fileElementId: ["file_1"]
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
        } else if ( name == "uptBtn" ) {  // 수정
            if ( confirm("저장하시겠습니까?") ) {
                document.getElementById("activeStatusBar").innerHTML = "";
                
                $.ajaxFileUpload({
                    url: "<c:url value='/whoya/uss/ion/bnr/updtBanner.do' />"
                    , secureuri: false
                    , fileElementId: ["file_1"]
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
                    url: "<c:url value='/whoya/uss/ion/bnr/removeBanner.do' />"
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
        url: "<c:url value='/whoya/uss/ion/bnr/selectBannerListJSON.do' />"
        , data: {
        	searchCondition : "1"
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
</body>
</html>
