<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" session="false"%>

<%@ include file="/WEB-INF/jsp/egovframework/include/header.jsp" %>

<script type="text/javascript">
	/**
	 * 검색
	 */
	function ChgSec() {
		document.frm.action = "<c:url value="/menu003/sub003/GRG_003_301.do"/>";
		document.frm.submit();
	}
	
	/**
	 * 용어 내용 숨기기/펼치기
	 * @param strGubun 게시판 일련번호
	 */
	function trView(strGubun) {
    	var oForm = document.getElementById("trID"+strGubun);
    
    	if (oForm.style.display != "none") {
    		oForm.style.display = "none";
    	} else {
    		oForm.style.display = "";
		}
    }
	
	/**
	 * 페이징
	 */
	function linkToPage(pageNo) {
		document.frm.pageIndex.value = pageNo;
		document.frm.action = "<c:url value="/menu003/sub003/GRG_003_301.do"/>";
		document.frm.submit();
	}
</script>

<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/green_box.css" />" />

<div id="container">	
		
	<div id="chest">
		<div class="area-cnt">
			<div id="heart">
				<div id="post-3096" class="post-3096 page type-page status-publish hentry entry">
					<h1 class="entry_title">용어사전</h1>
			    </div>

				<div class="front">
					
					<form:form name="frm" commandName="vo" method="post" action="/menu003/sub003/GRG_003_301.do" onsubmit="">
						<form:hidden path="category_seq" />
						<form:hidden path="searchCondition" />
						
						<div class="dopms-bbs">
							<!-- 용어사전검색영역 S -->
							<div class="btype2 parag">
								<div class="wrap">
									<fieldset>
										<legend>용어사전검색</legend>  
										<img src="<c:url value="/images/menu003/sub003/knowledge_txt1.gif"/>" alt="용어사전검색" style="margin-bottom:-3px;" /> 
										<label>검색어 입력</label>
										<form:input path="searchKeyword" cssClass="txt-type" />
										<input type="image" src="<c:url value="/images/menu003/sub003/btn_search.gif"/>" alt="검색" class="btn-type" />
									</fieldset>
								</div>
							</div>
							<!-- //용어사전검색영역 E -->
          
							<!-- 용어사전키워드버튼 S -->
							<div class="dic_keyword_box" style="margin-top:15px;">
								<div class="wordtitle">
									<table  border="0" cellspacing="0" cellpadding="2">
										<tr>
											<td><img src="<c:url value="/images/menu003/sub003/sear_01.gif"/>" alt="숫자검색" width="59" height="17" /></td>                  
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=001"><img src="<c:url value="/images/menu003/sub003/word_09.gif"/>" alt="0~9" width="37" height="15" /></a></td>
										</tr>
									</table>                  
								</div>
              
								<div class="wordtitle">
									<table  border="0" cellspacing="0" cellpadding="2">
										<tr>
											<td><img src="<c:url value="/images/menu003/sub003/sear_02.gif"/>" alt="한글검색" width="59" height="17" /></td>                  
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=101"><img src="<c:url value="/images/menu003/sub003/word_k1.gif"/>" alt="ㄱ" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=102"><img src="<c:url value="/images/menu003/sub003/word_k2.gif"/>" alt="ㄴ" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=103"><img src="<c:url value="/images/menu003/sub003/word_k3.gif"/>" alt="ㄷ" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=104"><img src="<c:url value="/images/menu003/sub003/word_k4.gif"/>" alt="ㄹ" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=105"><img src="<c:url value="/images/menu003/sub003/word_k5.gif"/>" alt="ㅁ" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=106"><img src="<c:url value="/images/menu003/sub003/word_k6.gif"/>" alt="ㅂ" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=107"><img src="<c:url value="/images/menu003/sub003/word_k7.gif"/>" alt="ㅅ" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=108"><img src="<c:url value="/images/menu003/sub003/word_k8.gif"/>" alt="ㅇ" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=109"><img src="<c:url value="/images/menu003/sub003/word_k9.gif"/>" alt="ㅈ" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=110"><img src="<c:url value="/images/menu003/sub003/word_k10.gif"/>" alt="ㅊ" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=111"><img src="<c:url value="/images/menu003/sub003/word_k11.gif"/>" alt="ㅋ" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=112"><img src="<c:url value="/images/menu003/sub003/word_k12.gif"/>" alt="ㅌ" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=113"><img src="<c:url value="/images/menu003/sub003/word_k13.gif"/>" alt="ㅍ" width="20" height="15" /></a></td>                      
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=114"><img src="<c:url value="/images/menu003/sub003/word_k14.gif"/>" alt="ㅎ" width="20" height="15" /></a></td>
										</tr>
									</table>                  
								</div>
              
								<div class="wordtitle">
									<table  border="0" cellspacing="0" cellpadding="2">
										<tr>
											<td><img src="<c:url value="/images/menu003/sub003/sear_03.gif"/>" alt="영문검색" width="59" height="17" /></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=a"><img src="<c:url value="/images/menu003/sub003/word_e1.gif"/>" alt="a" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=b"><img src="<c:url value="/images/menu003/sub003/word_e2.gif"/>" alt="b" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=c"><img src="<c:url value="/images/menu003/sub003/word_e3.gif"/>" alt="c" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=d"><img src="<c:url value="/images/menu003/sub003/word_e4.gif"/>" alt="d" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=e"><img src="<c:url value="/images/menu003/sub003/word_e5.gif"/>" alt="e" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=f"><img src="<c:url value="/images/menu003/sub003/word_e6.gif"/>" alt="f" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=g"><img src="<c:url value="/images/menu003/sub003/word_e7.gif"/>" alt="g" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=h"><img src="<c:url value="/images/menu003/sub003/word_e8.gif"/>" alt="h" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=i"><img src="<c:url value="/images/menu003/sub003/word_e9.gif"/>" alt="i" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=j"><img src="<c:url value="/images/menu003/sub003/word_e10.gif"/>" alt="j" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=k"><img src="<c:url value="/images/menu003/sub003/word_e11.gif"/>" alt="k" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=l"><img src="<c:url value="/images/menu003/sub003/word_e12.gif"/>" alt="l" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=m"><img src="<c:url value="/images/menu003/sub003/word_e13.gif"/>" alt="m" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=n"><img src="<c:url value="/images/menu003/sub003/word_e14.gif"/>" alt="n" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=o"><img src="<c:url value="/images/menu003/sub003/word_e15.gif"/>" alt="o" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=p"><img src="<c:url value="/images/menu003/sub003/word_e16.gif"/>" alt="p" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=q"><img src="<c:url value="/images/menu003/sub003/word_e17.gif"/>" alt="q" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=r"><img src="<c:url value="/images/menu003/sub003/word_e18.gif"/>" alt="r" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=s"><img src="<c:url value="/images/menu003/sub003/word_e19.gif"/>" alt="s" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=t"><img src="<c:url value="/images/menu003/sub003/word_e20.gif"/>" alt="t" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=u"><img src="<c:url value="/images/menu003/sub003/word_e21.gif"/>" alt="u" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=v"><img src="<c:url value="/images/menu003/sub003/word_e22.gif"/>" alt="v" width="20" height="15" /></a></td>
										</tr>
										<tr>
											<td></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=w"><img src="<c:url value="/images/menu003/sub003/word_e23.gif"/>" alt="w" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=x"><img src="<c:url value="/images/menu003/sub003/word_e24.gif"/>" alt="x" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=y"><img src="<c:url value="/images/menu003/sub003/word_e25.gif"/>" alt="y" width="20" height="15" /></a></td>
											<td><a href="<c:url value="/menu003/sub003/GRG_003_301.do"/>?category_seq=5&searchCondition=z"><img src="<c:url value="/images/menu003/sub003/word_e26.gif"/>" alt="z" width="20" height="15" /></a></td>
											<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
										</tr>
									</table>
								</div>
							</div>
							<!-- //용어사전키워드버튼 E -->
            
							<!-- 용어목록 S -->
							<div class="tablestyle">
								<div class="keywordlist">
									
									<c:forEach var="vo" items="${ dicList }">
									
										<dl>
											<dt>
												<a href="javascript:trView('<c:out value="${ vo.board_seq }" />');"><c:out value="${ vo.title }" /></a>
											</dt>
											<dd name="trID<c:out value="${ vo.board_seq }" />" id="trID<c:out value="${ vo.board_seq }" />" style="display: none ;">
												<c:out value="${ ct:removeTag(vo.content) }" />
											</dd>
										</dl>
										
									</c:forEach>

								</div>                            
							</div>
							<!-- //용어목록 E -->
          
							<div class="clear"></div>
							
							<div id="paging" style="text-align: center; margin-bottom: 10px;">
								<ui:pagination paginationInfo="${ paginationInfo }" type="text" jsFunction="linkToPage" />
								<form:hidden path="pageIndex" />
							</div>
					
						</div>

					</form:form>
					
				</div>
				<hr />
			</div>
		</div>
	</div>

</div>

<%@ include file="/WEB-INF/jsp/egovframework/include/sidebar.jsp" %>

<div class="clear"></div>

<%@ include file="/WEB-INF/jsp/egovframework/include/footer.jsp" %>