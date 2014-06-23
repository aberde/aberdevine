//<!--
/**
 '==============================================================================
 '#	INFORMATION
 '------------------------------------------------------------------------------
 '#	@Author 				: JJang. Jang,Seon-Joo (jeuse7@hanmail.net)
 '# @Reference 			: N/A
 '#	@FileName 			: EventMenu.js
 '#	@Description 		: 메뉴 이벤트 클래스
 '#	@Version 				: 1.0.0
 '#	@CreateDate 		: 2003.12.22
 '#	@UpdateDate 		: 2009.07.10
 '#	@Requirement 		: ../prototype/prototype.js, Field.js
 '#	@Function List	: 
 '#		N/A
 '#		
 '==============================================================================
*/


/**
 * Top Menu RollOver, RollOut Event
 * @param _depthNum1	(메뉴뎁스1 번호)
 * @param _depthNum2	(메뉴뎁스2 번호)
 * @param _depthNum3	(메뉴뎁스3 번호)
  */
  
 /* Define image status
 */
var CONST_IMAGE_Active = "_on";
var CONST_IMAGE_Passive = "_off";
//-->  
  
var EventTopMenu = function() { this.initialize.apply(this, arguments); }
EventTopMenu.prototype = {
	initialize: function(_depthNum1, _depthNum2, _depthNum3, _siteGubun) {
		//Defined Configuration
		this.DEPTH_NUM1 = _depthNum1;
		this.DEPTH_NUM2 = _depthNum2;
		this.DEPTH_NUM3 = _depthNum3;
		this.SITE_GUBUN = _siteGubun;
		this.FIRST_NODE = $("Top-Menu");
		//Defined Member Variables
		this.ARR_MENU = new Array();
		this.IS_ACTIVE = false;
		this.SET_TIME = 300;
		this.BACKGROUND_TARGET = $("LY-Top");
		if (this.SITE_GUBUN == "English") {
			this.BACKGROUND_CSS = "url(../images/Top_navi_bg.jpg) no-repeat right top";
		} else {
			this.BACKGROUND_CSS = "url(../images/Top_navi_bg.jpg) no-repeat right top";
		}
		this.BACKGROUND_IMG = "bg_0";
		this.initEvent();
		this.setDefault();
	},
	
	initEvent: function() {
		var index = 0;
		var ArrDepth1 = this.FIRST_NODE.childNodes;
		for (var i = 0; i < ArrDepth1.length; i++) {
			if (ArrDepth1[i].nodeType == 3 || ArrDepth1[i].tagName != "LI") continue;
			
			var __this = this;
			var depth1 = ArrDepth1[i].getElementsByTagName("a")[0];
			depth1.SubNode = ArrDepth1[i].getElementsByTagName("ul")[0];
			this.setOverEvent(depth1, index);
			this.setOutEvent(depth1);
			
			if (depth1.SubNode) {
				depth1.SubNode.onmouseover = depth1.SubNode.onfocus = function() {
					__this.IS_ACTIVE = true;
				}
				depth1.SubNode.onmouseout = depth1.SubNode.onblur = function() {
					__this.doPassive.call(__this);
				}
				
				var ArrDepth2 = depth1.SubNode.childNodes;
				for (var j = 0; j < ArrDepth2.length; j++) {
					if (ArrDepth2[j].nodeType == 3 || ArrDepth2[j].tagName != "LI") continue;
					
					var depth2 = ArrDepth2[j].getElementsByTagName("a")[0];
					depth2.onmouseover = depth2.onfocus = function() {
						__this.IS_ACTIVE = true;
						Field.doActive(this);
					}
					depth2.onmouseout = depth2.onblur = function() {
						__this.doPassive.call(__this);
						Field.doPassive(this);
					}
				}
			}
			
			this.ARR_MENU[index] = depth1;
			++index;
		}
	},
	
	setDefault: function() {
		if (this.DEPTH_NUM1 == 0) {
			this.BACKGROUND_TARGET.style.background = this.BACKGROUND_CSS.replace("[#BACKGROUND#]", this.BACKGROUND_IMG + 6);
		} else if (this.DEPTH_NUM1 == 6 || (this.DEPTH_NUM1 == 5 && this.SITE_GUBUN == "English")) {
			this.BACKGROUND_TARGET.style.background = this.BACKGROUND_CSS.replace("[#BACKGROUND#]", this.BACKGROUND_IMG + (this.DEPTH_NUM1));
		} else {
			try {
				var depth1 = this.ARR_MENU[this.DEPTH_NUM1 - 1];
				depth1.onmouseover();
				if (depth1.SubNode && this.DEPTH_NUM2 > 0) {
					var depth2 = depth1.SubNode.getElementsByTagName("li")[this.DEPTH_NUM2 - 1].getElementsByTagName("a")[0];
					Field.doActive(depth2);
					depth2.onmouseout = depth2.onblur = function() {
						Field.doActive(depth2);
					}
				}
			} catch (e) {
				alert("[Error] : Check the top menu number!! ");
			}
		}
	},
	
	setOverEvent: function(_target, _index) {
		var __this = this;
		_target.onmouseover = _target.onfocus = function() {
			__this.BACKGROUND_TARGET.style.background = __this.BACKGROUND_CSS.replace("[#BACKGROUND#]", __this.BACKGROUND_IMG + (_index + 1));	
			__this.doActive.call(__this, this);
		}
	},
	
	setOutEvent: function(_target) {
		var __this = this;
		_target.onmouseout = _target.onblur = function() {
			__this.doPassive.call(__this);
		}
	},
	
	doActive: function(_target) {
		this.IS_ACTIVE = true;
		this.doPassiveAll();
		Field.doActive(_target);
		if (_target.SubNode) _target.SubNode.style.display = "block";
	},
	
	doPassive: function() {
		this.IS_ACTIVE = false;
		var __this = this;
		window.setTimeout(function() {
			if (!__this.IS_ACTIVE) {
				__this.doPassiveAll();
				__this.setDefault();
			}
		}, this.SET_TIME);
	},
	
	doPassiveAll: function() {
		for (var i = 0; i < this.ARR_MENU.length; i++) {
			var depth1 = this.ARR_MENU[i];
			Field.doPassive(depth1);
			if (depth1.SubNode) {
				depth1.SubNode.style.display = "none";
			}
		}
	}
}








/**
 * Left Menu RollOver, RollOut Event
 * @param _depthNum1	(메뉴뎁스1 번호)
 * @param _depthNum2	(메뉴뎁스2 번호)
 * @param _depthNum3	(메뉴뎁스3 번호)
 */
var EventLeftMenu = function() { this.initialize.apply(this, arguments); }
EventLeftMenu.prototype = {
	initialize: function(_depthNum1, _depthNum2, _depthNum3) {
		//Defined Configuration
		this.DEPTH_NUM1 = _depthNum1;
		this.DEPTH_NUM2 = _depthNum2;
		this.DEPTH_NUM3 = _depthNum3;
		this.FIRST_NODE = $("LY-Left");
		//Defined Member Variables
		this.ARR_MENU = new Array();
		this.initEvent();
		this.setDefault();
	},
	
	initEvent: function() {
		var MenuNode = this.FIRST_NODE.getElementsByTagName("ul")[0];
		if (MenuNode) {
			var index = 0;
			var ArrDepth2 = MenuNode.childNodes;
			for (var i = 0; i < ArrDepth2.length; i++) {
				if (ArrDepth2[i].nodeType == 3 || ArrDepth2[i].tagName != "LI") continue;
				
				var depth2 = ArrDepth2[i].getElementsByTagName("a")[0];
				if (depth2) {
					depth2.SubNode = ArrDepth2[i].getElementsByTagName("ul")[0];
					this.setOverEvent(depth2);
					this.setOutEvent(depth2);
					if (depth2.SubNode) {
						depth2.SubNode.style.display = "none";
					}
				}
								
				this.ARR_MENU[index] = depth2;
				++index;
			}
		}
	},
	
	setDefault: function() {
		try {
			if (this.DEPTH_NUM2 > 0) {
				var depth2 = this.ARR_MENU[this.DEPTH_NUM2 - 1];
				depth2.onmouseover();
				depth2.onmouseout = depth2.onblur = function() {
					Field.doActive(this);
				}
				if (depth2.SubNode) {
					depth2.SubNode.style.display = "block";
					if (this.DEPTH_NUM3 > 0) {
						var depth3 = depth2.SubNode.getElementsByTagName("li")[this.DEPTH_NUM3 - 1].getElementsByTagName("a")[0];
						depth3.className = "over";
					}
				}
			}
		} catch (e) {
			alert("[Error] : Check the left menu number!! ");
		}
	},
	
	setOverEvent: function(_target) {
		var __this = this;
		_target.onmouseover = _target.onfocus = function() {
			__this.doActive.call(__this, this);
		}
	},
	
	setOutEvent: function(_target) {
		var __this = this;
		_target.onmouseout = _target.onblur = function() {
			__this.doPassive.call(__this, this);
		}
	},
	
	doActive: function(_target) {
		Field.doActive(_target);
	},
	
	doPassive: function(_target) {
		Field.doPassive(_target);
	}
}
//-->