package kr.go.rndcall.mgnt.notice.vo;

import java.lang.reflect.Field;
import java.util.Arrays;

import kr.go.rndcall.mgnt.common.BaseVO;

public class NoticeVO extends BaseVO{
	private String notice_id; // 아이디
	private String title;  //제목
	private String contents;  //내용 
	private int read_count;   //조회수
	private String file_id;    //파일id
	private String use_yn; //삭제여부
	private String reg_id; //작성자id
	private String reg_dt;    //등록일시
	private String edit_id;   //수정자id
	private String edit_dt;   //수정일시
	private String data_id;
	private String question_id; //질문시퀀스id
	private String insert_type; //등록타입
	private String board_type;  //보드타입
	private String seq;   //시퀀스번호
	private String reg_nm; //등록자이름
	private String del_file_id; //첨부파일 삭제
		
		
	public String getBoard_type() {
		return board_type;
	}

	public void setBoard_type(String board_type) {
		this.board_type = board_type;
	}

	public String getData_id() {
		return data_id;
	}

	public void setData_id(String data_id) {
		this.data_id = data_id;
	}

	public String getInsert_type() {
		return insert_type;
	}

	public void setInsert_type(String insert_type) {
		this.insert_type = insert_type;
	}

	public String getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}

	/**
	 * BuAdNotiLVO 생성자
	 */
	public NoticeVO(){
			
		this.notice_id = "";
		this.title = "";
		this.contents = "";
		this.read_count = 0;
		this.file_id = "";
		this.use_yn = "";
		this.reg_id = ""; 
		this.reg_dt = "";
		this.edit_id = "";
		this.edit_dt = "";
		
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

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
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

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public String getNotice_id() {
		return notice_id;
	}

	public void setNotice_id(String notice_id) {
		this.notice_id = notice_id;
	}

	public int getRead_count() {
		return read_count;
	}

	public void setRead_count(int read_count) {
		this.read_count = read_count;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}

	public String getReg_nm() {
		return reg_nm;
	}

	public void setReg_nm(String reg_nm) {
		this.reg_nm = reg_nm;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getDel_file_id() {
		return del_file_id;
	}

	public void setDel_file_id(String del_file_id) {
		this.del_file_id = del_file_id;
	}

	
}
