/**
 * jQuery 확장 함수
 * 
 * @author CelestialMoon
 */
jQuery.extend({	
	sendPost: function(url, parameter, callback, rootArea, isBlockAll, obj, divideNum) {
		renderWaitingBox(rootArea, isBlockAll, obj, divideNum);		
		$.ajax({
			url : url,
			type :  "POST",
			data :  parameter,
			dataType : "JSON",
			success : callback   // 응답 처리 함수명
			});
	},
	calculateInputByte: function(textAreaId, byteAreaNm, maxLength, doCheckByte) {
		var $input = $('#' + textAreaId);
		var byteArea = ($('#' + byteAreaNm).length == 0 ? $('.' + byteAreaNm) : $('#' + byteAreaNm));
		
		var updateCurrentLength = function() {
			var inputBytes = 0;
			var char = null;
			for (var i = 0; i < $input.val().length; i++) {
				char = $input.val().charAt(i);
				if (doCheckByte) {
					if (escape(char).length > 4) inputBytes += 2;
					else if (char == '\n') inputBytes += 1;
					else if (char == '\r\n') inputBytes += 2;
					else if (char == '<' || char == '>') inputBytes += 4;
					else inputBytes += 1;
				} else {
					if (char == '\n') inputBytes += 1;
					else if (char == '\r\n') inputBytes += 2;
					else if (char == '<' || char == '>') inputBytes += 4;
					else inputBytes += 1;
				}
			}

			if (inputBytes > maxLength) inputBytes = maxLength - inputBytes;
			byteArea.text(inputBytes);
		};

		$input.bind('input keyup paste', function() {setTimeout(updateCurrentLength, 0);});
		updateCurrentLength();
	}	
});

function renderWaitingBox(rootArea, isBlockAll, msg) {
	var message = (msg ? msg : "로딩 중...");
	var centerY = true;
	
	if ($('#' + rootArea).children().length > 2) centerY = false;
	else centerY = true;
	
	if (isBlockAll) {
		$.blockUI({ 
			message: '<h1><img src="/images/ajax/ajax_loader.gif" />&#160;' + message + '</h1>',
			css: { color: '#6666ff',border: '1px solid #9999ff' } });			
	} else {
		$('#' + rootArea).block({
			message : '<h1><img src="/images/ajax/ajax_loader.gif" />&#160;' + message + '</h1>', 
			css: { 
				color:'#6666ff', width:'200px',
				height:'25px', border: '1px solid #9999ff',
				paddingTop:'10px'
			}});
	}
}