package whoya.egovframework.com.sec.ram.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import whoya.whoyaLib;
import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.common.Common;
import whoya.common.commonReturn;
import whoya.egovframework.com.sec.ram.service.WhoyaEgovAuthorManageService;
import egovframework.com.sec.ram.service.AuthorManage;
import egovframework.com.sec.ram.service.AuthorManageVO;

/**
 * 권한관리에 관한 controller 클래스를 정의한다.
 */
 

@Controller
public class WhoyaEgovAuthorManageController {

	@Resource(name="whoyaEgovAuthorManageService")
	WhoyaEgovAuthorManageService egovAuthorManageService;
    
    /**
	 * 권한관리 화면
	 * @return ModelAndView
	 */
    @RequestMapping(value="/whoya/sec/ram/EgovAuthorList.do")
    public ModelAndView selectAuthorList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/egovframework/com/sec/ram/EgovAuthorManage");
		return mav;
	}
    
    /**
	 * 권한관리 목록을 조회한다
	 * @param authorManageVO AuthorManageVO
	 * @return JSONObject
	 * @exception Exception
	 */
    @RequestMapping(value="/whoya/sec/ram/EgovAuthorJSONList.do", headers="Accept=application/json")
    public @ResponseBody JSONObject selectLoginPolicyJSONList(@ModelAttribute("authorManageVO") AuthorManageVO authorManageVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject resultList = new JSONObject();
		
		try {
			authorManageVO.setPageIndex(0);
			List<AuthorManageVO> voList = egovAuthorManageService.selectAuthorList(authorManageVO);
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(voList));
			
			// 롤 정보 컬럼 추가.(이미지 링크걸기.)
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap map = list.getMap(i);
				String authorCode = (String)map.get("authorCode");
				map.put("roleLink", "../../../images/egovframework/com/cmm/icon/search.gif^롤 정보^" + request.getContextPath() + "/whoya/sec/ram/EgovAuthorRoleList.do?authorCode=" + authorCode + "^_self");
			}
			
			// 권한코드,권한 명,권한코드설명,권한등록일자
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "authorCode,authorNm,authorDc,authorCreatDe,roleLink"));
		 	resultList.put("status", commonReturn.SUCCESS);
		 	resultList.put("message", "조회되었습니다");
		} catch(Exception e) {
			resultList.put("status", commonReturn.FAIL);
			resultList.put("message", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
		return resultList;
	}
    
    /**
	 * 권한 세부정보를 조회한다.
	 * @param authorManageVO AuthorManageVO
	 * @return AuthorManageVO
	 * @exception Exception
	 */   
    @RequestMapping(value="/whoya/sec/ram/EgovAuthor.do")
    public @ResponseBody AuthorManageVO selectAuthor(@ModelAttribute("authorManageVO") AuthorManageVO authorManageVO, ModelMap model) throws Exception {
    	try {
    		AuthorManageVO vo = egovAuthorManageService.selectAuthor(authorManageVO);
    		return vo;
    	} catch ( Exception e ) {
    		e.printStackTrace();
    		throw e;
    	}
    }
    
    /**
	 * 권한 세부정보를 등록한다.
	 * @param authorManage AuthorManage
	 * @param bindingResult BindingResult
	 * @return JSONObject
	 * @exception Exception
	 */ 
    @RequestMapping(value="/whoya/sec/ram/EgovAuthorInsert.do")
    public @ResponseBody JSONObject insertAuthor(@ModelAttribute("authorManage") AuthorManage authorManage, BindingResult bindingResult, SessionStatus status, ModelMap model) throws Exception {
    	JSONObject result = new JSONObject();
    	try {
    		egovAuthorManageService.insertAuthor(authorManage);
    		result.put("status", commonReturn.SUCCESS);
    	} catch ( Exception e ) {
    		result.put("status", commonReturn.FAIL);
    		e.printStackTrace();
    		throw e;
    	}
    	
    	return result;
    }
    
    /**
	 * 권한 세부정보를 수정한다.
	 * @param authorManage AuthorManage
	 * @param bindingResult BindingResult
	 * @return JSONObject
	 * @exception Exception
	 */   
    @RequestMapping(value="/whoya/sec/ram/EgovAuthorUpdate.do")
    public @ResponseBody JSONObject updateAuthor(@ModelAttribute("authorManage") AuthorManage authorManage, BindingResult bindingResult, SessionStatus status, Model model) throws Exception {
    	JSONObject result = new JSONObject();
    	try {
    		egovAuthorManageService.updateAuthor(authorManage);
    		result.put("status", commonReturn.SUCCESS);
    	} catch ( Exception e ) {
    		result.put("status", commonReturn.FAIL);
    		e.printStackTrace();
    		throw e;
    	}
    	
    	return result;
    } 

    /**
	 * 권한 세부정보를 삭제한다.
	 * @param authorManage AuthorManage
	 * @return JSONObject
	 * @exception Exception
	 */  
    @RequestMapping(value="/whoya/sec/ram/EgovAuthorDelete.do")
    public @ResponseBody JSONObject deleteAuthor(@ModelAttribute("authorManage") AuthorManage authorManage, BindingResult bindingResult, SessionStatus status, Model model) throws Exception {
    	JSONObject result = new JSONObject();
    	try {
    		egovAuthorManageService.deleteAuthor(authorManage);
    		result.put("status", commonReturn.SUCCESS);
    	} catch ( Exception e ) {
    		result.put("status", commonReturn.FAIL);
    		e.printStackTrace();
    		throw e;
    	}
    	
    	return result;
    }
    
    /**
	 * 권한목록을 삭제한다.
	 * @param authorCodes String
	 * @param authorManage AuthorManage
	 * @exception Exception
	 */  
    @RequestMapping(value="/whoya/sec/ram/EgovAuthorListDelete.do")
    public @ResponseBody void deleteAuthorList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	try {
		    egovAuthorManageService.saveAuthor(request, response);
		} catch ( Exception e ) {
			e.printStackTrace();
			throw e;
		}
    }
    
//    /**
//	 * 권한관리 등록/수정/삭제한다.
//	 * @param request
//	 * @param response
//	 * @param model
//	 * @exception Exception
//	 */
//	@RequestMapping(value="whoya/sec/ram/saveAuthor.do")
//	public @ResponseBody void saveAuthor(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
//		try {
//		    whoyaEgovAuthorManageService.saveAuthor(request, response);
//		} catch ( Exception e ) {
//			e.printStackTrace();
//			throw e;
//		}
//	}
}
