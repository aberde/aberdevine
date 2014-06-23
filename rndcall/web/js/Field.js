//<!--
/**
 '==============================================================================
 '#	INFORMATION
 '------------------------------------------------------------------------------
 '#	@Author 				: JJang. Jang,Seon-Joo (jeuse7@hanmail.net)
 '# @Reference 			: Kent. Y, Park (kkabi71@gmail.com)
 '#	@FileName 			: Field.js
 '#	@Description		: Field	관련 클래스
 '#	@Version 				: 1.0.0
 '#	@CreateDate 		: 2007.06.14
 '#	@UpdateDate 		: N/A
 '#	@Requirement 		: ../prototype/prototype.js, String.js
 '#	@Function List	: 
 '#		N/A
 '#		
 '==============================================================================
*/


var Field = {
	/**
	 * 이미지를 활성화 시킨다.
	 * @param _Anchor	(Anchor 객체)
	 */
	doActive: function(_Anchor) {
		var element = _Anchor.getElementsByTagName("img")[0];
		var imgActive = CONST_IMAGE_Active || "_on";
		var imgPassive = CONST_IMAGE_Passive || "_off";
		element.src = element.src.replace(imgPassive, imgActive);
	},

	/**
	 * 이미지를 비활성화 시킨다.
	 * @param _Anchor	(Anchor 객체)
	 */
	doPassive: function (_Anchor) {
		var element = _Anchor.getElementsByTagName("img")[0];
		var imgActive = CONST_IMAGE_Active || "_on";
		var imgPassive = CONST_IMAGE_Passive || "_off";
		element.src = element.src.replace(imgActive, imgPassive);
	},
	
	/**
	 * Zoom 환경설정
	 */
	Zoom: {
		ZoomRate: 8,
		MaxZoom: 140,
		MinZoom: 92,
		NowZoom: 100,
		ElementName: "LY-Wrapper"
	},
	/**
	 * 화면을 확대한다.
	 * @param _elementName	(줌인 객체명)
	 */
	zoomIn: function (_elementName) {
		var element = _elementName || this.Zoom.ElementName;
		if (this.Zoom.NowZoom < this.Zoom.MaxZoom) {
			this.Zoom.NowZoom += this.Zoom.ZoomRate;
			$(element).style.zoom = this.Zoom.NowZoom + "%";
		}
	},
	/**
	 * 화면을 축소한다.
	 * @param _elementName	(줌아웃 객체명)
	 */
	zoomOut: function (_elementName) {
		var element = _elementName || this.Zoom.ElementName;
		if (this.Zoom.NowZoom > this.Zoom.MinZoom) {
			this.Zoom.NowZoom -= this.Zoom.ZoomRate;
			$(element).style.zoom = this.Zoom.NowZoom + "%";
		}
	}
}
//-->
