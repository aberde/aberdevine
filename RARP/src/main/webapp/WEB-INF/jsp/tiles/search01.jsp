<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">


function fnSearch()
{
	if(!fnSearchValidation())
		return;
	
	initForm();
	
	fnSetAct("");
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

	var postBom =  fnGetBomPostData();
	postBom.PART_CD = "";
	postBom.PART_SN= "";
	postBom.GRS_CD= "";
	fnSearchBomList(postBom);
	
	var crgValue = $("#sltCrg option:selected").val();
	var crgValues = crgValue.split("_");
	fnSearchInfo(crgValues[3], crgValues[2]);
	var postdata = fnGetDataPostData();
	fnSearchIsptDataList(postdata);
	fnSearchDataList(postdata);
	fnGetTagValue(postdata);
	fnViewShow();
	
	fnBomInfo(false);
	fnDivAniShow("");
}

function fnReportDown()
{
	if(!fnSearchValidation())
		return;
	
	var url = "${pageContext.request.contextPath}/sy/selectExcelDown.do";
	//pForm.target = "";                    
	pForm.action = url;                   
	pForm.method = "post";
	pForm.submit();     
}
  </script>
 <div  class="content01 clear_both">
        <div class="float_left">
          <dl>
            <span class="float_left content03_01"><img src="${pageContext.request.contextPath}/images/btn_arrow.png"/></span>
            <dt class="font07 float_left lnb03 content03_01">차종</dt>
            <dd class="float_left lnb03 content04">
       <select id="sltTrnType" onChange="javascript:fnChangeSltTrnType(this)">
        <option value=""> :: 차종 :: </option>
<c:forEach var="TRN_ITEM" items="${TRN_TYPE_DATA}" varStatus="status">
 		<option value="${TRN_ITEM['COMM_DTL_ID']}">${TRN_ITEM['COMM_DTL_NM']}</option>
</c:forEach>
      </select>
            </dd>
            <dd class="float_left content_line06"><img src="${pageContext.request.contextPath}/images/line01.gif" /></dd>
          </dl>
        </div>
        <div class="float_left">
          <dl>
            <span class="float_left content03_01"><img src="${pageContext.request.contextPath}/images/btn_arrow.png"/></span>
            <dt class="font07 float_left lnb03 content03_01"> 편성</dt>
            <dd class="float_left lnb03 content04">
       <select id="sltPrg" style="width:100px" onChange="javascript:fnChangeSltPrg(this)">
         <option value=""> :: 편성 :: </option>
       </select>
            </dd>
            <dd class="float_left content_line06"><img src="${pageContext.request.contextPath}/images/line01.gif" /></dd>
          </dl>
        </div>
        <div class="float_left">
          <dl>
            <span class="float_left content03_01"><img src="${pageContext.request.contextPath}/images/btn_arrow.png"/></span>
            <dt class="font07 float_left lnb03 content03_01">차호</dt>
            <dd class="float_left lnb03 content04">
       <select id="sltCrg" style="width:100px" onChange="javascript:fnChangeSltCrg(this)">
         <option value=""> :: 차호 :: </option>
       </select>
            </dd>
            <dd class="float_left lnb03 content04">
              <input id="txtCrgTypeNm" type="text" style="width:50px;border:0px;background-color:transparent" readonly/>
            </dd>
            <dd class="float_left content_line06"><img src="${pageContext.request.contextPath}/images/line01.gif" /></dd>
          </dl>
        </div>
        <div class="float_left">
          <dl>
            <span class="float_left content03_01"><img src="${pageContext.request.contextPath}/images/btn_arrow.png"/></span>
            <dt class="font07 float_left lnb03 content03_01"> 검사유형</dt>
            <dd class="float_left lnb03 content04">
                  <select id="sltIsptType" onChange="">
        <option value=""> :: 검사유형 :: </option>
			<c:forEach var="ISPT_ITEM" items="${ISPT_TYPE_DATA}" varStatus="status">
			 		<option value="${ISPT_ITEM['COMM_DTL_ID']}">${ISPT_ITEM['COMM_DTL_NM']}</option>
			</c:forEach>
      </select>
            </dd>
            <dd class="float_left content_line06"><img src="${pageContext.request.contextPath}/images/line01.gif" /></dd>
          </dl>
        </div>
        <div class="float_left">
          <dl>
            <span class="float_left content03_01"><img src="${pageContext.request.contextPath}/images/btn_arrow.png"/></span>
            <dt class="font07 float_left lnb03 content03_01"> 검사일자</dt>
            <dd class="float_left lnb03 content04">
              <input name="" type="text" id="stDate" style="width:80px;"  />~
              <input name="" type="text" id="edDate" style="width:80px;"  /></dd>
          </dl>
        </div>
        <a href="javascript:fnReportDown();">
        <div class="float_right btn06 lnb03">리포트 다운로드</div>
        </a> <a href="javascript:fnSearch();">
        <div class="float_right btn01">검색</div>
        </a> 
    </div>

