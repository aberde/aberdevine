package kr.go.rndcall.mgnt.notice.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.rndcall.mgnt.common.AttachVO;
import kr.go.rndcall.mgnt.common.FileUpload;
import kr.go.rndcall.mgnt.common.FileVO;
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.notice.form.NoticeForm;
import kr.go.rndcall.mgnt.notice.vo.NoticeResultVO;
import kr.go.rndcall.mgnt.notice.vo.NoticeSearchVO;
import kr.go.rndcall.mgnt.notice.vo.NoticeVO;
import kr.go.rndcall.mgnt.notice.biz.NoticeBiz;
import kr.go.rndcall.mgnt.notice.dao.NoticeDAO;
import kr.go.rndcall.mgnt.offer.biz.OfferBiz;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class NoticeAction extends DispatchAction {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public NoticeAction() {
        super();
    }

    //공지사항 등록폼           
    public ActionForward noticeInsertForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
        String target = "noticeInsertForm";   
        
		ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		NoticeForm fm = (NoticeForm) form;
		NoticeSearchVO searchVO = fm.getSearchVO();
		NoticeResultVO resultVO = new NoticeResultVO();
		//searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
		//searchVO.setName(Util.getName(request)); //로그인 이름정보		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		return mapping.findForward(target);
    }
    //공지사항 리스트조회 
    public ActionForward noticeList(ActionMapping mapping, ActionForm form, HttpServletRequest request
    		, HttpServletResponse response) throws Exception {
    	
    	String target = "noticeList";   
    	
    	ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	NoticeForm fm = (NoticeForm) form;
    	NoticeSearchVO searchVO = fm.getSearchVO();
    	NoticeResultVO resultVO = new NoticeResultVO();
    	NoticeVO vo = fm.getVo();
    	
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		searchVO.setBoard_type("NOTICE"); //임시로 사용
		searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	try{
    		NoticeBiz biz = new NoticeBiz();
    		resultVO = biz.noticeList(searchVO);
    		//seq = FaqDAO.getSeq();
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
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
    	fm.setVoList(resultVO.getVoList());
		fm.setVo(resultVO.getVo());
		fm.setTotRowCount(resultVO.getTotRowCount());
    	String title = resultVO.getVo().getTitle();
    	System.out.println("타이틀"+title);
    	saveErrors(request, errors);        
    	return mapping.findForward(target);
    }
    //공지사항을  등록하는 메소드
    public ActionForward noticeInsert(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String target = "noticeInsert";
        ActionErrors errors = null;
        String data_id = "";
        String question_id = ""; // question 아이디 
        int result = 0;

        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
                    .getAttribute("org.apache.struts.action.ERROR");
        }
        
        NoticeForm fm = (NoticeForm) form;
        NoticeSearchVO searchVO = fm.getSearchVO();
        NoticeResultVO resultVO = new NoticeResultVO();
        NoticeVO vo = fm.getVo();
        
		FileVO[] fileInfo = null;
	    AttachVO[] attachVO = null;
        
	    if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
			vo.setReg_id(Util.getLoginId(request));
		}
        //미리 질문테이블에서 id값을 가져온다.
        try{
        	NoticeDAO dao =  new NoticeDAO();
            question_id = dao.questionID();
            
            vo.setData_id(data_id);
            vo.setQuestion_id(question_id);
            vo.setInsert_type("WEB");
            vo.setBoard_type("NOTICE");
            
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
        /*
         * 첨부파일 업로드 
         */ 
		try {
            // attach
            FileUpload upload = new FileUpload(mapping, form, request);
            upload.setFilePath("rndcall/upload/notice/");
            
           // System.out.println("answer insert start4444444444444~~~~~~~~~~"+Util.getLoginUserVO(request).getUsername());
            upload.setUsername(searchVO.getLoginId());
            System.out.println("첨부파일 업로드");
            
	        try {
	            upload.service();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        fileInfo = upload.getFileInfo();

		} catch (Exception e) {
			logger.debug("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
	    }        
        //vo.setFilePath(fileInfo[0].getAbsolutePath() + fileInfo[0].getOutputFileName());
        
        /*
         * 업로드된 첨부파일 정보를DB에 insert
         */
		if(fileInfo.length != 0){
            attachVO = new AttachVO[fileInfo.length];
            for (int i = 0; i < fileInfo.length; i++) {
            	if (fileInfo[i] != null) {
	                attachVO[i] = new AttachVO();
	                attachVO[i].setFileNM(fileInfo[i].getInputFileName());
	                attachVO[i].setFilePath(fileInfo[i].getRelativePath());
	                attachVO[i].setFileSize(fileInfo[i].getSize());
	                attachVO[i].setSaveFileNM(fileInfo[i].getOutputFileName());
            	}
            }
            try { 
               NoticeBiz NoticeBiz = new NoticeBiz();
               NoticeBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
        
		//공지사항 등록 부분
        try {
           
        	NoticeBiz NoticeBiz = new NoticeBiz();
            resultVO = NoticeBiz.noticeInsert(vo, searchVO);
            request.setAttribute("result", new Integer(1));
            
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }
        searchVO.setBoard_type("NOTICE");
        //공지사항 리스트를 가져온다.
    	try{
    		NoticeBiz NoticeBiz = new NoticeBiz();
    		resultVO = NoticeBiz.noticeList(searchVO);
    		//seq = FaqDAO.getSeq();
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
    	}
    	request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
    	fm.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	fm.setVoList(resultVO.getVoList());
    	fm.setTotRowCount(resultVO.getTotRowCount());
    	
    	if (errors.isEmpty()) {
    		if (resultVO.getTotRowCount().intValue() == 0) {
    			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
    			"errors.getList.DataNotFound"));
    		} else {
    			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
    			"errors.getList.success"));
    		}
    	}
        
        if (errors.isEmpty()) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.getList.success"));
        }
        saveErrors(request, errors);

        logger.debug("target: " + target);
        return mapping.findForward(target);
    }
    
    //공지사항 상세정보 조회
    public ActionForward noticeDetailView(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String target = "noticeDetailView"; 
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		NoticeForm fm = (NoticeForm) form;
		NoticeSearchVO searchVO = fm.getSearchVO();
		NoticeResultVO resultVO = new NoticeResultVO();
		NoticeResultVO resultFileVO = new NoticeResultVO();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
						
		//searchVO.setLoginId("admin"); //로그인 아이디정보
		//searchVO.setName("관리자"); //로그인 이름정보
		
		try{
			NoticeBiz NoticeBiz = new NoticeBiz();
			resultVO = NoticeBiz.noticeDetailView(searchVO);
			
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			NoticeBiz NoticeBiz = new NoticeBiz();
			resultFileVO = NoticeBiz.getFileInfo(resultVO.getVo().getFile_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		System.out.println("resultVO.getVo().getSeq()::"+resultVO.getVo().getSeq());
		
		fm.setVo(resultVO.getVo());
		fm.setVoList(resultFileVO.getVoList());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
       
        return mapping.findForward(target);
    }
    
    
    //공지사항  수정폼
    public ActionForward noticeUpdateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "noticeUpdateForm";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		NoticeForm fm = (NoticeForm) form;
		NoticeSearchVO searchVO = fm.getSearchVO();
		NoticeResultVO resultVO = new NoticeResultVO();
		NoticeResultVO resultFileVO = new NoticeResultVO();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		try{
			NoticeBiz NoticeBiz = new NoticeBiz();
			resultVO = NoticeBiz.noticeDetailView(searchVO);
			
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			NoticeBiz NoticeBiz = new NoticeBiz();
			resultFileVO = NoticeBiz.getFileInfo(resultVO.getVo().getFile_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		fm.setVo(resultVO.getVo());
		fm.setVoList(resultFileVO.getVoList());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
        return mapping.findForward(target);   	
    	
    }
    
    
    //공지사항을 수정한다.
    public ActionForward noticeUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "noticeUpdate";
        
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		System.out.println("answer insert start~~~~~~~~~~");
		
		NoticeForm fm = (NoticeForm) form;
		NoticeSearchVO searchVO = fm.getSearchVO();
		NoticeResultVO resultVO = new NoticeResultVO();
		NoticeResultVO resultFileVO = new NoticeResultVO();
		NoticeVO vo = fm.getVo();
		
		FileVO[] fileInfo = null;
	    AttachVO[] attachVO = null;
	    
	    if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
			vo.setReg_id(Util.getLoginId(request));
		}
		
		System.out.println("answer insert start ::" + searchVO.getLoginId());
		System.out.println("answer insert start22222222222222~~~~~~~~~~");
		
		try {
            // attach
			 System.out.println("answer insert start3333333333333~~~~~~~~~~");
            FileUpload upload = new FileUpload(mapping, form, request);
            upload.setFilePath("rndcall/upload/notice/");
            
           // System.out.println("answer insert start4444444444444~~~~~~~~~~"+Util.getLoginUserVO(request).getUsername());
            upload.setUsername(searchVO.getLoginId());
            System.out.println("첨부파일 업로드");
            
	        try {
	            upload.service();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        fileInfo = upload.getFileInfo();

		} catch (Exception e) {
			logger.debug("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
	    } 
	    
		
		if(fileInfo.length != 0){
            attachVO = new AttachVO[fileInfo.length];
            for (int i = 0; i < fileInfo.length; i++) {
            	if (fileInfo[i] != null) {
	                attachVO[i] = new AttachVO();
	                attachVO[i].setFileNM(fileInfo[i].getInputFileName());
	                attachVO[i].setFilePath(fileInfo[i].getRelativePath());
	                attachVO[i].setFileSize(fileInfo[i].getSize());
	                attachVO[i].setSaveFileNM(fileInfo[i].getOutputFileName());
            	}
            }
            try { 
            	NoticeBiz NoticeBiz = new NoticeBiz();
            	NoticeBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
		
		//첨부파일 삭제유무에 따른 기존첨부파일 삭제
		try{
			NoticeBiz NoticeBiz = new NoticeBiz();
			NoticeBiz.getFileDelete(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		try{
			NoticeBiz NoticeBiz = new NoticeBiz();
			NoticeBiz.noticeUpdate(vo, searchVO);
			request.setAttribute("result", new Integer(1));
		} catch (Exception e) {
			logger.debug("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}
		try{
			NoticeBiz NoticeBiz = new NoticeBiz();
			resultVO = NoticeBiz.noticeDetailView(searchVO);
			
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			NoticeBiz NoticeBiz = new NoticeBiz();
			resultFileVO = NoticeBiz.getFileInfo(resultVO.getVo().getFile_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		//System.out.println("resultVO.getVo().getSeq()::"+resultVO.getVo().getSeq());
		
		fm.setVo(resultVO.getVo());
		fm.setVoList(resultFileVO.getVoList());
		
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
		
        return mapping.findForward(target);   	
    	
    }
    //공지사항  삭제
    public ActionForward noticeDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	String target = "noticeDelete";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		NoticeForm fm = (NoticeForm) form;
		NoticeSearchVO searchVO = fm.getSearchVO();
		NoticeResultVO resultVO = new NoticeResultVO();
		NoticeResultVO resultFileVO = new NoticeResultVO();
		NoticeResultVO satiResultVO = new NoticeResultVO();
		NoticeVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}

		searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		
		try{
			NoticeBiz NoticeBiz = new NoticeBiz();
			NoticeBiz.noticeDelete(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
				
    	try{
    		NoticeBiz biz = new NoticeBiz();
    		resultVO = biz.noticeList(searchVO);
    		//seq = FaqDAO.getSeq();
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
    	}
				
		fm.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		fm.setVoList(resultVO.getVoList());
		fm.setTotRowCount(resultVO.getTotRowCount());
		fm.setVo(resultVO.getVo());
        return mapping.findForward(target);   	
    	
    }   
}
