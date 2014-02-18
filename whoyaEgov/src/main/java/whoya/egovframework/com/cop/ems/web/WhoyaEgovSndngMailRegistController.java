package whoya.egovframework.com.cop.ems.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import whoya.egovframework.com.cop.ems.service.WhoyaEgovSndngMailRegistService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.ems.service.SndngMailVO;

/**
 * 발송메일등록, 발송요청XML파일 생성하는 컨트롤러 클래스
 */
@Controller
public class WhoyaEgovSndngMailRegistController {

	/** EgovFileMngUtil */
	@Resource(name="EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;
	
	/** EgovFileMngService */
	@Resource(name="EgovFileMngService")
	private EgovFileMngService fileMngService;

	/** EgovSndngMailRegistService */
	@Resource(name = "whoyaEgovSndngMailRegistService")
    private WhoyaEgovSndngMailRegistService sndngMailRegistService;
	
	/**
	 * 발송메일 등록화면으로 들어간다
	 * @param sndngMailVO SndngMailVO
	 * @return String
	 * @exception Exception
	 */
	@RequestMapping("/whoya/cop/ems/insertSndngMailView.do")
	public ModelAndView insertSndngMailView() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("whoya/egovframework/com/cop/ems/EgovMailRegist");
		return mav;
	}
  
	/**
	 * 발송메일을 등록한다
	 * @param multiRequest MultipartHttpServletRequest
	 * @param sndngMailVO SndngMailVO
	 * @return String
	 * @exception Exception
	 */
	@RequestMapping(value="/whoya/cop/ems/insertSndngMail.do")
	public @ResponseBody JSONObject insertSndngMail(final MultipartHttpServletRequest multiRequest, @ModelAttribute("sndngMailVO") SndngMailVO sndngMailVO, ModelMap model, HttpServletRequest request) throws Exception {
		JSONObject resultJSON = new JSONObject();
		
		try {
		  	String link = "N";
		  	
		  	if (sndngMailVO != null && sndngMailVO.getLink() != null && !sndngMailVO.getLink().equals("")) {
		  		link = sndngMailVO.getLink();
		  	}
		  	
		  	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		  	
		  	List<FileVO> _result = new ArrayList<FileVO>();	// 2012.11 KISA 보안조치
		  	String _atchFileId = "";
		  	final Map<String, MultipartFile> files = multiRequest.getFileMap();
		  	if(!files.isEmpty()){
			  	 _result = fileUtil.parseFileInf(files, "MSG_", 0, "", ""); 
			  	 _atchFileId = fileMngService.insertFileInfs(_result);  //파일이 생성되고나면 생성된 첨부파일 ID를 리턴한다.
		  	}
		  	
		  	String orignlFileList = "";
		  	
		  	for (int i = 0; i < _result.size(); i++) {
		  		FileVO fileVO = _result.get(i);
		  		orignlFileList = fileVO.getOrignlFileNm();
			}
		  	
		  	sndngMailVO.setAtchFileId(_atchFileId);
		  	sndngMailVO.setDsptchPerson(user.getId());
		  	sndngMailVO.setOrignlFileNm(orignlFileList);
		  	
		  	// 발송메일을 등록한다.
		  	boolean result = sndngMailRegistService.insertSndngMail(sndngMailVO);
		  	System.out.println("result : " + result);
		  	resultJSON.put("status", commonReturn.SUCCESS);
		} catch ( Exception e ) {
			e.printStackTrace();
			resultJSON.put("status", commonReturn.FAIL);
		}
		
		return resultJSON;
	}

//
//    /** 파일구분자 */
//    static final char FILE_SEPARATOR = File.separatorChar;
//    
//    /**
//	 * 발송메일 내용조회로 돌아간다.
//	 * @param sndngMailVO SndngMailVO
//	 * @return String
//	 * @exception Exception
//	 */
//    @RequestMapping(value="/cop/ems/backSndngMailRegist.do")
//	public String backSndngMailRegist(@ModelAttribute("sndngMailVO") SndngMailVO sndngMailVO,
//			ModelMap model) throws Exception {
//
//    	return "redirect:/cop/ems/selectSndngMailList.do";
//	}
}