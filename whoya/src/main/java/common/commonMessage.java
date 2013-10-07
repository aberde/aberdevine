package common;

import org.springframework.context.support.MessageSourceAccessor;

import whoya.whoyaMap;
import whoya.whoyaLib;

public class commonMessage {
	
	public static String getMessage(MessageSourceAccessor message, String str, whoyaMap whoyaMap){
		
		String returnVal = message.getMessage(str);
		
		if(null != whoyaMap && whoyaMap.size()>0){
			String[] text = whoyaLib.getTextKey(returnVal);
			if(null != text || text.length > 0){
				for(int i=0; i<text.length; i++){
					returnVal = whoyaLib.replaceTemplate(returnVal, text[i], whoyaMap.getString(text[i]));
				}
			}
		}
		
		/**
		if(null != whoyaMap && whoyaMap.size()>0){
			Set<String> keys = whoyaMap.keySet();
			Iterator<String> iter = keys.iterator();
	
			while (iter.hasNext()) {
				String key = iter.next();
				if(whoyaMap.getString(key).indexOf("<@") > -1){
					returnVal = whoyaMap.replaceTemplate(returnVal, key, whoyaMap.getString(key));
				}
			}
		}
		*/
		return returnVal;
	}
	
}
