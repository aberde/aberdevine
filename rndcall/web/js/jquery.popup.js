/* popup append */
function popAppend(){
	$('body').append('<div id="dimm"></div><div id="popAll"></div>');
};
/* popup close */
function popClose(){
	$('#dimm').remove();
	$('#popAll').remove();
}
/* popup position */
function popH(){
	var $popAll = $('#popAll');
	var $popH = $popAll.height();
	var $popW = $popAll.width();

	var $winW = $(window).width();
	var $winH = $(window).height();

	if($popH < $winH){
		//$popAll.css({'top':'30%', 'left':'50%', 'margin-top':-$popH/2, 'margin-left':'-360px'});
		$popAll.css({
			'top':'119px'
			
		//	, 'left':'50%'
			//'margin-top' : '119px'
			, 'margin-right': 'auto'
			, 'margin-left': 'auto'

		});
	}else{
		$popAll.css({'top':'0', 'left':'50%', 'margin-top':'0', 'margin-left':'-160px'});
	}

}
function popClose(){
	$('#dimm').remove();
	$('#popAll').remove();
}

//popUp
function layerPop(_url){
	var $popAll = $('#popAll');
	popClose();
	popAppend();

	$('#popAll').load(_url, function(){
		popH();
	});
}

/*  전체 메뉴 */
function totalMenuBtnHandler(_url)
{
	var $popTriger = $(".btn-menu");
	$popTriger.bind('click', function() {
		// var $popAll = $('#popAll');
		// popAppend();

		// $('#popAll').load(_url, function(){
		// 	popH();		
		// 	$popAll.css({'top':'0'});
		// });
		location.href="../sitemap/sitemap.html"
	});
	
}


