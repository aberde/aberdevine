// 디자인 관련 스크립트

// 탭온오프
$(function() {    
	$("img.tabon").mouseover(function() {
	    if($(this).attr("id") != "selected")
	    	$(this).attr("src", $(this).attr("src").replace("off","on"));
	});
	$("img.tabon").mouseout(function() {
	    if($(this).attr("id") != "selected")
			$(this).attr("src", $(this).attr("src").replace("on", "off"));
	});	
});