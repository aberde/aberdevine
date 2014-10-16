/*ready*/
$(document).ready(function() {
	onlineAdvice();
	closeBtnHandler();
});

function onlineAdvice(){
	var $eventBtn = {};
	$.extend($eventBtn, {
		init: function () {
			var $eventBtn 	= this,
				init 			    = $eventBtn.init;
				init.Active 	   = $('#onlineAdvice_btn > li');
				init.Target 	   = $('.center-officeBanner-box ul');
				init.Change   = $('.center-officeBanner .title .ch_title');
				
			init.Active.eq(0).addClass('active');
			init.Target.eq(0).show();
			
			init.Active.on({
				'click': function () {
					$eventBtn.active($(this), init.Target, init.Change);					
				}
			});			
		},//usually menu control
		active: function (node, target, change) {
			if(!node.hasClass('active')){//메뉴에 active일 경우 on을 재제
				node.addClass('active');
				node.siblings().removeClass('active');				
				target.eq(node.index()).show();
				target.eq(node.index()).siblings().hide();
				change.text(node.find('span').text());
			}
		},
	}).init();
}

/*function closeBtnHandler(){
	var $eventBtn = {};
	$.extend($eventBtn, {
		init: function () {
			var $eventBtn 	= this,
				init 			= $eventBtn.init;
				init.Active 	= $('#close_id');
				init.Target 	= $('.agency-listArea');
				
			init.Target.slideUp(200);
			init.Active.on({
				'click': function () {
					$eventBtn.active($(this), init.Target);					
				}
			});			
		},//usually menu control
		active: function (node, target) {
			if(!node.hasClass('active')){//메뉴에 active일 경우 on을 재제
				node.addClass('active');	
				node.find('img').attr('src',node.find('img').attr('src').replace('_close.gif','_open.gif'));		
				target.slideUp(200);
			}else{
				node.removeClass('active');
				node.find('img').attr('src',node.find('img').attr('src').replace('_open.gif','_close.gif'));
				target.slideDown(200);
			}
		},
	}).init();
}*/
