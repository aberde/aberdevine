package kr.go.rndcall.mgnt.faq.vo;

import java.lang.reflect.Field;
import java.util.Arrays;

import kr.go.rndcall.mgnt.common.BaseSearchVO;

public class FaqSearchVO extends BaseSearchVO{
	// 검색을 위한 조건들
	protected String searchTxt = ""; // 검색Text
	protected String whichSearch = "contents"; // 검색구분
	protected String seq = ""; //게시글고유번호
	protected String board_type = ""; //보드타입
	protected String searchCategory = ""; //카테고리
	protected String type = ""; //타입
	protected String flag = ""; //타입
	

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

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
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

}
