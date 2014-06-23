function pageSubmit(fmObj, module, pageUrl) {
    fmObj.action = module + pageUrl;
    fmObj.submit();
}

function getValByte(tmpStr){
    var tmpStr;  
    var count=0;
    var onechar;
    var tcount;
    tcount = 0;
    count = tmpStr.length;

    for (k=0;k<count;k++)
    {
        onechar = tmpStr.charAt(k);
       if (escape(onechar) =='%0D') {
            tcount ++ ;
        }else if (escape(onechar) =='%B7') {
            tcount +=2 ;
        }else if(escape(onechar) =='%0A') {
        } else if (escape(onechar).length > 4) {
            tcount += 2;
        } else {
            tcount++;
        }
    }
	return tcount;
}


/* 
유사과제 전용
2012.07.09 박인선
*/

function countByte(targetObj, countObj, maxLength){
    var tmpStr;
    var count=0;
    var onechar;
    var tcount;
    tcount = 0;
    tmpStr = new String(targetObj.value);
    count = tmpStr.length;

    for (k=0;k<count;k++)
    {
        onechar = tmpStr.charAt(k);
     
        if (escape(onechar) =='%0D') {
            tcount ++ ;
        }else if (escape(onechar) =='%B7') {
            tcount +=2 ;
        }else if(escape(onechar) =='%0A') {
        } else if (escape(onechar).length > 4) {
            tcount += 2;
        } else {
            tcount++;
        }
    }

    countObj.value = tcount;

    if(tcount>parseInt(maxLength)) {
        reserve = tcount-parseInt(maxLength);
        alert("쓰신 내용은 "+reserve+"바이트가 초과되었습니다.\n초과된 부분은 자동으로 삭제됩니다.");
        cutText(targetObj, countObj, maxLength);
        return;
    }
}

function cutText(targetObj, countObj, maxLength)
{
    var tmpStr;
    var temp=0;
    var onechar;
    var tcount;
    tcount = 0;

    tmpStr = new String(targetObj.value);
    temp = tmpStr.length;

    for(k=0;k<temp;k++)
    {
            onechar = tmpStr.charAt(k);

            if(escape(onechar).length > 4) {
                    tcount += 2;
            } else {
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
    targetObj.value = tmpStr;
    countByte(targetObj, countObj, maxLength);
}


/**
 * String.replaceAll(from, to) method ????
 */
String.prototype.replaceAll = function(from, to) {
	return this.replace(new RegExp(from, "g"), to); 
}
/**
 * String.removeAll(ch) method ????
 */
String.prototype.removeAll = function(ch) {
	return this.replaceAll(ch, "");
}
/**
 * String.reverse() method ????
 */
String.prototype.reverse = function() {
	var result = "";
	var i = this.length;

	while (i > 0) {
		result += this.charAt(--i);
	}

	return result;
}
/**
 * String.isEmpty() method ????
 */
String.prototype.isEmpty = function() {
    return this.trim().length == 0;
}


