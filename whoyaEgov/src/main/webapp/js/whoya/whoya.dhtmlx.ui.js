/**
 * whoya dhtmlx UI
 */
	// dhtmlx 함수
	whoya.dhtmlx = {};

	// dhtmlx의 layout함수
	whoya.dhtmlx.layout = {};
	/**
	 * <pre>
	 * 화면 layout 객체 생성
	 * @param data JSON형식의 UI셋팅 데이터
	 *   layout_target: document.body  // 레이아웃을 표현할 위치
	 *   layout_Pattern: "2U"  // 패턴종류(http://docs.dhtmlx.com/doku.php?id=dhtmlxlayout:layoutpattern)
	 *   dhtmlxwindows_setEffect_efName: "move"  // 윈도우창 효과 대상(http://docs.dhtmlx.com/doku.php?id=dhtmlxwindows:api_method_dhtmlxwindows_seteffect)
	 *   dhtmlxwindows_setEffect_efValue: true  // 윈도우창 효과 설정값.(true/false to enable/disable)
	 * @returns dhtmlXLayoutObject 객체
	 * </pre>
	 */
	whoya.dhtmlx.layout.init = function(data) {
		var whoyaData = {
			layout_target: document.body
			, layout_Pattern: "2U"
			, dhtmlxwindows_setEffect_efName: "move"
			, dhtmlxwindows_setEffect_efValue: true
		};
		$.extend(whoyaData, data);
		
		dhtmlx.image_path = whoya.context + "/dhtmlx/dhtmlx_pro_full/imgs/";  // 기본 이미지 경로 
		var layout = new dhtmlXLayoutObject(whoyaData.layout_target, whoyaData.layout_Pattern);  // 레이아웃 객체(기본은 body)
		// TODO 추후에 dhtmlxWindows 객체로 이동.(aberdevine)
		layout.dhxWins.setEffect(whoyaData.dhtmlxwindows_setEffect_efName, whoyaData.dhtmlxwindows_setEffect_efValue);  // 윈도우창 효과(기본은 윈도우 창 이동시 바로 이동.)
		
		return layout;
	};
	
	// layout의 toolbar함수
	whoya.dhtmlx.layout.toolbar = {};
	
	/**
	 * <pre>
	 * 화면 layout의 toolbar 정의
	 * @param data JSON형식의 UI셋팅 데이터
	 *   layout: layout  // dhtmlXLayoutObject 객체
	 * @returns dhtmlXLayoutObject의 toolbar 객체
	 * </pre>
	 */
	whoya.dhtmlx.layout.toolbar.init = function(data) {
		var whoyaData = {
		};
		$.extend(whoyaData, data);
			
		var toolbar = whoyaData.layout.attachToolbar();
		toolbar.setIconsPath(dhtmlx.image_path);
		
		return toolbar;
	};
	
	/**
	 * <pre>
	 * toolbar의 Button정의
	 * @param data JSON형식의 UI셋팅 데이터
	 *   toolbar: toolbar  // dhtmlXLayoutObject 객체의 toolbar
	 *   btn_Open: true  // 조회버튼출력
	 *   btn_Append: true  // 추가버튼출력
	 *   btn_Delete: true  // 삭제버튼출력
	 *   btn_Undo: true  // 취소버튼출력
	 *   btn_Save: true  // 저장버튼출력
	 *   btn_Print: true  // 인쇄버튼출력
	 *   btn_Excel: true  // 엑셀버튼출력
	 * @returns dhtmlXLayoutObject의 toolbar 객체
	 * </pre>
	 */
	whoya.dhtmlx.layout.toolbar.addButton = function(data) {
		var whoyaData = {
			btn_Open: true
			, btn_Append: true
			, btn_Delete: true
			, btn_Undo: true
			, btn_Save: true
			, btn_Print: true
			, btn_Excel: true
		};
		$.extend(whoyaData, data);
		
		whoyaData.toolbar.addSeparator("button_Separator", 10);
		whoyaData.toolbar.addSpacer("button_Separator");
		if ( whoyaData.btn_Open ) {
			whoyaData.toolbar.addSeparator("sep_Open", 11); 
			whoyaData.toolbar.addButton("btn_Open", 12, "조회", "open.gif");
		}
		if ( whoyaData.btn_Append ) {
			whoyaData.toolbar.addSeparator("sep_Append", 13); 
			whoyaData.toolbar.addButton("btn_Append", 14, "추가", "append.gif");
		}
		if ( whoyaData.btn_Delete ) {
			whoyaData.toolbar.addSeparator("sep_Delete", 15); 
			whoyaData.toolbar.addButton("btn_Delete", 16, "삭제", "close.gif");
		}
		if ( whoyaData.btn_Undo ) {
			whoyaData.toolbar.addSeparator("sep_Undo", 17); 
			whoyaData.toolbar.addButton("btn_Undo", 18, "취소", "undo.gif");
		}
		if ( whoyaData.btn_Save ) {
			whoyaData.toolbar.addSeparator("sep_Save", 19); 
			whoyaData.toolbar.addButton("btn_Save", 20, "저장", "save.gif");
		}
		if ( whoyaData.btn_Print ) {
			whoyaData.toolbar.addSeparator("sep_Print", 21);
			whoyaData.toolbar.addButton("btn_Print", 22, "인쇄", "print.gif");
		}
		if ( whoyaData.btn_Excel ) {
			whoyaData.toolbar.addSeparator("sep_Excel", 23);
			whoyaData.toolbar.addButton("btn_Excel", 24, "엑셀", "excel.gif");
		}
	};

	// layout의 cell함수
	whoya.dhtmlx.layout.cell = {};
	
	/**
	 * <pre>
	 * 화면 layout의 해당 cell 정의
	 * @param data JSON형식의 UI셋팅 데이터
	 *   layout: layout  // dhtmlXLayoutObject 객체
	 *   cell_target: "a"  // 레이아웃 화면선택(기본 "a"셀)
	 *   width: ($(document).width() / 10 * 7)  // 선택된 셀의 넓이를 70%로 설정.(단위:px)
	 *   hideHeader: true  // 헤더 숨김여부
	 * @returns dhtmlXLayoutObject의 cell 객체
	 * </pre>
	 */
	whoya.dhtmlx.layout.cell.init = function(data) {
		var whoyaData = {
			cell_target: "a"
			, width: ($(document).width() / 10 * 7)
			, hideHeader: true
			, setText: ""
		};
		$.extend(whoyaData, data);
		
		var cell = whoyaData.layout.cells(whoyaData.cell_target);  // 레이아웃 화면선택(기본 "a"셀)
		if ( whoyaData.width ) {  // 셀의 넓이가 설정되었을 경우에만 설정.
			cell.setWidth(whoyaData.width);  // 선택된 셀의 넓이설정.(기본 70%, 단위:px)
		}
		
		if ( whoyaData.hideHeader ) {  // 헤더 숨김여부
			cell.hideHeader();  // 헤더 숨기기
		}

		cell.setText(whoyaData.setText);  // 헤더 타이틀
		
		return cell;
	};
	
	/**
	 * <pre>
	 * 화면 layout에 dhtmlxGrid 객체 생성
	 * @param data JSON형식의 UI셋팅 데이터
	 *   cell: cell  // dhtmlXLayoutObject의 cell 객체
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
	 * </pre>
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
	 * <pre>
	 * 화면 layout에 dhtmlxForm 객체 생성
	 * @param data JSON형식의 UI셋팅 데이터
	 * 	 cell: cell  // dhtmlXLayoutObject의 cell 객체
	 *   formData: formData  // dhtmlxForm의 UI데이터
	 * @returns dhtmlxForm 객체
	 * </pre>
	 */
	whoya.dhtmlx.layout.cell.form = function(data) {
		var whoyaData = {
		};
		$.extend(whoyaData, data);
		
		var form = whoyaData.cell.attachForm(whoyaData.formData);
		form.setFontSize("11px");
		
		return form;
	};
	
	/**
	 * <pre>
	 * 화면 layout에 dhtmlxWindows 객체 생성
	 * @param data JSON형식의 UI셋팅 데이터
	 * 	 layout: layout  // dhtmlXLayoutObject 객체
	 *   id: ""  // dhtmlxWindows 객체의 id
	 *   x: 20  // x좌표
	 *   y: 30  // y좌표
	 *   width: 800  // 넓이
	 *   height: 240  // 높이
	 *   setText: ""  // 타이틀
	 *   setModal: true  // 모달창여부
	 *   keepInViewport: true  //
	 *   centerOnScreen: true  // 중앙에 위치
	 * @returns dhtmlxWindows 객체
	 * </pre>
	 */
	whoya.dhtmlx.layout.windows = function(data) {
		var whoyaData = {
			id: ""
			, x: 20
			, y: 30
			, width: 800
			, height: 240
			, setText: ""
			, setModal: true
			, keepInViewport: true
			, centerOnScreen: true
		};
		$.extend(whoyaData, data);
		
		var windows = whoyaData.layout.dhxWins.createWindow(whoyaData.id, whoyaData.x, whoyaData.y, whoyaData.width, whoyaData.height);
		windows.setText(whoyaData.setText);
		windows.setModal(whoyaData.setModal);
		windows.keepInViewport(whoyaData.keepInViewport);
		if ( whoyaData.centerOnScreen ) {
			windows.centerOnScreen();
		}
		
		return windows;
	};
	
	/**
	 * <pre>
	 * grid 또는 form 등의 객체에서 서버로 저장
	 * @param data JSON형식의 UI셋팅 데이터
	 *   url: ""  // 저장 경로
	 *   setTransactionMode_method: "POST"  // 전송메소드
	 *   setTransactionMode_allrow: true  // false: 한줄 데이터 전송, true: 여러줄 데이터 전송(grid에서만 사용)
	 *   setUpdateMode: "off"  //
	 *   enableDataNames: true  // 
	 *   obj: Object  // 데이터를 동기화시킬 객체(dhtmlxGrid, dhtmlxForme등)
	 * @returns dataProcessor 객체
	 * </pre>
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
		
		var dp = new dataProcessor(whoyaData.url);
		dp.setTransactionMode(whoyaData.setTransactionMode_method, whoyaData.setTransactionMode_allrow);
		dp.setUpdateMode(whoyaData.setUpdateMode);
		dp.enableDataNames(whoyaData.enableDataNames);
		dp.init(whoyaData.obj);
		
		return dp;
	};
	
	/**
	 * <pre>
	 * 화면 layout에 statusbar 객체 생성
	 * @param data JSON형식의 UI셋팅 데이터
	 *   layout: layout  // dhtmlXLayoutObject 객체
	 *   id:   // 상태바 id
	 * @returns statusbar 객체
	 * </pre>
	 */
	whoya.dhtmlx.statusbar = function(data) {
		var whoyaData = {
			id: ""
		};
		$.extend(whoyaData, data);
			
		var main_status = whoyaData.layout.attachStatusBar();
		main_status.setText("<div><table><td id='" + whoyaData.id + "activeImg'><img src='" + whoya.context + "/dhtmlx/dhtmlx_pro_full/imgs/run_exc.gif'></td><td id='" + whoyaData.id + "activeStatusBar' valign='middle'></td></table></div>");
		
		return main_status;
    };
    
