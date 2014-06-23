/** 
 * {파일, 클래스에 대한 설명을 기술한다.}
 * @author bkhwang
 * @version {Revision}
 * @since 2009. 10. 20
 * Copyright (C) 2007 by SAMSUNG SDS co.,Ltd. All right reserved.
 */
package kr.go.rndcall.mgnt.unisearch.vo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import kr.go.rndcall.mgnt.common.BaseResultVO;
import kr.go.rndcall.mgnt.unisearch.vo.UniSearchVO;

/**
 * @author bkhwang
 *
 */
public class UniSearchResultVO extends BaseResultVO {

	ArrayList voList3 = new ArrayList();

	public ArrayList getVoList3() {
		return voList3;
	}

	public void setVoList3(ArrayList voList3) {
		this.voList3 = voList3;
	}
}
