/**
 * whoya dhtmlx UI
 */
(function( window, undefined ) {
	whoya = function() {};
	whoya.dhtmlx = function() {};
	
	/**
	 * 화면 layout 객체 생성
	 * @param data JSON형식의 UI셋팅 데이터
	 *   context: ""  // 해당 웹서비스의 Context
	 *   layout_target: document.body  // 레이아웃을 표현할 위치
	 *   layout_Pattern: "2U"  // 패턴종류(http://docs.dhtmlx.com/doku.php?id=dhtmlxlayout:layoutpattern)
	 *   dhtmlxwindows_setEffect_efName: "move"  // 윈도우창 효과 대상(http://docs.dhtmlx.com/doku.php?id=dhtmlxwindows:api_method_dhtmlxwindows_seteffect)
	 *   dhtmlxwindows_setEffect_efValue: true  // 윈도우창 효과 설정값.(true/false to enable/disable)
	 * @returns dhtmlXLayoutObject 객체
	 */
	whoya.dhtmlx.layout = function(data) {
		var whoyaData = {
			context: ""
			, layout_target: document.body
			, layout_Pattern: "2U"
			, dhtmlxwindows_setEffect_efName: "move"
			, dhtmlxwindows_setEffect_efValue: true
		};
		$.extend(whoyaData, data);
		
		dhtmlx.image_path = whoyaData.context + "/dhtmlx/dhtmlx_pro_full/imgs/";  // 기본 이미지 경로 
		var layout = new dhtmlXLayoutObject(whoyaData.layout_target, whoyaData.layout_Pattern);  // 레이아웃 객체(기본은 body)
		// TODO 추후에 dhtmlxWindows 객체로 이동.(aberdevine)
		layout.dhxWins.setEffect(whoyaData.dhtmlxwindows_setEffect_efName, whoyaData.dhtmlxwindows_setEffect_efValue);  // 윈도우창 효과(기본은 윈도우 창 이동시 바로 이동.)
		
		return layout;
	};
	
	/**
	 * 화면 layout의 toolbar 정의
	 * @param data JSON형식의 UI셋팅 데이터
	 *   layout: layout  // dhtmlXLayoutObject 객체
	 * @returns dhtmlXLayoutObject의 toolbar 객체 
	 */
	whoya.dhtmlx.layout.toolbar = function(data) {
		var whoyaData = {
		};
		$.extend(whoyaData, data);
			
		var toolbar = whoyaData.layout.attachToolbar();
		toolbar.setIconsPath(dhtmlx.image_path);
		
		return toolbar;
	};
	
	/**
	 * 화면 layout의 해당 cell 정의
	 * @param data JSON형식의 UI셋팅 데이터
	 *   layout: layout  // dhtmlXLayoutObject 객체
	 *   cell_target: "a"  // 레이아웃 화면선택(기본 "a"셀)
	 *   width: ($(document).width() / 10 * 7)  // 선택된 셀의 넓이를 70%로 설정.(단위:px)
	 *   hideHeader: true  // 헤더 숨김여부
	 * @returns dhtmlXLayoutObject의 cell 객체 
	 */
	whoya.dhtmlx.layout.cell = function(data) {
		var whoyaData = {
			cell_target: "a"
			, width: ($(document).width() / 10 * 7)
			, hideHeader: true
		};
		$.extend(whoyaData, data);
		
		var cell = whoyaData.layout.cells(whoyaData.cell_target);  // 레이아웃 화면선택(기본 "a"셀)
		cell.setWidth($(document).width() / 10 * 7);  // 선택된 셀의 넓이를 70%로 설정.(단위:px)
		
		if ( whoyaData.hideHeader ) {  // 헤더 숨김여부
			cell.hideHeader();  // 헤더 숨기기
		}
		
		return cell;
	};
	
	/**
	 * 화면 layout에 dhtmlxGrid 객체 생성
	 * @param data JSON형식의 UI셋팅 데이터
	 *   setHeader: ""  // 컬럼제목
	 *   setColumnIds: ""  // 컬럼아이디
	 *   setInitWidths: ""  // 컬럼넓이
	 *   setColAlign: ""  // 컬럼정렬
	 *   setColTypes: ""  // 컬럼type
	 *   enableResizing: ""  // 컬럼사이즈조절
	 *   enableTooltips: ""  // 툴팁 출력유무
	 *   setColSorting: ""  // 컬럼 정렬
	 *   enableMultiselect: "true"  // grid 여러줄 선택여부
	 *   enableBlockSelection: "false"  // 
	 *   setColumnHidden: []  // 숨길컬럼
	 * @returns dhtmlxGrid 객체
	 */
	whoya.dhtmlx.layout.cell.grid = function(data) {
		var whoyaData = {
			setHeader: ""
			, setColumnIds: ""
			, setInitWidths: ""
			, setColAlign: ""
			, setColTypes: ""
			, enableResizing: ""
			, enableTooltips: ""
			, setColSorting: ""
			, enableMultiselect: "true"
			, enableBlockSelection: "false"
			, setColumnHidden: []
		};
		$.extend(whoyaData, data);
		
		var grid = whoyaData.cell.attachGrid();  // 그리드 객체생성
		grid.setIconsPath(dhtmlx.image_path);
		
		grid.setHeader(whoyaData.setHeader);
		grid.setColumnIds(whoyaData.setColumnIds);
		grid.setInitWidths(whoyaData.setInitWidths);
		grid.setColAlign(whoyaData.setColAlign);
		grid.setColTypes(whoyaData.setColTypes);
		grid.enableResizing(whoyaData.enableResizing);
		grid.enableTooltips(whoyaData.enableTooltips);
		grid.setColSorting(whoyaData.setColSorting);
		
		grid.enableMultiselect(whoyaData.enableMultiselect); 
		grid.enableBlockSelection(whoyaData.enableBlockSelection);
		grid.enableUndoRedo();
		grid.enableSmartRendering(true, 100);
		
		$.each(whoyaData.setColumnHidden, function() {
			grid.setColumnHidden(this.id, true);
		});
		
		grid.init();
		
		return grid;
	};
	
	/**
	 * grid 또는 form 등의 객체에서 서버로 저장
	 * @param data JSON형식의 UI셋팅 데이터
	 *   url: ""  // 저장 경로
	 *   setTransactionMode_method: "POST"  // 전송메소드
	 *   setTransactionMode_allrow: true  // false: 한줄 데이터 전송, true: 여러줄 데이터 전송(grid에서만 사용)
	 *   setUpdateMode: "off"  //
	 *   enableDataNames: true  // 
	 *   obj: Object  // 데이터를 동기화시킬 객체(dhtmlxGrid, dhtmlxForme등)
	 * @returns dataProcessor 객체
	 */
	whoya.dhtmlx.dataProcessor = function(data) {
		var whoyaData = {
			url: ""
			, setTransactionMode_method: "POST"
			, setTransactionMode_allrow: true
			, setUpdateMode: "off"
			, enableDataNames: true
		};
		$.extend(whoyaData, data);
		
		var dp = new dataProcessor(whoya.url);
		dp.setTransactionMode(whoyaData.setTransactionMode_method, whoyaData.setTransactionMode_allrow);
		dp.setUpdateMode(whoyaData.setUpdateMode);
		dp.enableDataNames(whoyaData.enableDataNames);
		dp.init(whoyaData.obj);
		
		return dp;
	};
	
	/**
	 *  화면 layout에 statusbar 객체 생성
	 * @param data JSON형식의 UI셋팅 데이터
	 *   layout: layout  // dhtmlXLayoutObject 객체
	 * @returns statusbar 객체
	 */
	whoya.dhtmlx.statusbar = function(data) {
		var whoyaData = {
		};
		$.extend(whoyaData, data);
			
		var main_status = whoyaData.layout.attachStatusBar();
		main_status.setText("<div><table><td id='activeImg'><img src='/dhtmlx/dhtmlx_pro_full/imgs/run_exc.gif'></td><td id='activeStatusBar' valign='middle'></td></table></div>");
		
		return main_status;
    };
})(window);