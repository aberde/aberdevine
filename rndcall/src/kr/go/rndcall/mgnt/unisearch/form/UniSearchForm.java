/** 
 * {파일, 클래스에 대한 설명을 기술한다.}
 * @author bkhwang
 * @version {Revision}
 * @since 2009. 10. 20
 * Copyright (C) 2007 by SAMSUNG SDS co.,Ltd. All right reserved.
 */
package kr.go.rndcall.mgnt.unisearch.form;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.struts.upload.FormFile;

import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.common.BaseForm;
import kr.go.rndcall.mgnt.unisearch.vo.UniSearchSearchVO;
import kr.go.rndcall.mgnt.unisearch.vo.UniSearchVO;

/**
 * @author bkhwang
 *
 */
public class UniSearchForm extends BaseForm {
	
	protected UniSearchVO vo = new UniSearchVO();
	protected UniSearchSearchVO searchVO = new UniSearchSearchVO();
	//private ArrayList yearList = kr.go.rndcall.mgnt.cm.common.Util.getIa_yearList(2002, Integer.parseInt(searchVO.getCurrentYYYY()));

	private ArrayList voList3 = new ArrayList();
	
	public UniSearchSearchVO getSearchVO() {
		return searchVO;
	}
	public void setSearchVO(UniSearchSearchVO searchVO) {
		this.searchVO = searchVO;
	}
	public UniSearchVO getVo() {
		return vo;
	}
	public void setVo(UniSearchVO vo) {
		this.vo = vo;
	}
	public ArrayList getVoList3() {
		return voList3;
	}
	public void setVoList3(ArrayList voList3) {
		this.voList3 = voList3;
	}
	public ArrayList getYearList() {
		return yearList;
	}
	public void setYearList(ArrayList yearList) {
		this.yearList = yearList;
	}
	
}
