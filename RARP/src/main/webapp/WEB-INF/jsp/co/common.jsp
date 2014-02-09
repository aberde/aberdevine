<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java" %> 
<!-- 공통으로 사용되는 taglib 선언 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- 공통 -->
<%
	String baseURL = "http://" + request.getServerName()+ ":" + request.getServerPort() + request.getContextPath(); 

%>
