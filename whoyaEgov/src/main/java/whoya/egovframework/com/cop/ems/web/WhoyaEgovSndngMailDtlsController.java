package whoya.egovframework.com.cop.ems.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import whoya.whoyaDataProcess;
import whoya.whoyaLib;
import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.common.Common;
import whoya.common.commonReturn;
import whoya.egovframework.com.cop.ems.service.WhoyaEgovSndngMailDtlsService;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cop.ems.service.SndngMailVO;

/**
 * 발송메일 내역을 조회하는 컨트롤러 클래스
 */
@Controller
public class WhoyaEgovSndngMailDtlsController {

	/** EgovSndngMailDtlsService */
	@Resource(name = "WhoyaEgovSndngMailDtlsService")
    private WhoyaEgovSndngMailDtlsService sndngMailDtlsService;
	
	/**
	 * 발송메일 목록화면
	 * @return ModelAndView
	 * @exception Exception
	 */
	@RequestMapping("/whoya/cop/ems/selectSndngMailList.do")
	public ModelAndView selectSndngMailList() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/egovframework/com/cop/ems/EgovMailDtls");
		return mav;
	}
	
	/**
	 * 발송메일 내역을 조회한다
	 * @param searchVO ComDefaultVO
	 * @return String
	 * @exception Exception
	 */
	@RequestMapping(value="/whoya/cop/ems/selectSndngMailJSONList.do")
	public @ResponseBody JSONObject selectSndngMailJSONList(@ModelAttribute("searchVO") ComDefaultVO searchVO, ModelMap model) throws Exception {
		JSONObject resultList = new JSONObject();
		
		try {
			searchVO.setPageIndex(0);
			List voList = sndngMailDtlsService.selectSndngMailList(searchVO);
			whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(voList));
			
			// 상태,발신자,수신자,제목,날짜,메세지ID
		 	resultList.put("list", whoyaLib.whoyaRenderGrid(list, "sndngResultCode,dsptchPerson,recptnPerson,sj,sndngDe,mssageId"));
		 	resultList.put("status", commonReturn.SUCCESS);
		 	resultList.put("message", "조회되었습니다");
		} catch(Exception e) {
			resultList.put("status", commonReturn.FAIL);
			resultList.put("message", e.getMessage());
			throw e;
		}
		
		return resultList;
	}
	
	/**
	 * 발송메일을 삭제한다.
	 * @param sndngMailVO SndngMailVO
	 * @return String
	 * @exception
	 */
	@RequestMapping(value="/whoya/cop/ems/deleteSndngMailList.do")
	public @ResponseBody void deleteSndngMailList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
    		String[] ids = request.getParameter("ids").split(",");
    		
    		whoyaDataProcess  data = new whoyaDataProcess();
    	    whoyaMap rows = new whoyaMap();
    	    rows = data.dataProcess(request);
    	    
    	    String mssageId = "";
    	    String atchFileIdList = "";
    		for (int i = 0; i < ids.length; i++) {
    			whoyaMap cols = (whoyaMap) rows.get(ids[i]);
    			SndngMailVO sndngMailVO = new SndngMailVO();
    			sndngMailVO = (SndngMailVO)Common.convertWhoyaMapToObject(cols, sndngMailVO);

    			if (sndngMailVO == null || sndngMailVO.getMssageId() == null || sndngMailVO.getMssageId().equals("")) {
    	    		throw new Exception("선택된 데이터가 없습니다.");
    	    	}
    			
    			mssageId += sndngMailVO.getMssageId() + ",";
    			atchFileIdList += sndngMailVO.getAtchFileIdList() + ",";
    		}
    		
    		SndngMailVO sndngMailVO = new SndngMailVO();
    		sndngMailVO.setMssageId(mssageId.substring(0, mssageId.length() - 1));
    		sndngMailVO.setAtchFileIdList(atchFileIdList.substring(0, atchFileIdList.length() - 1));
    		// 1. 발송메일을 삭제한다.
    		sndngMailDtlsService.deleteSndngMailList(sndngMailVO);
    		
    		data.dataRefresh(request, response);
    	} catch ( Exception e ) {
    		e.printStackTrace();
    		throw e;
    	}
	}
}