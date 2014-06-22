function __getIEVersion() {
    var rv = -1; // Return value assumes failure.
    if (navigator.appName == 'Microsoft Internet Explorer') {
        var ua = navigator.userAgent;
        var re = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
        if (re.exec(ua) != null)
            rv = parseFloat(RegExp.$1);
    }
    return rv;
}

function __getOperaVersion() {
    var rv = 0; // Default value
    if (window.opera) {
        var sver = window.opera.version();
        rv = parseFloat(sver);
    }
    return rv;
}

var __userAgent = navigator.userAgent;
var __isIE =  navigator.appVersion.match(/MSIE/) != null;
var __IEVersion = __getIEVersion();
var __isIENew = __isIE && __IEVersion >= 8;
var __isIEOld = __isIE && !__isIENew;

var __isFireFox = __userAgent.match(/firefox/i) != null;
var __isFireFoxOld = __isFireFox && ((__userAgent.match(/firefox\/2./i) != null) || 
	(__userAgent.match(/firefox\/1./i) != null));
var __isFireFoxNew = __isFireFox && !__isFireFoxOld;

var __isWebKit =  navigator.appVersion.match(/WebKit/) != null;
var __isChrome =  navigator.appVersion.match(/Chrome/) != null;
var __isOpera =  window.opera != null;
var __operaVersion = __getOperaVersion();
var __isOperaOld = __isOpera && (__operaVersion < 10);

function __parseBorderWidth(width) {
    var res = 0;
    if (typeof(width) == "string" && width != null && width != "" ) {
        var p = width.indexOf("px");
        if (p >= 0) {
            res = parseInt(width.substring(0, p));
        }
        else {
     		//do not know how to calculate other values 
		//(such as 0.5em or 0.1cm) correctly now
    		//so just set the width to 1 pixel
            res = 1; 
        }
    }
    return res;
}

//returns border width for some element
function __getBorderWidth(element) {
	var res = new Object();
	res.left = 0; res.top = 0; res.right = 0; res.bottom = 0;
	if (window.getComputedStyle) {
		//for Firefox
		var elStyle = window.getComputedStyle(element, null);
		res.left = parseInt(elStyle.borderLeftWidth.slice(0, -2));  
		res.top = parseInt(elStyle.borderTopWidth.slice(0, -2));  
		res.right = parseInt(elStyle.borderRightWidth.slice(0, -2));  
		res.bottom = parseInt(elStyle.borderBottomWidth.slice(0, -2));  
	}
	else {
		//for other browsers
		res.left = __parseBorderWidth(element.style.borderLeftWidth);
		res.top = __parseBorderWidth(element.style.borderTopWidth);
		res.right = __parseBorderWidth(element.style.borderRightWidth);
		res.bottom = __parseBorderWidth(element.style.borderBottomWidth);
	}
   
	return res;
}

//returns the absolute position of some element within document
function getElementAbsolutePos(element) {
	var res = new Object();
	res.x = 0; res.y = 0;
	if (element !== null) { 
		if (element.getBoundingClientRect) {
			var viewportElement = document.documentElement;  
 	        var box = element.getBoundingClientRect();
		    var scrollLeft = viewportElement.scrollLeft;
 		    var scrollTop = viewportElement.scrollTop;

		    res.x = box.left + scrollLeft;
		    res.y = box.top + scrollTop;

		}
		else { //for old browsers
			res.x = element.offsetLeft;
			res.y = element.offsetTop;

			var parentNode = element.parentNode;
			var borderWidth = null;

			while (offsetParent != null) {
				res.x += offsetParent.offsetLeft;
				res.y += offsetParent.offsetTop;
				
				var parentTagName = 
					offsetParent.tagName.toLowerCase();	

				if ((__isIEOld && parentTagName != "table") || 
					((__isFireFoxNew || __isChrome) && 
						parentTagName == "td")) {		    
					borderWidth = kGetBorderWidth
							(offsetParent);
					res.x += borderWidth.left;
					res.y += borderWidth.top;
				}
				
				if (offsetParent != document.body && 
				offsetParent != document.documentElement) {
					res.x -= offsetParent.scrollLeft;
					res.y -= offsetParent.scrollTop;
				}


				//next lines are necessary to fix the problem 
				//with offsetParent
				if (!__isIE && !__isOperaOld || __isIENew) {
					while (offsetParent != parentNode && 
						parentNode !== null) {
						res.x -= parentNode.scrollLeft;
						res.y -= parentNode.scrollTop;
						if (__isFireFoxOld || __isWebKit) 
						{
						    borderWidth = 
						     kGetBorderWidth(parentNode);
						    res.x += borderWidth.left;
						    res.y += borderWidth.top;
						}
						parentNode = parentNode.parentNode;
					}    
				}

				parentNode = offsetParent.parentNode;
				offsetParent = offsetParent.offsetParent;
			}
		}
	}
    return res;
} 



var gMonths = new Array("1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월");
var weekArray = new Array('일','월','화','수','목','금','토');

var gNow = new Date();
var ggWinCal;
isNav = (navigator.appName.indexOf("Netscape") != -1) ? true : false;
isIE = (navigator.appName.indexOf("Microsoft") != -1) ? true : false;

//오늘 날짜
var todayDateObject = new Date();
var currentYear = todayDateObject.getYear();
if (currentYear < 1900) currentYear += 1900;
if (navigator.appVersion.indexOf("MSIE 9") > -1) {
//	currentYear += 1900;
}
var currentMonth = todayDateObject.getMonth()+1;
var currentDay = todayDateObject.getDate();
var currentYMD = "";
//이미 선택된 날짜
var selectedYMD = "";
var textFieldObject;
var divObject;
//글자색 설정하기
var sunColor = "red";
var satColor = "blue";
var norColor = "black";
var otherColor = "#808080";
//배경색 설정하기
var todayBgColor = "yellow";
var selectedBgColor = "cyan";
var norBgColor = "white";

//보여줄 년도의 범위 설정(기본값)
var startYearScope = 1920;
var endYearScope = 2020;

//현재 보여주고 있는 달력의 년,월
var nowYear = "";
var nowMonth = "";

var isSeperator=true;

var SEPERATOR = "-";

function Calendar(p_item, p_WinCal, p_month, p_year) {
	if ((p_month == null) && (p_year == null))	return;

	if (p_WinCal == null)
		this.gWinCal = ggWinCal;
	else
		this.gWinCal = p_WinCal;
	
	if (p_month == null) {
		this.gMonthName = null;
		this.gMonth = null;
		this.gYearly = true;
	} else {
		this.gMonthName = Calendar.get_month(p_month);
		this.gMonth = new Number(p_month);
		this.gYearly = false;
	}

	this.gYear = p_year;
	this.gReturnItem = p_item;
}

// <field && method>
Calendar.Months = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"];
// 평년
Calendar.DOMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
// 윤년
Calendar.lDOMonth = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

Calendar.get_month = Calendar_get_month;
Calendar.get_daysofmonth = Calendar_get_daysofmonth;
Calendar.calc_month_year = Calendar_calc_month_year;
Calendar.print = Calendar_print;
// </field && method>

function Calendar_get_month(monthNo) {
	return Calendar.Months[monthNo];
}

function Calendar_get_daysofmonth(monthNo, p_year) {
	if ((p_year % 4) == 0) {
		if ((p_year % 100) == 0 && (p_year % 400) != 0)
			return Calendar.DOMonth[monthNo];
	
		return Calendar.lDOMonth[monthNo];
	} else
		return Calendar.DOMonth[monthNo];
}

function Calendar_calc_month_year(p_Month, p_Year, incr) {
	var ret_arr = new Array();
	
	if (incr == -1) {
		// B A C K W A R D
		if (p_Month == 0) {
			ret_arr[0] = 11;
			ret_arr[1] = parseInt(p_Year) - 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month) - 1;
			ret_arr[1] = parseInt(p_Year);
		}
	} else if (incr == 1) {
		// F O R W A R D
		if (p_Month == 11) {
			ret_arr[0] = 0;
			ret_arr[1] = parseInt(p_Year) + 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month) + 1;
			ret_arr[1] = parseInt(p_Year);
		}
	}
	
	return ret_arr;
}

function Calendar_print() {
	ggWinCal.print();
}

function Calendar_calc_month_year(p_Month, p_Year, incr) {
	var ret_arr = new Array();
	
	if (incr == -1) {
		// B A C K W A R D
		if (p_Month == 0) {
			ret_arr[0] = 11;
			ret_arr[1] = parseInt(p_Year) - 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month) - 1;
			ret_arr[1] = parseInt(p_Year);
		}
	} else if (incr == 1) {
		// F O R W A R D
		if (p_Month == 11) {
			ret_arr[0] = 0;
			ret_arr[1] = parseInt(p_Year) + 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month) + 1;
			ret_arr[1] = parseInt(p_Year);
		}
	}
	
	return ret_arr;
}

// For compatibility with Navigator 3
new Calendar();

Calendar.prototype.getMonthlyCalendarCode = function() {
	var vCode = "";
	var vHeader_Code = "";
	var vData_Code = "";
	
	vCode = vCode + "<table width='250' border='1' bgcolorcolor='#cccccc' cellpadding='5' cellspacing='0' style='border-collapse:collapse;>";
	vCode = vCode + "	<tr bordercolor='#EDF2FE'>";

	
	vHeader_Code = this.cal_header();
	vData_Code = this.cal_data();
	vCode = vCode + vHeader_Code + vData_Code;
	
	vCode = vCode + "</TABLE>";
	return vCode;
}

Calendar.prototype.show = function() {
	var vCode = "";
	
	// <note>
	this.gWinCal.document.open(); 

	// Setup the page...
	this.wwrite("<html>");
	this.wwrite("<head><title>Calendar</title>");

	// My Style...
	this.wwrite("<style type='text/css'>");
	this.wwrite("td {font-family:굴림, Gulim;font-size: 9pt}");
	this.wwrite("td.today { background-color: ffcd9c; }");
	this.wwrite("a:link {  font-size: 9pt; color: #000000; text-decoration: none}");
	this.wwrite("a:hover {  font-size: 9pt; color: #CC6600; text-decoration: underline}");
	this.wwrite("A:active {font-family: 굴림,Arial; text-decoration: none; color: red;}");
	this.wwrite("a:visited {  font-size: 9pt; color: #000000; text-decoration: none} ");
	this.wwrite(".list01{ font-family: 굴림,verdana; font-size: 9pt; color: #000000; text-align:center} ");
	this.wwrite("</style>");

	this.wwrite("</head>");

	this.wwrite("<body BGCOLOR=#FFFFFF leftmargin='0' topmargin='0' marginwidth='0' marginheight='0'>");

	// Link
	var prevMMYYYY = Calendar.calc_month_year(this.gMonth, this.gYear, -1);
	var prevMM = prevMMYYYY[0];
	var prevYYYY = prevMMYYYY[1];

	var nextMMYYYY = Calendar.calc_month_year(this.gMonth, this.gYear, 1);
	var nextMM = nextMMYYYY[0];
	var nextYYYY = nextMMYYYY[1];
	
	this.wwrite("<table width='259' border='0' align='left'>");
	this.wwrite("<tr>");
	this.wwrite("<td align='center'> ");
	this.wwrite("<table width='253' border='0' bgcolor='#E4EDC7' cellpadding='0' cellspacing='0'>");
	this.wwrite("<tr>");
	this.wwrite("<td height='30'>");
	this.wwrite("<table width='253' border=0 cellspacing=0 cellpadding=0 align=center>");
	this.wwrite("<TR>");
	this.wwrite("<TD ALIGN=center><A HREF=\"" +
		"javascript:window.opener.Build(" + 
		"'" + this.gReturnItem + "', '" + this.gMonth + "', '" + (parseInt(this.gYear)-1) + "');" +
		"\"><b class=list><span style='font-size:10;'><<</span></b><\/A></TD>");
	this.wwrite("<TD ALIGN=center><A HREF=\"" +
		"javascript:window.opener.Build(" + 
		"'" + this.gReturnItem + "', '" + prevMM + "', '" + prevYYYY + "');" +
		"\"><b class=list><span style='font-size:10;'><</span></b><\/A></TD>");
	this.wwriteA("<TD ALIGN=center class=list>" + this.gYear + "년 " + this.gMonthName + "</TD>");
	this.wwrite("<TD ALIGN=center><A HREF=\"" +
		"javascript:window.opener.Build(" + 
		"'" + this.gReturnItem + "', '" + nextMM + "', '" + nextYYYY + "');" +
		"\"><b class=list><span style='font-size:10;'>></span></b><\/A></TD>");
	this.wwrite("<TD ALIGN=center><A HREF=\"" +
		"javascript:window.opener.Build(" + 
		"'" + this.gReturnItem + "', '" + this.gMonth + "', '" + (parseInt(this.gYear)+1) + "');" +
		"\"><b class=list><span style='font-size:10;'>>></span></b><\/A></TD>");
	this.wwrite("</TR>");
	this.wwrite("</TABLE>");
	this.wwrite("</td>");
	this.wwrite("</tr>");
	this.wwrite("</table>");

	// Real Calendar
	vCode = this.getMonthlyCalendarCode();
	this.wwrite(vCode);

	this.wwrite("</div></td>");
	this.wwrite("</tr>");
	this.wwrite("</table>");
	this.wwrite("</div>");
	this.wwrite("</body></html>");
	this.gWinCal.document.close();	
	// </note>
}


Calendar.prototype.wwrite = function(wtext) {
	this.gWinCal.document.writeln(wtext);
}

Calendar.prototype.wwriteA = function(wtext) {
	this.gWinCal.document.write(wtext);
}

Calendar.prototype.cal_header = function() {
	var vCode = "";
	
	vCode = vCode + "<tr bgcolor='#EDF2FE'>";
	vCode = vCode + "<TD WIDTH='14%' height='14%' class='list01'><FONT COLOR='#FF6633'>일</FONT></TD>";
	vCode = vCode + "<TD WIDTH='14%' class='list01' height='14%'><FONT COLOR='#000000'>월</FONT></TD>";
	vCode = vCode + "<TD WIDTH='14%' class='list01' height='14%'><FONT COLOR='#000000'>화</FONT></TD>";
	vCode = vCode + "<TD WIDTH='14%' class='list01' height='14%'><FONT COLOR='#000000'>수</FONT></TD>";
	vCode = vCode + "<TD WIDTH='14%' class='list01' height='14%'><FONT COLOR='#000000'>목</FONT></TD>";
	vCode = vCode + "<TD WIDTH='14%' class='list01' height='14%'><FONT COLOR='#000000'>금</FONT></TD>";
	vCode = vCode + "<TD WIDTH='16%' class='list01' height='14%'><FONT COLOR='#3366CC'>토</FONT></TD>";
	vCode = vCode + "</TR>";
	
	return vCode;
}

Calendar.prototype.cal_data = function() {
	var vDate = new Date();
	vDate.setDate(1);
	vDate.setMonth(this.gMonth);
	vDate.setFullYear(this.gYear);

	var vFirstDay=vDate.getDay();
	var vDay=1;
	var vLastDay=Calendar.get_daysofmonth(this.gMonth, this.gYear);
	var vOnLastDay=0;
	var vCode = "";

	// 첫째주
	vCode = vCode + "<TR>";
	for (i=0; i<vFirstDay; i++) {
		vCode = vCode + "<TD WIDTH='14%' height='14%'>&nbsp;</TD>";
	}

	// 첫째주 나머지
	for (j=vFirstDay; j<7; j++) {
		vCode = vCode + "<TD WIDTH='14%' height='14%' " + this.format_day(vDay) + "><div align='center'>" + 
			"<A HREF='#' " + 
				"onClick=\"opener.document." + this.gReturnItem + ".value='" + 
				this.format_data(vDay) + 
				"';window.close();\" " + this.write_weekend_string(j) + ">" + 
				vDay + 
			"</div></A>" + 
			"</TD>";
		vDay=vDay + 1;
	}
	vCode = vCode + "</TR>";

	// 나머지주
	for (k=2; k<7; k++) {
		vCode = vCode + "<TR>";

		for (j=0; j<7; j++) {
			vCode = vCode + "<TD WIDTH='14%' height='14%' " + this.format_day(vDay) + "><div align='center'>" + 
				"<A HREF='#' " + 
					"onClick=\"opener.document." + this.gReturnItem + ".value='" + 
					this.format_data(vDay) + 
					"';window.close();\" " + this.write_weekend_string(j) + ">" + 
				vDay + 
				"</div></A>" + 
				"</TD>";
			vDay=vDay + 1;

			if (vDay > vLastDay) {
				vOnLastDay = 1;
				break;
			}
		}

		if (j == 6)
			vCode = vCode + "</TR>";
		if (vOnLastDay == 1)
			break;
	}
	
	// 다음달 날짜 출력
	for (m=1; m<(7-j); m++) {
		vCode = vCode + "<TD WIDTH='14%' height='14%'><div align='center'><FONT style=\"color: #cccccc\">" + m + "</div></TD>";
	}
	
	return vCode;
}

Calendar.prototype.format_day = function(vday) {
	var vNowDay = gNow.getDate();
	var vNowMonth = gNow.getMonth();
	var vNowYear = gNow.getFullYear();

	if (vday == vNowDay && this.gMonth == vNowMonth && this.gYear == vNowYear)
		return ("class=today");
	else
		return ("class=text");
}

Calendar.prototype.write_weekend_string = function(vday) {
	var i;

	// 토,일요일 색깔
	if (vday == 0)
	{
		return ("style=\"color: #3366CC\"");
	} else if (vday == 6)
	{
		return ("style=\"color: #FF6633\"");
	} else 
	{
		return "style=\"color: black\"";
	}
}
/*
*	gubun 1: 년, 2: 월, 3: 일 을 반환
*/
function getStr(_date, gubun) {
	if(gubun == 1) {
		return _date.substring(0, 4);
	}else if(gubun ==2) {
		return _date.substring(4, 6);
	}else {
		return _date.substring(6);
	}
}

// 날짜 포맷
Calendar.prototype.format_data = function(p_day) {

	var vMonth = 1 + this.gMonth;
	vMonth = (vMonth.toString().length < 2) ? "0" + vMonth : vMonth;
	var vMon = Calendar.get_month(this.gMonth).substr(0,3);
	var vY4 = new String(this.gYear);
	var vY2 = new String(this.gYear.substr(2,2));
	var vDD = (p_day.toString().length < 2) ? "0" + p_day : p_day;

	var vData = vY4 + "-" + vMonth + "-" + vDD; // 날짜 포맷
	return vData;
}

function Build(p_item, p_month, p_year) {
	
	var p_WinCal = ggWinCal;
	gCal = new Calendar(p_item, p_WinCal, p_month, p_year);

	gCal.show();
}

function show_calendar(target_item) {
	
	p_item = target_item;
	p_month = new String(gNow.getMonth());
	p_year = new String(gNow.getFullYear().toString());

	// <note>
	vWinCal = window.open("", "Calendar", "width=260,height=180,status=no,resizable=no,top=200,left=200,alwaysRaised=yes,scrollbars=no,titlebar=no");
	vWinCal.opener = self;
	vWinCal.focus();
	ggWinCal = vWinCal;
	// </note>

	Build(p_item, p_month, p_year);
}
/*
*	달력을 보여주기위해 초기화를 시킨후 달력을 보여준다.
*/
function CalendarStart(startYear, endYear, textField, div) {

	//if(startYear != "" && startYear.length == 4) {
	if(startYear > 999 && startYear < 10000) {
		startYearScope = startYear;
	}
	//if(endYear != "" && endYear.length == 4 ) {
	if(startYear > 999 && startYear < 10000) {
		endYearScope = endYear;
	}
	currentYMD = currentYear +""+ getTwoLength(currentMonth) + "" +getTwoLength(currentDay);
	textFieldObject = textField;  //eval("document.all."+textFieldName);
    divObject = div;

	selectedYMD = textFieldObject.value;
    if(isSeperator) {
        selectedYMD = selectedYMD.replace(SEPERATOR,"");
        selectedYMD = selectedYMD.replace(SEPERATOR,"");
    }


	if(selectedYMD.length != 8 || selectedYMD == "99991231") {
		resetDate( getStr(currentYMD,1), getStr(currentYMD, 2));
	}else {
		resetDate( getStr(selectedYMD,1), getStr(selectedYMD, 2));
	}

	var point = getObjectXY(textFieldObject);
	with(divObject.style){
	  	left = point.x;
		top  = point.y+textFieldObject.offsetHeight+1;
	}
	toggleCalendar();
}
function openCalender(obj){

    CalendarStart(1950, 2020, obj, cal_Div);
}
/*
*	지정한 날짜를 두자리 문자로 반환
*/
function getTwoLength(_day) {
	if(Number(_day) < 10) {
		return "0" + _day;
	}else {
		return _day;
	}
}

/*
*	주어진 년,월 데이터를 바탕으로 달력을 보여준다.
*/
function resetDate(_year, _month){

	if( Number(startYearScope) > Number(_year)) {
		alert("선택범위를 벗어 났습니다.");
		return;
	}else if( Number(endYearScope) < Number(_year)) {
		alert("선택범위를 벗어 났습니다.");
		return;
	}
	nowYear = _year;
	nowMonth = getTwoLength(_month);

    //iframe 으로 변경하면서 추가됨
    if(calIframe.document.body.innerHTML == "") {
        calIframe.document.write("<html><head><title>Wellcome to KFDA</title><link rel='stylesheet' href='/common/common.css'></head><body marginwidth='0' marginheight='0' topmargin='0' leftmargin='0'></body></html>");
    }
    calIframe.document.body.innerHTML = "";
    calIframe.document.body.innerHTML = getCalendarHtml( _year, getTwoLength(_month));

}

/*
*	달력전체를 html 로 반환 한다.
*/
function getCalendarHtml(_year, _month) {
	var _html = "";
	_html += ("<table border='0' cellspacing='4' cellpadding='0' width='100%' bgcolor='#44A8BF' style='border:1 solid #118099'>");
	_html += ("<tr>");
	_html += ("		<td align='center'><input type='button' name='PrevMonth' value='<' style='height:20;width:20;FONT:16 Fixedsys' onClick='parent.prevMonth()'>");
	_html += ("&nbsp;<SELECT name='tbSelYear' onChange='parent.resetDate(tbSelYear.value, tbSelMonth.value)' Victor='Won'>");

	for(i = startYearScope; i <= endYearScope; i++) {
		if(i == _year) {
			_html += ("<OPTION value='"+i+"' selected>"+i+"</OPTION>");
		}else {
			_html += ("<OPTION value='"+i+"'>"+i+"</OPTION>");
		}
	}

	_html += ("</SELECT>");
	_html += ("&nbsp;<select name='tbSelMonth' onChange='parent.resetDate(tbSelYear.value, tbSelMonth.value)' Victor='Won'>");

	for (i=0; i<12; i++) {
		if((i) == (Number(_month)-1)) {
			_html += ("<option value='"+(i+1)+"' selected>"+gMonths[i]+"</option>");
		}else {
			_html += ("<option value='"+(i+1)+"'>"+gMonths[i]+"</option>");
		}
	}

	_html += ("</SELECT>");
	_html += ("&nbsp;<input type='button' name='PrevMonth' value='>' style='height:20;width:20;FONT:16 Fixedsys' onclick='parent.nextMonth()'>");
	_html += ("&nbsp;<a href='javascript:parent.cancelDate()'><img src='/images_n/icon/btnClose.gif' align='absmiddle' border=0></a>");
	_html += ("</td>");
	_html += ("</tr><tr>");
	_html += ("<td align='center' bgcolor=#D6D6D6>");
	_html += getMonthMetrixHtml( Number(_year), Number(_month));
	_html += ("</td>");
	_html += ("</TR>");
	_html += ("</TABLE>");
//    _html += ("</body></html>");
	return _html;
}

/*
*	해당월의 일자 배열을 받아서 table 태그로 리턴
*/
function getMonthMetrixHtml(_year, _month) {
	var _tempMetrix = getMonthMetrix(_year, _month);
	var _metrixHtml = "";
	_metrixHtml += ("<table width='100%' border='0' cellpadding='0' cellspacing='1'>");

	_metrixHtml += "<TR>";
	for(i=0; i<7; i++)
		_metrixHtml += ("<td bgcolor='#FFFFFF' borderColor='#FFFFFF' valign='middle' align='center'>" + weekArray[i] + "</td>");
	_metrixHtml += "</TR>";

	for(i=0; i < 6; i++) {
		_metrixHtml += "<TR>";
		for(j = 0; j < 7; j++) {
			var _strDate = _tempMetrix[(i*7) + (j)];
			var color = "";
			var bgColor = "";
			if(j==0) {
				color = sunColor;
			}else if(j == 6) {
				color = satColor;
			}else {
				color = norColor;
			}
			if(_year != getStr(_strDate, 1) || Number(_month) != Number(getStr(_strDate, 2))) {
				color = otherColor;
			}
			if(_strDate == currentYMD) {
				bgColor = todayBgColor;
			}else if(_strDate == selectedYMD) {
				bgColor = selectedBgColor;
			}else {
				bgColor = norBgColor;
			}
			_metrixHtml += "<TD bgcolor='"+bgColor+"' borderColor='#FFFFFF' valign='middle' align='center' style='cursor:hand;padding:3 0 3 0' onclick=\"parent.setDateExit('"+_strDate+"');\">";
			_metrixHtml += "<font style='font-size:12' color='"+color+"'>"+Number(getStr(_strDate, 3)) + "</font>";
			_metrixHtml += "</TD>";
		}
		_metrixHtml += "</TR>";
	}
	_metrixHtml += ("</table>");
	return _metrixHtml;
}

/*
*	지정한 월의 날짜를 배열로 반환
*/
function getMonthMetrix(_year, _month) {
	var monthMetrix = new Array(42);
	var selectedMonthDayCount = getMonthDayCount(_year, _month);
	var selectedPrevMonthDayCount = 0;
	var selectedFirstDayOfWeek = 0;
	if(_month == 1)
		selectedPrevMonthDayCount = getMonthDayCount(_year-1, 12);
	else
		selectedPrevMonthDayCount = getMonthDayCount(_year, _month - 1);

	selectedFirstDayOfWeek = getDayOfWeek(_year, _month)

	var startNum = 1;
	var nextStatrNum = 1;
	for(i=0; i < 6; i++) {
		for(j = 0; j < 7; j ++) {
			var tempYearMonthDay = "00000000";
			var info = 0;
			if( i==0 && selectedFirstDayOfWeek > j ) {
				_tempDay = (selectedPrevMonthDayCount - selectedFirstDayOfWeek) + (j+1);
				tempYearMonthDay = getPrevYearMonth(_year, _month) + "" + getTwoLength(_tempDay);
			}else if(startNum <= selectedMonthDayCount) {
				tempYearMonthDay = _year + "" + getTwoLength(_month) + "" + getTwoLength(startNum);
				startNum ++;
			}else {
				tempYearMonthDay = getNextYearMonth(_year, _month) + "" + getTwoLength(nextStatrNum);
				nextStatrNum ++;
			}
			monthMetrix[(i*7) + (j)] = tempYearMonthDay;
		}
	}
	return monthMetrix;
}

/*
*	지정한 년, 월의 날수를 계산한다.
*/
function getMonthDayCount(_year, _month){
	selectedMonthFirstDay = new Date(_year, _month-1 , 1);
	selectedNextMonthFirstDay = new Date(_year, _month, 1);
	var _selectedMonthDayCount = (selectedNextMonthFirstDay - selectedMonthFirstDay)/1000/60/60/24;
	return _selectedMonthDayCount;
}

/*
*	지정한 달의 첫날의 요일을 반환
*/
function getDayOfWeek(_year, _month){
	 var _dayOfWeek= new Date(_year, _month-1, 1).getDay();
	 return _dayOfWeek;
}
/*
*	지정한 년, 월의 전월의 년월을 반환
*/
function getPrevYearMonth(_year, _month) {
	if(Number(_month) < 2) {
		return Number(_year)-1 + "12";
	}else {
		return Number(_year) +""+ getTwoLength(Number(_month)-1);
	}
}

/*
*	지정한 년, 월의 이월의 년월을 반환
*/
function getNextYearMonth(_year, _month) {
	if(_month == 12) {
		return Number(_year)+1 + "01";
	}else {
		return Number(_year) +""+ getTwoLength(Number(_month)+1);
	}
}

/*
*	현재 입력될 텍스트 박스의 좌표를 구한다.
*/
function getObjectXY(aTag){
	var oTmp = aTag;
	var pt = new Point(0,0);
	do {
		pt.x += oTmp.offsetLeft;
		pt.y += oTmp.offsetTop;
		oTmp = oTmp.offsetParent;
	} while(oTmp!=null && oTmp.tagName!="BODY");
	return pt;
}

/*
*	좌표를 나타내기 위한 객체
*/
function Point(iX, iY){
	this.x = iX;
	this.y = iY;
}

/*
*	달력을 숨기거나 , 보이게 한다.
*/
function toggleCalendar() {
	if(divObject.style.display == "none") {
		divObject.style.display = "";
		divObject.style.zIndex = "2";
	}else {
		divObject.style.display = "none";
	}
}

/*
*	달력을 숨긴다.
*/
function cancelDate() {
	toggleCalendar();
}

function setDateExit(ymd ){
	if(isSeperator==true){
		textFieldObject.value = ymd.substring(0,4) +SEPERATOR +ymd.substring(4,6)+SEPERATOR+ymd.substring(6,8);
	}else{
		textFieldObject.value = ymd;
	}
	toggleCalendar();
}