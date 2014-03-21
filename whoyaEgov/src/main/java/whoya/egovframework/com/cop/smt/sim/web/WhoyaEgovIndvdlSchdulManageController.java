package whoya.egovframework.com.cop.smt.sim.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import whoya.common.commonReturn;
import whoya.egovframework.com.cop.smt.sim.service.WhoyaEgovIndvdlSchdulManageService;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.smt.sim.service.IndvdlSchdulManageVO;
/**
 * 일정관리를 처리하는 Controller Class 구현
 */
@Controller
public class WhoyaEgovIndvdlSchdulManageController {
    
    @Resource(name = "whoyaEgovIndvdlSchdulManageService")
    private WhoyaEgovIndvdlSchdulManageService egovIndvdlSchdulManageService;

    // 첨부파일 관련
    @Resource(name="EgovFileMngService")
    private EgovFileMngService fileMngService;
  
    @Resource(name="EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;
    /**
     * 일정관리 화면
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/whoya/cop/smt/sim/EgovIndvdlSchdulManageList.do")
    public ModelAndView selectScrapList() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("whoya/egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageList");
        return mav;
    }
    
    /**
     * 일정 목록을 조회한다.
     * @param searchVO
     * @param commandMap
     * @param indvdlSchdulManageVO
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/cop/smt/sim/EgovIndvdlSchdulManageListJSON.do")
    public @ResponseBody List egovIndvdlSchdulManageListJSON(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, IndvdlSchdulManageVO indvdlSchdulManageVO, ModelMap model) throws Exception {
        List resultList = null;
        
        try {
            searchVO.setPageIndex(0);
            resultList = egovIndvdlSchdulManageService.selectIndvdlSchdulManageList(searchVO);
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        return resultList;
    }
    
    /**
     * 일정를 등록 처리 한다.
     * @param multiRequest
     * @param searchVO
     * @param commandMap
     * @param indvdlSchdulManageVO
     * @param bindingResult
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/cop/smt/sim/EgovIndvdlSchdulManageRegistActor.do")
    public @ResponseBody JSONObject indvdlSchdulManageRegistActor(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, @ModelAttribute("indvdlSchdulManageVO") IndvdlSchdulManageVO indvdlSchdulManageVO, ModelMap model) throws Exception {
        JSONObject result = new JSONObject();
        try {
            //로그인 객체 선언
            LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
            // 첨부파일 관련 첨부파일ID 생성
            List<FileVO> _result = null;
            String _atchFileId = "";
  
            final Map<String, MultipartFile> files = multiRequest.getFileMap();
  
            if(!files.isEmpty()){
             _result = fileUtil.parseFileInf(files, "DSCH_", 0, "", "");
             _atchFileId = fileMngService.insertFileInfs(_result);  //파일이 생성되고나면 생성된 첨부파일 ID를 리턴한다.
            }
  
            // 리턴받은 첨부파일ID를 셋팅한다..
            indvdlSchdulManageVO.setAtchFileId(_atchFileId);            // 첨부파일 ID
  
            //아이디 설정
            indvdlSchdulManageVO.setFrstRegisterId((String)loginVO.getUniqId());
            indvdlSchdulManageVO.setLastUpdusrId((String)loginVO.getUniqId());
  
            egovIndvdlSchdulManageService.insertIndvdlSchdulManage(indvdlSchdulManageVO);
            result.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.put("status", commonReturn.FAIL);
        }
        
        return result;
    }
    
    /**
     * 일정(월별) 목록을 조회한다.
     * @param searchVO
     * @param commandMap
     * @param indvdlSchdulManageVO
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/cop/smt/sim/EgovIndvdlSchdulManageMonthList.do")
    public @ResponseBody List egovIndvdlSchdulManageMonthList(@ModelAttribute("searchVO") ComDefaultVO searchVO, Map commandMap, IndvdlSchdulManageVO indvdlSchdulManageVO, ModelMap model) throws Exception {
        try {
            List resultList = egovIndvdlSchdulManageService.selectIndvdlSchdulManageRetrieve(commandMap);
            return resultList;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 일정 목록을 상세조회 조회한다.
     * @param searchVO
     * @param indvdlSchdulManageVO
     * @param commandMap
     * @param model
     * @return "egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageDetail"
     * @throws Exception
     */
    @RequestMapping(value="/whoya/cop/smt/sim/EgovIndvdlSchdulManageDetail.do")
    public @ResponseBody List egovIndvdlSchdulManageDetail(@ModelAttribute("searchVO") ComDefaultVO searchVO, IndvdlSchdulManageVO indvdlSchdulManageVO, Map commandMap, ModelMap model) throws Exception {
        try {
            List sampleList = egovIndvdlSchdulManageService.selectIndvdlSchdulManageDetail(indvdlSchdulManageVO);
            return sampleList;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 일정를 수정 처리 한다.
     * @param multiRequest
     * @param searchVO
     * @param commandMap
     * @param indvdlSchdulManageVO
     * @param bindingResult
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/cop/smt/sim/EgovIndvdlSchdulManageModifyActor.do")
    public @ResponseBody JSONObject indvdlSchdulManageModifyActor(final MultipartHttpServletRequest multiRequest, ComDefaultVO searchVO, Map commandMap, @ModelAttribute("indvdlSchdulManageVO") IndvdlSchdulManageVO indvdlSchdulManageVO, ModelMap model) throws Exception {
        JSONObject result = new JSONObject();
        try {
            //로그인 객체 선언
            LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
            /* *****************************************************************
            // 아이디 설정
            ****************************************************************** */
            indvdlSchdulManageVO.setFrstRegisterId((String)loginVO.getUniqId());
            indvdlSchdulManageVO.setLastUpdusrId((String)loginVO.getUniqId());
            /* *****************************************************************
            // 첨부파일 관련 ID 생성 start....
            ****************************************************************** */
            String _atchFileId = indvdlSchdulManageVO.getAtchFileId();
            
            final Map<String, MultipartFile> files = multiRequest.getFileMap();
            
            if(!files.isEmpty()){
                String atchFileAt = commandMap.get("atchFileAt") == null ? "" : (String)commandMap.get("atchFileAt");
                if("N".equals(atchFileAt)){
                    List<FileVO> _result = fileUtil.parseFileInf(files, "DSCH_", 0, _atchFileId, "");
                    _atchFileId = fileMngService.insertFileInfs(_result);
  
                    // 첨부파일 ID 셋팅
                    indvdlSchdulManageVO.setAtchFileId(_atchFileId);        // 첨부파일 ID
  
                }else{
                    FileVO fvo = new FileVO();
                    fvo.setAtchFileId(_atchFileId);
                    int _cnt = fileMngService.getMaxFileSN(fvo);
                    List<FileVO> _result = fileUtil.parseFileInf(files, "DSCH_", _cnt, _atchFileId, "");
                    fileMngService.updateFileInfs(_result);
                }
            }
  
            /* *****************************************************************
            // 일정관리정보 업데이트 처리
            ****************************************************************** */
            egovIndvdlSchdulManageService.updateIndvdlSchdulManage(indvdlSchdulManageVO);
            result.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.put("status", commonReturn.FAIL);
        }
        
        return result;
    }
    
    /**
     * 일정를 삭제 처리 한다.
     * @param multiRequest
     * @param searchVO
     * @param commandMap
     * @param indvdlSchdulManageVO
     * @param bindingResult
     * @param model
     * @return JSONObject
     * @throws Exception
     */
    @RequestMapping(value="/whoya/cop/smt/sim/EgovIndvdlSchdulManageDeleteActor.do")
    public @ResponseBody JSONObject indvdlSchdulManageDeleteActor(@ModelAttribute("searchVO") ComDefaultVO searchVO, IndvdlSchdulManageVO indvdlSchdulManageVO, Map commandMap, ModelMap model) throws Exception {
        JSONObject result = new JSONObject();
        try {
            egovIndvdlSchdulManageService.deleteIndvdlSchdulManage(indvdlSchdulManageVO);
            result.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.put("status", commonReturn.FAIL);
        }
        
        return result;
    }
//
//
//
//	protected Log log = LogFactory.getLog(this.getClass());
//
//	@Autowired
//	private DefaultBeanValidator beanValidator;
//
//	/** EgovMessageSource */
//    @Resource(name="egovMessageSource")
//    EgovMessageSource egovMessageSource;
//
//	@Resource(name="EgovCmmUseService")
//	private EgovCmmUseService cmmUseService;
//
//    /** EgovPropertyService */
//    @Resource(name = "propertiesService")
//    protected EgovPropertyService propertiesService;
//
//
//	/**
//	 * 메인페이지/일정관리조회
//	 * @param commandMap
//	 * @param model
//	 * @return "egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageMainList"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/cop/smt/sim/EgovIndvdlSchdulManageMainList.do")
//	public String egovIndvdlSchdulManageMainList(
//			Map commandMap,
//    		ModelMap model)
//    throws Exception {
//
//    	// 0. Spring Security 사용자권한 처리
//    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
//        	return "egovframework/com/uat/uia/EgovLoginUsr";
//    	}
//
//		//로그인 객체 선언
//		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//		if(loginVO == null){ loginVO = new LoginVO();}
//
//		HashMap hmParam = new HashMap();
//
//		hmParam.put("uniqId", (String)loginVO.getUniqId());
//
//		List reusltList = egovIndvdlSchdulManageService.selectIndvdlSchdulManageMainList(hmParam);
//
//		 model.addAttribute("resultList", reusltList);
//
//    	return "egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageMainList";
//
//	}
//
//	/**
//	 * 일정(일별) 목록을 조회한다.
//	 * @param searchVO
//	 * @param commandMap
//	 * @param indvdlSchdulManageVO
//	 * @param model
//	 * @return "egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageDailyList"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/cop/smt/sim/EgovIndvdlSchdulManageDailyList.do")
//	public String egovIndvdlSchdulManageDailyList(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO,
//			Map commandMap,
//			IndvdlSchdulManageVO indvdlSchdulManageVO,
//    		ModelMap model)
//    throws Exception {
//
//		//일정구분 검색 유지
//        model.addAttribute("searchKeyword", commandMap.get("searchKeyword") == null ? "" : (String)commandMap.get("searchKeyword"));
//        model.addAttribute("searchCondition", commandMap.get("searchCondition") == null ? "" : (String)commandMap.get("searchCondition"));
//
//		//공통코드 일정종류
//		ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
//	   	voComCode = new ComDefaultCodeVO();
//    	voComCode.setCodeId("COM030");
//    	List listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
//    	model.addAttribute("schdulSe", listComCode);
//
//		/* *****************************************************************
//    	// 캘런더 설정 로직
//		****************************************************************** */
//        Calendar calNow = Calendar.getInstance();
//
//		String strYear = (String)commandMap.get("year");
//		String strMonth = (String)commandMap.get("month");
//		String strDay =( String)commandMap.get("day");
//		String strSearchDay = "";
//		int iNowYear = calNow.get(Calendar.YEAR);
//		int iNowMonth = calNow.get(Calendar.MONTH);
//		int iNowDay = calNow.get(Calendar.DATE);
//
//		if(strYear != null)
//		{
//		  iNowYear = Integer.parseInt(strYear);
//		  iNowMonth = Integer.parseInt(strMonth);
//		  iNowDay = Integer.parseInt(strDay);
//		}
//
//		strSearchDay = Integer.toString(iNowYear);
//		strSearchDay += dateTypeIntForString(iNowMonth+1);
//		strSearchDay += dateTypeIntForString(iNowDay);
//
//		commandMap.put("searchMode", "DAILY");
//		commandMap.put("searchDay", strSearchDay);
//
//		model.addAttribute("year", iNowYear);
//		model.addAttribute("month", iNowMonth);
//		model.addAttribute("day", iNowDay);
//
//		List resultList = egovIndvdlSchdulManageService.selectIndvdlSchdulManageRetrieve(commandMap);
//        model.addAttribute("resultList", resultList);
//
//		return "egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageDailyList";
//	}
//
//	/**
//	 * 일정(주간별) 목록을 조회한다.
//	 * @param searchVO
//	 * @param commandMap
//	 * @param indvdlSchdulManageVO
//	 * @param model
//	 * @return "egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageWeekList"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/cop/smt/sim/EgovIndvdlSchdulManageWeekList.do")
//	public String egovIndvdlSchdulManageWeekList(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO,
//			Map commandMap,
//			IndvdlSchdulManageVO indvdlSchdulManageVO,
//    		ModelMap model)
//    throws Exception {
//
//		//일정구분 검색 유지
//        model.addAttribute("searchKeyword", commandMap.get("searchKeyword") == null ? "" : (String)commandMap.get("searchKeyword"));
//        model.addAttribute("searchCondition", commandMap.get("searchCondition") == null ? "" : (String)commandMap.get("searchCondition"));
//
//		//공통코드 일정종류
//		ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
//	   	voComCode = new ComDefaultCodeVO();
//    	voComCode.setCodeId("COM030");
//    	List listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
//    	model.addAttribute("schdulSe", listComCode);
//
//		/* *****************************************************************
//    	// 캘런더 설정 로직
//		****************************************************************** */
//        Calendar calNow = Calendar.getInstance();
//        Calendar calBefore = Calendar.getInstance();
//        Calendar calNext = Calendar.getInstance();
//
//
//		String strYear = (String)commandMap.get("year");
//		String strMonth = (String)commandMap.get("month");
//		String strWeek =( String)commandMap.get("week");
//
//		int iNowYear = calNow.get(Calendar.YEAR);
//		int iNowMonth = calNow.get(Calendar.MONTH);
//		int iNowDate = calNow.get(Calendar.DATE);
//		int iNowWeek = 0;
//
//		if(strYear != null)
//		{
//		  iNowYear = Integer.parseInt(strYear);
//		  iNowMonth = Integer.parseInt(strMonth);
//		  iNowWeek = Integer.parseInt(strWeek);
//		}
//
//		//년도/월 셋팅
//		calNow.set(iNowYear, iNowMonth, 1);
//		calBefore.set(iNowYear, iNowMonth, 1);
//		calNext.set(iNowYear, iNowMonth, 1);
//
//		calBefore.add(Calendar.MONTH, -1);
//		calNext.add(Calendar.MONTH, +1);
//
//		int startDay = calNow.getMinimum(Calendar.DATE);
//		int endDay = calNow.getActualMaximum(Calendar.DAY_OF_MONTH);
//		int startWeek = calNow.get(Calendar.DAY_OF_WEEK);
//
//
//		ArrayList listWeekGrop = new ArrayList();
//		ArrayList listWeekDate = new ArrayList();
//
//		String sUseDate = "";
//
//		calBefore.add(Calendar.DATE , calBefore.getActualMaximum(Calendar.DAY_OF_MONTH) - (startWeek-1));
//		for(int i = 1; i < startWeek ; i++ )
//		{
//			sUseDate = Integer.toString(calBefore.get(Calendar.YEAR));
//			sUseDate += dateTypeIntForString(calBefore.get(Calendar.MONTH)+1);
//			sUseDate += dateTypeIntForString(calBefore.get(Calendar.DATE));
//
//			listWeekDate.add(sUseDate);
//			calBefore.add(Calendar.DATE, +1);
//		}
//
//		int iBetweenCount = startWeek;
//
//		// 주별로 자른다. BETWEEN 구하기
//		for(int i=1; i <= endDay; i++)
//		{
//			sUseDate = Integer.toString(iNowYear);
//			//sUseDate += Integer.toString(iNowMonth).length() == 1 ? "0" + Integer.toString(iNowMonth+1) : Integer.toString(iNowMonth+1);
//			// (2011.09.02 수정사항) 10월의 주별 날짜가 이상하게 나와서 LeaderSchedule 보고 수정함. 위의 코드가 원래 코드
//			sUseDate += Integer.toString(iNowMonth+1).length() == 1 ? "0" + Integer.toString(iNowMonth+1) : Integer.toString(iNowMonth+1);
//			sUseDate += Integer.toString(i).length() == 1 ? "0" + Integer.toString(i) : Integer.toString(i);
//
//			listWeekDate.add(sUseDate);
//
//			if( iBetweenCount % 7 == 0){
//				listWeekGrop.add(listWeekDate);
//				listWeekDate = new ArrayList();
//
//				if(strYear == null &&  i < iNowDate){
//					iNowWeek++;
//
//				}
//			}
//
//			//미지막 7일 자동계산
//			if(i == endDay){
//
//				for(int j=listWeekDate.size(); j < 7;j++){
//					String sUseNextDate = Integer.toString(calNext.get(Calendar.YEAR));
//					sUseNextDate += dateTypeIntForString(calNext.get(Calendar.MONTH)+1);
//					sUseNextDate += dateTypeIntForString(calNext.get(Calendar.DATE));
//					listWeekDate.add(sUseNextDate);
//					calNext.add(Calendar.DATE, +1);
//				}
//
//				listWeekGrop.add(listWeekDate);
//			}
//
//			iBetweenCount++;
//		}
//
//		model.addAttribute("year", iNowYear);
//		model.addAttribute("month", iNowMonth);
//		model.addAttribute("week", iNowWeek);
//
//
//		model.addAttribute("listWeekGrop", listWeekGrop);
//
//		List listWeek = (List)listWeekGrop.get(iNowWeek);
//		commandMap.put("searchMode", "WEEK");
//		commandMap.put("schdulBgnde", (String)listWeek.get(0));
//		commandMap.put("schdulEndde", (String)listWeek.get(listWeek.size()-1));
//
//		List resultList = egovIndvdlSchdulManageService.selectIndvdlSchdulManageRetrieve(commandMap);
//        model.addAttribute("resultList", resultList);
//
//		return "egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageWeekList";
//	}
//
//	/**
//	 * 일정 수정 폼
//	 * @param searchVO
//	 * @param commandMap
//	 * @param indvdlSchdulManageVO
//	 * @param bindingResult
//	 * @param model
//	 * @return "egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageModify"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/cop/smt/sim/EgovIndvdlSchdulManageModify.do")
//	public String indvdlSchdulManageModify(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO,
//			Map commandMap,
//			IndvdlSchdulManageVO indvdlSchdulManageVO,
//			BindingResult bindingResult,
//    		ModelMap model)
//    throws Exception {
//
//		String sLocationUrl = "egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageModify";
//
//		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
//
//     	//공통코드  중요도 조회
//    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
//    	voComCode.setCodeId("COM019");
//    	List listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
//    	model.addAttribute("schdulIpcrCode", listComCode);
//    	//공통코드  일정구분 조회
//    	voComCode = new ComDefaultCodeVO();
//    	voComCode.setCodeId("COM030");
//    	listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
//    	model.addAttribute("schdulSe", listComCode);
//    	//공통코드  반복구분 조회
//    	voComCode = new ComDefaultCodeVO();
//    	voComCode.setCodeId("COM031");
//    	listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
//    	model.addAttribute("reptitSeCode", listComCode);
//
//    	//일정시작일자(시)
//    	model.addAttribute("schdulBgndeHH", (List)getTimeHH());
//    	//일정시작일자(분)
//    	model.addAttribute("schdulBgndeMM", (List)getTimeMM());
//    	//일정종료일자(시)
//    	model.addAttribute("schdulEnddeHH", (List)getTimeHH());
//    	//일정정료일자(분)
//    	model.addAttribute("schdulEnddeMM", (List)getTimeMM());
//
//    	IndvdlSchdulManageVO resultIndvdlSchdulManageVOReuslt = (IndvdlSchdulManageVO)egovIndvdlSchdulManageService.selectIndvdlSchdulManageDetailVO(indvdlSchdulManageVO);
//
//    	String sSchdulBgnde = resultIndvdlSchdulManageVOReuslt.getSchdulBgnde();
//    	String sSchdulEndde = resultIndvdlSchdulManageVOReuslt.getSchdulEndde();
//
//    	resultIndvdlSchdulManageVOReuslt.setSchdulBgndeYYYMMDD(sSchdulBgnde.substring(0, 4) +"-"+sSchdulBgnde.substring(4, 6)+"-"+sSchdulBgnde.substring(6, 8) );
//    	resultIndvdlSchdulManageVOReuslt.setSchdulBgndeHH(sSchdulBgnde.substring(8, 10));
//    	resultIndvdlSchdulManageVOReuslt.setSchdulBgndeMM(sSchdulBgnde.substring(10, 12));
//
//       	resultIndvdlSchdulManageVOReuslt.setSchdulEnddeYYYMMDD(sSchdulEndde.substring(0, 4) +"-"+sSchdulEndde.substring(4, 6)+"-"+sSchdulEndde.substring(6, 8) );
//    	resultIndvdlSchdulManageVOReuslt.setSchdulEnddeHH(sSchdulEndde.substring(8, 10));
//    	resultIndvdlSchdulManageVOReuslt.setSchdulEnddeMM(sSchdulEndde.substring(10, 12));
//
//    	model.addAttribute("indvdlSchdulManageVO", resultIndvdlSchdulManageVOReuslt);
//
//		return sLocationUrl;
//	}
//
//	/**
//	 * 일정를 등록 폼
//	 * @param searchVO
//	 * @param commandMap
//	 * @param indvdlSchdulManageVO
//	 * @param bindingResult
//	 * @param model
//	 * @return "egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageRegist"
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/cop/smt/sim/EgovIndvdlSchdulManageRegist.do")
//	public String indvdlSchdulManageRegist(
//			@ModelAttribute("searchVO") ComDefaultVO searchVO,
//			Map commandMap,
//			@ModelAttribute("indvdlSchdulManageVO") IndvdlSchdulManageVO indvdlSchdulManageVO,
//			BindingResult bindingResult,
//    		ModelMap model)
//    throws Exception {
//
//		String sLocationUrl = "egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageRegist";
//
//    	// 0. Spring Security 사용자권한 처리
//    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
//        	return "egovframework/com/uat/uia/EgovLoginUsr";
//    	}
//
//     	//공통코드  중요도 조회
//    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
//    	voComCode.setCodeId("COM019");
//    	List listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
//    	model.addAttribute("schdulIpcrCode", listComCode);
//    	//공통코드  일정구분 조회
//    	voComCode = new ComDefaultCodeVO();
//    	voComCode.setCodeId("COM030");
//    	listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
//    	model.addAttribute("schdulSe", listComCode);
//    	//공통코드  반복구분 조회
//    	voComCode = new ComDefaultCodeVO();
//    	voComCode.setCodeId("COM031");
//    	listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
//    	model.addAttribute("reptitSeCode", listComCode);
//
//    	//일정시작일자(시)
//    	model.addAttribute("schdulBgndeHH", (List)getTimeHH());
//    	//일정시작일자(분)
//    	model.addAttribute("schdulBgndeMM", (List)getTimeMM());
//    	//일정종료일자(시)
//    	model.addAttribute("schdulEnddeHH", (List)getTimeHH());
//    	//일정정료일자(분)
//    	model.addAttribute("schdulEnddeMM", (List)getTimeMM());
//
//
//    	return sLocationUrl;
//
//	}
//	/**
//	 * 시간을 LIST를 반환한다.
//	 * @return  List
//	 * @throws
//	 */
//	private List getTimeHH (){
//    	ArrayList listHH = new ArrayList();
//    	HashMap hmHHMM;
//    	for(int i=0;i <= 24; i++){
//    		String sHH = "";
//    		String strI = String.valueOf(i);
//    		if(i<10){
//    			sHH = "0" + strI;
//    		}else{
//    			sHH = strI;
//    		}
//
//    		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
//    		codeVO.setCode(sHH);
//    		codeVO.setCodeNm(sHH);
//
//    		listHH.add(codeVO);
//    	}
//
//    	return listHH;
//	}
//	/**
//	 * 분을 LIST를 반환한다.
//	 * @return  List
//	 * @throws
//	 */
//	private List getTimeMM (){
//    	ArrayList listMM = new ArrayList();
//    	HashMap hmHHMM;
//    	for(int i=0;i <= 60; i++){
//
//    		String sMM = "";
//    		String strI = String.valueOf(i);
//    		if(i<10){
//    			sMM = "0" + strI;
//    		}else{
//    			sMM = strI;
//    		}
//
//    		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
//    		codeVO.setCode(sMM);
//    		codeVO.setCodeNm(sMM);
//
//    		listMM.add(codeVO);
//    	}
//    	return listMM;
//	}
//	/**
//	 * 0을 붙여 반환
//	 * @return  String
//	 * @throws
//	 */
//    public String dateTypeIntForString(int iInput){
//		String sOutput = "";
//		if(Integer.toString(iInput).length() == 1){
//			sOutput = "0" + Integer.toString(iInput);
//		}else{
//			sOutput = Integer.toString(iInput);
//		}
//
//       return sOutput;
//    }

}


