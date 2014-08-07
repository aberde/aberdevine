package kr.go.rndcall.mgnt.data.action;

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


import kr.go.rndcall.mgnt.common.AttachVO;
import kr.go.rndcall.mgnt.common.FileUpload;
import kr.go.rndcall.mgnt.common.FileVO;
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.data.biz.DataBiz;
import kr.go.rndcall.mgnt.data.form.DataForm;
import kr.go.rndcall.mgnt.data.vo.DataResultVO;
import kr.go.rndcall.mgnt.data.vo.DataSearchVO;
import kr.go.rndcall.mgnt.data.dao.DataDAO;
import kr.go.rndcall.mgnt.data.vo.DataVO;
import kr.go.rndcall.mgnt.notice.biz.NoticeBiz;

public class DataAction extends DispatchAction {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public DataAction() {
        super();
    }

    //자료실 등록폼           
    public ActionForward dataInsertForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
        String target = "dataInsertForm";   
        
		ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		DataForm fm = (DataForm) form;
		DataSearchVO searchVO = fm.getSearchVO();
		DataResultVO resultVO = new DataResultVO();
		//searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
		//searchVO.setName(Util.getName(request)); //로그인 이름정보		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		return mapping.findForward(target);
    }
    
    //자료실을  등록하는 메소드
    public ActionForward dataInsert(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String target = "dataInsert";
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
        
        DataForm fm = (DataForm) form;
        DataSearchVO searchVO = fm.getSearchVO();
        DataResultVO resultVO = new DataResultVO();
        DataVO vo = fm.getVo();
        
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
        	DataDAO dao =  new DataDAO();
            question_id = dao.questionID();
            
            vo.setData_id(data_id);
            vo.setQuestion_id(question_id);
            vo.setInsert_type("WEB");
            
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
               DataBiz DataBiz = new DataBiz();
               DataBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
        
		//자료실 등록 부분
		try {
			DataBiz DataBiz = new DataBiz();
			searchVO.setFlag("V");
			resultVO = DataBiz.dataInsert(vo, searchVO);
			request.setAttribute("result", new Integer(1));
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.database.error", e.getMessage()));
		}
        //공지사항 리스트를 가져온다.
		searchVO.setBoard_type("DATA");
    	try{
    		DataBiz DataBiz = new DataBiz();
    		resultVO = DataBiz.dataList(searchVO);
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
    
    
    //공지사항 리스트조회 
    public ActionForward dataList(ActionMapping mapping, ActionForm form, HttpServletRequest request
    		, HttpServletResponse response) throws Exception {
    	
    	String target = "dataList";   
    	
    	ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	DataForm fm = (DataForm) form;
    	DataSearchVO searchVO = fm.getSearchVO();
    	DataResultVO resultVO = new DataResultVO();
    	DataVO vo = fm.getVo();
    	
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		//if(searchVO.getBoard_type().equals("ETC")){
		//	searchVO.setBoard_type("ETC"); 
		//}else{
		//	searchVO.setBoard_type("INS"); 
		//}
//		searchVO.setBoard_type("DATA"); 
		searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	try{
    		DataBiz biz = new DataBiz();
    		resultVO = biz.dataList(searchVO);
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
    	request.setAttribute("searchVO.menu_sn", "02");
    	System.out.println("타이틀"+title);
    	saveErrors(request, errors);        
    	return mapping.findForward(target);
    }
    //공지사항 리스트조회 
    public ActionForward dataListLaw(ActionMapping mapping, ActionForm form, HttpServletRequest request
    		, HttpServletResponse response) throws Exception {
    	
    	String target = "dataListLaw";   
    	
    	ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	DataForm fm = (DataForm) form;
    	DataSearchVO searchVO = fm.getSearchVO();
    	DataResultVO resultVO = new DataResultVO();
    	DataVO vo = fm.getVo();
    	
    	if(Util.getLoginUserVO(request) != null){
    		searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
    		searchVO.setName(Util.getName(request)); //로그인 이름정보
    		searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
    	}
    	
    	//if(searchVO.getBoard_type().equals("ETC")){
    	//	searchVO.setBoard_type("ETC"); 
    	//}else{
    	//	searchVO.setBoard_type("INS"); 
    	//}
    	searchVO.setBoard_type("DATA"); 
    	searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	try{
    		DataBiz biz = new DataBiz();
    		resultVO = biz.dataList(searchVO);
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
    //자료실 상세정보 조회
    public ActionForward dataDetailView(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String target = "dataDetailView"; 
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		DataForm fm = (DataForm) form;
		DataSearchVO searchVO = fm.getSearchVO();
		DataResultVO resultVO = new DataResultVO();
		DataResultVO resultFileVO = new DataResultVO();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
						
		//searchVO.setLoginId("admin"); //로그인 아이디정보
		//searchVO.setName("관리자"); //로그인 이름정보
		
		try{
			DataBiz DataBiz = new DataBiz();
			searchVO.setFlag("V");
			resultVO = DataBiz.dataDetailView(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			DataBiz DataBiz = new DataBiz();
			resultFileVO = DataBiz.getFileInfo(resultVO.getVo().getFile_id());
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
    
    //자료실  삭제
    public ActionForward dataDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	String target = "dataDelete";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		DataForm fm = (DataForm) form;
		DataSearchVO searchVO = fm.getSearchVO();
		DataResultVO resultVO = new DataResultVO();
		DataResultVO resultFileVO = new DataResultVO();
		DataVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}

		searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		
		try{
			DataBiz DataBiz = new DataBiz();
			DataBiz.dataDelete(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
				
    	try{
    		DataBiz biz = new DataBiz();
    		resultVO = biz.dataList(searchVO);
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
    //자료실  수정폼
    public ActionForward dataUpdateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "dataUpdateForm";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		DataForm fm = (DataForm) form;
		DataSearchVO searchVO = fm.getSearchVO();
		DataResultVO resultVO = new DataResultVO();
		DataResultVO resultFileVO = new DataResultVO();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		try{
			DataBiz DataBiz = new DataBiz();
			searchVO.setFlag("U");
			resultVO = DataBiz.dataDetailView(searchVO);
			
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			DataBiz DataBiz = new DataBiz();
			resultFileVO = DataBiz.getFileInfo(resultVO.getVo().getFile_id());
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
    //자료실 데이터를 수정한다.
    public ActionForward dataUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "dataUpdate";
        
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		System.out.println("answer insert start~~~~~~~~~~");
		
		DataForm fm = (DataForm) form;
		DataSearchVO searchVO = fm.getSearchVO();
		DataResultVO resultVO = new DataResultVO();
		DataResultVO resultFileVO = new DataResultVO();
		DataVO vo = fm.getVo();
		
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
            	DataBiz DataBiz = new DataBiz();
            	DataBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
		
		//첨부파일 삭제유무에 따른 기존첨부파일 삭제
		try{
			DataBiz DataBiz = new DataBiz();
			DataBiz.getFileDelete(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			DataBiz DataBiz = new DataBiz();
			DataBiz.dataUpdate(vo, searchVO);
			request.setAttribute("result", new Integer(1));
		} catch (Exception e) {
			logger.debug("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}
		
		try{
			DataBiz DataBiz = new DataBiz();
			searchVO.setFlag("V");
			resultVO = DataBiz.dataDetailView(searchVO);
			
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			DataBiz DataBiz = new DataBiz();
			resultFileVO = DataBiz.getFileInfo(resultVO.getVo().getFile_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());		
		fm.setVo(resultVO.getVo());
		fm.setVoList(resultFileVO.getVoList());
		fm.setSearchVO(searchVO);
		System.out.println("업데이트 메뉴 sn????"+searchVO.getMenu_sn());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
		
        return mapping.findForward(target);   	
    	
    }
    
    // 연구관리제도 
    public ActionForward dataSystemList(ActionMapping mapping, ActionForm form, HttpServletRequest request
            , HttpServletResponse response) throws Exception {
        
        String target = "dataSystemList";   
        
        ActionErrors errors = null;
        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
            .getAttribute("org.apache.struts.action.ERROR");
        }
        
        DataForm fm = (DataForm) form;
        DataSearchVO searchVO = fm.getSearchVO();
        DataResultVO resultVO = new DataResultVO();
        DataVO vo = fm.getVo();
        
        if(Util.getLoginUserVO(request) != null){
            searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
            searchVO.setName(Util.getName(request)); //로그인 이름정보
            searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
        }
        
        //if(searchVO.getBoard_type().equals("ETC")){
        //  searchVO.setBoard_type("ETC"); 
        //}else{
        //  searchVO.setBoard_type("INS"); 
        //}
//      searchVO.setBoard_type("DATA"); 
        searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
        try{
            DataBiz biz = new DataBiz();
            resultVO = biz.dataSystemList(searchVO);
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
        request.setAttribute("searchVO.menu_sn", "02");
        System.out.println("타이틀"+title);
        saveErrors(request, errors);        
        return mapping.findForward(target);
    }

    //연구관리제도 등록폼           
    public ActionForward dataSystemInsertForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
        
        String target = "dataSystemInsertForm";   
        
        ActionErrors errors = null;
        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
                    .getAttribute("org.apache.struts.action.ERROR");
        }

        DataForm fm = (DataForm) form;
        DataSearchVO searchVO = fm.getSearchVO();
        DataResultVO resultVO = new DataResultVO();
        //searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
        //searchVO.setName(Util.getName(request)); //로그인 이름정보       
        if(Util.getLoginUserVO(request) != null){
            searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
            searchVO.setName(Util.getName(request)); //로그인 이름정보
            searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
        }
        
        return mapping.findForward(target);
    }
    
    //연구관리제도를  등록하는 메소드
    public ActionForward dataSystemInsert(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String target = "dataSystemInsert";
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
        
        DataForm fm = (DataForm) form;
        DataSearchVO searchVO = fm.getSearchVO();
        DataResultVO resultVO = new DataResultVO();
        DataVO vo = fm.getVo();
        
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
            DataDAO dao =  new DataDAO();
            question_id = dao.questionID();
            
            vo.setData_id(data_id);
            vo.setQuestion_id(question_id);
            vo.setInsert_type("WEB");
            
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
               DataBiz DataBiz = new DataBiz();
               DataBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
        
        //자료실 등록 부분
        try {
            DataBiz DataBiz = new DataBiz();
            searchVO.setFlag("V");
            resultVO = DataBiz.dataSystemInsert(vo, searchVO);
            request.setAttribute("result", new Integer(1));
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }
        //공지사항 리스트를 가져온다.
        searchVO.setBoard_type("SYSTEM");
        try{
            DataBiz DataBiz = new DataBiz();
            resultVO = DataBiz.dataSystemList(searchVO);
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
    
    //연구제도관리 상세정보 조회
    public ActionForward dataSystemDetailView(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {     
        
        String target = "dataSystemDetailView"; 
        ActionErrors errors = null;
        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
                    .getAttribute("org.apache.struts.action.ERROR");
        }

        DataForm fm = (DataForm) form;
        DataSearchVO searchVO = fm.getSearchVO();
        DataResultVO resultVO = new DataResultVO();
        DataResultVO resultFileVO = new DataResultVO();
        
        if(Util.getLoginUserVO(request) != null){
            searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
            searchVO.setName(Util.getName(request)); //로그인 이름정보
            searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
        }
                        
        //searchVO.setLoginId("admin"); //로그인 아이디정보
        //searchVO.setName("관리자"); //로그인 이름정보
        
        try{
            DataBiz DataBiz = new DataBiz();
            searchVO.setFlag("V");
            resultVO = DataBiz.dataDetailView(searchVO);
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
        try{
            DataBiz DataBiz = new DataBiz();
            resultFileVO = DataBiz.getFileInfo(resultVO.getVo().getFile_id());
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
    
    //연구관리제도  수정폼
    public ActionForward dataSystemUpdateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
        
         // Default target to success
        String target = "dataSystemUpdateForm";
        ActionErrors errors = null;
        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
                    .getAttribute("org.apache.struts.action.ERROR");
        }

        DataForm fm = (DataForm) form;
        DataSearchVO searchVO = fm.getSearchVO();
        DataResultVO resultVO = new DataResultVO();
        DataResultVO resultFileVO = new DataResultVO();
        
        if(Util.getLoginUserVO(request) != null){
            searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
            searchVO.setName(Util.getName(request)); //로그인 이름정보
            searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
        }
        
        try{
            DataBiz DataBiz = new DataBiz();
            searchVO.setFlag("U");
            resultVO = DataBiz.dataDetailView(searchVO);
            
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
        try{
            DataBiz DataBiz = new DataBiz();
            resultFileVO = DataBiz.getFileInfo(resultVO.getVo().getFile_id());
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
    //연구관리제도 데이터를 수정한다.
    public ActionForward dataSystemUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
        
         // Default target to success
        String target = "dataSystemUpdate";
        
        ActionErrors errors = null;
        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
                    .getAttribute("org.apache.struts.action.ERROR");
        }

        System.out.println("answer insert start~~~~~~~~~~");
        
        DataForm fm = (DataForm) form;
        DataSearchVO searchVO = fm.getSearchVO();
        DataResultVO resultVO = new DataResultVO();
        DataResultVO resultFileVO = new DataResultVO();
        DataVO vo = fm.getVo();
        
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
                DataBiz DataBiz = new DataBiz();
                DataBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
        
        //첨부파일 삭제유무에 따른 기존첨부파일 삭제
        try{
            DataBiz DataBiz = new DataBiz();
            DataBiz.getFileDelete(vo, searchVO);
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
        try{
            DataBiz DataBiz = new DataBiz();
            DataBiz.dataUpdate(vo, searchVO);
            request.setAttribute("result", new Integer(1));
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
        try{
            DataBiz DataBiz = new DataBiz();
            searchVO.setFlag("V");
            resultVO = DataBiz.dataDetailView(searchVO);
            
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
        try{
            DataBiz DataBiz = new DataBiz();
            resultFileVO = DataBiz.getFileInfo(resultVO.getVo().getFile_id());
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());        
        fm.setVo(resultVO.getVo());
        fm.setVoList(resultFileVO.getVoList());
        fm.setSearchVO(searchVO);
        System.out.println("업데이트 메뉴 sn????"+searchVO.getMenu_sn());
        
        if (errors.isEmpty()) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.getList.success"));
        }
        saveErrors(request, errors);
        
        return mapping.findForward(target);     
        
    }
    
    //연구관리제도 삭제
    public ActionForward dataSystemDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
        
        String target = "dataSystemDelete";
        ActionErrors errors = null;
        if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
            errors = new ActionErrors();
        } else {
            errors = (ActionErrors) request
                    .getAttribute("org.apache.struts.action.ERROR");
        }

        DataForm fm = (DataForm) form;
        DataSearchVO searchVO = fm.getSearchVO();
        DataResultVO resultVO = new DataResultVO();
        DataResultVO resultFileVO = new DataResultVO();
        DataVO vo = fm.getVo();
        
        if(Util.getLoginUserVO(request) != null){
            searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
            searchVO.setName(Util.getName(request)); //로그인 이름정보
            searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
        }

        searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
        
        try{
            DataBiz DataBiz = new DataBiz();
            DataBiz.dataDelete(searchVO);
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
                
        try{
            DataBiz biz = new DataBiz();
            resultVO = biz.dataSystemList(searchVO);
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
