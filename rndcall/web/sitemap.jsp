<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
### 경로   : <%= request.getRequestURI() %>
### 사업명 : 11년도 개방형 연구성과분석지원서비스
### 사업일 : 2011-07-10 ~ 2011-08-31
### 개발일 : 2011-07-14
### 개발자 : 박인선
-->
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/include/top.jsp"%>
<!-- start # LY-Container -->
<div class="LY-Container">

<!-- start # 내용 들어갑니다. // 12년 10월 04일 수정 사항  =======================================-->
<br />
<br />

<% if (mainRoleCD != null && (mainRoleCD.equals("0000C") || mainRoleCD.equals("0000Z"))) { %>
<!-- start # SiteMap -->
<table border="0" cellspacing="0" cellpadding="0" class="SiteMap">
  <tr>
    <td class="Img"><img src="../images/content/Content_SImg03.gif" alt="사이트 맵"  /></td>
    <td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title01.gif" alt="상담센터" /></h1>
							<li><a href="JavaScript:goInquireForm()"><img src="../images/content/SiteMap_Title01_1.gif" border="0" alt="온라인상담" /></a></li>
							<li><a href="JavaScript:goFaq()"><img src="../images/content/SiteMap_Title01_2.gif" border="0" alt="Q&A" /></a></li>
							<li><a href="JavaScript:goInquireList()"><img src="../images/content/SiteMap_Title01_3.gif" border="0" alt="자주 묻는 질문" /></a></li>
					</ul>
			</div>														
	</td>
	<td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title07.gif" alt="신문고" /></h1>
							<li><a href="JavaScript:goOffer()"><img src="../images/content/SiteMap_Title07_1.gif" border="0" alt="신문고" /></a></li>
					</ul>
			</div>														
	</td>
    <td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title02.gif" alt="자료실" /></h1>
							<li><a href="http://rndgate.ntis.go.kr/switch.do?prefix=/un/rndLaw&page=/unRndLaw.do?method=retrieveLawSearchByKey" target="_blank"><img src="../images/content/SiteMap_Title02_1.gif" alt="법령자료" /></a></li>
							<li><a href="JavaScript:goData()"><img src="../images/content/SiteMap_Title02_3.gif" border="0" alt="기타자료" /></a></li>
					</ul>
			</div>														
	</td>
    <td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title05.gif" alt="관리자" /></h1>
							<li><a href="/switch.do?prefix=&page=/memberAdmin.do?method=getUserList&searchVO.roleCD=&searchVO.search_sel=&searchVO.search_word=&searchVO.menu_sn=09"><img src="../images/content/SiteMap_Title05_1.gif" border="0" alt="권한관리" /></a></li>
							<li><a href="/switch.do?prefix=&page=/category.do?method=getCategoryList&searchVO.menu_sn=09"><img src="../images/content/SiteMap_Title05_2.gif" border="0" alt="분야관리" /></a></li>
							<li><a href="/switch.do?prefix=/admin&page=/Admin.do?method=getOfflineDataForm&searchVO.menu_sn=09"><img src="../images/content/SiteMap_Title05_3.gif" border="0" alt="오프라인자료등록" /></a></li>
							<li><a href="/switch.do?prefix=/statistic&page=/Statistic.do?method=getStatCategory&searchVO.menu_sn=09"><img src="../images/content/SiteMap_Title05_4.gif" border="0" alt="통계정보" /></a></li>
					</ul>
			</div>														
	</td>
    <td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title03.gif" alt="마이페이지" /></h1>
							<li><a href="JavaScript:goMypage()"><img src="../images/content/SiteMap_Title03_1.gif" border="0" alt="마이페이지" /></a></li>
					</ul>
			</div>														
	</td>
  </tr>
  <tr>
  	<td></td>
    <td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title04.gif" alt="공지사항" /></h1>
							<li><a href="JavaScript:goNotice()"><img src="../images/content/SiteMap_Title04_1.gif" border="0" alt="공지사항" /></a></li>
					</ul>
			</div>														
	</td>
  </tr>
</table>
<!-- end # SiteMap -->
<% } else if (mainRoleCD != null && mainRoleCD.equals("0000B")){ %>
<!-- start # SiteMap -->
<table border="0" cellspacing="0" cellpadding="0" class="SiteMap">
  <tr>
    <td class="Img"><img src="../images/content/Content_SImg03.gif" alt="사이트 맵"  /></td>
    <td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title01.gif" alt="상담센터" /></h1>
							<li><a href="JavaScript:goInquireForm()"><img src="../images/content/SiteMap_Title01_1.gif" border="0" alt="온라인상담" /></a></li>
							<li><a href="JavaScript:goFaq()"><img src="../images/content/SiteMap_Title01_2.gif" border="0" alt="Q&A" /></a></li>
							<li><a href="JavaScript:goInquireList()"><img src="../images/content/SiteMap_Title01_3.gif" border="0" alt="자주 묻는 질문" /></a></li>
							</ul>
			</div>														
	</td>
	<td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title07.gif" alt="신문고" /></h1>
							<li><a href="JavaScript:goOffer()"><img src="../images/content/SiteMap_Title07_1.gif" border="0" alt="신문고" /></a></li>
					</ul>
			</div>														
	</td>
    <td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title02.gif" alt="자료실" /></h1>
							<li><a href="http://rndgate.ntis.go.kr/switch.do?prefix=/un/rndLaw&page=/unRndLaw.do?method=retrieveLawSearchByKey" target="_blank"><img src="../images/content/SiteMap_Title02_1.gif" alt="법령자료" /></a></li>
							<li><a href="JavaScript:goData()"><img src="../images/content/SiteMap_Title02_3.gif" border="0" alt="기타자료" /></a></li>
					</ul>
			</div>														
	</td>
    <td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title03.gif" alt="마이페이지" /></h1>
							<li><a href="JavaScript:goMypage()"><img src="../images/content/SiteMap_Title03_1.gif" border="0" alt="마이페이지" /></a></li>
					</ul>
			</div>														
	</td>
    <td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title04.gif" alt="공지사항" /></h1>
							<li><a href="JavaScript:goNotice()"><img src="../images/content/SiteMap_Title04_1.gif" border="0" alt="공지사항" /></a></li>
					</ul>
			</div>														
	</td>
    <td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title06.gif" alt="기관담당자" /></h1>
							<li><a href="/switch.do?prefix=/inquire&page=/Inquire.do?method=getInquireOrgList&searchVO.board_type=QNA&searchVO.menu_sn=12"><img src="../images/content/SiteMap_Title06_1.gif" border="0" alt="기관담당자" /></a></li>
							<li><a href="/switch.do?prefix=&page=/discussion.do?method=retrieveDiscussDetail&searchVO.menu_sn=12"><img src="../images/content/SiteMap_Title06_2.gif" border="0" alt="기관담당자" /></a></li>
					</ul>
			</div>														
	</td>
  </tr>
</table>
<!-- end # SiteMap -->
<% } else { %>
<!-- start # SiteMap -->
<table border="0" cellspacing="0" cellpadding="0" class="SiteMap">
  <tr>
    <td class="Img"><img src="../images/content/Content_SImg03.gif" alt="사이트 맵"  /></td>
    <td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title01.gif" alt="상담센터" /></h1>
							<li><a href="JavaScript:goInquireForm()"><img src="../images/content/SiteMap_Title01_1.gif" border="0" alt="온라인상담" /></a></li>
							<li><a href="JavaScript:goFaq()"><img src="../images/content/SiteMap_Title01_2.gif" border="0" alt="Q&A" /></a></li>
							<li><a href="JavaScript:goInquireList()"><img src="../images/content/SiteMap_Title01_3.gif" border="0" alt="자주 묻는 질문" /></a></li>
							</ul>
			</div>														
	</td>
	<td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title07.gif" alt="신문고" /></h1>
							<li><a href="JavaScript:goOffer()"><img src="../images/content/SiteMap_Title07_1.gif" border="0" alt="신문고" /></a></li>
					</ul>
			</div>														
	</td>
    <td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title02.gif" alt="자료실" /></h1>
							<li><a href="http://rndgate.ntis.go.kr/switch.do?prefix=/un/rndLaw&page=/unRndLaw.do?method=retrieveLawSearchByKey" target="_blank"><img src="../images/content/SiteMap_Title02_1.gif" alt="법령자료" /></a></li>
							<li><a href="JavaScript:goData()"><img src="../images/content/SiteMap_Title02_3.gif" border="0" alt="기타자료" /></a></li>
					</ul>
			</div>														
	</td>
    <td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title03.gif" alt="마이페이지" /></h1>
							<li><a href="JavaScript:goMypage()"><img src="../images/content/SiteMap_Title03_1.gif" border="0" alt="마이페이지" /></a></li>
					</ul>
			</div>														
	</td>
    <td>
			<div class="SiteMap-Menu">
					<ul>
					<h1><img src="../images/content/SiteMap_Title04.gif" alt="공지사항" /></h1>
							<li><a href="JavaScript:goNotice()"><img src="../images/content/SiteMap_Title04_1.gif" border="0" alt="공지사항" /></a></li>
					</ul>
			</div>														
	</td>
  </tr>
</table>
<!-- end # SiteMap -->
<% } %>


</div>
<!-- end # LY-Container -->


</div>

<%@  include file="/include/bottom.jsp"%>