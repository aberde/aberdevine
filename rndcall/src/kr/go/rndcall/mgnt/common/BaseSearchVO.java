package kr.go.rndcall.mgnt.common;

import java.io.Serializable;
import java.util.Calendar;


public class BaseSearchVO implements Serializable {
    protected String isUse = "Y";
    protected String currentYYYY = Integer.toString(((Calendar) Calendar.getInstance()).get(Calendar.YEAR));
    protected String loginId = "";
    protected String name = "";    
    protected String roleCD = "";
    protected String deptCD = "";
    protected String crud = "";
    protected String username = "";
    
    protected String maxIndexPages = "10";
    protected String pagerOffset = "0";
    protected String maxPageItems = "10";
    protected String orderByTarget = "";
    protected String orderByMethod = "";
    protected Integer totRowCount = Integer.valueOf("0");
    protected Integer totRowCount1 = Integer.valueOf("0");
    protected Integer totRowCount2 = Integer.valueOf("0");
    protected Integer totRowCount3 = Integer.valueOf("0");
    protected boolean paging = true; // 페이징 여부
    
    protected String dept_OnN = "";
    protected String dept_cd2 = "";
    protected String menu_sn = "";
    protected String uni = "";
    
    
    public String getDept_OnN() {
		return dept_OnN;
	}

	public void setDept_OnN(String dept_OnN) {
		this.dept_OnN = dept_OnN;
	}

	public String getDeptCD() {
		return deptCD;
	}

	public String getRoleCD() {
		return roleCD;
	}

	public void setRoleCD(String roleCD) {
		this.roleCD = roleCD;
	}

	public boolean isPaging() {
		return paging;
    }

    public void setPaging(boolean paging) {
		this.paging = paging;
    }
    
    protected String org_cd="";
	public String getOrg_cd() {
        return org_cd;
    }
    public void setOrg_cd(String org_cd) {
        this.org_cd = org_cd;
    }
    
    public void setCurrentYYYY(String currentYYYY) {
        this.currentYYYY = currentYYYY;
    }

    public void setDeptCD(String deptCD) {
        this.deptCD = deptCD;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public String getCurrentYYYY() {
        return currentYYYY;
    }


    public String getIsUse() {
        return isUse;
    }
    
    public String getMaxPageItems() {
        return maxPageItems;
    }

    public void setMaxPageItems(String maxPageItems) {
        this.maxPageItems = maxPageItems;
    }

    public String getPagerOffset() {
        return pagerOffset;
    }
    
    public void setPagerOffset(String pagerOffset) {
        this.pagerOffset = pagerOffset;
    }
    
    public String getMaxIndexPages() {
        return maxIndexPages;
    }
    
    public void setMaxIndexPages(String maxIndexPages) {
        this.maxIndexPages = maxIndexPages;
    }
    
    public String getOrderByTarget() {
        return orderByTarget;
    }

    public void setOrderByTarget(String orderByTarget) {
        this.orderByTarget = orderByTarget;
    }

	public String getOrderByMethod() {
		return orderByMethod;
	}

	public void setOrderByMethod(String orderByMethod) {
		this.orderByMethod = orderByMethod;
	}

	public Integer getTotRowCount() {
		return totRowCount;
	}

	public void setTotRowCount(Integer totRowCount) {
		this.totRowCount = totRowCount;
	}

	public String getDept_cd2() {
		return dept_cd2;
	}

	public void setDept_cd2(String dept_cd2) {
		this.dept_cd2 = dept_cd2;
	}
	
	public String getDept_cd() {
        return deptCD;
    }

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the crud
	 */
	public String getCrud() {
		return crud;
	}

	/**
	 * @param crud the crud to set
	 */
	public void setCrud(String crud) {
		this.crud = crud;
	}

	public String getMenu_sn() {
		return menu_sn;
	}

	public void setMenu_sn(String menu_sn) {
		this.menu_sn = menu_sn;
	}

	public String getUni() {
		return uni;
	}

	public void setUni(String uni) {
		this.uni = uni;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
