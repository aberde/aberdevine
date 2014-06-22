package kr.go.rndcall.mgnt.discussion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.rndcall.mgnt.common.FileUpload;
import kr.go.rndcall.mgnt.common.FileVO;
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.discussion.DiscussionBiz;
import kr.go.rndcall.mgnt.discussion.DiscussionForm;
import kr.go.rndcall.mgnt.discussion.DiscussionResultVO;
import kr.go.rndcall.mgnt.discussion.DiscussionSearchVO;
import kr.go.rndcall.mgnt.discussion.DiscussionVO;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class DiscussionAction extends DispatchAction {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 토론 상세화면으로 이동한다.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward retrieveDiscussDetail(ActionMapping mapping
				                      , ActionForm form
				                      , HttpServletRequest request
				                      , HttpServletResponse response) throws Exception {
		String target = "retrieveDiscussDetail";
		ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
		}
				
		DiscussionForm fm = (DiscussionForm)form;
		DiscussionVO vo = fm.getVo();
		DiscussionSearchVO searchVO = fm.getSearchVO();
		
		// 존재하지 않는 discuss_id 이거나 기타 권한이 없으면 에러페이지로 넘겨버린다.
		// 주소창에 직접 주소를 입력한 경우를 막기위한 부분임.
		if(Util.isValidSession(request)) {
			String role_cd = Util.getLoginUserVO(request).getRoleCD();
			System.out.println("###### now Role Cd : " + role_cd);
			if(role_cd == null || (role_cd.equals("0000A"))) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
				saveErrors(request, errors);
				return mapping.findForward("errorPage");
			}
		} else {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
			saveErrors(request, errors);
			return mapping.findForward("errorPage");
		}
		
		DiscussionResultVO resultVO = new DiscussionResultVO();
		String pagerOffset = (String)request.getAttribute("pagerOffset");

		if(pagerOffset != null && !pagerOffset.equals("")) {
			fm.setPagerOffset(pagerOffset);			
		} else {				
			fm.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		}
		searchVO.setPagerOffset(fm.getPagerOffset());
		searchVO.setMaxPageItems(fm.getMaxPageItems());
		searchVO.setOrderByTarget(fm.getOrderByTarget());
		searchVO.setOrderByMethod(fm.getOrderByMethod());
		searchVO.setDiscuss_id("1");
		searchVO.setDis_cont_id("1");
		request.setAttribute("searchVO.menu_sn", "12");
		try {
			DiscussionBiz biz = new DiscussionBiz();
			resultVO = biz.retrieveDiscussDetail(searchVO);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}
				
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
		}		
		saveErrors(request, errors);
		
		fm.setVo(resultVO.getVo()); // 이슈제목등
		fm.setVoList(resultVO.getVoList()); // 토론본문
		fm.setVoList1(resultVO.getVoList1()); // 댓글들
		fm.setTotRowCount(resultVO.getTotRowCount());
		// 베스트댓글
		String now_discuss_op_id = (String)request.getAttribute("now_discuss_op_id");
		System.out.println("$$$$$$$$$$$$$ request now_discuss_op_id :" + now_discuss_op_id);
		if(now_discuss_op_id != null && now_discuss_op_id.equals("del")) {
			System.out.println("######### del");
			request.setAttribute("now_discuss_op_id", "");
		} else if(now_discuss_op_id != null && !now_discuss_op_id.equals("")) {			
			request.setAttribute("now_discuss_op_id", now_discuss_op_id);
		} 
		
		if(searchVO.getP_discuss_op_id() != null && !searchVO.getP_discuss_op_id().equals("")) {
			request.setAttribute("now_discuss_op_id", searchVO.getP_discuss_op_id());
		} else {
			request.setAttribute("now_discuss_op_id", searchVO.getDiscuss_op_id());
		}
		
		saveToken(request);
		logger.debug("target: " + target);
		return mapping.findForward(target);
	}

	/**
	 * 답글 수정한다.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward updateCommentContents(ActionMapping mapping
				                      , ActionForm form
				                      , HttpServletRequest request
				                      , HttpServletResponse response) throws Exception {
		ActionErrors errors = null;
		
		String target = "";
		DiscussionForm fm = (DiscussionForm)form;
		FileVO[] fileInfo = null;
		DiscussionSearchVO searchVO = fm.getSearchVO();
		DiscussionVO vo = fm.getVo();		
		searchVO.setPagerOffset(fm.getPagerOffset());
		searchVO.setMaxPageItems(fm.getMaxPageItems());
		searchVO.setOrderByTarget(fm.getOrderByTarget());
		searchVO.setOrderByMethod(fm.getOrderByMethod());
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
		}
		
		
		DiscussionResultVO resultVO = null;		
		searchVO.setUsername(Util.getLoginUserVO(request).getLogin_id());
		
		vo.setDel_files(request.getParameterValues("file_del"));
		
		//파일을 업로드 한다.
		FileUpload upload = new FileUpload(mapping, form, request);		
        upload.setFilePath("rndcall/upload/discussion/");       
        upload.setUsername(Util.getLoginUserVO(request).getLogin_id());
        try {
            upload.service();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        fileInfo = upload.getFileInfo();
        
		try {			
			
			DiscussionBiz biz = new DiscussionBiz();
			biz.updateCommentContents(searchVO, vo, fileInfo);
			
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
		}	
		
		saveErrors(request, errors);
		request.setAttribute("submenu1", request.getParameter("submenu1"));
		request.setAttribute("submenu2", request.getParameter("submenu2"));
		request.setAttribute("submenu3", request.getParameter("submenu3"));
		request.setAttribute("now_discuss_op_id", searchVO.getP_discuss_op_id());
		logger.debug("target: " + target);		
		return retrieveDiscussDetail(mapping, form, request, response);
	}
	
	/**
	 * 댓글 수정폼을 제공한다.(AJAX를 위해)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward updateCommentContentsForm(ActionMapping mapping
				                      , ActionForm form
				                      , HttpServletRequest request
				                      , HttpServletResponse response) throws Exception {
		ActionErrors errors = null;
		
		String target = "updateCommentContentsForm";
		DiscussionForm fm = (DiscussionForm)form;
		DiscussionSearchVO searchVO = fm.getSearchVO();
		DiscussionVO vo = fm.getVo();		
		
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
		}
		
		
		DiscussionResultVO resultVO = null;		
		searchVO.setUsername(Util.getLoginUserVO(request).getLogin_id());        
    
		try {			
			
			DiscussionBiz biz = new DiscussionBiz();
			resultVO = biz.updateCommentContentsForm(searchVO);
			
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
		}		
		fm.setVoList(resultVO.getVoList());
		fm.setVo(resultVO.getVo());		
		saveErrors(request, errors);
		logger.debug("target: " + target);
		System.out.println("target+++++++++++++++++: " + target);
		return mapping.findForward(target);
	}
	
	/**
	 * 의견 수정한다.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward updateDiscussContents(ActionMapping mapping
				                      , ActionForm form
				                      , HttpServletRequest request
				                      , HttpServletResponse response) throws Exception {
		ActionErrors errors = null;
		
		String target = "";
		DiscussionForm fm = (DiscussionForm)form;
		FileVO[] fileInfo = null;
		DiscussionSearchVO searchVO = fm.getSearchVO();
		DiscussionVO vo = fm.getVo();		
		searchVO.setPagerOffset(fm.getPagerOffset());
		searchVO.setMaxPageItems(fm.getMaxPageItems());
		searchVO.setOrderByTarget(fm.getOrderByTarget());
		searchVO.setOrderByMethod(fm.getOrderByMethod());
		
		//request.setAttribute("pagerOffset", fm.getPagerOffset());	
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
		}
		
		
		DiscussionResultVO resultVO = null;		
		searchVO.setUsername(Util.getLoginUserVO(request).getLogin_id());
		
		vo.setDel_files(request.getParameterValues("file_del"));
		
		//파일을 업로드 한다.
		FileUpload upload = new FileUpload(mapping, form, request);		
        upload.setFilePath("rndcall/upload/discussion/");       
        upload.setUsername(Util.getLoginUserVO(request).getLogin_id());
        try {
            upload.service();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        fileInfo = upload.getFileInfo();
        
		try {			
			
			DiscussionBiz biz = new DiscussionBiz();
			biz.updateDiscussContents(searchVO, vo, fileInfo);
			
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
		}	
				
		saveErrors(request, errors);
		
		request.setAttribute("now_discuss_op_id", searchVO.getDiscuss_op_id());
		
		return retrieveDiscussDetail(mapping, form, request, response);
	}
	
	/**
	 * 의견 수정폼을 제공한다.(AJAX를 위해)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward updateDiscussContentsForm(ActionMapping mapping
				                      , ActionForm form
				                      , HttpServletRequest request
				                      , HttpServletResponse response) throws Exception {
		ActionErrors errors = null;
		System.out.println("###### : updateDiscussContentsForm");
		String target = "updateDiscussContentsForm";
		DiscussionForm fm = (DiscussionForm)form;
		DiscussionSearchVO searchVO = fm.getSearchVO();
		DiscussionVO vo = fm.getVo();		
		
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
		}
		
		
		DiscussionResultVO resultVO = null;		
		searchVO.setUsername(Util.getLoginUserVO(request).getLogin_id());        
    
		try {			
			
			DiscussionBiz biz = new DiscussionBiz();
			resultVO = biz.updateDiscussContentsForm(searchVO);
			
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
		}		
		fm.setVoList(resultVO.getVoList());
		fm.setVo(resultVO.getVo());		
		saveErrors(request, errors);
		logger.debug("target: " + target);
		System.out.println("target+++++++++++++++++: " + target);
		return mapping.findForward(target);
	}
	
	/**
	 * 토론본문을 입력한다.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward createDiscussContents(ActionMapping mapping
			                                 , ActionForm form
				                             , HttpServletRequest request
				                             , HttpServletResponse response) throws Exception {
		String target = "retrieveDiscussDetailAfterSave";
		ActionErrors errors = null;
		FileVO[] fileInfo = null;
		if (!isTokenValid(request)) { 
            logger.debug("target: " + target);
            return mapping.findForward(target);
        }
        resetToken(request);
        
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
		}
		DiscussionForm fm = (DiscussionForm)form;
		DiscussionSearchVO searchVO = fm.getSearchVO();
		DiscussionVO vo = fm.getVo();
		DiscussionResultVO resultVO = null;
		
		searchVO.setUsername(Util.getLoginUserVO(request).getLogin_id());
		
		// 파일을 업로드 한다.
		FileUpload upload = new FileUpload(mapping, form, request);		
        upload.setFilePath("rndcall/upload/discussion/");
        upload.setUsername(Util.getLoginUserVO(request).getLogin_id());
        try {
            upload.service();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileInfo = upload.getFileInfo();
        
        // 토론본문을 입력한다.
		try {			
			DiscussionBiz biz = new DiscussionBiz();
			biz.createDiscussContents(vo, searchVO, fileInfo);			
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
		}		
		saveErrors(request, errors);
		fm.setErrCd("saved");
		
		logger.debug("target: " + target);
		return mapping.findForward(target);
	}
	
	/**
	 * 토론 댓글을 등록한다.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward createDiscussOpin(ActionMapping mapping
				                      , ActionForm form
				                      , HttpServletRequest request
				                      , HttpServletResponse response) throws Exception {
		System.out.println("************ createDiscussOpin");
		ActionErrors errors = null;
		FileVO[] fileInfo = null;
		String target = "retrieveDiscussDetailAfterSave";
		DiscussionForm fm = (DiscussionForm)form;
		DiscussionSearchVO searchVO = fm.getSearchVO();
		DiscussionVO vo = fm.getVo();
		
		
		
		if (!isTokenValid(request)) {
            //errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.reflesh.notPermit"));
            //saveErrors(request, errors);			
            logger.debug("target: " + target);
            return mapping.findForward(target);
        }
        resetToken(request);
        
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
		}
		
		
		DiscussionResultVO resultVO = null;
		
		// 토론 등록자이름이 없어지는 경우가 발생함. 따라서 세션이 없거나 이름이 없으면 오류로 튕겨냄.
		if(Util.isValidSession(request)) {
			String userName = Util.getLoginUserVO(request).getLogin_id();			
			if(userName == null || userName.equals("") || userName.equals("null") || userName.equals("NULL")) {
				String Id = Util.getLoginUserVO(request).getLogin_id();				
				if(Id == null || Id.equals("") || Id.equals("null") || Id.equals("NULL")) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
					saveErrors(request, errors);
					return mapping.findForward("errorPage");
				} else {
					searchVO.setUsername(Util.getLoginUserVO(request).getLogin_id());
				}
			} else {
				searchVO.setUsername(Util.getLoginUserVO(request).getLogin_id());
			}			
		} else {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
			saveErrors(request, errors);
			return mapping.findForward("errorPage");
		}

		System.out.println("######## :" + Util.getLoginUserVO(request).getLogin_id());
		
		// 파일을 업로드 한다.
		FileUpload upload = new FileUpload(mapping, form, request);		
        upload.setFilePath("rndcall/upload/discussion/");
        upload.setUsername(Util.getLoginUserVO(request).getLogin_id());
        try {
            upload.service();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileInfo = upload.getFileInfo();
        System.out.println("fileInfo : " + fileInfo.length);
        // 토론을 생성한다.
        String discuss_op_id = "";
		try {			
			discuss_op_id = new DiscussionBiz().createDiscussOpin(vo, searchVO, fileInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
		}		
		saveErrors(request, errors);
		
		request.setAttribute("now_discuss_op_id", discuss_op_id);
		logger.debug("target: " + target);
		return mapping.findForward(target);
	}

	/**
	 * 토론 댓글의 댓글을 등록한다.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward createDiscussOpinOpin(ActionMapping mapping
				                      , ActionForm form
				                      , HttpServletRequest request
				                      , HttpServletResponse response) throws Exception {
		ActionErrors errors = null;
		FileVO[] fileInfo = null; 
		String target = "retrieveDiscussDetailAfterSave";
		DiscussionForm fm = (DiscussionForm)form;
		DiscussionSearchVO searchVO = fm.getSearchVO();
		DiscussionVO vo = fm.getVo();
		//fm.setPagerOffset(pagerOffset);
		searchVO.setPagerOffset(fm.getPagerOffset());
		searchVO.setMaxPageItems(fm.getMaxPageItems());
		searchVO.setOrderByTarget(fm.getOrderByTarget());
		searchVO.setOrderByMethod(fm.getOrderByMethod());
		System.out.println("@@@@@@@@@@@ : pageroffset set value =>"+ fm.getPagerOffset());
		request.setAttribute("pagerOffset", fm.getPagerOffset());
		if (!isTokenValid(request)) {
            logger.debug("target: " + target);
            return mapping.findForward(target);
        }
        resetToken(request);
        
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
		}
		
		
		DiscussionResultVO resultVO = null;
		
		//searchVO.setUsername(Util.getLoginUserVO(request).getLogin_id());
		// 토론 등록자이름이 없어지는 경우가 발생함. 따라서 세션이 없거나 이름이 없으면 오류로 튕겨냄.
		if(Util.isValidSession(request)) {
			String userName = Util.getLoginUserVO(request).getLogin_id();			
			if(userName == null || userName.equals("") || userName.equals("null") || userName.equals("NULL")) {
				String Id = Util.getLoginUserVO(request).getLogin_id();				
				if(Id == null || Id.equals("") || Id.equals("null") || Id.equals("NULL")) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
					saveErrors(request, errors);
					return mapping.findForward("errorPage");
				} else {
					searchVO.setUsername(Util.getLoginUserVO(request).getLogin_id());
				}
			} else {
				searchVO.setUsername(Util.getLoginUserVO(request).getLogin_id());
			}			
		} else {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
			saveErrors(request, errors);
			return mapping.findForward("errorPage");
		}
		
		// 파일을 업로드 한다.
		FileUpload upload = new FileUpload(mapping, form, request);		
        upload.setFilePath("rndcall/upload/discussion/");
        upload.setUsername(Util.getLoginUserVO(request).getLogin_id());
        try {
            upload.service();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileInfo = upload.getFileInfo();
                        
        // 토론을 생성한다.
		try {			
			new DiscussionBiz().createDiscussOpinOpin(vo, searchVO, fileInfo);
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
		}		
		saveErrors(request, errors);
		
		request.setAttribute("now_discuss_op_id", vo.getDiscuss_op_id());
		logger.debug("target: " + target);
		return mapping.findForward(target);
	}

	/**
	 * 토론 댓글을 삭제한다.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward deleteDiscussReply(ActionMapping mapping
				                      , ActionForm form
				                      , HttpServletRequest request
				                      , HttpServletResponse response) throws Exception {
		ActionErrors errors = null;
		
		String target = "retrieveDiscussDetailAfterSave";
		DiscussionForm fm = (DiscussionForm)form;
		DiscussionSearchVO searchVO = fm.getSearchVO();
		DiscussionVO vo = fm.getVo();
		
		searchVO.setPagerOffset(fm.getPagerOffset());
		searchVO.setMaxPageItems(fm.getMaxPageItems());
		searchVO.setOrderByTarget(fm.getOrderByTarget());
		searchVO.setOrderByMethod(fm.getOrderByMethod());
		request.setAttribute("pagerOffset", fm.getPagerOffset());
		
		if (!isTokenValid(request)) {            
            logger.debug("target: " + target);
            return mapping.findForward(target);
        }
        resetToken(request);
        
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
		}
		
		
		DiscussionResultVO resultVO = null;		
		searchVO.setUsername(Util.getLoginUserVO(request).getLogin_id());
        
        // 토론댓글을 삭제한다.
		try {			
			new DiscussionBiz().deleteDiscussOpin(searchVO);
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
		}		
		saveErrors(request, errors);
		if(searchVO.getP_discuss_op_id() != null && !searchVO.getP_discuss_op_id().equals("")) {
			request.setAttribute("now_discuss_op_id", searchVO.getP_discuss_op_id());
		} else {
			request.setAttribute("now_discuss_op_id", "del");
		}
		logger.debug("target: " + target);
		return mapping.findForward(target);
	}
	
	/**
	 * 토론을 삭제한다.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward deleteDiscuss(ActionMapping mapping
				                      , ActionForm form
				                      , HttpServletRequest request
				                      , HttpServletResponse response) throws Exception {
		ActionErrors errors = null;
		
		String target = "retrieveDiscussList";
		DiscussionForm fm = (DiscussionForm)form;
		DiscussionSearchVO searchVO = fm.getSearchVO();
		DiscussionVO vo = fm.getVo();
		
		if (!isTokenValid(request)) {            
            logger.debug("target: " + target);
            return mapping.findForward(target);
        }
        resetToken(request);
        
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
		}
		
		
		DiscussionResultVO resultVO = null;		
		searchVO.setUsername(Util.getLoginUserVO(request).getLogin_id());
        
        // 토론을 삭제한다.
		try {			
			DiscussionBiz biz = new DiscussionBiz();
			biz.deleteDiscuss(searchVO);
			
			resultVO = biz.retrieveDiscussList(vo, searchVO);
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
		}		
		saveErrors(request, errors);
		fm.setVoList(resultVO.getVoList());
		logger.debug("target: " + target);
		return mapping.findForward(target);
	}
	
	/**
	 * 토론을 수정한다.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward updateDiscuss(ActionMapping mapping
				                      , ActionForm form
				                      , HttpServletRequest request
				                      , HttpServletResponse response) throws Exception {
		ActionErrors errors = null;
		FileVO[] fileInfo = null;
		String target = "retrieveDiscussDetailAfterSave";
		DiscussionForm fm = (DiscussionForm)form;
		DiscussionSearchVO searchVO = fm.getSearchVO();
		DiscussionVO vo = fm.getVo();
		
		if (!isTokenValid(request)) {
            //errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.reflesh.notPermit"));
            //saveErrors(request, errors);			
            logger.debug("target: " + target);
            return mapping.findForward(target);
        }
        resetToken(request);
        
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
		}
		
		
		DiscussionResultVO resultVO = null;
		
		searchVO.setUsername(Util.getLoginUserVO(request).getLogin_id());
		
		// 파일을 업로드 한다.
		FileUpload upload = new FileUpload(mapping, form, request);		
        upload.setFilePath("rndcall/upload/discussion/");
        upload.setUsername(Util.getLoginUserVO(request).getLogin_id());
        try {
            upload.service();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileInfo = upload.getFileInfo();
        
        // 토론을 생성한다.
		try {			
			new DiscussionBiz().updateDiscuss(searchVO, vo, fileInfo);
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
		}		
		saveErrors(request, errors);
		logger.debug("target: " + target);
		return mapping.findForward(target);
	}
	
	/**
	 * 의견 수정폼을 제공한다.(AJAX를 위해)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward updateIssueDetailsForm(ActionMapping mapping
				                      , ActionForm form
				                      , HttpServletRequest request
				                      , HttpServletResponse response) throws Exception {
		ActionErrors errors = null;
		
		String target = "updateIssueDetailsForm";
		DiscussionForm fm = (DiscussionForm)form;
		DiscussionSearchVO searchVO = fm.getSearchVO();
		DiscussionVO vo = fm.getVo();		
		
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
		}
		
		
		DiscussionResultVO resultVO = null;		
		searchVO.setUsername(Util.getLoginUserVO(request).getLogin_id());        
    
		try {			
			
			DiscussionBiz biz = new DiscussionBiz();
			resultVO = biz.updateIssueDetailsForm(searchVO);
			
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
		}		
		fm.setVoList(resultVO.getVoList());
		fm.setVo(resultVO.getVo());		
		saveErrors(request, errors);
		logger.debug("target: " + target);
		System.out.println("target+++++++++++++++++: " + target);
		return mapping.findForward(target);
	}
	
	/**
	 * 의견 수정한다.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward updateIssueDetailsContents(ActionMapping mapping
				                      , ActionForm form
				                      , HttpServletRequest request
				                      , HttpServletResponse response) throws Exception {
		ActionErrors errors = null;
		
		String target = "";
		DiscussionForm fm = (DiscussionForm)form;
		FileVO[] fileInfo = null;
		DiscussionSearchVO searchVO = fm.getSearchVO();
		DiscussionVO vo = fm.getVo();		
		
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
		}
		
		
		DiscussionResultVO resultVO = null;		
		searchVO.setUsername(Util.getLoginUserVO(request).getLogin_id());
		
		vo.setDel_files(request.getParameterValues("file_del"));
		
		//파일을 업로드 한다.
		FileUpload upload = new FileUpload(mapping, form, request);		
        upload.setFilePath("rndcall/upload/discussion/");       
        upload.setUsername(Util.getLoginUserVO(request).getLogin_id());
        try {
            upload.service();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        fileInfo = upload.getFileInfo();
        
		try {			
			
			DiscussionBiz biz = new DiscussionBiz();
			biz.updateIssueDetailsContents(searchVO, vo, fileInfo);
			
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
		}	
				
		saveErrors(request, errors);
		
		logger.debug("target: " + target);		
		return retrieveDiscussDetail(mapping, form, request, response);
	}
	
	/**
	 * 토론 수정폼으로 이동한다.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward retrieveDiscussUpdateForm(ActionMapping mapping
				                      , ActionForm form
				                      , HttpServletRequest request
				                      , HttpServletResponse response) throws Exception {
		String target = "retrieveDiscussUpdateForm";
		ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
		}
		DiscussionForm fm = (DiscussionForm)form;
		DiscussionVO vo = fm.getVo();
		DiscussionSearchVO searchVO = fm.getSearchVO();
		DiscussionResultVO resultVO = null;
		try {
			DiscussionBiz biz = new DiscussionBiz();
			resultVO = biz.retrieveDiscussUpdateForm(searchVO);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}
				
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.put.success"));
		}		
		saveErrors(request, errors);
		
		fm.setVo(resultVO.getVo());
		fm.setVoList(resultVO.getVoList());

		saveToken(request);
		logger.debug("target: " + target);
		return mapping.findForward(target);
	}
	
	/**
	 * 토론 첨부파일을 삭제한다.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void deleteDiscussAttach(ActionMapping mapping
				                      , ActionForm form
				                      , HttpServletRequest request
				                      , HttpServletResponse response) throws Exception {
		ActionErrors errors = null;
				
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
		}
		
		DiscussionForm fm = (DiscussionForm)form;
		DiscussionSearchVO searchVO = fm.getSearchVO();
				
		searchVO.setUsername(Util.getLoginUserVO(request).getLogin_id());
        
		try {
			DiscussionBiz biz = new DiscussionBiz();
			biz.deleteDiscussAttach(searchVO);			
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}			
    
		response.setContentType("text/xml; charset=utf-8");
		response.getWriter().write(searchVO.getSpan_id());
	}

	/**
	 * 찬성반대를 입력한다.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void createDisApop(ActionMapping mapping
				                      , ActionForm form
				                      , HttpServletRequest request
				                      , HttpServletResponse response) throws Exception {
		ActionErrors errors = null;
		StringBuffer returnXML = new StringBuffer();
		returnXML.append("<res><data>");

		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
		}
		
		DiscussionForm fm = (DiscussionForm)form;
		DiscussionSearchVO searchVO = fm.getSearchVO();
		DiscussionVO vo = fm.getVo();
		DiscussionResultVO resultVO = null;
        
		try {
			String username = Util.getLoginUserVO(request).getLogin_id();
			
			
			
			searchVO.setUsername(username);
			if(username.equals("guest")) throw new NullPointerException();
			
			DiscussionBiz biz = new DiscussionBiz();
			// 본인글인지 판단한다. 본인글이면 찬성 반대하지 못한다.
			String isSelf = biz.isSelfDiscussOpin(searchVO);
			
			// 사용자ID에 해당하는 토론의견 찬성반대 정보가 있는지 조회한다.
			// ID가 이미 있다면 다시 찬성 또는 반대하지 못하도록 막는다.
			String isApOp = biz.isApOp(searchVO);
			if(isApOp.equals("Y")) {
				returnXML.append("<returnApOp>").append("0").append("</returnApOp>");
				returnXML.append("<returnCode>").append(searchVO.getAp_op()).append("</returnCode>");
				returnXML.append("<returnValue>").append("existData").append("</returnValue>");
				returnXML.append("</data></res>");					
			} else if(isSelf.equals("Y")) {
				returnXML.append("<returnApOp>").append("0").append("</returnApOp>");
				returnXML.append("<returnCode>").append(searchVO.getAp_op()).append("</returnCode>");
				returnXML.append("<returnValue>").append("self").append("</returnValue>");
				returnXML.append("</data></res>");	
			} else {
				// 처음이라면 찬성 또는 반대한다.
				biz.createDisApop(searchVO);
				returnXML.append("<returnApOp>").append(searchVO.getAp_op()).append("</returnApOp>");
				returnXML.append("<returnCode>").append(searchVO.getDiscuss_op_id()).append("</returnCode>");
				returnXML.append("<returnValue>").append(searchVO.getSpan_id()).append("</returnValue>");
				returnXML.append("</data></res>");
				System.out.println(returnXML.toString());
			}
			
		} catch(NullPointerException np) {
			returnXML.append("<returnApOp>").append("0").append("</returnApOp>");
			returnXML.append("<returnCode>").append("0").append("</returnCode>");
			returnXML.append("<returnValue>").append("noLogin").append("</returnValue>");
			returnXML.append("</data></res>");
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}
    
		response.setContentType("text/xml; charset=utf-8");
		response.getWriter().write(returnXML.toString());
	}
	
}
