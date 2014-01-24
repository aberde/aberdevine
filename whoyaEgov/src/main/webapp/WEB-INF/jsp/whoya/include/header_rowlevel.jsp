<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<script type="text/javascript" src="<c:out value='/js/whoya/jquery-1.7.min.js' />"></script>
<script type="text/javascript" src="<c:out value='/js/whoya/jquery.extends.js' />"></script>
<script type="text/javascript" src="<c:out value='/js/whoya/jquery.blockUI.js' />"></script>
<script type="text/javascript" src="<c:out value='/js/whoya/jquery.autotab.js' />"></script>
<script type="text/javascript" src="<c:out value='/js/whoya/jquery.ui.effect-1.7.3.min.js' />"></script>
<script type="text/javascript" src="<c:out value='/js/whoya/jquery.form.js' />"></script>
<script type="text/javascript" src="<c:out value='/js/whoya/jquery.floatingMenu.js' />"></script>
<script type="text/javascript" src="<c:out value='/js/whoya/jquery.foldingMenu.js' />"></script>
<script type="text/javascript" src="<c:out value='/js/whoya/jquery.simpleTab.js' />"></script>
<script type="text/javascript" src="<c:out value='/js/whoya/jquery.instanceUpload.js' />"></script>
<script type="text/javascript" src="<c:out value='/js/whoya/common.js' />"></script>

<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlx_pro_full/dhtmlx.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/libCompiler/dhtmlxcontainer.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/libCompiler/dhtmlxcommon.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/libCompiler/corestore.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/libCompiler/core.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxMessage/codebase/dhtmlxmessage.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxAccordion/codebase/dhtmlxaccordion.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxCalendar/codebase/dhtmlxcalendar.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxColorPicker/codebase/dhtmlxcolorpicker.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxCombo/codebase/dhtmlxcombo.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxCombo/codebase/ext/dhtmlxcombo_extra.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxCombo/codebase/ext/dhtmlxcombo_whp.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxCombo/codebase/ext/dhtmlxcombo_group.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxDataProcessor/codebase/dhtmlxdataprocessor.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxDataStore/codebase/datastore.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxEditor/codebase/dhtmlxeditor.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxEditor/codebase/ext/dhtmlxeditor_ext.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxForm/codebase/dhtmlxform.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxForm/codebase/ext/dhtmlxform_item_calendar.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxForm/codebase/ext/dhtmlxform_item_upload.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxForm/codebase/ext/dhtmlxform_item_colorpicker.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxForm/codebase/ext/dhtmlxform_item_container.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxForm/codebase/ext/dhtmlxform_item_combo.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxForm/codebase/ext/dhtmlxform_item_editor.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/dhtmlxgrid.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/dhtmlxgridcell.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_filter.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_group.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_drag.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_data.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_math.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_mcol.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_pgn.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_pivot.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_splt.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_ssc.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_start.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_undo.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_sub_row.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_validation.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_fast.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_markers.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_dhxcalendar.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_combo.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_cntr.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_link.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_clist.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_ra_str.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxLayout/codebase/dhtmlxlayout.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxMenu/codebase/dhtmlxmenu.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxMenu/codebase/ext/dhtmlxmenu_ext.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxPopup/codebase/dhtmlxpopup.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxSlider/codebase/dhtmlxslider.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTabbar/codebase/dhtmlxtabbar.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTabbar/codebase/dhtmlxtabbar_start.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxToolbar/codebase/dhtmlxtoolbar.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTree/codebase/dhtmlxtree.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTree/codebase/ext/dhtmlxtree_attrs.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTree/codebase/ext/dhtmlxtree_dragin.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTree/codebase/ext/dhtmlxtree_ed.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTree/codebase/ext/dhtmlxtree_json.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTree/codebase/ext/dhtmlxtree_kn.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTree/codebase/ext/dhtmlxtree_lf.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTree/codebase/ext/dhtmlxtree_li.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTree/codebase/ext/dhtmlxtree_rl.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTree/codebase/ext/dhtmlxtree_sb.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTree/codebase/ext/dhtmlxtree_srnd.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTree/codebase/ext/dhtmlxtree_start.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTree/codebase/ext/dhtmlxtree_xw.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTreeGrid/codebase/dhtmlxtreegrid.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTreeGrid/codebase/ext/dhtmlxtreegrid_lines.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxTreeGrid/codebase/ext/dhtmlxtreegrid_filter.js' />"></script>
<script type="text/javascript" src="<c:out value='/dhtmlx/dhtmlxWindows/codebase/dhtmlxwindows.js' />"></script>

<!-- link rel="stylesheet" type="text/css" href="/whoya/dhtmlx/dhtmlx_pro_full/dhtmlx.css" -->
<link rel="stylesheet" type="text/css" href="<c:out value='/css/whoya/common.css' />">

<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxCalendar/codebase/dhtmlxcalendar.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxChart/codebase/dhtmlxchart.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxColorPicker/codebase/dhtmlxcolorpicker.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxCombo/codebase/dhtmlxcombo.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxDataView/codebase/dhtmlxdataview.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxGrid/codebase/dhtmlxgrid.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxGrid/codebase/ext/dhtmlxgrid_pgn_bricks.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxLayout/codebase/dhtmlxlayout.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxSlider/codebase/dhtmlxslider.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxTabbar/codebase/dhtmlxtabbar.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxTree/codebase/dhtmlxtree.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxWindows/codebase/dhtmlxwindows.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxMessage/codebase/skins/dhtmlxmessage_dhx_skyblue.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxAccordion/codebase/skins/dhtmlxaccordion_dhx_skyblue.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxCalendar/codebase/skins/dhtmlxcalendar_dhx_skyblue.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxEditor/codebase/skins/dhtmlxeditor_dhx_skyblue.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxForm/codebase/skins/dhtmlxform_dhx_skyblue.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxGrid/codebase/skins/dhtmlxgrid_dhx_skyblue.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxLayout/codebase/skins/dhtmlxlayout_dhx_skyblue.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxMenu/codebase/skins/dhtmlxmenu_dhx_skyblue.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxPopup/codebase/skins/dhtmlxpopup_dhx_skyblue.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxToolbar/codebase/skins/dhtmlxtoolbar_dhx_skyblue.css' />">
<link rel="stylesheet" type="text/css" href="<c:out value='/dhtmlx/dhtmlxWindows/codebase/skins/dhtmlxwindows_dhx_skyblue.css' />">
