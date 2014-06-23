package kr.go.rndcall.mgnt.mypage.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import kr.go.rndcall.mgnt.common.BaseForm;
import kr.go.rndcall.mgnt.mypage.vo.SatiVO;
import kr.go.rndcall.mgnt.mypage.vo.MypageSearchVO;
import kr.go.rndcall.mgnt.mypage.vo.MypageResultVO;
import kr.go.rndcall.mgnt.mypage.vo.MypageAttachVO;
import kr.go.rndcall.mgnt.mypage.vo.MypageVO;

public class MypageForm extends BaseForm{
	
	private MypageVO vo = new MypageVO();
	private SatiVO satiVO = new SatiVO();
	private MypageSearchVO searchVO = new MypageSearchVO();
	private MypageResultVO resultVO = new MypageResultVO();
	private MypageAttachVO attachVO = new MypageAttachVO();

	private List fileList = new ArrayList();

	public MypageAttachVO getAttachVO() {
		return attachVO;
	}

	public void setAttachVO(MypageAttachVO attachVO) {
		this.attachVO = attachVO;
	}

	public List getFileList() {
		return fileList;
	}

	public void setFileList(List fileList) {
		this.fileList = fileList;
	}

	public MypageResultVO getResultVO() {
		return resultVO;
	}

	public void setResultVO(MypageResultVO resultVO) {
		this.resultVO = resultVO;
	}

	public MypageSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(MypageSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public MypageVO getVo() {
		return vo;
	}

	public void setVo(MypageVO vo) {
		this.vo = vo;
	}

	public SatiVO getSatiVO() {
		return satiVO;
	}

	public void setSatiVO(SatiVO satiVO) {
		this.satiVO = satiVO;
	}
	

}
