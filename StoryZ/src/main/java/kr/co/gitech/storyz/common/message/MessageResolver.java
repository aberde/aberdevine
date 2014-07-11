package kr.co.gitech.storyz.common.message;

import java.util.Locale;

import org.springframework.context.support.MessageSourceAccessor;

public class MessageResolver {

	private static MessageSourceAccessor msAcc = null;
	private static Locale locale = Locale.KOREAN;

	public void setMessageSourceAccessor(MessageSourceAccessor msAcc){
		MessageResolver.msAcc = msAcc;
	}

	public static String getMessage(String key){
		return msAcc.getMessage(key, locale);
	}

	public static String getMessage(String key, Object[] objs){
		return msAcc.getMessage(key, objs, locale);
	}
}