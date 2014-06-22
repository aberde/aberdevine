<!-- /include/tilePage_new.jsp -->

<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/pager-taglib" prefix="pg" %>
<%@page contentType="text/html; charset=utf-8"%>
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
							
								<a href="javascript:pageSubmit(fm, module, '<%= pageUrl %>')" class="first"><img src="/images/content/pr_prveFirst.gif" alt="first" title="처음"/></a>
							
						</pg:first>
                    	<pg:skip pages="<%= -Integer.parseInt(maxIndexPages)+4 %>">
							<a href="javascript:pageSubmit(fm, module, '<%= pageUrl %>')"><img src="/images/content/pr_prve.gif" alt="이전" title="이전"/></a>
						</pg:skip>
						<pg:pages>
						<%
						    if (pageNumber == currentPageNumber) {
						%>
						        <strong class='on'><%= pageNumber %></strong>
						<%
						    } else {
						%>
						        <a href="javascript:pageSubmit(fm, module, '<%= pageUrl %>')"><%= pageNumber %></a>
						<%
						    }
						%>
						</pg:pages>
						<pg:skip pages="<%= Integer.parseInt(maxIndexPages) %>">
							<a href="javascript:pageSubmit(fm, module, '<%= pageUrl %>')"><img src="/images/content/pr_next.gif" alt="다음" title="다음"/></a>
						</pg:skip>
						<pg:last>
							<a href="javascript:pageSubmit(fm, module, '<%= pageUrl %>')"><img src="/images/content/pr_nextEnd.gif" alt="마지막" title="마지막"/></a>
						</pg:last>
                    </pg:index>                    
                </pg:pager>
	</logic:greaterThan>
<!-- /include/tilePage_new.jsp -->