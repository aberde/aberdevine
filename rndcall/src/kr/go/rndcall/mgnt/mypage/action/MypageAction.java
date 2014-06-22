package kr.go.rndcall.mgnt.mypage.action;

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

import kr.go.rndcall.mgnt.inquire.vo.InquireResultVO;
import kr.go.rndcall.mgnt.mypage.vo.SatiVO;
import kr.go.rndcall.mgnt.mypage.form.MypageForm;
import kr.go.rndcall.mgnt.mypage.vo.MypageSearchVO;
import kr.go.rndcall.mgnt.mypage.vo.MypageAttachVO;
import kr.go.rndcall.mgnt.mypage.vo.MypageResultVO;
import kr.go.rndcall.mgnt.mypage.vo.MypageVO;
import kr.go.rndcall.mgnt.common.AttachVO;
import kr.go.rndcall.mgnt.common.FileUpload;
import kr.go.rndcall.mgnt.common.FileVO;
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.mypage.biz.MypageBiz;
import kr.go.rndcall.mgnt.mypage.dao.MypageDAO;

public class MypageAction extends DispatchAction {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public MypageAction() {
        super();
    }

	 /**
     * 마이페이지 리스트를 조회하는 함수
     */        
    public ActionForward getMypageList(ActionMapping mapping, ActionForm form, HttpServletRequest request
                                 , HttpServletResponse response) throws Exception {
    	
        String target = "getMypageList";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		MypageForm fm = (MypageForm) form;
		MypageSearchVO searchVO = fm.getSearchVO();
		MypageResultVO resultVO = new MypageResultVO();
		MypageVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		
		try{
			MypageBiz MypageBiz = new MypageBiz();
			resultVO = MypageBiz.getMypageList(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		
		fm.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		fm.setVoList(resultVO.getVoList());
		fm.setTotRowCount(resultVO.getTotRowCount());
		fm.setVo(resultVO.getVo());
		
		if(searchVO.getType().equals("4")){
            target = "getMypageListscrap";
        }

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
        
        return mapping.findForward(target);         
        
    } 
    
    
    /**
     * 마이페이지 상세정보를 조회하는 함수
     */     
    public ActionForward getMypageView(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String target = "getMypageView"; 
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		MypageForm fm = (MypageForm) form;
		MypageSearchVO searchVO = fm.getSearchVO();
		MypageResultVO resultVO = new MypageResultVO();
		MypageResultVO questionFileVO = new MypageResultVO();
		MypageResultVO answerFileVO = new MypageResultVO();		
		MypageResultVO satiResultVO = new MypageResultVO();
		
		MypageVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		
		try{
			MypageBiz MypageBiz = new MypageBiz();
			searchVO.setFlag("V");
			resultVO = MypageBiz.getMypageView(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			MypageBiz MypageBiz = new MypageBiz();
			questionFileVO = MypageBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			MypageBiz MypageBiz = new MypageBiz();
			answerFileVO = MypageBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			MypageBiz MypageBiz = new MypageBiz();
			satiResultVO = MypageBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		request.setAttribute("searchVO.menu_sn",searchVO.getMenu_sn());
		fm.setVoList(questionFileVO.getVoList());
		fm.setVoList1(answerFileVO.getVoList());
		fm.setVo(resultVO.getVo());
		fm.setSatiVO(satiResultVO.getSatiVO());
		
		if(searchVO.getScrap().equals("SCRAP")){
            target = "getMypageViewscrap";
        }

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
       
        return mapping.findForward(target);
    }
    
    /**
     * 마이페이지 상세정보를 조회하는 함수
     */     
    public ActionForward getMypageUpdateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String target = "getMypageUpdateForm"; 
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		MypageForm fm = (MypageForm) form;
		MypageSearchVO searchVO = fm.getSearchVO();
		MypageResultVO resultVO = new MypageResultVO();
		MypageResultVO questionFileVO = new MypageResultVO();
		
		MypageVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		
		try{
			MypageBiz MypageBiz = new MypageBiz();
			searchVO.setFlag("U");
			resultVO = MypageBiz.getMypageView(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			MypageBiz MypageBiz = new MypageBiz();
			questionFileVO = MypageBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		fm.setVo(resultVO.getVo());
		fm.setVoList(questionFileVO.getVoList());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
       
        return mapping.findForward(target);
    }
    
    /**
     * 마이페이지 수정하는 메소드
     */     
    public ActionForward getMypageUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getMypageUpdateForm";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		MypageForm fm = (MypageForm) form;
		MypageSearchVO searchVO = fm.getSearchVO();
		MypageResultVO resultVO = new MypageResultVO();
		MypageResultVO questionFileVO = new MypageResultVO();
		MypageResultVO answerFileVO = new MypageResultVO();
		MypageResultVO satiResultVO = new MypageResultVO();
		MypageVO vo = fm.getVo();
		

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
		    	MypageBiz MypageBiz = new MypageBiz();
		    	MypageBiz.putAttach(vo, attachVO);
		    } catch (Exception e) {
		    	logger.error("Exception: " + e.getMessage());
		    	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		    }
		}
		        
		// 첨부파일 삭제유무에 따른 기존첨부파일 삭제
		try{
			MypageBiz MypageBiz = new MypageBiz();
			if (vo.getDel_file_id() != null && !vo.getDel_file_id().equals(""))
				MypageBiz.getQuestionFileDelete(vo, searchVO);
		} catch (Exception e) {
			logger.debug("Exception: " + e.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
		}
				
		try{
			MypageBiz MypageBiz = new MypageBiz();
			success = MypageBiz.getMypageUpdate(vo, searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			MypageBiz MypageBiz = new MypageBiz();
			searchVO.setFlag("U");
			resultVO = MypageBiz.getMypageView(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			MypageBiz MypageBiz = new MypageBiz();
			System.out.println("resultVO.getVo().getQuestion_file_id()::"+resultVO.getVo().getQuestion_file_id());
			questionFileVO = MypageBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			MypageBiz MypageBiz = new MypageBiz();
			System.out.println("resultVO.getVo().getAnswer_file_id()::"+resultVO.getVo().getAnswer_file_id());
			answerFileVO = MypageBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		request.setAttribute("searchVO.menu_sn",searchVO.getMenu_sn());
		
		fm.setVo(resultVO.getVo());
		fm.setVoList(questionFileVO.getVoList());
		fm.setVoList1(answerFileVO.getVoList());
		fm.setErrCd(String.valueOf(success));
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
        return mapping.findForward(target);   	
    	
    }
    
    /**
     * 마이페이지 삭제하는 메소드
     */     
    public ActionForward getMypageDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
    	 // Default target to success
        String target = "getMypageDelete";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		MypageForm fm = (MypageForm) form;
		MypageSearchVO searchVO = fm.getSearchVO();
		MypageResultVO resultVO = new MypageResultVO();
		MypageResultVO resultFileVO = new MypageResultVO();
		MypageResultVO satiResultVO = new MypageResultVO();
		MypageVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		try{
			MypageBiz MypageBiz = new MypageBiz();
			MypageBiz.getMypageDelete(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			MypageBiz MypageBiz = new MypageBiz();
			resultVO = MypageBiz.getMypageList(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		
		fm.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
		
		fm.setVoList(resultVO.getVoList());
		fm.setTotRowCount(resultVO.getTotRowCount());
		fm.setVo(resultVO.getVo());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
        return mapping.findForward(target);   	
    	
    }
    
    /**
     * 마이페이지 상세정보를 조회하는 함수
     */     
    public ActionForward getSatiInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String target = "getSatiInsert"; 
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		MypageForm fm = (MypageForm) form;
		MypageSearchVO searchVO = fm.getSearchVO();
		MypageResultVO resultVO = new MypageResultVO();
		MypageResultVO questionFileVO = new MypageResultVO();
		MypageResultVO answerFileVO = new MypageResultVO();
		MypageResultVO satiResultVO = new MypageResultVO();
		
	
		MypageVO vo = fm.getVo();
		SatiVO satiVO = fm.getSatiVO();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		
//		만족도 평가 등록 
		try{
			satiVO.setReg_ip(request.getRemoteAddr());
			MypageBiz MypageBiz = new MypageBiz();
			MypageBiz.getSatiInsert(vo, searchVO, satiVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			MypageBiz MypageBiz = new MypageBiz();
			searchVO.setFlag("V");
			resultVO = MypageBiz.getMypageView(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		System.out.println("seq:"+resultVO.getVo().getSeq());
		System.out.println("file_id:"+resultVO.getVo().getFile_id());
		try{
			MypageBiz MypageBiz = new MypageBiz();
			questionFileVO = MypageBiz.getFileInfo(resultVO.getVo().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			MypageBiz MypageBiz = new MypageBiz();
			System.out.println("resultVO.getVo().getAnswer_file_id()::"+resultVO.getVo().getAnswer_file_id());
			answerFileVO = MypageBiz.getFileInfo(resultVO.getVo().getAnswer_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			MypageBiz MypageBiz = new MypageBiz();
			satiResultVO = MypageBiz.getSatiInfo(resultVO.getVo().getSeq(), searchVO.getLoginId());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		fm.setSatiVO(satiResultVO.getSatiVO());		
		fm.setVoList(questionFileVO.getVoList());
		fm.setVoList1(answerFileVO.getVoList());
		fm.setVo(resultVO.getVo());
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		saveErrors(request, errors);
       
        return mapping.findForward(target);
    }

	 /**
     * 스크랩 삭제
     */        
    public ActionForward getMypageScrapDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request
                                 , HttpServletResponse response) throws Exception {
    	
        String target = "getMypageScrapDelete";
        ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}
		boolean success = false;
		MypageForm fm = (MypageForm) form;
		MypageSearchVO searchVO = fm.getSearchVO();
		MypageResultVO resultVO = new MypageResultVO();
		MypageResultVO questionFileVO = new MypageResultVO();
		MypageResultVO answerFileVO = new MypageResultVO();
		MypageResultVO satiResultVO = new MypageResultVO();
		MypageVO vo = fm.getVo();
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		
		try{
			MypageBiz MypageBiz = new MypageBiz();
			success = MypageBiz.getMypageScrapDelete(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		try{
			MypageBiz MypageBiz = new MypageBiz();
			resultVO = MypageBiz.getMypageList(searchVO);
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		request.setAttribute("searchVO.menu_sn",searchVO.getMenu_sn());
		
		fm.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
		fm.setVoList(resultVO.getVoList());
		fm.setTotRowCount(resultVO.getTotRowCount());
		fm.setVo(resultVO.getVo());
		fm.setVoList1(answerFileVO.getVoList());
		fm.setErrCd(String.valueOf(success));
		//System.out.println("VO전부"+resultVO.getVo());
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
		if(searchVO.getType().equals("4")){
            target = "getMypageListscrap";
        }
		saveErrors(request, errors);
        
        return mapping.findForward(target);         
        
    } 
}
