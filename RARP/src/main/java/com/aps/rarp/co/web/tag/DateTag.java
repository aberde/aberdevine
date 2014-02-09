package com.aps.rarp.co.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.aps.rarp.co.util.DateUtil;
import com.aps.rarp.co.util.FormatUtil;

public class DateTag extends SimpleTagSupport{
	
	private String value = null;
	private String type = null;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	public void doTag() throws IOException {
		
		JspContext ctx = getJspContext();
		JspWriter out = ctx.getOut();
		
		
		String today = DateUtil.getToday();
		String result = "" ; 
		
		if("today".equals(type)){
            result = DateUtil.getToday();
		}else{
		    result= FormatUtil.trnalateFormat(type, today);
		}
		out.print(result);
	}
	
}
