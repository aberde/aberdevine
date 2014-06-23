package kr.go.rndcall.mgnt.offer.vo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import kr.go.rndcall.mgnt.common.BaseResultVO;
import kr.go.rndcall.mgnt.offer.vo.SatiVO;

public class OfferResultVO extends BaseResultVO{
	private OfferVO vo = new OfferVO();
	private SatiVO satiVO = new SatiVO();
	
	private OfferSearchVO searchVO = new OfferSearchVO();
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

	public OfferSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(OfferSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public OfferVO getVo() {
		return vo;
	}

	public void setVo(OfferVO vo) {
		this.vo = vo;
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

	public SatiVO getSatiVO() {
		return satiVO;
	}

	public void setSatiVO(SatiVO satiVO) {
		this.satiVO = satiVO;
	}
	

}
