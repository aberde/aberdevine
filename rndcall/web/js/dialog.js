function Dialog(sUrl, nWidth, nHeight, vArguments)
{
    this.url = null;
    this.args = null;
    this.features = "";

    // features
    this.dialogHeight   = "0";      //
    this.dialogWidth    = "0";      //
    this.dialogLeft     = "0";      //
    this.dialogTop      = "0";      //
    this.center         = "yes";    // center:{ yes | no | 1 | 0 | on | off }
    this.dialogHide     = "no";     // dialogHide:{ yes | no | 1 | 0 | on | off }
    this.edge           = "raised"; // edge:{ sunken | raised }
    this.resizable      = "no";     // resizable:{ yes | no | 1 | 0 | on | off }
    this.scroll         = "yes";    // scroll:{ yes | no | 1 | 0 | on | off }
    this.status         = "yes";    // status:{ yes | no | 1 | 0 | on | off }
    this.unadorned      = "no";     // unadorned:{ yes | no | 1 | 0 | on | off }
    this.help           = "yes";    // help:{ yes | no | 1 | 0 | on | off }     // : showMedelessDialog에서만 사용한다.

    nWidth = (typeof(nWidth) == "number")?nWidth+"px":nWidth;
    nHeight = (typeof(nHeight) == "number")?nHeight+"px":nHeight;
    // ----------------------------------------------------------------------
    // 초기화를 진행한다.
    // ----------------------------------------------------------------------
    if(sUrl)        this.url = sUrl;
    if(nWidth)      this.dialogWidth = nWidth;
    if(nHeight)     this.dialogHeight = nHeight;
    if(vArguments)  this.args = vArguments;
    // ----------------------------------------------------------------------


    // 다이얼로그 창을 띄운다.
    this.open = function(sUrl, nWidth, nHeight, vArguments)
    {
        if(sUrl)        this.url = sUrl;
        if(nWidth)      this.dialogWidth = nWidth;
        if(nHeight)     this.dialogHeight = nHeight;
        if(vArguments)  this.args = vArguments;

        this.setCenterPosition();
        this.setFeatures();
        return window.showModalDialog(this.url, this.args, this.features);
    }

    this.openModal = this.open;

    this.openModeless = function(sUrl, nWidth, nHeight, vArguments)
    {
        if(sUrl)        this.url = sUrl;
        if(nWidth)      this.dialogWidth = nWidth;
        if(nHeight)     this.dialogHeight = nHeight;
        if(vArguments)  this.args = vArguments;

        this.setCenterPosition();
        this.setModelessFeatures();
        window.showModelessDialog(this.url, this.args, this.features);
    }

    // 새로 띄월 윈도우에 넘길 아규먼트를 추가한다.
    this.addArgument = function(sArgName, vArgValue)
    {
//        if(!this.args || this.args.constructor != Object) this.args = new Object();
//        log("sArgName = " + sArgName + "\tvArgValue = " + vArgValue);
//        var args = eval("this.args." + sArgName);
//        args = vArgValue;
//        eval("this.args." + sArgName) = vArgValue;
//        this.args.action = vArgValue;
    }

    // 다이얼로그 속성값을 세팅한다.
    this.setFeatures = function()
    {
        this.features = "dialogHeight:"   + this.dialogHeight + "; "
                        + "dialogWidth:"  + this.dialogWidth + "; "
                        + "dialogLeft:"   + this.dialogLeft + "; "
                        + "dialogTop:"    + this.dialogTop + "; "
                        + "center:"       + this.center + "; "
                        + "dialogHide:"   + this.dialogHide + "; "
                        + "edge:"         + this.edge + "; "
                        + "resizable:"    + this.resizable + "; "
                        + "scroll:"       + this.scroll + "; "
                        + "status:"       + this.status + "; "
                        + "unadorned:"    + this.unadorned;
    }
    this.setModelessFeatures = function()
    {
        this.setFeatures();
        this.features += ",help:" + this.help;
    }


    // 다이얼로그 창을 화면 중간에 위치하도록 자리를 잡는다.
    this.setCenterPosition = function()
    {
        this.dialogTop = (screen.height - this.dialogHeight) / 2;
        this.dialogLeft = (screen.width - this.dialogWidth) / 2;
    }
}