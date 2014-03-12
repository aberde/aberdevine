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
            		statisticsListIemCn += "<li>" + "<img src='" + whoya.context + "/images/egovframework/com/uss/olp/qqm/chart/chart" + (idx + 1) + ".JPG' width='" + this.qustnrPercent + "px' height='8' alt='차트이미지' /> " + this.qustnrPercent + "%</li>";
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

	/**
	 * 설문문항 통계화면
	 * @pram qestnrId 설문ID
	 * @pram qestnrTmplatId 설문템플릿ID
	 */
	whoya.common.qustnrRespondStatisticsSelect = function(qestnrId, qestnrTmplatId) {
		whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellStatisticsFormData);
		
		$.ajax({
			url: whoya.context + "/whoya/uss/olp/qnn/EgovQustnrRespondInfoManageStatistics.do"
			, type: "POST"
			, data: {
				qestnrId: qestnrId
				, qestnrTmplatId: qestnrTmplatId
			}
			, success: function(data, textStatus, jqXHR) {
				// 설문 결과
				var Comtnqustnrqesitm = "";
				$.each(data.Comtnqustnrqesitm, function(idx) {
					Comtnqustnrqesitm += "<ul>";
					Comtnqustnrqesitm += "<li>" + this.qestnCn + "</li>";
					Comtnqustnrqesitm += "<li>";
					
					if ( this.qestnTyCode == '1' ) {  // 객관식
						var $qestnrTmplatId = this.qestnrTmplatId;
						var $qestnrId = this.qestnrId;
						var $qestnrQesitmId = this.qestnrQesitmId;
						
						// 설문항목
						$.each(data.Comtnqustnriem, function(idx1) {
							
							var $qustnrIemId = this.qustnrIemId;
							var chartCheck = 0;
							
							if ( this.qestnrTmplatId == $qestnrTmplatId && this.qestnrId == $qestnrId && this.qestnrQesitmId == $qestnrQesitmId ) {
								Comtnqustnrqesitm += "<ul>";
								Comtnqustnrqesitm += "<li>" + this.iemCn + "</li>";
								
								// 설문통계(객관식)
								$.each(data.qestnrStatistic1, function(idx2) {
									if ( this.qestnrTmplatId == $qestnrTmplatId && this.qestnrId == $qestnrId && this.qestnrQesitmId == $qestnrQesitmId && this.qustnrIemId == $qustnrIemId ) {
										Comtnqustnrqesitm += "<li>" + "<img src='" + whoya.context + "/images/egovframework/com/cmm/chart/chart" + (idx1 + 1) + ".JPG' width='" + this.qustnrPercent + "px' height='8' alt='차트이미지' /> " + this.qustnrPercent + "%</li>";
										chartCheck++;
									}
								});
								
								if ( chartCheck == 0 ) {
									Comtnqustnrqesitm += "<li>" + "<img src='" + whoya.context + "/images/egovframework/com/cmm/chart/chart" + (idx1 + 1) + ".JPG' width='0px' height='8' alt='차트이미지' /> 0%</li>";
								}
								Comtnqustnrqesitm += "</ul>";
							}
						});
					} else if ( this.qestnTyCode == '2' ) {  // 주관식
						var $qestnrTmplatId = this.qestnrTmplatId;
						var $qestnrId = this.qestnrId;
						var $qestnrQesitmId = this.qestnrQesitmId;
						
						// 설문통계(주관식)
						$.each(data.qestnrStatistic2, function(idx1) {
							Comtnqustnrqesitm += "<ul>";
							if ( this.qestnrTmplatId == $qestnrTmplatId && this.qestnrId == $qestnrId && this.qestnrQesitmId == $qestnrQesitmId ) {
								Comtnqustnrqesitm += "<li>" + this.respondNm + " : " + this.respondAnswerCn + "</li>";
							}
							Comtnqustnrqesitm += "</ul>";
						});
					}
					
					Comtnqustnrqesitm += "</li>";
					Comtnqustnrqesitm += "</ul>";
				});
				data.Comtnqestnrinfo[0].Comtnqustnrqesitm = Comtnqustnrqesitm;
				
				whoyaGlobalData.bForm.setFormData(data.Comtnqestnrinfo[0]);
			}
			, error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR);
				console.log(textStatus);
				console.log(errorThrown);
				alert(errorThrown);
			}
		});
	};
	
	/**
	 * 설문관리 [설문응답자정보/설문문항/설문조사/통계] 화면 이동.
	 * @pram qestnrId 설문ID
	 * @pram qestnrTmplatId 설문템플릿ID
	 * @pram type 질문유형코드
	 */
	whoya.common.qustnrManageSelect = function(qestnrId, qestnrTmplatId, type) {
		var url = "";
		var queryString = "?searchMode=Y&qestnrId=" + qestnrId + "&qestnrTmplatId=" + qestnrTmplatId;
		
		//QRM QQM QRI
		if ( type == "QRM" ) {  //설문응답자정보
			url = whoya.context + "/whoya/uss/olp/qrm/EgovQustnrRespondManageList.do";
		} else if ( type == "QQM" ) {  //설문문항
			url = whoya.context + "/whoya/uss/olp/qqm/EgovQustnrQestnManageList.do";
		} else if ( type == "QRI" ) {  //응답자결과
			url = whoya.context + "/whoya/uss/olp/qnn/EgovQustnrRespondInfoManageList.do";
		}
		
		window.location = url + queryString;
	};
	
	/**
	 * 설문관리 통계화면
	 * @pram qestnrId 설문ID
	 * @pram qestnrTmplatId 설문템플릿ID
	 */
	whoya.common.qustnrManageStatisticsSelect = function(qestnrId, qestnrTmplatId) {
		whoyaGlobalData.bForm = whoya.dhtmlx.layout.cell.form(whoyaGlobalData.bCellStatisticsFormData);
		
		$.ajax({
			url: whoya.context + "/whoya/uss/olp/qnn/EgovQustnrRespondInfoManageStatistics.do"
			, type: "POST"
			, data: {
				qestnrId: qestnrId
				, qestnrTmplatId: qestnrTmplatId
				, searchMode: "Y"
			}
			, success: function(data, textStatus, jqXHR) {
				// 설문 결과
				var Comtnqustnrqesitm = "";
				$.each(data.Comtnqustnrqesitm, function(idx) {
					Comtnqustnrqesitm += "<ul>";
					Comtnqustnrqesitm += "<li>" + this.qestnCn + "</li>";
					Comtnqustnrqesitm += "<li>";
					
					if ( this.qestnTyCode == '1' ) {  // 객관식
						var $qestnrTmplatId = this.qestnrTmplatId;
						var $qestnrId = this.qestnrId;
						var $qestnrQesitmId = this.qestnrQesitmId;
						
						// 설문항목
						$.each(data.Comtnqustnriem, function(idx1) {
							
							var $qustnrIemId = this.qustnrIemId;
							var chartCheck = 0;
							
							if ( this.qestnrTmplatId == $qestnrTmplatId && this.qestnrId == $qestnrId && this.qestnrQesitmId == $qestnrQesitmId ) {
								Comtnqustnrqesitm += "<ul>";
								Comtnqustnrqesitm += "<li>" + this.iemCn + "</li>";
								
								// 설문통계(객관식)
								$.each(data.qestnrStatistic1, function(idx2) {
									if ( this.qestnrTmplatId == $qestnrTmplatId && this.qestnrId == $qestnrId && this.qestnrQesitmId == $qestnrQesitmId && this.qustnrIemId == $qustnrIemId ) {
										Comtnqustnrqesitm += "<li>" + "<img src='" + whoya.context + "/images/egovframework/com/cmm/chart/chart" + (idx1 + 1) + ".JPG' width='" + this.qustnrPercent + "px' height='8' alt='차트이미지' /> " + this.qustnrPercent + "%</li>";
										chartCheck++;
									}
								});
								
								if ( chartCheck == 0 ) {
									Comtnqustnrqesitm += "<li>" + "<img src='" + whoya.context + "/images/egovframework/com/cmm/chart/chart" + (idx1 + 1) + ".JPG' width='0px' height='8' alt='차트이미지' /> 0%</li>";
								}
								Comtnqustnrqesitm += "</ul>";
							}
						});
					} else if ( this.qestnTyCode == '2' ) {  // 주관식
						var $qestnrTmplatId = this.qestnrTmplatId;
						var $qestnrId = this.qestnrId;
						var $qestnrQesitmId = this.qestnrQesitmId;
						
						// 설문통계(주관식)
						$.each(data.qestnrStatistic2, function(idx1) {
							Comtnqustnrqesitm += "<ul>";
							if ( this.qestnrTmplatId == $qestnrTmplatId && this.qestnrId == $qestnrId && this.qestnrQesitmId == $qestnrQesitmId ) {
								Comtnqustnrqesitm += "<li>" + this.respondNm + " : " + this.respondAnswerCn + "</li>";
							}
							Comtnqustnrqesitm += "</ul>";
						});
					}
					
					Comtnqustnrqesitm += "</li>";
					Comtnqustnrqesitm += "</ul>";
				});
				data.Comtnqestnrinfo[0].Comtnqustnrqesitm = Comtnqustnrqesitm;
				
				whoyaGlobalData.bForm.setFormData(data.Comtnqestnrinfo[0]);
			}
			, error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR);
				console.log(textStatus);
				console.log(errorThrown);
				alert(errorThrown);
			}
		});
	};