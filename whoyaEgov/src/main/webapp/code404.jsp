<%@page contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	taglib uri="/el-functions" prefix="ef"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>발송메일내역</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/whoya/include/header.jsp" />
<script type="text/javascript" src="<c:out value='/js/whoya/whoya.dhtmlx.ui.js' />"></script>

<script type="text/javascript">
$(function(document) {
	// #########################################
	// ## 레이아웃생성
	// #########################################
	var layout = whoya.dhtmlx.layout();
	//#########################################
	
	
	// #########################################
	// ## layout cell a 
	// #########################################
	var cellData = {};
	cellData.layout = layout;
	// 화면 layout의 해당 cell 정의 
	var cell = whoya.dhtmlx.layout.cell(cellData);
	// #########################################
	
	
	// #########################################
	// ## layout cell a에 grid생성
	// #########################################
	var aCellGridData = {};
	aCellGridData.cell = cell;
	aCellGridData.setHeader = "상태,발신자,수신자,제목,날짜,메세지ID";
	aCellGridData.setColumnIds = "sndngResultCode,dsptchPerson,recptnPerson,sj,sndngDe,mssageId";
	aCellGridData.setInitWidths = "100,100,150,*,100,100";
	aCellGridData.setColAlign = "center,center,center,center,center,center";
	aCellGridData.setColTypes = "ro,ro,ro,ro,ro,ro";
	aCellGridData.enableResizing = "false,true,false,false,false,false";
	aCellGridData.enableTooltips = "false,false,false,false,false,false";
	aCellGridData.setColSorting = "str,str,str,str,str,str";
	aCellGridData.setColumnHidden = [
		{ id: 5 }
	];
	// 화면 layout cell a에 dhtmlxGrid 객체 생성.
	var grid = whoya.dhtmlx.layout.grid(aCellGridData);
	// #########################################
	
});
</script>
</head>
<body>
</body>
</html>
