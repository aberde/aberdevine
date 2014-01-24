/**
 * @author 김동규
 * @since 2012.05.10
 * @dependency jquery.core
 * @dependency jquery.ui.effect
 * 
 * ==use==
 * $([selector]).foldingMenu(options);
 * 
 * ==options==
 * options = {
		menuSelector:		'.foldingMenu'				//최상위 레벨 메뉴 선택 셀렉터
		,highlightSuffix: 	''							//hover 시의 이미지 suffix. img버튼일 경우 동작.
		,selectedSuffix: 	''							//선택되었을 대의 이미지 suffix. img버튼일 경우 동작.
		,highlightClass: 	'foldingMenu-highlight'		//hover 되었을 때의 클래스
		,selectedClass: 	'foldingMenu-selected'		//선택되었을 때의 클래스
//		,subHighlightClass:	'foldingMenu-sub-highlight'	//미사용
//		,subSelectedClass:	'foldingMenu-sub-selected'	//미사용
		,showClass:			'foldingMenu-show'			//현재 보이는 서브 메뉴의 클래스
		,exclusive:			true						//서브메뉴를 하나만 보일지 여부. false일 경우 열린상태 유지
		,delay:				0							//서브메뉴가 열리는 시간. 0일경우 딜레이 없음.
	}
 */
(function($) {
var defaults = {
	menuSelector:		'.foldingMenu'
	,submenuSelector:	'.foldingMenu_sub'
	,highlightSuffix: 	''
	,selectedSuffix: 	''
	,highlightClass: 	'foldingMenu-highlight'
	,selectedClass: 	'foldingMenu-selected'
//	,subHighlightClass:	'foldingMenu-sub-highlight'
//	,subSelectedClass:	'foldingMenu-sub-selected'
	,showClass:			'foldingMenu-show'
	,exclusive:			true
	,delay:				0
};
var o = {};
var $menu = {};
var $sub = {};

/////////////////////////////////////////////////////////////////////
var _$category = function(obj) {
	return obj.find('a:first');
};
var _$img = function(obj) {
	return obj.find('a:first>img');
};
var _$menu = function(obj) {
	return obj.find(o.menuSelector);
}
var _$sub = function(menu) {
	return $menu.find(o.submenuSelector);
}
var _otherSub = function(el) {
	return $sub.not($menu.has(el).find(o.submenuSelector));
};

var _selfSub = function(el) {
	return $menu.has(el).find(o.submenuSelector);
};
////////////////////////////////////////////////////////////////////

var _altImageSrc = function(url, suffix) {
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
			if (o.selectedSuffix.length!=0) {
				var exp = new RegExp(o.selectedSuffix + '$');
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

var _hide = function(el, delay, force) {
	if(delay) {
		$(el).hide('blind', {}, delay);
	} else {
		$(el).css('display', 'none');
	}
	$(el).removeClass(o.showClass);
	if (!force) $menu.has(el).addClass(o.highlightClass);
	if ($menu.has(el).hasClass(o.highlightClass) && o.highlightClass.length!=0) {
		var img = _$img($menu.has(el).removeClass(o.selectedClass));
		img.attr('src', _altImageSrc(_altImageSrc(img.attr('src')), o.highlightSuffix));
	} else {
		var img = _$img($menu.has(el).removeClass(o.selectedClass));
		img.attr('src', _altImageSrc(img.attr('src')));
	}
};

var _show = function(el, delay) {
	if (o.exclusive) {
		$menu.filter('.' + o.selectedClass).find(o.submenuSelector).each(function(k, v) {
			_hide(v, delay, true);
		}); 
	}
	if (delay) {
		$(el).show('blind', {}, delay);
	} else {
		$(el).css('display', 'block');
	}
	$(el).addClass(o.showClass);
	var img = _$img($menu.has(el).addClass(o.selectedClass));
	img.attr('src', _altImageSrc(_altImageSrc(img.attr('src')), o.selectedSuffix));
};

var methods = {
	init: function(options) {
		var self = this;
		o = $.extend(defaults, options, true);
		$menu = _$menu(self);
		$sub = _$sub($menu);

		var _toggleSub = function(e) {
			//var el = _selfSub(this);
			var el = $menu.has(this);
			if (el.hasClass(o.selectedClass)) {
				//el.removeClass(o.selectedClass);
				_hide(_selfSub(this), o.delay);
			} else {
				//el.addClass(o.selectedClass);
				_show(_selfSub(this), o.delay);
			}
		};

		var _hoverIn = function(e) {
			var menu = $menu.has(this);
			var $img = $(this).find('img');
			if (!menu.hasClass(o.selectedClass)) {
				menu.addClass(o.highlightClass);
				if ($img[0]) {
					$img.attr('src', _altImageSrc($img.attr('src'), o.highlightSuffix));
				}
			}
		};
		var _hoverOut = function(e) {
			var menu = $menu.has(this).removeClass(o.highlightClass);
			var $img = $(this).find('img');
			
			if (!menu.hasClass(o.selectedClass)) {
				if ($img[0]) {
					$img.attr('src', _altImageSrc($img.attr('src')));
				}
			}
		};
		
		return self.each(function() {
			$menu.each(function(k, m) {
				_$category($(m)).each(function(k2, a) {
					$(a).hover(_hoverIn, _hoverOut);
					if (_selfSub(a).length!=0) {
						_hide(_selfSub(a), 0, true);
						$(a).attr('href', 'javascript:void(0)');
						$(a).click(_toggleSub);
					}
				});
			});
		});
	}
	,select: function(idx) {
		var self = this;
		if (!idx && (idx + '').length==0) return;
		var menu = undefined;
		if (typeof idx==='number') {
			menu = _$menu(self)[idx];
		} else if (typeof idx==='string') {
			menu = _$menu(self).filter('#' + idx);
		}

		if (!menu || !$(menu)[0]) return;
		
		$(menu).addClass(o.showClass);
		var src = _altImageSrc(_$img($(menu)).attr('src'),o.selectedSuffix);
		if (src && src.length!=0) {
			_$img($(menu)).attr('src', src);
		}
		
		_show($(menu).find(o.submenuSelector));
	}
}

$.fn.foldingMenu = function(options) {
	
	if (methods[options]) {
		return methods[options].apply(this, Array.prototype.slice.call(arguments, 1));
	} else if (typeof options==='object' || !options) {
		return methods.init.apply(this, arguments);
	}
};
})(jQuery);