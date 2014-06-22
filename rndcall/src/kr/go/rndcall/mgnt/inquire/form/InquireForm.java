package kr.go.rndcall.mgnt.inquire.form;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.struts.upload.FormFile;

import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.common.BaseForm;
import kr.go.rndcall.mgnt.inquire.vo.InquireSearchVO;
import kr.go.rndcall.mgnt.inquire.vo.InquireResultVO;
import kr.go.rndcall.mgnt.inquire.vo.InquireAttachVO;
import kr.go.rndcall.mgnt.inquire.vo.InquireVO;
import kr.go.rndcall.mgnt.inquire.vo.SatiVO;
import kr.go.rndcall.mgnt.login.LoginVO;

public class InquireForm extends BaseForm{
	
	private InquireVO vo = new InquireVO();
	private SatiVO satiVO = new SatiVO();
	private InquireSearchVO searchVO = new InquireSearchVO();
	private InquireResultVO resultVO = new InquireResultVO();
	private InquireAttachVO attachVO = new InquireAttachVO();
	private LoginVO loginVO = new LoginVO();
	
	private ArrayList yearListDesc = Util.getIa_yearList(2002,  ((Calendar) Calendar.getInstance()).get(Calendar.YEAR));
	private ArrayList mon_list = Util.getMonList();
	
    private List fileList = new ArrayList();
    
	public List getFileList() {
		return fileList;
	}
	public void setFileList(List fileList) {
		this.fileList = fileList;
	}
	public InquireVO getVo() {
		return vo;
	}
	public void setVo(InquireVO vo) {
		this.vo = vo;
	}
	public InquireAttachVO getAttachVO() {
		return attachVO;
	}
	public void setAttachVO(InquireAttachVO attachVO) {
		this.attachVO = attachVO;
	}
	public InquireResultVO getResultVO() {
		return resultVO;
	}
	public void setResultVO(InquireResultVO resultVO) {
		this.resultVO = resultVO;
	}
	public InquireSearchVO getSearchVO() {
		return searchVO;
	}
	public void setSearchVO(InquireSearchVO searchVO) {
		this.searchVO = searchVO;
	}
	public SatiVO getSatiVO() {
		return satiVO;
	}
	public void setSatiVO(SatiVO satiVO) {
		this.satiVO = satiVO;
	}
	public LoginVO getLoginVO() {
		return loginVO;
	}
	public void setLoginVO(LoginVO loginVO) {
		this.loginVO = loginVO;
	}    
	
	public ArrayList getYearListDesc() {
		return yearListDesc;
	}
	
	public void setYearListDesc(ArrayList yearListDesc) {
		this.yearListDesc = yearListDesc;
	}
	
	public ArrayList getMon_list() {
		return mon_list;
	}
	public void setMon_list(ArrayList mon_list) {
		this.mon_list = mon_list;
	}
}
