package kr.go.rndcall.mgnt.inquire.vo;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Calendar;

import kr.go.rndcall.mgnt.common.BaseSearchVO;

public class InquireSearchVO extends BaseSearchVO{
	// 검색을 위한 조건들
	protected String searchTxt = ""; // 검색Text
	protected String whichSearch = "all"; // 검색구분
	protected String searchType =""; // 검색분류
	protected String seq =""; // 글번호
	protected String board_type =""; // 게시물 구분
	protected String searchCategory =""; // 분야구분
	protected String type ="4"; // 현황구분구분
	protected String flag =""; //상세 수정 구분
	protected String trans_email ="";
	protected String trans_phone ="";
	protected String trans_id =""; //타기관담당자ID
	protected String insert_type =""; // 등록방법
	protected String stat =""; // 상태
	
	Calendar cal = Calendar.getInstance();
	
	protected String start_yy ="2007"; // 검색시작년도
	protected String start_mm ="01"; // 검색시작월
	protected String end_yy =Integer.toString(cal.get(Calendar.YEAR)); // 검색종료년도
	protected String end_mm = Integer.toString(cal.get(Calendar.MONTH)+1);	 // 검색종료월
	protected String public_trans_yn = "";
	
	public String getPublic_trans_yn() {
		return public_trans_yn;
	}

	public void setPublic_trans_yn(String public_trans_yn) {
		this.public_trans_yn = public_trans_yn;
	}

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

	/**
	 * 검색Text를 리턴하는 메소드
	 */
	public String getSearchTxt() {
		return searchTxt;
	}

	/**
	 * 등록방법을 리턴하는 메소드
	 */
	public String getInsert_type() {
		return insert_type;
	}

	/**
	 * 검색구분을 리턴하는 메소드
	 */
	public String getWhichSearch() {
		return whichSearch;
	}

	/**
	 * 검색Text를 설정하는 메소드
	 */
	public void setSearchTxt(String searchTxt) {
		this.searchTxt = searchTxt;
	}

	/**
	 * 검색구분을 설정하는 메소드
	 */
	public void setWhichSearch(String whichSearch) {
		this.whichSearch = whichSearch;
	}

	public String getEnd_mm() {
		return end_mm;
	}

	public void setEnd_mm(String end_mm) {
		if(end_mm.length() < 2){
			end_mm =  "0"+end_mm;
		}
		this.end_mm = end_mm;
	}

	public String getEnd_yy() {
		return end_yy;
	}

	public void setEnd_yy(String end_yy) {
		this.end_yy = end_yy;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getStart_mm() {
		return start_mm;
	}

	public void setStart_mm(String start_mm) {
		this.start_mm = start_mm;
	}

	public String getStart_yy() {
		return start_yy;
	}

	public void setStart_yy(String start_yy) {
		this.start_yy = start_yy;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getBoard_type() {
		return board_type;
	}

	public void setBoard_type(String board_type) {
		this.board_type = board_type;
	}

	public String getSearchCategory() {
		return searchCategory;
	}

	public void setSearchCategory(String searchCategory) {
		this.searchCategory = searchCategory;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}

	public void setInsert_type(String insert_type) {
		this.insert_type = insert_type;
	}

	public String getTrans_email() {
		return trans_email;
	}

	public void setTrans_email(String trans_email) {
		this.trans_email = trans_email;
	}

	public String getTrans_phone() {
		return trans_phone;
	}

	public void setTrans_phone(String trans_phone) {
		this.trans_phone = trans_phone;
	}

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}