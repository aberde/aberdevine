package kr.go.rndcall.mgnt.common;

import kr.go.rndcall.mgnt.login.LoginVO;
import kr.go.rndcall.mgnt.common.BaseForm;
import kr.go.rndcall.mgnt.common.FileVO;
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.common.Configuration;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class RoleDispatchAction extends DispatchAction {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	protected FileVO[] fileVOList = null;
	protected String rootPath = "";
	protected String defaultPath = "C:\\";
    
    public RoleDispatchAction() {    	
        super();        
    }

    public ActionForward execute(ActionMapping mapping
                                 , ActionForm form
                                 , javax.servlet.http.HttpServletRequest request
                                 , javax.servlet.http.HttpServletResponse response) throws Exception {
    	ActionForward forward = null;
        String target = null;

        if (isCancelled(request)) {
            // Cancel pressed back to previous page
            return mapping.findForward(request.getRequestURI());
        }

        ActionErrors errors = null;
        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
        }

        // 로그인이 안된상태면 세션에 게스트 권한을 담아준다.
        if(!Util.isValidSession(request, response)) {            
            //target = "delSessionDo";
            //errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.login.required"));
            //saveErrors(request, errors);
            //logger.debug("## session timeout");
            //logger.debug("target: " + target);
            //return mapping.findForward(target);
        	
        	LoginVO vo = new LoginVO();
			vo.setUsername("guest");
			vo.setRoleCD("00000");
			vo.setRoleNM("guest");
			vo.setLogin_id("guest");
			vo.setName("guest");
			vo.setEmail("guest");			
			request.getSession().setAttribute("loginUserVO",vo);
        }
        
    	// 메뉴 권한 체크
    	try {
    		// 세션에서 권한을 가져와서 이 권한이 메뉴에 접근가능한지 판단하고
    		// 메뉴에 접근가능하면 삭제권한, 등등이 있는지 세팅해서 돌려준다.
            if (!Util.isValidRole(request, this.servlet.getServletContext(), form) ) {
            	System.out.println("isValidRole = NoAccess!!");
                target = "noAccessDo";
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.login.role.required"));
                saveErrors(request, errors);	                
                return mapping.findForward(target);
            }
    	} catch(Exception e) {
    		e.printStackTrace();
    		target = "delSessionDo";
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.login.role.required"));
            saveErrors(request, errors);
            logger.debug("## session timeout");
            logger.debug("target: " + target);
            return mapping.findForward(target);
    	}        
    	Util.setAccessAuthByRole(request, form, this.servlet.getServletContext());
    	String category_id = request.getParameter("category_id");
    	String[] fileList = request.getParameterValues("_innods_filename");
    	if(fileList != null) {
    		String[] strFilename = request.getParameterValues("_innods_filename");
    		String[] strFolder = request.getParameterValues("_innods_folder");
    		String[] strFilesize = request.getParameterValues("_innods_filesize");
    		String[] strOriginFname = request.getParameterValues("_origin_fname");
    		String server_path = request.getParameter("_server_path");    		
    		try {
            	this.rootPath = Configuration.getInstance().get("conf.upload.path");			
        	} catch (Exception e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        		this.rootPath = defaultPath;
        	}    		
        	    		
        	this.fileVOList = new FileVO[fileList.length];
    		for(int i=0; i<fileList.length; i++) {
    			this.fileVOList[i] = new FileVO();
    			this.fileVOList[i].setInputFileName(strOriginFname[i]);
    			this.fileVOList[i].setOutputFileName(strFilename[i]);
    			this.fileVOList[i].setSize(String.valueOf(strFilesize[i]));
    			this.fileVOList[i].setRelativePath(server_path);
    			this.fileVOList[i].setAbsolutePath(this.rootPath + server_path);
    		}
    	} else {
    		this.fileVOList = null;
    	}
    	forward = super.execute(mapping, form, request, response);
        return forward;   
    }
    
}
