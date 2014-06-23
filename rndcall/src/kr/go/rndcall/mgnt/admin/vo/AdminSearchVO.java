package kr.go.rndcall.mgnt.admin.vo;

import java.lang.reflect.Field;
import java.util.Arrays;

import kr.go.rndcall.mgnt.common.BaseSearchVO;

public class AdminSearchVO extends BaseSearchVO{
	// 검색을 위한 조건들
	protected String searchTxt = ""; // 검색Text
	protected String whichSearch = ""; // 검색구분
	protected String start_yy =""; // 검색시작년도
	protected String start_mm =""; // 검색시작월
	protected String end_yy =""; // 검색종료년도
	protected String end_mm =""; // 검색종료월
	protected String searchType =""; // 검색분류
    protected String code =""; 
    protected String category1 =""; 
    protected String category2 =""; 

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

	public String getEnd_mm() {
		return end_mm;
	}

	public void setEnd_mm(String end_mm) {
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

    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

}