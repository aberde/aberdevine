<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="java.util.*"%>
<style type="text/css">

</style>

<script type="text/javascript">
jQuery(document).ready(function(){
jQuery("#gridData").jqGrid({
    url:'${pageContext.request.contextPath}/rf/selectRcvrList.do',
	datatype: "json",
	height: 210,
	width : dataWapperWdith,
   	colNames:['NO','장애발생일시','종류','이름', '아이디', '장애내용', 'IP주소', '리셋','RCVR_EVENT_ID','ROOT_CAUSE_YN'],
   	colModel:[
		{name:'RNUM',index:'RNUM', width:30, align:"center", sortable:false},
		{name:'REG_DT',index:'REG_DT', width:100, align:"center"},
   		{name:'UI_INFRA_TYPE_NM_KO',index:'UI_INFRA_TYPE_NM_KO', width:60, align:"center"},
   		{name:'INFRA_RSC_NM',index:'INFRA_RSC_NM', width:100, align:"center"},
   		{name:'INFRA_RSC_ID',index:'INFRA_RSC_ID', width:80, align:"center"},
   		{name:'EVENT_ITEM',index:'EVENT_ITEM', width:150, formatter: fnFmtEventItem, align:"center"},	
   		{name:'HOST_IP',index:'HOST_IP', width:80, align:"center"},	
   		{name:'RESET',index:'RESET', width:40, sortable:false, align:"center"},
   		{name:'RCVR_EVENT_ID',index:'RCVR_EVENT_ID', width:0, hidden:true},
   		{name:'ROOT_CAUSE_YN',index:'ROOT_CAUSE_YN', width:0, hidden:true}
   	],
   	rowNum:100000,
   	sortname: 'REG_DT',
    sortorder: "desc",
    emptyrecords: "", 
    viewrecords: true,
	loadui: 'disable',
    forceFit:     true,
    afterInsertRow: function(id, currentData, jsondata) {
        var button = "<a class='gridbutton' data-id='"+id+"' href='#'><img src='${pageContext.request.contextPath}/images/btn_reset.png' /></a>";
        $(this).setCell(id, "RESET", button);
    },
    loadComplete: function(data) {
        $(".gridbutton").on('click', function(e) {
           e.preventDefault();
           var rowData = $("#gridData").getRowData($(this).data("id"));
           fnReset(rowData);
        });

        var ids = $("#gridData").getDataIDs();
        // Grid Data Get!
       	 $.each( ids,function(idx, rowId){
       	 rowData = $("#gridData").getRowData(rowId);
       		 // 만약 rowName 컬럼의 데이터가 공백이라면 해당 Row의 색상을 변경!
       		 //if (rowData.ROOT_CAUSE_YN == 'Y') {
       		 //	$("#gridData").setRowData(rowId, false, { background:"#F7FAFC" });
       		// }
       	 });
     	fnShowDiv($('#gridData').getGridParam('records'));
    }
    
});

onClickMdlwData(null, "${FIRST_MDLW.INFRA_RSC_ID}");

});

function fnFmtEventItem(cellValue, opts, rowObject)
{
	var retVal ="";
	switch (cellValue) {
		case 'MWE_NET' : retVal ='미들웨어 네트워크 연결 장애'; break;
		case 'MWE_HDD' : retVal ='미들웨어 하드디스크 사용량 장애'; break;
		case 'MWE_DBC' : retVal ='미들웨어 데이터베이스 연결 장애'; break;
		case 'MWE_MEM' : retVal ='미들웨어 메모리 사용량 장애'; break;
		case 'MEW_CPU' : retVal ='미들웨어 CPU 사용량 징에'; break;
		case 'RDE_ANT' : retVal ='리더 안테나 상태 장애'; break;
		case 'RDE_IOP' : retVal ='리더 IO포트 상태 장애'; break;
		case 'RDE_NET' : retVal ='리더 네트워크 연결 장애'; break;
		case 'RDE_PWS' : retVal ='리더 전원 연결 장애'; break;
		case 'RDE_DEV' : retVal ='리더 프로토콜 상태 장애'; break;
		case 'RDE_CPU' : retVal ='리더 CPU 사용량 장애'; break;
		case 'RDE_MEM' : retVal ='리더 메모리 사용량 장애'; break;
	 	default   :  retVal =''; break;
	}
	return retVal;
}

function fnReset(data)
{
	alert(data.INFRA_RSC_ID.indexOf("MW"));
	if(data.INFRA_RSC_ID.indexOf("MW") != -1)
    {
		fnExecuteCmd(data.INFRA_RSC_ID, "M", "SOFT");
	}
	else
	{
		fnExecuteCmd(data.INFRA_RSC_ID, "R", "HARD");
	}
	
}

function fnGetRootData(obj, id)
{
	if($(obj).parent().find("ul:visible").length)
	{
	}
}

function fnText(a)
{
	var p = parseFloat(a);
	if(p > 100)
		return 100;
	else if(p < 0)
		return 0;
	else
		return a;
}

function fnGetResMonData(obj, rscId, type)
{
    $("#gridData").trigger("reloadGrid");
    
	 jQuery.ajax({
           type:"POST",
           url:"${pageContext.request.contextPath}/rf/selectResMonInfo.do",
           data: "INFRA_RSC_ID=" + rscId,
           dataType:"JSON", 
           success : function(data) {
           if(type =="M")
           {
           		$.each(data, function(key, value) {

                	if(key =="MON")
                    {
                        var totLen = 249;
                        var totLeft = 227;
                        var height = 12;
           				$("#DAT_COL_CNT").text(Math.round(parseFloat(value["DAT_COL_CNT"])/10000));
           				$("#MW_DPR_CNT").text(parseFloat(value["MW_DPR_CNT"])/10000);
           				$("#FNC_RPT_CNT").text(parseFloat(value["FNC_RPT_CNT"])/10000);

           				if(Math.round(parseFloat(value["DAT_COL_CNT"])/10000) < Math.round(parseFloat(value["DAT_COL_CNT_THRESHOLD"])/10000))
           				{
           					$("#DAT_COL_CNT_BAR").attr("src", "${pageContext.request.contextPath}/images/data.png");
               			}
           				else
               			{
           					$("#DAT_COL_CNT_BAR").attr("src", "${pageContext.request.contextPath}/images/data01.png");
                   		}
           				if(Math.round(parseFloat(value["MW_DPR_CNT"])/10000) < Math.round(parseFloat(value["MW_DPR_CNT_THRESHOLD"])/10000))
           				{
           					$("#MW_DPR_CNT_BAR").attr("src", "${pageContext.request.contextPath}/images/data.png");
               			}
           				else
               			{
           					$("#MW_DPR_CNT_BAR").attr("src", "${pageContext.request.contextPath}/images/data01.png");
                   		}
           				if(Math.round(parseFloat(value["FNC_RPT_CNT"])/10000) < Math.round(parseFloat(value["FNC_RPT_CNT_THRESHOLD"])/10000))
           				{
           					$("#FNC_RPT_CNT_BAR").attr("src", "${pageContext.request.contextPath}/images/data.png");
               			}
           				else
               			{
           					$("#FNC_RPT_CNT_BAR").attr("src", "${pageContext.request.contextPath}/images/data01.png");
                   		}
                   		
           				$("#DAT_COL_CNT_BAR").width(totLen*(parseFloat(value["DAT_COL_CNT"])/10000)/100);
           				$("#MW_DPR_CNT_BAR").width(totLen*(parseFloat(value["MW_DPR_CNT"])/10000)/100);
           				$("#FNC_RPT_CNT_BAR").width(totLen*(parseFloat(value["FNC_RPT_CNT"])/10000)/100);
           				
           				$("#DAT_COL_CNT_BAR_TH").css("left",totLeft*(parseFloat(value["DAT_COL_CNT_THRESHOLD"])/10000)/100);
           				$("#MW_DPR_CNT_BAR_TH").css("left",totLeft*(parseFloat(value["MW_DPR_CNT_THRESHOLD"])/10000)/100);
           				$("#FNC_RPT_CNT_BAR_TH").css("left",totLeft*(parseFloat(value["FNC_RPT_CNT_THRESHOLD"])/10000)/100);	
           				
           				$("#DAT_COL_CNT_BAR").height(height);
           				$("#MW_DPR_CNT_BAR").height(height);
           				$("#FNC_RPT_CNT_BAR").height(height);
                   				
           				$("#MW_CPU").text(fnText(value["CPU"]));
           				$("#MW_MEM").text(fnText(value["MEM"]));
           				$("#MW_HDD").text(fnText(value["MW_HDD"]));

           				if(parseInt(value["CPU"]) < parseInt(value["CPU_THRESHOLD"]))
           				{
           					$("#MW_CPU_BAR").attr("src", "${pageContext.request.contextPath}/images/data.png");
               			}
           				else
               			{
           					$("#MW_CPU_BAR").attr("src", "${pageContext.request.contextPath}/images/data01.png");
                   		}
           				if(parseInt(value["MEM"]) < parseInt(value["MEM_THRESHOLD"]))
           				{
           					$("#MW_MEM_BAR").attr("src", "${pageContext.request.contextPath}/images/data.png");
               			}
           				else
               			{
           					$("#MW_MEM_BAR").attr("src", "${pageContext.request.contextPath}/images/data01.png");
                   		}
           				if(parseInt(value["MW_HDD"]) < parseInt(value["MW_HDD_THRESHOLD"]))
           				{
           					$("#MW_HDD_BAR").attr("src", "${pageContext.request.contextPath}/images/data.png");
               			}
           				else
               			{
           					$("#MW_HDD_BAR").attr("src", "${pageContext.request.contextPath}/images/data01.png");
                   		}

           				$("#MW_CPU_BAR").width(totLen*(parseFloat(fnText(value["CPU"])))/100);
           				$("#MW_MEM_BAR").width(totLen*(parseFloat(fnText(value["MEM"])))/100);
           				$("#MW_HDD_BAR").width(totLen*(parseFloat(fnText(value["MW_HDD"])))/100);
    
           				$("#MW_CPU_BAR_TH").css("left",totLeft*(parseFloat(value["CPU_THRESHOLD"]))/100);
           				$("#MW_MEM_BAR_TH").css("left",totLeft*(parseFloat(value["MEM_THRESHOLD"]))/100);
           				$("#MW_HDD_BAR_TH").css("left",totLeft*(parseFloat(value["MW_HDD_THRESHOLD"]))/100);
           				
           				$("#MW_CPU_BAR").height(height);
           				$("#MW_MEM_BAR").height(height);
           				$("#MW_HDD_BAR").height(height);
                    }
                	else if(key =="RCV_LIST")
                    {
                        if(data["RCV_LIST"].length == 0)
                        {
                    		$('#MW_MSG_ON').show();
                      		$('#MW_MSG_ERR').hide();
                    		$('#DAT_COL_CNT_DL').attr("class", "content25 clear_both");
                    		$('#MW_DPR_CNT_DL').attr("class", "content25 clear_both");
                    		$('#FNC_RPT_CNT_DL').attr("class", "content25 clear_both");
                    		$('#MW_CPU_DL').attr("class", "content25 clear_both");
                    		$('#MW_MEM_DL').attr("class", "content25 clear_both");
                    		$('#MW_HDD_DL').attr("class", "content25 clear_both");
                    		$('#MW_MWE_DBC').attr("src", "${pageContext.request.contextPath}/images/icon_03.png");
                    		$('#MW_MWE_NET').attr("src", "${pageContext.request.contextPath}/images/icon_04.png");

                    		//$("#" + rscId +"_STATE").attr("src", "${pageContext.request.contextPath}/images/icon_on.png");
                    		
                        }
                        else
                        {

                     		$('#MW_MSG_ON').hide();
                      		$('#MW_MSG_ERR').show();
                      		$("#" + rscId +"_STATE").attr("src", "${pageContext.request.contextPath}/images/icon_off.png");
                            
                        }
                        
                   		$.each(value, function(key, item) {
 
                   			if(item["EVENT_ITEM"] =="MWE_DCO")
                           	{
                   				$('#DAT_COL_CNT_DL').attr("class", "content25_01 clear_both");
                            }
                       		if(item["EVENT_ITEM"] =="MWE_DPR")
                           	{
                       			$('#MW_DPR_CNT_DL').attr("class", "content25_01 clear_both");
                            }
                       		if(item["EVENT_ITEM"] =="MWE_RTR")
                           	{
                       			$('#FNC_RPT_CNT_DL').attr("class", "content25_01 clear_both");
                            }

                       		if(item["EVENT_ITEM"] =="MWE_CPU")
                           	{
                       			$('#MW_CPU_DL').attr("class", "content25_01 clear_both");
                            }
                       		if(item["EVENT_ITEM"] =="MWE_MEM")
                           	{
                       			$('#MW_MEM_DL').attr("class", "content25_01 clear_both");
                            }
                       		if(item["EVENT_ITEM"] =="MWE_HDD")
                           	{
                       			$('#MW_HDD_DL').attr("class", "content25_01 clear_both");
                            }
                    		
                    		
                       		if(item["EVENT_ITEM"] =="MWE_DBC")
                           	{
                       			$('#MW_MWE_DBC').attr("src", "${pageContext.request.contextPath}/images/icon_03_off.png");
                            }
                       		if(item["EVENT_ITEM"] =="MWE_NET")
                           	{
                       			$('#MW_MWE_NET').attr("src", "${pageContext.request.contextPath}/images/icon_04_off.png");
                            }
            			})
                    }
    			})
           }
           else
           {
                var totLen = 249;
                var totLeft = 227;
                var height = 12;
         		
          		$.each(data, function(key, value) {

                	if(key =="MON")
                    {
           				$("#TAG_ALARM_CNT").text(value["TAG_ALARM_CNT"]);
           				$("#CPU").text(fnText(value["CPU"]));
           				$("#MEM").text(fnText(value["MEM"]));
           				
           				if(parseInt(value["TAG_ALARM_CNT"]) < parseInt(value["TAG_ALARM_CNT_THRESHOLD"]))
           				{
           					$("#TAG_ALARM_CNT_BAR").attr("src", "${pageContext.request.contextPath}/images/data.png");
               			}
           				else
               			{
           					$("#TAG_ALARM_CNT_BAR").attr("src", "${pageContext.request.contextPath}/images/data01.png");
                   		}
           				if(parseInt(value["CPU"]) < parseInt(value["CPU_THRESHOLD"]))
           				{
           					$("#CPU_BAR").attr("src", "${pageContext.request.contextPath}/images/data.png");
               			}
           				else
               			{
           					$("#CPU_BAR").attr("src", "${pageContext.request.contextPath}/images/data01.png");
                   		}
           				if(parseInt(value["MEM"]) < parseInt(value["MEM_THRESHOLD"]))
           				{
           					$("#MEM_BAR").attr("src", "${pageContext.request.contextPath}/images/data.png");
               			}
           				else
               			{
           					$("#MEM_BAR").attr("src", "${pageContext.request.contextPath}/images/data01.png");
                   		}

          				$("#TAG_ALARM_CNT_BAR").width(totLen*(parseFloat(value["TAG_ALARM_CNT"]))/100);
           				$("#CPU_BAR").width(totLen*(parseFloat(fnText(value["CPU"])))/100);
           				$("#MEM_BAR").width(totLen*(parseFloat(fnText(value["MEM"])))/100);

           				$("#TAG_ALARM_CNT_BAR_TH").css("left",totLeft*(parseFloat(value["TAG_ALARM_CNT_THRESHOLD"]))/100);
           				$("#CPU_BAR_TH").css("left",totLeft*(parseFloat(value["CPU_THRESHOLD"]))/100);
           				$("#MEM_BAR_TH").css("left",totLeft*(parseFloat(value["MEM_THRESHOLD"]))/100);

           				$("#TAG_ALARM_CNT_BAR").height(height);
           				$("#CPU_BAR").height(height);
           				$("#MEM_BAR").height(height);
                    }
                	else if(key =="RCV_LIST")
                    {
                        if(data["RCV_LIST"].length == 0)
                        {
                    		$('#RD_MSG_ON').show();
                      		$('#RD_MSG_ERR').hide();
                    		$('#RDE_DEV').attr("src", "${pageContext.request.contextPath}/images/icon_05.png");
                    		$('#RDE_NET').attr("src", "${pageContext.request.contextPath}/images/icon_04.png");
                    		$('#RDE_PWS').attr("src", "${pageContext.request.contextPath}/images/icon_06.png");
                    		$('#RDE_IOP').attr("src", "${pageContext.request.contextPath}/images/icon_07.png");

                    		$('#ANTENNA1_STATUS').attr("src", "${pageContext.request.contextPath}/images/icon_08.png");
                    		$('#ANTENNA2_STATUS').attr("src", "${pageContext.request.contextPath}/images/icon_08.png");
                    		$('#ANTENNA3_STATUS').attr("src", "${pageContext.request.contextPath}/images/icon_08.png");
                    		$('#ANTENNA4_STATUS').attr("src", "${pageContext.request.contextPath}/images/icon_08.png");
                    		
                    		$('#CPU_DL').attr("class", "content24 clear_both");
                    		$('#MEM_DL').attr("class", "content25 clear_both");
                    		$('#TAG_ALARM_CNT_DL').attr("class", "content25 clear_both");
                    		//$("#" + rscId +"_STATE").attr("src", "${pageContext.request.contextPath}/images/icon_on.png");
                        }
                        else
                        {
                       		$('#RD_MSG_ON').hide();
                      		$('#RD_MSG_ERR').show();
                      		$("#" + rscId +"_STATE").attr("src", "${pageContext.request.contextPath}/images/icon_off.png"); 		
                        }
                        
                   		$.each(value, function(key, item) {
 							if(item["EVENT_ITEM"] =="RDE_DEV")
                           	{
                       			$('#RDE_DEV').attr("src", "${pageContext.request.contextPath}/images/icon_05_off.png");
                            }
                       		if(item["EVENT_ITEM"] =="RDE_NET")
                           	{
                       			$('#RDE_NET').attr("src", "${pageContext.request.contextPath}/images/icon_04_off.png");
                            }
                       		if(item["EVENT_ITEM"] =="RDE_PWS")
                           	{
                       			$('#RDE_PWS').attr("src", "${pageContext.request.contextPath}/images/icon_06_off.png");
                            }
                       		if(item["EVENT_ITEM"] =="RDE_IOP")
                           	{
                       			$('#RDE_IOP').attr("src", "${pageContext.request.contextPath}/images/icon_07_off.png");
                            }

                       		if(item["EVENT_ITEM"] =="RDE_TGC")
                           	{
                       			$('#TAG_ALARM_CNT_DL').attr("class", "content25_01 clear_both");
                            }

                       		if(item["EVENT_ITEM"] =="RDE_CPU")
                           	{
                       			$('#CPU_DL').attr("class", "content25_01 clear_both");
                            }

                       		if(item["EVENT_ITEM"] =="RDE_MEM")
                           	{
                       			$('#MEM_DL').attr("class", "content25_01 clear_both");
                            }
            			})
                    }
                	else if(key =="ANT_LIST")
                    {
                   		$.each(value, function(key, item) {
                   			fnAntennaHandler(item["ANTENNA1_STATUS"], $('#ANTENNA1_STATUS'));
                   			fnAntennaHandler(item["ANTENNA2_STATUS"], $('#ANTENNA2_STATUS'));
                   			fnAntennaHandler(item["ANTENNA3_STATUS"], $('#ANTENNA3_STATUS'));
                   			fnAntennaHandler(item["ANTENNA4_STATUS"], $('#ANTENNA4_STATUS'));
            			})
                    }
    			})
           }
          }
     });
}

function fnAntennaHandler(val, obj)
{
	switch (val) {
		case 'NORMAL' : 
			obj.attr("src", "${pageContext.request.contextPath}/images/icon_09.png");
  			break;
		case 'NON_USE' : 
			obj.attr("src", "${pageContext.request.contextPath}/images/icon_08.png");
			break;
		case 'FAULT' : 
			obj.attr("src", "${pageContext.request.contextPath}/images/icon_10.png");
			break;
	 	default   :  
	 		obj.attr("src", "${pageContext.request.contextPath}/images/icon_11.png");
	 	    break;
	}
}

function fnGetResData(obj, rscId, type)
{
	var sendInfo = { INFRA_RSC_ID: rscId};
			
	 jQuery.ajax({
         type:"POST",
         url:"${pageContext.request.contextPath}/rf/selectResInfo.do",
         data: sendInfo, 
         dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
         success : function(data) {
           // 통신이 성공적으로 이루어졌을 때 이 함수를 타게 된다.
           	$.each(data, function(key, value) {
           		
               	if(type =="M")
                {
               		$("#MW_HOST_IP").text(value["HOST_IP"]);
               		$("#MW_INFRA_RSC_NM").text(value["INFRA_RSC_NM"]);
                }
               	else
                {
               		$("#HOST_IP").text(value["HOST_IP"]);
               		$("#INFRA_RSC_NM").text(value["INFRA_RSC_NM"]);
                }
			})
      	   
         }
   });
}

var timer;
var selRscId = "";
function onClickMdlwData(obj, rscId)
{
	if(rscId == "")
		return;
		
	selRscId = rscId;
	clearInterval(timer);  
	$("#divMdlw").show();
	$("#divReader").hide();
	fnGetResMonData(obj,rscId,"M");
	fnGetResData(obj, rscId, "M");
	timer = setInterval(function () {        
		fnGetResMonData(obj,rscId,"M");        
	}, 3000);
		//if($(obj).parent().find("li:visible").length == 0)
		//{
	//}
}

function onClickReaderData(obj, rscId)
{
	selRscId = rscId;
	clearInterval(timer);  
	$("#divMdlw").hide();
	$("#divReader").show();
	fnGetResMonData(obj,rscId,"R");
	fnGetResData(obj, rscId, "R");
	timer = setInterval(function () {        
		fnGetResMonData(obj,rscId,"R");        
	}, 3000);
}

function fnMwReset()
{
	var mwIP = $("#MW_HOST_IP").text();
	fnExecuteCmd(selRscId, "M", "SOFT");
}

function fnRdReset()
{
	var rdIP = $("#HOST_IP").text();
	fnExecuteCmd(selRscId, "R", "HARD");
}

function fnRdInit()
{
	var rdIP = $("#HOST_IP").text();
	fnExecuteCmd(selRscId, "R", "PARAMETER");
}


function fnExecuteCmd(rscId, type, cmd)
{
	var sendInfo = { INFRA_RSC_ID: rscId, TYPE:type, CMD : cmd}		
	 jQuery.ajax({
         type:"POST",
         url:"${pageContext.request.contextPath}/sy/exceuteCmd.do",
         data: sendInfo, 
         dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
         success : function(data) {
           // 통신이 성공적으로 이루어졌을 때 이 함수를 타게 된다.
           	$.each(data, function(key, value) {
               	if(value)
                {
                    alert("성공하였습니다.");
                }
               	else
                {
               		alert("실패하였습니다.");
                }
			})
      	   
         }
   });
}
</script>

      <div  class="clear_both">
        <div class="content05 clear_both"> <span class="float_left"><img src="${pageContext.request.contextPath}/images/btn_arrow.png"/></span> <b class="float_left lnb03" >장애발생인프라</b></div>
        <div id="gridDataWapper"  class="content05">
             <table id="gridData"></table>            
        </div>
     </div>
<!-- step2 -->
         <div class="clear_both">
          <div class="float_left content17 lnb01">
<%
try
{
String sRcvrState = "on";
String resNm = "";
String sReadcss = "";
String treeRootId = (String)request.getAttribute("RSMO_INFRA");
Map<String, String> rootMap = (Map<String, String>)request.getAttribute(treeRootId);
%>          
            <div class="clear_both content08 font10" onClick="javascript:fnGetRootData(this, '<%=String.valueOf(rootMap.get("INFRA_STRUCTURE_SEQ")) %>');"> <span class="float_left"><img src="${pageContext.request.contextPath}/images/icon_01.png" /></span> <span class="float_left lnb03"><%=rootMap.get("STRUCTURE_NAME") %></span> </div>
				<div style="overflow-y:scroll;height:520px">
<%
String treeMdlwListId = "INFRA_MDLW_LIST_" + String.valueOf(rootMap.get("INFRA_STRUCTURE_SEQ"));
ArrayList<String> mdlwList = (ArrayList<String>)request.getAttribute(treeMdlwListId);
for (String mdlwId : mdlwList) {
Map<String, String> mdlwMap = (Map<String, String>)request.getAttribute(mdlwId);
String sRcvrCnt = String.valueOf(mdlwMap.get("RCVR_CNT"));
if(sRcvrCnt.equals("0"))
	 sRcvrState = "on";
else
	 sRcvrState = "off";
%>
            
              <div class="clear_both content12" onClick="javascript:onClickMdlwData(this, '<%=String.valueOf(mdlwMap.get("INFRA_RSC_ID")) %>');" > <span class="float_left content10"><img src="${pageContext.request.contextPath}/images/line03.png"/></span>
                <ul class="float_left content09">
                  <li class="float_left content12"><img src="${pageContext.request.contextPath}/images/icon_arrow.png"/></li>
                  <li class="float_left content10 font09" title="<%=mdlwMap.get("INFRA_RSC_NM") %>"><%=mdlwMap.get("INFRA_RSC_NM") %></li>
                  <li class="float_left content_line01"><img src="${pageContext.request.contextPath}/images/line03.gif"/></li>
                  <li class="float_left content11"><img id="<%=String.valueOf(mdlwMap.get("INFRA_RSC_ID")) %>_STATE" src="${pageContext.request.contextPath}/images/icon_<%=sRcvrState%>.png"/></li>
                </ul>
              </div>
<%
String treeResListId = "INFRA_RES_LIST_" + String.valueOf(mdlwMap.get("INFRA_RSC_ID"));
ArrayList<String> resList = (ArrayList<String>)request.getAttribute(treeResListId);
String sFirstCss = "content12";
String sIcon = "line04.png";
int i = 0;
for (String resId : resList) {
 Map<String, String> resMap = (Map<String, String>)request.getAttribute(resId);
 i ++;
 sRcvrCnt = String.valueOf(resMap.get("RCVR_CNT"));
 if(sRcvrCnt.equals("0"))
	 sRcvrState = "on";
 else
	 sRcvrState = "off";
 
 if(i >= resList.size())
	 sIcon = "line05.png";
 sReadcss = "";
 resNm = String.valueOf(resMap.get("INFRA_RSC_NM"));
 if(resNm.length() >= 9)
 {
	 resNm = resNm.substring(0, 9) + "...";
	 sReadcss = "_01";
 }
%>
              <div class="clear_both <%=sFirstCss %>" onClick="javascript:onClickReaderData(this, '<%=String.valueOf(resMap.get("INFRA_RSC_ID"))%>');"> <span class="float_left content14"><img src="${pageContext.request.contextPath}/images/<%=sIcon%>"/></span>
                <ul class="float_left content09">
                  <li class="float_left content12"><img src="${pageContext.request.contextPath}/images/icon_arrow.png"/></li>
                  <li class="float_left content10" title="<%=resMap.get("INFRA_RSC_NM") %>"><%= resNm %></li>
                  <li class="float_left content_line02<%=sReadcss%>"><img src="${pageContext.request.contextPath}/images/line02.gif"/></li>
                  <li class="float_left content11"><img id="<%=String.valueOf(resMap.get("INFRA_RSC_ID")) %>_STATE" src="${pageContext.request.contextPath}/images/icon_<%=sRcvrState%>.png"/></li>
                </ul>
              </div>
 <%
 sFirstCss = ""; 
} %>             
          
 <%}
}
catch(Exception ex)
{
}
%>
  		</div>
  	</div>
 <!-- step3 -->
          <div id="divMdlw" style="display:none;" class="float_right content02_05 content16">
            <div class="clear_both">
              <div class="float_left content15">
                <div class=" content18">
                  <p class=" font08">인프라명 :</p>
                  <p class=" font09 lnb03">  <span id="MW_INFRA_RSC_NM"></span></p>
                </div>
                <div class="content19">
                  <p class="font08">IP :</p>
                  <p class="font09 lnb03"> <span id="MW_HOST_IP" ></span></p>
                </div>
              </div>
              <div id="MW_MSG_ON" style="" class="float_left content20">
                <ul>
                  <li><img src="${pageContext.request.contextPath}/images/icon_02.png"/></li>
                  <li class=" font10 content21">정상적으로 작동하고 있습니다.</li>
                </ul>
              </div>

                <div id="MW_MSG_ERR" style="display:none" class="float_left content20_01">
                   <ul>
                  <li><img src="${pageContext.request.contextPath}/images/icon_02.png"/></li>
                  <li class=" font10 content21">장애가 발생하였습니다. 수동복구처리를 하시기 바랍니다. </li>
                </ul>
                </div> 
              
            </div>
            <div class="clear_both">
              <div class="float_left content22 content_line04">
                <ul class="content31">
                  <li class="font12">CONTROL</li>
                  <!-- 리셋버튼 -->
                  <li class="content23"><a href="javascript:fnMwReset();"><img src="${pageContext.request.contextPath}/images/btn_reset02.png" onmouseover="this.src='${pageContext.request.contextPath}/images/btn_reset02_on.png'" onmouseout="this.src='${pageContext.request.contextPath}/images/btn_reset02.png'" /></a></li>
                </ul>
              </div>
              <div class="content22_01 float_left" >
                <div class="float_left content_line04  content22_02">
                  
                  <div>
                    <dl id="MW_CPU_DL" class="content25 clear_both">
                      <dt class="font12"> CPU 사용률</dt>
                      <dd class="float_left content26"><span id="MW_CPU_BAR_TH" class="content26_01"><img style="display:none" src="${pageContext.request.contextPath}/images/data_line.png"/></span><img id="MW_CPU_BAR" src="${pageContext.request.contextPath}/images/data.png" style="width:0px"/></dd>
                      <dd class="float_right font13 content27"><span id="MW_CPU" ></span>%</dd>
                    </dl>
                  </div>
                                    <div>
                    <dl id="MW_MEM_DL" class="content25 clear_both">
                      <dt class="font12"> 메모리 사용률</dt>
                      <dd class="float_left content26"><span id="MW_MEM_BAR_TH" class="content26_01"><img style="display:none" src="${pageContext.request.contextPath}/images/data_line.png"/></span><img id="MW_MEM_BAR" src="${pageContext.request.contextPath}/images/data.png" style="width:0px"/></dd>
                      <dd class="float_right font13 content27"><span id="MW_MEM"></span>%</dd>
                    </dl>
                  </div>
                  <div>
                    <dl id="MW_HDD_DL" class="content25 clear_both">
                      <dt class="font12"> HDD 사용률</dt>
                      <dd class="float_left content26"><span id="MW_HDD_BAR_TH" class="content26_01"><img style="display:none" src="${pageContext.request.contextPath}/images/data_line.png"/></span><img id="MW_HDD_BAR" src="${pageContext.request.contextPath}/images/data.png" style="width:0px"/></dd>
                      <dd class="float_right font13 content27" ><span id="MW_HDD"></span>%</dd>
                    </dl>
                  </div>
                </div>
                <div class="float_left content22_02">

<div>
                    <dl id="MW_DPR_CNT_DL" class="content25 clear_both">
                      <dt class="font12"> 데이터 수집</dt>
                      <dd class="float_left content26"><span id="DAT_COL_CNT_BAR_TH" class="content26_01"><img style="display:none" src="${pageContext.request.contextPath}/images/data_line.png"/></span><img id="DAT_COL_CNT_BAR" src="${pageContext.request.contextPath}/images/data.png" style="width:0px"/></dd>
                      <dd class="float_right font13 content27"><span id="DAT_COL_CNT" ></span>%</dd>
                    </dl>
                  </div>
                  <div> 
                    <dl id="MW_DPR_CNT_DL"  class="content25 clear_both">
                      <dt class="font12"> 데이터 처리</dt>
                      <dd class="float_left content26"><span id="MW_DPR_CNT_BAR_TH" class="content26_01"><img style="display:none" src="${pageContext.request.contextPath}/images/data_line.png"/></span><img id="MW_DPR_CNT_BAR" src="${pageContext.request.contextPath}/images/data.png" style="width:0px"/></dd>
                      <dd class="float_right font13 content27"><span id="MW_DPR_CNT" ></span>%</dd>
                    </dl>
                  </div>
                  <div>
                    <dl id="FNC_RPT_CNT_DL" class="content25 clear_both">
                      <dt class="font12"> 리포트 전달</dt>
                      <dd class="float_left content26"><span id="FNC_RPT_CNT_BAR_TH" class="content26_01"><img style="display:none" src="${pageContext.request.contextPath}/images/data_line.png"/></span><img id="FNC_RPT_CNT_BAR"  src="${pageContext.request.contextPath}/images/data.png" style="width:0px"/></dd>
                      <dd class="float_right font13 content27"><span id="FNC_RPT_CNT" ></span>%</dd>
                    </dl>
                  </div>
                  <div>
                    <div class="content25 clear_both">
                      <ul class="float_left content28 content_line04">
                        <li><img id="MW_MWE_DBC"  src="${pageContext.request.contextPath}/images/icon_03.png"/></li>
                        <!-- <img src="${pageContext.request.contextPath}/images/icon_03_off.png"/> -->
                        <li class="content29 font14">DB 연결 상태</li>
                      </ul>
                      <ul class="float_left content28">
                        <li class=""><img id="MW_MWE_NET" src="${pageContext.request.contextPath}/images/icon_04.png"/></li>
                        <!-- <img src="${pageContext.request.contextPath}/images/icon_04_on.png"/> -->
                        <li class=" content29 font14">네트워크 상태</li>
                      </ul>
                      </dl>
                    </div>
                  </div>
                </div>
              </div>
			</div>
		</div>

 <!-- step3 -->
          <div id="divReader" style="display:none;" class="float_right content02_05 content16">
            <div class="clear_both">
              <div class="float_left content15">
                <div class=" content18">
                  <p class=" font08">인프라명 :</p>
                  <p class=" font09 lnb03"><span id="INFRA_RSC_NM"></span></p>
                </div>
                <div class="content19">
                  <p class="font08">IP :</p>
                  <p class="font09 lnb03"><span id="HOST_IP"></span></p>
                </div>
              </div>
              <div id="RD_MSG_ON"  class="float_left content20">
                <ul>
                  <li><img src="${pageContext.request.contextPath}/images/icon_02.png"/></li>
                  <li class=" font10 content21">정상적으로 작동하고 있습니다.</li>
                </ul>
              </div>
                <div id="RD_MSG_ERR" style="display:none" class="float_left content20_01">
                   <ul>
                  <li><img src="${pageContext.request.contextPath}/images/icon_02.png"/></li>
                  <li class=" font10 content21">장애가 발생하였습니다. 수동복구처리를 하시기 바랍니다. </li>
                </ul>
              </div>
              
            </div>
            <div class="clear_both">
              <div class="float_left content22_03 content_line04">
                <ul class="content30">
                  <li class="font12">CONTROL</li>
                  <!-- 리셋버튼 -->
                  <li class="content23"><a href="javascript:fnRdReset();"><img src="${pageContext.request.contextPath}/images/btn_reset03.png" onmouseover="this.src='${pageContext.request.contextPath}/images/btn_reset03_on.png'" onmouseout="this.src='${pageContext.request.contextPath}/images/btn_reset03.png'" /></a></li>
                  <li class="content23_01"><a href="javascript:fnRdInit();"><img src="${pageContext.request.contextPath}/images/btn_initial.png" onmouseover="this.src='${pageContext.request.contextPath}/images/btn_initial_on.png'" onmouseout="this.src='${pageContext.request.contextPath}/images/btn_initial.png'" /></a></li>
                </ul>
              </div>
              <div class="content22_01 float_left">
                <div class="float_left content_line04 content_line03 content22_02">
                  <div> 
                    <!-- 에러가 발생하지 않을때 .content25 에러가 발생할 때 .content25_01-->
                    <dl id="CPU_DL" class="content24 clear_both">
                      <dt class="font12">CPU 사용률</dt>
                      <dd class="float_left content26"><span id="CPU_BAR_TH" class="content26_01"><img style="display:none" src="${pageContext.request.contextPath}/images/data_line.png"/></span><img id="CPU_BAR" src="${pageContext.request.contextPath}/images/data.png" style="width:0px"/></dd>
                      <dd class="float_right font13 content27"><span id="CPU"></span>%</dd>
                    </dl>
                  </div>
                  <div>
                    <dl id="MEM_DL" class="content25 clear_both">
                      <dt class="font12">메모리 사용률</dt>
                      <dd class="float_left content26"><span id="MEM_BAR_TH" class="content26_01"><img style="display:none" src="${pageContext.request.contextPath}/images/data_line.png"/></span><img id="MEM_BAR" src="${pageContext.request.contextPath}/images/data.png" style="width:0px"/></dd>
                      <dd class="float_right font13 content27"><span id="MEM"></span>%</dd>
                    </dl>
                  </div>
                  <div>
                    <dl id="TAG_ALARM_CNT_DL" class="content25 clear_both">
                      <dt class="font12">초 당 태그 리딩 건수</dt>
                      <dd class="float_left content26"><span id="TAG_ALARM_CNT_BAR_TH" class="content26_01" style="display:none"><img style="display:none" src="${pageContext.request.contextPath}/images/data_line.png"/></span><img id="TAG_ALARM_CNT_BAR" src="${pageContext.request.contextPath}/images/data.png" style="width:0px"/></dd>
                      <dd class="float_right font13 content27"><span id="TAG_ALARM_CNT"></span>%</dd>
                    </dl>
                  </div>
                </div>
                <div class="float_left content22_02">
                  <div class="content_line05">
                    <div class="content25 clear_both">
                      <p class="font12" style="padding-bottom:20px"> 안테나</p>
                      <ul class="float_left content28_01">
                        <li><img id="ANTENNA1_STATUS" src="${pageContext.request.contextPath}/images/icon_11.png"/></li>
                        <li class="content29 font16">1</li>
                      </ul>
                      <ul class="float_left content28_01">
                        <li><img id="ANTENNA2_STATUS" src="${pageContext.request.contextPath}/images/icon_11.png"/></li>
                        <li class=" content29 font16">2</li>
                      </ul>
                      <ul class="float_left content28_01">
                        <li><img id="ANTENNA3_STATUS" src="${pageContext.request.contextPath}/images/icon_11.png"/></li>
                        <li class=" content29 font16">3</li>
                      </ul>
                      <ul class="float_left content28_01">
                        <li><img id="ANTENNA4_STATUS" src="${pageContext.request.contextPath}/images/icon_11.png"/></li>
                        <li class=" content29 font16">4</li>
                      </ul>
                    </div>
                  </div>
                  <div>
                    <div class="content25 clear_both">
                      <ul class="float_left content28_01 content_line04">
                        <li><img id="RDE_DEV" src="${pageContext.request.contextPath}/images/icon_05.png"/></li>
                        <!-- <img src="${pageContext.request.contextPath}/images/icon_05_off.png"/> -->
                        <li class="content29 font16">리더프로토콜<br/>
                          상태</li>
                      </ul>
                      <ul class="float_left content28_01 content_line04">
                        <li><img id="RDE_NET" src="${pageContext.request.contextPath}/images/icon_04.png"/></li>
                        <!-- <img src="${pageContext.request.contextPath}/images/icon_04_on.png"/> -->
                        <li class=" content29 font16">네트워크<br/>
                          상태</li>
                      </ul>
                      <ul class="float_left content28_01 content_line04">
                        <li><img id="RDE_PWS" src="${pageContext.request.contextPath}/images/icon_06.png"/></li>
                        <!-- <img src="${pageContext.request.contextPath}/images/icon_06_on.png"/> -->
                        <li class=" content29 font16">외부전원연결<br/>
                          상태</li>
                      </ul>
                      <ul class="float_left content28_01">
                        <li style="padding-top:10px"><img id="RDE_IOP" src="${pageContext.request.contextPath}/images/icon_07.png"/></li>
                        <!-- <img src="${pageContext.request.contextPath}/images/icon_07_off.png"/> -->
                        <li class=" content29 font16">I/O포트<br/>
                          상태</li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>