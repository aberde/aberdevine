/**
 * whoya form format
 */
	whoya.dhtmlx.form = {
		format: {}
	};
    
    /**
     * <pre>
     * form에 데이터만 출력시.
     * @param name  // form의 name
     * @param value  // form의 value
     * </pre>
     */
    whoya.dhtmlx.form.format.printData = function(name, value) {
    	return value;
    };

    /**
     * <pre>
     * XML메일보기 링크 만들기.
     * @param name  // form의 name
     * @param value  // form의 value
     * </pre>
     */
    whoya.dhtmlx.form.format.xmlMailViewLink = function(name, value) {
    	var xmlMailViewData = {
    		value: [value]
    	};
    	return "<a href='#' onclick='whoya.common.xmlMailView(" + JSON.stringify(xmlMailViewData) + ");return false;'>" + value + ".xml</a>";
    };

    /**
     * <pre>
     * 첨부파일 목록 가져오기.
     * @param name  // form의 name
     * @param value  // form의 value
     * </pre>
     */
    whoya.dhtmlx.form.format.getFileList = function(name, value) {
    	var fileList = "";
    	
    	$.ajax({
    		url: whoya.context + "/whoya/cmm/fms/selectFileInfs.do"
    		, async: false
    		, data: {
    			atchFileId: value
    		}
    		, success: function(data, textStatus, jqXHR) {
    			$.each(data, function() {
    				var fileDownloadData = {
			    		value: [this.atchFileId, this.fileSn]
			    	};
    				fileList += "<a href='#' onclick='whoya.common.fileDownload(" + JSON.stringify(fileDownloadData) + ");return false;' />" + this.orignlFileNm + " [ " + this.fileMg + " byte ]</a><br />";
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
    };

    /**
     * <pre>
     * 전화번호 입력폼 가져오기.(형식: areaNo-middleTelno-endTelno)
     * @param name  // form의 name
     * @param value  // form의 value
     * </pre>
     */
    whoya.dhtmlx.form.format.telInputForm = function(name, value) {
    	var form = "";
    	form = "<input type='text' id='areaNo' name='areaNo' class='dhxform_textarea' style='width: 40px;' />";
    	form += " - <input type='text' id='middleTelno' name='middleTelno' class='dhxform_textarea' style='width: 40px;' />";
    	form += " - <input type='text' id='endTelno' name='endTelno' class='dhxform_textarea' style='width: 40px;' />";
    	return form;
    };

    /**
     * <pre>
     * 이메일 입력폼 가져오기.(형식: emailAdres, emailAnswerAt)
     * @param name  // form의 name
     * @param value  // form의 value
     * </pre>
     */
    whoya.dhtmlx.form.format.emailInputForm = function(name, value) {
    	var form = "";
    	form = "<input type='text' id='emailAdres' name='emailAdres' class='dhxform_textarea' style='width: 170px;' /><br />";
    	form += "<input type='checkbox' id='emailAnswerAt' name='emailAnswerAt' value='Y' /><label for='emailAnswerAt'>이메일답변여부</label>";
    	return form;
    };

    /**
     * <pre>
     * 전화번호 출력폼 가져오기.(형식: areaNo-middleTelno-endTelno)
     * @param name  // form의 name
     * @param value  // form의 value
     * </pre>
     */
    whoya.dhtmlx.form.format.telPrintForm = function(name, value) {
    	var form = "";
    	form = "<span id='areaNo'></span>";
    	form += " - <span id='middleTelno'></span>";
    	form += " - <span id='endTelno'></span>";
    	return form;
    };
    
    /**
     * <pre>
     * 이메일 출력폼 가져오기.(형식: emailAdres, emailAnswerAt)
     * @param name  // form의 name
     * @param value  // form의 value
     * </pre>
     */
    whoya.dhtmlx.form.format.emailPrintForm = function(name, value) {
    	var form = "";
    	form = "<span id='emailAdres'></span><br />";
    	form += "<input type='checkbox' id='emailAnswerAt' name='emailAnswerAt' value='Y' disabled='disabled' /><label for='emailAnswerAt'>이메일답변여부</label>";
    	return form;
    };
    
    /**
     * <pre>
     * 템플릿 정보(tmplatId, tmplatNm)
     * @param name  // form의 name
     * @param value  // form의 value
     * </pre>
     */
    whoya.dhtmlx.form.format.tmplatInfo = function(name, value) {
    	var form = "";
    	form = "<input type='hidden' id='tmplatId' class='dhxform_textarea' style='width: 150px;' />";
    	form += "<input type='text' id='tmplatNm' name='tmplatNm' class='dhxform_textarea' style='width: 150px;' readonly='readonly' />";
    	form += " <a href='#' onclick='tmplatPopup();return false;'><img src='" + whoya.context + "/images/egovframework/com/cmm/icon/search.gif' alt='search'></a>";
    	return form;
    };
    
    /**
     * <pre>
     * 커뮤니티 관리자(emplyrId, emplyrNm)
     * @param name  // form의 name
     * @param value  // form의 value
     * </pre>
     */
    whoya.dhtmlx.form.format.emplyrInfo = function(name, value) {
    	var form = "";
    	form = "<input type='hidden' id='emplyrId' class='dhxform_textarea' style='width: 150px;' />";
    	form += "<input type='text' id='emplyrNm' name='emplyrNm' class='dhxform_textarea' style='width: 150px;' readonly='readonly' />";
    	form += " <a href='#' onclick='emplyrPopup();return false;'><img src='" + whoya.context + "/images/egovframework/com/cmm/icon/search.gif' alt='search'></a>";
    	return form;
    };

    /**
     * <pre>
     * 게시판 찾기(bbsId, bbsNm)
     * @param name  // form의 name
     * @param value  // form의 value
     * </pre>
     */
    whoya.dhtmlx.form.format.boardInfo = function(name, value) {
    	var form = "";
    	form = "<input type='hidden' id='bbsId' class='dhxform_textarea' style='width: 150px;' />";
    	form += "<input type='text' id='bbsNm' name='bbsNm' class='dhxform_textarea' style='width: 150px;' readonly='readonly' />";
    	form += " <a href='#' onclick='boardPopup();return false;'><img src='" + whoya.context + "/images/egovframework/com/cmm/icon/search.gif' alt='search'></a>";
    	return form;
    };
    
    /**
     * <pre>
     * 템플릿 유형 목록
     * @param name  // form의 name
     * @param value  // form의 value
     * </pre>
     */
    whoya.dhtmlx.form.format.qustnrTmplatList = function(name, value) {
    	var qustnrTmplatList = "";
    	
    	$.ajax({
    		url: whoya.context + "/whoya/uss/olp/qmc/selectQustnrTmplatList.do"
    		, async: false
    		, dataType: "json"
    		, success: function(data, textStatus, jqXHR) {
    			$.each(data, function(i) {
    				qustnrTmplatList += "<div style='margin-bottom: 5px;'><label for='qestnrTmplatId_" + i + "'><img src='" + whoya.context + "/uss/olp/qtm/EgovQustnrTmplatManageImg.do?qestnrTmplatId=" + this.qestnrTmplatId + "' align='middle' alt='템플릿유형 이미지' title='템플릿유형 이미지'><br /><input type='radio' id='qestnrTmplatId_" + i + "' name='qestnrTmplatId_rdo' value='" + this.qestnrTmplatId + "' />" + this.qestnrTmplatTy + "</label></div>";
    			});
    		}
    		, error: function(jqXHR, textStatus, errorThrown) {
    			console.log(jqXHR);
    			console.log(textStatus);
    			console.log(errorThrown);
    			alert(errorThrown);
    		}
    	});
    	return qustnrTmplatList;
    };

    /**
     * <pre>
     * 템플릿 유형
     * @param name  // form의 name
     * @param value  // form의 value
     * </pre>
     */
    whoya.dhtmlx.form.format.qustnrTmplat = function(name, value) {
    	return "<img src='" + whoya.context + "/uss/olp/qtm/EgovQustnrTmplatManageImg.do?qestnrTmplatId=" + value + "' align='middle' alt='템플릿유형 이미지' title='템플릿유형 이미지'>";
    };
    
    /**
     * <pre>
     * 보고대상자 목록(emplyrId, emplyrNm)
     * @param name  // form의 name
     * @param value  // form의 value
     * </pre>
     */
    whoya.dhtmlx.form.format.reportrInfo = function(name, value) {
    	var form = "";
    	form = "<input type='hidden' id='reportrId' class='dhxform_textarea' style='width: 150px;' />";
    	form += "<input type='text' id='reportrNm' name='reportrNm' class='dhxform_textarea' style='width: 150px;' readonly='readonly' />";
    	form += " <a href='#' onclick='reportrPopup();return false;'><img src='" + whoya.context + "/images/egovframework/com/cmm/icon/search.gif' alt='search'></a>";
    	return form;
    };
    
    /**
     * <pre>
     * 설문지정보(qestnrId, qestnrTmplatId, qestnrCn)
     * @param name  // form의 name
     * @param value  // form의 value
     * </pre>
     */
    whoya.dhtmlx.form.format.qestnrInfo = function(name, value) {
    	var form = "";
    	form = "<input type='hidden' id='qestnrId' class='dhxform_textarea' style='width: 150px;' />";
    	form += "<input type='hidden' id='qestnrTmplatId' class='dhxform_textarea' style='width: 150px;' />";
    	form += "<input type='text' id='qestnrCn' name='qestnrCn' class='dhxform_textarea' style='width: 150px;' />";
    	form += " <a href='#' onclick='qestnrPopup();return false;'><img src='" + whoya.context + "/images/egovframework/com/cmm/icon/search.gif' alt='search'></a>";
    	return form;
    };
    
    /**
     * <pre>
     * 설문문항정보(qestnrQesitmId, qestnrQesitmCn)
     * @param name  // form의 name
     * @param value  // form의 value
     * </pre>
     */
    whoya.dhtmlx.form.format.qestnrQesitmInfo = function(name, value) {
    	var form = "";
    	form = "<input type='hidden' id='qestnrQesitmId' class='dhxform_textarea' style='width: 150px;' />";
    	form += "<input type='text' id='qestnrQesitmCn' name='qestnrQesitmCn' class='dhxform_textarea' style='width: 150px;' />";
    	form += " <a href='#' onclick='qestnrQesitmPopup();return false;'><img src='" + whoya.context + "/images/egovframework/com/cmm/icon/search.gif' alt='search'></a>";
    	return form;
    };
    
    /**
     * <pre>
     * 웹에디터
     * @param name  // form의 name
     * @param value  // form의 value
     * </pre>
     */
    whoya.dhtmlx.form.format.webEditor = function(name, value) {
    	var form = "";
    	form += "<div id='" + name + "' style='width: 100%; height: 300px; border: #909090 1px solid;'></div>";
    	return form;
    };