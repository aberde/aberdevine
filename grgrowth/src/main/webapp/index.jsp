<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" session="false"%>

<%@ include file="/WEB-INF/jsp/egovframework/include/header.jsp" %>

<div id="home_container">

	<div class="home_left">
		<img src="<c:url value="/images/main/home_l_img01.gif" />" alt="녹색성장 더 큰 대한민국" />
		<div id="search" style="margin-top: 15px;">
			<%-- <?php get_search_form(); ?> --%>
		</div>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<a href="<c:url value="" />"><img style="margin-left: 5px" src="<c:url value="/images/main/home_l_img02.gif" />" alt="자료실" /></a>
	</div>
	<div class="home_center">
		<p class="home_c_main">
			<img src="<c:url value="/images/main/home_c_main.gif" />" alt="Geen KOREA 2013" />
		</p>
		<div class="news">
			<img src="<c:url value="/images/main/home_c_title.gif" />" alt="뉴스" />
			<ul>
				<!-- Tab 2 -->
				<div id="priBotTab2" class="primaryBottomTabs_body">
					
					<%-- 
					<?php if ( ! dynamic_sidebar( 'PrimaryBottomTab2' ) ) : ?>

					<?php 
							$args = array(
							  'post__not_in'=>$do_not_duplicate,
							  'showposts' => 5,
							  //'cat' => get_option('of_nw_cat_name3')//산업
							  'cat' => get_option('of_nw_cat_name2')//정부
							);						
							$gab_query = new WP_Query();$gab_query->query($args); 
							?>
					<ul>
						<?php
							while ($gab_query->have_posts()) : $gab_query->the_post();
							?>
						<li><span class="tabTitle"><img
								src="<?php bloginfo('template_url'); ?>/images/main/bullet_arrow.gif"
								align="absmiddle" border="0" alt="" />&nbsp;<a
								href="<?php /* Do not use rel=bookmark for titles of tab content, that will break tabbing */ the_permalink() ?>"
								title="<?php printf( esc_attr__( 'Permalink to %s', 'newspro' ), the_title_attribute( 'echo=0' ) ); ?>">
									<?php echo cut_str(get_the_title(), 55); ?>
							</a></span></li>
						<!-- /text -->

						<?php endwhile; wp_reset_query(); ?>
					</ul>
					<?php endif;  if(get_option('of_nw_widget') == 'true') { echo '<span class="widgetname">PrimaryBottomTab1</span>'; } ?>
					
					--%>
					
				</div>
				<!-- /priBotTab2 -->
			</ul>

		</div>
	</div>

	<div class="home_right">
		<div class="home_r_bn">
			<ul>
				<li class="bg">
					<a href="<c:url value="" />" class="go" title="위원회 소개가기"><img src="<c:url value="/images/main/go.png" />" alt="go" /></a>
				</li>
				<li class="bg">
					<a href="<c:url value="" />" class="go" title="녹색법령가기"><img src="<c:url value="/images/main/go.png" />" alt="go" /></a>
				</li>
				<li class="bg2">
					<a href="<c:url value="" />" class="go" title="위원회 활동"><img src="<c:url value="/images/main/go.png" />" alt="go" /></a>
				</li>
				<li class="bg2">
					<a href="<c:url value="" />" class="go" title="국가계획"><img src="<c:url value="/images/main/go.png" />" alt="go" /></a>
				</li>
			</ul>
		</div>

		<div class="home_r_weather">
			<div class="weather_tit">
				<img src="<c:url value="/images/main/home_r_title01.gif" />" alt="날씨" />
			</div>
			<div style="color: #d3d3d3;">
				<iframe src="<?php echo bloginfo('template_url'); ?>/weatherTicker.php" name="myframe" width="300px" height="18px" marginwidth="0" marginheight="0" frameborder="no" scrolling="no"></iframe>
			</div>
		</div>

		<div class="home_r_bn2">
			<a href="http://www.greengrowth.go.kr/wp-content/uploads/2014/12/green-eng-bro.pdf" target="_blank"><img src="<c:url value="/images/main/e-book_22.jpg" />" alt="녹색성장 더 큰 대한민국" /></a>
		</div>

	</div>

</div>

<%@ include file="/WEB-INF/jsp/egovframework/include/footer.jsp" %>