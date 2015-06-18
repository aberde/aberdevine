<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" session="false"%>

<%@ include file="/WEB-INF/jsp/egovframework/include/header.jsp" %>

<div id="sectionWrap">

	<div id="sectionTab">
		<div class="sectionTab<c:out value="${ empty searchCondition ? 'On' : 'Off' }" />" id="searchTotal" onclick="location.href='<c:url value="/main/search.do" />?searchKeyword=<c:out value="${ searchKeyword }" />';">
			<div class="imgTag"><span>통합검색</span></div>
			<div class="arrowTag"></div>
		</div>
		<div class="sectionTab<c:out value="${ searchCondition eq 'policy' ? 'On' : 'Off' }" />" id="searchPolicy" onclick="location.href='<c:url value="/main/search.do" />?searchKeyword=<c:out value="${ searchKeyword }" />&searchCondition=policy';">
			<div class="imgTag"><span>관련정책</span></div>
			<div class="arrowTag"></div>
		</div>
		<div class="sectionTab<c:out value="${ searchCondition eq 'news' ? 'On' : 'Off' }" />" id="searchNews" onclick="location.href='<c:url value="/main/search.do" />?searchKeyword=<c:out value="${ searchKeyword }" />&searchCondition=news';">
			<div class="imgTag"><span>소식/동향</span></div>
			<div class="arrowTag"></div>
		</div>
		<div class="sectionTab<c:out value="${ searchCondition eq 'multimedia' ? 'On' : 'Off' }" />" id="searchMultimedia" onclick="location.href='<c:url value="/main/search.do" />?searchKeyword=<c:out value="${ searchKeyword }" />&searchCondition=multimedia';">
			<div class="imgTag"><span>멀티미디어</span></div>
			<div class="arrowTag"></div>
		</div>
		<div class="sectionTab<c:out value="${ searchCondition eq 'edudata' ? 'On' : 'Off' }" />" id="searchEdudata" onclick="location.href='<c:url value="/main/search.do" />?searchKeyword=<c:out value="${ searchKeyword }" />&searchCondition=edudata';">
			<div class="imgTag"><span>교육/자료실</span></div>
			<div class="arrowTag"></div>
		</div>
		<div class="sectionTab<c:out value="${ searchCondition eq 'page' ? 'On' : 'Off' }" />" id="searchPage" onclick="location.href='<c:url value="/main/search.do" />?searchKeyword=<c:out value="${ searchKeyword }" />&searchCondition=page';">
			<div class="imgTag"><span>관련문서</span></div>
			<div class="arrowTag"></div>
		</div>
		<div class="sectionTab<c:out value="${ searchCondition eq 'participation' ? 'On' : 'Off' }" />" id="searchParticipation" onclick="location.href='<c:url value="/main/search.do" />?searchKeyword=<c:out value="${ searchKeyword }" />&searchCondition=participation';">
			<div class="imgTag"><span>참여마당</span></div>
			<div class="arrowTag"></div>
		</div>
	</div>

	<div id="sectionContainer">
		<div class="sectionTabTitle">
			?php if($post_category_img): ?><img src="<?php bloginfo('template_url'); ?>/images/search/<?php echo $post_category_img; ?>" border="0" alt="<?php esc_attr_e('소식/동향'); ?>" /> <? ;endif ?>
		</div>
	

		<?php 
	$post_category = '';
	$post_category_img = '';

	if ($_GET['section'])
	{
		$posts_row = 20;
		switch ($_GET['section'])
		{
			case 'news': $post_category = '5'; $post_type = 'post'; $post_category_img='section_news.gif'; include (TEMPLATEPATH . '/archive-news.php'); // 소식동향 
				break;
			case 'multimedia': include (TEMPLATEPATH . '/archive-multimedia.php'); // 멀티미디어
				break;
			case 'edudata': $post_category = '15,32,16,17'; $post_type = 'post'; $post_category_img='section_edudata.gif'; include (TEMPLATEPATH . '/archive-news.php'); // 교육/자료실
				break;
			case 'participation': $post_category = '23,25,26,27,28'; $post_type = 'post'; $post_category_img='section_participation.gif'; include (TEMPLATEPATH . '/archive-news.php'); // 참여마당
				break;
			case 'policy': $post_category = '41'; $post_type = 'post'; $post_category_img='section_policy.gif'; include (TEMPLATEPATH . '/archive-news.php'); // 관련정책
				break;
			case 'page': $post_category = ''; $post_type = 'page'; $post_category_img='section_page.gif'; include (TEMPLATEPATH . '/archive-news.php'); // 관련문서
				break;
		}
	}
	else 
	{
		$posts_row = 5;

		$post_category = '41';
		$post_category_img='section_policy.gif'; 
		$section = 'policy';
		$post_type = 'post'; 
		include (TEMPLATEPATH . '/archive-news.php'); // 관련정책

		$post_category = '5';
		$post_category_img='section_news.gif';
		$section = 'news';
		$post_type = 'post'; 
		include (TEMPLATEPATH . '/archive-news.php'); // 소식동향 

		include (TEMPLATEPATH . '/archive-multimedia.php'); // 멀티미디어
		
		$post_category = '15,32,16,17';
		$post_category_img='section_edudata.gif';
		$section = 'edudata';
		$post_type = 'post'; 
		include (TEMPLATEPATH . '/archive-news.php'); // 교육/자료실

		$post_category = '';
		$post_type = 'page';
		$post_category_img='section_page.gif'; 
		$section = 'page';
		include (TEMPLATEPATH . '/archive-news.php'); // 관련문서

		$post_category = '23,25,26,27,28';
		$post_category_img='section_participation.gif'; 
		$section = 'participation';
		$post_type = 'post'; 
		include (TEMPLATEPATH . '/archive-news.php'); // 참여마당

	}
?>
	</div>
	
</div>

<%@ include file="/WEB-INF/jsp/egovframework/include/footer.jsp"%>