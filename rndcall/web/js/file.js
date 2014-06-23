function addFile1(){
     var iRow = fileAttachTable.insertRow(); //
     iRow.id = "tr_" + fileAttachTable.rows.length;
	
	var iRow1 = fileAttachTable1.insertRow(); //
     iRow1.id = "tr_" + fileAttachTable1.rows.length;
     
    var idx = fileAttachTable.rows.length - 1;
    if(idx ==0){
		setCellInnerHTML_title(iRow1);
	}
    setCellInnerHTML(iRow);
}

function delFile1() {
    if (fileAttachTable.rows.length == 0) {
        return;
    }else{
	    fileAttachTable1.deleteRow(fileAttachTable1.rows.length - 1);
        fileAttachTable.deleteRow(fileAttachTable.rows.length - 1);
    }
}


function addFile2(){
     var iRow = fileAttachTable.insertRow(); //
     iRow.id = "tr_" + fileAttachTable.rows.length;
	/*
	var iRow1 = fileAttachTable1.insertRow(); //
     iRow1.id = "tr_" + fileAttachTable1.rows.length;
    */
    var idx = fileAttachTable.rows.length - 1;
    setCellInnerHTML(iRow);
}

function delFile2() {
    if (fileAttachTable.rows.length == 0) {
        return;
    }else{
        fileAttachTable.deleteRow(fileAttachTable.rows.length - 1);
    }
}



function setCellInnerHTML(row) {

    var iCol = row.insertCell(0);
    var jCol = row.insertCell(1);
    var index = fileAttachTable.rows.length - 1;
	
    iCol.className = "putTdTitle";
    iCol.setAttribute("width", "120");
    iCol.setAttribute("height", "30");
    iCol.innerHTML = "첨부파일 : ";
    jCol.className = "putTd";
    jCol.innerHTML = "<input name='vo.putFile[" + index + "]' type='file'  title='첨부파일' size=50>" ;
}

function setCellInnerHTML_title(row) {

    var iCol = row.insertCell(0);
    var jCol = row.insertCell(1);
    var index = fileAttachTable.rows.length - 1;
	if(index ==0){
		iCol.className = "putTdTitle";
	    iCol.setAttribute("width", "120");
	    iCol.innerHTML = "첨부파일 제목 :";
	    jCol.className = "putTd";
	    jCol.innerHTML = "<input name='vo.file_title' type='text'  title='첨부파일 제목' size=50>" ;
	}
}

/**
 * 파일 입력폼을 추가한다.
 * 사용법 : 1. 파일입력폼이 추가되기 원하는 곳에 <div> 태그를 만들고 id 를 지정한다.
 *        예) <div id="fileArea"></div>
 *        2. 위에서 만든 id 와 크기로 fncFileAdd 함수를 호출한다.
 *        예) fncFileAdd('fileArea', '70');
 *        3. 그러면 fileArea 안에 파일입력폼이 하나씩 추가된다.
 */
 var fileId = 1;
function fncFileAdd(fileObjName, size) {
    var divEl = document.createElement("DIV");
    var fileArea = document.getElementById(fileObjName);
    var inputEl = document.createElement("INPUT");
    inputEl.setAttribute("type", "file");
    inputEl.setAttribute("size", size);
    fileId = fileId + 1;
    inputEl.setAttribute("name", "files"+fileId);
    inputEl.className = "fileForm";
    divEl.style.marginTop = "6px";
    divEl.appendChild(inputEl);
    fileArea.appendChild(divEl);
}
/*
 * 파일 입력폼을 삭제한다.
 * 사용법 : 1. 삭제되길 원하는 파일입력폼 이름을 파라미터로 해서 함수를 호출한다.
 *        예) fncFileDel('fileArea');
 */
function fncFileDel(fileObjName) {
    var fileArea = document.getElementById(fileObjName);
    var childNds = fileArea.childNodes;
    if(childNds.length != 0) {
        fileArea.removeChild(childNds[childNds.length-1]);
    }
    /*
    var inputFileLength = $("input[type=file]").length;    
    if(inputFileLength != 0) {
        var el = $("input[type=file]").get(inputFileLength-1);
        fileArea.removeChild(el);
    }
    */
}