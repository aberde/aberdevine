package kr.go.rndcall.mgnt.inquire.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import kr.go.rndcall.mgnt.inquire.form.InquireForm;
import kr.go.rndcall.mgnt.inquire.vo.InquireSearchVO;
import kr.go.rndcall.mgnt.inquire.vo.InquireAttachVO;
import kr.go.rndcall.mgnt.inquire.vo.InquireResultVO;
import kr.go.rndcall.mgnt.inquire.vo.InquireVO;
import kr.go.rndcall.mgnt.inquire.vo.SatiVO;
import kr.go.rndcall.mgnt.common.AttachVO;
import kr.go.rndcall.mgnt.common.FileUpload;
import kr.go.rndcall.mgnt.common.FileVO;
import kr.go.rndcall.mgnt.common.Util;

import kr.go.rndcall.mgnt.inquire.biz.InquireBiz;
import kr.go.rndcall.mgnt.inquire.dao.InquireDAO;

public class InquireAction extends DispatchAction {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public InquireAction() {
        super();
    }

	 /**
     * 온라인상담 메인리스트를 조회
     */        
    public ActionForward getInquireMainList(ActionMapping mapping, ActionForm form, HttpServletRequest request
                                 , HttpServletResponse response) throws Exception {
    	
        String target = "getInquireMainList";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		searchVO.setMaxPageItems("5");
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			resultVO = InquireBiz.getInquireMainList(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		fm.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		fm.setVoList(resultVO.getVoList());
		fm.setVoList1(resultVO.getVoList1());
		fm.setVo(resultVO.getVo());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);     
        return mapping.findForward(target); ///inquire/inquire.jsp로 forward
    }
    
    /**
     * Q&A 리스트를 조회
     */        
    public ActionForward getInquireList(ActionMapping mapping, ActionForm form, HttpServletRequest request
                                 , HttpServletResponse response) throws Exception {
    	
        String target = "getInquireList";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO cateResultVO1 = new InquireResultVO();//대분야정보
		InquireVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		searchVO.setMenu_sn("01");
		searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			resultVO = InquireBiz.getInquireList(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		// 대분류 정보 조회
		try{
			InquireBiz InquireBiz = new InquireBiz();
			cateResultVO1 = InquireBiz.getCodeInfo();
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
				
		fm.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		fm.setVoList(resultVO.getVoList());
		fm.setTotRowCount(resultVO.getTotRowCount());
		fm.setVo(resultVO.getVo());		
		fm.setVoList2(cateResultVO1.getVoList());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);         
        return mapping.findForward(target); ///inquire/inquire.jsp로 forward
    }
    
    /**
     * Q&A 상세정보를 조회하는 함수
     */     
    public ActionForward getInquireView(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String target = "getInquireView"; 
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();		
		InquireResultVO satiResultVO = new InquireResultVO();
		InquireResultVO cateResultVO1 = new InquireResultVO();//대분야정보
		
		InquireResultVO questionFileVO = new InquireResultVO(); //질문 첨부파일
		InquireResultVO answerFileVO = new InquireResultVO(); //답변첨부파일
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());

		try{
			InquireBiz InquireBiz = new InquireBiz();
			searchVO.setFlag("V");
			resultVO = InquireBiz.getInquireView(searchVO);
			
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			questionFileVO = InquireBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			answerFileVO = InquireBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		
		System.out.println("resultVO.getVo().getSeq()::"+resultVO.getVo().getSeq());
		try{
			InquireBiz InquireBiz = new InquireBiz();
			satiResultVO = InquireBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		// 대분류 정보 조회
		try{
			InquireBiz InquireBiz = new InquireBiz();
			cateResultVO1 = InquireBiz.getCodeInfo();
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		fm.setVo(resultVO.getVo());
		fm.setVoList(questionFileVO.getVoList());	//질문첨부파일정보
		fm.setVoList1(answerFileVO.getVoList());	//답변첨부파일
		fm.setSatiVO(satiResultVO.getSatiVO());
		fm.setVoList2(cateResultVO1.getVoList());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
       
        return mapping.findForward(target);
    }
    
    
    /**
     * Q&A 상세정보를 프린트하기위해 불러오는 팝업창
     */     
    public ActionForward getInquireViewPop(ActionMapping mapping, ActionForm form, HttpServletRequest request
    		, HttpServletResponse response) throws Exception {   	
    	
    	String target = "getInquireViewPop"; 
    	ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	InquireForm fm = (InquireForm) form;
    	InquireSearchVO searchVO = fm.getSearchVO();
    	InquireResultVO resultVO = new InquireResultVO();		
    	InquireResultVO satiResultVO = new InquireResultVO();
    	InquireResultVO cateResultVO1 = new InquireResultVO();//대분야정보
    	
    	InquireResultVO questionFileVO = new InquireResultVO(); //질문 첨부파일
    	InquireResultVO answerFileVO = new InquireResultVO(); //답변첨부파일
    	
    	if(Util.getLoginUserVO(request) != null){
    		searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
    		searchVO.setName(Util.getName(request)); //로그인 이름정보
    		searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
    	}
    	searchVO.setMenu_sn("01");
    	
    	try{
    		InquireBiz InquireBiz = new InquireBiz();
    		searchVO.setFlag("V");
    		resultVO = InquireBiz.getInquireView(searchVO);
    		
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
    	}
    	
    	try{
    		InquireBiz InquireBiz = new InquireBiz();
    		questionFileVO = InquireBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
    	}
    	
    	try{
    		InquireBiz InquireBiz = new InquireBiz();
    		answerFileVO = InquireBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
    	}
    	
    	
    	System.out.println("resultVO.getVo().getSeq()::"+resultVO.getVo().getSeq());
    	try{
    		InquireBiz InquireBiz = new InquireBiz();
    		satiResultVO = InquireBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
    	}
    	
    	// 대분류 정보 조회
    	try{
    		InquireBiz InquireBiz = new InquireBiz();
    		cateResultVO1 = InquireBiz.getCodeInfo();
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
    	}
    	
    	fm.setVo(resultVO.getVo());
    	fm.setVoList(questionFileVO.getVoList());	//질문첨부파일정보
    	fm.setVoList1(answerFileVO.getVoList());	//답변첨부파일
    	fm.setSatiVO(satiResultVO.getSatiVO());
    	fm.setVoList2(cateResultVO1.getVoList());
    	
    	if (errors.isEmpty()) {
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
    		"errors.getList.success"));
    	}
    	saveErrors(request, errors);
    	
    	return mapping.findForward(target);
    }

    /**
     * Q&A 상세정보를 프린트하기위해 불러오는 팝업창
     */     
    public ActionForward getInquireViewPopup(ActionMapping mapping, ActionForm form, HttpServletRequest request
    		, HttpServletResponse response) throws Exception {   	
    	
    	String target = "getInquireViewPopup"; 
    	ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	InquireForm fm = (InquireForm) form;
    	InquireSearchVO searchVO = fm.getSearchVO();
    	InquireResultVO resultVO = new InquireResultVO();		
    	InquireResultVO satiResultVO = new InquireResultVO();
    	InquireResultVO cateResultVO1 = new InquireResultVO();//대분야정보
    	
    	InquireResultVO questionFileVO = new InquireResultVO(); //질문 첨부파일
    	InquireResultVO answerFileVO = new InquireResultVO(); //답변첨부파일
    	
    	if(Util.getLoginUserVO(request) != null){
    		searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
    		searchVO.setName(Util.getName(request)); //로그인 이름정보
    		searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
    	}
    	searchVO.setMenu_sn("01");
    	
    	try{
    		InquireBiz InquireBiz = new InquireBiz();
    		searchVO.setFlag("V");
    		resultVO = InquireBiz.getInquireView(searchVO);
    		
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
    	}
    	
    	try{
    		InquireBiz InquireBiz = new InquireBiz();
    		questionFileVO = InquireBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
    	}
    	
    	try{
    		InquireBiz InquireBiz = new InquireBiz();
    		answerFileVO = InquireBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
    	}
    	
    	
    	System.out.println("resultVO.getVo().getSeq()::"+resultVO.getVo().getSeq());
    	try{
    		InquireBiz InquireBiz = new InquireBiz();
    		satiResultVO = InquireBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
    	}
    	
    	// 대분류 정보 조회
    	try{
    		InquireBiz InquireBiz = new InquireBiz();
    		cateResultVO1 = InquireBiz.getCodeInfo();
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
    	}
    	
    	fm.setVo(resultVO.getVo());
    	fm.setVoList(questionFileVO.getVoList());	//질문첨부파일정보
    	fm.setVoList1(answerFileVO.getVoList());	//답변첨부파일
    	fm.setSatiVO(satiResultVO.getSatiVO());
    	fm.setVoList2(cateResultVO1.getVoList());
    	
    	if (errors.isEmpty()) {
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
    		"errors.getList.success"));
    	}
    	saveErrors(request, errors);
    	
    	return mapping.findForward(target);
    }

    /**
     * Q&A 등록/수정폼을 생성하는 메소드
     */     
    public ActionForward getInquireForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
        String target = "getInquireForm";   
        
		ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO loginResultVO = new InquireResultVO();
		InquireResultVO resultFileVO = new InquireResultVO();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			resultFileVO = InquireBiz.getFileInfo(resultVO.getVo().getFile_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			loginResultVO = InquireBiz.getUserInfo(searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }

		fm.setVoList(resultFileVO.getVoList());
		fm.setLoginVO(loginResultVO.getLoginVO());
		System.out.println("MOBILE :::::: " + fm.getLoginVO().getMobile());
		System.out.println("EMAIL :::::: " + fm.getLoginVO().getEmail());
		fm.setErrCd("0");
		
		return mapping.findForward(target);
    }
    
    
    /**
     * Q&A 등록하는 메소드
     */     
    public ActionForward getInquireInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getInquireForm";
        
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireVO vo = fm.getVo();
		FileVO[] fileInfo = null;
	    AttachVO[] attachVO = null;
	    boolean success = false;
	    
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		try {
            // attach
            FileUpload upload = new FileUpload(mapping, form, request);
            upload.setFilePath("rndcall/upload/qna/");
            
            upload.setUsername(searchVO.getLoginId());
            
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
               InquireBiz InquireBiz = new InquireBiz();
                InquireBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			success = InquireBiz.getInquireInsert(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			resultVO = InquireBiz.getInquireMainList(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		fm.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		fm.setVoList(resultVO.getVoList());
		fm.setVoList1(resultVO.getVoList1());
		fm.setVo(resultVO.getVo());
		fm.setErrCd(String.valueOf(success));
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors); 
        return mapping.findForward(target);
    }
    
    /**
     * Q&A 만족도 평가하는 메소드
     */     
    public ActionForward getSatiInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getSatiInsert";
        
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO questionFileVO = new InquireResultVO();
		InquireResultVO answerFileVO = new InquireResultVO();
		
		InquireResultVO satiResultVO = new InquireResultVO();
		InquireResultVO cateResultVO1 = new InquireResultVO();
		
		SatiVO satiVO = fm.getSatiVO();
		InquireVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		//만족도 평가 등록 
		try{
			InquireBiz InquireBiz = new InquireBiz();
			satiVO.setReg_ip(request.getRemoteAddr());
			
			InquireBiz.getSatiInsert(vo, searchVO, satiVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
		// 만족도 평가 등록후 상세화면 조회
		try{
			InquireBiz InquireBiz = new InquireBiz();
			searchVO.setFlag("V");
			resultVO = InquireBiz.getInquireView(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		System.out.println("getSatiInsert resultVO.getVo().getFile_id()::"+resultVO.getVo().getFile_id());
		try{
			InquireBiz InquireBiz = new InquireBiz();
			questionFileVO = InquireBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			answerFileVO = InquireBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		System.out.println("getSatiInsert resultVO.getVo().getSeq()::"+resultVO.getVo().getSeq());
		try{
			InquireBiz InquireBiz = new InquireBiz();
			satiResultVO = InquireBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		// 대분류 정보 조회
		try{
			InquireBiz InquireBiz = new InquireBiz();
			cateResultVO1 = InquireBiz.getCodeInfo();
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		
		fm.setVo(resultVO.getVo());
		fm.setVoList(questionFileVO.getVoList());	//질문첨부파일정보
		fm.setVoList1(answerFileVO.getVoList());	//답변첨부파일
		fm.setSatiVO(satiResultVO.getSatiVO());
		fm.setVoList2(cateResultVO1.getVoList());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
		
        return mapping.findForward(target);
    }
    
    /**
     * Q&A 만족도 평가하는 메소드
     */     
    public ActionForward getSatiOrgInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getSatiOrgInsert";
        
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO questionFileVO = new InquireResultVO();
		InquireResultVO answerFileVO = new InquireResultVO();
		
		InquireResultVO satiResultVO = new InquireResultVO();
		InquireResultVO cateResultVO1 = new InquireResultVO();
		
		SatiVO satiVO = fm.getSatiVO();
		InquireVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		//만족도 평가 등록 
		try{
			InquireBiz InquireBiz = new InquireBiz();
			satiVO.setReg_ip(request.getRemoteAddr());
			
			InquireBiz.getSatiInsert(vo, searchVO, satiVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
		// 만족도 평가 등록후 상세화면 조회
		try{
			InquireBiz InquireBiz = new InquireBiz();
			searchVO.setFlag("V");
			resultVO = InquireBiz.getInquireView(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		System.out.println("getSatiInsert resultVO.getVo().getFile_id()::"+resultVO.getVo().getFile_id());
		try{
			InquireBiz InquireBiz = new InquireBiz();
			questionFileVO = InquireBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			answerFileVO = InquireBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		System.out.println("getSatiInsert resultVO.getVo().getSeq()::"+resultVO.getVo().getSeq());
		try{
			InquireBiz InquireBiz = new InquireBiz();
			satiResultVO = InquireBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		// 대분류 정보 조회
		try{
			InquireBiz InquireBiz = new InquireBiz();
			cateResultVO1 = InquireBiz.getCodeInfo();
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		
		fm.setVo(resultVO.getVo());
		fm.setVoList(questionFileVO.getVoList());	//질문첨부파일정보
		fm.setVoList1(answerFileVO.getVoList());	//답변첨부파일
		fm.setSatiVO(satiResultVO.getSatiVO());
		fm.setVoList2(cateResultVO1.getVoList());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
		
        return mapping.findForward(target);
    }
    
    /**
     * Q&A 수정하는 메소드
     */     
    public ActionForward getInquireUpdateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getInquireUpdateForm";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO resultFileVO = new InquireResultVO();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			searchVO.setFlag("U");
			resultVO = InquireBiz.getInquireView(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			resultFileVO = InquireBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
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
    
    /**
     * Q&A 수정하는 메소드
     */     
    public ActionForward getInquireUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getInquireUpdate";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO satiResultVO = new InquireResultVO();
		
		InquireResultVO questionFileVO = new InquireResultVO(); //질문 첨부파일
		InquireResultVO answerFileVO = new InquireResultVO(); //답변첨부파일
		
		InquireVO vo = fm.getVo();
		FileVO[] fileInfo = null;
	    AttachVO[] attachVO = null;
	    boolean success = false;
	    
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		System.out.println("request.getParameter(searchVO.menu_sn)::"+request.getParameter("searchVO.menu_sn"));;
		
		searchVO.setMenu_sn("01");
				
		try {
            // attach
            FileUpload upload = new FileUpload(mapping, form, request);
            upload.setFilePath("rndcall/upload/qna/");
            
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
               InquireBiz InquireBiz = new InquireBiz();
                InquireBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
        
		// 첨부파일 삭제유무에 따른 기존첨부파일 삭제
		try{
			InquireBiz InquireBiz = new InquireBiz();
			InquireBiz.getQuestionFileDelete(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			success = InquireBiz.getAnswerUpdate(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			InquireBiz.getInquireUpdate(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			searchVO.setFlag("V");
			resultVO = InquireBiz.getInquireView(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			questionFileVO = InquireBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			answerFileVO = InquireBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			satiResultVO = InquireBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		
		
		System.out.println(searchVO.getMenu_sn());
		
		fm.setVo(resultVO.getVo());
		fm.setSatiVO(satiResultVO.getSatiVO());
		fm.setVoList(questionFileVO.getVoList());	//질문첨부파일정보
		fm.setVoList1(answerFileVO.getVoList());	//답변첨부파일
		fm.setErrCd(String.valueOf(success));
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
        return mapping.findForward(target);
    	
    }
    
    /**
     * Q&A 삭제하는 메소드
     */     
    public ActionForward getInquireDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	String target = "getInquireDelete";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO resultFileVO = new InquireResultVO();
		InquireResultVO satiResultVO = new InquireResultVO();
		InquireVO vo = fm.getVo();
		boolean success = false;
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}

		searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			success = InquireBiz.getInquireDelete(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
				
		try{
			InquireBiz InquireBiz = new InquireBiz();
			resultVO = InquireBiz.getInquireList(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
				
		fm.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		fm.setVoList(resultVO.getVoList());
		fm.setTotRowCount(resultVO.getTotRowCount());
		fm.setVo(resultVO.getVo());
		fm.setErrCd(String.valueOf(success));
		
		return mapping.findForward(target);   	
    	
    }
    
    
    /**
     * Q&A 답변등록하는 메소드
     */     
    public ActionForward getAnswerInsertForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getAnswerInsertForm";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO satiResultVO = new InquireResultVO();
		InquireResultVO cateResultVO1 = new InquireResultVO();//대분야정보
		InquireResultVO cateResultVO2 = new InquireResultVO();//소분야정보
		
		InquireResultVO questionFileVO = new InquireResultVO(); //질문 첨부파일
		InquireResultVO answerFileVO = new InquireResultVO(); //답변첨부파일
		
		InquireVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
        try{
			InquireBiz InquireBiz = new InquireBiz();
			searchVO.setFlag("A");
			resultVO = InquireBiz.getInquireView(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			satiResultVO = InquireBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		//대분류 정보 조회
		try{
			InquireBiz InquireBiz = new InquireBiz();
			cateResultVO1 = InquireBiz.getCodeInfo();
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		//소분류정보조회
		try{
			InquireBiz InquireBiz = new InquireBiz();
			cateResultVO2 = InquireBiz.getCodeInfo1();
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			questionFileVO = InquireBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			answerFileVO = InquireBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		fm.setVo(resultVO.getVo());
		fm.setVoList(questionFileVO.getVoList());
		fm.setVoList1(answerFileVO.getVoList());
		fm.setSatiVO(satiResultVO.getSatiVO());		
		fm.setVoList2(cateResultVO1.getVoList());
        fm.setVoList3(cateResultVO2.getVoList());
				
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
        return mapping.findForward(target);   	
    	
    }
    
    /**
     * Q&A 답변등록하는 메소드
     */     
    public ActionForward getAnswerInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getAnswerInsert";
        
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		System.out.println("answer insert start~~~~~~~~~~");
		
		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO satiResultVO = new InquireResultVO();

		InquireResultVO questionFileVO = new InquireResultVO(); //질문 첨부파일
		InquireResultVO answerFileVO = new InquireResultVO(); //답변첨부파일
		InquireVO vo = fm.getVo();
		
		FileVO[] fileInfo = null;
	    AttachVO[] attachVO = null;
	    boolean success = false;
	    
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
            upload.setFilePath("rndcall/upload/qna/");
            
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
               InquireBiz InquireBiz = new InquireBiz();
                InquireBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
		
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			success = InquireBiz.getAnswerInsert(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
		try{
			InquireBiz InquireBiz = new InquireBiz();
			searchVO.setFlag("V");
			resultVO = InquireBiz.getInquireView(searchVO);
			
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			questionFileVO = InquireBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			answerFileVO = InquireBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
				
		System.out.println("resultVO.getVo().getSeq()::"+resultVO.getVo().getSeq());
		try{
			InquireBiz InquireBiz = new InquireBiz();
			satiResultVO = InquireBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		fm.setVo(resultVO.getVo());
		fm.setVoList(questionFileVO.getVoList());
		fm.setVoList1(answerFileVO.getVoList());
		fm.setSatiVO(satiResultVO.getSatiVO());
		fm.setErrCd(String.valueOf(success));
		
		//SMS, EMAIL 발송
		SendSms(searchVO.getSeq(),"I", searchVO.getLoginId());
		SendEmail(searchVO.getSeq(),"I", searchVO.getLoginId());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
		
        return mapping.findForward(target);   	
    	
    }
    
    /**
     * Q&A 답변등록하는 메소드
     */     
    public ActionForward getAnswerUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getAnswerUpdate";
        
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		System.out.println("answer insert start~~~~~~~~~~");
		
		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO resultFileVO = new InquireResultVO();
		InquireResultVO satiResultVO = new InquireResultVO();
		InquireResultVO questionFileVO = new InquireResultVO(); //질문 첨부파일
		InquireResultVO answerFileVO = new InquireResultVO(); //답변첨부파일
		InquireVO vo = fm.getVo();
		
		FileVO[] fileInfo = null;
	    AttachVO[] attachVO = null;
	    boolean success = false;
	    
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
            upload.setFilePath("rndcall/upload/qna/");
            
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
               InquireBiz InquireBiz = new InquireBiz();
                InquireBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
		
		//첨부파일 삭제여부에 따른 기존첨부파일 삭제
		try{
			InquireBiz InquireBiz = new InquireBiz();
			InquireBiz.getAnswerFileDelete(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			success = InquireBiz.getAnswerUpdate(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
		try{
			InquireBiz InquireBiz = new InquireBiz();
			searchVO.setFlag("V");
			resultVO = InquireBiz.getInquireView(searchVO);
			
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			questionFileVO = InquireBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			answerFileVO = InquireBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		System.out.println("resultVO.getVo().getSeq()::"+resultVO.getVo().getSeq());
		try{
			InquireBiz InquireBiz = new InquireBiz();
			satiResultVO = InquireBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		
		fm.setVo(resultVO.getVo());
		fm.setVoList(questionFileVO.getVoList());
		fm.setVoList1(answerFileVO.getVoList());
		fm.setSatiVO(satiResultVO.getSatiVO());
		fm.setErrCd(String.valueOf(success));
		
		// SMS, EMAIL발송
		SendSms(searchVO.getSeq(),"U", searchVO.getLoginId());
		SendEmail(searchVO.getSeq(),"U", searchVO.getLoginId());
		
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
		
        return mapping.findForward(target);   	
    	
    }
    
    /**
     * Q&A 타기관지정 등록폼
     */     
    public ActionForward getOrgTransForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getOrgTransForm";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			resultVO = InquireBiz.getOrgTransList();
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		searchVO.setTrans_id("-");
		
		fm.setVoList(resultVO.getVoList());
		fm.setSearchVO(searchVO);
		fm.setErrCd("N");
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
        return mapping.findForward(target);
    }
    
    /**
     * Q&A 답변등록을 타기관에게 지정하는 메소드
     */     
    public ActionForward getOrgTransInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getOrgTransInsert";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireVO vo = fm.getVo();
		boolean success =false;
		
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		String server_ip = request.getServerName();
		try{
			InquireBiz InquireBiz = new InquireBiz();
			success = InquireBiz.getOrgTransInsert(vo, searchVO, server_ip);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			resultVO = InquireBiz.getOrgTransList();
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		fm.setVoList(resultVO.getVoList());
		fm.setErrCd(String.valueOf(success));
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		
		
		saveErrors(request, errors);
		return mapping.findForward(target);
    }
    
    /**
     * Q&A 부처담당자 지정된 Q&A 리스트를 조회
     */        
    public ActionForward getInquireOrgList(ActionMapping mapping, ActionForm form, HttpServletRequest request
                                 , HttpServletResponse response) throws Exception {
    	
        String target = "getInquireOrgList";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO cateResultVO1 = new InquireResultVO();//대분야정보
		InquireVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			resultVO = InquireBiz.getInquireOrgList(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
				
//		대분류 정보 조회
		try{
			InquireBiz InquireBiz = new InquireBiz();
			cateResultVO1 = InquireBiz.getCodeInfo();
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
			
		fm.setVoList2(cateResultVO1.getVoList());		
		fm.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		fm.setVoList(resultVO.getVoList());
		fm.setTotRowCount(resultVO.getTotRowCount());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);         
        return mapping.findForward(target); ///inquire/inquire.jsp로 forward
    }
    
    /**
     * Q&A 상세정보를 조회하는 함수
     */     
    public ActionForward getInquireOrgView(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String target = "getInquireOrgView"; 
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO satiResultVO = new InquireResultVO();
		InquireResultVO cateResultVO1 = new InquireResultVO();//대분야정보

		InquireResultVO questionFileVO = new InquireResultVO(); //질문 첨부파일
		InquireResultVO answerFileVO = new InquireResultVO(); //답변첨부파일
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			searchVO.setFlag("V");
			resultVO = InquireBiz.getInquireView(searchVO);
			
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			satiResultVO = InquireBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		// 대분류 정보 조회
		try{
			InquireBiz InquireBiz = new InquireBiz();
			cateResultVO1 = InquireBiz.getCodeInfo();
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		

		try{
			InquireBiz InquireBiz = new InquireBiz();
			questionFileVO = InquireBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			answerFileVO = InquireBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		fm.setVo(resultVO.getVo());
		fm.setVoList(questionFileVO.getVoList());
		fm.setVoList1(answerFileVO.getVoList());
		fm.setSatiVO(satiResultVO.getSatiVO());
		fm.setVoList2(cateResultVO1.getVoList());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
       
        return mapping.findForward(target);
    }
    
    /**
     * Q&A 답변(부처담당자)등록하는 메소드
     */     
    public ActionForward getInquireOrgForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getInquireOrgForm";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO satiResultVO = new InquireResultVO();
		InquireResultVO cateResultVO1 = new InquireResultVO();//대분야정보
		InquireResultVO cateResultVO2 = new InquireResultVO();//소분야정보
		
		InquireResultVO questionFileVO = new InquireResultVO(); //질문 첨부파일
		InquireResultVO answerFileVO = new InquireResultVO(); //답변첨부파일
		InquireVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
        try{
			InquireBiz InquireBiz = new InquireBiz();
			searchVO.setFlag("A");
			resultVO = InquireBiz.getInquireView(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }	
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			questionFileVO = InquireBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			answerFileVO = InquireBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			satiResultVO = InquireBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		//대분류 정보 조회
		try{
			InquireBiz InquireBiz = new InquireBiz();
			cateResultVO1 = InquireBiz.getCodeInfo();
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		//소분류 정보 조회
		try{
			InquireBiz InquireBiz = new InquireBiz();
			cateResultVO2 = InquireBiz.getCodeInfo1();
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		fm.setVo(resultVO.getVo());
		fm.setSatiVO(satiResultVO.getSatiVO());

		fm.setVoList(questionFileVO.getVoList());
		fm.setVoList1(answerFileVO.getVoList());
		fm.setVoList2(cateResultVO1.getVoList());
        fm.setVoList3(cateResultVO2.getVoList());
				
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
        return mapping.findForward(target);   	
    	
    }
    
    /**
     * Q&A 답변등록하는 메소드
     */     
    public ActionForward getInquireOrgInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getInquireOrgInsert";
        
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		System.out.println("answer insert start~~~~~~~~~~");
		
		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO satiResultVO = new InquireResultVO();
		InquireResultVO questionFileVO = new InquireResultVO(); //질문 첨부파일
		InquireResultVO answerFileVO = new InquireResultVO(); //답변첨부파일
		InquireVO vo = fm.getVo();
		
		FileVO[] fileInfo = null;
	    AttachVO[] attachVO = null;
	    boolean success = false;
	    
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
            upload.setFilePath("rndcall/upload/qna/");
            
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
               InquireBiz InquireBiz = new InquireBiz();
                InquireBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
		
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			success = InquireBiz.getAnswerInsert(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
		try{
			InquireBiz InquireBiz = new InquireBiz();
			searchVO.setFlag("V");
			resultVO = InquireBiz.getInquireView(searchVO);
			
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			questionFileVO = InquireBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			answerFileVO = InquireBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		System.out.println("resultVO.getVo().getSeq()::"+resultVO.getVo().getSeq());
		try{
			InquireBiz InquireBiz = new InquireBiz();
			satiResultVO = InquireBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		fm.setVo(resultVO.getVo());
		fm.setVoList(questionFileVO.getVoList());
		fm.setVoList1(answerFileVO.getVoList());
		fm.setSatiVO(satiResultVO.getSatiVO());
		fm.setErrCd(String.valueOf(success));
		
		// SMS, EMAIL 발송
		SendSms(searchVO.getSeq(),"I", searchVO.getLoginId());
		SendEmail(searchVO.getSeq(),"I", searchVO.getLoginId());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
		
        return mapping.findForward(target);   	
    	
    }
    
    /**
     * Q&A 답변등록하는 메소드
     */     
    public ActionForward getInquireOrgUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getInquireOrgUpdate";
        
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		System.out.println("answer insert start~~~~~~~~~~");
		
		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO satiResultVO = new InquireResultVO();
		InquireResultVO questionFileVO = new InquireResultVO(); //질문 첨부파일
		InquireResultVO answerFileVO = new InquireResultVO(); //답변첨부파일
		InquireVO vo = fm.getVo();
		
		FileVO[] fileInfo = null;
	    AttachVO[] attachVO = null;
	    boolean success = false;
	    
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
            upload.setFilePath("rndcall/upload/qna/");
            
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
               InquireBiz InquireBiz = new InquireBiz();
                InquireBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
		
		//첨부파일 삭제유무에 따른 기존첨부파일 삭제
		try{
			InquireBiz InquireBiz = new InquireBiz();
			InquireBiz.getAnswerFileDelete(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			success = InquireBiz.getAnswerUpdate(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
		try{
			InquireBiz InquireBiz = new InquireBiz();
			searchVO.setFlag("V");
			resultVO = InquireBiz.getInquireView(searchVO);
			
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			questionFileVO = InquireBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			answerFileVO = InquireBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		System.out.println("resultVO.getVo().getSeq()::"+resultVO.getVo().getSeq());
		try{
			InquireBiz InquireBiz = new InquireBiz();
			satiResultVO = InquireBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		fm.setVo(resultVO.getVo());
		fm.setVoList(questionFileVO.getVoList());
		fm.setVoList1(answerFileVO.getVoList());
		fm.setSatiVO(satiResultVO.getSatiVO());
		fm.setErrCd(String.valueOf(success));
		
		// SMS, EMAIL 발송
		SendSms(searchVO.getSeq(),"U", searchVO.getLoginId() );
		SendEmail(searchVO.getSeq(),"U", searchVO.getLoginId());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
		
        return mapping.findForward(target);   	
    	
    }
    
    /**
     * Q&A SMS발송하는 메소드
     */     
    public void SendSms(String seq, String type, String login_id) throws Exception {
    	
    	// Default target to success    	
        String msg = "";
        
        try{
			InquireBiz InquireBiz = new InquireBiz();
			InquireBiz.sendSms(seq, type, login_id);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
		
        if(type.equals("I")){
        	msg = "답변이 등록";
        	
        }else{
        	msg = "답변이 수정";
        }
        System.out.println("seq::"+seq+"에 "+msg+" 되어 SMS를 발송하였습니다.");
    }
    
    /**
     * Q&A 답변등록을 타기관에게 지정하는 메소드
     */     
    public void SendEmail(String seq, String type, String login_id) throws Exception {
    	
    	 // Default target to success
        String target = "SendEmail";
        String msg = "";
        
        try{
			InquireBiz InquireBiz = new InquireBiz();
			InquireBiz.sendEmail(seq, type, login_id);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
		
        if(type.equals("I")){
        	msg = "답변이 등록";
        }else{
        	msg = "답변이 수정";
        }
        
        System.out.println("seq::"+seq+"에 "+msg+" 되어  메일을 발송하였습니다.");
    }
    
    /**
     * Q&A 엑셀다운로드
     */     
    public ActionForward getInquireExcelDown(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getInquireExcelDown";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO cateResultVO1 = new InquireResultVO();//대분야정보
		InquireVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			resultVO = InquireBiz.getInquireExcelDown(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}
		
		System.out.println("resultVO.getVoList().size()::"+resultVO.getVoList().size());
		fm.setVoList(resultVO.getVoList());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);    
        return mapping.findForward(target);
    }
    
    
    /**
     * Q&A 타기관 담당자 정보조회하는 함수
     */     
    public ActionForward getTrans_info(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String target = "getTrans_info"; 
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();	
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			resultVO = InquireBiz.getUserInfo(searchVO.getTrans_id());
			
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		fm.setLoginVO(resultVO.getLoginVO());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
       
        return mapping.findForward(target);
    }
    
    /**
     * Q&A 타기관지정 등록폼
    */     
    public ActionForward getTransChange(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getOrgTransForm";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();
		InquireResultVO resultVO1 = new InquireResultVO();
		InquireVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			resultVO = InquireBiz.getOrgTransList();
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			resultVO1 = InquireBiz.getUserInfo(searchVO.getTrans_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		searchVO.setTrans_email(resultVO1.getLoginVO().getEmail());
		searchVO.setTrans_phone(resultVO1.getLoginVO().getMobile());
		
		fm.setVoList(resultVO.getVoList());
		fm.setSearchVO(searchVO);
		fm.setErrCd("N");
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
        return mapping.findForward(target);
    }
    
    /**
     * Q&A 스크랩 등록하는 메소드
     */     
    public ActionForward getInquireScrap(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getInquireScrap";
        
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		InquireForm fm = (InquireForm) form;
		InquireSearchVO searchVO = fm.getSearchVO();
		InquireResultVO resultVO = new InquireResultVO();		
		//InquireResultVO satiResultVO = new InquireResultVO();
		//InquireResultVO cateResultVO1 = new InquireResultVO();//대분야정보
		//InquireResultVO questionFileVO = new InquireResultVO(); //질문 첨부파일
		//InquireResultVO answerFileVO = new InquireResultVO(); //답변첨부파일
		InquireVO vo = fm.getVo();
		System.out.println("searchVO전부"+fm.getSearchVO());
	    //boolean success = false;
	    
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		try{
			InquireBiz InquireBiz = new InquireBiz();
			resultVO = InquireBiz.getInquireScrap(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
        //vo.setMem_table_nm("ok"); //
        System.out.println("getMem_table_nm() :=========== "+ resultVO.getVo().getMem_table_nm());
        if(resultVO.getVo().getMem_table_nm().equals("pass")){
            target = "update_result_iframe_ok";
        }else{
        	target = "update_result_iframe_er";
        } 
        return mapping.findForward(target); 
		
    }
    public ActionForward update_result_iframe_ok(ActionMapping mapping, ActionForm form, HttpServletRequest request
            , HttpServletResponse response) throws Exception {
		String target = "update_result_iframe_ok";
//		Form fm = (Form) form;
//		
//		fm.setMenuNM(currentMenuVO.getMenuNM());
//		fm.setTopMenuSN(currentMenuVO.getTopMenuSN());
//		fm.setRole(Util.getRoleBoard(request, currentMenuVO));
     	logger.info("target ---: " + target);
		return mapping.findForward(target);


    } 
    public ActionForward update_result_iframe_er(ActionMapping mapping, ActionForm form, HttpServletRequest request
            , HttpServletResponse response) throws Exception {
		String target = "update_result_iframe_er";
//		Form fm = (Form) form;
//		
//		fm.setMenuNM(currentMenuVO.getMenuNM());
//		fm.setTopMenuSN(currentMenuVO.getTopMenuSN());
//		fm.setRole(Util.getRoleBoard(request, currentMenuVO));
		logger.info("target ---: " + target);
		return mapping.findForward(target);


    }  
    
}