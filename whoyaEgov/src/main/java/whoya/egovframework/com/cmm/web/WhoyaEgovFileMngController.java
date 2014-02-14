package whoya.egovframework.com.cmm.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import whoya.egovframework.com.cmm.service.WhoyaEgovFileMngService;
import egovframework.com.cmm.service.FileVO;

/**
 * 파일 조회, 삭제, 다운로드 처리를 위한 컨트롤러 클래스
 */
@Controller
public class WhoyaEgovFileMngController {
	
	@Resource(name = "WhoyaEgovFileMngService")
	private WhoyaEgovFileMngService fileService;
	
	/**
	 * 첨부파일에 대한 목록을 조회한다.
	 * 
	 * @param fileVO
	 * @param atchFileId
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/whoya/cmm/fms/selectFileInfs.do")
	public @ResponseBody List<FileVO> selectFileInfs(@ModelAttribute("searchVO") FileVO fileVO, Map<String, Object> commandMap, ModelMap model) throws Exception {
		try {
			List<FileVO> result = fileService.selectFileInfs(fileVO);
		
			return result;
		} catch ( Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}
//	 
//	 
//    private static final Logger LOG = Logger.getLogger(EgovFileMngController.class.getName());
//
//    /**
//     * 첨부파일 변경을 위한 수정페이지로 이동한다.
//     * 
//     * @param fileVO
//     * @param atchFileId
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cmm/fms/selectFileInfsForUpdate.do")
//    public String selectFileInfsForUpdate(@ModelAttribute("searchVO") FileVO fileVO, Map<String, Object> commandMap,
//	    //SessionVO sessionVO,
//	    ModelMap model) throws Exception {
//
//	String atchFileId = (String)commandMap.get("param_atchFileId");
//
//	fileVO.setAtchFileId(atchFileId);
//
//	List<FileVO> result = fileService.selectFileInfs(fileVO);
//	
//	model.addAttribute("fileList", result);
//	model.addAttribute("updateFlag", "Y");
//	model.addAttribute("fileListCnt", result.size());
//	model.addAttribute("atchFileId", atchFileId);
//	
//	return "egovframework/com/cmm/fms/EgovFileList";
//    }
//
//    /**
//     * 첨부파일에 대한 삭제를 처리한다.
//     * 
//     * @param fileVO
//     * @param returnUrl
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cmm/fms/deleteFileInfs.do")
//    public String deleteFileInf(@ModelAttribute("searchVO") FileVO fileVO, @RequestParam("returnUrl") String returnUrl,
//	    //SessionVO sessionVO,
//	    HttpServletRequest request,
//	    ModelMap model) throws Exception {
//
//	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//	if (isAuthenticated) {
//	    fileService.deleteFileInf(fileVO);
//	}
//
//	//--------------------------------------------
//	// contextRoot가 있는 경우 제외 시켜야 함
//	//--------------------------------------------
//	////return "forward:/cmm/fms/selectFileInfs.do";
//	//return "forward:" + returnUrl;
//	
//	if ("".equals(request.getContextPath()) || "/".equals(request.getContextPath())) {
//	    return "forward:" + returnUrl;
//	}
//	
//	if (returnUrl.startsWith(request.getContextPath())) {
//	    return "forward:" + returnUrl.substring(returnUrl.indexOf("/", 1));
//	} else {
//	    return "forward:" + returnUrl;
//	}
//	////------------------------------------------
//    }
//
//    /**
//     * 이미지 첨부파일에 대한 목록을 조회한다.
//     * 
//     * @param fileVO
//     * @param atchFileId
//     * @param sessionVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/cmm/fms/selectImageFileInfs.do")
//    public String selectImageFileInfs(@ModelAttribute("searchVO") FileVO fileVO, Map<String, Object> commandMap,
//	    //SessionVO sessionVO,
//	    ModelMap model) throws Exception {
//
//	String atchFileId = (String)commandMap.get("atchFileId");
//
//	fileVO.setAtchFileId(atchFileId);
//	List<FileVO> result = fileService.selectImageFileList(fileVO);
//	
//	model.addAttribute("fileList", result);
//
//	return "egovframework/com/cmm/fms/EgovImgFileList";
//    }
}
