/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aps.rarp.co.web;

import javax.servlet.ServletContext;
import org.springframework.web.context.ServletContextAware;
import egovframework.rte.ptl.mvc.tags.ui.pagination.AbstractPaginationRenderer;

/**  
 * @Class Name : ImagePaginationRenderer.java
 * @Description : ImagePaginationRenderer Class
 * @Modification Information  
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2013.04.09  김명진         최초생성
 * 
 * @author IITECH 개발팀 김명진
 * @since 2009. 03.16
 * @version 1.0
 * @see
 * 
 *  Copyright (C) by KCA. All right reserved.
 */
public class RarpCoWebImgPaginationRenderer extends AbstractPaginationRenderer implements ServletContextAware{
	
	private ServletContext servletContext;
	
    /**
     * PaginationRenderer
     */
    public RarpCoWebImgPaginationRenderer() {

    }

    
    /**
    * initVariables
    */
    public void initVariables(){

		//String strWebDir = "/egovframework.eguideprogram.basic/images/egovframework/cmmn/"; // localhost
		//String strWebDir = "/###ARTIFACT_ID###/images/dbcsc/cms/btn/";
		String strWebDir    = servletContext.getContextPath()+"/images/dbcsc/cms/btn";
		
		firstPageLabel 		= "<span class=\"first\"><a href=\"#\" onclick=\"{0}({1}); return false;\" onkeypress=\"{0}({1}); return false;\"><img src=\""+strWebDir+"/btn_prev02.gif\" alt=\"첫페이지\" border=\"0\"/></a></span>"; 
        previousPageLabel 	= "<span class=\"previous\"><a href=\"#\" onclick=\"{0}({1}); return false;\" onkeypress=\"{0}({1}); return false;\"><img src=\""+strWebDir+"/btn_prev01.gif\" alt=\"이전페이지\" border=\"0\"/></a></span>";
        currentPageLabel 	= "<span class=\"current\"><b>{0}</b></span>";
        otherPageLabel 		= "<span onclick=\"{0}({1}); return false;\" onkeypress=\"{0}({1}); return false;\" class=\"cursor\"><a href=\"#\" onclick=\"return false;\">{2}</a></span>";
        nextPageLabel 		= "<span class=\"next\"><a href=\"#\" onclick=\"{0}({1}); return false;\" onkeypress=\"{0}({1}); return false;\"><img src=\""+strWebDir+"/btn_next01.gif\" alt=\"다음페이지\" border=\"0\"/></a></span>";
        lastPageLabel 		= "<span class=\"last\"><a href=\"#\" onclick=\"{0}({1}); return false;\" onkeypress=\"{0}({1}); return false;\"><img src=\""+strWebDir+"/btn_next02.gif\" alt=\"끝페이지\" border=\"0\"/></a></span>";

	}
    
    public void setServletContext(ServletContext servletContext) {
    	this.servletContext = servletContext;
    	   initVariables();
    }
}
