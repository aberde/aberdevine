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
    		url: whoya.context + "/whoya/cop/ems/selectSndngMailXml.do"
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
    		url: whoya.context + "/whoya/cmm/fms/FileDown.do"
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
	
	/**
	 * 게시판 정보(팝업)에서 선택된 값 저장.
	 * @pram bbsId 게시판 아이디
	 * @pram bbsNm 게시판 명
	 */
	whoya.common.boardSelect = function(bbsId, bbsNm) {
		whoyaGlobalData.bForm.setItemValue("bbsId", bbsId);
    	$("#bbsNm").val(bbsNm);
    	whoyaGlobalData.boardPopupWindows.close();
	};

	/**
	 * 커뮤니티 정보(팝업)에서 선택된 값 저장.
	 * @pram cmmntyId 커뮤니티 아이디
	 * @pram cmmntyNm 커뮤니티 명
	 */
	whoya.common.communitySelect = function(cmmntyId, cmmntyNm) {
		whoyaGlobalData.bForm.setItemValue("trgetId", cmmntyId);
		whoyaGlobalData.bForm.setItemValue("trgetNm", cmmntyNm);
		whoyaGlobalData.communityPopupWindows.close();
	};

	/**
	 * 동호회 정보(팝업)에서 선택된 값 저장.
	 * @pram clbId 동호회 아이디
	 * @pram clbNm 동호회 명
	 */
	whoya.common.clulbSelect = function(clbId, clbNm) {
		whoyaGlobalData.bForm.setItemValue("trgetId", clbId);
		whoyaGlobalData.bForm.setItemValue("trgetNm", clbNm);
		whoyaGlobalData.clubPopupWindows.close();
	};

	/**
	 * 보고대상자(팝업)에서 선택된 값 저장.
	 * @pram uniqId 사용자ID
	 * @pram emplNo 사원번호
	 * @pram emplyrNm 사용자명
	 * @pram orgnztNm 조직명
	 */
	whoya.common.reportrSelect = function(uniqId, emplNo, emplyrNm, orgnztNm) {
		$("#reportrId").val(uniqId);
		$("#reportrNm").val(emplyrNm);
		whoyaGlobalData.reportrPopupWindows.close();
	};

	/**
	 * 설문관리 목록(팝업)에서 선택된 값 저장.
	 * @pram qestnrId 설문지ID
	 * @pram qestnrTmplatId 템플릿 ID
	 * @pram qestnrCn 설문지정보
	 */
	whoya.common.qestnrSelect = function(qestnrId, qestnrTmplatId, qestnrCn) {
		$("#qestnrId").val(qestnrId);
		$("#qestnrTmplatId").val(qestnrTmplatId);
		$("#qestnrCn").val(qestnrCn);
		whoyaGlobalData.qestnrPopupWindows.close();
	};
	
	/**
	 * 설문문항 목록(팝업)에서 선택된 값 저장.
	 * @pram qestnrQesitmId 설문문항 ID
	 * @pram qestnCn 질문내용
	 * @pram qestnTyCode 질문유형코드
	 */
	whoya.common.qestnrQesitmSelect = function(qestnrQesitmId, qestnCn, qestnTyCode) {
		$("#qestnrQesitmId").val(qestnrQesitmId);
		$("#qestnrQesitmCn").val(qestnCn);
		if ( $("#qestnTyCode").length > 0 ) {
			$("#qestnTyCode").val(qestnTyCode);
		}
		whoyaGlobalData.qestnrQesitmPopupWindows.close();
	};
	
	/**
	 * 설문문항 통계화면
	 * @pram qestnrQesitmId 설문문항 ID
	 */
	whoya.common.qustnrStatisticsSelect = function(qestnrQesitmId) {
		whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellStatisticsFormData);
		formEvent();
		
		$.ajax({
            url: whoya.context + "/whoya/uss/olp/qqm/EgovQustnrQestnManageStatistics.do"
            , type: "POST"
            , data: {
            	qestnrQesitmId: qestnrQesitmId
            }
            , success: function(data, textStatus, jqXHR) {
            	// 설문항목 결과
            	var statisticsListIemCn = "";
            	$.each(data.statisticsList, function(idx) {
            		statisticsListIemCn += "<ul>";
            		statisticsListIemCn += "<li>" + this.iemCn + "</li>";
            		statisticsListIemCn += "<li>" + "<img src=" + whoya.context + "'/images/egovframework/com/uss/olp/qqm/chart/chart" + idx + ".JPG' width='" + this.qustnrPercent + "px' height='8' alt='차트이미지' /> " + this.qustnrPercent + "%</li>";
            		statisticsListIemCn += "</ul>";
            	});
            	data.detail.statisticsListIemCn = statisticsListIemCn;

            	// 응답자답변내용 결과
            	var statisticsList2RespondAnswerCn = "";
            	$.each(data.statisticsList2, function(idx) {
            		if ( this.respondAnswerCn ) {
            			statisticsList2RespondAnswerCn += "<ul>";
            			statisticsList2RespondAnswerCn += "<li>" + this.respondAnswerCn + "</li>";
            			statisticsList2RespondAnswerCn += "</ul>";
            		}
            	});
            	data.detail.statisticsList2RespondAnswerCn = statisticsList2RespondAnswerCn;
            	
            	// 기타답변내용 결과
            	var statisticsList2EtcAnswerCn = "";
            	$.each(data.statisticsList2, function(idx) {
            		if ( this.etcAnswerCn ) {
            			statisticsList2EtcAnswerCn += "<ul>";
            			statisticsList2EtcAnswerCn += "<li>" + this.etcAnswerCn + "</li>";
            			statisticsList2EtcAnswerCn += "</ul>";
            		}
            	});
            	data.detail.statisticsList2EtcAnswerCn = statisticsList2EtcAnswerCn;
            	
                whoyaGlobalData.bForm.setFormData(data.detail);
            }
            , error: function(jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
                alert(errorThrown);
            }
        });
	};