package kr.go.rndcall.mgnt.inquire.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;

public class InquireAttachVO implements Serializable{
	private String file_id = ""; // 파일ID
	private String seq = ""; // 일련번호
	private String file_nm = ""; // 파일명
	private String file_type = ""; // 파일타입
	private String file_up_path = "";  // 파일업로드경로
	private String file_down_path = ""; // 파일 다운로드 경로
	private String file_size = "";// 파일 크기
	private String file_desc = ""; // 파일 설명
	private String file_del_yn = ""; // 파일 삭제여부
	private String reg_id = ""; // 등록자id
	private String reg_dt = "";  // 등록일시
	private String edit_id = ""; // 수정자id
	private String edit_dt = "";// 수정일시
	private String file_path = "";// 파일경로
	
	/** 
	 * toString() 메소드
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
	
	public String getFile_del_yn() {
		return file_del_yn;
	}
	
	public void setFile_del_yn(String file_del_yn) {
		this.file_del_yn = file_del_yn;
	}
	
	public String getFile_desc() {
		return file_desc;
	}
	
	public void setFile_desc(String file_desc) {
		this.file_desc = file_desc;
	}
	
	public String getFile_down_path() {
		return file_down_path;
	}
	
	public void setFile_down_path(String file_down_path) {
		this.file_down_path = file_down_path;
	}
	
	public String getFile_id() {
		return file_id;
	}
	
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	
	public String getFile_nm() {
		return file_nm;
	}
	
	public void setFile_nm(String file_nm) {
		this.file_nm = file_nm;
	}
	
	public String getFile_size() {
		return file_size;
	}
	
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	
	public String getFile_type() {
		return file_type;
	}
	
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	
	public String getFile_up_path() {
		return file_up_path;
	}
	
	public void setFile_up_path(String file_up_path) {
		this.file_up_path = file_up_path;
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
	
	public String getSeq() {
		return seq;
	}
	
	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
}
