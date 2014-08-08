$(document).ready(function(){
	// 메인슬라이드
	$("#visual").owlCarousel({
		autoPlay : false,
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
	//totalMenuBtnHandler("html/pop/total_menu.html");
	// 메뉴
	topNav();
	contentBanner()
});

function contentBanner(){
	var $target=$(".content .banner ul li");
	$target.css({"cursor" : "pointer"});
	$target.each(function(index){
		$(this).click(function(){
			var link=$(this).find("a").attr("href");
			//console.log(link);
			location.href=link;
		});
	});
}

//메인 새소식 , 자료실
function FNrolling(_parent){
	var intervalTime=3000;
	var $notice=_parent;
	var $item=$("p", $notice);
	var cnt=0;
	var intervarID;

	function FNinterval_start(){
		intervarID=setInterval(FNinterval, intervalTime);
	}

	function FNinterval(){
		$item.eq(cnt).appendTo($notice);
		cnt++;
		if(cnt >=3) cnt=0;
	}

	$item.on("mouseenter", function(){
		clearInterval(intervarID)
	})
	$item.on("mouseleave", function(){
		clearInterval(intervarID)
		FNinterval_start();
	})
	FNinterval_start()
}
$(document).ready(function(){
	FNrolling($(".notice-bx"));
	FNrolling($(".data-bx"));
});