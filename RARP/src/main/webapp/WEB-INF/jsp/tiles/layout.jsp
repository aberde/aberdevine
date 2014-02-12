<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java" %> 

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/rarp-tags.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%   
	response.setHeader("Cache-Control","no-store");   
	response.setHeader("Pragma","no-cache");   
	response.setDateHeader("Expires",0);   
	if (request.getProtocol().equals("HTTP/1.1")) 
	        response.setHeader("Cache-Control", "no-cache"); 
	
	String sMenu = String.valueOf(request.getAttribute("MENU"));
	
	
%>  
<%@ include file="/WEB-INF/jsp/co/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META http-equiv="Cache-Control" content="no-cache">
<META http-equiv="Pragma" content="no-cache">
<META http-equiv="Expires" content="-1">
<META http-equiv="X-UA-Compatible" content="IE=edge" />

<title><tiles:getAsString name="title"/></title> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
<link href="${pageContext.request.contextPath}/css/custom-theme/jquery-ui-1.10.3.custom.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/grid/css/ui.jqgrid.css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.3.custom.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.bgiframe.js"></script>
<script src="${pageContext.request.contextPath}/grid/js/i18n/grid.locale-kr.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/grid/js/jquery.jqGrid.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/tree/jquery.treeview.css" />
<script src="${pageContext.request.contextPath}/tree/lib/jquery.cookie.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/tree/jquery.treeview.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.jqGrid.fluid.js" type="text/javascript"></script>
		
	<style type="text/css">
     .noDataDiv{
	     position:absolute;
	     z-index:10000;
	     font-family:NanumGothic;
	     font-size:120%;
	     diplay:none;
     }
	</style>
<script type="text/javascript" >

/* 변수 영역 */
var menuId = "${MENU}";
var partname ="";
var bEndLoadModel = false;
var selBomId = "";
var selPartCd ="";
var selGrsCd = "";
var isBomComplete = false;
var isDataComplete = false;
var isDataEvtComplete = false;
var bomPath = [];
var partPath = [];
var snsDt = "";
var rfidTagId = '';
var rowEvt = true;
var bSelectRow = false;
var bPageInit = true;
/* 함수 영역
*/

//개별 부품 선택
function fnSelectOnly(part)
{
	var parentBomId = "";
	var part_pos = part.split('_');
	selPartCd = part_pos[0];
	selGrsCd = part_pos[1];
	var postData =  {TRN_KIND_CD: $( "#TRN_KIND_CD" ).val()
			,CRG_CD: $( "#CRG_CD" ).val()
			,PRG_CD: $( "#PRG_CD" ).val()
			,CRG_TYPE_CD: $( "#CRG_TYPE_CD" ).val()
			,PART_CD: selPartCd
	        ,GRS_CD: selGrsCd
			};
	
	jQuery.ajax({
        type:"POST",
        url:"${pageContext.request.contextPath}/sy/selectBomInfo.do",
        data: postData,
        dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
        success : function(data) {
        	$.each(data, function(key, value) {    	
        		selBomId = value["BOM_ID"];
        		parentBomId = value["PARENT_BOM_ID"];
        		$( "#BOM_LVL" ).val(value["BOM_LVL"]);
        		$( "#PART_SN" ).val(value["PART_SN"]);
	   			$( "#BOM_ID" ).val(selBomId);
			    $( "#PARENT_BOM_ID" ).val(parentBomId);
			    $( "#PART_CD" ).val(selPartCd);
	        	fnDataAct("AX");
			})
        }
  });
}

//선택된 부품, BOM, DATA 그리드 ROW 선택 기능
function fnSelectRow(part)
{
	jQuery("#gridBom").jqGrid('resetSelection');
	jQuery("#gridData").jqGrid('resetSelection');

	fnSelectData(part);
	fnGetSelectBom(part);
}

function fnGetSelectBom(part)
{
	var part_pos = part.split('_');
	selPartCd = part_pos[0];
	selGrsCd = part_pos[1];
	var postData =  {TRN_KIND_CD: $( "#TRN_KIND_CD" ).val()
			,CRG_CD: $( "#CRG_CD" ).val()
			,PRG_CD: $( "#PRG_CD" ).val()
			,CRG_TYPE_CD: $( "#CRG_TYPE_CD" ).val()
			,PART_CD: selPartCd
	        ,GRS_CD: selGrsCd
			};
	
	jQuery.ajax({
        type:"POST",
        url:"${pageContext.request.contextPath}/sy/selectBomInfo.do",
        data: postData,
        dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
        success : function(data) {
        	$.each(data, function(key, value) {
	        	fnSelectBom(value["BOM_ID"])
			})
        }
  });
}


//개별 부품 정보 조회 
function fnGetPartInfo(BomId)
{
	var retData = "";
	var postData =  {TRN_KIND_CD: $( "#TRN_KIND_CD" ).val()
			,CRG_CD: $( "#CRG_CD" ).val()
			,PRG_CD: $( "#PRG_CD" ).val()
			,GRS_CD: $( "#GRS_CD" ).val()
			,CRG_TYPE_CD: $( "#CRG_TYPE_CD" ).val()
			,PRG_CD: $( "#PRG_CD" ).val()
			,BOM_ID: BomId};
	
	if((parseInt($("#BOM_LVL").val())) <= 2)
	{
		postData.GRS_CD ="";
	}
	
	jQuery.ajax({
        type:"POST",
        url:"${pageContext.request.contextPath}/sy/selectPartDtlList.do",
        data: postData,
        async: false,
        dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
        success : function(data) {
        	$.each(data, function(key, list) {
	        	$.each(list, function(key, value) {
	        		retData = value;
				});
			});
        }
  	});
	return retData;
}

//개별 BOM 정보 조회 
function fnSelectBom(bom)
{
	fnSetRowEvt(false);
	var ids = jQuery("#gridBom").jqGrid('getDataIDs'); 
    for(var i=0;i < ids.length;i++){ 
		var rowData = $("#gridBom").getRowData(ids[i]);
		if(bom == rowData.BOM_ID)
		{
			jQuery("#gridBom").setSelection (ids[i], true);
			break;
		}
    }
    fnSetRowEvt(true);
}

function fnSelectData(part)
{
	fnSetRowEvt(false);
	var ids = jQuery("#gridData").jqGrid('getDataIDs'); 
    for(var i=0;i < ids.length;i++){ 
		var rowData = $("#gridData").getRowData(ids[i]);
		if(part == rowData.SEL_PART_ID)
		{
			jQuery("#gridData").setSelection (ids[i], true);
			break;
		}
    }
    fnSetRowEvt(true);
}


//형상 정보 초기화 완료 여부 
function fnEndLoadModel(bLoadModel)
{
	bEndLoadModel = bLoadModel;
	$("#RFID_TAG_ID").val("");

}

function fnForward(menu){

	if(menu == 1)
	{
		location.href = "<c:url value="/hs/ispt.do"/>"
	}
	else
	{
		location.href = "<c:url value="/rf/tagStat.do"/>"
	}

}

function fnPopupCenter(url, title, w, h, view) {
	  var left = (screen.width/2)-(w/2);
	  var top = (screen.height/2)-(h/2);
	  
	  
	  if ( view ) {  // 3DVIEWR
		  window.showModalDialog(url, window,  "dialogHeight="+h+"px; dialogWidth="+w+"px; resizable=no; scroll=no; status=no; center=yes" );
	  } else {  // 부품 팝업.
		  return window.open(url, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width='+w+', height='+h+', top='+top+', left='+left);
	  }
	  
}

//부품 검색 화면 
function fnSearchPart()
{
	$("#txtPart").val("");
	var trnType = $("#sltTrnType").val();
	if ($.trim(trnType).length == 0) {
	    alert("차종을 선택해 주세요.");
	    $("#sltTrnType").trigger("focus");
	    return false;
	}
	$("#TRN_KIND_CD").val(trnType);
	
	var prg = $("#sltPrg").val();
	if ($.trim(prg).length == 0) {
	    alert("편성을 선택해 주세요.");
	    $("#sltPrg").trigger("focus");
	    return false;
	}
	$("#PRG_CD").val(prg);
	
	var crg = $("#sltCrg").val();
	if ($.trim(crg).length == 0) {
	    alert("차호을 선택해 주세요.");
	    $("#sltCrg").trigger("focus");
	    return false;
	}
	var ssCrg = crg.split('_');
	$("#CRG_CD").val(ssCrg[0]);

	$("#TRN_KIND_NM").val( $("#sltTrnType option:selected").text());
	$("#CRG_NM").val($("#sltCrg option:selected").text());
	var url = "${pageContext.request.contextPath}/sy/bomPart.do";
	var title = "PART";
	fnPopupCenter('',title,1145,650);  
	pForm.target = title;                    
	pForm.action = url;                   
	pForm.method = "post";
	pForm.submit();     
}

//검색 조건 확인
function fnSearchValidation()
{
	var trnType = $("#sltTrnType").val();
	if ($.trim(trnType).length == 0) {
	    alert("차종을 선택해 주세요.");
	    $("#sltTrnType").trigger("focus");
	    return false;
	}
	$("#TRN_KIND_CD").val(trnType);
	
	var prg = $("#sltPrg").val();
	if ($.trim(prg).length == 0) {
	    alert("편성을 선택해 주세요.");
	    $("#sltPrg").trigger("focus");
	    return false;
	}
	$("#PRG_CD").val(prg);
	
	var crg = $("#sltCrg").val();
	if ($.trim(crg).length == 0) {
	    alert("차호을 선택해 주세요.");
	    $("#sltCrg").trigger("focus");
	    return false;
	}
	
	var ssCrg = crg.split('_');
	$("#CRG_CD").val(ssCrg[0]);
	
	if(menuId == "HO01" || menuId == "HO02" || menuId == "HO03")
	{
		var stDate = $("#stDate").val();
		if ($.trim(stDate).length == 0) {
		    alert("검색 시작일 입력해 주세요.");
		    $("#stDate").trigger("focus");
		    return false;
		}
		$("#ST").val(stDate);
		
		var edDate = $("#edDate").val();
		if ($.trim(edDate).length == 0) {
		    alert("검색 종료일 입력해 주세요.");
		    $("#edDate").trigger("focus");
		    return false;
		}
		$("#ED").val(edDate);
		
		var stDt = new Date($("#stDate").val());
		var edDt = new Date($("#edDate").val());
		if(stDt > edDt)
	    {
			alert("종료일이 잘못 되었습니다.");
			$("#edDate").trigger("focus");
			return false;
	    }
		if(menuId == "HO01")
		{
			$("#ISPT_TYPE_CD").val($("#sltIsptType").val());
		}
	}
	
	return true;
}

//형상정보 표시
function fnViewShow()
{
	var viewerPage = "${pageContext.request.contextPath}/3d/view.jsp";
	viewerPage = viewerPage + "?CRG_TYPE_CD=" + jQuery("#CRG_TYPE_CD").val() + "&TRN_KIND_CD=" + jQuery("#TRN_KIND_CD").val();
	jQuery("#viewer").attr("src", viewerPage)
	jQuery("#viewer").show();
}

//지침서 표시
function fnPlay(file)
{
	var viewerPage = "${pageContext.request.contextPath}/3d/view.jsp";
	viewerPage = viewerPage + "?AUTO=1&CRG_TYPE_CD=P&TRN_KIND_CD=PART&PART_CD=" + file;
	var title = "ANIPART";
	fnPopupCenter(viewerPage,title,1145,650,true);  
}


//상위BOM 명령어
function fnBomUp()
{
	$("#BOM_LVL").val(parseInt($("#BOM_LVL").val())-1);
	if((parseInt($("#BOM_LVL").val())) <= 2)
	{
		$("#BOM_LVL").val("2");
		$("#BOM_ID").val(""); 
		$("#PARENT_BOM_ID").val("");
		$("#GRS_CD").val("");
		$("#PART_CD").val("");
		$("#PART_SN").val("");
		var postData = fnGetDataPostData();
		fnGetTagValue(postData);
		fnDataAct("LUX");
		$('#viewer')[0].contentWindow.fnShowAll();
	}
	else
	{
		var postData = fnGetDataPostData();
		var partGrs = partPath[parseInt($("#BOM_LVL").val())-1];
		var partPos = partGrs.split('_');
		fnDivAniShow(partPos[0]);
		postData.PART_CD = partPos[0];
		fnGetTagValue(postData);
		$('#viewer')[0].contentWindow.fnPartOnly(partPath[parseInt($("#BOM_LVL").val())-1]);
		fnDataAct("LUX");
	}


}

//하위 BOM 명령어 처리 
function fnBomDown()
{
	if(bSelectRow == false)
	{
		alert("선택된 부품이 없습니다.");
		return;
	}
	$('#viewer')[0].contentWindow.fnSelectPart("");
	bSelectRow = false;
	partPath[$("#BOM_LVL").val()] = selPartCd + "_" + selGrsCd;
	$("#BOM_LVL").val(parseInt($("#BOM_LVL").val())+1);

    fnDataAct("LDX");
	$('#viewer')[0].contentWindow.fnPartOnly(selPartCd + "_" + selGrsCd);

}

//초기화 명령어 
function fnDateInit()
{
	if( !$('#viewer').is(':visible') ) {
	   return;
	}
	bomPath = [];
	partPath = [];
	selBomId = "";
	selPartCd ="";
	selGrsCd = "";
	$("#BOM_LVL").val("2");
	$("#BOM_ID").val("");
	$("#PARENT_BOM_ID").val("");
	$("#UP_PARENT_BOM_ID").val("");
	$("#PART_CD").val("");
	$("#PART_SN").val("");
	$("#GRS_CD").val("");
	
	fnDivAniShow("");
	fnDataAct("IX");
	$('#viewer')[0].contentWindow.fnShowAll();
}

function fnSetRowEvt(val)
{
	rowEvt = val;
}

function fnGetRowEvt()
{
	return rowEvt;
}

function fnSetAct(val)
{
	$('#ACT').val(val);
}

function fnGetAct()
{
	return $('#ACT').val();
}

function fnGetBomPostData() {
	  var ary = $( "#pForm" ).serializeArray();
	  var obj = {};
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


//각각의 명령어 실행 부분
function fnDataAct(val)
{
	$('#ACT').val(val);
	
	var postBom = fnGetBomPostData();
	var postData = fnGetDataPostData();
	postData.page= "1";
	
	if(val =="BX")
	{	
		if(parseInt(postBom.BOM_LVL) <= 2)
		{
			postData.GRS_CD= "";
		}

		postData.PART_CD = "";
		postData.PART_SN= "";

		selPartCd ="";
	    selGrsCd = "";
	    selBomId = "";
		fnSearchDataList(postData);	    

	}
	else if(val =="DX")
	{

	}
	else if(val =="AX")
	{
		postBom.PART_CD= "";
		postBom.PART_SN= "";
		fnSearchBomList(postBom);
		
		postData.PART_CD= $( "#PART_CD" ).val();
		postData.PART_SN= $( "#PART_SN" ).val();
		fnSearchDataList(postData);
		
		var timer = setInterval(function () {       
	  		if(isBomComplete && isDataComplete)  
	   		{
	  			clearInterval(timer); 
		    	fnGetRowEvt(true);
	  			isBomComplete = false;
	  			isDataComplete = false;
	   		}       
	  	}, 5000)
	}
	else if(val =="TX")
	{
		selBomId = postBom.BOM_ID;

		if(postBom.PART_CD != "" && parseInt(postBom.BOM_LVL) == 2)
		{
			postBom.BOM_LVL = parseInt(postBom.BOM_LVL) + 1;	
			postBom.UP_PARENT_BOM_ID = "";
		}
		else if(postBom.PART_CD != "" && parseInt(postBom.BOM_LVL) == 3)
		{
			postBom.PARENT_BOM_ID = "";
			postBom.UP_PARENT_BOM_ID = postBom.BOM_ID;
		}
		else
		{
			 postBom.UP_PARENT_BOM_ID = "";
			 postBom.BOM_LVL = "2";
			 postBom.PARENT_BOM_ID ="";
			 postBom.BOM_ID ="";
		}
		postBom.BOM_ID = "";
		postBom.PART_CD= "";
		postBom.PART_SN= "";
		fnSearchBomList(postBom);
		
		selPartCd = $( "#PART_CD" ).val();
		selGrsCd = $( "#GRS_CD" ).val();
		
		postData.PART_CD= $( "#PART_CD" ).val();
		postData.PART_SN= $( "#PART_SN" ).val();
		fnSearchDataList(postData);
		
		var timer = setInterval(function () {       
	  		if(isBomComplete && isDataComplete && isDataEvtComplete)  
	   		{
	  			clearInterval(timer); 
	  			fnSetAct("");
	  			isBomComplete = false;
	  			isDataComplete = false;
	  			isDataEvtComplete = false;
	   		}       
	  	}, 5000)
		
	}
	else if(val =="IX")
	{
		bomPath[1] = "";
		
	    if(parseInt($("#BOM_LVL").val()) == 2)
		    $("#ACT").val("");
		
		postData.PART_CD = "";
		postData.PART_SN= "";
		postData.GRS_CD= "";
		selPartCd ="";
	    selGrsCd = "";
	    selBomId = "";
	    fnGetTagValue(postData);
	    fnBomInfo(false)
		fnSearchBomList(postBom);
		fnSearchDataList(postData);
	}
	else if(val =="LUX")
	{
		
		if(parseInt(postBom.BOM_LVL) <= 2)
		{
			bomPath[1] = "";
			
			selPartCd ="";
		    selGrsCd = "";
		    selBomId = "";
		    
			postBom.BOM_LVL = "2";
			postData.GRS_CD= "";
			postData.BOM_ID= "";
			fnBomInfo(false)
		}
		else
		{
			postBom.PARENT_BOM_ID = postBom.UP_PARENT_BOM_ID;
			postData.BOM_ID = postData.UP_PARENT_BOM_ID;
			
			bomPath[parseInt(postBom.BOM_LVL)-2] = "";
			postBom.BOM_ID ="";
			postBom.UP_PARENT_BOM_ID ="";
			postData.UP_PARENT_BOM_ID ="";

			fnBomInfo(true);
		}
		
		fnSearchBomList(postBom);
		
		postData.PART_CD = "";
		postData.PART_SN= "";
		fnSearchDataList(postData);

	}
	else if(val =="LDX")
	{	
		fnDivAniShow(postData.PART_CD);
		postBom.PARENT_BOM_ID = postBom.BOM_ID;
        postBom.BOM_ID= "";
        
		fnGetTagValue(postData);
		fnBomInfo(true);
		
		fnSearchBomList(postBom);
		postData.PART_CD = "";
		postData.PART_SN= "";
		fnSearchDataList(postData);
	}
}

//지침서 표시여부 
function fnDivAniShow(partCd)
{
	if(menuId == "HO01" || menuId == "HO02" || menuId == "HO03")
	{
		if(partCd == "10008183")
		{
			$('#divAniParts').show();
		}
		else
		{
			$('#divAniParts').hide();
		}
	}
}

//BOM 정보 조회 
function fnSearchBomList(postData)
{
	jQuery.ajax({
        type:"POST",
        url:"${pageContext.request.contextPath}/sy/selectBomList.do",
        data: postData,
        dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
        success : function(data) {
        	$.each(data, function(key, list) {
       			var i = 0;
       			$("#gridBom").jqGrid("clearGridData");
       			$("#UP_PARENT_BOM_ID").val($("#PARENT_BOM_ID").val());
        		if(list.length > 0)
        		{
            		$.each(data, function(key, value) {
            			jQuery("#gridBom").jqGrid('addRowData',i++,value);
        			});
        		}
        		else
        		{
        			if(fnGetAct() =="TX")
			   		{
			   			var postBom = fnGetBomPostData();
			   			selBomId = postBom.BOM_ID;
			   			
			   			if(parseInt(postBom.BOM_LVL) == 2)
			   			{
			   				 postBom.UP_PARENT_BOM_ID = "";
			   				 postBom.BOM_LVL = "2";
			   				 postBom.PARENT_BOM_ID ="";
			   				 postBom.BOM_ID ="";
			   			}
			   			
			   			postBom.BOM_ID = "";
			   			postBom.PART_CD= "";
			   			postBom.PART_SN= "";
			   			fnSearchBomList(postBom);
			   		}
        		}

			})
        },
        complete : function(data) {

        }
  });
}

//검색 관련 정보 표시 
function fnSearchInfo(snsDt, tagId)
{
	$("#SNS_DT").val(snsDt);
	$("#RFID_TAG_ID").val(tagId);
	
	$("#spanPrgNm").text($("#sltPrg option:selected").text());
	$("#spanCrgTypeNm").text($("#CRG_TYPE_NM").val());
	$("#spanCrgNm").text($("#sltCrg option:selected").text());
	if(snsDt =="")
	{
		$("#spanRfidTagId").text(tagId).css("color", "blue");
		$("#spanRfidTagId").attr('onclick','').unbind('click'); 
	}
	else
	{
		$("#spanRfidTagId").text(tagId).css("color", "red");
		$("#spanRfidTagId").bind("click", function() {    
  
		});
	}
}

//TAG 센시 정보 표시 
function fnSetTagId(snsDt, tagId)
{
	if((typeof snsDt) == "undefined")
	{
		return;
	}
	
	if(snsDt =="")
		$("#spanRfidTagId").text(tagId).css("color", "blue");
	else
		$("#spanRfidTagId").text(tagId).css("color", "red");
	
}


//BOM 정보 표시 여부 
function fnBomInfo(visible)
{
  if(visible)
  {
	var postData = fnGetDataPostData();
	if(postData.ACT =="LUX")
		postData.BOM_ID = postData.UP_PARENT_BOM_ID;
	
	jQuery.ajax({
        type:"POST",
        url:"${pageContext.request.contextPath}/sy/selectBomPathInfo.do",
        data: postData,
        dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
        success : function(data) {
        	var path = "";
        	$.each(data, function(key, value) {
        		path = value["BOM_PATH"];
			});
        	var paths = path.split("|");
        	for(i = 0; i < paths.length; i++)
        	{
        		if(i == 0)
    		  		$("#spanBomPath").text( paths[i]);
        		else
        			$("#spanBomPath").text( $("#spanBomPath").text() +" > "+ paths[i]);
        	}
      	  
		  $("#BOM_PATH").val($("#spanBomPath").text());
		  $("#divBomPath").show();
        }
  	});
  }
  else
  {
	  $("#divBomPath").hide();
  }
}


//TAG 정보 조회 
function fnGetTagValue(postData)
{
	postData.sidx ='';
	postData.BOM_ID ='';
	jQuery.ajax({
        type:"POST",
        url:"${pageContext.request.contextPath}/sy/selectTagInfo.do",
        data: postData,
        dataType:"JSON", 
        success : function(data) {
        	$.each(data, function(key, value) {
        		fnSetTagId(value["SNS_DT"], value["RFID_TAG_ID"]);
			})
        }
  });
}

/* 
 * 이벤트 영역 
 * 페이지 초기화 이벤트
 * 개별 컨트롤 이벤트
 */
var bomWapperWdith = 0;
var dataWapperWdith = 0;
$(function() {    
	bomWapperWdith = $("#gridBomWapper").width();
	dataWapperWdith = $("#gridDataWapper").width()
	fnViewShow();
	fnGridResize();
	$.datepicker.regional['ko'] = {  closeText: '닫기',  prevText: '이전달',  nextText: '다음달',  currentText: '오늘',  monthNames: ['1월','2월','3월','4월','5월','6월',  '7월','8월','9월','10월','11월','12월'],  monthNamesShort: ['1월','2월','3월','4월','5월','6월',  '7월','8월','9월','10월','11월','12월'],  dayNames: ['일','월','화','수','목','금','토'],  dayNamesShort: ['일','월','화','수','목','금','토'],  dayNamesMin: ['일','월','화','수','목','금','토'],  weekHeader: 'Wk',  dateFormat: 'yy-mm-dd',  firstDay: 0,  isRTL: false,  duration:200,  showAnim:'',  showMonthAfterYear: true,  yearSuffix: '년'};      
	$.datepicker.setDefaults($.datepicker.regional['ko']);

$('#stDate').datepicker({
	format:'Y/m/d',
	defaultDate: 'm',
	date: $('#stDate').val(),
	current: $('#stDate').val(),
	position: 'r',
	beforeShow: function (textbox, instance) {
           instance.dpDiv.css({
                  marginTop: '-170px'
          });
       },
	onSelect: function(formated, dates){
	    $('#stDate').val(formated);
	},
	onClose: function(formated, dates){
	    $('#dSt').hide();
	}
});
 
 $('#edDate').datepicker({
		format:'Y/m/d',
		defaultDate: 'm',
		date: $('#edDate').val(),
		current: $('#edDate').val(),
		position: 'r',
		beforeShow: function (textbox, instance) {
            instance.dpDiv.css({
                    marginTop: '-170px'
            });
        },
		onSelect: function(formated, dates){
		    $('#edDate').val(formated);
		},
		onClose: function(formated, dates){
		    $('#dEd').hide();
		}
});

 jQuery("#gridBom").jqGrid({
		datatype: "local",
		height:'340',
		width :bomWapperWdith,
	    viewrecords: true,
	    forceFit:true,
	   	colNames:['NO','ITEM', '수량','','', '',''],
	   	colModel:[
	   		{name:'ROWNUM',index:'ROWNUM', width:30, sorttype:"int", align:"center",sortable:false},
	   		{name:'BOM_NM',index:'BOM_NM', width:100,align:"center", sortable:false},
	   		{name:'PART_AMT',index:'PART_AMT', width:40, align:"center",sorttype:"int", sortable:false},
	   		{name:'BOM_ID',index:'BOM_ID', width:100, hidden:true},
	   		{name:'BOM_LVL',index:'BOM_LVL', width:100, hidden:true},
	   		{name:'PART_CD',index:'PART_CD', width:100, hidden:true},
	   		{name:'PARENT_BOM_ID',index:'PARENT_BOM_ID', width:100, hidden:true}
	   	],
	   	multiselect: false,
	   	subGrid: true,
		gridComplete: function () {
			var ids = jQuery("#gridBom").jqGrid('getDataIDs'); 
		    for(var i=0;i < ids.length;i++){ 
		    	 jQuery("#gridBom").jqGrid("setCell", ids[i], "ROWNUM", i+1, "");
		    }
        	fnSelectBom(selBomId);  
        	fnGridResize();
		},
	   	loadComplete: function(data) {
	   		selBomId = "";
	   		isBomComplete = true;	
	   		fnGridResize();
	    },
	    beforeSelectRow : function (rowId) {
			var rowIds = $("#gridBom").getDataIDs();
			$.each(rowIds, function (index, value) {
				if(rowId != value)
			    	$("#gridBom").collapseSubGridRow(value); 
			});
			return true;
		},
	    onSelectRow : function (rowId) {
	    	if(fnGetRowEvt() == false)
	    		return;
	    	
			var rowData = $("#gridBom").getRowData(rowId);
			$("#BOM_ID").val(rowData.BOM_ID);
			$("#BOM_LVL").val(parseInt(rowData.BOM_LVL));
			$("#PARENT_BOM_ID").val(rowData.PARENT_BOM_ID);
			$("#UP_PARENT_BOM_ID").val("");
			fnDataAct("BX");
		
			if(rowData.BOM_LVL == 2)
			{
				bomPath[0] = rowData.BOM_NM;
			}
			else
			{
				bomPath[1] = rowData.BOM_NM;
			}
			
			if(rowData.PART_AMT > 1)
			{
				$("#gridBom").expandSubGridRow(rowId); 
			}
			else if(rowData.PART_AMT == 0)
			{

			}
			else
			{
				var partDtlData = fnGetPartInfo(rowData.BOM_ID);

	        	selPartCd = partDtlData["PART_CD"];
				selGrsCd = partDtlData["GRS_CD"];

				$("#PART_CD").val(selPartCd);
				$("#GRS_CD").val(selGrsCd);
				$("#PART_SN").val(partDtlData["PART_SN"]);
				fnSearchInfo(partDtlData["SNS_DT"], partDtlData["RFID_TAG_ID"]);
				$('#viewer')[0].contentWindow.fnFocusPart(selPartCd +"_"+ selGrsCd);
				
				bSelectRow = true;
			}
			 
		},
	  	subGridOptions: {
    		"plusicon"  : "ui-icon-triangle-1-e",
    		"minusicon" : "ui-icon-triangle-1-s",
    		"openicon"  : "ui-icon-arrowreturn-1-e",
    		"reloadOnExpand": true,
            "selectOnExpand": true,
            "expandOnLoad":false, 
    	},
    	subGridBeforeExpand: function(subgrid_id, rowId) {
			var rowIds = $("#gridBom").getDataIDs();
			$.each(rowIds, function (index, value) {
				if(rowId != value)
			    	$("#gridBom").collapseSubGridRow(value); 
			});

			var rowData = $("#gridBom").getRowData(rowId);
			if(rowData.PART_AMT <= 1)
			{
				jQuery("#gridBom").setSelection (rowId, true);
				return false;
    		}

    	},
    	subGridRowExpanded: function(subgrid_id, rowId) {
			var rowData = $("#gridBom").getRowData(rowId);
			$("#BOM_ID").val(rowData.BOM_ID);
			$("#BOM_LVL").val(parseInt(rowData.BOM_LVL));
			$("#PARENT_BOM_ID").val(rowData.PARENT_BOM_ID);
			$("#UP_PARENT_BOM_ID").val("");
			
			$("#PART_CD").val("");
			$("#GRS_CD").val("");
			$("#PART_SN").val("");
			
			var postData = fnGetDataPostData();
			postData.PART_CD = "";
			postData.PART_SN= "";
			postData.GRS_CD= "";
			
    		var subgrid_table_id
    		subgrid_table_id = subgrid_id+"_t";
    		$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table>");
    		jQuery("#"+subgrid_table_id).jqGrid({
    	        type:"POST",
                <c:choose>
                <c:when test="${MENU == 'HO01'}">
                url:"${pageContext.request.contextPath}/sy/selectPartDtlList.do",
     			 </c:when>
               <c:when test="${MENU == 'HO02'}">
                url:"${pageContext.request.contextPath}/sy/selectPartDtlList.do",
     			 </c:when>
                <c:when test="${MENU == 'HO03'}">
                url:"${pageContext.request.contextPath}/sy/selectPartDtlList.do",
     			 </c:when>
                <c:when test="${MENU == 'RF01'}">
                url:"${pageContext.request.contextPath}/sy/selectPartDtlList.do",
     			 </c:when>
   				</c:choose>
    	        postData: postData,
    	        datatype:"JSON", 
    			colNames: ['부품코드','위수','','','','',''],
    			colModel: [
					{name:"PART_CD",index:"PART_CD",width:110, sortable:false, align:"center"},
    				{name:"GRS_CD",index:"GRS_CD",width:40, sortable:false, align:"center"},
    				{name:"SEL_PART_ID",index:"SEL_PART_ID",width:0, hidden:true},
    				{name:"PART_SN",index:"PART_SN",width:0, hidden:true},
    				{name:"RFID_TAG_ID",index:"RFID_TAG_ID",width:0, hidden:true},
    				{name:"SNS_DT",index:"SNS_DT",width:0, hidden:true},
    				{name:'RNUM',index:'RNUM', width:30, hidden:true}
    			],
    		   	sortname: 'GRS_CD',
    		    sortorder: "asc",
			    viewrecords: true,
			    forceFit:true,
    		    height: '100%',
    		    width: '330',
			    onSelectRow : function (subRowId) {
					var subRowData = $("#"+subgrid_table_id).getRowData(subRowId);
					var partPos = subRowData.SEL_PART_ID.split('_');
					selPartCd = partPos[0];
					selGrsCd = partPos[1];
					$("#PART_CD").val(partPos[0]);
					$("#GRS_CD").val(partPos[1]);
					$("#PART_SN").val(subRowData.PART_SN);
					$('#viewer')[0].contentWindow.fnFocusPart(subRowData.SEL_PART_ID);
					
					fnGetTagValue(fnGetDataPostData());
					bSelectRow = true;
					
					// ##################################################
					// ## 서브 그리드 선택시 하단 조회데이터 검색시 부품코드는 제거 
					// ##################################################
					var gridData = fnGetDataPostData();
					gridData.PART_CD = "";
					// ##################################################
					fnSearchDataList(gridData);
				},			   	
				gridComplete: function(data) {
					 $('.jqgrow').mouseover(function(e) {
						if((typeof e.result) != "undefined")
						{
							return;
						}
						e.result = true;
						var subRowId = $(this).attr('id');
						var subRowData =  $("#"+subgrid_table_id).getRowData(subRowId);
						$('#viewer')[0].contentWindow.fnSelectPart(subRowData.SEL_PART_ID);
					  });
					var postData = fnGetDataPostData();
					postData.PART_SN = "";
					fnGetTagValue(postData);
					$("#"+subgrid_table_id).jqGrid('resetSelection');
			    }
    		});
    	}	
	})		

});

//사업소 변경 이벤트
function fnChangeRsmo(obj)
{
	location.href = "${origRequestURL}?RSMO=" + obj.value;
}

//차종 변경 이벤트
function fnChangeSltTrnType(obj)
{
	jQuery.ajax({
        type:"POST",
        url:"${pageContext.request.contextPath}/sy/selectPrgInfoList.do",
        data: "TRN_KIND_CD=" + obj.value,
        dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
        success : function(data) {
          // 통신이 성공적으로 이루어졌을 때 이 함수를 타게 된다.
         	$('#sltPrg option').remove();
         	$('#sltCrg option').remove();
         	$("#sltCrg").append("<option value=''> :: 차호 :: </option>");
			$("#txtCrgTypeNm").val("");
    		$("#CRG_TYPE_CD").val("");
    		$("#CRG_TYPE_NM").val("");
        	$.each(data, function(key, list) {
        		$("#sltPrg").append("<option value=''> :: 편성 :: </option>");
        		$.each(list, function(key, value) {
        			$("#sltPrg").append("<option value='"+value['PRG_CD']+"'>"+value['PRG_NM']+"</option>");
    			})
			})
			if(fnGetAct() =="TX")
		 	{
				$("#sltPrg").val("${PARAM['PRG_CD']}").change();
		 	}
        }
  });
	
	initForm();  // 데이터 초기화
}

//편성 변경 이벤트
function fnChangeSltPrg(obj)
{
	jQuery.ajax({
        type:"POST",
        url:"${pageContext.request.contextPath}/sy/selectCrgInfoList.do",
        data: "PRG_CD=" + obj.value,
        dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
        success : function(data) {
          // 통신이 성공적으로 이루어졌을 때 이 함수를 타게 된다.
         	$('#sltCrg option').remove();
			$("#txtCrgTypeNm").val("");
    		$("#CRG_TYPE_CD").val("");
    		$("#CRG_TYPE_NM").val("");
        	$.each(data, function(key, list) {
        		$("#sltCrg").append("<option value=''> :: 차호 :: </option>");
        		$.each(list, function(key, value) {
        			$("#sltCrg").append("<option value='"+value['CRG_CD']+"_"+value['CRG_TYPE_CD']+"_"+value['RFID_TAG_ID']+"_"+value['SNS_DT']+"'>"+value['CRG_NM']+"</option>");
    			})
			})

			if(fnGetAct() =="TX")
		 	{
		    	$("#sltCrg").val("${PARAM['CRG_SLT']}").change();
				fnSearchInfo("${PARAM['SNS_DT']}", "${PARAM['RFID_TAG_ID']}");
				bomPath[0] = "${PARAM['BOM_LVL_2']}";
				bomPath[1] = "${PARAM['BOM_LVL_3']}";
				fnBomInfo(true);
		 	}
			else
			{
				$("#txtCrgTypeNm").val("");
        		$("#CRG_TYPE_CD").val("");
        		$("#CRG_TYPE_NM").val("");
        	}
        	
        }
  });
	
	initForm();  // 데이터 초기화
}

//차호 변경 이벤트
function fnChangeSltCrg(obj)
{
	if(obj.value.length == 0)
	{
		$("#txtCrgTypeNm").val("");
		return;
	}
	$("#CRG_SLT").val(obj.value);
	var ssCrg = obj.value.split('_');
	jQuery.ajax({
        type:"POST",
        url:"${pageContext.request.contextPath}/sy/selectCrgTypeInfo.do",
        data: "CRG_TYPE_CD=" +ssCrg[1],
        dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
        success : function(data) {
          // 통신이 성공적으로 이루어졌을 때 이 함수를 타게 된다.
        	$.each(data, function(key, value) {
        		$("#txtCrgTypeNm").val(value["COMM_DTL_NM"]);
        		$("#CRG_TYPE_CD").val(value["COMM_DTL_ID"]);
        		$("#CRG_TYPE_NM").val(value["COMM_DTL_NM"]);
			})
        }
  });
	
	initForm();  // 데이터 초기화
}

//그리드 사이즈 변경 
function fnGridResize()       
{         
	$('#gridBom').fluidGrid({base:'#gridBomWapper', offset:0});
	$('#gridData').fluidGrid({base:'#gridDataWapper', offset:0});
	$('#viewer').width($('#viewerWapper').width());
	fnResizeDiv();
}      

//그리드 조회 결과 표시 여부 
function fnShowDiv(rows)
{
	if(bPageInit)
	{
		bPageInit = false;
		return;
	}
	
	var objNoData = $("#noDataDiv");
	if(rows == 0)
	{
		 objNoData.show();
	     $("input.ui-pg-input").val('0'); 
	}
	else
	{
		 objNoData.hide();
	}
}

//그리드 사이즈 변경 
function fnResizeDiv()
{
	var objNoData = $("#noDataDiv");
	var objbdiv = $("#gridDataWapper");
	if( !objbdiv.is(':visible') ) {
		   return;
	 }
	 objNoData.css("left", objbdiv.offset().left + (dataWapperWdith/2)-150);
	 objNoData.css("top", objbdiv.offset().top  + 43)
}
$(window).resize(fnGridResize); 
$(document).ajaxError(function(event, jqXHR, ajaxSettings, thrownError) {
	if(jqXHR.status == 404)
	{
		alert("비즈니스 처리 중 Error 발생 하였습니다.");
		return;
	}
	else
	{
		alert("로그인 세션이 종료 되었습니다.");
	    location.href = '${pageContext.request.contextPath}/ma/logoutAction.do';
	}
});


// 검색 조건 변경시 데이터 초기화
function initForm() {
	// bom목록 초기화
	$("#gridBom").jqGrid("clearGridData");
	// 조회목록 초기화
	$("#gridData").jqGrid("clearGridData");
	
	// 3D viewer 초기화
	$("#CRG_TYPE_CD").val("");
	$("#TRN_KIND_CD").val("");
	fnViewShow();
	
	// 검색 부품 초기화
	$("#txtPart").val("");
	
	// 검색 관련 정보 표시 초기화
	// 편성
	$("#spanPrgNm").text("");
	// 차호
	$("#spanCrgNm").text("");
	// 객차유형
	$("#spanCrgTypeNm").text("");
	// RFID 태그ID
	$("#spanRfidTagId").text("");
	// BOM
	$("#spanBomPath").text("");
	
	// "조회된 데이터가 없습니다."라는 문구 안보이게 처리. 
	$("#noDataDiv").hide();
}
</script>
</head>
<body>
  <form id="pForm">
   <input type="hidden" id="ACT" name="ACT"></input>
   <input type="hidden" id="BOM_ID" name="BOM_ID" value="${PARAM.BOM_ID}"></input>
   <input type="hidden" id="TRN_KIND_CD" name="TRN_KIND_CD" value="${PARAM.TRN_KIND_CD}"></input>
   <input type="hidden" id="TRN_KIND_NM" name="TRN_KIND_NM" value="${PARAM.TRN_KIND_NM}"></input>
   <input type="hidden" id="BOM_LVL" name="BOM_LVL" value="${PARAM.BOM_LVL}"></input>
   <input type="hidden" id="PARENT_BOM_ID" name="PARENT_BOM_ID" value="${PARAM.PARENT_BOM_ID}"></input>
   <input type="hidden" id="OR_PARENT_BOM_ID" name="OR_PARENT_BOM_ID" value="${PARAM.OR_PARENT_BOM_ID}"></input>
   <input type="hidden" id="UP_PARENT_BOM_ID" name="UP_PARENT_BOM_ID" value="${PARAM.UP_PARENT_BOM_ID}"></input>
   <input type="hidden" id="CRG_TYPE_CD" name="CRG_TYPE_CD" value="${PARAM.CRG_TYPE_CD}"></input>
   <input type="hidden" id="CRG_TYPE_NM" name="CRG_TYPE_NM" value="${PARAM.CRG_TYPE_NM}"></input>
   <input type="hidden" id="CRG_CD" name="CRG_CD" value="${PARAM.CRG_CD}"></input>
   <input type="hidden" id="CRG_NM" name="CRG_NM" value="${PARAM.CRG_NM}"></input>
   <input type="hidden" id="CRG_SLT" name="CRG_SLT" value="${PARAM.CRG_SLT}"></input>
   <input type="hidden" id="PRG_CD" name="PRG_CD" value="${PARAM.PRG_CD}"></input>
   <input type="hidden" id="PART_CD" name="PART_CD" value="${PARAM.PART_CD}"></input>
   <input type="hidden" id="PART_SN" name="PART_SN" value="${PARAM.PART_SN}"></input>
   <input type="hidden" id="GRS_CD" name="GRS_CD" value="${PARAM.GRS_CD}"></input>
   <input type="hidden" id="ST" name="ST" value="${PARAM.ST}"></input>
   <input type="hidden" id="ED" name="ED" value="${PARAM.ED}"></input>
   <input type="hidden" id="ISPT_TYPE_CD" name="ISPT_TYPE_CD" value="${PARAM.ISPT_TYPE_CD}"></input>
   <input type="hidden" id="RFID_TAG_ID" name="RFID_TAG_ID" value="${PARAM.RFID_TAG_ID}"></input>
   <input type="hidden" id="SNS_DT" name="SNS_DT" value="${PARAM.SNS_DT}"></input>
   <input type="hidden" id="BOM_PATH" name="BOM_PATH" value="${PARAM.BOM_PATH}"></input>
   <input type="hidden" id="MENU" name="MENU" value="${MENU}"></input>
   <input type="hidden" id="BOM_PARENT_ID" name="BOM_PARENT_ID" value="${BOM_PARENT_ID}"></input>
  </form>
<div id="honey1" style="width:100%; height:100%">
  <div class="clear_both header00">
    <div class="float_left header01">
      <h1 class="float_left">
        <p class="font01"> 3D PLM 기반<br />
          <strong class="font02"><b>스마트 철도차량 유지보수 지원 시스템</b></strong></p>
      </h1>
    </div>
    <div class="float_right header02">
      <ul>
        <li class="float_left header05 ">
            <select style="height:25px" id="sltRsmo" onChange="fnChangeRsmo(this)" <c:if test="${USER_INFO['USER_TYPE'] eq 'U'}"> disabled</c:if>>
            <c:forEach var="RSMO_ITEM" items="${RSMO_DATA}" varStatus="status">
             <option value="${RSMO_ITEM['RSMO_CD']}" <c:if test="${RSMO_ITEM['RSMO_CD'] eq RSMO_CD}">selected</c:if>>${RSMO_ITEM['RSMO_NM']}</option>
           </c:forEach>
            </select>
        </li>
        <li class="float_left header03 header04">
          <p> ${USER_INFO['USER_NM']}님</p>
        </li>
        <!-- 로그아웃버튼 --> 
        <a href="${pageContext.request.contextPath}/ma/logoutAction.do">
        <li class="float_left header03 btn01">LOGOUT</li>
        </a> 
        <!-- 로그인버튼
         <a href="#">
        <li class="float_left header03 btn01_on">LOGIN</li>
        </a> -->
      </ul>
    </div>
  </div>
  <div class="clear_both gnb"> 
               <c:if test="${fn:startsWith(MENU, 'HO')}">
    <!-- 상단탭 -->
    <ul class="float_left">
      <li><a href="javascript:fnForward(1)"><img src="${pageContext.request.contextPath}/images/btn02_on.png" /></a></li>
    </ul>
    <ul class="float_left">
      <li><a href="javascript:fnForward(2)"><img src="${pageContext.request.contextPath}/images/btn02_off.png"/></a></li>
    </ul>
             </c:if>
                <c:if test="${fn:startsWith(MENU, 'RF')}">
    <!-- 상단탭 -->
    <ul class="float_left">
      <li><a href="javascript:fnForward(1)"><img src="${pageContext.request.contextPath}/images/btn01_off.png" /></a></li>
    </ul>
    <ul class="float_left">
      <li><a href="javascript:fnForward(2)"><img src="${pageContext.request.contextPath}/images/btn01_on.png"/></a></li>
    </ul>
		     </c:if>

  </div>
  <div id="container" class="clear_both"> 
    <!-- 네비게이션 -->

    <div class="lnb float_left">
             <c:if test="${fn:startsWith(MENU, 'HO')}">
      <ul class="clear_both lnb01">
        <li class="float_left"><img src="${pageContext.request.contextPath}/images/lnb_icon01.png" /></li>
        <li class="float_left font03 lnb03_01">전동차 이력조회</li>
      </ul>
             </c:if>
                <c:if test="${fn:startsWith(MENU, 'RF')}">
      <ul class="clear_both lnb01">
        <li class="float_left"><img src="${pageContext.request.contextPath}/images/lnb_icon01.png" /></li>
        <li class="float_left font03 lnb03_01">RFID인프라원격관제</li>
      </ul>
		     </c:if>
 <c:choose>
	  <c:when test="${MENU == 'HO01'}">
 <!-- 버튼 -->
      <ul class="clear_both lnb02">
        <a href="${pageContext.request.contextPath}/hs/ispt.do">
        <li class="float_left"><img src="${pageContext.request.contextPath}/images/lnb_icon05.png" /></li>
        <li class="float_left font05 lnb03_01">검사 이력조회</li>
        </a>
      </ul>
      <!-- 버튼 -->
      <ul class="clear_both lnb02">
        <a href="${pageContext.request.contextPath}/hs/chag.do"">
        <li class="float_left"><img src="${pageContext.request.contextPath}/images/lnb_icon02.png" /></li>
        <li class="float_left font04 lnb03_01">부품교환 이력조회</li>
        </a>
      </ul>
      <!-- 버튼 -->
      <ul class="clear_both lnb02">
        <a href="${pageContext.request.contextPath}/hs/trbl.do">
        <li class="float_left"><img src="${pageContext.request.contextPath}/images/lnb_icon02.png" /></li>
        <li class="float_left font04 lnb03_01">고장 이력조회</li>
        </a>
      </ul>
     <div class="clear_both"> <span class="lnb07"><img src="${pageContext.request.contextPath}/images/navi_bg.png"/></span> </div>
    
	 </c:when>
	 <c:when test="${MENU == 'HO02'}">
 <!-- 버튼 -->
      <ul class="clear_both lnb02">
        <a href="${pageContext.request.contextPath}/hs/ispt.do">
        <li class="float_left"><img src="${pageContext.request.contextPath}/images/lnb_icon02.png" /></li>
        <li class="float_left font04 lnb03_01">검사 이력조회</li>
        </a>
      </ul>
      <!-- 버튼 -->
      <ul class="clear_both lnb02">
        <a href="${pageContext.request.contextPath}/hs/chag.do">
        <li class="float_left"><img src="${pageContext.request.contextPath}/images/lnb_icon05.png" /></li>
        <li class="float_left font05 lnb03_01">부품교환 이력조회</li>
        </a>
      </ul>
      <!-- 버튼 -->
      <ul class="clear_both lnb02">
        <a href="${pageContext.request.contextPath}/hs/trbl.do">
        <li class="float_left"><img src="${pageContext.request.contextPath}/images/lnb_icon02.png" /></li>
        <li class="float_left font04 lnb03_01">고장 이력조회</li>
        </a>
      </ul>
      <div class="clear_both"> <span class="lnb04"><img src="${pageContext.request.contextPath}/images/navi_bg.png"/></span> </div>
	 </c:when>
	 <c:when test="${MENU == 'HO03'}">
    <!-- 버튼 -->
      <ul class="clear_both lnb02">
        <a href="${pageContext.request.contextPath}/hs/ispt.do">
        <li class="float_left"><img src="${pageContext.request.contextPath}/images/lnb_icon02.png" /></li>
        <li class="float_left font04 lnb03_01">검사 이력조회</li>
        </a>
      </ul>
      <!-- 버튼 -->
      <ul class="clear_both lnb02">
        <a href="${pageContext.request.contextPath}/hs/chag.do">
        <li class="float_left"><img src="${pageContext.request.contextPath}/images/lnb_icon02.png" /></li>
        <li class="float_left font04 lnb03_01">부품교환 이력조회</li>
        </a>
      </ul>
      <!-- 버튼 -->
      <ul class="clear_both lnb02">
        <a href="${pageContext.request.contextPath}/hs/trbl.do">
        <li class="float_left"><img src="${pageContext.request.contextPath}/images/lnb_icon05.png" /></li>
        <li class="float_left font05 lnb03_01">고장 이력조회</li>
        </a>
      </ul>
      <div class="clear_both"> <span class="lnb09"><img src="${pageContext.request.contextPath}/images/navi_bg.png"/></span> </div>
	 </c:when>
	 <c:when test="${MENU == 'RF01'}">
      <ul class="clear_both lnb02">
        <a href="${pageContext.request.contextPath}/rf/tagStat.do">
        <li class="float_left"><img src="${pageContext.request.contextPath}/images/lnb_icon05.png" /></li>
        <li class="float_left font05 lnb03_01">태그상태관제</li>
        </a>
      </ul>
      <ul class="clear_both lnb02">
        <a href="${pageContext.request.contextPath}/rf/infraStat.do">
        <li class="float_left"><img src="${pageContext.request.contextPath}/images/lnb_icon04.png" /></li>
        <li class="float_left font04 lnb03_01">RFID인프라원격관제</li>
        </a>
      </ul>
      <div><span class="lnb07"><img src="${pageContext.request.contextPath}/images/navi_bg.png"/></span></div>
	 </c:when>
	        <c:when test="${MENU == 'RF02'}">
  <!-- 버튼 -->
      <ul class="clear_both lnb02">
        <a href="${pageContext.request.contextPath}/rf/tagStat.do">
        <li class="float_left"><img src="${pageContext.request.contextPath}/images/lnb_icon02.png" /></li>
        <li class="float_left font04 lnb03_01">태그상태관제</li>
        </a>
      </ul>
      <!-- 버튼 -->
      <ul class="clear_both lnb02">
        <a href="${pageContext.request.contextPath}/rf/infraStat.do">
        <li class="float_left"><img src="${pageContext.request.contextPath}/images/lnb_icon03.png" /></li>
        <li class="float_left font05 lnb03_01">RFID인프라원격관제</li>
        </a>
      </ul>
      <div class="clear_both"> <span class="lnb04"><img src="${pageContext.request.contextPath}/images/navi_bg.png"/></span> </div>
    
	 </c:when>
</c:choose>
      <div class="footer">
        <ul>
          <li><img src="${pageContext.request.contextPath}/images/logo_lg.png"/></li>
        </ul>
        <ul class="footer01">
          <li class="font06">Copyright(c)2014 LG히다찌</li>
          <li class="font06">All rights reserved.</li>
        </ul>
      </div>
    </div>
    
    <!--우-->
    <div id="content" class="float_right">
    <c:choose>
	  <c:when test="${MENU == 'HO01'}">
<span class="lnb06"></span> 
	 </c:when>
	       <c:when test="${MENU == 'HO02'}">
<span class="lnb05"></span> 
	 </c:when>
	        <c:when test="${MENU == 'HO03'}">
<span class="lnb08"></span> 
	 </c:when>
	        <c:when test="${MENU == 'RF01'}">
      <span class="lnb06"></span> 
	 </c:when>
	        <c:when test="${MENU == 'RF02'}">
        <span class="lnb05"></span> 
	 </c:when>
</c:choose>
    
          <tiles:insertAttribute name="search"/>
          <tiles:insertAttribute name="content"/>
    </div>
  </div>
</div>
 <div id="noDataDiv" style="display:none" class="noDataDiv">조회된 데이터가 없습니다.</div>
</body>
</html>
