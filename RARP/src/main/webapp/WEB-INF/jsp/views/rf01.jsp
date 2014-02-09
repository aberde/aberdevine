<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<script type="text/javascript">

jQuery(document).ready(function(){
	jQuery("#gridData").jqGrid({
		datatype: "local",
		height: 210,
		width : dataWapperWdith,
		mtype: 'POST',
	   	colNames:['NO','센싱일시','분류', '주요장치', '품명','부품코드','시리얼넘버', '센싱타입', '센싱값','인식리더명', 'GRS_CD', 'SEL_PART_ID'],
	   	colModel:[
	   	   	{name:'RNUM',index:'RNUM', width:30, align:"center",sortable:false},
	  	   	{name:'SNS_DT',index:'SNS_DT', width:110, align:"center"},
		   	{name:'BOM_LVL_2',index:'BOM_LVL_2', width:80, sortable:false, align:"center"},
		   	{name:'BOM_LVL_3',index:'BOM_LVL_3', width:80, sortable:false, align:"center"},
		   	{name:'PART_NM',index:'PART_NM', width:80, align:"center",sortable:false},
	   		{name:'PART_CD',index:'PART_CD', width:70, align:"center",sortable:false},
	   		{name:'PART_SN',index:'PART_SN', width:70, align:"center",sortable:false},
	   		{name:'SNS_TYPE',index:'SNS_TYPE', width:70, formatter: fnFmtSnsType, align:"center",sortable:false},
	   		{name:'SNS_VAL',index:'SNS_VAL', width:30, align:"center",sorttype:"float",sortable:false},
	   		{name:'INFRA_RSC_NM',index:'INFRA_RSC_NM', width:80, align:"center",sortable:false},
	   		{name:'GRS_CD',index:'GRS_CD', width:0, hidden:true},
	   		{name:'SEL_PART_ID',index:'SEL_PART_ID', width:0, hidden:true}
	   	],
	   	multiselect: false,
	   	rowNum:6,
	  	pager: '#pager2',
	   	sortname: 'SNS_DT',
	    sortorder: "desc",
	    emptyrecords: "", 
	    viewrecords: true,
		loadui: 'disable',
	    forceFit:     true,
	    onSelectRow : function (rowId) {
	    	if(fnGetRowEvt() == false)
	    		return;
    		
			var rowData = $("#gridData").getRowData(rowId);
			$('#viewer')[0].contentWindow.fnFocusPart(rowData.SEL_PART_ID);
			fnDataAct("DX");
		},
		gridComplete: function () {
			 $('.jqgrow').mouseover(function(e) {
				if((typeof e.result) != "undefined")
				{
					return;
				}
				e.result = true;
				var rowId = $(this).attr('id');
				var rowData = $("#gridData").getRowData(rowId);
				$('#viewer')[0].contentWindow.fnSelectPart(rowData.SEL_PART_ID);
			 });

	   	 	if(fnGetAct() =="TX")
	   	 	{
	   	 		$('#viewer')[0].contentWindow.fnPartOnly("${PARAM['PART_CD']}_${PARAM['GRS_CD']}");
	   	 	}
   			var timer = setInterval(function () {       
	   			if(bEndLoadModel == true)  
		   		{
	   				clearInterval(timer); 
	   				if(selPartCd =="")
	   				{
	   					fnSearchDataEvt();
	   				}
		   		}       
	   		  }, 1000);
   			 fnSelectData(selPartCd + "_" + selGrsCd);
   			 
		},
	   	loadComplete: function(data) {
	   		isDataComplete = true;
	   		fnGridResize();
	   		fnShowDiv($('#gridData').getGridParam('records'));
	    }
	});
	jQuery("#gridData").jqGrid('navGrid','#pager2',{search:false,edit:false,add:false,del:false,refresh:false});
	$("input.ui-pg-input").val('0'); 
	if( $("#RFID_TAG_ID").val() != "")
 	{
		fnDataAct("TX");
		$("#sltTrnType").val("${PARAM['TRN_KIND_CD']}").change();
		fnViewShow();
 	}
});

function fnFmtSnsType(cellValue, opts, rowObject)
{
	var retVal ="";
	switch (cellValue) {
		case '01' : retVal ='온도'; break;
		case '02' : retVal ='진동'; break;
		case '03' : retVal ='밧데리 잔량'; break;
	 	default   :  retVal =''; break;
	}
	return retVal;
}

function fnSearchDataList(postdata)
{
	var url = '${pageContext.request.contextPath}/rf/selectTagSensingList.do';
    $("#gridData").jqGrid('setGridParam', { url: url,datatype:'json',postData: postdata});
    $("#gridData").trigger("reloadGrid");
}

function fnSearchDataEvt()
{
	if($("#CRG_TYPE_CD").val() =="")
		return;

	var postBom = fnGetBomPostData();
	jQuery.ajax({
        type:"POST",
        url:"${pageContext.request.contextPath}/rf/selectTagPartList.do",
        data:  postBom,
        dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
        success : function(data) {
        	var sensingArray = new Array();
        	var partArray = new Array();
        	var parentArray = new Array();
        	$.each(data, function(key, list) {
        		if(key =="sensing")
        		{
            		var i = 0;
	        		$.each(list, function(key, value) {
	        			sensingArray[i++] = value["SEL_PART_ID"];
	    			});
        	    }
        		else if(key =="part")
        		{
        			var i = 0;
	        		$.each(list, function(key, value) {
	        			partArray[i++] = value["SEL_PART_ID"];
	    			});
        		}
        		else if(key =="parent")
        		{
        			var i = 0;
	        		$.each(list, function(key, value) {
	        			parentArray[i++] = value["SEL_PART_ID"];
	    			});
        		}
			})
            
			$('#viewer')[0].contentWindow.fnBgDefaultParts(parentArray);
		    $('#viewer')[0].contentWindow.fnBgParts(sensingArray);
   	       	$('#viewer')[0].contentWindow.fnFocusParts(partArray);

        },
        complete: function(data) {
	   		isDataEvtComplete = true;
	    }
  });
}
</script>
  <!-- step2 -->
      <div class="content02">
        <div class="content02_01 clear_both">
          <dl class="float_left">
            <dt class="float_left font08 content03">편성:</dt>
            <dd  id="spanPrgNm"class="float_left font09 content03 lnb03"></dd>
            <dd class="float_left content_line"><img src="${pageContext.request.contextPath}/images/line02.gif" /></dd>
          </dl>
          <dl class="float_left">
            <dt class="float_left font08 content03">차호:</dt>
            <dd  id="spanCrgNm" class="float_left font09 content03 lnb03"></dd>
            <dd class="float_left content_line"><img src="${pageContext.request.contextPath}/images/line02.gif" /></dd>
          </dl>
          <dl class="float_left">
            <dt class="float_left font08 content03">객차유형:</dt>
            <dd  id="spanCrgTypeNm" class="float_left font09 content03 lnb03"></dd>
            <dd class="float_left content_line"><img src="${pageContext.request.contextPath}/images/line02.gif" /></dd>
          </dl>
          <dl class="float_left">
            <dt class="float_left font08 content03">RFID 태그ID:</dt>
            <dd  id="spanRfidTagId"  class="float_left font09 content03 lnb03"></dd>
                        <dd class="float_left content_line"><img src="${pageContext.request.contextPath}/images/line02.gif" /></dd>
            
          </dl>
           <dl id="divBomPath" style="display:none;" class="float_left">
            <dt class="float_left font08 content03">BOM:</dt>
            <dd  id="spanBomPath"  class="float_left font09 content03 lnb03"></dd>
          </dl>
        </div>
        <div class="content02_02 clear_both">
          <div class="float_left content02_03">
            <div>
              <ul>
                <a href="javascript:fnBomUp()">
                <li class="font15" ></li>
                </a> <a href="javascript:fnBomUp()">
                <li class=" btn02">상위 BOM◀</li>
                </a> <a href="javascript:fnDateInit()">
                <li class=" btn03 lnb03">초기화</li>
                </a> <a href="javascript:fnBomDown()">
                <li class=" btn02 lnb03">▶하위 BOM</li>
                </a> <a href="javascript:fnBomDown()">
                <li class="font15"></li>
                </a>
              </ul>
            </div>
            <div id="gridBomWapper">
               <table id="gridBom" class="scroll" ></table>
            </div>
          </div>
          <div id="viewerWapper" class="float_right content02_04" >
            <iframe id="viewer" src="" style="display:none;width:1113px; height:407px;" height="100%" width="100%" frameborder=0 scrolling = no></iframe>  
          </div>
        </div>
      </div>
      <!-- step3 -->
      <div id="gridDataWapper" style="margin-top:10px;">
        <table id="gridData"></table>
      </div>
      <div class="">
       <div id="pager2"></div>      
      </div>


            

             
