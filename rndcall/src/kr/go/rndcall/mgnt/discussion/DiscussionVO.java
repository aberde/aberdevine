package kr.go.rndcall.mgnt.discussion;

import java.util.ArrayList;

import kr.go.rndcall.mgnt.common.BaseVO;

public class DiscussionVO extends BaseVO {

	private ArrayList reply_list;
	private ArrayList reply_file_list;
	private ArrayList reply_reply_list;
	private ArrayList reply_reply_file_list;
	private ArrayList content_file_list;

	private String discuss_id;
	private String dis_title;
	private String dis_contents;
	private String dis_start_dt;
	private String dis_end_dt;
	private String dis_state;
	private String dis_count;
	private String discuss_op_id;
	private String dis_comment;
	private String p_discuss_op_id;
	private String recom_cnt;
	private String opp_cnt;
	private String reg_nm;
	private String reply_cnt;
	private String reg_dt;
	private String dis_cont_id;
	private String file_id;
	private String file_nm;
	private String file_path;
	private String file_size;
	private String dis_op_cnt;
	private String level;
	private String ap_op;
	private String span_id;
	private String dis_comment_reply;
	private String man_type_nm;
	private String issue_nm;
	private String discuss_op_cnt;
	private String reg_id;
	
	private String[] del_files;
	private String open_nm;
	private String notice_contents;
	private String discuss_notice_id;
	private String dis_email;
	private String ordernum;
	private String reply_kind;
	private String dis_email_cont;
	private String cate_job_ap_total;
	private String cate_job_op_total;
	private String email;
	private String dept_nm;
	private String opinion;
	private String reply;
	private String name_ko;
	private String dis_reply_cnt;
	private String page_num;
	private String ymd_reg_dt;
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
	public String getCate_job_ap_total() {
		return cate_job_ap_total;
	}
	public String getCate_job_op_total() {
		return cate_job_op_total;
	}
	public ArrayList getContent_file_list() {
		return content_file_list;
	}
	public String[] getDel_files() {
		return del_files;
	}
	public String getDept_nm() {
		return dept_nm;
	}
	public String getDis_comment() {
		return dis_comment;
	}
	public String getDis_comment_reply() {
		return dis_comment_reply;
	}
	public String getDis_cont_id() {
		return dis_cont_id;
	}
	public String getDis_contents() {
		return dis_contents;
	}
	public String getDis_count() {
		return dis_count;
	}
	public String getDis_email() {
		return dis_email;
	}
	public String getDis_email_cont() {
		return dis_email_cont;
	}
	public String getDis_end_dt() {
		return dis_end_dt;
	}
	public String getDis_op_cnt() {
		return dis_op_cnt;
	}
	public String getDis_reply_cnt() {
		return dis_reply_cnt;
	}
	public String getDis_start_dt() {
		return dis_start_dt;
	}
	public String getDis_state() {
		return dis_state;
	}
	public String getDis_title() {
		return dis_title;
	}
	public String getDiscuss_id() {
		return discuss_id;
	}
	public String getDiscuss_notice_id() {
		return discuss_notice_id;
	}
	public String getDiscuss_op_cnt() {
		return discuss_op_cnt;
	}
	public String getDiscuss_op_id() {
		return discuss_op_id;
	}
	public String getEmail() {
		return email;
	}
	public String getFile_id() {
		return file_id;
	}
	public String getFile_nm() {
		return file_nm;
	}
	public String getFile_path() {
		return file_path;
	}
	public String getFile_size() {
		return file_size;
	}
	public String getIssue_nm() {
		return issue_nm;
	}
	public String getLevel() {
		return level;
	}
	public String getMan_type_nm() {
		return man_type_nm;
	}
	public String getName_ko() {
		return name_ko;
	}
	public String getNotice_contents() {
		return notice_contents;
	}
	public String getOpen_nm() {
		return open_nm;
	}
	public String getOpinion() {
		return opinion;
	}
	public String getOpp_cnt() {
		return opp_cnt;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public String getP_discuss_op_id() {
		return p_discuss_op_id;
	}
	public String getPage_num() {
		return page_num;
	}
	public String getRecom_cnt() {
		return recom_cnt;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public String getReg_id() {
		return reg_id;
	}
	public String getReg_nm() {
		return reg_nm;
	}
	public String getReply() {
		return reply;
	}
	public String getReply_cnt() {
		return reply_cnt;
	}
	public ArrayList getReply_file_list() {
		return reply_file_list;
	}
	public String getReply_kind() {
		return reply_kind;
	}
	public ArrayList getReply_list() {
		return reply_list;
	}
	public ArrayList getReply_reply_file_list() {
		return reply_reply_file_list;
	}
	public ArrayList getReply_reply_list() {
		return reply_reply_list;
	}
	public String getSpan_id() {
		return span_id;
	}
	public String getYmd_reg_dt() {
		return ymd_reg_dt;
	}
	public void setAp_op(String ap_op) {
		this.ap_op = ap_op;
	}
	public void setCate_job_ap_total(String cate_job_ap_total) {
		this.cate_job_ap_total = cate_job_ap_total;
	}
	public void setCate_job_op_total(String cate_job_op_total) {
		this.cate_job_op_total = cate_job_op_total;
	}
	public void setContent_file_list(ArrayList content_file_list) {
		this.content_file_list = content_file_list;
	}
	public void setDel_files(String[] del_files) {
		this.del_files = del_files;
	}
	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}
	public void setDis_comment(String dis_comment) {
		this.dis_comment = dis_comment;
	}
	public void setDis_comment_reply(String dis_comment_reply) {
		this.dis_comment_reply = dis_comment_reply;
	}
	public void setDis_cont_id(String dis_cont_id) {
		this.dis_cont_id = dis_cont_id;
	}
	public void setDis_contents(String dis_contents) {
		this.dis_contents = dis_contents;
	}
	public void setDis_count(String dis_count) {
		this.dis_count = dis_count;
	}
	public void setDis_email(String dis_email) {
		this.dis_email = dis_email;
	}
	public void setDis_email_cont(String dis_email_cont) {
		this.dis_email_cont = dis_email_cont;
	}
	public void setDis_end_dt(String dis_end_dt) {
		this.dis_end_dt = dis_end_dt;
	}
	public void setDis_op_cnt(String dis_op_cnt) {
		this.dis_op_cnt = dis_op_cnt;
	}
	public void setDis_reply_cnt(String dis_reply_cnt) {
		this.dis_reply_cnt = dis_reply_cnt;
	}
	public void setDis_start_dt(String dis_start_dt) {
		this.dis_start_dt = dis_start_dt;
	}
	public void setDis_state(String dis_state) {
		this.dis_state = dis_state;
	}
	public void setDis_title(String dis_title) {
		this.dis_title = dis_title;
	}
	public void setDiscuss_id(String discuss_id) {
		this.discuss_id = discuss_id;
	}
	public void setDiscuss_notice_id(String discuss_notice_id) {
		this.discuss_notice_id = discuss_notice_id;
	}
	public void setDiscuss_op_cnt(String discuss_op_cnt) {
		this.discuss_op_cnt = discuss_op_cnt;
	}
	public void setDiscuss_op_id(String discuss_op_id) {
		this.discuss_op_id = discuss_op_id;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public void setFile_nm(String file_nm) {
		this.file_nm = file_nm;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	public void setIssue_nm(String issue_nm) {
		this.issue_nm = issue_nm;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public void setMan_type_nm(String man_type_nm) {
		this.man_type_nm = man_type_nm;
	}
	public void setName_ko(String name_ko) {
		this.name_ko = name_ko;
	}
	public void setNotice_contents(String notice_contents) {
		this.notice_contents = notice_contents;
	}
	public void setOpen_nm(String open_nm) {
		this.open_nm = open_nm;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public void setOpp_cnt(String opp_cnt) {
		this.opp_cnt = opp_cnt;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public void setP_discuss_op_id(String p_discuss_op_id) {
		this.p_discuss_op_id = p_discuss_op_id;
	}
	public void setPage_num(String page_num) {
		this.page_num = page_num;
	}
	public void setRecom_cnt(String recom_cnt) {
		this.recom_cnt = recom_cnt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}
	public void setReg_nm(String reg_nm) {
		this.reg_nm = reg_nm;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public void setReply_cnt(String reply_cnt) {
		this.reply_cnt = reply_cnt;
	}
	public void setReply_file_list(ArrayList reply_file_list) {
		this.reply_file_list = reply_file_list;
	}
	public void setReply_kind(String reply_kind) {
		this.reply_kind = reply_kind;
	}
	public void setReply_list(ArrayList reply_list) {
		this.reply_list = reply_list;
	}
	public void setReply_reply_file_list(ArrayList reply_reply_file_list) {
		this.reply_reply_file_list = reply_reply_file_list;
	}
	public void setReply_reply_list(ArrayList reply_reply_list) {
		this.reply_reply_list = reply_reply_list;
	}
	public void setSpan_id(String span_id) {
		this.span_id = span_id;
	}
	public void setYmd_reg_dt(String ymd_reg_dt) {
		this.ymd_reg_dt = ymd_reg_dt;
	}

}
