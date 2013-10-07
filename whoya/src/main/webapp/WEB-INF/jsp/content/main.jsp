<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>메뉴</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/include/header.jsp" />

<script>
var tabArray = new Array(30);

function init(){	

    dhtmlx.image_path="<c:url value='/dhtmlx/dhtmlx_pro_full/imgs/'/>";

	var main_layout = new dhtmlXLayoutObject(document.body, "2U");
	main_layout.attachHeader("header");

	/* 상단 풀다운메뉴´ */
    var main_pulldown_menu = main_layout.attachMenu();
	main_pulldown_menu.setIconsPath(dhtmlx.image_path);
    main_pulldown_menu.setOpenMode("win");

    var menuData =  '<?xml version="1.0"?>';
    	menuData += '<menu>';
    	<c:forEach items="${menuList}" var="list" varStatus="status">
    	<c:if test="${i>list.LV}">menuData += '</item></item>';</c:if>
    	<c:if test="${list.MNUFNC==NULL}">menuData += '<item id="${list.MNUID}" text="${list.MNUNM}">';</c:if>
    	<c:if test="${list.MNUFNC!=NULL}">menuData += '<item id="${list.MNUID}" text="${list.MNUNM}"/>';</c:if>
    	<c:set var="i" value="${list.LV}"/>
    	</c:forEach>
    	menuData += '</item>';
    	menuData += '</menu>';
    	
    main_pulldown_menu.loadXMLString(menuData);
	main_pulldown_menu.attachEvent("onClick", function( id ){
		choiceMenu(main_body_tabbar, main_pulldown_menu, id);
	})

	main_layout.hideMenu();
	main_layout.attachEvent("onExpand", function(){
		main_layout.hideMenu();
	})

    main_layout.attachEvent("onCollapse", function(){
		main_layout.showMenu();
	})

	var a = main_layout.cells("a");
	a.setText("메뉴");
	a.setWidth("250");

	/* 탭바 */
	var b = main_layout.cells("b");
	var main_body_tabbar = b.attachTabbar();
	//var main_body_tabbar = b.attachTabbar("bottom");
	main_body_tabbar.enableTabCloseButton(true);
	main_body_tabbar.setHrefMode("iframes-on-demand");
	main_body_tabbar.attachEvent("onTabClose", function(id){ 
	    
		for (var j = 0; j < tabArray.length; j++){
			if (tabArray[j] == id) {
				tabArray[j] = "";
			} 
		}
		return true;
	});

	/* 좌측 트리메뉴 */	
	var main_tree_menu = a.attachTree();
	main_tree_menu.setIconsPath(dhtmlx.image_path);
	//main_tree_menu.setIconSize("", "");
	main_tree_menu.enableHighlighting("1");
	//main_tree_menu.enableKeyboardNavigation(true);
	//main_tree_menu.enableAutoSavingSelected(true);
	//main_tree_menu.enableAutoTooltips(1);
	
	//서버로부터 트리메뉴정보를 가져온다 
    var treeData = [
		<c:forEach items="${menuList}" var="list" varStatus="status">
		<c:if test="${status.count>1}">,</c:if>
		["${list.MNUID}","${list.HMNUID}","${list.MNUNM}"]
		</c:forEach>  
	];
    main_tree_menu.loadJSArray(treeData);
    //main_tree_menu.openAllItemsDynamic();
    main_tree_menu.setOnClickHandler(function ( id ) {
    	if(main_tree_menu.getItemText(id).indexOf(")")==-1) {
			choiceMenu(main_body_tabbar, main_tree_menu, id);
        }
    });

	/* 하단 상태바  */
	var main_status = main_layout.attachStatusBar();
	main_status.setText("<div><table><td id='activeImg'><img src='/whoya/dhtmlx/dhtmlx_pro_full/imgs/run_exc.gif'></td><td id='activeStatusBar' valign='middle'></td></table></div>"); 
	document.getElementById("activeStatusBar").innerHTML = "온라인";
}

function choiceMenu(main_body_tabbar, main_tree_menu, id){
	var flag = true;
	var arrayIdx = 0;
	for(var i = 0; i < tabArray.length; i++) {
		if(tabArray[i] == undefined || tabArray[i] == "" ) {
			arrayIdx=(i+1);
		}
	}

	for(var j = 0; j < tabArray.length; j++) {
		if (tabArray[j] == id) {
			flag = false;
			break;
		} 
	}
	if(!flag) {
		main_body_tabbar.setTabActive(id);
		return;
	}
	
	var srcJsp = id.substr(0,2).toLowerCase()+"/"+id+".do";
	//alert(srcJsp)
	main_body_tabbar.addTab(id, main_tree_menu.getItemText(id), "140");
	main_body_tabbar.setContentHref(id, srcJsp);
	main_body_tabbar.setTabActive(id);
	tabArray[(arrayIdx-1)] = id;
}

$(document).ready(function() {
	init();
});
</script>
</head>
<body>
<div id="header" style="height:50px; width:100%; background-image:url(/whoya/images/main/main_top.png)" align="right">
<table border="0" height="100%">
<tr valign="bottom"><td>
	<input type="image" src="<c:url value='/images/main/main_btn.png'/>" onClick="javacript:location.href='index.jsp';"/>
    </td>
  <td width="1px"></td>
</tr>
    </table>
</div>
</body>
</html>
