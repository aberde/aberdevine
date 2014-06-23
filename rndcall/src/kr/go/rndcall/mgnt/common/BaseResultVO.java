package kr.go.rndcall.mgnt.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class BaseResultVO implements Serializable {
	protected String currentYYYY = Integer.toString(((Calendar) Calendar.getInstance()).get(Calendar.YEAR));
    protected ArrayList voList = new ArrayList(); 
    protected ArrayList voList1 = new ArrayList();
    protected ArrayList voList2 = new ArrayList();
    protected ArrayList voList3 = new ArrayList();
    protected ArrayList voList4 = new ArrayList();
    protected ArrayList voList5 = new ArrayList();
    protected ArrayList vo2List = new ArrayList();
    protected ArrayList voListStep = new ArrayList();
    protected HashMap data = new HashMap();
    protected Integer totRowCount = Integer.valueOf("0");
    protected Integer totRowCount1 = Integer.valueOf("0");
    protected Integer totRowCount2 = Integer.valueOf("0");
    protected Integer totRowCount3 = Integer.valueOf("0");
    protected String errCd = "";
    protected String errMsg = "";
    protected String returnMsg = "";
    protected String query = "";
    protected String loginSN = "0";
    
    
    
    public ArrayList getVoListStep() {
		return voListStep;
	}

	public void setVoListStep(ArrayList voListStep) {
		this.voListStep = voListStep;
	}

	public ArrayList getVoList() {
        return voList;
    }

    public void setVoList(ArrayList voList) {
        this.voList = voList;
    }

    public HashMap getData() {
		return data;
	}

	public void setData(HashMap data) {
		this.data = data;
	}

	public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrCd() {
        return errCd;
    }

    public Integer getTotRowCount() {
        return totRowCount;
    }

    public void setErrCd(String errCd) {
        this.errCd = errCd;
    }

    public void setTotRowCount(Integer totRowCount) {
        this.totRowCount = totRowCount;
    }

	public ArrayList getVo2List() {
		return vo2List;
	}

	public void setVo2List(ArrayList vo2List) {
		this.vo2List = vo2List;
	}

	public ArrayList getVoList1() {
		return voList1;
	}

	public void setVoList1(ArrayList voList1) {
		this.voList1 = voList1;
	}

	public ArrayList getVoList2() {
		return voList2;
	}

	public void setVoList2(ArrayList voList2) {
		this.voList2 = voList2;
	}

	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	 public String getCurrentYYYY() {
	        return currentYYYY;
	}
	 
	 public String getLoginSN() {
	        return loginSN;
    }

	public ArrayList getVoList3() {
		return voList3;
	}

	public void setVoList3(ArrayList voList3) {
		this.voList3 = voList3;
	}

	public ArrayList getVoList4() {
		return voList4;
	}

	public void setVoList4(ArrayList voList4) {
		this.voList4 = voList4;
	}

	public ArrayList getVoList5() {
		return voList5;
	}

	public void setVoList5(ArrayList voList5) {
		this.voList5 = voList5;
	}

	public Integer getTotRowCount1() {
		return totRowCount1;
	}

	public void setTotRowCount1(Integer totRowCount1) {
		this.totRowCount1 = totRowCount1;
	}

	public Integer getTotRowCount2() {
		return totRowCount2;
	}

	public void setTotRowCount2(Integer totRowCount2) {
		this.totRowCount2 = totRowCount2;
	}

	public Integer getTotRowCount3() {
		return totRowCount3;
	}

	public void setTotRowCount3(Integer totRowCount3) {
		this.totRowCount3 = totRowCount3;
	}
}
