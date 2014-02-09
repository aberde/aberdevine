package com.aps.rarp.co.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * Property 서비스를 UI 단에서 태그 형식으로 사용하기 위한
 * Custom Tag Library
 * @author 기술지원팀 김정수
 * @since 2012. 05.29
 * <pre>
 * == 개정이력(Modification Information) ==
 *
 * 수정일                		수정자 	 			수정내용
 * ------------		---------------		---------------------------
 * 2012. 05.29                기술지원팀 김정수	           최초 생성
 *</pre>
 */

public class PropertyTag extends SimpleTagSupport {

	private static final Logger LOGGER = Logger.getLogger(PropertyTag.class);
	
	private String key; 
	private String var; 

	/**
	 * key Setter Method
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * var Setter Method
	 * @param var
	 */
	public void setVar(String var) {
		this.var = var;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	public void doTag() throws JspException {
		JspContext ctx = getJspContext();
		JspWriter out = ctx.getOut();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(((PageContext) ctx).getServletContext());
		EgovPropertyService propertiesService = (EgovPropertyService) wac.getBean("egovPropertiesService");
		
		try {
			if(!StringUtil.isEmpty(var)){
				getJspContext().setAttribute(var, propertiesService.getString(key));
			}
			else{
				out.print(propertiesService.getString(key));
			}
			
		} catch (IOException e) {
		    LOGGER.error(e);
		}
	}
	
}
