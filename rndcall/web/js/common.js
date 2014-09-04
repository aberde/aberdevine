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


/**
 * 비회원 비밀번호 확인 폼.
 * @param baord_type
 * @param baord_seq
 */
function goPasswordCheckForm(baord_type, baord_seq, func) {
	var html = '<div id="passwordCheckDiv" class="passwordCheckForm" style="width: ' + $(window).width() + 'px; height: ' + $(document).height() + 'px; position: absolute; z-index: 9000;background-color:#000;left: 0px; top: 0px; opacity: 0.5;filter: alpha(opacity = 50);">';
	html += '</div>';
	html += '<!-- pop -->                                                                                                        ';
	html += '<div id="pop-join" class="passwordCheckForm">                                                                                                 ';
	html += '	<div class="tit">                                                                                                ';
	html += '		<strong>비밀번호</strong>                                                                                    ';
	html += '	</div>                                                                                                           ';
	html += '	<!-- board-write -->                                                                                             ';
	html += '	<div class="board-write">                                                                                        ';
	html += '		<div class="board-box">                                                                                      ';
	html += '			<table summary="비밀번호">                                                                               ';
	html += '				<caption>비밀번호</caption>                                                                          ';
	html += '				<colgroup>                                                                                           ';
	html += '					<col width="20%"/>                                                                               ';
	html += '					<col width="*"/>                                                                                 ';
	html += '				</colgroup>                                                                                          ';
	html += '				<tbody>                                                                                              ';
	html += '					<tr>                                                                                             ';
	html += '						<th scope="row"><label for="id">비밀번호</label></th>                                        ';
	html += '						<td>                                                                                         ';
	html += '							<input type="hidden" id="passwordCheck_baord_type" value="' + baord_type + '" />         ';
	html += '							<input type="hidden" id="passwordCheck_baord_seq" value="' + baord_seq + '" />         ';
	html += '							<input type="password" id="passwordCheck_baord_password" style="width:200px"/>                                         ';
	html += '							<span class="btn-set set2 s-blue"><a href="javascript:' + func + '">확인</a><span class="zoom"></span></span>  ';
	html += '						</td>                                                                                        ';
	html += '					</tr>                                                                                            ';
	html += '					<tr>                                                                                             ';
	html += '						<td colspan="2" class="txt-c bdl-n" >                                                        ';
	html += '							<span>                                                                                   ';
	html += '								이 게시물의 비밀번호를 입력하십시오.                                                 ';
	html += '							</span>                                                                                  ';
	html += '						</td>                                                                                        ';
	html += '					</tr>                                                                                            ';
	html += '				</tbody>                                                                                             ';
	html += '			 </table>                                                                                                ';
	html += '		</div>                                                                                                       ';
	html += '	</div>                                                                                                           ';
	html += '	<!-- // board-write -->                                                                                          ';
    html += '                                                                                                                    ';
	html += '	<a href="#" class="btn-close" onclick="$(\'.passwordCheckForm\').remove();"><span >닫기</span></a>                                        ';
	html += '</div>                                                                                                              ';
	html += '<!-- // pop -->                                                                                                     ';

	if ( $('#passwordCheckDiv').length > 0 ) {
		$('#passwordCheckDiv').remove();
	}
	if ( $('#pop-join').length > 0 ) {
		$('#pop-join').remove();
	}
	
	$('body').css('position','relative').append(html);
	var $layerPopupObj = $('#pop-join');
	var top = ( $(window).scrollTop() + ($(window).height() - $layerPopupObj.height()) / 2 );
	var left = ( $(window).scrollLeft() + ($(window).width() - $layerPopupObj.width()) / 2 );
	$layerPopupObj.css({'left':left,'top':top, 'position':'absolute', 'z-index':'9999'});
}

/**
 * 비회원 비밀번호 확인
 * @param baord_type 게시판 타입
 * @param baord_seq 게시판 일련번호
 */
function goPasswordCheck() {
	$.ajax({
		url: "/inquire/Inquire.do"
		, data: {
			method: "getPasswordCheck"
			, "vo.seq": $("#passwordCheck_baord_seq").val()
			, "vo.password": $("#passwordCheck_baord_password").val()
		}
		, success: function(data) {
			if ( data.success == "true" ) {
				var baord_type = $("#passwordCheck_baord_type").val();
				if ( baord_type == "OFFER" ) {
					offerDetailView($("#passwordCheck_baord_type").val(), $("#passwordCheck_baord_seq").val());
				} else {
					goInquireView($("#passwordCheck_baord_type").val(), $("#passwordCheck_baord_seq").val());
				}
				$("#passwordCheckDiv").remove();
			} else {
				alert("비밀번호를 확인해 주세요.");
			}
		}
	});
}

/****************************************************
테이블 병합
tbl      : 병합할 대상 table object
startRow : 병합 시작 row, title 한 줄일 경우 1
cNum     : 병합 실시할 컬럼번호, 0부터 시작
length   : 병합할 row의 길이, 보통 1
add      : 비교할 기준에 추가할 컬럼번호
          A | 1
          B | 1
         을 서로 구분하고 싶다면, add에 0번째
         컬럼을 추가
*****************************************************/
function mergeCell(tbl, startRow, cNum, length, add) {
	var isAdd = false;
	if(tbl == null) return;
	if(startRow == null || startRow.length == 0) startRow = 1;
	if(cNum == null || cNum.length == 0) return ;
	if(add  == null || add.length == 0) {
		isAdd = false;
	}else {
		isAdd = true;
		add   = parseInt(add);
	}
	cNum   = parseInt(cNum);
	length = parseInt(length);
	
	rows   = tbl.rows;
	rowNum = rows.length;
	
	tempVal  = '';
	cnt      = 0;
	startRow = parseInt(startRow);
	for( i = startRow; i < rowNum; i++ ) { 
		curVal = rows[i].cells[cNum].innerHTML.trim();
		if(isAdd) curVal += rows[i].cells[add].innerHTML.trim();
		if( curVal == tempVal ) {
			if(cnt == 0) {
				cnt++;
				startRow = i - 1;
			}
			cnt++;
		}else if(cnt > 0) {
			merge(tbl, startRow, cnt, cNum, length);
			startRow = endRow = 0;
			cnt = 0;
		}else {
		}
		tempVal = curVal;
	}

	if(cnt > 0) {
		merge(tbl, startRow, cnt, cNum, length);
	}
}

/*******************************************
mergeCell에서 사용하는 함수
********************************************/
function merge(tbl, startRow, cnt, cellNum, length) {
	rows = tbl.rows;
	row  = rows[startRow];
	
	for( i = startRow + 1; i < startRow + cnt; i++ ) {
		for( j = 0; j < length; j++) {
			rows[i].deleteCell(cellNum);
		}
	}
	for( j = 0; j < length; j++) {
		row.cells[cellNum + j].rowSpan = cnt;
	}
}

/**
 * jquery outerHTML함수 추가.
 */
$.fn.outerHTML = function() {
    var el = $(this);
    if( !el[0] ) return "";
 
    if (el[0].outerHTML) {
        return el[0].outerHTML;
    } else {
        var content = el.wrap('<p/>').parent().html();
        el.unwrap();
        return content;
    }
}