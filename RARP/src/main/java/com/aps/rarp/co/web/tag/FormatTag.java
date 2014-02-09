package com.aps.rarp.co.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.aps.rarp.co.util.FormatUtil;

public class FormatTag extends SimpleTagSupport {

	private String type = null;
	private String value = null;
	

	/**
	 * type Getter Method
	 * 
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * type Setter Method
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * value Getter Method
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * value Setter Method
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	public void doTag() throws IOException {
		
		JspContext ctx = getJspContext();
		JspWriter out = ctx.getOut();
		
		String result = FormatUtil.trnalateFormat(type, value);
		
		out.print(result);
	}


}
