package co;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.COI0010Svc;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import whoya.whoyaLib;
import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.whoyaDataProcess;

import common.commonSession;
import common.commonReturn;
import common.commonSessionUtil;


@Controller
public class COI0010Con extends MultiActionController {

	@Resource(name="COI0010Svc")
	private COI0010Svc COI0010Service;
	
	@RequestMapping(value="content/co/COI0010.do")
	public ModelAndView COI0010() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("content/co/COI0010");
		return mav;
	}
	
	/* 부서 조회 */
	@RequestMapping(value="content/co/COI0010Rc_dptCd.do", headers="Accept=application/json")
	public @ResponseBody JSONObject COI0010Rc_dptCd( HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		
		params.put("coId", session.getCoId());

	    JSONObject resultList = new JSONObject();
		
		try {	whoyaList list = COI0010Service.COI0010Rc_dptCd(params);
			 	resultList.put("list", whoyaLib.whoyaRenderCombo(list, "CD,CDNM"));
			 	resultList.put("status", commonReturn.SUCCESS);
			 	resultList.put("message", "조회되었습니다");
	  	} catch(Exception e){
	  			resultList.put("status", commonReturn.FAIL);
	  			resultList.put("message", e.getMessage());
	  			throw e;
	  	}
		/* return getJsonView(response, whoyaList); */
		return resultList;
	}
	
	/* 직위 조회 */
	@RequestMapping(value="content/co/COI0010Rc_rolCd.do", headers="Accept=application/json")
	public @ResponseBody JSONObject COI0010Rc_rolCd( HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		
		params.put("coId", session.getCoId());

	    JSONObject resultList = new JSONObject();
		
		try {	whoyaList list = COI0010Service.COI0010Rc_rolCd(params);
	 			resultList.put("list", whoyaLib.whoyaRenderCombo(list, "CD,CDNM"));
			 	resultList.put("status", commonReturn.SUCCESS);
			 	resultList.put("message", "조회되었습니다");
	  	} catch(Exception e){
	  			resultList.put("status", commonReturn.FAIL);
	  			resultList.put("message", e.getMessage());
	  			throw e;
	  	}
		return resultList;
	}

	/* 직무 조회 */
	@RequestMapping(value="content/co/COI0010Rc_rolId.do", headers="Accept=application/json")
	public @ResponseBody JSONObject COI0010Rc_rolId( HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		
		params.put("coId", session.getCoId());

	    JSONObject resultList = new JSONObject();
		
		try {	whoyaList list = COI0010Service.COI0010Rc_rolId(params);
				resultList.put("list", whoyaLib.whoyaRenderCombo(list, "CD,CDNM"));
			 	resultList.put("status", commonReturn.SUCCESS);
			 	resultList.put("message", "조회되었습니다");
	  	} catch(Exception e){
	  			resultList.put("status", commonReturn.FAIL);
	  			resultList.put("message", e.getMessage());
	  			throw e;
	  	}
		return resultList;
	}

	/* 작업그룹 조회 */
	@RequestMapping(value="content/co/COI0010Rc_grpId.do", headers="Accept=application/json")
	public @ResponseBody JSONObject COI0010Rc_grpId( HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		params.put("coId", session.getCoId());

	    JSONObject resultList = new JSONObject();
		
		try {	whoyaList list = COI0010Service.COI0010Rc_grpId(params);
				resultList.put("list", whoyaLib.whoyaRenderCombo(list, "CD,CDNM"));
			 	resultList.put("status", commonReturn.SUCCESS);
			 	resultList.put("message", "조회되었습니다");
	  	} catch(Exception e){
	  			resultList.put("status", commonReturn.FAIL);
	  			resultList.put("message", e.getMessage());
	  			throw e;
	  	}
		return resultList; 
	}

	/* 사용자 조회 */
	@RequestMapping(value="content/co/COI0010Rs.do", headers="Accept=application/json")
	public @ResponseBody JSONObject COI0010Rs( HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> paramMap )
    throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		params.put("coId", session.getCoId());
		params.put("dptCd", ServletRequestUtils.getStringParameter(request, "dptCd"));
		params.put("usrNm", ServletRequestUtils.getStringParameter(request, "usrNm"));
		//System.out.println(ServletRequestUtils.getStringParameter(request, "usrNm"));

	    JSONObject resultList = new JSONObject();
		
		try {	whoyaList list = COI0010Service.COI0010Rs(params);
				resultList.put("list", whoyaLib.whoyaRenderGrid(list, "USRID,USRNM,PWD,ROLCD,ROLID,GRPID,DPTCD,TELNO,HPNNO,USEYN,INSID,INSDT"));
				resultList.put("status", commonReturn.SUCCESS);
				resultList.put("message", "조회되었습니다");
				
	  	} catch(Exception e){
	  			resultList.put("status", commonReturn.FAIL);
	  			resultList.put("message", e.getMessage());
	  			throw e;
	  	}
		return resultList;
	}

	/* 사용자 저장 */
	@RequestMapping(value="content/co/COI0010Ru.do")
	public @ResponseBody void COI0010Ru( HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		params.put("coId", session.getCoId());

     	String[] ids = request.getParameter("ids").split(",");
		
	    whoyaDataProcess  data = new whoyaDataProcess();

	    whoyaMap rows = new whoyaMap();
	    whoyaMap cols = new whoyaMap();

	    rows = data.dataProcess(request, response);
	    
	    for (int i = 0; i < ids.length; i++) {
		    cols = (whoyaMap) rows.get(ids[i]);
		    params.put("usrId", cols.get("USRID"));
		    params.put("usrNm", cols.get("USRNM"));
		    params.put("pwd"  , cols.get("PWD"));
		    params.put("rolCd", cols.get("ROLCD"));
		    params.put("rolId", cols.get("ROLID"));
		    params.put("grpId", cols.get("GRPID"));
		    params.put("dptCd", cols.get("DPTCD"));
		    params.put("telNo", cols.get("TELNO"));
		    params.put("hpnNo", cols.get("HPNNO"));
		    params.put("useYn", cols.get("USEYN"));
		    System.out.println("===================================");
		    System.out.println(ids[i]+":"+cols.get("USRID")+":"+cols.get("!nativeeditor_status"));
		    System.out.println("===================================");
		    if (cols.get("!nativeeditor_status").equals("inserted") ) { COI0010Service.COI0010Is(params); }   
		    if (cols.get("!nativeeditor_status").equals("updated" ) ) { COI0010Service.COI0010Us(params); }   
		    if (cols.get("!nativeeditor_status").equals("deleted" ) ) { COI0010Service.COI0010Ds(params); }  
	    }
	}	
}
