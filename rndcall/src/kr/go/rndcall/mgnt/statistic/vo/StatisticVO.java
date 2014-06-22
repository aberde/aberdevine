package kr.go.rndcall.mgnt.statistic.vo;

import java.lang.reflect.Field;
import java.util.Arrays;

import kr.go.rndcall.mgnt.common.BaseVO;

public class StatisticVO extends BaseVO{
	private String category1;		//대분류 분야코드
	private String category2;		//소분류 분야코드
	private String category1_nm;	//대분류 분야코드명
	private String category2_nm;	//소분류 분야코드명
	private String stat_mm;			//월정보
	private int total_cnt;			//전체건수
	private int online_cnt;		//온라인건수
	private int offline_cnt;		//오프라인건수
	private int disposal_cnt;		//처리건수
	private int undisposal_cnt;		//미처리건수
	private int cnt;		//건수
	
	
	/**
	 * BuAdNotiLVO 생성자
	 */
	public StatisticVO(){
		this.category1 = "";
		this.category2 = "";
		this.category1_nm = "";
		this.category2_nm = "";
		this.stat_mm = "";
		this.total_cnt = 0;
		this.online_cnt = 0;
		this.offline_cnt = 0;
		this.disposal_cnt = 0;
		this.undisposal_cnt = 0;
		this.cnt = 0;
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

                    tempBuffer.append("\n " + className + "[] ").append(myField[i].getName()).append(" = "
                            + Arrays.asList((Object[]) myField[i].get(this)));

                } else if (myField[i].getType().isPrimitive() || myField[i].getType() == String.class) {

                    String typeName = myField[i].getType().getName();
                    typeName = ((myField[i].getType() == String.class) ? "String" : typeName);
                    tempBuffer.append("\n " + typeName + " ").append(myField[i].getName()).append(" = ["
                            + myField[i].get(this) + "]");
                } else if (myField[i].getType() == Class.class) {
                    // ignore
                } else {

                    String className = myField[i].getType().getName();
                    tempBuffer.append("\n " + className + " ").append(myField[i].getName()).append(" = ["
                            + myField[i].get(this) + "]");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tempBuffer.toString();
    }

	public String getCategory1() {
		return category1;
	}

	public void setCategory1(String category1) {
		this.category1 = category1;
	}

	public String getCategory1_nm() {
		return category1_nm;
	}

	public void setCategory1_nm(String category1_nm) {
		this.category1_nm = category1_nm;
	}

	public String getCategory2() {
		return category2;
	}

	public void setCategory2(String category2) {
		this.category2 = category2;
	}

	public String getCategory2_nm() {
		return category2_nm;
	}

	public void setCategory2_nm(String category2_nm) {
		this.category2_nm = category2_nm;
	}

	public int getDisposal_cnt() {
		return disposal_cnt;
	}

	public void setDisposal_cnt(int disposal_cnt) {
		this.disposal_cnt = disposal_cnt;
	}

	public int getOffline_cnt() {
		return offline_cnt;
	}

	public void setOffline_cnt(int offline_cnt) {
		this.offline_cnt = offline_cnt;
	}

	public int getOnline_cnt() {
		return online_cnt;
	}

	public void setOnline_cnt(int online_cnt) {
		this.online_cnt = online_cnt;
	}

	public String getStat_mm() {
		return stat_mm;
	}

	public void setStat_mm(String stat_mm) {
		this.stat_mm = stat_mm;
	}

	public int getTotal_cnt() {
		return total_cnt;
	}

	public void setTotal_cnt(int total_cnt) {
		this.total_cnt = total_cnt;
	}

	public int getUndisposal_cnt() {
		return undisposal_cnt;
	}

	public void setUndisposal_cnt(int undisposal_cnt) {
		this.undisposal_cnt = undisposal_cnt;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
}
