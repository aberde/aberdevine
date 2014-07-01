$(document).ready(function(){
	// 메인슬라이드
	$("#visual").owlCarousel({
		autoPlay : true,
		singleItem : true,
		pagination : true,
		mouseDrag : false
		// responsive : true
	});
 	// 배너 슬라이드 
	$("#owl-banner").owlCarousel({
		autoPlay :3600,
		singleItem : true,
		pagination : true,
		mouseDrag : false
	});
	var owlbanner = $("#owl-banner").data('owlCarousel');
	$(".stop").click(function(){owlbanner.stop();});
	$(".play").click(function(){owlbanner.play();});
	// 전체 보기 
	totalMenuBtnHandler("html/pop/total_menu.html");
	// 메뉴 
	 topNav();
});