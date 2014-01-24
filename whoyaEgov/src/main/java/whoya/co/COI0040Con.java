package whoya.co;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.common.commonReturn;
import whoya.common.commonSession;
import whoya.common.commonSessionUtil;


@Controller
public class COI0040Con extends MultiActionController {

	@Resource(name="COI0040Svc")
	private COI0040Svc COI0040Service;
	private whoyaMap commonParam;
	
	@RequestMapping(value="whoya/content/co/COI0040.do")
	public ModelAndView COI0040() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/content/co/COI0040");
		return mav;
	}
	
	/*시스템구분콤보*/
	@RequestMapping(value="whoya/content/co/COI0040Rc_sysDv.do", headers="Accept=application/json")
	public @ResponseBody JSONObject COI0040Rc_sysDv( HttpServletRequest request
			                                        ,HttpServletResponse response 
								 		            ,@RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		
		params.put("coId", session.getCoId());

	    JSONObject resultList = new JSONObject();
	    JSONArray jsonArray = new JSONArray();
		
		try {	whoyaList list = COI0040Service.COI0040Rc_sysDv(params);
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
	
	/*공통코드트리*/
	@RequestMapping(value="whoya/content/co/COI0040Rt.do", headers="Accept=application/json")
	public @ResponseBody JSONObject COI0040Rt( HttpServletRequest request
			                                  ,HttpServletResponse response 
								 		      ,@RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		
		params.put("coId", session.getCoId());

	    JSONObject resultList = new JSONObject();
	    JSONArray jsonArray = new JSONArray();
		
		try {	whoyaList list = COI0040Service.COI0040Rt(params);
			 	if(list != null && list.size() > 0){
					for (int i = 0; i < list.size(); i++) {
					    JSONArray jArray = new JSONArray();
					    jArray.add(((whoyaMap)list.get(i)).getString("CD"));
					    jArray.add(((whoyaMap)list.get(i)).getString("HCD"));
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
	@RequestMapping(value="whoya/content/co/COI0040Rs.do", headers="Accept=application/json")
	public @ResponseBody JSONObject COI0040Rs( HttpServletRequest request
			                                 , HttpServletResponse response 
										     , @RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		System.out.println(session.getUsrId());
		
		whoyaMap params = new whoyaMap();
		params.put("coId", session.getCoId());
		//params.put("dptCd", ServletRequestUtils.getStringParameter(request, "dptCd"));
		params.put("hCd", ServletRequestUtils.getStringParameter(request, "hCd"));
		//System.out.println(ServletRequestUtils.getStringParameter(request, "usrNm"));

	    JSONObject resultReturn = new JSONObject();
	    JSONObject result = new JSONObject();
	    JSONObject resultRows = new JSONObject();

	    JSONArray jsonArray = new JSONArray();
		
		try {	whoyaList list = COI0040Service.COI0040Rs(params);

			 	if(list != null && list.size() > 0){
					for (int i = 0; i < list.size(); i++) {
					    resultRows.put("id", ((whoyaMap)list.get(i)).getString("RNO"));
					    
					    JSONArray jsonArrayFor = new JSONArray();
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("SEQ"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("CD"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("CDNM"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("HCD"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("MGTITM1"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("MGTITM2"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("MGTITM3"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("MGTITM4"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("MGTITM5"));
					    jsonArrayFor.add(((whoyaMap)list.get(i)).getString("USEYN"));
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
