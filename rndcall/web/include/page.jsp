<!-- /include/tilePage_new.jsp -->

<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/pager-taglib" prefix="pg" %>
<%@page contentType="text/html; charset=utf-8"%>
	<!-- page-area-->
	<div class="page-area mt20">
		
		<logic:greaterThan name="totRowCount" value="<%= maxPageItems %>">	
			<pg:pager
				items="<%= Integer.parseInt(totRowCount.toString()) %>"
				url="<%= path %>"
				isOffset="<%= true %>"
				maxPageItems="<%= Integer.parseInt(maxPageItems) %>"
				maxIndexPages="<%= Integer.parseInt(maxIndexPages) %>"
				export="offset,currentPageNumber=pageNumber"
				scope="request">
				<pg:index>
					<pg:first>
						<span class="first"><a href="javascript:pageSubmit(fm, module, '<%= pageUrl %>')">처음으로</a></span>
					</pg:first>
					<pg:skip pages="<%= -Integer.parseInt(maxIndexPages)+4 %>">
						<span class="prev"><a href="javascript:pageSubmit(fm, module, '<%= pageUrl %>')">이전페이지</a></span>
					</pg:skip>
					<pg:pages>
						<%
						    if (pageNumber == currentPageNumber) {
						%>
						        <a href="#none;" class="active"><%= pageNumber %></a>
						<%
						    } else {
						%>
						        <a href="javascript:pageSubmit(fm, module, '<%= pageUrl %>')"><%= pageNumber %></a>
						<%
						    }
						%>
					</pg:pages>
					<pg:skip pages="<%= Integer.parseInt(maxIndexPages) %>">
						<span class="next"><a href="javascript:pageSubmit(fm, module, '<%= pageUrl %>')">다음페이지</a></span>
					</pg:skip>
					<pg:last>
						<span class="last"><a href="javascript:pageSubmit(fm, module, '<%= pageUrl %>')">마지막으로</a></span>
					</pg:last>
				</pg:index>
			</pg:pager>
		</logic:greaterThan>
		
	</div>
	<!-- //page-area -->
	
<!-- /include/tilePage_new.jsp -->