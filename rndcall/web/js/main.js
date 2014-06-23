$.fn.ImgRolling = function(){
		this.each(function(){	

			var $obj = $(this);
			var $btn_stop = $obj.find(".stop");
			var $btn_play = $obj.find(".play");
			var $RollList = $obj.find("ul.slide"); // 컨텐츠
			var $bannerNum = $obj.find(".btn-set a"); //배너
			var $nCurrnetBannerIndex = 0;  //활성화된 배너인덱스
			var $imgLeng = $obj.find("ul.slide li").find("img").length;
			var $ContWidth = $obj.outerWidth() * $imgLeng; //컨텐츠총width
			var showInterval; 
			var click = 0; //중복클릭방지
			function initMenu(){
				$RollList.width($ContWidth);
				showNum(0);
				play();
			}

			function nextBanner(){
				var nIndex = $nCurrnetBannerIndex +1;
				if(nIndex>=$imgLeng){
					nIndex = 0
				}
				showBannerAt(nIndex);
			}

			function showBannerAt(nIndex){
				if(nIndex != $nCurrnetBannerIndex){
					showNum(nIndex);
					$RollList.stop().animate({
						"margin-left" : "-=" + $obj.outerWidth() + "px"
						}, 'slow', 'swing' , function(){
							$RollList.find("li:first").appendTo($RollList);
							$RollList.css("margin-left" , 0+"px");
							$nCurrnetBannerIndex = nIndex;
					})
				}
			}

			function showNum(nIndex){
				$bannerNum.removeClass("on");
				$bannerNum.eq(nIndex).addClass("on");
			}

			$bannerNum.each(function(index){
				$(this).bind("click" ,function(e){
					stop();
					var nIndex = $bannerNum.index(this);
					showBannerAt(nIndex);
					play();
					return false;
				});
			});

			/* autoPlay */
			play = function() {
				if(click == 0){
					timer_popup = setInterval(function(){ nextBanner() }, 4000);
				}		
				click = 1;		
			};
			/* stop */
			stop = function() {
				click = 0;
				if ( timer_popup ) {clearInterval( timer_popup );}
			};
			$btn_play.click( play ); // play event
			$btn_stop.click( stop );// stop event

			initMenu();

		}); 
	};
	
	
	
	

