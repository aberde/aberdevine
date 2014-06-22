/** 
 * {파일, 클래스에 대한 설명을 기술한다.}
 * @author bkhwang
 * @version {Revision}
 * @since 2009. 10. 20
 * Copyright (C) 2007 by SAMSUNG SDS co.,Ltd. All right reserved.
 */
package kr.go.rndcall.mgnt.unisearch.vo;

import java.util.Calendar;

import kr.go.rndcall.mgnt.common.BaseSearchVO;

/**
 * @author bkhwang
 *
 */
public class UniSearchSearchVO extends BaseSearchVO {
	
	private String target = "";
	private String year = "";
	private String word = "";
	private String bf_word = "";
	private String crud = "";
	private String whichSearch = "";
	private String searchTxt = "";
	private String board_type = ""; // 보드타입
	private String name = ""; // 
	private String seq = ""; // 
	private String type = ""; //
	private String flag; //상세 수정 구분
	
	public String getCrud() {
		return crud;
	}
	public void setCrud(String crud) {
		this.crud = crud;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getBf_word() {
		return bf_word;
	}
	public void setBf_word(String bf_word) {
		this.bf_word = bf_word;
	}
	public String getWhichSearch() {
		return whichSearch;
	}
	public void setWhichSearch(String whichSearch) {
		this.whichSearch = whichSearch;
	}
	public String getSearchTxt() {
		return searchTxt;
	}
	public void setSearchTxt(String searchTxt) {
		this.searchTxt = searchTxt;
	}
	public String getBoard_type() {
		return board_type;
	}
	public void setBoard_type(String board_type) {
		this.board_type = board_type;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

}
