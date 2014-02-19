/**
 * whoya 공통
 */
	whoya.common = {};
    
    /**
     * <pre>
     * XML메일보기
     * @param data JSON형식의 데이터
     *   url: ""  // 경로
     *   param: param  // 배열로 된 데이터명
     *   value: value  // 배열로 된 데이터값
     * </pre>
     */
    whoya.common.xmlMailView = function(data) {
    	var whoyaData = {
    		url: "/whoya/cop/ems/selectSndngMailXml.do"
    		, param: ["mssageId"]
    		, value: [""]
    	};
    	$.extend(whoyaData, data);
    	
    	var queryString = "";
    	$.each(whoyaData.param, function(i, paramValue) {
    		if ( i == 0 ) {
    			queryString = "?" + paramValue + "=" + whoyaData.value[i];
    		} else {
    			queryString += "&" + paramValue + "=" + whoyaData.value[i];
    		}
    	});
    	
    	window.location = whoya.context + whoyaData.url + queryString;
    };

    /**
     * <pre>
     * 첨부파일 다운로드
     * @param data JSON형식의 데이터
     *   url: ""  // 경로
     *   param: param  // 배열로 된 데이터명
     *   value: value  // 배열로 된 데이터값
     * </pre>
     */
    whoya.common.fileDownload = function(data) {
    	var whoyaData = {
    		url: "/whoya/cmm/fms/FileDown.do"
			, param: ["atchFileId", "fileSn"]
    		, value: ["", ""]
    	};
    	$.extend(whoyaData, data);

    	var queryString = "";
    	$.each(whoyaData.param, function(i, paramValue) {
    		if ( i == 0 ) {
    			queryString = "?" + paramValue + "=" + whoyaData.value[i];
    		} else {
    			queryString += "&" + paramValue + "=" + whoyaData.value[i];
    		}
    	});
    	
    	window.location = whoya.context + whoyaData.url + queryString;
    };
    
    /**
	 * 템플릿 정보(팝업)에서 선택된 값 저장.
	 * @pram tmplatId 템플릿 아이디
	 * @pram tmplatNm 템플릿 명
	 */
    whoya.common.tmplatSelect = function(tmplatId, tmplatNm) {
    	whoyaGlobalData.bForm.setItemValue("tmplatId", tmplatId);
		$("#tmplatNm").val(tmplatNm);
		whoyaGlobalData.tmplatPopupWindows.close();
	};
	
	/**
	 * 커뮤니티 관리자 정보(팝업)에서 선택된 값 저장.
	 * @pram emplyrId 사용자 아이디
	 * @pram emplyrNm 사용자 명
	 */
    whoya.common.emplyrSelect = function(emplyrId, emplyrNm) {
    	whoyaGlobalData.bForm.setItemValue("emplyrId", emplyrId);
    	$("#emplyrNm").val(emplyrNm);
    	whoyaGlobalData.emplyrPopupWindows.close();
	};