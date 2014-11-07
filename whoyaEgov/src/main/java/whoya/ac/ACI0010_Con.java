package whoya.ac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import whoya.common.commonReturn;
import whoya.common.commonSession;
import whoya.common.commonSessionUtil;


@Controller
public class ACI0010_Con extends MultiActionController {

	@Resource(name="ACI0010_Svc")
	private ACI0010_Svc ACI0010_Service;
	
	@RequestMapping(value="whoya/content/ac/ACI0010_.do")
	public ModelAndView ACI0010_() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/content/ac/ACI0010_");
		return mav;
	}
	
	/*계정과목콤보*/
	@RequestMapping(value="whoya/content/ac/ACI0010_Rc_acCd.do", headers="Accept=application/json")
	public @ResponseBody JSONObject ACI0010Rc_acCd( HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> paramMap ) throws Exception {
		commonSession session = commonSessionUtil.getCommonSession();
		Map<String, String> params = new HashMap<String, String>();
		params.put("coId", session.getCoId());

	    JSONObject resultList = new JSONObject();
		
		try {
			List<Map<String, String>> list = ACI0010_Service.ACI0010_Rc_acCd(params);
			
		 	resultList.put("list", list);
		 	resultList.put("status", commonReturn.SUCCESS);
		 	resultList.put("message", "조회되었습니다");
	  	} catch(Exception e){
  			resultList.put("status", commonReturn.FAIL);
  			resultList.put("message", e.getMessage());
  			throw e;
	  	}
		return resultList;
	}
	
	@RequestMapping(value="whoya/content/ac/ACI0010_Rs.do", headers="Accept=application/json")
	public @ResponseBody JSONObject ACI0010Rs( HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		System.out.println(session.getUsrId());
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("coId", session.getCoId());
		params.put("acCd", ServletRequestUtils.getStringParameter(request, "acCd"));

	    JSONObject resultList = new JSONObject();
		
		try {
			List<Map<String, String>> list = ACI0010_Service.ACI0010_Rs(params);
			
			// dhtmlxGrid.parsing에서 인식할 수 있는 형태로 생성(배열에 입력되는 순서는 dhtmlxGrid에 출력될 순서와 동일해야됨[whoya도 마찬가지임])
			Map<String, Object> gridMap = gridParse(list, new String[]{"LV", "ACCD", "ACNM", "HACCD", "DCDV", "WRTYN", "CONYN", "USEYN", "INSID", "INSDT"});
			resultList.put("list", gridMap);
			resultList.put("status", commonReturn.SUCCESS);
			resultList.put("message", "조회되었습니다");
	  	} catch(Exception e){
	  		resultList.put("status", commonReturn.FAIL);
	  		resultList.put("message", e.getMessage());
	  		throw e;
	  	}
		return resultList;
	}

	/* 계정과목 저장 */
	@RequestMapping(value="whoya/content/ac/ACI0010_U.do")
	public @ResponseBody void ACI0010Ru( HttpServletRequest request, HttpServletResponse response ) throws Exception {

		commonSession session = commonSessionUtil.getCommonSession();
		

     	String[] ids = request.getParameter("ids").split(",");
     	
     	try {
	     	// 추가, 수정, 삭제된 항목 개수만큼
	     	for ( int i = 0; i < ids.length; i++ ) {
	     		Map<String, String> params = new HashMap<String, String>();
	    		params.put("coId", session.getCoId());
	    		params.put("usrId", session.getUsrId());
	     		params.put("acCd" , request.getParameter(ids[i] + "_ACCD"));
		    	params.put("acNm" , request.getParameter(ids[i] + "_ACNM"));
		    	params.put("hAcCd", request.getParameter(ids[i] + "_HACCD"));
		    	params.put("wrtYn", request.getParameter(ids[i] + "_WRTYN"));
		    	params.put("conYn", request.getParameter(ids[i] + "_CONYN"));
		    	params.put("useYn", request.getParameter(ids[i] + "_USEYN"));
		    	params.put("usrId", request.getParameter(ids[i] + "_INSID"));
		    	
		    	if (request.getParameter(ids[i] + "_!nativeeditor_status").equals("inserted") ) { ACI0010_Service.ACI0010_Ui(params); }
		    	if (request.getParameter(ids[i] + "_!nativeeditor_status").equals("updated" ) ) { ACI0010_Service.ACI0010_Uu(params); }
		    	if (request.getParameter(ids[i] + "_!nativeeditor_status").equals("deleted" ) ) { ACI0010_Service.ACI0010_Ud(params); }
	     	}
		
    		data.dataRefresh(request, response);
	    } catch(NumberFormatException e){
	    	System.out.println("@@@@@@@@@@@@@@@@@@@>"+e+"<@@@@@@@@@@@@@@@@@@@");
	    	throw e;
    	} 	
    }
	
	/**
	 * dhtmlxGrid의 parse에서 인식할 수 있는 형식으로 변경
	 * ( ex> rows:[{ id:1, data:["100","A Time to Kill","John Grisham"] }, { id: 2, data:["100","A Time to Kill","John Grisham"] }] )
	 * @param list
	 * @param gridKeys
	 * @return
	 */
	public Map<String, Object> gridParse(List<Map<String, String>> list, String[] gridKeys) {
		Map<String, Object> gridMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> gridList = new ArrayList<Map<String, Object>>();
		
		for ( int i = 0; i < list.size(); i++ ) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", i);
			String[] gridArr = new String[gridKeys.length];
			
			for ( int j = 0; j < gridKeys.length; j++ ) {
				gridArr[j] = (list.get(i).get(gridKeys[j]) == null ? "" : list.get(i).get(gridKeys[j]));
			}
			
			map.put("data", gridArr);
			gridList.add(map);
		}
		
		gridMap.put("rows", gridList);
		
		return gridMap;
	}
}
