package com.aps.rarp.rf.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import test.com.aps.rarp.rf.RarpRfidDAOTest;

import com.aps.rarp.co.RarpCoLoginSessionManager;
import com.aps.rarp.co.util.StringUtil;
import com.aps.rarp.co.web.RarpCoWebAbstarctController;
import com.aps.rarp.ma.model.RarpMaMainDAO;
import com.aps.rarp.rf.model.RarpRfidDAO;
import com.aps.rarp.sy.model.RarpSySystemDAO;

/**
 * @author Administrator
 * @version 1.0
 * @created 21-11-2013 오후 3:44:19
 */
@Controller
public class RarpRfRfidController extends RarpCoWebAbstarctController{

	Log logger = LogFactory.getLog(RarpRfidDAOTest.class);
	
	@Resource(name="rarpRfidDAO")
	private RarpRfidDAO rarpRfidDAO;
	
	@Resource(name="loginSessionManager")
	private RarpCoLoginSessionManager loginSessionManager;
	
	@Resource(name="rarpSySystemDAO")
	private RarpSySystemDAO rarpSySystemDAO;
	
	
	/**
	 * 태그 상태
	 */
    @RequestMapping(value = "/rf/tagStat.do")
    public String initTagStat(ModelMap model,@RequestParam HashMap<String, String> paramMap) throws Exception {
    	model.put("MENU", "RF01");
    	model.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
    	
    	List<HashMap<String, String>> rsmoMapList = rarpSySystemDAO.selectRsmoInfoList(paramMap);
    	model.put("RSMO_DATA", rsmoMapList);
    	
    	paramMap.put("COMM_ID", "A01");
    	List<HashMap<String, String>>  trnTypelist = rarpSySystemDAO.selectCommDtlList(paramMap);
    	model.put("TRN_TYPE_DATA", trnTypelist);
    	if(paramMap.get("RFID_TAG_ID") != null)
    	{
    		paramMap.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
			HashMap<String, String> paramBom = new HashMap<String, String>();
			paramBom.put("BOM_ID", paramMap.get("BOM_ID"));
			paramBom.put("BOM_LVL", "2");
			paramBom.put("CRG_TYPE_CD", paramMap.get("CRG_TYPE_CD"));
			paramBom.put("TRN_KIND_CD", paramMap.get("TRN_KIND_CD"));
			List<HashMap<String, String>> bomPathList = rarpSySystemDAO.selectBomPathList(paramBom);
			for (HashMap<String, String> mapBom : bomPathList) {
				paramMap.put("BOM_LVL_2", mapBom.get("BOM_NM"));
    			break;
			}
			
			paramBom.put("BOM_LVL", "3");
			bomPathList = rarpSySystemDAO.selectBomPathList(paramBom);
			for (HashMap<String, String> mapBom : bomPathList) {
				paramMap.put("BOM_LVL_3",  mapBom.get("BOM_NM"));
				break;
			}
	    	model.put("PARAM", paramMap);
    	}
    	
        return "/rf/03/rf01.tiles";
    }
    
    
	/**
	 * 인프라 상태
	 */
    @RequestMapping(value = "/rf/infraStat.do")
    public String initInfraStat(ModelMap model,@RequestParam HashMap<String, String> paramMap) throws Exception {
    	model.put("MENU", "RF02");
    	model.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
    	
    	List<HashMap<String, String>> rsmoMapList = rarpSySystemDAO.selectRsmoInfoList(paramMap);
    	model.put("RSMO_DATA", rsmoMapList);
    	
    	try
    	{
	    	paramMap.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
	    	List<HashMap<String, String>>  list = rarpRfidDAO.selectInfraList(paramMap);
			for (Map<String, String> map : list) {
			   logger.info("RSMO : " + map);
			   model.put("RSMO_" + String.valueOf(map.get("INFRA_STRUCTURE_SEQ")), map);
			   model.put("RSMO_INFRA", "RSMO_" + String.valueOf( map.get("INFRA_STRUCTURE_SEQ")));
			   
			   paramMap = new HashMap<String, String>();
			   paramMap.put("TOP_INFRA_STRUCTURE_SEQ", String.valueOf( map.get("INFRA_STRUCTURE_SEQ")));
			   paramMap.put("INFRA_RSC_TYPE", "MIDW");
			   ArrayList<String> midwList = new ArrayList<String>();
			   List<HashMap<String, String>>  listMdlw = rarpRfidDAO.selectResourceList(paramMap);
			   if(listMdlw.size() > 0)
				   model.put("FIRST_MDLW" , listMdlw.get(0));
			   
			   for (Map<String, String> mapMdlw : listMdlw) {
				   logger.info("MDLW : " + mapMdlw);
				   model.put("MDLW_" + String.valueOf( mapMdlw.get("INFRA_RSC_ID")), mapMdlw);
				   midwList.add("MDLW_" + String.valueOf( mapMdlw.get("INFRA_RSC_ID")));
				   
				   ArrayList<String> readerList = new ArrayList<String>();
				   paramMap = new HashMap<String, String>();
				   paramMap.put("PARENT_INFRA_RSC_ID", String.valueOf(mapMdlw.get("INFRA_RSC_ID")));
				   List<HashMap<String, String>>  listRes = rarpRfidDAO.selectResourceList(paramMap);
				   for (Map<String, String> mapRes : listRes) {
					   logger.info("RES : " + mapRes);
					   model.put("RES_" +String.valueOf( mapRes.get("INFRA_RSC_ID")), mapRes);
					   readerList.add("RES_" +String.valueOf(mapRes.get("INFRA_RSC_ID")));
				   }
				   model.put("INFRA_RES_LIST_" + String.valueOf(mapMdlw.get("INFRA_RSC_ID")), readerList);
			   }
			   
			   model.put("INFRA_MDLW_LIST_" + String.valueOf( map.get("INFRA_STRUCTURE_SEQ")), midwList);
			   
		}
    	}catch(Exception ex)
    	{
    		logger.error(ex);
    	}
        return "/rf/04/rf02.tiles";
    }
    /**
	 * 인프라 정보 조회
	 */
    @RequestMapping(value = "/rf/selectRcvrList.do")
    public String selectRcvrList(ModelMap model,@RequestParam HashMap<String, String> paramMap ) throws Exception {

		int totalResultCnt = 0; // 검색 결과 갯수
		int totalPageNum = 0;	// 전체 페이지 수
		int currentPageIdx = StringUtil.zeroConvert(paramMap.get("page")); // 현재 선택 인덱스
		int pageSize = StringUtil.zeroConvert(paramMap.get("rows"));	// 화면에 보여줄 갯수
		
		String sidx = paramMap.get("sidx"); // get index row - i.e. user click to sort
		String sord =  paramMap.get("sord"); // get the direction
		
		int sRow = 0; // 지정된 갯수 다음 부터 출력
		int eRow = 0;
		
    	try
    	{
	    	paramMap.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
	    	List<HashMap<String, String>>  list = rarpRfidDAO.selectInfraList(paramMap);
			for (Map<String, String> map : list) {
				paramMap.put("PARENT_INFRA_RSC_ID", String.valueOf(map.get("INFRA_STRUCTURE_SEQ")));
			}
    		totalResultCnt = rarpRfidDAO.selectRcvrCount(paramMap);
    		
			if (totalResultCnt > 0) {
				totalPageNum = (int)Math.ceil((double)totalResultCnt/(double)pageSize);
			}else {
				totalPageNum = 0;
			}
			if (totalPageNum != 0 && currentPageIdx > totalPageNum) 
				currentPageIdx = totalPageNum;
			
			if(currentPageIdx == 0)
				currentPageIdx = 1;

			sRow = pageSize * currentPageIdx - pageSize;
			eRow = sRow + pageSize; 
			paramMap.put("sRow", String.valueOf(sRow));
			paramMap.put("eRow", String.valueOf(eRow));
			
			
    		List<HashMap<String, Object>> resultList = rarpRfidDAO.selectRcvrList(paramMap);

    		model.put("rows", resultList);
			model.put("page", currentPageIdx);
			model.put("total", totalPageNum);
			model.put("records", totalResultCnt);
			
		} catch (Exception e) {
			model.put("result", "FAIL");
			model.put("fail_message", e.getMessage());

		}
        return "jsonView";
    }
   
    /**
	 * 인프라 정보 조회
	 */
    @RequestMapping(value = "/rf/selectTagSensingList.do")
    public String selectTagSensingList(ModelMap model,@RequestParam HashMap<String, String> paramMap ) throws Exception {

    	Map<String, Object> resultDataMap = new HashMap<String, Object>();
		int totalResultCnt = 0; // 검색 결과 갯수
		int totalPageNum = 0;	// 전체 페이지 수
		int currentPageIdx = StringUtil.zeroConvert(paramMap.get("page")); // 현재 선택 인덱스
		int pageSize = StringUtil.zeroConvert(paramMap.get("rows"));	// 화면에 보여줄 갯수
		
		String sidx = paramMap.get("sidx"); // get index row - i.e. user click to sort
		String sord =  paramMap.get("sord"); // get the direction
		
		int sRow = 0; // 지정된 갯수 다음 부터 출력
		int eRow = 0;
		
    	try
    	{
	    	paramMap.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
	    	
			HashMap<String, String> crgMap = rarpSySystemDAO.selectCrgInfo(paramMap);
            paramMap.put("CRG_TYPE_CD", crgMap.get("CRG_TYPE_CD"));
            
            paramMap.put("OR_PARENT_BOM_ID", paramMap.get("BOM_ID"));
            
    		totalResultCnt = rarpRfidDAO.selectTagSensingCount(paramMap);
    		
			if (totalResultCnt > 0) {
				totalPageNum = (int)Math.ceil((double)totalResultCnt/(double)pageSize);
			}else {
				totalPageNum = 0;
			}
			if (currentPageIdx > totalPageNum) 
				currentPageIdx = totalPageNum;
			
			if(currentPageIdx == 0)
				currentPageIdx = 1;

			sRow = pageSize * currentPageIdx - pageSize;
			eRow = sRow + pageSize; 
			paramMap.put("sRow", String.valueOf(sRow));
			paramMap.put("eRow", String.valueOf(eRow));
			
			
    		List<HashMap<String, String>> resultList = rarpRfidDAO.selectTagSensingList(paramMap);
    		for (HashMap<String, String> mapPart : resultList) {
    			HashMap<String, String> paramBom = new HashMap<String, String>();
    			paramBom.put("BOM_ID", mapPart.get("BOM_ID"));
    			paramBom.put("BOM_LVL", "1");
    			paramBom.put("CRG_TYPE_CD", mapPart.get("CRG_TYPE_CD"));
    			paramBom.put("TRN_KIND_CD", mapPart.get("TRN_KIND_CD"));
    			
    			List<HashMap<String, String>> bomPathList = rarpSySystemDAO.selectBomPathList(paramBom);
    			for (HashMap<String, String> mapBom : bomPathList) {
	    			mapPart.put("BOM_LVL_2", mapBom.get("BOM_NM"));
	    			break;
    			}
    			
    			paramBom.put("BOM_LVL", "2");
    			bomPathList = rarpSySystemDAO.selectBomPathList(paramBom);
    			for (HashMap<String, String> mapBom : bomPathList) {
    				mapPart.put("BOM_LVL_3",  mapBom.get("BOM_NM"));
    				break;
    			}
    			
    		}
    		model.put("rows", resultList);
			model.put("page", currentPageIdx);
			model.put("total", totalPageNum);
			model.put("records", totalResultCnt);
			
		} catch (Exception e) {
			model.put("result", "FAIL");
			model.put("fail_message", e.getMessage());
			model.put("resultData", resultDataMap);
		}
        return "jsonView";
    }

	/**
	 * 모니터링 정보 조회
	 * 
	 * @param model
	 * @param paramMap
	 * @exception Exception
	 */
	@RequestMapping(value = "/rf/selectResMonInfo.do")
	public String selectResMonInfo(ModelMap model,@RequestParam  HashMap<String, String> paramMap)
	  throws Exception{
		try
		{
			paramMap.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
			
    		HashMap<String, String> monInfoMap = rarpRfidDAO.selectResMonInfo(paramMap);
    		model.put("MON", monInfoMap);
    		
    		List<HashMap<String, String>> rcvrInfoMapList = rarpRfidDAO.selectResRcvrList(paramMap);
    		model.put("RCV_LIST", rcvrInfoMapList);
    		
    		List<HashMap<String, String>> antennaInfoMapList = rarpRfidDAO.selectReaderAntennaList(paramMap);
    		model.put("ANT_LIST", antennaInfoMapList);
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return "jsonView";
	}

	/**
	 * 리소스 정보
	 * 
	 * @param model
	 * @param paramMap
	 * @exception Exception
	 */
	@RequestMapping(value = "/rf/selectResInfo.do")
	public String selectResInfo(ModelMap model,@RequestParam  HashMap<String, String> paramMap)
	  throws Exception{
		try
		{
    		HashMap<String, String> monInfoMap = rarpRfidDAO.selectResInfo(paramMap);
    		model.put("data", monInfoMap);
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return "jsonView";
	}

	/**
	 * 전체 부품 정보
	 * 
	 * @param model
	 * @param paramMap
	 * @exception Exception Exception
	 */
	@RequestMapping(value = "/rf/selectTagPartList")
	public String selectTagPartList(ModelMap model, @RequestParam HashMap<String, String> paramMap)
	  throws Exception{
		
		try
		{
            paramMap.put("OR_PARENT_BOM_ID", paramMap.get("BOM_ID"));
			List<HashMap<String, String>> sensingList = rarpRfidDAO.selectTagSensingPartList(paramMap);
    		model.put("sensing", sensingList);
    		
    		List<HashMap<String, String>> partList = rarpRfidDAO.selectTagPartList(paramMap);
    		model.put("part", partList);
    		List<HashMap<String, String>> parentList = null;
    		if(!StringUtil.isEmpty(paramMap.get("BOM_ID").toString()))
    		{
    			parentList = rarpRfidDAO.selectTagParentPartList(paramMap);
    		}
    		model.put("parent", parentList);
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return "jsonView";
	}


	

}//end RarpRfRfidController