package com.aps.rarp.sy.web;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rim.rmcc.RMCC;

import com.aps.rarp.co.RarpCoLoginSessionManager;
import com.aps.rarp.co.web.RarpCoWebAbstarctController;
import com.aps.rarp.hs.model.RarpHsHistoryDAO;
import com.aps.rarp.sy.model.RarpSySystemDAO;


/**
* 메인페이지 처리를 위한  Controller 구현 클래스
* <p><b>NOTE:</b>
* @author 
* @since 2012.02.26
* @see
*
* <pre>
* == 개정이력(Modification Information) ==
*
* 수정일       수정자    수정내용
* ---------- ------- ---------------------------
* 2012.02.26     
*
* Copyright (C) 2013 by APS. All right reserved.
* </pre>
*/
@Controller
public class RarpSySystemController extends RarpCoWebAbstarctController{

	protected static final Logger logger = Logger.getLogger(RarpSySystemController.class);
	
	@Resource(name="rarpSySystemDAO")
	private RarpSySystemDAO rarpSySystemDAO;
	
	@Resource(name="rarpHsHistoryDAO")
	private RarpHsHistoryDAO rarpHsHistoryDAO;
	
	@Resource(name="loginSessionManager")
	private RarpCoLoginSessionManager loginSessionManager;
	/**
	 * 태그 상태
	 */
    @RequestMapping(value = "/sy/bomPart.do")
    public String initBomPart(ModelMap model,@RequestParam HashMap<String, String> paramMap) throws Exception {
    	model.put("MENU", "RF01");
    	model.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
    	model.put("PARAM", paramMap);
    	List<HashMap<String, String>> bomMapList = rarpSySystemDAO.selectBomAllList(paramMap);
    	model.put("BOM_ALL_DATA", bomMapList);
        return "/sy/part";
    }
	
	/**
	 * 차량 사업소
	 * @param session
	 * 
	 * @param request
	 * @param model
	 * @param paramMap
	 * @exception Exception Exception
	 */
	@RequestMapping(value = "/sy/selectRsmoInfoList.do")
	public String selectRsmoInfoList(HttpServletRequest request, ModelMap model, HashMap<String, String> paramMap)
	  throws Exception{
		try
		{
	    	List<HashMap<String, String>> rsmoMapList = rarpSySystemDAO.selectRsmoInfoList(paramMap);
	    	model.put("data", rsmoMapList);
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return "jsonView";
	}
	
	/**
	 * 편성 정보
	 */
	@RequestMapping(value = "/sy/selectPrgInfoList.do")
	public String selectPrgInfoList(ModelMap model,  @RequestParam HashMap<String, String> paramMap)
	  throws Exception{
		try
		{
			paramMap.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
	    	List<HashMap<String, String>> prgMapList = rarpSySystemDAO.selectPrgInfoList(paramMap);
	    	model.put("data", prgMapList);
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return "jsonView";
	}
	/**
	 * 차호정보
	 */
	@RequestMapping(value = "/sy/selectCrgInfoList.do")
	public String selectCrgInfoList(ModelMap model,  @RequestParam HashMap<String, String> paramMap)
	  throws Exception{
		try
		{
			paramMap.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
	    	List<HashMap<String, String>> crgMapList = rarpSySystemDAO.selectCrgInfoList(paramMap);
	    	model.put("data", crgMapList);
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return "jsonView";
	}
	/**
	 * 차호정보
	 */
	@RequestMapping(value = "/sy/selectCrgTypeInfo")
	public String selectCrgTypeInfo(ModelMap model, @RequestParam HashMap<String, String> paramMap)
	  throws Exception{
		try
		{
			paramMap.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
            paramMap.put("COMM_DTL_ID", paramMap.get("CRG_TYPE_CD"));

			paramMap.put("COMM_ID", "A05");
	    	HashMap<String, String>  mapData = rarpSySystemDAO.selectCommDtlInfo(paramMap);
	    	model.put("data", mapData);
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return "jsonView";
	}

	/**
	 * BOM 정보 조회
	 * 
	 * @param model
	 * @param paramMap
	 * @exception Exception
	 */
	@RequestMapping(value = "/sy/selectBomList")
	public String selectBomList(ModelMap model, @RequestParam HashMap<String, String> paramMap)
	  throws Exception{
		
    	try
    	{
	    	paramMap.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
	    	
			HashMap<String, String> crgMap = rarpSySystemDAO.selectCrgInfo(paramMap);
            paramMap.put("CRG_TYPE_CD", crgMap.get("CRG_TYPE_CD"));
            
    		List<HashMap<String, Object>> resultList = rarpSySystemDAO.selectBomList(paramMap);
    		model.put("rows", resultList);

			
		} catch (Exception e) {
			//model.put("result", "400");
		}
		return "jsonView";
	}

	/**
	 * 개별 bom 정보
	 * 
	 * @param model
	 * @param paramMap
	 * @exception Exception
	 */
	@RequestMapping(value = "/sy/selectBomInfo")
	public String selectBomInfo(ModelMap model, @RequestParam  HashMap<String, String> paramMap)
	  throws Exception{
		try
    	{
	    	paramMap.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
	    	
			HashMap<String, String> bomMap = rarpSySystemDAO.selectBomInfo(paramMap);
			model.put("data", bomMap);
			
		} catch (Exception e) {

		}
		return "jsonView";
	}
	
	/**
	 * 부품 상세 정보 
	 */
	@RequestMapping(value = "/sy/selectPartDtlList")
	public String selectPartDtlList(ModelMap model,  @RequestParam HashMap<String, String> paramMap)
	  throws Exception{
		try
		{
			paramMap.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
           // paramMap.put("OR_PARENT_BOM_ID", paramMap.get("BOM_ID"));
            
	    	List<HashMap<String, String>> mapList = rarpSySystemDAO.selectPartDtlList(paramMap);
	    	model.put("rows", mapList);
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return "jsonView";
	}
	/**
	 * 부품 상세 정보 
	 */
	@RequestMapping(value = "/sy/selectPartDetailList")
	public String selectPartDetailList(ModelMap model,  @RequestParam HashMap<String, String> paramMap)
	  throws Exception{
		try
		{
			paramMap.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
           // paramMap.put("OR_PARENT_BOM_ID", paramMap.get("BOM_ID"));
            
	    	List<HashMap<String, String>> mapList = rarpSySystemDAO.selectPartDetailList(paramMap);
	    	model.put("rows", mapList);
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return "jsonView";
	}
	
	/**
	 * BOM PATH 정보 
	 */
	@RequestMapping(value = "/sy/selectBomPathInfo")
	public String selectBomPathInfo(ModelMap model,  @RequestParam HashMap<String, String> paramMap)
	  throws Exception{
		try
		{
			paramMap.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
           // paramMap.put("OR_PARENT_BOM_ID", paramMap.get("BOM_ID"));
            
	    	HashMap<String, String> mapPath = rarpSySystemDAO.selectBomPathInfo(paramMap);
	    	model.put("data", mapPath);
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return "jsonView";
	}
	
	/**
	 * 태그 정보 조회 
	 */
	@RequestMapping(value = "/sy/selectTagInfo")
	public String selectTagInfo(ModelMap model,  @RequestParam HashMap<String, String> paramMap)
	  throws Exception{
		try
		{
			HashMap<String, String> mapTag = null;
			paramMap.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
            String sLvl = (String)paramMap.get("PART_SN");
            if(sLvl.equals(""))
            {
            	mapTag = rarpSySystemDAO.selectCrgInfo(paramMap);
            }
            else
            {
            	mapTag = rarpSySystemDAO.selectPartDtlInfo(paramMap);
            }
            
	    	model.put("data", mapTag);
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return "jsonView";
	}
	/**
	 * 초기화 명령
	 */
	@RequestMapping(value = "/sy/exceuteCmd")
	public String exceuteCmd(ModelMap model,  @RequestParam HashMap<String, String> paramMap)
	  throws Exception{
		boolean resetResult = false;
		try
		{
			paramMap.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
			String sType = String.valueOf(paramMap.get("TYPE"));
		    if(sType.equals("M"))
		    {
		    	resetResult = RMCC.getInstance().getRmccAPI().resetMiddleware(String.valueOf(paramMap.get("INFRA_RSC_ID")), String.valueOf(paramMap.get("CMD"))); 
		    }
		    else
		    {
		    	resetResult = RMCC.getInstance().getRmccAPI().resetReader(String.valueOf(paramMap.get("INFRA_RSC_ID")), String.valueOf(paramMap.get("CMD")));
		    }
		    model.put("RESULT", resetResult);
		}
		catch(Exception ex)
		{
			logger.error(ex);
			model.put("RESULT", resetResult);
		}
		return "jsonView";
	}
	
	/**
	 * 리포트
	 */
	@RequestMapping(value = "/sy/selectExcelDown")
	public String selectExcel(ModelMap model,  @RequestParam HashMap<String, String> paramMap)
            throws Exception {
    	
		paramMap.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
        String sMenu = String.valueOf(paramMap.get("MENU"));
        List<HashMap<String, String>> mapList = null;
        LinkedHashMap<String, String> excelHeader = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> excelWidth = new LinkedHashMap<String, String>();
        
		if(sMenu.equals("HO01"))
		{
			mapList = rarpHsHistoryDAO.selectIsptExportList(paramMap);
	    	//헤더 명
	        excelHeader.put("A0"		, "NO");
	        excelHeader.put("AA"		, "차종");
	        excelHeader.put("B1"		, "편성");
	        excelHeader.put("B2"		, "차호");
	        excelHeader.put("CC"		, "객차유형");
	        excelHeader.put("DD"		, "검사유형");
	        excelHeader.put("EE"		, "검사일자");
	        excelHeader.put("FF"		, "검사장소");

	        
	        //헤더 넓이
	        excelWidth.put("A0"		, "2000");
	        excelWidth.put("AA"		, "7000");
	        excelWidth.put("B1"		, "4000");
	        excelWidth.put("B2"		, "4000");
	        excelWidth.put("CC"		, "6000");
	        excelWidth.put("DD"		, "7000");
	        excelWidth.put("EE"		, "6000");
	        excelWidth.put("FF"		, "6000");
	        
	    	//엑셀 Title
	        model.addAttribute("ExcelTitle" 	, "검사이력조회");
		}
		else if(sMenu.equals("HO02"))
		{
			mapList = rarpHsHistoryDAO.selectChagExportList(paramMap);
			for (HashMap<String, String> mapPart : mapList) {
    			HashMap<String, String> paramBom = new HashMap<String, String>();
    			paramBom.put("BOM_ID", mapPart.get("BOM_ID"));
    			paramBom.put("BOM_LVL", "1");
    			paramBom.put("CRG_TYPE_CD", paramMap.get("CRG_TYPE_CD"));
    			paramBom.put("TRN_KIND_CD", paramMap.get("TRN_KIND_CD"));
    			
    			List<HashMap<String, String>> bomPathList = rarpSySystemDAO.selectBomPathList(paramBom);
    			for (HashMap<String, String> mapBom : bomPathList) {
	    			mapPart.put("DD", mapBom.get("BOM_NM"));
	    			break;
    			}
    			
    			paramBom.put("BOM_LVL", "2");
    			bomPathList = rarpSySystemDAO.selectBomPathList(paramBom);
    			for (HashMap<String, String> mapBom : bomPathList) {
    				mapPart.put("EE",  mapBom.get("BOM_NM"));
    				break;
    			}
    			
    		}
	    	//헤더 명
	        excelHeader.put("A0"		, "NO");
	        excelHeader.put("AA"		, "차종");
	        excelHeader.put("B1"		, "편성");
	        excelHeader.put("B2"		, "차호");
	        excelHeader.put("CC"		, "객차유형");
	        excelHeader.put("DD"		, "고장분류");
	        excelHeader.put("EE"		, "주요장치");
	        excelHeader.put("FF"		, "품명");
	        excelHeader.put("GG"		, "교환일자");
	        excelHeader.put("HH"		, "규격");
	        excelHeader.put("II"		, "부품코드");
	        excelHeader.put("JJ"		, "수량");
	        excelHeader.put("KK"		, "취거");
	        excelHeader.put("LL"		, "취부");
	        
	        //헤더 넓이
	        excelWidth.put("A0"		, "2000");
	        excelWidth.put("AA"		, "7000");
	        excelWidth.put("B1"		, "4000");
	        excelWidth.put("B2"		, "4000");
	        excelWidth.put("CC"		, "6000");
	        excelWidth.put("DD"		, "7000");
	        excelWidth.put("EE"		, "3000");
	        excelWidth.put("FF"		, "3000");
	        excelWidth.put("GG"		, "6000");
	        excelWidth.put("HH"		, "6000");
	        excelWidth.put("II"		, "7000");
	        excelWidth.put("JJ"		, "3000");
	        excelWidth.put("KK"		, "3000");
	        excelWidth.put("LL"		, "3000");
	        
	    	//엑셀 Title
	        model.addAttribute("ExcelTitle" 	, "부품교환이력조회");
		}
		else if(sMenu.equals("HO03"))
		{
			mapList = rarpHsHistoryDAO.selectTrbExportlList(paramMap);
			for (HashMap<String, String> mapPart : mapList) {
    			HashMap<String, String> paramBom = new HashMap<String, String>();
    			paramBom.put("BOM_ID", mapPart.get("BOM_ID"));
    			paramBom.put("BOM_LVL", "1");
    			paramBom.put("CRG_TYPE_CD", paramMap.get("CRG_TYPE_CD"));
    			paramBom.put("TRN_KIND_CD", paramMap.get("TRN_KIND_CD"));
    			
    			List<HashMap<String, String>> bomPathList = rarpSySystemDAO.selectBomPathList(paramBom);
    			for (HashMap<String, String> mapBom : bomPathList) {
	    			mapPart.put("EE", mapBom.get("BOM_NM"));
	    			break;
    			}
    			
    			paramBom.put("BOM_LVL", "2");
    			bomPathList = rarpSySystemDAO.selectBomPathList(paramBom);
    			for (HashMap<String, String> mapBom : bomPathList) {
    				mapPart.put("FF",  mapBom.get("BOM_NM"));
    				break;
    			}
    			
    		}
	    	//헤더 명
			excelHeader.put("A0"		, "NO");
	        excelHeader.put("AA"		, "차종");
	        excelHeader.put("B1"		, "편성");
	        excelHeader.put("B2"		, "차호");
	        excelHeader.put("CC"		, "객차유형");
	        excelHeader.put("DD"		, "검사일자");
	        excelHeader.put("EE"		, "고장분류");
	        excelHeader.put("FF"		, "주요장치");
	        excelHeader.put("GG"		, "불량상태");
	        excelHeader.put("HH"		, "조치코드");
	        excelHeader.put("II"		, "부품코드");
	        excelHeader.put("JJ"		, "수량");
	        excelHeader.put("KK"		, "점검 및 조치내역");
	        excelHeader.put("LL"		, "사업소");
	        
	        //헤더 넓이
	        excelWidth.put("A0"		, "2000");
	        excelWidth.put("AA"		, "7000");
	        excelWidth.put("B1"		, "4000");
	        excelWidth.put("B2"		, "4000");
	        excelWidth.put("CC"		, "6000");
	        excelWidth.put("DD"		, "7000");
	        excelWidth.put("EE"		, "7000");
	        excelWidth.put("FF"		, "7000");
	        excelWidth.put("GG"		, "3000");
	        excelWidth.put("HH"		, "6000");
	        excelWidth.put("II"		, "7000");
	        excelWidth.put("JJ"		, "3000");
	        excelWidth.put("KK"		, "7000");
	        excelWidth.put("LL"		, "3000");
	    	//엑셀 Title
	        model.addAttribute("ExcelTitle" 	, "고장이력조회");
		}
		int i = 1;
		for (HashMap<String, String> mapData : mapList) {
			mapData.put("A0", String.valueOf(i));
			i++;
		}
        //엑셀 Width
        model.addAttribute("categoryWidth" , excelWidth);
        //엑셀 Header
        model.addAttribute("categoryHeader" , excelHeader);
        //엑셀 Body
        model.addAttribute("categoryList"   , mapList);

        return "ExcelView";
        
    }
}//end RarpMaMainController