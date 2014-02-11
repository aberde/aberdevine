<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java" %> 
<%@page import="java.util.*"%>
<%@ include file="/WEB-INF/jsp/co/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<META http-equiv="Cache-Control" content="no-cache">
	<META http-equiv="Pragma" content="no-cache">
	<META http-equiv="Expires" content="-1">
	<META http-equiv="X-UA-Compatible" content="IE=edge" />
<title>::3D PLM 기반 스마트 철도차량 유지보수 시스템::</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
<link href="${pageContext.request.contextPath}/css/custom-theme/jquery-ui-1.10.3.custom.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/grid/css/ui.jqgrid.css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.3.custom.js"></script>
<script src="${pageContext.request.contextPath}/grid/js/i18n/grid.locale-kr.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/grid/js/jquery.jqGrid.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/tree/jquery.treeview.css" />
<script src="${pageContext.request.contextPath}/tree/lib/jquery.cookie.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/tree/jquery.treeview.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/tree/screen.css" />
<style type="text/css">
body {
	font-size: 110%;
	font-family:NanumGothic;
	background: #fff;
	color: #333;
}
</style>

 <script type="text/javascript">
 function fnSelectPartCd(){
 	 var PART_CD = $("#PART_CD").val();
 	 if(PART_CD =="")
 	 {
 		 alert("선택한 부품이 없습니다.");
 		 return;
 	 }
 	 opener.initForm();  // 데이터 초기화
	 opener.document.getElementById("txtPart").value = PART_CD;
	 opener.document.getElementById("PART_CD").value = PART_CD;
	 self.close();
 }
 function fnSelectPartClose(){
	 self.close();
 }
 function fnGetBomPostData() {
	  var ary = $( "#pForm" ).serializeArray();
	  var obj = $("#gridBom").jqGrid('getGridParam', 'postData'); 
	  for (var a = 0; a < ary.length; a++) 
		  obj[ary[a].name] = ary[a].value;

	  return obj;
}

function fnGetDataPostData() {
	  var ary = $( "#pForm" ).serializeArray();
	  var obj = $("#gridData").jqGrid('getGridParam', 'postData'); 
	  for (var a = 0; a < ary.length; a++) 
		  obj[ary[a].name] = ary[a].value;

	  return obj;
}
function fnClickBomData(obj, path, id, lvl)
{
	$("#BOM_ID").val(id);
	$("#OR_PARENT_BOM_ID").val(id);
	for(i = 0; i < 5; i++)
		path = path.replace("|", ">");

	$("#bomPath").text(path);
	var url = "";

	url = '${pageContext.request.contextPath}/sy/selectBomList.do';
	$("#gridBom").jqGrid('setGridParam', { url: url,datatype:'json',postData: fnGetBomPostData()});
    $("#gridBom").trigger("reloadGrid");
	
    
	url = '${pageContext.request.contextPath}/sy/selectPartDetailList.do';
    $("#gridData").jqGrid('setGridParam', { url: url,datatype:'json',postData: fnGetDataPostData()});
    $("#gridData").trigger("reloadGrid");
}
 
jQuery(document).ready(function(){
	jQuery("#gridBom").jqGrid({
		datatype: "local",
		height: 137,
		width : 870,
		mtype: 'POST',
	   	colNames:['NO','BOM코드','BOM명', '수량'],
	   	colModel:[
	  	   	{name:'ROWNUM',index:'ROWNUM', width:14, sortable:false, align: "center"},
	   		{name:'BOM_ID',index:'BOM_ID', width:60, align: "center"},
	   		{name:'BOM_NM',index:'BOM_NM', width:50 , align: "center"},
	   		{name:'PART_AMT',index:'PART_AMT', width:10, align: "center"}
	   	],
	   	rowNum:10000,
	 	multiselect: false,
	   	sortname: 'BOM_ID',
	    sortorder: "asc",
	    emptyrecords: "조회된 데이터가 없습니다.", 
	    viewrecords: true,
	    forceFit:     true,
	    onSelectRow : function (rowId) {
	    	var rowData = $("#gridBom").getRowData(rowId);
	    	$("#BOM_ID").val(rowData.BOM_ID);
	    	$("#OR_PARENT_BOM_ID").val("");
	    	var url = '${pageContext.request.contextPath}/sy/selectPartDetailList.do';
	        $("#gridData").jqGrid('setGridParam', { url: url,datatype:'json',postData: fnGetDataPostData()});
	        $("#gridData").trigger("reloadGrid");
		},
		gridComplete: function () {
			var ids = jQuery("#gridBom").jqGrid('getDataIDs'); 
		    for(var i=0;i < ids.length;i++){ 
		    	 jQuery("#gridBom").jqGrid("setCell", ids[i], "ROWNUM", i+1, "");
		    }

		},
	   	loadComplete: function(data) {

	   		
	    }
	 });
	jQuery("#gridData").jqGrid({
		datatype: "local",
		height: 137,
		width : 870,
		mtype: 'POST',
	   	colNames:['NO','부품코드','품명', '규격'],
	   	colModel:[
	  	   	{name:'ROWNUM',index:'ROWNUM', width:30, sortable:false, align: "center"},
	   		{name:'PART_CD',index:'PART_CD', width:60, align: "center"},
	   		{name:'PART_NM',index:'PART_NM', width:90 , align: "center"},
	   		{name:'PART_SPEC',index:'PART_SPEC', width:100}
	   	],
	   	rowNum:10000,
	 	multiselect: false,
	   	sortname: 'PART_CD',
	    sortorder: "desc",
	    emptyrecords: "조회된 데이터가 없습니다.", 
	    viewrecords: true,
	    forceFit:     true,
	    onSelectRow : function (rowId) {
	    	var rowData = $("#gridData").getRowData(rowId);
	    	$("#PART_CD").val(rowData.PART_CD);
		},
		gridComplete: function () {
			var ids = jQuery("#gridData").jqGrid('getDataIDs'); 
		    for(var i=0;i < ids.length;i++){ 
		    	 jQuery("#gridData").jqGrid("setCell", ids[i], "ROWNUM", i+1, "");
		    }

		},
	   	loadComplete: function(data) {

	   		
	    }
	 });
	$("#browser").treeview();
});

</script>
</head>
<body>
  <form id="pForm">
   <input type="hidden" id="BOM_ID" name="BOM_ID" value="${PARAM.BOM_ID}"></input>
   <input type="hidden" id="TRN_KIND_CD" name="TRN_KIND_CD" value="${PARAM.TRN_KIND_CD}"></input>
   <input type="hidden" id="TRN_KIND_NM" name="TRN_KIND_NM" value="${PARAM.TRN_KIND_NM}"></input>
   <input type="hidden" id="BOM_LVL" name="BOM_LVL" value=""></input>
   <input type="hidden" id="PARENT_BOM_ID" name="PARENT_BOM_ID" value=""></input>
   <input type="hidden" id="OR_PARENT_BOM_ID" name="OR_PARENT_BOM_ID" value=""></input>
   <input type="hidden" id="UP_PARENT_BOM_ID" name="UP_PARENT_BOM_ID" value=""></input>
   <input type="hidden" id="CRG_TYPE_CD" name="CRG_TYPE_CD" value="${PARAM.CRG_TYPE_CD}"></input>
   <input type="hidden" id="CRG_TYPE_NM" name="CRG_TYPE_NM" value="${PARAM.CRG_TYPE_NM}"></input>
   <input type="hidden" id="CRG_CD" name="CRG_CD" value="${PARAM.CRG_CD}"></input>
   <input type="hidden" id="CRG_NM" name="CRG_NM" value="${PARAM.CRG_NM}"></input>
   <input type="hidden" id="PRG_CD" name="PRG_CD" value="${PARAM.PRG_CD}"></input>
   <input type="hidden" id="PART_CD" name="PART_CD" value=""></input>
   <input type="hidden" id="PART_SN" name="PART_SN" value=""></input>
   <input type="hidden" id="GRS_CD" name="GRS_CD" value=""></input>
   <input type="hidden" id="ST" name="ST" value="${PARAM.ST}"></input>
   <input type="hidden" id="ED" name="ED" value="${PARAM.ED}"></input>
   <input type="hidden" id="ISPT_TYPE_CD" name="ISPT_TYPE_CD" value="${PARAM.ISPT_TYPE_CD}"></input>

  </form>
<div style="width:60%; height:60%">
   <div class="map02 font10" style="margin-top: 20px; font-size: 1.2em;">부품검색</div>
  <div id="container" class="clear_both"> 
    <!-- 네비게이션 -->
    <div class="lnb10 float_left">
      <ul class="clear_both">
        <li class="float_left"><img src="${pageContext.request.contextPath}/images/lnb_icon01.png" /></li>
        <li class="float_left font03 lnb03_01">BOM Tree</li>
      </ul>
      <div style="overflow-y:scroll;height:520px;font-size:14px">
         <ul id="browser" class="filetree">
		         <li><span class="folder">${PARAM.TRN_KIND_NM}</span>
<%
try
{
int preLvl = 0;
List<HashMap<String, String>> bomList = (List<HashMap<String, String>>)request.getAttribute("BOM_ALL_DATA");

for (int i = 0; i < bomList.size(); i++) {
	HashMap<String, String> mapData = (HashMap<String, String>)bomList.get(i);
	int lvl = Integer.parseInt(String.valueOf(mapData.get("BOM_LVL")));
    if(lvl == 1)
    {
%>
        <ul>
         <li  class="closed"><span class="folder" onClick="javascript:fnClickBomData(this, '<%=mapData.get("BOM_PATH") %>', '<%=mapData.get("BOM_ID") %>', '<%=String.valueOf(mapData.get("BOM_LVL")) %>');"><%=mapData.get("BOM_NM") %></span>
		  <%
		    mapData = (HashMap<String, String>)bomList.get(i+1);
			lvl = Integer.parseInt(String.valueOf(mapData.get("BOM_LVL")));
			if(lvl == 2)
			{
				i++;
				%>
			       <ul>
		<%
				   for (; i < bomList.size(); i++) {
					    mapData = (HashMap<String, String>)bomList.get(i);
						lvl = Integer.parseInt(String.valueOf(mapData.get("BOM_LVL")));
						if(lvl == 1)
						{
							i--;
							break;
						}
					%>
					         <li  class="closed"><span class="folder" onClick="javascript:fnClickBomData(this, '<%=mapData.get("BOM_PATH") %>', '<%=mapData.get("BOM_ID") %>', '<%=String.valueOf(mapData.get("BOM_LVL")) %>');"><%=mapData.get("BOM_NM") %></span>
									          <%
							    mapData = (HashMap<String, String>)bomList.get(i+1);
								lvl = Integer.parseInt(String.valueOf(mapData.get("BOM_LVL")));
								if(lvl == 3)
								{
									i++;
									%>
								       <ul>
							  <%
								     for (; i < bomList.size(); i++) {
										mapData = (HashMap<String, String>)bomList.get(i);
										lvl = Integer.parseInt(String.valueOf(mapData.get("BOM_LVL")));
										if(lvl != 3)
										{
											i--;
											break;
										}
							  %>
							         <li class="closed"><span class="folder" onClick="javascript:fnClickBomData(this, '<%=mapData.get("BOM_PATH") %>', '<%=mapData.get("BOM_ID") %>', '<%=String.valueOf(mapData.get("BOM_LVL")) %>');"><%=mapData.get("BOM_NM") %></span></li>
							<%	
							       	  } 
							  %>
							  		 </ul>
							<%
							    }
					%>
					   
					<%
					}
		%>		 
	       </ul>
		<%
			 }
%>
	 </ul>
<%
    }
 }
}
catch(Exception ex)
{
	//System.err.println(ex.getMessage());
}
%>
					</li>
				</ul>  
			</div>  
    </div>
    <!--우-->
    <div id="content01" class="float_right"> 
      <!-- step1 -->

      <div  class="content01 clear_both content29">
        <div class="float_left">
          <dl>
            <span class="float_left content03_01"><img src="${pageContext.request.contextPath}/images/btn_arrow.png"/></span>
            <dt class="font07 float_left lnb03 content03_01">차종 : </dt>
            <dd class="float_left font09 content03_01 lnb03">${PARAM.TRN_KIND_NM}
            </dd>
            <dd class="float_left content_line06"><img src="${pageContext.request.contextPath}/images/line01.gif" /></dd>
          </dl>
        </div>
        <div class="float_left">
          <dl>
            <span class="float_left content03_01"><img src="${pageContext.request.contextPath}/images/btn_arrow.png"/></span>
            <dt class="font07 float_left lnb03 content03_01">차호 : </dt>
            <dd class="float_left font09 content03_01 lnb03">${PARAM.CRG_NM}[${PARAM.CRG_TYPE_NM}]</dd>
            <dd class="float_left content_line06"><img src="${pageContext.request.contextPath}/images/line01.gif" /></dd>
          </dl>
        </div>
        <div class="float_left">
          <dl>
            <span class="float_left content03_01"><img src="${pageContext.request.contextPath}/images/btn_arrow.png"/></span>
            <dt class="font07 float_left lnb03 content03_01">BOM : </dt>
            <dd class="float_left font09 content03_01 lnb03" id="bomPath"></dd>
          </dl>
        </div>
      </div>
      <div class="content05">
        <ul class="font07 clear_both" >
          <li class="float_left"><img src="${pageContext.request.contextPath}/images/btn_arrow.png"/></li>
          <li class="float_left lnb03 ">BOM 조회결과</li>
        </ul>
      </div>
      <!-- 빈공간 스타일 삭제하시면 됩니다 -->
      <div class="content12" ><table id="gridBom"></table></div>
      <div class="content05">
        <ul class="font07 clear_both" >
          <li class="float_left"><img src="${pageContext.request.contextPath}/images/btn_arrow.png"/></li>
          <li class="float_left lnb03 ">부품 조회결과</li>
        </ul>
      </div>
      <div class="content12"> <table id="gridData"></table></div>
      <div class="content05">
       <a href="javascript:fnSelectPartClose();">
        <div class="float_right btn06 lnb03">닫기</div>
        </a> <a href="javascript:fnSelectPartCd();">
        <div class="float_right btn06">선 택</div>
        </a>
      </div>
    </div>

  </div>
</div>
</body>
</html>
