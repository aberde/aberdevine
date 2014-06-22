package kr.go.rndcall.mgnt.inquire.vo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import kr.go.rndcall.mgnt.common.BaseResultVO;
import kr.go.rndcall.mgnt.login.LoginVO;

public class InquireResultVO extends BaseResultVO{
	private InquireVO vo = new InquireVO();
	private SatiVO satiVO = new SatiVO();
	
	private InquireSearchVO searchVO = new InquireSearchVO();
	private InquireAttachVO attachVO = new InquireAttachVO();
	private LoginVO loginVO = new LoginVO();
	
	private ArrayList voList1 = new ArrayList();
	private ArrayList voList2 = new ArrayList();
	
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

	public InquireSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(InquireSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public InquireAttachVO getAttachVO() {
		return attachVO;
	}

	public void setAttachVO(InquireAttachVO attachVO) {
		this.attachVO = attachVO;
	}

	public InquireVO getVo() {
		return vo;
	}

	public void setVo(InquireVO vo) {
		this.vo = vo;
	}
	
	public SatiVO getSatiVO() {
		return satiVO;
	}

	public void setSatiVO(SatiVO satiVO) {
		this.satiVO = satiVO;
	}

	public LoginVO getLoginVO() {
		return loginVO;
	}

	public void setLoginVO(LoginVO loginVO) {
		this.loginVO = loginVO;
	}

	/**
	 * toString 메소드
	 */
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
	

}
