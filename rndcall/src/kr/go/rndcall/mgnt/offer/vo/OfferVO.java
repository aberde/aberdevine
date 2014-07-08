package kr.go.rndcall.mgnt.offer.vo;

import java.lang.reflect.Field;
import java.util.Arrays;

import kr.go.rndcall.mgnt.common.BaseVO;

public class OfferVO extends BaseVO{
	private String faq_id; // 아이디
	private String type;  // 분류
	private String title;  //제목
	private String contents;  //내용 
	private String answer_contents;  //의견 
	private String cause_contents;  //이유 
	private int read_count;   //조회수
	private String file_id;    //파일id
	private String use_yn; //삭제여부
	private String reg_id; //작성자id
	private String reg_dt;    //등록일시
	private String edit_id;   //수정자id
	private String edit_dt;   //수정일시
	private String sms_receive_yn; //sms수신여부
	private String call_receive_yn; //sms수신여부
	private String email_receive_yn; //sms수신여부
	private String faq_title; //질문제목
	private String faq_Content; //질문내용
	private String faq_cate; //카테고리분류
	private String call_num; //수신번호
	private String open_yn; //공개여부
	private String cell_number;    // 핸드폰번호	
	private String email;    // 이메일
	private String seq;    // faq 고유번호
	private String answer_cont;
	private String category1; //대분류
	private String category2; //소분류
	private String reg_nm; //등록자
	private String board_type; //등록자
    private String code; //코드
    private String code_nm; //코드명
    private String p_code; //부모코드
    private String data_id; //파일시퀀스
    private String question_id;
    private String answer_id;
    private String insert_type;
    private String name;
    private String question_contents;
	private String statCnt1; //Q&A 미처리현황정보
	private String statCnt2; //Q&A 자체처리현황정보
	private String statCnt3; //Q&A 타기관처리현황정보
	private String statCnt4; //제안하기 미처리현황정보
	private String statCnt5; //제안하기 자체처리현황정보
	private String statCnt6; //제안하기 타기관처리현황정보
	private String stat;     //처리현황정보
	private String up_del_stat; //수정 삭제 가능여부
	private String del_file_id; //삭제 파일번호정보
	private String answer_seq; //답변일련번호
	private String answerContents; //답변내용
	private String public_trans_yn; 
	private String answer_file_id;
	private String question_file_id;
	private String category1_nm;
	private String category2_nm;
	private String password;  // 비밀번호
	private String query_user_info; // 질의자정보
    
	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuery_user_info() {
        return query_user_info;
    }

    public void setQuery_user_info(String query_user_info) {
        this.query_user_info = query_user_info;
    }

    public String getAnswer_file_id() {
		return answer_file_id;
	}

	public void setAnswer_file_id(String answer_file_id) {
		this.answer_file_id = answer_file_id;
	}



	/**
	 * BuAdNotiLVO 생성자
	 */
	public OfferVO(){
		
		this.faq_id = "";
		this.type = "";
		this.title = "";
		this.contents = "";
		this.answer_contents = "";  //의견 
		this.cause_contents = "";  //이유 
		this.read_count = 0;
		this.file_id = "";
		this.use_yn = "";
		this.reg_id = ""; 
		this.reg_dt = "";
		this.edit_id = "";
		this.edit_dt = "";
		this.seq = ""; // 아이디
		this.board_type = ""; //게시판구분
		this.title = "";    //제목
		this.contents = ""; //내용
		this.answerContents = ""; //답변내용
		this.call_receive_yn = "N"; // SMS수신여부
		this.cell_number = "";    // 핸드폰번호
		this.email_receive_yn = "N"; // 이메일수신여부
		this.email = "";    // 이메일
		this.public_trans_yn = "";   //타기관지정여부
		this.insert_type = "";  //등록반법분류
		this.read_count = 0; // 조회 수
		this.open_yn = "Y"; //userSN
		this.file_id = "";  // size
		this.reg_id = ""; // 등록자ID
		this.reg_dt = ""; // 등록일시
		this.edit_id = ""; // 수정자ID
		this.edit_dt = ""; // 수정일시
		this.reg_nm = ""; //등록자이름
		this.category1 = ""; //대분류코드
		this.category2 = ""; //소분류코드
		this.statCnt1 = ""; //Q&A 미처리현황정보
		this.statCnt2 = ""; //Q&A 자체처리현황정보
		this.statCnt3 = ""; //Q&A 타기관처리현황정보
		this.statCnt4 = ""; //제안하기 미처리현황정보
		this.statCnt5 = ""; //제안하기 자체처리현황정보
		this.statCnt6 = ""; //제안하기 타기관처리현황정보
		this.stat = "";     //처리현황정보
		this.up_del_stat = ""; //수장 삭제 가능여부
		this.del_file_id = ""; //삭제 파일번호정보
		this.answer_seq = ""; //답변일련번호
		
		
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
	
	public String getPublic_trans_yn() {
		return public_trans_yn;
	}



	public void setPublic_trans_yn(String public_trans_yn) {
		this.public_trans_yn = public_trans_yn;
	}



	public String getAnswerContents() {
		return answerContents;
	}



	public void setAnswerContents(String answerContents) {
		this.answerContents = answerContents;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getAnswer_id() {
		return answer_id;
	}



	public void setAnswer_id(String answer_id) {
		this.answer_id = answer_id;
	}



	public String getInsert_type() {
		return insert_type;
	}



	public void setInsert_type(String insert_type) {
		this.insert_type = insert_type;
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



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getCode_nm() {
		return code_nm;
	}



	public void setCode_nm(String code_nm) {
		this.code_nm = code_nm;
	}



	public String getP_code() {
		return p_code;
	}



	public void setP_code(String p_code) {
		this.p_code = p_code;
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



	public String getStat() {
		return stat;
	}



	public void setStat(String stat) {
		this.stat = stat;
	}



	public String getAnswer_cont() {
		return answer_cont;
	}



	public void setAnswer_cont(String answer_cont) {
		this.answer_cont = answer_cont;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getOpen_yn() {
		return open_yn;
	}



	public void setOpen_yn(String open_yn) {
		this.open_yn = open_yn;
	}
	
	public String getAnswer_contents() {
		return answer_contents;
	}



	public void setAnswer_contents(String answer_contents) {
		this.answer_contents = answer_contents;
	}



	public String getCause_contents() {
		return cause_contents;
	}



	public void setCause_contents(String cause_contents) {
		this.cause_contents = cause_contents;
	}



	public String getContents() {
		return contents;
	}



	public void setContents(String contents) {
		this.contents = contents;
	}



	public String getFaq_id() {
		return faq_id;
	}



	public void setFaq_id(String faq_id) {
		this.faq_id = faq_id;
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



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getUse_yn() {
		return use_yn;
	}



	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}



	public String getEmail_receive_yn() {
		return email_receive_yn;
	}



	public void setEmail_receive_yn(String email_receive_yn) {
		this.email_receive_yn = email_receive_yn;
	}



	public String getFaq_Content() {
		return faq_Content;
	}



	public void setFaq_Content(String faq_Content) {
		this.faq_Content = faq_Content;
	}



	public String getFaq_title() {
		return faq_title;
	}



	public void setFaq_title(String faq_title) {
		this.faq_title = faq_title;
	}



	public String getSms_receive_yn() {
		return sms_receive_yn;
	}



	public void setSms_receive_yn(String sms_receive_yn) {
		this.sms_receive_yn = sms_receive_yn;
	}



	public String getFaq_cate() {
		return faq_cate;
	}



	public void setFaq_cate(String faq_cate) {
		this.faq_cate = faq_cate;
	}



	public String getCall_num() {
		return call_num;
	}



	public void setCall_num(String call_num) {
		this.call_num = call_num;
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



	public String getReg_nm() {
		return reg_nm;
	}



	public void setReg_nm(String reg_nm) {
		this.reg_nm = reg_nm;
	}



	public String getBoard_type() {
		return board_type;
	}



	public void setBoard_type(String board_type) {
		this.board_type = board_type;
	}



	public String getQuestion_contents() {
		return question_contents;
	}



	public void setQuestion_contents(String question_contents) {
		this.question_contents = question_contents;
	}



	public String getSeq() {
		return seq;
	}



	public void setSeq(String seq) {
		this.seq = seq;
	}



	public String getAnswer_seq() {
		return answer_seq;
	}



	public void setAnswer_seq(String answer_seq) {
		this.answer_seq = answer_seq;
	}



	public String getDel_file_id() {
		return del_file_id;
	}



	public void setDel_file_id(String del_file_id) {
		this.del_file_id = del_file_id;
	}



	public String getStatCnt1() {
		return statCnt1;
	}



	public void setStatCnt1(String statCnt1) {
		this.statCnt1 = statCnt1;
	}



	public String getStatCnt2() {
		return statCnt2;
	}



	public void setStatCnt2(String statCnt2) {
		this.statCnt2 = statCnt2;
	}



	public String getStatCnt3() {
		return statCnt3;
	}



	public void setStatCnt3(String statCnt3) {
		this.statCnt3 = statCnt3;
	}



	public String getStatCnt4() {
		return statCnt4;
	}



	public void setStatCnt4(String statCnt4) {
		this.statCnt4 = statCnt4;
	}



	public String getStatCnt5() {
		return statCnt5;
	}



	public void setStatCnt5(String statCnt5) {
		this.statCnt5 = statCnt5;
	}



	public String getStatCnt6() {
		return statCnt6;
	}



	public void setStatCnt6(String statCnt6) {
		this.statCnt6 = statCnt6;
	}



	public String getUp_del_stat() {
		return up_del_stat;
	}



	public void setUp_del_stat(String up_del_stat) {
		this.up_del_stat = up_del_stat;
	}



	public String getQuestion_file_id() {
		return question_file_id;
	}



	public void setQuestion_file_id(String question_file_id) {
		this.question_file_id = question_file_id;
	}



	public String getCategory1_nm() {
		return category1_nm;
	}



	public void setCategory1_nm(String category1_nm) {
		this.category1_nm = category1_nm;
	}



	public String getCategory2_nm() {
		return category2_nm;
	}



	public void setCategory2_nm(String category2_nm) {
		this.category2_nm = category2_nm;
	}
	
	
}