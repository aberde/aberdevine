package kr.go.rndcall.mgnt.member.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.rndcall.mgnt.common.Util;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MemberAdminAction extends DispatchAction {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * getUserList 회원목록
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward getUserList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String target = "getUserList";
		ActionErrors errors = null;
		
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		MemberAdminForm fm = (MemberAdminForm) form;
		MemberAdminSearchVO searchVO = fm.getSearchVO();
		MemberAdminResultVO resultVO = new MemberAdminResultVO();
		
		fm.setPagerOffset(Util.replaceNull(
				request.getParameter("pager.offset"), "0"));
		searchVO.setPagerOffset(fm.getPagerOffset());
		searchVO.setMaxPageItems("10");

		try {		       
			MemberAdminBiz biz = new MemberAdminBiz();
			resultVO = biz.getUserList(searchVO);
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.database.error", e.getMessage()));
		}

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		fm.setVoList(resultVO.getVoList());
		fm.setOrgCdList(resultVO.getOrgCdList());
		fm.setTotRowCount(resultVO.getTotRowCount());
		fm.setPagerOffset(searchVO.getPagerOffset());
		fm.setMaxPageItems(searchVO.getMaxPageItems());

		saveErrors(request, errors);

		logger.debug("target: " + target);
		return mapping.findForward(target);
	}

	/**
	 * getUserInfo 회원정보
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward getUserInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String target = "getUserInfo";
		ActionErrors errors = null;
		
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		MemberAdminForm fm = (MemberAdminForm) form;
		MemberAdminSearchVO searchVO = fm.getSearchVO();
		MemberAdminResultVO resultVO = new MemberAdminResultVO();
		MemberAdminVO vo = fm.getVo();
		
		try {		       
			MemberAdminBiz biz = new MemberAdminBiz();
			resultVO = biz.getUserInfo(searchVO, vo);
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.database.error", e.getMessage()));
		}

		fm.setOrgCdList(resultVO.getOrgCdList());
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
