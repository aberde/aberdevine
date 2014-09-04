package kr.go.rndcall.mgnt.statistic.action;

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

import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.statistic.biz.StatisticBiz;
import kr.go.rndcall.mgnt.statistic.form.StatisticForm;
import kr.go.rndcall.mgnt.statistic.vo.StatisticResultVO;
import kr.go.rndcall.mgnt.statistic.vo.StatisticSearchVO;	
import kr.go.rndcall.mgnt.statistic.vo.StatisticVO;

public class StatisticAction extends DispatchAction {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public StatisticAction() {
        super();
    }

	 /**
     * 통계관리 분야별통계현황 정보조회
     */        
    public ActionForward getStatCategory(ActionMapping mapping, ActionForm form, HttpServletRequest request
                                 , HttpServletResponse response) throws Exception {
    	
        String target = "getStatCategory";
        ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	StatisticForm fm = (StatisticForm) form;
    	StatisticSearchVO searchVO = fm.getSearchVO();
    	StatisticResultVO resultVO = new StatisticResultVO();
    	StatisticVO vo = fm.getVo();
    	
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		searchVO.setCrud("category");
    	searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	try{
    		StatisticBiz biz = new StatisticBiz();
    		resultVO = biz.getStatCategory(searchVO);
    		
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
        
        
    }       
    
    /**
     * 통계관리 기간별현황 정보조회
     */     
    public ActionForward getStatDate(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
        String target = "getStatDate";
        ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	StatisticForm fm = (StatisticForm) form;
    	StatisticSearchVO searchVO = fm.getSearchVO();
    	StatisticResultVO resultVO = new StatisticResultVO();
    	StatisticVO vo = fm.getVo();
    	
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		searchVO.setCrud("date");
    	searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	try{
    		StatisticBiz biz = new StatisticBiz();
    		resultVO = biz.getStatDate(searchVO);
    		
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
    }
    
    /**
     * 현황별 문의 리스트
     */     
    public ActionForward getStatDataList(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String target = "getStatDataList"; 
        ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	StatisticForm fm = (StatisticForm) form;
    	StatisticSearchVO searchVO = fm.getSearchVO();
    	StatisticResultVO resultVO = new StatisticResultVO();
    	StatisticVO vo = fm.getVo();
    	
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		
    	searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	searchVO.setCrud("date");
    	try{
    		StatisticBiz biz = new StatisticBiz();
    		resultVO = biz.getStatDataList(searchVO);
    		
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
    	
    	
    	saveErrors(request, errors);
       
        return mapping.findForward(target);
    }
    
    /**
     * 현황별 문의 리스트
     */     
    public ActionForward getStatDataExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String target = "getStatDataExcel"; 
        ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	StatisticForm fm = (StatisticForm) form;
    	StatisticSearchVO searchVO = fm.getSearchVO();
    	StatisticResultVO resultVO = new StatisticResultVO();
    	StatisticVO vo = fm.getVo();
    	
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		
    	searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	try{
    		StatisticBiz biz = new StatisticBiz();
    		resultVO = biz.getStatDataExcel(searchVO);
    		
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
    	
    	
    	saveErrors(request, errors);
       
        return mapping.findForward(target);
    }
    
    
    /**
     * 통계관리 접속자현황 정보조회
     */     
    public ActionForward getStatVisit(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {
    	
        String target = "getStatVisit";
        ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	StatisticForm fm = (StatisticForm) form;
    	StatisticSearchVO searchVO = fm.getSearchVO();
    	StatisticResultVO resultVO = new StatisticResultVO();
    	StatisticVO vo = fm.getVo();
    	
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		
    	searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	try{
    		StatisticBiz biz = new StatisticBiz();
    		resultVO = biz.getStatVisit(searchVO);
    		
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
    	fm.setVo(resultVO.getVo());
    	
    	saveErrors(request, errors);   
               
        return mapping.findForward(target);
    }    

    /**
     * 현황별 문의 리스트
     */     
    public ActionForward getStatList(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String target = "getStatList"; 
        ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	StatisticForm fm = (StatisticForm) form;
    	StatisticSearchVO searchVO = fm.getSearchVO();
    	StatisticResultVO resultVO = new StatisticResultVO();
    	StatisticVO vo = fm.getVo();
    	
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		
    	searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	try{
    		StatisticBiz biz = new StatisticBiz();
    		resultVO = biz.getStatList(searchVO);
    		
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
    	
    	
    	saveErrors(request, errors);
       
        return mapping.findForward(target);
    }
    
    /**
     * 현황별 문의 리스트
     */     
    public ActionForward getStatView(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String target = "getStatView"; 
        ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	StatisticForm fm = (StatisticForm) form;
    	StatisticSearchVO searchVO = fm.getSearchVO();
    	StatisticResultVO resultVO = new StatisticResultVO();
    	StatisticVO vo = fm.getVo();
    	
    	StatisticResultVO questionFileVO = new StatisticResultVO(); //질문 첨부파일
		StatisticResultVO answerFileVO = new StatisticResultVO(); //답변첨부파일
		
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		
    	searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	try{
    		StatisticBiz biz = new StatisticBiz();
    		resultVO = biz.getStatView(searchVO);
    		
    	} catch (Exception e) {
    		logger.debug("Exception: " + e.getMessage());
    		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
    	}
    	
    	
    	try{
			StatisticBiz StatisticBiz = new StatisticBiz();
			questionFileVO = StatisticBiz.getFileInfo(resultVO.getVo1().getQuestion_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		try{
			StatisticBiz StatisticBiz = new StatisticBiz();
			answerFileVO = StatisticBiz.getFileInfo(resultVO.getVo1().getAnswer_file_id());
		} catch (Exception e) {
            logger.debug("Exception: " + e.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.database.error", e.getMessage()));
        }
		
		fm.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	fm.setVo1(resultVO.getVo1());
		fm.setVoList(questionFileVO.getVoList());	//질문첨부파일정보
		fm.setVoList1(answerFileVO.getVoList());	//답변첨부파일
		
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.getList.success"));
		}
    	
    	saveErrors(request, errors);
       
        return mapping.findForward(target);
    }
    
    /**
     * 현황별 문의 리스트
     */     
    public ActionForward getStatListExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String target = "getStatListExcel"; 
        ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	StatisticForm fm = (StatisticForm) form;
    	StatisticSearchVO searchVO = fm.getSearchVO();
    	StatisticResultVO resultVO = new StatisticResultVO();
    	StatisticVO vo = fm.getVo();
    	
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		
    	searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
    	try{
    		StatisticBiz biz = new StatisticBiz();
    		resultVO = biz.getStatExcelList(searchVO);
    		
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
    }
    
    /**
     * 통계정보 엑셀다운로드
     */     
    public ActionForward getStatExcelDownLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request
                             , HttpServletResponse response) throws Exception {   	
    	
        String target = "stat_category_excel";
        ActionErrors errors = null;
    	if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
    		errors = new ActionErrors();
    	} else {
    		errors = (ActionErrors) request
    		.getAttribute("org.apache.struts.action.ERROR");
    	}
    	
    	StatisticForm fm = (StatisticForm) form;
    	StatisticSearchVO searchVO = fm.getSearchVO();
    	StatisticResultVO resultVO = new StatisticResultVO();
    	StatisticVO vo = fm.getVo();
    	
		if(Util.getLoginUserVO(request) != null){
			searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
			searchVO.setName(Util.getName(request)); //로그인 이름정보
			searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
		}
		
		if(searchVO.getDown_type().equals("category")){
			target = "stat_category_excel";
		}else if(searchVO.getDown_type().equals("date")){
			target = "stat_date_excel";
		}else if(searchVO.getDown_type().equals("visit")){
			target = "stat_visit_excel";
		}else if(searchVO.getDown_type().equals("board_type")){
		    target = "stat_board_type_excel";
		}
        
		try{
    		StatisticBiz biz = new StatisticBiz();
    		
    		if(searchVO.getDown_type().equals("category")){
    			resultVO = biz.getStatCategory(searchVO);
    		}else if(searchVO.getDown_type().equals("date")){
    			resultVO = biz.getStatDate(searchVO);
    		}else if(searchVO.getDown_type().equals("visit")){
    			resultVO = biz.getStatVisit(searchVO);
    		}
    		
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
    	fm.setVo(resultVO.getVo());
        
        return mapping.findForward(target);
    }

    
    /**
    * 통계관리 게시타입별 통계현황 정보조회
    */        
   public ActionForward getStatBoardType(ActionMapping mapping, ActionForm form, HttpServletRequest request
                                , HttpServletResponse response) throws Exception {
       
       String target = "getStatBoardType";
       ActionErrors errors = null;
       if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
           errors = new ActionErrors();
       } else {
           errors = (ActionErrors) request
           .getAttribute("org.apache.struts.action.ERROR");
       }
       
       StatisticForm fm = (StatisticForm) form;
       StatisticSearchVO searchVO = fm.getSearchVO();
       StatisticResultVO resultVO = new StatisticResultVO();
       StatisticVO vo = fm.getVo();
       
       if(Util.getLoginUserVO(request) != null){
           searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
           searchVO.setName(Util.getName(request)); //로그인 이름정보
           searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
       }
       
       searchVO.setCrud("category");
       searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
       try{
           StatisticBiz biz = new StatisticBiz();
           resultVO = biz.getStatBoardType(searchVO);
           
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
       
       
   }
   
   /**
   * 통계관리 사용자 소속별 통계현황 정보조회
   */        
  public ActionForward getStatQueryUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request
                               , HttpServletResponse response) throws Exception {
      
      String target = "getStatQueryUserInfo";
      ActionErrors errors = null;
      if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
          errors = new ActionErrors();
      } else {
          errors = (ActionErrors) request
          .getAttribute("org.apache.struts.action.ERROR");
      }
      
      StatisticForm fm = (StatisticForm) form;
      StatisticSearchVO searchVO = fm.getSearchVO();
      StatisticResultVO resultVO = new StatisticResultVO();
      StatisticVO vo = fm.getVo();
      
      if(Util.getLoginUserVO(request) != null){
          searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
          searchVO.setName(Util.getName(request)); //로그인 이름정보
          searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
      }
      
      searchVO.setCrud("category");
      searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
      try{
          StatisticBiz biz = new StatisticBiz();
          resultVO = biz.getStatQueryUserInfo(searchVO);
          
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
      
      
  }
  
  /**
   * 통계관리 사용자 소속별 통계현황 정보조회
   */        
  public ActionForward getStatUserSector(ActionMapping mapping, ActionForm form, HttpServletRequest request
          , HttpServletResponse response) throws Exception {
      
      String target = "getStatUserSector";
      ActionErrors errors = null;
      if (request.getAttribute("org.apache.struts.action.ERROR") == null) {
          errors = new ActionErrors();
      } else {
          errors = (ActionErrors) request
                  .getAttribute("org.apache.struts.action.ERROR");
      }
      
      StatisticForm fm = (StatisticForm) form;
      StatisticSearchVO searchVO = fm.getSearchVO();
      StatisticResultVO resultVO = new StatisticResultVO();
      StatisticVO vo = fm.getVo();
      
      if(Util.getLoginUserVO(request) != null){
          searchVO.setLoginId(Util.getLoginId(request)); //로그인 아이디정보
          searchVO.setName(Util.getName(request)); //로그인 이름정보
          searchVO.setRoleCD(Util.getLoginUserVO(request).getRoleCD());
      }
      
      searchVO.setCrud("category");
      searchVO.setPagerOffset(Util.replaceNull(request.getParameter("pager.offset"), "0"));
      try{
          StatisticBiz biz = new StatisticBiz();
          resultVO = biz.getStatUserSector(searchVO);
          
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
      
      
  }
}
