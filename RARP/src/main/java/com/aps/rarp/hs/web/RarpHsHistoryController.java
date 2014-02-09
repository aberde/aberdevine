package com.aps.rarp.hs.web;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aps.rarp.co.RarpCoLoginSessionManager;
import com.aps.rarp.co.util.StringUtil;
import com.aps.rarp.co.web.RarpCoWebAbstarctController;
import com.aps.rarp.hs.model.RarpHsHistoryDAO;
import com.aps.rarp.ma.model.RarpMaMainDAO;
import com.aps.rarp.ma.web.RarpMaMainController;
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
public class RarpHsHistoryController extends RarpCoWebAbstarctController{

	
	protected static final Logger logger = Logger.getLogger(RarpHsHistoryController.class);
	
	@Resource(name="rarpHsHistoryDAO")
	private RarpHsHistoryDAO rarpHsHistoryDAO;
	
	@Resource(name="loginSessionManager")
	private RarpCoLoginSessionManager loginSessionManager;
	
	@Resource(name="rarpSySystemDAO")
	private RarpSySystemDAO rarpSySystemDAO;
	
	/**
	 * 검사 이력 조회
	 * @param model
	 * @param session
	 * @return "selectMain.tiles"
	 * @throws Exception
	 */
    @RequestMapping(value = "/hs/ispt.do")
    public String initIspt(ModelMap model,@RequestParam HashMap<String, String> paramMap) throws Exception {
    	
    	model.put("MENU", "HO01");
    	model.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
    	
    	List<HashMap<String, String>> rsmoMapList = rarpSySystemDAO.selectRsmoInfoList(paramMap);
    	model.put("RSMO_DATA", rsmoMapList);
    	
    	paramMap.put("COMM_ID", "A01");
    	List<HashMap<String, String>>  trnTypelist = rarpSySystemDAO.selectCommDtlList(paramMap);
    	model.put("TRN_TYPE_DATA", trnTypelist);
    	
      	paramMap.put("COMM_ID", "A02");
    	List<HashMap<String, String>>  isptTypelist = rarpSySystemDAO.selectCommDtlList(paramMap);
    	model.put("ISPT_TYPE_DATA", isptTypelist);
    	
        return "/hs/01/hs01.tiles";
    }
	
	/**
	 * 검사 이력 조회
	 * @param model
	 * @param session
	 * @return "selectMain.tiles"
	 * @throws Exception
	 */
    @RequestMapping(value = "/hs/chag.do")
    public String initChag(ModelMap model,@RequestParam HashMap<String, String> paramMap) throws Exception {
    	
    	model.put("MENU", "HO02");
    	model.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
    	
    	List<HashMap<String, String>> rsmoMapList = rarpSySystemDAO.selectRsmoInfoList(paramMap);
    	model.put("RSMO_DATA", rsmoMapList);
    	
    	paramMap.put("COMM_ID", "A01");
    	List<HashMap<String, String>>  trnTypelist = rarpSySystemDAO.selectCommDtlList(paramMap);
    	model.put("TRN_TYPE_DATA", trnTypelist);
    	
        return "/hs/02/hs02.tiles";
    }
    
	/**
	 * 검사 이력 조회
	 * @param model
	 * @param session
	 * @return "selectMain.tiles"
	 * @throws Exception
	 */
    @RequestMapping(value = "/hs/trbl.do")
    public String initTrbl(ModelMap model,@RequestParam HashMap<String, String> paramMap) throws Exception {
    	
    	model.put("MENU", "HO03");
    	model.put("RSMO_CD", loginSessionManager.getLoginInfo("RSMO_CD"));
    	
    	List<HashMap<String, String>> rsmoMapList = rarpSySystemDAO.selectRsmoInfoList(paramMap);
    	model.put("RSMO_DATA", rsmoMapList);
    	
    	paramMap.put("COMM_ID", "A01");
    	List<HashMap<String, String>>  trnTypelist = rarpSySystemDAO.selectCommDtlList(paramMap);
    	model.put("TRN_TYPE_DATA", trnTypelist);
    	
        return "/hs/02/hs03.tiles";
    }
    
    /**
	 * 검사 정보 조회
	 */
    @RequestMapping(value = "/hs/selectIsptList.do")
    public String selectIsptList(ModelMap model,@RequestParam HashMap<String, String> paramMap ) throws Exception {

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
    		totalResultCnt = rarpHsHistoryDAO.selectIsptCount(paramMap);
    		
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
			
			
    		List<HashMap<String, String>> resultList = rarpHsHistoryDAO.selectIsptList(paramMap);
			
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
	 * 검사 정보 조회
	 */
    @RequestMapping(value = "/hs/selectChagList.do")
    public String selectChagList(ModelMap model,@RequestParam HashMap<String, String> paramMap ) throws Exception {

    	Map<String, Object> resultDataMap = new HashMap<String, Object>();
		int totalResultCnt = 0; // 검색 결과 갯수
		int totalPageNum = 0;	// 전체 페이지 수
		int currentPageIdx = StringUtil.zeroConvert(paramMap.get("page")); // 현재 선택 인덱스
		int pageSize = StringUtil.zeroConvert(paramMap.get("rows"));	// 화면에 보여줄 갯수
		
		String sidx = paramMap.get("sidx"); // get index row - i.e. user click to sort
		String sord = paramMap.get("sord"); // get the direction
		
		int sRow = 0; // 지정된 갯수 다음 부터 출력
		int eRow = 0;
		
    	try
    	{
            paramMap.put("OR_PARENT_BOM_ID", paramMap.get("BOM_ID"));
            
    		totalResultCnt = rarpHsHistoryDAO.selectChagCount(paramMap);
    		
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
			
			
    		List<HashMap<String, String>> resultList = rarpHsHistoryDAO.selectChagList(paramMap);
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
	 * 검사 정보 조회
	 */
    @RequestMapping(value = "/hs/selectTrblList.do")
    public String selectTrblList(ModelMap model,@RequestParam HashMap<String, String> paramMap ) throws Exception {

    	Map<String, Object> resultDataMap = new HashMap<String, Object>();
		int totalResultCnt = 0; // 검색 결과 갯수
		int totalPageNum = 0;	// 전체 페이지 수
		int currentPageIdx = StringUtil.zeroConvert(paramMap.get("page")); // 현재 선택 인덱스
		int pageSize = StringUtil.zeroConvert(paramMap.get("rows"));	// 화면에 보여줄 갯수
		
		String sidx = paramMap.get("sidx"); // get index row - i.e. user click to sort
		String sord = paramMap.get("sord"); // get the direction
		
		int sRow = 0; // 지정된 갯수 다음 부터 출력
		int eRow = 0;
		
    	try
    	{
            paramMap.put("OR_PARENT_BOM_ID", paramMap.get("BOM_ID"));
            
    		totalResultCnt = rarpHsHistoryDAO.selectTrblCount(paramMap);
    		
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
			
			
    		List<HashMap<String, String>> resultList = rarpHsHistoryDAO.selectTrblList(paramMap);
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
	 * 변경 부품 정보
	 * 
	 * @param model
	 * @param paramMap
	 * @exception Exception Exception
	 */
	@RequestMapping(value = "/hs/selectChagPartList")
	public String selectChagPartList(ModelMap model, @RequestParam HashMap<String, String> paramMap)
	  throws Exception{
		
		try
		{
            paramMap.put("OR_PARENT_BOM_ID", paramMap.get("BOM_ID"));
			List<HashMap<String, String>> sensingList = rarpHsHistoryDAO.selectChagPartList(paramMap);
    		model.put("data", sensingList);
    		
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return "jsonView";
	}
	
	/**
	 * 장애 부품 정보
	 * 
	 * @param model
	 * @param paramMap
	 * @exception Exception Exception
	 */
	@RequestMapping(value = "/hs/selectTrblPartList")
	public String selectTrblPartList(ModelMap model, @RequestParam HashMap<String, String> paramMap)
	  throws Exception{
		
		try
		{
            paramMap.put("OR_PARENT_BOM_ID", paramMap.get("BOM_ID"));
			List<HashMap<String, String>> sensingList = rarpHsHistoryDAO.selectTrblPartList(paramMap);
    		model.put("data", sensingList);

		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return "jsonView";
	}
    
	
}//end RarpHsHistoryController