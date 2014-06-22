var xmlHttpRequest = null;

function createXMLHttpRequest(){ //서버와 연동할 객체를 생성하는 함수
	if(window.ActiveXObject){ //window 용
		xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
	}else{ //그 외
		xmlHttpRequest = new XMLHttpRequest();
	}
}
/**
 * @type   : function
 * @access : public
 * @desc   : 요청을 초기화 한 후 요청을 서버로 전송한다.
 * <pre>
 *     createXMLHttpRequest();
 * </pre>
 * @sig    :
 * @param  :
 * @return :
 * @author : naruma
 */
function cfXmlHttpRequestSend(url, method, content)
{
    createXMLHttpRequest();
    xmlHttpRequest.onreadystatechange = handleStateChange;

    if(!method) { method = "GET"; }

    xmlHttpRequest.open(method, url, true);
    if(method) { // method 가 POST 라면
      xmlHttpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    }

    xmlHttpRequest.send(content);
}
/**
 * @type   : function
 * @access : public
 * @desc   : 요청을 초기화 한 후 요청을 서버로 전송한다.
 * <pre>
 *     createXMLHttpRequest();
 * </pre>
 * @sig    :
 * @param  :
 * @return :
 * @author : naruma
 */
function cfXmlHttpRequestSend2(url, method, content)
{
    createXMLHttpRequest();
    xmlHttpRequest.onreadystatechange = handleStateChange;

    if(!method) { method = "GET"; }

    xmlHttpRequest.open(method, url, true);    
    xmlHttpRequest.send(content);
}
/**
 * @type   : function
 * @access : public
 * @desc   : 서버에 갔다 온 후 처리를 지시한다.
 * <pre>
 *     createXMLHttpRequest();
 * </pre>
 * @sig    :
 * @param  :
 * @return :
 * @author : naruma
 */
function handleStateChange()
{
    if(xmlHttpRequest.readyState == 4)
    {
        if(xmlHttpRequest.status == 200)
        {
            processBiz();
        }        
    }
}
/**************************************************/
// Select Element에 Option Element를 추가한다.
// [Arguments]
// oParentElement(mandatory) : Option Element를 추가할 Select Element
// sValue(mandatory) : Option Element에 추가할 value 속성값
// sText : Option Element에서 화면에 보여줄 text 속성값
// [browser] : MSIE 6.0 , Mozilla Firefox 1.0.7
// [Author] : naruma
/**************************************************/
function addOptionElement(oParentElement, sValue, sText)
{    
    //oOption = createElementByTagName("OPTION", oParentElement);    
    var oOption = document.createElement("OPTION");
    oOption.setAttribute("value", sValue);
    var textNd = document.createTextNode(sText);
    oOption.appendChild(textNd);
    oParentElement.appendChild(oOption);
    return oOption;
    //oOption.value = sValue;
    //oOption.text = sText;
}
/**************************************************/
// select element의 모든 option 객체를 제거한다.
// [Return Type] : Boolean
// [Returns] : remove가 성공적으로 수행되면 true를 반환한다. 그렇지 않으면 false를 반환한다.
// [Arguments]
// oElement(mandotory) : remove할 target Select Element
// [Browser] : MSIE6.0, FIREFOX1.0.7
// [Author] : naruma
/**************************************************/
function removeAllOptions(oElement)
{
    for(var i=oElement.options.length-1; i>=0; --i)
    {
        oElement.removeChild(oElement.options[i]);
    }
    return true;
}
/**
 * @type   : function
 * @access : public
 * @desc   : 서버에서 가져온 XML을 파싱해서 Combo 박스에 set 한다.
 * <pre>
 *     var xhr = new XmlHttpRequest();
 *     xhr.processBiz = functionName;
 * </pre>
 * @sig    :
 * @param  :
 * @return :
 * @author : naruma
 */
function cfSetSubCodeCombo(oSelect, style, isBlank)
{
    var result = xmlHttpRequest.responseXML.getElementsByTagName("option");
    if(style == null || style == "") style = "";    
    var p = oSelect.parentNode;
    p.removeChild(oSelect);
    var strHtml = "<select name='" + oSelect.name + "' id='" + oSelect.id + "' " + style + ">";
    if(isBlank) strHtml += "<option value=''></option>";
    for(var i=0; i<result.length; ++i) {
        strHtml += "<option value='" + result[i].getAttribute("value") + "'>" + result[i].getAttribute("name") + "</option>";
    }
    strHtml += "</select>";
    //alert(strHtml);
    p.innerHTML = strHtml;
}
/**************************************************/
// 태그이름에 해당하는 객체를 생성한다.
// oParent 객체값이 전달되면 생성된 객체를 oParent의 child로 설정한다.
// [Return Type] : element
// [Returns] : 태그이름에 해당하는 객체를 생성해서 반환한다.
// [Arguments]
// sTagName(mandotory) : tag name
// oParent : parent element
// [Browser] : MSIE6.0, FIREFOX1.0.7
// [Author] : naruma
/**************************************************/
function createElementByTagName(sTagName, oParent)
{
    var oDocument = document;
    if(oParent != null) oDocument = oParent.ownerDocument;

    var oElement = oDocument.createElement(sTagName.toUpperCase());
    //if(isValidElement(oParent))
    //{
        oParent.appendChild(oElement);
    //}
    return oElement;
}
/**
 * @type   : function
 * @access : public
 * @desc   : 폼에서 엘러먼트 이름의 일부분에 해당하는 모든 엘러먼트들을 get 한다.
 * <pre>
 *     엘러먼튼 이름 중에서 nara가 들어가는 모든 엘러먼트들을 get 한다.
 *     cfGetElementsBySubName(document.forms[0], "nara")
 * </pre>
 * 위와같이 사용했을 경우 true를 리턴한다.
 * @sig    : oForm, sElementSubName
 * @param  : oForm 폼객체
 * @param  : sElementSubName 찾으려는 엘러먼트 이름의 일부분
 * @return : boolean. 전부 유효하면 true, 하나라도 유효하지 않으면 false
 * @author : naruma
 */
function cfGetElementsBySubName(oForm, sElementSubName)
{
    var aReturn = new Array();
    var index = 0;
    for(var i=0; i<oForm.length; ++i)
    {
        if(oForm[i].name.search(sElementSubName) != -1)
        {
            aReturn[index++] = oForm[i];
        }
    }
    return aReturn;
}