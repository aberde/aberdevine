/**
 * @author 김동규
 * @since 2012.05.02
 * @dependency jquery.core
 * 
 * ==use==
 * $([selector]).simpleTab(options);
 * 
 * ==options==
 * options = {
		tabs:				'.simple-tab-button'		//탭 버튼 셀렉터
		,contents:			'.simple-tab-contents'		//탭 컨텐츠 셀렉터
		,eventType:			'click' //click|hover		//탭 컨텐츠 영역을 로드하기 위한 이벤트 구분. 탭버튼 클릭 | 마우스 오버
		,hoverChange:		true	//버튼 스타일(이미지)을 마우스 오버시 바꿀지 여부 . eventType이 'hover' 일 경우 항상 true 
		,buttonType:		'style' //style|image
		,selectedClick:		false	//eventType=='click' 일때 이미 선택된 탭 버튼을 다시 클릭 가능한지 여부. contentsType이 static일때는 항상 false.
		,actions: 			{
			menu1: {
	 			normalImage:	''		//이미지 버튼일 경우 기본 이미지 src
				,higihightImage:	''	//이미지 버튼일 경우 마우스 오버시의 src
				,selectedImage:		''	//이미지 버튼일 경우 선택된 상태의 src
				,contents:			''	//컨텐츠 영역을 suffix를 이용하지 않고 명시적으로 접근하기 위한 셀렉터를 지정. jquery 셀럭터 형식으로 사용.
	 			,contentsType:		''	//전역 설정의 contentsType 과 동일
				,contentsTemplete:	''	//전역 설정의 contentsTemplete 과 동일
				,cache:				false//전역 설정의 cache 와 동일
				,more:				''	//more 버튼의 url 이나 handler 함수 지정.(a 태그가 없으면 동작하지 않음)
	 			,ajaxOptions: {			//ajax 옵션은 기본 jQuery 코어함수인 $.ajax 의 옵션과 동일
	 				url:		''		
	 				,dataType:	'json'
	 			}
	 		}
	 		,menu2: {...}
	 		,...
		}		
		//[{},{}] 형태의 배열로 지정 가능.
		//array(index base) or object(id base)
		
 		
		,defaultSelect:		0		//최초 로드시 선택될 탭 버튼의 인덱스. ajaxOnload 가 false 일경우 selectedTag만 지정하고 컨텐츠 로드는 하지 않는다.
		//image일때만 동작. highlight시 이미지의 파일명에 붙을 suffix.
		//ex)highlightSuffix: '_on', image01.jpg -> image01_on.jpg
		,contentsSuffix:	'_contents'
		,highlightSuffix:	''		//image일때만 동작. 마우스오버 되었을 경우의 이미지 파일명에 붙을 suffix.
		,selectedSuffix:	''		//image일때만 동작. 선택된 탭의 이미지 파일명에 붙을 suffix.
		,ajaxOnload:		false	//페이지 로드시 첫번째 컨텐츠를 구성할지 여부. true 시 탭 첫 페이지를 페이지 로드시 ajax로 가져온다.
		,highlightClass:	'simple-tab-highlight'
		,selectedClass:		'simple-tab-selected'
		,templete:			''		//컨텐츠 영역을 구성하기 위한 템플릿
		
		,contentsType:		'static'//컨텐츠 타입 (static:미리 구성되어 숨겨진 컨텐츠 | dynamic: ajax로 동적으로 불러올 컨텐츠)
		,contentsTemplete:	''		//컨텐츠 영역 내부를 구성하기 위한 템플릿
		,cache: false				//컨텐츠를 캐싱할지 여부
	}
 */

(function($) {
$.fn.simpleTab = function(options) {
	var self = this;
	
	var defaults = {
		tabs:				'.simple-tab-button'		//탭 버튼 셀렉터
		,contents:			'.simple-tab-contents'		//탭 컨텐츠 셀렉터
		,more:				'.simple-tab-more'			//more 버튼 셀렉터
		,eventType:			'click' //click|hover		//탭 컨텐츠 영역을 로드하기 위한 이벤트 구분. 탭버튼 클릭 | 마우스 오버
		,hoverChange:		true	//버튼 스타일(이미지)을 마우스 오버시 바꿀지 여부 . eventType이 'hover' 일 경우 항상 true 
		,buttonType:		'style' //style|image
		,selectedClick:		false	//eventType=='click' 일때 이미 선택된 탭 버튼을 다시 클릭 가능한지 여부. contentsType이 static일때는 항상 false.
		,actions: 			{}		//array(index base) or object(id base)
		,defaultSelect:		0		//최초 로드시 선택될 탭 버튼의 인덱스. ajaxOnload 가 false 일경우 selectedTag만 지정하고 컨텐츠 로드는 하지 않는다.
		//image일때만 동작. highlight시 이미지의 파일명에 붙을 suffix.
		//ex)highlightSuffix: '_on', image01.jpg -> image01_on.jpg
		,contentsSuffix:	'_contents'
		,highlightSuffix:	''		//image일때만 동작. 마우스오버 되었을 경우의 이미지 파일명에 붙을 suffix.
		,selectedSuffix:	''		//image일때만 동작. 선택된 탭의 이미지 파일명에 붙을 suffix.
		,ajaxOnload:		false	//페이지 로드시 첫번째 컨텐츠를 구성할지 여부. true 시 탭 첫 페이지를 페이지 로드시 ajax로 가져온다.
		,highlightClass:	'simple-tab-highlight'
		,selectedClass:		'simple-tab-selected'
		,templete:			''		//컨텐츠 영역을 구성하기 위한 템플릿
		
		,contentsType:		'static'//컨텐츠 타입 (static:미리 구성되어 숨겨진 컨텐츠 | dynamic: ajax로 동적으로 불러올 컨텐츠)
		,contentsTemplete:	''		//컨텐츠 영역 내부를 구성하기 위한 템플릿
		,cache: false				//컨텐츠를 캐싱할지 여부
	};
	var curContentsClass = 'simple-tab-cur-contents';
	var cachedClass = 'simple-tab-cached';
	
	var o = $.extend(defaults, options);
	
	var $tabs = self.find(o.tabs);
	var $contents = self.find(o.contents);
	var $more = self.find(o.more);
	
	//메뉴 ID가 지정되어 있지 않을 경우 자동 부여
	$.each($tabs, function(k, v) {
		if (!v.id || v.id.length==0) {
			v.id = self.attr('id') + '_menu_' + k;
		}
	});
	//컨텐츠 ID가 지정되어 있지 않을 경우 자동 부여
	if ($contents.filter('[id]').length==0) {
		$.each($contents, function(k, v) {
			if ($tabs[k]) {
				v.id = $tabs[k].id + o.contentsSuffix;
			}
		});
	}
	
	$($tabs[o.defaultSelect]).addClass(o.selectedClass);
	
	var _realContents = function(contents) {
		return $(contents).find(':first').length!=0 ? $(contents).find(':first') : $(contents); 
	};
	
	var _getContents = function(el, action) {
		if (el && $(el)[0] && $(el).hasClass(o.tabs.substring(1))) {
			if (action && action.contents && action.contents.length!=0) {
				el = $contents.filter(action.contents);
			} else 
				el = $contents.filter('#' + $(el).attr('id') + o.contentsSuffix);
		}

		return $(el);
	};
	var _showContents = function(el, action) {
		
		var contentsType = !action ? o.contentsType : action.contentsType==undefined ? o.contentsType : action.contentsType;
		var cache = contentsType=='static' ? true : !action ? o.cache : action.cache==undefined ? o.cache : action.cache;
		var contents = !action.contents ? undefined : action.contents.length == 0 ? undefined : action.contents;
		
		el = _getContents(el, action);
		
		if (!el || !$(el)[0]) {
			el = $(o.templete).addClass(o.contents.substring(1))
				.attr('id', contents ? contents.substring(1) : $tabs.filter('.' + o.selectedClass).attr('id') + o.contentsSuffix)
				.appendTo(self);
			$contents = self.find(o.contents);
		}
		
		if (!cache) {
			el.html('');
		}
		
		$more.find('a').unbind('click.moreHandler');
		if (action.more) {
			if (typeof action.more==='string') {
				$more.find('a').attr('href', action.more);
			} else if (typeof action.more==='function') {
				$more.find('a').attr('href', '#')
					.bind('click.moreHandler' , action.more);
			}
		}
		
		$contents.not(el).css('display', 'none').removeClass(curContentsClass);
		el.css('display', 'block').addClass(curContentsClass);
		
		return el;
	};

	var _find = function(el) {
		return o.buttonType=='style' ? $(el) : o.buttonType=='image' ? $(el).find('img') : undefined;
	};
	
	var _anonymousAction = function(el) {
		var obj = _find(el);
		var action = {};
		if (obj) {
			action.contentsType = 'static';
			if (o.buttonType=='image') {
				action.normalImage = obj.attr('src');
				action.highlightImage = obj.attr('src');
				action.selectedImage = obj.attr('src');
			}	
		}
		return action;
	};
	var _altImageSrc = function(action, suffix) {
		if (!suffix || suffix.length==0)
			return action.normalImage;
		if (!action.normalImage || action.normalImage.length==0)
			return false;
		var tokens = action.normalImage.split('.');
		var src = '';
		if(tokens.length>1) {
			tokens[tokens.length-2] = tokens[tokens.length-2] + suffix;
			$.each(tokens, function(k2, v2) {
				src += ('.' + v2);
			});
			
			return src.substring(1);
		}
		return action.normalImage;
	};
	
	var _hoverTabButton = function(el, action, isHoverIn) {
		if (isHoverIn) {
			if (!$(el).hasClass(o.selectedClass) && o.highlightSuffix.length!=0) {
				if (o.buttonType=='image') {
					if (action.highlightImage) {
						$(this).attr('src', action.highlightImage);
					} else {
						action.highlightImage = _altImageSrc(action, o.highlightSuffix);
						$(this).attr('src', action.highlightImage);
					}
				};
				$(el).addClass(o.highlightClass);
			} else if (o.buttonType=='image') {
				$(this).attr('src', action.highlightImage);
			}	
		} else {
			if (!$(el).hasClass(o.selectedClass)) {
				if (o.buttonType=='image') $(this).attr('src', action.normalImage);
			}
			$(el).removeClass(o.highlightClass);
		}
	};
	
	var _htmlDataHandler = function(el, action, data, status, xhr) {
		_realContents(el).html(data);
	};
	var _jsonDataHandler = function(el, action, data, status, xhr) {
		var contentsTemplete = action.contentsTemplete ? action.contentsTemplete : o.contentsTemplete;
		if (contentsTemplete.length!=0) {
			$.each(data, function(k, v) {
				var c = contentsTemplete; 
				$.each(v, function(k2, v2) {
					c = c.replace('{' + k2 + '}', v2);
				});
				_realContents(el).append($(c));
			});
		}
	};
	var _buildAjaxHandler = function(el, action) {
		var handler = undefined;
		var cache = action.cache!=undefined ? action.cache : o.cache;
		
		if (!action.ajaxOptions)
			return;
		if (action.ajaxOptions.dataType=='json') {
			handler = _jsonDataHandler;
		} else if (action.ajaxOptions.dataType=='html')
			handler = _htmlDataHandler;
		
		var success = action.ajaxOptions.success;
		action.ajaxOptions.success = function(data, status, xhr) {
			var c = _getContents(el, action);
			handler && handler.apply(this, [c, action, data, status, xhr]);
			success && success.apply(this, [data, status, xhr]);
			cache && c.addClass(cachedClass);
		};
	};
	
	var _selectTabButton = function(el, action) {
		
		var contentsType = action.contentsType ? action.contentsType : o.contentsType;
		var selectedClick = contentsType=='static' ? false : o.selectedClick;
		if (!selectedClick && $(el).hasClass(o.selectedClass)) {
			return;
		}

		if (o.buttonType=='image') {
			var li = $tabs.filter('.' + o.selectedClass);			
			_find(li).attr('src', o.actions[(o.actions instanceof Array ? li.index() : li[0].id)].normalImage);
			
			if (action.selectedImage) {
				$(this).attr('src', action.selectedImage);
			} else {
				action.selectedImage = _altImageSrc(action, o.selectedSuffix);
				$(this).attr('src', action.selectedImage);
			}
		}
		$tabs.removeClass(o.selectedClass).removeClass(o.highlightClass);
		$(el).addClass(o.selectedClass);
		
		//
		// 컨텐츠 로드 .................
		if (contentsType=='static') {
			_showContents(el, action);
		} else if (contentsType=='dynamic') {
			var contents = _showContents(el, action);
			if (!contents.hasClass(cachedClass) && 
					action.ajaxOptions && action.ajaxOptions.url && action.ajaxOptions.url.length!=0) {
				$.ajax(action.ajaxOptions);
			}
		}
		//
	};
	
	var f = o.actions instanceof Array;

	$tabs.each(function(k, v) {
		var idx = f ? k : v.id;
		var action = o.actions[idx];
		
		if (!action) {
			action = _anonymousAction(v);
			o.actions[idx] = action;
		}

		_buildAjaxHandler(v, action);
		
		if (o.eventType=='hover' || o.hoverChange) {//button hover event start
			_find(v).hover(function(e, ui) {
				if (o.eventType=='hover')
					_selectTabButton.apply(this, [v, action]);
				else if (o.eventType=='click')
					_hoverTabButton.apply(this, [v, action, true]);
					
			},
			function(e, ui) {
				o.eventType=='click' && _hoverTabButton.apply(this, [v, action, false]);
			});
		}//button hover event end
		if (o.eventType=='click') { 
			_find(v).click(function(e, ui) {
				_selectTabButton.apply(this, [v, action]);
			});
		}
		
	});
	
	//최초 선택될 탭 지정
	var firstAction = f ? o.actions[o.defaultSelect] : o.actions[$($tabs[o.defaultSelect]).attr('id')];
	var contentsTypeFirst = firstAction.contentsType ? firstAction.contentsType : o.contentsType;
	var firstContents = $contents.filter('#' + $($tabs[o.defaultSelect]).attr('id') + o.contentsSuffix);
	_showContents(firstContents, firstAction);

	if (o.ajaxOnload && contentsTypeFirst=='dynamic') {
		if (firstAction.ajaxOptions && firstAction.ajaxOptions.url && firstAction.ajaxOptions.url.length!=0)
			$.ajax(firstAction.ajaxOptions);
	}
};
})(jQuery);