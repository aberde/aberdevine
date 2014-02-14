package whoya.egovframework.com.cop.ems.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import whoya.whoyaDataProcess;
import whoya.whoyaMap;
import whoya.common.Common;
import whoya.egovframework.com.cop.ems.service.WhoyaEgovSndngMailDetailService;
import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cop.ems.service.SndngMailVO;

/**
 * 발송메일을 상세 조회하는 컨트롤러 클래스
 */
@Controller
public class WhoyaEgovSndngMailDetailController {
	
	private static final Logger LOG = Logger.getLogger(WhoyaEgovSndngMailDetailController.class.getClass());

	/** EgovSndngMailDetailService */
	@Resource(name = "WhoyaEgovSndngMailDetailService")
    private WhoyaEgovSndngMailDetailService sndngMailDetailService;
	
	/**
	 * 발송메일을 상세 조회한다.
	 * @param sndngMailVO SndngMailVO
	 * @return String
	 * @exception Exception
	 */
	@RequestMapping(value="/whoya/cop/ems/selectSndngMailDetail.do")
	public @ResponseBody SndngMailVO selectSndngMail(@ModelAttribute("sndngMailVO") SndngMailVO sndngMailVO, ModelMap model) throws Exception {
		try {
			if (sndngMailVO == null || sndngMailVO.getMssageId() == null || sndngMailVO.getMssageId().equals("")) {
		  		throw new Exception("선택된 데이터가 없습니다.");
		  	}
			
			// 1. 발송메일을 상세 조회한다.
		  	SndngMailVO resultMailVO = sndngMailDetailService.selectSndngMail(sndngMailVO);
		  	return resultMailVO;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * XML형태의 발송요청메일을 조회한다.
	 * @param sndngMailVO SndngMailVO
	 * @exception Exception
	 */
	@RequestMapping(value="/whoya/cop/ems/selectSndngMailXml.do")
	public @ResponseBody void selectSndngMailXml(@ModelAttribute("sndngMailVO") SndngMailVO sndngMailVO, HttpServletResponse response, ModelMap model) throws Exception {
	  	String xmlFile = Globals.MAIL_REQUEST_PATH + sndngMailVO.getMssageId() + ".xml";
	  	File uFile = new File(EgovWebUtil.filePathBlackList(xmlFile));
	  	int fSize = (int) uFile.length();

	  	// 2011.10.10 보안점검 후속 조치
	  	if (fSize > 0) {
	  		String mimetype = "application/x-msdownload;charset=UTF-8";

	  		//response.setBufferSize(fSize);
	  		response.setContentType(mimetype);
	  		response.setHeader("Content-Disposition", "attachment; filename=\"" + uFile.getName() + "\"");
	  		response.setContentLength(fSize);

	  		BufferedInputStream in = null;
	  		
	  		try {
				in = new BufferedInputStream(new FileInputStream(uFile));
				FileCopyUtils.copy(in, response.getOutputStream());
	  		} finally {
	  			if (in != null) {
	  				try {
	  					in.close();
	  				} catch (Exception ignore) {
	  					// no-op :
	  					LOG.debug("IGNORED: " + ignore.getMessage());
	  				}
	  			}
	  		}
	  		response.getOutputStream().flush();
	  		response.getOutputStream().close();
	  	} else {
	  		response.setContentType("application/x-msdownload");
			PrintWriter printwriter = response.getWriter();
			printwriter.println("<html>");
			printwriter.println("<br><br><br><h2>Could not get file name:<br>" + EgovWebUtil.clearXSSMinimum(xmlFile) + "</h2>");
			printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
			printwriter.println("<br><br><br>&copy; webAccess");
			printwriter.println("</html>");
			printwriter.flush();
			printwriter.close();
	  	}
	}

    /**
	 * 발송메일을 삭제한다.
	 * @param sndngMailVO SndngMailVO
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/whoya/cop/ems/deleteSndngMail.do")
	public @ResponseBody void deleteSndngMail(@ModelAttribute("sndngMailVO") SndngMailVO sndngMailVO, ModelMap model) throws Exception {
    	try {
	    	if (sndngMailVO == null || sndngMailVO.getMssageId() == null || sndngMailVO.getMssageId().equals("")) {
	    		throw new Exception("선택된 데이터가 없습니다.");
	    	}
	
	    	// 1. 발송메일을 삭제한다.
	    	sndngMailDetailService.deleteSndngMail(sndngMailVO);
	
	    	// 2. 첨부파일을 삭제한다.
	    	sndngMailDetailService.deleteAtchmnFile(sndngMailVO);
    	} catch ( Exception e ) {
    		e.printStackTrace();
    		throw e;
    	}
	}
//
//    /**
//	 * 발송메일 내용조회로 돌아간다.
//	 * @param sndngMailVO SndngMailVO
//	 * @return String
//	 * @exception Exception
//	 */
//    @RequestMapping(value="/cop/ems/backSndngMailDetail.do")
//	public String backSndngMailDtls(@ModelAttribute("sndngMailVO") SndngMailVO sndngMailVO, ModelMap model) throws Exception {
//
//    	return "redirect:/cop/ems/selectSndngMailList.do";
//	}
}