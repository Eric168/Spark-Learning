package com.eric.self;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by wangfeng on 19/1/23.
 */
public class ApplicationContextHolder implements ApplicationContextAware {
    private static ApplicationContext ctx = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    public static <T> T getBean(String beanName) {
        return (T) ctx.getBean(beanName);
    }


    public static <T> T getBean(Class<T> requiredType) {
        return (T) ctx.getBean(requiredType);
    }
}

