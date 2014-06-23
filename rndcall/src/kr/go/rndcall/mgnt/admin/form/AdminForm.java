package kr.go.rndcall.mgnt.admin.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import kr.go.rndcall.mgnt.common.BaseForm;
import kr.go.rndcall.mgnt.admin.vo.AdminSearchVO;
import kr.go.rndcall.mgnt.admin.vo.AdminResultVO;
import kr.go.rndcall.mgnt.admin.vo.AdminAttachVO;
import kr.go.rndcall.mgnt.admin.vo.AdminVO;

public class AdminForm extends BaseForm{
	
	private AdminVO vo = new AdminVO();
	private AdminSearchVO searchVO = new AdminSearchVO();
	private AdminResultVO resultVO = new AdminResultVO();
	private AdminAttachVO attachVO = new AdminAttachVO();	
    private List fileList = new ArrayList();
    
	public List getFileList() {
		return fileList;
	}
	public void setFileList(List fileList) {
		this.fileList = fileList;
	}
	public AdminVO getVo() {
		return vo;
	}
	public void setVo(AdminVO vo) {
		this.vo = vo;
	}
	public AdminAttachVO getAttachVO() {
		return attachVO;
	}
	public void setAttachVO(AdminAttachVO attachVO) {
		this.attachVO = attachVO;
	}
	public AdminResultVO getResultVO() {
		return resultVO;
	}
	public void setResultVO(AdminResultVO resultVO) {
		this.resultVO = resultVO;
	}
	public AdminSearchVO getSearchVO() {
		return searchVO;
	}
	public void setSearchVO(AdminSearchVO searchVO) {
		this.searchVO = searchVO;
	}    
    
}
