package kr.go.rndcall.mgnt.inquire.vo;

import java.lang.reflect.Field;
import java.util.Arrays;

import kr.go.rndcall.mgnt.common.BaseVO;

public class InquireVO extends BaseVO{
	
	private String seq; // 아이디
	private String board_type; //게시판구분
	private String title;    //제목
	private String contents; //내용
	private String answerContents; //답변내용
	private String call_receive_yn; // SMS수신여부
	private String cell_number;    // 핸드폰번호
	private String email_receive_yn; // 이메일수신여부
	private String email;    // 이메일
	private String public_trans_yn;   //타기관지정여부
	private String public_trans_id;   //타기관담당자id
	private String insert_type;  //등록방법분류
	private int read_count; // 조회 수
	private String open_yn; //userSN
	private String del_yn;       // formNo
	private String file_id;  // size
	private String reg_id; // 등록자ID
	private String reg_dt; // 등록일시
	private String edit_id; // 수정자ID
	private String edit_dt; // 수정일시
	private String reg_nm; //등록자이름
	private String category1; //대분류코드
	private String category2; //소분류코드
	private String category1_nm; //대분류코드명
	private String category2_nm; //소분류코드명
	
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
	
	private String ntis_reg_id;
	private String answer_file_id;
	private String answer_del_yn;
	private String answer_reg_nm;
	private String answer_reg_dt;
	private String answer_edit_nm;
	private String answer_edit_dt;
	
	private String question_file_id;
	private String trans_id; //타기관담당자ID
	private String trans_nm; //타기관담당자이름
	private String trans_org_nm; //타기관담당자 소속기관
	private String trans_attached_nm; //타기관담당자 소속부서
	private String gov_answer; //타기관담당자 답변등록 여부
	private String tel; //타기관담당자 연락처
	private String mem_table_nm = "";
	public String getPublic_trans_org_nm() {
		return public_trans_org_nm;
	}

	public void setPublic_trans_org_nm(String public_trans_org_nm) {
		this.public_trans_org_nm = public_trans_org_nm;
	}

	private String public_trans_org_nm = "";
	
	public String getMem_table_nm() {
		return mem_table_nm;
	}

	public void setMem_table_nm(String mem_table_nm) {
		this.mem_table_nm = mem_table_nm;
	}

	/**
	 * BuAdNotiLVO 생성자
	 */
	public InquireVO(){
		
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
		this.public_trans_id = "";   //타기관담당자id
		this.insert_type = "";  //등록반법분류
		this.read_count = 0; // 조회 수
		this.open_yn = "Y"; //userSN
		this.del_yn = "";       // formNo
		this.file_id = "";  // size
		this.reg_id = ""; // 등록자ID
		this.reg_dt = ""; // 등록일시
		this.edit_id = ""; // 수정자ID
		this.edit_dt = ""; // 수정일시
		this.reg_nm = ""; //등록자이름
		this.category1 = ""; //대분류코드
		this.category2 = ""; //소분류코드
		this.category1_nm = ""; //대분류코드명
		this.category2_nm = ""; //소분류코드명
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
		
		this.ntis_reg_id = "";
		this.answer_file_id = "";
		this.answer_del_yn = "";
		this.answer_reg_nm = "";
		this.answer_reg_dt = "";
		this.answer_edit_nm = "";
		this.answer_edit_dt = "";
		this.question_file_id = "";
		
		this.trans_id = "";
		this.trans_nm = "";
		this.trans_org_nm = "";
		this.trans_attached_nm = "";
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
	
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
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

	public String getPublic_trans_yn() {
		return public_trans_yn;
	}

	public void setPublic_trans_yn(String public_trans_yn) {
		this.public_trans_yn = public_trans_yn;
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

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getUp_del_stat() {
		return up_del_stat;
	}

	public void setUp_del_stat(String up_del_stat) {
		this.up_del_stat = up_del_stat;
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

	public String getDel_file_id() {
		return del_file_id;
	}

	public void setDel_file_id(String del_file_id) {
		this.del_file_id = del_file_id;
	}

	public String getAnswer_seq() {
		return answer_seq;
	}

	public void setAnswer_seq(String answer_seq) {
		this.answer_seq = answer_seq;
	}

	public String getAnswer_del_yn() {
		return answer_del_yn;
	}

	public void setAnswer_del_yn(String answer_del_yn) {
		this.answer_del_yn = answer_del_yn;
	}

	public String getAnswer_edit_dt() {
		return answer_edit_dt;
	}

	public void setAnswer_edit_dt(String answer_edit_dt) {
		this.answer_edit_dt = answer_edit_dt;
	}

	public String getAnswer_edit_nm() {
		return answer_edit_nm;
	}

	public void setAnswer_edit_nm(String answer_edit_nm) {
		this.answer_edit_nm = answer_edit_nm;
	}

	public String getAnswer_file_id() {
		return answer_file_id;
	}

	public void setAnswer_file_id(String answer_file_id) {
		this.answer_file_id = answer_file_id;
	}

	public String getAnswer_reg_dt() {
		return answer_reg_dt;
	}

	public void setAnswer_reg_dt(String answer_reg_dt) {
		this.answer_reg_dt = answer_reg_dt;
	}

	public String getAnswer_reg_nm() {
		return answer_reg_nm;
	}

	public void setAnswer_reg_nm(String answer_reg_nm) {
		this.answer_reg_nm = answer_reg_nm;
	}

	public String getNtis_reg_id() {
		return ntis_reg_id;
	}

	public void setNtis_reg_id(String ntis_reg_id) {
		this.ntis_reg_id = ntis_reg_id;
	}

	public String getQuestion_file_id() {
		return question_file_id;
	}

	public void setQuestion_file_id(String question_file_id) {
		this.question_file_id = question_file_id;
	}

	public String getTrans_attached_nm() {
		return trans_attached_nm;
	}

	public void setTrans_attached_nm(String trans_attached_nm) {
		this.trans_attached_nm = trans_attached_nm;
	}

	public String getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}

	public String getTrans_nm() {
		return trans_nm;
	}

	public void setTrans_nm(String trans_nm) {
		this.trans_nm = trans_nm;
	}

	public String getTrans_org_nm() {
		return trans_org_nm;
	}

	public void setTrans_org_nm(String trans_org_nm) {
		this.trans_org_nm = trans_org_nm;
	}

	public String getGov_answer() {
		return gov_answer;
	}

	public void setGov_answer(String gov_answer) {
		this.gov_answer = gov_answer;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}