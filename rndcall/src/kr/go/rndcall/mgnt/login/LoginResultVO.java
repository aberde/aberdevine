package kr.go.rndcall.mgnt.login;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginResultVO implements Serializable {
    private ArrayList voList = null;
    private LoginVO vo = null;
    private String errCd = "";
    private String errMsg = "";
    private String returnMsg = "";
    private String totRowCount = "";

    public LoginResultVO() {
    }

    public String toString() {
        StringBuffer tempBuffer = new StringBuffer();
        tempBuffer.append("\n [" + this.getClass().getName() + "]");
        Field[] myField = this.getClass().getDeclaredFields();

        for (int i = 0; i < myField.length; i++) {
            try {
                if (myField[i].getType().isArray()) {
                    String className = myField[i].getType().getName();
                    className = className.substring(2, className.length() - 1);

                    tempBuffer.append("\n " + className + "[] ")
                        .append(myField[i].getName())
                        .append(" = " + Arrays.asList( (Object[]) myField[i].get(this)));

                } else if (myField[i].getType().isPrimitive() ||
                           myField[i].getType() == String.class) {

                    String typeName = myField[i].getType().getName();
                    typeName = ( (myField[i].getType() == String.class) ? "String" : typeName);
                    tempBuffer.append("\n " + typeName + " ")
                        .append(myField[i].getName())
                        .append(" = [" + myField[i].get(this) + "]");
                } else if (myField[i].getType() == Class.class) {
                    // ignore
                } else {

                    String className = myField[i].getType().getName();
                    tempBuffer.append("\n " + className + " ")
                        .append(myField[i].getName())
                        .append(" = [" + myField[i].get(this) + "]");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tempBuffer.toString();
    }

    public ArrayList getVoList() {
        return voList;
    }

    public void setVoList(ArrayList voList) {
        this.voList = voList;
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

    public String getTotRowCount() {
        return totRowCount;
    }

    public void setTotRowCount(String totRowCount) {
        this.totRowCount = totRowCount;
    }

    public String getErrCd() {
        return errCd;
    }

    public LoginVO getVo() {
        return vo;
    }

    public void setErrCd(String errCd) {
        this.errCd = errCd;
    }

    public void setVo(LoginVO vo) {
        this.vo = vo;
    }
}
