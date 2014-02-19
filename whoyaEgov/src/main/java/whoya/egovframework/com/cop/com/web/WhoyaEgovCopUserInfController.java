package whoya.egovframework.com.cop.com.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import whoya.whoyaLib;
import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.common.Common;
import whoya.common.commonReturn;
import whoya.egovframework.com.cop.com.service.WhoyaEgovUserInfManageService;
import egovframework.com.cop.com.service.UserInfVO;


/**
 * 협업기능에서 활용하는 사용자 정보 조회용 컨트롤러 클래스
 */
@Controller
public class WhoyaEgovCopUserInfController {

    @Resource(name = "WhoyaEgovUserInfManageService")
    private WhoyaEgovUserInfManageService userInfService;

    /**
     * 사용자 정보에 대한 목록을 조회한다.
     * 
     * @param userVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/whoya/cop/com/selectUserList.do")
    public @ResponseBody JSONObject selectUserList(@ModelAttribute("searchVO") UserInfVO userVO, Map<String, Object> commandMap, ModelMap model) throws Exception {
		JSONObject resultList = new JSONObject();
		
		try {
			userVO.setPageIndex(0);
			Map<String, Object> map = userInfService.selectUserList(userVO);
			List<UserInfVO> voList = (List<UserInfVO>)map.get("resultList");
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(voList));
			
			// 번호 컬럼 추가.
			// TODO 수정필요 list목록이 많아지면 속도저하 whoyaLib에서 whoyaRenderGrid호출시 같이 처리되도록 해야됨.(aberdevine) 
			for ( int i = 0 ; i < list.size(); i++ ) {
				whoyaMap wmap = list.getMap(i);
				wmap.put("no", i + 1);
				wmap.put("selectLink", "../../../images/egovframework/com/cmm/icon/search.gif^선택^javascript:whoya.common.emplyrSelect(\"" + wmap.get("userId") + "\", \"" + wmap.get("userNm") + "\");^_self");
			}

			// 번호,사용자아이디,사용자명,주소,이메일,사용여부,선택
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "no,userId,userNm,userAdres,userEmail,useAt,selectLink"));
		 	resultList.put("status", commonReturn.SUCCESS);
		 	resultList.put("message", "조회되었습니다");
		} catch(Exception e) {
			resultList.put("status", commonReturn.FAIL);
			resultList.put("message", e.getMessage());
			throw e;
		}
		
		return resultList;
    }
}
