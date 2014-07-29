function topNav(gnbActiveNum, gnbActiveSub) {
	// gnb
	var $gnb = {};
	$.extend($gnb, {
		init: function () {
			var $gnb 	= this,
				init 	= $gnb.init;
			init.gnb 	= $('#gnb');
			init.nav 	= init.gnb.find('.gnbContainer>ul>li');
			init.subNav = init.nav.find('ul>li');

			
		
			if (gnbActiveNum >= 1) {
				$gnb.active(init.nav.eq(gnbActiveNum-1));
			}
			//init subActive
			if (gnbActiveSub >= 1) {
				init.nav.eq(gnbActiveNum-1).find('li').eq(gnbActiveSub-1).addClass('on');
			}
			
			init.nav.on({
				'mouseenter': function () {

					if(init.gnb.hasClass('gnb')){//전체메뉴가 아닐경우
						if (gnbActiveNum >= 1 && $(this).index() == gnbActiveNum-1) {
						} else {
							$gnb.inactive(init.nav.eq(gnbActiveNum-1));
							$gnb.active($(this));
						}
					}else{//전체메뉴 일 경우	
						$gnb.menuInactive(init.nav.eq(gnbActiveNum-1));	
						init.nav.eq(gnbActiveNum-1).find('li').eq(gnbActiveSub-1).removeClass('on');
						$gnb.menuActive($(this));
					}					
				},
				'mouseleave': function () {
						if (gnbActiveNum >= 1 && $(this).index() == gnbActiveNum-1) {
						} else {
							if(init.gnb.hasClass('gnb')){//전체메뉴가 아닐경우
								$gnb.active(init.nav.eq(gnbActiveNum-1));
								$gnb.inactive($(this));
							}else{//전체메뉴 일 경우	
								$gnb.menuActive(init.nav.eq(gnbActiveNum-1));
								init.nav.eq(gnbActiveNum-1).find('li').eq(gnbActiveSub-1).addClass('on');
								$gnb.menuInactive($(this));
							}
						}				
				}
			});
			init.nav.on({//focus 시 메뉴 컨트롤
				'focusin': function () {
					if(init.gnb.hasClass('gnb')){//전체메뉴가 아닐경우
						if (gnbActiveNum >= 1 && $(this).index() == gnbActiveNum-1) {
							$gnb.focusActive($(this), init.nav,1);			
						} else {
							$gnb.focusActive($(this), init.nav,0);			
						}
					}else{//전체메뉴 일 경우	
						$gnb.menuInactive(init.nav.eq(gnbActiveNum-1));
						init.nav.eq(gnbActiveNum-1).find('li').eq(gnbActiveSub-1).removeClass('on');
						$gnb.menuActive($(this));
					}					
				},
				'focusout': function () {
						if (gnbActiveNum >= 1 && $(this).index() == gnbActiveNum-1) {	
						} else {
							if(init.gnb.hasClass('gnb')){//전체메뉴가 아닐경우
								init.nav.eq(gnbActiveNum-1).find('div').show();
								$gnb.focusInactive($(this));
							}else{//전체메뉴 일 경우	
								$gnb.menuActive(init.nav.eq(gnbActiveNum-1));
								init.nav.eq(gnbActiveNum-1).find('li').eq(gnbActiveSub-1).addClass('on');
								$gnb.menuInactive($(this));
							}
						}				
				}
			});
		},//usually menu control
		active: function (node) {
			if(!node.hasClass('on')){//메뉴에 active일 경우 on을 재제
				node.addClass('on');
				node.find('div').show();
				$gnb.menuOver(node);
				$gnb.menuPosition(node);
			}
		},
		inactive: function (node) {
			node.removeClass('on');
			node.find("div").hide();	
			$gnb.menuOut(node);		
		}, //focus menu control
		focusActive: function (node, nav, show) {
			if(!node.hasClass('on')){//메뉴에 active일 경우 on을 재제
				node.addClass('on');
				$gnb.menuOver(node);
			}
			if(show == 1){
				nav.eq(gnbActiveNum-1).find('div').show();
			}else{
				nav.eq(gnbActiveNum-1).find('div').hide();
				nav.eq(gnbActiveNum-1).removeClass('on');
				$gnb.menuOut(nav.eq(gnbActiveNum-1));
			}
			node.siblings().find('div').hide();
			node.find('div').show();
			if(node.index() > 0){
				nav.eq(node.index()-1).find('div').show();	
			}			
			$gnb.menuPosition(node);
		},
		focusInactive: function (node) {		
			node.removeClass('on');
			$gnb.menuOut(node);
		},//all menu control
		menuActive: function (node) {
			if(!node.hasClass('on')){//메뉴에 active일 경우 on을 재제
				node.addClass('on');
				$gnb.menuOver(node);
			}		
		},
		menuInactive: function (node) {		
			node.removeClass('on');
			$gnb.menuOut(node);	
		},//images change for menu img control
		menuOver:function (node){ //over menu change
			//var nodeImg = node.find('.mnb').find('img');
			//nodeImg.attr('src', nodeImg.attr('src').replace('.png', '_over.png'));
			node.addClass(".snb_on")
			//alert("node"+node)
		},
		menuOut:function (node){
			//var nodeImg = node.find('.gnbFirst').find('img');
			//nodeImg.attr('src', nodeImg.attr('src').replace('_over.png', '.png'));		
		},//usually menu control for position of menu sub 
		menuPosition :function (node){
			
			if(node.index() == 0){
				node.find('ul').css({'left': "10px"});
			}else if(node.index() <= 1){
				node.find('ul').css({'left': "220px"});
			}else if(node.index() <= 3){
				node.find('ul').css({'left': "500px"});
			}else if(node.index() <= 4){
				node.find('ul').css({'left': "580px"});
			}
			
		}
	}).init();
}


function FNfamily_go(_type){
	var goUrl="";
	if(_type=="1") {
		goUrl=$("#family1").val();
		if(goUrl=="") {
			alert("사이트를 선택해 주세요.");
			return;
		}
	}else if(_type=="2"){
		goUrl=$("#family2").val();
		if(goUrl=="") {
			alert("사이트를 선택해 주세요.");
			return;
		}
	}else{
		goUrl=$("#family3").val();
		if(goUrl=="") {
			alert("사이트를 선택해 주세요.");
			return;
		}
	}
	console.log("goUrl : "+goUrl);
	window.open(goUrl)
}

