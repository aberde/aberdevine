<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html dir="ltr" lang="en-US">
<head>
	<meta charset="UTF-8" />
	<title>녹색성장위원회</title>
	<style>
		/* news ticker */
		#newsTicker {float: left; width: 100%; height: 30px; position: absolute; top: 250px; left: 0px; background: #fff;}
		.newsTickerData1 {float: left; border-right: 1px solid #dbdbdb; width: 295px; height: 24px; padding: 5px 0 0 15px; margin: 2px 0;}
		.newsTickerData2 {float: left; height: 24px; padding: 3px 0 0 15px; margin: 2px 0;}
		.newsTickerData2Wrap {float: left; padding: 2px 0 0 0px; border-top: 1px solid #dbdbdb; width: 317px;}
		.newsTickerTxt1 {background-color: #a1d243; border: 1px solid #86c30f; color: #fff; padding-top: 2px; font-size: 12px; width: 35px; height: 17px; float: left; text-align: center;}
		.newsTickerTxt11 {background-color: #a1d243; border: 1px solid #86c30f; color: #fff; padding-top: 2px; font-size: 12px; width: 80px; height: 17px; float: left; text-align: center;}
		.newsTickerTxt2 {color: #666; font-size: 12px; font-weight: bold;}
		.newsTickerTxt3 {color: #666; font-size: 12px; margin: 3px 0px 0px 5px;}
		.newsticker-jcarousellite {float: left; width: 240px;}
		.newsticker-jcarousellite .newsinfo {margin: 4px 0px 0px 5px;}
		.newsticker-jcarousellite ul li {list-style: none; display: block; padding-bottom: 1px; margin-bottom: 5px;}
		.newsticker-jcarousellite .info {float: right; width: 220px; display: block;}
	</style>
</head>

<body style="margin: 0;">
	<marquee scrollamount="2" onmouseover="TICKER_PAUSED=true" onmouseout="TICKER_PAUSED=false">
		
		<c:forEach var="vo" items="${ weatherList }">
			<span class="newsTickerTxt2"><c:out value="${ vo.local }" /></span>
			<span class="newsTickerTxt3">현재기온:<c:out value="${ vo.ta }" />℃ &nbsp; <c:out value="${ vo.desc }" /> &nbsp;&nbsp;&nbsp;</span>
		</c:forEach>

	</marquee>
</body>
</html>