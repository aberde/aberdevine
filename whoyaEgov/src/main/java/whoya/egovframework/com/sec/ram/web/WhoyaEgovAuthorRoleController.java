package whoya.egovframework.com.sec.ram.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import whoya.whoyaLib;
import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.common.Common;
import whoya.common.commonReturn;
import whoya.egovframework.com.sec.ram.service.WhoyaEgovAuthorRoleManageService;
import egovframework.com.sec.ram.service.AuthorRoleManageVO;

/**
 * 권한별 롤관리에 관한 controller 클래스를 정의한다.
 */

@Controller
public class WhoyaEgovAuthorRoleController {

	@Resource(name="whoyaEgovAuthorRoleManageService")
	WhoyaEgovAuthorRoleManageService whoyaEgovAuthorRoleManageService;
	
	/**
	 * 권한별 롤관리 화면
	 * @return ModelAndView
	 */
    @RequestMapping(value="whoya/sec/ram/EgovAuthorRoleList.do")
    public ModelAndView selectAuthorRoleList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/egovframework/com/sec/ram/EgovAuthorRoleManage");
		return mav;
	}
    
    /**
	 * 권한별 롤관리 목록을 조회한다
	 * @param authorRoleManageVO AuthorRoleManageVO
	 * @return JSONObject
	 * @exception Exception
	 */
    @RequestMapping(value="whoya/sec/ram/EgovAuthorRoleJSONList.do", headers="Accept=application/json")
    public @ResponseBody JSONObject selectAuthorRoleJSONList(@ModelAttribute("authorRoleManageVO") AuthorRoleManageVO authorRoleManageVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject resultList = new JSONObject();
		
		try {
			authorRoleManageVO.setPageIndex(0);
			List<AuthorRoleManageVO> voList = whoyaEgovAuthorRoleManageService.selectAuthorRoleList(authorRoleManageVO);
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(voList));
			
			// authorCode컬럼에 검색조건 authorCode롤 입력
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap map = list.getMap(i);
				map.put("authorCode", authorRoleManageVO.getSearchKeyword());
			}
						
			// 롤 ID,롤 명,롤 타입,롤 Sort,롤 설명,등록일자,등록여부,권한코드,롤 패턴
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "roleCode,roleNm,roleTyp,roleSort,roleDc,creatDt,regYn,authorCode,rolePtn"));
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
	 * 권한별 롤관리를 저장한다.
	 * @param request
	 * @param response
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping(value="whoya/sec/ram/saveAuthorRole.do")
	public @ResponseBody void saveAuthorRole(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		try {
			whoyaEgovAuthorRoleManageService.saveAuthorRole(request, response);
		} catch ( Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}
}