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
   	colNames:['NO','검사유형','검사일자', '검사장소'],
   	colModel:[
  	   	{name:'RNUM',index:'RNUM', width:10, align:"center", sortable:false},
   		{name:'ISPT_TYPE_NM',index:'ISPT_TYPE_NM', width:60, align:"center"},
   		{name:'ISPT_DT',index:'ISPT_DT', width:90, sorttype:"date", align:"center"},
   		{name:'RSMO_NM',index:'RSMO_NM', width:100, align:"center"}
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

	},
	gridComplete: function () {
		 $('.jqgrow').mouseover(function(e) {
				if((typeof e.result) != "undefined")
				{
					return;
				}
				e.result = true;
		  });
			  
	},
   	loadComplete: function(data) {
   		fnGridResize();
	   	fnShowDiv($('#gridData').getGridParam('records'));
    }
 });
jQuery("#gridData").jqGrid('navGrid','#pager2',{search:false,edit:false,add:false,del:false,refresh:false});
$("input.ui-pg-input").val('0'); 
});

function fnSearchDataList(postdata)
{
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
  	isDataComplete = true;	  
}
function fnSearchIsptDataList(postdata)
{
	var url = '${pageContext.request.contextPath}/hs/selectIsptList.do';
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
       <div id="pager2">      
       </div>      
      </div>



             
