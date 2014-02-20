<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 	<style type="text/css">
		
	</style>
<script type="text/javascript">
jQuery(document).ready(function(){
jQuery("#gridData").jqGrid({
	datatype: "local",
	height: 210,
	width : dataWapperWdith,
	mtype: 'POST',
   	colNames:['NO','검사일자','고장분류', '주요장치', '불량상태', '조치코드', '부품코드', '수량', '점검 및 조치내역', '사업소', 'SEL_PART_ID'],
   	colModel:[
  	   	   	{name:'RNUM',index:'RNUM', width:30, align:"center", sortable:false},
     	    {name:'ISPT_DT',index:'ISPT_DT', width:150, sorttype:"date", align:"center"}, 
   	  		{name:'BOM_LVL_2',index:'BOM_LVL_2', width:100, sortable:false, align:"center"},
   			{name:'BOM_LVL_3',index:'BOM_LVL_3', width:100, sortable:false, align:"center"},
   	   		{name:'TRBL_STAT_NM',index:'TRBL_STAT_NM', width:90, align:"center"},
   	   		{name:'FIX_CD_NM',index:'FIX_CD_NM', width:90, align:"center"},
   	   		{name:'PART_CD',index:'PART_CD', width:90, align:"center"},
   	   		{name:'AMT',index:'AMT', width:30, sorttype:"int", align:"center"},
   	   		{name:'FIX_DESC',index:'FIX_DESC', width:100, align:"center"},
   	   		{name:'RSMO_NM',index:'RSMO_NM', width:80, align:"center"},
   	   		{name:'SEL_PART_ID',index:'SEL_PART_ID', width:0, hidden:true}
   	],
 	multiselect: false,
   	rowNum:6,
  	pager: '#pager2',
   	sortname: 'ISPT_DT',
    sortorder: "desc",
    emptyrecords: "", 
    viewrecords: true,
	loadui: 'disable',
    forceFit:     true,
    onSelectRow : function (rowId) {
    	if(fnGetRowEvt() == false)
    		return;
		
		var rowData = $("#gridData").getRowData(rowId);
		// 위수코드가 존재하지 않아 3D화면에 정상출력되지 않아 제거함.(2014.02.12. : 이민규)
// 		$('#viewer')[0].contentWindow.fnFocusPart(rowData.SEL_PART_ID);
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
				// 위수코드가 존재하지 않아 3D화면에 정상출력되지 않아 제거함.(2014.02.12. : 이민규)
// 				$('#viewer')[0].contentWindow.fnSelectPart(rowData.SEL_PART_ID);
		});
	
		var timer = setInterval(function () {       
	  		if(bEndLoadModel == true)  
	   		{
				clearInterval(timer); 
				if( selPartCd =="")
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
});


function fnSearchDataList(postdata)
{
	if($("#txtPart").val() != "")
	{
		postdata.PART_CD = $("#txtPart").val();
    }
	// ##################################################
	// ## 고장이력조회에서 하단 조회데이터 검색시 위수코드 제거(고장이력은 부품코스, 위수코드 둘다 검색 안함.)
	// ##################################################
    postdata.GRS_CD = "";
	// ##################################################
	var url = '${pageContext.request.contextPath}/hs/selectTrblList.do';
    $("#gridData").jqGrid('setGridParam', { url: url,datatype:'json',postData: postdata});
    $("#gridData").trigger("reloadGrid");
}

function fnSearchDataEvt()
{
	if($("#CRG_TYPE_CD").val() =="")
		return;

	jQuery.ajax({
        type:"POST",
        url:"${pageContext.request.contextPath}/sy/selectPartDtlList.do",
        data:  $( "#pForm" ).serializeArray(),
        dataType:"JSON", 
        success : function(data) {
        	var partArray = new Array();
        	var i = 0;
        	$.each(data, function(key, list) {
        		$.each(list, function(key, value) {
        			partArray[i++] = value["SEL_PART_ID"];
    			});
			})
			//alert(selPartCd);
       		$('#viewer')[0].contentWindow.fnFocusParts(partArray);
        },
        complete : function(data) {
		    selPartCd ="";
 		    selGrsCd = "";
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
           <a href="javascript:fnPlay('M10008183');">
           <div id="divAniParts" style="display:none" class="float_right btn01">정비 지침서</div>
          </a> 
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
               <table id="gridBom"></table>
            </div>
          </div>
          <div class="float_right content02_04" >
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

             
