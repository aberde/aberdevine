package com.aps.rarp.co;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**  
 * @Class Name  : DspCoMessageSource.java
 * @Description : 메시지 리소스 사용을 위한 MessageSource 인터페이스 및 ReloadableResourceBundleMessageSource 클래스의 구현체
 * @Modification Information  
 * @
 * @  수정일      수정자       수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2012.05.21   김정수       최초생성
 * 
 * @author 김정수
 * @since 2012. 05.21
 * @version 1.0
 * @see
 * 
 *  Copyright (C) 2012 by SAMSUNG SDS co.,Ltd. All right reserved.
 */
public class RarpCoMessageSource extends ReloadableResourceBundleMessageSource implements MessageSource {

	protected static final Logger LOGGER = Logger.getLogger(RarpCoMessageSource.class);
	
	private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;

	/**
	 * getReloadableResourceBundleMessageSource() 
	 * @param reloadableResourceBundleMessageSource - resource MessageSource
	 * @return ReloadableResourceBundleMessageSource
	 */	
	public void setReloadableResourceBundleMessageSource(ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource) {
		this.reloadableResourceBundleMessageSource = reloadableResourceBundleMessageSource;
	}
	
	/**
	 * getReloadableResourceBundleMessageSource() 
	 * @return ReloadableResourceBundleMessageSource
	 */	
	public ReloadableResourceBundleMessageSource getReloadableResourceBundleMessageSource() {
		return reloadableResourceBundleMessageSource;
	}
	
	/**
	 * 정의된 메세지 조회
	 * @param code - 메세지 코드
	 * @return String
	 */	
	public String getMessage(String code) {
		return getReloadableResourceBundleMessageSource().getMessage(code, null, Locale.getDefault());
	}
	
	/**
	 * 정의된 메세지 조회
	 * @param code - 메세지 코드, parameter
	 * @return String
	 */	
	public String getMessage(String code, Object[] parameter ) {
		return getReloadableResourceBundleMessageSource().getMessage(code, parameter , Locale.getDefault());
	}
	
	/**
	 * 로케일별 메시지 조회
	 * @param code - 메세지 코드, Locale
	 * @return String
	 */
	public String getMessage(String code, Locale locale) {		
		return getReloadableResourceBundleMessageSource().getMessage(code, null, locale);
	}
	
	/**
	 * 모든 메시지 소스 불러오기
	 * @param code - 메세지 코드, parameter
	 * @return String
	 */	
    public Map<String, String> listAllAltLabels(String basename, Locale locale) {
        Map<String, String> altLabels = new HashMap<String, String>();

        PropertiesHolder propertiesHolder 	= getMergedProperties(locale);
        Properties properties 				= propertiesHolder.getProperties();

        for(Object key : properties.keySet()){
            if(((String)key).startsWith("alt.")) {
                altLabels.put((String)key, (String)properties.get(key));
            }
        }

        return altLabels;
    }
}
