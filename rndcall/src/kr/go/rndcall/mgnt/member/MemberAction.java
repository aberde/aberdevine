package kr.go.rndcall.mgnt.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.rndcall.mgnt.login.LoginResultVO;
import kr.go.rndcall.mgnt.login.LoginVO;
import kr.go.rndcall.mgnt.member.admin.MemberAdminDAO;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MemberAction extends DispatchAction {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * getUserInsertForm 신규회원가입폼
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward getUserInsertForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String target = "getUserInsertForm";
		ActionErrors errors = null;
		
		MemberForm fm = (MemberForm) form;

		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
		fm.setErrCd("");
		
		MemberAdminDAO dao = new MemberAdminDAO();
		fm.setOrgCdList(dao.getOrgCD(null));
		
		fm.setVo(new MemberVO());

		logger.debug("target: " + target);
		return mapping.findForward(target);
	}
	
	/**
	 * getIdCheckForm 아이디 중복확인체크폼
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward getIdCheckForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String target = "getIdCheckForm";
		ActionErrors errors = null;
		
		MemberForm fm = (MemberForm) form;
		MemberSearchVO searchVO = fm.getSearchVO();
		MemberResultVO resultVO = new MemberResultVO();
		String cnt = "";
		
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		
		try {
		       
			MemberBiz MemberBiz = new MemberBiz();
			cnt = MemberBiz.getIdCheck(searchVO);
			
			if(Integer.parseInt(cnt) > 0){
				searchVO.setCheckIdVal("N");
			}else{
				searchVO.setCheckIdVal("Y");
			}
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.database.error", e.getMessage()));
		}
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		
		saveErrors(request, errors);

		logger.debug("target: " + target);
		return mapping.findForward(target);
	}
	
	/**
	 * getInsert 회원가입
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward getInsert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String target = "getUserInsertForm";
		ActionErrors errors = null;
		
		MemberForm fm = (MemberForm) form;
		MemberSearchVO searchVO = fm.getSearchVO();
		MemberResultVO resultVO = new MemberResultVO();
		MemberVO vo = fm.getVo();
		String ins = "false";
		int oldDocCnt = 0;
		
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		
		try {		       
			MemberBiz MemberBiz = new MemberBiz();
			ins = MemberBiz.getInsert(vo);
			oldDocCnt = MemberBiz.getOldDocCnt(vo);
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.database.error", e.getMessage()));
		}
		
		if(ins.equals("true")){
			/*
			 * 등록이 정상적일 경우에는 session 생성
			 */
			LoginResultVO LoginResultVO = new LoginResultVO();
			MemberDAO MemberDAO = new MemberDAO();
			LoginResultVO = MemberDAO.getMemberInfo(vo.getLogin_id());	
			
			LoginVO loginUserVO = null;
			for (int i = 0; i < LoginResultVO.getVoList().size(); i++) {
				loginUserVO = (LoginVO) LoginResultVO.getVoList().get(i);
			}
			System.out.println("### loginUserVO :"+loginUserVO );
				
			request.getSession().setAttribute("loginUserVO", loginUserVO);
			//response.sendRedirect("/switch.do?prefix=&page=/login.do?method=login");
//		    request.getRequestDispatcher("/switch.do?prefix=&page=/login.do?method=login").forward(request, response);
		}
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		
		fm.setErrCd(ins);
		fm.setErrMsg(String.valueOf(oldDocCnt));
		saveErrors(request, errors);

		logger.debug("target: " + target);
		return mapping.findForward(target);
	}
	
	/**
	 * getUserUpdateForm 회원정보수정폼
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward getUserUpdateForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String target = "getUserUpdateForm";
		ActionErrors errors = null;
		
		MemberForm fm = (MemberForm) form;
		MemberVO vo = fm.getVo();
		MemberResultVO resultVO = new MemberResultVO();

		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		
		try {		       
			MemberBiz MemberBiz = new MemberBiz();
			resultVO = MemberBiz.getUserInfo(vo);
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.database.error", e.getMessage()));
		}
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
		fm.setVo(resultVO.getVo());
		fm.setErrCd("");

		logger.debug("target: " + target);
		return mapping.findForward(target);
	}
	
	/**
	 * getInsert 회원가입
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward getUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String target = "getUserUpdateForm";
		ActionErrors errors = null;
		
		MemberForm fm = (MemberForm) form;
		MemberSearchVO searchVO = fm.getSearchVO();
		MemberResultVO resultVO = new MemberResultVO();
		MemberVO vo = fm.getVo();
		
		LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginUserVO");  // 세션에서 아이디 가져오기
        vo.setLogin_id(loginVO.getLogin_id());
		
		String ins = "false";
		int oldDocCnt = 0;
	
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		
		try {		       
			MemberBiz MemberBiz = new MemberBiz();
			ins = MemberBiz.getUpdate(vo);
			oldDocCnt = MemberBiz.getOldDocCnt(vo);
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.database.error", e.getMessage()));
		}
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		
		fm.setErrCd(ins);
		fm.setErrMsg(String.valueOf(oldDocCnt));
		saveErrors(request, errors);

		logger.debug("target: " + target);
		return mapping.findForward(target);
	}
	
	/**
	 * idFind 아이디 찾기
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward idFind(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String target = "idFind";
		ActionErrors errors = null;
		request.setCharacterEncoding("UTF-8");
		
		MemberForm fm = (MemberForm) form;
		MemberVO vo = fm.getVo();
		MemberResultVO resultVO = new MemberResultVO();
		
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		
		try {
		       
			MemberBiz MemberBiz = new MemberBiz();
			resultVO = MemberBiz.idFind(vo);
			
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
		fm.setVo(new MemberVO());

		saveErrors(request, errors);

		logger.debug("target: " + target);
		return mapping.findForward(target);
	}
	
	/**
	 * pwFind 비밀번호 찾기
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward pwFind(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String target = "pwFind";
		ActionErrors errors = null;
		request.setCharacterEncoding("UTF-8");
		
		MemberForm fm = (MemberForm) form;
		MemberVO vo = fm.getVo();
		MemberResultVO resultVO = new MemberResultVO();
		
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		
		try {
			MemberBiz MemberBiz = new MemberBiz();
			resultVO = MemberBiz.pwFind(vo);
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.database.error", e.getMessage()));
		}
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		fm.setVo(resultVO.getVo());
		
		saveErrors(request, errors);

		logger.debug("target: " + target);
		return mapping.findForward(target);
	}

	/**
	 * getOldDocList 기존문의내용 이관
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward getOldDocList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String target = "oldDocFind";
		ActionErrors errors = null;
		
		MemberForm fm = (MemberForm) form;
		MemberVO vo = fm.getVo();
		MemberResultVO resultVO = new MemberResultVO();

		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		
		try {		       
			MemberBiz MemberBiz = new MemberBiz();
			resultVO = MemberBiz.getOldDocList(vo);
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.database.error", e.getMessage()));
		}
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
		fm.setVoList(resultVO.getVoList());
		fm.setErrCd("");

		logger.debug("target: " + target);
		return mapping.findForward(target);
	}
	
	/**
	 * updateDocRegister 기존문의내용 이관저장
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward updateDocRegister(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String target = "oldDocFind";
		ActionErrors errors = null;
		
		MemberForm fm = (MemberForm) form;
		MemberVO vo = fm.getVo();
		MemberResultVO resultVO = new MemberResultVO();

		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		
		try {		       
			MemberBiz MemberBiz = new MemberBiz();
			resultVO = MemberBiz.getOldDocSave(vo);
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.database.error", e.getMessage()));
		}
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
		fm.setVoList(resultVO.getVoList());
		fm.setErrCd(resultVO.getErrCd());

		logger.debug("target: " + target);
		return mapping.findForward(target);
	}

	   
    /**
     * getUserUpdateForm 회원정보상세폼
     * @param mapping
     * @param form
     * @param request
     * @param response
     */
    public ActionForward getUserView(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String target = "getUserView";
        ActionErrors errors = null;
        
        MemberForm fm = (MemberForm) form;
        MemberVO vo = fm.getVo();
        MemberResultVO resultVO = new MemberResultVO();

        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
                    .getAttribute("org.apache.struts.action.ERROR");
        }
        
        try {
            LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginUserVO");
            vo.setLogin_id(loginVO.getLogin_id());
            MemberBiz MemberBiz = new MemberBiz();
            resultVO = MemberBiz.getUserInfo(vo);
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }
        
        if (errors.isEmpty()) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.getList.success"));
        }
        saveErrors(request, errors);
        fm.setVo(resultVO.getVo());
        fm.setErrCd("");

        logger.debug("target: " + target);
        return mapping.findForward(target);
    }
    
    /**
     * getDelete 회원탈퇴
     * @param mapping
     * @param form
     * @param request
     * @param response
     */
    public ActionForward getDelete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String target = "logout";
        ActionErrors errors = null;
        
        MemberForm fm = (MemberForm) form;
        MemberSearchVO searchVO = fm.getSearchVO();
        MemberResultVO resultVO = new MemberResultVO();
        MemberVO vo = fm.getVo();
        String ins = "false";
        int oldDocCnt = 0;
    
        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
                    .getAttribute("org.apache.struts.action.ERROR");
        }
        
        try {              
            MemberBiz MemberBiz = new MemberBiz();
            ins = MemberBiz.getDelete(vo);
            oldDocCnt = MemberBiz.getOldDocCnt(vo);
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }
        
        if (errors.isEmpty()) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.getList.success"));
        }
        
        fm.setErrCd(ins);
        fm.setErrMsg(String.valueOf(oldDocCnt));
        saveErrors(request, errors);

        logger.debug("target: " + target);
        return mapping.findForward(target);
    }
}
