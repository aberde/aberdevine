package kr.go.rndcall.mgnt.data.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import kr.go.rndcall.mgnt.common.BaseForm;
import kr.go.rndcall.mgnt.data.vo.DataSearchVO;
import kr.go.rndcall.mgnt.data.vo.DataVO;

public class DataForm extends BaseForm{
	
	private DataVO vo = new DataVO();
	
	private DataSearchVO searchVO = new DataSearchVO();
	private List fileList = new ArrayList();
	
	public List getFileList() {
		return fileList;
	}
	public void setFileList(List fileList) {
		this.fileList = fileList;
	}
	
	public DataSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(DataSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public DataVO getVo() {
		return vo;
	}

	public void setVo(DataVO vo) {
		this.vo = vo;
	}
}
