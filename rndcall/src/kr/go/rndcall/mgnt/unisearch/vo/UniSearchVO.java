/** 
 * {파일, 클래스에 대한 설명을 기술한다.}
 * @author bkhwang
 * @version {Revision}
 * @since 2009. 10. 20
 * Copyright (C) 2007 by SAMSUNG SDS co.,Ltd. All right reserved.
 */
package kr.go.rndcall.mgnt.unisearch.vo;

import java.lang.reflect.Field;
import java.util.Arrays;

import kr.go.rndcall.mgnt.common.BaseVO;

/**
 * @author bkhwang
 *
 */
public class UniSearchVO extends BaseVO {

	// 사업공고
	private String seq = "";
	private String seq_ans = "";
	private String title = "";
	private String contents = "";
	private String answercont = "";
	private String read_count = "";
	private String reg_dt = "";
	private String reg_nm = "";
	private String board_type = "";
	
	public String getAnswercont() {
		return answercont;
	}
	public void setAnswercont(String answercont) {
		this.answercont = answercont;
	}
	public String getRead_count() {
		return read_count;
	}
	public void setRead_count(String read_count) {
		this.read_count = read_count;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
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
	public String getSeq_ans() {
		return seq_ans;
	}
	public void setSeq_ans(String seq_ans) {
		this.seq_ans = seq_ans;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getBoard_type() {
		return board_type;
	}
	public void setBoard_type(String board_type) {
		this.board_type = board_type;
	}



	



	
}
