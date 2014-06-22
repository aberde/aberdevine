package kr.go.rndcall.mgnt.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class BaseForm extends ActionForm {
	protected ArrayList voList = new ArrayList();
	protected ArrayList voList1 = new ArrayList();
	protected ArrayList voList2 = new ArrayList(); 
	protected ArrayList voList3 = new ArrayList(); 
	protected ArrayList voList4 = new ArrayList(); 
	protected HashMap data = new HashMap();
	protected String topMenuSN = "";
	protected String topMenuNM = "";
	protected String topMenuURL = "";
	protected String maxIndexPages = "10";
	protected Integer totRowCount = Integer.valueOf("0");
	protected Integer totRowCount1 = Integer.valueOf("0");
	protected Integer totRowCount2 = Integer.valueOf("0");
	protected Integer totRowCount3 = Integer.valueOf("0");
	protected String errCd = "";
	protected String errMsg = "";
	protected String returnMsg = ""; 
	protected String pagerOffset = "0";
	protected String maxPageItems = "10";
	protected String orderByTarget = "";
	protected String orderByMethod = "";
	protected ArrayList yearList = new ArrayList();
	protected String role = "";
	protected String roleCD = "";
	protected String roleNM = "";
	protected String menuNM = "";
	protected String menuSN = "";
	protected String requestURI = "";
	protected String successTarget = "";
	protected String failTarget = "";
	protected String query = "";
	protected String temp = "";
	protected String deptCD = "";
	protected String esYN = "";
	protected String sel = "0";
	protected String menusnCheck = "N";
	protected String is_del = "";
	protected String is_update = "";
	protected String is_put = "";
	protected String is_view = "";
	protected String is_list = "";
	
	//예산배분 년도
	protected ArrayList bu_yearList = new ArrayList();
	//조사분석 년도 검색조건
	protected ArrayList ia_yearList = new ArrayList();
	
	
	public String getSel() {
		return sel;
	}


	public void setSel(String sel) {
		this.sel = sel;
	}


	public String getEsYN() {
		return esYN;
	}



	public void setEsYN(String esYN) {
		this.esYN = esYN;
	}



	public HashMap getData() {
		return data;
	}

	public void setData(HashMap data) {
		this.data = data;
	}

	public ArrayList getVoList() {
		return voList;
	}
	
	public void setVoList(ArrayList voList) {
		this.voList = voList;
	}
	
	public String getErrCd() {
		return errCd;
	}
	
	public void setErrCd(String errCd) {
		this.errCd = errCd;
	}
	
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	public String getErrMsg() {
		return errMsg;
	}
	
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	
	public String getReturnMsg() {
		return returnMsg;
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
	
	public String getOrderByMethod() {
		return orderByMethod;
	}
	
	public String getOrderByTarget() {
		return orderByTarget;
	}
	
	public void setPagerOffset(String pagerOffset) {
		this.pagerOffset = pagerOffset;
	}
	
	public void setOrderByMethod(String orderByMethod) {
		this.orderByMethod = orderByMethod;
	}
	
	public void setOrderByTarget(String orderByTarget) {
		this.orderByTarget = orderByTarget;
	}
	
	public String getTopMenuSN() {
		return topMenuSN;
	}
	
	public String getMaxIndexPages() {
		return maxIndexPages;
	}
	
	public Integer getTotRowCount() {
		return totRowCount;
	}
		
	public void setIa_yearList(ArrayList ia_yearList) {
		this.ia_yearList = ia_yearList;
	}
	
	public String getRole() {
		return role;
	}
	
	public String getMenuNM() {
		return menuNM;
	}
	
	public String getTopMenuNM() {
		return topMenuNM;
	}
	
	public String getRequestURI() {
		return requestURI;
	}
	
	public String getRoleCD() {
		return roleCD;
	}
	
	public String getSuccessTarget() {
		
		return successTarget;
	}
	
	public String getFailTarget() {
		return failTarget;
	}
	
	public void setTopMenuSN(String topMenuSN) {
		this.topMenuSN = topMenuSN;
	}
	
	public void setMaxIndexPages(String maxIndexPages) {
		this.maxIndexPages = maxIndexPages;
	}
	
	public void setTotRowCount(Integer totRowCount) {
		this.totRowCount = totRowCount;
	}
	
	public void setYearList(ArrayList yearList) {
		this.yearList = yearList;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public void setMenuNM(String menuNM) {
		this.menuNM = menuNM;
	}
	
	public void setTopMenuNM(String topMenuNM) {
		this.topMenuNM = topMenuNM;
	}
	
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	
	public void setRoleCD(String roleCD) {
		this.roleCD = roleCD;
	}
	
	public void setSuccessTarget(String successTarget) {
		
		this.successTarget = successTarget;
	}
	
	public void setFailTarget(String failTarget) {
		this.failTarget = failTarget;
	}
	
	public ArrayList getVoList2() {
		return voList2;
	}
	
	public void setVoList2(ArrayList voList2) {
		this.voList2 = voList2;
	}
	
	public ArrayList getVoList1() {
		return voList1;
	}
	
	public void setVoList1(ArrayList voList1) {
		this.voList1 = voList1;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public String getTopMenuURL() {
		return topMenuURL;
	}
	
	public void setTopMenuURL(String topMenuURL) {
		this.topMenuURL = topMenuURL;
	}
	
	/**
	 * @return Returns the temp.
	 */
	public String getTemp() {
		return temp;
	}
	
	/**
	 * @param temp The temp to set.
	 */
	public void setTemp(String temp) {
		this.temp = temp;
	}
		
	public String getMenuSN() {
		return menuSN;
	}

	public void setMenuSN(String menuSN) {
		this.menuSN = menuSN;
	}

	public String getRoleNM() {
		return roleNM;
	}

	public void setRoleNM(String roleNM) {
		this.roleNM = roleNM;
	}

	public String getDeptCD() {
		return deptCD;
	}

	public void setDeptCD(String deptCD) {
		this.deptCD = deptCD;
	}

	public String getMenusnCheck() {
		return menusnCheck;
	}

	public void setMenusnCheck(String menusnCheck) {
		this.menusnCheck = menusnCheck;
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


	/**
	 * @return the bu_yearList
	 */
	public ArrayList getBu_yearList() {
		return bu_yearList;
	}


	/**
	 * @return the ia_yearList
	 */
	public ArrayList getIa_yearList() {
		return ia_yearList;
	}


	/**
	 * @return the is_del
	 */
	public String getIs_del() {
		return is_del;
	}


	/**
	 * @return the is_list
	 */
	public String getIs_list() {
		return is_list;
	}


	/**
	 * @return the is_put
	 */
	public String getIs_put() {
		return is_put;
	}


	/**
	 * @return the is_update
	 */
	public String getIs_update() {
		return is_update;
	}


	/**
	 * @return the is_view
	 */
	public String getIs_view() {
		return is_view;
	}


	/**
	 * @return the yearList
	 */
	public ArrayList getYearList() {
		return yearList;
	}


	/**
	 * @param bu_yearList the bu_yearList to set
	 */
	public void setBu_yearList(ArrayList bu_yearList) {
		this.bu_yearList = bu_yearList;
	}


	/**
	 * @param is_del the is_del to set
	 */
	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}


	/**
	 * @param is_list the is_list to set
	 */
	public void setIs_list(String is_list) {
		this.is_list = is_list;
	}


	/**
	 * @param is_put the is_put to set
	 */
	public void setIs_put(String is_put) {
		this.is_put = is_put;
	}


	/**
	 * @param is_update the is_update to set
	 */
	public void setIs_update(String is_update) {
		this.is_update = is_update;
	}


	/**
	 * @param is_view the is_view to set
	 */
	public void setIs_view(String is_view) {
		this.is_view = is_view;
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