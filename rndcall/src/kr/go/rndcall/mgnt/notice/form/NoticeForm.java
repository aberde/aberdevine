package kr.go.rndcall.mgnt.notice.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import kr.go.rndcall.mgnt.common.BaseForm;
import kr.go.rndcall.mgnt.notice.vo.NoticeSearchVO;
import kr.go.rndcall.mgnt.notice.vo.NoticeVO;

public class NoticeForm extends BaseForm{
	
	private NoticeVO vo = new NoticeVO();
	
	private NoticeSearchVO searchVO = new NoticeSearchVO();
	private List fileList = new ArrayList();
	
	public List getFileList() {
		return fileList;
	}
	public void setFileList(List fileList) {
		this.fileList = fileList;
	}
	    
	public NoticeSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(NoticeSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public NoticeVO getVo() {
		return vo;
	}

	public void setVo(NoticeVO vo) {
		this.vo = vo;
	}
}