package com.aps.rarp.co.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;


public class RarpCoWebDecrytResolver implements WebArgumentResolver{
	
	/*
	@Resource(name = "propertiesService")
	private EgovPropertyService propertiesService;
	 */
    protected static final Logger LOGGER = Logger.getLogger(RarpCoWebDecrytResolver.class);
    
	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webReq)
			throws Exception {
		
	   
		HttpServletRequest request = (HttpServletRequest) webReq.getNativeRequest();
		String iniParam = request.getParameter("INIpluginData");
		Class<?> clazz = methodParameter.getParameterType();

		LOGGER.debug("=========================================================================");
        LOGGER.debug("= iniParam        =["+ iniParam +"]");
        LOGGER.debug("= clazz.getCanonicalName() = "+clazz.getCanonicalName());
        LOGGER.debug("=========================================================================");

		/*if(!"".equals(iniParam) && iniParam != null&&clazz.getCanonicalName().contains("efros")){
    		HttpServletResponse response = null;
    		
    		//1. Initech 송수신 암호화 초기화
    		String propertiesFullPath = propertiesService.getString("initechFullPath");		
    		
    		IniPlugin mIP = new IniPlugin(request,response,propertiesFullPath);
    		
    		try{
    			mIP.init();
    		}catch(Exception e){
    		    LOGGER.debug(e); //e.printStackTrace();
    		}
    
    		Field[] allFields=clazz.getDeclaredFields();
    		
    		try
            {
                String fieldName = null;
                Object targetClazz = clazz.newInstance();
                
                for(int i=0; i<allFields.length; i++)
                {
                    fieldName = (String) allFields[i].getName();
                    LOGGER.debug("= fieldName        =["+ fieldName +"]"); 
                    if(mIP.getParameter(fieldName) != null)
                    {
                        String setMethodName = "set"+ fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                        Class<?>  fieldType     = clazz.getDeclaredField(fieldName).getType();
                        Method setMethod     = clazz.getMethod(setMethodName, new Class[] {fieldType});
                        String value         = (mIP.getParameter(fieldName) == null) ? "" : mIP.getParameter(fieldName);
                        value = value.trim();
                        setMethod.invoke(targetClazz, new Object[] {value});
                        LOGGER.debug("=========================================================================");
                        LOGGER.debug("= value        =["+ value +"]");
                        LOGGER.debug("=========================================================================");
                    }
                } 
               
                return targetClazz;
            } 
            catch(Exception e)
            {
                LOGGER.debug(e); //e.printStackTrace();
            }
    	}*/
		
		return UNRESOLVED;
	}
}
