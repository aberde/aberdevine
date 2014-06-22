package kr.go.rndcall.mgnt.statistic.form;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.struts.upload.FormFile;

import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.common.BaseForm;
import kr.go.rndcall.mgnt.statistic.vo.StatisticSearchVO;
import kr.go.rndcall.mgnt.statistic.vo.StatisticVO;
import kr.go.rndcall.mgnt.inquire.vo.InquireVO;

public class StatisticForm extends BaseForm{
	
	private StatisticVO vo = new StatisticVO();
	private InquireVO vo1 = new InquireVO();
	private StatisticSearchVO searchVO = new StatisticSearchVO();
	
	private ArrayList yearListDesc = Util.getIa_yearList(2002,  ((Calendar) Calendar.getInstance()).get(Calendar.YEAR));	
	private ArrayList mon_list = Util.getMonList();
	
	public ArrayList getMon_list() {
		return mon_list;
	}
	public void setMon_list(ArrayList mon_list) {
		this.mon_list = mon_list;
	}
	public StatisticSearchVO getSearchVO() {
		return searchVO;
	}
	public void setSearchVO(StatisticSearchVO searchVO) {
		this.searchVO = searchVO;
	}
	public StatisticVO getVo() {
		return vo;
	}
	public void setVo(StatisticVO vo) {
		this.vo = vo;
	}
	public ArrayList getYearListDesc() {
		return yearListDesc;
	}
	public void setYearListDesc(ArrayList yearListDesc) {
		this.yearListDesc = yearListDesc;
	}
	public InquireVO getVo1() {
		return vo1;
	}
	public void setVo1(InquireVO vo1) {
		this.vo1 = vo1;
	}
	
	
}