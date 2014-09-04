<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="/tags/struts-html" prefix="html"%>
<%@taglib uri="/tags/struts-logic" prefix="logic"%>
<%@taglib uri="/tags/struts-bean" prefix="bean"%>
<%@taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@taglib uri="/tags/taglibs-log" prefix="log"%>

<%@page import="kr.go.rndcall.mgnt.common.Util" %>
<%@page import="kr.go.rndcall.mgnt.login.LoginVO" %>
<%@page import="java.util.Date"%>
<%@page import="kr.go.rndcall.mgnt.inquire.form.InquireForm"%>
<%@page contentType="text/html; charset=utf-8" %>
<%@page import="kr.go.rndcall.mgnt.inquire.vo.*" %>
<%
    response.setContentType("application/msword");
    response.setHeader("Content-Disposition", "attachment; filename=" + (new Date()).getTime() + ".doc");
%>
<%
  boolean mainIsLogin =false;
  LoginVO mainLoginVO = (LoginVO) session.getAttribute("loginUserVO");
  if(mainLoginVO != null && mainLoginVO.getLogin_id()!=null && !mainLoginVO.getLogin_id().equals("guest")) {
      mainIsLogin = true;
  }

  String mainRoleCD = "guest";
  String nameKO = "";
  String login_id = "";
  String menu_sn = "";
  
  menu_sn = (String) request.getParameter("searchVO.menu_sn");
  if(menu_sn =="" || menu_sn==null || menu_sn == "null") menu_sn = (String)request.getAttribute("searchVO.menu_sn");
  
  System.out.println(" top 페이지 menu_sn::"+menu_sn);

  if (mainLoginVO != null && mainIsLogin) { 
      mainRoleCD = mainLoginVO.getRoleCD();
      nameKO = mainLoginVO.getName();
      login_id = mainLoginVO.getLogin_id();
  }
%>

    <bean:define name="InquireForm" property="searchVO" id="searchVO" type="kr.go.rndcall.mgnt.inquire.vo.InquireSearchVO"/>
    <bean:define name="InquireForm" property="vo" id="vo" type="kr.go.rndcall.mgnt.inquire.vo.InquireVO"/>
    <bean:define name="InquireForm" property="pagerOffset" id="pagerOffset" type="java.lang.String"/>
    <bean:define name="InquireForm" property="voList" id="file_list" type="java.util.ArrayList"/>
    <bean:define name="InquireForm" property="voList2" id="cateL" type="java.util.ArrayList"/>

    <bean:define id="path" type="java.lang.String" value="/Inquire.do"/>

<%
    String uni = searchVO.getUni();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <style type="text/css">
    @charset "utf-8";
/* HTML 리셋 */
/* *{margin:0;padding:0;line-height:160%;color:#666;font-size:12px}  * 은 전체 적용시 주석 제거  */
body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,a,button,form,fieldset,p,blockquote{margin:0;padding:0;}
html {overflow-y:scroll;}
html, body, div, span, applet, object, iframe,h1, h2, h3, h4, h5, h6, p, blockquote, pre,a, abbr, acronym, address, big, cite, code,del, dfn, em, img, ins, kbd, q, s, samp,
small, strike, strong, sub, sup, tt, var,b, u, i, center,dl, dt, dd, ol, ul, li,form, label, legend,table, caption, tbody, tfoot, thead, tr, th, td,article, aside, canvas, details, embed, 
figure, figcaption, footer, header, hgroup, menu, nav, output, ruby, section, summary,time, mark, audio, video {font-family:'Nanum Gothic','나눔 고딕', 돋움, Dotum, 굴림, Gulim, AppleGothic, Sans-serif;}

img, fieldset {border:0;}
body, dl, dt, dd, th, td, div, p, a, button, table,li {color:#000;font-size:13px; }
form, fieldset,legend, input, button, textarea, select {color:#000;font-size:13px;}
ul, ol {list-style:none;}
table {border-collapse:collapse; width:100%}
input, textarea {display:inline-block;font-size:13px;vertical-align:middle;}
textarea {resize:none;}
address, em {font-style:normal;}
th, td {padding:0;vertical-align:top;text-align:left;font-weight:normal;}
a {color:#000; text-decoration:none;}
a:hover,a:focus,a:active {cursor:pointer !important; color:#8a8a8a; text-decoration:none;}
blockquote, p {margin:0;}

/* 테이블 리셋 */
caption {height:0;overflow:hidden;font-size:0;line-height:0;text-indent:-20000px;}

/* 헤딩 테그 리셋 */
h1, h2, h3, h4, h5, h6 {font-size:1.000em; color:#262626; }

/* inline */
img {display:inline-block;border:0 none;vertical-align:top;}
a, cite, code, dfn, del, em, ins, label, q, span, strong, input, select {display:inline-block; vertical-align:middle;}
cite, em, dfn {font-style:normal;}
hr {display:none;}
sub {vertical-align:bottom;font-family:Arial;}
sup {vertical-align:top;font-family:Arial;}
/* label {*vertical-align:middle;}  radio, checkbox 뒤에 label이 있을 경우 비틀어져 있을경우 주석 제거 */
div, li, p { line-height:18px; color:#767676;}
input[type="radio"],
input[type="checkbox"] {margin:0;padding:0;}

/* Form Element */
input[type="text"]{border:1px solid #cbcbcb; font-weight:normal; font-size:12px; color:#24292c;padding:2px 5px 2px 5px;} 
input[type="password"]{border:1px solid #e5e5e5; font-weight:normal; font-size:12px; color:#24292c;padding:2px 5px 2px 5px; background-color:#f9f9f9;} 
input[type="radio"]{margin-left:10px; background:#fff;}
textarea{padding:15px 9px; overflow:auto; border:1px solid #e5e5e5; background-color:#f9f9f9; font-size:12px;}
select{border:1px solid #cbcbcb; font-weight:normal; font-size:12px; color:#24292c; line-height:12px; padding:1px 1px 1px 5px;}

/* 스킵네비 */
.skip {position:absolute;top:0;left:0;width:100%;height:0;line-height:0; z-index:9999;}
.skip a {display:block;overflow:hidden;height:1px;width:1px;margin:0 -1px -1px 0;padding:0;font-size:0;line-height:0}
.skip a:hover,
.skip a:active,
.skip a:focus{position:absolute;top:0;left:0;width:100%;height:19px;line-height:100%;margin:0;padding:5px 0 0 0; border:1px solid #54a51b !important; background:#63c31e; font-weight:bold;font-size:14px;color:#ffffff;text-align:center;} 

/* font 사이즈  */
.small-txt{font-size:12px; color:#24292c; letter-spacing:-0.5px;}

/* 플롯헤제 */
.clearfix {*zoom:1;} /* IE5.5~7 브라우저 대응 Hack */
.clearfix:before,
.clearfix:after{display:block;clear:both;content:"";} /* 표준브라우저 */

/* float 관련 */
.fl {float:left !important;}
.fr {float:right !important;}

/* 정렬 */
.txt-c {text-align:center !important;}
.txt-l {text-align:left !important;}
.txt-r {text-align:right !important;}

/* 백그라운드, 디스플레이 none 처리, hidden 처리 */
.bg-n {background-image:none !important; padding-left:0;}
.dis-n {display:none;}
.hidden {border:1px solid red; font-size:0; width:0; height:0; text-indent:-9999px; padding:0; margin:0;visibility:hidden;overflow:hidden;}

/* font color */
.txt-red{color:#ff0000; font-size:12px;}
.txt-em{color:#4393c6 !important; font-weight:bold; }

/* 여백 마진, 페딩 필요시 추가 */
.mt0 {margin-top:0px !important;}
.mt5 {margin-top:5px !important;}
.mt10 {margin-top:10px !important;}
.mt15 {margin-top:15px !important;}
.mt20 {margin-top:20px !important;}
.mt25 {margin-top:25px !important;}
.mt30 {margin-top:30px !important;}
.mt40 {margin-top:40px !important;}
.mt50 {margin-top:50px !important;}
.mt60 {margin-top:60px !important;}
.mt70 {margin-top:70px !important;}

.mb0 {margin-bottom:0px !important;}
.mb10 {margin-bottom:10px !important;}
.mb20 {margin-bottom:20px !important;}
.mb30 {margin-bottom:30px !important;}
.mb40 {margin-bottom:40px !important;}

.ml10 {margin-left:10px !important;}
.ml20 {margin-left:20px !important;}
.ml50 {margin-left:50px !important;}
.ml70 {margin-left:70px !important;}
.mr0 {margin-right:0px !important;}
.mr10 {margin-right:10px !important;}

.pt10 {padding-top:10px !important;}
.pb10 {padding-bottom:10px !important;}
.pl5 {padding-left:5px !important;}
.pr10 {padding-right:10px !important;}


/* btn-set */
.btn-area {margin-top:50px;}

.btn-set {display:inline-block; background: url(../img/btn/btn_set.gif) 100% 0 ; padding-right:17px;}
.btn-set a {display:inline-block;  background: url(../img/btn/btn_set.gif) 0 0; color:#fff; padding-left:18px; line-height:31px; font-size:12px; font-weight:bold;}
.btn-set.pink {background-position:100% -31px;}
.btn-set.pink a {background-position:0 -31px;}
.btn-set.gray {background-position:100% -62px;}
.btn-set.gray a {background-position:0 -62px;}
.btn-set.green {background-position:100% -93px;}
.btn-set.green a {background-position:0 -93px;}



.btn-set.sky {background-position:100% -124px; padding-right:35px}
.btn-set.sky a {background-position:0 -124px; font-size:12px; padding-left:30px; line-height:36px }

.btn-set.login {background-position:100% -447px; padding-right:10px}
.btn-set.login a {background-position:0 -447px; font-size:24px; padding-left:20px; line-height:66px }

.btn-set.red {background-position:100% -520px; padding-right:35px}
.btn-set.red a {background-position:0 -520px; font-size:14px; padding-left:30px; line-height:36px }

.btn-set.set2 {padding-right:12px}
.btn-set.set2 a {padding-left:12px; font-size:11px; line-height:21px }
.btn-set.set2.s-blue {background-position:100% -165px;}
.btn-set.set2.s-blue a {background-position:0 -165px;}
.btn-set.set2.navy {background-position:100% -191px;}
.btn-set.set2.navy a{background-position:0 -191px;}
.btn-set.set2.black {background-position:100% -217px;}
.btn-set.set2.black a{background-position:0 -217px;}
.btn-set.set2.green {background-position:100% -243px;}
.btn-set.set2.green a{background-position:0 -243px;}
.btn-set.set3 {padding-right:12px}
.btn-set.set3 a {padding-left:12px; font-size:11px; line-height:20px}
.btn-set.set3.pink {background-position:100% -355px;}
.btn-set.set3.pink a{background-position:0 -355px;}
.btn-set.set3.blue {background-position:100% -378px;}
.btn-set.set3.blue a{background-position:0 -378px;}

.btn-set.set4{padding:0; text-align:center; color:#fff; font-size:11px; font-weight:bold; line-height:19px; width:59px; height:19px;}
.btn-set.set4.yellow {background: url(../img/btn/bg_yellow.gif) 0 0;}
.btn-set.set4.gray {background: url(../img/btn/bg_gray.gif) 0 0; }
.btn-set.set4.green {background: url(../img/btn/bg_green.gif) 0 0; }

/* 버튼 아이콘 셋 */
.btn-set .bullet {background: url(../img/btn/btn_icon.png) 0 0; width:8px; height:15px; margin-left:9px; text-indent:-9999px;}
.btn-set .zoom {background: url(../img/btn/btn_icon.png) 0 -20px; width:10px; height:12px; margin-left:5px; text-indent:-9999px;} 
.btn-set.save {background: url(../img/btn/btn_icon.png) 0 -37px; width:16px; height:16px; padding:0;} 
.btn-set.save a {display:block; background: url(../img/btn/btn_icon.png) 0 -37px; width:16px; height:16px; text-indent:-9999px; }

/* 아이콘셋 */
.icon{margin-right:10px; background: url(../img/btn/bg_pink.gif) 0 0;  text-align:center; color:#fff; font-size:11px; font-weight:bold; width:35px; height:18px;}
.icon.icon01{background: url(../img/btn/bg_blue.gif) 0 0;}
.icon.icon02{background: url(../img/btn/bg_gray01.gif) 0 0;}
.icon.icon03{background: url(../img/btn/bg_yellow01.gif) 0 0;}

/* page-area */
.page-area {text-align:center;}
.page-area span { width:23px; height:23px; background: url(../img/btn/btn_icon.png) 0 0;}
.page-area span a{ display:block; text-indent:-9999px;  border:1px solid #e2e2e2; line-height:23px;}
.page-area span a:hover {background:none; border:1px solid #e2e2e2; width:23px; height:23px;}
.page-area .first{background-position:0 -57px;}
.page-area .prev{background-position:-24px -57px;}
.page-area .next{background-position:0 -81px;}
.page-area .last {background-position:-24px -81px;}
.page-area a {display:inline-block;  vertical-align:top; border:1px solid #e2e2e2; width:23px; height:23px; line-height:24px; text-align:center; font-size:10px;}
.page-area a:hover {border:none; background:#666666; color:#fff; width:25px; height:25px;}
.page-area a.active {border:none; background:#666666; color:#fff; width:25px; height:25px;}

/* search-box */
.search-box{margin-top:30px; padding:11px 10px 11px 18px; border-radius:3px ; border:1px solid #dfdcdc; background:#f1f0f0;}
.search-box .search-form{width:322px; height:30px;background-color:#fff;}
.search-box .search-form select{height:20px;border:none;}
.search-box .search-form input{width:190px; height:25px;border:none;}
.search-box .btn-search img{display:block; padding-right:5px; ; vertical-align: middle;}

/* board-type01 */
.board-type01 .list-info{margin-bottom:10px; line-height:14px; }
.board-type01 .board-box{border-top:2px solid #474747; border-bottom:1px solid #474747;}
.board-type01 .list-info span{color:#0e6eb7; vertical-align: top}
.board-type01 table{border-collapse:collapse;}
.board-type01 thead tr:first-child th {border-top:0;}
.board-type01 thead th {border-left:1px solid #e2e2e2; border-top:1px solid #eeeeee; background-color:#f1f0f0; color:#474747;  font-weight:bold; text-align:center; padding:12px 6px 12px; }
.board-type01 tbody td {border-left:1px solid #e2e2e2; border-top:1px solid #eeeeee; padding:9px 5px; color:#474747; font-size:11px; text-align:center;  vertical-align:middle;}
.board-type01 thead th:first-child, .board-type01 tbody td:first-child{border-left:0;}
.board-type01 tbody tr.on{background-color:#fafafa;}
.board-type01 tbody td a{padding-left:15px; font-size:11px;}
.board-type01 tbody td a.lock{padding-left:20px; background:url(../img/icon/icon_lock.png) 5px 2px no-repeat;}
.board-type01 tfoot td{border-left:1px solid #e2e2e2;border-top:1px solid #e2e2e2;padding:9px 5px; color:#474747; font-weight:bold;text-align:center;  vertical-align:middle; }
.board-type01 tfoot td:first-child{border-left:0;}

/* board-write*/
.board-write .board-box{border-top:2px solid #474747; border-bottom:1px solid #474747;}
.board-write th{padding:7px 0 8px 26px;border-bottom:1px solid #eeeeee;font-size:12px; font-weight:bold;color:#474747; vertical-align:top; background:#fafafa url(../img/sub/bullet_rect.jpg) 19px 14px no-repeat;}
.board-write td{padding:7px 8px;border:1px solid #e2e2e2; border-right:none;}
.board-write input[type="text"]{border:1px solid #e5e5e5;background-color:#f9f9f9;}
.board-write td .pop-bx{height:130px; overflow-y:scroll;}
.board-write td .pw-bx{padding:40px 0; font-size:13px; line-height:19px; color:#666;}
.board-write td .pw-bx strong{color:#333;}

.board-write .explain{padding-left:10px;font-size:11px;font-weight:normal; }
/* board-detail*/
.board-detail .board-box{border-top:2px solid #474747; border-bottom:1px solid #474747;}
.board-detail  th{padding:7px 0 8px 26px;border-bottom:1px solid #eeeeee;font-size:12px; font-weight:bold;color:#474747; vertical-align:top; letter-spacing:-2px;background:#fafafa url(../img/sub/bullet_rect.jpg) 19px 14px no-repeat;}
.board-detail td{padding:7px 8px;border:1px solid #e2e2e2; border-right:none;}
.board-detail input[type="text"]{border:1px solid #e5e5e5;background-color:#f9f9f9;}
.board-detail .comment p{min-height:130px;}
.board-detail h3{margin-bottom:15px;background: url("../img/common/bullet.gif") no-repeat scroll left center rgba(0, 0, 0, 0); color: #171717; font-size: 16px; font-weight: bold; line-height: 15px; padding-left: 15px; }




/* color */
.txt-blue{color:#4393c6 !important;}

/* btn-position */
.btn-lst{margin-top:24px;}

/* tbl-type01 */
.tbl-type01 {border-top:2px solid #474747; border-bottom:2px solid #474747;}
.tbl-type01 thead tr th:first-child {border-left:0}
.tbl-type01 thead tr th {text-align:center; vertical-align:middle; font-size:14px;  color:#3a7fa3; background:#eef9fc; font-weight:bold; border-bottom:1px solid #eeeeee;  padding:10px 0 13px; border-left:1px solid #e2e2e2;}
.tbl-type01 thead tr.on th {background:#f6fdff; color:#6caccd; font-size:14px; }
.tbl-type01 tbody tr td:first-child {border-left:0;}
.tbl-type01 tbody tr td {vertical-align:middle; padding:8px 10px 10px; font-size:14px; color:#474747; border-top:1px solid #eeeeee; border-left:1px solid #e2e2e2; letter-spacing:-1px;}
.tbl-type01 tbody tr td a{font-size:14px; color:#2279a6;}
.tbl-type01 tbody tr.on td, 
.tbl-type01 tbody tr td.on{background:#fafafa}
.tbl-type01 tbody tr:first-child td,
.tbl-type01 tbody tr:first-child th {border-top:0;}
.tbl-type01 tbody tr th {text-align:center; vertical-align:middle; font-size:14px; color:#3a7fa3; background:#eef9fc; font-weight:bold; text-align:center; border-top:1px solid #eeeeee;}
.tbl-type01 tbody tr td.row-line{border-left:1px solid #e2e2e2;}
.tbl-type01 .bg-butterfly{position:absolute; bottom:2px; right:19px; width:354px;height:366px; background:url(../img/sub/bg_butterfly.png) 0 0 no-repeat;}

.tbl-type01 tbody td .small{font-size:12px;}
/* blullet */
.bullet{padding-left:15px; background: url(../img/common/bullet.gif) no-repeat left center; color:#171717; font-size:16px; font-weight:bold; line-height:15px;}

/* lst-type */
.lst-type li{padding-left:15px; background: url(../img/sub/bullet_rect.jpg) no-repeat left 8px; color:#6c6c6c; font-size:11px;}

/*line*/
.line-l {border-left:1px solid #e2e2e2 !important; }

/* 설명 박스 */
.explain-bx{padding:15px 22px; border:1px solid #fad57b;}
.explain-bx strong{font-size:14px;line-height:22px;font-weight:bold; color:#595959;}
.explain-bx p{font-size:14px; color:#595959;}

@charset "utf-8";
html,body {height:100%;}

#wrap{position:relative; border-top:5px solid #3f77d5; width:100%; min-height:100%;}

/* header */
#header{width:100%;}
#header .head{position:relative; margin:-5px auto; padding:20px 0 15px; overflow:hidden; background: url(../img/bg/bg_haeder.gif) no-repeat left top; width:990px; height:45px;}
.head h1{position:absolute; left:30px; top:20px;}
.head .nav-bx{float:right;}
.head .nav-bx .nav-lst {margin:3px 3px 0 0;}
.head .nav-bx .nav-lst li:first-child{background:none;}
.head .nav-bx .nav-lst li{float:left; padding:0 9px; background: url(../img/common/nav_line.gif) no-repeat left 6px;}
.head .nav-bx .nav-lst li a{font-weight:bold; font-size:12px; color:#1d1d1d;}
.head .nav-bx .nav-lst li.user strong{font-size:12px; color:#1d1d1d;}
.head .nav-bx .nav-lst li.user {padding-left:15px; background: url(../img/common/top_user_icon.gif) 0 3px no-repeat;}
.head .nav-bx .search-bx{padding:2px 7px 5px 12px; border:2px solid #7c7c7c; width:220px;}
.head .nav-bx .search-bx label{margin-right:15px; color:#000; font-size:12px;}
.head .nav-bx .search-bx input{border:0;margin:0; color:#b0b0b0; font-size:12px; width:120px;}
.head .nav-bx .search-bx .search-btn{font-size:0; line-height:0; color:#fff; text-indent:-9999px; background: url(../img/common/icon_search.gif) no-repeat left; width:13px; height:16px;}

/* gnb */
.gnb{position:relative; z-index:949;width:100%; background:#fff url(../img/bg/nav_bg.gif) 0 bottom repeat-x;}
.gnb .gnbContainer{margin:0 auto; width:980px; height:43px;}
.gnb ul li{float:left;  height:39px;}
.gnb ul li a{padding:12px 24px; font-weight:bold; font-size:16px; color:#474747;}
.gnb ul li.on a, 
.gnb ul li a:hover{color:#388bd3;}
.gnb ul li>div{position:absolute; top:43px; left:0; width:100%; min-width:980px; height:39px; display:none; background-color:#fff; border-bottom:1px solid #bcbcbc;}
.gnb ul li>div>.snb-bx>ul{position:absolute; top:-2px;}
.gnb ul li>div>.snb-bx>ul>li{float:left; border:none;  background:url(../img/bg/nav_line.gif) left center no-repeat;}
.gnb ul li>div>.snb-bx>ul>li a{font-weight:bold; font-size:13px; color:#666666 !important; padding:10px 15px !important;}
.gnb ul li>div>.snb-bx>ul>li:first-child {border:none; background:none;}
.gnb ul li>div>.snb-bx>ul>li.on a, 
.gnb ul li>div>.snb-bx>ul>li a:hover{color:#d23962 !important;}

/* all-menu */
.all-menu a{padding-left:20px; margin-top:10px; color:#000; font-weight:bold; font-size:14px; background: url(../img/common/icon_plus.gif) no-repeat left center;}

/* container */
#container{position:relative; margin:0 auto; padding:0 0 210px 10px; width:980px;}

/* lnb */
.lnb{position:absolute; left:10px; top:40px;}
.lnb .tit-area{padding-left:20px; margin-bottom:22px;}
.lnb .tit-area h2{font-size:35px; line-height:35px; font-weight:normal; color:#d23962; letter-spacing:-2px;}
.lnb .tit-area h2.develop-lnb{font-size:28px; line-height:28px;}
.lnb .tit-area span{margin:7px 0 0 5px;}
.lnb-lst li:first-child{border-top:1px solid #d9d9d9;}
.lnb-lst li{border-bottom:1px solid #d9d9d9; width:199px; height:31px;}
.lnb-lst li a{padding-left:33px; display:block; color:#262626; font-size:13px; font-weight:bold; letter-spacing:-1px; line-height:33px; width:166px; height:33px;}
.lnb-lst li.on a{color:#fff; background:url(../img/common/lnb_bg.gif) no-repeat;}



#container.development h2 {color:#959191;}
#container.advice h2 {color:#d23962;}
#container.sinmungo h2 {color:#6095c3;}
#container.notice h2 {color:#aaca5c;}
#container.dataroom h2{color:#e8c968;}
#container.center h2{color:#2a4266;}

#container.development .lnb-lst li.on a {background:url(../img/common/lnb_bg_development.gif) no-repeat;}
#container.advice .lnb-lst li.on a {background:url(../img/common/lnb_bg_advice.gif) no-repeat;}
#container.sinmungo .lnb-lst li.on a {background:url(../img/common/lnb_bg_sinmungo.gif) no-repeat;}
#container.notice .lnb-lst li.on a {background:url(../img/common/lnb_bg_notice.gif) no-repeat;}
#container.dataroom .lnb-lst li.on a {background:url(../img/common/lnb_bg_data.gif) no-repeat;}
#container.center .lnb-lst li.on a {background:url(../img/common/lnb_bg_center.gif) no-repeat;}



.content{position:relative; margin-left:220px; padding-top:20px; width:760px;}



/* location */
.location li:first-child{padding-left:0; background:none; vertical-align:bottom;}
.location li:first-child a{vertical-align:bottom;}
.location li{float:left; padding-left:10px; margin-right:6px; background:url(../img/common/location_arrow.gif) no-repeat left 5px; height:15px;}
.location li a{color:#595959; font-size:12px; font-weight:bold;vertical-align:top;}
.location li.on a{color:#d23962;}

/* section  */
.section {padding-right:20px;}
.section .tit-area{margin-top:30px; padding-bottom:15px;}

.section .tit-area h3{margin-bottom:5px; padding-left:15px; font-size:25px; color:#262626; letter-spacing:-1px; line-height:25px; background:url(../img/common/h3_line.gif) no-repeat left;}
#container.development .section .tit-area h3 {background:url(../img/common/h3_line_development.gif) no-repeat left;}
#container.advice .section .tit-area h3 {background:url(../img/common/h3_line_advice.gif) no-repeat left;}
#container.sinmungo .section .tit-area h3 {background:url(../img/common/h3_line_sinmungo.gif) no-repeat left}
#container.notice .section .tit-area h3 {background:url(../img/common/h3_line_notice.gif) no-repeat left;}
#container.dataroom .section .tit-area h3 {background:url(../img/common/h3_line_dataroom.gif) no-repeat left}
#container.center .section .tit-area h3 {background:url(../img/common/h3_line_center.gif) no-repeat left;}


.section .tit-area p{font-size:14px; color:#767676;}
.section .tit-area p.txt-f{font-size:14px; color:#767676;}

/* foot */
#footer{clear:both; position:absolute; bottom:0; left:0; right:0; border-top:1px solid #e4e4e4; background:#bcbdc0; width:100%;}
#footer .footer{position:relative; padding:20px 0; margin:0 auto; width:1020px;}
#footer .footer .foot-logo{position:absolute; left:20px; top:30px;}
#footer .footer .add{float:left; padding-left:200px; font-size:11px;}
#footer .footer .add-lst li:first-child{padding-left:0; background:none;}
#footer .footer .add-lst li{float:left; padding:0 8px; background: url(../img/common/foot_line.gif) no-repeat left }
#footer .footer .add-lst li a{color:#514c4c; font-weight:bold; font-size:12px;} 
#footer .footer address{margin:15px 0 0; color:#4c4c4c;}
#footer .footer .copyright{color:#4c4c4c; font-size:11px;}

#footer .family-site{float:right;}
#footer .family-site .txt{margin-right:30px; color:#423b3b; font-size:19px; font-weight:bold; background: url(../img/common/foot_img.gif) no-repeat center bottom; height:95px;}
#footer .bn-service{color:#fff; width:191px;}
#footer .bn-service strong{font-size:19px;}
#footer .bn-service p{margin:5px 0 8px; font-size:12px; color:#fff; letter-spacing:-1px; line-height:16px}
#footer .bn-service div{margin-bottom:5px;}
#footer .bn-service select{border:0; margin-right:3px; padding:5px 0 0 8px; background:#898989; font-size:11px; color:#fff; width:150px; height:25px;}
#footer .bn-service .btn-go{background:#7a7a7a;width:28px; height:25px;}
#footer .bn-service .btn-go a{color:#fff; font-weight:bold; font-size:12px;text-align:center; line-height:23px; width:28px; height:25px;}

/* popup common */
#dimm{ margin:0 auto;position:fixed;top:0;left:0;right:0;bottom:0;z-index:990;background:#000000 !important;opacity:0;filter:alpha(opacity=0)}
#popAll{width:100%;position:absolute;z-index:999;}


/* popup 전체보기 */
#pop{z-index:999; width:972px; margin:0 auto;position:relative;  padding:29px 25px; background:#000000 !important;opacity:0.8;filter:alpha(opacity=80); border:1px solid #000;}
#pop .btn-close{display:block; position:absolute; right:40px; top:20px; background:url(../img/icon/btn_close.png) no-repeat left; width:27px; height:27px;}
#pop .btn-close span{font-size:0; line-height:0; text-indent:-9999px; color:#fff;}

#pop .tit{margin-bottom:30px; padding-bottom:20px; border-bottom:1px solid #fff;}
#pop .tit strong{font-size:18px; line-height:18px;font-weight:bold; color:#fff;}
#pop .tit p{margin-top:15px; font-size:14px; color:#666;}

#pop .menu{padding-left:15px;}
#pop .menu>li{float:left; padding-left:50px; margin-bottom:25px;}
#pop .menu>li:first-child{padding-left:0px;}
#pop .menu .txt a{color:#fff; font-size:18px;font-weight:bold;}
#pop .menu .txt ul{padding-top:20px;}
#pop .menu .txt ul li{padding-left:10px; background:url(../img/icon/pop_bullet.gif) no-repeat left;}
#pop .menu .txt ul li a{color:#fff; font-size:14px; line-height:30px;}



/* popup 회원가입 */
#pop-join{position:relative; padding:35px 40px; background:#fff; z-index:999; width:500px; height:420px;}
#pop-join .btn-close{display:block; position:absolute; right:40px; top:46px; background:url(../img/icon/btn_close.gif) no-repeat left; width:27px; height:27px;}
#pop-join .btn-close span{font-size:0; line-height:0; text-indent:-9999px; color:#fff;}
#pop-join .btn-close span{font-size:0; line-height:0; text-indent:-9999px; color:#fff;}
#pop-join .tit{ padding-bottom:35px; }
#pop-join .tit strong{font-size:26px; line-height:26px;  color:#333;}
#pop-join .bdl-n{border-left:none;}
#pop-join select{border:1px solid #cbcbcb; font-weight:normal; font-size:12px; color:#24292c; line-height:12px; padding:1px 1px 1px 5px; background-color:#f9f9f9;}


#pop-join span.tbox{display:block;  padding:0 5px;margin-bottom:5px;font-weight:bold;color:#388bd3;font-size:18px !important;line-height:28px; }
    </style>
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

    <!-- section -->
    <div class="section">       
        <div class="tit-area">
            <h3>온라인 상담</h3>
            <!-- <p>R&amp;D 관련 규정 및 제도에 대해 궁금하신 사항에 담당자가 답변해 드립니다.기존 답변을 검색 후 질의해주세요.</p> -->
        </div>
     <!--  explain-bx -->
     <div class="explain-bx mt10">
         <strong>국가연구개발사업 관련 공통법령 및 제도에 대해 궁금하신 사항에 답변해드립니다.</strong>
         <p>*기존 질의응답을 검색 후 질의해주시기 바랍니다.</p>
     </div>
     <!--  //explain-bx -->
        
     <html:form action="/Inquire" method="post" name="fm" type="kr.go.rndcall.mgnt.inquire.form.InquireForm">
         <html:hidden name="InquireForm" property="method" value="getInquireInsert"/>
         <html:hidden name="InquireForm" property="vo.cell_number"/>
         <html:hidden name="InquireForm" property="vo.email"/>
         <html:hidden name="InquireForm" property="searchVO.loginId"/>
         <html:hidden name="InquireForm" property="searchVO.name"/>
         <html:hidden name="InquireForm" property="searchVO.seq"/>
         <html:hidden name="InquireForm" property="searchVO.board_type"/>
         <html:hidden name="InquireForm" property="searchVO.searchCategory"/>
         <html:hidden name="InquireForm" property="searchVO.whichSearch"/>
         <html:hidden name="InquireForm" property="searchVO.searchTxt"/>             
         <html:hidden name="InquireForm" property="searchVO.menu_sn"/>
         <html:hidden name="InquireForm" property="searchVO.uni"/>
         <%
             if ( mainRoleCD.equals("0000Z") || mainRoleCD.equals("0000C") ) {
         %>
         <html:hidden name="InquireForm" property="searchVO.start_yy"/>
         <html:hidden name="InquireForm" property="searchVO.start_mm"/>
         <html:hidden name="InquireForm" property="searchVO.end_yy"/>
         <html:hidden name="InquireForm" property="searchVO.end_mm"/>
         
         <!-- board-detail -->
            <div class="board-detail mt30">
                <h3>등록자 정보</h3>
                <div class="board-box">
                    <table border="1" summary="제목, 분류, 질의내용, 답변내용  페이지">
                        <caption>자주묻는 질문 페이지</caption>
                        <colgroup>
                            <col width="15%"/>
                            <col width="20%"/>
                            <col width="15%"/>
                            <col width="20%"/>
                            <col width="15%"/>
                            <col width="*"/>
                        </colgroup>
                        <tbody>
                            <tr>
                                <th scope="row">아이디</th>
                                <td>
                                    <logic:empty name="InquireForm" property="vo.reg_id">
                                           비회원
                                    </logic:empty>
                                    <logic:notEmpty name="InquireForm" property="vo.reg_id">
                                        <bean:write name="InquireForm" property="vo.reg_id"/>
                                    </logic:notEmpty>
                                </td>
                                <th scope="row">소속</th>
                                <td>
                                    <logic:equal name="InquireForm" property="vo.query_user_info" value="1">중앙행정기관</logic:equal>
                                    <logic:equal name="InquireForm" property="vo.query_user_info" value="2">전문기관</logic:equal>
                                    <logic:equal name="InquireForm" property="vo.query_user_info" value="3">정부출연연구기관</logic:equal>
                                    <logic:equal name="InquireForm" property="vo.query_user_info" value="4">대학</logic:equal>
                                    <logic:equal name="InquireForm" property="vo.query_user_info" value="5">기업</logic:equal>
                                    <logic:equal name="InquireForm" property="vo.query_user_info" value="6">기타</logic:equal>
                                </td>
                                <th scope="row">공개여부</th>
                                <td>
                                    <bean:define name="InquireForm" property="vo.open_yn" id="open_yn" type="java.lang.String"/>
                                    <%
                                        if(open_yn.equals("Y")){
                                            out.println("공개");
                                        }else{
                                            out.println("비공개");
                                        }
                                    %>                  
                                </td>
                            </tr>
                        </tbody>
                     </table>
                </div>
            </div>
            <!-- //board-detail -->
         <%
             }
         %>
         
         <!-- board-detail -->
         <div class="board-detail mt10">
          <div class="board-box">
              <table border="1" summary="제목, 등록일, 질의내용, 답변내용 페이지">
                  <caption>온라인 상담 상세 페이지</caption>
                  <colgroup>
                      <col width="16%"/>
                      <col width="*"/>
                  </colgroup>
                  <tbody>
                      <tr>
                          <th scope="row">제목</th>
                          <td><bean:write name="InquireForm" property="vo.title" filter="false"/></td>
                      </tr>
                      <tr>
                          <th scope="row">등록일</th>
                          <td><bean:write name="InquireForm" property="vo.reg_dt"/></td>
                      </tr>
                      <tr class="comment">
                          <th scope="row">질의내용</th>
                          <td>
                              <bean:write name="InquireForm" property="vo.contents" filter="false"/>
                                 <br/><br/>
                                 <logic:notEmpty name="InquireForm" property="voList">
                                         첨부파일 : 
                                     <logic:iterate name="InquireForm" property="voList" indexId="rowNum" id="attachVO">
                                         <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
                                     </logic:iterate>
                                 </logic:notEmpty>
                          </td>
                      </tr>
                      <tr class="comment">
                          <th scope="row" class="txt-blue">답변내용(자체검토)</th>
                          <td class="txt-blue">
                              <bean:write name="InquireForm" property="vo.answerContents" filter="false"/>
                                 <br/><br/>
                                 <logic:notEmpty name="InquireForm" property="voList1">
                                         첨부파일 : 
                                     <logic:iterate name="InquireForm" property="voList1" indexId="rowNum1" id="attachVO">
                                         <a href="javascript:downLoad('<bean:write name="attachVO" property="file_nm"/>', '<bean:write name="attachVO" property="file_path"/>', '', 'Y');"><bean:write name="attachVO" property="file_nm" /></a><br/>
                                     </logic:iterate>
                                 </logic:notEmpty>
                          </td>
                      </tr>
                  </tbody>
               </table>
          </div>
      </div>
         <!-- //board-detail -->
     </html:form>
    
    </div>
    <!-- //section -->    
