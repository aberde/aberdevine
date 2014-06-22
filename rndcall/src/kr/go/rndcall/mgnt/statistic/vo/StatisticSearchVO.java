package kr.go.rndcall.mgnt.statistic.vo;

import java.util.Calendar;

import kr.go.rndcall.mgnt.common.BaseSearchVO;

public class StatisticSearchVO extends BaseSearchVO{
	
	Calendar cal = Calendar.getInstance();
	
	// 검색을 위한 조건들
	private String searchTxt = ""; 		// 검색Text
	private String whichSearch = ""; 	// 검색구분
	private String searchTxt1 = ""; 		// 검색Text
	private String whichSearch1 = ""; 	// 검색구분
	private String stat_type = "";		// 통계구분
	private String start_yy =""; // 검색시작년도
	private String start_mm =""; // 검색시작월
	private String end_yy =Integer.toString(cal.get(Calendar.YEAR)); // 검색종료년도
	private String end_mm = Integer.toString(cal.get(Calendar.MONTH)+1);	 // 검색종료월
	private String down_type= "";
	private String category1 = "";
	private String category2 = "";
	private String seq =""; // 글번호
	private String board_type =""; // 게시물 구분
	
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
	public String getSearchTxt() {
		return searchTxt;
	}
	public void setSearchTxt(String searchTxt) {
		this.searchTxt = searchTxt;
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
	public String getStat_type() {
		return stat_type;
	}
	public void setStat_type(String stat_type) {
		this.stat_type = stat_type;
	}
	public String getWhichSearch() {
		return whichSearch;
	}
	public void setWhichSearch(String whichSearch) {
		this.whichSearch = whichSearch;
	}
	public String getDown_type() {
		return down_type;
	}
	public void setDown_type(String down_type) {
		this.down_type = down_type;
	}
	public String getWhichSearch1() {
		return whichSearch1;
	}
	public void setWhichSearch1(String whichSearch1) {
		this.whichSearch1 = whichSearch1;
	}
	public String getBoard_type() {
		return board_type;
	}
	public void setBoard_type(String board_type) {
		this.board_type = board_type;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getSearchTxt1() {
		return searchTxt1;
	}
	public void setSearchTxt1(String searchTxt1) {
		this.searchTxt1 = searchTxt1;
	}
}
