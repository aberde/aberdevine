package kr.go.rndcall.mgnt.common;

import java.io.Serializable;

public class BaseVO implements Serializable {
    protected String isUse = "Y";
    protected String isUseNM = "";
    protected String isLast = "Y";
    protected String parentSN = "0";
    protected String topSN = "0";
    protected String putLoginSN = "0";
    protected String putDesc = "";
    protected String putDate = "";
    protected String setLoginSN = "0";
    protected String setDate = "";
    protected String setDesc = "";
    protected String etc = "";
    protected String isAttached = "N";
    protected String attachSN = "0";
    protected String newImage = "";
    protected Integer readCount = Integer.valueOf("0");
    protected String putNM = "";
    protected String setNM = "";
    protected String loginSN = "";
    protected String fileNM = "";
    protected String filePath = "";
    protected String fileSize = "0";
    protected String saveFileNM = "";
    protected String requestURI = "";
    protected String subjectNo = "";
    protected String reg_dt = "";
    protected String edit_dt = "";
    protected String reg_id = "";
    protected String edit_id = "";
    protected String code; //코드
    protected String code_nm; //코드명
    protected String p_code; //부모코드
    
    
    
    public String getEdit_dt() {
		return edit_dt;
	}

	public void setEdit_dt(String edit_dt) {
		this.edit_dt = edit_dt;
	}

	public String getEdit_id() {
		return edit_id;
	}

	public void setEdit_id(String edit_id) {
		this.edit_id = edit_id;
	}

	public String getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}

	public String getReg_id() {
		return reg_id;
	}

	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}

	public String getAttachSN() {
        return attachSN;
    }

    public String getEtc() {
        return etc;
    }

    public String getIsAttached() {
        return isAttached;
    }

    public String getIsLast() {
        return isLast;
    }

    public String getIsUse() {
        return isUse;
    }

    public String getParentSN() {
        return parentSN;
    }

    public String getPutDate() {
        return putDate;
    }

    public String getPutDesc() {
        return putDesc;
    }

    public String getPutLoginSN() {
        return putLoginSN;
    }

    public String getSetDate() {
        return setDate;
    }

    public String getSetDesc() {
        return setDesc;
    }

    public String getSetLoginSN() {
        return setLoginSN;
    }

    public String getTopSN() {
        return topSN;
    }

    public String getNewImage() {
        return newImage;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public String getPutNM() {
        return putNM;
    }

    public String getSetNM() {
        return setNM;
    }

    public String getLoginSN() {
        return loginSN;
    }

    public String getFileNM() {
        return fileNM;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getSaveFileNM() {
        return saveFileNM;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getSubjectNo() {
        return subjectNo;
    }

    public String getIsUseNM() {
        return isUseNM;
    }

    public void setAttachSN(String attachSN) {
        this.attachSN = attachSN;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public void setTopSN(String topSN) {
        this.topSN = topSN;
    }

    public void setSetLoginSN(String setLoginSN) {
        this.setLoginSN = setLoginSN;
    }

    public void setSetDesc(String setDesc) {
        this.setDesc = setDesc;
    }

    public void setSetDate(String setDate) {
        this.setDate = setDate;
    }

    public void setPutDesc(String putDesc) {
        this.putDesc = putDesc;
    }

    public void setPutLoginSN(String putLoginSN) {
        this.putLoginSN = putLoginSN;
    }

    public void setPutDate(String putDate) {
        this.putDate = putDate;
    }

    public void setParentSN(String parentSN) {
        this.parentSN = parentSN;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public void setIsLast(String isLast) {
        this.isLast = isLast;
    }

    public void setIsAttached(String isAttached) {
        this.isAttached = isAttached;
    }

    public void setNewImage(String newImage) {
        this.newImage = newImage;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public void setPutNM(String putNM) {
        this.putNM = putNM;
    }

    public void setSetNM(String setNM) {
        this.setNM = setNM;
    }

    public void setLoginSN(String loginSN) {
        this.loginSN = loginSN;
    }

    public void setFileNM(String fileNM) {
        this.fileNM = fileNM;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setSaveFileNM(String saveFileNM) {
        this.saveFileNM = saveFileNM;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public void setSubjectNo(String subjectNo) {
        this.subjectNo = subjectNo;
    }

    public void setIsUseNM(String isUseNM) {
        this.isUseNM = isUseNM;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode_nm() {
		return code_nm;
	}

	public void setCode_nm(String code_nm) {
		this.code_nm = code_nm;
	}

	public String getP_code() {
		return p_code;
	}

	public void setP_code(String p_code) {
		this.p_code = p_code;
	}

}
