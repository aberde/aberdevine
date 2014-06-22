package kr.go.rndcall.mgnt.inquire.vo;

import java.lang.reflect.Field;
import java.util.Arrays;

import kr.go.rndcall.mgnt.common.BaseSearchVO;

public class SatiVO {
	
	protected String sati_id = ""; // 만족도 아이디
	protected String write_id = ""; // 글번호
	protected String board_type = ""; // 게시물 구분
	protected String reg_id = ""; // 등록자ID
	protected String app_point = "3"; // 만족도 평가점수
	protected String reg_ip = ""; // 등록자IP
	protected String reg_dt = ""; // 등록일시
	protected String sati_reg_yn = ""; // 만족도 평가 여부
	protected String avg_app_point = ""; // 만족도점수 평균정보
	protected String sati_reg_cnt = ""; // 만족도평가 건수
	
	
    /**
     * toString 메소드
     */
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


	public String getApp_point() {
		return app_point;
	}


	public void setApp_point(String app_point) {
		this.app_point = app_point;
	}


	public String getBoard_type() {
		return board_type;
	}


	public void setBoard_type(String board_type) {
		this.board_type = board_type;
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


	public String getReg_ip() {
		return reg_ip;
	}


	public void setReg_ip(String reg_ip) {
		this.reg_ip = reg_ip;
	}


	public String getSati_id() {
		return sati_id;
	}


	public void setSati_id(String sati_id) {
		this.sati_id = sati_id;
	}


	public String getWrite_id() {
		return write_id;
	}


	public void setWrite_id(String write_id) {
		this.write_id = write_id;
	}


	public String getSati_reg_yn() {
		return sati_reg_yn;
	}


	public void setSati_reg_yn(String sati_reg_yn) {
		this.sati_reg_yn = sati_reg_yn;
	}


	public String getAvg_app_point() {
		return avg_app_point;
	}


	public void setAvg_app_point(String avg_app_point) {
		this.avg_app_point = avg_app_point;
	}


	public String getSati_reg_cnt() {
		return sati_reg_cnt;
	}


	public void setSati_reg_cnt(String sati_reg_cnt) {
		this.sati_reg_cnt = sati_reg_cnt;
	}
}