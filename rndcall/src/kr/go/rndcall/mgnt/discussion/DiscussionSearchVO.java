package kr.go.rndcall.mgnt.discussion;

import kr.go.rndcall.mgnt.common.BaseSearchVO;

public class DiscussionSearchVO extends BaseSearchVO {

	private String discuss_id;
	private String discuss_op_id;
	private String ap_op;
	private String span_id;
	private String file_id;
	private String search_text;
	private String search_type;
	private String dis_cont_id;
	private String discuss_notice_id;
	private String start_dt;
	private String end_dt;
	private String p_discuss_op_id;
	private String category_id;
	
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getAp_op() {
		return ap_op;
	}
	public String getDis_cont_id() {
		return dis_cont_id;
	}
	public String getDiscuss_id() {
		return discuss_id;
	}
	public String getDiscuss_notice_id() {
		return discuss_notice_id;
	}
	public String getDiscuss_op_id() {
		return discuss_op_id;
	}
	public String getEnd_dt() {
		return end_dt;
	}
	public String getFile_id() {
		return file_id;
	}
	public String getP_discuss_op_id() {
		return p_discuss_op_id;
	}
	public String getSearch_text() {
		return search_text;
	}
	public String getSearch_type() {
		return search_type;
	}
	public String getSpan_id() {
		return span_id;
	}
	public String getStart_dt() {
		return start_dt;
	}
	public void setAp_op(String ap_op) {
		this.ap_op = ap_op;
	}
	public void setDis_cont_id(String dis_cont_id) {
		this.dis_cont_id = dis_cont_id;
	}
	public void setDiscuss_id(String discuss_id) {
		this.discuss_id = discuss_id;
	}
	public void setDiscuss_notice_id(String discuss_notice_id) {
		this.discuss_notice_id = discuss_notice_id;
	}
	public void setDiscuss_op_id(String discuss_op_id) {
		this.discuss_op_id = discuss_op_id;
	}
	public void setEnd_dt(String end_dt) {
		this.end_dt = end_dt;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public void setP_discuss_op_id(String p_discuss_op_id) {
		this.p_discuss_op_id = p_discuss_op_id;
	}
	public void setSearch_text(String search_text) {
		this.search_text = search_text;
	}
	public void setSearch_type(String search_type) {
		this.search_type = search_type;
	}
	public void setSpan_id(String span_id) {
		this.span_id = span_id;
	}
	public void setStart_dt(String start_dt) {
		this.start_dt = start_dt;
	}
}
