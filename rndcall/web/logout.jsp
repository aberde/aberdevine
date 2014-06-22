<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
    if(session != null && session.getAttribute("loginUserVO") != null) { 
    	session.removeAttribute("loginUserVO");
    	session.invalidate();  
	}
   	response.sendRedirect("/index.jsp");
%>