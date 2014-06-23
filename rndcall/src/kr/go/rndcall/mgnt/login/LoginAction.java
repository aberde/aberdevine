package kr.go.rndcall.mgnt.login;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.rndcall.mgnt.common.Configuration;
import kr.go.rndcall.mgnt.common.Util;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LoginAction extends DispatchAction {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public LoginAction() {
        super();
    }

	 /**
     * 로그인하는 함수
     */        
    public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request
                                 , HttpServletResponse response) throws Exception {
    	
        String target = "login";
        
        ActionErrors actionerrors = new ActionErrors();
        if(isCancelled(request)){
            return mapping.findForward(target);
        }
                
        LoginForm fm = (LoginForm) form;
        LoginVO vo = fm.getVo();
        LoginResultVO resultVO = new LoginResultVO();
        
    	request.setAttribute("errCd", resultVO.getErrCd());
        String returnURL = request.getParameter("returnURL");
        
        LoginBiz LoginBiz = new LoginBiz();
        resultVO = LoginBiz.login(request, vo);
        
        fm.reset(mapping, request);
        //httpservletrequest.setAttribute("errCd", resultVO.getErrCd());
        if(resultVO.getErrCd() != null && resultVO.getErrCd().equals("N")) {
        	request.setAttribute("errCd", resultVO.getErrCd());
            target = "login";
        } 
        
        if (returnURL != null && !returnURL.equals("")) {
        	response.sendRedirect(returnURL);
        }
        
        saveErrors(request, actionerrors);
        
        return mapping.findForward(target);
    }
    
    /**
     * 로그인폼을 호출하는 함수
     */        
    public ActionForward loginForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                                 , HttpServletResponse response) throws Exception {
    	
        String target = "login";
        ActionErrors actionerrors = null;
        if(request.getAttribute("org.apache.struts.action.ERROR") == null){
            actionerrors = new ActionErrors();
        } else {
            actionerrors = (ActionErrors)request.getAttribute("org.apache.struts.action.ERROR");
        }

        saveErrors(request, actionerrors);
        logger.debug("target: " + target);
        return mapping.findForward(target);
    }
    
    /**
     * 로그아웃하는 함수
     */     
    public ActionForward delSession(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String s = "loginGuestDo";
        ActionErrors actionerrors = null;
        if(request.getAttribute("org.apache.struts.action.ERROR") == null)
        {
            actionerrors = new ActionErrors();
        } else
        {
            actionerrors = (ActionErrors)request.getAttribute("org.apache.struts.action.ERROR");
        }
        if(!actionerrors.isEmpty()) {
        	saveErrors(request, actionerrors);
        }
        
        s = "index";
        
        logger.debug("target: " + s);
        
    	return mapping.findForward(s);
    }

    /**
     * 로그아웃하는 함수
     */     
    public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String target = "loginGuestDo";
        ActionErrors actionerrors = null;

        if(request.getAttribute("org.apache.struts.action.ERROR") == null){
            actionerrors = new ActionErrors();
        } else{
            actionerrors = (ActionErrors)request.getAttribute("org.apache.struts.action.ERROR");
        }
        
    	if(!Util.isValidSession(request, response)){
	    	target = "delSessionDo";
	        actionerrors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("errors.login.required"));
	        saveErrors(request, actionerrors);
	        logger.debug("? session timeout");
	        logger.debug("target: " + target);
	        return mapping.findForward(target);
    	}
	        
        boolean flag = Configuration.getInstance().getBoolean("conf.sso.apply");
        
        if(flag){
        	
            LoginBiz LoginBiz = new LoginBiz();
            LoginBiz.logout(response);
            
            target = "index";
        }         
        
        if(actionerrors.isEmpty()){
            actionerrors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("errors.logout.sucess"));
            HttpSession httpsession = request.getSession();
            LoginVO loginvo = (LoginVO)httpsession.getAttribute("loginUserVO");
            
            if(loginvo != null){
                loginvo.setUsername("");
            }
            
            httpsession.invalidate();
            logger.debug("session invalidated!");
        }
                
        saveErrors(request, actionerrors);
        logger.debug("target: " + target);
        
        return mapping.findForward(target);
    }

    /**
	 * 권한없음.
	 * @param actionmapping
	 * @param actionform
	 * @param httpservletrequest
	 * @param httpservletresponse
	 */
    public ActionForward displayNoAccess(ActionMapping mapping, ActionForm form, HttpServletRequest request
            		, HttpServletResponse response) throws Exception {   	
        String s = "displayNoAccess";
        ActionErrors actionerrors = null;
        if(request.getAttribute("org.apache.struts.action.ERROR") == null)
        {
            actionerrors = new ActionErrors();
        } else
        {
            actionerrors = (ActionErrors)request.getAttribute("org.apache.struts.action.ERROR");
        }
        if(!actionerrors.isEmpty()) {
        	saveErrors(request, actionerrors);
        }
       	return mapping.findForward(s);
    }

}