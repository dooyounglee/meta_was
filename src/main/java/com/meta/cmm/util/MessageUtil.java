package com.meta.cmm.util;

import java.util.Locale;

import com.meta.cmm.message.CustomMessageSource;


public class MessageUtil {

	private final static CustomMessageSource messageSource = (CustomMessageSource) BeanUtil.getBean("messageSource");
	
	private static Locale defulatLocale = Locale.KOREAN;
	
	public static String getMessage(String messageCode) {
		return messageSource.getMessage(messageCode, null, defulatLocale);
	}
	
	public static String getMessage(String messageCode, Object[] args) {
		return messageSource.getMessage(messageCode, args, defulatLocale);
	}
	
	public static String getMessage(String messageCode, Object[] args, Locale locale) {
		if (locale == null) locale = defulatLocale;
		return messageSource.getMessage(messageCode, args, locale);
	}
	
	public static void removeMessage(String messageCode) {
		messageSource.removeCode(messageCode, defulatLocale);
	}
}