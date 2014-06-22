package kr.go.rndcall.mgnt.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;

import kr.go.rndcall.mgnt.common.BaseVO;

public class MenuRoleVO extends BaseVO {

    private String treeLevel;
    private String menuSN;
    private String menuNM;
    private String isFolder;
    private String isFolderNM;
    private String orderNO;
    private String parentSN;
    private String URL;
    private String isUse;
    private String isUseNM;
    private String treeOrderNO;
    private String requestURI;
    private String topMenuSN;
    private String roleCD;
    private String roleNM;
    private String[] roleTP;
    private String isPut;
    private String isSet;
    private String isGet;
    private String isDel;
    private String isDisplay;
    private String boardCD;

    public MenuRoleVO() {
        this.treeLevel = "";
        this.menuSN = "";
        this.menuNM = "";
        this.isFolder = "";
        this.isFolderNM = "";
        this.orderNO = "1";
        this.parentSN = "";
        this.URL = "";
        this.isUse = "";
        this.isUseNM = "";
        this.treeOrderNO = "";
        this.requestURI = "";
        this.topMenuSN = "";
        this.roleCD = "";
        this.roleNM = "";
        this.roleTP = new String[]{};
        this.isPut = "";
        this.isSet = "";
        this.isGet = "";
        this.isDel = "";
        this.isDisplay = "";
        this.boardCD = "";
    }

    public String getParentSN() {
        return parentSN;
    }

    public String getIsFolder() {
        return isFolder;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public void setParentSN(String parentSN) {
        this.parentSN = parentSN;
    }

    public void setIsFolder(String isFolder) {
        this.isFolder = isFolder;
    }

    public void setURL(String URL) {
//    	String newReturn = "";
//    	try {
//    		TempDev td = TempDev.getInstance();
//    		newReturn = td.getNewURI(URL);
//    	} catch (Exception e) {
//    		
//    	}
//    	this.URL = newReturn;
    	
        this.URL = URL;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public void setMenuNM(String menuNM) {
        this.menuNM = menuNM;
    }

    public void setMenuSN(String menuSN) {
        this.menuSN = menuSN;
    }

    public void setTreeLevel(String treeLevel) {
        this.treeLevel = treeLevel;
    }

    public void setTreeOrderNO(String treeOrderNO) {
        this.treeOrderNO = treeOrderNO;
    }

    public void setIsFolderNM(String isFolderNM) {
        this.isFolderNM = isFolderNM;
    }

    public void setIsUseNM(String isUseNM) {
        this.isUseNM = isUseNM;
    }

    public void setRequestURI(String requestURI) {
//    	String newReturn = "";
//    	try {
//    		TempDev td = TempDev.getInstance();
//    		newReturn = td.getNewURI(requestURI);
//    	} catch (Exception e) {
//    		
//    	}
//    	this.requestURI = newReturn;
    	
        this.requestURI = requestURI;
    }

    public void setTopMenuSN(String topMenuSN) {
        this.topMenuSN = topMenuSN;
    }

    public void setRoleTP(String[] roleTP) {
        this.roleTP = roleTP;
    }

    public void setIsGet(String isGet) {
        this.isGet = isGet;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public void setIsPut(String isPut) {
        this.isPut = isPut;
    }

    public void setIsSet(String isSet) {
        this.isSet = isSet;
    }

    public void setRoleNM(String roleNM) {
        this.roleNM = roleNM;
    }

    public void setRoleCD(String roleCD) {
        this.roleCD = roleCD;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay;
    }

    public void setBoardCD(String boardCD) {
        this.boardCD = boardCD;
    }

    public String getOrderNO() {
        return orderNO;
    }

    public String getURL() {
        return URL;
    }

    public String getIsUse() {
        return isUse;
    }

    public String getMenuNM() {
        return menuNM;
    }

    public String getMenuSN() {
        return menuSN;
    }

    public String getTreeLevel() {
        return treeLevel;
    }

    public String getTreeOrderNO() {
        return treeOrderNO;
    }

    public String getIsFolderNM() {
        return isFolderNM;
    }

    public String getIsUseNM() {
        return isUseNM;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getTopMenuSN() {
        return topMenuSN;
    }

    public String[] getRoleTP() {
        return roleTP;
    }

    public String getIsGet() {
        return isGet;
    }

    public String getIsDel() {
        return isDel;
    }

    public String getIsPut() {
        return isPut;
    }

    public String getIsSet() {
        return isSet;
    }

    public String getRoleNM() {
        return roleNM;
    }

    public String getRoleCD() {
        return roleCD;
    }

    public String getIsDisplay() {
        return isDisplay;
    }

    public String getBoardCD() {
        return boardCD;
    }

    public String toString() {
        StringBuffer tempBuffer = new StringBuffer();
        // super
        tempBuffer.append("\n [" + this.getClass().getSuperclass().getName() + "]");
        Field[] superField = this.getClass().getSuperclass().getDeclaredFields();

        for (int i = 0; i < superField.length; i++) {
            try {
                if (superField[i].getType().isArray()) {
                    String className = superField[i].getType().getName();
                    className = className.substring(2, className.length() - 1);
                    tempBuffer.append("\n " + className + "[] ").append(superField[i].getName()).append(" = "
                            + Arrays.asList((Object[]) superField[i].get(this)));
                } else if (superField[i].getType().isPrimitive() || superField[i].getType() == String.class) {
                    String typeName = superField[i].getType().getName();
                    typeName = ((superField[i].getType() == String.class) ? "String" : typeName);
                    tempBuffer.append("\n " + typeName + " ").append(superField[i].getName()).append(" = ["
                            + superField[i].get(this) + "]");
                } else if (superField[i].getType() == Class.class) {
                    // ignore
                } else {
                    String className = superField[i].getType().getName();
                    tempBuffer.append("\n " + className + " ").append(superField[i].getName()).append(" = ["
                            + superField[i].get(this) + "]");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // this
        tempBuffer.append("\n [" + this.getClass().getName() + "]");
        Field[] thisField = this.getClass().getDeclaredFields();

        for (int i = 0; i < thisField.length; i++) {
            try {
                if (thisField[i].getType().isArray()) {
                    String className = thisField[i].getType().getName();
                    className = className.substring(2, className.length() - 1);
                    tempBuffer.append("\n " + className + "[] ").append(thisField[i].getName()).append(" = "
                            + Arrays.asList((Object[]) thisField[i].get(this)));
                } else if (thisField[i].getType().isPrimitive() || thisField[i].getType() == String.class) {
                    String typeName = thisField[i].getType().getName();
                    typeName = ((thisField[i].getType() == String.class) ? "String" : typeName);
                    tempBuffer.append("\n " + typeName + " ").append(thisField[i].getName()).append(" = ["
                            + thisField[i].get(this) + "]");
                } else if (thisField[i].getType() == Class.class) {
                    // ignore
                } else {
                    String className = thisField[i].getType().getName();
                    tempBuffer.append("\n " + className + " ").append(thisField[i].getName()).append(" = ["
                            + thisField[i].get(this) + "]");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tempBuffer.toString();
    }


}
