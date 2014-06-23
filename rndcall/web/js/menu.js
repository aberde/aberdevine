////////////////////////////////////////////////////////////////////////////////
// 제  목 : 메뉴 처리 스크립트
// 사용법 : 메뉴의 id는 다음과 같이 설정한다.
//		* 상단메뉴
//			1. 메인메뉴 부분의 id는 "topmain"
//			2. 서브메뉴 부분의 id는 "topsub"
//		* 좌측메뉴
//			1. 메인메뉴 부분의 id는 "leftmain"
//			2. 서브메뉴 부분의 id는 "leftsub"
//		* 선택 메뉴 위치 포인트 이미지의 id는 "selPoint"
////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
// 상단 메인 메뉴 처리 스크립트
// 사용법 :
//		topMenuSel(mode, sel, subpos)
//			mode : 동작모드
//				- m:메인메뉴선택
//				- i:서브메뉴 MouseOver 이벤트 처리
//				- o:서브메뉴 MouseOut 이벤트 처리
//			sel : 선택된 메뉴의 index
//			subpos : 서브 메뉴 표시 위치 지정
//		예1-메인메뉴선택시) topMenuSel('m',0,200);
//		예2-메인메뉴벗어날때) topMenuSel('m',-1);
//		예3-서브메뉴선택시) topMenuSel('i',0); topMenuSel('o',0);
////////////////////////////////////////////////////////////////////////////////
function topMenuSel(mode, sel, subpos){
	var mainobj = document.getElementsByName("topmain");
	var subobj = document.getElementsByName("topsub");

	switch(mode) {
	case "m" :
		for (i=0; i<subobj.length; i++ ) {
			subobj[i].style.display = "none";
		}

		if (sel == -1) {

		} else {
			if (subobj[sel].style.display != "none") {
				subobj[sel].style.display = "none";
			} else {
				subobj[sel].style.left = subpos + "px";
				subobj[sel].style.display = "";
			}
		}
		break;
	case "i" :		// 서브메뉴에 마우스가 올라왔을 때
		subobj[sel].style.display = "";
		break;
	case "o" :
		subobj[sel].style.display = "none";
		break;
	}

}

////////////////////////////////////////////////////////////////////////////////
// 좌측 메뉴 처리 스크립트
// 사용법 :
//		leftMenuSel(sel)
//			sel : 선택된 메뉴의 index
//		예) leftMenuSel(0);
////////////////////////////////////////////////////////////////////////////////
function leftMenuSel(sel){
	var mainobj = document.getElementsByName("leftmain");
	var subobj = document.getElementsByName("leftsub");

	for (i=0; i<subobj.length; i++ ) {
		mainobj[i].className = "";
		subobj[i].style.display = "none";
	}

	if (subobj[sel].style.display != "none") {
		mainobj[sel].className = "";
		subobj[sel].style.display = "none";
	} else {
		mainobj[sel].className = "selected";
		subobj[sel].style.display = "";
	}
}

////////////////////////////////////////////////////////////////////////////////
// 마우스 클릭 점선 없애기
////////////////////////////////////////////////////////////////////////////////
function bluring() {
	if(event.srcElement.tagName=="A"||event.srcElement.tagName=="IMG") document.body.focus();
}

function downLoad(fileID, sourceFileNM, saveFileNM, filePath)
{
	var fm = document.downLoad;
	fm.fileID.value=fileID;
	fm.sourceFileNM.value=sourceFileNM;
	fm.saveFileNM.value=saveFileNM;
	fm.filePath.value=filePath;
	fm.submit();
}

