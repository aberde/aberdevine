package kr.go.rndcall.mgnt.faq.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.rndcall.mgnt.common.AttachVO;
import kr.go.rndcall.mgnt.common.FileUpload;
import kr.go.rndcall.mgnt.common.FileVO;
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.faq.dao.FaqDAO;
import kr.go.rndcall.mgnt.faq.vo.FaqVO;
import kr.go.rndcall.mgnt.faq.vo.SatiVO;
import kr.go.rndcall.mgnt.faq.biz.FaqBiz;
import kr.go.rndcall.mgnt.faq.form.FaqForm;
import kr.go.rndcall.mgnt.faq.vo.FaqResultVO;
import kr.go.rndcall.mgnt.faq.vo.FaqSearchVO;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class FaqAction extends DispatchAction {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public FaqAction() {
        super();
    }
    /**
     * 자주하는질문 등록폼을 생성하는 메소드
     */     
    public ActionForward faqInsertForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
        String target = "faqInsertForm";   
        
		ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		FaqForm fm = (FaqForm) form;
		FaqSearchVO searchVO = fm.getSearchVO();
		FaqResultVO resultVO = new FaqResultVO();
		FaqVO vo = fm.getVo();
		//searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
		//searchVO.setName(Util.getName(request)); //로그인 이름정보		
						
	    if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
			vo.setReg_id(Util.getLoginId(request));
		}
        
		try {
        	FaqBiz biz = new FaqBiz();
            
            // 대분류
            fm.setVoList2(biz.retrieveCategory1Code(searchVO).getVoList());
            
            //소분류
            fm.setVoList3(biz.retrieveCategory2Code(searchVO).getVoList());
            
            System.out.println("biz.retrieveCategory1Code(searchVO).getVoList()=>" + biz.retrieveCategory1Code(searchVO).getVoList().size());
            System.out.println("biz.retrieveCategory1Code(searchVO).getVoList()=>" + biz.retrieveCategory2Code(searchVO).getVoList().size());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		return mapping.findForward(target);
    }
    /**
     * 자주하는질문 등록폼을 생성하는 메소드
     */     
    public ActionForward smsPopForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
    		, HttpServletResponse response) throws Exception {
    	
    	String target = "smsPopForm";   
    	
    	ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	FaqForm fm = (FaqForm) form;
    	FaqSearchVO searchVO = fm.getSearchVO();
    	FaqResultVO resultVO = new FaqResultVO();
    	FaqVO vo = fm.getVo();
    	//searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
    	//searchVO.setName(Util.getName(request)); //로그인 이름정보		
    	
	    if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
			vo.setReg_id(Util.getLoginId(request));
		}
    	
    	
    	return mapping.findForward(target);
    }

    /**
     * 자주하는질문과답변을 등록하는 메소드
     */     
    public ActionForward faqInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request
    		, HttpServletResponse response) throws Exception {
    	
    	String target = "faqInsert"; 
    	String faqSeq = "";
    	
    	ActionErrors errors = null;
    	
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	FaqForm fm = (FaqForm) form;
    	FaqSearchVO searchVO = fm.getSearchVO();
    	//System.out.println("메뉴sn=========================="+fm.getSearchVO().getMenu_sn());
    	FaqResultVO resultVO = new FaqResultVO();
    	//FaqDAO dao = new FaqDAO();
    	FaqVO vo = fm.getVo();
    	
		FileVO[] fileInfo = null;
	    AttachVO[] attachVO = null;
    	
	    if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
			vo.setReg_id(Util.getLoginId(request));
		}
        /*
         * 첨부파일 업로드 
         */ 
		try {
            // attach
            FileUpload upload = new FileUpload(mapping, form, request);
            upload.setFilePath("rndcall/upload/data/");
            
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
               FaqBiz FaqBiz = new FaqBiz();
               FaqBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
    	try{
    		FaqDAO dao = new FaqDAO();
    		faqSeq = dao.getFaqSeqID();
    		vo.setFaqSeq(faqSeq);
    	} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
    	
    	try{
    		FaqBiz biz = new FaqBiz();
    		searchVO.setFlag("V");
    		biz.faqInsert(vo, searchVO);
    		request.setAttribute("result", new Integer(1));
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
		
    	try{
    		FaqBiz biz = new FaqBiz();
    		resultVO = biz.faqList(vo, searchVO);
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
    	request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		fm.setTotRowCount(resultVO.getTotRowCount());
    	fm.setVoList(resultVO.getVoList());
		saveErrors(request, errors);        
        return mapping.findForward(target);
    }
    /**
     * 자주하는질문 리스트조회 메소드
     */     
    public ActionForward faqList(ActionMapping mapping, ActionForm form, HttpServletRequest request
    		, HttpServletResponse response) throws Exception {
    	
    	String target = "faqList";   
    	
    	ActionErrors errors = null;
    	
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	FaqForm fm = (FaqForm) form;
    	FaqSearchVO searchVO = fm.getSearchVO();
    	FaqResultVO resultVO = new FaqResultVO();
    	FaqVO vo = fm.getVo();
    	
	    if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
			vo.setReg_id(Util.getLoginId(request));
		}
		
		//searchVO.setLoginId("admin"); //로그인 아이디정보
		//searchVO.setName("관리자"); //로그인 이름정보
    	searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	
    	try{
    		FaqBiz biz = new FaqBiz();
    		resultVO = biz.faqList(vo, searchVO);
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
    	

		try {
        	FaqBiz biz = new FaqBiz();
            
            // 대분류
            fm.setVoList2(biz.retrieveCategory1Code(searchVO).getVoList());
            
            System.out.println("biz.retrieveCategory1Code(searchVO).getVoList()=>" + biz.retrieveCategory1Code(searchVO).getVoList().size());
            System.out.println("biz.retrieveCategory1Code(searchVO).getVoList()=>" + biz.retrieveCategory2Code(searchVO).getVoList().size());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
    	
    	fm.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	fm.setVoList(resultVO.getVoList());
		fm.setTotRowCount(resultVO.getTotRowCount());
		
    	saveErrors(request, errors);        
    	return mapping.findForward(target);
    }
    /**
     * 자주하는질문 상세보기 메소드
     */     
    public ActionForward faqDetailView(ActionMapping mapping, ActionForm form, HttpServletRequest request
    		, HttpServletResponse response) throws Exception {
    	
    	String target = "faqDetailView";   
    	
    	ActionErrors errors = null;
    	
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	FaqForm fm = (FaqForm) form;
    	FaqSearchVO searchVO = fm.getSearchVO();
    	FaqResultVO resultVO = new FaqResultVO();
    	FaqResultVO satiResultVO = new FaqResultVO();
    	FaqResultVO resultFileVO = new FaqResultVO();
    	FaqVO vo = fm.getVo();
    	
	    if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
			vo.setReg_id(Util.getLoginId(request));
		}
		
    	try{
    		FaqBiz biz = new FaqBiz();
    		resultVO = biz.faqDetailView(vo, searchVO);
    		//seq = FaqDAO.getSeq();
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
    	}
		try{
			FaqBiz FaqBiz = new FaqBiz();
			resultFileVO = FaqBiz.getFileInfo(resultVO.getVo().getFile_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		//fm.setVo(resultVO.getVo());
		try{
			FaqBiz FaqBiz = new FaqBiz();
			satiResultVO = FaqBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
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
    	
    	fm.setVoList1(resultFileVO.getVoList());
    	fm.setVoList(resultVO.getVoList());
    	fm.setVo(resultVO.getVo());
    	fm.setSatiVO(satiResultVO.getSatiVO());
    	saveErrors(request, errors);        
    	return mapping.findForward(target);
    }
    /**
     * FAQ 만족도 평가하는 메소드
     */     
    public ActionForward faqSatiInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "faqSatiInsert";
        
        ActionErrors errors = null;
        
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		
		FaqForm fm = (FaqForm) form;
		FaqSearchVO searchVO = fm.getSearchVO();
		FaqResultVO resultVO = new FaqResultVO();
		FaqResultVO resultFileVO = new FaqResultVO();
		FaqResultVO satiResultVO = new FaqResultVO();

		SatiVO satiVO = fm.getSatiVO();
		FaqVO vo = fm.getVo();
		
	    if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
			vo.setReg_id(Util.getLoginId(request));
		}
		//만족도 평가 등록 
		try{
			FaqBiz FaqBiz = new FaqBiz();
			satiVO.setReg_ip(request.getRemoteAddr());
			FaqBiz.faqSatiInsert(vo, searchVO, satiVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		// 만족도 평가 등록후 상세화면 조회
		try{
			FaqBiz FaqBiz = new FaqBiz();
			resultVO = FaqBiz.faqDetailView(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		try{
			FaqBiz FaqBiz = new FaqBiz();
			resultFileVO = FaqBiz.getFileInfo(resultVO.getVo().getFile_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		try{
			FaqBiz FaqBiz = new FaqBiz();
			satiResultVO = FaqBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		
		fm.setVoList1(resultFileVO.getVoList());
		fm.setVo(resultVO.getVo());
		fm.setVoList(resultFileVO.getVoList());
		fm.setSatiVO(satiResultVO.getSatiVO());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		
		saveErrors(request, errors);
        return mapping.findForward(target);
    }
    
    /**
     * 자주하는질문 답변등록하는 메소드
     */     
/*    public ActionForward faqAnswerInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request
    		, HttpServletResponse response) throws Exception {
    	
    	String target = "faqList";   
    	
    	ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	FaqForm fm = (FaqForm) form;
    	FaqSearchVO searchVO = fm.getSearchVO();
    	FaqResultVO resultVO = new FaqResultVO();
    	FaqVO vo = fm.getVo();
		searchVO.setLoginId("admin"); //로그인 아이디정보
		searchVO.setName("관리자"); //로그인 이름정보
    	
    	try{
    		FaqBiz biz = new FaqBiz();
    		biz.faqAnswerInsert(vo, searchVO);
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
    	try{
    		FaqBiz biz = new FaqBiz();
    		resultVO = biz.faqList(vo, searchVO);
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
    	fm.setVoList(resultVO.getVoList());
		
		saveErrors(request, errors);        
        return mapping.findForward(target);
    }*/
    /**
     * FAQ를 수정하기위해 정보값을 불러오는 메소드
     */     
    public ActionForward faqContentConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "faqContentConfirm";
        ActionErrors errors = null;
        
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		
    	FaqForm fm = (FaqForm) form;
    	FaqSearchVO searchVO = fm.getSearchVO();
    	FaqResultVO resultVO = new FaqResultVO();
    	FaqVO vo = fm.getVo();
    	FaqResultVO resultFileVO = new FaqResultVO();
    	
	    if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
			vo.setReg_id(Util.getLoginId(request));
		}
		//searchVO.setLoginId("admin"); //로그인 아이디정보
		//searchVO.setName("관리자"); //로그인 이름정보
		try{
			FaqBiz FaqBiz = new FaqBiz();
			//searchVO.setFlag("U");
			resultVO = FaqBiz.faqContentConfirm(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		try{
			FaqBiz FaqBiz = new FaqBiz();
			resultFileVO = FaqBiz.getFileInfo(resultVO.getVo().getFile_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		fm.setVo(resultVO.getVo());
		fm.setVoList(resultVO.getVoList());
		fm.setVoList(resultFileVO.getVoList());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		
		saveErrors(request, errors);
        return mapping.findForward(target);   	
    }
    /**
     * FAQ를 수정하는 메소드
     */     
    public ActionForward faqUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "faqUpdate";
        ActionErrors errors = null;
        
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		
		FaqForm fm = (FaqForm) form;
		FaqSearchVO searchVO = fm.getSearchVO();
		FaqResultVO resultVO = new FaqResultVO();
		FaqResultVO resultFileVO = new FaqResultVO();
		FaqResultVO satiResultVO = new FaqResultVO();
		FaqVO vo = fm.getVo();
		
		FileVO[] fileInfo = null;
	    AttachVO[] attachVO = null;
	    
	    if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
			vo.setReg_id(Util.getLoginId(request));
		}
		//searchVO.setLoginId("admin"); //로그인 아이디정보
		//searchVO.setName("관리자"); //로그인 이름정보
		try {
            // attach
			 System.out.println("answer insert start3333333333333~~~~~~~~~~");
            FileUpload upload = new FileUpload(mapping, form, request);
            upload.setFilePath("rndcall/upload/data/");
            
           // System.out.println("answer insert start4444444444444~~~~~~~~~~"+Util.getLoginUserVO(request).getUsername());
            upload.setUsername(searchVO.getLoginId());
            System.out.println("첨부파일 업로드");
            
	        try {
	            upload.service();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        fileInfo = upload.getFileInfo();
	        System.out.println("파일정보는?"+fileInfo);

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
            	FaqBiz FaqBiz = new FaqBiz();
            	FaqBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
		
		//첨부파일 삭제유무에 따른 기존첨부파일 삭제
		try{
			FaqBiz FaqBiz = new FaqBiz();
			FaqBiz.getFileDelete(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
	    
		try{
			FaqBiz FaqBiz = new FaqBiz();
			FaqBiz.faqUpdate(vo, searchVO);
			request.setAttribute("result", new Integer(1));
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
    	try{
    		FaqBiz biz = new FaqBiz();
    		resultVO = biz.faqDetailView(vo, searchVO);
    		//seq = FaqDAO.getSeq();
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
    	}
		try{
			FaqBiz FaqBiz = new FaqBiz();
			resultFileVO = FaqBiz.getFileInfo(resultVO.getVo().getFile_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		try{
			FaqBiz FaqBiz = new FaqBiz();
			satiResultVO = FaqBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
/*    	try{
    		FaqBiz biz = new FaqBiz();
    		resultVO = biz.faqList(vo, searchVO);
    		//seq = FaqDAO.getSeq();
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
    	}*/
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		fm.setVoList1(resultFileVO.getVoList());
		fm.setVo(resultVO.getVo());
		fm.setVoList(resultVO.getVoList());
		//fm.setVoList(resultFileVO.getVoList());
		fm.setSatiVO(satiResultVO.getSatiVO());
		fm.setTotRowCount(resultVO.getTotRowCount());
		searchVO.setMenu_sn(searchVO.getMenu_sn());
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
        return mapping.findForward(target);   	
    	
    }
    /**
     * FAQ를 삭제한다.
     */     
    public ActionForward faqDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	String target = "faqDelete";
        ActionErrors errors = null;
        
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		FaqForm fm = (FaqForm) form;
		FaqSearchVO searchVO = fm.getSearchVO();
		FaqResultVO resultVO = new FaqResultVO();
		FaqResultVO resultFileVO = new FaqResultVO();
		FaqResultVO satiResultVO = new FaqResultVO();
		FaqVO vo = fm.getVo();
		
	    if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
			vo.setReg_id(Util.getLoginId(request));
		}
		//searchVO.setLoginId("admin"); //로그인 아이디정보
		//searchVO.setName("관리자"); //로그인 이름정보

		searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		
		try{
			FaqBiz FaqBiz = new FaqBiz();
			FaqBiz.faqDelete(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
    	try{
    		FaqBiz biz = new FaqBiz();
    		resultVO = biz.faqList(vo, searchVO);
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
    //제안하기 답변등록하는 메소드
    public ActionForward smsCommit(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "smsCommit";
        
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		
		FaqForm fm = (FaqForm) form;
		FaqSearchVO searchVO = fm.getSearchVO();
		FaqResultVO resultVO = new FaqResultVO();
		FaqVO vo = fm.getVo();
	    
	    if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
			vo.setReg_id(Util.getLoginId(request));
		}
		
		
	    String server_ip = request.getServerName();
		try{
			FaqBiz FaqBiz = new FaqBiz();
			FaqBiz.smsCommit(vo, searchVO);
			FaqBiz.emailCommit(vo, searchVO, server_ip);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
		
		//fm.setVo(resultVO.getVo()); 
		
		//SMS, EMAIL 발송
		//SendSms(searchVO.getSeq(),"Y", searchVO.getLoginId());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
		
        return mapping.findForward(target);   	
    	
    }
    //유권해석 faq등록 SMS발송
    public void SendSms(String seq, String type, String login_id) throws Exception {
    	
    	// Default target to success    	
        String msg = "";
        
        try{
			FaqBiz FaqBiz = new FaqBiz();
			FaqBiz.sendSms(seq, type, login_id);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
		
/*        if(type.equals("I")){
        	msg = "답변이 등록";
        	
        }else{
        	msg = "답변이 수정";
        }*/
        System.out.println("seq::"+seq+"에 "+msg+" 되어 SMS를 발송하였습니다.");
    }

    /**
     * 부처담당자 리스트를 가져온다.
     */     
    public ActionForward orgTelNum(ActionMapping mapping, ActionForm form, HttpServletRequest request
    		, HttpServletResponse response) throws Exception {
    	
    	String target = "orgTelNum";   
    	
    	ActionErrors errors = null;
    	
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	FaqForm fm = (FaqForm) form;
    	FaqSearchVO searchVO = fm.getSearchVO();
    	FaqResultVO resultVO = new FaqResultVO();
    	FaqVO vo = fm.getVo();
    	
	    if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
			vo.setReg_id(Util.getLoginId(request));
		}
		
		//searchVO.setLoginId("admin"); //로그인 아이디정보
		//searchVO.setName("관리자"); //로그인 이름정보
    	searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	
    	try{
    		FaqBiz biz = new FaqBiz();
    		resultVO = biz.orgTelNum(vo, searchVO);
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
		fm.setTotRowCount(resultVO.getTotRowCount());
		fm.setSearchVO(searchVO);
    	saveErrors(request, errors);        
    	return mapping.findForward(target);
    }
}
