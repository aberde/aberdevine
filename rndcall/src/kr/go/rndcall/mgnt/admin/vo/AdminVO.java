package kr.go.rndcall.mgnt.admin.vo;

import java.lang.reflect.Field;
import java.util.Arrays;

import kr.go.rndcall.mgnt.common.BaseVO;

public class AdminVO extends BaseVO{
	
	private String id; // 아이디
	public String getReg_time() {
		return reg_time;
	}



	public void setReg_time(String reg_time) {
		this.reg_time = reg_time;
	}



	private String contents; //내용
	private String answerContents; //답변내용
	private String call_receive_yn; // SMS수신여부
	private String cell_number;    // 핸드폰번호
	private String email_receive_yn; // 이메일수신여부
	private String email;    // 이메일
	private String public_tnrans_yn;   //타기관지정여부
	private String public_trans_id;   //타기관담당자id
	private int read_count; // 조회 수
	private String open_yn; //userSN
	private String del_yn;       // formNo
	private String file_id;  // size
	private String reg_id; // 등록자ID
	private String reg_dt; // 등록일시
	private String reg_time; // 등록시간
	private String edit_id; // 수정자ID
	private String edit_dt; // 수정일시
    
    private String title;    //제목
    private String name;    //이름
    private String category1;    //대분류
    private String category2;    //소분류
    private String data_id; //파일 아이디
    private String question_id = ""; //질문 아이디
    private String answer_id = ""; //답변 아이디
    private String insert_type;  //등록방법분류
    private String board_type;  //게시판 타입
    private String cell_number1; //연락처 
    private String cell_number2; //연락처 
    private String cell_number3; //연락처 
    private String question_contents; //질문내용
    private String answer_contents; //답변내용

    /**
	 * AdminVO 생성자
	 */
	public AdminVO(){
		
		this.id = ""; // 아이디
		this.contents = ""; //내용
		this.answerContents = ""; //답변내용
		this.call_receive_yn = ""; // SMS수신여부
		this.cell_number = "";    // 핸드폰번호
		this.email_receive_yn = ""; // 이메일수신여부
		this.email = "";    // 이메일
		this.public_tnrans_yn = "";   //타기관지정여부
		this.public_trans_id = "";   //타기관담당자id
		this.insert_type = "";  //등록반법분류
		this.read_count = 0; // 조회 수
		this.open_yn = ""; //userSN
		this.del_yn = "";       // formNo
		this.file_id = "";  // size
		this.reg_id = "";  // 등록자ID
		this.reg_dt = "";  // 등록일시
		this.edit_id = ""; // 수정자ID
		this.edit_dt = ""; // 수정일시
        
        
        this.title = "";        //제목
        this.name  = "";        //이름
        this.category1 = "";    //대분류
        this.category2 = "";    //소분류
        this.data_id = ""; //파일 아이디
        this.question_id = ""; //질문 아이디
        this.answer_id = ""; //답변 아이디
        this.cell_number1 = ""; //연락처 
        this.cell_number2 = ""; //연락처 
        this.cell_number3 = ""; //연락처 
        this.question_contents = ""; //질문내용
        this.answer_contents = ""; //답변내용

        this.code = ""; //코드
        this.code_nm = ""; //코드명
        this.p_code = ""; //부모코드
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




	public String getAnswerContents() {
		return answerContents;
	}




	public void setAnswerContents(String answerContents) {
		this.answerContents = answerContents;
	}




	public String getCall_receive_yn() {
		return call_receive_yn;
	}




	public void setCall_receive_yn(String call_receive_yn) {
		this.call_receive_yn = call_receive_yn;
	}




	public String getCell_number() {
		return cell_number;
	}




	public void setCell_number(String cell_number) {
		this.cell_number = cell_number;
	}




	public String getContents() {
		return contents;
	}




	public void setContents(String contents) {
		this.contents = contents;
	}




	public String getDel_yn() {
		return del_yn;
	}




	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
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




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getEmail_receive_yn() {
		return email_receive_yn;
	}




	public void setEmail_receive_yn(String email_receive_yn) {
		this.email_receive_yn = email_receive_yn;
	}




	public String getFile_id() {
		return file_id;
	}




	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}




	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getInsert_type() {
		return insert_type;
	}




	public void setInsert_type(String insert_type) {
		this.insert_type = insert_type;
	}




	public String getOpen_yn() {
		return open_yn;
	}




	public void setOpen_yn(String open_yn) {
		this.open_yn = open_yn;
	}




	public String getPublic_tnrans_yn() {
		return public_tnrans_yn;
	}




	public void setPublic_tnrans_yn(String public_tnrans_yn) {
		this.public_tnrans_yn = public_tnrans_yn;
	}




	public String getPublic_trans_id() {
		return public_trans_id;
	}




	public void setPublic_trans_id(String public_trans_id) {
		this.public_trans_id = public_trans_id;
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




    public String getName() {
        return name;
    }




    public void setName(String name) {
        this.name = name;
    }




    public String getCategory1() {
        return category1;
    }




    public void setCategory1(String category1) {
        this.category1 = category1;
    }




    public String getCategory2() {
        return category2;
    }




    public void setCategory2(String category2) {
        this.category2 = category2;
    }




    public String getData_id() {
        return data_id;
    }




    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    
    public String getQuestion_id() {
        return question_id;
    }



    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }



    public String getBoard_type() {
        return board_type;
    }



    public void setBoard_type(String board_type) {
        this.board_type = board_type;
    }



    public String getCell_number1() {
        return cell_number1;
    }



    public void setCell_number1(String cell_number1) {
        this.cell_number1 = cell_number1;
    }



    public String getCell_number2() {
        return cell_number2;
    }



    public void setCell_number2(String cell_number2) {
        this.cell_number2 = cell_number2;
    }



    public String getCell_number3() {
        return cell_number3;
    }



    public void setCell_number3(String cell_number3) {
        this.cell_number3 = cell_number3;
    }



    public String getAnswer_contents() {
        return answer_contents;
    }



    public void setAnswer_contents(String answer_contents) {
        this.answer_contents = answer_contents;
    }



    public String getQuestion_contents() {
        return question_contents;
    }



    public void setQuestion_contents(String question_contents) {
        this.question_contents = question_contents;
    }



    public String getAnswer_id() {
        return answer_id;
    }



    public void setAnswer_id(String answer_id) {
        this.answer_id = answer_id;
    }
}
