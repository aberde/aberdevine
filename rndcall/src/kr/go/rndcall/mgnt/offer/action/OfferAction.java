package kr.go.rndcall.mgnt.offer.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.offer.dao.OfferDAO;
import kr.go.rndcall.mgnt.offer.vo.OfferVO;
import kr.go.rndcall.mgnt.offer.vo.SatiVO;
import kr.go.rndcall.mgnt.offer.biz.OfferBiz;
import kr.go.rndcall.mgnt.offer.form.OfferForm;
import kr.go.rndcall.mgnt.offer.vo.OfferResultVO;
import kr.go.rndcall.mgnt.offer.vo.OfferSearchVO;
import kr.go.rndcall.mgnt.common.AttachVO;
import kr.go.rndcall.mgnt.common.FileUpload;
import kr.go.rndcall.mgnt.common.FileUploadData;
import kr.go.rndcall.mgnt.common.FileVO;
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.inquire.biz.InquireBiz;
import kr.go.rndcall.mgnt.inquire.form.InquireForm;
import kr.go.rndcall.mgnt.inquire.vo.InquireResultVO;
import kr.go.rndcall.mgnt.inquire.vo.InquireSearchVO;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class OfferAction extends DispatchAction {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public OfferAction() {
        super();
    }

    /**
     * 제안하기 등록/수정폼을 생성하는 메소드
     */     
    public ActionForward offerInsertForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
        String target = "offerInsertForm";   
        
		ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		OfferForm fm = (OfferForm) form;
		OfferSearchVO searchVO = fm.getSearchVO();
		OfferResultVO resultVO = new OfferResultVO();
		//searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
		//searchVO.setName(Util.getName(request)); //로그인 이름정보		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		return mapping.findForward(target);
    }
/*		searchVO.setLoginId("admin"); //로그인 아이디정보
		searchVO.setName("관리자"); //로그인 이름정보
        
		try {
			OfferBiz biz = new OfferBiz();
            
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
    }*/

    //제안하기 등록하는 메소드
    public ActionForward offerInsert(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String target = "offerInsert";
        ActionErrors errors = null;
        String data_id = "";
        String question_id = ""; // question 아이디 
        String answer_id = ""; //answer 아이디
        int result = 0;

        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
                    .getAttribute("org.apache.struts.action.ERROR");
        }
        
        OfferForm fm = (OfferForm) form;
        OfferSearchVO searchVO = fm.getSearchVO();
        OfferResultVO resultVO = new OfferResultVO();
        OfferVO vo = fm.getVo();
        
		FileVO[] fileInfo = null;
	    AttachVO[] attachVO = null;
        
	    if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
			vo.setReg_id(Util.getLoginId(request));
		}
	    
	    if(searchVO.getType().equals("03") && Util.getLoginUserVO(request) != null ){
	    	target = "myPageMethod";
	    	searchVO.setMenu_sn("03");
	    }
        
        /*
         * RNDCALL_FILE_ID_SEQ 시퀀스 정보조회
         * RNDCALL_BOARD_QUESTION 테이블 SEQ(시퀀스)정보조회
         * RNDCALL_BOARD_ANSWER 테이블 SEQ(시퀀스)정보조회
         */     
        try{
            OfferDAO dao =  new OfferDAO();
            //data_id = dao.getFileIdSeq();
            question_id = dao.questionID();
            answer_id = dao.answerID();
            
            vo.setData_id(data_id);
            vo.setQuestion_id(question_id);
            vo.setAnswer_id(answer_id);
            vo.setInsert_type("WEB");
            vo.setBoard_type("OFFER");
            vo.setCell_number(vo.getCell_number());
            
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
        /*
         * 첨부파일 업로드 
         */ 
		try {
            // attach
			 System.out.println("answer insert start3333333333333~~~~~~~~~~");
            FileUpload upload = new FileUpload(mapping, form, request);
            upload.setFilePath("rndcall/upload/offer/");
            
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
               OfferBiz OfferBiz = new OfferBiz();
               OfferBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
        
		//제안하기 등록
        try {
           
            OfferBiz OfferBiz = new OfferBiz();
                resultVO = OfferBiz.offerInsert(vo, searchVO);
            
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }
        
    	try{
    		OfferBiz biz = new OfferBiz();
    		resultVO = biz.offerList(searchVO);
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
    	fm.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	fm.setVoList(resultVO.getVoList());
		fm.setVoList(resultVO.getVoList());
		fm.setTotRowCount(resultVO.getTotRowCount());
        
        if (errors.isEmpty()) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.getList.success"));
        }
        saveErrors(request, errors);

        logger.debug("target: " + target);
        return mapping.findForward(target);
    }
    //제안하기 리스트조회 
    public ActionForward offerList(ActionMapping mapping, ActionForm form, HttpServletRequest request
    		, HttpServletResponse response) throws Exception {
    	
    	String target = "offerList";   
    	
    	ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	OfferForm fm = (OfferForm) form;
    	OfferSearchVO searchVO = fm.getSearchVO();
    	OfferResultVO resultVO = new OfferResultVO();
    	OfferVO vo = fm.getVo();
    	
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		searchVO.setLoginId("admin"); //로그인 아이디정보
		searchVO.setName("관리자"); //로그인 이름정보
		searchVO.setBoard_type("OFFER"); //임시로 사용
		
    	searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	try{
    		OfferBiz biz = new OfferBiz();
    		resultVO = biz.offerList(searchVO);
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
    //제안하기 상세정보 조회
    public ActionForward offerDetailView(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String target = "offerDetailView"; 
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		OfferForm fm = (OfferForm) form;
		OfferSearchVO searchVO = fm.getSearchVO();
		OfferResultVO resultVO = new OfferResultVO();
		OfferResultVO satiResultVO = new OfferResultVO();
		
		OfferResultVO questionFileVO = new OfferResultVO(); //질문 첨부파일
		OfferResultVO answerFileVO = new OfferResultVO(); //답변첨부파일

		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
						
		//searchVO.setLoginId("admin"); //로그인 아이디정보
		//searchVO.setName("관리자"); //로그인 이름정보
		
		try{
			OfferBiz OfferBiz = new OfferBiz();
			searchVO.setFlag("V");
			resultVO = OfferBiz.offerDetailView(searchVO);
			
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		System.out.println("제안하기 시퀀스 넘버?:"+resultVO.getVo().getSeq());
		System.out.println("제안하기 파일 ID?"+resultVO.getVo().getFile_id());

		try{
			OfferBiz OfferBiz = new OfferBiz();
			questionFileVO = OfferBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }

		try{
			OfferBiz OfferBiz = new OfferBiz();
			answerFileVO = OfferBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
			logger.debug("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}
		
		System.out.println("resultVO.getVo().getSeq()::"+resultVO.getVo().getSeq());
		try{
			OfferBiz OfferBiz = new OfferBiz();
			satiResultVO = OfferBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		fm.setVo(resultVO.getVo());
		fm.setVoList(questionFileVO.getVoList());	//질문첨부파일정보
		fm.setVoList1(answerFileVO.getVoList());	//답변첨부파일
		fm.setSatiVO(satiResultVO.getSatiVO());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
       
        return mapping.findForward(target);
    }
    //제안하기 만족도 평가하는 메소드
    public ActionForward offerSatiInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "offerSatiInsert";
        
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		OfferForm fm = (OfferForm) form;
		OfferSearchVO searchVO = fm.getSearchVO();
		OfferResultVO resultVO = new OfferResultVO();
		OfferResultVO resultFileVO = new OfferResultVO();
		OfferResultVO satiResultVO = new OfferResultVO();
		SatiVO satiVO = new SatiVO();
		OfferVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보	
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		//만족도 평가 등록 
		try{
			OfferBiz OfferBiz = new OfferBiz();
			satiVO.setReg_ip(request.getRemoteAddr());
			
			OfferBiz.offerSatiInsert(vo, searchVO, satiVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
		// 만족도 평가 등록후 상세화면 조회
		try{
			OfferBiz OfferBiz = new OfferBiz();
			resultVO = OfferBiz.offerDetailView(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			OfferBiz OfferBiz = new OfferBiz();
			resultFileVO = OfferBiz.getFileInfo(resultVO.getVo().getFile_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			OfferBiz OfferBiz = new OfferBiz();
			satiResultVO = OfferBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
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
    
    //자주하는질문 답변등록하는 메소드
    public ActionForward offerAnswerInsertForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "offerAnswerInsertForm";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		OfferForm fm = (OfferForm) form;
		OfferSearchVO searchVO = fm.getSearchVO();
		OfferResultVO resultVO = new OfferResultVO();
		OfferResultVO questionFileVO = new OfferResultVO(); //질문 첨부파일
		OfferResultVO answerFileVO = new OfferResultVO(); //답변첨부파일
		OfferResultVO satiResultVO = new OfferResultVO();
		OfferResultVO cateResultVO1 = new OfferResultVO();//대분야정보
		OfferResultVO cateResultVO2 = new OfferResultVO();//소분야정보
		OfferVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
        try{
        	OfferBiz OfferBiz = new OfferBiz();
			searchVO.setFlag("U");
			resultVO = OfferBiz.offerDetailView(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }	
		try{
			OfferBiz OfferBiz = new OfferBiz();
			questionFileVO = OfferBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		try{
			OfferBiz OfferBiz = new OfferBiz();
			answerFileVO = OfferBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
			logger.debug("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}
		
		try{
			OfferBiz OfferBiz = new OfferBiz();
			satiResultVO = OfferBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		fm.setVo(resultVO.getVo());
		fm.setVoList(questionFileVO.getVoList());	//질문첨부파일정보
		fm.setVoList1(answerFileVO.getVoList());	//답변첨부파일
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
    //제안하기 답변등록하는 메소드
    public ActionForward offerAnswerInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "offerAnswerInsert";
        
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		System.out.println("answer insert start~~~~~~~~~~");
		
		OfferForm fm = (OfferForm) form;
		OfferSearchVO searchVO = fm.getSearchVO();
		OfferResultVO resultVO = new OfferResultVO();
		OfferResultVO questionFileVO = new OfferResultVO(); //질문 첨부파일
		OfferResultVO answerFileVO = new OfferResultVO(); //답변첨부파일
		OfferResultVO satiResultVO = new OfferResultVO();
		OfferVO vo = fm.getVo();
		
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
            	OfferBiz OfferBiz = new OfferBiz();
            	OfferBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
		
		
		try{
			OfferBiz OfferBiz = new OfferBiz();
			OfferBiz.offerAnswerInsert(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
		try{
			OfferBiz OfferBiz = new OfferBiz();
			searchVO.setFlag("V");
			resultVO = OfferBiz.offerDetailView(searchVO);
			
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		try{
			OfferBiz OfferBiz = new OfferBiz();
			questionFileVO = OfferBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		try{
			OfferBiz OfferBiz = new OfferBiz();
			answerFileVO = OfferBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
			logger.debug("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}
		
		System.out.println("resultVO.getVo().getSeq()::"+resultVO.getVo().getSeq());
		try{
			OfferBiz OfferBiz = new OfferBiz();
			satiResultVO = OfferBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		fm.setVo(resultVO.getVo());
		fm.setVoList(questionFileVO.getVoList());	//질문첨부파일정보
		fm.setVoList1(answerFileVO.getVoList());	//답변첨부파일
		fm.setSatiVO(satiResultVO.getSatiVO());
		
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
    //제안하기 답변수정하는 메소드
    public ActionForward offerAnswerUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "offerAnswerUpdate";
        
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		System.out.println("answer insert start~~~~~~~~~~");
		
		OfferForm fm = (OfferForm) form;
		OfferSearchVO searchVO = fm.getSearchVO();
		OfferResultVO resultVO = new OfferResultVO();
		OfferResultVO questionFileVO = new OfferResultVO(); //질문 첨부파일
		OfferResultVO answerFileVO = new OfferResultVO(); //답변첨부파일
		OfferResultVO satiResultVO = new OfferResultVO();
		OfferVO vo = fm.getVo();
		
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
            upload.setFilePath("rndcall/upload/offer/");
            
           // System.out.println("answer insert start4444444444444~~~~~~~~~~"+Util.getLoginUserVO(request).getUsername());
            upload.setUsername(searchVO.getLoginId());
            System.out.println("첨부파일 업로드");
            
	        try {
	            upload.service();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        fileInfo = upload.getFileInfo();
	        System.out.println("첨부파일정보는?"+fileInfo);
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
            	OfferBiz OfferBiz = new OfferBiz();
            	OfferBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
		
		//첨부파일 삭제유무에 따른 기존첨부파일 삭제
		try{
			OfferBiz OfferBiz = new OfferBiz();
			OfferBiz.getAnswerFileDelete(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		
		try{
			OfferBiz OfferBiz = new OfferBiz();
			OfferBiz.offerAnswerUpdate(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
		try{
			OfferBiz OfferBiz = new OfferBiz();
			searchVO.setFlag("V");
			resultVO = OfferBiz.offerDetailView(searchVO);
			
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		try{
			OfferBiz OfferBiz = new OfferBiz();
			questionFileVO = OfferBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		try{
			OfferBiz OfferBiz = new OfferBiz();
			answerFileVO = OfferBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
			logger.debug("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}
		
		System.out.println("resultVO.getVo().getSeq()::"+resultVO.getVo().getSeq());
		try{
			OfferBiz OfferBiz = new OfferBiz();
			satiResultVO = OfferBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		fm.setVo(resultVO.getVo());
		fm.setVoList(questionFileVO.getVoList());	//질문첨부파일정보
		fm.setVoList1(answerFileVO.getVoList());	//답변첨부파일
		fm.setSatiVO(satiResultVO.getSatiVO());
		
		// SMS, EMAIL 발송
		SendSms(searchVO.getSeq(),"U", searchVO.getLoginId());
		SendEmail(searchVO.getSeq(),"U", searchVO.getLoginId());
		
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
		
        return mapping.findForward(target);   	
    	
    }
    //제안하기 질문 수정폼
    public ActionForward offerUpdateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "offerUpdateForm";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		OfferForm fm = (OfferForm) form;
		OfferSearchVO searchVO = fm.getSearchVO();
		OfferResultVO resultVO = new OfferResultVO();
		OfferResultVO resultFileVO = new OfferResultVO(); //질문 첨부파일
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
        try{
        	OfferBiz OfferBiz = new OfferBiz();
			searchVO.setFlag("U");
			resultVO = OfferBiz.offerDetailView(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }	
		try{
			OfferBiz OfferBiz = new OfferBiz();
			resultFileVO = OfferBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		fm.setVo(resultVO.getVo());
		fm.setVoList(resultFileVO.getVoList());	//질문첨부파일정보
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
        return mapping.findForward(target);   	
    	
    }
	//제안하기 질의내용 수정
    public ActionForward offerUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "offerUpdate";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		OfferForm fm = (OfferForm) form;
		OfferSearchVO searchVO = fm.getSearchVO();
		OfferResultVO resultVO = new OfferResultVO();
		OfferResultVO questionFileVO = new OfferResultVO(); //질문 첨부파일
		OfferResultVO answerFileVO = new OfferResultVO(); //답변첨부파일
		OfferResultVO satiResultVO = new OfferResultVO();
		OfferVO vo = fm.getVo();
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
            	OfferBiz OfferBiz = new OfferBiz();
            	OfferBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
		// 첨부파일 삭제유무에 따른 기존첨부파일 삭제
		try{
			OfferBiz OfferBiz = new OfferBiz();
			OfferBiz.getQuestionFileDelete(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			OfferBiz OfferBiz = new OfferBiz();
			OfferBiz.offerUpdate(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			OfferBiz OfferBiz = new OfferBiz();
			searchVO.setFlag("V");
			resultVO = OfferBiz.offerDetailView(searchVO);
			
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		try{
			OfferBiz OfferBiz = new OfferBiz();
			questionFileVO = OfferBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
			System.out.println("offer파일id"+OfferBiz.getFileInfo(resultVO.getVo().getQuestion_file_id()));
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		try{
			OfferBiz OfferBiz = new OfferBiz();
			answerFileVO = OfferBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
			logger.debug("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}
		
		try{
			OfferBiz OfferBiz = new OfferBiz();
			satiResultVO = OfferBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		fm.setVo(resultVO.getVo());
		fm.setVoList(questionFileVO.getVoList());	//질문첨부파일정보
		fm.setVoList1(answerFileVO.getVoList());	//답변첨부파일
		fm.setSatiVO(satiResultVO.getSatiVO());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
        return mapping.findForward(target);   	
    	
    }
    //제안하기 삭제
    public ActionForward offerDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	String target = "offerDelete";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		OfferForm fm = (OfferForm) form;
		OfferSearchVO searchVO = fm.getSearchVO();
		OfferResultVO resultVO = new OfferResultVO();
		OfferResultVO questionFileVO = new OfferResultVO(); //질문 첨부파일
		OfferResultVO answerFileVO = new OfferResultVO(); //답변첨부파일
		OfferResultVO satiResultVO = new OfferResultVO();
		OfferVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}

		searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		
		try{
			OfferBiz OfferBiz = new OfferBiz();
			OfferBiz.offerDelete(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
				
    	try{
    		OfferBiz biz = new OfferBiz();
    		resultVO = biz.offerList(searchVO);
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
    //제안하기 SMS발송
    public void SendSms(String seq, String type, String login_id) throws Exception {
    	
    	// Default target to success    	
        String msg = "";
        
        try{
			OfferBiz OfferBiz = new OfferBiz();
			OfferBiz.sendSms(seq, type, login_id);
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
    //제안하기 E-Mail발송
    public void SendEmail(String seq, String type, String login_id) throws Exception {
    	
    	 // Default target to success
        String target = "SendEmail";
        String msg = "";
        
        try{
        	OfferBiz OfferBiz = new OfferBiz();
        	OfferBiz.sendEmail(seq, type, login_id);
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
    

    //제안하기 리스트조회 
    public ActionForward adminOfferList(ActionMapping mapping, ActionForm form, HttpServletRequest request
            , HttpServletResponse response) throws Exception {
        
        String target = "adminOfferList";   
        
        ActionErrors errors = null;
        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
            .getAttribute("org.apache.struts.action.ERROR");
        }
        
        OfferForm fm = (OfferForm) form;
        OfferSearchVO searchVO = fm.getSearchVO();
        OfferResultVO resultVO = new OfferResultVO();
        OfferVO vo = fm.getVo();
        
        if(Util.getLoginUserVO(request) != null){
            searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
            searchVO.setName(Util.getName(request)); //로그인 이름정보
            searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
        }
        
        searchVO.setLoginId("admin"); //로그인 아이디정보
        searchVO.setName("관리자"); //로그인 이름정보
        searchVO.setBoard_type("OFFER"); //임시로 사용
        
        searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
        try{
            OfferBiz biz = new OfferBiz();
            resultVO = biz.offerList(searchVO);
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
    
    public ActionForward adminOfferDetailView(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        String target = "adminOfferDetailView";
        ActionErrors errors = null;
        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
                    .getAttribute("org.apache.struts.action.ERROR");
        }

        OfferForm fm = (OfferForm) form;
        OfferSearchVO searchVO = fm.getSearchVO();
        OfferResultVO resultVO = new OfferResultVO();
        OfferResultVO satiResultVO = new OfferResultVO();

        OfferResultVO questionFileVO = new OfferResultVO(); // 질문 첨부파일
        OfferResultVO answerFileVO = new OfferResultVO(); // 답변첨부파일

        if (Util.getLoginUserVO(request) != null) {
            searchVO.setLoginId(Util.getLoginId(request)); // 로그인 아이디정보
            searchVO.setName(Util.getName(request)); // 로그인 이름정보
            searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
        }

        // searchVO.setLoginId("admin"); //로그인 아이디정보
        // searchVO.setName("관리자"); //로그인 이름정보

        try {
            OfferBiz OfferBiz = new OfferBiz();
            searchVO.setFlag("V");
            resultVO = OfferBiz.offerDetailView(searchVO);

        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }
        System.out.println("제안하기 시퀀스 넘버?:" + resultVO.getVo().getSeq());
        System.out.println("제안하기 파일 ID?" + resultVO.getVo().getFile_id());

        try {
            OfferBiz OfferBiz = new OfferBiz();
            questionFileVO = OfferBiz.getFileInfo(resultVO.getVo()
                    .getQuestion_file_id());
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }

        try {
            OfferBiz OfferBiz = new OfferBiz();
            answerFileVO = OfferBiz.getFileInfo(resultVO.getVo()
                    .getAnswer_file_id());
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }

        System.out.println("resultVO.getVo().getSeq()::"
                + resultVO.getVo().getSeq());
        try {
            OfferBiz OfferBiz = new OfferBiz();
            satiResultVO = OfferBiz.getSatiInfo(resultVO.getVo().getSeq(),
                    searchVO.getLoginId());
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }

        fm.setVo(resultVO.getVo());
        fm.setVoList(questionFileVO.getVoList()); // 질문첨부파일정보
        fm.setVoList1(answerFileVO.getVoList()); // 답변첨부파일
        fm.setSatiVO(satiResultVO.getSatiVO());

        if (errors.isEmpty()) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.getList.success"));
        }
        saveErrors(request, errors);

        return mapping.findForward(target);
    }
    
    public ActionForward adminOfferDelete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String target = "adminOfferDelete";
        ActionErrors errors = null;
        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
                    .getAttribute("org.apache.struts.action.ERROR");
        }

        OfferForm fm = (OfferForm) form;
        OfferSearchVO searchVO = fm.getSearchVO();
        OfferResultVO resultVO = new OfferResultVO();
        OfferResultVO questionFileVO = new OfferResultVO(); // 질문 첨부파일
        OfferResultVO answerFileVO = new OfferResultVO(); // 답변첨부파일
        OfferResultVO satiResultVO = new OfferResultVO();
        OfferVO vo = fm.getVo();

        if (Util.getLoginUserVO(request) != null) {
            searchVO.setLoginId(Util.getLoginId(request)); // 로그인 아이디정보
            searchVO.setName(Util.getName(request)); // 로그인 이름정보
            searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
        }

        searchVO.setPagerOffset(Util.replaceNull(
                request.getParameter("pager.offset"), "0"));

        try {
            OfferBiz OfferBiz = new OfferBiz();
            OfferBiz.offerDelete(searchVO);
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }

        try {
            OfferBiz biz = new OfferBiz();
            resultVO = biz.offerList(searchVO);
            // seq = FaqDAO.getSeq();
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }

        fm.setPagerOffset(Util.replaceNull(
                request.getParameter("pager.offset"), "0"));
        fm.setVoList(resultVO.getVoList());
        fm.setTotRowCount(resultVO.getTotRowCount());
        fm.setVo(resultVO.getVo());
        return mapping.findForward(target);

    }
    
    //자주하는질문 답변등록하는 메소드
    public ActionForward adminOfferAnswerInsertForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
        
         // Default target to success
        String target = "adminOfferAnswerInsertForm";
        ActionErrors errors = null;
        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
                    .getAttribute("org.apache.struts.action.ERROR");
        }

        OfferForm fm = (OfferForm) form;
        OfferSearchVO searchVO = fm.getSearchVO();
        OfferResultVO resultVO = new OfferResultVO();
        OfferResultVO questionFileVO = new OfferResultVO(); //질문 첨부파일
        OfferResultVO answerFileVO = new OfferResultVO(); //답변첨부파일
        OfferResultVO satiResultVO = new OfferResultVO();
        OfferResultVO cateResultVO1 = new OfferResultVO();//대분야정보
        OfferResultVO cateResultVO2 = new OfferResultVO();//소분야정보
        OfferVO vo = fm.getVo();
        
        if(Util.getLoginUserVO(request) != null){
            searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
            searchVO.setName(Util.getName(request)); //로그인 이름정보
            searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
        }
        
        try{
            OfferBiz OfferBiz = new OfferBiz();
            searchVO.setFlag("U");
            resultVO = OfferBiz.offerDetailView(searchVO);
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }   
        try{
            OfferBiz OfferBiz = new OfferBiz();
            questionFileVO = OfferBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        try{
            OfferBiz OfferBiz = new OfferBiz();
            answerFileVO = OfferBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
        try{
            OfferBiz OfferBiz = new OfferBiz();
            satiResultVO = OfferBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
        fm.setVo(resultVO.getVo());
        fm.setVoList(questionFileVO.getVoList());   //질문첨부파일정보
        fm.setVoList1(answerFileVO.getVoList());    //답변첨부파일
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
    
    public ActionForward adminOfferAnswerInsert(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // Default target to success
        String target = "adminOfferAnswerInsert";

        ActionErrors errors = null;
        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
                    .getAttribute("org.apache.struts.action.ERROR");
        }

        System.out.println("answer insert start~~~~~~~~~~");

        OfferForm fm = (OfferForm) form;
        OfferSearchVO searchVO = fm.getSearchVO();
        OfferResultVO resultVO = new OfferResultVO();
        OfferResultVO questionFileVO = new OfferResultVO(); // 질문 첨부파일
        OfferResultVO answerFileVO = new OfferResultVO(); // 답변첨부파일
        OfferResultVO satiResultVO = new OfferResultVO();
        OfferVO vo = fm.getVo();

        FileVO[] fileInfo = null;
        AttachVO[] attachVO = null;

        if (Util.getLoginUserVO(request) != null) {
            searchVO.setLoginId(Util.getLoginId(request)); // 로그인 아이디정보
            searchVO.setName(Util.getName(request)); // 로그인 이름정보
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
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }

        if (fileInfo.length != 0) {
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
                OfferBiz OfferBiz = new OfferBiz();
                OfferBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                        "errors.database.error", e.getMessage()));
            }
        }

        try {
            OfferBiz OfferBiz = new OfferBiz();
            OfferBiz.offerAnswerInsert(vo, searchVO);
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }

        try {
            OfferBiz OfferBiz = new OfferBiz();
            searchVO.setFlag("V");
            resultVO = OfferBiz.offerDetailView(searchVO);

        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }
        try {
            OfferBiz OfferBiz = new OfferBiz();
            questionFileVO = OfferBiz.getFileInfo(resultVO.getVo()
                    .getQuestion_file_id());
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }
        try {
            OfferBiz OfferBiz = new OfferBiz();
            answerFileVO = OfferBiz.getFileInfo(resultVO.getVo()
                    .getAnswer_file_id());
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }

        System.out.println("resultVO.getVo().getSeq()::"
                + resultVO.getVo().getSeq());
        try {
            OfferBiz OfferBiz = new OfferBiz();
            satiResultVO = OfferBiz.getSatiInfo(resultVO.getVo().getSeq(),
                    searchVO.getLoginId());
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }
        request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
        fm.setVo(resultVO.getVo());
        fm.setVoList(questionFileVO.getVoList()); // 질문첨부파일정보
        fm.setVoList1(answerFileVO.getVoList()); // 답변첨부파일
        fm.setSatiVO(satiResultVO.getSatiVO());

        // SMS, EMAIL 발송
        SendSms(searchVO.getSeq(), "I", searchVO.getLoginId());
        SendEmail(searchVO.getSeq(), "I", searchVO.getLoginId());

        if (errors.isEmpty()) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.getList.success"));
        }
        saveErrors(request, errors);

        return mapping.findForward(target);

    }

    // 제안하기 답변수정하는 메소드
    public ActionForward adminOfferAnswerUpdate(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // Default target to success
        String target = "adminOfferAnswerUpdate";

        ActionErrors errors = null;
        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
                    .getAttribute("org.apache.struts.action.ERROR");
        }

        System.out.println("answer insert start~~~~~~~~~~");

        OfferForm fm = (OfferForm) form;
        OfferSearchVO searchVO = fm.getSearchVO();
        OfferResultVO resultVO = new OfferResultVO();
        OfferResultVO questionFileVO = new OfferResultVO(); // 질문 첨부파일
        OfferResultVO answerFileVO = new OfferResultVO(); // 답변첨부파일
        OfferResultVO satiResultVO = new OfferResultVO();
        OfferVO vo = fm.getVo();

        FileVO[] fileInfo = null;
        AttachVO[] attachVO = null;

        if (Util.getLoginUserVO(request) != null) {
            searchVO.setLoginId(Util.getLoginId(request)); // 로그인 아이디정보
            searchVO.setName(Util.getName(request)); // 로그인 이름정보
            searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
            vo.setReg_id(Util.getLoginId(request));
        }

        System.out.println("answer insert start ::" + searchVO.getLoginId());
        System.out.println("answer insert start22222222222222~~~~~~~~~~");

        try {
            // attach
            System.out.println("answer insert start3333333333333~~~~~~~~~~");
            FileUpload upload = new FileUpload(mapping, form, request);
            upload.setFilePath("rndcall/upload/offer/");

            // System.out.println("answer insert start4444444444444~~~~~~~~~~"+Util.getLoginUserVO(request).getUsername());
            upload.setUsername(searchVO.getLoginId());
            System.out.println("첨부파일 업로드");

            try {
                upload.service();
            } catch (Exception e) {
                e.printStackTrace();
            }
            fileInfo = upload.getFileInfo();
            System.out.println("첨부파일정보는?" + fileInfo);
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }

        if (fileInfo.length != 0) {
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
                OfferBiz OfferBiz = new OfferBiz();
                OfferBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                        "errors.database.error", e.getMessage()));
            }
        }

        // 첨부파일 삭제유무에 따른 기존첨부파일 삭제
        try {
            OfferBiz OfferBiz = new OfferBiz();
            OfferBiz.getAnswerFileDelete(vo, searchVO);
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }

        try {
            OfferBiz OfferBiz = new OfferBiz();
            OfferBiz.offerAnswerUpdate(vo, searchVO);
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }

        try {
            OfferBiz OfferBiz = new OfferBiz();
            searchVO.setFlag("V");
            resultVO = OfferBiz.offerDetailView(searchVO);

        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }
        try {
            OfferBiz OfferBiz = new OfferBiz();
            questionFileVO = OfferBiz.getFileInfo(resultVO.getVo()
                    .getQuestion_file_id());
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }
        try {
            OfferBiz OfferBiz = new OfferBiz();
            answerFileVO = OfferBiz.getFileInfo(resultVO.getVo()
                    .getAnswer_file_id());
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }

        System.out.println("resultVO.getVo().getSeq()::"
                + resultVO.getVo().getSeq());
        try {
            OfferBiz OfferBiz = new OfferBiz();
            satiResultVO = OfferBiz.getSatiInfo(resultVO.getVo().getSeq(),
                    searchVO.getLoginId());
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }
        request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
        fm.setVo(resultVO.getVo());
        fm.setVoList(questionFileVO.getVoList()); // 질문첨부파일정보
        fm.setVoList1(answerFileVO.getVoList()); // 답변첨부파일
        fm.setSatiVO(satiResultVO.getSatiVO());

        // SMS, EMAIL 발송
        SendSms(searchVO.getSeq(), "U", searchVO.getLoginId());
        SendEmail(searchVO.getSeq(), "U", searchVO.getLoginId());

        if (errors.isEmpty()) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.getList.success"));
        }
        saveErrors(request, errors);

        return mapping.findForward(target);

    }
    
    /**
     * R&D 신문고 상세정보를 프린트하기위해 불러오는 팝업창
     */     
    public ActionForward getOfferViewPop(ActionMapping mapping, ActionForm form, HttpServletRequest request
            , HttpServletResponse response) throws Exception {      
        
        String target = "getOfferViewPop"; 
        ActionErrors errors = null;
        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
                    .getAttribute("org.apache.struts.action.ERROR");
        }

        OfferForm fm = (OfferForm) form;
        OfferSearchVO searchVO = fm.getSearchVO();
        OfferResultVO resultVO = new OfferResultVO();
        OfferResultVO satiResultVO = new OfferResultVO();

        OfferResultVO questionFileVO = new OfferResultVO(); // 질문 첨부파일
        OfferResultVO answerFileVO = new OfferResultVO(); // 답변첨부파일

        if (Util.getLoginUserVO(request) != null) {
            searchVO.setLoginId(Util.getLoginId(request)); // 로그인 아이디정보
            searchVO.setName(Util.getName(request)); // 로그인 이름정보
            searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
        }

        // searchVO.setLoginId("admin"); //로그인 아이디정보
        // searchVO.setName("관리자"); //로그인 이름정보

        try {
            OfferBiz OfferBiz = new OfferBiz();
            searchVO.setFlag("V");
            resultVO = OfferBiz.offerDetailView(searchVO);

        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }
        System.out.println("제안하기 시퀀스 넘버?:" + resultVO.getVo().getSeq());
        System.out.println("제안하기 파일 ID?" + resultVO.getVo().getFile_id());

        try {
            OfferBiz OfferBiz = new OfferBiz();
            questionFileVO = OfferBiz.getFileInfo(resultVO.getVo()
                    .getQuestion_file_id());
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }

        try {
            OfferBiz OfferBiz = new OfferBiz();
            answerFileVO = OfferBiz.getFileInfo(resultVO.getVo()
                    .getAnswer_file_id());
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }

        System.out.println("resultVO.getVo().getSeq()::"
                + resultVO.getVo().getSeq());
        try {
            OfferBiz OfferBiz = new OfferBiz();
            satiResultVO = OfferBiz.getSatiInfo(resultVO.getVo().getSeq(),
                    searchVO.getLoginId());
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }

        fm.setVo(resultVO.getVo());
        fm.setVoList(questionFileVO.getVoList()); // 질문첨부파일정보
        fm.setVoList1(answerFileVO.getVoList()); // 답변첨부파일
        fm.setSatiVO(satiResultVO.getSatiVO());

        if (errors.isEmpty()) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.getList.success"));
        }
        saveErrors(request, errors);

        return mapping.findForward(target);
    }
}
