package whoya.hr;

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

import whoya.whoyaLib;
import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.whoyaDataProcess;
import whoya.common.commonReturn;
import whoya.common.commonSession;
import whoya.common.commonSessionUtil;
import whoya.hr.HRI0010Svc;



@Controller
public class HRI0010Con extends MultiActionController {

	@Resource(name="HRI0010Svc")
	private HRI0010Svc HRI0010Service;
	
	@RequestMapping(value="whoya/content/hr/HRI0010.do")
	public ModelAndView HRI0010() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/content/hr/HRI0010");
		return mav;
	}
	
	/* 부서 조회 */
	@RequestMapping(value="whoya/content/hr/HRI0010Rc_dptCd.do")
	public @ResponseBody void HRI0010Rc_dptCd( HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		
		params.put("coId", session.getCoId());

	    JSONObject resultList = new JSONObject();
		
		try {	whoyaList list = HRI0010Service.HRI0010Rc_dptCd(params);
			 	resultList.put("list", whoyaLib.whoyaRenderFormCombo(list, "CD,CDNM", response));
			 	resultList.put("status", commonReturn.SUCCESS);
			 	resultList.put("message", "조회되었습니다");
	  	} catch(Exception e){
	  			resultList.put("status", commonReturn.FAIL);
	  			resultList.put("message", e.getMessage());
	  			throw e;
	  	}
	}
	
	/* 부서 조회 */
	@RequestMapping(value="whoya/content/hr/HRI0010Rc_dptCd2.do", headers="Accept=application/json")
	public @ResponseBody JSONObject HRI0010Rc_dptCd2( HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		
		params.put("coId", session.getCoId());

	    JSONObject resultList = new JSONObject();
		
		try {	whoyaList list = HRI0010Service.HRI0010Rc_dptCd(params);
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

	/* 재직구분 공통코드 조회 */
	@RequestMapping(value="whoya/content/hr/HRI0010Rc_jgicCd.do")
	public @ResponseBody void HRI0010Rc_jgicCd( HttpServletRequest request, HttpServletResponse response
    ) throws Exception {		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		
		params.put("coId", session.getCoId());
	    JSONObject resultList = new JSONObject();
		
		try {	whoyaList list = HRI0010Service.HRI0010Rc_jgicCd(params);
	 			resultList.put("options", whoyaLib.whoyaRenderFormCombo(list, "CD,CDNM", response));
			 	resultList.put("status", commonReturn.SUCCESS);
			 	resultList.put("message", "조회되었습니다");
	  	} catch(Exception e){
	  			resultList.put("status", commonReturn.FAIL);
	  			resultList.put("message", e.getMessage());
	  			throw e;
	  	}
	}

	/* 직위 조회 */
	@RequestMapping(value="whoya/content/hr/HRI0010Rc_rolCd.do")
	public @ResponseBody void HRI0010Rc_rolCd( HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		
		params.put("coId", session.getCoId());

	    JSONObject resultList = new JSONObject();
		
		try {	whoyaList list = HRI0010Service.HRI0010Rc_rolCd(params);
	 			resultList.put("list", whoyaLib.whoyaRenderFormCombo(list, "CD,CDNM", response));
			 	resultList.put("status", commonReturn.SUCCESS);
			 	resultList.put("message", "조회되었습니다");
	  	} catch(Exception e){
	  			resultList.put("status", commonReturn.FAIL);
	  			resultList.put("message", e.getMessage());
	  			throw e;
	  	}
	}

	/* 직무 조회 */
	@RequestMapping(value="whoya/content/hr/HRI0010Rc_rolId.do", headers="Accept=application/json")
	public @ResponseBody JSONObject HRI0010Rc_rolId( HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		
		params.put("coId", session.getCoId());

	    JSONObject resultList = new JSONObject();
		
		try {	whoyaList list = HRI0010Service.HRI0010Rc_rolId(params);
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
	@RequestMapping(value="whoya/content/hr/HRI0010Rc_grpId.do", headers="Accept=application/json")
	public @ResponseBody JSONObject HRI0010Rc_grpId( HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> paramMap
    ) throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		params.put("coId", session.getCoId());

	    JSONObject resultList = new JSONObject();
		
		try {	whoyaList list = HRI0010Service.HRI0010Rc_grpId(params);
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
	@RequestMapping(value="whoya/content/hr/HRI0010Rs.do", headers="Accept=application/json")
	public @ResponseBody JSONObject HRI0010Rs( HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> paramMap )
    throws Exception {
		
		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		params.put("coId", session.getCoId());
		params.put("wrkDptCd", ServletRequestUtils.getStringParameter(request, "wrkDptCd"));
		params.put("empNmKo", ServletRequestUtils.getStringParameter(request, "empNmKo"));
		
	    JSONObject resultList = new JSONObject();
		
		try {	whoyaList list = HRI0010Service.HRI0010Rs(params);
				resultList.put("list", whoyaLib.whoyaRenderGrid(list
						, "EMPNO,EMPNMKO,EMPNMHN,EMPNMEN,RSNO,PNGDIR,SEX,BIRDD,BIRDV,MARYN,MARDD,"
                        + "POSID,JGBID,ROLID,HBG,RGNID,RNMID,WRKDPTCD,ADDDPTCD,SUPDPTCD,SENDPTCD,EMAIL,COTELNO,LNTELNO,HPTELNO,HMTELNO,"
                        + "POSTNO,ADDR1,ADDR2,WRKDV,DNDDV,TANDV,RCUDV,RCUCNTN,ROLSETDD,DPTSETDD,HOLDV,"
		                + "INTDD,OUTDD,OUTCNTN,INSDT,INSID,UPDDT,UPDID"
                ));
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
	@RequestMapping(value="whoya/content/hr/HRI0010Ru.do")
	public @ResponseBody void HRI0010Ru( HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

		commonSession session = commonSessionUtil.getCommonSession();
		whoyaMap params = new whoyaMap();
		params.put("coId", session.getCoId());

     	String[] ids = request.getParameter("ids").split(",");
     	
     	System.out.println(ids);
		
	    whoyaDataProcess  data = new whoyaDataProcess();

	    whoyaMap rows = new whoyaMap();
	    whoyaMap cols = new whoyaMap();

	    rows = data.dataProcess(request);
	    
	    for (int i = 0; i < ids.length; i++) {
		    cols = (whoyaMap) rows.get(ids[i]);
		    params.put("empNo"   , cols.get("EMPNO"));
		    params.put("empNmKo" , cols.get("EMPNMKO"));
		    params.put("empNmHn" , cols.get("EMPNMHN"));
		    params.put("empNmEn" , cols.get("EMPNMEN"));
		    params.put("rsNo"    , cols.get("RSNO"));
		    params.put("pngDir"  , cols.get("PNGDIR"));
		    params.put("sex"     , cols.get("SEX"));
		    params.put("birDD"   , cols.get("BIRDD"));
		    params.put("birDv"   , cols.get("BIRDV"));
		    params.put("marYn"   , cols.get("MARYN"));
		    params.put("marDD"   , cols.get("MARDD"));
		    params.put("posId"   , cols.get("POSID"));
		    params.put("jgbId"   , cols.get("JGBID"));
		    params.put("rolId"   , cols.get("ROLID"));
		    params.put("hbg"     , cols.get("HBG"));
		    params.put("rgnId"   , cols.get("RGNID"));
		    params.put("rnmId"   , cols.get("RNMID"));
		    params.put("wrkDptCd", cols.get("WRKDPTCD"));
		    params.put("addDptCd", cols.get("ADDDPTCD"));
		    params.put("supDptCd", cols.get("SUPDPTCD"));
		    params.put("senDptCd", cols.get("SENDPTCD"));
		    params.put("eMail"   , cols.get("EMAIL"));
		    params.put("coTelNo" , cols.get("COTELNO"));
		    params.put("lnTelNo" , cols.get("LNTELNO"));
		    params.put("hpTelNo" , cols.get("HPTELNO"));
		    params.put("hmTelNo" , cols.get("HMTELNO"));
		    params.put("postNo"  , cols.get("POSTNO"));
		    params.put("addr1"   , cols.get("ADDR1"));
		    params.put("addr2"   , cols.get("ADDR2"));
		    params.put("wrkDv"   , cols.get("WRKDV"));
		    params.put("dndDv"   , cols.get("DNDDV"));
		    params.put("tanDv"   , cols.get("TANDV"));
		    params.put("rcuDv"   , cols.get("RCUDV"));
		    params.put("rcuCntn" , cols.get("RCUCNTN"));
		    params.put("rolSetDD", cols.get("ROLSETDD"));
		    params.put("dptSetDD", cols.get("DPTSETDD"));
		    params.put("holDv"   , cols.get("HOLDV"));
		    params.put("intDD"   , cols.get("INTDD"));
		    params.put("outDD"   , cols.get("OUTDD"));
		    params.put("outCntn" , cols.get("OUTCNTN"));
		    params.put("insDT"   , cols.get("INSDT"));
		    params.put("insId"   , cols.get("INSID"));
		    params.put("updDT"   , cols.get("UPDDT"));
		    params.put("updId"   , cols.get("UPDID"));
		    
		    if (cols.get("!nativeeditor_status").equals("inserted") ) { HRI0010Service.HRI0010Is(params); }   
		    if (cols.get("!nativeeditor_status").equals("updated" ) ) { HRI0010Service.HRI0010Us(params); }   
		    if (cols.get("!nativeeditor_status").equals("deleted" ) ) { HRI0010Service.HRI0010Ds(params); }  
	    }
	    
	    data.dataRefresh(request, response);
	}	
}
