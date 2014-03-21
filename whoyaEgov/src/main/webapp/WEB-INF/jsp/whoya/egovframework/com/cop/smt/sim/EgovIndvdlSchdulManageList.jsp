<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>일정관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/whoya/include/header.jsp" />
<script src="<c:out value='/dhtmlx/dhtmlxScheduler/codebase/dhtmlxscheduler.js' />" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="<c:out value='/dhtmlx/dhtmlxScheduler/codebase/dhtmlxscheduler.css' />" type="text/css" media="screen" title="no title" charset="utf-8">
<script type="text/javascript">
function init() {
	// #########################################
    // ## 스케쥴 등록폼 구성.
    // #########################################
	scheduler.locale.labels.section_schdulSe = "일정구분";
	scheduler.locale.labels.section_schdulIpcrCode = "중요도";
	scheduler.locale.labels.section_schdulDeptNameTp = "부서";
	scheduler.locale.labels.section_schdulNm = "일정명";
	scheduler.locale.labels.section_schdulCn = "일정 내용";
	scheduler.locale.labels.section_reptitSeCodeTp = "반복구분";
	scheduler.locale.labels.section_time = "날짜/시간";
	scheduler.locale.labels.section_schdulChargerNameTp = "담당자";
	scheduler.locale.labels.section_file_1Tp = "파일첨부";
	
	scheduler.form_blocks["schdulDeptNameTp"] = {
		render: function(sns) {
			// 부서폼 생성.
			var schdulDeptNameTp = "<div>";
			schdulDeptNameTp += "<input type='hidden' name='schdulDeptId' id='schdulDeptId' value='' />";
	        schdulDeptNameTp += "<input type='text' name='schdulDeptName' id='schdulDeptName' style='width: 580px;margin-left: 10px;' value='' readonly='readonly' />";
	        schdulDeptNameTp += "<a href='#' onclick='groupPopup();return false;'><img src='" + whoya.context + "/images/egovframework/com/cmm/icon/search.gif' alt='search'></a>";
	        schdulDeptNameTp += "</div>";
		    return schdulDeptNameTp;
		}
        , set_value: function(node, value, ev) {
            node.childNodes[0].value = "";
            node.childNodes[1].value = "";
        }
        , get_value: function(node, ev) {
            ev.schdulDeptId = node.childNodes[0].value;
            ev.schdulDeptName = node.childNodes[1].value;
            return node.childNodes[1].value;
        }
        , focus: function(node) {
            var a = node.childNodes[1];
            a.select();
            a.focus();
        }
    };

	scheduler.form_blocks["reptitSeCodeTp"] = {
		render: function(sns) {
			// 반복구분
	        var reptitSeCodeTp = "<div>";
	        reptitSeCodeTp += "<input type='radio' name='reptitSeCode' id='reptitSeCode1' value='1' style='margin-left: 10px;' /><label for='reptitSeCode1'>당일</label>";
	        reptitSeCodeTp += "<input type='radio' name='reptitSeCode' id='reptitSeCode2' value='2' /><label for='reptitSeCode2'>반복</label>";
	        reptitSeCodeTp += "<input type='radio' name='reptitSeCode' id='reptitSeCode3' value='3' /><label for='reptitSeCode3'>연속</label>";
	        reptitSeCodeTp += "</div>";
		    return reptitSeCodeTp;
		}
        , set_value: function(node, value, ev) {
       		$.each(node.childNodes, function() {
      			this.checked = false;
       		});
        }
        , get_value: function(node, ev) {
        	$.each(node.childNodes, function() {
                if ( this.checked ) {
                	ev.reptitSeCode = this.value;
                    return this.value;
                }
            });
        }
        , focus: function(node) {
            var a = node.childNodes[0];
            a.select();
            a.focus();
        }
    };

	scheduler.form_blocks["schdulChargerNameTp"] = {
		render: function(sns) {
			// 담당자폼 생성.
	        var schdulChargerNameTp = "<div>";
	        schdulChargerNameTp += "<input type='hidden' name='schdulChargerId' id='schdulChargerId' value='' />";
	        schdulChargerNameTp += "<input type='text' name='schdulChargerName' id='schdulChargerName' style='width: 580px;margin-left: 10px;' value='' readonly='readonly' />";
	        schdulChargerNameTp += "<a href='#' onclick='schdulChargerPopup();return false;'><img src='" + whoya.context + "/images/egovframework/com/cmm/icon/search.gif' alt='search'></a>";
	        schdulChargerNameTp += "</div>";
		    return schdulChargerNameTp;
		}
		, set_value: function(node, value, ev) {
	        node.childNodes[0].value = "";
	        node.childNodes[1].value = "";
	    }
	    , get_value: function(node, ev) {
	        ev.schdulChargerId = node.childNodes[0].value;
	        ev.schdulChargerName = node.childNodes[1].value;
	        return node.childNodes[1].value;
	    }
	    , focus: function(node) {
	        var a = node.childNodes[1];
	        a.select();
	        a.focus();
	    }
    };

	scheduler.form_blocks["file_1Tp"] = {
		render: function(sns) {
			// 파일첨부폼 생성.
	        var file_1Tp = "<div>";
	        file_1Tp += "<input type='file' name='file_1' id='file_1' value='' style='margin-left: 10px;' />";
	        file_1Tp += "</div>";
		    return file_1Tp;
		}
		, set_value: function(node, value, ev) {
	    }
	    , get_value: function(node, ev) {
	        ev.file_1Tp = node.childNodes[0].value;
	        return node.childNodes[0].value;
	    }
	    , focus: function(node) {
	        var a = node.childNodes[0];
	        a.select();
	        a.focus();
	    }
    };

	getSchdulSeCode();
	getSchdulIpcrCode();
	
    scheduler.config.lightbox.sections = [
        { type: "select", name: "schdulSe", map_to: "schdulSe", options: whoyaGlobalData.schdulSeOptions },
        { type: "select", name: "schdulIpcrCode", map_to: "schdulIpcrCode", options: whoyaGlobalData.schdulIpcrCodeOptions },
        { type: "schdulDeptNameTp", name: "schdulDeptNameTp", map_to: "schdulDeptNameTp" },
        { type: "textarea", name: "schdulNm", map_to: "schdulNm", height: 19 },
        { type: "textarea", name: "schdulCn", map_to: "schdulCn", height: 50 },
        { type: "reptitSeCodeTp", name: "reptitSeCodeTp", map_to: "reptitSeCodeTp" },
        { type: "time", name: "time", map_to: "auto", time_format: ["%Y", "%m", "%d", "%H:%i"] },
        { type: "schdulChargerNameTp", name: "schdulChargerNameTp", map_to: "schdulChargerNameTp" },
        { type: "file_1Tp", name: "file_1Tp", map_to: "file_1Tp" }
    ];
    
    // 등록폼 버튼 이벤트
    scheduler.attachEvent("onEventAdded", function(id, ev){
        var schdulDate = scheduler.formSection('time').getValue();

        var data = {
			schdulSe: scheduler.formSection('schdulSe').getValue()
			, schdulIpcrCode: scheduler.formSection('schdulIpcrCode').getValue()
			, schdulDeptId: $("#schdulDeptId").val()
			, schdulDeptName: $("#schdulDeptName").val()
			, schdulNm: scheduler.formSection('schdulNm').getValue()
			, schdulCn: scheduler.formSection('schdulCn').getValue()
			, reptitSeCode: $("input[name='reptitSeCode']:checked").val()
			, schdulBgnde: schdulDate.start_date.format("yyyyMMddHHmm")
			, schdulEndde: schdulDate.end_date.format("yyyyMMddHHmm")
			, schdulChargerId: $("#schdulChargerId").val()
			, schdulChargerName: $("#schdulChargerName").val()
			, schdulKindCode: "2"
		};
      
        $.ajaxFileUpload({
			url: "<c:url value='/whoya/cop/smt/sim/EgovIndvdlSchdulManageRegistActor.do' />"
			, secureuri: false
			, fileElementId: ["file_1"]
			, data: data
			, dataType: 'json'
			, success: function (data, status) {
			    if ( data.status == "SUCCESS" ) {
			        alert("저장하였습니다.");
			        scheduler.clearAll();
			        search();
			    } else {
			        alert("저장에 실패하였습니다.");
			    }
			}
			, error: function (data, status, e) {
			    alert(e);
			}
		});
    });

    // 수정폼 버튼 이벤트
    scheduler.attachEvent("onEventChanged", function(id, ev){
        var schdulDate = scheduler.formSection('time').getValue();

        var data = {
       		schdulId: id
			, schdulSe: scheduler.formSection('schdulSe').getValue()
			, schdulIpcrCode: scheduler.formSection('schdulIpcrCode').getValue()
			, schdulDeptId: $("#schdulDeptId").val()
			, schdulDeptName: $("#schdulDeptName").val()
			, schdulNm: scheduler.formSection('schdulNm').getValue()
			, schdulCn: scheduler.formSection('schdulCn').getValue()
			, reptitSeCode: $("input[name='reptitSeCode']:checked").val()
			, schdulBgnde: schdulDate.start_date.format("yyyyMMddHHmm")
			, schdulEndde: schdulDate.end_date.format("yyyyMMddHHmm")
			, schdulChargerId: $("#schdulChargerId").val()
			, schdulChargerName: $("#schdulChargerName").val()
			, schdulKindCode: "2"
		};
      
        $.ajaxFileUpload({
			url: "<c:url value='/whoya/cop/smt/sim/EgovIndvdlSchdulManageModifyActor.do' />"
			, secureuri: false
			, fileElementId: ["file_1"]
			, data: data
			, dataType: 'json'
			, success: function (data, status) {
			    if ( data.status == "SUCCESS" ) {
			        alert("저장하였습니다.");
			        scheduler.clearAll();
			        search();
			    } else {
			        alert("저장에 실패하였습니다.");
			    }
			}
			, error: function (data, status, e) {
			    alert(e);
			}
		});
    });

    // 삭제폼 버튼 이벤트
    scheduler.attachEvent("onEventDeleted", function(id, ev){
        $.ajax({
            url: "<c:url value='/whoya/cop/smt/sim/EgovIndvdlSchdulManageDeleteActor.do' />"
            , data: {
                schdulId: id
            }
            , success: function(data, textStatus, jqXHR) {
            	if ( data.status == "SUCCESS" ) {
                    alert("저장하였습니다.");
                    scheduler.clearAll();
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
    });
    // #########################################
    
    scheduler.showLightbox = function (a){
    	if ( a ) {
    		if ( this.callEvent("onBeforeLightbox",[a]) ) {
    			var b = this.getLightbox();
    			this.showCover(b);
    			this._fill_lightbox(a,b);
    			this.callEvent("onLightbox",[a]);
    			
    			$.ajax({
    		        url: "<c:url value='/whoya/cop/smt/sim/EgovIndvdlSchdulManageDetail.do' />"
    		        , data: {
    		        	schdulId: a
    		        }
    		        , success: function(data, textStatus, jqXHR) {
    		            $.each(data, function(idx) {
    		            	scheduler.formSection('schdulSe').setValue(this.schdulSe);
    		                scheduler.formSection('schdulIpcrCode').setValue(this.schdulIpcrCode);
    		                $("#schdulDeptId").val(this.schdulDeptId);
    		                $("#schdulDeptName").val(this.schdulDeptName);
    		                scheduler.formSection('schdulNm').setValue(this.schdulNm);
    		                scheduler.formSection('schdulCn').setValue(this.schdulCn);
    		                $("input[name='reptitSeCode'][value='" + this.reptitSeCode + "']").attr("checked", true);
    		                $("#schdulChargerId").val(this.schdulChargerId);
    		                $("#schdulChargerName").val(this.schdulChargerName);
    		            });
    		        }
    		        , error: function(jqXHR, textStatus, errorThrown) {
    		            console.log(jqXHR);
    		            console.log(textStatus);
    		            console.log(errorThrown);
    		            alert(errorThrown);
    		        }
    		    });
    		} else if ( this._new_event ) {
    			this._new_event=null;
    		}
    	}
    };
    
    // #########################################
    // ## 스케쥴 표 생성.
    // #########################################
	scheduler.config.xml_date="%Y-%m-%d %H:%i";
    scheduler.init('scheduler',new Date(),"month");

    search();
    // #########################################
}

/**
 * 일정관리 조회
 */
function search() {
	var $data = [];
    $.ajax({
        url: "<c:url value='/whoya/cop/smt/sim/EgovIndvdlSchdulManageMonthList.do' />"
        , async: false
        , success: function(data, textStatus, jqXHR) {
            $.each(data, function(idx) {
                $data.push({
                    id: this.schdulId
                    , start_date: this.schdulBgnde.substring(0, 4) + "-" + this.schdulBgnde.substring(4, 6) + "-" + this.schdulBgnde.substring(6, 8) + " " + this.schdulBgnde.substring(8, 10) + " " + this.schdulBgnde.substring(10, 12)
                    , end_date: this.schdulEndde.substring(0, 4) + "-" + this.schdulEndde.substring(4, 6) + "-" + this.schdulEndde.substring(6, 8) + " " + this.schdulEndde.substring(8, 10) + " " + this.schdulEndde.substring(10, 12)
                    , text: this.schdulNm
                });
            });
        }
        , error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
            alert(errorThrown);
        }
    });
    
    scheduler.parse($data, "json");
}


/**
 * 일정구분 목록 가져오기.
 */
function getSchdulSeCode() {
    $.ajax({
        url: "<c:url value='/whoya/cop/tpl/selectTemplateCodeList.do' />"
       	, async: false
        , dataType: "json"
        , data: {
            codeId: "COM030"
        }
        , success: function(data, textStatus, jqXHR) {
        	var schdulSeOptions = [{key: "", label: "선택"}];
            $.each(data, function() {
            	schdulSeOptions.push({key: this.code, label: this.codeNm});
            });
            whoyaGlobalData.schdulSeOptions = schdulSeOptions;
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
 * 중요도 목록 가져오기.
 */
function getSchdulIpcrCode() {
    $.ajax({
        url: "<c:url value='/whoya/cop/tpl/selectTemplateCodeList.do' />"
       	, async: false
        , dataType: "json"
        , data: {
            codeId: "COM019"
        }
        , success: function(data, textStatus, jqXHR) {
        	var schdulIpcrCodeOptions = [{key: "", label: "선택"}];
            $.each(data, function() {
            	schdulIpcrCodeOptions.push({key: this.code, label: this.codeNm});
            });
            whoyaGlobalData.schdulIpcrCodeOptions = schdulIpcrCodeOptions;
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
    
    $("body").live({
        mousedown: function() {
            $("div.dhx_modal_cover_dv").css("z-index", "20000");
            $("div.dhtmlx_window_active").css("z-index", "20001");
        }
    });
});
</script>
</head>
<body>
    <div id="scheduler" class="dhx_cal_container" style='width:100%; height:100%;'>
        <div class="dhx_cal_navline">
            <div class="dhx_cal_prev_button">&nbsp;</div>
            <div class="dhx_cal_next_button">&nbsp;</div>
            <div class="dhx_cal_today_button"></div>
            <div class="dhx_cal_date"></div>
            <div class="dhx_cal_tab" name="day_tab" style="right:204px;"></div>
            <div class="dhx_cal_tab" name="week_tab" style="right:140px;"></div>
            <div class="dhx_cal_tab" name="month_tab" style="right:76px;"></div>
        </div>
        <div class="dhx_cal_header">
        </div>
        <div class="dhx_cal_data">
        </div>
    </div>
</body>
</html>
