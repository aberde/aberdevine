<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="/tags/struts-html" prefix="html"%>
<%@taglib uri="/tags/struts-logic" prefix="logic"%>
<%@taglib uri="/tags/struts-bean" prefix="bean"%>
<%@taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@taglib uri="/tags/taglibs-log" prefix="log"%>

<%@page import="kr.go.rndcall.mgnt.common.Util" %>
<%@page import="kr.go.rndcall.mgnt.login.LoginVO" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <link rel="stylesheet" type="text/css" href="/css/common.css" />
    <link rel="stylesheet" type="text/css" href="/css/layout.css" />
    <!--[if lt IE 7]>
    <style media="screen" type="text/css">
    #container {
        height:100%;
    }
    </style>
    <![endif]-->
	<script type="text/javascript" src="/js/prototype.js"></script>
	<script type="text/javascript" src="/js/Field.js"></script>
	<script type="text/javascript" src="/js/EventMenu.js"></script>
	<script type="text/javascript" src="/js/validate.js"></script>
	<script type="text/javascript" src="/js/common.js"></script> 
