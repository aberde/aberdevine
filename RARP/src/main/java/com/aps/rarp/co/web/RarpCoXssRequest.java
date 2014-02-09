package com.aps.rarp.co.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;

public class RarpCoXssRequest extends HttpServletRequestWrapper {
    
    protected static final Logger LOGGER = Logger.getLogger(RarpCoXssRequest.class);
    public RarpCoXssRequest(HttpServletRequest request) {
        super(request);
        LOGGER.debug( "DspCoXssRequest ");
    }

    public String getParameter(String key) {
        LOGGER.debug( "getParameter ");
        String value = null;
        try {
            value = super.getParameter(key);
            if (value != null) {
                value = xssReplace(key);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return value;
    }

    public String[] getParameterValues(String key) {
        LOGGER.debug( "getParameterValues ");
        String[] values =super.getParameterValues(key);   
        try {
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    values[i] = xssReplace(key);     
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return values;
    }
    
    private String xssReplace(String key){
        String rslt   = (super.getParameter(key) == null) ? "" : super.getParameter(key);
        if(rslt != null){
        	rslt = rslt.trim();
        }
        LOGGER.debug("=======================================================");
        LOGGER.debug("= before key =["+key+"]     rslt =["+rslt+"]");

        if(!key.equals("INIpluginData")&&
                !key.equals("_ETEExt_SEED_")&&
                !key.equals("ozrPath")&&
                //!key.equals("forward")&&
                rslt!=null &&
                !rslt.equals("")
                )
        {
            rslt = rslt.replaceAll("&","&#38;");
            rslt = rslt.replaceAll("/","&#47;");
            rslt = rslt.replaceAll("<","&#60;");
            rslt = rslt.replaceAll(">","&#62;");
            rslt = rslt.replaceAll("@","&#64;");
        }    

        LOGGER.debug("= after  key =["+key+"]     rslt =["+rslt+"]");
        LOGGER.debug("=======================================================");
        
        return rslt;
    }
}
