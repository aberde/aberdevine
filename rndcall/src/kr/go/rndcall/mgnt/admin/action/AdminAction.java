package kr.go.rndcall.mgnt.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.rndcall.mgnt.admin.biz.AdminBiz;
import kr.go.rndcall.mgnt.admin.dao.AdminDAO;
import kr.go.rndcall.mgnt.admin.form.AdminForm;
import kr.go.rndcall.mgnt.admin.vo.AdminResultVO;
import kr.go.rndcall.mgnt.admin.vo.AdminSearchVO;
import kr.go.rndcall.mgnt.admin.vo.AdminVO;
import kr.go.rndcall.mgnt.common.AttachVO;
import kr.go.rndcall.mgnt.common.FileUploadData;
import kr.go.rndcall.mgnt.common.FileVO;
import kr.go.rndcall.mgnt.common.Util;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AdminAction extends DispatchAction {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public AdminAction() {
        super();
    }

    
    /**
     * 오프라인자료 등록폼을 생성하는 메소드
     */     
    public ActionForward getOfflineDataForm(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
        String target = "getOfflineDataForm";   
        
		ActionErrors errors = null;
		if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
			errors = new ActionErrors();
		} else {
			errors = (ActionErrors) request
					.getAttribute("org.apache.struts.action.ERROR");
		}

		AdminForm fm = (AdminForm) form;
		AdminSearchVO searchVO = fm.getSearchVO();
		AdminResultVO resultVO = new AdminResultVO();
		//searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
		//searchVO.setName(Util.getName(request)); //로그인 이름정보		
						
		searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
//		searchVO.setName("관리자"); //로그인 이름정보
        
        try {
            AdminBiz biz = new AdminBiz();
            
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
    
    /*
    
    /**
    /**
     * getOfflineDataInsert 오프라인자료 등록
     * @param mapping
     * @param form
     * @param request
     * @param response
     */
    public ActionForward getOfflineDataInsert(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String target = "getOfflineDataInsert";
        ActionErrors errors = null;
        FileVO[] fileInfo = null;
        FileVO[] fileInfo2 = null;
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
        
        AdminForm fm = (AdminForm) form;
        AdminSearchVO searchVO = fm.getSearchVO();
        AdminResultVO resultVO = new AdminResultVO();
        AdminVO vo = fm.getVo();
        AttachVO[] attachVO = null;
        
        searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
        //vo.setReg_id(Util.getLoginUserVO(request).getUsername()); //등록자ID
        vo.setReg_id(Util.getLoginId(request));
        //searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD()); //로그인 ID 권한
        //searchVO.setBoard_kind("data");//자료실 구분자
        
        /*
         * RNDCALL_FILE_ID_SEQ 시퀀스 정보조회
         * RNDCALL_BOARD_QUESTION 테이블 SEQ(시퀀스)정보조회
         * RNDCALL_BOARD_ANSWER 테이블 SEQ(시퀀스)정보조회
         */     
        try{
            AdminDAO dao =  new AdminDAO();
            data_id = dao.getFileIdSeq();
            question_id = dao.getOfflineDataQuestionSeq();
            answer_id = dao.getOfflineDataAnswerSeq();
            
            vo.setData_id(data_id);
            vo.setQuestion_id(question_id);
            vo.setAnswer_id(answer_id);
            vo.setInsert_type("OFFLINE");
            vo.setBoard_type("QNA");
            vo.setCell_number(vo.getCell_number1()+"-"+vo.getCell_number2()+"-"+vo.getCell_number3());
            
        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
        
        /*
         * 첨부파일 업로드 
         */ 
        try {
            // attach
            FileUploadData upload = new FileUploadData(mapping, form, request);
            upload.setFilePath("rndcall/upload/offlinedata/");
            //upload.setUsername(Util.getLoginUserVO(request).getUsername());
            upload.setUsername("admin");
            
            try {
                upload.service();
            } catch (Exception e) {
                e.printStackTrace();
            }
            fileInfo = upload.getFileInfo();
            fileInfo2 = upload.getFileInfo2();

        } catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }        
        //vo.setFilePath(fileInfo[0].getAbsolutePath() + fileInfo[0].getOutputFileName());
        
        /*
         * 업로드된 첨부파일 정보를DB에 insert
         */
        System.out.println("fileInfo.length=>" +fileInfo.length);
        System.out.println("fileInfo2.length=>" +fileInfo2.length);
        
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
                AdminBiz AdminBiz = new AdminBiz();
                AdminBiz.putAttach(vo, attachVO);
            } catch (Exception e) {
                logger.error("Exception: " + e.getMessage());
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
            }
        }
        
        /*
         * RNDCALL_BOARD_QUESTION 에 INSERT
         * RNDCALL_BOARD_ANSWER 에 INSERT
         */
        try {
           
            AdminBiz AdminBiz = new AdminBiz();
            
            if(!"".equals(vo.getTitle()))
                resultVO = AdminBiz.getOfflineDataInsert(vo);
            
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }
        
        /*
         * 일괄등록 EXCEL 업로드
         * 
         */
        try {
            AdminBiz AdminBiz = new AdminBiz();
            searchVO.setLoginId(Util.getLoginId(request));
            
            if(fileInfo2 != null)
                result = AdminBiz.getOfflineDataExcelUploadInsert(fileInfo2, searchVO);
            
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.database.error", e.getMessage()));
        }
        
        
        request.setAttribute("searchVO.menu_sn", searchVO.getMenu_sn());
        fm.setErrCd(Integer.toString(result));
        
        if (errors.isEmpty()) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "errors.getList.success"));
        }
        saveErrors(request, errors);

        logger.debug("target: " + target);
        return mapping.findForward(target);
    }
    
}