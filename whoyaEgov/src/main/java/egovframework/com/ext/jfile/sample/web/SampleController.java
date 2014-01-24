package egovframework.com.ext.jfile.sample.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.ext.jfile.view.JSonView;
import egovframework.com.ext.jfile.sample.service.SampleService;

@Controller
public class SampleController {
	
	@Autowired
	private SampleService service;
	
	@RequestMapping
	public void fileupload() {
		service.testConnection();
	}
	
	@IncludedInfo(name="JFile 가이드",order = 3000 ,gid = 100)
	@RequestMapping(value="/jfile/sample/guide.do")
	public String guide() {
		return "egovframework/com/ext/jfile/sample/guide";
	}

	/**
	 * 샘플 업로드 페이지
	 */
	@IncludedInfo(name="JFile 파일 업로드",order = 3010 ,gid = 100)
	@RequestMapping(value="/jfile/sample/site/upload.do")
	public String upload() {
		
		return "egovframework/com/ext/jfile/sample/site/upload";
	}
	
	/**
	 * 샘플 다운로드 페이지
	 */
	@IncludedInfo(name="JFile 파일 다운로드",order = 3020 ,gid = 100)
	@RequestMapping(value="/jfile/sample/site/download.do")
	public String  download() {
		
		return "egovframework/com/ext/jfile/sample/site/download";
	}
	
	@RequestMapping(value="/jfile/sample/site/cutdownSessionLimitTime.do")
	public ModelAndView cutdownSessionLimitTime(HttpServletRequest request) {
		System.out.println("SampleController.cutdownSessionLimitTime()");
		ModelAndView modelAndView = new ModelAndView(JSonView.NAME);
		request.getSession().setMaxInactiveInterval(10);
		return modelAndView;
	}
}
