package ac;

import java.util.Map; 

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.ACI0010Svc;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import whoya.whoyaDataProcess;
import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.whoyaLib;

import common.commonSession;
import common.commonReturn;
import common.commonSessionUtil;

@Controller
public class ACI0010Con extends MultiActionController {

	@Resource(name="ACI0010Svc")
	private ACI0010Svc ACI0010Service;
	
	@RequestMapping(value="content/ac/ACI0010.do")
	public ModelAndView ACI0010() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("content/ac/ACI0010");
		return mav;
	}
	
	/*계정과목콤보*/
	@RequestMapping(value="content/ac/ACI0010Rc_acCd.do", headers="Accept=application/json")
	public @ResponseBody JSONObject ACI0010Rc_acCd( HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		params.put("coId", session.getCoId());

	    JSONObject resultList = new JSONObject();
		
		try {	whoyaList list = ACI0010Service.ACI0010Rc_acCd(params);
			 	resultList.put("list", whoyaLib.whoyaRenderCombo(list, "ACCD,ACNM"));
			 	resultList.put("status", commonReturn.SUCCESS);
			 	resultList.put("message", "조회되었습니다");
				
	  	} catch(Exception e){
	  			resultList.put("status", commonReturn.FAIL);
	  			resultList.put("message", e.getMessage());
	  			throw e;
	  	}
		return resultList;
	}
	
	@RequestMapping(value="content/ac/ACI0010Rs.do", headers="Accept=application/json")
	public @ResponseBody JSONObject ACI0010Rs( HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		System.out.println(session.getUsrId());
		
		whoyaMap params = new whoyaMap();
		params.put("coId", session.getCoId());
		params.put("acCd", ServletRequestUtils.getStringParameter(request, "acCd"));

	    JSONObject resultList = new JSONObject();
		
		try { whoyaList list = ACI0010Service.ACI0010Rs(params);
			  resultList.put("list", whoyaLib.whoyaRenderGrid(list, "LV,ACCD,ACNM,HACCD,DCDV,WRTYN,CONYN,USEYN,INSID,INSDT"));
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
	@RequestMapping(value="content/ac/ACI0010U.do")
	public @ResponseBody void ACI0010Ru( HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		params.put("coId", session.getCoId());
		params.put("usrId", session.getUsrId());

     	String[] ids = request.getParameter("ids").split(",");
		
	    whoyaDataProcess  data = new whoyaDataProcess();

	    whoyaMap rows = new whoyaMap();
	    whoyaMap cols = new whoyaMap();

	    rows = data.dataProcess(request, response);
	    
	    for (int i = 0; i < ids.length; i++) {
		    cols = (whoyaMap) rows.get(ids[i]);
		    params.put("acCd" , cols.get("ACCD"));
		    params.put("acNm" , cols.get("ACNM"));
		    params.put("hAcCd", cols.get("HACCD"));
		    params.put("wrtYn", cols.get("WRTYN"));
		    params.put("conYn", cols.get("CONYN"));
		    params.put("useYn", cols.get("USEYN"));
		    params.put("usrId", cols.get("INSID"));
		    System.out.println("===================================");
		    System.out.println(cols.get("USRID")+":"+cols.get("!nativeeditor_status"));
		    System.out.println("===================================");
		    if (cols.get("!nativeeditor_status").equals("inserted") ) { ACI0010Service.ACI0010Ui(params); }   
		    if (cols.get("!nativeeditor_status").equals("updated" ) ) { ACI0010Service.ACI0010Uu(params); }   
		    if (cols.get("!nativeeditor_status").equals("deleted" ) ) { ACI0010Service.ACI0010Ud(params); }  
	    }
	}	
}
