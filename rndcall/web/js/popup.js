//*********************************************************************
//  팝업창 관련 스크립트. window.open 팝업 및 모달, 모달리스 팝업창 모두 가능.
//  제작자 : jhbae
//  사용법 : 1. window.open 팝업창
//           (1) 기본 띄우는 방법  
//             new Popup(url, 넓이, 높이, 팝업창이름).open();
//           (2) 속성변경을 하고싶은 경우
//             var pop = new Popup(url, 넓이, 높이, 팝업창이름);
//                 pop.status = "no";    // 상태바가 안나오도록.
//                 pop.location = "yes"; // 주소창이 나오도록 설정.
//                 pop.open(); // 속성이 적용된 팝업창이 나타난다.
//          2. 모달 팝업창
//            (1) 기본 띄우는 방법
//              var arg = new Object(); // 전달할 속성이 있는경우.
//              var returnValue = new Dialog(url, 넓이, 높이, arg).open();
//*********************************************************************

function Popup(sUrl, nWidth, nHeight, winName) {
	this.url = null;
    this.windowName = null;
    this.features = "";
    this.pos_width=0;
    this.pos_height=0;
    
    // window.open features
    this.left="0";
    this.top="0";
    this.width="0";
    this.height="0";
    this.toolbar="no";
    this.location="no";      
    this.menubar="no";
    this.scrollbars="yes";    
    this.resizable="yes";
    this.status="yes";
    
    if(nWidth)  this.pos_width = nWidth; 
    if(nHeight) this.pos_height = nHeight;    
    
    nWidth = (typeof(nWidth) == "number")?nWidth+"px":nWidth;
    nHeight = (typeof(nHeight) == "number")?nHeight+"px":nHeight;
    
    // ----------------------------------------------------------------------
    // 초기화를 진행한다.
    // ----------------------------------------------------------------------
    if(sUrl)        this.url = sUrl;
    if(nWidth)      this.width = nWidth;
    if(nHeight)     this.height = nHeight;
    if(winName)     this.windowName = winName;
    // ----------------------------------------------------------------------
    
    this.open = function(sUrl, nWidth, nHeight, winName)
    {
        if(sUrl)        this.url = sUrl;
        if(nWidth)      this.width = nWidth;
        if(nHeight)     this.height = nHeight;
        if(winName)     this.windowName = winName;
        
        this.setCenterPosition();
        this.setFeatures();        
        window.open(this.url, this.windowName, this.features);
    }
    
    // 다이얼로그 속성값을 세팅한다.
    this.setFeatures = function()
    {
        this.features =   "top="          + this.top + ", "
                        + "left="         + this.left + ", "
                        + "width="        + this.width + ", "
                        + "height="       + this.height + ", "
                        + "toolbar="      + this.toolbar + ", "
                        + "menubar="      + this.menubar + ", "
                        + "status="       + this.status + ", "
                        + "resizable="    + this.resizable + ", "
                        + "scrollbars="   + this.scrollbars + ", "
                        + "status="       + this.status + ", "
                        + "location="     + this.location;
    }
    
	// 다이얼로그 창을 화면 중간에 위치하도록 자리를 잡는다.
    this.setCenterPosition = function()
    {
        this.top = (screen.height - this.pos_height) / 2;
        this.left = (screen.width - this.pos_width) / 2;
    }
}