/**
 * @author 김동규
 * @since 2012.05.02
 * @dependency jquery.core
 * 
 * ==use==
 * $([selector]).floatingMenu(options);
 * 
 * ==options==
 * options = {
		area: {
			min: 0					//left position
			,max:1000				//right position
		}
		,selectedSuffix:'_on'		//선택된 메인메뉴의 이미지 suffix
		,submenuArea: 	'.submenu'	//서브메뉴가 위치한 영역을 지정할 샐렉터
		,submenuSuffix:	'_sub'		//서브메뉴 ID의 suffix. 메인메뉴ID + suffix 로 서브메뉴를 지정한다.
	}
 */
(function($) {

var showClass = 'floatingMenu-show';
var hoverClass = 'floatingMenu-hover';

var o = {};
function _posX(el) {
	return $(el).offset().left + $(el).outerWidth()/2; 
};
function _getLeft(menu, sub, o) {
	var left = 0;
	var halfSubWidth = sub.outerWidth()/2;
	var posX = _posX(menu);
	var min = o.area.min;
	var max = o.area.max;
	var offset = $(o.submenuArea).offset().left;
	
	if (sub.outerWidth()>(max - min)) {
		left = min - offset;
	} else if ((posX - halfSubWidth)<min) {
		left = min - offset;
	} else if ((posX + halfSubWidth)>max) {
		left = max - sub.outerWidth() - offset;
	} else {
		left = posX - sub.outerWidth()/2 - offset;
	}
	
	return left;
};
function _show(el) {
	$(el).css('display', 'block');
	$(el).addClass(showClass);
};
function _hide(el) {
	var d = {'display' : 'none'};
	$(el).css(d);
	$(el).removeClass(showClass);
};
function _altImageSrc(url, suffix) {
	
	if (!url || url.length==0)
		return '';
	if (suffix && suffix.length==0)
		return url;
	
	var tokens = url.split('.');
	var src = '';
	if(tokens.length>1) {
		if (suffix) {
			tokens[tokens.length-2] = tokens[tokens.length-2] + suffix;
		} else {
			if (o.highlightSuffix.length!=0) {
				var exp = new RegExp(o.highlightSuffix + '$');
				tokens[tokens.length-2] = tokens[tokens.length-2].replace(exp, '');
			}
		}
		$.each(tokens, function(k2, v2) {
			src += ('.' + v2);
		});
		
		return src.substring(1);
	}
	return src;
};

var methods = {
	init : function(options) {
		
		var self = this;
		var defaults = {
			area: {
				min: 0
				,max:1000
			}
			//,highlightSuspend:	false
			,highlightSuffix:	''
			,selectedSuffix:	'_on'
			,submenuArea: 		'.submenu'
			,submenuSuffix:		'_sub'
		};

		o = $.extend(defaults, options, true);
		
		var $menu = self.find('li');
		var $sub = $(o.submenuArea).find('ul');
		
		var _otherSub = function(el) {
			return $sub.filter(':not(#' + $menu.has(el).attr('id') + o.submenuSuffix + ')');
		};
		var _selfSub = function(el) {
			return $sub.filter('#' + $menu.has(el).attr('id') + o.submenuSuffix);
		};
						
		var _hoverIn = function(e) {
			var menu = $menu.has(this);
			var $img = $(this).find('img');
			if (!menu.hasClass(showClass)) {
				menu.addClass(hoverClass);
				if ($img[0]) {
					$img.attr('src', _altImageSrc($img.attr('src'), o.highlightSuffix));
				}
			}
			
			_hide(_otherSub(this));
			var sub = _selfSub(this);
			
			sub.css({
				left:	_getLeft(this, sub, o)
			});
			_show(sub);
		};
		var _hoverOut = function(e) {
			var menu = $menu.has(this).removeClass(hoverClass);
			var $img = $(this).find('img');
			
			if (!menu.hasClass(showClass)) {
				if ($img[0]) {
					$img.attr('src', _altImageSrc($img.attr('src')));
				}
			}

			_hide(_otherSub(this));
		};
		
		$sub.css({
			display: 	'none'
			,position:	'relative'
		});
		
		$.each($menu.find('a'), function(k,v) {
			$(v).hover(_hoverIn, _hoverOut);
		});
		
		return this;
	}
	,select: function(idx) {
		if (!idx && (idx + '').length==0) return;
		var self = this;
		var menu = undefined;
		if (typeof idx==='number') {
			menu = self.find('li')[idx];
		} else if (typeof idx==='string') {
			menu = self.find('li').filter('#' + idx);
		}

		if (!menu || !$(menu)[0]) return;
		
		$(menu).addClass(showClass);
		var src = _altImageSrc($(menu).find('img').attr('src'),o.selectedSuffix);
		if (src && src.length!=0) {
			$(menu).find('img').attr('src', src);
		}
		
		var _readyMenu = function() {
			if ($(menu).find('a').width()==0) {
				setTimeout(function() {
					_readyMenu();
				}, 50);
				return;
			}
			$(menu).find('a').trigger('mouseenter');
		};
		
		_readyMenu();
	}
};
$.fn.floatingMenu = function(options) {
	
	if (methods[options]) {
		return methods[options].apply(this, Array.prototype.slice.call(arguments, 1));
	} else if (typeof options==='object' || !options) {
		return methods.init.apply(this, arguments);
	}
};
})(jQuery);