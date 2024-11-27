package com.meta.cmm.util;

import org.springframework.context.ApplicationContext;

import com.meta.cmm.bean.ApplicationContextProvider;


public class BeanUtil {
    
    public static Object getBean(String beanId) {
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		return applicationContext.getBean(beanId);
	}
}
