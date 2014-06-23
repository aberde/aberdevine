package kr.go.rndcall.mgnt.offer.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import kr.go.rndcall.mgnt.common.BaseForm;
import kr.go.rndcall.mgnt.offer.vo.OfferSearchVO;
import kr.go.rndcall.mgnt.offer.vo.OfferVO;
import kr.go.rndcall.mgnt.offer.vo.SatiVO;

public class OfferForm extends BaseForm{
	
	private OfferVO vo = new OfferVO();	
	private SatiVO satiVO = new SatiVO();
	private OfferSearchVO searchVO = new OfferSearchVO();
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
	
	public OfferSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(OfferSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public OfferVO getVo() {
		return vo;
	}

	public void setVo(OfferVO vo) {
		this.vo = vo;
	}
}
