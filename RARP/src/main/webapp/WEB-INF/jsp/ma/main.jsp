<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java" %> 
<%@ include file="/WEB-INF/jsp/co/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>::3D PLM 기반 스마트 철도차량 유지보수 시스템::</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.3.custom.js"></script>
<script src="${pageContext.request.contextPath}/grid/js/i18n/grid.locale-kr.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}grid/js/jquery.jqGrid.min.js" type="text/javascript"></script>
<SCRIPT language="JavaScript" type="text/javascript" src="http://map.vworld.kr/js/vworldMapInit.js.do?apiKey=4BAAB4E4-05F1-3AC2-8159-F7E66C72EFBB"></SCRIPT>
</head> 
<script type="text/javaScript" language="javascript">
<!--
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
//-->
</script>
 <style type="text/css">

 </style>
<script type="text/javascript">
	var testMarker = null;
	var map = null;  
	vworld.showMode = false; 
	// 통합지도 초기화
	vworld.init(
		"cont1"	// rootDiv
		, "map-first" // mapType
		,function() {         
			map = this.vmap; 
			map.setBaseLayer(map.vworldBaseMap); 
			map.setControlsType({"simpleMap":true});	//간단한 화면	 
			fnGetRsmoData();
			//화면중심점과 레벨로 이동
			map.setCenterAndZoom(14134309.5508,4516685.71485, 10); 
		}
		,function (obj){SOPPlugin = obj; }//initCallback
		,function (msg){alert('vworld init fail');}//failCallback
	);
   
	function addMarker(lon, lat, message, icon){
		var marker = new vworld.Marker(lon, lat,message,"");
        var imgurl = "http://192.168.0.211:8010/rarp/images/icon_train"+icon+".png";
		if (typeof imgurl == 'string') {marker.setIconImage(imgurl);}
	
		// 마커의 z-Index 설정
		marker.setZindex(3);
		map.addMarker(marker);
		testMarker = marker; 
	}

	jQuery(document).ready(function(){	
		
	});
	
    function fnSelRSMO(id)
    {
    	location.href ="<c:url value="/rf/tagStat.do"/>?RSMO=" + id //"<c:url value="/hs/ispt.do"/>?RSMO=" + id
    }
	function fnGetRsmoData()
	{
		 jQuery.ajax({
	           type:"POST",
	           url:"${pageContext.request.contextPath}/sy/selectRsmoInfoList.do",
	           dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
	           success : function(data) {
	             // 통신이 성공적으로 이루어졌을 때 이 함수를 타게 된다.
				$.each(data, function(key, list) {
					$.each(list, function(key, value) {
							var info = "<br>" + value["RSMO_ZIP_CD"] +" "+ value["RSMO_ADDR"] + "<br><img src='http://192.168.0.211:8010/rarp/images/BTN_RSMO.png' onClick='javascript:fnSelRSMO(\""+value["RSMO_CD"]+"\")'>"
							addMarker(value["RSMO_X"], value["RSMO_Y"], info , value["RSMO_CD"]);
					 })
				})
	           },
	           error : function(xhr, status, error) {
	        	   alert("위치 정보 조회시 Error 발생 하였습니다.")
	           }

	     });
	}

</script>     
</head>
<body>
<center>
<div class="main01">
  <div class="map01 clear_both">
    <div class="map02 font10">차량사업소 선택</div>
    <div class="map03" id="cont1">
    </div>
    <a href="javascript:fnForward(1)">
    <div class="btn01 float_right map04">SKIP</div>
    </a>
  </div>
</div>
</center>
</body>
</html>