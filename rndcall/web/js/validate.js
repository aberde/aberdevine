
//common/validate.js
//2004-06-07 by foxlion

// Global 변수선언
var GLB_MONTH_IN_YEAR       = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
var GLB_SHORT_MONTH_IN_YEAR = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
var GLB_DAY_IN_WEEK         = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
var GLB_SHORT_DAY_IN_WEEK   = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
var GLB_DAYS_IN_MONTH       = [31,28,31,30,31,30,31,31,30,31,30,31];

/**
 * @type   : prototype_function*
 * @object : Date
 * @access : public
 * @desc   : 자바스크립트의 내장 객체인 Date 객체에 format 메소드를 추가한다. format 메소드는 Date 객체가 가진 날짜를
 *           지정된 포멧의 스트링으로 변환한다.
 * <pre>
 *     var dateStr = new Date().format("YYYYMMDD");
 *
 *     참고 : Date 오브젝트 생성자들 - dateObj = new Date()
 *                                   - dateObj = new Date(dateVal)
 *                                   - dateObj = new Date(year, month, date[, hours[, minutes[, seconds[,ms]]]])
 * </pre>
 * 위의 예에서 오늘날짜가 2002년 3월 5일이라면 dateStr의 값은 "20020305"가 된다.
 * default pattern은 "YYYYMMDD"이다.
 * @sig    : [pattern]
 * @param  : pattern optional 변환하고자 하는 패턴 스트링. (default : YYYYMMDD)
 * <pre>
 *     # syntex
 *
 *       YYYY : hour in am/pm (1~12)
 *       MM   : month in year(number)
 *       MON  : month in year(text)  예) "January"
 *       mon  : short month in year(text)  예) "Jan"
 *       DD   : day in month
 *       DAY  : day in week  예) "Sunday"
 *       day  : short day in week  예) "Sun"
 *       hh   : hour in am/pm (1~12)
 *       HH   : hour in day (0~23)
 *       mm   : minute in hour
 *       ss   : second in minute
 *       SS   : millisecond in second
 *       a    : am/pm  예) "AM"
 * </pre>
 * @return : Date를 표현하는 변환된 String.
 * @author : foxlion
 */
Date.prototype.format = function(pattern) {
    var year      = this.getFullYear();
    var month     = this.getMonth() + 1;
    var day       = this.getDate();
    var dayInWeek = this.getDay();
    var hour24    = this.getHours();
    var ampm      = (hour24 < 12) ? "AM" : "PM";
    var hour12    = (hour24 > 12) ? (hour24 - 12) : hour24;
    var min       = this.getMinutes();
    var sec       = this.getSeconds();

    var YYYY = "" + year;
    var YY   = YYYY.substr(2);
    var MM   = (("" + month).length == 1) ? "0" + month : "" + month;
    var MON  = GLB_MONTH_IN_YEAR[month-1];
    var mon  = GLB_SHORT_MONTH_IN_YEAR[month-1];
    var DD   = (("" + day).length == 1) ? "0" + day : "" + day;
    var DAY  = GLB_DAY_IN_WEEK[dayInWeek];
    var day  = GLB_SHORT_DAY_IN_WEEK[dayInWeek];
    var HH   = (("" + hour24).length == 1) ? "0" + hour24 : "" + hour24;
    var hh   = (("" + hour12).length == 1) ? "0" + hour12 : "" + hour12;
    var mm   = (("" + min).length == 1) ? "0" + min : "" + min;
    var ss   = (("" + sec).length == 1) ? "0" + sec : "" + sec;
    var SS   = "" + this.getMilliseconds();

    var dateStr;
    var index = -1;

    if (typeof(pattern) == "undefined") {
        dateStr = "YYYYMMDD";
    } else {
        dateStr = pattern;
    }

    dateStr = dateStr.replace(/YYYY/g, YYYY);
    dateStr = dateStr.replace(/YY/g,   YY);
    dateStr = dateStr.replace(/MM/g,   MM);
    dateStr = dateStr.replace(/MON/g,  MON);
    dateStr = dateStr.replace(/mon/g,  mon);
    dateStr = dateStr.replace(/DD/g,   DD);
    dateStr = dateStr.replace(/DAY/g,  DAY);
    dateStr = dateStr.replace(/day/g,  day);
    dateStr = dateStr.replace(/hh/g,   hh);
    dateStr = dateStr.replace(/HH/g,   HH);
    dateStr = dateStr.replace(/mm/g,   mm);
    dateStr = dateStr.replace(/ss/g,   ss);
    dateStr = dateStr.replace(/(\s+)a/g, "$1" + ampm);

    return dateStr;
}

/**
 * @type   : prototype_function
 * @access : public
 * @desc   : 자바스크립트의 내장 객체인 String 객체에 trim 메소드를 추가한다. trim 메소드는 스트링의 앞과 뒤에
 *           있는 white space 를 제거한다.
 * <pre>사용예 :
 *     var str = " abcde "
 *     str = str.trim();
 * </pre>
 * 위의 예에서 str는 "abede"가 된다.
 * @return : trimed String.
 * @author : foxlion
 */
String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * @type   : prototype_function
 * @access : public
 * @desc   : 자바스크립트의 내장 객체인 String 객체에 trimAll 메소드를 추가한다. trim 메소드는 스트링 내에
 *           있는 white space 를 모두 제거한다.
 * <pre>사용예 :
 *     var str = " abc de "
 *     str = str.trimAll();
 * </pre>
 * 위의 예에서 str는 "abcde"가 된다.
 * @return : trimed String.
 * @author : foxlion
 */
String.prototype.trimAll = function() {
    return this.replace(/\s*/g, "");
}

/**
 * @type   : prototype_function
 * @object : Date
 * @access : public
 * @desc   : 현재 Date 객체의 날짜보다 이후날짜를 가진 Date 객체를 리턴한다.
 *           예를 들어 내일 날짜를 얻으려면 다음과 같이 하면 된다.
 * <pre>사용예 :
 *    <font color=blue>주로 format()과 함께 사용한다.</font>
 *     var oneDayAfter = new Date.after(0, 0, 1); => Fri Apr 11 13:19:45 UTC+0900 2003
 *     var oneDayAfter = new Date.after(0, 0, 1).format( "YYYYYMMDD" ); => 20030411
 *
 *     ㅇ 다음날의 경우 : after( 0, 0, 1 );
 *     ㅇ 다음달의 경우 : after( 0, 1, 0 );
 *     ㅇ 내년의 경우 : after( 1, 0, 0 );
 *
 * </pre>
 * @sig    : [years[, months[, dates[, hours[, minutes[, seconds[, mss]]]]]]]
 * @param  : years   optional 이후 년수
 * @param  : months  optional 이후 월수
 * @param  : dates   optional 이후 일수
 * @param  : hours   optional 이후 시간수
 * @param  : minutes optional 이후 분수
 * @param  : seconds optional 이후 초수
 * @param  : mss     optional 이후 밀리초수
 * @return : 이후날짜를 표현하는 Date 객체
 * @author : foxlion
 */
Date.prototype.after = function(years, months, dates, hours, miniutes, seconds, mss) {
    if (years == null)    years    = 0;
    if (months == null)   months   = 0;
    if (dates == null)    dates    = 0;
    if (hours == null)    hours    = 0;
    if (miniutes == null) miniutes = 0;
    if (seconds == null)  seconds  = 0;
    if (mss == null)      mss      = 0;

    return new Date(this.getFullYear() + years,
                    this.getMonth() + months,
                    this.getDate() + dates,
                    this.getHours() + hours,
                    this.getMinutes() + miniutes,
                    this.getSeconds() + seconds,
                    this.getMilldfiseconds() + mss
                   );
}

function isRequired(obj) {
//  debug('obj', obj);
//  debug('obj.name', obj.name);
  if (isNotEmpty(obj)) {
    //debugMsg("is not empty");
    return false;
  }
  else {
    //debugMsg("is required");
//	debugAlert('obj.disabled', obj.disabled);
    if (obj.disabled != true) {
        obj.focus();
    }
//    debug("obj.type", obj.type);
    if (obj.type != 'select-one' && obj.disabled != true) {
        obj.select();
    }
    return true;
  }
}

function isRequiredWithMsg(obj, msg) {
  if (isNotEmptyNoMsg(obj)) {
    return false;
  }
  else {
    alert(msg);
    if (obj.disabled != true) {
        obj.focus();
    }
    if (obj.type != 'select-one' && obj.disabled != true) {
        obj.select();
    }
    return true;
  }
}

//////////////////////////
// validates that the field value string has one or more characters in it
function isEmpty(elem) {
    var str = elem.value;
    var re = /.+/;
    if(!str.trim().match(re)) {
        alert("'" + elem.title + "'는(은) 필수 입력 항목입니다.");
//        alert("Please fill in the required field.");
        return true;
    } else {
        return false;
    }
}

// validates that the field value string has one or more characters in it
function isNotEmpty(elem) {
    //debug("elem.name", elem.name);
    //debug("elem.value", elem.value);
    var str = elem.value;
    var re = /.+/;
    if(!str.trim().match(re)) {
        alert("'" + elem.title + "'는(은) 필수 입력 항목입니다.");
//        alert("Please fill in the required field.");
        return false;
    } else {
        return true;
    }
}

// validates that the field value string has one or more characters in it
function isNotEmptyNoMsg(elem) {
    //debug("elem.name", elem.name);
    //debug("elem.value", elem.value);
    var str = elem.value;
    var re = /.+/;
    if(!str.trim().match(re)) {
//        alert("'" + elem.title + "'는(은) 필수 입력 항목입니다.");
//        alert("Please fill in the required field.");
        return false;
    } else {
        return true;
    }
}

//validates that the entry is a positive or negative number
function isNumber(elem) {
    var str = elem.value;
    var re = /^[-]?\d*\.?\d*$/;
    str = str.toString( );
    if (!str.match(re)) {
        alert("Enter only numbers into the field.");
        return false;
    }
    return true;
}

// validates that the entry is 16 characters long when
// input field's maxlength attribute is set to 16
function isLen16(elem) {
    var str = elem.value;
    var re = /\b.{16}\b/;
    if (!str.match(re)) {
        alert("Entry does not contain the required 16 characters.");
        return false;
    } else {
        return true;
    }
}

// validates that the entry is formatted as an email address
function isEMailAddr(elem) {
    var str = elem.value;
    var re = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;
    if (!str.match(re)) {
        alert("'이메일주소'이(가) 유효하지 않습니다.");
//        alert("Verify the email address format.");
        return false;
    } else {
        return true;
    }
}
//////////////////////////

// validate that the user made a selection other than default
function isChosen(select) {
    if (select.selectedIndex == 0) {
        alert("Please make a choice from the list.");
        return false;
    } else {
        return true;
    }
}

function isNotEmail(select){
  if(select.selectedIndex = 0 ){
    alert("email이 없어 승인내용을 전송할수 없습니다.사용자정보의 연구책임자 email를 입력하십시요.");
  }
  return true;
}
// validate that the user has checked one of the radio buttons
function isValidRadio(radio) {
    var valid = false;
    for (var i = 0; i < radio.length; i++) {
        if (radio[i].checked) {
            return true;
        }
    }
    alert("Make a choice from the radio buttons.");
    return false;
}

function isValidDate(day, month, year) {
    if (month < 1 || month > 12) {
        return false;
    }
    if (day < 1 || day > 31) {
        return false;
    }
    if ((month == 4 || month == 6 || month == 9 || month == 11) &&
        (day == 31)) {
        return false;
    }
    if (month == 2) {
        var leap = (year % 4 == 0 &&
                   (year % 100 != 0 || year % 400 == 0));
        if (day>29 || (day == 29 && !leap)) {
            return false;
        }
    }
    return true;
}

function checkDate(fld) {
    var mo, day, yr;
    var entry = fld.value;
    entry = entry.trim();
    var reLong = /\b\d{1,2}[\/-]\d{1,2}[\/-]\d{4}\b/;
    var reShort = /\b\d{1,2}[\/-]\d{1,2}[\/-]\d{2}\b/;
    var valid = (reLong.test(entry)) || (reShort.test(entry));
    if (valid) {
        var delimChar = (entry.indexOf("/") != -1) ? "/" : "-";
        var delim1 = entry.indexOf(delimChar);
        var delim2 = entry.lastIndexOf(delimChar);
        mo = parseInt(entry.substring(0, delim1), 10);
        day = parseInt(entry.substring(delim1+1, delim2), 10);
        yr = parseInt(entry.substring(delim2+1), 10);
        // handle two-digit year
        if (yr < 100) {
            var today = new Date( );
            // get current century floor (e.g., 2000)
            var currCent = parseInt(today.getFullYear( ) / 100) * 100;
            // two digits up to this year + 15 expands to current century
            var threshold = (today.getFullYear( ) + 15) - currCent;
            if (yr > threshold) {
                yr += currCent - 100;
            } else {
                yr += currCent;
            }
        }
        var testDate = new Date(yr, mo-1, day);
        if (testDate.getDate( ) == day) {
            if (testDate.getMonth( ) + 1 == mo) {
                if (testDate.getFullYear( ) == yr) {
                    // fill field with database-friendly format
                    fld.value = mo + "/" + day + "/" + yr;
                    return true;
                } else {
                    alert("There is a problem with the year entry.");
                }
            } else {
                alert("There is a problem with the month entry.");
            }
        } else {
            alert("There is a problem with the date entry.");
        }
    } else {
        alert("Incorrect date format. Enter as mm/dd/yyyy.");
    }
    return false;
}

function daysBetween(date1, date2) {
    var DSTAdjust = 0;
    // constants used for our calculations below
    oneMinute = 1000 * 60;
    var oneDay = oneMinute * 60 * 24;
    // equalize times in case date objects have them
    date1.setHours(0);
    date1.setMinutes(0);
    date1.setSeconds(0);
    date2.setHours(0);
    date2.setMinutes(0);
    date2.setSeconds(0);
    // take care of spans across Daylight Saving Time changes
    if (date2 > date1) {
        DSTAdjust =
            (date2.getTimezoneOffset( ) - date1.getTimezoneOffset( )) * oneMinute;
    } else {
        DSTAdjust =
            (date1.getTimezoneOffset( ) - date2.getTimezoneOffset( )) * oneMinute;
    }
    var diff = Math.abs(date2.getTime( ) - date1.getTime( )) - DSTAdjust;
    return Math.ceil(diff/oneDay);
}

/**
 * @type   : function
 * @access : public
 * @desc   : 2개의 날짜를 비교해서 앞의 날짜가 뒤의 날짜보다 크면 false를 반환하고
 * 			 앞의 날짜가 작으면 true를 반환한다.<br>
 *
 * <pre>사용예 :
 *
 *     onClick="cfCompareDate(mEditFromDate.text, mEditToDate.text )"
 *
 * </pre>
 * @sig    : start_date, end_date
 * @param  : start_date required 시작날짜, 형식:YYYYMMDD
 * @param  : end_date 	required 끝날짜, 형식:YYYYMMDD
 * @return : boolean
 * @author : foxlion
 */
function compareDate(start_date, end_date) {
    start_date = stripHyphen(start_date);
    end_date = stripHyphen(end_date);

    var f_year  = parseInt( start_date.substr(0,4), 10 );
    var f_month = parseInt( start_date.substr(4,2), 10 );
    var f_day   = parseInt( start_date.substr(6,2), 10 );
    var t_year  = parseInt( end_date.substr(0,4), 10 );
    var t_month = parseInt( end_date.substr(4,2), 10 );
    var t_day   = parseInt( end_date.substr(6,2), 10 );

    if( f_year > t_year )
        return false;
    else if( f_year == t_year && f_month > t_month )
        return false;
    else if( f_year == t_year && f_month == t_month && f_day > t_day )
        return false;

    return true;
}

function daysStringBetween(date1Str, date2Str) {
    date1Str = stripHyphen(date1Str);
    date2Str = stripHyphen(date2Str);
    debug("date1Str", date1Str);
    debug("date1Str.substring(0, 4)", date1Str.substring(0, 4));
    debug("date1Str.substring(4, 6)", date1Str.substring(4, 6));
    debug("date1Str.substring(6, 8)).after(0, 0, 1).format('YYYYMMDD')", date1Str.substring(6, 8));
    debug("date2Str", date2Str);

    var date1 = new Date(date1Str.substring(0, 4), date1Str.substring(4, 6), date1Str.substring(6, 8));
    var date2 = new Date(date2Str.substring(0, 4), date2Str.substring(4, 6), date2Str.substring(6, 8));

    var DSTAdjust = 0;
    // constants used for our calculations below
    oneMinute = 1000 * 60;
    var oneDay = oneMinute * 60 * 24;
    // equalize times in case date objects have them
    date1.setHours(0);
    date1.setMinutes(0);
    date1.setSeconds(0);
    date2.setHours(0);
    date2.setMinutes(0);
    date2.setSeconds(0);
    // take care of spans across Daylight Saving Time changes
    if (date2 > date1) {
        DSTAdjust =
            (date2.getTimezoneOffset( ) - date1.getTimezoneOffset( )) * oneMinute;
    } else {
        DSTAdjust =
            (date1.getTimezoneOffset( ) - date2.getTimezoneOffset( )) * oneMinute;
    }
    var diff = Math.abs(date2.getTime( ) - date1.getTime( )) - DSTAdjust;
    return Math.ceil(diff/oneDay);
}

function numeralsOnly(evt) {
    evt = (evt) ? evt : event;
    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :
        ((evt.which) ? evt.which : 0));
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        alert("숫자만 입력 가능합니다.");
//        alert("Enter numerals only in this field.");
        return false;
    }
    return true;
}

function ynOnly(evt) {
    evt = (evt) ? evt : event;
    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :
        ((evt.which) ? evt.which : 0));
    if (charCode > 31 && charCode != 78 && charCode != 89 &&
        charCode != 110 && charCode != 121) {
        alert("Enter \"Y\" or \"N\" only.");
        return false;
    }
    return true;
}

////////////////////
function autofocus(field, limit, next, evt) {
    evt = (evt) ? evt : event;
    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :
        ((evt.which) ? evt.which : 0));
    if (charCode > 31 && field.value.length == limit) {
        field.form.elements[next].focus( );
    }
}

function formatNumber (num, decplaces) {
    // convert in case it arrives as a string value
    num = parseFloat(num);
    // make sure it passes conversion
    if (!isNaN(num)) {
        // multiply value by 10 to the decplaces power;
        // round the result to the nearest integer;
        // convert the result to a string
        var str = "" + Math.round (eval(num) * Math.pow(10,decplaces));
        // exponent means value is too big or small for this routine
        if (str.indexOf("e") != -1) {
            return "Out of Range";
        }
        // if needed for small values, pad zeros
        // to the left of the number
        while (str.length <= decplaces) {
            str = "0" + str;
        }
        // calculate decimal point position
        var decpoint = str.length - decplaces;
        // assemble final result from: (a) the string up to the position of
        // the decimal point; (b) the decimal point; and (c) the balance
        // of the string. Return finished product.
        return str.substring(0,decpoint) + "." + str.substring(decpoint,str.length);
    } else {
        return "NaN";
    }
}

function formatCommas(numString) {
    var re = /(-?\d+)(\d{3})/;
    while (re.test(numString)) {
        numString = numString.replace(re, "$1,$2");
    }
    return numString;
}

function formatCommas2 (Val)  {
    OutString="";
    len=Val.length;
    if (len>=3) {
        while (len>0) {
            TempString=Val.substring(len-3, len)
            if (TempString.length==3) {
                OutString=","+TempString+OutString
                len=len-3;
            } else {
                OutString=TempString+OutString
                len=0
            }
        }
        if (OutString.substring(0, 1)==",")
            Val=OutString.substring (1, OutString.length)
        else
            Val=OutString
    }
    return (Val);
}

function formatCutterny(number){
    // number의 데이터형을 string으로 변환 - added by tgim
    number = number + "";;

    var rValue ='';

    var EnableChar = "-0123456789";
    var Chr='';
    var EnableNumber = '';				//EnableNumber: 순수 -를 포함한 문자열중 정수 부분

    // 소수 부분을 처리하기 위한 코드 - added by tgim
    var enableFractionChar = '0123456789'               //enableFractionChar: 소수 부분에 가능한 문자 집합
    var isFraction = false;                             //isFraction: 소수 부분이 있는지 표시하는 Flag
    var fraction = '';                                  //fraction: 소수 부분의 문자열
    var enableFraction = ''                             //enableFraction: 유효한 문자만이 포함된 소수 부분의 문자열

    // tgim - 소수점 이하를 다른 변수에 할당
    if (number.indexOf(".") != -1) {
        isFraction = true;
        fraction = number.substring(number.indexOf("."));
        number = number.substring(0, number.indexOf("."));

        // 소수 부분에 유효하지 않은 문자를 걸러냄
        for (var i=0;i<fraction.length;i++) {
            Chr = fraction.charAt(i);

            if (enableFractionChar.indexOf(Chr) != -1)
                enableFraction += Chr;
        }
    }
    // 소수 부분 처리 종료

    for (var i=0;i<number.length;i++) {
        Chr = number.charAt(i);

        if (EnableChar.indexOf(Chr) != -1)
            EnableNumber += Chr;
    }

    var Minus = false;
    var ABSNumber = '';					//ABSNumber : 절대값
    if (parseInt(EnableNumber)<0){		//Minus : -를 포함하는지 여부
        Minus = true
        ABSNumber = EnableNumber.substring(1, EnableNumber.length);
    }
    else{
        ABSNumber = EnableNumber;
    }

    if (ABSNumber.length < 4) {			//총길이가 3이하면 탈출
        rValue = ABSNumber
        if (Minus)
            rValue = "-"+ABSNumber;
        if (isFraction)
            rValue = rValue+"."+enableFraction;
        return rValue
    }

    var ReverseWords = ''				//ReverseWords : 뒤집어진 '-'를 제외한 문자열
    for(i=ABSNumber.length;i>=0;i--){
        if (ABSNumber.charAt(i)!='-')
            ReverseWords += ABSNumber.charAt(i);
    }

    rValue = ReverseWords.substring(0, 3);

    var dotCount = ReverseWords.length/3-1;	//','가 들어갈 갯수
    for (j=1;j<=dotCount;j++){
        for(i=0;i<ReverseWords.length;i++){
            if (i==j*3)
                rValue+=","+ReverseWords.substring(i, i+3);
        }
    }

    var elseN = ReverseWords.length%3;	//','를 다 붙인 후 남은 문자열 합체
    if (elseN!=0)
        rValue+= ","+ReverseWords.substring(ReverseWords.length-elseN, ReverseWords.length);

    ReverseWords = rValue;	//초기화
    rValue = '';				//초기화
    for(i=ReverseWords.length;i>=0;i--){
        if (ReverseWords.charAt(i)!='-')
            rValue += ReverseWords.charAt(i);
    }

    if (Minus)
        rValue = "-"+rValue;
    if (isFraction)
        rValue = rValue+"."+enableFraction;

    return rValue;
}

function stripCommas(numString) {

    numString = numString + "";

    var re = /,/g;
    return numString.replace(re,"");
}

function stripHyphen(numString) {

    numString = numString + "";

    var re = /-/g;
    return numString.replace(re,"");
}

// 해당월의 첫날을 가져오는 함수
function getFirstDay(sToday) {
    var day = new Date();
    var year = day.getYear();
    var month = day.getMonth() + 1;

    if ( month < 10 ) {
        month = "0" + month;
    }

    var currentDate = year + "" + month;

    if (sToday == null || sToday == "") {
        sToday = currentDate;
    }

    return sToday.substring(0, 6) + "01";
}

// 하루 후의 날짜를 리턴 
function getNextDay(dt) {
    if (dt == null || dt == "") {
        dt = new Date().after(0, 0, 1).format("YYYYMMDD");
    }

    dt = new Date(dt.substring(0, 4), dt.substring(4, 6) - 1, dt.substring(6, 8)).after(0, 0, 1).format("YYYYMMDD");
    return dt;
}

function textOnly(evt) {
    evt = (evt) ? evt : event;

    if (evt.keyCode == 13) {
        return true;
    }

		var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :
        ((evt.which) ? evt.which : 0));

    var str = String.fromCharCode(charCode);
    var re = /[a-zA-Z가-\uD7A3ㄱ-ㅎ0-9%.,)(\r\n\t ]/;
    if (!str.match(re)) {
        alert("영문, 한글, 숫자 및 공백 문자만 입력 가능합니다.");
        return false;
    } else {
        return true;
    }
}

function isText(elem) {
    var str = elem.value;
    var re = /^[a-zA-Z가-\uD7A3ㄱ-ㅎ0-9%.,)(\r\n\t ]*$/;
    if (!str.match(re)) {
    	  elem.focus();
    	  elem.select();
      	alert("영문, 한글, 숫자 및 공백 문자만 입력 가능합니다.");
        return false;
    } else {
        return true;
    }
}

function koreanOnly(evt) {
    evt = (evt) ? evt : event;

    if (evt.keyCode == 13) {
        return true;
    }

    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :
        ((evt.which) ? evt.which : 0));

//	debug("charCode", String.fromCharCode(charCode));
//	debug("escape(charCode)", escape(charCode));

    var str = String.fromCharCode(charCode);
//    var str = escape(charCode);
    var re = /[가-\uD7A3ㄱ-ㅎ]/;
    if (!str.match(re)) {
        alert("한글만 입력 가능합니다.");
        return false;
    } else {
        return true;
    }
}

function koreanNumeralsOnly(evt) {
    evt = (evt) ? evt : event;

    if (evt.keyCode == 13) {
        return true;
    }

    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :
        ((evt.which) ? evt.which : 0));

//	debug("charCode", String.fromCharCode(charCode));
//	debug("escape(charCode)", escape(charCode));

    var str = String.fromCharCode(charCode);
//    var str = escape(charCode);
    var re = /[가-\uD7A3ㄱ-ㅎ0-9]/;
    if (!str.match(re)) {
        alert("한글만 입력 가능합니다.");
        return false;
    } else {
        return true;
    }
}

function koreanOnly2(evt) {
    evt = (evt) ? evt : event;
    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :
        ((evt.which) ? evt.which : 0));

//	debug("charCode", String.fromCharCode(charCode));
//	debug("escape(charCode)", escape(charCode));

    var str = String.fromCharCode(charCode);
//    var str = escape(charCode);
    var re = /[가-\uD7A3ㄱ-ㅎ]/;
    if (!str.match(re)) {
        alert("한글만 입력 가능합니다.");
        return false;
    } else {
        return true;
    }
}

String.prototype.removeKO = function() {
    return this.replace(/[가-\uD7A3ㄱ-ㅎ]+/, "");
}

function alphaOnly(evt) {
//	debug("evt.charCode", evt.charCode);
//	debug("evt.keyCode", evt.keyCode);
//	debug("evt.which", evt.which);

    evt = (evt) ? evt : event;

    if (evt.keyCode == 13) {
        return true;
    }

    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :
        ((evt.which) ? evt.which : 0));

//	debug("String.fromCharCode(charCode)", String.fromCharCode(charCode));
//	debug("escape(charCode)", escape(charCode));
//	debug("charCode", charCode);

    var str = String.fromCharCode(charCode);
//    var str = escape(charCode);
    var re = /[a-zA-Z ]/;
    if (!str.match(re)) {
        alert("영문만 입력 가능합니다.");
        return false;
    } else {
        return true;
    }
}

function alphaCapOnly(evt) {
//	debug("evt.charCode", evt.charCode);
//	debug("evt.keyCode", evt.keyCode);
//	debug("evt.which", evt.which);

    evt = (evt) ? evt : event;

    if (evt.keyCode == 13) {
        return true;
    }

    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :
        ((evt.which) ? evt.which : 0));

//	debug("String.fromCharCode(charCode)", String.fromCharCode(charCode));
//	debug("escape(charCode)", escape(charCode));
//	debug("charCode", charCode);

    var str = String.fromCharCode(charCode);
//    var str = escape(charCode);
    var re = /[A-Z ]/;
    if (!str.match(re)) {
    alert("영문대분자만 입력 가능합니다.");
        return false;
    } else {
        return true;
    }
}

function isReadOnly(evt) {
//	debug("evt.charCode", evt.charCode);
//	debug("evt.keyCode", evt.keyCode);
//	debug("evt.which", evt.which);

    evt = (evt) ? evt : event;

    if (evt.keyCode == 13) {
        return true;
    }

//    if (evt.keyCode == 32) {
//        openCalender(obj);
//        return false;
//    }

    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :
        ((evt.which) ? evt.which : 0));

//	debug("String.fromCharCode(charCode)", String.fromCharCode(charCode));
//	debug("escape(charCode)", escape(charCode));
//	debug("charCode", charCode);

    var str = String.fromCharCode(charCode);
//    var str = escape(charCode);
    var re = /[ㄱ]/;
    if (!str.match(re)) {
        return false;
    } else {
        return true;
    }
}

function isKorean(str) {
    var re = /[가-\uD7A3ㄱ-ㅎ]/;
    if (!str.match(re)) {
        alert("한글만 입력 가능합니다.");
        return false;
    } else {
        return true;
    }
}

function isAlpha(str) {
    var re = /[a-zA-Z ]/;
    if (!str.match(re)) {
        alert("영문만 입력 가능합니다.");
        return false;
    } else {
        return true;
    }
}

function isKoreanObj(obj) {
	  var str = obj.value;
    var re = /^[가-\uD7A3ㄱ-ㅎ]*$/;
    if (!str.match(re)) {
        obj.focus();
        obj.select();
        alert("한글만 입력 가능합니다.");
        return false;
    } else {
        return true;
    }
}

function isAlphaObj(obj) {
    if (isNullOrSpace(obj)) {
        return true;
    }
    var re = /^[a-zA-Z ]*$/;
    if (!obj.value.match(re)) {
        obj.focus();
        obj.select();
        alert("영문만 입력 가능합니다.");
        return false;
    } else {
        return true;
    }
}

function isAlphaCapObj(obj) {
    if (isNullOrSpace(obj)) {
        return true;
    }
    var re = /^[A-Z ]*$/;
    if (!obj.value.match(re)) {
        obj.focus();
        obj.select();
        alert("영문대문자만 입력 가능합니다.");
        return false;
    } else {
        return true;
    }
}

function checkAlphaOnly(obj) {
//    debug("obj.value", obj.value);
    if (!isAlpha(obj.value)) {
//	    debug("obj.value.removeKO()", obj.value.removeKO());
        obj.value = obj.value.removeKO();
//	    debug("obj.value", obj.value);
        alert("영문만 입력 가능합니다.");
        obj.focus();
        obj.select();
    }
}

function trim(obj) {
    obj.value = obj.value.trim();
}

function checkResidentNO(no) {
    // 주민번호의 형태와 7번째 자리(성별) 유효성 검사
    fmt = /^\d{6}-[1234]\d{6}$/;
    if (!fmt.test(no)) {
        alert("잘못된 주민등록번호입니다."); return;
    }

    // 날짜 유효성 검사
    birthYear = (no.charAt(7) <= "2") ? "19" : "20";
    birthYear += no.substr(0, 2);
    birthMonth = no.substr(2, 2) - 1;
    birthDate = no.substr(4, 2);
    birth = new Date(birthYear, birthMonth, birthDate);

    if ( birth.getYear() % 100 != no.substr(0, 2) ||
        birth.getMonth() != birthMonth ||
        birth.getDate() != birthDate) {
        alert("잘못된 주민등록번호입니다."); return;
    }

    // Check Sum 코드의 유효성 검사
    buf = new Array(13);
    for (i = 0; i < 6; i++) buf[i] = parseInt(no.charAt(i));
    for (i = 6; i < 13; i++) buf[i] = parseInt(no.charAt(i + 1));

//    if(buf[6] == 5 || buf[6] == 6 || buf[6] == 7 || buf[6] == 8){
//    alert("ok");
//    	return true;
//    }

//    alert("Uoops!!");

    multipliers = [2,3,4,5,6,7,8,9,2,3,4,5];
    for (i = 0, sum = 0; i < 12; i++) sum += (buf[i] *= multipliers[i]);
	
	if (buf[6] >= 1 && buf[6] <= 4) {
	    if ((11 - (sum % 11)) % 10 != buf[12]) {
	        alert("잘못된 주민등록번호입니다."); return;
	    }
	} else {
	    if (((11 - (sum % 11)) % 10 + 2) % 10 != buf[12]) {
	        alert("잘못된 외국인등록번호입니다."); return;
	    }
	}
    alert("정상적인 주민등록번호입니다.");
}

function isValidResidentNO(no) {
 // 주민번호의 형태와 7번째 자리(성별) 유효성 검사 20071221 es 수정 (5,6번 주민번호 등록 안됨;;;)
 //   fmt = /^\d{6}-[1234]\d{6}$/;
 //   if (!fmt.test(no)) {
 //       alert("잘못된 주민등록번호입니다.");
 //       return false;
 //   }
    // 날짜 유효성 검사
    birthYear = (no.charAt(7) <= "2") ? "19" : "20";
    birthYear += no.substr(0, 2);
    birthMonth = no.substr(2, 2) - 1;
    birthDate = no.substr(4, 2);
    birth = new Date(birthYear, birthMonth, birthDate);

    if ( birth.getYear() % 100 != no.substr(0, 2) ||
        birth.getMonth() != birthMonth ||
        birth.getDate() != birthDate) {
        alert("잘못된 주민등록번호입니다.");
        return false;
    }
    // Check Sum 코드의 유효성 검사
    buf = new Array(13);
    for (i = 0; i < 6; i++) buf[i] = parseInt(no.charAt(i));
    for (i = 6; i < 13; i++) buf[i] = parseInt(no.charAt(i + 1));

     if(buf[6] == 5 || buf[6] == 6){
    	return true;
    } else {
    multipliers = [2,3,4,5,6,7,8,9,2,3,4,5];
    for (i = 0, sum = 0; i < 12; i++) sum += (buf[i] *= multipliers[i]);

    if ((11 - (sum % 11)) % 10 != buf[12]) {
        alert("잘못된 주민등록번호입니다.");
        return false;
    }
    }

    return true;
}

function isValidResidentNO_F(no) {
 // 주민번호의 형태와 7번째 자리(성별) 유효성 검사 20071221 es 수정 (5,6번 주민번호 등록 안됨;;;)
 //   fmt = /^\d{6}-[1234]\d{6}$/;
 //   if (!fmt.test(no)) {
 //       alert("잘못된 주민등록번호입니다.");
 //       return false;
 //   }
    // 날짜 유효성 검사
    birthYear = (no.charAt(7) <= "2") ? "19" : "20";
    birthYear += no.substr(0, 2);
    birthMonth = no.substr(2, 2) - 1;
    birthDate = no.substr(4, 2);
    birth = new Date(birthYear, birthMonth, birthDate);

    if ( birth.getYear() % 100 != no.substr(0, 2) ||
        birth.getMonth() != birthMonth ||
        birth.getDate() != birthDate) {
        alert("잘못된 주민등록번호입니다.");
        return false;
    }
    // Check Sum 코드의 유효성 검사
    buf = new Array(13);
    for (i = 0; i < 6; i++) buf[i] = parseInt(no.charAt(i));
    for (i = 6; i < 13; i++) buf[i] = parseInt(no.charAt(i + 1));
	
    if(no.charAt(13) == "F" || no.charAt(13) == "f"){
    	return true;
    } else {
	    multipliers = [2,3,4,5,6,7,8,9,2,3,4,5];
	    for (i = 0, sum = 0; i < 12; i++) sum += (buf[i] *= multipliers[i]);
		
		if(no.charAt(7)>= 1 && no.charAt(7) <=4){
		    if ((11 - (sum % 11)) % 10 != buf[12]) {
		        alert("잘못된 주민등록번호입니다.");
		        return false;
		    }
		}else if(no.charAt(7)>= 5 && no.charAt(7) <=8){
			if ((((11 - (sum % 11)) % 10 + 2) % 10) != buf[12]) {
		        alert("잘못된 외국인등록번호입니다.");
		        return false;
		    }
		}else{
			alert("성별이 잘못되었습니다.");
	        return false;
		}				    
    }

    return true;
}

function isValidBizRegNo(bizID)
{
        var checkID = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5, 1);
        var i, Sum=0, c2, remander;
 
        bizID = bizID.replace(/-/gi,'');
 
        for (i=0; i<=7; i++){
               Sum += checkID[i] * bizID.charAt(i);
        }
       
        c2 = "0" + (checkID[8] * bizID.charAt(8));
        c2 = c2.substring(c2.length - 2, c2.length);
       
        Sum += Math.floor(c2.charAt(0)) + Math.floor(c2.charAt(1));
       
        remander = (10 - (Sum % 10)) % 10 ;
       
        if(bizID.length != 10){
               return false;
        }else if (Math.floor(bizID.charAt(9)) != remander){
               return false;
        }else{
               return true;
        }
}

function isEquals(obj1, obj2) {
    if (obj1.value == obj2.value) {
        return true;
    } else {
        alert("두 입력값이 일치해야 합니다.");
        return false;
    }
}

function AlphaNumeralsOnlyNoSpace(evt) {
//	debug("evt.charCode", evt.charCode);
//	debug("evt.keyCode", evt.keyCode);
//	debug("evt.which", evt.which);

    evt = (evt) ? evt : event;

    if (evt.keyCode == 13) {
        return true;
    }

    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :
        ((evt.which) ? evt.which : 0));

//	debug("String.fromCharCode(charCode)", String.fromCharCode(charCode));
//	debug("escape(charCode)", escape(charCode));
//	debug("charCode", charCode);

    var str = String.fromCharCode(charCode);
//    var str = escape(charCode);
    var re2 = /^[-]?\d*\.?\d*$/;
    var re = /[a-zA-Z]/;
    if (!(str.match(re) || str.match(re2))) {
        alert("영문과 숫자만 입력 가능합니다.");
        return false;
    } else {
        return true;
    }
}

///////////////////////////////////////////////////////////////////////////////////
// 글자나 숫자 수 카운트 하는 함수
//사용 예제
// 1.문자 <varchar2(30)의 경우>
//<html:text property="searchVO.txt" name="xxxForm" onkeyup="chk_vali(this,30,'text');" styleClass="lineInput"   maxlength="50" size="50" title="내용" ></html:text>
//
// 2.숫자 <3자리숫자마다 ( , ) 적용 ,number(22)일때 화면에 기본이 백만원일 경우. number 16적용 >
//<html:text property="searchVO.amt" name="xxxForm" onkeyup="chk_vali(this,16,'num');" styleClass="lineInputNum" maxlength="50" size="50" title="내용" ></html:text>
///////////////////////////////////////////////////////////////////////////////////
function chk_vali(targetObj, maxLength,gbn){
    var tmpStr;
    var count=0;
    var onechar;
    var tcount;
    tcount = 0;
    if(gbn=='num') tmpStr = new String(stripCommas(targetObj.value));
    else if(gbn=='text') tmpStr = new String(targetObj.value);
    else if(gbn=='tel') tmpStr = new String(targetObj.value);
    count = tmpStr.length;

    for (k=0;k<count;k++)
    {
        onechar = tmpStr.charAt(k);
        if (escape(onechar) =='%0D') {
            tcount += 3;
        } else if (escape(onechar).length > 4) {
            tcount += 2;
        } else {
            tcount++;
        }
    }

    if(tcount>parseInt(maxLength)) {
        reserve = tcount-parseInt(maxLength);
        if(gbn=='tel') alert("쓰신 내용은 "+reserve+"바이트가 초과되었습니다.\n초과된 부분 및 숫자외 문자는 자동으로 삭제됩니다.");
        else alert("쓰신 내용은 "+reserve+"바이트가 초과되었습니다.\n초과된 부분은 자동으로 삭제됩니다.");
        cut_vali(targetObj,maxLength,gbn);
        return;
    }
    if(gbn=='num') targetObj.value = formatCutterny(tmpStr);
    if(gbn=='tel') targetObj.value = tmpStr.maskPhoneNo();
}
///////////////////////////////////////////////////////////////////////////////////
// 초과된 문자나 숫자를 자동삭제하는 부분
///////////////////////////////////////////////////////////////////////////////////
function cut_vali(targetObj, maxLength,gbn){
    var tmpStr;
    var temp=0;
    var onechar;
    var tcount;
    tcount = 0;

    if(gbn=='num') tmpStr = new String(stripCommas(targetObj.value));
    else if(gbn=='text') tmpStr = new String(targetObj.value);
    else if(gbn=='tel') tmpStr = new String(targetObj.value);
    temp = tmpStr.length;

    for(k=0;k<temp;k++)
    {
            onechar = tmpStr.charAt(k);

            if(escape(onechar).length > 4) {
                    tcount += 2;
            } else {
                    // 엔터값이 들어왔을때 값(rn)이 두번실행되는데 첫번째 값(n)이 들어왔을때 tcount를 증가시키지 않는다.
                    if(escape(onechar)=='%0D') {
                      tcount += 3;
                    } else {
                            tcount++;
                    }
            }

            if(tcount>parseInt(maxLength)) {
                    tmpStr = tmpStr.substring(0,k);
                    break;
            }
    }
    if(gbn=='num') targetObj.value = formatCutterny(tmpStr);
    else if(gbn=='text') targetObj.value = tmpStr;
    else if(gbn=='tel') targetObj.value = tmpStr;
    chk_vali(targetObj, maxLength,gbn);
}
/******************************
*  기능 :  TelNumber Check      *
*******************************/
function isNotValidTel(field) {

   if(field.value.length < 11){
		alert("올바른 전화번호를 입력해주세요");
		return false;
	}
	var partStr=field.value.split("-");
	var lastP=partStr.length-1;
	if(partStr[lastP].toString().length!=4){
		alert("올바른 전화번호를 입력해주세요");
		return false;
	};
	return true;
}

function sosu(num, len){

        var strNum = String(num);
        var i = strNum.indexOf(".")

        if (isNaN(num)) return 0;
        if (! isFinite(num))return 0;
        if(i==-1) {
        	return Math.round(strNum);
        } else {
            var num1 = strNum.split(".")[0];
            var num2 = strNum.split(".")[1];

            if(len > num2.length) {
            	return strNum;
            } else {
            	var tmpNum = String(Math.round(num2.substring(0,len)+"."+num2.substring(len)));
				return parseFloat(num1+"."+tmpNum.substring(0,len));
            }
        }
}
////////////////////


// added by tgim
function toNumber(val) {
    val = parseFloat(stripCommas(val));

    if((!isNaN(val)) && (val !=Infinity)) {
        return val;
    } else {
        return 0;
    }
}

// dividend를 divisor로 나눈다. divisor가 0일경우 0을 반환한다.
function divide(dividend, divisor) {
    dividend = toNumber(dividend);
    divisor = toNumber(divisor);

    if (divisor == 0) {
        return 0;
    } else {
        return dividend / divisor;
    }
}

function isEqual(val1, val2) {
    if (toNumber(val1) == toNumber(val2)) {
        return true;
    } else {
        return false;
    }
}

function isGreaterThan (val1, val2) {
    if (toNumber(val1) > toNumber(val2)) {
        return true;
    } else {
        return false;
    }
}

function isGreaterEqual (val1, val2) {
    if (isGreaterThan(val1, val2) || isEqual(val1, val2)) {
        return true;
    } else {
        return false;
    }
}

function isLessEqual (val1, val2) {
    if (isGreaterEqual(val2, val1)) {
        return true;
    } else {
        return false;
    }
}

function isLessThan (val1, val2) {
    if (isGreaterThan(val2, val1)) {
        return true;
    } else {
        return false;
    }
}

function isValidFileFormat(filename){
	return isValidFileFormat(filename, null);
}

function isValidFileFormat(filename, extArr){

	var nm;
	var ext;
	var isValid = false;
	var noextArr = new Array("jsp","php","asp","cgi");
	var no_cot = 0;
	var yes_cot = 0;
	if(filename != null && filename != ""){
		nm = filename;

		while(nm.indexOf("\\") != -1){
			nm = nm.slice(nm.indexOf("\\") + 1);
		}

		if(nm.indexOf(".") != -1){
			//확장자
			ext  = nm.slice(nm.indexOf(".") + 1).toLowerCase();
		}
		
		if(noextArr != null){
			for(var i = 0 ; i < noextArr.length ; i++){
				if(noextArr[i] == ext){
					no_cot++;
				}
			}
		
			if(no_cot > 0){
				alert("허용된 형식에 맞지 않는 파일을 선택하였습니다.\n\n파일명: "+filename);
				return false;
			}
		}

		if(extArr != null){
			for(var i = 0 ; i < extArr.length ; i++){
				if(extArr[i] == ext){
					yes_cot++;
				}
			}
		
			if(yes_cot == 0){
				alert("허용한 형식의 파일만 선택하세요.\n\n"+extArr+" 파일을 허용합니다.");
				return false;
			}
		}

		return true;
	}else{
		//alert("파일명이 없습니다.");
		return true;
	}
}

//숫자와 ','  값인지 체크
function isPerCheck(theField, msg)
{
  trim(theField);
  var inStr = theField.value;
  var inLen = theField.value.length;
  for (var i=0; i< inLen; i++){
    var ch = inStr.charAt(i);
    if ( ( ch < "0" || ch > "9")&&(ch!=".") ) {
      alert(msg);
      theField.select();
      theField.value = "";
      return false;
    }
  }
  return true;
}

//숫자와 ','  값인지 체크 후 지우고 포커스
function isPerCheckDF(theField){
  trim(theField);
  var inStr = theField.value;
  var inLen = theField.value.length;
  for (var i=0; i< inLen; i++){
    var ch = inStr.charAt(i);
    if ( ( ch < "0" || ch > "9")&&(ch!=".") ) {
      alert("숫자와 소수점만 입력해주세요.");
      theField.value = "";
      theField.focus();
      return false;
    }
  }
  return true;
}

/*
 * item : textarea
 * limitLength : 제한바이트 수(byte)
 */
function checkTextLength(item, limitLength){

	var rLength = getByte(item.value);
	var lLength = eval(limitLength);

	if(rLength > limitLength){
		alert("'"+item.title + "' 항목의 글자수는 "+lLength+"바이트 (한글 "+lLength/2+"자, 영문 "+lLength+"자)로 제한되어 있습니다.\n"
				+ "입력하신 내용은 "+rLength+" 바이트를 사용했습니다.");
		return false;
	}else{
		return true;
	}
}