package co;

import java.util.Map; 

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.COI0020Svc;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.whoyaLib;

import common.commonEncrypt;
import common.commonSession;
import common.commonReturn;
import common.commonSessionUtil;

@Controller
public class COI0020Con extends MultiActionController {

	@Resource(name="COI0020Svc")
	private COI0020Svc COI0020Service;
	private whoyaMap commonParam;
	
	@RequestMapping(value="content/co/COI0020.do")
	public ModelAndView COI0020() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("content/co/COI0020");
		return mav;
	}
	
	/*부서콤보*/
	@RequestMapping(value="content/co/COI0020Rc_mnuDv.do", headers="Accept=application/json")
	public @ResponseBody JSONObject COI0020Rc_mnuDv( HttpServletRequest request
			                                       ,HttpServletResponse response 
										           ,@RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		
		params.put("coId", session.getCoId());

	    JSONObject resultList = new JSONObject();
	    JSONArray jsonArray = new JSONArray();
		
		try {	whoyaList list = COI0020Service.COI0020Rc_mnuDv(params);
				JSONArray jArrayAll = new JSONArray();
				jArrayAll.add("");
				jArrayAll.add("전체");
				jsonArray.add(jArrayAll);
			 	if(list != null && list.size() > 0){
					for (int i = 0; i < list.size(); i++) {
					    JSONArray jArray = new JSONArray();
					    jArray.add(((whoyaMap)list.get(i)).getString("MNUID"));
					    jArray.add(((whoyaMap)list.get(i)).getString("MNUNM"));
					    jsonArray.add(jArray);
					}	
				}	
			 	resultList.put("list", jsonArray);
			 	resultList.put("status", commonReturn.SUCCESS);
			 	resultList.put("message", "조회되었습니다");
				
	  	} catch(Exception e){
	  			resultList.put("status", commonReturn.FAIL);
	  			resultList.put("message", e.getMessage());
	  			throw e;
	  	}
		return resultList;
	}
	
	/*직무콤보*/
	@RequestMapping(value="content/co/COI0020Rc_mnuFnc.do", headers="Accept=application/json")
	public @ResponseBody JSONObject COI0020Rc_mnuFnc( HttpServletRequest request
			                                       ,HttpServletResponse response 
										           ,@RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		
		params.put("coId", session.getCoId());

	    JSONObject resultList = new JSONObject();
	    JSONArray jsonArray = new JSONArray();
		
		try {	whoyaList list = COI0020Service.COI0020Rc_mnuFnc(params);
				JSONArray jArrayAll = new JSONArray();
				jArrayAll.add("");
				jArrayAll.add("전체");
				jsonArray.add(jArrayAll);
			 	if(list != null && list.size() > 0){
					for (int i = 0; i < list.size(); i++) {
					    JSONArray jArray = new JSONArray();
					    jArray.add(((whoyaMap)list.get(i)).getString("CD"));
					    jArray.add(((whoyaMap)list.get(i)).getString("CDNM"));
					    jsonArray.add(jArray);
					}	
				}	
			 	resultList.put("list", jsonArray);
			 	resultList.put("status", commonReturn.SUCCESS);
			 	resultList.put("message", "조회되었습니다");
				
	  	} catch(Exception e){
	  			resultList.put("status", commonReturn.FAIL);
	  			resultList.put("message", e.getMessage());
	  			throw e;
	  	}
		return resultList;
	}

	@RequestMapping(value="content/co/COI0020Rs.do", headers="Accept=application/json")
	public @ResponseBody JSONObject COI0020Rs( HttpServletRequest request
			                                 , HttpServletResponse response 
										     , @RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		//System.out.println(session.getUsrId());
		
		whoyaMap params = new whoyaMap();
		params.put("coId", session.getCoId());
		params.put("dptCd", ServletRequestUtils.getStringParameter(request, "dptCd"));
		params.put("usrNm", ServletRequestUtils.getStringParameter(request, "usrNm"));
		//System.out.println(ServletRequestUtils.getStringParameter(request, "usrNm"));

	    JSONObject resultReturn = new JSONObject();
	    JSONObject result = new JSONObject();
	    JSONObject resultRows = new JSONObject();

	    JSONArray jsonArray = new JSONArray();
		
		try {	whoyaList list = COI0020Service.COI0020Rs(params);

			 	if(list != null && list.size() > 0){
					for (int i = 0; i < list.size(); i++) {
					    resultRows.put("id", ((whoyaMap)list.get(i)).getString("RNO"));
					    JSONArray jsonArrayFor = new JSONArray();
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("LV"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("SEQ"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("MNUID"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("MNUNM"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("HMNUID"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("MNUFNC"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("MNUCNTN"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("INSDT"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("INSID"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("UPDDT"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("UPDID"));
					    resultRows.put("data", jsonArrayFor);
					    jsonArray.add(resultRows);
					}	
					result.put("rows", jsonArray);
				}	
			 	resultReturn.put("list", result);
			 	resultReturn.put("status", commonReturn.SUCCESS);
			 	resultReturn.put("message", "조회되었습니다");
	  	} catch(Exception e){
	  			result.put("status", commonReturn.FAIL);
	  			result.put("message", e.getMessage());
	  			throw e;
	  	}
		return resultReturn;
	}
}
