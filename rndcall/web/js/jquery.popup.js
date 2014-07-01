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
	var $winH = $(window).height();

	if($popH < $winH){
		//$popAll.css({'top':'30%', 'left':'50%', 'margin-top':-$popH/2, 'margin-left':'-360px'});
		$popAll.css({'top':'13%', 'left':'30%'});//margin-top':"-$popH/2",
	}else{
		$popAll.css({'top':'0', 'left':'50%', 'margin-top':'0', 'margin-left':'-160px'});
	}
}

/*  전체 메뉴 */
function totalMenuBtnHandler(_url)
{
	var $popTriger = $(".btn-menu");
	$popTriger.bind('click', function() {
		var $popAll = $('#popAll');
		popAppend();

		$('#popAll').load(_url, function(){
			popH();		
			$popAll.css({'top':'0'});
		});
	});
}


