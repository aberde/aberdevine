/**
 * 개요
 * - 배너에 대한 controller 클래스를 정의한다.
 *
 * 상세내용
 * - 배너에 대한 등록, 수정, 삭제, 조회, 반영확인 기능을 제공한다.
 * - 배너의 조회기능은 목록조회, 상세조회로 구분된다.
 */

package whoya.egovframework.com.uss.ion.bnr.web;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import whoya.whoyaLib;
import whoya.whoyaList;
import whoya.common.Common;
import whoya.common.commonReturn;
import whoya.egovframework.com.uss.ion.bnr.service.WhoyaEgovBannerService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.bnr.service.Banner;
import egovframework.com.uss.ion.bnr.service.BannerVO;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Controller
public class WhoyaEgovBannerController {
    
    @Resource(name = "whoyaEgovBannerService")
    private WhoyaEgovBannerService egovBannerService;
    
    @Resource(name="EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;
    
    @Resource(name="EgovFileMngService")
    private EgovFileMngService fileMngService;
    
    /** Message ID Generation */
    @Resource(name="egovBannerIdGnrService")
    private EgovIdGnrService egovBannerIdGnrService;
    
    /**
     * 배너관리 화면
     * @return ModelAndView
     */
    @RequestMapping(value="/whoya/uss/ion/bnr/selectBannerList.do")
    public ModelAndView selectBannerList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("whoya/egovframework/com/uss/ion/bnr/EgovBannerList");
        return mav;
    }
    
    /**
     * 배너를 관리하기 위해 등록된 배너목록을 조회한다.
     * @param bannerVO - 배너 VO
     * @return String - 리턴 URL
     * @throws Exception
     */
    @RequestMapping(value="/whoya/uss/ion/bnr/selectBannerListJSON.do")
    public @ResponseBody JSONObject selectBannerListJSON(@ModelAttribute("bannerVO") BannerVO bannerVO, ModelMap model) throws Exception{
        JSONObject resultList = new JSONObject();
        
        try {
            bannerVO.setPageIndex(0);
            // EgovMap 형식으로 받음.
            List<BannerVO> entrprsList = egovBannerService.selectBannerList(bannerVO);
            whoyaList list = new whoyaList(Common.ConverObjectToWhoyaMap(entrprsList));
            
            // 배너 명,링크 URL,배너 설명,반영여부,배너아이디
            resultList.put("list", whoyaLib.whoyaRenderGrid(list, "bannerNm,linkUrl,bannerDc,reflctAt,bannerId"));
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
     * 등록된 배너의 상세정보를 조회한다.
     * @param bannerVO - 배너 Vo
     * @return String - 리턴 Url
     */
    @RequestMapping(value="/whoya/uss/ion/bnr/getBanner.do")
    public @ResponseBody BannerVO selectBanner(@RequestParam("bannerId") String bannerId, @ModelAttribute("bannerVO") BannerVO bannerVO, ModelMap model) throws Exception {
        try {
           BannerVO vo = egovBannerService.selectBanner(bannerVO);
           return vo;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 배너정보를 신규로 등록한다.
     * @param banner - 배너 model
     * @return JSONObject
     */
      @RequestMapping(value="/whoya/uss/ion/bnr/addBanner.do")
    public @ResponseBody JSONObject insertBanner(final MultipartHttpServletRequest multiRequest, @ModelAttribute("banner") Banner banner, @ModelAttribute("bannerVO") BannerVO bannerVO, SessionStatus status, ModelMap model) throws Exception {
          JSONObject resultJSON = new JSONObject();
          try {
              List<FileVO> result = null;
              
              String uploadFolder = "";
              String bannerImage = "";
              String bannerImageFile = "";
              String atchFileId = "";
    
              final Map<String, MultipartFile> files = multiRequest.getFileMap();
    
              if(!files.isEmpty()){
                  result = fileUtil.parseFileInf(files, "BNR_", 0, "", uploadFolder);
                  atchFileId = fileMngService.insertFileInfs(result);
    
                  FileVO vo = (FileVO)result.get(0);
                  Iterator iter = result.iterator();
    
                  while (iter.hasNext()) {
                      vo = (FileVO)iter.next();
                      bannerImage = vo.getOrignlFileNm();
                      bannerImageFile = vo.getStreFileNm();
                  }
              }
    
              LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    
              banner.setBannerId(egovBannerIdGnrService.getNextStringId());
              banner.setBannerImage(bannerImage);
              banner.setBannerImageFile(atchFileId);
              banner.setUserId(user.getId());
              bannerVO.setBannerId(banner.getBannerId());
              status.setComplete();
              
              resultJSON.put("banner", egovBannerService.insertBanner(banner, bannerVO));
              resultJSON.put("status", commonReturn.SUCCESS);
          } catch ( Exception e ) {
              e.printStackTrace();
              resultJSON.put("status", commonReturn.FAIL);
          }
          
          return resultJSON;
    }
      
      
    /**
     * 기 등록된 배너정보를 수정한다.
     * @param banner - 배너 model
     * @return String - 리턴 Url
     */
    @RequestMapping(value="/whoya/uss/ion/bnr/updtBanner.do")
    public @ResponseBody JSONObject updateBanner(final MultipartHttpServletRequest multiRequest, @ModelAttribute("banner") Banner banner, SessionStatus status, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        try {
            List<FileVO> result = null;
            
            String uploadFolder = "";
            String bannerImage = "";
            String bannerImageFile = "";
            String atchFileId = "";
  
            final Map<String, MultipartFile> files = multiRequest.getFileMap();
  
            if(!files.isEmpty()){
                result = fileUtil.parseFileInf(files, "BNR_", 0, "", uploadFolder);
                atchFileId = fileMngService.insertFileInfs(result);
  
                FileVO vo = null;
                Iterator iter = result.iterator();
  
                while (iter.hasNext()) {
                    vo = (FileVO)iter.next();
                    bannerImage = vo.getOrignlFileNm();
                    bannerImageFile = vo.getStreFileNm();
                }
  
                if (vo == null) {
                    banner.setAtchFile(false);
                } else {
                    banner.setBannerImage(bannerImage);
                    banner.setBannerImageFile(atchFileId);
                    banner.setAtchFile(true);
                }
            } else {
                banner.setAtchFile(false);
            }
  
            LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
            banner.setUserId(user.getId());
  
            egovBannerService.updateBanner(banner);
            
            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
        
        return resultJSON;
    }
    
    /**
     * 기 등록된 배너정보를 삭제한다.
     * @param banner Banner
     * @return String
     * @exception Exception
     */
    @RequestMapping(value="/whoya/uss/ion/bnr/removeBanner.do")
    public @ResponseBody JSONObject deleteBanner(@RequestParam("bannerId") String bannerId, @ModelAttribute("banner") Banner banner, SessionStatus status, ModelMap model) throws Exception {
        JSONObject resultJSON = new JSONObject();
        try {
            egovBannerService.deleteBanner(banner);
            status.setComplete();
            resultJSON.put("status", commonReturn.SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            resultJSON.put("status", commonReturn.FAIL);
        }
        
        return resultJSON;
    }
//    @Resource(name="egovMessageSource")
//    EgovMessageSource egovMessageSource;
//
//    @Autowired
//	private DefaultBeanValidator beanValidator;
//    
//    final private Log logger = LogFactory.getLog(this.getClass());
//
//    /**
//	 * 배너 목록화면 이동
//	 * @return String
//	 * @exception Exception
//	 */
//    @RequestMapping("/uss/ion/bnr/selectBannerListView.do")
//    public String selectBannerListView() throws Exception {
//
//        return "egovframework/com/uss/ion/bnr/EgovBannerList";
//    }
//
//	/**
//	 * 배너등록 화면으로 이동한다.
//	 * @param banner - 배너 model
//	 * @return String - 리턴 Url
//	 */
//    @RequestMapping(value="/uss/ion/bnr/addViewBanner.do")
//	public String insertViewBanner(@ModelAttribute("bannerVO") BannerVO bannerVO,
//			                        ModelMap model) throws Exception {
//
//    	model.addAttribute("banner", bannerVO);
//    	return "egovframework/com/uss/ion/bnr/EgovBannerRegist";
//	}
//
//	/**
//	 * 기 등록된 배너정보목록을 일괄 삭제한다.
//	 * @param banners String
//	 * @param banner Banner
//	 * @return String
//	 * @exception Exception
//	 */
//    @RequestMapping(value="/uss/ion/bnr/removeBannerList.do")
//	public String deleteBannerList(@RequestParam("bannerIds") String bannerIds,
//			                       @ModelAttribute("banner") Banner banner,
//			                        SessionStatus status,
//			                        ModelMap model) throws Exception {
//
//    	String [] strBannerIds = bannerIds.split(";");
//
//    	for(int i=0; i<strBannerIds.length;i++) {
//    		banner.setBannerId(strBannerIds[i]);
//    		egovBannerService.deleteBanner(banner);
//    	}
//
//    	status.setComplete();
//    	model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
//		return "forward:/uss/ion/bnr/selectBannerList.do";
//	}
//
//	/**
//	 * 배너가 특정화면에 반영된 결과를 조회한다.
//	 * @param bannerVO - 배너 VO
//	 * @return String - 리턴 Url
//	 */
//	@RequestMapping(value="/uss/ion/bnr/getBannerImage.do")
//	public String selectBannerResult(@ModelAttribute("bannerVO") BannerVO bannerVO,
//                                      ModelMap model) throws Exception {
//
//		List<BannerVO> fileList = egovBannerService.selectBannerResult(bannerVO);
//		model.addAttribute("fileList", fileList);
//		model.addAttribute("resultType", bannerVO.getResultType());
//
//		return "egovframework/com/uss/ion/bnr/EgovBannerView";
//	}
//
//	/**
//	 * MyPage에 배너정보를 제공하기 위해 목록을 조회한다.
//	 * @param bannerVO - 배너 VO
//	 * @return String - 리턴 URL
//	 * @throws Exception
//	 */
//	@IncludedInfo(name="MYPAGE배너관리", order = 741 ,gid = 50)
//    @RequestMapping(value="/uss/ion/bnr/selectBannerMainList.do")
//	public String selectBannerMainList(@ModelAttribute("bannerVO") BannerVO bannerVO,
//                             		ModelMap model) throws Exception{
//
//    	/** paging */
//    	PaginationInfo paginationInfo = new PaginationInfo();
//		paginationInfo.setCurrentPageNo(bannerVO.getPageIndex());
//		paginationInfo.setRecordCountPerPage(5);
//		paginationInfo.setPageSize(bannerVO.getPageSize());
//
//		bannerVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
//		bannerVO.setLastIndex(paginationInfo.getLastRecordIndex());
//		bannerVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
//
//		bannerVO.setBannerList(egovBannerService.selectBannerList(bannerVO));
//
//		model.addAttribute("bannerList", bannerVO.getBannerList());
//
//		return "egovframework/com/uss/ion/bnr/EgovBannerMainList";
//	}
}
