package kr.go.rndcall.mgnt.category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.rndcall.mgnt.category.CategoryBiz;
import kr.go.rndcall.mgnt.category.CategoryForm;
import kr.go.rndcall.mgnt.category.CategoryResultVO;
import kr.go.rndcall.mgnt.category.CategorySearchVO;
import kr.go.rndcall.mgnt.category.CategoryVO;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class CategoryAction extends DispatchAction {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * getCategoryList ī�װ? ��з� ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward getCategoryList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String target = "getCategoryList";
		ActionErrors errors = null;
		
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		CategoryForm fm = (CategoryForm) form;
		CategorySearchVO searchVO = fm.getSearchVO();
		CategoryResultVO resultVO = new CategoryResultVO();
		CategoryVO vo = fm.getVo();
		
		try {		       
			CategoryBiz biz = new CategoryBiz();
			resultVO = biz.getCategoryList(searchVO, vo);
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.database.error", e.getMessage()));
		}

		fm.setVoList(resultVO.getVoList());
		fm.setErrCd(resultVO.getErrCd());
		fm.setTotRowCount(resultVO.getTotRowCount());
		fm.setVo(new CategoryVO());

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}

		saveErrors(request, errors);

		logger.debug("target: " + target);
		return mapping.findForward(target);
	}

	/**
	 * getCategoryList ī�װ? ��з� ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward getCategoryListDtl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String target = "getCategoryListDtl";
		ActionErrors errors = null;
		
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		CategoryForm fm = (CategoryForm) form;
		CategorySearchVO searchVO = fm.getSearchVO();
		CategoryResultVO resultVO = new CategoryResultVO();
		CategoryVO vo = fm.getVo();
		
		try {		       
			CategoryBiz biz = new CategoryBiz();
			resultVO = biz.getCategoryListDtl(searchVO, vo);
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.database.error", e.getMessage()));
		}

		fm.setParent_nm(resultVO.getVo().getParent_nm());
		fm.setVoList(resultVO.getVoList());
		fm.setErrCd(resultVO.getErrCd());
		fm.setTotRowCount(resultVO.getTotRowCount());
		fm.setVo(new CategoryVO());

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}

		saveErrors(request, errors);

		logger.debug("target: " + target);
		return mapping.findForward(target);
	}

	/**
	 * getCategoryList ī�װ? ��з� ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward getCategoryInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String target = "getCategoryInfo";
		ActionErrors errors = null;
		
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		CategoryForm fm = (CategoryForm) form;
		CategorySearchVO searchVO = fm.getSearchVO();
		CategoryResultVO resultVO = new CategoryResultVO();
		CategoryVO vo = fm.getVo();
		
		try {		       
			CategoryBiz biz = new CategoryBiz();
			resultVO = biz.getCategoryInfo(searchVO, vo);
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.database.error", e.getMessage()));
		}

		fm.setVo(resultVO.getVo());
		fm.setErrCd(resultVO.getErrCd());

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}

		saveErrors(request, errors);

		logger.debug("target: " + target);
		return mapping.findForward(target);
	}

}
