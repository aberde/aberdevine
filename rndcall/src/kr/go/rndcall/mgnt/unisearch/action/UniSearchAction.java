/** 
 * {파일, 클래스에 대한 설명을 기술한다.}
 * @author bkhwang
 * @version {Revision}
 * @since 2009. 10. 20
 * Copyright (C) 2007 by SAMSUNG SDS co.,Ltd. All right reserved.
 */
package kr.go.rndcall.mgnt.unisearch.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.unisearch.biz.UniSearchBiz;
import kr.go.rndcall.mgnt.unisearch.form.UniSearchForm;
import kr.go.rndcall.mgnt.unisearch.vo.UniSearchResultVO;
import kr.go.rndcall.mgnt.unisearch.vo.UniSearchSearchVO;	
import kr.go.rndcall.mgnt.unisearch.vo.UniSearchVO;

/**
 * @author bkhwang
 *
 */
public class UniSearchAction extends DispatchAction {
	private Logger logger = Logger.getLogger(this.getClass().getName());


	/**
	 * uniSearch 도우미센터 통합검색
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward uniSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String target = "uniSearch";
		ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		UniSearchForm fm = (UniSearchForm) form;
		UniSearchSearchVO searchVO = fm.getSearchVO();
		UniSearchResultVO resultVO = new UniSearchResultVO();
		searchVO.setBf_word(searchVO.getWord());

		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
        
		searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		try {
			UniSearchBiz biz = new UniSearchBiz();
			resultVO = biz.uniSearch(searchVO);
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.database.error", e.getMessage()));
		}
    	if (errors.isEmpty()) {
    		if (resultVO.getTotRowCount().intValue() == 0) {
    			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
    			"errors.getList.DataNotFound"));
    		} else {
    			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
    			"errors.getList.success"));
    		}
    	}
		fm.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		fm.setSearchVO(searchVO);
		fm.setVoList(resultVO.getVoList());
		fm.setVoList1(resultVO.getVoList1());      
		fm.setVoList2(resultVO.getVoList2());      
		fm.setVoList3(resultVO.getVoList3());  
		fm.setTotRowCount(resultVO.getTotRowCount());
		fm.setTotRowCount1(resultVO.getTotRowCount1());
		fm.setTotRowCount2(resultVO.getTotRowCount2());
		fm.setTotRowCount3(resultVO.getTotRowCount3());
		fm.setErrCd(searchVO.getCrud());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);

		logger.debug("target: " + target);
		return mapping.findForward(target);
	}			

}
