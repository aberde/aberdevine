    whoya.common = function() {};
    
    /**
     * XML메일보기
     * @param data JSON형식의 데이터
     *   url: ""  // 경로
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
     * 첨부파일 다운로드
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