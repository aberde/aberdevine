package kr.go.rndcall.mgnt.faq.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import kr.go.rndcall.mgnt.common.BaseForm;
import kr.go.rndcall.mgnt.faq.vo.FaqSearchVO;
import kr.go.rndcall.mgnt.faq.vo.FaqVO;
import kr.go.rndcall.mgnt.faq.vo.SatiVO;

public class FaqForm extends BaseForm{
	
	private FaqVO vo = new FaqVO();	
	private SatiVO satiVO = new SatiVO();
	private FaqSearchVO searchVO = new FaqSearchVO();
	private List fileList = new ArrayList();
	
	public SatiVO getSatiVO() {
		return satiVO;
	}
	public void setSatiVO(SatiVO satiVO) {
		this.satiVO = satiVO;
	}
	public List getFileList() {
		return fileList;
	}
	public void setFileList(List fileList) {
		this.fileList = fileList;
	}
	
	public FaqSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(FaqSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public FaqVO getVo() {
		return vo;
	}

	public void setVo(FaqVO vo) {
		this.vo = vo;
	}
}
